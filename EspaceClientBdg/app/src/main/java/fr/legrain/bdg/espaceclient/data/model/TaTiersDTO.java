package fr.legrain.bdg.espaceclient.data.model;


import java.util.Date;


public class TaTiersDTO implements java.io.Serializable {

	private static final long serialVersionUID = -4278037257017688067L;

	private Integer id = 0;
	private String codeTiers;
	private String codeCompta;
	private String compte;
	private String nomTiers;
	private String prenomTiers;
	private String surnomTiers;
	private Boolean actifTiers = true;
	private Boolean utiliseCompteClient = false;
	private Boolean emailCleCompteClientEnvoye = false;
	private Boolean ttcTiers = false;
	private Integer idTCivilite;
	private String codeTCivilite;
	private Integer idEntreprise;
	private String nomEntreprise;
	private String liblEntreprise;
	private Integer idTTiers;
	private String codeTTiers;
	private String libelleTTiers;
	private Integer idCommentaire;
	private String commentaire;
	private String codeTTvaDoc;
	private Integer idTTvaDoc;
	
	private Date dateDerniereConnexionCompteClient;
	
	
	private Integer idTEntite;
	private String codeTEntite;
	private String liblTEntite;
	private String tvaIComCompl;
	private String siretCompl;
	private String numAgrementSanitaire;
	private String accise;
	private String ics;
	
	private Integer idAdresse;
	private String adresse1Adresse;
	private String adresse2Adresse;
	private String adresse3Adresse;
	private String codepostalAdresse;
	private String villeAdresse;
	private String paysAdresse;
	
	private Integer idEmail;
	private String adresseEmail;
	
	private String adresseWeb;
	private String numeroTelephone;
	
	
	private String codeCPaiement;
	private String libCPaiement;
	private Integer reportCPaiement=0;
	private Integer finMoisCPaiement=0;
	
	private String codeTPaiement;
	private String libTPaiement;
	private String compteTPaiement;
	private Integer reportTPaiement=0;
	private Integer finMoisTPaiement=0;
	
	private String codeTTarif;
	private Boolean accepte = true;
	
	private Date dateAnniv=new Date();
	
	private int idFamilleTiers;
	private String codeFamilleTiers;
	private String libelleFamilleTiers;
	
	private String maRefTiers;
	
	private Integer versionObj;
	private int nbTiers; // Utilisé pour compter un nombre de tiers
	
	public TaTiersDTO() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodeTiers() {
		return codeTiers;
	}

	public void setCodeTiers(String codeTiers) {
		this.codeTiers = codeTiers;
	}

	public String getCodeCompta() {
		return codeCompta;
	}

	public void setCodeCompta(String codeCompta) {
		this.codeCompta = codeCompta;
	}

	public String getCompte() {
		return compte;
	}

	public void setCompte(String compte) {
		this.compte = compte;
	}

	public String getNomTiers() {
		return nomTiers;
	}

	public void setNomTiers(String nomTiers) {
		this.nomTiers = nomTiers;
	}

	public String getPrenomTiers() {
		return prenomTiers;
	}

	public void setPrenomTiers(String prenomTiers) {
		this.prenomTiers = prenomTiers;
	}

	public String getSurnomTiers() {
		return surnomTiers;
	}

	public void setSurnomTiers(String surnomTiers) {
		this.surnomTiers = surnomTiers;
	}

	public Boolean getActifTiers() {
		return actifTiers;
	}

	public void setActifTiers(Boolean actifTiers) {
		this.actifTiers = actifTiers;
	}

	public Boolean getUtiliseCompteClient() {
		return utiliseCompteClient;
	}

	public void setUtiliseCompteClient(Boolean utiliseCompteClient) {
		this.utiliseCompteClient = utiliseCompteClient;
	}

	public Boolean getEmailCleCompteClientEnvoye() {
		return emailCleCompteClientEnvoye;
	}

	public void setEmailCleCompteClientEnvoye(Boolean emailCleCompteClientEnvoye) {
		this.emailCleCompteClientEnvoye = emailCleCompteClientEnvoye;
	}

	public Boolean getTtcTiers() {
		return ttcTiers;
	}

	public void setTtcTiers(Boolean ttcTiers) {
		this.ttcTiers = ttcTiers;
	}

	public Integer getIdTCivilite() {
		return idTCivilite;
	}

	public void setIdTCivilite(Integer idTCivilite) {
		this.idTCivilite = idTCivilite;
	}

