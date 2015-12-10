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
import java.util.Random;

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

    private TextView pin;
    private TextView teacher;
    private TextView qq;
    private TextView Hometown;
    private TextView Nation;
    private TextView ZipCode;
    private TextView FMobile;
    private TextView MMobile;


    private ImageView imageViewBack;
    private ImageView imageView;


    private ArrayList<UserInfoBean> user;


    private int[] imgRId =
            {R.drawable.user_0,R.drawable.user_3,R.drawable.user_5};


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

        pin.setText(user.get(0).getPIN());
        teacher.setText(user.get(0).getTeacher());
        qq.setText(user.get(0).getQQ());
        Hometown.setText(user.get(0).getHometown());
        Nation.setText(user.get(0).getNation());
        ZipCode.setText(user.get(0).getZipCode());
        FMobile.setText(user.get(0).getFMobile());
        MMobile.setText(user.get(0).getMMobile());


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

        imageView.setImageResource(imgRId[new Random().nextInt(3)]);

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

        pin = (TextView) findViewById(R.id.pinInfo);
        teacher = (TextView) findViewById(R.id.teacherInfo);
        qq = (TextView) findViewById(R.id.qqInfo);
        Hometown = (TextView) findViewById(R.id.hometownInfo);
        Nation = (TextView) findViewById(R.id.nationInfo);
        ZipCode = (TextView) findViewById(R.id.zipCodeInfo);
        FMobile = (TextView) findViewById(R.id.FMobileInfo);
        MMobile = (TextView) findViewById(R.id.MMobileInfo);


        imageViewBack = (ImageView) findViewById(R.id.back);
        imageView = (ImageView) findViewById(R.id.stu_img);


    }


}
