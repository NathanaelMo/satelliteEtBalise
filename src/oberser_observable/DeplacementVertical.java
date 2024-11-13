package oberser_observable;

class DeplacementVertical implements DeplacementStrategy {
    private int deltaY;

    public DeplacementVertical(int deltaY) {
        this.deltaY = deltaY;
    }

    @Override
    public void deplacer(Balise balise) {
        balise.setPosition(balise.getPosition().x, balise.getPosition().y + deltaY);
    }

    @Override
    public void deplacer(Satellite satellite) {

    }
}