package com.duzngmd.btl_andorid.intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.duzngmd.btl_andorid.R;
import com.duzngmd.btl_andorid.model.SanPham;
import com.squareup.picasso.Picasso;

public class SanPhamDetail extends AppCompatActivity {

    ImageView imgAnhChinhSanPham, imgAnhPhuSanPham;
    TextView tvTenSanPham, tvGiaSanPham, tvmotaSanPham;
    Button btnMua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham_detail);

        Reflect();
        getData();
    }

    private void Reflect() {
        imgAnhChinhSanPham = (ImageView) findViewById(R.id.imgAnhChinhSanPham);
        imgAnhPhuSanPham   = (ImageView) findViewById(R.id.imgAnhPhuSanPham);
        tvTenSanPham       = (TextView) findViewById(R.id.tvTenSanPham);
        tvGiaSanPham       = (TextView) findViewById(R.id.tvGiaSanPham);
        tvmotaSanPham      = (TextView) findViewById(R.id.tvmotaSanPham);
        btnMua             = (Button) findViewById(R.id.btnMua);
    }

    private void getData(){
        Intent intent = getIntent();
        final SanPham sanPham = (SanPham) intent.getSerializableExtra("sanphamdetail");
        sanPham.setSoLuong(1);
        Picasso.get().load(sanPham.getAnh_chinh()).into(imgAnhChinhSanPham);
        tvTenSanPham.setText(sanPham.getTen());
        tvGiaSanPham.setText(String.valueOf(sanPham.getGia()));
        tvmotaSanPham.setText(sanPham.getMota());
        Picasso.get().load(sanPham.getAnh_phu()).into(imgAnhPhuSanPham);

        btnMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(SanPhamDetail.this, GioHang.class);
                intent1.putExtra("muasanpham", sanPham);
                startActivity(intent1);
                Toast.makeText(SanPhamDetail.this, "Thêm sản phẩm vào giỏ hàng thành công ",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

}