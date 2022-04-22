package fr.legrain.bdg.espaceclient.ui.facture;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.stripe.android.PaymentConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import fr.legrain.bdg.espaceclient.BuildConfig;
import fr.legrain.bdg.espaceclient.LgrActivity;
import fr.legrain.bdg.espaceclient.LgrCheckoutActivity;
import fr.legrain.bdg.espaceclient.ParamActivity;
import fr.legrain.bdg.espaceclient.R;
import fr.legrain.bdg.espaceclient.api.client.dao.IEspaceClientBdgService;
import fr.legrain.bdg.espaceclient.api.client.dao.IFactureBdgService;
import fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.EspaceClientBdgService;
import fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.FactureBdgService;
import fr.legrain.bdg.espaceclient.api.client.dto.FactureDTO;
import fr.legrain.bdg.espaceclient.api.client.dto.LigneFactureDTO;
import fr.legrain.bdg.espaceclient.data.LoginDataSource;
import fr.legrain.bdg.espaceclient.data.model.Const;
import fr.legrain.bdg.espaceclient.data.model.ParamPaiement;
import fr.legrain.bdg.espaceclient.data.model.Parametre;
import fr.legrain.bdg.espaceclient.databinding.FactureLigneItemBinding;
import fr.legrain.bdg.espaceclient.databinding.FragmentFactureDetailBinding;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * A placeholder fragment containing a simple view.
 */
