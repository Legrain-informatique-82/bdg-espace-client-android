package fr.legrain.bdg.espaceclient;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import fr.legrain.bdg.espaceclient.api.client.dao.IEspaceClientBdgService;
import fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.EspaceClientBdgService;
import fr.legrain.bdg.espaceclient.data.model.ParamFirebaseCloudMessaging;
import fr.legrain.bdg.espaceclient.data.model.Parametre;
import fr.legrain.bdg.espaceclient.ui.facture.FactureActivity;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/*
https://firebase.google.com/docs/cloud-messaging/android/client
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public MyFirebaseMessagingService() {
    }

    private Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = EspaceClientApp.getAppContext();
    }

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String token) {
        Log.d("", "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.

//        sendRegistrationToServer(token);

        miseAJourTokenSurBdg(token);
    }

    private void miseAJourTokenSurBdg(String nouveauToken) {
        miseAJourTokenSurBdg(this,nouveauToken);
    }

    public void miseAJourTokenSurBdg(Context context, String nouveauToken) {
        SharedPreferences prefs = context.getSharedPreferences(LgrActivity.MY_PREFS_NAME, LgrActivity.MODE_PRIVATE);
        String loggedInUser = prefs.getString(LgrActivity.PARAM_KEY_LOGGED_IN_USER_NAME, null);
        String ancienToken = prefs.getString(LgrActivity.PARAM_KEY_ANDROID_REGISTRATION_TOKEN_FIREBASE, null);

        //String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        if(loggedInUser!=null) {
//                            AppDatabase db = Room.databaseBuilder(MainActivity.this,
//                                    AppDatabase.class, Parametre.CONST_DB_NAME)
//                                    .allowMainThreadQueries()
//                                    .build();
//                            Utilisateur u = db.utilisateurDao().findByEmail(loggedInUser);
//                            u.setAndroidRegistrationToken(token);
//                            db.utilisateurDao().updateUtilisateur(u);
//                            db.close();

            SharedPreferences.Editor editor = context.getSharedPreferences(LgrActivity.MY_PREFS_NAME, LgrActivity.MODE_PRIVATE).edit();
            editor.putString(LgrActivity.PARAM_KEY_ANDROID_REGISTRATION_TOKEN_FIREBASE, nouveauToken);
            editor.apply();
//
//                            UtilisateurDTO dto = UtilisateurMapper.INSTANCE.utilisateurToUtilisateurDto(u);
//                            IUtilisateurBdgService dao = new UtilisateurBdgService();
//                            dao.merge(dto);

//                              UtilisateurDTO dto = UtilisateurMapper.INSTANCE.utilisateurToUtilisateurDto(u);
//                            IUtilisateurBdgService dao = new UtilisateurBdgService();
//                            dao.merge(dto);

            IEspaceClientBdgService daoEspaceClient = new EspaceClientBdgService();
            ParamFirebaseCloudMessaging param = new ParamFirebaseCloudMessaging();
            param.setAncienToken(ancienToken);
            param.setNouveauToken(nouveauToken);
            daoEspaceClient.updateAndroidRegistrationToken(Parametre.getInstance().getEspaceClientDTO().getId(),param)
           // daoEspaceClient.updateAndroidRegistrationToken(Parametre.getInstance().getEspaceClientDTO().getId(),nouveauToken)
                    .subscribe(new Observer<ResponseBody>() {
                        @Override
                        public void onSubscribe(Disposable d) { }

                        @Override
                        public void onNext(ResponseBody listeDto) { }

                        @Override
                        public void onError(Throwable e) { }

                        @Override
                        public void onComplete() {}
                    });

        }




        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
//                        Log.d("", msg);
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
        Log.d("Cle FCM", nouveauToken);
    }

    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d("", "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d("", "Message data payload: " + remoteMessage.getData());

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use WorkManager.
//                scheduleJob();
            } else {
                // Handle message within 10 seconds
//                handleNow();
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d("", "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.


        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, MainActivity.class);
        //Intent intent = new Intent(this, FactureActivity.class); //Attention, si l'application n'est pas lancé, il peut y avoir des choses initialisé dans l'activité principale qui sont nulle
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);



        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getString(R.string.default_notification_channel_id))
                //.setSmallIcon(R.drawable.ic_logo_bdg)
                .setSmallIcon(R.drawable.ic_notif)
                .setContentTitle(remoteMessage.getData().get("titre"))
                .setContentText(remoteMessage.getData().get("resume"))
                .setContentIntent(pendingIntent) // Set the intent that will fire when the user taps the notification
                .setAutoCancel(true) // Supprime automatiquement la notification après avoir cliqué dessus
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if(remoteMessage.getData().get("contenu")!=null) {
            builder.setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(remoteMessage.getData().get("contenu")));
        }

        if(remoteMessage.getData().get("image")!=null) {
//            byte[] decodedString = Base64.decode(remoteMessage.getData().get("image"), Base64.DEFAULT);
//            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


            Bitmap bitmap = getBitmapFromURL(remoteMessage.getData().get("image"));

            builder.setStyle(new NotificationCompat.BigPictureStyle()
                    .bigPicture(bitmap)
                    .bigLargeIcon(null));
        }

        if(remoteMessage.getData().get("url")!=null) {
//            Intent snoozeIntent = new Intent(this, MyBroadcastReceiver.class);
//            snoozeIntent.setAction(ACTION_SNOOZE);
//            snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);
//            PendingIntent snoozePendingIntent =
//                    PendingIntent.getBroadcast(this, 0, snoozeIntent, 0);


            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(remoteMessage.getData().get("url")));
            PendingIntent pendingIntent2 = PendingIntent.getActivity(this, 0, i, 0);

            builder.addAction(R.drawable.ic_notif, "Ouvrir", pendingIntent2);

        }

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

    // notificationId is a unique int for each notification that you must define
        //Remember to save the notification ID that you pass to NotificationManagerCompat.notify() because you'll need it later if you want to update or remove the notification.
        int notificationId = 1;
        notificationManager.notify(notificationId, builder.build());


    }



    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }


}
