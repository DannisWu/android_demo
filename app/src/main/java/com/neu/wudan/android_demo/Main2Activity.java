package com.neu.wudan.android_demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    private MulticastClient mMulticastClient;
    private MulticastServer mMulticastServer;
    //private ScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent mIntent = getIntent();
        Bundle mBundle = mIntent.getBundleExtra("bundle");
        String ip = mBundle.getString("IP");
        String port = mBundle.getString("Port");
        int type = mBundle.getInt("type");

        TextView mTextViewHello = (TextView)findViewById(R.id.textView_hello);
        mTextViewHello.setText("Start Multicast Test(" + ip + ":" + port + ", " + type + ")");

        //mScrollView = (ScrollView)findViewById(R.id.scrollView);

        if (type == 1) {
            mMulticastClient = new MulticastClient(ip, Integer.parseInt(port));
        } else if (type == 2) {
            mMulticastServer = new MulticastServer(ip, Integer.parseInt(port));
        }
    }
}
