package com.ryan.view_siso.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryan.view_siso.R;
import com.ryan.view_siso.adapter.UsersInfoAdapter;
import com.ryan.view_siso.bean.UserInfoBean;

import java.util.ArrayList;

/**
 * Created by air on 15/11/26.
 */
public class STU_fragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<UserInfoBean> users;

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

        recyclerView.setAdapter(new UsersInfoAdapter(users));
    }
}
