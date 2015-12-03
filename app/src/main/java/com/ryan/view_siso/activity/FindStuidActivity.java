package com.ryan.view_siso.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ryan.view_siso.R;
import com.ryan.view_siso.bean.UserInfoBean;

import java.util.ArrayList;

public class FindStuidActivity extends AppCompatActivity {

    private static final String TAG = FindStuidActivity.class.getSimpleName();
    private TextView StuName;
    private TextView STUClass;
    private TextView Sex;
    private TextView Mobile;

    private TextView Birthday;
    private TextView EMail;
    private TextView BankCard;
    private TextView Dept;
    private TextView Specialty;
    private TextView Grade;
    private TextView DormNumber;

    private ImageView imageViewBack;


    private ArrayList<UserInfoBean> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_stuid);

        initView();

        user = this.getIntent().getExtras().getParcelableArrayList("user");

        StuName.setText(user.get(0).getStuName());
        STUClass.setText(user.get(0).getSTUClass());
        Sex.setText(user.get(0).getSex());
        Mobile.setText(user.get(0).getMobile());

        Birthday.setText(user.get(0).getBirthday());
        EMail.setText(user.get(0).getEMail());
        BankCard.setText(user.get(0).getBankCard());
        Dept.setText(user.get(0).getDept());
        Specialty.setText(user.get(0).getSpecialty());
        Grade.setText(user.get(0).getGrade());
        DormNumber.setText(user.get(0).getDormNumber());


        Mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toCall(Mobile.getText().toString());
            }
        });

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void toCall(String s) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + s));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    private void initView() {
        StuName = (TextView) findViewById(R.id.stu_name);
        STUClass = (TextView) findViewById(R.id.tea_bumen);
        Sex = (TextView) findViewById(R.id.tea_sex);
        Mobile = (TextView) findViewById(R.id.tea_phone);

        Birthday = (TextView) findViewById(R.id.zhigonghaoInfo);
        EMail = (TextView) findViewById(R.id.xingminInfo);
        BankCard = (TextView) findViewById(R.id.youjianInfo);
        Dept = (TextView) findViewById(R.id.xingbieInfo);
        Specialty = (TextView) findViewById(R.id.bumenInfo);
        Grade = (TextView) findViewById(R.id.officephoneInfo);
        DormNumber = (TextView) findViewById(R.id.cellphoneInfo);

        imageViewBack = (ImageView) findViewById(R.id.back);
    }


}
