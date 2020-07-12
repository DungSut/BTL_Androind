package com.duzngmd.btl_andorid.intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.duzngmd.btl_andorid.MainActivity;
import com.duzngmd.btl_andorid.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ThongTinNguoiDung extends AppCompatActivity {

    TextView tvEmail, tvAnHien, tvBaoMat;
    EditText etPass;
    Button btnDoiMK, btnDoi, btnHuy, btnDangXuat, btnSanPham, btnTrangChu;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_nguoi_dung);

        user = FirebaseAuth.getInstance().getCurrentUser();
        Reflect();
        AnHien();
        Even();
    }

    private void getEmail(){
        if (user != null){
            String email = user.getEmail();
            tvEmail.setText(email);
            boolean baomat = user.isEmailVerified();
            if (baomat){
                tvBaoMat.setText("Cao");
            }else {
                tvBaoMat.setText("Thấp");
            }
            String iD = user.getUid();
            if (iD.equals("LwWfq6hJ2NNUiFDRKKuAYIsEDIm1")){
                btnSanPham.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ThongTinNguoiDung.this, SuaSanPham.class);
                        startActivity(intent);
                    }
                });
            }else {
                btnSanPham.setVisibility(View.GONE);
            }
        }else {
            btnDoiMK.setVisibility(View.GONE);
            btnDangXuat.setVisibility(View.GONE);
        }
    }

    private void Reflect() {
        tvBaoMat    = (TextView) findViewById(R.id.tvBaoMat);
        tvEmail     = (TextView) findViewById(R.id.tvEmail);
        tvAnHien    = (TextView) findViewById(R.id.tvAnHien);
        etPass      = (EditText) findViewById(R.id.etPass);
        btnDoiMK    = (Button) findViewById(R.id.btnDoiMK);
        btnDoi      = (Button) findViewById(R.id.btnDoi);
        btnHuy      = (Button) findViewById(R.id.btnHuy);
        btnDangXuat = (Button) findViewById(R.id.btnDangXuat);
        btnSanPham  = (Button) findViewById(R.id.btnSanPham);
        btnTrangChu = (Button) findViewById(R.id.btnTrangChu);
        An();
        getEmail();
    }

    private void Even() {
        btnDoiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hien();
            }
        });
        btnDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String newPass = etPass.getText().toString().trim();
                if (newPass.isEmpty()){
                    Toast.makeText(ThongTinNguoiDung.this,
                            "Nhập mật khẩu trước khi đổi", Toast.LENGTH_SHORT).show();
                }else {
                    if (newPass.length() < 6){
                        Toast.makeText(ThongTinNguoiDung.this,
                                "Mật khẩu có độ dài không hợp lý", Toast.LENGTH_SHORT).show();
                    }else {
                        user.updatePassword(newPass)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(ThongTinNguoiDung.this,
                                                    "Đổi mật khẩu thành công",
                                                    Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(ThongTinNguoiDung.this,
                                                    DangNhap.class);
                                            startActivity(intent);
                                        }
                                    }
                                });
                        An();
                        etPass.setText("");
                    }
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                An();
                etPass.setText("");
            }
        });
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ThongTinNguoiDung.this, DangNhap.class);
                startActivity(intent);
            }
        });
        btnTrangChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThongTinNguoiDung.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void An(){
        etPass.setVisibility(View.GONE);
        tvAnHien.setVisibility(View.GONE);
        btnDoi.setVisibility(View.GONE);
        btnHuy.setVisibility(View.GONE);
    }

    private void AnHien() {
        tvAnHien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvAnHien.getText().equals("Hiện")){
                    etPass.setTransformationMethod(null);
                    etPass.setSelection(etPass.length());
                    tvAnHien.setText("Ẩn");
                }else {
                    etPass.setTransformationMethod(new PasswordTransformationMethod());
                    etPass.setSelection(etPass.length());
                    tvAnHien.setText("Hiện");
                }
            }
        });
    }

    private void Hien(){
        etPass.setVisibility(View.VISIBLE);
        tvAnHien.setVisibility(View.VISIBLE);
        btnDoi.setVisibility(View.VISIBLE);
        btnHuy.setVisibility(View.VISIBLE);
    }
}