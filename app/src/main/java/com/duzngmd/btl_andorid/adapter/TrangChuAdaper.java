package com.duzngmd.btl_andorid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.duzngmd.btl_andorid.R;
import com.duzngmd.btl_andorid.model.DanhMucTrangChu;

import java.util.List;

public class TrangChuAdaper extends BaseAdapter {
    private Context context;
    private int layout;
    private List<DanhMucTrangChu> trangChuList;

    public TrangChuAdaper(Context context, int layout, List<DanhMucTrangChu> trangChuList) {
        this.context = context;
        this.layout = layout;
        this.trangChuList = trangChuList;
    }

    @Override
    public int getCount() {
        return trangChuList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class ViewHolder{
        ImageView imgHinhTC;
        TextView tvNameTC;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.imgHinhTC = (ImageView) convertView.findViewById(R.id.imgHinhTC);
            holder.tvNameTC = (TextView) convertView.findViewById(R.id.tvNameTC);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }


        DanhMucTrangChu danhMucTrangChu = trangChuList.get(position);
        holder.imgHinhTC.setImageResource(danhMucTrangChu.getHinh());
        holder.tvNameTC.setText(danhMucTrangChu.getName());
        return convertView;
    }
}
