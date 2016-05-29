package com.neu.wudan.android_demo;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;

/**
 * Created by Administrator on 2016/5/25 0025.
 */
public class MulticastClient {
    private static String TAG = MulticastClient.class.getSimpleName();

    public MulticastClient(String server, int port) {
        new Thread(new MulticastClientReceiver(server, port)).start();
    }

    private String getHexString(byte[] b, int lenth) {
        StringBuffer sbBuffer = new StringBuffer();
        for (int i = 0; i < lenth; i++)
        {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1)
            {
                hex = '0' + hex;
            }
            sbBuffer.append(hex.toUpperCase() + " ");
        }

        return sbBuffer.toString();
    }

    private class MulticastClientReceiver implements Runnable {
        private String mAddress;
        private int mPort;
        private MulticastSocket mSocket;
        private InetAddress mGroup;

        public MulticastClientReceiver(String server, int port) {
            try {
                mAddress = server;
                mPort = port;
                mSocket = new MulticastSocket(port);
                mGroup = InetAddress.getByName(server);
                mSocket.joinGroup(mGroup);
                Log.d(TAG, "MulticastClientReceiver: JoinGroup done(" + mAddress + ", " + mPort + ")");
            } catch (IOException ioe) {
                Log.e(TAG, "MulticastClientReceiver: creating multicast socket: ", ioe);
                ioe.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                byte[] buf = new byte[512];
                while (true) {
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    mSocket.receive(packet);

                    Log.d(TAG, "MulticastClientReceiver: " + getHexString(buf, buf.length));
                }
            } catch (SocketException se) {
                Log.e(TAG, "MulticastClientReceiver: Error creating socket: ", se);
                se.printStackTrace();
            } catch (IOException ioe) {
                Log.e(TAG, "MulticastClientReceiver: Error creating multicast socket: ", ioe);
                ioe.printStackTrace();
            }
        }
    }
}