public class FactureDetailFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public static final String ARG_SECTION_NUMBER = "section_number";
    public static final String ARG_SECTION_CONTENT = "section_content";

    private FactureDTO documentDTO;

    private IFactureBdgService dao = null;
    private IEspaceClientBdgService daoEspaceClient = null;

    private Boolean menuPayerVisible = true;

    private ProgressBar spinner;
    private ListView mListView = null;
    private FragmentFactureDetailBinding binding = null;

    public FactureDetailFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FactureDetailFragment newInstance(int sectionNumber, FactureDTO documentDTO) {
        FactureDetailFragment fragment = new FactureDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putSerializable(ARG_SECTION_CONTENT, documentDTO);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);


        inflater.inflate(R.menu.menu_document_detail, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
// You can hide the state of the menu item here if you call getActivity().supportInvalidateOptionsMenu(); somewhere in your code
        if(!menuPayerVisible) {
            MenuItem menuItem = menu.findItem(R.id.action_pay);
            menuItem.setVisible(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_download) {
            download();
        } else if (id == R.id.action_pay) {
            payer();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_facture_detail, container, false);

        documentDTO = (FactureDTO) getArguments().getSerializable(ARG_SECTION_CONTENT);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_facture_detail, container, false);
        binding.setDocument(documentDTO);

        spinner = (ProgressBar) binding.getRoot().findViewById(R.id.progressBar1);

        dao = new FactureBdgService();
        daoEspaceClient = new EspaceClientBdgService();


        mListView = (ListView) binding.getRoot().findViewById(R.id.listViewLigneBonliv);

        View header = inflater.inflate(R.layout.facture_ligne_item_header, null);
        if(mListView.getHeaderViewsCount()==0) {
            mListView.addHeaderView(header);
        }

        //Récupération du DTO "complet" a partir de son ID (y compris la liste des lignes DTO)
        //documentDTO = dao.findByIdSync(data.getId(), Parametre.getInstance().getEspaceClientDTO().getCodeTiers());

        dao.findById(documentDTO.getId(), Parametre.getInstance().getEspaceClientDTO().getCodeTiers())

                .subscribe(new Observer<FactureDTO>() {
                    FactureDTO dto;

                    @Override
                    public void onSubscribe(Disposable d) {
                        if(spinner!=null) {
                            spinner.setIndeterminate(true);
                            spinner.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override

                    public void onNext(FactureDTO doc) {
                        dto = doc;
                        documentDTO = doc;
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                        //final FactureDTO finalData = dto;

                        //final FactureDTO finalData = data; //TODO declarer en global sur la classe

//                        TextView textView = (TextView) binding.getRoot().findViewById(R.id.tv_libelleDocument);
//                        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

                        final Button btnFacturePdf = (Button) binding.getRoot().findViewById(R.id.btnDocumentPdf);
                        btnFacturePdf.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                download();
                            }
                        });

                        final Button btnPayerDocument = (Button) binding.getRoot().findViewById(R.id.btnPayerDocument);
                        final LinearLayout layoutPayerDocument = (LinearLayout) binding.getRoot().findViewById(R.id.idLayoutPayerDocument);
                        final LinearLayout layoutTelechargerDocument = (LinearLayout) binding.getRoot().findViewById(R.id.idLayoutTelechargerDocument);
                        btnPayerDocument.setVisibility(View.INVISIBLE);
                        layoutPayerDocument.setVisibility(View.GONE);
                        //Boolean paiementPossible = daoEspaceClient.fournisseurPermetPaiementParCBSync("demo",Parametre.getInstance().getEspaceClientDTO().getCodeTiers());

                        daoEspaceClient.fournisseurPermetPaiementParCB(/*Const.dossierBdg,*/ Parametre.getInstance().getEspaceClientDTO().getCodeTiers())
                                .subscribe(new Observer<ResponseBody>() {
                                    //             .subscribe(new Observer<ArticleDTO[]>() {
                                    ResponseBody coinListh;
                                    Boolean paiementPossible;

                                    @Override
                                    public void onSubscribe(Disposable d) {
                                        if(spinner!=null) {
                                            spinner.setIndeterminate(true);
                                            spinner.setVisibility(View.VISIBLE);
                                        }
                                    }

                                    @Override

                                    public void onNext(ResponseBody coinList) {
                                        try {
                                            String l = coinList.byteString().utf8();
                                            JSONObject json = new JSONObject(l);

                                            paiementPossible = json.getBoolean("b");
                                        } catch (JSONException | IOException e) {
                                            e.printStackTrace();
                                        }
                                        coinListh = coinList;
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {
                                        Boolean nonTotalementRegle = documentDTO.getResteAReglerComplet() != null && documentDTO.getResteAReglerComplet().compareTo(BigDecimal.ZERO) > 0;
                                        if ( nonTotalementRegle &&
                                                paiementPossible != null && paiementPossible
                                        ) {
                                            layoutPayerDocument.setVisibility(View.VISIBLE);
                                            btnPayerDocument.setVisibility(View.VISIBLE);

                                            btnPayerDocument.setOnClickListener(new View.OnClickListener() {

                                                @Override
                                                public void onClick(View v) {
                                                    payer();
                                                }
                                            });
                                        } else { //soit deja regele, soit non payable en ligne
                                            ConstraintLayout.LayoutParams parameter =  (ConstraintLayout.LayoutParams) layoutTelechargerDocument.getLayoutParams();
                                            parameter.topMargin = 16;
                                            parameter.setMargins(parameter.leftMargin, parameter.topMargin , parameter.rightMargin, parameter.bottomMargin); // left, top, right, bottom
                                            layoutTelechargerDocument.setLayoutParams(parameter);
//                                            if(nonTotalementRegle) {
//
//                                            }
                                            menuPayerVisible = false;
                                            getActivity().invalidateOptionsMenu();
                                        }

                                        if(spinner!=null) {
                                            spinner.setVisibility(View.GONE);
                                        }
                                        getActivity().findViewById(android.R.id.content).invalidate();

                                    }
                                });


                        if(spinner!=null) {
                            spinner.setVisibility(View.GONE);
                        }

                        initData(documentDTO, binding.getRoot());
                        getActivity().findViewById(android.R.id.content).invalidate();

                        //findViewById(R.id.activity_main_frame_layout).invalidate();



                    }
                });

        //return rootView;
        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( requestCode == 1234 ) {
          // initData(documentDTO, binding.getRoot());
            //TODO rafraichir l'affichage du fragement et eventuellement de la liste
        }
    }

    public void download() {
        //Toast.makeText(getContext(), "PDF", Toast.LENGTH_SHORT).show();

        //Vérification des permissions d'écriture
        int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
        if ((ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
//                        || (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }

        File f = dao.facturePdfSync(/*Const.dossierBdg,*/ documentDTO.getCodeDocument());

        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        Environment.getDataDirectory();
        Uri uri = FileProvider.getUriForFile(
                getActivity(),
                BuildConfig.APPLICATION_ID + ".provider",
                new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), f.getName()));

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        startActivity(Intent.createChooser(intent, "PDF ..."));
    }

    public void payer() {
        String clePubliqueStripe = daoEspaceClient.clePubliqueStripe();
//                    IFactureBdgService dao = new FactureBdgService();
//                    Boolean paiementPossible = dao.fournisseurPermetPaiementParCB("demo", Parametre.getInstance().getEspaceClientDTO().getCodeTiers());
        String publishableKey = clePubliqueStripe;
//                    if(paiementPossible!=null && paiementPossible) {
        publishableKey = daoEspaceClient.clePubliqueStripe();
        if (publishableKey != null && !publishableKey.equals("")) {
            PaymentConfiguration.init(
                    getActivity().getApplicationContext(),
                    publishableKey
            );
//                        }
            /*
            https://stripe.com/docs/mobile/android/basic#collect-details
            https://stripe.com/docs/terminal/sdk/android
            https://stripe.com/docs/testing#regulatory-cards
            */
            ParamPaiement p = new ParamPaiement();
            p.setTypeDocument("Facture"); //"AvisEcheance"
            p.setCodeDocument(documentDTO.getCodeDocument());
            p.setMontant(documentDTO.getNetTtcCalc());
            p.setCodeDossierFournisseur(Const.dossierBdg);
            p.setCodeClientChezCeFournisseur(Parametre.getInstance().getEspaceClientDTO().getCodeTiers());
            //  String paymentSecret = dao.creerPaymentIntent(p);

            Intent intent = new Intent(getContext(), LgrCheckoutActivity.class);
            //intent.putExtra("paymentSecret",paymentSecret);
            intent.putExtra("paramPaiement", p);
            //startActivity(intent);
            startActivityForResult(intent,1234);

        }
        Toast.makeText(getContext(), "Payer " + clePubliqueStripe, Toast.LENGTH_SHORT).show();
    }


    public void initData(final FactureDTO data, final View rootView) {
        try {

//            getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
                    List<LigneFactureDTO> l = data.getLignesDTO();
                    //ArrayAdapter<String> adapter = new ArrayAdapter<String>(KanjiActivity.this, android.R.layout.simple_list_item_1, cache.getKanjisTxt());
                    ArrayList<LigneFactureDTO> l2 = new ArrayList<LigneFactureDTO>();
                    l2.addAll(l);
                    LigneFactureAdapter adapter = new LigneFactureAdapter(getContext(), l2);

                    // getActivity().getLayoutInflater().inflate(R.id.listViewLigneBonliv,rootView);

                    mListView.setAdapter(adapter);
                    mListView.postInvalidate();

                    getActivity().findViewById(android.R.id.content).invalidate();
//                }
//            });

        } catch (Exception e) {
            e.printStackTrace();
//            Log.e("Erreur lors de l'envoi de la requête : ", e.getMessage());
        }

    }

    public class LigneFactureAdapter extends ArrayAdapter<LigneFactureDTO> {

        public LigneFactureAdapter(Context context, ArrayList<LigneFactureDTO> listeLigneDTO) {
            super(context, 0, listeLigneDTO);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // Get the data item for this position
            LigneFactureDTO kanji = getItem(position);

            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.facture_ligne_item, parent, false);
            }

            LayoutInflater inflater = LayoutInflater.from(getContext());
            FactureLigneItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.facture_ligne_item, null, false);
            //binding.setBonliv(getItem(position));
            binding.setLigneDocument(kanji);

            return binding.getRoot();
        }
    }

}