package com.duzngmd.btl_andorid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.duzngmd.btl_andorid.MainActivity;
import com.duzngmd.btl_andorid.R;
import com.duzngmd.btl_andorid.model.SanPham;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GioHangAdapter extends BaseAdapter {
    private int soLuong;
    private Context context;
    private int layout;
    private List<SanPham> sanPhamList;
    public long tongTien = 0;
    public static List<Double> giaSanPhamList = new ArrayList<>();


    public interface UpdateTongTien {
        public void onDataChanged(ArrayList<SanPham> gioHang);
    }


    public GioHangAdapter(Context context, int layout, List<SanPham> sanPhamList) {
        this.context = context;
        this.layout = layout;
        this.sanPhamList = sanPhamList;
    }

    UpdateTongTien mUpdateTongTien;

    public void updateTongTien(UpdateTongTien updateTongTien) {
        mUpdateTongTien = updateTongTien;
    }

    @Override
    public int getCount() {
        return sanPhamList.size();
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
        ImageView imgSP, imgTru, imgCong;
        TextView tvTenSP, tvGiaSP, tvSoLuong;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder = new ViewHolder();
            holder.imgSP     = (ImageView) convertView.findViewById(R.id.imgSP);
            holder.imgTru    = (ImageView) convertView.findViewById(R.id.imgTru);
            holder.imgCong   = (ImageView) convertView.findViewById(R.id.imgCong);
            holder.tvTenSP   = (TextView) convertView.findViewById(R.id.tvTenSP);
            holder.tvGiaSP   = (TextView) convertView.findViewById(R.id.tvGiaSP);
            holder.tvSoLuong = (TextView) convertView.findViewById(R.id.tvSoLuong);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        final SanPham sanPham = sanPhamList.get(position);
        final double giaSP = sanPham.getGia();
        holder.tvTenSP.setText(sanPham.getTen());
        holder.tvGiaSP.setText(String.valueOf(giaSP));
        Picasso.get().load(sanPham.getAnh_chinh()).into(holder.imgSP);
        soLuong = sanPham.getSoLuong();
        holder.tvSoLuong.setText(String.valueOf(soLuong));
        giaSanPhamList.add(position, giaSP);

        updateScreen(soLuong, holder.imgCong, holder.imgTru, MainActivity.gioHang.get(position));

        holder.imgCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soMoi = soLuong + 1;
                soLuong = soMoi;
                holder.tvSoLuong.setText(String.valueOf(soMoi));
                updateScreen(soLuong, holder.imgCong, holder.imgTru, MainActivity.gioHang.get(position));
            }
        });
        holder.imgTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soMoi = soLuong - 1;
                soLuong = soMoi;
                holder.tvSoLuong.setText(String.valueOf(soMoi));
                updateScreen(soLuong, holder.imgCong, holder.imgTru, MainActivity.gioHang.get(position));
            }
        });
        return convertView;
    }

    private void updateScreen(int soLuong, ImageView imgCong, ImageView imgTru, SanPham sanPham) {
        if (soLuong>=10){
            imgCong.setVisibility(View.GONE);
            imgTru.setVisibility(View.VISIBLE);
        }else if (soLuong <= 1){
            imgTru.setVisibility(View.GONE);
            imgCong.setVisibility(View.VISIBLE);
        }else {
            imgCong.setVisibility(View.VISIBLE);
            imgTru.setVisibility(View.VISIBLE);
        }

        sanPham.setSoLuong(soLuong);
        if (mUpdateTongTien != null) {
            mUpdateTongTien.onDataChanged(MainActivity.gioHang);
        }

    }

}
