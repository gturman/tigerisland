import org.junit.*;

/**
 * Created by geoff on 3/8/17.
 */

public class GameBoardTest {
    //some testing stuff should happen in here, for sure

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

    /*  @Test
      public void testAbilityToPreventNonAdjacentTilePlacement() {
          GameBoard gameboard = new GameBoard();

          Tile initialTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                  terrainTypes.GRASSLANDS, terrainTypes.VOLCANO, terrainTypes.LAKE);

          gameboard.setTileAtPosition(102, 102, initialTile);

          Tile nonAdjacentTile = new Tile(gameboard.getGameboardTileID(), gameboard.getGameBoardHexID(),
                                          terrainTypes.ROCKY, terrainTypes.VOLCANO, terrainTypes.LAKE);



          //Assert.assertEquals(gameboard.getGameBoardPositionArray()[][], );
      }
  */
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

        Assert.assertEquals(gameboard.checkIfTileBeingPlacedWillBeAdjadent(103, 101, secondTile), true);
        Assert.assertEquals(gameboard.checkIfTileBeingPlacedWillBeAdjadent(100, 101, secondTile), true);
        Assert.assertEquals(gameboard.checkIfTileBeingPlacedWillBeAdjadent(100, 100, secondTile), true);
        Assert.assertEquals(gameboard.checkIfTileBeingPlacedWillBeAdjadent(104, 104, secondTile), true);

        Assert.assertEquals(gameboard.checkIfTileBeingPlacedWillBeAdjadent(99, 99, secondTile), false);
        Assert.assertEquals(gameboard.checkIfTileBeingPlacedWillBeAdjadent(50, 50, secondTile), false);
        Assert.assertEquals(gameboard.checkIfTileBeingPlacedWillBeAdjadent(105, 105, secondTile), false);
    }
}