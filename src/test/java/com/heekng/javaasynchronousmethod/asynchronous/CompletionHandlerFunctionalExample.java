package com.heekng.javaasynchronousmethod.asynchronous;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CompletionHandlerFunctionalExample {

    static ExecutorService executorService;

    @Test
    @DisplayName("Functional CompletionHandler을 사용한 비동기 처리")
    void test() throws Exception {
        executorService = Executors.newCachedThreadPool();
        execute(param -> {
            logging(String.format("결과: %s, 두 번째 작업 시작", param));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            logging("두 번째 작업 종료");
        });

        logging("별개 작업 시작");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        logging("별개 작업 완료");

        Thread.sleep(3000);

    }

    static void execute(Consumer<String> callback) {
        executorService.submit(() -> {
            logging("첫 번째 작업 시작");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            logging("첫 번째 작업 완료");
            callback.accept("성공");
        });
    }

    private static void logging(String text) {
        System.out.println(Thread.currentThread().getName() + " | " + text);
    }
}
