package com.ryan.view_siso.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ryan.view_siso.R;
import com.ryan.view_siso.bean.UserInfoBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by air on 15/11/8.
 * <p/>
 * 展示班级所有人的信息简表（item）
 */
public class UsersInfoAdapter extends RecyclerView.Adapter {

    private ArrayList<UserInfoBean> users;
    private OnItemClickListener mListener;

    private int[] imgRId =
            {R.drawable.user_0,R.drawable.user_3,R.drawable.user_5};

    public UsersInfoAdapter(ArrayList<UserInfoBean> user) {
        this.users = user;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.userinfo_item, parent, false);
        //http://www.bkjia.com/Androidjc/975673.html
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
        UserInfoBean mUserInfoBean = users.get(position);
        MyViewHold myViewHold = (MyViewHold) holder;

        myViewHold.st_name.setText(mUserInfoBean.getStuName());
        myViewHold.st_phone.setText(mUserInfoBean.getMobile());
//        myViewHold.st_id.setText(mUserInfoBean.getStuID().substring(6));
        myViewHold.st_id.setText(mUserInfoBean.getStuID());

        //获取伪随机数范围
//        int range = imgRId.length;
        //伪随机数对象
//        Random random = new Random();
        myViewHold.st_img.setImageResource(imgRId[new Random().nextInt(3)]);

        myViewHold.itemView.setTag(mUserInfoBean);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class MyViewHold extends RecyclerView.ViewHolder {

        private TextView st_name;
        private TextView st_phone;
        private TextView st_id;
        private ImageView st_img;


        public MyViewHold(View itemView) {
            super(itemView);

            st_name = (TextView) itemView.findViewById(R.id.st_class);
            st_phone = (TextView) itemView.findViewById(R.id.st_phone);
            st_id = (TextView) itemView.findViewById(R.id.st_id);

            st_img = (ImageView) itemView.findViewById(R.id.tea_img);
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, UserInfoBean data);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public void setFilter(List<UserInfoBean> userInfoBeen) {
        users = new ArrayList<>();
        users.addAll(userInfoBeen);
        notifyDataSetChanged();
    }
}
