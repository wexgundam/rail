package com.critc.network.service;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * what:    测试线程池. <br/>
 * when:    (这里描述这个类的适用时机 – 可选).<br/>
 * how:     (这里描述这个类的使用方法 – 可选).<br/>
 * warning: (这里描述这个类的注意事项 – 可选).<br/>
 *
 * @author 靳磊 created on 2019/9/10
 */
public class TestExecutorService {
    @Test
    public void testExecutorService() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<FutureTask<String>> futureTasks = new ArrayList<>();

        for (int index = 0; index < 20; index++) {
            FutureTask<String> futureTask = new FutureTask<>(new Task(index));
            futureTasks.add(futureTask);
            executorService.submit(futureTask);
        }

        executorService.shutdown();

        for (FutureTask<String> task : futureTasks) {
            System.out.println(task.get());
        }

        System.out.println("All task is executed.");
    }

    class Task implements Callable<String> {
        private int index;

        Task(int index) {
            this.index = index;
        }

        @Override
        public String call() throws Exception {
            return "Executed Task[" + index + "].";
        }
    }
}
