package fr.legrain.bdg.espaceclient;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.Stripe;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.view.CardInputWidget;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import fr.legrain.bdg.espaceclient.api.client.dao.IEspaceClientBdgService;
import fr.legrain.bdg.espaceclient.api.client.dao.IFactureBdgService;
import fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.EspaceClientBdgService;
import fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.FactureBdgService;
import fr.legrain.bdg.espaceclient.api.client.dto.EspaceClientDTO;
import fr.legrain.bdg.espaceclient.data.model.ParamPaiement;
import fr.legrain.bdg.espaceclient.data.model.Parametre;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;


public class LgrCheckoutActivity extends LgrActivity {

    private static Context context;

    private OkHttpClient httpClient = new OkHttpClient();
    private String paymentIntentClientSecret;
    private String stripePublishableKey = null;
    private Stripe stripe;
    private ParamPaiement paramPaiement = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        LgrCheckoutActivity.context = getApplicationContext();

        TextView tvLibellePaiement = (TextView) findViewById(R.id.tvLibellePaiement);
        TextView tvMontantPaiementTTC = (TextView) findViewById(R.id.tvMontantPaiementTTC);


        Intent myIntent = getIntent(); // gets the previously created intent
        //String paymentIntentClientSecret = myIntent.getStringExtra("paymentSecret");
        paramPaiement = (ParamPaiement) myIntent.getSerializableExtra("paramPaiement");

        //this.configureToolBar();
        //this.configureDrawerLayout();
        //this.configureNavigationView();

        tvLibellePaiement.setText("Montant total à payer (TTC)");
        tvMontantPaiementTTC.setText(paramPaiement.getMontant().toPlainString()+" €");

        startCheckout();

    }

    private void startCheckout() {

        /*
        https://stripe.com/docs/mobile/android/basic#collect-details
        https://stripe.com/docs/terminal/sdk/android
        https://stripe.com/docs/testing#regulatory-cards
         */

        //IFactureBdgService dao = new FactureBdgService();
        IEspaceClientBdgService daoEspaceClient = new EspaceClientBdgService();

        stripePublishableKey = daoEspaceClient.clePubliqueStripe();
        paymentIntentClientSecret = daoEspaceClient.creerPaymentIntent(paramPaiement);

        stripe = new Stripe(
                getApplicationContext(),
                Objects.requireNonNull(stripePublishableKey)
        );

        /*TODO
        https://stripe.com/docs/payments/accept-a-payment#android-create-server-side
         */

        Button payButton = findViewById(R.id.payButton);
        payButton.setOnClickListener((View view) -> {
            CardInputWidget cardInputWidget = findViewById(R.id.cardInputWidget);
            PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();
            if (params != null) {
                ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
                        .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret);
                stripe.confirmPayment(this, confirmParams);
            }
        });
    }

    private void displayAlert(@NonNull String title,
                              @Nullable String message
                              /*,boolean restartDemo*/) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message);
//        if (restartDemo) {
//            builder.setPositiveButton("Restart demo",
//                    (DialogInterface dialog, int index) -> {
//                        CardInputWidget cardInputWidget = findViewById(R.id.cardInputWidget);
//                        cardInputWidget.clear();
//                        startCheckout();
//                    });
//        } else {
            builder.setPositiveButton("Ok",
                        (DialogInterface dialog, int index) -> {
                            finish();
                        }
                    );
//        }
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Handle the result of stripe.confirmPayment
        stripe.onPaymentResult(requestCode, data, new PaymentResultCallback(this));
    }

    private void onPaymentSuccess(@NonNull final Response response) throws IOException {
//        Gson gson = new Gson();
//        Type type = new TypeToken<Map<String, String>>(){}.getType();
//        Map<String, String> responseMap = gson.fromJson(
//                Objects.requireNonNull(response.body()).string(),
//                type
//        );
//
//        // The response from the server includes the Stripe publishable key and
//        // PaymentIntent details.
//        // For added security, our sample app gets the publishable key from the server
//        String stripePublishableKey = responseMap.get("publishableKey");
//        paymentIntentClientSecret = responseMap.get("clientSecret");

        // Configure the SDK with your Stripe publishable key so that it can make requests to the Stripe API
        stripe = new Stripe(
                getApplicationContext(),
                Objects.requireNonNull(stripePublishableKey)
        );
    }

    private static final class PayCallback implements Callback {
        @NonNull private final WeakReference<LgrCheckoutActivity> activityRef;

        PayCallback(@NonNull LgrCheckoutActivity activity) {
            activityRef = new WeakReference<>(activity);
        }

        @Override
        public void onFailure(@NonNull Call call, @NonNull IOException e) {
            final LgrCheckoutActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            activity.runOnUiThread(() ->
                    Toast.makeText(
                            activity, "Error: " + e.toString(), Toast.LENGTH_LONG
                    ).show()
            );
        }

        @Override
        public void onResponse(@NonNull Call call, @NonNull final Response response)
                throws IOException {
            final LgrCheckoutActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            if (!response.isSuccessful()) {
                activity.runOnUiThread(() ->
                        Toast.makeText(
                                activity, "Error: " + response.toString(), Toast.LENGTH_LONG
                        ).show()
                );
            } else {
                activity.onPaymentSuccess(response);
            }
        }
    }

    private static final class PaymentResultCallback implements ApiResultCallback<PaymentIntentResult> {
        @NonNull private final WeakReference<LgrCheckoutActivity> activityRef;

        PaymentResultCallback(@NonNull LgrCheckoutActivity activity) {
            activityRef = new WeakReference<>(activity);
        }

        @Override
        public void onSuccess(@NonNull PaymentIntentResult result) {
            final LgrCheckoutActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            PaymentIntent paymentIntent = result.getIntent();
            PaymentIntent.Status status = paymentIntent.getStatus();
            if (status == PaymentIntent.Status.Succeeded) {
                // Payment completed successfully
               // Gson gson = new GsonBuilder().setPrettyPrinting().create();
                activity.displayAlert(
                        "Paiement",
                        "Le paiement a été réalisé avec succès."/*,//,gson.toJson(paymentIntent),
                        true*/
                );
            } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {
                // Payment failed – allow retrying using a different payment method
                activity.displayAlert(
                        "Erreur lors du paiement.",
                        Objects.requireNonNull(paymentIntent.getLastPaymentError()).getMessage()/*,
                        false*/
                );
            }
        }

        @Override
        public void onError(@NonNull Exception e) {
            final LgrCheckoutActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            // Payment request failed – allow retrying using the same payment method
            activity.displayAlert("Erreur", e.toString()/*, false*/);
        }
    }


}
