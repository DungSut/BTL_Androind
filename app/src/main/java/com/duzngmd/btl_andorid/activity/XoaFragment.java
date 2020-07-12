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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class XoaFragment extends Fragment {
    private static final String[] sanPham = new String[]{"Thực phẩm tươi sống", "Đồ chơi mẹ và bé",
            "Điện thoại - Máy tính bảng", "Làm đẹp sức khỏe", "Điện gia dụng", "Thời trang",
            "Lap top - Máy vi tính", "Nhà cửa đời sống", "Hàng quốc tế", "Bách hóa online",
            "Thiết bị số", "Phương tiện", "Nhà sách", "Điện tử - điện lạnh", "Thể thao giã ngoại",
            "Máy ảnh"};
    AutoCompleteTextView autoTenSanPhamXoa;
    ImageView imgSearchSanPhamXoa, imgAnhChinhXoa, imgAnhPhuXoa;
    TextView tvTenSanPhamXoa, tvGiaSanPhamXoa, tvMotaSanPhamXoa;
    Button btnXoa, btnHuyXoa;
    GridView gvlistXoa;
    View view;
    private DatabaseReference mDatabase;
    private FirebaseStorage storage;
    private DatabaseReference reference;
    ArrayList<SanPham> sanPhamArrayList;
    SanPhamAdapter adapter1;
    int vitri;
    int REQUEST_CODE = 123;
    SanPham sanPhamDelete;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        view = inflater.inflate(R.layout.fragment_xoa, container, false);
        Reflect();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, sanPham);
        autoTenSanPhamXoa.setAdapter(adapter);
        adapter1 = new SanPhamAdapter(getActivity(), R.layout.dong_san_pham, sanPhamArrayList);
        Even();
        gvlistXoa.setAdapter(adapter1);
        gvlistXoa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Hien();
                SanPham sanPham1 = sanPhamArrayList.get(position);
                Picasso.get().load(sanPham1.getAnh_chinh()).into(imgAnhChinhXoa);
                tvTenSanPhamXoa.setText(sanPham1.getTen());
                tvGiaSanPhamXoa.setText(String.valueOf(sanPham1.getGia()));
                tvMotaSanPhamXoa.setText(sanPham1.getMota());
                Picasso.get().load(sanPham1.getAnh_phu()).into(imgAnhPhuXoa);
                gvlistXoa.setVisibility(View.GONE);
                vitri = position;
                sanPhamDelete = sanPham1;
            }
        });
        return view;
    }

    private void Even(){
        imgSearchSanPhamXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loaiSP = autoTenSanPhamXoa.getText().toString();
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
        btnHuyXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ThongTinNguoiDung.class);
                startActivity(intent);
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Xu ly xoa
                String loai = autoTenSanPhamXoa.getText().toString();
                reference = FirebaseDatabase.getInstance().getReference(loai);
                final String nhanh = String.valueOf(vitri);
                StorageReference image_Chinh = storage.getReferenceFromUrl(sanPhamDelete.getAnh_chinh());
                final StorageReference image_Phu = storage.getReferenceFromUrl(sanPhamDelete.getAnh_phu());
                image_Chinh.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        image_Phu.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                reference.child(nhanh).removeValue();
                                Toast.makeText(getActivity(), "Xóa sản phẩm thành công!",
                                        Toast.LENGTH_SHORT).show();
                                DialogXoa();
                                notification();
                            }
                        });
                    }
                });
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
                        .setContentText("Dữ liệu đã xóa thành công")
                        .setContentIntent(pendingIntent);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getActivity());
        managerCompat.notify(123, builder.build());
    }

    private void DialogXoa(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_xoa);
        dialog.setCanceledOnTouchOutside(false);

        Button btnCoXoa     = (Button) dialog.findViewById(R.id.btnCoXoa);
        Button btnKhongXoa  = (Button) dialog.findViewById(R.id.btnKhongXoa);

        btnCoXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Xu ly
                dialog.dismiss();
                An();
                autoTenSanPhamXoa.setText("");
            }
        });
        btnKhongXoa.setOnClickListener(new View.OnClickListener() {
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
        autoTenSanPhamXoa   = (AutoCompleteTextView)view.findViewById(R.id.autoTenSanPhamXoa);
        imgSearchSanPhamXoa = (ImageView)view.findViewById(R.id.imgSearchSanPhamXoa);
        imgAnhChinhXoa      = (ImageView)view.findViewById(R.id.imgAnhChinhXoa);
        imgAnhPhuXoa        = (ImageView)view.findViewById(R.id.imgAnhPhuXoa);
        tvTenSanPhamXoa     = (TextView)view.findViewById(R.id.tvTenSanPhamXoa);
        tvGiaSanPhamXoa     = (TextView)view.findViewById(R.id.tvGiaSanPhamXoa);
        tvMotaSanPhamXoa    = (TextView)view.findViewById(R.id.tvMotaSanPhamXoa);
        btnXoa              = (Button)view.findViewById(R.id.btnXoa);
        btnHuyXoa           = (Button)view.findViewById(R.id.btnHuyXoa);
        gvlistXoa           = (GridView)view.findViewById(R.id.gvlistXoa);
        An();
        sanPhamArrayList = new ArrayList<>();
    }

    private void An(){
        imgAnhChinhXoa.setVisibility(View.GONE);
        tvTenSanPhamXoa.setVisibility(View.GONE);
        tvGiaSanPhamXoa.setVisibility(View.GONE);
        tvMotaSanPhamXoa.setVisibility(View.GONE);
        imgAnhPhuXoa.setVisibility(View.GONE);
        btnXoa.setVisibility(View.GONE);
        btnHuyXoa.setVisibility(View.GONE);
    }

    private void Hien(){
        imgAnhChinhXoa.setVisibility(View.VISIBLE);
        tvTenSanPhamXoa.setVisibility(View.VISIBLE);
        tvGiaSanPhamXoa.setVisibility(View.VISIBLE);
        tvMotaSanPhamXoa.setVisibility(View.VISIBLE);
        imgAnhPhuXoa.setVisibility(View.VISIBLE);
        btnXoa.setVisibility(View.VISIBLE);
        btnHuyXoa.setVisibility(View.VISIBLE);
    }
}
