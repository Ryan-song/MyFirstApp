package com.ryan.view_siso.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ryan.view_siso.R;
import com.ryan.view_siso.bean.AllClassNameBean;
import com.ryan.view_siso.bean.UserInfoBean;
import com.ryan.view_siso.bean.UserLoginBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FindActivity extends AppCompatActivity {

    private static final String TAG = FindActivity.class.getSimpleName();
    private EditText et_stuid;
    private AutoCompleteTextView autoTv_class;
    private EditText et_name;
    private Button bt_find;

    private int if_id = 0, else_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        initToolBar();
        initView();
        clickEvent();

        JsonArrayRequest_get_allclassname();

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

    private void JsonArrayRequest_get_allclassname() {
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        final String url = "http://xj.siso.edu.cn/stu/jasonclassnames.aspx";

        JsonRequest<JSONArray> jsonRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("get_allclassname", "gothere");
                        Log.i("get_allclassname", response.toString());
                        Log.i("response.length()", String.valueOf(response.length()));

//                        //Success~~!
                        //解析成List
                        Type type = new TypeToken<ArrayList<AllClassNameBean>>() {
                        }.getType();
                        ArrayList<AllClassNameBean> user = new Gson().fromJson(response.toString(), type);
                        Log.i("(user.size())", String.valueOf(user.size()));
                        //转换为数组。。。。
                        String[] array = new String[user.size()];
                        int i;
                        for (i = 0; i < user.size(); i++) {
                            array[i] = (String) user.get(i).getClassName();
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(FindActivity.this,
                                android.R.layout.simple_dropdown_item_1line,
                                array);
                        autoTv_class.setAdapter(adapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("JsonObjectRequest_ERROR", error.getMessage(), error);
            }
        });
        requestQueue.add(jsonRequest);
    }

    private void initView() {
        et_stuid = (EditText) findViewById(R.id.et_stuid);
        autoTv_class = (AutoCompleteTextView) findViewById(R.id.autotv_class);
        et_name = (EditText) findViewById(R.id.et_name);
        bt_find = (Button) findViewById(R.id.bt_find);
        autoTv_class.setThreshold(1);
    }

    private void clickEvent() {
        bt_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!"".equals(et_stuid.getText().toString().trim())) {
                    if_id += 1;
                    else_id = 1;
                }
                if (!"".equals(autoTv_class.getText().toString().trim())) {
                    if_id += 1;
                    else_id = 2;
                }
                if (!"".equals(et_name.getText().toString().trim())) {
                    if_id += 1;
                    else_id = 3;
                }

                switch (if_id) {
                    case 0:
                        Snackbar.make(v, "请输入数据", Snackbar.LENGTH_LONG).show();
                        break;
                    case 1:
                        if (else_id == 1) {
                            Log.i(TAG, "only id find");
//                            JsonObjectRequest_findid();
                            JsonArrayRequest_findid();
                        } else if (else_id == 2) {

                            Log.i(TAG, "only class find");
                            try {
                                JsonArrayRequest_findclass();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }

                        } else if (else_id == 3) {
                            Log.i(TAG, "only name find");
                            try {
                                JsonArrayRequest_findname();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case 2:
                        Snackbar.make(v, "无法两个数据查询,可一个或三个数据查询", Snackbar.LENGTH_LONG).show();
                        break;
                    case 3:
                        Snackbar.make(v, "此功能有待实现", Snackbar.LENGTH_LONG).show();
                        break;
                }
                if_id = 0;
            }
        });


    }

