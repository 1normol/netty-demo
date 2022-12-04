package nio.block;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * 服务器
 *
 * @author 李茂林
 * @date 2022/12/03
 */
@Slf4j
public class Server {
    public static void main(String[] args)  {
    try{
        //1.0创建Socket通道
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        //1.1绑定服务端
        serverChannel.bind(new InetSocketAddress("localhost",8080));
        //1.11设置服务器channel非阻塞
        serverChannel.configureBlocking(false);
        List<SocketChannel> channelList = new ArrayList<>();
        //1.2创建字节缓冲池
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        //1.3循环不断接收连接
        while(true){
            //1.3.1 accept接收客户端连接
           // log.debug("connecting ...");
            SocketChannel socketChannel = serverChannel.accept();
            if (socketChannel != null){
                log.debug("connected ...",socketChannel);
                socketChannel.configureBlocking(false);
                channelList.add(socketChannel);
            }
            for (SocketChannel channel : channelList) {
                //1.4 将接收数据存储到byteBuffer中
                int read = channel.read(byteBuffer);
                if (read > 0){
                    log.debug("before ...reading.....{}",channel);
                    //1.5切换读模式
                    byteBuffer.flip();
                    //1.6工具类打印
                    ByteBufferUtil.debugRead(byteBuffer);
                    log.debug("after ...reading.....{}",channel);
                    //1.7切换至写模式
                    byteBuffer.clear();
                }
            }
        }
    }catch (Exception e){
        e.printStackTrace();
    }
    }
}
