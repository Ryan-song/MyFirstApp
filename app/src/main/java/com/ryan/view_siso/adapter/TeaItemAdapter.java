package com.ryan.view_siso.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ryan.view_siso.R;
import com.ryan.view_siso.bean.TeaInfoBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by air on 15/11/27.
 */
public class TeaItemAdapter extends RecyclerView.Adapter<TeaItemAdapter.teaViewHold> {

    private ArrayList<TeaInfoBean> teachers;
    private OnItemClickListener mListener;

    private int[] imgRId =
            {R.drawable.user_0,R.drawable.user_3,R.drawable.user_5};

    public TeaItemAdapter(ArrayList<TeaInfoBean> teachers) {
        this.teachers = teachers;
    }

    @Override
    public teaViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        final  View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.teainfo_item,parent,false);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener!=null){
                    mListener.onItemClick(v, (TeaInfoBean) itemView.getTag());
                }
            }
        });

        return new teaViewHold(itemView);
    }

    @Override
    public void onBindViewHolder(teaViewHold holder, int position) {
        TeaInfoBean teacherinfo = teachers.get(position);
        holder.depat.setText(teacherinfo.getBumen());
        holder.name.setText(teacherinfo.getXingmin());
        holder.cellphone.setText(teacherinfo.getCellphone());

        holder.tea_img.setImageResource(imgRId[new Random().nextInt(3)]);

        holder.itemView.setTag(teacherinfo);
    }

    @Override
    public int getItemCount() {
        return teachers.size();
    }

    public class teaViewHold extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView depat;
        private TextView cellphone;
        private ImageView tea_img;

        public teaViewHold(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.xingmin);
            depat = (TextView) itemView.findViewById(R.id.tea_bumen);
            cellphone = (TextView) itemView.findViewById(R.id.cellphone);

            tea_img = (ImageView) itemView.findViewById(R.id.tea_img);
        }
    }


    public interface OnItemClickListener{
        public void onItemClick(View view,TeaInfoBean data);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }


    public void setFilter(List<TeaInfoBean> userInfoBeen){
        teachers = new ArrayList<>();
        teachers.addAll(userInfoBeen);
        notifyDataSetChanged();
    }

}