	public String getCodeTCivilite() {
		return codeTCivilite;
	}

	public void setCodeTCivilite(String codeTCivilite) {
		this.codeTCivilite = codeTCivilite;
	}

	public Integer getIdEntreprise() {
		return idEntreprise;
	}

	public void setIdEntreprise(Integer idEntreprise) {
		this.idEntreprise = idEntreprise;
	}

	public String getNomEntreprise() {
		return nomEntreprise;
	}

	public void setNomEntreprise(String nomEntreprise) {
		this.nomEntreprise = nomEntreprise;
	}

	public String getLiblEntreprise() {
		return liblEntreprise;
	}

	public void setLiblEntreprise(String liblEntreprise) {
		this.liblEntreprise = liblEntreprise;
	}

	public Integer getIdTTiers() {
		return idTTiers;
	}

	public void setIdTTiers(Integer idTTiers) {
		this.idTTiers = idTTiers;
	}

	public String getCodeTTiers() {
		return codeTTiers;
	}

	public void setCodeTTiers(String codeTTiers) {
		this.codeTTiers = codeTTiers;
	}

	public String getLibelleTTiers() {
		return libelleTTiers;
	}

	public void setLibelleTTiers(String libelleTTiers) {
		this.libelleTTiers = libelleTTiers;
	}

	public Integer getIdCommentaire() {
		return idCommentaire;
	}

