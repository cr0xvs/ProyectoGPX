package com.mycompany.proyectogpx.beans;

import javax.xml.bind.annotation.XmlElement;

public class Trk {
    
    private TrkSEG trkseg;
    private String name;
    
    public TrkSEG getTrkseg(){
        return trkseg;
    }
    
    @XmlElement(namespace="http://www.topografix.com/GPX/1/1")
    public void setTrkseg(TrkSEG trkseg){
        this.trkseg = trkseg;
    }
    
    public String getName(){
        return name;
    }
    
    @XmlElement(namespace="http://www.topografix.com/GPX/1/1")
    public void setName(String name){
        this.name = name;
    }
}
