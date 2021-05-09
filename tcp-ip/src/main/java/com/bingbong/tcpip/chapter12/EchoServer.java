package com.bingbong.tcpip.chapter12;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class EchoServer {

    private static final String END_MESSAGE = "q";

    public static void main(String[] args) throws Exception {
        // Selector 생성
        Selector selector = Selector.open();
        // TCP 서버 소켓 채널 생성, UDP는 DatagramChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("localhost", 15454));
        // 논 블로킹 모드로 사용해야 Selector에 등록 가능
        serverSocketChannel.configureBlocking(false);
        // 해당 Selector에 감시하고자 하는 이벤트(accept)와 서버 소켓 채널을 등록합니다.
        // accept이므로 클라이언트로부터 연결을 수락하는 이벤트를 감시하는 SocketChannel을 등록합니다.
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        // Java NIO는 스트림이 아닌 버퍼 지향 모델
        ByteBuffer buffer = ByteBuffer.allocate(256);

        while (true) {
            // 입출력이 준비되어 있는 채널이 있으면 해당 SelectionKey들을 반환
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectionKeys.iterator();

            // 파일 디스크립터를 돌면서 해당 상태가 accept인지, read인지 파악
            while (iter.hasNext()) {
                // 채널을 나타내는 데이터 보유
                SelectionKey key = iter.next();

                // 이처럼 편의 메서드로 되어있음
                if (key.isAcceptable()) {
                    register(selector, serverSocketChannel);
                }

                if (key.isReadable()) {
                    answerWithEcho(buffer, key);
                }
                iter.remove();
            }
        }
    }

    private static void answerWithEcho(ByteBuffer buffer, SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        client.read(buffer);
        if (END_MESSAGE.equals(new String(buffer.array()).trim())) {
            client.close();
            System.out.println("더 이상 클라이언트의 메시지를 읽을 수 없습니다.");
        } else {
            // 읽은 버퍼에 쓰려면 flip을 호출해야 함
            buffer.flip();
            client.write(buffer);
            buffer.clear();
        }
    }

    private static void register(Selector selector, ServerSocketChannel serverSocketChannel) throws Exception {
        // 논 블로킹이므로 바로 반환된다. 연결되어있지 않으면 null이 들어옴
        SocketChannel client = serverSocketChannel.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
    }

    public static Process start() throws Exception {
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
        String classpath = System.getProperty("java.class.path");
        String className = EchoServer.class.getCanonicalName();

        ProcessBuilder builder = new ProcessBuilder(javaBin, "-cp", classpath, className);

        return builder.start();
    }
}
