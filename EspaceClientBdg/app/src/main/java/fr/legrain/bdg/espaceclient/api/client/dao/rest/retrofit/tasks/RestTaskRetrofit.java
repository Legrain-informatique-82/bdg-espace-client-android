package fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.tasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

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
public class RestTaskRetrofit<T> extends AsyncTask<Call, Void, T> {
    private static final String TAG = "AARestTask";
    public static final String HTTP_RESPONSE = "httpResponse";

    private Context mContext;
    private Retrofit mClient;
    private String mAction;

    public static Retrofit getRetrofitInstance() {
        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

        SharedPreferences prefs =  MainActivity.getAppContext().getSharedPreferences(LgrActivity.MY_PREFS_NAME, LgrActivity.MODE_PRIVATE);
        String ENDPOINT_URL = prefs.getString(LgrActivity.PARAM_KEY_BASE_URL, null);
        ENDPOINT_URL = Const.urlConnexionApi;

        Retrofit retrofit =  new Retrofit.Builder()
                .baseUrl(ENDPOINT_URL)
                .client(okHttpClient)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        return retrofit;
    }

    public RestTaskRetrofit(Context context, String action) {
        mContext = context;
        mAction = action;
        mClient = getRetrofitInstance();
    }

    public RestTaskRetrofit(Context context, String action, Retrofit client) {
        mContext = context;
        mAction = action;
        mClient = client;
    }

    @Override
    protected T doInBackground(Call... params) {
        try {
            Call call = params[0];

            Response<T> response = call.execute();
            T result = response.body();

            return result;
        }
        catch (Exception e) {
            // TODO handle this properly
            e.printStackTrace();
            return null;
        }
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