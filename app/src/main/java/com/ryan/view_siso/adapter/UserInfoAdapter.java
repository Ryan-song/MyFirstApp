package com.ryan.view_siso.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ryan.view_siso.R;
import com.ryan.view_siso.bean.UserInfoBean;

/**
 * Created by air on 15/11/14.
 *
 * 尝试重构失败
 * 无用
 *
 */
public class UserInfoAdapter {

    private UserInfoBean user;

    public UserInfoAdapter(UserInfoBean user) {
        this.user = user;
    }


    public static class MyViewHold extends RecyclerView.ViewHolder{

//        private TextView st_name;
//        private TextView st_id;
//        private TextView st_job;

        public MyViewHold(View itemView) {
            super(itemView);

//            st_name = (TextView) itemView.findViewById(R.id.st_name);
//            st_id = (TextView) itemView.findViewById(R.id.st_id);
//            st_id = (TextView) itemView.findViewById(R.id.st_id);
        }
    }


}

