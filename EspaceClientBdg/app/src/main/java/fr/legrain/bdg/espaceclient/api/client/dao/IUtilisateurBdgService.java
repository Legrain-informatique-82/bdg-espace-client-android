package fr.legrain.bdg.espaceclient.api.client.dao;

import java.util.List;

import fr.legrain.bdg.espaceclient.api.client.dto.AutorisationDossierDTO;
import fr.legrain.bdg.espaceclient.api.client.dto.UtilisateurDTO;

public interface IUtilisateurBdgService {

 public List<UtilisateurDTO> selectAll();

 public UtilisateurDTO findById(int id);

 public void persist(UtilisateurDTO utilisateur);

 public void merge(UtilisateurDTO utilisateur);

 public void remove(UtilisateurDTO utilisateur);

 public AutorisationDossierDTO findAutorisationDossier();

 public UtilisateurDTO authenticate(String loginForm, String pwdForm);
}