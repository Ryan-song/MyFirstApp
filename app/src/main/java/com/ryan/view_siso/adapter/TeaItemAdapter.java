package com.ryan.view_siso.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ryan.view_siso.R;
import com.ryan.view_siso.bean.TeaInfoBean;

import java.util.ArrayList;

/**
 * Created by air on 15/11/27.
 */
public class TeaItemAdapter extends RecyclerView.Adapter<TeaItemAdapter.teaViewHold> {

    private ArrayList<TeaInfoBean> teachers;

    public TeaItemAdapter(ArrayList<TeaInfoBean> teachers) {
        this.teachers = teachers;
    }

    @Override
    public teaViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        final  View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.teainfo_item,parent,false);
        return new teaViewHold(itemView);
    }

    @Override
    public void onBindViewHolder(teaViewHold holder, int position) {
        TeaInfoBean teacherinfo = teachers.get(position);
        holder.depat.setText(teacherinfo.getBumen());
        holder.name.setText(teacherinfo.getXingmin());
    }

    @Override
    public int getItemCount() {
        return teachers.size();
    }

    public class teaViewHold extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView depat;

        public teaViewHold(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.xingmin);
            depat = (TextView) itemView.findViewById(R.id.bumen);
        }
    }

}
