package com.zjianhao.module.pc.util;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;

import com.zjianhao.universalcontroller.Constant;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by 张建浩（Clarence) on 2017-4-11 23:07.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

public class BroadcastDiscovery extends Thread {


    private boolean timerStop = false;
    private DiscoverHostListener listener;
    public static final int REQUEST_CODE = 400;
    public static final int RESPONSE_CODE = 200;
    private DatagramSocket socket;

    public interface DiscoverHostListener {
        public void onDiscovery(String ip, String hostname, String sysType);

        public void onFailure(Exception e);
    }

    public void setOnDiscoverHostListener(DiscoverHostListener listener) {
        this.listener = listener;
    }

    private Context context;

    public BroadcastDiscovery(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        super.run();
        try {
            sendRequestData();
            receiveResponseData();
        } catch (IOException e) {
            if (listener != null)
                listener.onFailure(e);
        }

    }

    private void sendRequestData() throws IOException {
        byte[] buf = (REQUEST_CODE + "").getBytes();
        DatagramPacket out = new DatagramPacket(buf, 0, buf.length);
        socket = new DatagramSocket();
        System.out.println(getBroadcastAddress(context).getHostAddress());
        out.setAddress(getBroadcastAddress(context));
        out.setPort(Constant.REMOTE_PORT);
        socket.send(out);
    }


    public void stopDiscovery() {
        timerStop = true;
//        socket.disconnect();
        socket = null;

    }

    private void receiveResponseData() throws IOException {
        byte[] buf = new byte[2048];
        DatagramPacket in = new DatagramPacket(buf, buf.length);
        while (!timerStop) {
            if (!socket.isClosed()) {
                socket.receive(in);
                InetAddress address = in.getAddress();
                String result = new String(in.getData(), 0, in.getLength());
                if (result.contains("#")) {
                    String[] split = result.split("#");
                    if ((RESPONSE_CODE + "").equals(split[0]) && listener != null)
                        listener.onDiscovery(address.getHostAddress(), address.getHostName(), split[1]);
                }
            }

        }
    }

    public static InetAddress getBroadcastAddress(Context context) throws UnknownHostException {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        int state = getWifiApState(wifi);
        if (state == 13)
            return InetAddress.getByName("192.168.43.1");
        DhcpInfo dhcp = wifi.getDhcpInfo();
        if (dhcp == null) {
            return InetAddress.getByName("255.255.255.255");
        }
        int broadcast = (dhcp.ipAddress & dhcp.netmask) | ~dhcp.netmask;
        byte[] quads = new byte[4];
        for (int k = 0; k < 4; k++)
            quads[k] = (byte) ((broadcast >> k * 8) & 0xFF);
        return InetAddress.getByAddress(quads);
    }

    private static int getWifiApState(WifiManager wifiManager) {
        try {
            Method method = wifiManager.getClass().getMethod("getWifiApState");
            return ((Integer) method.invoke(wifiManager));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 11;
        }

    }
}
