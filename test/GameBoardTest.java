import org.junit.*;

/**
 * Created by geoff on 3/8/17.
 */

public class GameBoardTest {

    @Test
    public void createGameBoardTest() {
        GameBoard gameBoard = new GameBoard();

        Assert.assertTrue(gameBoard instanceof GameBoard);
    }

    @Test
    public void placingFirstTileUpdatesHexAndTileIDsProperlyTest() {
        GameBoard gameBoard = new GameBoard();

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Assert.assertEquals(gameBoard.getGameBoardHexID(), 6);
        Assert.assertEquals(gameBoard.getGameBoardTileID(), 2);
    }

    @Test
    public void placingFirstTileUpdatesPositionArrayProperlyTest() {
        GameBoard gameBoard = new GameBoard();

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Hex testGameBoardHexCenter = new Hex(1, 1, terrainTypes.VOLCANO);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][102].getHexID(), testGameBoardHexCenter.getHexID());
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][102].getParentTileID(), testGameBoardHexCenter.getParentTileID());
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][102].getTerrainType(), testGameBoardHexCenter.getTerrainType());

        Hex testGameBoardHexTopLeft = new Hex(2, 1, terrainTypes.JUNGLE);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][101].getHexID(), testGameBoardHexTopLeft.getHexID());
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][101].getParentTileID(), testGameBoardHexTopLeft.getParentTileID());
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][101].getTerrainType(), testGameBoardHexTopLeft.getTerrainType());

        Hex testGameBoardHexTopRight = new Hex(3, 1, terrainTypes.LAKE);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][101].getHexID(), testGameBoardHexTopRight.getHexID());
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][101].getParentTileID(), testGameBoardHexTopRight.getParentTileID());
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][101].getTerrainType(), testGameBoardHexTopRight.getTerrainType());

        Hex testGameBoardHexBottomLeft = new Hex(4, 1, terrainTypes.ROCKY);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][103].getHexID(), testGameBoardHexBottomLeft.getHexID());
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][103].getParentTileID(), testGameBoardHexBottomLeft.getParentTileID());
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][103].getTerrainType(), testGameBoardHexBottomLeft.getTerrainType());

        Hex testGameBoardHexBottomRight = new Hex(5, 1, terrainTypes.GRASSLANDS);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][103].getHexID(), testGameBoardHexBottomRight.getHexID());
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][103].getParentTileID(), testGameBoardHexBottomRight.getParentTileID());
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][103].getTerrainType(), testGameBoardHexBottomRight.getTerrainType());
    }

    @Test
    public void placingFirstTileUpdatesValidPlacementArrayProperlyTest() {
        GameBoard gameBoard = new GameBoard();

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Assert.assertEquals(gameBoard.getValidPlacementArray()[102][102], -1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[101][101], -1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[102][101], -1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[101][103], -1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[102][103], -1);

        Assert.assertEquals(gameBoard.getValidPlacementArray()[101][100], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[102][100], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[103][100], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[103][101], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[103][102], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[103][103], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[103][104], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[102][104], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[101][104], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[100][103], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[101][102], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[100][101], 1);
    }

    @Test
    public void placeFlippedTileTest() {
        GameBoard gameBoard = new GameBoard();
        Tile initialTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        initialTile.flip();
        gameBoard.setTileAtPosition(102, 102, initialTile);

        Assert.assertEquals(initialTile.getHexA().getCoordinatePair().getColumnPosition(), 102);
        Assert.assertEquals(initialTile.getHexA().getCoordinatePair().getRowPosition(), 102);
        Assert.assertEquals(initialTile.getHexB().getCoordinatePair().getColumnPosition(), 101);
        Assert.assertEquals(initialTile.getHexB().getCoordinatePair().getRowPosition(), 103);
        Assert.assertEquals(initialTile.getHexC().getCoordinatePair().getColumnPosition(), 102);
        Assert.assertEquals(initialTile.getHexC().getCoordinatePair().getRowPosition(), 103);

        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][102], initialTile.getHexA());
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][103], initialTile.getHexB());
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][103], initialTile.getHexC());

    }

    @Test
    public void testAbilityToIncrementHexAndTileID() {
        GameBoard gameBoard = new GameBoard();

        Assert.assertEquals(gameBoard.getGameBoardHexID(), 1);
        Assert.assertEquals(gameBoard.getGameBoardTileID(), 1);

        Tile initialTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);

        gameBoard.setTileAtPosition(102, 102, initialTile);

        Assert.assertEquals(gameBoard.getGameBoardHexID(), 4);
        Assert.assertEquals(gameBoard.getGameBoardTileID(), 2);
    }

    @Test
    public void testAbilityToUpdateNonFlippedValidTilePlacementList() {
        GameBoard gameBoard = new GameBoard();

        Tile initialTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);

        gameBoard.setTileAtPosition(102, 102, initialTile);

        Assert.assertEquals(gameBoard.getValidPlacementArray()[102][102], -1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[101][101], -1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[102][101], -1);

        Assert.assertEquals(gameBoard.getValidPlacementArray()[101][100], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[102][100], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[103][100], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[103][101], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[103][102], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[102][103], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[101][103], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[101][102], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[100][101], 1);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);

        gameBoard.setTileAtPosition(102, 100, secondTile);

        Assert.assertEquals(gameBoard.getValidPlacementArray()[102][102], -1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[101][101], -1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[102][101], -1);

        Assert.assertEquals(gameBoard.getValidPlacementArray()[102][100], -1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[101][99], -1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[102][99], -1);

        Assert.assertEquals(gameBoard.getValidPlacementArray()[101][100], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[100][99], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[101][98], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[102][98], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[103][98], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[103][99], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[103][100], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[103][100], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[103][101], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[103][102], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[102][103], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[101][103], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[101][102], 1);
    }

    @Test
    public void testAbilityToUpdateFlippedValidTilePlacementList() {
        GameBoard gameBoard = new GameBoard();

        Tile initialTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);

        initialTile.flip();

        gameBoard.setTileAtPosition(102, 102, initialTile);

        Assert.assertEquals(gameBoard.getValidPlacementArray()[102][102], -1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[101][103], -1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[102][103], -1);

        Assert.assertEquals(gameBoard.getValidPlacementArray()[101][101], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[102][101], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[103][102], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[103][103], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[103][104], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[102][104], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[101][104], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[100][103], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[101][102], 1);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);

        secondTile.flip();

        gameBoard.setTileAtPosition(102, 104, secondTile);

        Assert.assertEquals(gameBoard.getValidPlacementArray()[102][102], -1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[101][103], -1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[102][103], -1);

        Assert.assertEquals(gameBoard.getValidPlacementArray()[102][104], -1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[101][105], -1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[102][105], -1);

        Assert.assertEquals(gameBoard.getValidPlacementArray()[101][101], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[102][101], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[103][102], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[103][103], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[103][104], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[103][105], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[103][106], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[102][106], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[101][106], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[100][105], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[101][104], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[100][103], 1);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[101][102], 1);
    }

    @Test
    public void checkingIfValidArrayInitializesToZero() {
        GameBoard gameBoard = new GameBoard();

        Assert.assertEquals(gameBoard.getValidPlacementArray()[0][0], 0);
        Assert.assertEquals(gameBoard.getValidPlacementArray()[102][102], 0);
    }

    @Test
    public void checkIfTileBeingPlacedWillBeAdjacentTest() {
        GameBoard gameBoard = new GameBoard();
        Tile initialTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);
        gameBoard.setTileAtPosition(102, 102, initialTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);

        //unflipped
        Assert.assertEquals(gameBoard.checkIfTileBeingPlacedWillBeAdjacent(103, 101, secondTile), true);
        Assert.assertEquals(gameBoard.checkIfTileBeingPlacedWillBeAdjacent(100, 101, secondTile), true);
        Assert.assertEquals(gameBoard.checkIfTileBeingPlacedWillBeAdjacent(100, 100, secondTile), true);
        Assert.assertEquals(gameBoard.checkIfTileBeingPlacedWillBeAdjacent(104, 104, secondTile), true);

        Assert.assertEquals(gameBoard.checkIfTileBeingPlacedWillBeAdjacent(99, 99, secondTile), false);
        Assert.assertEquals(gameBoard.checkIfTileBeingPlacedWillBeAdjacent(50, 50, secondTile), false);
        Assert.assertEquals(gameBoard.checkIfTileBeingPlacedWillBeAdjacent(105, 105, secondTile), false);

        //flipped
        secondTile.flip();
        Assert.assertEquals(gameBoard.checkIfTileBeingPlacedWillBeAdjacent(103, 103, secondTile), true);
        Assert.assertEquals(gameBoard.checkIfTileBeingPlacedWillBeAdjacent(101, 102, secondTile), true);
        Assert.assertEquals(gameBoard.checkIfTileBeingPlacedWillBeAdjacent(99, 99, secondTile), true);
        Assert.assertEquals(gameBoard.checkIfTileBeingPlacedWillBeAdjacent(103, 101, secondTile), true);

        Assert.assertEquals(gameBoard.checkIfTileBeingPlacedWillBeAdjacent(98, 99, secondTile), false);
        Assert.assertEquals(gameBoard.checkIfTileBeingPlacedWillBeAdjacent(50, 50, secondTile), false);
        Assert.assertEquals(gameBoard.checkIfTileBeingPlacedWillBeAdjacent(105, 105, secondTile), false);
    }

    @Test
    public void testIfNukeTestFailsOnEntireTileCoverup() {
        GameBoard gameBoard = new GameBoard();

        Tile initialTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.LAKE, terrainTypes.GRASSLANDS);
        gameBoard.setTileAtPosition(102, 102, initialTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        gameBoard.setTileAtPosition(103, 101, secondTile);

        Assert.assertEquals(gameBoard.nukeAtPositionIsValid(102, 102, secondTile), false);
    }

    @Test
    public void testIfNukeTestFailsOnDifferentLevelCoverup() {
        GameBoard gameBoard = new GameBoard();
        Tile initialTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(102, 102, initialTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        gameBoard.setTileAtPosition(103, 101, secondTile);

        Tile thirdTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.ROCKY, terrainTypes.VOLCANO, terrainTypes.ROCKY);
        gameBoard.setTileAtPosition(103, 103, thirdTile);

        gameBoard.getGameBoardPositionArray()[103][102].setLevel(2);

        Tile fourthTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        fourthTile.flip();

        Tile fifthTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);

        Assert.assertEquals(gameBoard.nukeAtPositionIsValid(102, 101, fourthTile), false);
        Assert.assertEquals(gameBoard.nukeAtPositionIsValid(103, 102, fifthTile), false);
    }

    @Test
    public void testIfNukeTestFailsOnNukingOverEmptyGameboard() {
        GameBoard gameBoard = new GameBoard();
        Tile initialTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.VOLCANO, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(102, 102, initialTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.ROCKY);

        Assert.assertEquals(gameBoard.nukeAtPositionIsValid(102, 101, secondTile), false);
        Assert.assertEquals(gameBoard.nukeAtPositionIsValid(101, 101, secondTile), false);
        Assert.assertEquals(gameBoard.nukeAtPositionIsValid(104, 105, secondTile), false);

        secondTile.flip();

        Assert.assertEquals(gameBoard.nukeAtPositionIsValid(102, 102, secondTile), false);
        Assert.assertEquals(gameBoard.nukeAtPositionIsValid(101, 101, secondTile), false);
        Assert.assertEquals(gameBoard.nukeAtPositionIsValid(102, 101, secondTile), false);
        Assert.assertEquals(gameBoard.nukeAtPositionIsValid(104, 105, secondTile), false);
    }

    @Test
    public void testlIfNukeTestFailsOnVolcanoOverNonVolcano() {
        GameBoard gameBoard = new GameBoard();
        Tile initialTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(102, 102, initialTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.ROCKY, terrainTypes.VOLCANO, terrainTypes.ROCKY);
        gameBoard.setTileAtPosition(103, 101, secondTile);

        Tile thirdTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.ROCKY, terrainTypes.ROCKY, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(103, 103, thirdTile);

        Tile fourthTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.ROCKY);
        fourthTile.flip();

        Tile fifthTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.JUNGLE, terrainTypes.JUNGLE, terrainTypes.VOLCANO);

        Assert.assertEquals(gameBoard.nukeAtPositionIsValid(102, 101, fourthTile), false);
        Assert.assertEquals(gameBoard.nukeAtPositionIsValid(103, 102, fifthTile), false);
    }

    @Test
    public void testIfNukeTestPassesForValidNuking() {
        GameBoard gameBoard = new GameBoard();
        Tile initialTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.ROCKY, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(102, 102, initialTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);

        gameBoard.setTileAtPosition(103, 101, secondTile);

        Tile thirdTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);

        gameBoard.setTileAtPosition(103, 103, thirdTile);

        Tile fourthTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        fourthTile.flip();
        gameBoard.setTileAtPosition(101, 99, fourthTile);

        Tile fifthTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        fifthTile.flip();
        gameBoard.setTileAtPosition(101, 102, fifthTile);

        Tile sixthTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.LAKE, terrainTypes.GRASSLANDS);
        sixthTile.flip();
        gameBoard.setTileAtPosition(100, 100, sixthTile);

        Tile seventhTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.ROCKY);

        Tile eigthTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.LAKE, terrainTypes.GRASSLANDS);

        Assert.assertEquals(gameBoard.nukeAtPositionIsValid(102, 101, seventhTile), true);
        Assert.assertEquals(gameBoard.nukeAtPositionIsValid(101, 102, eigthTile), true);
    }

    @Test
    public void testIfNukeWorksVolcanoOnA() {
        GameBoard gameBoard = new GameBoard();

        Tile tileOne = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.ROCKY, terrainTypes.ROCKY, terrainTypes.JUNGLE);
        gameBoard.setTileAtPosition(102, 102, tileOne);

        Tile tileTwo = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.ROCKY, terrainTypes.VOLCANO, terrainTypes.ROCKY);
        gameBoard.setTileAtPosition(102, 104, tileTwo);

        Tile tileThree = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.ROCKY, terrainTypes.VOLCANO);
        tileThree.flip();

        Assert.assertEquals(gameBoard.nukeAtPositionIsValid(102, 102, tileThree), true);
    }

    @Test
    public void testIfNukeWorksVolcanoOnB() {
        GameBoard gameBoard = new GameBoard();

        Tile tileOne = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.JUNGLE);
        gameBoard.setTileAtPosition(102, 102, tileOne);

        Tile tileTwo = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.ROCKY, terrainTypes.JUNGLE);
        tileTwo.flip();
        gameBoard.setTileAtPosition(101, 102, tileTwo);

        Tile tileThree = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.ROCKY, terrainTypes.JUNGLE);
        tileThree.flip();

        Assert.assertEquals(gameBoard.nukeAtPositionIsValid(101, 101, tileThree), true);
    }

    @Test
    public void testIfNukeWorksVolcanoOnC() {
        GameBoard gameBoard = new GameBoard();

        Tile tileOne = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.GRASSLANDS, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(102, 102, tileOne);

        Tile tileTwo = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.ROCKY, terrainTypes.ROCKY);
        tileTwo.flip();
        gameBoard.setTileAtPosition(103, 102, tileTwo);

        Tile tileThree = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.LAKE, terrainTypes.VOLCANO, terrainTypes.JUNGLE);
        tileThree.flip();

        Assert.assertEquals(gameBoard.nukeAtPositionIsValid(102, 101, tileThree), true);
    }

    @Test
    public void testIfNukeOverwritesHexValues() {
        GameBoard gameBoard = new GameBoard();
        Tile initialTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.ROCKY, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(102, 102, initialTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);

        gameBoard.setTileAtPosition(103, 101, secondTile);

        Tile thirdTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);

        gameBoard.setTileAtPosition(103, 103, thirdTile);

        Tile fourthTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        fourthTile.flip();
        gameBoard.setTileAtPosition(101, 99, fourthTile);

        Tile fifthTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        fifthTile.flip();
        gameBoard.setTileAtPosition(101, 102, fifthTile);

        Tile sixthTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.LAKE, terrainTypes.GRASSLANDS);
        sixthTile.flip();
        gameBoard.setTileAtPosition(100, 100, sixthTile);

        Tile seventhTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.ROCKY);
        seventhTile.flip();

        Tile eigthTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.LAKE, terrainTypes.GRASSLANDS);

        gameBoard.nukeTiles(102, 101, seventhTile);
        gameBoard.nukeTiles(101, 102, eigthTile);

        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][101], seventhTile.getHexA());
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][101].getParentTileID(), seventhTile.getHexA().getParentTileID());
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[103][102], seventhTile.getHexC());
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[103][102].getParentTileID(), seventhTile.getHexB().getParentTileID());
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][102], seventhTile.getHexB());
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][102].getParentTileID(), seventhTile.getHexC().getParentTileID());

        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][102], eigthTile.getHexA());
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][102].getParentTileID(), eigthTile.getHexA().getParentTileID());
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[100][101], eigthTile.getHexB());
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[100][101].getParentTileID(), eigthTile.getHexB().getParentTileID());
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][101], eigthTile.getHexC());
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][101].getParentTileID(), eigthTile.getHexC().getParentTileID());
    }

    @Test
    public void testIfNukeWorksForEvenAndFlippedTileWithVolcanoAtHexC() {
        GameBoard game = new GameBoard();
        Player player = new Player(1);

        game.placeFirstTileAndUpdateValidPlacementList();

        Tile secondTile = new Tile(game.getGameBoardTileID(), game.getGameBoardHexID(), terrainTypes.ROCKY, terrainTypes.GRASSLANDS, terrainTypes.VOLCANO);
        game.setTileAtPosition(103, 103, secondTile);

        Assert.assertEquals(game.getGameBoardPositionArray()[102][101].getTerrainType(), terrainTypes.LAKE);
        Assert.assertEquals(game.getGameBoardPositionArray()[102][102].getTerrainType(), terrainTypes.VOLCANO);
        Assert.assertEquals(game.getGameBoardPositionArray()[103][102].getTerrainType(), terrainTypes.GRASSLANDS);

        Tile thirdTile = new Tile(game.getGameBoardTileID(), game.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.JUNGLE, terrainTypes.VOLCANO);
        thirdTile.flip();

        game.nukeTiles(102, 101, thirdTile);

        Assert.assertEquals(game.getGameBoardPositionArray()[102][101].getTerrainType(), terrainTypes.GRASSLANDS);
        Assert.assertEquals(game.getGameBoardPositionArray()[102][102].getTerrainType(), terrainTypes.VOLCANO);
        Assert.assertEquals(game.getGameBoardPositionArray()[103][102].getTerrainType(), terrainTypes.JUNGLE);
    }

    @Test
    public void testIfNukeWorksForEvenAndNotFlippedTileWithVolcanoAtHexC() {
        GameBoard game = new GameBoard();
        Player player = new Player(1);

        game.placeFirstTileAndUpdateValidPlacementList();

        Tile secondTile = new Tile(game.getGameBoardTileID(), game.getGameBoardHexID(), terrainTypes.ROCKY, terrainTypes.GRASSLANDS, terrainTypes.VOLCANO);
        game.setTileAtPosition(103, 103, secondTile);

        Assert.assertEquals(game.getGameBoardPositionArray()[102][103].getTerrainType(), terrainTypes.GRASSLANDS);
        Assert.assertEquals(game.getGameBoardPositionArray()[102][102].getTerrainType(), terrainTypes.VOLCANO);
        Assert.assertEquals(game.getGameBoardPositionArray()[103][102].getTerrainType(), terrainTypes.GRASSLANDS);

        Tile fourthTile = new Tile(game.getGameBoardTileID(), game.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        game.setTileAtPosition(103, 101, fourthTile);

        Tile thirdTile = new Tile(game.getGameBoardTileID(), game.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.JUNGLE, terrainTypes.VOLCANO);

        game.nukeTiles(103, 102, thirdTile);

        Assert.assertEquals(game.getGameBoardPositionArray()[103][102].getTerrainType(), terrainTypes.GRASSLANDS);
        Assert.assertEquals(game.getGameBoardPositionArray()[102][101].getTerrainType(), terrainTypes.JUNGLE);
        Assert.assertEquals(game.getGameBoardPositionArray()[103][101].getTerrainType(), terrainTypes.VOLCANO);
    }

    @Test
    public void TestHexLevelIncreasesWhenNuking() {
        GameBoard gameBoard = new GameBoard();
        Tile initialTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.ROCKY, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(102, 102, initialTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);

        gameBoard.setTileAtPosition(103, 101, secondTile);

        Tile thirdTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);

        gameBoard.setTileAtPosition(103, 103, thirdTile);

        Tile fourthTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        fourthTile.flip();
        gameBoard.setTileAtPosition(101, 99, fourthTile);

        Tile fifthTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        fifthTile.flip();
        gameBoard.setTileAtPosition(101, 102, fifthTile);

        Tile sixthTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.LAKE, terrainTypes.GRASSLANDS);
        sixthTile.flip();
        gameBoard.setTileAtPosition(100, 100, sixthTile);

        Tile seventhTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.ROCKY);
        seventhTile.flip();

        gameBoard.nukeTiles(102, 101, seventhTile);

        Tile eighthTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.LAKE, terrainTypes.GRASSLANDS);
        gameBoard.nukeTiles(101, 102, eighthTile);

        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][101].getLevel(), 2);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[103][102].getLevel(), 2);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][102].getLevel(), 2);

        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][102].getLevel(), 2);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[100][101].getLevel(), 2);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][101].getLevel(), 2);

        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][102].getTerrainType(), terrainTypes.VOLCANO);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][102].getParentTileID(), eighthTile.getTileID());
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][101].getParentTileID(), eighthTile.getTileID());
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][102].getParentTileID(), seventhTile.getTileID());

        Tile ninthTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        ninthTile.flip();

        Assert.assertEquals(gameBoard.nukeAtPositionIsValid(101, 101, ninthTile), true);

        gameBoard.nukeTiles(101, 101, ninthTile);

        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][101].getLevel(), 3);
    }

    @Test
    public void testPreventingNukeIfTotoroIsOnAHex() {
        GameBoard gameBoard = new GameBoard();
        Tile initialTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(102, 102, initialTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        gameBoard.setTileAtPosition(103, 101, secondTile);

        Tile thirdTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.ROCKY, terrainTypes.VOLCANO, terrainTypes.ROCKY);
        gameBoard.setTileAtPosition(103, 103, thirdTile);

        gameBoard.getGameBoardPositionArray()[103][102].setTotoroCount(1);

        Tile fourthTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        fourthTile.flip();

        Tile fifthTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);

        Assert.assertEquals(gameBoard.nukeAtPositionIsValid(102, 101, fourthTile), false);
        Assert.assertEquals(gameBoard.nukeAtPositionIsValid(103, 102, fifthTile), false);
    }

    @Test
    public void testPreventingNukeIfTigerPenIsOnAHex() {
        GameBoard gameBoard = new GameBoard();
        Tile initialTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(102, 102, initialTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        gameBoard.setTileAtPosition(103, 101, secondTile);

        Tile thirdTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.ROCKY, terrainTypes.VOLCANO, terrainTypes.ROCKY);
        gameBoard.setTileAtPosition(103, 103, thirdTile);

        gameBoard.getGameBoardPositionArray()[103][102].setTigerCount(1);

        Tile fourthTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        fourthTile.flip();

        Tile fifthTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);

        Assert.assertEquals(gameBoard.nukeAtPositionIsValid(102, 101, fourthTile), false);
        Assert.assertEquals(gameBoard.nukeAtPositionIsValid(103, 102, fifthTile), false);
    }

    @Test
    public void testPreventingNukeIfNukeCoversSize1Settlement() {
        GameBoard gameBoard = new GameBoard();
        Tile initialTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(102, 102, initialTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        gameBoard.setTileAtPosition(103, 101, secondTile);

        Tile thirdTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.ROCKY, terrainTypes.VOLCANO, terrainTypes.ROCKY);
        gameBoard.setTileAtPosition(103, 103, thirdTile);

        gameBoard.getGameBoardPositionArray()[103][102].setSettlementID(1);
        gameBoard.assignSizeToGameBoardSettlementList(1, 1);

        Tile fourthTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        fourthTile.flip();

        Tile fifthTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.ROCKY, terrainTypes.GRASSLANDS, terrainTypes.VOLCANO);

        gameBoard.getGameBoardPositionArray()[102][102].setSettlementID(2);
        gameBoard.assignSizeToGameBoardSettlementList(2, 1);

        Assert.assertEquals(gameBoard.nukeAtPositionIsValid(102, 101, fourthTile), false);
        Assert.assertEquals(gameBoard.nukeAtPositionIsValid(102, 102, fifthTile), false);
    }

    @Test
    public void testPreventingNukeIfNukeCoversSize2Settlement() {
        GameBoard gameBoard = new GameBoard();
        Tile initialTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(102, 102, initialTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        gameBoard.setTileAtPosition(103, 101, secondTile);

        Tile thirdTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.ROCKY, terrainTypes.VOLCANO, terrainTypes.ROCKY);
        gameBoard.setTileAtPosition(103, 103, thirdTile);

        Tile fourthTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        fourthTile.flip();

        Tile fifthTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.ROCKY, terrainTypes.GRASSLANDS, terrainTypes.VOLCANO);

        gameBoard.getGameBoardPositionArray()[102][102].setSettlementID(2);
        gameBoard.getGameBoardPositionArray()[103][102].setSettlementID(2);
        gameBoard.assignSizeToGameBoardSettlementList(2, 2);

        Assert.assertEquals(gameBoard.nukeAtPositionIsValid(102, 101, fourthTile), false);
        Assert.assertEquals(gameBoard.nukeAtPositionIsValid(102, 102, fifthTile), false);
    }

    @Test
    public void testPreventingNukeIfNukeCoversSize1Settlement2() {
        GameBoard gameBoard = new GameBoard();
        Tile initialTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(102, 102, initialTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        gameBoard.setTileAtPosition(103, 101, secondTile);

        Tile thirdTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.ROCKY, terrainTypes.VOLCANO, terrainTypes.ROCKY);
        gameBoard.setTileAtPosition(103, 103, thirdTile);

        gameBoard.getGameBoardPositionArray()[103][102].setSettlementID(1);
        gameBoard.assignSizeToGameBoardSettlementList(1, 1);

        Tile fourthTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        fourthTile.flip();

        Tile fifthTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.ROCKY, terrainTypes.GRASSLANDS, terrainTypes.VOLCANO);

        gameBoard.getGameBoardPositionArray()[103][102].setSettlementID(2);
        gameBoard.assignSizeToGameBoardSettlementList(2, 1);

        Assert.assertEquals(gameBoard.nukeAtPositionIsValid(102, 101, fourthTile), false);
        Assert.assertEquals(gameBoard.nukeAtPositionIsValid(102, 102, fifthTile), false);
    }

    @Test
    public void testPreventingNukeIfNukeCoversSize2Settlement2() {

        GameBoard gameBoard = new GameBoard();
        Tile initialTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(102, 102, initialTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        gameBoard.setTileAtPosition(103, 101, secondTile);

        Tile thirdTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.ROCKY, terrainTypes.VOLCANO, terrainTypes.ROCKY);
        gameBoard.setTileAtPosition(103, 103, thirdTile);

        Tile fourthTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.ROCKY);
        fourthTile.flip();

        gameBoard.getGameBoardPositionArray()[104][102].setSettlementID(4);
        gameBoard.assignSizeToGameBoardSettlementList(4, 1);
        gameBoard.getGameBoardPositionArray()[103][102].setSettlementID(3);
        gameBoard.assignSizeToGameBoardSettlementList(3, 1);

        Assert.assertEquals(gameBoard.nukeAtPositionIsValid(103, 101, fourthTile), false);

    }

    @Test
    public void testIfWillNukeSize2SettlementWhileOnlyNuking1TileThatHasSettlement2() {
        GameBoard gameBoard = new GameBoard();
        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.LAKE);
        gameBoard.setTileAtPosition(102, 102, firstTile);
        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        secondTile.flip();
        gameBoard.setTileAtPosition(103, 102, secondTile);
        Tile thirdTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.ROCKY);
        gameBoard.setTileAtPosition(104, 102, thirdTile);

        Tile fourthTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.LAKE, terrainTypes.VOLCANO, terrainTypes.ROCKY);


        gameBoard.getGameBoardPositionArray()[103][103].setSettlementID(2);
        gameBoard.getGameBoardPositionArray()[102][103].setSettlementID(2);
        gameBoard.assignSizeToGameBoardSettlementList(2, 2);

        Assert.assertEquals(gameBoard.nukeAtPositionIsValid(103, 103, fourthTile), true);

    }

    @Test
    public void anotherNukeSettlementSizeTest() {
        GameBoard gameBoard = new GameBoard();

        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.LAKE);
        gameBoard.setTileAtPosition(102, 102, firstTile);
        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        secondTile.flip();
        gameBoard.setTileAtPosition(103, 102, secondTile);
        Tile thirdTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.ROCKY);
        gameBoard.setTileAtPosition(104, 102, thirdTile);
        Tile fourthTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.ROCKY);

        gameBoard.getGameBoardPositionArray()[102][101].setSettlementID(2);
        gameBoard.assignSizeToGameBoardSettlementList(2, 1);
        gameBoard.getGameBoardPositionArray()[103][101].setSettlementID(3);
        gameBoard.assignSizeToGameBoardSettlementList(3, 1);

        Assert.assertEquals(gameBoard.nukeAtPositionIsValid(103, 102, fourthTile), false);


        gameBoard.getGameBoardPositionArray()[103][101].setSettlementID(2);
        gameBoard.assignSizeToGameBoardSettlementList(2, 2);
        gameBoard.assignSizeToGameBoardSettlementList(3, 0);

        Assert.assertEquals(gameBoard.nukeAtPositionIsValid(103, 102, fourthTile), false);
    }

    @Test
    public void testForLevel2NukeOnSettlements() {

        GameBoard gameBoard = new GameBoard();

        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.LAKE);
        gameBoard.setTileAtPosition(102, 102, firstTile);
        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        secondTile.flip();
        gameBoard.setTileAtPosition(103, 102, secondTile);
        Tile thirdTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.ROCKY);
        gameBoard.setTileAtPosition(104, 102, thirdTile);

        Tile fourthTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.LAKE, terrainTypes.ROCKY, terrainTypes.VOLCANO);
        fourthTile.flip();
        gameBoard.setTileAtPosition(103, 99, fourthTile);

        Tile fifthTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.ROCKY, terrainTypes.VOLCANO, terrainTypes.LAKE);
        fifthTile.flip();

        gameBoard.setTileAtPosition(102, 101, fifthTile);

        Tile sixthTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.LAKE, terrainTypes.GRASSLANDS, terrainTypes.VOLCANO);

        gameBoard.setTileAtPosition(103, 101, sixthTile);

        gameBoard.getGameBoardPositionArray()[102][101].setSettlementID(2);
        gameBoard.getGameBoardPositionArray()[103][101].setSettlementID(2);
        gameBoard.assignSizeToGameBoardSettlementList(2, 2);
        Tile seventhTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.LAKE);

        Assert.assertEquals(gameBoard.nukeAtPositionIsValid(103, 102, seventhTile), false);

    }

    @Test
    public void whereTheHeckAreOurTerrains() {
        GameBoard gameBoard = new GameBoard();

        Tile initialTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.ROCKY, terrainTypes.LAKE, terrainTypes.VOLCANO);

        gameBoard.setTileAtPosition(102, 102, initialTile);

        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][102].getTerrainType(), terrainTypes.ROCKY);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][101].getTerrainType(), terrainTypes.LAKE);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][101].getTerrainType(), terrainTypes.VOLCANO);

        GameBoard secondGameBoard = new GameBoard();

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.ROCKY, terrainTypes.LAKE, terrainTypes.VOLCANO);

        secondTile.flip();

        secondGameBoard.setTileAtPosition(102, 102, secondTile);

        Assert.assertEquals(secondGameBoard.getGameBoardPositionArray()[102][102].getTerrainType(), terrainTypes.ROCKY);
        Assert.assertEquals(secondGameBoard.getGameBoardPositionArray()[101][103].getTerrainType(), terrainTypes.VOLCANO);
        Assert.assertEquals(secondGameBoard.getGameBoardPositionArray()[102][103].getTerrainType(), terrainTypes.LAKE);

    }

    @Test
    public void testAbilityToBuildSettlements() {
        GameBoard gameBoard = new GameBoard();
        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.ROCKY, terrainTypes.JUNGLE, terrainTypes.VOLCANO);
        firstTile.flip();
        Player player = new Player(1);

        gameBoard.setTileAtPosition(99, 98, firstTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.JUNGLE, terrainTypes.VOLCANO, terrainTypes.ROCKY);
        secondTile.flip();
        gameBoard.setTileAtPosition(99, 100, secondTile);

        gameBoard.buildSettlement(99, 98, player);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[99][98].getSettlerCount(), 1);
        Assert.assertEquals(player.getSettlerCount(), 19);

        gameBoard.buildSettlement(98, 99, player);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[98][99].getSettlerCount(), 0); //volcano
        Assert.assertEquals(player.getSettlerCount(), 19);

        gameBoard.buildSettlement(99, 99, player);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[99][98].getSettlerCount(), 1);
        Assert.assertEquals(player.getSettlerCount(), 18);

        gameBoard.buildSettlement(99, 100, player);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[99][100].getSettlerCount(), 1);
        Assert.assertEquals(player.getSettlerCount(), 17);

        gameBoard.buildSettlement(98, 101, player);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[98][101].getSettlerCount(), 1);
        Assert.assertEquals(player.getSettlerCount(), 16);

        gameBoard.buildSettlement(99, 101, player);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[99][101].getSettlerCount(), 0); //volcano
        Assert.assertEquals(player.getSettlerCount(), 16);

        Assert.assertEquals(player.getScore(), 4);

        gameBoard.mergeSettlements();

        //nuke
        Tile thirdTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.LAKE, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        Assert.assertEquals(gameBoard.nukeAtPositionIsValid(99, 100, thirdTile), true);
        gameBoard.nukeTiles(99, 100, thirdTile);

        gameBoard.buildSettlement(98, 99, player);
        gameBoard.buildSettlement(99, 99, player);
        gameBoard.buildSettlement(99, 100, player);

        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[98][99].getSettlerCount(), 0); //volcano + level 2
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[99][99].getSettlerCount(), 0); //level 2
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[99][100].getSettlerCount(), 0); //level 2
    }

    @Test
    public void testCalculateVillagersNeededForExpansionOnLevelOne() {
        GameBoard gameBoard = new GameBoard();
        Player playerOne = new Player(2);
        Tile tileOne = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.LAKE, terrainTypes.GRASSLANDS);
        Tile tileTwo = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.GRASSLANDS);
        Tile tileThree = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS, terrainTypes.GRASSLANDS, terrainTypes.VOLCANO);
        tileThree.flip();

        gameBoard.setTileAtPosition(100, 100, tileOne);
        gameBoard.setTileAtPosition(101, 101, tileTwo);
        gameBoard.setTileAtPosition(100, 101, tileThree);

        gameBoard.buildSettlement(99, 99, playerOne);
        int testInt = gameBoard.calculateVillagersForExpansion(99, 99, terrainTypes.GRASSLANDS, playerOne);

        Assert.assertEquals(testInt, 5);
    }

    @Test
    public void testCalculateVillagersNeededForExpansionOnMultipleLevels() {
        GameBoard gameBoard = new GameBoard();
        Player playerOne = new Player(2);
        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.GRASSLANDS, terrainTypes.VOLCANO);
        firstTile.flip();
        gameBoard.setTileAtPosition(99, 98, firstTile);
        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        secondTile.flip();
        gameBoard.setTileAtPosition(99, 100, secondTile);
        Tile thirdTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        gameBoard.nukeTiles(99, 100, thirdTile);

        gameBoard.buildSettlement(99, 98, playerOne);

        int testInt = gameBoard.calculateVillagersForExpansion(99, 98, terrainTypes.GRASSLANDS, playerOne);

        Assert.assertEquals(testInt, 5);
    }

    @Test
    public void testExpansionFromExistingSettlement() {

        GameBoard gameBoard = new GameBoard();
        Player playerOne = new Player(1);
        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.GRASSLANDS, terrainTypes.VOLCANO);
        firstTile.flip();
        gameBoard.setTileAtPosition(99, 98, firstTile);
        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        secondTile.flip();
        gameBoard.setTileAtPosition(99, 100, secondTile);
        Tile thirdTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        gameBoard.nukeTiles(99, 100, thirdTile);

        gameBoard.buildSettlement(99, 98, playerOne);

        gameBoard.expandSettlement(99, 98, terrainTypes.GRASSLANDS, playerOne);

        Assert.assertEquals(playerOne.getScore(), 10); //1 for settlement founding + 9 for expansion (4*2 + 1)
        Assert.assertEquals(playerOne.getSettlerCount(), 14);//-1 settlement founding, -5 expansion (2 on lvl2 + 1 on lvl 1)
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[99][99].getSettlerCount(), 2);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[99][99].getPlayerID(), 1);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[99][100].getSettlerCount(), 2);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[99][100].getPlayerID(), 1);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[98][101].getSettlerCount(), 1);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[99][100].getPlayerID(), 1);
    }

    @Test
    public void testExpansionNotFromExistingSettlement() {

        GameBoard gameBoard = new GameBoard();
        Player playerOne = new Player(1);

        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.GRASSLANDS, terrainTypes.VOLCANO);
        firstTile.flip();
        gameBoard.setTileAtPosition(99, 98, firstTile);
        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        secondTile.flip();
        gameBoard.setTileAtPosition(99, 100, secondTile);
        Tile thirdTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        gameBoard.nukeTiles(99, 100, thirdTile);

        gameBoard.buildSettlement(99, 98, playerOne);

        gameBoard.expandSettlement(99, 100, terrainTypes.GRASSLANDS, playerOne);

        Assert.assertEquals(playerOne.getScore(), 1); //1 for settlement founding + 9 for expansion (4*2 + 1)
        Assert.assertEquals(playerOne.getSettlerCount(), 19);//-1 settlement founding, -5 expansion (2 on lvl2 + 1 on lvl 1)

        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[99][98].getSettlerCount(), 1);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[99][98].getPlayerID(), 1);

        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[99][99].getSettlerCount(), 0);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[99][99].getPlayerID(), 0);

        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[99][100].getSettlerCount(), 0);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[99][100].getPlayerID(), 0);

        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[98][101].getSettlerCount(), 0);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[99][100].getPlayerID(), 0);
    }

    @Test
    public void testIfBuildUpdatesPlayerAndGameboardSettlementDataLists() {
        GameBoard gameBoard = new GameBoard();
        Player player = new Player(1);

        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.ROCKY, terrainTypes.JUNGLE, terrainTypes.VOLCANO);
        firstTile.flip();
        gameBoard.setTileAtPosition(99, 98, firstTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.JUNGLE, terrainTypes.VOLCANO, terrainTypes.ROCKY);
        secondTile.flip();
        gameBoard.setTileAtPosition(99, 100, secondTile);

        gameBoard.buildSettlement(99, 98, player);
        Assert.assertEquals(gameBoard.getGameBoardSettlementListOwner(1), 1);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(1, 1), true);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[99][98].getSettlementID(), 1);

        gameBoard.buildSettlement(98, 99, player); // placing over volcano
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[98][99].getSettlementID(), 0);

        gameBoard.buildSettlement(99, 99, player);
        Assert.assertEquals(gameBoard.getGameBoardSettlementListOwner(2), 1);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(2, 1), true);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[99][99].getSettlementID(), 2);

        gameBoard.buildSettlement(99, 100, player);
        Assert.assertEquals(gameBoard.getGameBoardSettlementListOwner(1), 1);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(1, 1), true);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[99][100].getSettlementID(), 1);

        gameBoard.buildSettlement(98, 101, player);
        Assert.assertEquals(gameBoard.getGameBoardSettlementListOwner(2), 1);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(2, 1), true);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[98][101].getSettlementID(), 2);

        gameBoard.buildSettlement(99, 101, player); // placing over volcano
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[99][101].getSettlementID(), 0);
    }

    @Test
    public void testIfExpandUpdatesdGameBoardSettlementDataListsSize() {
        GameBoard gameBoard = new GameBoard();
        Player playerOne = new Player(1);

        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.GRASSLANDS, terrainTypes.VOLCANO);
        firstTile.flip();
        gameBoard.setTileAtPosition(99, 98, firstTile);
        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        secondTile.flip();
        gameBoard.setTileAtPosition(99, 100, secondTile);
        Tile thirdTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        gameBoard.nukeTiles(99, 100, thirdTile);

        gameBoard.buildSettlement(99, 98, playerOne);

        gameBoard.expandSettlement(99, 98, terrainTypes.GRASSLANDS, playerOne);

        Assert.assertEquals(gameBoard.getGameBoardSettlementListSettlementSize(1), 4);

        GameBoard gameBoardTwo = new GameBoard();
        Player playerTwo = new Player(1);

        gameBoardTwo.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTileTwo = new Tile(gameBoardTwo.getGameBoardTileID(), gameBoardTwo.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        firstTileTwo.flip();
        gameBoardTwo.setTileAtPosition(102, 104, firstTileTwo);

        Tile secondTileTwo = new Tile(gameBoardTwo.getGameBoardTileID(), gameBoardTwo.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        secondTileTwo.flip();
        gameBoardTwo.setTileAtPosition(102, 106, secondTileTwo);

        Tile thirdTileTwo = new Tile(gameBoardTwo.getGameBoardTileID(), gameBoardTwo.getGameBoardHexID(), terrainTypes.LAKE, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        thirdTileTwo.flip();
        gameBoardTwo.setTileAtPosition(100, 105, thirdTileTwo);

        gameBoardTwo.buildSettlement(102, 103, playerTwo);
        Assert.assertEquals(gameBoardTwo.getGameBoardSettlementListSettlementSize(1), 1);
        gameBoardTwo.expandSettlement(102, 103, terrainTypes.GRASSLANDS, playerTwo);

        Assert.assertEquals(gameBoardTwo.getGameBoardSettlementListSettlementSize(1), 5);

        Assert.assertEquals(gameBoardTwo.getGameBoardPositionArray()[102][103].getSettlementID(), 1);
        Assert.assertEquals(gameBoardTwo.getGameBoardPositionArray()[102][104].getSettlementID(), 1);
        Assert.assertEquals(gameBoardTwo.getGameBoardPositionArray()[101][105].getSettlementID(), 1);
        Assert.assertEquals(gameBoardTwo.getGameBoardPositionArray()[102][106].getSettlementID(), 1);
        Assert.assertEquals(gameBoardTwo.getGameBoardPositionArray()[101][107].getSettlementID(), 1);
    }

    @Test
    public void testIfNukeUpdatesGameBoardSettlementDataList() {
        GameBoard gameBoard = new GameBoard();
        Player playerOne = new Player(1);

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.LAKE, terrainTypes.GRASSLANDS);
        firstTile.flip();
        gameBoard.setTileAtPosition(102, 104, firstTile);

        Tile thirdTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(103, 103, thirdTile);

        gameBoard.getGameBoardPositionArray()[101][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[103][102].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[103][103].setSettlementID(1);

        gameBoard.getGameBoardPositionArray()[101][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[103][102].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[103][103].setSettlerCount(1);

        gameBoard.getGameBoardPositionArray()[101][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[103][102].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[103][103].setPlayerID(1);

        gameBoard.assignSizeToGameBoardSettlementList(1, 4);
        gameBoard.setPlayerOwnedSettlementsListIsOwned(1, 1);

        Assert.assertEquals(gameBoard.getGameBoardSettlementListSettlementSize(1), 4);

        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][103].getSettlementID(), 1);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][103].getSettlementID(), 1);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[103][102].getSettlementID(), 1);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[103][103].getSettlementID(), 1);

        gameBoard.setPlayerOwnedSettlementsListIsOwned(1, 1);

        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(1, 1), true);

        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][103].getPlayerID(), 1);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][103].getPlayerID(), 1);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[103][102].getPlayerID(), 1);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[103][103].getPlayerID(), 1);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.ROCKY, terrainTypes.GRASSLANDS);

        gameBoard.nukeTiles(102, 104, secondTile);

        Assert.assertEquals(gameBoard.getGameBoardSettlementListSettlementSize(1), 2);

        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][103].getSettlementID(), 0);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][103].getSettlementID(), 0);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[103][102].getSettlementID(), 1);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[103][103].getSettlementID(), 1);

        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(1, 1), true);

        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][103].getPlayerID(), 0);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][103].getPlayerID(), 0);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[103][102].getPlayerID(), 1);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[103][103].getPlayerID(), 1);
    }

    @Test
    public void testIfTotoroPlacementIsPreventedForLessThanSize5SettlementAndOnVolcano() {
        GameBoard gameBoard = new GameBoard();

        Player playerOne = new Player(1);

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.LAKE, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        firstTile.flip();
        gameBoard.setTileAtPosition(102, 104, firstTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(103, 103, secondTile);

        gameBoard.getGameBoardPositionArray()[101][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[103][102].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[103][103].setSettlementID(1);

        gameBoard.getGameBoardPositionArray()[101][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[103][102].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[103][103].setSettlerCount(1);

        gameBoard.getGameBoardPositionArray()[101][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[103][102].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[103][103].setPlayerID(1);

        gameBoard.setGameBoardSettlementListPlayerID(1, 1);
        gameBoard.assignSizeToGameBoardSettlementList(1, 4);

        Assert.assertEquals(gameBoard.isValidTotoroPlacement(101, 101, 1, playerOne), false);
        Assert.assertEquals(gameBoard.isValidTotoroPlacement(102, 104, 1, playerOne), false);
        Assert.assertEquals(gameBoard.isValidTotoroPlacement(102, 101, 1, playerOne), false);
        Assert.assertEquals(gameBoard.isValidTotoroPlacement(102, 103, 1, playerOne), false);
        Assert.assertEquals(gameBoard.isValidTotoroPlacement(102, 102, 1, playerOne), false); // volcano

        gameBoard.placeTotoroSanctuary(101, 101, 1, playerOne);
        gameBoard.placeTotoroSanctuary(102, 104, 1, playerOne);
        gameBoard.placeTotoroSanctuary(102, 101, 1, playerOne);
        gameBoard.placeTotoroSanctuary(102, 103, 1, playerOne);
        gameBoard.placeTotoroSanctuary(102, 102, 1, playerOne);

        Assert.assertEquals(gameBoard.getGameBoardSettlementListTotoroCount(1), 0);
    }

    @Test
    public void testIfTotoroPlacementIsPreventedForSettlementWithTotoroAlreadyInIt() {
        GameBoard gameBoard = new GameBoard();

        Player playerOne = new Player(1);

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.LAKE, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        firstTile.flip();
        gameBoard.setTileAtPosition(102, 104, firstTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(103, 103, secondTile);

        gameBoard.getGameBoardPositionArray()[101][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[103][102].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[103][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][104].setSettlementID(1);

        gameBoard.getGameBoardPositionArray()[101][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[103][102].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[103][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][104].setSettlerCount(1);

        gameBoard.getGameBoardPositionArray()[101][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[103][102].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[103][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][104].setPlayerID(1);

        gameBoard.setGameBoardSettlementListPlayerID(1, 1);

        gameBoard.assignSizeToGameBoardSettlementList(1, 5);

        Assert.assertEquals(gameBoard.isValidTotoroPlacement(102, 101, 1, playerOne), true);
        gameBoard.placeTotoroSanctuary(102, 101, 1, playerOne);

        Assert.assertEquals(gameBoard.isValidTotoroPlacement(101, 105, 1, playerOne), false);

        gameBoard.placeTotoroSanctuary(101, 105, 1, playerOne);

        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][101].getTotoroCount(), 1);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][105].getTotoroCount(), 0);
    }

    @Test
    public void testIfTotoroCanNotBePlacedOverOccupiedPiece() {
        GameBoard gameBoard = new GameBoard();

        Player playerOne = new Player(1);

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.LAKE, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        firstTile.flip();
        gameBoard.setTileAtPosition(102, 104, firstTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(103, 103, secondTile);

        gameBoard.getGameBoardPositionArray()[101][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[103][102].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[103][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][104].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][101].setSettlementID(1);

        gameBoard.getGameBoardPositionArray()[101][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[103][102].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[103][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][104].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][101].setSettlerCount(1);

        gameBoard.getGameBoardPositionArray()[101][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[103][102].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[103][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][104].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][101].setPlayerID(1);

        gameBoard.setGameBoardSettlementListPlayerID(1, 1);

        gameBoard.assignSizeToGameBoardSettlementList(1, 6);

        Assert.assertEquals(gameBoard.isValidTotoroPlacement(102, 101, 1, playerOne), false);

        gameBoard.getGameBoardPositionArray()[102][101].setTotoroCount(1);
        Assert.assertEquals(gameBoard.isValidTotoroPlacement(102, 101, 1, playerOne), false);

        gameBoard.getGameBoardPositionArray()[102][101].setTigerCount(1);
        Assert.assertEquals(gameBoard.isValidTotoroPlacement(102, 101, 1, playerOne), false);
    }

    @Test
    public void testIfTotoroCanNotBePlacedFarAwayFromSettlement() {
        GameBoard gameBoard = new GameBoard();

        Player playerOne = new Player(1);

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.LAKE, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        firstTile.flip();
        gameBoard.setTileAtPosition(102, 104, firstTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(103, 103, secondTile);

        gameBoard.getGameBoardPositionArray()[101][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[103][102].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[103][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][101].setSettlementID(1);

        gameBoard.getGameBoardPositionArray()[101][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[103][102].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[103][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][101].setSettlerCount(1);

        gameBoard.getGameBoardPositionArray()[101][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[103][102].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[103][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][104].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][101].setPlayerID(1);

        gameBoard.setGameBoardSettlementListPlayerID(1, 1);
        gameBoard.assignSizeToGameBoardSettlementList(1, 5);

        Assert.assertEquals(gameBoard.isValidTotoroPlacement(101, 105, 1, playerOne), false);
    }

    @Test
    public void testIfTotoroPlacementDeniedIfNoTotorosLeft() {
        GameBoard gameBoard = new GameBoard();

        Player playerOne = new Player(1);

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.LAKE, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        firstTile.flip();
        gameBoard.setTileAtPosition(102, 104, firstTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(103, 103, secondTile);

        gameBoard.getGameBoardPositionArray()[101][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[103][102].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[103][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][104].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][101].setSettlementID(1);

        gameBoard.getGameBoardPositionArray()[101][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[103][102].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[103][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][104].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][101].setSettlerCount(1);

        gameBoard.getGameBoardPositionArray()[101][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[103][102].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[103][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][104].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][101].setPlayerID(1);

        gameBoard.setGameBoardSettlementListPlayerID(1, 1);
        gameBoard.assignSizeToGameBoardSettlementList(1, 6);

        playerOne.setTotoroCount(0);

        Assert.assertEquals(gameBoard.isValidTotoroPlacement(101, 105, 1, playerOne), false);
    }

    @Test
    public void testIfTotoroPlacementIsAllowed() {
        GameBoard gameBoard = new GameBoard();

        Player playerOne = new Player(1);

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.LAKE, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        firstTile.flip();
        gameBoard.setTileAtPosition(102, 104, firstTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(103, 103, secondTile);

        gameBoard.getGameBoardPositionArray()[101][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[103][102].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[103][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][104].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][101].setSettlementID(1);

        gameBoard.getGameBoardPositionArray()[101][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[103][102].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[103][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][104].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][101].setSettlerCount(1);

        gameBoard.getGameBoardPositionArray()[101][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[103][102].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[103][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][104].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][101].setPlayerID(1);

        gameBoard.setGameBoardSettlementListPlayerID(1, 1);
        gameBoard.assignSizeToGameBoardSettlementList(1, 6);

        Assert.assertEquals(gameBoard.isValidTotoroPlacement(101, 105, 1, playerOne), true);
    }

    @Test
    public void testIfTotoroPlacementUpdatesHex() {
        GameBoard gameBoard = new GameBoard();

        Player playerOne = new Player(1);

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.LAKE, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        firstTile.flip();
        gameBoard.setTileAtPosition(102, 104, firstTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(103, 103, secondTile);

        gameBoard.getGameBoardPositionArray()[101][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[103][102].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[103][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][104].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][101].setSettlementID(1);

        gameBoard.getGameBoardPositionArray()[101][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[103][102].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[103][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][104].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][101].setSettlerCount(1);

        gameBoard.getGameBoardPositionArray()[101][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[103][102].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[103][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][104].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][101].setPlayerID(1);

        gameBoard.setGameBoardSettlementListPlayerID(1, 1);
        gameBoard.assignSizeToGameBoardSettlementList(1, 6);

        gameBoard.placeTotoroSanctuary(101, 105, 1, playerOne);

        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][105].getTotoroCount(), 1);
    }

    @Test
    public void testIfTotoroPlacementUpdatesPlayerScoreAndRemovesPieceFromInventory() {
        GameBoard gameBoard = new GameBoard();

        Player playerOne = new Player(1);

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.LAKE, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        firstTile.flip();
        gameBoard.setTileAtPosition(102, 104, firstTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(103, 103, secondTile);

        gameBoard.getGameBoardPositionArray()[101][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[103][102].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[103][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][104].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][101].setSettlementID(1);

        gameBoard.getGameBoardPositionArray()[101][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[103][102].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[103][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][104].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][101].setSettlerCount(1);

        gameBoard.getGameBoardPositionArray()[101][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[103][102].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[103][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][104].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][101].setPlayerID(1);

        gameBoard.setGameBoardSettlementListPlayerID(1, 1);
        gameBoard.assignSizeToGameBoardSettlementList(1, 6);

        Assert.assertEquals(playerOne.getScore(), 0);
        Assert.assertEquals(playerOne.getTotoroCount(), 3);

        gameBoard.placeTotoroSanctuary(101, 105, 1, playerOne);

        Assert.assertEquals(playerOne.getScore(), 200);
        Assert.assertEquals(playerOne.getTotoroCount(), 2);
    }

    @Test
    public void testIfTotoroPlacementUpdatesGameBoardSettlementListSizeAndTotoroCount() {
        GameBoard gameBoard = new GameBoard();

        Player playerOne = new Player(1);

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.LAKE, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        firstTile.flip();
        gameBoard.setTileAtPosition(102, 104, firstTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(103, 103, secondTile);

        gameBoard.getGameBoardPositionArray()[101][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[103][102].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[103][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][104].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][101].setSettlementID(1);

        gameBoard.getGameBoardPositionArray()[101][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[103][102].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[103][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][104].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][101].setSettlerCount(1);

        gameBoard.getGameBoardPositionArray()[101][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[103][102].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[103][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][104].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][101].setPlayerID(1);

        gameBoard.setGameBoardSettlementListPlayerID(1, 1);
        gameBoard.assignSizeToGameBoardSettlementList(1, 6);

        gameBoard.placeTotoroSanctuary(101, 105, 1, playerOne);

        Assert.assertEquals(gameBoard.getGameBoardSettlementListTotoroCount(1), 1);
        Assert.assertEquals(gameBoard.getGameBoardSettlementListSettlementSize(1), 7);
    }

    @Test
    public void testIfTigerPlacementIsPreventedForLessThanLevel3HexAndVolcano() {
        GameBoard gameBoard = new GameBoard();

        Player playerOne = new Player(1);

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.LAKE, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        firstTile.flip();
        gameBoard.setTileAtPosition(102, 104, firstTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(103, 103, secondTile);

        gameBoard.getGameBoardPositionArray()[101][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[103][102].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[103][103].setSettlementID(1);

        gameBoard.getGameBoardPositionArray()[101][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[103][102].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[103][103].setSettlerCount(1);

        gameBoard.getGameBoardPositionArray()[101][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[103][102].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[103][103].setPlayerID(1);

        gameBoard.setGameBoardSettlementListPlayerID(1, 1);
        gameBoard.assignSizeToGameBoardSettlementList(1, 4);

        Assert.assertEquals(gameBoard.checkIfValidTigerPlacement(101, 101, 1, playerOne), false);
        Assert.assertEquals(gameBoard.checkIfValidTigerPlacement(102, 101, 1, playerOne), false);
        Assert.assertEquals(gameBoard.checkIfValidTigerPlacement(102, 103, 1, playerOne), false);
        Assert.assertEquals(gameBoard.checkIfValidTigerPlacement(102, 102, 1, playerOne), false); // volcano

        gameBoard.placeTigerPen(101, 101, 1, playerOne);
        gameBoard.placeTigerPen(102, 101, 1, playerOne);
        gameBoard.placeTigerPen(102, 103, 1, playerOne);
        gameBoard.placeTigerPen(102, 102, 1, playerOne);

        Assert.assertEquals(gameBoard.getGameBoardSettlementListTigerCount(1), 0);
    }

    @Test
    public void testIfTigerPlacementIsPreventedForSettlementWithTigerAlreadyInIt() {
        GameBoard gameBoard = new GameBoard();

        Player playerOne = new Player(1);

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.LAKE, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        firstTile.flip();
        gameBoard.setTileAtPosition(102, 104, firstTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(103, 103, secondTile);

        gameBoard.getGameBoardPositionArray()[101][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[103][102].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[103][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][104].setSettlementID(1);

        gameBoard.getGameBoardPositionArray()[101][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[103][102].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[103][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][104].setSettlerCount(1);

        gameBoard.getGameBoardPositionArray()[101][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[103][102].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[103][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][104].setPlayerID(1);

        gameBoard.setGameBoardSettlementListPlayerID(1, 1);

        gameBoard.assignSizeToGameBoardSettlementList(1, 5);

        gameBoard.getGameBoardPositionArray()[102][101].setLevel(3);
        Assert.assertEquals(gameBoard.checkIfValidTigerPlacement(102, 101, 1, playerOne), true);
        gameBoard.placeTigerPen(102, 101, 1, playerOne);

        gameBoard.getGameBoardPositionArray()[102][101].setLevel(2);
        Assert.assertEquals(gameBoard.checkIfValidTigerPlacement(101, 105, 1, playerOne), false);
        gameBoard.placeTigerPen(101, 105, 1, playerOne);

        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][101].getTigerCount(), 1);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][105].getTigerCount(), 0);
    }

    @Test
    public void testIfTigerCanNotBePlacedOverOccupiedPiece() {
        GameBoard gameBoard = new GameBoard();

        Player playerOne = new Player(1);

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.LAKE, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        firstTile.flip();
        gameBoard.setTileAtPosition(102, 104, firstTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(103, 103, secondTile);

        gameBoard.getGameBoardPositionArray()[101][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[103][102].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[103][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][104].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][101].setSettlementID(1);

        gameBoard.getGameBoardPositionArray()[101][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[103][102].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[103][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][104].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][101].setSettlerCount(1);

        gameBoard.getGameBoardPositionArray()[101][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[103][102].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[103][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][104].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][101].setPlayerID(1);

        gameBoard.setGameBoardSettlementListPlayerID(1, 1);

        gameBoard.assignSizeToGameBoardSettlementList(1, 6);

        Assert.assertEquals(gameBoard.checkIfValidTigerPlacement(102, 101, 1, playerOne), false);

        gameBoard.getGameBoardPositionArray()[102][101].setTigerCount(1);
        Assert.assertEquals(gameBoard.checkIfValidTigerPlacement(102, 101, 1, playerOne), false);

        gameBoard.getGameBoardPositionArray()[102][101].setTigerCount(1);
        Assert.assertEquals(gameBoard.checkIfValidTigerPlacement(102, 101, 1, playerOne), false);
    }

    @Test
    public void testIfTigerCanNotBePlacedFarAwayFromSettlement() {
        GameBoard gameBoard = new GameBoard();

        Player playerOne = new Player(1);

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.LAKE, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        firstTile.flip();
        gameBoard.setTileAtPosition(102, 104, firstTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(103, 103, secondTile);

        gameBoard.getGameBoardPositionArray()[101][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[103][102].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[103][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][101].setSettlementID(1);

        gameBoard.getGameBoardPositionArray()[101][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[103][102].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[103][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][101].setSettlerCount(1);

        gameBoard.getGameBoardPositionArray()[101][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[103][102].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[103][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][104].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][101].setPlayerID(1);

        gameBoard.setGameBoardSettlementListPlayerID(1, 1);
        gameBoard.assignSizeToGameBoardSettlementList(1, 5);

        Assert.assertEquals(gameBoard.checkIfValidTigerPlacement(101, 105, 1, playerOne), false);
    }

    @Test
    public void testIfTigerPlacementIsNotAllowedIfPlayerDoesNotHaveEnoughPieces() {
        GameBoard gameBoard = new GameBoard();

        Player playerOne = new Player(1);

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.LAKE, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        firstTile.flip();
        gameBoard.setTileAtPosition(102, 104, firstTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(103, 103, secondTile);

        gameBoard.getGameBoardPositionArray()[101][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[103][102].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[103][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][104].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][101].setSettlementID(1);

        gameBoard.getGameBoardPositionArray()[101][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[103][102].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[103][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][104].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][101].setSettlerCount(1);

        gameBoard.getGameBoardPositionArray()[101][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[103][102].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[103][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][104].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][101].setPlayerID(1);

        gameBoard.setGameBoardSettlementListPlayerID(1, 1);
        gameBoard.assignSizeToGameBoardSettlementList(1, 6);
        gameBoard.getGameBoardPositionArray()[101][105].setLevel(3);

        playerOne.setTigerCount(0);

        Assert.assertEquals(gameBoard.checkIfValidTigerPlacement(101, 105, 1, playerOne), false);

    }

    @Test
    public void testIfTigerPlacementIsAllowed() {
        GameBoard gameBoard = new GameBoard();

        Player playerOne = new Player(1);

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.LAKE, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        firstTile.flip();
        gameBoard.setTileAtPosition(102, 104, firstTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(103, 103, secondTile);

        gameBoard.getGameBoardPositionArray()[101][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[103][102].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[103][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][104].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][101].setSettlementID(1);

        gameBoard.getGameBoardPositionArray()[101][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[103][102].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[103][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][104].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][101].setSettlerCount(1);

        gameBoard.getGameBoardPositionArray()[101][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[103][102].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[103][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][104].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][101].setPlayerID(1);

        gameBoard.setGameBoardSettlementListPlayerID(1, 1);
        gameBoard.assignSizeToGameBoardSettlementList(1, 6);
        gameBoard.getGameBoardPositionArray()[101][105].setLevel(3);

        Assert.assertEquals(gameBoard.checkIfValidTigerPlacement(101, 105, 1, playerOne), true);

        gameBoard.getGameBoardPositionArray()[101][101].setLevel(50);

        Assert.assertEquals(gameBoard.checkIfValidTigerPlacement(101, 101, 1, playerOne), true);
    }

    @Test
    public void testIfTigerPlacementUpdatesHex() {
        GameBoard gameBoard = new GameBoard();

        Player playerOne = new Player(1);

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.LAKE, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        firstTile.flip();
        gameBoard.setTileAtPosition(102, 104, firstTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(103, 103, secondTile);

        gameBoard.getGameBoardPositionArray()[101][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[103][102].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[103][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][104].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][101].setSettlementID(1);

        gameBoard.getGameBoardPositionArray()[101][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[103][102].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[103][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][104].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][101].setSettlerCount(1);

        gameBoard.getGameBoardPositionArray()[101][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[103][102].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[103][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][104].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][101].setPlayerID(1);

        gameBoard.setGameBoardSettlementListPlayerID(1, 1);
        gameBoard.assignSizeToGameBoardSettlementList(1, 6);

        gameBoard.getGameBoardPositionArray()[101][105].setLevel(3);
        gameBoard.placeTigerPen(101, 105, 1, playerOne);

        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][105].getTigerCount(), 1);
    }

    @Test
    public void testIfTigerPlacementUpdatesPlayerScoreAndInventory() {
        GameBoard gameBoard = new GameBoard();

        Player playerOne = new Player(1);

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.LAKE, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        firstTile.flip();
        gameBoard.setTileAtPosition(102, 104, firstTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(103, 103, secondTile);

        gameBoard.getGameBoardPositionArray()[101][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[103][102].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[103][103].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][104].setSettlementID(1);
        gameBoard.getGameBoardPositionArray()[102][101].setSettlementID(1);

        gameBoard.getGameBoardPositionArray()[101][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[103][102].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[103][103].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][104].setSettlerCount(1);
        gameBoard.getGameBoardPositionArray()[102][101].setSettlerCount(1);

        gameBoard.getGameBoardPositionArray()[101][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[103][102].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[103][103].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][104].setPlayerID(1);
        gameBoard.getGameBoardPositionArray()[102][101].setPlayerID(1);

        gameBoard.setGameBoardSettlementListPlayerID(1, 1);
        gameBoard.assignSizeToGameBoardSettlementList(1, 6);

        Assert.assertEquals(playerOne.getScore(), 0);
        Assert.assertEquals(playerOne.getTigerCount(), 2);

        gameBoard.getGameBoardPositionArray()[101][105].setLevel(3);
        gameBoard.placeTigerPen(101, 105, 1, playerOne);

        Assert.assertEquals(playerOne.getTigerCount(), 1);
        Assert.assertEquals(playerOne.getScore(), 75);
    }

    @Test
    public void testIfTigerPlacementUpdatesGameBoardSettlementListSizeAndTigerCount() {
        GameBoard gameBoard = new GameBoard();

        Player playerOne = new Player(1);

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.LAKE, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        firstTile.flip();
        gameBoard.setTileAtPosition(102, 104, firstTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(103, 103, secondTile);

        gameBoard.buildSettlement(101, 103, playerOne);
        gameBoard.buildSettlement(102, 103, playerOne);
        gameBoard.buildSettlement(103, 102, playerOne);
        gameBoard.buildSettlement(103, 103, playerOne);
        gameBoard.buildSettlement(102, 104, playerOne);

        gameBoard.setGameBoardSettlementListPlayerID(1, 1);
        gameBoard.assignSizeToGameBoardSettlementList(1, 5);

        Assert.assertEquals(playerOne.getScore(), 5);
        System.out.println(gameBoard.getGameBoardPositionArray()[101][103].getSettlementID());
        gameBoard.getGameBoardPositionArray()[102][101].setLevel(3);
        Assert.assertEquals(gameBoard.getGameBoardSettlementListSettlementSize(1), 5);
        gameBoard.placeTigerPen(102, 101, 1, playerOne);

        Assert.assertEquals(gameBoard.getGameBoardSettlementListTigerCount(1), 1);
        Assert.assertEquals(gameBoard.getGameBoardSettlementListSettlementSize(1), 6);
        Assert.assertEquals(playerOne.getScore(), 80);
    }

    @Test
    public void testIfMergeOccursAfterBuilds() {
        Player playerOne = new Player(1);

        GameBoard gameBoard = new GameBoard();

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        firstTile.flip();
        gameBoard.setTileAtPosition(102, 104, firstTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(103, 103, secondTile);

        Tile thirdTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.GRASSLANDS);
        thirdTile.flip();
        gameBoard.setTileAtPosition(104, 103, thirdTile);

        gameBoard.buildSettlement(101, 103, playerOne);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][103].getSettlementID(), 1);
        gameBoard.buildSettlement(102, 103, playerOne);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][103].getSettlementID(), 2);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][103].getSettlementID(), 2);
        gameBoard.buildSettlement(102, 104, playerOne);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][104].getSettlementID(), 1);
        gameBoard.buildSettlement(101, 101, playerOne);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][101].getSettlementID(), 2);
        gameBoard.buildSettlement(102, 101, playerOne);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][101].getSettlementID(), 3);
        gameBoard.buildSettlement(104, 104, playerOne);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[104][104].getSettlementID(), 2);
        gameBoard.mergeSettlements();

        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][103].getSettlementID(), gameBoard.getGameBoardPositionArray()[102][103].getSettlementID());
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][103].getSettlementID(), gameBoard.getGameBoardPositionArray()[102][104].getSettlementID());
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][101].getSettlementID(), gameBoard.getGameBoardPositionArray()[102][101].getSettlementID());

        Assert.assertEquals(gameBoard.getGameBoardSettlementListSettlementSize(gameBoard.getGameBoardPositionArray()[101][103].getSettlementID()), 3);
        Assert.assertEquals(gameBoard.getGameBoardSettlementListSettlementSize(gameBoard.getGameBoardPositionArray()[101][101].getSettlementID()), 2);
        Assert.assertEquals(gameBoard.getGameBoardSettlementListSettlementSize(gameBoard.getGameBoardPositionArray()[104][104].getSettlementID()), 1);

        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(1, 1), true);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(2, 1), true);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(3, 1), true);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(4, 1), false);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(5, 1), false);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(6, 1), false);
    }

    @Test
    public void testIfMergeOccursAfterExpansions() {
        Player playerOne = new Player(1);

        GameBoard gameBoard = new GameBoard();

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        firstTile.flip();
        gameBoard.setTileAtPosition(102, 104, firstTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.ROCKY, terrainTypes.GRASSLANDS, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(103, 103, secondTile);

        Tile thirdTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.ROCKY, terrainTypes.LAKE, terrainTypes.ROCKY);
        thirdTile.flip();
        gameBoard.setTileAtPosition(104, 103, thirdTile);

        gameBoard.buildSettlement(101, 103, playerOne);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][103].getSettlementID(), 1);
        gameBoard.buildSettlement(102, 101, playerOne);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][101].getSettlementID(), 2);
        gameBoard.buildSettlement(104, 104, playerOne);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[104][104].getSettlementID(), 3);

        gameBoard.expandSettlement(101, 103, terrainTypes.GRASSLANDS, playerOne);
        gameBoard.mergeSettlements();

        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][103].getSettlementID(), 1); // EXPANSIONS OVERWRITE FROM HOME HEX, NOT LAST HEX PLACED
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][103].getSettlementID(), 1);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[103][102].getSettlementID(), 1);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][104].getSettlementID(), 1);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][105].getSettlementID(), 1);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][101].getSettlementID(), 1);

        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[104][104].getSettlementID(), 3);

        Assert.assertEquals(gameBoard.getGameBoardSettlementListSettlementSize(1), 6);

        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(1, playerOne.getPlayerID()), true);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(2, playerOne.getPlayerID()), false);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(3, playerOne.getPlayerID()), true);
    }

    @Test
    public void testIfMergeDoesNotMergeOtherPlayerStructures() {
        Player playerOne = new Player(1);
        Player playerTwo = new Player(2);

        GameBoard gameBoard = new GameBoard();

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        firstTile.flip();
        gameBoard.setTileAtPosition(102, 104, firstTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.ROCKY, terrainTypes.GRASSLANDS, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(103, 103, secondTile);

        Tile thirdTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.ROCKY, terrainTypes.LAKE, terrainTypes.ROCKY);
        thirdTile.flip();
        gameBoard.setTileAtPosition(104, 103, thirdTile);

        gameBoard.buildSettlement(101, 103, playerOne);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][103].getSettlementID(), 1);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][103].getPlayerID(), 1);

        gameBoard.buildSettlement(102, 101, playerTwo);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][101].getSettlementID(), 2);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][101].getPlayerID(), 2);

        gameBoard.buildSettlement(102, 104, playerTwo);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][104].getSettlementID(), 3);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][104].getPlayerID(), 2);

        gameBoard.buildSettlement(104, 104, playerOne);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[104][104].getSettlementID(), 4);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[104][104].getPlayerID(), 1);

        gameBoard.expandSettlement(101, 103, terrainTypes.GRASSLANDS, playerOne);

        gameBoard.mergeSettlements();

        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][103].getSettlementID(), 1); // EXPANSIONS OVERWRITE FROM HOME HEX, NOT LAST HEX PLACED
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][103].getSettlementID(), 1);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[103][102].getSettlementID(), 1);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[103][102].getSettlementID(), 1);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][104].getSettlementID(), 3);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][105].getSettlementID(), 0);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][101].getSettlementID(), 2);

        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[104][104].getSettlementID(), 4);

        Assert.assertEquals(gameBoard.getGameBoardSettlementListSettlementSize(1), 3);

        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(1, 1), true);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(2, 1), false);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(3, 1), false);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(4, 1), true);

        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(1, 2), false);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(2, 2), true);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(3, 2), true);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(4, 2), false);
    }

    @Test
    public void testAbilityToSplitSettlementsAfterNuke() {
        Player playerOne = new Player(1);

        GameBoard gameBoard = new GameBoard();

        gameBoard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        firstTile.flip();
        gameBoard.setTileAtPosition(102, 104, firstTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.ROCKY, terrainTypes.VOLCANO);
        gameBoard.setTileAtPosition(103, 103, secondTile);

        Tile thirdTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.LAKE, terrainTypes.GRASSLANDS);
        thirdTile.flip();
        gameBoard.setTileAtPosition(104, 103, thirdTile);

        gameBoard.buildSettlement(101, 103, playerOne);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][103].getSettlementID(), 1);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(1, 1), true);
        gameBoard.buildSettlement(102, 103, playerOne);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][103].getSettlementID(), 2);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(2, 1), true);
        gameBoard.buildSettlement(102, 104, playerOne);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][104].getSettlementID(), 1);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(1, 1), true);
        gameBoard.buildSettlement(101, 105, playerOne);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][105].getSettlementID(), 2);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(2, 1), true);
        gameBoard.buildSettlement(103, 103, playerOne);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[103][103].getSettlementID(), 1);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(1, 1), true);
        gameBoard.buildSettlement(104, 104, playerOne);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[104][104].getSettlementID(), 2);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(2, 1), true);
        gameBoard.buildSettlement(103, 102, playerOne);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[103][102].getSettlementID(), 1);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(1, 1), true);
        gameBoard.buildSettlement(102, 101, playerOne);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][101].getSettlementID(), 2);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(2, 1), true);
        gameBoard.buildSettlement(101, 101, playerOne);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][101].getSettlementID(), 1);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(1, 1), true);

        gameBoard.mergeSettlements();

        Assert.assertEquals(gameBoard.getHexesBuiltOnThisTurn().isEmpty(), true);

        Assert.assertEquals(gameBoard.getGameBoardSettlementListSettlementSize(1), 9);

        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(1, 1), true);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(2, 1), false);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(3, 1), false);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(4, 1), false);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(5, 1), false);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(6, 1), false);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(7, 1), false);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(8, 1), false);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(9, 1), false);

        Tile fourthTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.ROCKY);
        Assert.assertEquals(gameBoard.nukeAtPositionIsValid(102, 103, fourthTile), true);
        gameBoard.nukeTiles(102, 103, fourthTile);

        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][103].getSettlementID(), 4);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][104].getSettlementID(), 4);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][105].getSettlementID(), 4);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[104][104].getSettlementID(), 2);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[103][103].getSettlementID(), 2);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][101].getSettlementID(), 3);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][101].getSettlementID(), 3);

        Assert.assertEquals(gameBoard.getGameBoardSettlementListSettlementSize(4), 3);
        Assert.assertEquals(gameBoard.getGameBoardSettlementListSettlementSize(2), 2);
        Assert.assertEquals(gameBoard.getGameBoardSettlementListSettlementSize(3), 2);

        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(1, 1), false);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(2, 1), true);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(3, 1), true);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(4, 1), true);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(5, 1), false);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(6, 1), false);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(7, 1), false);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(8, 1), false);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(9, 1), false);
    }

    @Test
    public void testIfExpandWorksOffOfSettlementOfSizeGreaterThanOne() {
        GameBoard gameboard = new GameBoard();

        Player playerOne = new Player(1);

        gameboard.placeFirstTileAndUpdateValidPlacementList();

        Tile tileOne = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.GRASSLANDS);
        gameboard.setTileAtPosition(103, 103, tileOne);

        Tile tileTwo = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        gameboard.setTileAtPosition(103, 101, tileTwo);

        Tile tileThree = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.GRASSLANDS);
        gameboard.setTileAtPosition(105, 102, tileThree);

        gameboard.buildSettlement(103, 101, playerOne);
        gameboard.buildSettlement(104, 102, playerOne);
        gameboard.buildSettlement(104, 101, playerOne);

        gameboard.mergeSettlements();


        Assert.assertEquals(gameboard.playerOwnsSettlementWithID(1, playerOne.getPlayerID()), true);

        Assert.assertEquals(gameboard.getGameBoardPositionArray()[103][101].getSettlementID(), 1);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[104][102].getSettlementID(), 1);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[104][101].getSettlementID(), 1);

        gameboard.expandSettlement(104, 101, terrainTypes.GRASSLANDS, playerOne);

        Assert.assertEquals(gameboard.playerOwnsSettlementWithID(1, playerOne.getPlayerID()), true);
        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(1), 7);
    }

    @Test
    public void testIfMergeWorksForSettlementWithTotoroInIt() {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        gameboard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.LAKE);
        gameboard.setTileAtPosition(103, 103, firstTile);

        Tile secondTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        gameboard.setTileAtPosition(105, 104, secondTile);

        gameboard.buildSettlement(101, 103, player);
        gameboard.buildSettlement(102, 103, player);
        gameboard.buildSettlement(104, 102, player);
        gameboard.buildSettlement(102, 101, player);
        gameboard.buildSettlement(103, 102, player);

        gameboard.placeTotoroSanctuary(101, 101, gameboard.getGameBoardPositionArray()[102][103].getSettlementID(), player);

        gameboard.buildSettlement(105, 103, player);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTotoroCount(gameboard.getGameBoardPositionArray()[105][103].getSettlementID()), 0);
        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[105][103].getSettlementID()), 1);

        Assert.assertEquals(gameboard.getGameBoardSettlementListTotoroCount(gameboard.getGameBoardPositionArray()[101][103].getSettlementID()), 1);
        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[101][103].getSettlementID()), 6);
        gameboard.expandSettlement(105, 103, terrainTypes.JUNGLE, player);

        int mergedSettlementID = gameboard.getGameBoardPositionArray()[101][103].getSettlementID();

        Assert.assertEquals(gameboard.getGameBoardSettlementListTotoroCount(gameboard.getGameBoardPositionArray()[104][103].getSettlementID()), 1);

        Assert.assertEquals(gameboard.getGameBoardSettlementListTotoroCount(gameboard.getGameBoardPositionArray()[101][103].getSettlementID()), 1);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTotoroCount(gameboard.getGameBoardPositionArray()[101][101].getSettlementID()), 1);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTotoroCount(mergedSettlementID), 1);
        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[101][103].getSettlementID()), 8);
        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[101][101].getSettlementID()), 8);
        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(mergedSettlementID), 8);
    }

    @Test
    public void testIfMergeWorksAfterTotoroIsBuilt() {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        gameboard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.LAKE);
        gameboard.setTileAtPosition(103, 103, firstTile);

        Tile secondTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        gameboard.setTileAtPosition(105, 104, secondTile);

        gameboard.buildSettlement(101, 103, player);
        gameboard.buildSettlement(102, 103, player);
        gameboard.buildSettlement(102, 101, player);
        gameboard.buildSettlement(103, 102, player);
        gameboard.buildSettlement(101, 101, player);
        gameboard.buildSettlement(104, 103, player);
        gameboard.buildSettlement(105, 103, player);

        Assert.assertEquals(gameboard.getGameBoardPositionArray()[101][103].getSettlementID(), 1);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[105][103].getSettlementID(), 3);

        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(1), 5);
        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(3), 2);

        Assert.assertEquals(gameboard.getGameBoardSettlementListTotoroCount(1), 0);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTotoroCount(3), 0);

        Assert.assertEquals(gameboard.getGameBoardSettlementListTigerCount(1), 0);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTigerCount(3), 0);

        gameboard.placeTotoroSanctuary(104, 102, 1, player);

        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(1), 8);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTotoroCount(1), 1);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTigerCount(1), 0);

        Assert.assertEquals(gameboard.getGameBoardPositionArray()[101][103].getSettlementID(), 1);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[102][103].getSettlementID(), 1);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[103][102].getSettlementID(), 1);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[102][101].getSettlementID(), 1);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[101][101].getSettlementID(), 1);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[104][102].getSettlementID(), 1);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[104][103].getSettlementID(), 1);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[105][103].getSettlementID(), 1);
    }

    @Test
    public void testIfMergeWorksForSettlementWithTigerPenInIt() {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        gameboard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.LAKE);
        gameboard.setTileAtPosition(103, 103, firstTile);

        Tile secondTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        gameboard.setTileAtPosition(105, 104, secondTile);

        gameboard.buildSettlement(101, 103, player);
        gameboard.buildSettlement(102, 103, player);
        gameboard.buildSettlement(104, 102, player);
        gameboard.buildSettlement(102, 101, player);
        gameboard.buildSettlement(103, 102, player);

        gameboard.getGameBoardPositionArray()[101][101].setLevel(3);

        gameboard.placeTigerPen(101, 101, gameboard.getGameBoardPositionArray()[102][103].getSettlementID(), player);

        gameboard.buildSettlement(105, 103, player);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTotoroCount(gameboard.getGameBoardPositionArray()[105][103].getSettlementID()), 0);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTigerCount(gameboard.getGameBoardPositionArray()[105][103].getSettlementID()), 0);
        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[105][103].getSettlementID()), 1);

        Assert.assertEquals(gameboard.getGameBoardSettlementListTigerCount(gameboard.getGameBoardPositionArray()[101][103].getSettlementID()), 1);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTotoroCount(gameboard.getGameBoardPositionArray()[101][103].getSettlementID()), 0);
        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[101][103].getSettlementID()), 6);

        gameboard.expandSettlement(105, 103, terrainTypes.JUNGLE, player);

        int mergedSettlementID = gameboard.getGameBoardPositionArray()[101][103].getSettlementID();

        Assert.assertEquals(gameboard.getGameBoardSettlementListTigerCount(gameboard.getGameBoardPositionArray()[104][103].getSettlementID()), 1);

        Assert.assertEquals(gameboard.getGameBoardSettlementListTigerCount(gameboard.getGameBoardPositionArray()[101][103].getSettlementID()), 1);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTigerCount(gameboard.getGameBoardPositionArray()[101][101].getSettlementID()), 1);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTotoroCount(gameboard.getGameBoardPositionArray()[101][101].getSettlementID()), 0);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTotoroCount(mergedSettlementID), 0);
        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[101][103].getSettlementID()), 8);
        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[101][101].getSettlementID()), 8);
        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(mergedSettlementID), 8);
    }

    @Test
    public void testIfMergeWorksAfterTigerPenIsBuilt() {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        gameboard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.LAKE);
        gameboard.setTileAtPosition(103, 103, firstTile);

        Tile secondTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        gameboard.setTileAtPosition(105, 104, secondTile);

        gameboard.buildSettlement(101, 103, player);
        gameboard.buildSettlement(102, 103, player);
        gameboard.buildSettlement(102, 101, player);
        gameboard.buildSettlement(103, 102, player);
        gameboard.buildSettlement(101, 101, player);
        gameboard.buildSettlement(104, 103, player);
        gameboard.buildSettlement(105, 103, player);

        Assert.assertEquals(gameboard.getGameBoardPositionArray()[101][103].getSettlementID(), 1);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[105][103].getSettlementID(), 3);

        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(1), 5);
        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(3), 2);

        Assert.assertEquals(gameboard.getGameBoardSettlementListTotoroCount(1), 0);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTotoroCount(3), 0);

        Assert.assertEquals(gameboard.getGameBoardSettlementListTigerCount(1), 0);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTigerCount(3), 0);

        gameboard.getGameBoardPositionArray()[104][102].setLevel(3);
        gameboard.placeTigerPen(104, 102, 1, player);

        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(1), 8);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTotoroCount(1), 0);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTigerCount(1), 1);

        Assert.assertEquals(gameboard.getGameBoardPositionArray()[101][103].getSettlementID(), 1);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[102][103].getSettlementID(), 1);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[103][102].getSettlementID(), 1);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[102][101].getSettlementID(), 1);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[101][101].getSettlementID(), 1);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[104][102].getSettlementID(), 1);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[104][103].getSettlementID(), 1);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[105][103].getSettlementID(), 1);
    }

    @Test
    public void testIfNukeDecrementsSettlementSize() {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        gameboard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.LAKE);
        gameboard.setTileAtPosition(103, 103, firstTile);

        Tile secondTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        gameboard.setTileAtPosition(105, 104, secondTile);

        gameboard.buildSettlement(101, 103, player);
        gameboard.buildSettlement(102, 103, player);
        gameboard.buildSettlement(103, 102, player);

        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[101][103].getSettlementID()), 3);

        Tile thirdTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(), terrainTypes.JUNGLE, terrainTypes.VOLCANO, terrainTypes.LAKE);

        gameboard.nukeTiles(102, 103, thirdTile);

        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[101][103].getSettlementID()), 1);
    }
