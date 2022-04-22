package fr.legrain.bdg.espaceclient.api.client.dao.rest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IConstRest {

 public static final String HEADER_DOSSIER = "X-Dossier";
 public static final String HEADER_ACCEPT_APPLICATION_JSON = "Accept: application/json";

 public static final String HEADER_APPLICATION_JSON = "application/json";
 public static final String HEADER_CONTENT_TYPE_APPLICATION_X_WWW_FORM_URLENCODED = "Content-Type: application/x-www-form-urlencoded";
 public static final String HEADER_ACCEPT = "Accept";

 public static final String HEADER_LGR = "X-Lgr";

 public static final String HEADER_APIKEY_1 = "X-Apikey-1";
 public static final String HEADER_APIKEY_2 = "X-Apikey-2";
 public static final String HEADER_LOGIN = "X-Bdg-login";
 public static final String HEADER_PASSWORD = "X-Bdg-password";

}