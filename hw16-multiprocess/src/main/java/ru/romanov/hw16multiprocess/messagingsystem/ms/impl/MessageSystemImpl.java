package ru.romanov.hw16multiprocess.messagingsystem.ms.impl;

import lombok.extern.slf4j.Slf4j;
import ru.romanov.hw16multiprocess.common.model.domain.MsMessage;
import ru.romanov.hw16multiprocess.common.ms.api.MessageSystem;
import ru.romanov.hw16multiprocess.common.ms.api.MsClient;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public final class MessageSystemImpl implements MessageSystem {
    private static final int MESSAGE_QUEUE_SIZE = 1_000;
    private static final int MSG_HANDLER_THREAD_LIMIT = 2;

    private final AtomicBoolean runFlag = new AtomicBoolean(true);

    private final Map<String, MsClient> clientMap = new ConcurrentHashMap<>();
    private final BlockingQueue<MsMessage> messageQueue = new ArrayBlockingQueue<>(MESSAGE_QUEUE_SIZE);

    public MessageSystemImpl() {
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
        log.info("msgProcessor started\n");
        while (runFlag.get()) {
            try {
                MsMessage msg = messageQueue.take();
                if (msg == MsMessage.VOID_MS_MESSAGE) {
                    log.info("received the stop message");
                } else {
                    MsClient clientTo = clientMap.get(msg.getTo());
                    if (clientTo == null) {
                        log.warn("client not found");
                    } else {
                        msgHandler.submit(() -> handleMessage(clientTo, msg));
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

    private void messageHandlerShutdown() {
        msgHandler.shutdown();
        log.info("msgHandler has been shut down");
    }

    private void handleMessage(MsClient msClient, MsMessage msg) {
        try {
            msClient.handle(msg);
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
    public void addClient(MsClient msClient) {
        log.info("new client:{}", msClient.getName());
        if (clientMap.containsKey(msClient.getName())) {
            throw new IllegalArgumentException("Error. client: " + msClient.getName() + " already exists");
        }
        clientMap.put(msClient.getName(), msClient);
    }

    @Override
    public void removeClient(String clientId) {
        MsClient removedClient = clientMap.remove(clientId);
        if (removedClient == null) {
            log.warn("client not found: {}", clientId);
        } else {
            log.info("removed client:{}", removedClient);
        }
    }

    @Override
    public boolean newMessage(MsMessage msg) {
        if (runFlag.get()) {
            return messageQueue.offer(msg);
        } else {
            log.warn("MS is being shutting down... rejected:{}", msg);
            return false;
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
