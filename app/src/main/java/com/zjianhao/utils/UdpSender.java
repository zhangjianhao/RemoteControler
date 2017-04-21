package com.zjianhao.utils;

import com.zjianhao.universalcontroller.Constant;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by 张建浩 on 2016/3/6.
 */
public class UdpSender {
    public static void sendOrder(String ip, int orderCode) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        byte[] buf = (orderCode + "#").getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName(ip), Constant.REMOTE_PORT);
        socket.send(packet);
        packet = null;
        socket.close();
    }

    public static void sendOrder(String ip, int orderCode, String info) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        byte[] buf = (orderCode + "#" + info).getBytes("utf-8");
        DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName(ip), Constant.REMOTE_PORT);
        socket.send(packet);
        packet = null;
        socket.close();
    }

    public static void sendOrder(String ip, int port, int orderCode, String info) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        byte[] buf = (orderCode + "#" + info).getBytes("utf-8");
        DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName(ip), port);
        socket.send(packet);
        packet = null;
        socket.close();
    }

    public static void sendOrderSyn(final String ip, final int orderCode) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    UdpSender.sendOrder(ip, orderCode);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public static void sendOrderSyn(final String ip, final int orderCode, final String info) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    UdpSender.sendOrder(ip, orderCode, info);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    public static void sendOrderSyn(final String ip, final int port, final int orderCode, final String info) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    UdpSender.sendOrder(ip, port, orderCode, info);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
