package com.google.ar.sceneform.samples.hellosceneform;

import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;

import java.util.ArrayList;

public class  Salle{
    private String name;
    private Node node;
    private ArrayList<Salle> salles;

    public Salle(String name, AnchorNode parent, ModelRenderable model, Vector3 position, Vector3 scale, Quaternion rotation) {
        this.salles = new ArrayList<>();
        this.name = name;
        this.node = new Node();
        this.node.setParent(parent);
        if (model != null){
            model.setShadowReceiver(false);
            model.setShadowCaster(false);
            this.node.setRenderable(model);
        }
        this.node.setWorldPosition(position);
        this.node.setLocalScale(scale);
        this.node.setWorldRotation(rotation);
    }

    public void addNeighbour(Salle s){
        if(!this.salles.contains(s)){
            this.salles.add(s);
            s.addNeighbour(this);
        }
    }

    public ArrayList<Salle> goTo(Salle end , Salle previous){
        ArrayList<Salle> res = new ArrayList<>();
        if(this == end){
            res.add(this);
            return res;
        }
        else{
            for(Salle s : this.salles){
                if(s==previous)continue;
                ArrayList<Salle> listSalle = s.goTo(end,this);
                if(!listSalle.isEmpty()) {
                    listSalle.add(this);
                    return listSalle;
                }
            }
        }
        return res;
    }

    public Node getNode() {
        return node;
    }

    public String getName() {
        return name;
    }
}
