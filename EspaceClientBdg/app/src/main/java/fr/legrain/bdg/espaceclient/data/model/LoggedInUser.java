package fr.legrain.bdg.espaceclient.data.model;

import fr.legrain.bdg.espaceclient.api.client.dto.EspaceClientDTO;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String userId;
    private String displayName;
    private EspaceClientDTO dto;

    public LoggedInUser(String userId, String displayName) {
        this.userId = userId;
        this.displayName = displayName;
    }

    public LoggedInUser(String userId, String displayName, EspaceClientDTO dto) {
        this.userId = userId;
        this.displayName = displayName;
        this.dto = dto;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public EspaceClientDTO getDto() {
        return dto;
    }
}
