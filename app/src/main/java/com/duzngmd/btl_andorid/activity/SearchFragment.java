package com.duzngmd.btl_andorid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.duzngmd.btl_andorid.R;
import com.duzngmd.btl_andorid.intent.SanPhamList;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    View view;
    ListView lvSearch;
    Button btnQuatMini, btnKhauTrang, btnGiayNu;
    Button btnDelete, btnBluetooth, btnDonghoThongMinh;
    TextView tvHistory;
    EditText etSearch;
    ImageView imgSearch;
    ArrayList<String> arrayListSearch;
    ArrayAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        Reflect();
        Adapter();
        return view;
    }

    private void Adapter() {
        arrayListSearch = new ArrayList<>();
        adapter = new ArrayAdapter(getActivity(),
                android.R.layout.simple_list_item_1, arrayListSearch);
        lvSearch.setAdapter(adapter);
    }

    private void Reflect() {
        btnQuatMini        = view.findViewById(R.id.btnQuatMini);
        btnKhauTrang       = view.findViewById(R.id.btnKhauTrang);
        btnGiayNu          = view.findViewById(R.id.btnGiayNu);
        btnBluetooth       = view.findViewById(R.id.btnBluetooth);
        btnDonghoThongMinh = view.findViewById(R.id.btnDonghoThongMinh);
        lvSearch           = view.findViewById(R.id.lvSearch);
        btnDelete          = view.findViewById(R.id.btnDelete);
        tvHistory          = view.findViewById(R.id.tvHistory);
        etSearch           = view.findViewById(R.id.etSearch);
        imgSearch          = view.findViewById(R.id.imgSearch);
        AddarrayListSearch();
        tvHistory.setVisibility(View.GONE);
        btnDelete.setVisibility(View.GONE);
    }

    private void getName(String name){
        int dem = 0;
        for (int i = 0; i< arrayListSearch.size(); i++){
            if (name.equalsIgnoreCase(arrayListSearch.get(i))){
                dem = dem + 1;
            }else {
                dem = dem + 0;
            }
        }
        if (dem > 0){
            DeleteHistorySearch();
        }else {
            arrayListSearch.add(name);
            adapter.notifyDataSetChanged();
            DeleteHistorySearch();
        }
    }

    private void DeleteHistorySearch(){
        if (arrayListSearch.size() > 0){
            tvHistory.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    arrayListSearch.clear();
                    tvHistory.setVisibility(View.GONE);
                    btnDelete.setVisibility(View.GONE);
                }
            });
        }
    }

    private void AddarrayListSearch() {
        btnQuatMini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = btnQuatMini.getText().toString();
                getName(ten);
                Intent intent = new Intent(getActivity(), SanPhamList.class);
                intent.putExtra("timsanpham", ten);
                startActivity(intent);
            }
        });
        btnKhauTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = btnKhauTrang.getText().toString();
                getName(ten);
                Intent intent = new Intent(getActivity(), SanPhamList.class);
                intent.putExtra("timsanpham", ten);
                startActivity(intent);
            }
        });
        btnGiayNu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = btnGiayNu.getText().toString();
                getName(ten);
                Intent intent = new Intent(getActivity(), SanPhamList.class);
                intent.putExtra("timsanpham", ten);
                startActivity(intent);
            }
        });
        btnBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = btnBluetooth.getText().toString();
                getName(ten);
                Intent intent = new Intent(getActivity(), SanPhamList.class);
                intent.putExtra("timsanpham", ten);
                startActivity(intent);
            }
        });
        btnDonghoThongMinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = btnDonghoThongMinh.getText().toString();
                getName(ten);
                Intent intent = new Intent(getActivity(), SanPhamList.class);
                intent.putExtra("timsanpham", ten);
                startActivity(intent);
            }
        });
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = etSearch.getText().toString().trim();
                getName(ten);
                if (ten.isEmpty()){
                    Toast.makeText(getActivity(),
                            "Mày phải nhập từ khóa cần tìm!", Toast.LENGTH_SHORT).show();
                }else {
//                    Toast.makeText(getActivity(),
//                            "Mày vừa click vào tìm kiếm!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), SanPhamList.class);
                    intent.putExtra("timsanpham", ten);
                    startActivity(intent);
                    etSearch.setText("");
                }
            }
        });
        lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                etSearch.setText(arrayListSearch.get(position));
            }
        });
    }
}
