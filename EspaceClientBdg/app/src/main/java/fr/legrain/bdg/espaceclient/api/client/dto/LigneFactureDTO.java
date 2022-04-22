package fr.legrain.bdg.espaceclient.api.client.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "idDocument",
        "codeTLigne",
        "idArticle",
        "numLigneLDocument",
        "codeArticle",
        "libLDocument",
        "qteLDocument",
        "qte2LDocument",
        "prixULDocument",
        "tauxTvaLDocument",
        "codeTvaLDocument",
        "codeTTvaLDocument",
        "mtHtLDocument",
        "remTxLDocument",
        "mtTtcLDocument",
        "remHtLDocument",
        "mtHtLApresRemiseGlobaleDocument",
        "mtTtcLApresRemiseGlobaleDocument",
        "ipAcces",
        "u1LDocument",
        "u2LDocument",
        "codeEntrepot",
        "emplacement",
        "dluo",
        "numLot",
        "libelleLot",
        "codeBarre",
        "versionObj",
        "txRemHtDocument",
        "idLDocument"
})
public class LigneFactureDTO implements Serializable, ILigneDocumentDTO {

    @JsonProperty("idDocument")
    private Object idDocument;
    @JsonProperty("codeTLigne")
    private Object codeTLigne;
    @JsonProperty("idArticle")
    private Object idArticle;
    @JsonProperty("numLigneLDocument")
    private Object numLigneLDocument;
    @JsonProperty("codeArticle")
    private String codeArticle;
    @JsonProperty("libLDocument")
    private String libLDocument;
    @JsonProperty("qteLDocument")
    private BigDecimal qteLDocument;
    @JsonProperty("qte2LDocument")
    private BigDecimal qte2LDocument;
    @JsonProperty("prixULDocument")
    private BigDecimal prixULDocument;
    @JsonProperty("tauxTvaLDocument")
    private BigDecimal tauxTvaLDocument;
    @JsonProperty("codeTvaLDocument")
    private Object codeTvaLDocument;
    @JsonProperty("codeTTvaLDocument")
    private Object codeTTvaLDocument;
    @JsonProperty("mtHtLDocument")
    private BigDecimal mtHtLDocument;
    @JsonProperty("remTxLDocument")
    private BigDecimal remTxLDocument;
    @JsonProperty("mtTtcLDocument")
    private BigDecimal mtTtcLDocument;
    @JsonProperty("remHtLDocument")
    private BigDecimal remHtLDocument;
    @JsonProperty("mtHtLApresRemiseGlobaleDocument")
    private BigDecimal mtHtLApresRemiseGlobaleDocument;
    @JsonProperty("mtTtcLApresRemiseGlobaleDocument")
    private BigDecimal mtTtcLApresRemiseGlobaleDocument;
    @JsonProperty("ipAcces")
    private Object ipAcces;
    @JsonProperty("u1LDocument")
    private Object u1LDocument;
    @JsonProperty("u2LDocument")
    private Object u2LDocument;
    @JsonProperty("codeEntrepot")
    private Object codeEntrepot;
    @JsonProperty("emplacement")
    private Object emplacement;
    @JsonProperty("dluo")
    private Date dluo;
    @JsonProperty("numLot")
    private String numLot;
    @JsonProperty("libelleLot")
    private String libelleLot;
    @JsonProperty("codeBarre")
    private String codeBarre;
    @JsonProperty("versionObj")
    private Integer versionObj;
    @JsonProperty("txRemHtDocument")
    private Object txRemHtDocument;
    @JsonProperty("idLDocument")
    private Integer idLDocument;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("idDocument")
    public Object getIdDocument() {
        return idDocument;
    }

    @JsonProperty("idDocument")
    public void setIdDocument(Object idDocument) {
        this.idDocument = idDocument;
    }

    @JsonProperty("codeTLigne")
    public Object getCodeTLigne() {
        return codeTLigne;
    }

    @JsonProperty("codeTLigne")
    public void setCodeTLigne(Object codeTLigne) {
        this.codeTLigne = codeTLigne;
    }

    @JsonProperty("idArticle")
    public Object getIdArticle() {
        return idArticle;
    }

