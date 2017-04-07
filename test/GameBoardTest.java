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
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][102].getHexTerrainType(), testGameBoardHexCenter.getHexTerrainType());

        Hex testGameBoardHexTopLeft = new Hex(2, 1, terrainTypes.JUNGLE);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][101].getHexID(), testGameBoardHexTopLeft.getHexID());
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][101].getParentTileID(), testGameBoardHexTopLeft.getParentTileID());
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][101].getHexTerrainType(), testGameBoardHexTopLeft.getHexTerrainType());

        Hex testGameBoardHexTopRight = new Hex(3, 1, terrainTypes.LAKE);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][101].getHexID(), testGameBoardHexTopRight.getHexID());
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][101].getParentTileID(), testGameBoardHexTopRight.getParentTileID());
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][101].getHexTerrainType(), testGameBoardHexTopRight.getHexTerrainType());

        Hex testGameBoardHexBottomLeft = new Hex(4, 1, terrainTypes.ROCKY);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][103].getHexID(), testGameBoardHexBottomLeft.getHexID());
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][103].getParentTileID(), testGameBoardHexBottomLeft.getParentTileID());
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][103].getHexTerrainType(), testGameBoardHexBottomLeft.getHexTerrainType());

        Hex testGameBoardHexBottomRight = new Hex(5, 1, terrainTypes.GRASSLANDS);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][103].getHexID(), testGameBoardHexBottomRight.getHexID());
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][103].getParentTileID(), testGameBoardHexBottomRight.getParentTileID());
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][103].getHexTerrainType(), testGameBoardHexBottomRight.getHexTerrainType());
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

        Assert.assertEquals(initialTile.getHexA().getHexCoordinate().getColumnPosition(), 102);
        Assert.assertEquals(initialTile.getHexA().getHexCoordinate().getRowPosition(), 102);
        Assert.assertEquals(initialTile.getHexB().getHexCoordinate().getColumnPosition(), 101);
        Assert.assertEquals(initialTile.getHexB().getHexCoordinate().getRowPosition(), 103);
        Assert.assertEquals(initialTile.getHexC().getHexCoordinate().getColumnPosition(), 102);
        Assert.assertEquals(initialTile.getHexC().getHexCoordinate().getRowPosition(), 103);

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

        gameBoard.getGameBoardPositionArray()[103][102].setHexLevel(2);

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
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[103][102], seventhTile.getHexB());
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][102], seventhTile.getHexC());

        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][102], eigthTile.getHexA());
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[100][101], eigthTile.getHexB());
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][101], eigthTile.getHexC());
    }

    @Test
    public void TestHexLevelIncreasesWhenNuking(){
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

        Tile eighthTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.LAKE, terrainTypes.GRASSLANDS);

        gameBoard.nukeTiles(102, 101, seventhTile);
        gameBoard.nukeTiles(101, 102, eighthTile);

        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][101].getHexLevel(), 2);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[103][102].getHexLevel(), 2);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][102].getHexLevel(), 2);

        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][102].getHexLevel(), 2);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[100][101].getHexLevel(), 2);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][101].getHexLevel(), 2);
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
    public void testPreventingNukeIfNukeCoversSize1Settlement2(){
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
    public void testPreventingNukeIfNukeCoversSize2Settlement2(){

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

        Tile fourthTile = new Tile(gameBoard.getGameBoardTileID(),gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.ROCKY);
        fourthTile.flip();

        gameBoard.getGameBoardPositionArray()[104][102].setSettlementID(4);
        gameBoard.assignSizeToGameBoardSettlementList(4, 1);
        gameBoard.getGameBoardPositionArray()[103][102].setSettlementID(3);
        gameBoard.assignSizeToGameBoardSettlementList(3, 1);

        Assert.assertEquals(gameBoard.nukeAtPositionIsValid(103,101,fourthTile),false);

    }

    @Test
    public void testIfWillNukeSize2SettlementWhileOnlyNuking1TileThatHasSettlement2(){
        GameBoard gameBoard = new GameBoard();
        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(),gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.LAKE);
        gameBoard.setTileAtPosition(102,102,firstTile);
        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(),gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        secondTile.flip();
        gameBoard.setTileAtPosition(103,102,secondTile);
        Tile thirdTile = new Tile(gameBoard.getGameBoardTileID(),gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.GRASSLANDS,terrainTypes.ROCKY);
        gameBoard.setTileAtPosition(104,102,thirdTile);

        Tile fourthTile = new Tile(gameBoard.getGameBoardTileID(),gameBoard.getGameBoardHexID(),
                terrainTypes.LAKE, terrainTypes.VOLCANO, terrainTypes.ROCKY);


        gameBoard.getGameBoardPositionArray()[103][103].setSettlementID(2);
        gameBoard.getGameBoardPositionArray()[102][103].setSettlementID(2);
        gameBoard.assignSizeToGameBoardSettlementList(2, 2);

        Assert.assertEquals(gameBoard.nukeAtPositionIsValid(103,103,fourthTile),true);

    }

    @Test
    public void anotherNukeSettlementSizeTest(){
        GameBoard gameBoard = new GameBoard();

        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(),gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.LAKE);
        gameBoard.setTileAtPosition(102,102,firstTile);
        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(),gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        secondTile.flip();
        gameBoard.setTileAtPosition(103,102,secondTile);
        Tile thirdTile = new Tile(gameBoard.getGameBoardTileID(),gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.GRASSLANDS,terrainTypes.ROCKY);
        gameBoard.setTileAtPosition(104,102,thirdTile);
        Tile fourthTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.GRASSLANDS,terrainTypes.ROCKY);

        gameBoard.getGameBoardPositionArray()[102][101].setSettlementID(2);
        gameBoard.assignSizeToGameBoardSettlementList(2, 1);
        gameBoard.getGameBoardPositionArray()[103][101].setSettlementID(3);
        gameBoard.assignSizeToGameBoardSettlementList(3, 1);

        Assert.assertEquals(gameBoard.nukeAtPositionIsValid(103,102,fourthTile),false);


        gameBoard.getGameBoardPositionArray()[103][101].setSettlementID(2);
        gameBoard.assignSizeToGameBoardSettlementList(2, 2);
        gameBoard.assignSizeToGameBoardSettlementList(3, 0);

        Assert.assertEquals(gameBoard.nukeAtPositionIsValid(103,102,fourthTile),false);

    }

    @Test
    public void testForLevel2NukeOnSettlements(){

        GameBoard gameBoard = new GameBoard();

        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(),gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.LAKE);
        gameBoard.setTileAtPosition(102,102,firstTile);
        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(),gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        secondTile.flip();
        gameBoard.setTileAtPosition(103,102,secondTile);
        Tile thirdTile = new Tile(gameBoard.getGameBoardTileID(),gameBoard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS,terrainTypes.VOLCANO,terrainTypes.ROCKY);
        gameBoard.setTileAtPosition(104,102,thirdTile);

        Tile fourthTile = new Tile(gameBoard.getGameBoardTileID(),gameBoard.getGameBoardHexID(),
                terrainTypes.LAKE,terrainTypes.ROCKY,terrainTypes.VOLCANO);
        fourthTile.flip();
        gameBoard.setTileAtPosition(103,99,fourthTile);

        Tile fifthTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.ROCKY, terrainTypes.VOLCANO, terrainTypes.LAKE);
        fifthTile.flip();

        gameBoard.setTileAtPosition(102,101,fifthTile);

        Tile sixthTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.LAKE, terrainTypes.GRASSLANDS, terrainTypes.VOLCANO);

        gameBoard.setTileAtPosition(103,101,sixthTile);

        gameBoard.getGameBoardPositionArray()[102][101].setSettlementID(2);
        gameBoard.getGameBoardPositionArray()[103][101].setSettlementID(2);
        gameBoard.assignSizeToGameBoardSettlementList(2, 2);
        Tile seventhTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.GRASSLANDS,terrainTypes.LAKE);

        Assert.assertEquals(gameBoard.nukeAtPositionIsValid(103,102,seventhTile),false);

    }

    @Test
    public void whereTheFuckAreOurTerrains(){
        GameBoard gameBoard = new GameBoard();

        Tile initialTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.ROCKY, terrainTypes.LAKE, terrainTypes.VOLCANO);

        gameBoard.setTileAtPosition(102, 102, initialTile);

        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][102].getHexTerrainType(), terrainTypes.ROCKY);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][101].getHexTerrainType(), terrainTypes.LAKE);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][101].getHexTerrainType(), terrainTypes.VOLCANO);

        GameBoard secondGameBoard = new GameBoard();

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(),
                terrainTypes.ROCKY, terrainTypes.LAKE, terrainTypes.VOLCANO);

        secondTile.flip();

        secondGameBoard.setTileAtPosition(102, 102, secondTile);

        Assert.assertEquals(secondGameBoard.getGameBoardPositionArray()[102][102].getHexTerrainType(), terrainTypes.ROCKY);
        Assert.assertEquals(secondGameBoard.getGameBoardPositionArray()[101][103].getHexTerrainType(), terrainTypes.VOLCANO);
        Assert.assertEquals(secondGameBoard.getGameBoardPositionArray()[102][103].getHexTerrainType(), terrainTypes.LAKE);

    }

    @Test
    public void testAbilityToBuildSettlements(){
        GameBoard gameBoard = new GameBoard();
        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(),gameBoard.getGameBoardHexID(),terrainTypes.ROCKY,terrainTypes.JUNGLE,terrainTypes.VOLCANO);
        firstTile.flip();
        Player player = new Player(1);

        gameBoard.setTileAtPosition(99,98,firstTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(),gameBoard.getGameBoardHexID(),terrainTypes.JUNGLE,terrainTypes.VOLCANO,terrainTypes.ROCKY);
        secondTile.flip();
        gameBoard.setTileAtPosition(99,100,secondTile);

        gameBoard.buildSettlement(99,98,player);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[99][98].getSettlerCount(),1);
        Assert.assertEquals(player.getVillagerCount(), 19);

        gameBoard.buildSettlement(98,99,player);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[98][99].getSettlerCount(),0); //volcano
        Assert.assertEquals(player.getVillagerCount(), 19);

        gameBoard.buildSettlement(99,99,player);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[99][98].getSettlerCount(),1);
        Assert.assertEquals(player.getVillagerCount(), 18);

        gameBoard.buildSettlement(99,100,player);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[99][100].getSettlerCount(),1);
        Assert.assertEquals(player.getVillagerCount(), 17);

        gameBoard.buildSettlement(98,101,player);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[98][101].getSettlerCount(),1);
        Assert.assertEquals(player.getVillagerCount(), 16);

        gameBoard.buildSettlement(99,101,player);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[99][101].getSettlerCount(),0); //volcano
        Assert.assertEquals(player.getVillagerCount(), 16);

        Assert.assertEquals(player.getScore(), 4);

        gameBoard.mergeSettlements();

        //nuke
        Tile thirdTile = new Tile(gameBoard.getGameBoardTileID(),gameBoard.getGameBoardHexID(),terrainTypes.LAKE,terrainTypes.VOLCANO,terrainTypes.GRASSLANDS);
        Assert.assertEquals(gameBoard.nukeAtPositionIsValid(99, 100, thirdTile), true);
        gameBoard.nukeTiles(99,100,thirdTile);

        gameBoard.buildSettlement(98,99, player);
        gameBoard.buildSettlement(99,99, player);
        gameBoard.buildSettlement(99,100, player);

        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[98][99].getSettlerCount(),0); //volcano + level 2
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[99][99].getSettlerCount(),0); //level 2
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[99][100].getSettlerCount(),0); //level 2
    }

    @Test
    public void testCalculateVillagersNeededForExpansionOnLevelOne(){
        GameBoard gameBoard = new GameBoard();
        Player testPlayer = new Player(2);
        Tile tileOne = new Tile(gameBoard.getGameBoardTileID(),gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.LAKE,terrainTypes.GRASSLANDS);
        Tile tileTwo = new Tile(gameBoard.getGameBoardTileID(),gameBoard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.GRASSLANDS,terrainTypes.GRASSLANDS);
        Tile tileThree = new Tile(gameBoard.getGameBoardTileID(),gameBoard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS,terrainTypes.GRASSLANDS,terrainTypes.VOLCANO);
        tileThree.flip();

        gameBoard.setTileAtPosition(100,100,tileOne);
        gameBoard.setTileAtPosition(101,101,tileTwo);
        gameBoard.setTileAtPosition(100,101,tileThree);

        gameBoard.buildSettlement(99,99,testPlayer);
        int testInt = gameBoard.calculateVillagersForExpansion(99,99,terrainTypes.GRASSLANDS, testPlayer);

        Assert.assertEquals(testInt,5);
    }

    @Test
    public void testCalculateVillagersNeededForExpansionOnMultipleLevels(){
        GameBoard gameBoard = new GameBoard();
        Player testPlayer = new Player(2);
        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(),gameBoard.getGameBoardHexID(),terrainTypes.GRASSLANDS,terrainTypes.GRASSLANDS,terrainTypes.VOLCANO);
        firstTile.flip();
        gameBoard.setTileAtPosition(99,98,firstTile);
        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(),gameBoard.getGameBoardHexID(),terrainTypes.GRASSLANDS,terrainTypes.VOLCANO,terrainTypes.GRASSLANDS);
        secondTile.flip();
        gameBoard.setTileAtPosition(99,100,secondTile);
        Tile thirdTile = new Tile(gameBoard.getGameBoardTileID(),gameBoard.getGameBoardHexID(),terrainTypes.GRASSLANDS,terrainTypes.VOLCANO,terrainTypes.GRASSLANDS);
        gameBoard.nukeTiles(99,100,thirdTile);

        gameBoard.buildSettlement(99,98,testPlayer);

        int testInt = gameBoard.calculateVillagersForExpansion(99,98,terrainTypes.GRASSLANDS, testPlayer);

        Assert.assertEquals(testInt,5);
    }

    @Test
    public void testExpansionFromExistingSettlement(){

        GameBoard gameBoard = new GameBoard();
        Player testPlayer = new Player(1);
        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(),gameBoard.getGameBoardHexID(),terrainTypes.GRASSLANDS,terrainTypes.GRASSLANDS,terrainTypes.VOLCANO);
        firstTile.flip();
        gameBoard.setTileAtPosition(99,98,firstTile);
        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(),gameBoard.getGameBoardHexID(),terrainTypes.GRASSLANDS,terrainTypes.VOLCANO,terrainTypes.GRASSLANDS);
        secondTile.flip();
        gameBoard.setTileAtPosition(99,100,secondTile);
        Tile thirdTile = new Tile(gameBoard.getGameBoardTileID(),gameBoard.getGameBoardHexID(),terrainTypes.GRASSLANDS,terrainTypes.VOLCANO,terrainTypes.GRASSLANDS);
        gameBoard.nukeTiles(99,100,thirdTile);

        gameBoard.buildSettlement(99,98,testPlayer);

        gameBoard.expandSettlement(99,98,terrainTypes.GRASSLANDS,testPlayer);

        Assert.assertEquals(testPlayer.getScore(),10); //1 for settlement founding + 9 for expansion (4*2 + 1)
        Assert.assertEquals(testPlayer.getVillagerCount(),14);//-1 settlement founding, -5 expansion (2 on lvl2 + 1 on lvl 1)
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[99][99].getSettlerCount(),2);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[99][99].getPlayerID(),1);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[99][100].getSettlerCount(),2);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[99][100].getPlayerID(),1);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[98][101].getSettlerCount(),1);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[99][100].getPlayerID(),1);
    }

    @Test
    public void testIfBuildUpdatesPlayerAndGameboardSettlementDataLists() {
        GameBoard gameBoard = new GameBoard();
        Player player = new Player(1);

        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(),gameBoard.getGameBoardHexID(),terrainTypes.ROCKY,terrainTypes.JUNGLE,terrainTypes.VOLCANO);
        firstTile.flip();
        gameBoard.setTileAtPosition(99,98,firstTile);

        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(),gameBoard.getGameBoardHexID(),terrainTypes.JUNGLE,terrainTypes.VOLCANO,terrainTypes.ROCKY);
        secondTile.flip();
        gameBoard.setTileAtPosition(99,100,secondTile);

        gameBoard.buildSettlement(99,98,player);
        Assert.assertEquals(gameBoard.getGameBoardSettlementListOwner(1), 1);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(1, 1), true);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[99][98].getSettlementID(), 1);

        gameBoard.buildSettlement(98,99,player); // placing over volcano
        Assert.assertEquals(gameBoard.getGameBoardSettlementListOwner(2), 0);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(2, 1), false);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[98][99].getSettlementID(), 0);

        gameBoard.buildSettlement(99,99,player);
        Assert.assertEquals(gameBoard.getGameBoardSettlementListOwner(2), 1);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(2, 1), true);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[99][99].getSettlementID(), 2);

        gameBoard.buildSettlement(99,100,player);
        Assert.assertEquals(gameBoard.getGameBoardSettlementListOwner(3), 1);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(3, 1), true);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[99][100].getSettlementID(), 3);

        gameBoard.buildSettlement(98,101,player);
        Assert.assertEquals(gameBoard.getGameBoardSettlementListOwner(4), 1);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(4, 1), true);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[98][101].getSettlementID(), 4);

        gameBoard.buildSettlement(99,101,player); // placing over volcano
        Assert.assertEquals(gameBoard.getGameBoardSettlementListOwner(5), 0);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(5, 1), false);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[99][101].getSettlementID(), 0);
    }

    @Test
    public void testIfExpandUpdatesdGameBoardSettlementDataListsSize() {
        GameBoard gameBoard = new GameBoard();
        Player testPlayer = new Player(1);

        Tile firstTile = new Tile(gameBoard.getGameBoardTileID(),gameBoard.getGameBoardHexID(),terrainTypes.GRASSLANDS,terrainTypes.GRASSLANDS,terrainTypes.VOLCANO);
        firstTile.flip();
        gameBoard.setTileAtPosition(99,98,firstTile);
        Tile secondTile = new Tile(gameBoard.getGameBoardTileID(),gameBoard.getGameBoardHexID(),terrainTypes.GRASSLANDS,terrainTypes.VOLCANO,terrainTypes.GRASSLANDS);
        secondTile.flip();
        gameBoard.setTileAtPosition(99,100,secondTile);
        Tile thirdTile = new Tile(gameBoard.getGameBoardTileID(),gameBoard.getGameBoardHexID(),terrainTypes.GRASSLANDS,terrainTypes.VOLCANO,terrainTypes.GRASSLANDS);
        gameBoard.nukeTiles(99,100,thirdTile);

        gameBoard.buildSettlement(99,98,testPlayer);

        gameBoard.expandSettlement(99,98,terrainTypes.GRASSLANDS,testPlayer);

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
        Player testPlayer = new Player(1);

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

        Player player = new Player(1);

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

        Assert.assertEquals(gameBoard.checkIfValidTotoroPlacement(101, 101, 1, player), false);
        Assert.assertEquals(gameBoard.checkIfValidTotoroPlacement(102, 104, 1, player), false);
        Assert.assertEquals(gameBoard.checkIfValidTotoroPlacement(102, 101, 1, player), false);
        Assert.assertEquals(gameBoard.checkIfValidTotoroPlacement(102, 103, 1, player), false);
        Assert.assertEquals(gameBoard.checkIfValidTotoroPlacement(102, 102, 1, player), false); // volcano

        gameBoard.placeTotoroSanctuary(101, 101, 1, player);
        gameBoard.placeTotoroSanctuary(102, 104, 1, player);
        gameBoard.placeTotoroSanctuary(102, 101, 1, player);
        gameBoard.placeTotoroSanctuary(102, 103, 1, player);
        gameBoard.placeTotoroSanctuary(102, 102, 1, player);

        Assert.assertEquals(gameBoard.getGameBoardSettlementListTotoroCount(1), 0);
    }

    @Test
    public void testIfTotoroPlacementIsPreventedForSettlementWithTotoroAlreadyInIt() {
        GameBoard gameBoard = new GameBoard();

        Player player = new Player(1);

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

        Assert.assertEquals(gameBoard.checkIfValidTotoroPlacement(102, 101, 1, player), true);
        gameBoard.placeTotoroSanctuary(102, 101, 1, player);

        Assert.assertEquals(gameBoard.checkIfValidTotoroPlacement(101, 105, 1, player), false);

        gameBoard.placeTotoroSanctuary(101, 105, 1, player);

        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][101].getTotoroCount(), 1);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][105].getTotoroCount(), 0);
    }

    @Test
    public void testIfTotoroCanNotBePlacedOverOccupiedPiece() {
        GameBoard gameBoard = new GameBoard();

        Player player = new Player(1);

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

        Assert.assertEquals(gameBoard.checkIfValidTotoroPlacement(102, 101, 1, player), false);

        gameBoard.getGameBoardPositionArray()[102][101].setTotoroCount(1);
        Assert.assertEquals(gameBoard.checkIfValidTotoroPlacement(102, 101, 1, player), false);

        gameBoard.getGameBoardPositionArray()[102][101].setTigerCount(1);
        Assert.assertEquals(gameBoard.checkIfValidTotoroPlacement(102, 101, 1, player), false);
    }

    @Test
    public void testIfTotoroCanNotBePlacedFarAwayFromSettlement() {
        GameBoard gameBoard = new GameBoard();

        Player player = new Player(1);

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

        Assert.assertEquals(gameBoard.checkIfValidTotoroPlacement(101, 105, 1, player), false);
    }

    @Test
    public void testIfTotoroPlacementDeniedIfNoTotorosLeft() {
        GameBoard gameBoard = new GameBoard();

        Player player = new Player(1);

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

        player.setTotoroCount(0);

        Assert.assertEquals(gameBoard.checkIfValidTotoroPlacement(101, 105, 1, player), false);
    }

    @Test
    public void testIfTotoroPlacementIsAllowed() {
        GameBoard gameBoard = new GameBoard();

        Player player = new Player(1);

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

        Assert.assertEquals(gameBoard.checkIfValidTotoroPlacement(101, 105, 1, player), true);
    }

    @Test
    public void testIfTotoroPlacementUpdatesHex() {
        GameBoard gameBoard = new GameBoard();

        Player player = new Player(1);

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

        gameBoard.placeTotoroSanctuary(101, 105, 1, player);

        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][105].getTotoroCount(), 1);
    }

        @Test
    public void testIfTotoroPlacementUpdatesPlayerScoreAndRemovesPieceFromInventory() {
            GameBoard gameBoard = new GameBoard();

            Player player = new Player(1);

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

            Assert.assertEquals(player.getScore(), 0);
            Assert.assertEquals(player.getTotoroCount(), 3);

            gameBoard.placeTotoroSanctuary(101, 105, 1, player);

            Assert.assertEquals(player.getScore(), 200);
            Assert.assertEquals(player.getTotoroCount(), 2);
    }

    @Test
    public void testIfTotoroPlacementUpdatesGameBoardSettlementListSizeAndTotoroCount() {
        GameBoard gameBoard = new GameBoard();

        Player player = new Player(1);

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

        gameBoard.placeTotoroSanctuary(101, 105, 1, player);

        Assert.assertEquals(gameBoard.getGameBoardSettlementListTotoroCount(1), 1);
        Assert.assertEquals(gameBoard.getGameBoardSettlementListSettlementSize(1), 7);
    }

    @Test
    public void testIfTigerPlacementIsPreventedForLessThanLevel3HexAndVolcano() {
        GameBoard gameBoard = new GameBoard();

        Player player = new Player(1);

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

        Assert.assertEquals(gameBoard.checkIfValidTigerPlacement(101, 101, 1, player), false);
        Assert.assertEquals(gameBoard.checkIfValidTigerPlacement(102, 101, 1, player), false);
        Assert.assertEquals(gameBoard.checkIfValidTigerPlacement(102, 103, 1, player), false);
        Assert.assertEquals(gameBoard.checkIfValidTigerPlacement(102, 102, 1, player), false); // volcano

        gameBoard.placeTigerPen(101, 101, 1, player);
        gameBoard.placeTigerPen(102, 101, 1, player);
        gameBoard.placeTigerPen(102, 103, 1, player);
        gameBoard.placeTigerPen(102, 102, 1, player);

        Assert.assertEquals(gameBoard.getGameBoardSettlementListTigerCount(1), 0);
    }

    @Test
    public void testIfTigerPlacementIsPreventedForSettlementWithTigerAlreadyInIt() {
        GameBoard gameBoard = new GameBoard();

        Player player = new Player(1);

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

        gameBoard.getGameBoardPositionArray()[102][101].setHexLevel(3);
        Assert.assertEquals(gameBoard.checkIfValidTigerPlacement(102, 101, 1, player), true);
        gameBoard.placeTigerPen(102, 101, 1, player);

        gameBoard.getGameBoardPositionArray()[102][101].setHexLevel(2);
        Assert.assertEquals(gameBoard.checkIfValidTigerPlacement(101, 105, 1, player), false);
        gameBoard.placeTigerPen(101, 105, 1, player);

        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][101].getTigerCount(), 1);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][105].getTigerCount(), 0);
    }

    @Test
    public void testIfTigerCanNotBePlacedOverOccupiedPiece() {
        GameBoard gameBoard = new GameBoard();

        Player player = new Player(1);

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

        Assert.assertEquals(gameBoard.checkIfValidTigerPlacement(102, 101, 1, player), false);

        gameBoard.getGameBoardPositionArray()[102][101].setTigerCount(1);
        Assert.assertEquals(gameBoard.checkIfValidTigerPlacement(102, 101, 1, player), false);

        gameBoard.getGameBoardPositionArray()[102][101].setTigerCount(1);
        Assert.assertEquals(gameBoard.checkIfValidTigerPlacement(102, 101, 1, player), false);
    }

    @Test
    public void testIfTigerCanNotBePlacedFarAwayFromSettlement() {
        GameBoard gameBoard = new GameBoard();

        Player player = new Player(1);

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

        Assert.assertEquals(gameBoard.checkIfValidTigerPlacement(101, 105, 1, player), false);
    }

    @Test
    public void testIfTigerPlacementIsNotAllowedIfPlayerDoesNotHaveEnoughPieces() {
        GameBoard gameBoard = new GameBoard();

        Player player = new Player(1);

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
        gameBoard.getGameBoardPositionArray()[101][105].setHexLevel(3);

        player.setTigerCount(0);

        Assert.assertEquals(gameBoard.checkIfValidTigerPlacement(101, 105, 1, player), false);

    }

    @Test
    public void testIfTigerPlacementIsAllowed() {
        GameBoard gameBoard = new GameBoard();

        Player player = new Player(1);

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
        gameBoard.getGameBoardPositionArray()[101][105].setHexLevel(3);

        Assert.assertEquals(gameBoard.checkIfValidTigerPlacement(101, 105, 1, player), true);

        gameBoard.getGameBoardPositionArray()[101][101].setHexLevel(50);

        Assert.assertEquals(gameBoard.checkIfValidTigerPlacement(101, 101, 1, player), true);
    }

    @Test
    public void testIfTigerPlacementUpdatesHex() {
        GameBoard gameBoard = new GameBoard();

        Player player = new Player(1);

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

        gameBoard.getGameBoardPositionArray()[101][105].setHexLevel(3);
        gameBoard.placeTigerPen(101, 105, 1, player);

        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][105].getTigerCount(), 1);
    }

        @Test
    public void testIfTigerPlacementUpdatesPlayerScoreAndInventory() {
            GameBoard gameBoard = new GameBoard();

            Player player = new Player(1);

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

            Assert.assertEquals(player.getScore(), 0);
            Assert.assertEquals(player.getTigerCount(), 2);

            gameBoard.getGameBoardPositionArray()[101][105].setHexLevel(3);
            gameBoard.placeTigerPen(101, 105, 1, player);

            Assert.assertEquals(player.getTigerCount(), 1);
            Assert.assertEquals(player.getScore(), 75);
    }

    @Test
    public void testIfTigerPlacementUpdatesGameBoardSettlementListSizeAndTigerCount() {
        GameBoard gameBoard = new GameBoard();

        Player player = new Player(1);

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

        Assert.assertEquals(player.getScore(), 0);

        gameBoard.getGameBoardPositionArray()[101][105].setHexLevel(3);
        gameBoard.placeTigerPen(101, 105, 1, player);

        Assert.assertEquals(gameBoard.getGameBoardSettlementListTigerCount(1), 1);
        Assert.assertEquals(gameBoard.getGameBoardSettlementListSettlementSize(1), 7);
    }

    @Test
    public void testIfMergeOccursAfterBuilds() {
        Player player = new Player(1);

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

        gameBoard.buildSettlement(101, 103, player);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][103].getSettlementID(), 1);
        gameBoard.buildSettlement(102, 103, player);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][103].getSettlementID(), 2);
        gameBoard.buildSettlement(102, 104, player);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][104].getSettlementID(), 3);
        gameBoard.buildSettlement(101, 101, player);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][101].getSettlementID(), 4);
        gameBoard.buildSettlement(102, 101, player);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][101].getSettlementID(), 5);
        gameBoard.buildSettlement(104, 104, player);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[104][104].getSettlementID(), 6);
        gameBoard.mergeSettlements();

        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][103].getSettlementID(), gameBoard.getGameBoardPositionArray()[102][103].getSettlementID());
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][103].getSettlementID(), gameBoard.getGameBoardPositionArray()[102][104].getSettlementID());
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][101].getSettlementID(), gameBoard.getGameBoardPositionArray()[102][101].getSettlementID());

        Assert.assertEquals(gameBoard.getGameBoardSettlementListSettlementSize(gameBoard.getGameBoardPositionArray()[101][103].getSettlementID()), 3);
        Assert.assertEquals(gameBoard.getGameBoardSettlementListSettlementSize(gameBoard.getGameBoardPositionArray()[101][101].getSettlementID()), 2);
        Assert.assertEquals(gameBoard.getGameBoardSettlementListSettlementSize(gameBoard.getGameBoardPositionArray()[104][104].getSettlementID()), 1);

        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(1, 1), false); // NOTE: THE *LAST* SETTLEMENT IN A "GROUP" PLACED WILL BE THE "MASTER" SETTLEMENT ID-WISE
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(2, 1), false);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(3, 1), true);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(4, 1), false);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(5, 1), true);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(6, 1), true);
    }

    @Test
    public void testIfMergeOccursAfterExpansions() {
        Player player = new Player(1);

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

        gameBoard.buildSettlement(101, 103, player);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][103].getSettlementID(), 1);
        gameBoard.buildSettlement(102, 101, player);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][101].getSettlementID(), 2);
        gameBoard.buildSettlement(104, 104, player);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[104][104].getSettlementID(), 3);

        gameBoard.expandSettlement(101, 103, terrainTypes.GRASSLANDS, player);
        gameBoard.mergeSettlements();

        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][103].getSettlementID(), 1); // EXPANSIONS OVERWRITE FROM HOME HEX, NOT LAST HEX PLACED
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][103].getSettlementID(), 1);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[103][102].getSettlementID(), 1);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][104].getSettlementID(), 1);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][105].getSettlementID(), 1);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][101].getSettlementID(), 1);

        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[104][104].getSettlementID(), 3);

        Assert.assertEquals(gameBoard.getGameBoardSettlementListSettlementSize(1), 6);

        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(1, player.getPlayerID()), true);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(2, player.getPlayerID()), false);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(3, player.getPlayerID()), true);
    }

    @Test
    public void testIfMergeDoesNotMergeOtherPlayerStructures() {
        Player player = new Player(1);
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

        gameBoard.buildSettlement(101, 103, player);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][103].getSettlementID(), 1);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][103].getPlayerID(), 1);

        gameBoard.buildSettlement(102, 101, playerTwo);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][101].getSettlementID(), 2);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][101].getPlayerID(), 2);

        gameBoard.buildSettlement(102, 104, playerTwo);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][104].getSettlementID(), 3);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][104].getPlayerID(), 2);

        gameBoard.buildSettlement(104, 104, player);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[104][104].getSettlementID(), 4);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[104][104].getPlayerID(), 1);

        gameBoard.expandSettlement(101, 103, terrainTypes.GRASSLANDS, player);

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
        Player player = new Player(1);

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

        gameBoard.buildSettlement(101, 103, player);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][103].getSettlementID(), 1);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(1, 1), true);
        gameBoard.buildSettlement(102, 103, player);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][103].getSettlementID(), 2);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(2, 1), true);
        gameBoard.buildSettlement(102, 104, player);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][104].getSettlementID(), 3);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(3, 1), true);
        gameBoard.buildSettlement(101, 105, player);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][105].getSettlementID(), 4);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(4, 1), true);
        gameBoard.buildSettlement(103, 103, player);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[103][103].getSettlementID(), 5);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(5, 1), true);
        gameBoard.buildSettlement(104, 104, player);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[104][104].getSettlementID(), 6);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(6, 1), true);
        gameBoard.buildSettlement(103, 102, player);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[103][102].getSettlementID(), 7);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(7, 1), true);
        gameBoard.buildSettlement(102, 101, player);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][101].getSettlementID(), 8);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(8, 1), true);
        gameBoard.buildSettlement(101, 101, player);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][101].getSettlementID(), 9);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(9, 1), true);

        gameBoard.mergeSettlements();

        Assert.assertEquals(gameBoard.getHexesBuiltOnThisTurn().isEmpty(), true);

        Assert.assertEquals(gameBoard.getGameBoardSettlementListSettlementSize(9), 9);

        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(1, 1), false);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(2, 1), false);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(3, 1), false);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(4, 1), false);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(5, 1), false);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(6, 1), false);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(7, 1), false);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(8, 1), false);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(9, 1), true);

        Tile fourthTile = new Tile(gameBoard.getGameBoardTileID(), gameBoard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.ROCKY);
        Assert.assertEquals(gameBoard.nukeAtPositionIsValid(102, 103, fourthTile), true);
        gameBoard.nukeTiles(102, 103, fourthTile);

        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][103].getSettlementID(), 3);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][104].getSettlementID(), 3);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][105].getSettlementID(), 3);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[104][104].getSettlementID(), 1);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[103][103].getSettlementID(), 1);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[102][101].getSettlementID(), 2);
        Assert.assertEquals(gameBoard.getGameBoardPositionArray()[101][101].getSettlementID(), 2);

        Assert.assertEquals(gameBoard.getGameBoardSettlementListSettlementSize(3), 3);
        Assert.assertEquals(gameBoard.getGameBoardSettlementListSettlementSize(1), 2);
        Assert.assertEquals(gameBoard.getGameBoardSettlementListSettlementSize(2), 2);

        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(1, 1), true);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(2, 1), true);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(3, 1), true);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(4, 1), false);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(5, 1), false);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(6, 1), false);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(7, 1), false);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(8, 1), false);
        Assert.assertEquals(gameBoard.playerOwnsSettlementWithID(9, 1), false);
    }

    @Test
    public void testIfExpandWorksOffOfSettlementOfSizeGreaterThanOne() {
        GameBoard gameboard = new GameBoard();

        Player player = new Player(1);

        gameboard.placeFirstTileAndUpdateValidPlacementList();

        Tile tileOne = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.GRASSLANDS);
        gameboard.setTileAtPosition(103, 103, tileOne);

        Tile tileTwo = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(), terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.GRASSLANDS);
        gameboard.setTileAtPosition(103, 101, tileTwo);

        Tile tileThree = new Tile(gameboard.getGameBoardTileID(), gameboard.getGameBoardHexID(), terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.GRASSLANDS);
        gameboard.setTileAtPosition(105, 102, tileThree);

        gameboard.buildSettlement(103, 101, player);
        gameboard.buildSettlement(104, 102, player);
        gameboard.buildSettlement(104, 101, player);

        gameboard.mergeSettlements();

        Assert.assertEquals(gameboard.playerOwnsSettlementWithID(3, player.getPlayerID()), true);

        gameboard.expandSettlement(104, 101, terrainTypes.GRASSLANDS, player);

        Assert.assertEquals(gameboard.playerOwnsSettlementWithID(3, player.getPlayerID()), true);
        Assert.assertEquals(gameboard.getGameBoardSettlementListSettlementSize(3), 7);
    }
}