package com.neu.wudan.android_demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private Button mButton;
    private EditText mEditTextIP;
    private EditText mEditTextPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button)findViewById(R.id.button);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditTextIP = (EditText)findViewById(R.id.editText_ip);
                mEditTextPort = (EditText)findViewById(R.id.editText_port);

                Bundle mBundle = new Bundle();
                mBundle.putString("IP", mEditTextIP.getText().toString().trim());
                mBundle.putString("Port", mEditTextPort.getText().toString().trim());

                Intent mIntent = new Intent(MainActivity.this, Main2Activity.class);
                mIntent.putExtra("bundle", mBundle);
                startActivity(mIntent);
            }
        });
    }
}
