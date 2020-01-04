package ru.romanov.hw14seqofnumbers;

public class App {

    private static final int LOW_COUNT = 2;
    private static final int HIGH_COUNT = 1;
    private static final int MAX_THREAD_COUNT = 3;
    private static final int MIN_NUMBER = 1;
    private static final int MAX_NUMBER = 10;
    private static final Object o = new Object();
    private static int globalCounter = 0;

    public static void main(String[] args) {
        new Thread(App::action).start();
        new Thread(App::action).start();
        new Thread(App::action).start();
    }

    private static void action() {
        int threadNumber = Integer.parseInt(Thread.currentThread().getName().split("-")[1]);
        int realLowCount = 0, realHighCount = 0;
        int number = 1;
        while ((realLowCount != LOW_COUNT) || (realHighCount != HIGH_COUNT)) {
            synchronized (o) {
                while ((globalCounter) % (MAX_THREAD_COUNT) != threadNumber) {
                    try {
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println(Thread.currentThread().getName() + ": " + number);

                globalCounter++;

                if (number == MIN_NUMBER) {
                    realLowCount++;
                } else if (number == MAX_NUMBER) {
                    realHighCount++;
                }

                if (realLowCount > realHighCount) {
                    number++;
                } else if (realLowCount == realHighCount) {
                    number--;
                }

                o.notifyAll();
            }
        }
    }
}
