package fr.legrain.bdg.espaceclient.ui.avis_echeance;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import fr.legrain.bdg.espaceclient.LgrActivity;
import fr.legrain.bdg.espaceclient.R;
import fr.legrain.bdg.espaceclient.api.client.dao.IAvisEcheanceBdgService;
import fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.AvisEcheanceBdgService;
import fr.legrain.bdg.espaceclient.api.client.dto.AvisEcheanceDTO;
import fr.legrain.bdg.espaceclient.api.client.dto.FactureDTO;
import fr.legrain.bdg.espaceclient.data.model.Const;
import fr.legrain.bdg.espaceclient.data.model.Parametre;

public class AvisEcheanceDetailActivity extends LgrActivity implements ViewPager.OnPageChangeListener {

    public static final String ARG_POSITION = "position";
    public static final String ARG_LISTE_DONNEES = "liste";

    private IAvisEcheanceBdgService dao = new AvisEcheanceBdgService();
    private ProgressBar spinner;

    private ArrayList<AvisEcheanceDTO> listeDonnees = null;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avis_echeance_detail);

        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();

        initData();

//        int data = getIntent().getIntExtra(ARG_POSITION,0);
//        mViewPager.setCurrentItem(data);

//        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(listeDonnees!=null) {
            setTitle(listeDonnees.get(position).getCodeDocument()+" • "+"Avis d'échéance");
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private AvisEcheanceDTO[] laListe;

        public SectionsPagerAdapter(FragmentManager fm, AvisEcheanceDTO[] laListe) {
            super(fm);
            this.laListe = laListe;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a KanjiCardFragment (defined as a static inner class below).

            return AvisEcheanceDetailFragment.newInstance(position + 1,laListe[position]);
            //return TiersDetailFragment.newInstance(position,mapping(cache.getReponse().get(position)));
        }

        @Override
        public int getCount() {
            return laListe.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return laListe[position].getCodeDocument();
        }
    }


    public void initData(){

        try {

            spinner = (ProgressBar)findViewById(R.id.progressBar1);
            listeDonnees = (ArrayList<AvisEcheanceDTO>) getIntent().getSerializableExtra(ARG_LISTE_DONNEES);

            SimpleDateFormat sdf = new SimpleDateFormat(Const.patternDateStr);

            Date debut = null;
            Date fin = null;
            try {
                debut = sdf.parse(Const.dateDebutStr);
                fin = sdf.parse(Const.dateFinStr);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
//            List<AvisEcheanceDTO> l =  dao.selectAllSync(Const.dossierBdg, Parametre.getInstance().getEspaceClientDTO().getCodeTiers(), "2019-01-01", "2020-12-31");

            final AvisEcheanceDTO[] d = new AvisEcheanceDTO[listeDonnees.size()];
            listeDonnees.toArray(d);

//            this.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
                    // Create the adapter that will return a fragment for each of the three
                    // primary sections of the activity.
                    mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),d);

                    // Set up the ViewPager with the sections adapter.
                    mViewPager = (ViewPager) findViewById(R.id.container);
                    mViewPager.setAdapter(mSectionsPagerAdapter);
                    //mViewPager.setCurrentItem(5);
                    findViewById(android.R.id.content).invalidate();
//                }
//            });

            /////////////////////////////////////////////
            int pos = getIntent().getIntExtra(ARG_POSITION,0);

            mViewPager.setCurrentItem(pos);
            mViewPager.addOnPageChangeListener(this);
            onPageSelected(pos);

            if(spinner!=null) {
                spinner.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();
//            Log.e("Erreur lors de l'envoi de la requête : ", e.getMessage());
        }
    }

}
