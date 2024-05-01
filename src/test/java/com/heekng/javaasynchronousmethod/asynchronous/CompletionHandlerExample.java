package com.heekng.javaasynchronousmethod.asynchronous;

import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CompletionHandlerExample {

    @Test
    @DisplayName("CompletionHandler을 사용한 비동기 처리")
    void test() throws Exception {
        CompletionHandler<String, Void> completionHandler = new CompletionHandler<>() {
            @Override
            public void completed(String result, Void attachment) {
                logging(String.format("결과: %s, 두 번째 작업 시작", result));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                logging("두 번째 작업 완료");
            }

            @Override
            public void failed(Throwable exc, Void attachment) {
                logging(String.format("두 번째 작업 실패 | %s", exc.toString()));
            }
        };

        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.submit(() -> {
            logging("첫 번째 작업 시작");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            logging("첫 번째 작업 완료");

            completionHandler.completed("성공", null);
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

    private static void logging(String text) {
        System.out.println(Thread.currentThread().getName() + " | " + text);
    }
}
