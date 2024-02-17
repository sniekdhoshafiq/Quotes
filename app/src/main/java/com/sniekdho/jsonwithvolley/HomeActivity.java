package com.sniekdho.jsonwithvolley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


public class HomeActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView internetMSGTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        internetMSGTV = (TextView) findViewById(R.id.internetMSGTV);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
                registerReceiver(connectivityReceiver, filter);

                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                boolean isConnected = networkInfo!= null && networkInfo.isConnectedOrConnecting();

                if (!isConnected)
                {
                    progressBar.setVisibility(View.VISIBLE);
                    internetMSGTV.setText(getString(R.string.no_internet));
                }

            }
        }, 1500);

    }

    private BroadcastReceiver connectivityReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            boolean isConnected = networkInfo!= null && networkInfo.isConnectedOrConnecting();

            if (isConnected)
            {
                progressBar.setVisibility(View.GONE);
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
            }

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(connectivityReceiver);
    }
}