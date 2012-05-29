package com.xpansive.layer.util;

import java.util.Random;

/**
 * Provides a wrapper for Java's built in Random class with extra functions that are useful for world generators.
 */
public class WedgeRandom {
    private final Random jrandom;

    /**
     * Instantiates the WedgeRandom class with the specified Random to wrap.
     *
     * @param random A Random object to use for calculations.
     */
    public WedgeRandom(Random random) {
        jrandom = random;
    }

    /**
     * Gets the Random object associated with this WedgeRandom.
     *
     * @return A Random object.
     */
    public Random getJavaRandom() {
        return jrandom;
    }

    /**
     * Gets a boolean which will be true chance percent of the time.
     *
     * @param chance The percent chance of getting a result of true. Should range from 0 (0% chance) to 1 (100% chance).
     * @return A boolean indicating if the chance was greater than a random number.
     */
    public boolean percentChance(double chance) {
        return jrandom.nextDouble() < chance;
    }

    /**
     * Gets a random double between 0 and max.
     *
     * @param max The maximum value the random number can have.
     * @return The random double.
     */
    public double randDouble(double max) {
        return jrandom.nextDouble() * max;
    }

    /**
     * Gets a random double between min and max.
     *
     * @param min The minimum value the random number can have.
     * @param max The maximum value the random number can have.
     * @return The random double.
     */
    public double randDouble(double min, double max) {
        return min + jrandom.nextDouble() * Math.abs(max - min);
    }

    /**
     * Gets a random integer between 0 and max.
     *
     * @param max The maximum value the random number can have.
     * @return The random integer.
     */
    public int randInt(int max) {
        return jrandom.nextInt(max);
    }

    /**
     * Gets a random integer between min and max.
     *
     * @param min The minimum value the random number can have.
     * @param max The maximum value the random number can have.
     * @return The random integer.
     */
    public int randInt(int min, int max) {
        return min + jrandom.nextInt(Math.abs(max - min + 1));
    }
}
