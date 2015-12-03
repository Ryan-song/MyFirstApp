package com.ryan.view_siso.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.ryan.view_siso.activity.FindClassUserActivity;
import com.ryan.view_siso.activity.Main2Activity;
import com.ryan.view_siso.activity.TeaInfoActivity;
import com.ryan.view_siso.adapter.TeaItemAdapter;
import com.ryan.view_siso.bean.TeaInfoBean;
import com.ryan.view_siso.bean.UserInfoBean;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by air on 15/11/26.
 */
public class TEA_fragment extends Fragment implements SearchView.OnQueryTextListener{

    private RecyclerView recyclerView;
    private ArrayList<TeaInfoBean> teas;
    private TeaItemAdapter teaItemAdapter;

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
        //使用自己的toolbar工具栏
        setHasOptionsMenu(true);

        try {
            JsonArray_findteacher();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main,menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<TeaInfoBean> filteredModelList = filter(teas, newText);
        teaItemAdapter.setFilter(filteredModelList);
        return true;
    }

    private List<TeaInfoBean> filter(List<TeaInfoBean> teaInfoBean, String newText){
        newText = newText.toLowerCase();
        final List<TeaInfoBean> filteredModelList = new ArrayList<>();
        for (TeaInfoBean model : teaInfoBean){
            final String text = model.getXingmin().toLowerCase();
            if (text.contains(newText)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
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
                            teas = new Gson().fromJson(response.toString(), type);
                            Log.i("JsonObjectRequest_get", String.valueOf(teas.size()));

                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                            teaItemAdapter = new TeaItemAdapter(teas);

                            teaItemAdapter.setOnItemClickListener(new TeaItemAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, TeaInfoBean data) {
                                    /**
                                     *  传递一个对象
                                     */
                                    Intent intent = new Intent(getActivity(),TeaInfoActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putParcelableArrayList("users", data);
                                    intent.putExtra("teas", data);
                                    startActivity(intent);
                                }
                            });

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
