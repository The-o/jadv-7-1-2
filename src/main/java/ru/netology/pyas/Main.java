/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package ru.netology.pyas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    private final static int THREADS_COUNT = 4;
    private final static int MIN_TIMEOUT = 2000;
    private final static int MAX_TIMEOUT = 3000;
    private final static int MIN_TIME = 10000;
    private final static int MAX_TIME = 20000;

    public static void main(String[] args) {
        List<BabblingCallable> babblers = getBabblers();
        ExecutorService pool = Executors.newFixedThreadPool(THREADS_COUNT);

        try {
            List<Future<Integer>> result = pool.invokeAll(babblers);
            for (int i = 0; i < result.size(); ++i) {
                BabblingCallable babbler = babblers.get(i);
                try {
                    int count = result.get(i).get();
                    System.out.printf("%s сказал %d раз%n", babbler.getName(), count);
                } catch (ExecutionException e) {
                    System.out.printf("Ошибка в %s%n", babbler.getName());
                }
            }
        } catch (InterruptedException e) {

        }

        System.out.println();
        babblers = getBabblers();

        try {
            int count = pool.invokeAny(babblers);
            System.out.printf("Самый быстрый поток сказал %d раз%n", count);
        } catch (InterruptedException e) {
        } catch (ExecutionException e) {
        }

        pool.shutdown();
    }

    private static List<BabblingCallable> getBabblers() {
        Random rng = new Random();
        List<BabblingCallable> result = new ArrayList<>();

        for (int i = 1; i <= THREADS_COUNT; ++i) {
            String name = "Поток " + String.valueOf(i);
            long timeout = rng.nextInt(MAX_TIMEOUT - MIN_TIMEOUT) + MIN_TIMEOUT;
            long time = rng.nextInt(MAX_TIME - MIN_TIME) + MIN_TIME;
            result.add(new BabblingCallable(name, timeout, time));
        }
        return result;
    }
}
