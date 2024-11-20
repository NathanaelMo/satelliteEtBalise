package v7;

import java.io.IOException;

public class VueBalise extends NiImageCustom implements Vue {
    private static final long serialVersionUID = 1L;
    private final ModeleBalise modele;

    public VueBalise(ModeleBalise modele) throws IOException {
        super("src/images/balise.png", "src/images/sync-effect.png", 50, 50);
        this.modele = modele;
    }

    @Override
    public void mettreAJour() {
        this.setLocation(modele.getX(), modele.getY());
        this.setAfficherSync(modele.estEnSynchronisation());
    }
}