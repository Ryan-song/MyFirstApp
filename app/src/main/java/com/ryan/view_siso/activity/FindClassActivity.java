package com.ryan.view_siso.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.ryan.view_siso.R;
import com.ryan.view_siso.adapter.UsersInfoAdapter;
import com.ryan.view_siso.bean.UserInfoBean;

import java.util.ArrayList;


/**
 *  此油 Main2Activity代替
 */

public class FindClassActivity extends AppCompatActivity {

    private ArrayList<UserInfoBean> user;
    private RecyclerView mRecyclerView;
    private UsersInfoAdapter mUsersInfoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_class);

        initToolbar();

        user = this.getIntent().getExtras().getParcelableArrayList("users");

        mRecyclerView = (RecyclerView) findViewById(R.id.usersInfo);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.setHasFixedSize(true);
        mUsersInfoAdapter = new UsersInfoAdapter(user);
        mUsersInfoAdapter.setOnItemClickListener(new UsersInfoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, UserInfoBean data) {
                /**
                 *  传递一个对象
                 */
                Intent intent = new Intent(FindClassActivity.this, FindClassUserActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putParcelableArrayList("users", data);
                intent.putExtra("user", data);
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mUsersInfoAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "点击YES,可以短信回复提建议哦～", Snackbar.LENGTH_LONG)
                        .setAction("YES", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                SmsUtil.sendSmsWithDefault(getApplicationContext());
                                Uri smsToUri = Uri.parse("smsto:15995490890");
                                Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
                                intent.putExtra("sms_body", "谢谢支持!~~" + '\n');
                                startActivity(intent);
                            }
                        })
                        .setActionTextColor(getResources().getColor(R.color.green_500))
                        .show();
            }
        });
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
