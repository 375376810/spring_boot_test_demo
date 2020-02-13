package com.yxm.project.spring_boot_test_demo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    /**
     * 点击了show按钮
     */
    public void info(View view) {
        OkHttpClient httpClient = new OkHttpClient();
        //http://localhost:8080/info
        Request request = new Request.Builder().url("http://192.168.123.220:8080/info").get().build();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "出现了错误,请重试", Toast.LENGTH_LONG).show();
                        System.out.println("http://192.168.123.220:8080/info");
                    }
                });
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String message = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        System.out.println("收到服务器的一条消息:" + message);
                    }
                });
            }
        });
    }

    public void test(View view) {
        Log.d("測試一下", "测试一下");
    }

}
