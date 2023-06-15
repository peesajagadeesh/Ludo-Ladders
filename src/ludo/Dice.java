package ludo;

import ludo.Interfaces.DiceInterface;

public class Dice implements DiceInterface {
    private final int numSides;

    /**
     * Constructs a dice object with the specified number of sides.
     *
     * @param numSides the number of sides on the dice
     */
    public Dice(int numSides) {
        this.numSides = numSides;
    }

    /**
     * Rolls the dice and returns the result.
     *
     * @return the result of the dice roll
     */
    public int roll() {
        return (int) (Math.random() * numSides) + 1;
    }
}