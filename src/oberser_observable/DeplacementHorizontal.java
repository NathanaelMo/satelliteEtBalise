package oberser_observable;

class DeplacementHorizontal implements DeplacementStrategy {
    private int deltaX;

    public DeplacementHorizontal(int deltaX) {
        this.deltaX = deltaX;
    }

    @Override
    public void deplacer(Balise balise) {
        balise.setPosition(balise.getPosition().x + deltaX, balise.getPosition().y);
    }

    @Override
    public void deplacer(Satellite satellite) {
        satellite.setPosition(satellite.getPosition().x + deltaX, satellite.getPosition().y);
    }
}
