package fr.legrain.bdg.espaceclient.ui.login;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter;

import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import fr.legrain.bdg.espaceclient.LgrActivity;
import fr.legrain.bdg.espaceclient.R;
import fr.legrain.bdg.espaceclient.api.client.dao.IEspaceClientBdgService;
import fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.EspaceClientBdgService;
import fr.legrain.bdg.espaceclient.api.client.dto.EspaceClientDTO;
import fr.legrain.bdg.espaceclient.data.model.Const;
import fr.legrain.bdg.espaceclient.data.model.Parametre;
import fr.legrain.bdg.espaceclient.data.model.TaTiersDTO;

public class LoginActivity extends LgrActivity {

    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        final EditText dossier = findViewById(R.id.dossier);
        final Button btnAvance = findViewById(R.id.btnAvance);
        final EditText urlComplete = findViewById(R.id.urlComplete);

        final TextView tvVersionWeb = findViewById(R.id.tvVersionWeb);

        Parametre p = loadParam();
        if(!p.isModeConnexionAvancee()) {
            urlComplete.setVisibility(View.INVISIBLE);
        }
        if(!Const.debug) {
            dossier.setVisibility(View.GONE);
            btnAvance.setVisibility(View.GONE);
            urlComplete.setVisibility(View.GONE);
            urlComplete.setText(Const.urlConnexionBdg);
            dossier.setText(Const.dossierBdg);
        } else {
//            urlComplete.setText(p.getBaseUrl());
//            dossier.setText(p.getDossier());

            urlComplete.setText(Const.urlConnexionBdg);
            dossier.setText(Const.dossierBdg);
        }

        usernameEditText.setText(p.getLogin());
        passwordEditText.setText(p.getPassword());

        loginViewModel.loginDataChanged(usernameEditText.getText().toString(), passwordEditText.getText().toString());

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());

//                    SharedPreferences prefs = getSharedPreferences((LgrActivity.MY_PREFS_NAME, LgrActivity.MODE_PRIVATE);
//                    String dernier = prefs.getString(LgrActivity.PARAM_KEY_LOGGED_IN_USER_NAME, null);

                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putString(PARAM_KEY_LOGGED_IN_USER_NAME, loginResult.getSuccess().getDisplayName());
                    editor.putString(PARAM_KEY_LOGGED_IN_USER_ACCESS_TOKEN, Parametre.getInstance().getEspaceClientDTO().getAccessToken());
                    editor.putString(PARAM_KEY_LOGGED_IN_USER_REFRESH_TOKEN, Parametre.getInstance().getEspaceClientDTO().getRefreshToken());
                    Moshi moshi = new Moshi.Builder()
                            .add(Date.class, new Rfc3339DateJsonAdapter().nullSafe())
                            .build();
                    JsonAdapter<EspaceClientDTO> jsonAdapter = moshi.adapter(EspaceClientDTO.class);
                    String json = jsonAdapter.toJson(Parametre.getInstance().getEspaceClientDTO());
                    editor.putString(PARAM_KEY_LOGGED_IN_USER_OBJECT_JSON, json);



                    IEspaceClientBdgService daoEspaceClient = new EspaceClientBdgService();
                    List<TaTiersDTO> listeCompteTiersDTO = daoEspaceClient.listeTiersSync(Parametre.getInstance().getEspaceClientDTO().getId());
                    Parametre.getInstance().setModeMultiCompteTiers(!listeCompteTiersDTO.isEmpty() && listeCompteTiersDTO.size()>1);
                    editor.putBoolean(PARAM_KEY_MODE_MULTI_COMPTE_TIERS, Parametre.getInstance().isModeMultiCompteTiers());

                    editor.apply();

                    setResult(Activity.RESULT_OK);

                    //Complete and destroy login activity once successful
                    finish();
                }
//                setResult(Activity.RESULT_OK);
//
//                //Complete and destroy login activity once successful
//                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);

                Parametre p = Parametre.getInstance();
                if(Const.debug) {
                    p.setBaseUrl(urlComplete.getText().toString());
                    p.setDossier(dossier.getText().toString());

                    p.setModeConnexionAvancee(false);
                    if(p.getBaseUrl()!=null && !p.getBaseUrl().equals("") && (urlComplete.getVisibility()==View.VISIBLE)) {
                        p.setModeConnexionAvancee(true);
                    } else {
                        p.setBaseUrl("https://"+p.getDossier()+".bdg.cloud");
                        urlComplete.setText(p.getBaseUrl());
                    }
                } else {
                    p.setBaseUrl(Const.urlConnexionBdg);
                    p.setDossier(Const.dossierBdg);
                }
                p.setLogin(usernameEditText.getText().toString());
                p.setPassword(passwordEditText.getText().toString());

                saveParam(p);

                loginViewModel.login(usernameEditText.getText().toString(), passwordEditText.getText().toString());
            }
        });

        btnAvance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(urlComplete.getVisibility()!=View.VISIBLE) {
                    urlComplete.setVisibility(View.VISIBLE);
                    if(urlComplete.getText()!=null && !urlComplete.getText().equals("")) {
                        urlComplete.setText("https://"+dossier.getText()+".bdg.cloud");
                    }

                } else {
                    urlComplete.setVisibility(View.INVISIBLE);
                    //dossier.setText("");
                    //dossier.setEnabled(true);
                }
            }
        });

        tvVersionWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityVersionWeb();
            }
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        //startActivityNouveauFlash();
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
