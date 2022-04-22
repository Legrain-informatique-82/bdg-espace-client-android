package fr.legrain.bdg.espaceclient.api.client.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.databinding.BindingAdapter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "username",
        "passwd",
        "nom",
        "prenom",
        "email",
        "dernierAcces",
        "actif",
        "autorisations",
        "androidRegistrationToken",
        "dernierAccesMobile"

})
@JsonIgnoreProperties(ignoreUnknown = true)
public class UtilisateurDTO implements Serializable {

    /*
private Integer id;
	private String username;
	private String passwd;
	private List<TaRRoleUtilisateurDTO> roles;
	private TaEntrepriseClientDTO taEntreprise;
	private String nom;
	private String prenom;
	private String email;
	private Date dernierAcces;
	private Boolean actif;
	private String autorisations;
	private Boolean adminDossier = false;
	private Boolean systeme = false;
     */
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("username")
    private String username;
    @JsonProperty("passwd")
    private String passwd;
    @JsonProperty("nom")
    private String nom;
    @JsonProperty("prenom")
    private String prenom;
    @JsonProperty("email")
    private String email;
    @JsonProperty("dernierAcces")
    private Date dernierAcces;
    @JsonProperty("actif")
    private Boolean actif;
    @JsonProperty("autorisations")
    private String autorisations;
    @JsonProperty("androidRegistrationToken")
    private String androidRegistrationToken; //token Firebase pour l'instant
    @JsonProperty("dernierAccesMobile")
    private Date dernierAccesMobile;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDernierAcces() {
        return dernierAcces;
    }

    public void setDernierAcces(Date dernierAcces) {
        this.dernierAcces = dernierAcces;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public String getAutorisations() {
        return autorisations;
    }

    public void setAutorisations(String autorisations) {
        this.autorisations = autorisations;
    }

    public String getAndroidRegistrationToken() {
        return androidRegistrationToken;
    }

    public void setAndroidRegistrationToken(String androidRegistrationToken) {
        this.androidRegistrationToken = androidRegistrationToken;
    }

    public Date getDernierAccesMobile() {
        return dernierAccesMobile;
    }

    public void setDernierAccesMobile(Date dernierAccesMobile) {
        this.dernierAccesMobile = dernierAccesMobile;
    }
}