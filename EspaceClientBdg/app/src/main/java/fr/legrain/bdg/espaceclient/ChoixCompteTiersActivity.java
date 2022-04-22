package fr.legrain.bdg.espaceclient;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.databinding.DataBindingUtil;
import fr.legrain.bdg.espaceclient.api.client.dao.IEspaceClientBdgService;
import fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.EspaceClientBdgService;
import fr.legrain.bdg.espaceclient.data.model.Parametre;
import fr.legrain.bdg.espaceclient.data.model.TaTiersDTO;
import fr.legrain.bdg.espaceclient.databinding.CompteTiersItemBinding;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class ChoixCompteTiersActivity extends LgrActivity {

    private static Context context;
    private ProgressBar spinner = null;

    private  ArrayList<TaTiersDTO> listeTiers = null;
    private IEspaceClientBdgService daoEspaceClient = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_compte_tiers);

        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();

        setTitle("Choix du tiers");
        daoEspaceClient = new EspaceClientBdgService();

        listeTiers =  (ArrayList<TaTiersDTO>)getIntent().getSerializableExtra("liste_tiers");

    }

    @Override
    public void onResume() {

        super.onResume();
        initData();
    }

    public class CompeTiersAdapter extends ArrayAdapter<TaTiersDTO> {

        public CompeTiersAdapter(Context context, ArrayList<TaTiersDTO> kanjis) {
            super(context, 0, kanjis);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // Get the data item for this position
            TaTiersDTO documentDTO = getItem(position);

            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.compte_tiers_item, parent, false);

            }

            LayoutInflater inflater = LayoutInflater.from(getContext());
            CompteTiersItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.compte_tiers_item, null, false);
            binding.setDocument(documentDTO);

            //Coloration du background une ligne sur deux
            if (position % 2 == 1) {
                binding.getRoot().setBackgroundColor(Color.parseColor("#F3F3F3"));
            } else {
                //binding.getRoot().setBackgroundColor(Color.BLUE);
            }

            if (documentDTO.getCodeTiers().equals(Parametre.getInstance().getEspaceClientDTO().getCodeTiers())) {
                binding.getRoot().setBackgroundColor(Color.parseColor("#888888"));
            }

            return binding.getRoot();
        }
    }

    public void initData(){
        try {

            ListView mListView = (ListView) findViewById(R.id.listViewCompteTiers);

//            View header = getLayoutInflater().inflate(R.layout.facture_item_header, null);
//            if(mListView.getHeaderViewsCount()==0) {
//                mListView.addHeaderView(header);
//            }

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView parent, View view, int position, long id) {

//                    Intent intent = new Intent(getBaseContext() , FactureDetailActivity.class);
//                    intent.putExtra(FactureDetailActivity.ARG_POSITION,position-1); //-1 a cause du décalage du header
//                    intent.putExtra(FactureDetailActivity.ARG_LISTE_DONNEES, listeDocumentDTO);
//
//                    startActivity(intent);

                    Parametre.getInstance().getEspaceClientDTO().setCodeTiers(listeTiers.get(position).getCodeTiers());

//                    Intent intentAccueil = new Intent(getBaseContext(), MainActivity.class);
//                    startActivity(intentAccueil);

                    finish();
                }
            });

            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    List<TaTiersDTO> l =  daoEspaceClient.listeTiersSync(Parametre.getInstance().getEspaceClientDTO().getId());


                    daoEspaceClient.listeTiers(Parametre.getInstance().getEspaceClientDTO().getId())
                            .subscribe(new Observer<TaTiersDTO[]>() {
                                TaTiersDTO[] tableauDocumentDTO;


                                @Override
                                public void onSubscribe(Disposable d) {
                                    spinner = (ProgressBar)findViewById(R.id.progressBar1);
                                    if(spinner!=null) {
                                        spinner.setIndeterminate(true);
                                        spinner.setVisibility(View.VISIBLE);
                                    }
                                }

                                @Override
                                public void onNext(TaTiersDTO[] listeDto) {
                                    listeTiers = new ArrayList<>(Arrays.asList(listeDto));;
                                    tableauDocumentDTO = listeDto;
                                }

                                @Override
                                public void onError(Throwable e) {
                                    //Toast.makeText(getBaseContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG);

                                    spinner.setVisibility(View.GONE);
                                }

                                @Override
                                public void onComplete() {

                                    //ArrayAdapter<String> adapter = new ArrayAdapter<String>(KanjiActivity.this, android.R.layout.simple_list_item_1, cache.getKanjisTxt());
                                    ArrayList<TaTiersDTO> l2 = new ArrayList<TaTiersDTO>();
                                    l2.addAll(listeTiers);
                                    CompeTiersAdapter adapter = new CompeTiersAdapter(ChoixCompteTiersActivity.this,l2);


                                    ListView mListView = (ListView) findViewById(R.id.listViewCompteTiers);
                                    mListView.setAdapter(adapter);
                                    mListView.postInvalidate();

                                    findViewById(android.R.id.content).invalidate();
                                    //findViewById(R.id.activity_main_frame_layout).invalidate();

                                    spinner.setVisibility(View.GONE);

                                }
                            });


//                    /*Si synchrone*/
//
//                    //ArrayAdapter<String> adapter = new ArrayAdapter<String>(KanjiActivity.this, android.R.layout.simple_list_item_1, cache.getKanjisTxt());
//                    ArrayList<TaTiersDTO> l2 = new ArrayList<TaTiersDTO>();
//                    l2.addAll(listeTiers);
//                    CompeTiersAdapter adapter = new CompeTiersAdapter(ChoixCompteTiersActivity.this,l2);
//
//
//                    ListView mListView = (ListView) findViewById(R.id.listViewCompteTiers);
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
