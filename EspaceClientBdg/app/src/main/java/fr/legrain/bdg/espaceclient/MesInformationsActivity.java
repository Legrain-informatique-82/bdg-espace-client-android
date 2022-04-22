package fr.legrain.bdg.espaceclient;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import fr.legrain.bdg.espaceclient.api.client.dao.IEspaceClientBdgService;
import fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.EspaceClientBdgService;
import fr.legrain.bdg.espaceclient.data.model.Parametre;
import fr.legrain.bdg.espaceclient.data.model.TaTiersDTO;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class MesInformationsActivity extends LgrActivity {

    private static Context context;
    private ProgressBar spinner = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_informations);

        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();

        setTitle("Mes informations");
    }

    @Override
    public void onResume() {

        super.onResume();
        initData();
    }

    public void initData(){
        TextView tvCodeTiers = findViewById(R.id.tvCodeTiers);
        TextView tvEntreprise = findViewById(R.id.tvEntreprise);
        TextView tvNom = findViewById(R.id.tvNom);

        TextView tvAdresse1 = findViewById(R.id.tvAdresse1);
        TextView tvAdresse2 = findViewById(R.id.tvAdresse2);
        TextView tvAdresse3 = findViewById(R.id.tvAdresse3);
        TextView tvCodePostal = findViewById(R.id.tvCodePostal);
        TextView tvVille = findViewById(R.id.tvVille);
        TextView tvPays = findViewById(R.id.tvPays);

        TextView tvEmail = findViewById(R.id.tvEmail);
        TextView tvTelephone = findViewById(R.id.tvTelephone);

        IEspaceClientBdgService daoEspaceClient = new EspaceClientBdgService();

//        TaTiersDTO taTiersDTO = daoEspaceClient.infosClientChezFournisseurDtoSync(Parametre.getInstance().getEspaceClientDTO().getCodeTiers());
//        tvEmail.setText(taTiersDTO.getAdresseEmail());
//        tvTelephone.setText(taTiersDTO.getNomTiers());

        daoEspaceClient.infosClientChezFournisseurDTO(Parametre.getInstance().getEspaceClientDTO().getCodeTiers())
                .subscribe(new Observer<TaTiersDTO>() {
                    //             .subscribe(new Observer<ArticleDTO[]>() {
//                    ArticleDTO[] coinListh;
//                    List<ArticleDTO> la = null;
                    TaTiersDTO dto;
                    List<TaTiersDTO> la = null;
                    @Override
                    public void onSubscribe(Disposable d) {
                        spinner = (ProgressBar)findViewById(R.id.progressBar1);
                        spinner.setIndeterminate(true);
                        spinner.setVisibility(View.VISIBLE);
                    }

                    @Override
//                    public void onNext(ArticleDTO[] coinList) {
//                        la = new ArrayList<>(Arrays.asList(coinList));;
                    public void onNext(TaTiersDTO coinList) {
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
                            if (dto.getCodeTiers() != null) {
                                tvCodeTiers.setText("Code tiers : "+dto.getCodeTiers());
                            } else {
                                tvCodeTiers.setVisibility(View.GONE);
                            }
                            tvEntreprise.setText("Entreprise : "+dto.getCodeTEntite()!=null?dto.getCodeTEntite():""+" "+dto.getNomEntreprise()!=null?dto.getNomEntreprise():(dto.getNomTiers()!=null?dto.getNomTiers():""));
                            tvNom.setText("Nom : "+(dto.getCodeTCivilite()!=null?dto.getCodeTCivilite():"")+" "+(dto.getNomTiers()!=null?dto.getNomTiers():"")+" "+(dto.getPrenomTiers()!=null?dto.getPrenomTiers():""));

                            //adresse
                            tvAdresse1.setText(dto.getAdresse1Adresse());
                            tvAdresse2.setText(dto.getAdresse2Adresse());
                            tvAdresse3.setText(dto.getAdresse3Adresse());
                            tvCodePostal.setText(dto.getCodepostalAdresse());
                            tvVille.setText(dto.getVilleAdresse());
                            tvPays.setText(dto.getPaysAdresse());

                            //communication
                            if(dto.getNumeroTelephone()!=null)
                                tvTelephone.setText("Téléphone : "+dto.getNumeroTelephone());

                            if(dto.getAdresseEmail()!=null)
                                tvEmail.setText("Email : "+dto.getAdresseEmail());

                            //divers
                                 /*
                        <p-fieldset legend="Divers">
                            <p *ngIf="tiers && tiers.siretCompl">SIRET : {{tiers.siretCompl}}</p>
                            <p *ngIf="tiers && tiers.tvaIComCompl">Num TVA : {{tiers.tvaIComCompl}}</p>
                        </p-fieldset>
                         */
                        }

//                        CacheArticleRoom ctr = new CacheArticleRoom(getBaseContext(),progressBar);
//                        ctr.cacheBdd(la);
//                        articleOK[0] = true;
//                        if(progressBar!=null && tiersOK[0] && articleOK[0]) {
//                            progressBar.setVisibility(View.GONE);
//                            Toast.makeText(getApplicationContext(), "Reconstruction du cache terminée.", Toast.LENGTH_SHORT).show();
//                        }

                        spinner.setVisibility(View.INVISIBLE);
                    }
                });
    }
}
