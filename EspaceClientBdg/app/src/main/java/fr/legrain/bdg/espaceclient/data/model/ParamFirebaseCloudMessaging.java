package fr.legrain.bdg.espaceclient.data.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class ParamFirebaseCloudMessaging implements Serializable {

	private String ancienToken;
	private String nouveauToken;

	public String getAncienToken() {
		return ancienToken;
	}

	public void setAncienToken(String ancienToken) {
		this.ancienToken = ancienToken;
	}

	public String getNouveauToken() {
		return nouveauToken;
	}

	public void setNouveauToken(String nouveauToken) {
		this.nouveauToken = nouveauToken;
	}
}
