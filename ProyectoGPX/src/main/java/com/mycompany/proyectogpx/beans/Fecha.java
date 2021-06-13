package com.mycompany.proyectogpx.beans;

import java.time.LocalDate;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class Fecha extends XmlAdapter<String, LocalDate> {
    
    @Override
    public LocalDate unmarshal(String vt) throws Exception {
        return LocalDate.parse(vt);
    }
    
    @Override
    public String marshal(LocalDate bt) throws Exception {
       return bt.toString();
    }
}
