package com.duzngmd.btl_andorid.activity;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.duzngmd.btl_andorid.MainActivity;
import com.duzngmd.btl_andorid.R;
import com.duzngmd.btl_andorid.intent.ThongTinNguoiDung;
import com.duzngmd.btl_andorid.model.SanPham;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class ThemFragment extends Fragment {
    private static final String[] sanPham = new String[]{"Thực phẩm tươi sống", "Đồ chơi mẹ và bé",
            "Điện thoại - Máy tính bảng", "Làm đẹp sức khỏe", "Điện gia dụng", "Thời trang",
            "Lap top - Máy vi tính", "Nhà cửa đời sống", "Hàng quốc tế", "Bách hóa online",
            "Thiết bị số", "Phương tiện", "Nhà sách", "Điện tử - điện lạnh", "Thể thao giã ngoại",
            "Máy ảnh"};
    ImageView imgAnhChinhThem, imgAnhPhuThem;
    Button btnChonAnhChinh, btnChupAnhChinh, btnChonAnhPhu, btnChupAnhPhu, btnThem, btnHuyThem;
    EditText etTenThem, etGiaThem, etMotaThem;
    AutoCompleteTextView autoTenSanPhamThem;
    int REQUEST_CHON_ANH_CHINH = 1;
    int REQUEST_CHUP_ANH_CHINH = 11;
    int REQUEST_CHON_ANH_PHU = 2;
    int REQUEST_CHUP_ANH_PHU = 22;
    int REQUEST_CODE = 123;
    Uri uriImageAnhChinh, uriImageAnhPhu;
    public static String loai, anh_chinh, anh_phu;
    StorageReference storage;
    DatabaseReference data;

    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        storage = FirebaseStorage.getInstance().getReference();
        view = inflater.inflate(R.layout.fragment_them, container, false);

        Reflect();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, sanPham);
        autoTenSanPhamThem.setAdapter(adapter);
        Even();
        return view;
    }

    private void Even() {
        btnChonAnhChinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //xu ly chon anh chinh
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CHON_ANH_CHINH);
            }
        });

        btnChupAnhChinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //xu ly chup anh chinh
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CHUP_ANH_CHINH);
            }
        });

        btnChonAnhPhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //xu ly chon anh phu
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CHON_ANH_PHU);
            }
        });

        btnChupAnhPhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //xu ly chup anh phu
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CHUP_ANH_PHU);
            }
        });

        btnHuyThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ThongTinNguoiDung.class);
                startActivity(intent);
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Xu ly them
                String auto = autoTenSanPhamThem.getText().toString().trim();
                String ten = etTenThem.getText().toString().trim();
                String gia = etGiaThem.getText().toString().trim();
                String mota = etMotaThem.getText().toString().trim();
                if (auto.isEmpty() || ten.isEmpty() || gia.isEmpty() || mota.isEmpty()){
                    Toast.makeText(getActivity(), "Mày phải nhập đủ thông tin trước khi thêm!",
                            Toast.LENGTH_SHORT).show();
                }else {
                    loai = auto;
                    data = FirebaseDatabase.getInstance().getReference();
                    uploadAnhChinh();
                    uploadAnhPhu();
                    if(anh_chinh != null && anh_phu != null) {
                        SanPham sanPham = new SanPham(ten, anh_chinh, Double.parseDouble(gia), anh_phu, mota);
                        data.child(loai).push().setValue(sanPham, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error,
                                                   @NonNull DatabaseReference ref) {
                                if (error == null){
                                    Toast.makeText(getActivity(), "Thêm sản phẩm thành công!",
                                            Toast.LENGTH_SHORT).show();
                                    notification();
                                    DialogThem();
                                }else {
                                    Toast.makeText(getActivity(), "Thêm sản phẩm thất bại!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

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
                        .setContentText("Dữ liệu được thêm thành công")
                        .setContentIntent(pendingIntent);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getActivity());
        managerCompat.notify(123, builder.build());
    }

    private void uploadAnhChinh(){
        if (uriImageAnhChinh != null){
            final StorageReference fileReference = storage.child("image" + System.currentTimeMillis()
                    + "." + getFileExtension(uriImageAnhChinh));
            UploadTask uploadTask = fileReference.putFile(uriImageAnhChinh);
            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot,
                    Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
//                        Toast.makeText(getActivity(), "Tải ảnh chính thành công!",
//                                Toast.LENGTH_SHORT).show();
                        Uri downloadUri = task.getResult();
                        anh_chinh = downloadUri.toString();
                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });
        }else {
            Toast.makeText(getActivity(), "Không có ảnh nào được chọn!", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadAnhPhu(){
        if (uriImageAnhPhu != null){
            final StorageReference fileReference = storage.child("image" + System.currentTimeMillis()
                    + "." + getFileExtension(uriImageAnhChinh));
            UploadTask uploadTask = fileReference.putFile(uriImageAnhChinh);
            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot,
                    Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
//                        Toast.makeText(getActivity(), "Tải ảnh phụ thành công!",
//                                Toast.LENGTH_SHORT).show();
                        Uri downloadUri = task.getResult();
                        anh_phu = downloadUri.toString();
                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });
        }else {
            Toast.makeText(getActivity(), "Không có ảnh nào được chọn!", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mine = MimeTypeMap.getSingleton();
        return mine.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void ThemAnhChinh(){
//        StorageReference storageRef = storage.getReference();
//
//        Calendar calendar = Calendar.getInstance();
//        String tenHinh = "image" + calendar.getTimeInMillis() + ".png";
//        final StorageReference mountainsRef = storageRef.child(tenHinh);
//
//        // Get the data from an ImageView as bytes
//        imgAnhChinhThem.setDrawingCacheEnabled(true);
//        imgAnhChinhThem.buildDrawingCache();
//        Bitmap bitmap = ((BitmapDrawable) imgAnhChinhThem.getDrawable()).getBitmap();
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        byte[] data = baos.toByteArray();
//
//        UploadTask uploadTask = mountainsRef.putBytes(data);
//        uploadTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle unsuccessful uploads
//                Toast.makeText(getActivity(), "Lưu hình lỗi!", Toast.LENGTH_SHORT).show();
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
//                // ...
//                Toast.makeText(getActivity(), "Lưu hình thành công", Toast.LENGTH_SHORT).show();
//            }
//        });

//        Uri file = Uri.fromFile(new File("path/to/images/" + tenHinh));
//        uploadTask = mountainsRef.putFile(file);
//        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//            @Override
//            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                if (!task.isSuccessful()) {
//                    throw task.getException();
//                }
//
//                // Continue with the task to get the download URL
//                return mountainsRef.getDownloadUrl();
//            }
//        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//            @Override
//            public void onComplete(@NonNull Task<Uri> task) {
//                if (task.isSuccessful()) {
//                    Uri downloadUri = task.getResult();
//                    Log.d("AAA", downloadUri + "");
//                } else {
//                    // Handle failures
//                    // ...
//                }
//            }
//        });
    }

    private void DialogThem(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_them);
        dialog.setCanceledOnTouchOutside(false);

        Button btnCoThem     = (Button) dialog.findViewById(R.id.btnCoThem);
        Button btnKhongThem  = (Button) dialog.findViewById(R.id.btnKhongThem);

        btnCoThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Xu ly
                dialog.dismiss();
                imgAnhChinhThem.setImageResource(R.drawable.no_image);
                etTenThem.setText("");
                etGiaThem.setText("");
                etMotaThem.setText("");
                imgAnhPhuThem.setImageResource(R.drawable.no_image);
            }
        });
        btnKhongThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(getActivity(), ThongTinNguoiDung.class);
                startActivity(intent);
            }
        });
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == REQUEST_CHON_ANH_CHINH && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            uriImageAnhChinh = data.getData();
            Picasso.get().load(uriImageAnhChinh).into(imgAnhChinhThem);
