package netty.c1;

import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 事件组演示
 *
 * @author 李茂林
 * @date 2022/12/04
 */
@Slf4j
public class EventGroupDemo {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup(4);

        EventLoop eventLoop = eventLoopGroup.next();
        System.out.println(eventLoop.next());
        System.out.println(eventLoop.next());
        System.out.println(eventLoop.next());

/*        for (int i = 0; i < 10; i++) {
            eventLoop.next().submit(() -> log.debug("hello"));
        }*/
        eventLoop.scheduleAtFixedRate(()->log.debug("hello"),1,1, TimeUnit.SECONDS);
    }
    
}
