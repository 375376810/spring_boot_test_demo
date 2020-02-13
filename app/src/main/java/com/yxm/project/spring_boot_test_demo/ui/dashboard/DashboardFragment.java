package com.yxm.project.spring_boot_test_demo.ui.dashboard;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yxm.project.spring_boot_test_demo.R;
import com.yxm.project.spring_boot_test_demo.domain.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DashboardFragment extends Fragment implements View.OnClickListener {

    private DashboardViewModel dashboardViewModel;
    private EditText mEtUsername;
    private Activity mRootActivity;
    private Button mBtnSave;
    private ListView mLvList;
    private ArrayList<User> mUserList;
    private MyListAdapter myListAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        View fragment_dashbord = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = fragment_dashbord.findViewById(R.id.text_dashboard);
        dashboardViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        initView(fragment_dashbord);
        return fragment_dashbord;
    }

    private void initView(View fragment_dashbord) {
        mEtUsername = fragment_dashbord.findViewById(R.id.et_username);
        mRootActivity = this.getActivity();
        mBtnSave = fragment_dashbord.findViewById(R.id.btn_save);
        mBtnSave.setOnClickListener(this);
        mLvList = fragment_dashbord.findViewById(R.id.lv_list);

        mUserList = findAllUser();

        myListAdapter = new MyListAdapter();
        mLvList.setAdapter(myListAdapter);
    }

    /**
     * 发送post请求查询所有人员信息
     */
    private ArrayList<User> findAllUser() {
        ArrayList<User> all = new ArrayList<>();
        new OkHttpClient().newCall(new Request.Builder().url("http://192.168.123.220:8080/get_all_user").get().build()).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("出现了错误", "未能从后台查询到所有的数据");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mUserList = new Gson().fromJson(response.body().string(), new TypeToken<List<User>>(){}.getType());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myListAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
        return all;
    }

    /**
     * 在后台服务器保存用户,用户名为输入内容
     */
    public void saveUser() {
        String username = mEtUsername.getText().toString();
        if (TextUtils.isEmpty(username)) {
            //如果为空则不提交
            Toast.makeText(this.getContext(), "请输入内容", Toast.LENGTH_LONG).show();
        } else {
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(new User(username, "123456", "新建用户")));
            //向服务器发送请求
            new OkHttpClient().newCall(new Request.Builder().url("http://192.168.123.220:8080/save_user").post(body).build()).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    mRootActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mRootActivity, "出现了错误,请重试" + e.toString(), Toast.LENGTH_LONG).show();
                            System.out.println("http://192.168.123.220:8080/save_user");
                            e.printStackTrace();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    mRootActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mRootActivity.getApplicationContext(), "保存成功?" + response.toString(), Toast.LENGTH_LONG).show();
                            System.out.println("保存成功");
                        }
                    });
                }
            });
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                saveUser();
                break;
        }
    }

    /**
     * 一个自定义的适配器
     */
    class MyListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mUserList.size();
        }

        @Override
        public Object getItem(int position) {
            return mUserList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv = new TextView(getActivity());
            tv.setText("用户名 : " + mUserList.get(position).getUsername() + "密码 : " + mUserList.get(position).getPassword());
            tv.setTextColor(0xFFFFFFFF);
            tv.setTextSize(18);
            return tv;
        }
    }



}
