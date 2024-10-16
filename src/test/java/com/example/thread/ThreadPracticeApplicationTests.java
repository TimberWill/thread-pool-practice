package com.example.thread;

import com.example.thread.category.TestThread;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.*;

@SpringBootTest
class ThreadPracticeApplicationTests {

    /**
     * singleThreadPool
     */
    @Test
    void singTheadPoolTest() {
        ExecutorService executorService = Executors.newSingleThreadExecutor(new TestThread("singleThreadPool"));
        for (int i = 0; i < 10; i++) {
            executorService.submit(()->{
                System.out.println(Thread.currentThread().getName());
            });
        }
    }

    /**
     * cacheThreadPool
     */
    @Test
    void cacheThreadPoolTest(){
        ExecutorService executorService = Executors.newCachedThreadPool(new TestThread("cacheThreadPool"));
        for (int i = 0; i < 10; i++) {
            executorService.submit(()->{
                System.out.println(Thread.currentThread().getName());
            });
        }
    }

    /**
     * fixedThreadPool
     */
    @Test
    void fixedThreadPoolTest(){
        ExecutorService executorService = Executors.newFixedThreadPool(5,new TestThread("fixedThreadPool"));
        for (int i = 0; i < 10; i++) {
            executorService.submit(()->{
                System.out.println(Thread.currentThread().getName());
            });
        }

    }

    /**
     * scheduledThreadPool
     */
    @Test
    void scheduledThreadPool(){
        //可以指定核心线程数
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3, new TestThread("scheduledThreadPool"));
        //定时执行一次的任务，延迟1s后执行
        executorService.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ", delays 1s");
            }
        },1, TimeUnit.SECONDS);

        //延迟2s后，每3s周期性执行一次
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "delays 2s, per 3s");
            }
        }, 2, 3, TimeUnit.SECONDS);
    }

    /**
     * ThreadPoolExecutor 手写一个线程池
     */
    @Test
    void threadPoolExecutorTest(){
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                30,
                100,
                30,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10000),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            executor.execute(()->{
                System.out.println("第" + finalI + "次：" + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            });
        }

        executor.shutdown();//关闭线程池
    }
}
