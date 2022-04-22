package fr.legrain.bdg.espaceclient;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Room;
import fr.legrain.bdg.espaceclient.api.client.dao.IEspaceClientBdgService;
import fr.legrain.bdg.espaceclient.api.client.dao.IUtilisateurBdgService;
import fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.EspaceClientBdgService;
import fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.UtilisateurBdgService;
import fr.legrain.bdg.espaceclient.api.client.dto.EspaceClientDTO;
import fr.legrain.bdg.espaceclient.api.client.dto.FactureDTO;
import fr.legrain.bdg.espaceclient.api.client.dto.UtilisateurDTO;
import fr.legrain.bdg.espaceclient.data.model.Const;
import fr.legrain.bdg.espaceclient.data.model.Parametre;
import fr.legrain.bdg.espaceclient.data.model.TDoc;
import fr.legrain.bdg.espaceclient.data.model.TaTiersDTO;
import fr.legrain.bdg.espaceclient.ui.avis_echeance.AvisEcheanceActivity;
import fr.legrain.bdg.espaceclient.ui.facture.FactureActivity;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;


public class MainActivity extends LgrActivity {

    private static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity.context = getApplicationContext();

        loadParam();

        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();

        SharedPreferences prefs = getSharedPreferences(LgrActivity.MY_PREFS_NAME, LgrActivity.MODE_PRIVATE);
        String loggedInUser = prefs.getString(LgrActivity.PARAM_KEY_LOGGED_IN_USER_NAME, null);

        if(loggedInUser==null) {
            startActivityLogin(getApplicationContext());
        } else {
            String json = prefs.getString(LgrActivity.PARAM_KEY_LOGGED_IN_USER_OBJECT_JSON, null);
            if(json!=null) {
                Moshi moshi = new Moshi.Builder()
                        .add(Date.class, new Rfc3339DateJsonAdapter().nullSafe())
                        .build();
                JsonAdapter<EspaceClientDTO> jsonAdapter = moshi.adapter(EspaceClientDTO.class);
                try {
                    EspaceClientDTO espaceClientDTO = jsonAdapter.fromJson(json);
                    Parametre.getInstance().setEspaceClientDTO(espaceClientDTO);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


//        Button btnNouveauPreparation = (Button) findViewById(R.id.btnNouveauPreparation);
//        btnNouveauPreparation.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v){
//                startActivityNouveauFlash(TDoc.TYPE_PREPARATION);
//            }
//        });
//
//        Button btnNouveauInventaire = (Button) findViewById(R.id.btnNouveauInventaire);
//        btnNouveauInventaire.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v){
//                startActivityNouveauFlash(TDoc.TYPE_INVENTAIRE);
//            }
//        });
//
//        Button btnNouveauFabrication = (Button) findViewById(R.id.btnNouveauFabrication);
//        btnNouveauFabrication.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v){
//                startActivityNouveauFlash(TDoc.TYPE_FABRICATION);
//            }
//        });

        Button btnTiers = (Button) findViewById(R.id.btnFacture);
        btnTiers.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                Intent intentTiers = new Intent(getBaseContext(), FactureActivity.class);
                startActivity(intentTiers);
            }
        });

        Button btnArticle = (Button) findViewById(R.id.btnAvisEcheance);
        btnArticle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                Intent intentArticle = new Intent(getBaseContext(), AvisEcheanceActivity.class);
                startActivity(intentArticle);
            }
        });

        /*
         Verification de la disponibilite de Google Play Service sur le terminal, notament pour accéder aux fonctionnalité de Firebase comme
         Firebase Cloud Messenging (FCM) pour les notification push.
         Si on souhaite utilisé uniquement le systeme de message de Firebase d'autre librairie peuvent le remplacer ou peut être directement des websocket ouvert depuis le serveur.
         */
        //GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(this); //déplacer dans le onResume uniquement

        createNotificationChannel();
        findFireBaseCloudMessagingToken();
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*
         Verification de la disponibilite de Google Play Service sur le terminal, notament pour accéder aux fonctionnalité de Firebase comme
         Firebase Cloud Messenging (FCM) pour les notification push.
         Si on souhaite utilisé uniquement le systeme de message de Firebase d'autre librairie peuvent le remplacer ou peut être directement des websocket ouvert depuis le serveur.
         */
        GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==123) { //activité de login termine
            if(resultCode== Activity.RESULT_OK) { // login ok
                findFireBaseCloudMessagingToken(); //relancer la liaison d'un token pour le push maintenant que l'utilisateur est identifié

                if(Parametre.getInstance().isModeMultiCompteTiers()) {
                    startActivityChoixCompteTiers(null);
                }
            }
        }
    }

    public static Context getAppContext() {
        return MainActivity.context;
    }

    public String findFireBaseCloudMessagingToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Cle FCM", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        MyFirebaseMessagingService myFirebaseMessagingService = new MyFirebaseMessagingService();
                        myFirebaseMessagingService.miseAJourTokenSurBdg(getAppContext(),token);
                    }
                });
        return null;
    }

    public static final int CHANNEL_ID = 1;
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = getString(R.string.channel_name);
//            String description = getString(R.string.channel_description);
            CharSequence name = "Nom chaine";
            String description = "Description chaine";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(getString(R.string.default_notification_channel_id), name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }



}
