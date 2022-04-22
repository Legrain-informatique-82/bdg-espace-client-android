package fr.legrain.bdg.espaceclient;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


public class AproposActivity extends LgrActivity {

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_propos);

        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();

        setTitle("A Propos");

//        IEspaceClientBdgService daoEspaceClient = new EspaceClientBdgService();
//        TaParamEspaceClientDTO taParamEspaceClientDTO = daoEspaceClient.parametres();
//
//        TextView tvEmail = findViewById(R.id.tvEmail);
//        TextView tvTelephone = findViewById(R.id.tvTelephone);
//
//        tvEmail.setText(taParamEspaceClientDTO.getContactEmail());
//        tvTelephone.setText(taParamEspaceClientDTO.getContactWeb());

        ImageView imgLegrain = findViewById(R.id.imgLegrain);
        imgLegrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivitySiteWeb("https://legrain.fr");
            }
        });

        ImageView imgBdg = findViewById(R.id.imgBdg);
        imgBdg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivitySiteWeb("https://bdg.cloud");
            }
        });
    }

}
