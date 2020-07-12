package com.duzngmd.btl_andorid.intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.duzngmd.btl_andorid.R;
import com.duzngmd.btl_andorid.adapter.ThongBaoAdapter;

import java.util.ArrayList;

public class ThongBao extends AppCompatActivity {

    ListView lvThongBao;
    ArrayList<com.duzngmd.btl_andorid.model.ThongBao> arrayListThongBao;
    ThongBaoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_bao);

        Reflect();
        AddThongBao();
        Adapter();
    }

    private void Adapter() {
        adapter = new ThongBaoAdapter(this, R.layout.dong_thong_bao, arrayListThongBao);
        lvThongBao.setAdapter(adapter);
        lvThongBao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ThongBao.this, ThongBaoDetail.class);
                intent.putExtra("ThongBao", arrayListThongBao.get(position));
                startActivity(intent);
            }
        });
    }

    private void AddThongBao() {
        arrayListThongBao = new ArrayList<>();
        arrayListThongBao.add(new com.duzngmd.btl_andorid.model.ThongBao(
                "Tiki Cập nhật giá & Chính sách vận chuyển",
                "20/05/2020","Từ 20/05/2020, Tiki miễn phí giao tiêu chuẩn cho đơn " +
                "hàng từ 250k, áp dụng phí 19k cho đơn hàng dưới 250k",
                getString(R.string.noidung11)));
        arrayListThongBao.add(new com.duzngmd.btl_andorid.model.ThongBao(
                "MÙNG 10 mua ngày VÀNG THẦN TÀI chỉ từ 230k",
                "13/04/2020","Giảm ngay 300k khi mua đơn hàng trên 1,5 triệu. Số" +
                "lượng cực kỳ có hạn", getString(R.string.noidung12)));
        arrayListThongBao.add(new com.duzngmd.btl_andorid.model.ThongBao(
                "10 quyển sách chỉ 500k", "06/03/2020",
                "10 quyển sách chỉ 500k - Áp dụng cho các sản phẩm sách ngoại văn do Tiki" +
                        "phân phối", getString(R.string.noidung13)));
        arrayListThongBao.add(new com.duzngmd.btl_andorid.model.ThongBao(
                "Deal sốc công nghệ", "19/01/2020",
                "Giảm 20% cho các phụ kiện điện tử. Thời gian áp dụng từ 20 - 30/01",
                getString(R.string.noidung14)));
        arrayListThongBao.add(new com.duzngmd.btl_andorid.model.ThongBao(
                "Black Friday Sale Sốc - Săn Ngay Kẻo Hết!",
                "23/11/2019", "Black Friday Sale Sốc Hàng Công Nghệ Đã Mở - Săn Ngay",
                getString(R.string.noidung15)));
        arrayListThongBao.add(new com.duzngmd.btl_andorid.model.ThongBao(
                "GỢI Ý QUÀ TẶNG 20.10", "10/10/2019",
                "GIẢM 50K CHO ĐƠN HÀNG 700k. Áp dụng cho mọi sản phẩm",
                getString(R.string.noidung16)));
        arrayListThongBao.add(new com.duzngmd.btl_andorid.model.ThongBao(
                "Lego", "20/05/2019", "Ưu đãi quốc tế" +
                "thiếu nhi - Giảm 12% cho đơn hàng đồ chơi Lego từ 400k",
                getString(R.string.noidung17)));
    }

    private void Reflect() {
        lvThongBao = (ListView) findViewById(R.id.lvThongBao);
    }
}