package com.example.networktest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button send = (Button) findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socket s = new socket();
                new Thread(s).start();
            }});

    }
    class socket implements Runnable{
        Socket socket;
        String serverResponse;
        EditText mnr = (EditText) findViewById(R.id.mnr);
        TextView response = (TextView) findViewById(R.id.response);
        String host = "se2-isys.aau.at";
        int port = 53212;

        @Override
        public void run() {
            try {
                socket = new Socket(host, port);
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                dataOutputStream.writeBytes(mnr.getText().toString() + '\n');

                serverResponse = (bufferedReader.readLine());
                System.out.println(serverResponse);
                //response.setText(serverResponse);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        response.setText(serverResponse);
                    }
                });
                socket.close();

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        response.setText("Something went wrong...");
                    }
                });
            }
        }
    }
}
