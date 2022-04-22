package fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.interfaces;

import fr.legrain.bdg.espaceclient.api.client.dao.rest.IConstRest;
import fr.legrain.bdg.espaceclient.api.client.dto.AvisEcheanceDTO;
import fr.legrain.bdg.espaceclient.api.client.dto.AvisEcheanceDTO;
import fr.legrain.bdg.espaceclient.api.client.dto.FactureDTO;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

public interface IAvisEcheanceRetrofit {

  @Headers({IConstRest.HEADER_ACCEPT_APPLICATION_JSON})
  @GET("v1/espace-client/avis-echeance/")
  public Call<AvisEcheanceDTO[]> allSync(
        //  @Query(value="codeDossierFournisseur", encoded=true) String codeDossierFournisseur,
          @Query(value="codeTiers", encoded=true) String codeTiers,
          @Query(value="debut", encoded=true) String dateDebutStr,
          @Query(value="fin", encoded=true) String dateFinStr
  );

  @Headers({IConstRest.HEADER_ACCEPT_APPLICATION_JSON})
  @GET("v1/avis-echeance/")
  public Observable<AvisEcheanceDTO[]> all(
      //    @Query(value="codeDossierFournisseur", encoded=true) String codeDossierFournisseur,
          @Query(value="codeTiers", encoded=true) String codeTiers,
          @Query(value="debut", encoded=true) String dateDebutStr,
          @Query(value="fin", encoded=true) String dateFinStr
  );

  @Headers({IConstRest.HEADER_ACCEPT_APPLICATION_JSON})
  @GET("v1/avis-echeance/{id}")
  public Call<AvisEcheanceDTO> selectSync(@Path("id") int id, @Query(value="codeTiers", encoded=true) String codeTiers);

  @Headers({IConstRest.HEADER_ACCEPT_APPLICATION_JSON})
  @GET("v1/avis-echeance/{id}")
  public Observable<AvisEcheanceDTO> select(@Path("id") int id, @Query(value="codeTiers", encoded=true) String codeTiers);

  //@Headers({IConstRest.HEADER_ACCEPT_APPLICATION_JSON})
  @GET("v1/avis-echeance/pdf")
  @Streaming
  public Call<ResponseBody> avisEcheancePdfSync(
          @Query(value="codeDocument", encoded=true) String codeDocument
  );

  @GET("v1/avis-echeance/pdf")
  @Streaming
  public Observable<ResponseBody> avisEcheancePdf(
          @Query(value="codeDocument", encoded=true) String codeDocument
  );

}