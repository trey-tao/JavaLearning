package com.trey.completablefuture;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @FileName: CompleteableFutureDemo.java
 * @Description: CompleteableFutureDemo.java类说明
 * @Author: tao.shi
 * @Date: 2018/11/21 15:01
 */
public class CompleteableFutureDemo {

    private static Random rand = new Random();
    private static long t = System.currentTimeMillis();

    static int getMoreData() {
        System.out.println("begin to start compute");
        try{
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    System.out.println("end to start compute. passed " + (System.currentTimeMillis() - t)/1000 + " seconds");
        return rand.nextInt(1000);
    }

    /**
     * 纯消费（执行Action）future.get()返回null
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void testThenAccept() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            return 100;
        });
        CompletableFuture<Void> f =  future.thenAccept(System.out::println);
        System.out.println(f.get());
    }

    private static void testThenApplyAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            return 100;
        });
        CompletableFuture<String> f =  future.thenApplyAsync(i -> i * 5).thenApply(i -> "Hello, " + i.toString());
        System.out.println(f.get()); //"1000
    }

    /**
     * future作为参数 进行二次计算
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void testThenAcceptBoth() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            return 100;
        });
        CompletableFuture<Void> f = future.thenAcceptBoth(CompletableFuture.completedFuture(5),(x,y) -> System.out.println(x * y));
        System.out.println(f.get());
    }

    /**
     * future结果被忽略了
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void testThenRun() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            return 100;
        });
        CompletableFuture<Void> f =  future.thenRun(() -> System.out.println("finished"));
        System.out.println(f.get());
    }

    private static void testThenCompose() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 120);
        CompletableFuture<String> f =  future.thenCompose( i -> CompletableFuture.supplyAsync(() -> (i * 10) + ""));
    /**
     * CompletableFuture<String> f = future.thenCompose( i -> {
     *      return CompletableFuture.supplyAsync(() -> {
     *          return (i * 10) + "";
     *      });
     * });
     */
    System.out.println(f.join()); // 1000
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

//        testThenAccept();
        testThenCompose();
  }



}
