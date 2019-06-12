package com.example.ensisar;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.Plane;
import com.google.ar.core.Session;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Trex extends AppCompatActivity {

    private ModelRenderable mTyrexRederable;
    private ArFragment mPlayAreaFragment;
    private Session mSession;
    private ScheduledExecutorService schedule= Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture schedulerHandler;
    private Anchor anchor;
    private AnchorNode anchorNode;
    private Node tyrex;
    private int hit=0;
    private int miss=0;
    private TextView hitTextView;
    private TextView missTextView;
    private String missText="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trex);
        Toast.makeText(getApplicationContext(), "Attrape tous les Trex", Toast.LENGTH_LONG).show();
        hitTextView = findViewById(R.id.hitText);
        missTextView = findViewById(R.id.missText);

        mPlayAreaFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.playAreaFragment);
        Log.d("Play Area",mPlayAreaFragment.getArSceneView().getScene().toString());
        buildTyrexRenderable();

        new Handler().postDelayed(this::generateTyrex, 10000);
        schedulerHandler=schedule.scheduleAtFixedRate(() -> runOnUiThread(() -> placeTyrexAtRandom()),3,3, TimeUnit.SECONDS);
    }

    public void generateTyrex() {
        anchor = null;
        mSession = mPlayAreaFragment.getArSceneView().getSession();
        for(Plane plane: mSession.getAllTrackables(Plane.class)){
            if(plane.getType()==Plane.Type.HORIZONTAL_UPWARD_FACING
                    && plane.getTrackingState()== TrackingState.TRACKING){
                anchor=plane.createAnchor(plane.getCenterPose());
                Log.d("Plane anchor",plane.toString());
                break;
            }
        }

        anchorNode = new AnchorNode(anchor);
        anchorNode.setParent(mPlayAreaFragment.getArSceneView().getScene());
        tyrex = new Node();
        tyrex.setParent(anchorNode);
        tyrex.setRenderable(mTyrexRederable);
        tyrex.setLocalScale(new Vector3(0.13f, 0.13f, 0.13f));
        tyrex.setLocalPosition(new Vector3(0f,0,0f));
        tyrex.setOnTapListener((hitTestResult ,motionEvent) -> {
            tyrex.setEnabled(false);
            hit++;
            String hitText = String.valueOf(hit);
            hitTextView.setText(hitText);
        });
    }

    public void buildTyrexRenderable() {
        ModelRenderable.builder()
                .setSource(this, Uri.parse("Trex.sfb"))
                .build()
                .thenAccept(modelRenderable -> mTyrexRederable = modelRenderable);
    }
    public void placeTyrexAtRandom(){
        if(tyrex!=null){
            if(tyrex.isEnabled()){
                miss++;
                missText = String.valueOf(miss);
                missTextView.setText(missText);
            }
            tyrex.setEnabled(true);
            tyrex.setLocalPosition(new Vector3(randFloat(-0.3f,0.5f),0,randFloat(-0.5f,0.5f)));
        }
    }

    public static float randFloat(float min, float max) {
        Random rand = new Random();
        float result = rand.nextFloat() * (max - min) + min;
        return result;
    }

}

