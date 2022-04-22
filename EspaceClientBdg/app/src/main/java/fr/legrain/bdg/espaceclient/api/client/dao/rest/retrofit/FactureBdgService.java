package fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;

import fr.legrain.bdg.espaceclient.MainActivity;
import fr.legrain.bdg.espaceclient.api.client.dao.IFactureBdgService;
import fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.interfaces.IAvisEcheanceRetrofit;
import fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.interfaces.IEspaceClientRetrofit;
import fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.interfaces.IFactureRetrofit;
import fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.tasks.RestTaskRetrofit;
import fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.tasks.RestTaskRetrofitList;
import fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.tasks.RestTaskRetrofitStream;
import fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.tasks.UnsafeOkHttpClient;
import fr.legrain.bdg.espaceclient.api.client.dto.AvisEcheanceDTO;
import fr.legrain.bdg.espaceclient.api.client.dto.FactureDTO;
import fr.legrain.bdg.espaceclient.data.model.Const;
import fr.legrain.bdg.espaceclient.data.model.Parametre;
import fr.legrain.bdg.espaceclient.data.model.TaParamEspaceClientDTO;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class FactureBdgService implements IFactureBdgService {

    private RestTaskRetrofitList<FactureDTO> t = new RestTaskRetrofitList<FactureDTO>(MainActivity.getAppContext(),"");
    private RestTaskRetrofit<FactureDTO> t2 = new RestTaskRetrofit<FactureDTO>(MainActivity.getAppContext(),"");
    private RestTaskRetrofitStream t3 = null;

    @Override
    public List<FactureDTO> selectAllSync(/*String codeDossierFournisseur,*/ String codeClientChezCeForunisseur, String dateDebutStr, String dateFinStr) {

        IFactureRetrofit bonlivCall = RestTaskRetrofitList.getRetrofitInstance().create(IFactureRetrofit.class);

        Call<FactureDTO[]> call = bonlivCall.allSync(/*codeDossierFournisseur,*/ codeClientChezCeForunisseur, dateDebutStr, dateFinStr);
        t.execute(call);

        List<FactureDTO> l = null;
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

    public Observable<FactureDTO[]> selectAll(/*String codeDossierFournisseur,*/ String codeClientChezCeForunisseur, String dateDebutStr, String dateFinStr) {
//        try {
            OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

            String ENDPOINT_URL = Const.urlConnexionApi;
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ENDPOINT_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();

            IFactureRetrofit iArticleRetrofit = retrofit.create(IFactureRetrofit.class);

            return iArticleRetrofit.all(/*codeDossierFournisseur,*/ codeClientChezCeForunisseur, dateDebutStr, dateFinStr)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    ;

//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//        return null;
    }

    @Override
    public FactureDTO findByIdSync(int id, String codeClientChezCeForunisseur) {
        IFactureRetrofit bonlivCall = RestTaskRetrofit.getRetrofitInstance().create(IFactureRetrofit.class);

        Call<FactureDTO> call = bonlivCall.selectSync(id,codeClientChezCeForunisseur);
        t2.execute(call);

        FactureDTO l = null;
        try {
            l = t2.get();
            System.out.println("FactureBdgService.findById");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return l;
    }

    public Observable<FactureDTO> findById(int id, String codeClientChezCeForunisseur) {
        try {
            OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

            String ENDPOINT_URL = Const.urlConnexionApi;
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ENDPOINT_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();

            IFactureRetrofit iArticleRetrofit = retrofit.create(IFactureRetrofit.class);

            return iArticleRetrofit.select(id, codeClientChezCeForunisseur)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    ;

        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public File facturePdfSync(/*String codeDossierFournisseur,*/ String codeFactureChezCeFournisseur) {
        IFactureRetrofit bonlivCall = RestTaskRetrofit.getRetrofitInstance().create(IFactureRetrofit.class);

        t3 = new RestTaskRetrofitStream(MainActivity.getAppContext(),"",codeFactureChezCeFournisseur+".pdf");
        Call<ResponseBody> call = bonlivCall.facturePdfSync(/*codeDossierFournisseur,*/ codeFactureChezCeFournisseur);
        t3.execute(call);

        ResponseBody response = null;
        File file = null;
        try {
            file = t3.get();
            System.out.println("FactureBdgService.findById");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public Observable<ResponseBody> facturePdf(/*String codeDossierFournisseur,*/ String codeFactureChezCeFournisseur) {
        try {
            OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

            String ENDPOINT_URL = Const.urlConnexionApi;
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ENDPOINT_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();

            IFactureRetrofit iArticleRetrofit = retrofit.create(IFactureRetrofit.class);

            return iArticleRetrofit.facturePdf(/*codeDossierFournisseur,*/ codeFactureChezCeFournisseur)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    ;

        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
