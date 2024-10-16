package com.example.thread.category;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: thread-practice
 * @description:
 * @author: whl
 * @create: 2024-10-16 15:24
 **/
public class TestThread implements ThreadFactory {
    private AtomicInteger threadId = new AtomicInteger(0);

    private String namePrefix;

    public TestThread(String prefix) {
        this.namePrefix = prefix;
    }


    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName(namePrefix + "测试" + threadId.getAndIncrement());//自增（应该是）
        return thread;
    }
}
