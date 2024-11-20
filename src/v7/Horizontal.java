package v7;

public class Horizontal implements Deplacement{

    @Override
    public Position deplacer(Position position) {
        position.setX((position.getX() + 2) % 800);
        return position;
    }

}