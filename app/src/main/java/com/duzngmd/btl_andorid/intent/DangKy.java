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

import com.duzngmd.btl_andorid.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DangKy extends AppCompatActivity {

    TextView tvAnHien;
    EditText etPass, etEmailDK;
    Button btnDangKy;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        Reflect();
        AnHien();
        Even();
    }

    private void Even() {
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(DangKy.this,
//                        "Mày vừa click vào đăng ký!", Toast.LENGTH_SHORT).show();
                String email = etEmailDK.getText().toString().trim();
                String pass = etPass.getText().toString().trim();
                if (email.isEmpty()){
                    if (pass.isEmpty()){
                        Toast.makeText(DangKy.this,
                                "Vui lòng nhập đủ 2 trường!", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(DangKy.this,
                                "Vui lòng nhập trường Email!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    if (pass.isEmpty()){
                        Toast.makeText(DangKy.this,
                                "Vui lòng nhập trường mật khẩu!", Toast.LENGTH_SHORT).show();
                    }else {
                        DangKy();
                        Intent intent = new Intent(DangKy.this, DangNhap.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private void DangKy(){
        String email = etEmailDK.getText().toString().trim();
        String password = etPass.getText().toString().trim();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(DangKy.this,
                                    "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DangKy.this,
                                    "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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

    private void Reflect() {
        tvAnHien  = (TextView) findViewById(R.id.tvAnHien);
        etPass    = (EditText) findViewById(R.id.etPass);
        etEmailDK = (EditText) findViewById(R.id.etEmailDK);
        btnDangKy = (Button) findViewById(R.id.btnDangKy);
    }
}