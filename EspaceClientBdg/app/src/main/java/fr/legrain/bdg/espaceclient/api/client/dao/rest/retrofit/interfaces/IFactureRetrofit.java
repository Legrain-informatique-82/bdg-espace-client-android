package fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.interfaces;

import java.util.Date;

import fr.legrain.bdg.espaceclient.api.client.dao.rest.IConstRest;
import fr.legrain.bdg.espaceclient.api.client.dto.FactureDTO;
import fr.legrain.bdg.espaceclient.data.model.ParamPaiement;
import fr.legrain.bdg.espaceclient.data.model.RetourPaiementCarteBancaire;
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

public interface IFactureRetrofit {

  @Headers({IConstRest.HEADER_ACCEPT_APPLICATION_JSON})
  @GET("v1/factures")
  public Call<FactureDTO[]> allSync(
          @Query(value="codeTiers", encoded=true) String codeTiers,
          @Query(value="debut", encoded=true) String dateDebutStr,
          @Query(value="fin", encoded=true) String dateFinStr
  );

  @Headers({IConstRest.HEADER_ACCEPT_APPLICATION_JSON})
  @GET("v1/factures")
  public Observable<FactureDTO[]> all(
          @Query(value="codeTiers", encoded=true) String codeTiers,
          @Query(value="debut", encoded=true) String dateDebutStr,
          @Query(value="fin", encoded=true) String dateFinStr
  );

  @Headers({IConstRest.HEADER_ACCEPT_APPLICATION_JSON})
  @GET("v1/factures/{id}")
  public Call<FactureDTO> selectSync(@Path("id") int id, @Query(value="codeTiers", encoded=true) String codeTiers);

  @Headers({IConstRest.HEADER_ACCEPT_APPLICATION_JSON})
  @GET("v1/factures/{id}")
  public Observable<FactureDTO> select(@Path("id") int id, @Query(value="codeTiers", encoded=true) String codeTiers);

  //@Headers({IConstRest.HEADER_ACCEPT_APPLICATION_JSON})
  @GET("v1/factures/pdf")
  @Streaming
  public Call<ResponseBody> facturePdfSync(
          @Query(value="codeDocument", encoded=true) String codeDocument
  );

  @GET("v1/factures/pdf")
  @Streaming
  public Observable<ResponseBody> facturePdf(
          @Query(value="codeDocument", encoded=true) String codeDocument
  );

}