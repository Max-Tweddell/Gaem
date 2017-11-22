package game;
public class State {
    public static double targetX;
    public static double targetY;

    public static double[] calculateTarget(double posX, double posY) {
        double[] resultArray;
        resultArray = new double[2];
        resultArray[0] = (targetX - posX);
        resultArray[1] = (targetY - posY);

        return resultArray;
    }

    private static State ourInstance = new State();

    public static State getInstance() {
        return ourInstance;
    }

    private State() {
    }
}
