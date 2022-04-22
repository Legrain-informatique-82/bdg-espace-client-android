package fr.legrain.bdg.espaceclient.api.client.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "codeDocument",
        "dateDocument",
        "dateEchDocument",
        "dateLivDocument",
        "numLigneLDocument",
        "libelleDocument",
        "idAdresse",
        "idAdresseLiv",
        "idTiers",
        "codeTiers",
        "nomTiers",
        "prenomTiers",
        "surnomTiers",
        "nomEntreprise",
        "codeCompta",
        "compte",
        "idTPaiement",
        "codeTPaiement",
        "idCPaiement",
        "remHtDocument",
        "txRemHtDocument",
        "remTtcDocument",
        "txRemTtcDocument",
        "nbEDocument",
        "mtTtcCalc",
        "mtHtCalc",
        "mtTvaCalc",
        "netTtcCalc",
        "netHtCalc",
        "netTvaCalc",
        "resteAReglerComplet",
        "ipAcces",
        "ttc",
        "commentaire",
        "accepte",
        "codeTTvaDoc",
        "codeEtat",
        "liblEtat",
        "versionObj",
        "estMiseADisposition",
        "dateExport",
        "dateVerrouillage",
        "lignesDTO",
        "estMisADisposition"
})
public class FactureDTO implements Serializable, IDocumentDTO {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("codeDocument")
    private String codeDocument;
    @JsonProperty("dateDocument")
    private Date dateDocument;
    @JsonProperty("dateEchDocument")
    private Date dateEchDocument;
    @JsonProperty("dateLivDocument")
    private Date dateLivDocument;
    @JsonProperty("numLigneLDocument")
    private Object numLigneLDocument;
    @JsonProperty("libelleDocument")
    private String libelleDocument;
    @JsonProperty("idAdresse")
    private Integer idAdresse;
    @JsonProperty("idAdresseLiv")
    private Integer idAdresseLiv;
    @JsonProperty("idTiers")
    private Integer idTiers;
    @JsonProperty("codeTiers")
    private String codeTiers;
    @JsonProperty("nomTiers")
    private String nomTiers;
    @JsonProperty("prenomTiers")
    private String prenomTiers;
    @JsonProperty("surnomTiers")
    private String surnomTiers;
    @JsonProperty("nomEntreprise")
    private String nomEntreprise;
    @JsonProperty("codeCompta")
    private String codeCompta;
    @JsonProperty("compte")
    private String compte;
    @JsonProperty("idTPaiement")
    private Integer idTPaiement;
    @JsonProperty("codeTPaiement")
    private String codeTPaiement;
    @JsonProperty("idCPaiement")
    private Integer idCPaiement;
    @JsonProperty("remHtDocument")
    private Integer remHtDocument;
    @JsonProperty("txRemHtDocument")
    private Integer txRemHtDocument;
    @JsonProperty("remTtcDocument")
    private Integer remTtcDocument;
    @JsonProperty("txRemTtcDocument")
    private Integer txRemTtcDocument;
    @JsonProperty("nbEDocument")
    private Integer nbEDocument;
    @JsonProperty("mtTtcCalc")
    private BigDecimal mtTtcCalc;
    @JsonProperty("mtHtCalc")
    private BigDecimal mtHtCalc;
    @JsonProperty("mtTvaCalc")
    private BigDecimal mtTvaCalc;
    @JsonProperty("netTtcCalc")
    private BigDecimal netTtcCalc;
    @JsonProperty("netHtCalc")
    private BigDecimal netHtCalc;
    @JsonProperty("netTvaCalc")
    private BigDecimal netTvaCalc;
    @JsonProperty("resteAReglerComplet")
    private BigDecimal resteAReglerComplet;
    @JsonProperty("ipAcces")
    private String ipAcces;
    @JsonProperty("ttc")
    private Boolean ttc;
    @JsonProperty("commentaire")
    private String commentaire;
    @JsonProperty("accepte")
    private Boolean accepte;
    @JsonProperty("codeTTvaDoc")
    private String codeTTvaDoc;
    @JsonProperty("codeEtat")
    private Object codeEtat;
    @JsonProperty("liblEtat")
    private Object liblEtat;
    @JsonProperty("versionObj")
    private Integer versionObj;
    @JsonProperty("estMiseADisposition")
    private Boolean estMiseADisposition;
    @JsonProperty("dateExport")
    private Object dateExport;
    @JsonProperty("dateVerrouillage")
    private Object dateVerrouillage;
    @JsonProperty("lignesDTO")
    private List<LigneFactureDTO> lignesDTO = null;
    @JsonProperty("estMisADisposition")
    private Boolean estMisADisposition;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

//    @BindingAdapter("android:text")
//    public static void setText(TextView view, Date date) {
//        if (date!=null) {
//            DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
//            String localizedDate = df.format(date);
//
//            view.setText(localizedDate);
//        }
//    }

