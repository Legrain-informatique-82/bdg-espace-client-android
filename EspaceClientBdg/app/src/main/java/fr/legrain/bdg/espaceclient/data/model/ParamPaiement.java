package fr.legrain.bdg.espaceclient.data.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class ParamPaiement implements Serializable {

	private String codeDossierFournisseur;
	private String codeClientChezCeFournisseur; 
	private BigDecimal montant;
	private String codeDocument;
	private String typeDocument;
	
	public String getCodeDossierFournisseur() {
		return codeDossierFournisseur;
	}
	public void setCodeDossierFournisseur(String codeDossierFournisseur) {
		this.codeDossierFournisseur = codeDossierFournisseur;
	}
	public String getCodeClientChezCeFournisseur() {
		return codeClientChezCeFournisseur;
	}
	public void setCodeClientChezCeFournisseur(String codeClientChezCeFournisseur) {
		this.codeClientChezCeFournisseur = codeClientChezCeFournisseur;
	}
	public BigDecimal getMontant() {
		return montant;
	}
	public void setMontant(BigDecimal montant) {
		this.montant = montant;
	}
	public String getCodeDocument() {
		return codeDocument;
	}
	public void setCodeDocument(String codeDocument) {
		this.codeDocument = codeDocument;
	}
	public String getTypeDocument() {
		return typeDocument;
	}
	public void setTypeDocument(String typeDocumen) {
		this.typeDocument = typeDocumen;
	}
}
