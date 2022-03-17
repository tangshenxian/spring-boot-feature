package com.shenxian;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: shenxian
 * @date: 2022/3/17 13:57
 */
@SpringBootTest
public class Test {

    public static ExecutorService executor = Executors.newFixedThreadPool(10);

    /**
     * runAsync无返回值
     */
    @org.junit.Test
    public void test1() {
        System.out.println("test()方法开始了。。。");
        CompletableFuture.runAsync(() -> {
            System.out.println("异步线程开始执行。。。");
            System.out.println("执行业务逻辑。。。");
            System.out.println("异步线程执行结束。。。");
        }, executor);
        System.out.println("test()方法执行结束");
    }

    /**
     * supplyAsync有返回值
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @org.junit.Test
    public void test2() throws ExecutionException, InterruptedException {
        System.out.println("test()方法开始了。。。");
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("异步线程开始了。。。");
            System.out.println("执行业务逻辑。。。");
            System.out.println("异步线程结束了。。。");
            return "我是返回值";
        }, executor);
        // completableFuture.get()会阻塞当前线程，直到异步线程执行结束，才会回复当前线程执行
        System.out.println("test()方法结束了。。。" + completableFuture.get());
    }

    /**
     * whenComplete同步执行回调
     *
     * whenComplete方法虽然能获得异常信息，但是无法修改CompletableFuture的返回值，
     * 因此可以链式调用exceptionally方法，指定一个出现异常以后默认的返回值
     */
    @org.junit.Test
    public void test3() {
        System.out.println("test()方法开始了。。。");
        CompletableFuture.supplyAsync(() -> {
            System.out.println("异步线程开始了。。。");
            System.out.println("执行业务逻辑。。。");
            System.out.println("异步线程结束了。。。");
            return "我是返回值";
        }, executor).whenComplete(((result, throwable) -> {
            System.out.println("异步线程执行结束，返回结果：" + result);
            System.out.println("异步任务执行结束，异常：" + throwable);
            System.out.println("whenComplete线程：" + Thread.currentThread().getName());
        })).exceptionally(throwable -> {
            System.out.println("异常：" + throwable);
            return "异常返回值";
        });
        System.out.println("test()方法结束了。。。");
    }

    /**
     * whenCompleteAsync异步执行回调
     */
    @org.junit.Test
    public void test4() {
        System.out.println("test()方法开始了。。。");
        CompletableFuture.supplyAsync(() -> {
            System.out.println("异步线程开始了。。。");
            System.out.println("执行业务逻辑。。。");
            System.out.println("异步线程执行结束。。。");
            return "我是返回值";
        }, executor).whenCompleteAsync((result, throwable) -> {
            System.out.println("异步任务执行结束，返回结果：" + result);
            System.out.println("异步任务执行结束，异常：" + throwable);
            System.out.println("whenCompleteAsync线程：" + Thread.currentThread().getName());
        }).exceptionally(throwable -> {
            System.out.println("异常：" + throwable);
            return "异常返回值";
        });
        System.out.println("test()方法执行结束。。。");
    }

    /**
     * handle
     *
     * handle是方法执行结束后的处理，无论是成功结束，还是失败结束。complete系列方法只能感知异常，
     * 若要返回异常信息，只能在后面加exceptionally()方法
     */
    @org.junit.Test
    public void test5() {
        System.out.println("test()方法开始了。。。");
        CompletableFuture.supplyAsync(() -> {
            System.out.println("异步线程开始了。。。");
            System.out.println("执行业务逻辑。。。");
            System.out.println("异步线程结束了。。。");
            return "我是返回值";
        }, executor).handle((result, throwable) -> {
            System.out.println("handle执行线程：" + Thread.currentThread().getName());
            if (result != null) {
                return result;
            }
            if (throwable != null) {
                return "handle exception返回";
            }
            return "handle返回";
        });
        System.out.println("test()方法执行结束。。。");
    }

    // 线程串行化 假设B线程需要A线程执行完成之后才能继续执行，则需要线程串行化

