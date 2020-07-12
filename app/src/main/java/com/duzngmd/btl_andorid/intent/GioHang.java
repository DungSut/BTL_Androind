package com.duzngmd.btl_andorid.intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.duzngmd.btl_andorid.MainActivity;
import com.duzngmd.btl_andorid.R;
import com.duzngmd.btl_andorid.activity.HomeFragment;
import com.duzngmd.btl_andorid.adapter.GioHangAdapter;
import com.duzngmd.btl_andorid.adapter.SanPhamAdapter;
import com.duzngmd.btl_andorid.model.SanPham;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class GioHang extends AppCompatActivity {

    TextView tvTien, tvTongTien, tvEmailThanhToan;
    Button btnThanhToan, btnTiepTuc, btnThanhToanOK;
    Button btnDangNhapThanhToan, btnDangKyThanhToan;
    ListView lvgioHang;
    GioHangAdapter adapter;
    FirebaseUser user;
    int CODE_DANGNHAP_TT = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        user = FirebaseAuth.getInstance().getCurrentUser();
        Reflect();
        adapter = new GioHangAdapter(this, R.layout.dong_gio_hang, MainActivity.gioHang);
        Even();

    }

    private void Even() {
        Intent intent = getIntent();
        SanPham sanPham = (SanPham) intent.getSerializableExtra("muasanpham");
        if (sanPham != null){
            MainActivity.gioHang.add(sanPham);
            adapter.notifyDataSetChanged();
            lvgioHang.setAdapter(adapter);
            btnTiepTuc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GioHang.this, MainActivity.class);
                    startActivity(intent);
                }
            });
            updateTongTien();
        }else {
            if (MainActivity.gioHang.size() <= 0){
                tvTongTien.setVisibility(View.GONE);
                tvTien.setVisibility(View.GONE);
                btnThanhToan.setVisibility(View.GONE);
                lvgioHang.setVisibility(View.GONE);
                btnTiepTuc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(GioHang.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
            }else {
                lvgioHang.setAdapter(adapter);
                btnTiepTuc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(GioHang.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }
        //tinh tien

        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null){
                    ThanhToan();
                }else {
//                    ThanhToanKhongUser();
//                    ThanhToan();
                }
            }
        });
    }

//    private void ThanhToanKhongUser(){
//        Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_ttkhonguser);
//        dialog.setCanceledOnTouchOutside(false);
//
//        btnDangKyThanhToan   = (Button)dialog.findViewById(R.id.btnDangKyThanhToan);
//        btnDangNhapThanhToan = (Button)dialog.findViewById(R.id.btnDangNhapThanhToan);
//        btnDangNhapThanhToan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(GioHang.this, DangNhap.class);
//                startActivityForResult(intent, CODE_DANGNHAP_TT);
//            }
//        });
//        dialog.show();
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == CODE_DANGNHAP_TT && resultCode == RESULT_OK && data != null){
//            String email = data.getStringExtra("guiemail");
//        }
//    }

    public void updateTongTien() {
        adapter.updateTongTien(new GioHangAdapter.UpdateTongTien() {
            @Override
            public void onDataChanged(ArrayList<SanPham> gioHang) {
                int tongTien = 0;
                for (SanPham sanPham: gioHang) {
                    tongTien += sanPham.getSoLuong() * sanPham.getGia();
                }
                tvTien.setText(String.valueOf(tongTien));
            }
        });

    }

    private void ThanhToan(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_thanhtoan);
        dialog.setCanceledOnTouchOutside(false);

        tvEmailThanhToan = (TextView)dialog.findViewById(R.id.tvEmailThanhToan);
        btnThanhToanOK   = (Button)dialog.findViewById(R.id.btnThanhToanOK);
        tvEmailThanhToan.setText(user.getEmail());
        btnThanhToanOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GioHang.this, "Cảm ơn quý khách!", Toast.LENGTH_SHORT).show();
                MainActivity.gioHang.clear();
                dialog.dismiss();
                Intent intent = new Intent(GioHang.this, MainActivity.class);
                startActivity(intent);
            }
        });
        dialog.show();
    }

    private void Reflect() {
        tvTongTien   = (TextView) findViewById(R.id.tvTongTien);
        tvTien       = (TextView) findViewById(R.id.tvTien);
        btnThanhToan = (Button) findViewById(R.id.btnThanhToan);
        btnTiepTuc   = (Button) findViewById(R.id.btnTiepTuc);
        lvgioHang    = (ListView) findViewById(R.id.lvgioHang);
    }
}