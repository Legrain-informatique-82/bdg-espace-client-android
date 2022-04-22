package fr.legrain.bdg.espaceclient.api.client.dao;

import java.io.File;
import java.util.Date;
import java.util.List;

import fr.legrain.bdg.espaceclient.api.client.dto.FactureDTO;
import fr.legrain.bdg.espaceclient.data.model.ParamPaiement;
import fr.legrain.bdg.espaceclient.data.model.RetourPaiementCarteBancaire;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

public interface IFactureBdgService {

 public List<FactureDTO> selectAllSync(/*String codeDossierFournisseur,*/ String codeClientChezCeForunisseur, String dateDebutStr, String dateFinStr);
 public Observable<FactureDTO[]> selectAll(/*String codeDossierFournisseur,*/ String codeClientChezCeForunisseur, String dateDebutStr, String dateFinStr);

 public FactureDTO findByIdSync(int id, String codeClientChezCeForunisseur);
 public Observable<FactureDTO> findById(int id, String codeClientChezCeForunisseur);

 public File facturePdfSync(/*String codeDossierFournisseur,*/ String codeFactureChezCeFournisseur);
 public Observable<ResponseBody> facturePdf(/*String codeDossierFournisseur,*/ String codeFactureChezCeFournisseur);

}