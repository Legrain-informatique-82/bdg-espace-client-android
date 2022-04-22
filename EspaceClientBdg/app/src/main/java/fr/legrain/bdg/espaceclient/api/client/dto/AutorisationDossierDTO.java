package fr.legrain.bdg.espaceclient.api.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "fichier",
        "dateDerSynchro",
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class AutorisationDossierDTO implements Serializable {

    /*
	private Integer id;
	private String fichier;
	private Date dateDerSynchro;
     */
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("fichier")
    private String fichier;
    @JsonProperty("dateDerSynchro")
    private Date dateDerSynchro;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFichier() {
        return fichier;
    }

    public void setFichier(String fichier) {
        this.fichier = fichier;
    }

    public Date getDateDerSynchro() {
        return dateDerSynchro;
    }

    public void setDateDerSynchro(Date dateDerSynchro) {
        this.dateDerSynchro = dateDerSynchro;
    }

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }
}