package com.ryan.view_siso.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ryan.view_siso.Fragment.STU_fragment;
import com.ryan.view_siso.Fragment.TEA_fragment;
import com.ryan.view_siso.R;
import com.ryan.view_siso.adapter.ViewPagerAdapter;

public class Main2Activity extends AppCompatActivity {

    private TabLayout tablayout;
    private ViewPager viewPager01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        initToolbar();
        initView();


    }

    private void initView() {

        viewPager01 = (ViewPager) findViewById(R.id.viewPager01);
        setupViewPager(viewPager01);

        tablayout = (TabLayout) findViewById(R.id.tablayout);
        tablayout.setupWithViewPager(viewPager01);

    }

    private void setupViewPager(ViewPager viewPager01) {
        ViewPagerAdapter viewpageradapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewpageradapter.addFragment(new STU_fragment(),"学生");
        viewpageradapter.addFragment(new TEA_fragment(),"老师");
        viewPager01.setAdapter(viewpageradapter);
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
