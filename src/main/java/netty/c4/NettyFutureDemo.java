package netty.c4;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * @author ：limaolin
 * @date ：Created in 2022/12/5 11:25
 * @description：
 * @modified By：
 */
@Slf4j
public class NettyFutureDemo {
    public static void main(String[] args) {
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        eventLoopGroup.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {

                return 5;
            }
        }).addListener(new GenericFutureListener<Future<? super Integer>>() {
            @Override
            public void operationComplete(Future<? super Integer> future) throws Exception {
                log.debug(String.valueOf(future.getNow()));
            }
        });

    }
}
