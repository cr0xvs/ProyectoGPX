package com.mycompany.proyectogpx.beans;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;

public class TrkSEG {
    
    private ArrayList<TrkPT> trkpt = new ArrayList<TrkPT>();

    public ArrayList<TrkPT> getTrkpt() {
        return trkpt;

    }
    
    @XmlElement(namespace="http://www.topografix.com/GPX/1/1")
    public void setTrkpt(ArrayList<TrkPT> trkpt) {
        this.trkpt = trkpt;
    }
}

