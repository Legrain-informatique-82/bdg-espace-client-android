package fr.legrain.bdg.espaceclient.ui.facture;

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
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import androidx.databinding.DataBindingUtil;

import fr.legrain.bdg.espaceclient.LgrActivity;
import fr.legrain.bdg.espaceclient.MainActivity;
import fr.legrain.bdg.espaceclient.R;
import fr.legrain.bdg.espaceclient.api.client.dao.IFactureBdgService;
import fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.FactureBdgService;
import fr.legrain.bdg.espaceclient.api.client.dto.FactureDTO;
import fr.legrain.bdg.espaceclient.data.model.Const;
import fr.legrain.bdg.espaceclient.data.model.Parametre;
import fr.legrain.bdg.espaceclient.databinding.FactureItemBinding;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class FactureActivity extends LgrActivity {

    private IFactureBdgService dao = new FactureBdgService();
    private ProgressBar spinner;
    private ArrayList<FactureDTO> listeDocumentDTO = null; //List ne peut pas être passé directement dans les intent ou Bundle contrairement a ArrayList

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facture);

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

    public class FactureAdapter extends ArrayAdapter<FactureDTO> {

        public FactureAdapter(Context context, ArrayList<FactureDTO> kanjis) {
            super(context, 0, kanjis);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // Get the data item for this position
            FactureDTO documentDTO = getItem(position);

            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.facture_item, parent, false);

            }

            LayoutInflater inflater = LayoutInflater.from(getContext());
            FactureItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.facture_item, null, false);
            binding.setDocument(documentDTO);

            //Coloration du background une ligne sur deux
            if (position % 2 == 1) {
                binding.getRoot().setBackgroundColor(Color.parseColor("#F3F3F3"));
            } else {
                //binding.getRoot().setBackgroundColor(Color.BLUE);
            }

//            TextView t = (TextView) binding.getRoot().findViewById(R.id.tvResteAReglerLigneFacture);
//            if (t.getText().toString().equals("0.00 €")) {
//                t.setTextColor(getColor(R.color.colorPrimary));
//            }


            return binding.getRoot();
        }
    }

    public void initData(){
        try {

            ListView mListView = (ListView) findViewById(R.id.listViewDocument);

            View header = getLayoutInflater().inflate(R.layout.facture_item_header, null);
            if(mListView.getHeaderViewsCount()==0) {
                mListView.addHeaderView(header);
            }

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(getBaseContext() , FactureDetailActivity.class);
                    intent.putExtra(FactureDetailActivity.ARG_POSITION,position-1); //-1 a cause du décalage du header
                    intent.putExtra(FactureDetailActivity.ARG_LISTE_DONNEES, listeDocumentDTO);

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
//                    List<FactureDTO> l =  dao.selectAllSync("demo", Parametre.getInstance().getEspaceClientDTO().getCodeTiers(), "2019-01-01", "2020-12-31");


                    dao.selectAll(/*Const.dossierBdg, */Parametre.getInstance().getEspaceClientDTO().getCodeTiers(), Const.dateDebutStr, Const.dateFinStr)
                        .subscribe(new Observer<FactureDTO[]>() {
                            FactureDTO[] tableauDocumentDTO;
                            //List<FactureDTO> listeFactureDTO = null;

                                @Override
                                public void onSubscribe(Disposable d) {
                                    spinner = (ProgressBar)findViewById(R.id.progressBar1);
                                    if(spinner!=null) {
                                        spinner.setIndeterminate(true);
                                        spinner.setVisibility(View.VISIBLE);
                                    }
                                }

                                @Override
                                public void onNext(FactureDTO[] listeDto) {
                                    listeDocumentDTO = new ArrayList<>(Arrays.asList(listeDto));;
                                    tableauDocumentDTO = listeDto;
                                }

                                @Override
                                public void onError(Throwable e) {
                                    //Toast.makeText(getBaseContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG);

                                    if(e instanceof retrofit2.adapter.rxjava2.HttpException) {
                                        Snackbar mySnackbar = Snackbar.make(findViewById(R.id.activity_main_drawer_layout),e.getLocalizedMessage()
                                                /*R.string.app_name*/, Snackbar.LENGTH_LONG);
                                        mySnackbar.setAction("Reconnexion", new MyUndoListener());
                                        mySnackbar.show();
                                    }

                                    spinner.setVisibility(View.GONE);
                                }

                                @Override
                                public void onComplete() {

                                    //ArrayAdapter<String> adapter = new ArrayAdapter<String>(KanjiActivity.this, android.R.layout.simple_list_item_1, cache.getKanjisTxt());
                                    ArrayList<FactureDTO> l2 = new ArrayList<FactureDTO>();
                                    l2.addAll(listeDocumentDTO);
                                    FactureAdapter adapter = new FactureAdapter(FactureActivity.this,l2);


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
//                    ArrayList<FactureDTO> l2 = new ArrayList<FactureDTO>();
//                    l2.addAll(l);
//                    FactureAdapter adapter = new FactureAdapter(FactureActivity.this,l2);
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

    public class MyUndoListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            logout();
        }
    }



}
