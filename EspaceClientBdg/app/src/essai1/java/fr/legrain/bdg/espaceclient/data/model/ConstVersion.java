package fr.legrain.bdg.espaceclient.data.model;

public class ConstVersion {

    private static ConstVersion instance = null;
    public static String dossierBdg = "essai1";

    public static ConstVersion getInstance() {
        if(instance==null) {
            instance = new ConstVersion();
        }
        return instance;
    }

    private ConstVersion(){}
}