    @JsonProperty("idArticle")
    public void setIdArticle(Object idArticle) {
        this.idArticle = idArticle;
    }

    @JsonProperty("numLigneLDocument")
    public Object getNumLigneLDocument() {
        return numLigneLDocument;
    }

    @JsonProperty("numLigneLDocument")
    public void setNumLigneLDocument(Object numLigneLDocument) {
        this.numLigneLDocument = numLigneLDocument;
    }

    @JsonProperty("codeArticle")
    public String getCodeArticle() {
        return codeArticle;
    }

    @JsonProperty("codeArticle")
    public void setCodeArticle(String codeArticle) {
        this.codeArticle = codeArticle;
    }

    @JsonProperty("libLDocument")
    public String getLibLDocument() {
        return libLDocument;
    }

    @JsonProperty("libLDocument")
    public void setLibLDocument(String libLDocument) {
        this.libLDocument = libLDocument;
    }

    @JsonProperty("qteLDocument")
    public BigDecimal getQteLDocument() {
        return qteLDocument;
    }

    @JsonProperty("qteLDocument")
    public void setQteLDocument(BigDecimal qteLDocument) {
        this.qteLDocument = qteLDocument;
    }

    @JsonProperty("qte2LDocument")
    public BigDecimal getQte2LDocument() {
        return qte2LDocument;
    }

    @JsonProperty("qte2LDocument")
    public void setQte2LDocument(BigDecimal qte2LDocument) {
        this.qte2LDocument = qte2LDocument;
    }

    @JsonProperty("prixULDocument")
    public BigDecimal getPrixULDocument() {
        return prixULDocument;
    }

    @JsonProperty("prixULDocument")
    public void setPrixULDocument(BigDecimal prixULDocument) {
        this.prixULDocument = prixULDocument;
    }

    @JsonProperty("tauxTvaLDocument")
    public BigDecimal getTauxTvaLDocument() {
        return tauxTvaLDocument;
    }

    @JsonProperty("tauxTvaLDocument")
    public void setTauxTvaLDocument(BigDecimal tauxTvaLDocument) {
        this.tauxTvaLDocument = tauxTvaLDocument;
    }

    @JsonProperty("codeTvaLDocument")
    public Object getCodeTvaLDocument() {
        return codeTvaLDocument;
    }

    @JsonProperty("codeTvaLDocument")
    public void setCodeTvaLDocument(Object codeTvaLDocument) {
        this.codeTvaLDocument = codeTvaLDocument;
    }

    @JsonProperty("codeTTvaLDocument")
    public Object getCodeTTvaLDocument() {
        return codeTTvaLDocument;
    }

    @JsonProperty("codeTTvaLDocument")
    public void setCodeTTvaLDocument(Object codeTTvaLDocument) {
        this.codeTTvaLDocument = codeTTvaLDocument;
    }

    @JsonProperty("mtHtLDocument")
    public BigDecimal getMtHtLDocument() {
        return mtHtLDocument;
    }

    @JsonProperty("mtHtLDocument")
    public void setMtHtLDocument(BigDecimal mtHtLDocument) {
        this.mtHtLDocument = mtHtLDocument;
    }

    @JsonProperty("remTxLDocument")
    public BigDecimal getRemTxLDocument() {
        return remTxLDocument;
    }

    @JsonProperty("remTxLDocument")
    public void setRemTxLDocument(BigDecimal remTxLDocument) {
        this.remTxLDocument = remTxLDocument;
    }

    @JsonProperty("mtTtcLDocument")
    public BigDecimal getMtTtcLDocument() {
        return mtTtcLDocument;
    }

    @JsonProperty("mtTtcLDocument")
    public void setMtTtcLDocument(BigDecimal mtTtcLDocument) {
        this.mtTtcLDocument = mtTtcLDocument;
    }

    @JsonProperty("remHtLDocument")
    public BigDecimal getRemHtLDocument() {
        return remHtLDocument;
    }

    @JsonProperty("remHtLDocument")
    public void setRemHtLDocument(BigDecimal remHtLDocument) {
        this.remHtLDocument = remHtLDocument;
    }

