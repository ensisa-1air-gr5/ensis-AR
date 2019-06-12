package com.example.ensisar;

import java.util.ArrayList;

public class GSalle {


    private ArrayList<Salle> salles;
    private static GSalle instance;


    private GSalle(){
        this.salles = new ArrayList<>();
        instance = this;
    }

    public static GSalle getInstance(){
        if(GSalle.instance==null)
            instance = new GSalle();
        return instance;
    }

    public Salle create(Salle s){
        this.salles.add(s);
        return s;
    }


    public Salle getSalle(String s){
        Salle res = null;
        for(Salle salle : this.salles){
            if(salle.getName().equals(s)){
                res = salle;
                break;
            }
        }
        return res;
    }

    public ArrayList<Salle> getSalles() {
        return salles;
    }
}
