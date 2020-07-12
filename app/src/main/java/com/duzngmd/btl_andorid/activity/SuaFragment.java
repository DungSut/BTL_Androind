package com.duzngmd.btl_andorid.activity;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.duzngmd.btl_andorid.MainActivity;
import com.duzngmd.btl_andorid.R;
import com.duzngmd.btl_andorid.adapter.SanPhamAdapter;
import com.duzngmd.btl_andorid.intent.ThongTinNguoiDung;
import com.duzngmd.btl_andorid.model.SanPham;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class SuaFragment extends Fragment {
    private static final String[] sanPham = new String[]{"Thực phẩm tươi sống", "Đồ chơi mẹ và bé",
            "Điện thoại - Máy tính bảng", "Làm đẹp sức khỏe", "Điện gia dụng", "Thời trang",
            "Lap top - Máy vi tính", "Nhà cửa đời sống", "Hàng quốc tế", "Bách hóa online",
            "Thiết bị số", "Phương tiện", "Nhà sách", "Điện tử - điện lạnh", "Thể thao giã ngoại",
            "Máy ảnh"};
    AutoCompleteTextView autoTenSanPhamSua;
    ImageView imgSearchSanPhamSua, imgAnhChinhSua, imgAnhPhuSua;
    Button btnSua, btnHuySua;
    TextView etTenSua, etGiaSua, etMotaSua;
    GridView gvlistSua;
    View view;
    int vitri;
    private DatabaseReference reference;
    private DatabaseReference mDatabase;
    ArrayList<SanPham> sanPhamArrayList;
    SanPhamAdapter adapter1;
    int REQUEST_CODE = 123;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        view = inflater.inflate(R.layout.fragment_sua, container, false);
        Reflect();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, sanPham);
        autoTenSanPhamSua.setAdapter(adapter);
        adapter1 = new SanPhamAdapter(getActivity(), R.layout.dong_san_pham, sanPhamArrayList);
        Even();
        gvlistSua.setAdapter(adapter1);
        gvlistSua.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Hien();
                SanPham sanPham1 = sanPhamArrayList.get(position);
                Picasso.get().load(sanPham1.getAnh_chinh()).into(imgAnhChinhSua);
                etTenSua.setText(sanPham1.getTen());
                etGiaSua.setText(String.valueOf(sanPham1.getGia()));
                etMotaSua.setText(sanPham1.getMota());
                Picasso.get().load(sanPham1.getAnh_phu()).into(imgAnhPhuSua);
                gvlistSua.setVisibility(View.GONE);
                vitri = position;
            }
        });
        return view;
    }

    private void Even() {
        imgSearchSanPhamSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loaiSP = autoTenSanPhamSua.getText().toString();
//                Toast.makeText(getActivity(), loaiSP, Toast.LENGTH_SHORT).show();
                mDatabase.child(loaiSP).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot,
                                             @Nullable String previousChildName) {
                        SanPham sanPham = snapshot.getValue(SanPham.class);
                        sanPhamArrayList.add(sanPham);
                        adapter1.notifyDataSetChanged();
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
        });
        btnHuySua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ThongTinNguoiDung.class);
                startActivity(intent);
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loai = autoTenSanPhamSua.getText().toString();
                String tenMoi = etTenSua.getText().toString().trim();
                double giaMoi = Double.parseDouble(etGiaSua.getText().toString().trim());
//                String giaMoi = etGiaSua.getText().toString().trim();
                String motaMoi = etMotaSua.getText().toString().trim();
                if (tenMoi.isEmpty() || String.valueOf(giaMoi).isEmpty() || motaMoi.isEmpty()){
                    Toast.makeText(getActivity(), "Mày phải điền đầy đủ các trường dữ liệu!",
                            Toast.LENGTH_SHORT).show();
                }else {
                    //Xu ly cap nhap.
                    reference = FirebaseDatabase.getInstance().getReference(loai);
                    String nhanh = String.valueOf(vitri);
                    HashMap hashMap = new HashMap();
                    hashMap.put("ten", tenMoi);
                    hashMap.put("gia", giaMoi);
                    hashMap.put("mota", motaMoi);
                    reference.child(nhanh).updateChildren(hashMap)
                            .addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            Toast.makeText(getActivity(), "Cập nhật sản phẩm thành công!",
                                    Toast.LENGTH_SHORT).show();
                            notification();
                            DialogSua();
                        }
                    });
                }
            }
        });
    }

    private void notification(){
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel
                    ("Dung", "Dung", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getActivity().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        Intent intent = new Intent(getActivity(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(),
                REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getActivity(), "Dung")
                .setContentText("Thông báo")
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setAutoCancel(true)
                .setContentText("Dữ liệu được cập nhật thành công")
                .setContentIntent(pendingIntent);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getActivity());
        managerCompat.notify(123, builder.build());

    }

    private void DialogSua(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sua);
        dialog.setCanceledOnTouchOutside(false);

        Button btnCoSua     = (Button) dialog.findViewById(R.id.btnCoSua);
        Button btnKhongSua  = (Button) dialog.findViewById(R.id.btnKhongSua);

        btnCoSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Xu ly
                dialog.dismiss();
                An();
                autoTenSanPhamSua.setText("");
            }
        });
        btnKhongSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(getActivity(), ThongTinNguoiDung.class);
                startActivity(intent);
            }
        });
        dialog.show();
    }

    private void Reflect() {
        btnSua                = (Button)view.findViewById(R.id.btnSua);
        btnHuySua             = (Button)view.findViewById(R.id.btnHuySua);
        autoTenSanPhamSua     = (AutoCompleteTextView)view.findViewById(R.id.autoTenSanPhamSua);
        imgSearchSanPhamSua   = (ImageView)view.findViewById(R.id.imgSearchSanPhamSua);
        imgAnhChinhSua        = (ImageView)view.findViewById(R.id.imgAnhChinhSua);
        imgAnhPhuSua          = (ImageView)view.findViewById(R.id.imgAnhPhuSua);
        etTenSua              = (EditText)view.findViewById(R.id.etTenSua);
        etGiaSua              = (EditText)view.findViewById(R.id.etGiaSua);
        etMotaSua             = (EditText)view.findViewById(R.id.etMotaSua);
        gvlistSua             = (GridView)view.findViewById(R.id.gvlistSua);
        sanPhamArrayList      = new ArrayList<>();
        An();
    }

    private void An(){
        imgAnhChinhSua.setVisibility(View.GONE);
        etTenSua.setVisibility(View.GONE);
        etGiaSua.setVisibility(View.GONE);
        etMotaSua.setVisibility(View.GONE);
        imgAnhPhuSua.setVisibility(View.GONE);
        btnSua.setVisibility(View.GONE);
        btnHuySua.setVisibility(View.GONE);
    }

    private void Hien(){
        imgAnhChinhSua.setVisibility(View.VISIBLE);
        etTenSua.setVisibility(View.VISIBLE);
        etGiaSua.setVisibility(View.VISIBLE);
        etMotaSua.setVisibility(View.VISIBLE);
        imgAnhPhuSua.setVisibility(View.VISIBLE);
        btnSua.setVisibility(View.VISIBLE);
        btnHuySua.setVisibility(View.VISIBLE);
    }
}
