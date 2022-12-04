package netty.c2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * 简单服务器
 *
 * @author 李茂林
 * @date 2022/12/04
 */
@Slf4j
public class SimpleServer {
    public static void main(String[] args) {
        EventLoopGroup eventLoopGroup = new DefaultEventLoop();

        new ServerBootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast("eventGroup1",new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf byteBuf = (ByteBuf) msg;
                                log.debug(byteBuf.toString(Charset.forName("utf-8")));
                                ctx.fireChannelRead(byteBuf);
                            //    System.out.println(((ByteBuf) msg).toString(Charset.forName("utf-8")));
                            }
                        }).addLast(eventLoopGroup,"eventGroup2",new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf byteBuf = (ByteBuf) msg;
                                log.debug(byteBuf.toString(Charset.forName("utf-8")));
                            }
                        });
                    }
                })
                .bind(8080);
    }
}
