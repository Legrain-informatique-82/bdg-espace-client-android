package fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit;

import java.util.List;
import java.util.concurrent.ExecutionException;

import fr.legrain.bdg.espaceclient.MainActivity;
import fr.legrain.bdg.espaceclient.api.client.dao.IUtilisateurBdgService;
import fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.interfaces.IUtilisateurRetrofit;
import fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.tasks.RestTaskRetrofit;
import fr.legrain.bdg.espaceclient.api.client.dao.rest.retrofit.tasks.RestTaskRetrofitList;
import fr.legrain.bdg.espaceclient.api.client.dto.AutorisationDossierDTO;
import fr.legrain.bdg.espaceclient.api.client.dto.UtilisateurDTO;
import retrofit2.Call;

public class UtilisateurBdgService implements IUtilisateurBdgService {

    private RestTaskRetrofitList<UtilisateurDTO> t = new RestTaskRetrofitList<UtilisateurDTO>(MainActivity.getAppContext(),"");
    private RestTaskRetrofit<UtilisateurDTO> t2 = new RestTaskRetrofit<UtilisateurDTO>(MainActivity.getAppContext(),"");
    private RestTaskRetrofit<AutorisationDossierDTO> t3 = new RestTaskRetrofit<AutorisationDossierDTO>(MainActivity.getAppContext(),"");

    @Override
    public List<UtilisateurDTO> selectAll() {

        IUtilisateurRetrofit utilisateurCall = RestTaskRetrofitList.getRetrofitInstance().create(IUtilisateurRetrofit.class);

        Call<UtilisateurDTO[]> call = utilisateurCall.all(/*param.getDossier()*/);
        t.execute(call);

        List<UtilisateurDTO> l = null;
        try {
            l = t.get();
            System.out.println("UtilisateurBdgService.selectAll");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return l;
    }

    @Override
    public UtilisateurDTO findById(int id) {
        IUtilisateurRetrofit utilisateurCall = RestTaskRetrofit.getRetrofitInstance().create(IUtilisateurRetrofit.class);

        Call<UtilisateurDTO> call = utilisateurCall.select(/*param.getDossier()*/id);
        t2.execute(call);

        UtilisateurDTO l = null;
        try {
            l = t2.get();
            System.out.println("UtilisateurBdgService.findById");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return l;
    }

    @Override
    public void persist(UtilisateurDTO utilisateur) {
        IUtilisateurRetrofit utilisateurCall = RestTaskRetrofit.getRetrofitInstance().create(IUtilisateurRetrofit.class);

        Call<UtilisateurDTO> call = utilisateurCall.create(/*param.getDossier()*/utilisateur);
        t2.execute(call);

        UtilisateurDTO l = null;
        try {
            l = t2.get();
            System.out.println("UtilisateurBdgService.persist");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //return l;
    }

    @Override
    public void merge(UtilisateurDTO utilisateur) {
        IUtilisateurRetrofit utilisateurCall = RestTaskRetrofit.getRetrofitInstance().create(IUtilisateurRetrofit.class);

        Call<UtilisateurDTO> call = utilisateurCall.update(/*param.getDossier()*/utilisateur.getId(), utilisateur);
        t2.execute(call);

        UtilisateurDTO l = null;
        try {
            l = t2.get();
            System.out.println("UtilisateurBdgService.merge");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //return l;
    }

    @Override
    public void remove(UtilisateurDTO utilisateur) {
        IUtilisateurRetrofit utilisateurCall = RestTaskRetrofit.getRetrofitInstance().create(IUtilisateurRetrofit.class);

        Call<UtilisateurDTO> call = utilisateurCall.delete(/*param.getDossier()*/utilisateur.getId());
        t2.execute(call);

        UtilisateurDTO l = null;
        try {
            l = t2.get();
            System.out.println("UtilisateurBdgService.remove");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //return l;
    }

    @Override
    public AutorisationDossierDTO findAutorisationDossier() {
        IUtilisateurRetrofit utilisateurCall = RestTaskRetrofit.getRetrofitInstance().create(IUtilisateurRetrofit.class);

        Call<AutorisationDossierDTO> call = utilisateurCall.autorisationDossier();
        t3.execute(call);

        AutorisationDossierDTO l = null;
        try {
            l = t3.get();
            System.out.println("UtilisateurBdgService.findById");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return l;
    }

    public UtilisateurDTO authenticate(String loginForm, String pwdForm) {
        IUtilisateurRetrofit utilisateurCall = RestTaskRetrofit.getRetrofitInstance().create(IUtilisateurRetrofit.class);

        Call<UtilisateurDTO> call = utilisateurCall.authenticate(loginForm, pwdForm);
        t2 = new RestTaskRetrofit<UtilisateurDTO>(MainActivity.getAppContext(),"");
        t2.execute(call);

        UtilisateurDTO l = null;
        try {
            l = t2.get();
            System.out.println("UtilisateurBdgService.authenticate");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return l;
    }
}
