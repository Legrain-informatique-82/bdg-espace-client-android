package fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.tasks;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.interfaces.IAvisEcheanceRetrofit;
import fr.legrain.bdg.espaceclient.api.client.dto.AvisEcheanceDTO;
import fr.legrain.bdg.espaceclient.api.client.dto.AvisEcheanceDTO;
import fr.legrain.bdg.espaceclient.data.model.Const;
import fr.legrain.bdg.espaceclient.data.model.Parametre;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class AvisEcheanceRetrofitTask extends AsyncTask<String, Void, List<AvisEcheanceDTO>>{

//    private String mRestUrl;
//    private RestTaskCallback mCallback;
    private Parametre param;

//    public GetTask(String restUrl, RestTaskCallback callback){
//        this.mRestUrl = restUrl;
//        this.mCallback = callback;
//    }
    public AvisEcheanceRetrofitTask(Parametre param){
        this.param = param;
//        this.mRestUrl = restUrl;
//        this.mCallback = callback;
    }

    @Override
    protected List<AvisEcheanceDTO> doInBackground(String... params) {
        List<AvisEcheanceDTO> r = null;

        try {
            OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

            String ENDPOINT_URL = Const.urlConnexionApi;
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ENDPOINT_URL)
                    .client(okHttpClient)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();

            IAvisEcheanceRetrofit getTodos = retrofit.create(IAvisEcheanceRetrofit.class);

//            Call<AvisEcheanceDTO[]> call = getTodos.all(/*param.getDossier()*/);
//            Response<AvisEcheanceDTO[]> response = call.execute();
//            AvisEcheanceDTO[] result = response.body();


//            r = new ArrayList<>(Arrays.asList(result));


            int id = 12;
//            Call<TiersDTO> call = getTodos.select(id);
//            Response<TiersDTO> response = call.execute();
//            TiersDTO result = response.body();

            System.out.println("tt");

        } catch(Exception e) {
            e.printStackTrace();
        }

        return r;
    }

    @Override
    protected void onPostExecute(List<AvisEcheanceDTO> r) {
//        mCallback.onTaskComplete(result);
        super.onPostExecute(r);
    }


}