    /**
     * thenRun系列方法，没有返回值，不需要依赖A返回的结果
     */
    @org.junit.Test
    public void test6() {
        System.out.println("test()方法开始了。。。");
        CompletableFuture.supplyAsync(() -> {
            System.out.println("异步线程开始了。。。");
            System.out.println("执行业务逻辑。。。");
            System.out.println("异步线程结束了。。。");
            return "我是返回值";
        }, executor).thenRun(() -> {
            System.out.println("thenRun执行线程：" + Thread.currentThread().getName());
        });
        System.out.println("test()方法结束了。。。");
    }

    /**
     * thenRun -> thenRunAsync 异步执行
     */
    @org.junit.Test
    public void test7() {
        System.out.println("test()方法开始了。。。");
        CompletableFuture.supplyAsync(() -> {
            System.out.println("异步线程开始了。。。");
            System.out.println("执行业务逻辑。。。");
            System.out.println("异步线程结束了。。。");
            return "我是返回值";
        }, executor).thenRunAsync(() -> {
            System.out.println("thenRun执行线程：" + Thread.currentThread().getName());
        });
        System.out.println("test()方法结束了。。。");
    }

    /**
     * thenAccept系列方法，没有返回值，需要依赖A返回的结果
     */
    @org.junit.Test
    public void test8() {
        System.out.println("test()方法开始了。。。");
        CompletableFuture.supplyAsync(() -> {
            System.out.println("异步线程开始了。。。");
            System.out.println("执行业务逻辑。。。");
            System.out.println("异步线程结束了。。。");
            return "我是返回值";
        }, executor).thenAcceptAsync((result) -> {
            System.out.println("thenAcceptAsync执行线程：" + Thread.currentThread().getName());
            System.out.println("thenAcceptAsync()接收到返回值" + result);
        });
        System.out.println("test()方法执行结束。。。");
    }

    /**
     * thenApply系列方法，有返回值，依赖A返回的结果
     */
    @org.junit.Test
    public void test9() throws ExecutionException, InterruptedException {
        System.out.println("test()方法开始了。。。");
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("异步线程开始了。。。");
            System.out.println("执行业务逻辑。。。");
            System.out.println("异步线程结束了。。。");
            return "我是返回值";
        }, executor).thenApplyAsync((result) -> {
            System.out.println("thenApplyAsync执行线程：" + Thread.currentThread().getName());
            System.out.println("thenApplyAsync()接收到返回值：" + result);
            return "thenApplyAsync返回结果";
        });
        System.out.println("test()方法执行结束。。。");
        System.out.println("异步线程执行结果：" + completableFuture.get());
    }

    // 多任务组合

    /**
     * allOf，等待所有任务都执行完成
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @org.junit.Test
    public void test10() throws ExecutionException, InterruptedException {
        System.out.println("test()方法开始了。。。");
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("future1执行开始。。。");
            System.out.println("future1执行结束。。。");
            return "future1执行结果";
        }, executor);
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("future2执行开始。。。");
            System.out.println("future2执行结束。。。");
            return "future2执行结果";
        }, executor);
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("future3执行开始。。。");
            System.out.println("future3执行结束。。。");
            return "future3执行结果";
        }, executor);
        CompletableFuture<Void> allOf = CompletableFuture.allOf(future1, future2, future3);
        allOf.get();
        System.out.println("test()方法执行结束。。。");
    }

    /**
     * anyOf，等待任何一个任务执行完成
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @org.junit.Test
    public void test11() throws ExecutionException, InterruptedException {
        System.out.println("test()方法开始了。。。");
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("future1执行开始。。。");
            System.out.println("future1执行结束。。。");
            return "future1执行结果";
        }, executor);
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("future2执行开始。。。");
            System.out.println("future2执行结束。。。");
            return "future2执行结果";
        }, executor);
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("future3执行开始。。。");
            System.out.println("future3执行结束。。。");
            return "future3执行结果";
        }, executor);
        CompletableFuture<Object> anyOf = CompletableFuture.anyOf(future1, future2, future3);
        System.out.println("test()方法执行结束。。。" + anyOf.get());
    }

}
