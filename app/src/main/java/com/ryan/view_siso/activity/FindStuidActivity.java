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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ryan.view_siso.R;
import com.ryan.view_siso.adapter.UserInfoAdapter;
import com.ryan.view_siso.bean.UserInfoBean;

import java.util.ArrayList;

public class FindStuidActivity extends AppCompatActivity {

    private static final String TAG = FindStuidActivity.class.getSimpleName();
    private TextView StuName;
    private TextView Sex;
    private TextView StuID;
    private TextView Specialty;
    private TextView Class;
    private TextView Mobile;
    private TextView QQ;
    private TextView FMobile;
    private TextView MMobile;

    private ImageView iv_Mobile, iv_FMobile, iv_MMobile;

    private ArrayList<UserInfoBean> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_stuid);

        initToolBar();

        initView();

        user = this.getIntent().getExtras().getParcelableArrayList("user");


        StuName.setText(user.get(0).getStuName());
        Sex.setText(user.get(0).getSex());
        StuID.setText(user.get(0).getStuID());
        Specialty.setText(user.get(0).getSpecialty());
        Class.setText(user.get(0).getSTUClass());
        Mobile.setText(user.get(0).getMobile());
        QQ.setText(user.get(0).getQQ());
        FMobile.setText(user.get(0).getFMobile());
        MMobile.setText(user.get(0).getMMobile());


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

        Mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toCall(Mobile.getText().toString());
            }
        });

        iv_Mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toCall(Mobile.getText().toString());
            }
        });

        iv_FMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toCall(FMobile.getText().toString());
            }
        });

        iv_MMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toCall(MMobile.getText().toString());
            }
        });
    }

    private void toCall(String s) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +s));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    private void initView() {
        StuName = (TextView) findViewById(R.id.stu_name);
        Sex = (TextView) findViewById(R.id.stu_sex);
        StuID = (TextView) findViewById(R.id.stu_id);
        Specialty = (TextView) findViewById(R.id.Specialty);
        Class = (TextView) findViewById(R.id.Class);
        Mobile = (TextView) findViewById(R.id.Mobile);
        QQ = (TextView) findViewById(R.id.QQ);
        FMobile = (TextView) findViewById(R.id.FMobile);
        MMobile = (TextView) findViewById(R.id.MMobile);

        iv_Mobile = (ImageView) findViewById(R.id.image_phone);
        iv_MMobile = (ImageView) findViewById(R.id.image_phone_m);
        iv_FMobile = (ImageView) findViewById(R.id.image_phone_f);

    }


    private void initToolBar() {
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
