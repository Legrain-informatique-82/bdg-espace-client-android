package fr.legrain.bdg.espaceclient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import fr.legrain.bdg.espaceclient.api.client.dao.IEspaceClientBdgService;
import fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.EspaceClientBdgService;
import fr.legrain.bdg.espaceclient.data.LoginDataSource;
import fr.legrain.bdg.espaceclient.data.model.Const;
import fr.legrain.bdg.espaceclient.data.model.Parametre;
import fr.legrain.bdg.espaceclient.data.model.TDoc;
import fr.legrain.bdg.espaceclient.data.model.TaTiersDTO;
import fr.legrain.bdg.espaceclient.ui.avis_echeance.AvisEcheanceActivity;
import fr.legrain.bdg.espaceclient.ui.facture.FactureActivity;
import fr.legrain.bdg.espaceclient.ui.login.LoginActivity;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by nicolas on 10/08/17.
 */
public class LgrActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String MY_PREFS_NAME = "fr.legrain.bdg.preferences";

    public static final String PARAM_KEY_BASE_URL = "baseUrl";
    public static final String PARAM_KEY_DOSSIER = "dossier";
    public static final String PARAM_KEY_API_KEY_DOSSIER = "apiKeyDossier";
    public static final String PARAM_KEY_API_KEY_UTILISATEUR = "apiKeyUtilisateur";
    public static final String PARAM_KEY_API_LOGIN = "apiLogin";
    public static final String PARAM_KEY_API_PASSWORD = "apiPassword";
    public static final String PARAM_KEY_MODE_MULTI_COMPTE_TIERS= "modeMultiCompteTiers";
    public static final String PARAM_KEY_MODE_TEST = "modeTest";
    public static final String PARAM_KEY_MODE_CONNECTE = "modeConnecte";
    public static final String PARAM_KEY_LIGNE_SUIVANTE_AUTO = "ligneSuivanteAuto";

    public static final String PARAM_KEY_NE_PAS_CONSERVER_FLASH = "nePasConserverApresTransfert";
    public static final String PARAM_KEY_MODE_REGROUPEMENT = "modeRegroupement";
    public static final String PARAM_KEY_TYPE_CODE_BARRE = "typeCodeBarre";

    public static final String PARAM_KEY_LAST_UTILISATEUR_FLASH = "dernierUtilisateurFlash";
    public static final String PARAM_KEY_LAST_TYPE_DOC_FLASH = "dernierTypeDocFlash";

    public static final String PARAM_KEY_LOGGED_IN_USER_NAME = "utilisateurConnecte";
    public static final String PARAM_KEY_LOGGED_IN_USER_ACCESS_TOKEN = "utilisateurConnecteAccessToken";
    public static final String PARAM_KEY_LOGGED_IN_USER_REFRESH_TOKEN = "utilisateurConnecteRefreshToken";
    public static final String PARAM_KEY_LOGGED_IN_USER_OBJECT_JSON = "utilisateurConnecteObjet";
    public static final String PARAM_KEY_MODE_CONNEXION_AVANACEE = "modeConnexionAvancee";

    public static final String PARAM_KEY_ANDROID_REGISTRATION_TOKEN_FIREBASE = "androidRegistrationTokenFirebase";


    public static final int PARAM_VALUE_MODE_REGROUPEMENT_AUCUN = 1;
    public static final int PARAM_VALUE_MODE_REGROUPEMENT_ARTICLE = 2;
    public static final int PARAM_VALUE_MODE_REGROUPEMENT_LOT = 3;

    public static final int PARAM_VALUE_TYPE_CODE_BARRE_EAN13 = 1;
    public static final int PARAM_VALUE_TYPE_CODE_BARRE_EAN128 = 2;

//FOR DESIGN..

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

