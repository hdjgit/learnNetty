package com.hdj.netty.demo.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Author: 端吉
 * Date:   2017/12/7.
 */
public class ClientSocketDemo {

    public static void main(String[] args) throws IOException {
        Socket socket = null;
        BufferedReader reader = null;
        PrintWriter writer = null;
        try {
            socket = new Socket("127.0.0.1", 8088);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //此处的autoflush必须为true，否则需要手动刷新
            writer = new PrintWriter(socket.getOutputStream(),true);

            writer.println("hello world");
            System.out.println(reader.readLine());
        } finally {
            if (writer != null) {
                writer.close();
            }
            if (reader != null) {
                reader.close();
            }
            if (socket != null) {
                socket.close();
            }
            socket = null;
        }
    }
}
