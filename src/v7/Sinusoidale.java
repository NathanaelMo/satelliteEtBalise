package v7;

public class Sinusoidale implements Deplacement{

    @Override
    public Position deplacer(Position position) {

        position.setX((position.getX() + 2) % 800);
        position.setY((Y_MIN + Y_MAX) / 2 + (int)(50 * Math.sin(position.getX() * 0.05)));
        return position;
    }

}
