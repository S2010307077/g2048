package spw4.game2048;

import java.util.Random;

public class RealTileRandom implements TileRandom {

    private Random random;

    public RealTileRandom() {
        random = new Random();
    }

    @Override
    public int getRandomPosition(int numberOfFreeTiles) {
        return random.nextInt(numberOfFreeTiles);
    }

    @Override
    public int getRandomValue() {
        int i = random.nextInt(9);
        // returns 4 in 1 of 10 cases
        if (i != 0)
            return 2;
        return 4;
    }
}
