package com.duzngmd.btl_andorid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.duzngmd.btl_andorid.R;
import com.duzngmd.btl_andorid.intent.DangKy;
import com.duzngmd.btl_andorid.intent.DangNhap;
import com.duzngmd.btl_andorid.intent.GioiThieu;
import com.duzngmd.btl_andorid.intent.ThongBao;
import com.duzngmd.btl_andorid.intent.ThongTinNguoiDung;

public class PersonFragment extends Fragment {
    ViewFlipper viewFlipper;
    ImageView imgDangNhap, imgDangKy, imgPerson;
    ImageView imgThongBao, imgGioithieu;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_person, container, false);
        Reflect();
        Flipper();
        Even();
        return view;
    }

    private void Even() {
        imgDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(),
//                        "Mày vừa click vào đăng nhập!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), DangNhap.class);
                startActivity(intent);
            }
        });
        imgDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(),
//                        "Mày vừa click vào đăng ký!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), DangKy.class);
                startActivity(intent);
            }
        });
        imgPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(),
//                        "Mày vừa click vào Person!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ThongTinNguoiDung.class);
                startActivity(intent);
            }
        });
        imgThongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(),
//                        "Mày vừa click vào Thông Báo!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ThongBao.class);
                startActivity(intent);
            }
        });
        imgGioithieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(),
//                        "Mày vừa click vào giới thiệu!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), GioiThieu.class);
                startActivity(intent);
            }
        });
    }

    private void Reflect() {
        viewFlipper  = (ViewFlipper)view.findViewById(R.id.viewFlipper);
        imgDangNhap  = (ImageView)view.findViewById(R.id.imgDangNhap);
        imgDangKy    = (ImageView)view.findViewById(R.id.imgDangKy);
        imgPerson    = (ImageView)view.findViewById(R.id.imgPerson);
        imgThongBao  = (ImageView)view.findViewById(R.id.imgThongBao);
        imgGioithieu = (ImageView)view.findViewById(R.id.imgGioithieu);
    }

    private void flipperImage(int image){
        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundResource(image);
        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(2000);
        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(getContext(), android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(getContext(), android.R.anim.slide_out_right);
    }

    private void Flipper(){
        int images[] = {R.drawable.flip1, R.drawable.flip2, R.drawable.flip3, R.drawable.flip4};
        for (int i =0; i< images.length; i++){
            flipperImage(images[i]);
        }
    }
}
