package com.example.ensisar;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;

public class Peach extends AppCompatActivity {

    private ViewRenderable PeachRederable;
    private ArFragment arFragment;
    private Anchor anchor;
    private AnchorNode anchorNode;
    private Node peach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peach);
        Toast.makeText(this, "Trouvez la princesse la plus kidnapper", Toast.LENGTH_LONG).show();
        arFragment = (ArFragment)getSupportFragmentManager().findFragmentById(R.id.ux_fragment);
        buildtextRenderable();
        new Handler().postDelayed(this::generatePeach,10000);


    }

    public void generatePeach() {

        anchor = null;

        anchorNode = new AnchorNode(anchor);
        anchorNode.setParent(arFragment.getArSceneView().getScene());
        peach = new Node();
        peach.setParent(anchorNode);
        peach.setRenderable(PeachRederable);
        peach.setLocalPosition(new Vector3(10.05f,0f,-0.85f));
        peach.setLocalScale(new Vector3(0.5f,0.5f,0.5f));
        peach.setOnTapListener((hitTestResult ,motionEvent) -> {
            peach.setEnabled(false);
            Toast.makeText(this, "Bien joué !", Toast.LENGTH_LONG).show();
            Intent main = new Intent(Peach.this, Qrcode.class);
            startActivity(main);
        });
    }

    public void buildtextRenderable() {
        ViewRenderable.builder()
                .setView(arFragment.getContext(), R.layout.imgpeach)
                .build()
                .thenAccept(renderable -> {
                    PeachRederable = renderable;
                });

    }
}
