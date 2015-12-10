package com.ryan.view_siso.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ryan.view_siso.R;
import com.ryan.view_siso.bean.TeaInfoBean;
import com.ryan.view_siso.bean.UserInfoBean;

public class TeaInfoActivity extends AppCompatActivity {

    private TextView zhigonghao;
    private TextView xingmin;
    private TextView youjian;
    private TextView xingbie;
    private TextView bumen;
    private TextView officephone;
    private TextView cellphone;

    private TextView tea_name;
    private TextView tea_bumen;
    private TextView tea_sex;
    private TextView tea_phone;

    private ImageView imageViewBack;

    private TeaInfoBean tea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tea_info);

        initView();
        Intent intent = getIntent();
        tea = intent.getParcelableExtra("teas");//得到一个对象

        tea_name.setText(tea.getXingmin());
        tea_bumen.setText(tea.getBumen());
        tea_sex.setText(tea.getXingbie());
        tea_phone.setText(tea.getCellphone());

        zhigonghao.setText(tea.getZhigonghao());
        xingmin.setText(tea.getXingmin());
        youjian.setText(tea.getYoujian()+"@siso.edu.cn");
        xingbie.setText(tea.getXingbie());
        bumen.setText(tea.getBumen());
        officephone.setText(tea.getOfficephone());
        cellphone.setText(tea.getCellphone());


        tea_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toCall(tea_phone.getText().toString());
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

        tea_name = (TextView) findViewById(R.id.tea_name);
        tea_bumen = (TextView) findViewById(R.id.tea_bumen);
        tea_sex = (TextView) findViewById(R.id.tea_sex);
        tea_phone = (TextView) findViewById(R.id.tea_phone);

        zhigonghao = (TextView) findViewById(R.id.zhigonghaoInfo);
        xingmin = (TextView) findViewById(R.id.xingminInfo);
        youjian = (TextView) findViewById(R.id.youjianInfo);
        xingbie = (TextView) findViewById(R.id.xingbieInfo);
        bumen = (TextView) findViewById(R.id.bumenInfo);
        officephone = (TextView) findViewById(R.id.officephoneInfo);
        cellphone = (TextView) findViewById(R.id.cellphoneInfo);

        imageViewBack = (ImageView) findViewById(R.id.back);
    }
}
