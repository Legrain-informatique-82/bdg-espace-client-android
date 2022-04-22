package fr.legrain.bdg.espaceclient.data.model;

import fr.legrain.bdg.espaceclient.api.client.dto.EspaceClientDTO;

/**
 * Created by nicolas on 09/08/17.
 */

public class Parametre {


    private String baseUrl = "https://dev.demo.promethee.biz:8443/"; //https://dev.demo.promethee.biz:8443/
    private String baseUrlApi = "https://dev.api.espace-client.promethee.biz:8443/";
    private String dossier = "demo"; //demo
    private String apiKeyDossier = ""; //
    private String apiKeyUtilisateur = ""; //
    private String login = "demo"; //demo
    private String password = "demo"; //demo

    private boolean modeMultiCompteTiers = false;

    private EspaceClientDTO espaceClientDTO = null;

    private boolean modeConnexionAvancee = false;

    private boolean ligneSuivanteAuto = true;

    /*
     * toutes les requetes font appel au serveur (données toujours à jour et affichage "instantané" dans BDG
     * Peut être plus lent, soumis aux problèmes de connections
     */
    private boolean modeConnecteUniquement = true;


    /*
     * La plus part des traitements et recherche se font à partir d'un cache local avec une synchroisation vers le serveur a certains moment
     *
     */
    private boolean modeConnecteAvecCacheLocal = false;

    private boolean effacerFlashApresTransfert = false;
    private int modeCodeBarre = 2; //EAN128 ou EAN13
    private int modeRegroupement = 1; //AUCUN ou ARTICLE ou LOT



    public static final String CONST_VALEUR_ACCES_API_LGR = "aa"; //aa
    public static final String CONST_DB_NAME = "bdg.db"; //bdg.db

    private Boolean modeTest = false;

    private boolean clavierHardware = false;

    static private Parametre param = null;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getDossier() {
        return dossier;
    }

    public void setDossier(String dossier) {
        this.dossier = dossier;
    }

    public String getApiKeyDossier() {
        return apiKeyDossier;
    }

    public void setApiKeyDossier(String apiKeyDossier) {
        this.apiKeyDossier = apiKeyDossier;
    }

    public String getApiKeyUtilisateur() {
        return apiKeyUtilisateur;
    }

    public void setApiKeyUtilisateur(String apiKeyUtilisateur) {
        this.apiKeyUtilisateur = apiKeyUtilisateur;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private Parametre() {

    }

    static public Parametre getInstance() {
        if(param == null) {
            param = new Parametre();
        }
        return param;
    }


    public Boolean getModeTest() {
        return modeTest;
    }

    public void setModeTest(Boolean modeTest) {
        this.modeTest = modeTest;
    }

    public boolean isClavierHardware() {
        return clavierHardware;
    }

    public void setClavierHardware(boolean clavierHardware) {
        this.clavierHardware = clavierHardware;
    }

    public boolean isLigneSuivanteAuto() {
        return ligneSuivanteAuto;
    }

    public void setLigneSuivanteAuto(boolean ligneSuivanteAuto) {
        this.ligneSuivanteAuto = ligneSuivanteAuto;
    }

    public boolean isModeConnecteUniquement() {
        return modeConnecteUniquement;
    }

    public void setModeConnecteUniquement(boolean modeConnecteUniquement) {
        this.modeConnecteUniquement = modeConnecteUniquement;
    }

    public boolean isModeConnecteAvecCacheLocal() {
        return modeConnecteAvecCacheLocal;
    }

    public void setModeConnecteAvecCacheLocal(boolean modeConnecteAvecCacheLocal) {
        this.modeConnecteAvecCacheLocal = modeConnecteAvecCacheLocal;
    }

    public boolean isEffacerFlashApresTransfert() {
        return effacerFlashApresTransfert;
    }

    public void setEffacerFlashApresTransfert(boolean effacerFlashApresTransfert) {
        this.effacerFlashApresTransfert = effacerFlashApresTransfert;
    }

    public int getModeCodeBarre() {
        return modeCodeBarre;
    }

    public void setModeCodeBarre(int modeCodeBarre) {
        this.modeCodeBarre = modeCodeBarre;
    }

    public int getModeRegroupement() {
        return modeRegroupement;
    }

    public void setModeRegroupement(int modeRegroupement) {
        this.modeRegroupement = modeRegroupement;
    }

    public boolean isModeConnexionAvancee() {
        return modeConnexionAvancee;
    }

    public void setModeConnexionAvancee(boolean modeConnexionAvancee) {
        this.modeConnexionAvancee = modeConnexionAvancee;
    }

    public EspaceClientDTO getEspaceClientDTO() {
        return espaceClientDTO;
    }

    public void setEspaceClientDTO(EspaceClientDTO espaceClientDTO) {
        this.espaceClientDTO = espaceClientDTO;
    }

    public String getBaseUrlApi() {
        return baseUrlApi;
    }

    public void setBaseUrlApi(String baseUrlApi) {
        this.baseUrlApi = baseUrlApi;
    }

    public boolean isModeMultiCompteTiers() {
        return modeMultiCompteTiers;
    }

    public void setModeMultiCompteTiers(boolean modeMultiCompteTiers) {
        this.modeMultiCompteTiers = modeMultiCompteTiers;
    }
}
