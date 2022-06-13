package spw4.game2048;

public class GameImpl implements Game {

    private int[][] tiles;

    private int moves;

    private int score;

    private TileRandom tileRandom;

    public GameImpl() {
        this(new RealTileRandom());
    }

    public GameImpl(TileRandom tileRandom) {
        tiles = new int[4][4];
        this.tileRandom = tileRandom;
    }

    public int getMoves() {
        return moves;
    }

    public int getScore() {
        return score;
    }

    public int getValueAt(int x, int y) {
        return tiles[x][y];
    }

    public boolean isOver() {
        if (isWon())
            return true;
        if (getNumberOfFreeTiles() != 0)
            return false;

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (hasMergableNeighbours(x, y))
                    return false;
            }
        }

        return true;
    }

    public boolean isWon() {
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (getValueAt(x, y) == 2048)
                    return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Moves: ").append(getMoves()).append("   ").
                append("Score: ").append(getScore()).append("\n");
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                int value = getValueAt(x, y);
                sb.append(value == 0 ? "." : value).append("     ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void move(Direction direction) {
        int lowestPos;
        switch (direction) {
            case up:
                for (int y = 0; y < 4; y++) {
                    lowestPos = 0;
                    for (int x = 0; x < 3; x++) {
                        int value = getValueAt(x, y);
                        for (int nX = x + 1; nX < 4; nX++) {
                            int newValue = getValueAt(nX, y);
                            if (value == newValue) {
                                score += 2 * value;
                                tiles[x][y] = 2 * value;
                                tiles[nX][y] = 0;
                                break;
                            } else if (newValue != 0) {
                                break;
                            }
                        }
                    }
                    for (int x = 0; x < 4; x++) {
                        int value = getValueAt(x, y);
                        if (value != 0) {
                            tiles[lowestPos][y] = value;
                            if (x != lowestPos)
                                tiles[x][y] = 0;
                            lowestPos++;
                        }
                    }
                }
                break;

            case down:
                for (int y = 0; y < 4; y++) {
                    lowestPos = 3;
                    for (int x = 3; x > 0; x--) {
                        int value = getValueAt(x, y);
                        for (int nX = x - 1; nX >= 0; nX--) {
                            int newValue = getValueAt(nX, y);
                            if (value == newValue) {
                                score += 2 * value;
                                tiles[x][y] = 2 * value;
                                tiles[nX][y] = 0;
                                break;
                            } else if (newValue != 0) {
                                break;
                            }
                        }
                    }
                    for (int x = 3; x >= 0; x--) {
                        int value = getValueAt(x, y);
                        if (value != 0) {
                            tiles[lowestPos][y] = value;
                            if (x != lowestPos)
                                tiles[x][y] = 0;
                            lowestPos--;
                        }
                    }
                }
                break;
            case left:
                for (int x = 0; x < 4; x++) {
                    lowestPos = 0;
                    for (int y = 0; y < 3; y++) {
                        int value = getValueAt(x, y);
                        for (int nY = y + 1; nY < 4; nY++) {
                            int newValue = getValueAt(x, nY);
                            if (value == newValue) {
                                score += 2 * value;
                                tiles[x][y] = 2 * value;
                                tiles[x][nY] = 0;
                                break;
                            } else if (newValue != 0) {
                                break;
                            }
                        }
                    }
                    for (int y = 0; y < 4; y++) {
                        int value = getValueAt(x, y);
                        if (value != 0) {
                            tiles[x][lowestPos] = value;
                            if (y != lowestPos)
                                tiles[x][y] = 0;
                            lowestPos++;
                        }
                    }
                }
                break;
            case right:
                for (int x = 0; x < 4; x++) {
                    lowestPos = 3;
                    for (int y = 3; y > 0; y--) {
                        int value = getValueAt(x, y);
                        for (int nY = y - 1; nY >= 0; nY--) {
                            int newValue = getValueAt(x, nY);
                            if (value == newValue) {
                                score += 2 * value;
                                tiles[x][y] = 2 * value;
                                tiles[x][nY] = 0;
                                break;
                            } else if (newValue != 0) {
                                break;
                            }
                        }
                    }
                    for (int y = 3; y >= 0; y--) {
                        int value = getValueAt(x, y);
                        if (value != 0) {
                            tiles[x][lowestPos] = value;
                            if (y != lowestPos)
                                tiles[x][y] = 0;
                            lowestPos--;
                        }
                    }
                }
                break;
        }
        moves++;
        if (getNumberOfFreeTiles() != 0)
            spawn();
    }

    public void initialize() {
        spawn();
        spawn();
    }

    private int getNumberOfFreeTiles() {
        int freeTiles = 0;
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (getValueAt(x, y) == 0)
                    freeTiles++;
            }
        }
        return freeTiles;
    }

    private void spawn() {
        int position = tileRandom.getRandomPosition(getNumberOfFreeTiles());
        int value = tileRandom.getRandomValue();

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (getValueAt(x, y) == 0) {
                    if (position == 0) {
                        tiles[x][y] = value;
                        return;
                    }
                    position--;
                }
            }
        }
    }

    private boolean hasMergableNeighbours(int x, int y) {
        int value = getValueAt(x, y);
        if (x - 1 >= 0) {
            if (getValueAt(x - 1, y) == value)
                return true;
        }
        if (y - 1 >= 0) {
            return getValueAt(x, y - 1) == value;
        }
        return false;
    }
}