//    private void JsonObjectRequest_findid() {
//        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
///**
// * 返回数组，一个对象
// */
//        final String url = "http://xj.siso.edu.cn/stu/jasonquery.aspx?" +
//                "sid=" +
//                et_stuid.getText().toString();
//
//        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
//                Request.Method.GET,
//                url,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.i("JsonObject_findid", "go-->");
//                        Log.i("JsonObject_findid", response.toString());
//                        Log.i("JsonObject_findid", String.valueOf(response.length()));
//
//                        //Success~!!
//                        Gson gson = new Gson();
//                        UserInfoBean user;
//                        user = gson.fromJson(String.valueOf(response), UserInfoBean.class);
//
//                        Intent intent = new Intent(FindActivity.this, FindStuidActivity.class);
//                        intent.putExtra("user", user);
//                        startActivity(intent);
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("JsonObjectRequest_get", error.getMessage(), error);
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/json; charset=utf-8");
//                return headers;
//            }
//        };
//        requestQueue.add(jsonRequest);
//    }


    private void JsonArrayRequest_findname() throws UnsupportedEncodingException {

        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        final String url = "http://xj.siso.edu.cn/stu/jasonquery.aspx?" +
                "name=" +
                URLEncoder.encode(et_name.getText().toString().trim(), "UTF-8");

        Log.i("TAG", url);

        JsonRequest<JSONArray> jsonRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("JsonArrayRequest_get", "gothere");
                        Log.i("JsonArrayRequest_get", response.toString());
                        Log.i("JsonArrayRequest_get", String.valueOf(response.length()));

                        if (!(response.length() == 0)) {
//                        Success~~!
                            Type type = new TypeToken<ArrayList<UserInfoBean>>() {
                            }.getType();
                            ArrayList<UserInfoBean> user = new Gson().fromJson(response.toString(), type);
                            Log.i("JsonObjectRequest_get", String.valueOf(user.size()));

                            //传递数组对象02
                            Intent intent = new Intent(FindActivity.this, FindNameActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList("users_name", user);
                            intent.putExtras(bundle);
                            startActivity(intent);

                        } else {
                            Toast.makeText(getApplicationContext(), "查无此号", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("JsonObjectRequest_ERROR", error.getMessage(), error);
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

    private void JsonArrayRequest_findclass() throws UnsupportedEncodingException {
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        final String url = "http://xj.siso.edu.cn/stu/jasonquery.aspx?" +
                "classname=" +
                URLEncoder.encode(autoTv_class.getText().toString().trim(), "UTF-8");
        Log.i("TAG", url);


        JsonRequest<JSONArray> jsonRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("JsonArrayRequest_get", "gothere");
                        Log.i("JsonArrayRequest_get", response.toString());
                        Log.i("JsonArrayRequest_get", String.valueOf(response.length()));

                        if (!(response.length() == 0)) {
//                        Success~~!
                            Type type = new TypeToken<ArrayList<UserInfoBean>>() {
                            }.getType();
                            ArrayList<UserInfoBean> user = new Gson().fromJson(response.toString(), type);

                            Log.i("JsonObjectRequest_get", String.valueOf(user.size()));

                            Intent intent = new Intent(FindActivity.this, FindClassActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList("users", user);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "查无此号", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("JsonObjectRequest_ERROR", error.getMessage(), error);
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

    private void JsonArrayRequest_findid() {
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        /**
         * 返回数组，一个对象
         */
        final String url = "http://xj.siso.edu.cn/stu/jasonquery.aspx?" +
                "sid=" +
                et_stuid.getText().toString();

        JsonRequest<JSONArray> jsonRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("JsonArrayRequest_findid", "go-->");
                        Log.i("JsonArrayRequest_findid", response.toString());
                        Log.i("JsonArrayRequest_findid", String.valueOf(response.length()));

                        if (!(response.length() == 0)) {
//                        Success~~!
                            Type type = new TypeToken<ArrayList<UserInfoBean>>() {
                            }.getType();
                            ArrayList<UserInfoBean> user = new Gson().fromJson(response.toString(), type);
                            Log.i("JsonArrayRequest_findid", String.valueOf(user.size()));

                            //ArrayList<UserInfoBean> TEST
                            Log.i(TAG, user.get(0).getPoliticalStatus() + user.get(0).getStuName() + "MAY BE COULD");
                            //传递数组对象02
                            Intent intent = new Intent(FindActivity.this, FindStuidActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList("user", user);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "查无此号", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("JsonObjectRequest_ERROR", error.getMessage(), error);
            }
        });
        requestQueue.add(jsonRequest);
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FindActivity.this, MainActivity.class));
            }
        });
    }


}
