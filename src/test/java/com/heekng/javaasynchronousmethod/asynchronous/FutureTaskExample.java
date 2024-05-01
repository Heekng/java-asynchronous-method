package com.heekng.javaasynchronousmethod.asynchronous;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FutureTaskExample {
    @Test
    @DisplayName("FutureTask를 이용한 비동기 처리")
    void test() throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();

        new FutureTask<>(() -> {
            logging("첫 번째 작업 시작");
            Thread.sleep(1000);
            logging("첫 번째 작업 완료");
            return "성공";
        });
        Future<String> future = executorService.submit(() -> {
            logging("첫 번째 작업 시작");
            Thread.sleep(1000);
            logging("첫 번째 작업 완료");
            return "성공";
        });

        logging("두 번째 작업 시작");
        try {
            logging(String.format("첫 번째 작업 성공 여부: %s", future.isDone()));
            String result = future.get();
            logging(String.format("첫 번째 작업 종료 여부: %s", future.isDone()));
            logging(String.format("첫 번째 작업 완료 결과: %s", result));
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        logging("두 번째 작업 완료");
    }

    private static void logging(String text) {
        System.out.println(Thread.currentThread().getName() + " | " + text);
    }
}
