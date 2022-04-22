package fr.legrain.bdg.espaceclient.api.client.dao;

import java.io.File;
import java.util.List;

import fr.legrain.bdg.espaceclient.api.client.dto.AvisEcheanceDTO;
import fr.legrain.bdg.espaceclient.api.client.dto.FactureDTO;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

public interface IAvisEcheanceBdgService {

 public List<AvisEcheanceDTO> selectAllSync(/*String codeDossierFournisseur,*/ String codeClientChezCeForunisseur, String dateDebutStr, String dateFinStr);
 public Observable<AvisEcheanceDTO[]> selectAll(/*String codeDossierFournisseur,*/ String codeClientChezCeForunisseur, String dateDebutStr, String dateFinStr);

 public AvisEcheanceDTO findByIdSync(int id, String codeClientChezCeForunisseur);
 public Observable<AvisEcheanceDTO> findById(int id, String codeClientChezCeForunisseur);

 public File avisEcheancePdfSync(/*String codeDossierFournisseur,*/ String codeFactureChezCeFournisseur);
 public Observable<ResponseBody> avisEcheancePdf(/*String codeDossierFournisseur,*/ String codeFactureChezCeFournisseur);

}