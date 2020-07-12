package com.duzngmd.btl_andorid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.duzngmd.btl_andorid.activity.HomeFragment;
import com.duzngmd.btl_andorid.activity.PersonFragment;
import com.duzngmd.btl_andorid.activity.SearchFragment;
import com.duzngmd.btl_andorid.intent.GioHang;
import com.duzngmd.btl_andorid.model.SanPham;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<SanPham> gioHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().
                replace(R.id.fragment_container, new HomeFragment()).commit();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView imgCart;
        imgCart = (ImageView) findViewById(R.id.imgCart);
        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this,
//                        "Mày vừa click vào giỏ hàng!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, GioHang.class);
                startActivity(intent);
            }
        });
        if (gioHang != null){
            //khong cap phat mang moi
        }else {
            //cap phat mang moi
            gioHang = new ArrayList<>();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()){
                        case R.id.nav_home:{
                            selectedFragment = new HomeFragment();
                            break;
                        }
                        case R.id.nav_search:{
                            selectedFragment = new SearchFragment();
                            break;
                        }
                        case R.id.nav_person:{
                            selectedFragment = new PersonFragment();
                            break;
                        }
                    }
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };
}