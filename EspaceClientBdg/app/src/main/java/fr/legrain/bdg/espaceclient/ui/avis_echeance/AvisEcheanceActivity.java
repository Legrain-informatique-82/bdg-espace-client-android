package fr.legrain.bdg.espaceclient.ui.avis_echeance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import androidx.databinding.DataBindingUtil;
import fr.legrain.bdg.espaceclient.LgrActivity;
import fr.legrain.bdg.espaceclient.R;
import fr.legrain.bdg.espaceclient.api.client.dao.IAvisEcheanceBdgService;
import fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.AvisEcheanceBdgService;
import fr.legrain.bdg.espaceclient.api.client.dto.AvisEcheanceDTO;
import fr.legrain.bdg.espaceclient.data.model.Const;
import fr.legrain.bdg.espaceclient.data.model.Parametre;
import fr.legrain.bdg.espaceclient.databinding.AvisEcheanceItemBinding;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class AvisEcheanceActivity extends LgrActivity {

    private IAvisEcheanceBdgService dao = new AvisEcheanceBdgService();
    private ProgressBar spinner;
    private ArrayList<AvisEcheanceDTO> listeDocumentDTO = null; //List ne peut pas être passé directement dans les intent ou Bundle contrairement a ArrayList


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avis_echeance);

        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();

        //initData(); //déplacer dans le onResume uniquement
    }

    @Override
    public void onResume() {

        super.onResume();
        initData();
    }

    public class AvisEcheanceAdapter extends ArrayAdapter<AvisEcheanceDTO> {

        public AvisEcheanceAdapter(Context context, ArrayList<AvisEcheanceDTO> kanjis) {
            super(context, 0, kanjis);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // Get the data item for this position
            AvisEcheanceDTO documentDTO = getItem(position);

            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.avis_echeance_item, parent, false);
            }

            LayoutInflater inflater = LayoutInflater.from(getContext());
            AvisEcheanceItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.avis_echeance_item, null, false);
            binding.setDocument(documentDTO);

            return binding.getRoot();
        }
    }

    public void initData(){
        try {

            ListView mListView = (ListView) findViewById(R.id.listViewDocument);

            View header = getLayoutInflater().inflate(R.layout.avis_echeance_item_header, null);
            if(mListView.getHeaderViewsCount()==0) {
                mListView.addHeaderView(header);
            }

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(getBaseContext() , AvisEcheanceDetailActivity.class);
                    intent.putExtra(AvisEcheanceDetailActivity.ARG_POSITION,position-1); //-1 a cause du décalage du header
                    intent.putExtra(AvisEcheanceDetailActivity.ARG_LISTE_DONNEES, listeDocumentDTO);

                    startActivity(intent);
                }
            });

            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
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
 //                   List<AvisEcheanceDTO> l =  dao.selectAllSync(Const.dossierBdg, Parametre.getInstance().getEspaceClientDTO().getCodeTiers(), "2019-01-01", "2020-12-31");

                    dao.selectAll(/*Const.dossierBdg,*/ Parametre.getInstance().getEspaceClientDTO().getCodeTiers(), Const.dateDebutStr, Const.dateFinStr)
                            .subscribe(new Observer<AvisEcheanceDTO[]>() {
                                AvisEcheanceDTO[] tableauDocumentDTO;
                                //List<AvisEcheanceDTO> listeFactureDTO = null;

                                @Override
                                public void onSubscribe(Disposable d) {
                                    spinner = (ProgressBar)findViewById(R.id.progressBar1);
                                    if(spinner!=null) {
                                        spinner.setIndeterminate(true);
                                        spinner.setVisibility(View.VISIBLE);
                                    }
                                }

                                @Override
                                public void onNext(AvisEcheanceDTO[] listeDto) {
                                    listeDocumentDTO = new ArrayList<>(Arrays.asList(listeDto));;
                                    tableauDocumentDTO = listeDto;
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                    //ArrayAdapter<String> adapter = new ArrayAdapter<String>(KanjiActivity.this, android.R.layout.simple_list_item_1, cache.getKanjisTxt());
                                    ArrayList<AvisEcheanceDTO> l2 = new ArrayList<AvisEcheanceDTO>();
                                    l2.addAll(listeDocumentDTO);
                                    AvisEcheanceActivity.AvisEcheanceAdapter adapter = new AvisEcheanceActivity.AvisEcheanceAdapter(AvisEcheanceActivity.this,l2);


                                    ListView mListView = (ListView) findViewById(R.id.listViewDocument);
                                    mListView.setAdapter(adapter);
                                    mListView.postInvalidate();

                                    findViewById(android.R.id.content).invalidate();
                                    //findViewById(R.id.activity_main_frame_layout).invalidate();

                                    spinner.setVisibility(View.GONE);

//                        CacheArticleRoom ctr = new CacheArticleRoom(getBaseContext(),progressBar);
//                        ctr.cacheBdd(la);
//                        articleOK[0] = true;
//                        if(progressBar!=null && tiersOK[0] && articleOK[0]) {
//                            progressBar.setVisibility(View.GONE);
//                            Toast.makeText(getApplicationContext(), "Reconstruction du cache terminée.", Toast.LENGTH_SHORT).show();
//                        }
                                }
                     });

//                    /*Si synchrone*/
//
//                    //ArrayAdapter<String> adapter = new ArrayAdapter<String>(KanjiActivity.this, android.R.layout.simple_list_item_1, cache.getKanjisTxt());
//                    ArrayList<AvisEcheanceDTO> l2 = new ArrayList<AvisEcheanceDTO>();
//                    l2.addAll(l);
//                    AvisEcheanceAdapter adapter = new AvisEcheanceAdapter(AvisEcheanceActivity.this,l2);
//
//
//                    ListView mListView = (ListView) findViewById(R.id.listViewDocument);
//                    mListView.setAdapter(adapter);
//                    mListView.postInvalidate();
//
//                    findViewById(android.R.id.content).invalidate();
//                    //findViewById(R.id.activity_main_frame_layout).invalidate();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
//            Log.e("Erreur lors de l'envoi de la requête : ", e.getMessage());
        }

    }


}
