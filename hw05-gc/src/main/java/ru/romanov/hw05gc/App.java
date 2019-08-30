package ru.romanov.hw05gc;

import java.util.ArrayDeque;
import java.util.Deque;

public class App {

    public static void main(String[] args) throws InterruptedException {
        long count = 0;
        long startTime = System.currentTimeMillis();
        long lastTime = 0;
        long pause = 1;
        Deque<Integer> deque = new ArrayDeque<>();

        while (true) {
            deque.offer((int) (Math.random() * 10));
            deque.offer((int) (Math.random() * 10));
            deque.offer((int) (Math.random() * 10));
            deque.remove();
            deque.remove();
            count = count + 4;
            long currentTime = System.currentTimeMillis();
            long time = (currentTime - startTime) / 1000;
            if (time == lastTime + 1) {
                System.out.println("time = " + time + "c " + " count = " + count / 1_000_000);
                lastTime++;
            }
            long mln = count / 100_000_000;
            if (mln > pause) {
                Thread.sleep(2000);
                pause++;
            }
        }
    }
}
