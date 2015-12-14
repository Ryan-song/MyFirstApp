package com.ryan.view_siso.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ryan.view_siso.Fragment.SETing_Fragment_2;
import com.ryan.view_siso.Fragment.STU_Fragment_0;
import com.ryan.view_siso.Fragment.TEA_Fragment_1;
import com.ryan.view_siso.Fragment.TEA_fragment;
import com.ryan.view_siso.R;
import com.ryan.view_siso.adapter.ViewPagerAdapter;

public class FindActivity2 extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find2);


        initToolbar();
        initView();

    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager_2);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tablayout_2);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter vipagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        vipagerAdapter.addFragment(new STU_Fragment_0(),"学生");
        vipagerAdapter.addFragment(new TEA_fragment(),"老师");
        vipagerAdapter.addFragment(new SETing_Fragment_2(),"设置");
        viewPager.setAdapter(vipagerAdapter);
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
