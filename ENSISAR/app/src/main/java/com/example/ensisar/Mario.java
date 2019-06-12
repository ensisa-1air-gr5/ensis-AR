package com.example.ensisar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;

public class Mario extends AppCompatActivity {

    private ViewRenderable MarioRederable;
    private ArFragment arFragment;
    private Anchor anchor;
    private AnchorNode anchorNode;
    private Node mario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mario);
        Toast.makeText(this, "Trouvez le plombier le plus connus", Toast.LENGTH_LONG).show();
        arFragment = (ArFragment)getSupportFragmentManager().findFragmentById(R.id.ux_fragment);
        buildtextRenderable();
        new Handler().postDelayed(this::generateMario,10000);


    }

    public void generateMario() {

        anchor = null;

        anchorNode = new AnchorNode(anchor);
        anchorNode.setParent(arFragment.getArSceneView().getScene());
        mario = new Node();
        mario.setParent(anchorNode);
        mario.setRenderable(MarioRederable);
        mario.setLocalPosition(new Vector3(-10.05f,-0.5f,-0.3f));
        mario.setLocalScale(new Vector3(0.5f,0.5f,0.5f));
        mario.setOnTapListener((hitTestResult ,motionEvent) -> {
            mario.setEnabled(false);
            Toast.makeText(this, "Bien joué !", Toast.LENGTH_LONG).show();
            Intent main = new Intent(Mario.this, Qrcode.class);
            startActivity(main);
        });
    }

    public void buildtextRenderable() {
        ViewRenderable.builder()
                .setView(arFragment.getContext(), R.layout.imgmario)
                .build()
                .thenAccept(renderable -> {
                    MarioRederable = renderable;
                });

    }
}
