

public class Dice {
    private int numSides;

    public Dice(int numSides) {
        this.numSides = numSides;
    }

    public int roll() {
        return (int) (Math.random() * numSides) + 1;
    }
}