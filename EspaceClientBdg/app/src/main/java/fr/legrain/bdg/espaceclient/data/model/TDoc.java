package fr.legrain.bdg.espaceclient.data.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TDoc implements Serializable {

    private Integer idTDoc;
    private String liblTDoc;
    private String codeTDoc;
    private String descriptionTDoc;

    private static final List<TDoc> listeDocFlash = new ArrayList<>();

    public static final TDoc TYPE_PREPARATION = new TDoc(1,"Préparation","Preparation","");
    public static final TDoc TYPE_INVENTAIRE = new TDoc(2,"Inventaire","Inventaire","");
    public static final TDoc TYPE_FABRICATION = new TDoc(3,"Fabrication","Fabrication","");

    public static final List<TDoc> getListeDocFlash() {
        if(listeDocFlash.isEmpty()) {
            listeDocFlash.add(TYPE_PREPARATION);
            listeDocFlash.add(TYPE_INVENTAIRE);
        }
        return listeDocFlash;
    }

    public TDoc(Integer idTDoc, String liblTDoc, String codeTDoc, String descriptionTDoc) {
        this.idTDoc = idTDoc;
        this.liblTDoc = liblTDoc;
        this.codeTDoc = codeTDoc;
        this.descriptionTDoc = descriptionTDoc;
    }

    public Integer getIdTDoc() {
        return idTDoc;
    }

    public void setIdTDoc(Integer idTDoc) {
        this.idTDoc = idTDoc;
    }

    public String getLiblTDoc() {
        return liblTDoc;
    }

    public void setLiblTDoc(String liblTDoc) {
        this.liblTDoc = liblTDoc;
    }

    public String getCodeTDoc() {
        return codeTDoc;
    }

    public void setCodeTDoc(String codeTDoc) {
        this.codeTDoc = codeTDoc;
    }

    public String getDescriptionTDoc() {
        return descriptionTDoc;
    }

    public void setDescriptionTDoc(String descriptionTDoc) {
        this.descriptionTDoc = descriptionTDoc;
    }
}