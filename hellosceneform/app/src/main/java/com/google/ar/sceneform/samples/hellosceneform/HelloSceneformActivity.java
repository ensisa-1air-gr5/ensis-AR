package com.google.ar.sceneform.samples.hellosceneform;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.Pose;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.ux.ArFragment;

import java.util.ArrayList;

/**
 * This is an example activity that uses the Sceneform UX package to make common AR tasks easier.
 */
public class HelloSceneformActivity extends AppCompatActivity {
  private static final String TAG = HelloSceneformActivity.class.getSimpleName();
  private static final double MIN_OPENGL_VERSION = 3.0;

  private ArFragment arFragment;
  private ModelRenderable ascenseur_model;
  private ModelRenderable toilette_model;
  private ModelRenderable e30_model;
  private ModelRenderable e31_model;
  private ModelRenderable e32_model;
  private ModelRenderable e33_model;
  private ModelRenderable e34_model;
  private ModelRenderable e35_model;
  private ModelRenderable e36_model;
  private ModelRenderable e37_model;
  private ModelRenderable e37_bis_model;
  private ModelRenderable e38_model;
  private ModelRenderable Aubry_E;
  private ModelRenderable Binder_G;
  private ModelRenderable Basset_M;
  private ModelRenderable Dupuis_R;
  private ModelRenderable BEN_SOUISSI_S;

  private GSalle gSalle;
  private int nbtap = 0;


  private Node addLine(Salle from, Salle to) {
    float lenght = Vector3.subtract(from.getNode().getWorldPosition(), to.getNode().getWorldPosition()).length();
    Color blue = new Color(android.graphics.Color.parseColor("#2c5c9a"));
    Node node = new Node();
    //create a blue cylinder
    MaterialFactory.makeOpaqueWithColor(this.getApplicationContext(), blue)
            .thenAccept(material -> {
                ModelRenderable model = ShapeFactory.makeCylinder(0.01f, lenght,
                        new Vector3(0f, lenght/2, 0f), material);
                model.setShadowCaster(false);
                model.setShadowReceiver(false);

                //assign model
                node.setParent(from.getNode().getParent());
                node.setRenderable(model);
                node.setWorldPosition(Vector3.add(to.getNode().getWorldPosition(), new Vector3(-0.5f, -1f, 0f)));

                //set rotation
                final Vector3 difference = Vector3.subtract(to.getNode().getWorldPosition(), from.getNode().getWorldPosition());
                final Vector3 directionFromTopToBottom = difference.normalized();
                final Quaternion rotationFromAToB =
                        Quaternion.lookRotation(directionFromTopToBottom, Vector3.up());
                    node.setWorldRotation(Quaternion.multiply(rotationFromAToB,
                        Quaternion.axisAngle(new Vector3(1.0f, 0.0f, 0.0f), 90)));
        });
    return node;
  }

  private void drawPath(ArrayList<Salle> path){
    for(int i = 0 ; i < path.size()-1; i++){
        addLine(path.get(i),path.get(i+1));
    }
  }

  private Salle nearest(){
      Salle res = null;
      float distance = Float.MAX_VALUE;
      Vector3 camera = arFragment.getArSceneView().getScene().getCamera().getWorldPosition();
      for(Salle s : gSalle.getSalles()){
          float tmp = Vector3.subtract(s.getNode().getWorldPosition(),camera).length();
        if( tmp < distance){
            distance = tmp;
            res = s;
        }
      }
      return res;
  }



  @Override
  @SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"})
  // CompletableFuture requires api level 24
  // FutureReturnValueIgnored is not valid
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (!checkIsSupportedDeviceOrFinish(this)) {
      return;
    }

