package fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit;

import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import fr.legrain.bdg.espaceclient.LgrActivity;
import fr.legrain.bdg.espaceclient.MainActivity;
import fr.legrain.bdg.espaceclient.api.client.dao.IEspaceClientBdgService;
import fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.interfaces.IEspaceClientRetrofit;
import fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.interfaces.IFactureRetrofit;
import fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.tasks.RestTaskRetrofit;
import fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.tasks.RestTaskRetrofitJSON;
import fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.tasks.RestTaskRetrofitList;
import fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.tasks.UnsafeOkHttpClient;
import fr.legrain.bdg.espaceclient.api.client.dto.AutorisationDossierDTO;
import fr.legrain.bdg.espaceclient.api.client.dto.EspaceClientDTO;
import fr.legrain.bdg.espaceclient.data.model.Const;
import fr.legrain.bdg.espaceclient.data.model.ParamFirebaseCloudMessaging;
import fr.legrain.bdg.espaceclient.data.model.ParamPaiement;
import fr.legrain.bdg.espaceclient.data.model.Parametre;
import fr.legrain.bdg.espaceclient.data.model.TaParamEspaceClientDTO;
import fr.legrain.bdg.espaceclient.data.model.TaTiersDTO;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class EspaceClientBdgService implements IEspaceClientBdgService {

    private RestTaskRetrofitList<EspaceClientDTO> t = new RestTaskRetrofitList<EspaceClientDTO>(MainActivity.getAppContext(),"");
    private RestTaskRetrofit<EspaceClientDTO> t2 = new RestTaskRetrofit<EspaceClientDTO>(MainActivity.getAppContext(),"");
    private RestTaskRetrofit<AutorisationDossierDTO> t3 = new RestTaskRetrofit<AutorisationDossierDTO>(MainActivity.getAppContext(),"");

    public EspaceClientDTO authenticate(String loginForm, String pwdForm) {
        IEspaceClientRetrofit utilisateurCall = RestTaskRetrofit.getRetrofitInstance().create(IEspaceClientRetrofit.class);

        Call<EspaceClientDTO> call = utilisateurCall.authenticate(loginForm, pwdForm);
        t2 = new RestTaskRetrofit<EspaceClientDTO>(MainActivity.getAppContext(),"");
        t2.execute(call);

        EspaceClientDTO l = null;
        try {
            l = t2.get();
            System.out.println("UtilisateurBdgService.authenticate");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return l;
    }

    public EspaceClientDTO refresh(String refreshToken) {

        EspaceClientDTO l = null;
                final String accessToken = null;

        SharedPreferences prefs =  MainActivity.getAppContext().getSharedPreferences(LgrActivity.MY_PREFS_NAME, LgrActivity.MODE_PRIVATE);
        String ENDPOINT_URL = prefs.getString(LgrActivity.PARAM_KEY_BASE_URL, null);
        ENDPOINT_URL = Const.urlConnexionApi;

        MediaType JSON  = MediaType.parse("application/json; charset=utf-8");
        RequestBody reqbody = RequestBody.create(refreshToken, JSON);
        OkHttpClient client = UnsafeOkHttpClient.getUnsafeOkHttpClient();
        Request request = new Request.Builder()
                .url(ENDPOINT_URL + "/rest/users/refresh")
                .method("POST", reqbody)
                .addHeader("Authorization",  "{ aa : \""+refreshToken+"\"}")
                .build();

        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.code());
            if ((response.code()) == 200) {
                // Get response
                String jsonData = response.body().string();

                JSONObject json = new JSONObject(jsonData);
                Parametre.getInstance().getEspaceClientDTO().setAccessToken(json.getString("accessToken"));
                Parametre.getInstance().getEspaceClientDTO().setToken(json.getString("token"));
                Parametre.getInstance().getEspaceClientDTO().setRefreshToken(json.getString("refreshToken"));

//                Gson gson = new Gson();
//                RefreshTokenResponseModel refreshTokenResponseModel = gson.fromJson(jsonData, RefreshTokenResponseModel.class);
//                if (refreshTokenResponseModel.getRespCode().equals("1")) {
//                    sharedPreferences.edit().putString(ACCESS_TOKEN, refreshTokenResponseModel.getResponse()).apply();
//                    return refreshTokenResponseModel.getResponse();
//                }
            }
            response.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return Parametre.getInstance().getEspaceClientDTO();
    }

    public Observable<ResponseBody> updateAndroidRegistrationToken(int id, String androidRegistrationToken) {
        try {
            OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

            String ENDPOINT_URL = Const.urlConnexionApi;
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ENDPOINT_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();

            IEspaceClientRetrofit iArticleRetrofit = retrofit.create(IEspaceClientRetrofit.class);

            return iArticleRetrofit.updateAndroidRegistrationToken(id, androidRegistrationToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    ;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Observable<ResponseBody> updateAndroidRegistrationToken(int id, ParamFirebaseCloudMessaging paramFirebaseCloudMessaging) {
        try {
            OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

            String ENDPOINT_URL = Const.urlConnexionApi;
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ENDPOINT_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();

            IEspaceClientRetrofit iArticleRetrofit = retrofit.create(IEspaceClientRetrofit.class);

            return iArticleRetrofit.updateAndroidRegistrationToken(id, paramFirebaseCloudMessaging)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    ;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public TaParamEspaceClientDTO parametresSync() {
        IEspaceClientRetrofit utilisateurCall = RestTaskRetrofit.getRetrofitInstance().create(IEspaceClientRetrofit.class);

        Call<TaParamEspaceClientDTO> call = utilisateurCall.parametresSync();
        RestTaskRetrofit<TaParamEspaceClientDTO> task = new RestTaskRetrofit<TaParamEspaceClientDTO>(MainActivity.getAppContext(),"");
        task = new RestTaskRetrofit<TaParamEspaceClientDTO>(MainActivity.getAppContext(),"");
        task.execute(call);

        TaParamEspaceClientDTO l = null;
        try {
            l = task.get();
            System.out.println("UtilisateurBdgService.refresh");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }

    public Observable<TaParamEspaceClientDTO> parametres() {

        try {
            OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

            String ENDPOINT_URL = Const.urlConnexionApi;
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ENDPOINT_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();

            IEspaceClientRetrofit iArticleRetrofit = retrofit.create(IEspaceClientRetrofit.class);

            return iArticleRetrofit.parametres()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    ;

        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public TaTiersDTO infosClientChezFournisseurDtoSync(String codeClientChezCeForunisseur) {
        IEspaceClientRetrofit utilisateurCall = RestTaskRetrofit.getRetrofitInstance().create(IEspaceClientRetrofit.class);

        Call<TaTiersDTO> call = utilisateurCall.infosClientChezFournisseurDtoSync(codeClientChezCeForunisseur);
        RestTaskRetrofit<TaTiersDTO> task = new RestTaskRetrofit<TaTiersDTO>(MainActivity.getAppContext(),"");
        task = new RestTaskRetrofit<TaTiersDTO>(MainActivity.getAppContext(),"");
        task.execute(call);

        TaTiersDTO l = null;
        try {
            l = task.get();
            System.out.println("UtilisateurBdgService.refresh");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }

    public Observable<TaTiersDTO[]> listeTiers(int idEspaceClient) {

        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

        String ENDPOINT_URL = Const.urlConnexionApi;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        IEspaceClientRetrofit iArticleRetrofit = retrofit.create(IEspaceClientRetrofit.class);

        return iArticleRetrofit.listeTiers(idEspaceClient)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                ;
    }

    public List<TaTiersDTO> listeTiersSync(int idEspaceClient) {

        RestTaskRetrofitList<TaTiersDTO> t = new RestTaskRetrofitList<TaTiersDTO>(MainActivity.getAppContext(),"");
        IEspaceClientRetrofit bonlivCall = RestTaskRetrofitList.getRetrofitInstance().create(IEspaceClientRetrofit.class);

        Call<TaTiersDTO[]> call = bonlivCall.listeTiersSync(idEspaceClient);
        t.execute(call);

        List<TaTiersDTO> l = null;
        try {
            l = t.get();
            System.out.println("FactureBdgService.selectAll");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return l;
    }

    public Observable<TaTiersDTO> infosClientChezFournisseurDTO(String codeClientChezCeForunisseur) {

        try {
            OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

            String ENDPOINT_URL = Const.urlConnexionApi;
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ENDPOINT_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();


            IEspaceClientRetrofit iArticleRetrofit = retrofit.create(IEspaceClientRetrofit.class);


            return iArticleRetrofit.infosClientChezFournisseurDTO(codeClientChezCeForunisseur)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    ;

        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*******************************************************************************************************************/
    /**                    PAIEMENT                                                                                    */
    /*******************************************************************************************************************/
    public Boolean paiementTermine(String idPaymentIntent) {
        IEspaceClientRetrofit bonlivCall = RestTaskRetrofit.getRetrofitInstance().create(IEspaceClientRetrofit.class);

        RestTaskRetrofitJSON task = new RestTaskRetrofitJSON(MainActivity.getAppContext(),"");

        Call<ResponseBody> call = bonlivCall.paiementTermine(idPaymentIntent);
        task.execute(call);

        String l = null;
        Boolean b = null;
        try {
            l = task.get();
            System.out.println("FactureBdgService.findById");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return b;
    }

    public Boolean fournisseurPermetPaiementParCBSync(/*String codeDossierFournisseur,*/ String codeClientChezCeFournisseur) {
        IEspaceClientRetrofit bonlivCall = RestTaskRetrofit.getRetrofitInstance().create(IEspaceClientRetrofit.class);

        RestTaskRetrofitJSON task = new RestTaskRetrofitJSON(MainActivity.getAppContext(),"");

        Call<ResponseBody> call = bonlivCall.fournisseurPermetPaiementParCBSync(/*codeDossierFournisseur,*/ codeClientChezCeFournisseur);
        task.execute(call);

        String l = null;
        Boolean b = null;
        try {
            l = task.get();
            JSONObject json = new JSONObject(l);
            b = json.getBoolean("b");
            System.out.println("FactureBdgService.findById");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }
    public Observable<ResponseBody> fournisseurPermetPaiementParCB(/*String codeDossierFournisseur,*/ String codeClientChezCeFournisseur) {
        try {
            OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

            String ENDPOINT_URL = Const.urlConnexionApi;
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ENDPOINT_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();

            IEspaceClientRetrofit iArticleRetrofit = retrofit.create(IEspaceClientRetrofit.class);

            return iArticleRetrofit.fournisseurPermetPaiementParCB(/*codeDossierFournisseur,*/ codeClientChezCeFournisseur)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    ;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String clePubliqueStripe() {
        IEspaceClientRetrofit bonlivCall = RestTaskRetrofit.getRetrofitInstance().create(IEspaceClientRetrofit.class);

        RestTaskRetrofitJSON task = new RestTaskRetrofitJSON(MainActivity.getAppContext(),"");

        Call<ResponseBody> call = bonlivCall.clePubliqueStripe();
        task.execute(call);

        String l = null;
        try {
            l = task.get();
            JSONObject json = new JSONObject(l);
            l = json.getString("b");
            System.out.println("FactureBdgService.findById");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }

    public String creerPaymentIntent(ParamPaiement p) {
        IEspaceClientRetrofit bonlivCall = RestTaskRetrofit.getRetrofitInstance().create(IEspaceClientRetrofit.class);

        RestTaskRetrofitJSON task = new RestTaskRetrofitJSON(MainActivity.getAppContext(),"");

        Call<ResponseBody> call = bonlivCall.creerPaymentIntent(p);
        task.execute(call);

        String l = null;
        try {
            l = task.get();
            JSONObject json = new JSONObject(l);
            l = json.getString("paymentSecret");
            System.out.println("FactureBdgService.findById");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }
    /*******************************************************************************************************************/
}
