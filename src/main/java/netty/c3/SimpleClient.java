package netty.c3;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * @author ：limaolin
 * @date ：Created in 2022/12/5 10:00
 * @description：简单客户端
 * @modified By：
 */
@Slf4j
public class SimpleClient {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        ChannelFuture channelFuture = new Bootstrap()
                .group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new StringEncoder());
                    }
                })
                .connect(new InetSocketAddress("localhost",8080));

        Channel channel = channelFuture.sync().channel();
        new Thread(()->{
            Scanner input = new Scanner(System.in);
            while (true){
                System.out.println("请输入需要发送的信息:");
                String line = input.nextLine();
                if (line.equals("quit")){
                    channel.close();
                    break;
                }
                channel.writeAndFlush(line);
            }
        },"input").start();
        ChannelFuture closeFuture = channel.closeFuture();
        closeFuture.addListener( channelFuture1 -> {
            log.debug("正在关闭Nio事件组...");
            eventLoopGroup.shutdownGracefully();
        });

    }
}
