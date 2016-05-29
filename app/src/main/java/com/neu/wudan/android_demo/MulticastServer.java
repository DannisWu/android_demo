package com.neu.wudan.android_demo;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;

/**
 * Created by Administrator on 2016/5/29 0029.
 */
public class MulticastServer {
    private static String TAG = MulticastServer.class.getSimpleName();

    public MulticastServer(String address, int port) {

    }

    private class MulticastServerSender implements Runnable {
        private String mAddress;
        private int mPort;
        private MulticastSocket mSocket;
        private InetAddress mGroup;

        public MulticastServerSender(String server, int port) {
            try {
                mAddress = server;
                mPort = port;
                mSocket = new MulticastSocket(port);
                mGroup = InetAddress.getByName(server);
                mSocket.joinGroup(mGroup);
                Log.d(TAG, "JoinGroup done(" + mAddress + ", " + mPort + ")");
            } catch (IOException ioe) {
                Log.e(TAG, "creating multicast socket: ", ioe);
                ioe.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                byte[] buf = "Hello I am MulticastSender".getBytes();
                DatagramPacket packet = new DatagramPacket(buf, buf.length, mGroup, mPort);
                while (true) {
                    mSocket.send(packet);
                    Thread.sleep(1000);
                }
            } catch (SocketException se) {
                Log.e(TAG, "Error creating socket: ", se);
                se.printStackTrace();
            } catch (IOException ioe) {
                Log.e(TAG, "Error creating multicast socket: ", ioe);
                ioe.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
