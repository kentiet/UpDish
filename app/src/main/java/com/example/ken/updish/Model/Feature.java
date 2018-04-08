package com.example.ken.updish.Model;

/**
 * Created by ken on 3/23/2018.
 */

public class Feature {
    //private String type;
    private String feature;

    public Feature() {
        this.feature = null;
    }

    public Feature(String _feature){
        //this.type = _type;
        this.feature = _feature;
    }

    public void setFeature(String f) {
        this.feature = f;
    }

    public String getFeature(){
        return feature;
    }
}