//            imgAnhChinhThem.setImageURI(uriImageAnhChinh);
        }

        if (requestCode == REQUEST_CHUP_ANH_CHINH && resultCode == RESULT_OK
                && data != null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgAnhChinhThem.setImageBitmap(bitmap);

        }

        if (requestCode == REQUEST_CHON_ANH_PHU && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            uriImageAnhPhu = data.getData();
            Picasso.get().load(uriImageAnhPhu).into(imgAnhPhuThem);
//            imgAnhPhuThem.setImageURI(uriImageAnhPhu);
        }

        if (requestCode == REQUEST_CHUP_ANH_PHU && resultCode == RESULT_OK
                && data != null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgAnhPhuThem.setImageBitmap(bitmap);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    String currentPhotoPath;

    private void Reflect() {
        imgAnhChinhThem     = (ImageView)view.findViewById(R.id.imgAnhChinhThem);
        imgAnhPhuThem       = (ImageView)view.findViewById(R.id.imgAnhPhuThem);
        btnChonAnhChinh     = (Button)view.findViewById(R.id.btnChonAnhChinh);
        btnChupAnhChinh     = (Button)view.findViewById(R.id.btnChupAnhChinh);
        btnChonAnhPhu       = (Button)view.findViewById(R.id.btnChonAnhPhu);
        btnChupAnhPhu       = (Button)view.findViewById(R.id.btnChupAnhPhu);
        btnThem             = (Button)view.findViewById(R.id.btnThem);
        btnHuyThem          = (Button)view.findViewById(R.id.btnHuyThem);
        etTenThem           = (EditText)view.findViewById(R.id.etTenThem);
        etGiaThem           = (EditText)view.findViewById(R.id.etGiaThem);
        etMotaThem          = (EditText)view.findViewById(R.id.etMotaThem);
        autoTenSanPhamThem  = (AutoCompleteTextView)view.findViewById(R.id.autoTenSanPhamThem);
    }
}
