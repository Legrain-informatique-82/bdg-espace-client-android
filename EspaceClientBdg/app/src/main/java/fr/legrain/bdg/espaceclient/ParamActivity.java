package fr.legrain.bdg.espaceclient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.core.content.FileProvider;

import fr.legrain.bdg.espaceclient.api.client.dao.IEspaceClientBdgService;
import fr.legrain.bdg.espaceclient.api.client.dao.IUtilisateurBdgService;

import fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.EspaceClientBdgService;
import fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.UtilisateurBdgService;

import fr.legrain.bdg.espaceclient.api.client.dto.AutorisationDossierDTO;
import fr.legrain.bdg.espaceclient.api.client.dto.EspaceClientDTO;

import fr.legrain.bdg.espaceclient.api.client.dto.UtilisateurDTO;
import fr.legrain.bdg.espaceclient.data.model.Const;
import fr.legrain.bdg.espaceclient.data.model.Parametre;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ParamActivity extends LgrActivity {

    private ProgressBar progressBar = null;
    private Parametre p = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.param);

        this.configureToolBar();

        this.configureDrawerLayout();

        this.configureNavigationView();

        final EditText etParamBaseUrl = (EditText) findViewById(R.id.etParamBaseUrl);
        final EditText etParamDossier = (EditText) findViewById(R.id.etParamDossier);
        final EditText etParamCleApiDossier = (EditText) findViewById(R.id.etParamCleApiDossier);
        final EditText etParamCleApiUtilisateur = (EditText) findViewById(R.id.etParamCleApiUtilisateur);
        final EditText etParamLogin = (EditText) findViewById(R.id.etParamLoginApi);
        final EditText etParamPassword = (EditText) findViewById(R.id.etParamPasswordApi);

//        final CheckBox cbParamValidationLigneAuto = (CheckBox) findViewById(R.id.cbParamValidationLigneAuto);
        final CheckBox cbModeConnecte = (CheckBox) findViewById(R.id.cbModeConnecte);
//        final CheckBox cbNePasConserverApresTransfert = (CheckBox) findViewById(R.id.cbNePasConserverApresTransfert);
//
//        final RadioButton rbRegroupeAucun = (RadioButton) findViewById(R.id.radio_regroupe_aucun);
//        final RadioButton rbRegroupeArticle = (RadioButton) findViewById(R.id.radio_regroupe_article);
//        final RadioButton rbRegroupeLot = (RadioButton) findViewById(R.id.radio_regroupe_lot);
//
//        final RadioButton rbTypeEan13 = (RadioButton) findViewById(R.id.type_ean13);
//        final RadioButton rbTypeEan128 = (RadioButton) findViewById(R.id.type_ean128);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        p = loadParam();
        etParamBaseUrl.setText(p.getBaseUrl());
        etParamDossier.setText(p.getDossier());
        etParamCleApiDossier.setText(p.getApiKeyDossier());
        etParamCleApiUtilisateur.setText(p.getApiKeyUtilisateur());
        etParamLogin.setText(p.getLogin());
        etParamPassword.setText(p.getPassword());

//        cbParamValidationLigneAuto.setChecked(p.isLigneSuivanteAuto());
        cbModeConnecte.setChecked(p.isModeConnecteUniquement());
//        cbNePasConserverApresTransfert.setChecked(p.isEffacerFlashApresTransfert());

//        if(p.getModeRegroupement()==LgrActivity.PARAM_VALUE_MODE_REGROUPEMENT_AUCUN) {
//            rbRegroupeAucun.setChecked(true);
//        } else if(p.getModeRegroupement()==LgrActivity.PARAM_VALUE_MODE_REGROUPEMENT_ARTICLE) {
//            rbRegroupeArticle.setChecked(true);
//        } else if(p.getModeRegroupement()==LgrActivity.PARAM_VALUE_MODE_REGROUPEMENT_LOT) {
//            rbRegroupeLot.setChecked(true);
//        }
//
//        if(p.getModeCodeBarre()==LgrActivity.PARAM_VALUE_TYPE_CODE_BARRE_EAN13) {
//            rbTypeEan13.setChecked(true);
//        } else if(p.getModeCodeBarre()==LgrActivity.PARAM_VALUE_TYPE_CODE_BARRE_EAN128) {
//            rbTypeEan128.setChecked(true);
//        }

        Button btnEnregistrerParam = (Button) findViewById(R.id.btnEnregistrerParam);
        btnEnregistrerParam.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                p = Parametre.getInstance();
                p.setBaseUrl(etParamBaseUrl.getText().toString());
                p.setDossier(etParamDossier.getText().toString());
                p.setApiKeyDossier(etParamCleApiDossier.getText().toString());
                p.setApiKeyUtilisateur(etParamCleApiDossier.getText().toString());
                p.setLogin(etParamLogin.getText().toString());
                p.setPassword(etParamPassword.getText().toString());

