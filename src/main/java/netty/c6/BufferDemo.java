package netty.c6;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import lombok.extern.slf4j.Slf4j;
import nio.block.ByteBufferUtil;

import java.nio.charset.StandardCharsets;

/**
 * @author ：limaolin
 * @date ：Created in 2022/12/5 15:20
 * @description：
 * @modified By：
 */
public class BufferDemo {
    public static void main(String[] args) {
/*        ByteBuf directBuffer = ByteBufAllocator.DEFAULT.directBuffer();
        ByteBuf heapBuffer = ByteBufAllocator.DEFAULT.heapBuffer();
        heapBuffer.writeBytes("hello".getBytes(StandardCharsets.UTF_8));
        log.debug("{}",heapBuffer);

        log.debug("heapBuffer的类为{}",heapBuffer.getClass());
        log.debug("directBuffer的类为{}",directBuffer.getClass());
  //      System.out.println(heapBuffer.getClass());
        for (int i = 0; i < 500; i++) {
            heapBuffer.writeBytes("a".getBytes(StandardCharsets.UTF_8));
        }
        log.debug("{}",heapBuffer);*/

        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        buffer.writeBytes(new byte[]{'a','b','c','d'});
        System.out.println(buffer.readByte());
       // log.debug("buffer:{}",buffer);

    }

}
