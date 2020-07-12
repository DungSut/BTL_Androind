package com.duzngmd.btl_andorid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.duzngmd.btl_andorid.R;
import com.duzngmd.btl_andorid.adapter.TrangChuAdaper;
import com.duzngmd.btl_andorid.intent.SanPhamList;
import com.duzngmd.btl_andorid.model.DanhMucTrangChu;
import com.duzngmd.btl_andorid.model.SanPham;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    View view;
    ListView lvlist;
    GridView gvlist;
    ArrayList<DanhMucTrangChu> trangChuArrayList;
    TrangChuAdaper adaper;
    private DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Reflect();
        AddTrangChu();
        Adapter();
        Even();
        return view;
    }

    private void Even() {
        gvlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name =trangChuArrayList.get(position).getName();
//                Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), SanPhamList.class);
                intent.putExtra("sanpham", name);
                startActivity(intent);
            }
        });
    }

    private void AddTrangChu() {
        trangChuArrayList = new ArrayList<>();
        trangChuArrayList.add(
                new DanhMucTrangChu("Thực phẩm tươi sống", R.drawable.thuc_pham_tuoi_song));
        trangChuArrayList.add(new DanhMucTrangChu(
                "Đồ chơi mẹ và bé", R.drawable.do_choi_me_va_be));
        trangChuArrayList.add(new DanhMucTrangChu(
                "Điện thoại - Máy tính bảng", R.drawable.dien_thoai_may_tinh_bang));
        trangChuArrayList.add(new DanhMucTrangChu(
                "Làm đẹp sức khỏe", R.drawable.lam_dep_suc_khoe));
        trangChuArrayList.add(new DanhMucTrangChu(
                "Điện gia dụng", R.drawable.dien_gia_dung));
        trangChuArrayList.add(new DanhMucTrangChu(
                "Thời trang", R.drawable.thoi_trang));
        trangChuArrayList.add(new DanhMucTrangChu(
                "Lap top - Máy vi tính", R.drawable.lap_top_may_vi_tinh));
        trangChuArrayList.add(new DanhMucTrangChu(
                "Nhà cửa đời sống", R.drawable.nha_cua_doi_song));
        trangChuArrayList.add(new DanhMucTrangChu(
                "Hàng quốc tế", R.drawable.hang_quoc_te));
        trangChuArrayList.add(new DanhMucTrangChu(
                "Bách hóa online", R.drawable.bach_hoa_online));
        trangChuArrayList.add(new DanhMucTrangChu(
                "Thiết bị số", R.drawable.thiet_bi_so));
        trangChuArrayList.add(new DanhMucTrangChu(
                "Phương tiện", R.drawable.oto_xemay_xedap));
        trangChuArrayList.add(new DanhMucTrangChu(
                "Nhà sách", R.drawable.nha_sach));
        trangChuArrayList.add(new DanhMucTrangChu(
                "Điện tử - điện lạnh", R.drawable.dien_tu_dien_lanh));
        trangChuArrayList.add(new DanhMucTrangChu(
                "Thể thao giã ngoại", R.drawable.the_thao_da_ngoai));
        trangChuArrayList.add(new DanhMucTrangChu(
                "Máy ảnh", R.drawable.may_anh_may_quay_phim));
    }

    private void Adapter() {
        adaper = new TrangChuAdaper(getActivity(), R.layout.dong_trang_chu, trangChuArrayList);
        lvlist.setAdapter(adaper);
        gvlist.setAdapter(adaper);
    }

    private void Reflect() {
        lvlist = (ListView)view.findViewById(R.id.lvlist);
        gvlist = (GridView)view.findViewById(R.id.gvlist);
    }
}
