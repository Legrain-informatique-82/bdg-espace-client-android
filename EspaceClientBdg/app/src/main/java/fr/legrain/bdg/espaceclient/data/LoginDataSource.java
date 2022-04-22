package fr.legrain.bdg.espaceclient.data;

import android.content.SharedPreferences;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.room.Room;
import fr.legrain.bdg.espaceclient.LgrActivity;
import fr.legrain.bdg.espaceclient.MainActivity;
import fr.legrain.bdg.espaceclient.api.client.dao.IEspaceClientBdgService;
import fr.legrain.bdg.espaceclient.api.client.dao.IUtilisateurBdgService;
import fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.EspaceClientBdgService;
import fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.UtilisateurBdgService;
import fr.legrain.bdg.espaceclient.api.client.dto.EspaceClientDTO;
import fr.legrain.bdg.espaceclient.api.client.dto.UtilisateurDTO;
import fr.legrain.bdg.espaceclient.data.model.LoggedInUser;
import fr.legrain.bdg.espaceclient.data.model.Parametre;
import fr.legrain.bdg.espaceclient.data.model.TaTiersDTO;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private boolean utiliseCache = false;
    private IUtilisateurBdgService daoUtilisateur = null;
    private IEspaceClientBdgService daoEspaceClient = null;

    public Result<LoggedInUser> login(String username, String password) {

        try {
            daoUtilisateur = new UtilisateurBdgService();
            daoEspaceClient = new EspaceClientBdgService();

            // TODO: handle loggedInUser authentication

//            List<Utilisateur> l = null;
//            EspaceClientD u = null;
            EspaceClientDTO dto = null;

            if(utiliseCache) {
//                AppDatabase db = Room.databaseBuilder(MainActivity.getAppContext(),
//                        AppDatabase.class, Parametre.CONST_DB_NAME)
//                        .allowMainThreadQueries()
//                        .build();
//                l = db.utilisateurDao().getAll();
//                u = db.utilisateurDao().findByEmail(username);

            } else {
                dto = daoEspaceClient.authenticate(username, password);
                if(dto!=null) {
//                    u = UtilisateurMapper.INSTANCE.utilisateurDtoToUtilisateur(dto);
                }

            }
            LoggedInUser fakeUser =

                new LoggedInUser(
                    String.valueOf(dto.getIdTiers()),
                    dto.getNom(),dto);
            Parametre.getInstance().setEspaceClientDTO(dto);
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication

        //SharedPreferences prefs = getSharedPreferences((LgrActivity.MY_PREFS_NAME, LgrActivity.MODE_PRIVATE);
        //String dernier = prefs.getString(LgrActivity.PARAM_KEY_LOGGED_IN_USER_NAME, null);

        Parametre.getInstance().setEspaceClientDTO(null);

        SharedPreferences.Editor editor = MainActivity.getAppContext().getSharedPreferences(LgrActivity.MY_PREFS_NAME, LgrActivity.MODE_PRIVATE).edit();
        editor.putString(LgrActivity.PARAM_KEY_LOGGED_IN_USER_NAME, null);
        editor.putString(LgrActivity.PARAM_KEY_LOGGED_IN_USER_ACCESS_TOKEN, null);
        editor.putString(LgrActivity.PARAM_KEY_LOGGED_IN_USER_REFRESH_TOKEN, null);
        editor.putString(LgrActivity.PARAM_KEY_LOGGED_IN_USER_OBJECT_JSON, null);
        editor.apply();

    }
}
