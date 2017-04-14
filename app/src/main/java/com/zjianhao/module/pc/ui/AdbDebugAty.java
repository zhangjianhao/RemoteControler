package com.zjianhao.module.pc.ui;

import android.app.Activity;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zjianhao.universalcontroller.R;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;


public class AdbDebugAty extends Activity {
    private boolean open = false;
    private Button adbDebug;
    private TextView remindInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pc_adb_wireless_main);
        remindInfo = (TextView)findViewById(R.id.adb_info_tv);
        adbDebug = (Button)findViewById(R.id.adb_debug_btn);
        adbDebug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRoot()){
                    Toast.makeText(AdbDebugAty.this,"获取root权限失败,请检查手机是否root", Toast.LENGTH_SHORT).show();
                    return ;
                }
                if (!open){
                    remindInfo.setText("请在命令行输入 adb connect " + getLocalIpAddress()
                            + ":5555");
                    adbDebug.setText("关闭调试");
                    execShell("setprop service.adb.tcp.port 5555");
                    execShell("start adbd");
                    open = true;
                }else {
                    remindInfo.setText("未开启无线调试");
                    adbDebug.setText("开启调试");
                    execShell("stop adbd");
                    open = false;
                }
            }
        });

    }

    private String getLocalIpAddress() {
        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        // 获取32位整型IP地址
        int ipAddress = wifiInfo.getIpAddress();

        // 返回整型地址转换成“*.*.*.*”地址
        return String.format("%d.%d.%d.%d", (ipAddress & 0xff),
                (ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff),
                (ipAddress >> 24 & 0xff));
    }

    public static String getIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()
                            && inetAddress instanceof Inet4Address) {
                        // if (!inetAddress.isLoopbackAddress() && inetAddress
                        // instanceof Inet6Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("异常");
        }
        return null;
    }


    private boolean isRoot(){
        try
        {
           Process process  = Runtime.getRuntime().exec("su");
            process.getOutputStream().write("exit\n".getBytes());
            process.getOutputStream().flush();
            int i = process.waitFor();
            if(0 == i){
                process = Runtime.getRuntime().exec("su");
                return true;
            }

        } catch (Exception e)
        {
            return false;
        }
        return false;

    }
    public void execShell(String str) {
        try {
            // 权限设置
            Process p = Runtime.getRuntime().exec("su");
            // 获取输出流
            OutputStream outputStream = p.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(
                    outputStream);
            // 将命令写入
            dataOutputStream.writeBytes(str);
            // 提交命令
            dataOutputStream.flush();
            // 关闭流操作
            dataOutputStream.close();
            outputStream.close();
            System.out.println("成功执行shell");
        } catch (Exception t) {
            System.out.println("执行shell失败");
            Toast.makeText(this,"获取root权限失败", Toast.LENGTH_SHORT).show();
            t.printStackTrace();
        }
    }

}
