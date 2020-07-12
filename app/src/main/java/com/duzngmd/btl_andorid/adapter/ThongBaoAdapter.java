package com.duzngmd.btl_andorid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.duzngmd.btl_andorid.R;
import com.duzngmd.btl_andorid.model.ThongBao;

import java.util.List;

public class ThongBaoAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<ThongBao> thongBaoList;

    public ThongBaoAdapter(Context context, int layout, List<ThongBao> thongBaoList) {
        this.context = context;
        this.layout = layout;
        this.thongBaoList = thongBaoList;
    }

    @Override
    public int getCount() {
        return thongBaoList.size();
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
        TextView tvTitle, tvNgay, tvNoiDung;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.tvTitle   = (TextView) convertView.findViewById(R.id.tvTitle);
            holder.tvNgay    = (TextView) convertView.findViewById(R.id.tvNgay);
            holder.tvNoiDung = (TextView) convertView.findViewById(R.id.tvNoiDung);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        ThongBao thongBao = thongBaoList.get(position);
        holder.tvTitle.setText(thongBao.getTieude());
        holder.tvNgay.setText(thongBao.getNgay());
        holder.tvNoiDung.setText(thongBao.getNoidung());
        return convertView;
    }
}
