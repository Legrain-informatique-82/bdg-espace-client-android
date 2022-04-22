package fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.tasks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter;

import java.io.IOException;
import java.net.ConnectException;
import java.util.Date;

import fr.legrain.bdg.espaceclient.LgrActivity;
import fr.legrain.bdg.espaceclient.MainActivity;
import fr.legrain.bdg.espaceclient.api.client.dao.IEspaceClientBdgService;
import fr.legrain.bdg.espaceclient.api.client.dao.rest.IConstRest;
import fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.EspaceClientBdgService;
import fr.legrain.bdg.espaceclient.api.client.dto.EspaceClientDTO;
import fr.legrain.bdg.espaceclient.data.LoginDataSource;
import fr.legrain.bdg.espaceclient.data.LoginRepository;
import fr.legrain.bdg.espaceclient.data.model.Const;
import fr.legrain.bdg.espaceclient.data.model.Parametre;
import fr.legrain.bdg.espaceclient.ui.login.LoginActivity;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LgrInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        SharedPreferences prefs =  MainActivity.getAppContext().getSharedPreferences(LgrActivity.MY_PREFS_NAME, LgrActivity.MODE_PRIVATE);
        String dossier = prefs.getString(LgrActivity.PARAM_KEY_DOSSIER, null);
        String login = prefs.getString(LgrActivity.PARAM_KEY_API_LOGIN, null);
        String password =  prefs.getString(LgrActivity.PARAM_KEY_API_PASSWORD, null);

        //ce sont les accès pour accéder à l'API (utilisateur du dossier) et non 1 des utilisateur espace client
        login = "adminlgr";
        password = "pwdlgr";
        dossier = Const.dossierBdg;

        Log.e("intercept : ", dossier);
        Log.e("intercept : ", Const.urlConnexionBdg);

        Request.Builder builder = originalRequest.newBuilder()
//                .header("Authorization", Credentials.basic("aUsername", "aPassword"))
                .header(IConstRest.HEADER_DOSSIER,dossier)
                .header(IConstRest.HEADER_LGR,Parametre.CONST_VALEUR_ACCES_API_LGR)
                .header(IConstRest.HEADER_LOGIN,login)
                .header(IConstRest.HEADER_PASSWORD,password);

        if(Parametre.getInstance().getEspaceClientDTO()!=null) {
            builder.header("Authorization", "Bearer "+Parametre.getInstance().getEspaceClientDTO().getAccessToken());
        }
        Request newRequest = builder.build();

        /*************************************************************************************************************/
        // try the request
        Response response = null;
        try {
            response = chain.proceed(newRequest);

            boolean unauthorized = false;
            boolean limiteEssaiAtteinte = false;
            if (response.code() == 401 || response.code() == 422) {
                unauthorized = true;
            }
            if (response.priorResponse() != null && response.priorResponse().priorResponse() != null) {
                //on a deja re-essaye 2 fois, donc 3 fois en tout => on arrete quand même
                limiteEssaiAtteinte = true;
            }
            if (!limiteEssaiAtteinte) {
                if (unauthorized) {
                    IEspaceClientBdgService dao = new EspaceClientBdgService();
                    EspaceClientDTO refreshTokenCall = dao.refresh(Parametre.getInstance().getEspaceClientDTO().getRefreshToken());
                    /*
                        TODO faire un système d'appel récursif qui garde le dernier code de retour d'un appel "classique" (qui devrait renvoyer des données)
                        autre que le refresh qui lui va donner la plus part du temps un HTTP 200 et donc empecher de compter combien  d'erreurs successives ont eu lieux
                   */
                    //val refreshResponse = refreshTokenCall.execute();
                    //if(refreshResponse.isSuccessful) {
                    //    PrefManager.login = refreshResponse.body();
                    if (refreshTokenCall != null) {
                        Parametre.getInstance().getEspaceClientDTO().setAccessToken(refreshTokenCall.getAccessToken());
                        Parametre.getInstance().getEspaceClientDTO().setRefreshToken(refreshTokenCall.getRefreshToken());

                        SharedPreferences.Editor editor = MainActivity.getAppContext().getSharedPreferences(LgrActivity.MY_PREFS_NAME, LgrActivity.MODE_PRIVATE).edit();
                        editor.putString(LgrActivity.PARAM_KEY_LOGGED_IN_USER_ACCESS_TOKEN, Parametre.getInstance().getEspaceClientDTO().getAccessToken());
                        editor.putString(LgrActivity.PARAM_KEY_LOGGED_IN_USER_REFRESH_TOKEN, Parametre.getInstance().getEspaceClientDTO().getRefreshToken());
                        Moshi moshi = new Moshi.Builder()
                                .add(Date.class, new Rfc3339DateJsonAdapter().nullSafe())
                                .build();
                        JsonAdapter<EspaceClientDTO> jsonAdapter = moshi.adapter(EspaceClientDTO.class);
                        String json = jsonAdapter.toJson(Parametre.getInstance().getEspaceClientDTO());
                        editor.putString(LgrActivity.PARAM_KEY_LOGGED_IN_USER_OBJECT_JSON, json);

                        if (Parametre.getInstance().getEspaceClientDTO() != null) {
                            builder.header("Authorization", "Bearer " + Parametre.getInstance().getEspaceClientDTO().getAccessToken());
                        }
                        newRequest = builder.build();
                        return chain.proceed(newRequest);
                    }
                    return null;


                }
            } else {
                //limite du nombre de tentative atteinte
                Log.e("eee","limite du nombre de tentative atteinte");
            }
        } catch(ConnectException e) {
            Log.e("eee","pas de réponse, pas de connexion");
            logout();
        } catch(Exception e) {
//            Log.e("eee","pas de réponse, pas de connexion");
//            logout();
        }
        /*************************************************************************************************************/

        return chain.proceed(newRequest);
    }


    public void logout() {
        LoginDataSource lds = new LoginDataSource();
        lds.logout();
        //startActivityLogin();

        Intent intentLogin = new Intent(MainActivity.getAppContext(), LoginActivity.class);
        MainActivity.getAppContext().startActivity(intentLogin);
    }
}
