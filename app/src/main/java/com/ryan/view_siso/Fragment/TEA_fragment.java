package com.ryan.view_siso.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.ryan.view_siso.adapter.TeaItemAdapter;
import com.ryan.view_siso.bean.TeaInfoBean;
import com.ryan.view_siso.bean.UserInfoBean;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by air on 15/11/26.
 */
public class TEA_fragment extends Fragment {

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_tea_fg, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.tea_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        try {
            JsonArray_findteacher();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }


    private void JsonArray_findteacher() throws UnsupportedEncodingException {

        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        final String url = "http://xj.siso.edu.cn/stu/teacherquery.aspx";
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
                            Type type = new TypeToken<ArrayList<TeaInfoBean>>() {}.getType();
                            ArrayList<TeaInfoBean> teas = new Gson().fromJson(response.toString(), type);
                            Log.i("JsonObjectRequest_get", String.valueOf(teas.size()));

                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                            TeaItemAdapter teaItemAdapter = new TeaItemAdapter(teas);
                            recyclerView.setAdapter(teaItemAdapter);
                        } else {
                            Toast.makeText(getActivity(), "网络延迟", Toast.LENGTH_LONG).show();
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
}
