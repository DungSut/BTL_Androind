package com.duzngmd.btl_andorid.intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.duzngmd.btl_andorid.MainActivity;
import com.duzngmd.btl_andorid.R;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


public class DangNhap extends AppCompatActivity {

    EditText etName, etPass;
    TextView tvAnHien;
    CheckBox checkBox;
    Button btnDangNhap;
    FirebaseUser user;
//    SignInButton imgGoogle;
//    LoginButton imgFaceBook;
    SharedPreferences sharedPreferences;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager mCallbackManager;
    private AccessTokenTracker accessTokenTracker;
    int CODE_LOGIN_GG = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);
        Reflect();
        AnHien();
        Even();
        getData();
    }

    private void getData(){
        etName.setText(sharedPreferences.getString("email", ""));
        etPass.setText(sharedPreferences.getString("matkhau",""));
        checkBox.setChecked(sharedPreferences.getBoolean("check", false));
    }

    private void Even() {
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(DangNhap.this,
//                        "Mày vừa click vào button Đăng nhập!", Toast.LENGTH_SHORT).show();
                String name = etName.getText().toString().trim();
                String pass = etPass.getText().toString().trim();
                if (name.isEmpty()){
                    if (pass.isEmpty()){
                        Toast.makeText(DangNhap.this,
                                "Vui lòng nhập đầy đủ 2 trường!", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(DangNhap.this,
                                "Vui lòng nhập trường email!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    if (pass.isEmpty()){
                        Toast.makeText(DangNhap.this,
                                "Vui lòng nhập trường mật khẩu!", Toast.LENGTH_SHORT).show();
                    }else {
                        DangNhap();
                    }
                }
            }
        });
//        imgFaceBook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Toast.makeText(DangNhap.this,
////                        "Mày vừa click vào image Facebook!", Toast.LENGTH_SHORT).show();
//                FacebookSdk.sdkInitialize(getApplicationContext());
//                mCallbackManager = CallbackManager.Factory.create();
//                imgFaceBook.setReadPermissions("email", "public_profile");
//                imgFaceBook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
//                    @Override
//                    public void onSuccess(LoginResult loginResult) {
//                        handleFacebookToken(loginResult.getAccessToken());
//                    }
//
//                    @Override
//                    public void onCancel() {
//
//                    }
//
//                    @Override
//                    public void onError(FacebookException error) {
//
//                    }
//                });
//                authStateListener = new FirebaseAuth.AuthStateListener() {
//                    @Override
//                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                        FirebaseUser user = firebaseAuth.getCurrentUser();
//                        if (user != null){
//                            updateUII(user);
//                        }else {
//                            updateUII(null);
//                        }
//                    }
//                };
//                accessTokenTracker = new AccessTokenTracker() {
//                    @Override
//                    protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
//                                                               AccessToken currentAccessToken) {
//                        if (currentAccessToken == null){
//                            mAuth.signOut();
//                        }
//                    }
//                };
//            }
//        });
//        imgGoogle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Toast.makeText(DangNhap.this,
////                        "Mày vừa click vào image Google!", Toast.LENGTH_SHORT).show();
////                 Configure Google Sign In
//                GoogleSignInOptions gso = new GoogleSignInOptions.Builder
//                        (GoogleSignInOptions.DEFAULT_SIGN_IN)
//                        .requestIdToken(getString(R.string.default_web_client_id))
//                        .requestEmail()
//                        .build();
//                mGoogleSignInClient = GoogleSignIn.getClient(DangNhap.this, gso);
//                signIn();
//            }
//        });
    }


//    private void handleFacebookToken(AccessToken token){
//        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
//        mAuth.signInWithCredential(credential).addOnCompleteListener(this,
//                new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()){
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUII(user);
//                        }else {
//                            updateUII(null);
//                        }
//                    }
//                });
//    }

//    private void updateUII(FirebaseUser user){
//        if (user != null){
//
//        }else {
//
//        }
//    }

//    private void signIn(){
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, CODE_LOGIN_GG);
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        mCallbackManager.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == CODE_LOGIN_GG){
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            handleSignInResult(task);
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }

//    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
//        try {
//            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
//            Toast.makeText(this,
//                    "Đăng nhập bằng Google thành công!", Toast.LENGTH_SHORT).show();
//            FirebaseGoogleAuth(acc);
//        }catch (ApiException e){
//            Toast.makeText(this,
//                    "Đăng nhập bằng Google thất bại!", Toast.LENGTH_SHORT).show();
//            FirebaseGoogleAuth(null);
//        }
//    }

//    private void FirebaseGoogleAuth(GoogleSignInAccount acct){
//        AuthCredential authCredential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
//        mAuth.signInWithCredential(authCredential).addOnCompleteListener
//                (this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()){
//                    FirebaseUser user = mAuth.getCurrentUser();
//                    updateUI(user);
//                }else {
//                    updateUI(null);
//                }
//            }
//        });
//    }

//    private void updateUI(FirebaseUser fUser){
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
//        if (account != null){
//            String personEmail = account.getEmail();
//            Toast.makeText(this, personEmail, Toast.LENGTH_SHORT).show();
//        }
//    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        mAuth.addAuthStateListener(authStateListener);
//    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (authStateListener != null){
//            mAuth.removeAuthStateListener(authStateListener);
//        }
//    }

    private void DangNhap(){
        final String email = etName.getText().toString().trim();
        final String password = etPass.getText().toString().trim();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(DangNhap.this,
                                    "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent();
//                            intent.putExtra("guiemail", user.getEmail());
//                            setResult(RESULT_OK, intent);
//                            finish();
                            Intent intent = new Intent(DangNhap.this, MainActivity.class);
                            startActivity(intent);
                            if (checkBox.isChecked()){
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("email", email);
                                editor.putString("matkhau", password);
                                editor.putBoolean("check", true);
                                editor.commit();
                            }else {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.remove("email");
                                editor.remove("matkhau");
                                editor.remove("check");
                                editor.commit();
                            }
                        } else {
                            Toast.makeText(DangNhap.this,
                                    "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
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
        etName      = (EditText) findViewById(R.id.etName);
        etPass      = (EditText) findViewById(R.id.etPass);
        tvAnHien    = (TextView) findViewById(R.id.tvAnHien);
        btnDangNhap = (Button) findViewById(R.id.btnDangNhap);
//        imgFaceBook = (LoginButton) findViewById(R.id.imgFaceBook);
//        imgGoogle   = (SignInButton) findViewById(R.id.imgGoogle);
        checkBox    = (CheckBox) findViewById(R.id.checkBox);
    }

}