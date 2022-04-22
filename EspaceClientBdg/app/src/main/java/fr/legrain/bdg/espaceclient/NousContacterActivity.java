package fr.legrain.bdg.espaceclient;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import fr.legrain.bdg.espaceclient.api.client.dao.IEspaceClientBdgService;
import fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.EspaceClientBdgService;
import fr.legrain.bdg.espaceclient.data.model.TaParamEspaceClientDTO;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class NousContacterActivity extends LgrActivity {

    private static Context context;
    private ProgressBar spinner = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nous_contacter);

        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();

        setTitle("Nous contacter");

        IEspaceClientBdgService daoEspaceClient = new EspaceClientBdgService();

        TextView tvAdresseComplete = findViewById(R.id.tvAdresseComplete);
//        TextView tvAdresse2 = findViewById(R.id.tvAdresse2);
//        TextView tvAdresse3 = findViewById(R.id.tvAdresse3);
//        TextView tvCodePostal = findViewById(R.id.tvCodePostal);
//        TextView tvVille = findViewById(R.id.tvVille);
//        TextView tvPays = findViewById(R.id.tvPays);

        TextView tvEmail = findViewById(R.id.tvEmail);
        TextView tvTelephone = findViewById(R.id.tvTelephone);

        ImageView imgLogoEntreprise = findViewById(R.id.imgLogoEntreprise);

        daoEspaceClient.parametres()
                .subscribe(new Observer<TaParamEspaceClientDTO>() {
                    //             .subscribe(new Observer<ArticleDTO[]>() {
//                    ArticleDTO[] coinListh;
//                    List<ArticleDTO> la = null;
                    TaParamEspaceClientDTO dto;
                    List<TaParamEspaceClientDTO> la = null;
                    @Override
                    public void onSubscribe(Disposable d) {
                        spinner = (ProgressBar)findViewById(R.id.progressBar1);
                        spinner.setIndeterminate(true);
                        spinner.setVisibility(View.VISIBLE);
                    }

                    @Override
//                    public void onNext(ArticleDTO[] coinList) {
//                        la = new ArrayList<>(Arrays.asList(coinList));;
                    public void onNext(TaParamEspaceClientDTO coinList) {
                        // la = new ArrayList<>(Arrays.asList(coinList));;
                        //((TextView) findViewById(R.id.tvResultatTest)).setText("ARTICLES");
                        dto = coinList;
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {



                        if(dto!=null) {
                            //identitée
//                            if (dto.getCodeTiers() != null) {
//                                tvCodeTiers.setText("Code tiers : "+dto.getCodeTiers());
//                            } else {
//                                tvCodeTiers.setVisibility(View.GONE);
//                            }
//                            tvEntreprise.setText("Entreprise : "+dto.getCodeTEntite()!=null?dto.getCodeTEntite():""+" "+dto.getNomEntreprise()!=null?dto.getNomEntreprise():(dto.getNomTiers()!=null?dto.getNomTiers():""));
//                            tvNom.setText("Nom : "+dto.getCodeTCivilite()!=null?dto.getCodeTCivilite():""+" "+dto.getNomTiers()!=null?dto.getNomTiers():""+" "+dto.getPrenomTiers()!=null?dto.getPrenomTiers():"");

                            //adresse
                            if(dto.getAdresseComplete()!=null) tvAdresseComplete.setText(dto.getAdresseComplete());
                                //https://developers.google.com/maps/documentation/urls/android-intents#launch_turn-by-turn_navigation

                            if(dto.getAdresseComplete()!=null && !dto.getAdresseComplete().trim().equals("")) {
                                tvAdresseComplete.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Uri gmmIntentUri = Uri.parse("geo:0,0?q="+dto.getAdresseComplete());
                                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                        mapIntent.setPackage("com.google.android.apps.maps");
                                        startActivity(mapIntent);
                                    }
                                });
                            }

                            //communication
                            if(dto.getContactTel()!=null)
                                tvTelephone.setText(dto.getContactTel());

                            if(dto.getContactEmail()!=null)
                                tvEmail.setText(dto.getContactEmail());

                            //divers
                            if(dto.getContactWeb()!=null && !dto.getContactWeb().trim().equals("")) {
                                imgLogoEntreprise.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivitySiteWeb(dto.getContactWeb());
                                    }
                                });
                            }
                        }


                        spinner.setVisibility(View.INVISIBLE);
                    }
                });
    }
}
