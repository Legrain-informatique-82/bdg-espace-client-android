package fr.legrain.bdg.espaceclient.data.model;

import java.io.Serializable;

public class RetourPaiementCarteBancaire implements Serializable {

	private static final long serialVersionUID = -4924301988705977526L;
	
	private boolean paye;
	private String refPaiement;
	private String idReglement;
	private String codeReglement;
	
	public RetourPaiementCarteBancaire() {
		
	}
	
	public boolean getPaye() {
		return paye;
	}
	public void setPaye(boolean paye) {
		this.paye = paye;
	}
	
	public String getRefPaiement() {
		return refPaiement;
	}
	public void setRefPaiement(String refPaiement) {
		this.refPaiement = refPaiement;
	}
	
	public String getIdReglement() {
		return idReglement;
	}
	public void setIdReglement(String idReglement) {
		this.idReglement = idReglement;
	}
	
	public String getCodeReglement() {
		return codeReglement;
	}
	public void setCodeReglement(String codeReglement) {
		this.codeReglement = codeReglement;
	}
	
}
