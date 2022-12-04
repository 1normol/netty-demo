package netty.c1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * 简单服务器
 *
 * @author 李茂林
 * @date 2022/12/04
 */
public class SimpleServer {
    public static void main(String[] args) {
        //1.0启动器，负责组装netty组件，启动服务器
        new ServerBootstrap()
                //2.
                .group(new NioEventLoopGroup())
                //3.选择服务器的channel实现
                .channel(NioServerSocketChannel.class)
                //4. channel代表客户端与服务端通信的通道Initializer初始化，负责添加其他handler
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new SimpleChannelInboundHandler(){
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
                                System.out.println(msg);
                             //   super.channelRead(ctx, msg);
                            }
                        });
                    }
                })
                .bind(8080);
    }
}
