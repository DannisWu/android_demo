package com.neu.wudan.android_demo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    private int mType;
    private MulticastClient mMulticastClient;
    private MulticastServer mMulticastServer;
    private Button mButtonLog;
    private String mRunLog;
    private TextView mTextViewLog;
    private WifiManager mWifiManager;
    private WifiManager.MulticastLock mMulticastLock;
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent mIntent = getIntent();
        Bundle mBundle = mIntent.getBundleExtra("bundle");
        String ip = mBundle.getString("IP");
        String port = mBundle.getString("Port");
        mType = mBundle.getInt("type");

        mTextViewLog = (TextView)findViewById(R.id.textView_hello);
        mRunLog = "Start Multicast Test(" + ip + ":" + port + ", " + mType + ")";
        mTextViewLog.setText(mRunLog);

        mWifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        mMulticastLock = mWifiManager.createMulticastLock("wudan_demo");
        mMulticastLock.acquire();
        if (mType == 1) {
            mMulticastClient = new MulticastClient(ip, Integer.parseInt(port));
        } else if (mType == 2) {
            mMulticastServer = new MulticastServer(ip, Integer.parseInt(port));
        }

        mButtonLog = (Button)findViewById(R.id.buttonLog);
        mButtonLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mType == 1) {
                    mTextViewLog.setText(mRunLog + mMulticastClient.getRunLog());
                } else if (mType == 2) {
                    mTextViewLog.setText(mRunLog + mMulticastServer.getRunLog());
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次后退键退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                if (mType == 1) {
                    mMulticastClient.stopMulticastClient();
                } else if (mType == 2) {
                    mMulticastServer.stopMulticastServer();
                }
                finish();
                System.exit(0);
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
