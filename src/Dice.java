

public class Dice implements DiceInterface {
    private final int  numSides;

    public Dice(int numSides) {
        this.numSides = numSides;
    }

    public int roll() {
        return (int) (Math.random() * numSides) + 1;
    }
}