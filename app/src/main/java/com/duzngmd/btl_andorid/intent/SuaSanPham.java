package com.duzngmd.btl_andorid.intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.duzngmd.btl_andorid.R;
import com.duzngmd.btl_andorid.activity.SuaFragment;
import com.duzngmd.btl_andorid.activity.ThemFragment;
import com.duzngmd.btl_andorid.activity.XoaFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SuaSanPham extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_san_pham);

        BottomNavigationView bottomNavigationView = findViewById(R.id.sua_san_pham);
        bottomNavigationView.setOnNavigationItemSelectedListener(nav);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_san_pham, new ThemFragment()).commit();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_sanpham);
        setSupportActionBar(toolbar);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener nav =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment fragment = null;
                    switch (item.getItemId()){
                        case R.id.them:{
                            fragment = new ThemFragment();
                            break;
                        }
                        case R.id.sua:{
                            fragment = new SuaFragment();
                            break;
                        }
                        case R.id.xoa:{
                            fragment = new XoaFragment();
                            break;
                        }
                    }
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_san_pham, fragment).commit();
                    return true;
                }
            };
}