    @JsonProperty("mtHtLApresRemiseGlobaleDocument")
    public BigDecimal getMtHtLApresRemiseGlobaleDocument() {
        return mtHtLApresRemiseGlobaleDocument;
    }

    @JsonProperty("mtHtLApresRemiseGlobaleDocument")
    public void setMtHtLApresRemiseGlobaleDocument(BigDecimal mtHtLApresRemiseGlobaleDocument) {
        this.mtHtLApresRemiseGlobaleDocument = mtHtLApresRemiseGlobaleDocument;
    }

    @JsonProperty("mtTtcLApresRemiseGlobaleDocument")
    public BigDecimal getMtTtcLApresRemiseGlobaleDocument() {
        return mtTtcLApresRemiseGlobaleDocument;
    }

    @JsonProperty("mtTtcLApresRemiseGlobaleDocument")
    public void setMtTtcLApresRemiseGlobaleDocument(BigDecimal mtTtcLApresRemiseGlobaleDocument) {
        this.mtTtcLApresRemiseGlobaleDocument = mtTtcLApresRemiseGlobaleDocument;
    }

    @JsonProperty("ipAcces")
    public Object getIpAcces() {
        return ipAcces;
    }

    @JsonProperty("ipAcces")
    public void setIpAcces(Object ipAcces) {
        this.ipAcces = ipAcces;
    }

    @JsonProperty("u1LDocument")
    public Object getU1LDocument() {
        return u1LDocument;
    }

    @JsonProperty("u1LDocument")
    public void setU1LDocument(Object u1LDocument) {
        this.u1LDocument = u1LDocument;
    }

    @JsonProperty("u2LDocument")
    public Object getU2LDocument() {
        return u2LDocument;
    }

    @JsonProperty("u2LDocument")
    public void setU2LDocument(Object u2LDocument) {
        this.u2LDocument = u2LDocument;
    }

    @JsonProperty("codeEntrepot")
    public Object getCodeEntrepot() {
        return codeEntrepot;
    }

    @JsonProperty("codeEntrepot")
    public void setCodeEntrepot(Object codeEntrepot) {
        this.codeEntrepot = codeEntrepot;
    }

    @JsonProperty("emplacement")
    public Object getEmplacement() {
        return emplacement;
    }

    @JsonProperty("emplacement")
    public void setEmplacement(Object emplacement) {
        this.emplacement = emplacement;
    }

    @JsonProperty("dluo")
    public Date getDluo() {
        return dluo;
    }

    @JsonProperty("dluo")
    public void setDluo(Date dluo) {
        this.dluo = dluo;
    }

    @JsonProperty("numLot")
    public String getNumLot() {
        return numLot;
    }

    @JsonProperty("numLot")
    public void setNumLot(String numLot) {
        this.numLot = numLot;
    }

    @JsonProperty("libelleLot")
    public String getLibelleLot() {
        return libelleLot;
    }

    @JsonProperty("libelleLot")
    public void setLibelleLot(String libelleLot) {
        this.libelleLot = libelleLot;
    }

    @JsonProperty("codeBarre")
    public String getCodeBarre() {
        return codeBarre;
    }

    @JsonProperty("codeBarre")
    public void setCodeBarre(String codeBarre) {
        this.codeBarre = codeBarre;
    }

    @JsonProperty("versionObj")
    public Integer getVersionObj() {
        return versionObj;
    }

    @JsonProperty("versionObj")
    public void setVersionObj(Integer versionObj) {
        this.versionObj = versionObj;
    }

    @JsonProperty("txRemHtDocument")
    public Object getTxRemHtDocument() {
        return txRemHtDocument;
    }

    @JsonProperty("txRemHtDocument")
    public void setTxRemHtDocument(Object txRemHtDocument) {
        this.txRemHtDocument = txRemHtDocument;
    }

    @JsonProperty("idLDocument")
    public Integer getIdLDocument() {
        return idLDocument;
    }

    @JsonProperty("idLDocument")
    public void setIdLDocument(Integer idLDocument) {
        this.idLDocument = idLDocument;
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

