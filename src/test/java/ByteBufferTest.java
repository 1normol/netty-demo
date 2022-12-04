import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ByteBufferTest {
    public static void main(String[] args) {


        ByteBuffer source = ByteBuffer.allocate(32);
        //                     11            24
        source.put("Hello,world\nI'm zhangsan\nHo".getBytes());
        split(source);

        source.put("w are you?\nhaha!\n".getBytes());
        split(source);
    }
/*    public static void split(ByteBuffer source){

        //1.粘包处理
        //2.半包处理
        source.flip();
        //切换至读模式
        for (int i = 0; i < source.limit(); i++) {

            if (source.get(i)=='\n'){
                //确定target Buffer容量
                ByteBuffer target = ByteBuffer.allocate(i + 1 - source.position());
                //切换limit指针
                target.put(source);
                source.limit(i+1);
            }
        }
        source.compact();
    }*/

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
