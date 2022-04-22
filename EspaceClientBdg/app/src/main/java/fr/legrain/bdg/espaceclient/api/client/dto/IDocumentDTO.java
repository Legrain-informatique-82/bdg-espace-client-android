package fr.legrain.bdg.espaceclient.api.client.dto;

import java.util.List;

public interface IDocumentDTO {

    public String getNomTiers();
    public List<? extends ILigneDocumentDTO> getLignesDTO();
}
