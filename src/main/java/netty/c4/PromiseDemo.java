package netty.c4;

import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

/**
 * @author ：limaolin
 * @date ：Created in 2022/12/5 11:28
 * @description：
 * @modified By：
 */
@Slf4j
public class PromiseDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        EventLoop eventLoop = new NioEventLoopGroup().next();
        DefaultPromise<Integer> integerPromise = new DefaultPromise<>(eventLoop);
        new Thread(
                ()->{
                    try {
                        int i = 1/0;
                        Thread.sleep(50);
                        integerPromise.setSuccess(555);
                    } catch (Exception e) {
                       // throw new RuntimeException(e);
                        integerPromise.setFailure(e);
                    }
                }
        ).start();

        Integer result = integerPromise.get();
        integerPromise.addListener(new GenericFutureListener<Future<? super Integer>>() {
            @Override
            public void operationComplete(Future<? super Integer> future) throws Exception {
                log.debug(String.valueOf(result));

            }
        });

      //  log.debug(String.valueOf(result));
    }
}
