package spw4.game2048;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameImplTest {

    private GameImpl game;

    @BeforeEach
    public void createGame() {
        game = new GameImpl();
    }

    @Test
    public void testNewGameImplYieldsEmptyGame() {
        GameImpl game = new GameImpl();

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                assertEquals(0, game.getValueAt(x, y));
            }
        }
    }

    @Test
    public void testInitializeYieldsGameWithTwoTiles() {
        game.initialize();

        int spawnedTiles = 0;

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (game.getValueAt(x, y) != 0)
                    spawnedTiles++;
            }
        }
        assertEquals(2, spawnedTiles);
    }

    @Test
    public void testInitializeRandomPositionYieldsGameWithTwoTilesInCorrectPosition() {
        TileRandom tileRandom = mock(TileRandom.class);
        when(tileRandom.getRandomPosition(anyInt()))
                .thenReturn(3)
                .thenReturn(2);

        when(tileRandom.getRandomValue()).thenReturn(2);

        GameImpl game = new GameImpl(tileRandom);
        game.initialize();

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if ((x == 0 && y == 3) || (x == 0 && y == 2)) {
                    assertNotEquals(0, game.getValueAt(x, y));
                } else {
                    assertEquals(0, game.getValueAt(x, y));
                }
            }
        }
    }

    @Test
    public void testInitializeRandomPositionYieldsGameWithTwoTilesWithCorrectValues() {
        TileRandom tileRandom = mock(TileRandom.class);
        when(tileRandom.getRandomPosition(anyInt()))
                .thenReturn(3)
                .thenReturn(9);
        when(tileRandom.getRandomValue())
                .thenReturn(4)
                .thenReturn(2);

        GameImpl game = new GameImpl(tileRandom);
        game.initialize();

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (x == 0 && y == 3) {
                    assertEquals(4, game.getValueAt(x, y));
                } else if (x == 2 && y == 2) {
                    assertEquals(2, game.getValueAt(x, y));

                } else {
                    assertEquals(0, game.getValueAt(x, y));
                }
            }
        }
    }

    @Test
    public void testMoveDownMovesTilesToCorrectPosition() {
        TileRandom tileRandom = mock(TileRandom.class);
        when(tileRandom.getRandomPosition(anyInt()))
                .thenReturn(0)
                .thenReturn(0)
                .thenReturn(3);
        when(tileRandom.getRandomValue())
                .thenReturn(4)
                .thenReturn(2);

        GameImpl game = new GameImpl(tileRandom);
        game.initialize();

        game.move(Direction.down);

        // old positions are now empty
        assertEquals(0, game.getValueAt(0, 0));
        assertEquals(0, game.getValueAt(0, 1));
        // tiles have moved to the correct position
        assertEquals(4, game.getValueAt(3, 0));
        assertEquals(2, game.getValueAt(3, 1));
    }

    @Test
    public void testMoveUpMovesTilesToCorrectPosition() {
        TileRandom tileRandom = mock(TileRandom.class);
        when(tileRandom.getRandomPosition(anyInt()))
                .thenReturn(12)
                .thenReturn(12);
        when(tileRandom.getRandomValue())
                .thenReturn(4)
                .thenReturn(2);

        GameImpl game = new GameImpl(tileRandom);
        game.initialize();

        game.move(Direction.up);

        // old positions are now empty
        assertEquals(0, game.getValueAt(3, 0));
        assertEquals(0, game.getValueAt(3, 1));
        // tiles have moved to the correct position
        assertEquals(4, game.getValueAt(0, 0));
        assertEquals(2, game.getValueAt(0, 1));
    }

    @Test
    public void testMoveRightMovesTilesToCorrectPosition() {
        TileRandom tileRandom = mock(TileRandom.class);
        when(tileRandom.getRandomPosition(anyInt()))
                .thenReturn(0)
                .thenReturn(4);
        when(tileRandom.getRandomValue())
                .thenReturn(4)
                .thenReturn(2);

        GameImpl game = new GameImpl(tileRandom);
        game.initialize();

        game.move(Direction.right);

        // old positions are now empty
        assertEquals(0, game.getValueAt(0, 0));
        assertEquals(0, game.getValueAt(1, 0));
        // tiles have moved to the correct position
        assertEquals(4, game.getValueAt(0, 3));
        assertEquals(2, game.getValueAt(1, 3));
    }

    @Test
    public void testMoveLeftMovesTilesToCorrectPosition() {
        TileRandom tileRandom = mock(TileRandom.class);
        when(tileRandom.getRandomPosition(anyInt()))
                .thenReturn(3)
                .thenReturn(6);
        when(tileRandom.getRandomValue())
                .thenReturn(4)
                .thenReturn(2);

        GameImpl game = new GameImpl(tileRandom);
        game.initialize();

        game.move(Direction.left);

        // old positions are now empty
        assertEquals(0, game.getValueAt(0, 3));
        assertEquals(0, game.getValueAt(1, 3));
        // tiles have moved to the correct position
        assertEquals(4, game.getValueAt(0, 0));
        assertEquals(2, game.getValueAt(1, 0));
    }

    @Test
    public void testMoveRightTileCollisionMergesCorrectly() {
        TileRandom tileRandom = mock(TileRandom.class);
        when(tileRandom.getRandomPosition(anyInt()))
                .thenReturn(0)
                .thenReturn(0);
        when(tileRandom.getRandomValue())
                .thenReturn(2)
                .thenReturn(2);

        GameImpl game = new GameImpl(tileRandom);
        game.initialize();

        game.move(Direction.right);

        assertEquals(4, game.getValueAt(0, 3));
        assertEquals(0, game.getValueAt(1, 3));
    }

    @Test
    public void testMoveLeftTileCollisionMergesCorrectly() {
        TileRandom tileRandom = mock(TileRandom.class);
        when(tileRandom.getRandomPosition(anyInt()))
                .thenReturn(3)
                .thenReturn(2)
                .thenReturn(10);
        when(tileRandom.getRandomValue())
                .thenReturn(2)
                .thenReturn(2);

        GameImpl game = new GameImpl(tileRandom);
        game.initialize();

        game.move(Direction.left);

        assertEquals(4, game.getValueAt(0, 0));
        assertEquals(0, game.getValueAt(0, 3));
    }

    @Test
    public void testMoveDownTileCollisionMergesCorrectly() {
        TileRandom tileRandom = mock(TileRandom.class);
        when(tileRandom.getRandomPosition(anyInt()))
                .thenReturn(0)
                .thenReturn(3);
        when(tileRandom.getRandomValue())
                .thenReturn(2)
                .thenReturn(2);

        GameImpl game = new GameImpl(tileRandom);
        game.initialize();

        game.move(Direction.down);

        assertEquals(4, game.getValueAt(3, 0));
        assertEquals(0, game.getValueAt(2, 0));
    }

    @Test
    public void testMoveUpTileCollisionMergesCorrectly() {
        TileRandom tileRandom = mock(TileRandom.class);
        when(tileRandom.getRandomPosition(anyInt()))
                .thenReturn(12)
                .thenReturn(8);
        when(tileRandom.getRandomValue())
                .thenReturn(2)
                .thenReturn(2);

        GameImpl game = new GameImpl(tileRandom);
        game.initialize();

        game.move(Direction.up);

        assertEquals(4, game.getValueAt(0, 0));
        assertEquals(0, game.getValueAt(1, 0));
    }

    @Test
    public void testGetMovesAfterInitializationYieldsCorrectNumberOfMoves() {
        game.initialize();

        assertEquals(0, game.getMoves());
    }

    @Test
    public void testGetMovesAfterMoveYieldsCorrectNumberOfMoves() {
        game.initialize();

        game.move(Direction.down);

        assertEquals(1, game.getMoves());
    }

    @Test
    public void testGetScoreAfterInitializationYieldsCorrectScore() {
        game.initialize();

        assertEquals(0, game.getMoves());
    }

    @Test
    public void testGetScoreAfterMoveYieldsCorrectScore() {
        TileRandom tileRandom = mock(TileRandom.class);
        when(tileRandom.getRandomPosition(anyInt()))
                .thenReturn(12)
                .thenReturn(8);
        when(tileRandom.getRandomValue())
                .thenReturn(2)
                .thenReturn(2);

        GameImpl game = new GameImpl(tileRandom);
        game.initialize();

        game.move(Direction.up);

        assertEquals(4, game.getScore());
    }

    @Test
    public void testToStringAfterInitReturnsCorrectGameboardRepresentation() {
        TileRandom tileRandom = mock(TileRandom.class);
        when(tileRandom.getRandomPosition(anyInt()))
                .thenReturn(3)
                .thenReturn(0);
        when(tileRandom.getRandomValue())
                .thenReturn(2)
                .thenReturn(4);

        GameImpl game = new GameImpl(tileRandom);
        game.initialize();

        String gameboard =
                "Moves: 0   Score: 0\n" +
                        "4     .     .     2     \n" +
                        ".     .     .     .     \n" +
                        ".     .     .     .     \n" +
                        ".     .     .     .     \n";

        assertEquals(gameboard, game.toString());
    }

    @Test
    public void testIsWonWithTile2048InGameboardYieldsTrue() {
        TileRandom tileRandom = mock(TileRandom.class);
        when(tileRandom.getRandomPosition(anyInt()))
                .thenReturn(0);
        when(tileRandom.getRandomValue())
                .thenReturn(2048)
                .thenReturn(2);

        GameImpl game = new GameImpl(tileRandom);
        game.initialize();

        assertTrue(game.isOver());
        assertTrue(game.isWon());
    }

    @Test
    public void testIsOverWithFullBoardAndNoPossibleMergesYieldsTrue() {
        TileRandom tileRandom = mock(TileRandom.class);
        when(tileRandom.getRandomPosition(anyInt()))
                .thenReturn(0);
        when(tileRandom.getRandomValue())
                .thenReturn(2)
                .thenReturn(4)
                .thenReturn(8)
                .thenReturn(16)
                .thenReturn(16)
                .thenReturn(8)
                .thenReturn(4)
                .thenReturn(2)
                .thenReturn(2)
                .thenReturn(4)
                .thenReturn(8)
                .thenReturn(16)
                .thenReturn(16)
                .thenReturn(8)
                .thenReturn(4)
                .thenReturn(2);
        GameImpl game = new GameImpl(tileRandom);
        game.initialize();

        for (int i = 0; i < 14; i++) {
            game.move(Direction.left);
        }

        assertTrue(game.isOver());
    }

    @Test
    public void testIsOverAfterInitializationYieldsFalse() {
        game.initialize();

        assertFalse(game.isOver());
    }

    @Test
    public void testIsOverWithFullBoardAndPossibleMergeVerticalYieldsFalse() {
        TileRandom tileRandom = mock(TileRandom.class);
        when(tileRandom.getRandomPosition(anyInt()))
                .thenReturn(0);
        when(tileRandom.getRandomValue())
                .thenReturn(2)
                .thenReturn(4)
                .thenReturn(8)
                .thenReturn(16)
                .thenReturn(2)
                .thenReturn(4)
                .thenReturn(8)
                .thenReturn(16)
                .thenReturn(256)
                .thenReturn(4)
                .thenReturn(8)
                .thenReturn(16)
                .thenReturn(32)
                .thenReturn(4)
                .thenReturn(8)
                .thenReturn(16);
        GameImpl game = new GameImpl(tileRandom);
        game.initialize();

        for (int i = 0; i < 14; i++) {
            game.move(Direction.left);
        }

        assertFalse(game.isOver());
    }

    @Test
    public void testIsOverWithFullBoardAndPossibleMergeHorizontalYieldsFalse() {
        TileRandom tileRandom = mock(TileRandom.class);
        when(tileRandom.getRandomPosition(anyInt()))
                .thenReturn(0);
        when(tileRandom.getRandomValue())
                .thenReturn(2)
                .thenReturn(4)
                .thenReturn(8)
                .thenReturn(16)
                .thenReturn(2)
                .thenReturn(4)
                .thenReturn(8)
                .thenReturn(16)
                .thenReturn(256)
                .thenReturn(4)
                .thenReturn(8)
                .thenReturn(16)
                .thenReturn(32)
                .thenReturn(4)
                .thenReturn(8)
                .thenReturn(16);
        GameImpl game = new GameImpl(tileRandom);
        game.initialize();

        for (int i = 0; i < 14; i++) {
            game.move(Direction.left);
        }

        assertFalse(game.isOver());
    }

    @Test
    public void testMergingRightWithSpaceMergesCorrectly() {
        TileRandom tileRandom = mock(TileRandom.class);
        when(tileRandom.getRandomPosition(anyInt()))
                .thenReturn(0)
                .thenReturn(2)
                .thenReturn(12);
        when(tileRandom.getRandomValue())
                .thenReturn(2);

        GameImpl game = new GameImpl(tileRandom);
        game.initialize();

        game.move(Direction.right);

        assertEquals(0, game.getValueAt(0, 0));
        assertEquals(0, game.getValueAt(0, 1));
        assertEquals(0, game.getValueAt(0, 2));
        assertEquals(4, game.getValueAt(0, 3));
    }

    @Test
    public void testMergingLeftWithSpaceMergesCorrectly() {
        TileRandom tileRandom = mock(TileRandom.class);
        when(tileRandom.getRandomPosition(anyInt()))
                .thenReturn(0)
                .thenReturn(2)
                .thenReturn(12);
        when(tileRandom.getRandomValue())
                .thenReturn(2);

        GameImpl game = new GameImpl(tileRandom);
        game.initialize();

        game.move(Direction.left);

        assertEquals(4, game.getValueAt(0, 0));
        assertEquals(0, game.getValueAt(0, 1));
        assertEquals(0, game.getValueAt(0, 2));
        assertEquals(0, game.getValueAt(0, 3));
    }

    @Test
    public void testMergingUpWithSpaceMergesCorrectly() {
        TileRandom tileRandom = mock(TileRandom.class);
        when(tileRandom.getRandomPosition(anyInt()))
                .thenReturn(0)
                .thenReturn(11)
                .thenReturn(2);
        when(tileRandom.getRandomValue())
                .thenReturn(2);

        GameImpl game = new GameImpl(tileRandom);
        game.initialize();

        game.move(Direction.up);

        assertEquals(4, game.getValueAt(0, 0));
        assertEquals(0, game.getValueAt(1, 0));
        assertEquals(0, game.getValueAt(2, 0));
        assertEquals(0, game.getValueAt(3, 0));
    }

    @Test
    public void testMergingDownWithSpaceMergesCorrectly() {
        TileRandom tileRandom = mock(TileRandom.class);
        when(tileRandom.getRandomPosition(anyInt()))
                .thenReturn(0)
                .thenReturn(11)
                .thenReturn(2);
        when(tileRandom.getRandomValue())
                .thenReturn(2);

        GameImpl game = new GameImpl(tileRandom);
        game.initialize();

        game.move(Direction.down);

        assertEquals(0, game.getValueAt(0, 0));
        assertEquals(0, game.getValueAt(1, 0));
        assertEquals(0, game.getValueAt(2, 0));
        assertEquals(4, game.getValueAt(3, 0));
    }
}