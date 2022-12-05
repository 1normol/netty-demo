package netty.c4;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author ：limaolin
 * @date ：Created in 2022/12/5 11:21
 * @description：
 * @modified By：
 */
@Slf4j
public class FutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);
        Future<Integer> future = fixedThreadPool.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(50);

                return 50;
            }
        });
        log.debug(String.valueOf(future.get()));

    }




}
