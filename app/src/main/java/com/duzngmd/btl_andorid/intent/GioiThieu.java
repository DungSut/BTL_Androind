package com.duzngmd.btl_andorid.intent;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.duzngmd.btl_andorid.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GioiThieu extends AppCompatActivity implements OnMapReadyCallback {

    VideoView videoView;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gioi_thieu);

        Reflect();
        playVideo();
    }

    private void Reflect(){
        videoView = (VideoView)findViewById(R.id.video_view);
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.myMap);
        mapFragment.getMapAsync(this);
    }

    private void playVideo() {
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.tiki;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng tiKi = new LatLng(-34, 151);
        map.addMarker(new MarkerOptions().position(tiKi).title("Trụ sở chính Tiki"));
        map.moveCamera(CameraUpdateFactory.newLatLng(tiKi));
    }
}