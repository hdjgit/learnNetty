package com.hdj.netty.demo.bio;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Author: 端吉
 * Date:   2017/12/6.
 * 传统bio
 */
public class ServerSocketDemo {

    public static void main(String[] args) throws Exception {
        ServerSocket ss = null;
        try {
            //创建服务端 ServerSocket
            ss = new ServerSocket(8088);
            while (true) {
                //accept客户端
                Socket socket = ss.accept();
                System.out.println("socket accept.");
                new TimeServerThread(socket).start();
            }
        } finally {
            if (ss != null) {
                ss.close();
            }
        }

    }

    public static class TimeServerThread extends Thread {

        Socket socket;

        public TimeServerThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            if (this.socket != null) {
                BufferedReader reader = null;
                PrintWriter writer = null;
                try {
                    reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    writer = new PrintWriter(socket.getOutputStream());

                    while (!this.socket.isClosed()) {   //这里的 socket.isClosed() 貌似一直是false，哪怕对端关闭了。
                        String line = reader.readLine();
                        System.out.println("receive:" + line);
                        System.out.println(this.socket.isClosed());
                        writer.println(line);
                        if (StringUtils.equalsIgnoreCase(line, "q") || line == null) {
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if (writer != null) {
                        writer.close();
                    }

                    if (socket != null) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        socket = null;
                    }
                }
            }
        }
    }
}
