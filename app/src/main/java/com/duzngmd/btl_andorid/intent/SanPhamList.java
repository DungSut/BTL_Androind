package com.duzngmd.btl_andorid.intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;

import com.duzngmd.btl_andorid.R;
import com.duzngmd.btl_andorid.adapter.SanPhamAdapter;
import com.duzngmd.btl_andorid.model.SanPham;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SanPhamList extends AppCompatActivity {

    private DatabaseReference mDatabase;
    GridView gvsanpham;
    ArrayList<SanPham> mangSanPham;
    ArrayList<String> tim;
    SanPhamAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham_list);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        Reflect();
        adapter = new SanPhamAdapter(this, R.layout.dong_san_pham, mangSanPham);
        LoadData();
        gvsanpham.setAdapter(adapter);
        gvsanpham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SanPhamList.this, SanPhamDetail.class);
                intent.putExtra("sanphamdetail", mangSanPham.get(position));
                startActivity(intent);
//               Toast.makeText(SanPhamList.this, mangSanPham.get(position).getTen(),
//                       Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void LoadData(){
        final Intent intent = getIntent();
        String name = intent.getStringExtra("sanpham");
        if (name == null){
            final String ten = intent.getStringExtra("timsanpham");
//            Toast.makeText(this, ten, Toast.LENGTH_SHORT).show();
            for (int i = 0; i< tim.size(); i++){
                mDatabase.child(tim.get(i)).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot,
                                             @Nullable String previousChildName) {
                        SanPham sanPham = snapshot.getValue(SanPham.class);
                        if (ten.equalsIgnoreCase(sanPham.getTen())){
                            mangSanPham.add(sanPham);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot,
                                               @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot,
                                             @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }else {
            mDatabase.child(name).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot,
                                         @Nullable String previousChildName) {
                    SanPham sanPham = snapshot.getValue(SanPham.class);
                    mangSanPham.add(new SanPham(sanPham.getTen(), sanPham.getAnh_chinh(), sanPham.getGia(),
                            sanPham.getAnh_phu(), sanPham.getMota()));
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot,
                                           @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot,
                                         @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void Reflect() {
        gvsanpham   = (GridView) findViewById(R.id.gvsanpham);
        mangSanPham = new ArrayList<>();
        tim = new ArrayList<>();
        tim.add("Thực phẩm tươi sống");
        tim.add("Đồ chơi mẹ và bé");
        tim.add("Điện thoại - Máy tính bảng");
        tim.add("Làm đẹp sức khỏe");
        tim.add("Điện gia dụng");
        tim.add("Thời trang");
        tim.add("Lap top - Máy vi tính");
        tim.add("Nhà cửa đời sống");
        tim.add("Hàng quốc tế");
        tim.add("Bách hóa online");
        tim.add("Thiết bị số");
        tim.add("Phương tiện");
        tim.add("Nhà sách");
        tim.add("Điện tử - điện lạnh");
        tim.add("Thể thao giã ngoại");
        tim.add("Máy ảnh");
    }

}