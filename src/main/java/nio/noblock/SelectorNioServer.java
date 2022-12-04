package nio.noblock;

import lombok.extern.slf4j.Slf4j;
import nio.block.ByteBufferUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * 使用Selector建立服务器
 *
 * @author 李茂林
 * @date 2022/12/03
 */
@Slf4j
public class SelectorNioServer {
    public static void main(String[] args) throws IOException {
        //1.创建Selector,管理多个channel
        Selector selector = Selector.open();
        //1.1创建channel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("localhost",8080));
        //2.注册channel到selector
        serverSocketChannel.configureBlocking(false);
        SelectionKey register = serverSocketChannel.register(selector, 0);
        log.debug("注册成功,{}",selector);
        //2.1选择注册器需要关心的事件
        register.interestOps(SelectionKey.OP_ACCEPT);
        //3.
        while (true){
            //4.select方法，表示有事件则处理事件，没有事件则阻塞。
            selector.select();
            //5.selectedKeys表示接收到的所有事件
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            if (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                log.debug("监听到的事件为,{}",selector);
                //5.1区分事件类型
                if (selectionKey.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel socketChannel = channel.accept();
                    socketChannel.configureBlocking(false);
                    ByteBuffer byteBuffer = ByteBuffer.allocate(16);
                    SelectionKey readKey = socketChannel.register(selector, 0,byteBuffer);
                    readKey.interestOps(SelectionKey.OP_READ);
                    log.debug("connected...{}",socketChannel);
                }
                else if (selectionKey.isReadable()){
                    try {
                        SocketChannel readChannel = (SocketChannel) selectionKey.channel();
                        ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
                        split(buffer);
                        int read = readChannel.read(buffer);
                        if (read == -1){
                            //当read为-1时表示正常断开
                            selectionKey.cancel();
                        }else{
//                            buffer.flip();
//                            ByteBufferUtil.debugRead(buffer);
                            split(buffer);
                            if (buffer.position() == buffer.limit()){
                                ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity() * 2);
                                newBuffer.flip();
                                newBuffer.put(buffer);
                                selectionKey.attach(newBuffer);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        selectionKey.cancel();
                    }
                }
                iterator.remove();


            }
        }
    }

    private static void split(ByteBuffer source) {
        source.flip();
        int oldLimit = source.limit();
        for (int i = 0; i < oldLimit; i++) {
            if (source.get(i) == '\n') {
                System.out.println(i);
                int length = i + 1 - source.position();
                ByteBuffer target = ByteBuffer.allocate(length);
                // 0 ~ limit
                for (int j = 0; j < length; j++) {
                    target.put(source.get());
                }
                ByteBufferUtil.debugAll(target);
            }
        }
        source.compact();
    }

}