    setContentView(R.layout.activity_ux);
    arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);
    gSalle = GSalle.getInstance();
      ModelRenderable.builder()
              .setSource(this, R.raw.toilettes)
              .build()
              .thenAccept(renderable -> toilette_model = renderable)
              .exceptionally(
                      throwable -> {
                          Toast toast =
                                  Toast.makeText(this, "Unable to load toilettes renderable", Toast.LENGTH_LONG);
                          toast.setGravity(Gravity.CENTER, 0, 0);
                          toast.show();
                          return null;
                      });

      ModelRenderable.builder()
              .setSource(this, R.raw.ascenseur)
              .build()
              .thenAccept(renderable -> ascenseur_model = renderable)
              .exceptionally(
                      throwable -> {
                          Toast toast =
                                  Toast.makeText(this, "Unable to load ascenseur renderable", Toast.LENGTH_LONG);
                          toast.setGravity(Gravity.CENTER, 0, 0);
                          toast.show();
                          return null;
                      });

      ModelRenderable.builder()
              .setSource(this, R.raw.e30)
              .build()
              .thenAccept(renderable -> e30_model = renderable)
              .exceptionally(
                      throwable -> {
                          Toast toast =
                                  Toast.makeText(this, "Unable to load e30 renderable", Toast.LENGTH_LONG);
                          toast.setGravity(Gravity.CENTER, 0, 0);
                          toast.show();
                          return null;
                      });

      ModelRenderable.builder()
              .setSource(this, R.raw.e31)
              .build()
              .thenAccept(renderable -> e31_model = renderable)
              .exceptionally(
                      throwable -> {
                          Toast toast =
                                  Toast.makeText(this, "Unable to load e31 renderable", Toast.LENGTH_LONG);
                          toast.setGravity(Gravity.CENTER, 0, 0);
                          toast.show();
                          return null;
                      });

      ModelRenderable.builder()
              .setSource(this, R.raw.e32)
              .build()
              .thenAccept(renderable -> e32_model = renderable)
              .exceptionally(
                      throwable -> {
                          Toast toast =
                                  Toast.makeText(this, "Unable to load e32 renderable", Toast.LENGTH_LONG);
                          toast.setGravity(Gravity.CENTER, 0, 0);
                          toast.show();
                          return null;
                      });

      ModelRenderable.builder()
              .setSource(this, R.raw.e33)
              .build()
              .thenAccept(renderable -> e33_model = renderable)
              .exceptionally(
                      throwable -> {
                          Toast toast =
                                  Toast.makeText(this, "Unable to load e33 renderable", Toast.LENGTH_LONG);
                          toast.setGravity(Gravity.CENTER, 0, 0);
                          toast.show();
                          return null;
                      });

      ModelRenderable.builder()
              .setSource(this, R.raw.e34)
              .build()
              .thenAccept(renderable -> e34_model = renderable)
              .exceptionally(
                      throwable -> {
                          Toast toast =
                                  Toast.makeText(this, "Unable to load e34 renderable", Toast.LENGTH_LONG);
                          toast.setGravity(Gravity.CENTER, 0, 0);
                          toast.show();
                          return null;
                      });

      ModelRenderable.builder()
              .setSource(this, R.raw.e35)
              .build()
              .thenAccept(renderable -> e35_model = renderable)
              .exceptionally(
                      throwable -> {
                          Toast toast =
                                  Toast.makeText(this, "Unable to load e35 renderable", Toast.LENGTH_LONG);
                          toast.setGravity(Gravity.CENTER, 0, 0);
                          toast.show();
                          return null;
                      });

      ModelRenderable.builder()
              .setSource(this, R.raw.e36)
              .build()
              .thenAccept(renderable -> e36_model = renderable)
              .exceptionally(
                      throwable -> {
                          Toast toast =
                                  Toast.makeText(this, "Unable to load e36 renderable", Toast.LENGTH_LONG);
                          toast.setGravity(Gravity.CENTER, 0, 0);
                          toast.show();
                          return null;
                      });

      ModelRenderable.builder()
              .setSource(this, R.raw.e37)
              .build()
              .thenAccept(renderable -> e37_model = renderable)
              .exceptionally(
                      throwable -> {
                          Toast toast =
                                  Toast.makeText(this, "Unable to load e37 renderable", Toast.LENGTH_LONG);
                          toast.setGravity(Gravity.CENTER, 0, 0);
                          toast.show();
                          return null;
                      });

      ModelRenderable.builder()
              .setSource(this, R.raw.e37_bis)
              .build()
              .thenAccept(renderable -> e37_bis_model = renderable)
              .exceptionally(
                      throwable -> {
                          Toast toast =
                                  Toast.makeText(this, "Unable to load e37bis renderable", Toast.LENGTH_LONG);
                          toast.setGravity(Gravity.CENTER, 0, 0);
                          toast.show();
                          return null;
                      });

      ModelRenderable.builder()
              .setSource(this, R.raw.e38)
              .build()
              .thenAccept(renderable -> e38_model = renderable)
              .exceptionally(
                      throwable -> {
                          Toast toast =
                                  Toast.makeText(this, "Unable to load e38 renderable", Toast.LENGTH_LONG);
                          toast.setGravity(Gravity.CENTER, 0, 0);
                          toast.show();
                          return null;
                      });


    arFragment.getArSceneView().getScene().setOnTouchListener(
        (HitTestResult hitResult, MotionEvent motionEvent) -> {

            if(nbtap == 0){

                Anchor debut3 = arFragment.getArSceneView().getSession().createAnchor(Pose.makeTranslation(new float[] {0,0,-15f}));
                Anchor fin3 = arFragment.getArSceneView().getSession().createAnchor(Pose.makeTranslation(new float[] {0,0,-30f}));

                AnchorNode debutNode3 = new AnchorNode(debut3);
                debutNode3.setParent(arFragment.getArSceneView().getScene());

                AnchorNode finNode3 = new AnchorNode(fin3);
                finNode3.setParent(arFragment.getArSceneView().getScene());

                Salle toilette = gSalle.create(new Salle("Toilette", debutNode3,toilette_model,  new Vector3(0f,1f,-3.3f), new Vector3(0.5f,1f,0.5f), new Quaternion(new Vector3(0,1,0), -90) ));
                Salle ascenseur = gSalle.create(new Salle("Ascenseur", debutNode3, ascenseur_model,  new Vector3(0f,1f,-4.8f), new Vector3(0.5f,1f,0.5f), new Quaternion(new Vector3(0,1,0), -90) ));
                Salle toilette_handicap = gSalle.create(new Salle("Toilette handicapé", debutNode3,toilette_model,  new Vector3(0f,1f,-9.5f), new Vector3(0.5f,1f,0.5f), new Quaternion(new Vector3(0,1,0), -90)) );
                Salle e30 = gSalle.create(new Salle("e30", debutNode3,e30_model,  new Vector3(0f,1f,-17.5f), new Vector3(0.5f,1f,0.5f), new Quaternion(new Vector3(0,1,0), -90)) );
                Salle e31 = gSalle.create(new Salle("e31", debutNode3, e31_model, new Vector3(0f,1f, -18.8f), new Vector3(0.5f,1f,0.5f), new Quaternion(new Vector3(0,1,0), -90)));
                Salle e32 = gSalle.create(new Salle("e32", debutNode3, e32_model, new Vector3(0f,1f, -30.4f), new Vector3(0.5f,1f,0.5f), new Quaternion(new Vector3(0,1,0), -90)));
                Salle e33 = gSalle.create(new Salle("e33", finNode3, e33_model,  new Vector3(0f,1f, -31.5f), new Vector3(0.5f,1f,0.5f), new Quaternion(new Vector3(0,1,0), -90)));
                Salle e34 = gSalle.create(new Salle("e34", finNode3, e34_model,  new Vector3(0f,1f, -40f), new Vector3(0.5f,1f,0.5f), new Quaternion(new Vector3(0,1,0), -90)));
                Salle e35 = gSalle.create(new Salle("e35", finNode3, e35_model,  new Vector3(0f,1f, -44.2f), new Vector3(0.5f,1f,0.5f), new Quaternion(new Vector3(0,1,0), -90)));
                Salle e36 = gSalle.create(new Salle("e36", finNode3, e36_model,  new Vector3(0f,1f, -50f), new Vector3(0.5f,1f,0.5f), new Quaternion(new Vector3(0,1,0), -90)));
                Salle e37 = gSalle.create(new Salle("e37", finNode3, e37_model,  new Vector3(0f,1f, -53.2f), new Vector3(0.5f,1f,0.5f), new Quaternion(new Vector3(0,1,0), -90)));
                Salle e37_bis = gSalle.create(new Salle("e37_bis", finNode3, e37_bis_model, new Vector3(0f,1f, -60.5f), new Vector3(0.5f,1f,0.5f), new Quaternion(new Vector3(0,1,0), -90)));
                Salle e38 = gSalle.create(new Salle("e38", finNode3, e38_model,  new Vector3(0f,1f, -67.5f), new Vector3(0.5f,1f,0.5f), new Quaternion(new Vector3(0,1,0), -90)));

                toilette.addNeighbourg(ascenseur);
                ascenseur.addNeighbourg(toilette_handicap);
                toilette_handicap.addNeighbourg(e30);
                e30.addNeighbourg(e31);
                e31.addNeighbourg(e32);
                e32.addNeighbourg(e33);
                e33.addNeighbourg(e34);
                e34.addNeighbourg(e35);
                e35.addNeighbourg(e36);
                e36.addNeighbourg(e37);
                e37.addNeighbourg(e37_bis);
                e37_bis.addNeighbourg(e38);
            }
            if(nbtap == 1){
                Salle s = nearest();
                drawPath(s.goTo(gSalle.getSalle("e36"),null));
            }
            nbtap++;
            return false;
        });
  }

  /**
   * Returns false and displays an error message if Sceneform can not run, true if Sceneform can run
   * on this device.
   *
   * <p>Sceneform requires Android N on the device as well as OpenGL 3.0 capabilities.
   *
   * <p>Finishes the activity if Sceneform can not run
   */
  public static boolean checkIsSupportedDeviceOrFinish(final Activity activity) {
    if (Build.VERSION.SDK_INT < VERSION_CODES.N) {
      Log.e(TAG, "Sceneform requires Android N or later");
      Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG).show();
      activity.finish();
      return false;
    }
    String openGlVersionString =
        ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE))
            .getDeviceConfigurationInfo()
            .getGlEsVersion();
    if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
      Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later");
      Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
          .show();
      activity.finish();
      return false;
    }
    return true;
  }
}