//        setContentView(R.layout.activity_main);
        // 6 - Configure all views

    }


    @Override
    public void onBackPressed() {
        // 5 - Handle back click to close menu
        if (this.drawerLayout != null && this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // 4 - Handle Navigation Item Click
        int id = item.getItemId();

        switch (id) {
            case R.id.activity_main_drawer_accueil:
                Intent intentAccueil = new Intent(getBaseContext(), MainActivity.class);
                //intent.putExtra("parametre1",verbes[position]);
                startActivity(intentAccueil);
                break;
            case R.id.activity_main_drawer_facture:
                Intent intentTiers = new Intent(getBaseContext(), FactureActivity.class);
                //intent.putExtra("parametre1",verbes[position]);
                startActivity(intentTiers);
                break;
            case R.id.activity_main_drawer_avis_echeance:
                Intent intentArticle = new Intent(getBaseContext(), AvisEcheanceActivity.class);
                //intent.putExtra("parametre1",verbes[position]);
                startActivity(intentArticle);
                break;
            case R.id.activity_main_drawer_mes_informations:
                // Toast.makeText(LgrActivity.this, "Bon de livraison", Toast.LENGTH_SHORT).show();
                Intent intentBonliv = new Intent(getBaseContext(), MesInformationsActivity.class);
                //intent.putExtra("parametre1",verbes[position]);
                startActivity(intentBonliv);
                break;
            case R.id.activity_main_drawer_a_propos:
                Intent intentPreparation = new Intent(getBaseContext(), AproposActivity.class);
//                intentPreparation.putExtra("type", TDoc.TYPE_PREPARATION);
                startActivity(intentPreparation);
                break;
            case R.id.activity_main_drawer_nous_contacter:
                Intent intentInventaire = new Intent(getBaseContext(), NousContacterActivity.class);
//                intentInventaire.putExtra("type", TDoc.TYPE_INVENTAIRE);
                startActivity(intentInventaire);
                break;
            case R.id.activity_main_drawer_version_web:
                startActivityVersionWeb();
                break;
            case R.id.activity_main_drawer_preferences:
                Intent intentParam = new Intent(getBaseContext(), ParamActivity.class);
                //intent.putExtra("parametre1",verbes[position]);
                startActivity(intentParam);
                break;
            case R.id.activity_changer_de_tiers:
                startActivityChoixCompteTiers(null);
                break;
            default:
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    // ---------------------
    // CONFIGURATION
    // ---------------------

    // 1 - Configure Toolbar
    protected void configureToolBar() {
        this.toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
    }


    // 2 - Configure Drawer Layout
    protected void configureDrawerLayout() {
        this.drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();
    }

    // 3 - Configure NavigationView
    protected void configureNavigationView() {
        this.navigationView = (NavigationView) findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /***********************************************************************************/
        Menu nav_Menu = navigationView.getMenu();
        MenuItem menuChangerDeTiers = (MenuItem) nav_Menu.findItem(R.id.activity_changer_de_tiers);
        menuChangerDeTiers.setVisible(Parametre.getInstance().isModeMultiCompteTiers());
        /***********************************************************************************/
        try {
//            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
//                    AppDatabase.class, Parametre.CONST_DB_NAME)
//                    .allowMainThreadQueries()
//                    .build();
//            List<AutorisationDossier> a = db.autorisationDossierDao().getAll();
//            db.close();
//            if (a != null & !a.isEmpty()) {
//                AutorisationDossier au = a.get(0);
//
//                if (au != null) {
//                    Menu nav_Menu = navigationView.getMenu();
//                    MenuItem menuTiers = (MenuItem) nav_Menu.findItem(R.id.activity_main_drawer_tiers);
//                    menuTiers.setVisible(au.moduleAutorise(AutorisationDossier.ID_MODULE_TIERS));
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        /***********************************************************************************/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==456) { //activité de changement de tiers
            onResume();
//            if(resultCode== Activity.RESULT_OK) { // login ok
//                if(Parametre.getInstance().isModeMultiCompteTiers()) {
//                    startActivityChoixCompteTiers(null);
//                }
//            }
        }
    }


    public void saveParam(Parametre p) {
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(PARAM_KEY_BASE_URL, p.getBaseUrl());
        editor.putString(PARAM_KEY_DOSSIER, p.getDossier());
        editor.putString(PARAM_KEY_API_KEY_DOSSIER, p.getApiKeyDossier());
        editor.putString(PARAM_KEY_API_KEY_UTILISATEUR, p.getApiKeyUtilisateur());
        editor.putString(PARAM_KEY_API_LOGIN, p.getLogin());
        editor.putString(PARAM_KEY_API_PASSWORD, p.getPassword());
        editor.putBoolean(PARAM_KEY_MODE_TEST, p.getModeTest());
        editor.putBoolean(PARAM_KEY_MODE_CONNECTE, p.isModeConnecteUniquement());
        editor.putBoolean(PARAM_KEY_LIGNE_SUIVANTE_AUTO, p.isLigneSuivanteAuto());
        editor.putBoolean(PARAM_KEY_MODE_MULTI_COMPTE_TIERS, p.isModeMultiCompteTiers());

        editor.putBoolean(PARAM_KEY_NE_PAS_CONSERVER_FLASH, p.isEffacerFlashApresTransfert());
        editor.putInt(PARAM_KEY_MODE_REGROUPEMENT, p.getModeRegroupement());
        editor.putInt(PARAM_KEY_TYPE_CODE_BARRE, p.getModeCodeBarre());

        editor.putBoolean(PARAM_KEY_MODE_CONNEXION_AVANACEE, p.isModeConnexionAvancee());
        editor.apply();
    }

    public Parametre loadParam() {
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String restoredText = prefs.getString("text", null);
        Parametre p = Parametre.getInstance();
        ;
        //if (restoredText != null) {
        p.setBaseUrl(prefs.getString(PARAM_KEY_BASE_URL, null));//"No name defined" is the default value.
        p.setDossier(prefs.getString(PARAM_KEY_DOSSIER, null));
        p.setApiKeyDossier(prefs.getString(PARAM_KEY_API_KEY_DOSSIER, null));
        p.setApiKeyUtilisateur(prefs.getString(PARAM_KEY_API_KEY_UTILISATEUR, null));
        p.setLogin(prefs.getString(PARAM_KEY_API_LOGIN, null));
        p.setPassword(prefs.getString(PARAM_KEY_API_PASSWORD, null));
        p.setModeTest(prefs.getBoolean(PARAM_KEY_MODE_TEST, false));
        p.setModeConnecteUniquement(prefs.getBoolean(PARAM_KEY_MODE_CONNECTE, false));
        p.setLigneSuivanteAuto(prefs.getBoolean(PARAM_KEY_LIGNE_SUIVANTE_AUTO, false));
        p.setModeMultiCompteTiers(prefs.getBoolean(PARAM_KEY_MODE_MULTI_COMPTE_TIERS, false));

        p.setEffacerFlashApresTransfert(prefs.getBoolean(PARAM_KEY_NE_PAS_CONSERVER_FLASH, false));
        p.setModeRegroupement(prefs.getInt(PARAM_KEY_MODE_REGROUPEMENT, PARAM_VALUE_MODE_REGROUPEMENT_AUCUN));
        p.setModeCodeBarre(prefs.getInt(PARAM_KEY_TYPE_CODE_BARRE, PARAM_VALUE_TYPE_CODE_BARRE_EAN128));

        p.setModeConnexionAvancee(prefs.getBoolean(PARAM_KEY_MODE_CONNEXION_AVANACEE, false));
        //}
        return p;
    }

    public void startActivityVersionWeb() {
        String url = Const.urlConnexionEspaceClient;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void startActivityChoixCompteTiers(ArrayList<TaTiersDTO> listeTiers) {
        Intent intent = new Intent(getBaseContext(), ChoixCompteTiersActivity.class);
        intent.putExtra("liste_tiers",listeTiers);
        //startActivity(intent);
        startActivityForResult(intent,456);
    }

    public void startActivityNouveauFlash() {
        startActivityNouveauFlash(null);
    }

    public void startActivityNouveauFlash(TDoc typeDoc) {
//        Intent intentFlash = new Intent(getBaseContext(), FlashFormActivity.class);
//        intentFlash.putExtra("type",typeDoc);
//        startActivity(intentFlash);
    }

    public void startActivityLogin() {
        startActivityLogin(null);
    }

    public void startActivityLogin(Context c) {
        if(c==null) {
            c = MainActivity.getAppContext();
        }
        Intent intentLogin = new Intent(getBaseContext(), LoginActivity.class);
//        //intent.putExtra("parametre1",verbes[position]);
        //getBaseContext().startActivity(intentLogin);
        /*c.*/startActivityForResult(intentLogin,123);
    }

    public void startActivitySiteWeb(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //if(Build.VERSION.SDK_INT > 11) {
        SharedPreferences prefs = getSharedPreferences(LgrActivity.MY_PREFS_NAME, LgrActivity.MODE_PRIVATE);
        String loggedInUser = prefs.getString(LgrActivity.PARAM_KEY_LOGGED_IN_USER_NAME, null);
            invalidateOptionsMenu();
            if(loggedInUser!=null) {
                menu.findItem(R.id.action_logout).setVisible(true);
                menu.findItem(R.id.action_login).setVisible(false);
            }
            else {
                menu.findItem(R.id.action_login).setVisible(true);
                menu.findItem(R.id.action_logout).setVisible(false);
            }

        //}
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intentParam = new Intent(getBaseContext(), ParamActivity.class);
            //intent.putExtra("parametre1",verbes[position]);
            startActivity(intentParam);
//        } else if (id == R.id.action_syncro_agrifact) {
//            Intent intentTest = new Intent(getBaseContext() , DBTestActivity.class);
//            //intent.putExtra("parametre1",verbes[position]);
//            startActivity(intentTest);
//            return true;
          } else if (id == R.id.action_login) {
            startActivityLogin();
            return true;
        } else if (id == R.id.action_logout) {
            logout();
            return true;
//        } else if (id == R.id.action_nouveau_bl) {
//            startActivityNouveauBL();
//            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void logout() {
        LoginDataSource lds = new LoginDataSource();
        lds.logout();
        startActivityLogin();
    }

}
