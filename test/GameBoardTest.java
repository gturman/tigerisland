import org.junit.*;

/**
 * Created by geoff on 3/8/17.
 */

public class GameBoardTest {
    @Test
    public void createGameBoardTest() {
        GameBoard gameboard = new GameBoard();

        Assert.assertTrue(gameboard instanceof GameBoard);
    }

    @Test
    public void placingFirstTileTest() {
        GameBoard gameboard = new GameBoard();
        Tile initialTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        gameboard.setTileAtPosition(102, 102, initialTile);

        Assert.assertEquals(initialTile.getHexA().getHexCoordinate().getColumnPosition(), 102);
        Assert.assertEquals(initialTile.getHexA().getHexCoordinate().getRowPosition(), 102);
        Assert.assertEquals(initialTile.getHexB().getHexCoordinate().getColumnPosition(), 101);
        Assert.assertEquals(initialTile.getHexB().getHexCoordinate().getRowPosition(), 101);
        Assert.assertEquals(initialTile.getHexC().getHexCoordinate().getColumnPosition(), 102);
        Assert.assertEquals(initialTile.getHexC().getHexCoordinate().getRowPosition(), 101);

        Assert.assertEquals(gameboard.getGameBoardPositionArray()[102][102], initialTile.getHexA());
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[101][101], initialTile.getHexB());
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[102][101], initialTile.getHexC());
    }

    @Test
    public void placeFlippedTileTest() {
        GameBoard gameboard = new GameBoard();
        Tile initialTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        initialTile.flip();
        gameboard.setTileAtPosition(102, 102, initialTile);

        Assert.assertEquals(initialTile.getHexA().getHexCoordinate().getColumnPosition(), 102);
        Assert.assertEquals(initialTile.getHexA().getHexCoordinate().getRowPosition(), 102);
        Assert.assertEquals(initialTile.getHexB().getHexCoordinate().getColumnPosition(), 101);
        Assert.assertEquals(initialTile.getHexB().getHexCoordinate().getRowPosition(), 103);
        Assert.assertEquals(initialTile.getHexC().getHexCoordinate().getColumnPosition(), 102);
        Assert.assertEquals(initialTile.getHexC().getHexCoordinate().getRowPosition(), 103);

        Assert.assertEquals(gameboard.getGameBoardPositionArray()[102][102], initialTile.getHexA());
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[101][103], initialTile.getHexB());
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[102][103], initialTile.getHexC());

    }

    @Test
    public void testAbilityToIncrementHexAndTileID() {
        GameBoard gameboard = new GameBoard();

        Assert.assertEquals(gameboard.getGameBoardHexID(), 1);
        Assert.assertEquals(gameboard.getGameboardTileID(), 1);

        Tile initialTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);

        gameboard.setTileAtPosition(102, 102, initialTile);

        Assert.assertEquals(gameboard.getGameBoardHexID(), 4);
        Assert.assertEquals(gameboard.getGameboardTileID(), 2);
    }

    @Test
    public void testAbilityToUpdateNonFlippedValidTilePlacementList() {
        GameBoard gameboard = new GameBoard();

        Tile initialTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);

        gameboard.setTileAtPosition(102, 102, initialTile);

        Assert.assertEquals(gameboard.getValidPlacementArray()[102][102], -1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[101][101], -1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[102][101], -1);

        Assert.assertEquals(gameboard.getValidPlacementArray()[101][100], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[102][100], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[103][100], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[103][101], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[103][102], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[102][103], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[101][103], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[101][102], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[100][101], 1);

        Tile secondTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);

        gameboard.setTileAtPosition(102, 100, secondTile);

        Assert.assertEquals(gameboard.getValidPlacementArray()[102][102], -1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[101][101], -1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[102][101], -1);

        Assert.assertEquals(gameboard.getValidPlacementArray()[102][100], -1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[101][99], -1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[102][99], -1);

        Assert.assertEquals(gameboard.getValidPlacementArray()[101][100], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[100][99], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[101][98], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[102][98], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[103][98], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[103][99], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[103][100], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[103][100], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[103][101], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[103][102], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[102][103], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[101][103], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[101][102], 1);
    }

    @Test
    public void testAbilityToUpdateFlippedValidTilePlacementList() {
        GameBoard gameboard = new GameBoard();

        Tile initialTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);

        initialTile.flip();

        gameboard.setTileAtPosition(102, 102, initialTile);

        Assert.assertEquals(gameboard.getValidPlacementArray()[102][102], -1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[101][103], -1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[102][103], -1);

        Assert.assertEquals(gameboard.getValidPlacementArray()[101][101], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[102][101], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[103][102], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[103][103], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[103][104], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[102][104], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[101][104], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[100][103], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[101][102], 1);

        Tile secondTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);

        secondTile.flip();

        gameboard.setTileAtPosition(102, 104, secondTile);

        Assert.assertEquals(gameboard.getValidPlacementArray()[102][102], -1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[101][103], -1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[102][103], -1);

        Assert.assertEquals(gameboard.getValidPlacementArray()[102][104], -1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[101][105], -1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[102][105], -1);

        Assert.assertEquals(gameboard.getValidPlacementArray()[101][101], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[102][101], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[103][102], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[103][103], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[103][104], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[103][105], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[103][106], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[102][106], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[101][106], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[100][105], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[101][104], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[100][103], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[101][102], 1);
    }


    @Test
    public void checkingIfValidArrayInitializesToZero() {
        GameBoard gameboard = new GameBoard();

        Assert.assertEquals(gameboard.getValidPlacementArray()[0][0], 0);
        Assert.assertEquals(gameboard.getValidPlacementArray()[102][102], 0);
    }

    @Test
    public void checkIfTileBeingPlacedWillBeAdjacentTest() {
        GameBoard gameboard = new GameBoard();
        Tile initialTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        gameboard.setTileAtPosition(102, 102, initialTile);

        Tile secondTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);

        //unflipped
        Assert.assertEquals(gameboard.checkIfTileBeingPlacedWillBeAdjacent(103, 101, secondTile), true);
        Assert.assertEquals(gameboard.checkIfTileBeingPlacedWillBeAdjacent(100, 101, secondTile), true);
        Assert.assertEquals(gameboard.checkIfTileBeingPlacedWillBeAdjacent(100, 100, secondTile), true);
        Assert.assertEquals(gameboard.checkIfTileBeingPlacedWillBeAdjacent(104, 104, secondTile), true);

        Assert.assertEquals(gameboard.checkIfTileBeingPlacedWillBeAdjacent(99, 99, secondTile), false);
        Assert.assertEquals(gameboard.checkIfTileBeingPlacedWillBeAdjacent(50, 50, secondTile), false);
        Assert.assertEquals(gameboard.checkIfTileBeingPlacedWillBeAdjacent(105, 105, secondTile), false);

        //flipped
        secondTile.flip();
        Assert.assertEquals(gameboard.checkIfTileBeingPlacedWillBeAdjacent(103, 103, secondTile), true);
        Assert.assertEquals(gameboard.checkIfTileBeingPlacedWillBeAdjacent(101, 102, secondTile), true);
        Assert.assertEquals(gameboard.checkIfTileBeingPlacedWillBeAdjacent(99, 99, secondTile), true);
        Assert.assertEquals(gameboard.checkIfTileBeingPlacedWillBeAdjacent(103, 101, secondTile), true);

        Assert.assertEquals(gameboard.checkIfTileBeingPlacedWillBeAdjacent(98, 99, secondTile), false);
        Assert.assertEquals(gameboard.checkIfTileBeingPlacedWillBeAdjacent(50, 50, secondTile), false);
        Assert.assertEquals(gameboard.checkIfTileBeingPlacedWillBeAdjacent(105, 105, secondTile), false);
    }

    @Test
    public void testIfNukeTestFailsOnEntireTileCoverup() {
        GameBoard gameboard = new GameBoard();

        Tile initialTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.LAKE, terrainTypes.GRASSLANDS);
        gameboard.setTileAtPosition(102, 102, initialTile);

        Tile secondTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        gameboard.setTileAtPosition(103, 101, secondTile);

        Assert.assertEquals(gameboard.checkIfValidNuke(102, 102, secondTile), false);
    }

    @Test
    public void testIfNukeTestFailsOnDifferentLevelCoverup() {
        GameBoard gameboard = new GameBoard();
        Tile initialTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameboard.setTileAtPosition(102, 102, initialTile);

        Tile secondTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        gameboard.setTileAtPosition(103, 101, secondTile);

        Tile thirdTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.ROCKY, terrainTypes.VOLCANO, terrainTypes.ROCKY);
        gameboard.setTileAtPosition(103, 103, thirdTile);

        gameboard.getGameBoardPositionArray()[103][102].setHexLevel(2);

        Tile fourthTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        fourthTile.flip();

        Tile fifthTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);

        Assert.assertEquals(gameboard.checkIfValidNuke(102, 101, fourthTile), false);
        Assert.assertEquals(gameboard.checkIfValidNuke(103, 102, fifthTile), false);
    }

    @Test
    public void testIfNukeTestFailsOnNukingOverEmptyGameboard() {
        GameBoard gameboard = new GameBoard();
        Tile initialTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.VOLCANO, terrainTypes.VOLCANO);
        gameboard.setTileAtPosition(102, 102, initialTile);

        Tile secondTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.ROCKY);

        Assert.assertEquals(gameboard.checkIfValidNuke(102, 101, secondTile), false);
        Assert.assertEquals(gameboard.checkIfValidNuke(101, 101, secondTile), false);
        Assert.assertEquals(gameboard.checkIfValidNuke(104, 105, secondTile), false);

        secondTile.flip();

        Assert.assertEquals(gameboard.checkIfValidNuke(102, 102, secondTile), false);
        Assert.assertEquals(gameboard.checkIfValidNuke(101, 101, secondTile), false);
        Assert.assertEquals(gameboard.checkIfValidNuke(102, 101, secondTile), false);
        Assert.assertEquals(gameboard.checkIfValidNuke(104, 105, secondTile), false);
    }

    @Test
    public void testlIfNukeTestFailsOnVolcanoOverNonVolcano() {
        GameBoard gameboard = new GameBoard();
        Tile initialTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameboard.setTileAtPosition(102, 102, initialTile);

        Tile secondTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.ROCKY, terrainTypes.VOLCANO, terrainTypes.ROCKY);
        gameboard.setTileAtPosition(103, 101, secondTile);

        Tile thirdTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.ROCKY, terrainTypes.ROCKY, terrainTypes.VOLCANO);
        gameboard.setTileAtPosition(103, 103, thirdTile);

        Tile fourthTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.ROCKY);
        fourthTile.flip();

        Tile fifthTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.JUNGLE, terrainTypes.JUNGLE, terrainTypes.VOLCANO);

        Assert.assertEquals(gameboard.checkIfValidNuke(102, 101, fourthTile), false);
        Assert.assertEquals(gameboard.checkIfValidNuke(103, 102, fifthTile), false);
    }

    @Test
    public void testIfNukeTestPassesForValidNuking() {
        GameBoard gameboard = new GameBoard();
        Tile initialTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.ROCKY, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameboard.setTileAtPosition(102, 102, initialTile);

        Tile secondTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);

        gameboard.setTileAtPosition(103, 101, secondTile);

        Tile thirdTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);

        gameboard.setTileAtPosition(103, 103, thirdTile);

        Tile fourthTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        fourthTile.flip();
        gameboard.setTileAtPosition(101, 99, fourthTile);

        Tile fifthTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        fifthTile.flip();
        gameboard.setTileAtPosition(101, 102, fifthTile);

        Tile sixthTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.LAKE, terrainTypes.GRASSLANDS);
        sixthTile.flip();
        gameboard.setTileAtPosition(100, 100, sixthTile);

        Tile seventhTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.ROCKY);

        Tile eigthTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.LAKE, terrainTypes.GRASSLANDS);

        Assert.assertEquals(gameboard.checkIfValidNuke(102, 101, seventhTile), true);
        Assert.assertEquals(gameboard.checkIfValidNuke(101, 102, eigthTile), true);
    }

    @Test
    public void testIfNukeOverwritesHexValues() {
                GameBoard gameboard = new GameBoard();
        Tile initialTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.ROCKY, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameboard.setTileAtPosition(102, 102, initialTile);

        Tile secondTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);

        gameboard.setTileAtPosition(103, 101, secondTile);

        Tile thirdTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);

        gameboard.setTileAtPosition(103, 103, thirdTile);

        Tile fourthTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        fourthTile.flip();
        gameboard.setTileAtPosition(101, 99, fourthTile);

        Tile fifthTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        fifthTile.flip();
        gameboard.setTileAtPosition(101, 102, fifthTile);

        Tile sixthTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.LAKE, terrainTypes.GRASSLANDS);
        sixthTile.flip();
        gameboard.setTileAtPosition(100, 100, sixthTile);

        Tile seventhTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.ROCKY);
        seventhTile.flip();

        Tile eigthTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.LAKE, terrainTypes.GRASSLANDS);

        gameboard.nukeTiles(102, 101, seventhTile);
        gameboard.nukeTiles(101, 102, eigthTile);

        Assert.assertEquals(gameboard.getGameBoardPositionArray()[102][101], seventhTile.getHexA());
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[103][102], seventhTile.getHexB());
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[102][102], seventhTile.getHexC());

        Assert.assertEquals(gameboard.getGameBoardPositionArray()[101][102], eigthTile.getHexA());
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[100][101], eigthTile.getHexB());
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[101][101], eigthTile.getHexC());
    }

    @Test
    public void TestHexLevelIncreasesWhenNuking(){
        GameBoard gameboard = new GameBoard();
        Tile initialTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.ROCKY, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameboard.setTileAtPosition(102, 102, initialTile);

        Tile secondTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);

        gameboard.setTileAtPosition(103, 101, secondTile);

        Tile thirdTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);

        gameboard.setTileAtPosition(103, 103, thirdTile);

        Tile fourthTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        fourthTile.flip();
        gameboard.setTileAtPosition(101, 99, fourthTile);

        Tile fifthTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        fifthTile.flip();
        gameboard.setTileAtPosition(101, 102, fifthTile);

        Tile sixthTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.LAKE, terrainTypes.GRASSLANDS);
        sixthTile.flip();
        gameboard.setTileAtPosition(100, 100, sixthTile);

        Tile seventhTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.ROCKY);
        seventhTile.flip();

        Tile eigthTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.LAKE, terrainTypes.GRASSLANDS);

        gameboard.nukeTiles(102, 101, seventhTile);
        gameboard.nukeTiles(101, 102, eigthTile);

        Assert.assertEquals(gameboard.getGameBoardPositionArray()[102][101].getHexLevel(), 1);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[103][102].getHexLevel(), 1);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[102][102].getHexLevel(), 1);

        Assert.assertEquals(gameboard.getGameBoardPositionArray()[101][102].getHexLevel(), 1);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[100][101].getHexLevel(), 1);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[101][101].getHexLevel(), 1);
    }

    @Test
    public void testPreventingNukeIfTotoroIsOnAHex() {
        GameBoard gameboard = new GameBoard();
        Tile initialTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameboard.setTileAtPosition(102, 102, initialTile);

        Tile secondTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        gameboard.setTileAtPosition(103, 101, secondTile);

        Tile thirdTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.ROCKY, terrainTypes.VOLCANO, terrainTypes.ROCKY);
        gameboard.setTileAtPosition(103, 103, thirdTile);

        gameboard.getGameBoardPositionArray()[103][102].setTotoroCount(1);

        Tile fourthTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        fourthTile.flip();

        Tile fifthTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);

        Assert.assertEquals(gameboard.checkIfValidNuke(102, 101, fourthTile), false);
        Assert.assertEquals(gameboard.checkIfValidNuke(103, 102, fifthTile), false);
    }

    @Test
    public void testPreventingNukeIfTigerPenIsOnAHex() {
        GameBoard gameboard = new GameBoard();
        Tile initialTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameboard.setTileAtPosition(102, 102, initialTile);

        Tile secondTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        gameboard.setTileAtPosition(103, 101, secondTile);

        Tile thirdTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.ROCKY, terrainTypes.VOLCANO, terrainTypes.ROCKY);
        gameboard.setTileAtPosition(103, 103, thirdTile);

        gameboard.getGameBoardPositionArray()[103][102].setTigerCount(1);

        Tile fourthTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        fourthTile.flip();

        Tile fifthTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);

        Assert.assertEquals(gameboard.checkIfValidNuke(102, 101, fourthTile), false);
        Assert.assertEquals(gameboard.checkIfValidNuke(103, 102, fifthTile), false);
    }

    @Test
    public void testPreventingNukeIfNukeCoversSize1Settlement() {
        GameBoard gameboard = new GameBoard();
        Tile initialTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameboard.setTileAtPosition(102, 102, initialTile);

        Tile secondTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        gameboard.setTileAtPosition(103, 101, secondTile);

        Tile thirdTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.ROCKY, terrainTypes.VOLCANO, terrainTypes.ROCKY);
        gameboard.setTileAtPosition(103, 103, thirdTile);

        gameboard.getGameBoardPositionArray()[103][102].setSettlementID(1);
        gameboard.setSettlementSizeList(1, 1);

        Tile fourthTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        fourthTile.flip();

        Tile fifthTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.ROCKY, terrainTypes.GRASSLANDS, terrainTypes.VOLCANO);

        gameboard.getGameBoardPositionArray()[102][102].setSettlementID(2);
        gameboard.setSettlementSizeList(2, 1);

        Assert.assertEquals(gameboard.checkIfValidNuke(102, 101, fourthTile), false);
        Assert.assertEquals(gameboard.checkIfValidNuke(102, 102, fifthTile), false);
    }

    @Test
    public void whereTheFuckAreOurTerrains(){
        GameBoard gameboard = new GameBoard();

        Tile initialTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.ROCKY, terrainTypes.LAKE, terrainTypes.VOLCANO);

        gameboard.setTileAtPosition(102, 102, initialTile);

        Assert.assertEquals(gameboard.getGameBoardPositionArray()[102][102].getHexTerrainType(), terrainTypes.ROCKY);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[101][101].getHexTerrainType(), terrainTypes.LAKE);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[102][101].getHexTerrainType(), terrainTypes.VOLCANO);

        GameBoard secondGameboard = new GameBoard();

        Tile secondTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.ROCKY, terrainTypes.LAKE, terrainTypes.VOLCANO);

        secondTile.flip();

        secondGameboard.setTileAtPosition(102, 102, secondTile);

        Assert.assertEquals(secondGameboard.getGameBoardPositionArray()[102][102].getHexTerrainType(), terrainTypes.ROCKY);
        Assert.assertEquals(secondGameboard.getGameBoardPositionArray()[101][103].getHexTerrainType(), terrainTypes.VOLCANO);
        Assert.assertEquals(secondGameboard.getGameBoardPositionArray()[102][103].getHexTerrainType(), terrainTypes.LAKE);

    }
}