/*
    @Test
    public void testIfNukeDecrementsTigerPenCountForHexC() {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        gameboard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.LAKE);
        gameboard.setTileAtPosition(103, 103, firstTile);

        Tile secondTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        gameboard.setTileAtPosition(105, 104, secondTile);

        gameboard.buildSettlement(101, 103, player);
        gameboard.buildSettlement(102, 103, player);
        gameboard.getGameBoardPositionArray()[103][102].setLevel(3);
        gameboard.getGameBoardPositionArray()[102][102].setLevel(3);
        gameboard.getGameBoardPositionArray()[102][103].setLevel(3);
        gameboard.placeTigerPen(103, 102, gameboard.getGameBoardPositionArray()[101][103].getSettlementID(), player);

        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[101][103].getSettlementID()), 3);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTigerCount(gameboard.getGameBoardPositionArray()[101][103].getSettlementID()), 1);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTotoroCount(gameboard.getGameBoardPositionArray()[101][103].getSettlementID()), 0);

        Tile thirdTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(), terrainTypes.JUNGLE, terrainTypes.VOLCANO, terrainTypes.LAKE);

        gameboard.nukeTiles(102, 103, thirdTile);

        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[101][103].getSettlementID()), 1);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTigerCount(gameboard.getGameBoardPositionArray()[101][103].getSettlementID()), 0);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTotoroCount(gameboard.getGameBoardPositionArray()[101][103].getSettlementID()), 0);

        Assert.assertEquals(gameboard.getGameBoardPositionArray()[103][102].getTigerCount(), 0);
    }

    @Test
    public void testIfNukeDecrementsTigerPenCountForHexA() {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        gameboard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.LAKE);
        gameboard.setTileAtPosition(103, 103, firstTile);

        Tile secondTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        gameboard.setTileAtPosition(105, 104, secondTile);

        gameboard.buildSettlement(101, 103, player);
        gameboard.buildSettlement(103, 102, player);
        gameboard.getGameBoardPositionArray()[103][102].setLevel(3);
        gameboard.getGameBoardPositionArray()[102][102].setLevel(3);
        gameboard.getGameBoardPositionArray()[102][103].setLevel(3);
        gameboard.placeTigerPen(102, 103, gameboard.getGameBoardPositionArray()[101][103].getSettlementID(), player);

        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[101][103].getSettlementID()), 3);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTigerCount(gameboard.getGameBoardPositionArray()[101][103].getSettlementID()), 1);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTotoroCount(gameboard.getGameBoardPositionArray()[101][103].getSettlementID()), 0);

        Tile thirdTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(), terrainTypes.JUNGLE, terrainTypes.VOLCANO, terrainTypes.LAKE);

        gameboard.nukeTiles(102, 103, thirdTile);

        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[101][103].getSettlementID()), 1);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTigerCount(gameboard.getGameBoardPositionArray()[101][103].getSettlementID()), 0);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTotoroCount(gameboard.getGameBoardPositionArray()[101][103].getSettlementID()), 0);

        Assert.assertEquals(gameboard.getGameBoardPositionArray()[102][103].getTigerCount(), 0);
    }
*/
    @Test
    public void testIfSplitSettlementsUpdatesSettlementListValuesForTotoroNextToNukedHexes() {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        gameboard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.LAKE);
        gameboard.setTileAtPosition(103, 103, firstTile);

        Tile secondTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        gameboard.setTileAtPosition(105, 104, secondTile);

        Tile thirdTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(), terrainTypes.JUNGLE, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameboard.setTileAtPosition(103, 101, thirdTile);

        gameboard.buildSettlement(101, 101, player);
        gameboard.buildSettlement(102, 101, player);
        gameboard.buildSettlement(103, 101, player);
        gameboard.buildSettlement(105, 103, player);
        gameboard.buildSettlement(103, 102, player);
        gameboard.buildSettlement(102, 103, player);
        gameboard.buildSettlement(101, 103, player);
        gameboard.buildSettlement(103, 100, player);
        gameboard.buildSettlement(104, 103, player);

        Assert.assertEquals(gameboard.getGameBoardPositionArray()[101][101].getSettlementID(), 1);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[101][103].getSettlementID(), 1);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[103][101].getSettlementID(), 1);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[104][103].getSettlementID(), 3);
        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(1), 7);

        gameboard.placeTotoroSanctuary(104, 102, 1, player);

        Assert.assertEquals(gameboard.getGameBoardSettlementListTotoroCount(1), 1);

        Tile fourthTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(), terrainTypes.JUNGLE, terrainTypes.LAKE, terrainTypes.VOLCANO);
        fourthTile.flip();
        gameboard.nukeTiles(102, 101, fourthTile);

        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[101][101].getSettlementID()), 1);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTotoroCount(gameboard.getGameBoardPositionArray()[101][101].getSettlementID()), 0);

        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[101][103].getSettlementID()), 2);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTotoroCount(gameboard.getGameBoardPositionArray()[101][103].getSettlementID()), 0);

        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[102][103].getSettlementID()), 2);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTotoroCount(gameboard.getGameBoardPositionArray()[102][103].getSettlementID()), 0);

        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[103][100].getSettlementID()), 5);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTotoroCount(gameboard.getGameBoardPositionArray()[103][100].getSettlementID()), 1);

        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[103][101].getSettlementID()), 5);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTotoroCount(gameboard.getGameBoardPositionArray()[103][101].getSettlementID()), 1);

        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[104][102].getSettlementID()), 5);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTotoroCount(gameboard.getGameBoardPositionArray()[104][102].getSettlementID()), 1);

        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[104][103].getSettlementID()), 5);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTotoroCount(gameboard.getGameBoardPositionArray()[104][103].getSettlementID()), 1);

        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[105][103].getSettlementID()), 5);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTotoroCount(gameboard.getGameBoardPositionArray()[105][103].getSettlementID()), 1);
    }


    @Test
    public void testIfSplitSettlementsDriverUpdatesSettlementListValuesForTigerNextToNukedHexes() {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        gameboard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.LAKE);
        gameboard.setTileAtPosition(103, 103, firstTile);

        Tile secondTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        gameboard.setTileAtPosition(105, 104, secondTile);

        Tile thirdTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(), terrainTypes.JUNGLE, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameboard.setTileAtPosition(103, 101, thirdTile);

        gameboard.buildSettlement(101, 101, player);
        gameboard.buildSettlement(102, 101, player);
        gameboard.buildSettlement(103, 101, player);
        gameboard.buildSettlement(105, 103, player);
        gameboard.buildSettlement(103, 102, player);
        gameboard.buildSettlement(102, 103, player);
        gameboard.buildSettlement(101, 103, player);
        gameboard.buildSettlement(103, 100, player);
        gameboard.buildSettlement(104, 103, player);

        Assert.assertEquals(gameboard.getGameBoardPositionArray()[101][101].getSettlementID(), 1);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[101][103].getSettlementID(), 1);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[103][101].getSettlementID(), 1);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[104][103].getSettlementID(), 3);
        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(1), 7);

        gameboard.getGameBoardPositionArray()[104][102].setLevel(3);
        gameboard.placeTigerPen(104, 102, 3, player);

        Assert.assertEquals(gameboard.getGameBoardSettlementListTigerCount(3), 1);

        Tile fourthTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(), terrainTypes.JUNGLE, terrainTypes.LAKE, terrainTypes.VOLCANO);
        fourthTile.flip();
        gameboard.nukeTiles(102, 101, fourthTile);

        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[101][101].getSettlementID()), 1);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTigerCount(gameboard.getGameBoardPositionArray()[101][101].getSettlementID()), 0);

        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[101][103].getSettlementID()), 2);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTigerCount(gameboard.getGameBoardPositionArray()[101][103].getSettlementID()), 0);

        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[102][103].getSettlementID()), 2);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTigerCount(gameboard.getGameBoardPositionArray()[102][103].getSettlementID()), 0);

        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[103][100].getSettlementID()), 5);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTigerCount(gameboard.getGameBoardPositionArray()[103][100].getSettlementID()), 1);

        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[103][101].getSettlementID()), 5);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTigerCount(gameboard.getGameBoardPositionArray()[103][101].getSettlementID()), 1);

        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[104][102].getSettlementID()), 5);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTigerCount(gameboard.getGameBoardPositionArray()[104][102].getSettlementID()), 1);

        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[104][103].getSettlementID()), 5);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTigerCount(gameboard.getGameBoardPositionArray()[104][103].getSettlementID()), 1);

        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[105][103].getSettlementID()), 5);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTigerCount(gameboard.getGameBoardPositionArray()[105][103].getSettlementID()), 1);
    }

    @Test
    public void testIfSplitSettlementsUpdatesSettlementListValuesForTotoroNotNextToNukedHexes() {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        gameboard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.LAKE);
        gameboard.setTileAtPosition(103, 103, firstTile);

        Tile secondTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        gameboard.setTileAtPosition(105, 104, secondTile);

        Tile thirdTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(), terrainTypes.JUNGLE, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameboard.setTileAtPosition(103, 101, thirdTile);

        gameboard.buildSettlement(101, 101, player);
        gameboard.buildSettlement(102, 101, player);
        gameboard.buildSettlement(103, 100, player);
        gameboard.buildSettlement(103, 101, player);
        gameboard.buildSettlement(103, 102, player);
        gameboard.buildSettlement(102, 103, player);
        gameboard.buildSettlement(101, 103, player);
        gameboard.buildSettlement(104, 102, player);
        gameboard.buildSettlement(104, 103, player);

        Assert.assertEquals(gameboard.getGameBoardPositionArray()[101][101].getSettlementID(), 1);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[101][103].getSettlementID(), 1);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[103][100].getSettlementID(), 1);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[104][103].getSettlementID(), 1);
        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(1), 9);

        gameboard.placeTotoroSanctuary(105, 103, 1, player);

        Assert.assertEquals(gameboard.getGameBoardSettlementListTotoroCount(1), 1);

        Tile fourthTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(), terrainTypes.JUNGLE, terrainTypes.LAKE, terrainTypes.VOLCANO);
        fourthTile.flip();
        gameboard.nukeTiles(102, 101, fourthTile);

        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[101][101].getSettlementID()), 1);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTotoroCount(gameboard.getGameBoardPositionArray()[101][101].getSettlementID()), 0);

        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[101][103].getSettlementID()), 2);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTotoroCount(gameboard.getGameBoardPositionArray()[101][103].getSettlementID()), 0);

        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[102][103].getSettlementID()), 2);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTotoroCount(gameboard.getGameBoardPositionArray()[102][103].getSettlementID()), 0);

        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[103][100].getSettlementID()), 5);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTotoroCount(gameboard.getGameBoardPositionArray()[103][100].getSettlementID()), 1);

        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[103][101].getSettlementID()), 5);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTotoroCount(gameboard.getGameBoardPositionArray()[103][101].getSettlementID()), 1);

        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[104][102].getSettlementID()), 5);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTotoroCount(gameboard.getGameBoardPositionArray()[104][102].getSettlementID()), 1);

        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[104][103].getSettlementID()), 5);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTotoroCount(gameboard.getGameBoardPositionArray()[104][103].getSettlementID()), 1);

        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[105][103].getSettlementID()), 5);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTotoroCount(gameboard.getGameBoardPositionArray()[105][103].getSettlementID()), 1);
    }

    @Test
    public void testIfSplitSettlementsUpdatesSettlementListValuesForTigerNotNextToNukedHexes() {
        GameBoard gameboard = new GameBoard();
        Player player = new Player(1);

        gameboard.placeFirstTileAndUpdateValidPlacementList();

        Tile firstTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.LAKE);
        gameboard.setTileAtPosition(103, 103, firstTile);

        Tile secondTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        gameboard.setTileAtPosition(105, 104, secondTile);

        Tile thirdTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(), terrainTypes.JUNGLE, terrainTypes.LAKE, terrainTypes.VOLCANO);
        gameboard.setTileAtPosition(103, 101, thirdTile);

        gameboard.buildSettlement(101, 101, player);
        gameboard.buildSettlement(102, 101, player);
        gameboard.buildSettlement(103, 100, player);
        gameboard.buildSettlement(103, 101, player);
        gameboard.buildSettlement(103, 102, player);
        gameboard.buildSettlement(102, 103, player);
        gameboard.buildSettlement(101, 103, player);
        gameboard.buildSettlement(104, 102, player);
        gameboard.buildSettlement(104, 103, player);

        Assert.assertEquals(gameboard.getGameBoardPositionArray()[101][101].getSettlementID(), 1);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[101][103].getSettlementID(), 1);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[103][100].getSettlementID(), 1);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[104][103].getSettlementID(), 1);
        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(1), 9);

        gameboard.getGameBoardPositionArray()[105][103].setLevel(3);
        gameboard.placeTigerPen(105, 103, 1, player);

        Assert.assertEquals(gameboard.getGameBoardSettlementListTigerCount(1), 1);

        Tile fourthTile = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(), terrainTypes.JUNGLE, terrainTypes.LAKE, terrainTypes.VOLCANO);
        fourthTile.flip();
        gameboard.nukeTiles(102, 101, fourthTile);

        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[101][101].getSettlementID()), 1);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTigerCount(gameboard.getGameBoardPositionArray()[101][101].getSettlementID()), 0);

        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[101][103].getSettlementID()), 2);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTigerCount(gameboard.getGameBoardPositionArray()[101][103].getSettlementID()), 0);

        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[102][103].getSettlementID()), 2);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTigerCount(gameboard.getGameBoardPositionArray()[102][103].getSettlementID()), 0);

        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[103][100].getSettlementID()), 5);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTigerCount(gameboard.getGameBoardPositionArray()[103][100].getSettlementID()), 1);

        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[103][101].getSettlementID()), 5);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTigerCount(gameboard.getGameBoardPositionArray()[103][101].getSettlementID()), 1);

        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[104][102].getSettlementID()), 5);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTigerCount(gameboard.getGameBoardPositionArray()[104][102].getSettlementID()), 1);

        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[104][103].getSettlementID()), 5);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTigerCount(gameboard.getGameBoardPositionArray()[104][103].getSettlementID()), 1);

        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(gameboard.getGameBoardPositionArray()[105][103].getSettlementID()), 5);
        Assert.assertEquals(gameboard.getGameBoardSettlementListTigerCount(gameboard.getGameBoardPositionArray()[105][103].getSettlementID()), 1);
    }
}