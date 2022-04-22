package fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.tasks;


  import android.content.SharedPreferences;
	
  import java.io.IOException;

  import fr.legrain.bdg.espaceclient.api.client.dao.IEspaceClientBdgService;
  import fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.EspaceClientBdgService;
  import fr.legrain.bdg.espaceclient.api.client.dto.EspaceClientDTO;
  import fr.legrain.bdg.espaceclient.data.model.Parametre;
  import okhttp3.Authenticator;
  import okhttp3.Request;
  import okhttp3.Response;
  import okhttp3.Route;
	
  
/*
https://www.woolha.com/tutorials/android-retrofit-2-refresh-access-token-with-okhttpclient-and-authenticator
https://blog.webwag.com/2018/12/03/refresh-token/

 */
  public class TokenAuthenticator implements Authenticator {

    private IEspaceClientBdgService dao = new EspaceClientBdgService();

      @Override
      public Request authenticate(Route route, Response response) throws IOException {

          if(responseCount(response) >= 2) {
              return null;
          }

          EspaceClientDTO refreshTokenCall = dao.refresh(Parametre.getInstance().getEspaceClientDTO().getRefreshToken());
          //val refreshResponse = refreshTokenCall.execute();
          //if(refreshResponse.isSuccessful) {
          //    PrefManager.login = refreshResponse.body();
          if(refreshTokenCall!=null) {
              Parametre.getInstance().getEspaceClientDTO().setAccessToken(refreshTokenCall.getAccessToken());
              Parametre.getInstance().getEspaceClientDTO().setRefreshToken(refreshTokenCall.getRefreshToken());
              return response.request()
                      .newBuilder()
                      .header("Authorization", "Bearer "+Parametre.getInstance().getEspaceClientDTO().getAccessToken())
                      .build();
          }
          return null;
	
      }

    private int responseCount(Response response)  {
        int count = 1;
        Response res = response.priorResponse();
        while(res != null) {
            count++;
            res = res.priorResponse();
        }
        return count;
    }
	
  }