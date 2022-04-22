package fr.legrain.bdg.espaceclient.data.model;


public class TaParamEspaceClientDTO  implements java.io.Serializable {

	private static final long serialVersionUID = 2371159789946188318L;

	private Integer id;
	
	private Boolean afficheDevis = true;
	private Boolean afficheFactures = true;
	private Boolean afficheCommandes = true;
	private Boolean afficheLivraisons = true;
	private Boolean afficheAvisEcheance = true;
	private Boolean paiementCb  = true;
	private Boolean espaceClientActif = true;
	private Boolean miseADispositionAutomatiqueDesFactures = false;
	private Boolean affichePublicite = false;
	private byte[] logoLogin;
	private byte[] logoPagesSimples;
	private byte[] logoHeader;
	private byte[] logoFooter;
	private byte[] imageBackgroundLogin;
	private String nomImageLogoLogin;
	private String nomImageLogoPagesSimples;
	private String nomImageLogoHeader;
	private String nomImageLogoFooter;
	private String nomImageBackgroundLogin;
	private String urlPerso;
	private String texteLogin1;
	private String texteLogin2;
	private String texteAccueil;
	private String themeDefaut;
	
	private String contactEmail;
	private String contactWeb;
	private String contactTel;
	private String adresse1;
	private String adresse2;
	private String adresse3;
	private String codePostal;
	private String ville;
	private String pays;

	private Integer versionObj;

	public TaParamEspaceClientDTO() {
	}

	public void setEspaceClientDTO(TaParamEspaceClientDTO taEspaceClientDTO) {
		this.id = taEspaceClientDTO.id;

	}

	public static TaParamEspaceClientDTO copy(TaParamEspaceClientDTO taEspaceClientDTO){
		TaParamEspaceClientDTO taEspaceClientDTOLoc = new TaParamEspaceClientDTO();
		taEspaceClientDTOLoc.setId(taEspaceClientDTO.getId());                               
		return taEspaceClientDTOLoc;
	}