//                p.setLigneSuivanteAuto(cbParamValidationLigneAuto.isChecked());
                p.setModeConnecteUniquement(cbModeConnecte.isChecked());

//                if(rbRegroupeAucun.isChecked()) {
//                    p.setModeRegroupement(LgrActivity.PARAM_VALUE_MODE_REGROUPEMENT_AUCUN);
//                } else if(rbRegroupeArticle.isChecked()) {
//                    p.setModeRegroupement(LgrActivity.PARAM_VALUE_MODE_REGROUPEMENT_ARTICLE);
//                } else if(rbRegroupeLot.isChecked()) {
//                    p.setModeRegroupement(LgrActivity.PARAM_VALUE_MODE_REGROUPEMENT_LOT);
//                }
//
//                if(rbTypeEan13.isChecked()) {
//                    p.setModeCodeBarre(LgrActivity.PARAM_VALUE_TYPE_CODE_BARRE_EAN13);
//                } else if(rbTypeEan128.isChecked()) {
//                    p.setModeCodeBarre(LgrActivity.PARAM_VALUE_TYPE_CODE_BARRE_EAN128);
//                }

                saveParam(p);

                Toast.makeText(getApplicationContext(), "Enregistrer les paramètres", Toast.LENGTH_SHORT).show();

                Intent returnIntent = new Intent();
                //returnIntent.putExtra("result",data);
                //returnIntent.putExtra("bool",true);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });


        Button btnReconstruireCache = (Button) findViewById(R.id.btnReconstruireCache);
        btnReconstruireCache.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){

                AlertDialog.Builder alert = new AlertDialog.Builder(ParamActivity.this);
                alert.setTitle("Attention");
                alert.setMessage("Etes vous sur de vouloir reconstruire le cache maintenant ?\n" +
                        "Cela aura pour effet d'effacer l'intégralité de la base de données de l'application sur ce terminal mobile.\n" +
                        "Il est conseillé de transférer tous les éléments flasher avant de continuer");

                alert.setPositiveButton("Reconstruire le cache", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        onClickCache();
                    }
                });

                alert.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alert.show();

            }
        });



        Button btnExporterBddEmail = (Button) findViewById(R.id.btnExporterBddEmail);
        btnExporterBddEmail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                email();
            }
        });

        Button btnOuvrirDossier = (Button) findViewById(R.id.btnOuvrirDossier);
        btnOuvrirDossier.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                String url = etParamBaseUrl.getText().toString();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        final TextView tvResultatTest = (TextView) findViewById(R.id.tvResultatTest);
        Button btnTestConnection = (Button) findViewById(R.id.btnTestConnection);
        btnTestConnection.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){

                TestConnectionInternetTask t = new TestConnectionInternetTask();
                t.execute();

                Boolean s =  null;
                Boolean s2 =  null;
                try {
                    s = t.get();

                    if(s!=null && s) {
                        tvResultatTest.setText("internet OK");

                    } else {
                        tvResultatTest.setText("pas d'internet");
                    }
                } catch (Exception e) {
                    tvResultatTest.setText("ERREUR");
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
                }

                if(s!=null && s) {
                    TestConnectionServeurBdgTask ta = new TestConnectionServeurBdgTask();
                    ta.execute();

                    try {

                        s2 = ta.get();

                        if(s2!=null && s2) {
                            tvResultatTest.setText("internet OK | serveur BDG OK");

                        } else {
                            tvResultatTest.setText("internet OK | serveur BDG innacecible");
                        }
                    } catch (Exception e) {
                        tvResultatTest.setText("internet OK | serveur BDG innacecible");
                        s2 = false;
                        e.printStackTrace();
                    }

                }
                //s2 = true;

                if((s!=null && s) && (s2!=null && s2)) {
//                    TestConnectionServeurTask t2 = new TestConnectionServeurTask();
//                    t2.execute();
                    try {

                        IEspaceClientBdgService dao = new EspaceClientBdgService();
                        EspaceClientDTO l = dao.authenticate(p.getLogin(),p.getPassword());
                        if(l!=null) {
                            tvResultatTest.setText("internet OK | serveur BDG OK | connexion OK");
                        } else {
                            tvResultatTest.setText("internet OK | serveur BDG OK | echec connexion");
                        }

                    } catch (Exception e) {
                        tvResultatTest.setText("internet OK | serveur BDG OK | ERREUR");
                              e.printStackTrace();
                    }
                }
            }
        });

        if(!Const.debug) {
            etParamBaseUrl.setEnabled(false);
            etParamDossier.setEnabled(false);
            btnOuvrirDossier.setEnabled(false);
            cbModeConnecte.setEnabled(false);
            btnExporterBddEmail.setEnabled(false);
            btnReconstruireCache.setEnabled(false);
        }

    }

    public void onClickCache() {
        Toast.makeText(getApplicationContext(), "Reconstruction de cache en cours ...", Toast.LENGTH_SHORT).show();
//        ParamActivity.this.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
////                        progressBar.setVisibility(View.VISIBLE);
////                        progressBar.setIndeterminate(true);
//            }
//        });
////                BdgCRUD crud = new BdgCRUD(getBaseContext());
////                crud.recreerBaseVide();
//
//        //getBaseContext().deleteDatabase("bdg");
//        getBaseContext().deleteDatabase(Parametre.CONST_DB_NAME);
//
//        final Boolean[] tiersOK = {false};
//        final Boolean[] articleOK = {false};
//
//        ITiersBdgService daoTiers = new TiersBdgService();
//        // List<TiersDTO> lt =  daoTiers.selectAll();
//
//        progressBar.setVisibility(View.VISIBLE);
//        progressBar.setIndeterminate(true);
//
//        daoTiers.selectAll()
//                .subscribe(new Observer<TiersDTO[]>() {
//                    TiersDTO[] coinListh;
//                    List<TiersDTO> lt = null;
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        if(progressBar!=null) {
//                            progressBar.setVisibility(View.VISIBLE);
//                            progressBar.setIndeterminate(true);
//                            // ((TextView) findViewById(R.id.tvResultatTest)).setText("TIERS");
//                        }
//                    }
//
//                    @Override
//                    public void onNext(TiersDTO[] coinList) {
//                        lt = new ArrayList<>(Arrays.asList(coinList));;
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        CacheTiersRoom ctr = new CacheTiersRoom(getBaseContext(),progressBar);
//                        ctr.cacheBdd(lt);
//                        tiersOK[0] = true;
//                        if(progressBar!=null && tiersOK[0] && articleOK[0]) {
//                            progressBar.setVisibility(View.GONE);
//                            Toast.makeText(getApplicationContext(), "Reconstruction du cache terminée.", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
////                CacheTiers ct = new CacheTiers(getBaseContext());
////                ct.cacheBdd(lt);
//
//
//        IArticleBdgService daoArticle = new ArticleBdgService();
//        //List<ArticleDTO> la =  daoArticle.selectAllSync();
//
//        daoArticle.selectAll()
//                .subscribe(new Observer<ArticleDTO[]>() {
//                    ArticleDTO[] coinListh;
//                    List<ArticleDTO> la = null;
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        if(progressBar!=null) {
//                            progressBar.setVisibility(View.VISIBLE);
//                            progressBar.setIndeterminate(true);
//
//                        }
//                    }
//
//                    @Override
//                    public void onNext(ArticleDTO[] coinList) {
//                        la = new ArrayList<>(Arrays.asList(coinList));;
//                        //((TextView) findViewById(R.id.tvResultatTest)).setText("ARTICLES");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        CacheArticleRoom ctr = new CacheArticleRoom(getBaseContext(),progressBar);
//                        ctr.cacheBdd(la);
//                        articleOK[0] = true;
//                        if(progressBar!=null && tiersOK[0] && articleOK[0]) {
//                            progressBar.setVisibility(View.GONE);
//                            Toast.makeText(getApplicationContext(), "Reconstruction du cache terminée.", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
////                CacheArticleRoom car = new CacheArticleRoom(getBaseContext(),progressBar);
////                car.cacheBdd(la);
//
//                IUtilisateurBdgService daoUtilisateur = new UtilisateurBdgService();
//                List<UtilisateurDTO> lu =  daoUtilisateur.selectAll();
////                CacheArticle ca = new CacheArticle(getBaseContext());
////                ca.cacheBdd(la);
//                CacheUtilisateurRoom cur = new CacheUtilisateurRoom(getBaseContext());
//                cur.cacheBdd(lu);
//
//                //IUtilisateurBdgService daoUtilisateur = new UtilisateurBdgService();
//               // List<AutorisationDossierDTO> lauth =  daoUtilisateur.findAutorisationDossier();
//                AutorisationDossierDTO lauth =  daoUtilisateur.findAutorisationDossier();
//                CacheAutorisationDossierRoom cauthr = new CacheAutorisationDossierRoom(getBaseContext());
//                List<AutorisationDossierDTO> ll = new ArrayList<>();
//                ll.add(lauth);
//                cauthr.cacheBdd(ll);
//
//        ParamActivity.this.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
////                        progressBar.setVisibility(View.GONE);
//            }
//        });
    }

//    public void onRadioButtonRegroupementClicked(View view) {
//        // Is the button now checked?
//        boolean checked = ((RadioButton) view).isChecked();
//
//        // Check which radio button was clicked
//        switch(view.getId()) {
//            case R.id.radio_regroupe_aucun:
//                if (checked)
//                    //....
//                    break;
//            case R.id.radio_regroupe_article:
//                if (checked)
//                    ///....
//                    break;
//            case R.id.radio_regroupe_lot:
//                if (checked)
//                    //....
//                    break;
//        }
//    }

//    public void onRadioButtonTypeCodeBarreClicked(View view) {
//        // Is the button now checked?
//        boolean checked = ((RadioButton) view).isChecked();
//
//        // Check which radio button was clicked
//        switch(view.getId()) {
//            case R.id.type_ean13:
//                if (checked)
//                    //....
//                    break;
//            case R.id.type_ean128:
//                if (checked)
//                    //....
//                    break;
//        }
//    }

    public void email() {
       // BdgCRUD crud = new BdgCRUD(getBaseContext());
        //crud.exportDatabse("bdg");

        //String filename="backupname.db";
        String filename="/data/fr.legrain.bdg/databases//"+"bdg.db"+"";
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("*/*");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"nicolas@legrain.fr"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Bureau de Gestion - Android");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Exportation de la base de données Bureau de Gestion - Android");
        emailIntent.putExtra(Intent.EXTRA_STREAM,
                FileProvider.getUriForFile(ParamActivity.this, BuildConfig.APPLICATION_ID + ".provider", new File(Environment.getDataDirectory(),filename))
        );
        startActivity(Intent.createChooser(emailIntent, "Envoyer la base de données pas E-Mail ..."));
    }

    class TestConnectionInternetTask extends AsyncTask<String, Void, Boolean> {

        private Exception exception;

        protected Boolean doInBackground(String... urls) {
            try {
                Socket sock = new Socket();
                sock.connect(new InetSocketAddress("8.8.8.8", 53), 1500);
                sock.close();
                return true;
            } catch (IOException e) { return false; }
        }

        protected void onPostExecute(List<EspaceClientDTO> feed) {
            // TODO: check this.exception
            // TODO: do something with the feed
        }
    }

    class TestConnectionServeurBdgTask extends AsyncTask<String, Void, Boolean> {

        private Exception exception;

        protected Boolean doInBackground(String... urls) {
            try {
                Socket sock = new Socket();
                sock.connect(new InetSocketAddress(
                        Const.urlConnexionBdg.
                                replace("https://","").
                                replace("http://","").
                                replace(":"+(Const.portConnexionBdg!=null?Const.portConnexionBdg:""),""),
                        Integer.valueOf(Const.portConnexionBdg!=null?Const.portConnexionBdg:"80")
                ), 1500);
                sock.close();
                return true;
            } catch (IOException e) {
                return false;
            }
        }

        protected void onPostExecute(List<EspaceClientDTO> feed) {
            // TODO: check this.exception
            // TODO: do something with the feed
        }
    }

    class TestConnectionServeurTask extends AsyncTask<String, Void, Boolean> {

        private Exception exception;

        protected Boolean doInBackground(String... urls) {
            try {
                IEspaceClientBdgService dao = new EspaceClientBdgService();
                dao.authenticate(p.getApiKeyDossier(),p.getApiKeyUtilisateur());
                return true;
            } catch (Exception e) { return false; }
        }

        protected void onPostExecute(List<EspaceClientDTO> feed) {
            // TODO: check this.exception
            // TODO: do something with the feed
        }
    }
}
