package com.ryan.view_siso.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.ryan.view_siso.NetUtil;
import com.ryan.view_siso.R;
import com.ryan.view_siso.SmsUtil;
import com.ryan.view_siso.bean.UserLoginBean;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * 12/11 代码改动较大。
 *
 * MainActivity为原第一界面，不变，200～无参跳转
 * FindActivity为原第二界面，［修改为主FindActivity2，包含此Activity的内容］
 *      ｛ 新命名：FindActivity2，包含3个Fragment，［STU_Fragment_0,TEA_Fragment_1,SETing_Fragment_2],
 *              FindActivity——>STU_Fragment
 *              TEA_Fragment_1-->TEA_fragment｝
 * STU_Fragment{
 * FindStuidActivity为原第三界面，第一查询。不变
 *
 *  Main2Activity为原第三界面，第二查询。［原替代FindClassActivity］
 *      ｛ 新命名：更改为原来的原替代FindClassActivity｝
 *
 * FindNameActivity为原第三界面，第三查询，不变
 *}
 * TEA_Fragment{
 *     老师接口
 * }
 * SETing_Fragment_2{
 *     设置界面
 * }
 */

public class MainActivity extends AppCompatActivity {
    // TODO: 15/12/8 https://github.com/lzyzsd/AndroidRandomColor

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String LOGIN_FAIL = "未找到";

    private CoordinatorLayout mCoordinatorLayout;
    private AutoCompleteTextView mAutoCompleteTextView;
    private EditText mEditText;
    private Button mButton;
    private CheckBox mCheckBox;

    private SharedPreferences sp;
    private String username;
    private String userpass;



    //上次按下返回键的系统时间
    private long lastBackTime = 0;
    //当前按下返回键的系统时间
    private long currentBackTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        clickEvent();

        initWindow();

        if (!NetUtil.isConnected(this)) {
            Snackbar.make(mCoordinatorLayout, "未接入网络", Snackbar.LENGTH_LONG).show();
        }

        if (sp.getAll().size() != 0) {
            mAutoCompleteTextView.setText(sp.getString("admin", ""));
            mEditText.setText(sp.getString("password", ""));
        }


    }


    private SystemBarTintManager tintManager;
    private void initWindow(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintColor(getResources().getColor(R.color.app_main_color));
            tintManager.setStatusBarTintEnabled(true);
        }
    }




    private void clickEvent() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObjectRequest();
            }
        });


        mAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String[] allusername;
                allusername = sp.getAll().keySet().toArray(new String[0]);
                // sp.getAll()返回一张hash map
                // keySet()得到的是a set of the keys.
                // hash map是由key-value组成的
                Log.i("allusername", String.valueOf(allusername.length));
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        MainActivity.this,
                        android.R.layout.simple_dropdown_item_1line,
                        allusername);
                mAutoCompleteTextView.setAdapter(adapter);// 设置数据适配器
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEditText.setText(sp.getString(mAutoCompleteTextView.getText().toString(), ""));// 自动输入密码
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void JsonObjectRequest() {
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
/**
 * 一、验证学生
 */
        username = mAutoCompleteTextView.getText().toString();
        userpass = mEditText.getText().toString();
        final String url = "http://xj.siso.edu.cn/stu/jasonlogin.aspx?" +
                "u=" +
                username +
                "&p=" +
                userpass;
        Log.i(TAG, url);

        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, response.toString());

                        //Success~!!
                        Gson gson = new Gson();
                        UserLoginBean user;
                        user = gson.fromJson(String.valueOf(response), UserLoginBean.class);

                        String type = user.getType();
                        Log.i(TAG, type);
                        if (type.equals(LOGIN_FAIL)) {
                            Snackbar.make(mCoordinatorLayout, "用户名或密码有误", Snackbar.LENGTH_LONG).show();
                            Log.i(TAG, "用户名或密码有误");
                        } else {
                            if (mCheckBox.isChecked()) {
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString(username, userpass);
                                editor.putString("admin", username);
                                editor.putString("password", userpass);
                                editor.commit();
                                Log.i("SPSPSPSP", sp.getString(username, "noon"));
                                Log.i("SPSPSPSP", sp.getString("admin", "noon"));
                                Log.i("SPSPSPSP", sp.getString("password", "noon"));
                            } else {
                                SharedPreferences.Editor editor = sp.edit();
                                editor.remove(username);
                                editor.remove("password");
                                editor.commit();
                            }
//                            startActivity(new Intent(MainActivity.this, FindActivity.class));

                            startActivity(new Intent(MainActivity.this, FindActivity2.class));

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage(), error);
                Snackbar.make(mCoordinatorLayout, "连接超时", Snackbar.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        requestQueue.add(jsonRequest);
    }

    private void initView() {
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        mAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autotv_name);
        mEditText = (EditText) findViewById(R.id.et_pass);
        mButton = (Button) findViewById(R.id.btn_signin);
        mCheckBox = (CheckBox) findViewById(R.id.cb_login);
        mCheckBox.setChecked(true);

        sp = this.getSharedPreferences("SP", MODE_PRIVATE);
        mAutoCompleteTextView.setThreshold(1);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //获取当前系统时间的毫秒数
            currentBackTime = System.currentTimeMillis();
            Log.i("currentBackTime", String.valueOf(currentBackTime));
            if (currentBackTime - lastBackTime > 2 * 1000) {
                Snackbar.make(mCoordinatorLayout, "再按一次返回键退出", Snackbar.LENGTH_LONG).show();
                lastBackTime = currentBackTime;
                Log.i("lastBackTime", String.valueOf(lastBackTime));
            } else {
                finish();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