	public String getAdresseComplete() {
		String adresse = "";
		adresse += getAdresse1()!=null?getAdresse1()+" ":"";
		adresse += getAdresse2()!=null?"\n"+getAdresse2()+" ":"";
		adresse += getAdresse3()!=null?"\n"+getAdresse3()+" ":"";
		adresse += getCodePostal()!=null?"\n"+getCodePostal()+" ":"";
		adresse += getVille()!=null?getVille()+" ":"";
		adresse += getPays()!=null?"\n"+getPays()+" ":"";
		adresse = adresse.trim();
		return adresse;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getVersionObj() {
		return this.versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	public Boolean getAfficheDevis() {
		return afficheDevis;
	}

	public void setAfficheDevis(Boolean afficheDevis) {
		this.afficheDevis = afficheDevis;
	}

	public Boolean getAfficheFactures() {
		return afficheFactures;
	}

	public void setAfficheFactures(Boolean afficheFactures) {
		this.afficheFactures = afficheFactures;
	}

	public Boolean getAfficheCommandes() {
		return afficheCommandes;
	}

	public void setAfficheCommandes(Boolean afficheCommandes) {
		this.afficheCommandes = afficheCommandes;
	}

	public Boolean getAfficheLivraisons() {
		return afficheLivraisons;
	}

	public void setAfficheLivraisons(Boolean afficheLivraisons) {
		this.afficheLivraisons = afficheLivraisons;
	}

	public Boolean getPaiementCb() {
		return paiementCb;
	}

	public void setPaiementCb(Boolean paiementCb) {
		this.paiementCb = paiementCb;
	}

	public Boolean getEspaceClientActif() {
		return espaceClientActif;
	}

	public void setEspaceClientActif(Boolean espaceClientActif) {
		this.espaceClientActif = espaceClientActif;
	}

	public byte[] getLogoLogin() {
		return logoLogin;
	}

	public void setLogoLogin(byte[] logoLogin) {
		this.logoLogin = logoLogin;
	}



	public byte[] getLogoFooter() {
		return logoFooter;
	}

	public void setLogoFooter(byte[] logoFooter) {
		this.logoFooter = logoFooter;
	}

	public byte[] getLogoPagesSimples() {
		return logoPagesSimples;
	}

	public void setLogoPagesSimples(byte[] logoPagesSimples) {
		this.logoPagesSimples = logoPagesSimples;
	}

	public byte[] getLogoHeader() {
		return logoHeader;
	}

	public void setLogoHeader(byte[] logoHeader) {
		this.logoHeader = logoHeader;
	}

	public byte[] getImageBackgroundLogin() {
		return imageBackgroundLogin;
	}

	public void setImageBackgroundLogin(byte[] imageBackgroundLogin) {
		this.imageBackgroundLogin = imageBackgroundLogin;
	}

	public String getNomImageLogoLogin() {
		return nomImageLogoLogin;
	}

	public void setNomImageLogoLogin(String nomImageLogoLogin) {
		this.nomImageLogoLogin = nomImageLogoLogin;
	}

	public String getNomImageLogoPagesSimples() {
		return nomImageLogoPagesSimples;
	}

	public void setNomImageLogoPagesSimples(String nomImageLogoPagesSimples) {
		this.nomImageLogoPagesSimples = nomImageLogoPagesSimples;
	}

	public String getNomImageLogoHeader() {
		return nomImageLogoHeader;
	}

	public void setNomImageLogoHeader(String nomImageLogoHeader) {
		this.nomImageLogoHeader = nomImageLogoHeader;
	}

	public String getNomImageLogoFooter() {
		return nomImageLogoFooter;
	}

	public void setNomImageLogoFooter(String nomImageLogoFooter) {
		this.nomImageLogoFooter = nomImageLogoFooter;
	}

	public String getNomImageBackgroundLogin() {
		return nomImageBackgroundLogin;
	}

	public void setNomImageBackgroundLogin(String nomImageBackgroundLogin) {
		this.nomImageBackgroundLogin = nomImageBackgroundLogin;
	}

	public String getUrlPerso() {
		return urlPerso;
	}

	public void setUrlPerso(String urlPerso) {
		this.urlPerso = urlPerso;
	}

	public String getTexteLogin1() {
		return texteLogin1;
	}

	public void setTexteLogin1(String texteLogin1) {
		this.texteLogin1 = texteLogin1;
	}

	public String getTexteLogin2() {
		return texteLogin2;
	}

	public void setTexteLogin2(String texteLogin2) {
		this.texteLogin2 = texteLogin2;
	}

	public String getTexteAccueil() {
		return texteAccueil;
	}

	public void setTexteAccueil(String texteAccueil) {
		this.texteAccueil = texteAccueil;
	}

	public String getThemeDefaut() {
		return themeDefaut;
	}

	public void setThemeDefaut(String themeDefaut) {
		this.themeDefaut = themeDefaut;
	}

	public Boolean getAfficheAvisEcheance() {
		return afficheAvisEcheance;
	}

	public void setAfficheAvisEcheance(Boolean afficheAvisEcheance) {
		this.afficheAvisEcheance = afficheAvisEcheance;
	}

	public Boolean getMiseADispositionAutomatiqueDesFactures() {
		return miseADispositionAutomatiqueDesFactures;
	}

	public void setMiseADispositionAutomatiqueDesFactures(Boolean miseADispositionAutomatiqueDesFactures) {
		this.miseADispositionAutomatiqueDesFactures = miseADispositionAutomatiqueDesFactures;
	}

	public Boolean getAffichePublicite() {
		return affichePublicite;
	}

	public void setAffichePublicite(Boolean affichePublicite) {
		this.affichePublicite = affichePublicite;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactWeb() {
		return contactWeb;
	}

	public void setContactWeb(String contactWeb) {
		this.contactWeb = contactWeb;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public String getAdresse1() {
		return adresse1;
	}

	public void setAdresse1(String adresse1) {
		this.adresse1 = adresse1;
	}

	public String getAdresse2() {
		return adresse2;
	}

	public void setAdresse2(String adresse2) {
		this.adresse2 = adresse2;
	}

	public String getAdresse3() {
		return adresse3;
	}

	public void setAdresse3(String adresse3) {
		this.adresse3 = adresse3;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getPays() {
		return pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}
}
