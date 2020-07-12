package com.duzngmd.btl_andorid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.duzngmd.btl_andorid.R;
import com.duzngmd.btl_andorid.model.SanPham;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SanPhamAdapter extends BaseAdapter {
    private Context context;
    private int layput;
    private List<SanPham> arraySanPham;

    public SanPhamAdapter(Context context, int layput, List<SanPham> arraySanPham) {
        this.context = context;
        this.layput = layput;
        this.arraySanPham = arraySanPham;
    }

    @Override
    public int getCount() {
        return arraySanPham.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        ImageView imgAnhChinh;
        TextView tvTen, tvGia;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layput, null);
            holder = new ViewHolder();
            holder.imgAnhChinh  = (ImageView) convertView.findViewById(R.id.imgAnhChinh);
            holder.tvTen        = (TextView) convertView.findViewById(R.id.tvTen);
            holder.tvGia        = (TextView) convertView.findViewById(R.id.tvGia);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        SanPham sanPham = arraySanPham.get(position);
        holder.tvTen.setText(sanPham.getTen());
        holder.tvGia.setText(String.valueOf(sanPham.getGia()));
        Picasso.get().load(sanPham.getAnh_chinh()).into(holder.imgAnhChinh);
        return convertView;
    }
}
