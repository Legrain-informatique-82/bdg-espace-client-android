package fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.interfaces;

import fr.legrain.bdg.espaceclient.api.client.dao.rest.IConstRest;
import fr.legrain.bdg.espaceclient.api.client.dto.AutorisationDossierDTO;
import fr.legrain.bdg.espaceclient.api.client.dto.EspaceClientDTO;
import fr.legrain.bdg.espaceclient.api.client.dto.UtilisateurDTO;
import fr.legrain.bdg.espaceclient.data.model.ParamFirebaseCloudMessaging;
import fr.legrain.bdg.espaceclient.data.model.ParamPaiement;
import fr.legrain.bdg.espaceclient.data.model.TaParamEspaceClientDTO;
import fr.legrain.bdg.espaceclient.data.model.TaTiersDTO;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IEspaceClientRetrofit {

  @Headers({IConstRest.HEADER_ACCEPT_APPLICATION_JSON/*, IConstRest.HEADER_CONTENT_TYPE_APPLICATION_X_WWW_FORM_URLENCODED*/})
  @POST("v1/auth/authenticate-espace-client")
  @FormUrlEncoded
  public Call<EspaceClientDTO> authenticate(@Field("login") String loginForm, @Field("password") String pwdForm);

  @Headers({IConstRest.HEADER_ACCEPT_APPLICATION_JSON/*, IConstRest.HEADER_CONTENT_TYPE_APPLICATION_X_WWW_FORM_URLENCODED*/})
  @POST("v1/auth/refresh-espace-client")
  public Call<EspaceClientDTO> refresh(@Body String refreshToken);

  @Headers({IConstRest.HEADER_ACCEPT_APPLICATION_JSON})
  @POST("v1/auth/{id}/android-registration-token")
  public Observable<ResponseBody> updateAndroidRegistrationToken(@Path("id") int id,@Body String androidRegistrationToken);

  @Headers({IConstRest.HEADER_ACCEPT_APPLICATION_JSON})
  @POST("v1/auth/{id}/android-registration-token-update")
  public Observable<ResponseBody> updateAndroidRegistrationToken(@Path("id") int id,@Body ParamFirebaseCloudMessaging paramFirebaseCloudMessaging);

  /*******************************************************************************************************************/

  @Headers({IConstRest.HEADER_ACCEPT_APPLICATION_JSON})
  @GET("v1/espace-client/parametres")
  public Call<TaParamEspaceClientDTO> parametresSync();

  @Headers({IConstRest.HEADER_ACCEPT_APPLICATION_JSON})
  @GET("v1/espace-client/parametres")
  public Observable<TaParamEspaceClientDTO> parametres();

  @Headers({IConstRest.HEADER_ACCEPT_APPLICATION_JSON})
  @GET("v1/espace-client/liste-tiers/{id}")
  public Observable<TaTiersDTO[]> listeTiers(@Path("id") int idEspaceClient);

  @Headers({IConstRest.HEADER_ACCEPT_APPLICATION_JSON})
  @GET("v1/espace-client/liste-tiers/{id}")
  public Call<TaTiersDTO[]> listeTiersSync(@Path("id") int idEspaceClient);


  @Headers({IConstRest.HEADER_ACCEPT_APPLICATION_JSON})
  @GET("v1/espace-client/infos-tiers")
  public Call<TaTiersDTO> infosClientChezFournisseurDtoSync(@Query(value="codeTiers", encoded=true) String codeTiers);

  @Headers({IConstRest.HEADER_ACCEPT_APPLICATION_JSON})
  @GET("v1/espace-client/infos-tiers")
  public Observable<TaTiersDTO> infosClientChezFournisseurDTO(@Query(value="codeTiers", encoded=true) String codeTiers);

  /*******************************************************************************************************************/
  /**                   PAIEMENT                                                                                     */
  /*******************************************************************************************************************/
  @Headers({IConstRest.HEADER_ACCEPT_APPLICATION_JSON})
  @GET("v1/paiement/etat-paiement-courant")
  public Call<ResponseBody> paiementTermine(@Query(value="idPaymentIntent", encoded=true) String idPaymentIntent
  );

  @Headers({IConstRest.HEADER_ACCEPT_APPLICATION_JSON})
  @GET("v1/paiement/paiment-cb-possible")
  public Call<ResponseBody> fournisseurPermetPaiementParCBSync(
          @Query(value="codeTiers", encoded=true) String codeTiers
  );

  @Headers({IConstRest.HEADER_ACCEPT_APPLICATION_JSON})
  @GET("v1/paiement/paiment-cb-possible")
  public Observable<ResponseBody> fournisseurPermetPaiementParCB(
          @Query(value="codeTiers", encoded=true) String codeTiers
  );

  @Headers({IConstRest.HEADER_ACCEPT_APPLICATION_JSON})
  @GET("v1/paiement/cle-publique-stripe")
  public Call<ResponseBody> clePubliqueStripe(
  );

  @Headers({IConstRest.HEADER_ACCEPT_APPLICATION_JSON})
  @POST("v1/paiement/paiement-document-cb")
  public Call<ResponseBody> creerPaymentIntent(@Body ParamPaiement p);
  /*******************************************************************************************************************/
}