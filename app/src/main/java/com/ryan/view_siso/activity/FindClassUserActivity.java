package com.ryan.view_siso.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.ryan.view_siso.R;
import com.ryan.view_siso.bean.UserInfoBean;

/**
 * 此类和FindStuidActivity,可重构。重构失败，返回类型不同
 * <p/>
 * setContentView(R.layout.activity_find_class_user);
 * 可和setContentView(R.layout.activity_find_stuid);合并
 */


public class FindClassUserActivity extends AppCompatActivity {


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


    private UserInfoBean user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_stuid);


        initView();

        Intent intent = getIntent();
        user = intent.getParcelableExtra("user");//得到一个对象

        StuName.setText(user.getStuName());
        STUClass.setText(user.getSTUClass());
        Sex.setText(user.getSex());
        Mobile.setText(user.getMobile());

        Birthday.setText(user.getBirthday());
        EMail.setText(user.getEMail());
        BankCard.setText(user.getBankCard());
        Dept.setText(user.getDept());
        Specialty.setText(user.getSpecialty());
        Grade.setText(user.getGrade());
        DormNumber.setText(user.getDormNumber());


        Mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toCall(Mobile.getText().toString());
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

    }


}
