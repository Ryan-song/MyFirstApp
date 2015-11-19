package com.ryan.view_siso.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ryan.view_siso.R;
import com.ryan.view_siso.bean.UserInfoBean;

import java.util.ArrayList;

/**
 * Created by air on 15/11/8.
 * <p/>
 * 通过姓名查找，显示界面的适配器  don't move
 */
public class UserInfoAdapter_2 extends RecyclerView.Adapter {

    private ArrayList<UserInfoBean> users;
    private OnItemClickListener mListener;

    public UserInfoAdapter_2(ArrayList<UserInfoBean> users) {
        this.users = users;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.userinfo_item_2, parent, false);
        itemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(v, (UserInfoBean) itemview.getTag());
                }

            }
        });

        MyViewHold vh = new MyViewHold(itemview);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        UserInfoBean userInfoBean = users.get(position);
        MyViewHold myViewHold = (MyViewHold) holder;

        myViewHold.st_class.setText(userInfoBean.getSTUClass());
        myViewHold.st_id.setText(userInfoBean.getStuID());
//        myViewHold.st_sex.setText(userInfoBean.getSex());
        myViewHold.itemView.setTag(userInfoBean);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    public static class MyViewHold extends RecyclerView.ViewHolder {

        private TextView st_class;
        private TextView st_id;
//        private TextView st_sex;

        public MyViewHold(View itemView) {
            super(itemView);
            st_class = (TextView) itemView.findViewById(R.id.st_class);
            st_id = (TextView) itemView.findViewById(R.id.st_id);
//            st_sex = (TextView) itemView.findViewById(R.id.st_sex);

        }
    }


    public interface OnItemClickListener {
        public void onItemClick(View view, UserInfoBean data);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }


}
