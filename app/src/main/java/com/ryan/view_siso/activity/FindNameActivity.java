package com.ryan.view_siso.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ryan.view_siso.R;
import com.ryan.view_siso.adapter.UserInfoAdapter_2;
import com.ryan.view_siso.bean.UserInfoBean;

import java.util.ArrayList;

public class FindNameActivity extends AppCompatActivity {

    private ArrayList<UserInfoBean> users;
    private TextView name;
    private TextView number;
    private RecyclerView mRecyclerview;

    private UserInfoAdapter_2 mUserinfoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_name);

        initToolbar();
        initView();

        users = this.getIntent().getExtras().getParcelableArrayList("users_name");
        name.setText(users.get(0).getStuName());
        number.setText("" + users.size());

        mRecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerview.setHasFixedSize(true);
        mUserinfoAdapter = new UserInfoAdapter_2(users);
        mUserinfoAdapter.setOnItemClickListener(new UserInfoAdapter_2.OnItemClickListener() {
            @Override
            public void onItemClick(View view, UserInfoBean data) {
                Intent intent = new Intent(FindNameActivity.this,FindClassUserActivity.class);
                intent.putExtra("user", data);
                Log.d("fffffff", "name"+data.getStuName());
                startActivity(intent);
            }
        });

        mRecyclerview.setAdapter(mUserinfoAdapter);

    }

    private void initView() {
        name = (TextView) findViewById(R.id.stttttt_name);
        number = (TextView) findViewById(R.id.stu_number);
        mRecyclerview = (RecyclerView) findViewById(R.id.userInfo);
    }


    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
