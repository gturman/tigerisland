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
    public void placingFirstTileUpdatesHexAndTileIDsProperlyTest() {
        GameBoard gameboard = new GameBoard();

        gameboard.placeFirstTileAndUpdateValidPlacementList();

        Assert.assertEquals(gameboard.getGameBoardHexID(), 6);
        Assert.assertEquals(gameboard.getGameboardTileID(), 2);
    }

    @Test
    public void placingFirstTileUpdatesPositionArrayProperlyTest() {
        GameBoard gameboard = new GameBoard();

        gameboard.placeFirstTileAndUpdateValidPlacementList();

        Hex testGameboardHexCenter = new Hex(1, 1, terrainTypes.VOLCANO);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[102][102].getHexID(), testGameboardHexCenter.getHexID());
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[102][102].getParentTileID(), testGameboardHexCenter.getParentTileID());
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[102][102].getHexTerrainType(), testGameboardHexCenter.getHexTerrainType());

        Hex testGameboardHexTopLeft = new Hex(2, 1, terrainTypes.JUNGLE);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[101][101].getHexID(), testGameboardHexTopLeft.getHexID());
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[101][101].getParentTileID(), testGameboardHexTopLeft.getParentTileID());
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[101][101].getHexTerrainType(), testGameboardHexTopLeft.getHexTerrainType());

        Hex testGameboardHexTopRight = new Hex(3, 1, terrainTypes.LAKE);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[102][101].getHexID(), testGameboardHexTopRight.getHexID());
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[102][101].getParentTileID(), testGameboardHexTopRight.getParentTileID());
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[102][101].getHexTerrainType(), testGameboardHexTopRight.getHexTerrainType());

        Hex testGameboardHexBottomLeft = new Hex(4, 1, terrainTypes.ROCKY);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[101][103].getHexID(), testGameboardHexBottomLeft.getHexID());
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[101][103].getParentTileID(), testGameboardHexBottomLeft.getParentTileID());
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[101][103].getHexTerrainType(), testGameboardHexBottomLeft.getHexTerrainType());

        Hex testGameboardHexBottomRight = new Hex(5, 1, terrainTypes.GRASSLANDS);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[102][103].getHexID(), testGameboardHexBottomRight.getHexID());
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[102][103].getParentTileID(), testGameboardHexBottomRight.getParentTileID());
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[102][103].getHexTerrainType(), testGameboardHexBottomRight.getHexTerrainType());
    }

    @Test
    public void placingFirstTileUpdatesValidPlacementArrayProperlyTest() {
        GameBoard gameboard = new GameBoard();

        gameboard.placeFirstTileAndUpdateValidPlacementList();

        Assert.assertEquals(gameboard.getValidPlacementArray()[102][102], -1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[101][101], -1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[102][101], -1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[101][103], -1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[102][103], -1);

        Assert.assertEquals(gameboard.getValidPlacementArray()[101][100], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[102][100], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[103][100], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[103][101], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[103][102], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[103][103], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[103][104], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[102][104], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[101][104], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[100][103], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[101][102], 1);
        Assert.assertEquals(gameboard.getValidPlacementArray()[100][101], 1);
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

        Assert.assertEquals(gameboard.getGameBoardPositionArray()[102][101].getHexLevel(), 2);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[103][102].getHexLevel(), 2);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[102][102].getHexLevel(), 2);

        Assert.assertEquals(gameboard.getGameBoardPositionArray()[101][102].getHexLevel(), 2);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[100][101].getHexLevel(), 2);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[101][101].getHexLevel(), 2);
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
    public void testPreventingNukeIfNukeCoversSize2Settlement() {
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

        Tile fourthTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        fourthTile.flip();

        Tile fifthTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.ROCKY, terrainTypes.GRASSLANDS, terrainTypes.VOLCANO);

        gameboard.getGameBoardPositionArray()[102][102].setSettlementID(2);
        gameboard.setSettlementSizeList(2, 1);
        gameboard.getGameBoardPositionArray()[103][102].setSettlementID(2);
        gameboard.setSettlementSizeList(2,2);

        Assert.assertEquals(gameboard.checkIfValidNuke(102, 101, fourthTile), false);
        Assert.assertEquals(gameboard.checkIfValidNuke(102, 102, fifthTile), false);
    }

    @Test
    public void testPreventingNukeIfNukeCoversSize1Settlement2(){
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

        gameboard.getGameBoardPositionArray()[103][102].setSettlementID(2);
        gameboard.setSettlementSizeList(2, 1);

        Assert.assertEquals(gameboard.checkIfValidNuke(102, 101, fourthTile), false);
        Assert.assertEquals(gameboard.checkIfValidNuke(102, 102, fifthTile), false);
    }

    @Test
    public void testPreventingNukeIfNukeCoversSize2Settlement2(){

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

        Tile fourthTile = new Tile(gameboard.getGameboardTileID(),gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.ROCKY);
        fourthTile.flip();


        gameboard.getGameBoardPositionArray()[104][102].setSettlementID(4);
        gameboard.setSettlementSizeList(4,1);
        gameboard.getGameBoardPositionArray()[103][102].setSettlementID(3);
        gameboard.setSettlementSizeList(3,1);

        Assert.assertEquals(gameboard.checkIfValidNuke(103,101,fourthTile),false);

    }

    @Test
    public void testIfWillNukeSize2SettlementWhileOnlyNuking1TileThatHasSettlement2(){
        GameBoard gameboard = new GameBoard();
        Tile firstTile = new Tile(gameboard.getGameboardTileID(),gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.LAKE);
        gameboard.setTileAtPosition(102,102,firstTile);
        Tile secondTile = new Tile(gameboard.getGameboardTileID(),gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        secondTile.flip();
        gameboard.setTileAtPosition(103,102,secondTile);
        Tile thirdTile = new Tile(gameboard.getGameboardTileID(),gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.GRASSLANDS,terrainTypes.ROCKY);
        gameboard.setTileAtPosition(104,102,thirdTile);

        Tile fourthTile = new Tile(gameboard.getGameboardTileID(),gameboard.getGameBoardHexID(),
                terrainTypes.LAKE, terrainTypes.VOLCANO, terrainTypes.ROCKY);


        gameboard.getGameBoardPositionArray()[103][103].setSettlementID(2);
        gameboard.getGameBoardPositionArray()[102][103].setSettlementID(2);
        gameboard.setSettlementSizeList(2,2);

        Assert.assertEquals(gameboard.checkIfValidNuke(103,103,fourthTile),true);

    }

    @Test
    public void anotherNukeSettlementSizeTest(){
        GameBoard gameboard = new GameBoard();

        Tile firstTile = new Tile(gameboard.getGameboardTileID(),gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.LAKE);
        gameboard.setTileAtPosition(102,102,firstTile);
        Tile secondTile = new Tile(gameboard.getGameboardTileID(),gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        secondTile.flip();
        gameboard.setTileAtPosition(103,102,secondTile);
        Tile thirdTile = new Tile(gameboard.getGameboardTileID(),gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.GRASSLANDS,terrainTypes.ROCKY);
        gameboard.setTileAtPosition(104,102,thirdTile);
        Tile fourthTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.GRASSLANDS,terrainTypes.ROCKY);

        gameboard.getGameBoardPositionArray()[102][101].setSettlementID(2);
        gameboard.setSettlementSizeList(2,1);
        gameboard.getGameBoardPositionArray()[103][101].setSettlementID(3);
        gameboard.setSettlementSizeList(3,1);

        Assert.assertEquals(gameboard.checkIfValidNuke(103,102,fourthTile),false);


        gameboard.getGameBoardPositionArray()[103][101].setSettlementID(2);
        gameboard.setSettlementSizeList(2,2);
        gameboard.setSettlementSizeList(3,0);

        Assert.assertEquals(gameboard.checkIfValidNuke(103,102,fourthTile),false);

    }

    @Test
    public void testForLevel2NukeOnSettlements(){

        GameBoard gameboard = new GameBoard();

        Tile firstTile = new Tile(gameboard.getGameboardTileID(),gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.GRASSLANDS, terrainTypes.LAKE);
        gameboard.setTileAtPosition(102,102,firstTile);
        Tile secondTile = new Tile(gameboard.getGameboardTileID(),gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.JUNGLE, terrainTypes.ROCKY);
        secondTile.flip();
        gameboard.setTileAtPosition(103,102,secondTile);
        Tile thirdTile = new Tile(gameboard.getGameboardTileID(),gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS,terrainTypes.VOLCANO,terrainTypes.ROCKY);
        gameboard.setTileAtPosition(104,102,thirdTile);

        Tile fourthTile = new Tile(gameboard.getGameboardTileID(),gameboard.getGameBoardHexID(),
                terrainTypes.LAKE,terrainTypes.ROCKY,terrainTypes.VOLCANO);
        fourthTile.flip();
        gameboard.setTileAtPosition(103,99,fourthTile);

        Tile fifthTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.ROCKY, terrainTypes.VOLCANO, terrainTypes.LAKE);
        fifthTile.flip();

        gameboard.setTileAtPosition(102,101,fifthTile);

        Tile sixthTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.LAKE, terrainTypes.GRASSLANDS, terrainTypes.VOLCANO);

        gameboard.setTileAtPosition(103,101,sixthTile);

        gameboard.getGameBoardPositionArray()[102][101].setSettlementID(2);
        gameboard.getGameBoardPositionArray()[103][101].setSettlementID(2);
        gameboard.setSettlementSizeList(2,2);

        Tile seventhTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO, terrainTypes.GRASSLANDS,terrainTypes.LAKE);

        Assert.assertEquals(gameboard.checkIfValidNuke(103,102,seventhTile),false);

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

    @Test
    public void testAbilityToBuildSettlements(){
        GameBoard gameboard = new GameBoard();
        Tile firstTile = new Tile(gameboard.getGameboardTileID(),gameboard.getGameBoardHexID(),terrainTypes.ROCKY,terrainTypes.JUNGLE,terrainTypes.VOLCANO);
        firstTile.flip();
        Player player = new Player(1);

        gameboard.setTileAtPosition(99,98,firstTile);

        Tile secondTile = new Tile(gameboard.getGameboardTileID(),gameboard.getGameBoardHexID(),terrainTypes.JUNGLE,terrainTypes.VOLCANO,terrainTypes.ROCKY);
        secondTile.flip();
        gameboard.setTileAtPosition(99,100,secondTile);

        gameboard.buildSettlement(99,98,player);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[99][98].getSettlerCount(),1);
        Assert.assertEquals(player.getVillagerCount(), 19);

        gameboard.buildSettlement(98,99,player);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[98][99].getSettlerCount(),0); //volcano
        Assert.assertEquals(player.getVillagerCount(), 19);

        gameboard.buildSettlement(99,99,player);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[99][98].getSettlerCount(),1);
        Assert.assertEquals(player.getVillagerCount(), 18);

        gameboard.buildSettlement(99,100,player);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[99][100].getSettlerCount(),1);
        Assert.assertEquals(player.getVillagerCount(), 17);

        gameboard.buildSettlement(98,101,player);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[98][101].getSettlerCount(),1);
        Assert.assertEquals(player.getVillagerCount(), 16);

        gameboard.buildSettlement(99,101,player);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[99][101].getSettlerCount(),0); //volcano
        Assert.assertEquals(player.getVillagerCount(), 16);

        Assert.assertEquals(player.getScore(), 4);

        //nuke
        Tile thirdTile = new Tile(gameboard.getGameboardTileID(),gameboard.getGameBoardHexID(),terrainTypes.LAKE,terrainTypes.VOLCANO,terrainTypes.GRASSLANDS);
        gameboard.nukeTiles(99,100,thirdTile);

        gameboard.buildSettlement(98,99,player);
        gameboard.buildSettlement(99,99,player);
        gameboard.buildSettlement(99,100,player);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[98][99].getSettlerCount(),0); //volcano + level 2
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[99][99].getSettlerCount(),0); //level 2
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[99][100].getSettlerCount(),0); //level 2
    }

    @Test
    public void testCalculateVillagersNeededForExpansionOnLevelOne(){
        GameBoard gameboard = new GameBoard();
        Player testPlayer = new Player(5);
        Tile tileOne = new Tile(gameboard.getGameboardTileID(),gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.LAKE,terrainTypes.GRASSLANDS);
        Tile tileTwo = new Tile(gameboard.getGameboardTileID(),gameboard.getGameBoardHexID(),
                terrainTypes.VOLCANO,terrainTypes.GRASSLANDS,terrainTypes.GRASSLANDS);
        Tile tileThree = new Tile(gameboard.getGameboardTileID(),gameboard.getGameBoardHexID(),
                terrainTypes.GRASSLANDS,terrainTypes.GRASSLANDS,terrainTypes.VOLCANO);
        tileThree.flip();

        gameboard.setTileAtPosition(100,100,tileOne);
        gameboard.setTileAtPosition(101,101,tileTwo);
        gameboard.setTileAtPosition(100,101,tileThree);

        gameboard.buildSettlement(99,99,testPlayer);
        int testInt = gameboard.calculateVillagersForExpansion(99,99,terrainTypes.GRASSLANDS);

        Assert.assertEquals(testInt,5);
    }

    @Test
    public void testCalculateVillagersNeededForExpansionOnMultipleLevels(){
        GameBoard gameboard = new GameBoard();
        Player testPlayer = new Player(5);
        Tile firstTile = new Tile(gameboard.getGameboardTileID(),gameboard.getGameBoardHexID(),terrainTypes.GRASSLANDS,terrainTypes.GRASSLANDS,terrainTypes.VOLCANO);
        firstTile.flip();
        gameboard.setTileAtPosition(99,98,firstTile);
        Tile secondTile = new Tile(gameboard.getGameboardTileID(),gameboard.getGameBoardHexID(),terrainTypes.GRASSLANDS,terrainTypes.VOLCANO,terrainTypes.GRASSLANDS);
        secondTile.flip();
        gameboard.setTileAtPosition(99,100,secondTile);
        Tile thirdTile = new Tile(gameboard.getGameboardTileID(),gameboard.getGameBoardHexID(),terrainTypes.GRASSLANDS,terrainTypes.VOLCANO,terrainTypes.GRASSLANDS);
        gameboard.nukeTiles(99,100,thirdTile);

        gameboard.buildSettlement(99,98,testPlayer);

        int testInt = gameboard.calculateVillagersForExpansion(99,98,terrainTypes.GRASSLANDS);

        Assert.assertEquals(testInt,5);
    }

    @Test
    public void testExpansionFromExistingSettlement(){

        GameBoard gameboard = new GameBoard();
        Player testPlayer = new Player();
        Tile firstTile = new Tile(gameboard.getGameboardTileID(),gameboard.getGameBoardHexID(),terrainTypes.GRASSLANDS,terrainTypes.GRASSLANDS,terrainTypes.VOLCANO);
        firstTile.flip();
        gameboard.setTileAtPosition(99,98,firstTile);
        Tile secondTile = new Tile(gameboard.getGameboardTileID(),gameboard.getGameBoardHexID(),terrainTypes.GRASSLANDS,terrainTypes.VOLCANO,terrainTypes.GRASSLANDS);
        secondTile.flip();
        gameboard.setTileAtPosition(99,100,secondTile);
        Tile thirdTile = new Tile(gameboard.getGameboardTileID(),gameboard.getGameBoardHexID(),terrainTypes.GRASSLANDS,terrainTypes.VOLCANO,terrainTypes.GRASSLANDS);
        gameboard.nukeTiles(99,100,thirdTile);

        gameboard.buildSettlement(99,98,testPlayer);

        gameboard.expandSettlement(99,98,terrainTypes.GRASSLANDS,testPlayer);

        Assert.assertEquals(testPlayer.getScore(),10); //1 for settlement founding, 9 for expansion (4*2 + 1)
        Assert.assertEquals(testPlayer.getVillagerCount(),14);//-1 settlement founding, -5 expansion (2 on lvl2 + 1 on lvl 1)
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[99][99].getSettlerCount(),2);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[99][99].getPlayerID(),2);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[99][100].getSettlerCount(),2);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[99][100].getPlayerID(),2);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[98][101].getSettlerCount(),1);
        Assert.assertEquals(gameboard.getGameBoardPositionArray()[99][100].getPlayerID(),2);
    }
}