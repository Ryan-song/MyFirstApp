package com.ryan.view_siso.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ryan.view_siso.R;
import com.ryan.view_siso.activity.FindNameActivity;
import com.ryan.view_siso.activity.FindStuidActivity;
import com.ryan.view_siso.activity.Main2Activity;
import com.ryan.view_siso.bean.AllClassNameBean;
import com.ryan.view_siso.bean.UserInfoBean;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by air on 15/12/11.
 */
public class STU_Fragment_0 extends Fragment {

    private static final String TAG = STU_Fragment_0.class.getSimpleName();

    private EditText et_STUid;
    private AutoCompleteTextView autoTv_STUclass;
    private EditText et_STUname;
    private Button bt_find;

    private int if_id = 0, else_id;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_stu_0, container, false);
        initView(view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //获取AUTO的班级信息
        JsonArrayRequest_get_allclassname();
        //Button的点击事件
        clickEvent();
    }

    private void clickEvent() {
        bt_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!"".equals(et_STUid.getText().toString().trim())) {
                    if_id += 1;
                    else_id = 1;
                }
                if (!"".equals(autoTv_STUclass.getText().toString().trim())) {
                    if_id += 1;
                    else_id = 2;
                }
                if (!"".equals(et_STUname.getText().toString().trim())) {
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

    /**
     * 通过姓名查找，传递 putParcelableArrayList
     *
     * @throws UnsupportedEncodingException
     */
    private void JsonArrayRequest_findname() throws UnsupportedEncodingException {

        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        final String url = "http://xj.siso.edu.cn/stu/jasonquery.aspx?" +
                "name=" +
                URLEncoder.encode(et_STUname.getText().toString().trim(), "UTF-8");

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
                            Intent intent = new Intent(getActivity(), FindNameActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList("users_name", user);
                            intent.putExtras(bundle);
                            startActivity(intent);

                        } else {
                            Toast.makeText(getContext(), "查无此号", Toast.LENGTH_LONG).show();
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


    /**
     * 通过班级查找，传递 putParcelableArrayList
     *
     * @throws UnsupportedEncodingException
     */
    private void JsonArrayRequest_findclass() throws UnsupportedEncodingException {
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final String url = "http://xj.siso.edu.cn/stu/jasonquery.aspx?" +
                "classname=" +
                URLEncoder.encode(autoTv_STUclass.getText().toString().trim(), "UTF-8");
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

                            /**
                             * 更改地方1
                             */
//                            Intent intent = new Intent(FindActivity.this, FindClassActivity.class);
                            Intent intent = new Intent(getActivity(), Main2Activity.class);

                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList("users", user);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getContext(), "查无此号", Toast.LENGTH_LONG).show();
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


    /**
     * 通过学号查找, 传递 putParcelableArrayList
     */
    private void JsonArrayRequest_findid() {
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        /**
         * 返回数组，一个对象
         */
        final String url = "http://xj.siso.edu.cn/stu/jasonquery.aspx?" +
                "sid=" +
                et_STUid.getText().toString();

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
                            Intent intent = new Intent(getActivity(), FindStuidActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList("user", user);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getActivity(), "查无此号", Toast.LENGTH_LONG).show();
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


    /**
     * 获取能查询的班级
     */
    private void JsonArrayRequest_get_allclassname() {
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
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

                        //type的使用
                        Type type = new TypeToken<ArrayList<AllClassNameBean>>() {
                        }.getType();
                        //以对象的形式存放
                        ArrayList<AllClassNameBean> user = new Gson().fromJson(response.toString(), type);

                        Log.i("(user.size())", String.valueOf(user.size()));
                        //这里又转换为数组。。。。是为了适配AUTO的Adapter
                        String[] array = new String[user.size()];
                        int i;
                        for (i = 0; i < user.size(); i++) {
                            array[i] = (String) user.get(i).getClassName();
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_dropdown_item_1line,
                                array);

                        autoTv_STUclass.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("JsonObjectRequest_ERROR", error.getMessage(), error);
            }
        });
        requestQueue.add(jsonRequest);
    }

    private void initView(View view) {
        et_STUid = (EditText) view.findViewById(R.id.et_stuid);
        autoTv_STUclass = (AutoCompleteTextView) view.findViewById(R.id.autotv_class);
        et_STUname = (EditText) view.findViewById(R.id.et_name);
        bt_find = (Button) view.findViewById(R.id.bt_find);
        autoTv_STUclass.setThreshold(1);
    }
}
