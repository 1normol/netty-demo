package nio.noblock;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * 客户端
 *
 * @author 李茂林
 * @date 2022/12/03
 */
public class Client {
    public static void main(String[] args) throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.connect(new InetSocketAddress("localhost", 8080));
        int count = 0;
        while (true){
            {
                ByteBuffer buffer = ByteBuffer.allocate(1024*1024);
                count += channel.read(buffer);
                System.out.println(count);
                buffer.clear();
            }
        }

    }
}
