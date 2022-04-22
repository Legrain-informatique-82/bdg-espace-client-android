package fr.legrain.bdg.espaceclient;

import android.app.Application;
import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.stripe.android.PaymentConfiguration;

import fr.legrain.bdg.espaceclient.api.client.dao.IFactureBdgService;
import fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.FactureBdgService;
import fr.legrain.bdg.espaceclient.data.model.Parametre;

public class EspaceClientApp extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        EspaceClientApp.context = getApplicationContext();

        //        IFactureBdgService dao = new FactureBdgService();
//        Boolean paiementPossible = dao.fournisseurPermetPaiementParCB("demo", Parametre.getInstance().getEspaceClientDTO().getCodeTiers());
//        String publishableKey = null;
//        if(paiementPossible!=null && paiementPossible) {
//            publishableKey = dao.clePubliqueStripe();
//            if(publishableKey!=null && !publishableKey.equals("")) {
//                PaymentConfiguration.init(
//                        getApplicationContext(),
//                        publishableKey
//                );
//            }
//        }
    }

    public static Context getAppContext() {
        return EspaceClientApp.context;
    }

}