package ru.romanov.hw16multiprocess.messagingsystem.ms.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.romanov.hw16multiprocess.common.model.ServiceType;
import ru.romanov.hw16multiprocess.common.model.domain.MsMessage;
import ru.romanov.hw16multiprocess.common.ms.api.MessageSystem;
import ru.romanov.hw16multiprocess.messagingsystem.socket.Client;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.romanov.hw16multiprocess.common.model.ServiceType.DATABASE;
import static ru.romanov.hw16multiprocess.common.model.ServiceType.FRONTEND;
import static ru.romanov.hw16multiprocess.common.model.ServiceType.valueOfName;

@Slf4j
public final class MessageSystemImpl implements MessageSystem {
    private static final int MESSAGE_QUEUE_SIZE = 1_000;
    private static final int DATABASE_MESSAGE_QUEUE_SIZE = 10;
    private static final int MSG_HANDLER_THREAD_LIMIT = 2;

    private final AtomicBoolean runFlag = new AtomicBoolean(true);
    private final Map<UUID, Integer> frontendClientMap = new ConcurrentHashMap<>();
    private final BlockingQueue<Integer> databaseClientQueue = new ArrayBlockingQueue<>(DATABASE_MESSAGE_QUEUE_SIZE);
    private final BlockingQueue<MsMessage> messageQueue = new ArrayBlockingQueue<>(MESSAGE_QUEUE_SIZE);

    private final Client client;

    public MessageSystemImpl(Client client) {
        this.client = client;
        msgProcessor.submit(this::msgProcessor);
    }

    private final ExecutorService msgProcessor = Executors.newSingleThreadExecutor(runnable -> {
        Thread thread = new Thread(runnable);
        thread.setName("msg-processor-thread");
        return thread;
    });

    private final ExecutorService msgHandler = Executors.newFixedThreadPool(MSG_HANDLER_THREAD_LIMIT, new ThreadFactory() {
        private final AtomicInteger threadNameSeq = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setName("msg-handler-thread-" + threadNameSeq.incrementAndGet());
            return thread;
        }
    });

    private void msgProcessor() {
        log.info("msgProcessor started");
        while (runFlag.get()) {
            try {
                MsMessage msg = messageQueue.take();
                if (msg == MsMessage.VOID_MS_MESSAGE) {
                    log.info("received the stop message");
                } else {
                    Integer clientToPort = getClientToPort(msg);
                    if (clientToPort == null) {
                        log.warn("client not found");
                    } else {
                        msgHandler.submit(() -> handleMessage(clientToPort, msg));
                    }
                }
            } catch (InterruptedException ex) {
                log.error(ex.getMessage(), ex);
                Thread.currentThread().interrupt();
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        }
        msgHandler.submit(this::messageHandlerShutdown);
        log.info("msgProcessor finished");
    }

    private Integer getClientToPort(MsMessage msg) throws Exception {
        ServiceType type = valueOfName(msg.getTo());
        switch (type) {
            case FRONTEND:
                UUID uuid = msg.getSourceMessageId()
                        .orElseThrow(() -> new Exception("UUID = null!"));
                return frontendClientMap.remove(uuid);
            case DATABASE:
                Integer port = databaseClientQueue.take();
                return port;
        }
        return null;
    }

    private void messageHandlerShutdown() {
        msgHandler.shutdown();
        log.info("msgHandler has been shut down");
    }

    private void handleMessage(Integer clientToPort, MsMessage msg) {
        try {
            client.newMessage(clientToPort, msg);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            log.error("message:{}", msg);
        }
    }

    private void insertStopMessage() throws InterruptedException {
        boolean result = messageQueue.offer(MsMessage.VOID_MS_MESSAGE);
        while (!result) {
            Thread.sleep(100);
            result = messageQueue.offer(MsMessage.VOID_MS_MESSAGE);
        }
    }

    @Override
    public boolean addDatabaseClient(Integer port) {
        log.info("registered new client: port={}", port);
        return databaseClientQueue.offer(port);
    }

    @Override
    public boolean removeDatabaseClient(Integer clientPort) {
        boolean result = databaseClientQueue.remove(clientPort);
        if (!result) {
            log.warn("client with port={} not found", clientPort);
        } else {
            log.info("removed client: port={}", clientPort);
        }
        return result;
    }

    @Override
    public void newMessage(Integer clientFromPort, MsMessage msg) {
        if (runFlag.get()) {
            if (FRONTEND.getName().equals(msg.getFrom())) {
                frontendClientMap.put(msg.getId(), clientFromPort);
            } else if (DATABASE.getName().equals(msg.getFrom())) {
                databaseClientQueue.offer(clientFromPort);
            }
            boolean result = messageQueue.offer(msg);
            if (!result) {
                if (FRONTEND.getName().equals(msg.getFrom())) {
                    frontendClientMap.remove(msg.getId());
                } else if (DATABASE.getName().equals(msg.getFrom())) {
                    databaseClientQueue.remove(clientFromPort);
                }
            }
        } else {
            log.warn("MS is being shutting down... rejected:{}", msg);
        }
    }

    @Override
    public void dispose() throws InterruptedException {
        runFlag.set(false);
        insertStopMessage();
        msgProcessor.shutdown();
        msgHandler.awaitTermination(60, TimeUnit.SECONDS);
    }
}
