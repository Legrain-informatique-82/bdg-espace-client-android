package fr.legrain.bdg.espaceclient.api.client.dao;

import java.util.List;

import fr.legrain.bdg.espaceclient.api.client.dto.AutorisationDossierDTO;
import fr.legrain.bdg.espaceclient.api.client.dto.EspaceClientDTO;
import fr.legrain.bdg.espaceclient.api.client.dto.EspaceClientDTO;
import fr.legrain.bdg.espaceclient.data.model.ParamFirebaseCloudMessaging;
import fr.legrain.bdg.espaceclient.data.model.ParamPaiement;
import fr.legrain.bdg.espaceclient.data.model.TaParamEspaceClientDTO;
import fr.legrain.bdg.espaceclient.data.model.TaTiersDTO;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;

public interface IEspaceClientBdgService {

 public EspaceClientDTO authenticate(String loginForm, String pwdForm);

 public EspaceClientDTO refresh(String refreshToken);

 public Observable<ResponseBody> updateAndroidRegistrationToken(int id, String androidRegistrationToken);
 public Observable<ResponseBody> updateAndroidRegistrationToken(int id, ParamFirebaseCloudMessaging paramFirebaseCloudMessaging);

 public TaParamEspaceClientDTO parametresSync();
 public Observable<TaParamEspaceClientDTO> parametres();

 public TaTiersDTO infosClientChezFournisseurDtoSync(String codeClientChezCeForunisseur);
 public Observable<TaTiersDTO> infosClientChezFournisseurDTO(String codeClientChezCeForunisseur);

 public Observable<TaTiersDTO[]> listeTiers(int idEspaceClient);
 public List<TaTiersDTO> listeTiersSync(int idEspaceClient);

 /*******************************************************************************************************************/
 /**                    PAIEMENT                                                                                    */
 /*******************************************************************************************************************/
 public Boolean paiementTermine( String idPaymentIntent);

 public Boolean fournisseurPermetPaiementParCBSync(/*String codeDossierFournisseur,*/ String codeClientChezCeFournisseur);
 public Observable<ResponseBody> fournisseurPermetPaiementParCB(/*String codeDossierFournisseur,*/ String codeClientChezCeFournisseur);

 public String clePubliqueStripe();

 public String creerPaymentIntent(ParamPaiement p);
 /*******************************************************************************************************************/

}