	public void setIdCommentaire(Integer idCommentaire) {
		this.idCommentaire = idCommentaire;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public String getCodeTTvaDoc() {
		return codeTTvaDoc;
	}

	public void setCodeTTvaDoc(String codeTTvaDoc) {
		this.codeTTvaDoc = codeTTvaDoc;
	}

	public Integer getIdTTvaDoc() {
		return idTTvaDoc;
	}

	public void setIdTTvaDoc(Integer idTTvaDoc) {
		this.idTTvaDoc = idTTvaDoc;
	}

	public Date getDateDerniereConnexionCompteClient() {
		return dateDerniereConnexionCompteClient;
	}

	public void setDateDerniereConnexionCompteClient(Date dateDerniereConnexionCompteClient) {
		this.dateDerniereConnexionCompteClient = dateDerniereConnexionCompteClient;
	}

	public Integer getIdTEntite() {
		return idTEntite;
	}

	public void setIdTEntite(Integer idTEntite) {
		this.idTEntite = idTEntite;
	}

	public String getCodeTEntite() {
		return codeTEntite;
	}

	public void setCodeTEntite(String codeTEntite) {
		this.codeTEntite = codeTEntite;
	}

	public String getLiblTEntite() {
		return liblTEntite;
	}

	public void setLiblTEntite(String liblTEntite) {
		this.liblTEntite = liblTEntite;
	}

	public String getTvaIComCompl() {
		return tvaIComCompl;
	}

	public void setTvaIComCompl(String tvaIComCompl) {
		this.tvaIComCompl = tvaIComCompl;
	}

	public String getSiretCompl() {
		return siretCompl;
	}

	public void setSiretCompl(String siretCompl) {
		this.siretCompl = siretCompl;
	}

	public String getNumAgrementSanitaire() {
		return numAgrementSanitaire;
	}

	public void setNumAgrementSanitaire(String numAgrementSanitaire) {
		this.numAgrementSanitaire = numAgrementSanitaire;
	}

	public String getAccise() {
		return accise;
	}

	public void setAccise(String accise) {
		this.accise = accise;
	}

	public String getIcs() {
		return ics;
	}

	public void setIcs(String ics) {
		this.ics = ics;
	}

	public Integer getIdAdresse() {
		return idAdresse;
	}

	public void setIdAdresse(Integer idAdresse) {
		this.idAdresse = idAdresse;
	}

	public String getAdresse1Adresse() {
		return adresse1Adresse;
	}

	public void setAdresse1Adresse(String adresse1Adresse) {
		this.adresse1Adresse = adresse1Adresse;
	}

	public String getAdresse2Adresse() {
		return adresse2Adresse;
	}

	public void setAdresse2Adresse(String adresse2Adresse) {
		this.adresse2Adresse = adresse2Adresse;
	}

	public String getAdresse3Adresse() {
		return adresse3Adresse;
	}

	public void setAdresse3Adresse(String adresse3Adresse) {
		this.adresse3Adresse = adresse3Adresse;
	}

	public String getCodepostalAdresse() {
		return codepostalAdresse;
	}

	public void setCodepostalAdresse(String codepostalAdresse) {
		this.codepostalAdresse = codepostalAdresse;
	}

	public String getVilleAdresse() {
		return villeAdresse;
	}

	public void setVilleAdresse(String villeAdresse) {
		this.villeAdresse = villeAdresse;
	}

	public String getPaysAdresse() {
		return paysAdresse;
	}

	public void setPaysAdresse(String paysAdresse) {
		this.paysAdresse = paysAdresse;
	}

	public Integer getIdEmail() {
		return idEmail;
	}

	public void setIdEmail(Integer idEmail) {
		this.idEmail = idEmail;
	}

	public String getAdresseEmail() {
		return adresseEmail;
	}

	public void setAdresseEmail(String adresseEmail) {
		this.adresseEmail = adresseEmail;
	}

	public String getAdresseWeb() {
		return adresseWeb;
	}

	public void setAdresseWeb(String adresseWeb) {
		this.adresseWeb = adresseWeb;
	}

	public String getNumeroTelephone() {
		return numeroTelephone;
	}

	public void setNumeroTelephone(String numeroTelephone) {
		this.numeroTelephone = numeroTelephone;
	}

	public String getCodeCPaiement() {
		return codeCPaiement;
	}

	public void setCodeCPaiement(String codeCPaiement) {
		this.codeCPaiement = codeCPaiement;
	}

	public String getLibCPaiement() {
		return libCPaiement;
	}

	public void setLibCPaiement(String libCPaiement) {
		this.libCPaiement = libCPaiement;
	}

	public Integer getReportCPaiement() {
		return reportCPaiement;
	}

	public void setReportCPaiement(Integer reportCPaiement) {
		this.reportCPaiement = reportCPaiement;
	}

	public Integer getFinMoisCPaiement() {
		return finMoisCPaiement;
	}

	public void setFinMoisCPaiement(Integer finMoisCPaiement) {
		this.finMoisCPaiement = finMoisCPaiement;
	}

	public String getCodeTPaiement() {
		return codeTPaiement;
	}

	public void setCodeTPaiement(String codeTPaiement) {
		this.codeTPaiement = codeTPaiement;
	}

	public String getLibTPaiement() {
		return libTPaiement;
	}

	public void setLibTPaiement(String libTPaiement) {
		this.libTPaiement = libTPaiement;
	}

	public String getCompteTPaiement() {
		return compteTPaiement;
	}

	public void setCompteTPaiement(String compteTPaiement) {
		this.compteTPaiement = compteTPaiement;
	}

	public Integer getReportTPaiement() {
		return reportTPaiement;
	}

	public void setReportTPaiement(Integer reportTPaiement) {
		this.reportTPaiement = reportTPaiement;
	}

	public Integer getFinMoisTPaiement() {
		return finMoisTPaiement;
	}

	public void setFinMoisTPaiement(Integer finMoisTPaiement) {
		this.finMoisTPaiement = finMoisTPaiement;
	}

	public String getCodeTTarif() {
		return codeTTarif;
	}

	public void setCodeTTarif(String codeTTarif) {
		this.codeTTarif = codeTTarif;
	}

	public Boolean getAccepte() {
		return accepte;
	}

	public void setAccepte(Boolean accepte) {
		this.accepte = accepte;
	}

	public Date getDateAnniv() {
		return dateAnniv;
	}

	public void setDateAnniv(Date dateAnniv) {
		this.dateAnniv = dateAnniv;
	}

	public int getIdFamilleTiers() {
		return idFamilleTiers;
	}

	public void setIdFamilleTiers(int idFamilleTiers) {
		this.idFamilleTiers = idFamilleTiers;
	}

	public String getCodeFamilleTiers() {
		return codeFamilleTiers;
	}

	public void setCodeFamilleTiers(String codeFamilleTiers) {
		this.codeFamilleTiers = codeFamilleTiers;
	}

	public String getLibelleFamilleTiers() {
		return libelleFamilleTiers;
	}

	public void setLibelleFamilleTiers(String libelleFamilleTiers) {
		this.libelleFamilleTiers = libelleFamilleTiers;
	}

	public String getMaRefTiers() {
		return maRefTiers;
	}

	public void setMaRefTiers(String maRefTiers) {
		this.maRefTiers = maRefTiers;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	public int getNbTiers() {
		return nbTiers;
	}

	public void setNbTiers(int nbTiers) {
		this.nbTiers = nbTiers;
	}
}