    /**
     * Regroupe les lignes pour un même numéro de lot en faisant la somme des quantités.
     */
    public void regroupeLigne() {
        if(lignesDTO!=null) {
            Map<String, LigneFactureDTO> mapNumlotLigneBl = new HashMap<>();
            List<LigneFactureDTO> ligneSansNumLot = new ArrayList<>();
            String cle_codeBarreNumLot = "";
            for (LigneFactureDTO l : lignesDTO) {
                if(l.getNumLot()!=null) {
                    cle_codeBarreNumLot = l.getNumLot()+l.getCodeBarre();
                    if (mapNumlotLigneBl.get(cle_codeBarreNumLot) == null) {
                        //premiere ligne avec ce numero de lot, on l'ajoute
                        mapNumlotLigneBl.put(cle_codeBarreNumLot, l);
                    } else {
                        //deuxieme fois qu'on utilise ce numero de lot, on cumule les quantités sur la meme ligne
                        mapNumlotLigneBl.get(cle_codeBarreNumLot).setQteLDocument(mapNumlotLigneBl.get(cle_codeBarreNumLot).getQteLDocument().add(l.getQteLDocument()));
                    }
                } else {
                    ligneSansNumLot.add(l);
                }
            }
            lignesDTO.clear();
            for (LigneFactureDTO l : mapNumlotLigneBl.values()) {
                lignesDTO.add(l);
            }
            lignesDTO.addAll(ligneSansNumLot);

            //tri en fonction du libellé
            Collections.sort(lignesDTO, new Comparator<LigneFactureDTO>() {
                @Override
                public int compare(LigneFactureDTO l1, LigneFactureDTO l2) {
                    String libLot1 = l1.getLibelleLot();
                    String libLot2 = l2.getLibelleLot();
                    String lot1 = lot(l1.getNumLot());
                    String lot2 = lot(l2.getNumLot());
                    libLot1 = libLot1+lot1;
                    libLot2 = libLot2+lot2;

                    return  libLot1.compareTo(libLot2);
                }

                public String lot(String numLot) {
                    String r = "";
//                    if(numLot!=null) {
//                        String datelot = numLot.substring(0, 9);
//                        datelot = "20" + datelot.split("/")[2] + datelot.split("/")[1] + datelot.split("/")[0];
//                        String heurelot = numLot.split("_")[1].replace("(", "").replace(")", "").replace(":", "");
//                        r = datelot + heurelot;
//                    }
                    return r;
                }
            });
        }
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("codeDocument")
    public String getCodeDocument() {
        return codeDocument;
    }

    @JsonProperty("codeDocument")
    public void setCodeDocument(String codeDocument) {
        this.codeDocument = codeDocument;
    }

    @JsonProperty("dateDocument")
    public Date getDateDocument() {
        return dateDocument;
    }

    @JsonProperty("dateDocument")
    public void setDateDocument(Date dateDocument) {
        this.dateDocument = dateDocument;
    }

    @JsonProperty("dateEchDocument")
    public Date getDateEchDocument() {
        return dateEchDocument;
    }

    @JsonProperty("dateEchDocument")
    public void setDateEchDocument(Date dateEchDocument) {
        this.dateEchDocument = dateEchDocument;
    }

    @JsonProperty("dateLivDocument")
    public Date getDateLivDocument() {
        return dateLivDocument;
    }

    @JsonProperty("dateLivDocument")
    public void setDateLivDocument(Date dateLivDocument) {
        this.dateLivDocument = dateLivDocument;
    }

    @JsonProperty("numLigneLDocument")
    public Object getNumLigneLDocument() {
        return numLigneLDocument;
    }

    @JsonProperty("numLigneLDocument")
    public void setNumLigneLDocument(Object numLigneLDocument) {
        this.numLigneLDocument = numLigneLDocument;
    }

    @JsonProperty("libelleDocument")
    public String getLibelleDocument() {
        return libelleDocument;
    }

    @JsonProperty("libelleDocument")
    public void setLibelleDocument(String libelleDocument) {
        this.libelleDocument = libelleDocument;
    }

    @JsonProperty("idAdresse")
    public Integer getIdAdresse() {
        return idAdresse;
    }

    @JsonProperty("idAdresse")
    public void setIdAdresse(Integer idAdresse) {
        this.idAdresse = idAdresse;
    }

    @JsonProperty("idAdresseLiv")
    public Integer getIdAdresseLiv() {
        return idAdresseLiv;
    }

    @JsonProperty("idAdresseLiv")
    public void setIdAdresseLiv(Integer idAdresseLiv) {
        this.idAdresseLiv = idAdresseLiv;
    }

    @JsonProperty("idTiers")
    public Integer getIdTiers() {
        return idTiers;
    }

    @JsonProperty("idTiers")
    public void setIdTiers(Integer idTiers) {
        this.idTiers = idTiers;
    }

    @JsonProperty("codeTiers")
    public String getCodeTiers() {
        return codeTiers;
    }

    @JsonProperty("codeTiers")
    public void setCodeTiers(String codeTiers) {
        this.codeTiers = codeTiers;
    }

    @JsonProperty("nomTiers")
    public String getNomTiers() {
        return nomTiers;
    }

    @JsonProperty("nomTiers")
    public void setNomTiers(String nomTiers) {
        this.nomTiers = nomTiers;
    }

    @JsonProperty("prenomTiers")
    public String getPrenomTiers() {
        return prenomTiers;
    }

    @JsonProperty("prenomTiers")
    public void setPrenomTiers(String prenomTiers) {
        this.prenomTiers = prenomTiers;
    }

    @JsonProperty("surnomTiers")
    public String getSurnomTiers() {
        return surnomTiers;
    }

    @JsonProperty("surnomTiers")
    public void setSurnomTiers(String surnomTiers) {
        this.surnomTiers = surnomTiers;
    }

    @JsonProperty("nomEntreprise")
    public String getNomEntreprise() {
        return nomEntreprise;
    }

    @JsonProperty("nomEntreprise")
    public void setNomEntreprise(String nomEntreprise) {
        this.nomEntreprise = nomEntreprise;
    }

    @JsonProperty("codeCompta")
    public String getCodeCompta() {
        return codeCompta;
    }

    @JsonProperty("codeCompta")
    public void setCodeCompta(String codeCompta) {
        this.codeCompta = codeCompta;
    }

    @JsonProperty("compte")
    public String getCompte() {
        return compte;
    }

    @JsonProperty("compte")
    public void setCompte(String compte) {
        this.compte = compte;
    }

    @JsonProperty("idTPaiement")
    public Integer getIdTPaiement() {
        return idTPaiement;
    }

    @JsonProperty("idTPaiement")
    public void setIdTPaiement(Integer idTPaiement) {
        this.idTPaiement = idTPaiement;
    }

    @JsonProperty("codeTPaiement")
    public String getCodeTPaiement() {
        return codeTPaiement;
    }

    @JsonProperty("codeTPaiement")
    public void setCodeTPaiement(String codeTPaiement) {
        this.codeTPaiement = codeTPaiement;
    }

    @JsonProperty("idCPaiement")
    public Integer getIdCPaiement() {
        return idCPaiement;
    }

    @JsonProperty("idCPaiement")
    public void setIdCPaiement(Integer idCPaiement) {
        this.idCPaiement = idCPaiement;
    }

    @JsonProperty("remHtDocument")
    public Integer getRemHtDocument() {
        return remHtDocument;
    }

    @JsonProperty("remHtDocument")
    public void setRemHtDocument(Integer remHtDocument) {
        this.remHtDocument = remHtDocument;
    }

    @JsonProperty("txRemHtDocument")
    public Integer getTxRemHtDocument() {
        return txRemHtDocument;
    }

    @JsonProperty("txRemHtDocument")
    public void setTxRemHtDocument(Integer txRemHtDocument) {
        this.txRemHtDocument = txRemHtDocument;
    }

    @JsonProperty("remTtcDocument")
    public Integer getRemTtcDocument() {
        return remTtcDocument;
    }

    @JsonProperty("remTtcDocument")
    public void setRemTtcDocument(Integer remTtcDocument) {
        this.remTtcDocument = remTtcDocument;
    }

    @JsonProperty("txRemTtcDocument")
    public Integer getTxRemTtcDocument() {
        return txRemTtcDocument;
    }

    @JsonProperty("txRemTtcDocument")
    public void setTxRemTtcDocument(Integer txRemTtcDocument) {
        this.txRemTtcDocument = txRemTtcDocument;
    }

    @JsonProperty("nbEDocument")
    public Integer getNbEDocument() {
        return nbEDocument;
    }

    @JsonProperty("nbEDocument")
    public void setNbEDocument(Integer nbEDocument) {
        this.nbEDocument = nbEDocument;
    }

    @JsonProperty("mtTtcCalc")
    public BigDecimal getMtTtcCalc() {
        return mtTtcCalc;
    }

    @JsonProperty("mtTtcCalc")
    public void setMtTtcCalc(BigDecimal mtTtcCalc) {
        this.mtTtcCalc = mtTtcCalc;
    }

    @JsonProperty("mtHtCalc")
    public BigDecimal getMtHtCalc() {
        return mtHtCalc;
    }

    @JsonProperty("mtHtCalc")
    public void setMtHtCalc(BigDecimal mtHtCalc) {
        this.mtHtCalc = mtHtCalc;
    }

    @JsonProperty("mtTvaCalc")
    public BigDecimal getMtTvaCalc() {
        return mtTvaCalc;
    }

    @JsonProperty("mtTvaCalc")
    public void setMtTvaCalc(BigDecimal mtTvaCalc) {
        this.mtTvaCalc = mtTvaCalc;
    }

    @JsonProperty("netTtcCalc")
    public BigDecimal getNetTtcCalc() {
        return netTtcCalc;
    }

    @JsonProperty("netTtcCalc")
    public void setNetTtcCalc(BigDecimal netTtcCalc) {
        this.netTtcCalc = netTtcCalc;
    }

    @JsonProperty("netHtCalc")
    public BigDecimal getNetHtCalc() {
        return netHtCalc;
    }

    @JsonProperty("netHtCalc")
    public void setNetHtCalc(BigDecimal netHtCalc) {
        this.netHtCalc = netHtCalc;
    }

    @JsonProperty("netTvaCalc")
    public BigDecimal getNetTvaCalc() {
        return netTvaCalc;
    }

    @JsonProperty("netTvaCalc")
    public void setNetTvaCalc(BigDecimal netTvaCalc) {
        this.netTvaCalc = netTvaCalc;
    }

    @JsonProperty("ipAcces")
    public String getIpAcces() {
        return ipAcces;
    }

    @JsonProperty("ipAcces")
    public void setIpAcces(String ipAcces) {
        this.ipAcces = ipAcces;
    }

    @JsonProperty("ttc")
    public Boolean getTtc() {
        return ttc;
    }

    @JsonProperty("ttc")
    public void setTtc(Boolean ttc) {
        this.ttc = ttc;
    }

    @JsonProperty("commentaire")
    public String getCommentaire() {
        return commentaire;
    }

    @JsonProperty("commentaire")
    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    @JsonProperty("accepte")
    public Boolean getAccepte() {
        return accepte;
    }

    @JsonProperty("accepte")
    public void setAccepte(Boolean accepte) {
        this.accepte = accepte;
    }

    @JsonProperty("codeTTvaDoc")
    public String getCodeTTvaDoc() {
        return codeTTvaDoc;
    }

    @JsonProperty("codeTTvaDoc")
    public void setCodeTTvaDoc(String codeTTvaDoc) {
        this.codeTTvaDoc = codeTTvaDoc;
    }

    @JsonProperty("codeEtat")
    public Object getCodeEtat() {
        return codeEtat;
    }

    @JsonProperty("codeEtat")
    public void setCodeEtat(Object codeEtat) {
        this.codeEtat = codeEtat;
    }

    @JsonProperty("liblEtat")
    public Object getLiblEtat() {
        return liblEtat;
    }

    @JsonProperty("liblEtat")
    public void setLiblEtat(Object liblEtat) {
        this.liblEtat = liblEtat;
    }

    @JsonProperty("versionObj")
    public Integer getVersionObj() {
        return versionObj;
    }

    @JsonProperty("versionObj")
    public void setVersionObj(Integer versionObj) {
        this.versionObj = versionObj;
    }

    @JsonProperty("estMiseADisposition")
    public Boolean getEstMiseADisposition() {
        return estMiseADisposition;
    }

    @JsonProperty("estMiseADisposition")
    public void setEstMiseADisposition(Boolean estMiseADisposition) {
        this.estMiseADisposition = estMiseADisposition;
    }

    @JsonProperty("dateExport")
    public Object getDateExport() {
        return dateExport;
    }

    @JsonProperty("dateExport")
    public void setDateExport(Object dateExport) {
        this.dateExport = dateExport;
    }

    @JsonProperty("dateVerrouillage")
    public Object getDateVerrouillage() {
        return dateVerrouillage;
    }

    @JsonProperty("dateVerrouillage")
    public void setDateVerrouillage(Object dateVerrouillage) {
        this.dateVerrouillage = dateVerrouillage;
    }

    @JsonProperty("lignesDTO")
    public List<LigneFactureDTO> getLignesDTO() {
        return lignesDTO;
    }

    @JsonProperty("lignesDTO")
    public void setLignesDTO(List<LigneFactureDTO> lignesDTO) {
        this.lignesDTO = lignesDTO;
    }

    @JsonProperty("estMisADisposition")
    public Boolean getEstMisADisposition() {
        return estMisADisposition;
    }

    @JsonProperty("estMisADisposition")
    public void setEstMisADisposition(Boolean estMisADisposition) {
        this.estMisADisposition = estMisADisposition;
    }

    public BigDecimal getResteAReglerComplet() {
        return resteAReglerComplet;
    }

    public void setResteAReglerComplet(BigDecimal resteAReglerComplet) {
        this.resteAReglerComplet = resteAReglerComplet;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}