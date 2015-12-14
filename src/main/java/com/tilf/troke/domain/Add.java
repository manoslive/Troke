package com.tilf.troke.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Alex on 2015-12-02.
 */
public class Add {


    public String getNameobject() {
        return nameobject;
    }

    public void setNameobject(String nameobject) {
        this.nameobject = nameobject;
    }

    @Size(min = 1, max= 50, message=" *Veuillez entrez un nom entre 1 et 50 lettres..")
    @NotNull(message = " *Veuillez entrer un nom pour l'item ..")
    private String nameobject;


    public String getDescobject() {
        return descobject;
    }

    public void setDescobject(String descobject) {
        this.descobject = descobject;
    }

    @NotNull(message = " *Veuillez entrer une description a l'item")
    @Size(min = 1, max = 500, message = " *Veuillez entrer une description entre 1 et 500 lettres..")
    private String descobject;

//    private int idsubcategory;


    public Integer getValueobject() {
        return valueobject;
    }

    public void setValueobject(Integer valueobject) {
        this.valueobject = valueobject;
    }

    @NotNull(message = " *Veuillez évalué votre item ..")
    private Integer valueobject;


    public Integer getQuality() {
        return quality;
    }

    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    @NotNull( message = " *Veuillez indiqué une qualité a votre item..")
    private Integer quality;


}
