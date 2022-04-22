package fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.tasks;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.interfaces.IUtilisateurRetrofit;
import fr.legrain.bdg.espaceclient.api.client.dto.UtilisateurDTO;
import fr.legrain.bdg.espaceclient.data.model.Const;
import fr.legrain.bdg.espaceclient.data.model.Parametre;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/*
 <interfaces>
        <interface name="management">
            <inet-address value="${jboss.bind.address.management:127.0.0.1}"/>
        </interface>
        <interface name="public">
<!--             <inet-address value="${jboss.bind.address:0.0.0.0}"/> -->
			<any-address/>
        </interface>
    </interfaces>
 */
public class UtilisateurRetrofitTask extends AsyncTask<String, Void, List<UtilisateurDTO>>{

//    private String mRestUrl;
//    private RestTaskCallback mCallback;
    private Parametre param;

//    public GetTask(String restUrl, RestTaskCallback callback){
//        this.mRestUrl = restUrl;
//        this.mCallback = callback;
//    }
    public UtilisateurRetrofitTask(Parametre param){
        this.param = param;
//        this.mRestUrl = restUrl;
//        this.mCallback = callback;
    }

    @Override
    protected List<UtilisateurDTO> doInBackground(String... params) {
        List<UtilisateurDTO> r = null;
        //Use HTTP Client APIs to make the call.
        //Return the HTTP Response body here.

        try {
            OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

            String ENDPOINT_URL = param.getBaseUrl();
            ENDPOINT_URL = Const.urlConnexionApi;
//            String ENDPOINT_URL = "http://dev.demo.promethee.biz:8080/";
//            String ENDPOINT_URL = "https://192.168.1.22:8443/";
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ENDPOINT_URL)
                    .client(okHttpClient)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();


            IUtilisateurRetrofit utilisateurRetrofit = retrofit.create(IUtilisateurRetrofit.class);


//            UtilisateurDTO tiers = new UtilisateurDTO();
//            tiers.setId(104);
//            tiers.setNomTiers("azerty");
//            Call<UtilisateurDTO> call = iTiersRetrofit.update(param.getDossier(),104,tiers);
//            Response<UtilisateurDTO> response = call.execute();


            Call<UtilisateurDTO[]> call = utilisateurRetrofit.all(/*param.getDossier()*/);
            Response<UtilisateurDTO[]> response = call.execute();
            UtilisateurDTO[] result = response.body();

            r = new ArrayList<>(Arrays.asList(result));


            int id = 12;
//            Call<UtilisateurDTO> call = getTodos.select(id);
//            Response<UtilisateurDTO> response = call.execute();
//            UtilisateurDTO result = response.body();

            System.out.println("tt");

        } catch(Exception e) {
            e.printStackTrace();
        }

        return r;
    }

    @Override
    protected void onPostExecute(List<UtilisateurDTO> r) {
//        mCallback.onTaskComplete(result);
        super.onPostExecute(r);
    }


}