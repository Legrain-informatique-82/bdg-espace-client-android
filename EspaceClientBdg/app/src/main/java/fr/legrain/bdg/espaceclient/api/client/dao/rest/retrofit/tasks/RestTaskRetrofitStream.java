package fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.tasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import androidx.core.content.FileProvider;
import fr.legrain.bdg.espaceclient.BuildConfig;
import fr.legrain.bdg.espaceclient.LgrActivity;
import fr.legrain.bdg.espaceclient.MainActivity;
import fr.legrain.bdg.espaceclient.data.model.Const;
import fr.legrain.bdg.espaceclient.data.model.Parametre;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Android RestTask (REST) from the Android Recipes book.
 */
public class RestTaskRetrofitStream extends AsyncTask<Call, Void, File> {
    private static final String TAG = "AARestTask";
    public static final String HTTP_RESPONSE = "httpResponse";

    private Context mContext;
    private Retrofit mClient;
    private String mAction;
    private String nomFichier;

    public static Retrofit getRetrofitInstance() {
        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

        SharedPreferences prefs =  MainActivity.getAppContext().getSharedPreferences(LgrActivity.MY_PREFS_NAME, LgrActivity.MODE_PRIVATE);
        String ENDPOINT_URL = prefs.getString(LgrActivity.PARAM_KEY_BASE_URL, null);
        ENDPOINT_URL = Const.urlConnexionApi;

        Retrofit retrofit =  new Retrofit.Builder()
                .baseUrl(ENDPOINT_URL)
                .client(okHttpClient)
                //.addConverterFactory(JacksonConverterFactory.create())
                .build();

        return retrofit;
    }

    public RestTaskRetrofitStream(Context context, String action, String nomFichier) {
        mContext = context;
        mAction = action;
        mClient = getRetrofitInstance();
        this.nomFichier = nomFichier;
    }

    public RestTaskRetrofitStream(Context context, String action, Retrofit client) {
        mContext = context;
        mAction = action;
        mClient = client;
    }

    @Override
    protected File doInBackground(Call... params) {
        try {
            Call call = params[0];
            File file = null;
            Response<ResponseBody> response = call.execute();

            try {
                //avec FileProvider
//                Uri fileUri = FileProvider.getUriForFile(
//                        MainActivity.getAppContext(),
//                        BuildConfig.APPLICATION_ID + ".provider",
//                        new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),nomFichier));
//                file = new File(fileUri.getPath());

                //sans FileProvider
                File outputDir = Environment.getExternalStorageDirectory(); // context being the Activity pointer
                outputDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                file = new File(outputDir,nomFichier);

                try (OutputStream output = new FileOutputStream(file)) {
                    byte[] buffer = new byte[4 * 1024]; // or other buffer size
                    int read;

                    while ((read = response.body().byteStream().read(buffer)) != -1) {
                        output.write(buffer, 0, read);
                    }

                    output.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } finally {
                 response.body().byteStream().close();
            }

            return file;
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