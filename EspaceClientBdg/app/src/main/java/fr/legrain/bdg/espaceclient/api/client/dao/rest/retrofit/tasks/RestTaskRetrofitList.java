package fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.tasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import fr.legrain.bdg.espaceclient.LgrActivity;
import fr.legrain.bdg.espaceclient.MainActivity;
import fr.legrain.bdg.espaceclient.data.model.Const;
import fr.legrain.bdg.espaceclient.data.model.Parametre;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Android RestTask (REST) from the Android Recipes book.
 */
public class RestTaskRetrofitList<T> extends AsyncTask<Call, Void, List<T>> {
    private static final String TAG = "AARestTask";
    public static final String HTTP_RESPONSE = "httpResponse";

    private Context mContext;
    private Retrofit mClient;
    private String mAction;

//    private ProgressBar progressBar;

//    public static Retrofit getRetrofitInstance() {
//        return getRetrofitInstance(null);
//    }

    public static Retrofit getRetrofitInstance(/*ProgressBar progressBar*/) {
        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

        SharedPreferences prefs =  MainActivity.getAppContext().getSharedPreferences(LgrActivity.MY_PREFS_NAME, LgrActivity.MODE_PRIVATE);
        String ENDPOINT_URL = prefs.getString(LgrActivity.PARAM_KEY_BASE_URL, null);
        ENDPOINT_URL = Const.urlConnexionApi;

       // this.progressBar = progressBar;

        Retrofit retrofit =  new Retrofit.Builder()
                .baseUrl(ENDPOINT_URL)
                .client(okHttpClient)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        return retrofit;
    }

    public RestTaskRetrofitList(Context context, String action) {
        mContext = context;
        mAction = action;
        mClient = getRetrofitInstance();
    }


    public RestTaskRetrofitList(Context context, String action, Retrofit client) {
        mContext = context;
        mAction = action;
        mClient = client;
    }


    protected void onPreExecute() {
        //dialog.setMessage("Loading corresponding destinations...");
        //dialog.show();
//        if(progressBar!=null) {
//            progressBar.setVisibility(View.VISIBLE);
//            progressBar.setIndeterminate(true);
//        }
    }


    @Override
    protected List<T> doInBackground(Call... params) {
        try {
            Call call = params[0];

            Response<T[]> response = call.execute();
            T[] result = response.body();

            List<T> r = new ArrayList<>(Arrays.asList(result));

            return r;
        }
        catch (Exception e) {
            // TODO handle this properly
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<T> result) {
//        if(progressBar!=null) {
//            progressBar.setVisibility(View.GONE);
//        }
    }

//    /**
//     * `onPostExecute` is run after `doInBackground`, and it's
//     * run on the main/ui thread, so you it's safe to update ui
//     * components from it. (this is the correct way to update ui
//     * components.)
//     */
//    @Override
//    protected void onPostExecute(String result)
//    {
//        Log.i(TAG, "RESULT = " + result);
//        Intent intent = new Intent(mAction);
//        intent.putExtra(HTTP_RESPONSE, result);
//
//        // broadcast the completion
//        mContext.sendBroadcast(intent);
//    }

}