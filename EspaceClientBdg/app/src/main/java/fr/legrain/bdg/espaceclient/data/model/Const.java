package fr.legrain.bdg.espaceclient.data.model;

import fr.legrain.bdg.espaceclient.BuildConfig;

public class Const {

    public static final int DEV_LOCAL = 1;
    public static final int DEV_INTERNET = 2;
    public static final int DEV_PPROD = 3;
    public static final int DEV_TEST_PROD = 4;
    public static final int PROD = 5;
    public static int mode = DEV_LOCAL;

    public static final String dateDebutStr = "2019-01-01";
    public static final String dateFinStr = "2020-12-31";
    public static final String patternDateStr = "yy-MM-dd";

    public static String urlConnexionBdg = null;
    public static String portConnexionBdg = null;
    public static String urlConnexionEspaceClient = null;
    public static String dossierBdg = null;
    public static String urlConnexionApi = null;
    public static String portConnexionApi = null;
    public static boolean debug = false;

    static {

        Const.dossierBdg = ConstVersion.getInstance().dossierBdg;
        if(BuildConfig.BUILD_TYPE.equals("debug")) {
            /*
                https://dev.demo.promethee.biz:8443
                https://dev.demo.espace-client.promethee.biz:4200/#/mes-factures
             */
            Const.debug = true;
            Const.dossierBdg = "demo";
            init(DEV_LOCAL, dossierBdg, "promethee.biz", "8443", null, null, "4200", null, null, "8443");

//            init(PROD, dossierBdg, "bdg.cloud", null, null, null, "80", null, null, null);

//            init(DEV_INTERNET, dossierBdg, "devbdg.work", "80", null, null, "80", null, null, null);
//            init(DEV_PPROD, dossierBdg, "pprodbdg.work", "80", null, null, "80", null, null, null);
//            init(DEV_TEST_PROD, dossierBdg, "testprodbdg.work", "80", null, null, "80", null, null, null);
        } else {
            ////init(PROD, dossierBdg, "bdg.cloud", "80", null, null, "80", null, null, null);
            //Const.dossierBdg = "legrain82";
            init(PROD, dossierBdg, "bdg.cloud", null, null, null, null, null, null, null);
            //init(DEV_PPROD, dossierBdg, "pprodbdg.work", null, null, null, null, null, null, null);
        }
    }


    public static void init(
            int paramMode,
            String dossier,
            String bdgHost, String bdgPort, String bdgFullUrl,
            String espaceClientHost, String espaceClientPort, String espaceClientFullUrl, String aliasEspaceClient, String apiPort
        ) {
        mode = paramMode;
        urlConnexionBdg = "https://";
        portConnexionBdg = bdgPort;
        portConnexionApi = apiPort;
        urlConnexionEspaceClient = "https://";
        urlConnexionApi = "https://";
        if(espaceClientHost==null || espaceClientHost.equals("")) {
            espaceClientHost = bdgHost;
        }
        if(paramMode == DEV_LOCAL) {
            //dossierBdg = dossier;
            urlConnexionBdg += "dev.";
            urlConnexionEspaceClient += "dev.";
            urlConnexionApi += "dev.";
        }
        urlConnexionBdg += dossierBdg+"."+bdgHost+(portConnexionBdg!=null?":"+portConnexionBdg:"");
        urlConnexionEspaceClient += dossierBdg+".espace-client."+espaceClientHost+(espaceClientPort!=null?":"+espaceClientPort:"");
        urlConnexionApi += "api"+".espace-client."+bdgHost+(portConnexionApi!=null?":"+portConnexionApi:"");
    }

}
