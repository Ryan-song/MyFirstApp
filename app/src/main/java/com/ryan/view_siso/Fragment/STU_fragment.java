package com.ryan.view_siso.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ryan.view_siso.R;
import com.ryan.view_siso.adapter.UsersInfoAdapter;
import com.ryan.view_siso.bean.UserInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by air on 15/11/26.
 */
public class STU_fragment extends Fragment implements SearchView.OnQueryTextListener{

    private RecyclerView recyclerView;
    private ArrayList<UserInfoBean> users;
    private UsersInfoAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_stu_fg, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        users = getActivity().getIntent().getParcelableArrayListExtra("users");

        adapter = new UsersInfoAdapter(users);
        recyclerView.setAdapter(adapter);
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
        final List<UserInfoBean> filteredModelList = filter(users, newText);
        adapter.setFilter(filteredModelList);
        return true;
    }

    private List<UserInfoBean> filter(List<UserInfoBean> userInfoBean,String newText){
        newText = newText.toLowerCase();
        final List<UserInfoBean> filteredModelList = new ArrayList<>();
        for (UserInfoBean model : userInfoBean){
            final String text = model.getStuName().toLowerCase();
            if (text.contains(newText)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

}
