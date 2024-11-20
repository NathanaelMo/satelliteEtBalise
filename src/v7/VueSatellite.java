package v7;

import java.io.IOException;

public class VueSatellite extends NiImageCustom implements Vue {
    private static final long serialVersionUID = 1L;
    private final ModeleSatellite modele;

    public VueSatellite(ModeleSatellite modele) throws IOException {
        super("src/images/satellite.png", "src/images/sync-effect.png", 40, 40);
        this.modele = modele;
    }


    @Override
    public void mettreAJour() {
        this.setLocation(modele.getX(), modele.getY());
        this.setAfficherSync(modele.estEnSynchronisation());
    }
}