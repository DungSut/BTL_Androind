package com.duzngmd.btl_andorid.intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.duzngmd.btl_andorid.R;
import com.duzngmd.btl_andorid.model.ThongBao;

public class ThongBaoDetail extends AppCompatActivity {

    TextView tvTieuDe, tvNoiDung1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_bao_detail);
        
        Reflect();
        setData();
    }

    private void setData() {
        Intent intent = getIntent();
        ThongBao thongBao = (ThongBao) intent.getSerializableExtra("ThongBao");
        tvTieuDe.setText(thongBao.getTieude());
        tvNoiDung1.setText(thongBao.getNoidung1());
    }

    private void Reflect() {
        tvTieuDe   = (TextView) findViewById(R.id.tvTieuDe);
        tvNoiDung1 = (TextView) findViewById(R.id.tvNoiDung1);
    }
}