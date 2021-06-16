package ru.netology.pyas;

import java.util.concurrent.Callable;

public class BabblingCallable implements Callable<Integer> {

    private String name;
    private long timeout;
    private long time;

    public BabblingCallable(String name, long timeout, long time) {
        this.name = name;
        this.timeout = timeout;
        this.time = time;
    }

    @Override
    public Integer call() throws Exception {
        int result = 0;

        try {
            while(time >= 0) {
                Thread.sleep(timeout);
                System.out.println(name + ": Всем привет!");
                time -= timeout;
                ++result;
            }
        } catch (InterruptedException err) {
        } finally{
            System.out.println(name + " завершен");
        }

        return result;
    }

    public String getName() {
        return name;
    }
}
