/*
 * Created by William on 3/14/2017.
 */

import java.util.*;
import java.util.function.Predicate;

public class GameBoard {
    private int boardHeight = 202;
    private int boardWidth = 202;
    private int gameBoardTileID;
    private int gameBoardHexID;

    public int[][] validPlacementArray = new int[boardHeight][boardWidth];
    public Hex[][] gameBoardPositionArray = new Hex[boardHeight][boardWidth];
    public int[][] gameBoardSettlementList = new int[256][4];
    public int[] usedSettlementIDs = new int[256]; // Note: Settlement ID of 0 denotes a hex with no settlement on it - never use this ID

    private boolean[][] playerOwnedSettlementsList = new boolean[256][2]; // NEVER USE 0 FOR SETTLEMENT ID

    public Vector<Pair> hexesBuiltOnThisTurn = new Vector();
    public Vector<Pair> hexesToResetTraversalValue = new Vector();

    GameBoard() {
        this.gameBoardTileID = 1;
        this.gameBoardHexID = 1;
        usedSettlementIDs[0] = 1; // Note: Settlement ID of 0 denotes a hex with no settlement on it - never use this ID
    }



    void placeFirstTileAndUpdateValidPlacementList() {
        updateGameBoardPositionArrayWithFirstTileHexes();

        int hexAmount = 5;

        incrementGameBoardHexID(hexAmount);
        incrementGameBoardTileID();

        updateValidPlacementArrayWithFirstTile();
    }

    void updateGameBoardPositionArrayWithFirstTileHexes() {
        gameBoardPositionArray[102][102] = new Hex(1, 1, terrainTypes.VOLCANO);
        gameBoardPositionArray[101][101] = new Hex(2, 1, terrainTypes.JUNGLE);
        gameBoardPositionArray[102][101] = new Hex(3, 1, terrainTypes.LAKE);
        gameBoardPositionArray[101][103] = new Hex(4, 1, terrainTypes.ROCKY);
        gameBoardPositionArray[102][103] = new Hex(5, 1, terrainTypes.GRASSLANDS);
    }

    void updateValidPlacementArrayWithFirstTile() {
        validPlacementArray[102][102] = -1;
        validPlacementArray[101][101] = -1;
        validPlacementArray[102][101] = -1;
        validPlacementArray[101][103] = -1;
        validPlacementArray[102][103] = -1;

        validPlacementArray[101][102] = 1;
        validPlacementArray[100][101] = 1;
        validPlacementArray[101][100] = 1;
        validPlacementArray[102][100] = 1;
        validPlacementArray[103][100] = 1;
        validPlacementArray[103][101] = 1;
        validPlacementArray[103][102] = 1;
        validPlacementArray[103][103] = 1;
        validPlacementArray[103][104] = 1;
        validPlacementArray[102][104] = 1;
        validPlacementArray[101][104] = 1;
        validPlacementArray[100][103] = 1;
    }



    void nukeTiles(int colPos, int rowPos, Tile tileToBePlaced) {
        if (checkIfValidNuke(colPos, rowPos, tileToBePlaced)) {
            if (tileIsEvenAndFlipped(rowPos, tileToBePlaced))
                increaseEvenFlippedTileLevelAndUpdateGameBoard(colPos, rowPos, tileToBePlaced);
            if (tileIsOddAndFlipped(rowPos, tileToBePlaced))
                increaseOddFlippedTileLevelAndUpdateGameBoard(colPos, rowPos, tileToBePlaced);
            if (tileIsEvenAndNotFlipped(rowPos, tileToBePlaced))
                increaseEvenNotFlippedTileLevelAndUpdateGameBoard(colPos, rowPos, tileToBePlaced);
            if (tileIsOddAndNotFlipped(rowPos, tileToBePlaced))
                increaseOddNotFlippedTileLevelAndUpdateGameBoard(colPos, rowPos, tileToBePlaced);
            splitSettlements();
        }
    }

    boolean checkIfValidNuke(int colPos, int rowPos, Tile tileToBePlaced) {
        if (tileIsEvenAndFlipped(rowPos, tileToBePlaced) && canValidlyPlaceEvenFlippedTileInLocation(colPos, rowPos)) {
            if (hexAOfNewTileIsTheVolcano(tileToBePlaced) && hexBelowIsAVolcano(colPos, rowPos))
                return checkIfNoInvalidSettlementNukingForEvenFlippedTileWithVolcanoAtA(colPos, rowPos);
            if (hexBOfNewTileIsTheVolcano(tileToBePlaced) && hexBelowIsAVolcano(colPos, rowPos + 1))
                return checkIfNoInvalidSettlementNukingForEvenFlippedTileWithVolcanoAtB(colPos, rowPos);
            if (hexCOfNewTileIsTheVolcano(tileToBePlaced) && hexBelowIsAVolcano(colPos - 1, rowPos + 1))
                return checkIfNoInvalidSettlementNukingForEvenFlippedTileWithVolcanoAtC(colPos, rowPos);
        }

        if (tileIsOddAndFlipped(rowPos, tileToBePlaced) && canValidlyPlaceOddFlippedTileInLocation(colPos, rowPos)) {
            if (hexAOfNewTileIsTheVolcano(tileToBePlaced) && hexBelowIsAVolcano(colPos, rowPos))
                return checkIfNoInvalidSettlementNukingForOddFlippedTileWithVolcanoAtA(colPos, rowPos);
            if (hexBOfNewTileIsTheVolcano(tileToBePlaced) && hexBelowIsAVolcano(colPos + 1, rowPos + 1))
                return checkIfNoInvalidSettlementNukingForOddFlippedTileWithVolcanoAtB(colPos, rowPos);
            if (hexCOfNewTileIsTheVolcano(tileToBePlaced) && hexBelowIsAVolcano(colPos, rowPos + 1))
                return checkIfNoInvalidSettlementNukingForOddFlippedTileWithVolcanoAtC(colPos, rowPos);
        }

        if (tileIsEvenAndNotFlipped(rowPos, tileToBePlaced) && canValidlyPlaceEvenNotFlippedTileInLocation(colPos, rowPos)) {
            if (hexAOfNewTileIsTheVolcano(tileToBePlaced) && hexBelowIsAVolcano(colPos, rowPos))
                return checkIfNoInvalidSettlementNukingForEvenNotFlippedTileWithVolcanoAtA(colPos, rowPos);
            if (hexBOfNewTileIsTheVolcano(tileToBePlaced) && hexBelowIsAVolcano(colPos - 1, rowPos - 1))
                return checkIfNoInvalidSettlementNukingForEvenNotFlippedTileWithVolcanoAtB(colPos, rowPos);
            if (hexCOfNewTileIsTheVolcano(tileToBePlaced) && hexBelowIsAVolcano(colPos, rowPos - 1))
                return checkIfNoInvalidSettlementNukingForEvenNotFlippedTileWithVolcanoAtC(colPos, rowPos);
        }

        if (tileIsOddAndNotFlipped(rowPos, tileToBePlaced) && canValidlyPlaceOddNotFlippedTileInLocation(colPos, rowPos)) {
            if (hexAOfNewTileIsTheVolcano(tileToBePlaced) && hexBelowIsAVolcano(colPos, rowPos))
                return checkIfNoInvalidSettlementNukingForOddNotFlippedTileWithVolcanoAtA(colPos, rowPos);
            if (hexBOfNewTileIsTheVolcano(tileToBePlaced) && hexBelowIsAVolcano(colPos, rowPos - 1))
                return checkIfNoInvalidSettlementNukingForOddNotFlippedTileWithVolcanoAtB(colPos, rowPos);
            if (hexCOfNewTileIsTheVolcano(tileToBePlaced) && hexBelowIsAVolcano(colPos + 1, rowPos - 1))
                return checkIfNoInvalidSettlementNukingForOddNotFlippedTileWithVolcanoAtC(colPos, rowPos);
        }
        return false;
    }

    boolean canValidlyPlaceEvenFlippedTileInLocation(int colPos, int rowPos) {
        return noEmptySpacesBelowEvenFlippedTile(colPos, rowPos) &&
                sameHexLevelBelowAsNewEvenFlippedTile(colPos, rowPos) &&
                notCoveringEntireEvenFlippedTile(colPos, rowPos) &&
                noTigerPenBelowEvenFlippedTile(colPos, rowPos) &&
                noTotoroBelowEvenFlippedTile(colPos, rowPos);
    }

    boolean canValidlyPlaceOddFlippedTileInLocation(int colPos, int rowPos) {
        return noEmptySpacesBelowOddFlippedTile(colPos, rowPos) &&
                sameHexLevelBelowAsNewOddFlippedTile(colPos, rowPos) &&
                notCoveringEntireOddFlippedTile(colPos, rowPos) &&
                noTigerPenBelowOddFlippedTile(colPos, rowPos) &&
                noTotoroBelowOddFlippedTile(colPos, rowPos);
    }

    boolean canValidlyPlaceEvenNotFlippedTileInLocation(int colPos, int rowPos) {
        return noEmptySpacesBelowEvenNotFlippedTile(colPos, rowPos) &&
                sameHexLevelBelowAsNewEvenNotFlippedTile(colPos, rowPos) &&
                notCoveringEntireEvenNotFlippedTile(colPos, rowPos) &&
                noTigerPenBelowEvenNotFlippedTile(colPos, rowPos) &&
                noTotoroBelowEvenNotFlippedTile(colPos, rowPos);
    }

    boolean canValidlyPlaceOddNotFlippedTileInLocation(int colPos, int rowPos) {
        return noEmptySpacesBelowOddNotFlippedTile(colPos, rowPos) &&
                sameHexLevelBelowAsNewOddNotFlippedTile(colPos, rowPos) &&
                notCoveringEntireOddNotFlippedTile(colPos, rowPos) &&
                noTigerPenBelowOddNotFlippedTile(colPos, rowPos) &&
                noTotoroBelowOddNotFlippedTile(colPos, rowPos);
    }

    boolean noEmptySpacesBelowEvenFlippedTile(int colPos, int rowPos) {
        return gameBoardPositionNotEmpty(colPos, rowPos) // not nuking over empty gameBoard
                && gameBoardPositionNotEmpty(colPos, rowPos + 1)
                && gameBoardPositionNotEmpty(colPos - 1, rowPos + 1);
    }

    boolean noEmptySpacesBelowOddFlippedTile(int colPos, int rowPos) {
        return gameBoardPositionNotEmpty(colPos, rowPos)
                && gameBoardPositionNotEmpty(colPos + 1, rowPos + 1)
                && gameBoardPositionNotEmpty(colPos, rowPos + 1);
    }

    boolean noEmptySpacesBelowEvenNotFlippedTile(int colPos, int rowPos) {
        return gameBoardPositionNotEmpty(colPos, rowPos)
                && gameBoardPositionNotEmpty(colPos - 1, rowPos - 1)
                && gameBoardPositionNotEmpty(colPos, rowPos - 1);
    }

    boolean noEmptySpacesBelowOddNotFlippedTile(int colPos, int rowPos) {
        return gameBoardPositionNotEmpty(colPos, rowPos)
                && gameBoardPositionNotEmpty(colPos, rowPos - 1)
                && gameBoardPositionNotEmpty(colPos + 1, rowPos - 1);
    }

    boolean sameHexLevelBelowAsNewEvenFlippedTile(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getHexLevel() == gameBoardPositionArray[colPos][rowPos + 1].getHexLevel() //making sure hex level of new tile placing is same as one below
                && gameBoardPositionArray[colPos][rowPos].getHexLevel() == gameBoardPositionArray[colPos - 1][rowPos + 1].getHexLevel()
                && gameBoardPositionArray[colPos][rowPos + 1].getHexLevel() == gameBoardPositionArray[colPos - 1][rowPos + 1].getHexLevel();
    }

    boolean sameHexLevelBelowAsNewOddFlippedTile(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getHexLevel() == gameBoardPositionArray[colPos + 1][rowPos + 1].getHexLevel()
                && gameBoardPositionArray[colPos][rowPos].getHexLevel() == gameBoardPositionArray[colPos][rowPos + 1].getHexLevel()
                && gameBoardPositionArray[colPos + 1][rowPos + 1].getHexLevel() == gameBoardPositionArray[colPos][rowPos + 1].getHexLevel();
    }

    boolean sameHexLevelBelowAsNewEvenNotFlippedTile(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getHexLevel() == gameBoardPositionArray[colPos - 1][rowPos - 1].getHexLevel()
                && gameBoardPositionArray[colPos][rowPos].getHexLevel() == gameBoardPositionArray[colPos][rowPos - 1].getHexLevel()
                && gameBoardPositionArray[colPos - 1][rowPos - 1].getHexLevel() == gameBoardPositionArray[colPos][rowPos - 1].getHexLevel();
    }

    boolean sameHexLevelBelowAsNewOddNotFlippedTile(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getHexLevel() == gameBoardPositionArray[colPos][rowPos - 1].getHexLevel()
                && gameBoardPositionArray[colPos][rowPos].getHexLevel() == gameBoardPositionArray[colPos + 1][rowPos - 1].getHexLevel()
                && gameBoardPositionArray[colPos][rowPos - 1].getHexLevel() == gameBoardPositionArray[colPos + 1][rowPos - 1].getHexLevel();
    }

    boolean notCoveringEntireEvenFlippedTile(int colPos, int rowPos) {
        return !(gameBoardPositionArray[colPos][rowPos].getParentTileID() == gameBoardPositionArray[colPos][rowPos + 1].getParentTileID() // making sure not nuking over same entire tile
                && gameBoardPositionArray[colPos][rowPos].getParentTileID() == gameBoardPositionArray[colPos - 1][rowPos + 1].getParentTileID()
                && gameBoardPositionArray[colPos][rowPos + 1].getParentTileID() == gameBoardPositionArray[colPos - 1][rowPos + 1].getParentTileID());
    }

    boolean notCoveringEntireOddFlippedTile(int colPos, int rowPos) {
        return !(gameBoardPositionArray[colPos][rowPos].getParentTileID() == gameBoardPositionArray[colPos + 1][rowPos + 1].getParentTileID()
                && gameBoardPositionArray[colPos][rowPos].getParentTileID() == gameBoardPositionArray[colPos][rowPos + 1].getParentTileID()
                && gameBoardPositionArray[colPos + 1][rowPos + 1].getParentTileID() == gameBoardPositionArray[colPos][rowPos + 1].getParentTileID());
    }

    boolean notCoveringEntireEvenNotFlippedTile(int colPos, int rowPos) {
        return !(gameBoardPositionArray[colPos][rowPos].getParentTileID() == gameBoardPositionArray[colPos - 1][rowPos - 1].getParentTileID()
                && gameBoardPositionArray[colPos][rowPos].getParentTileID() == gameBoardPositionArray[colPos][rowPos - 1].getParentTileID()
                && gameBoardPositionArray[colPos - 1][rowPos - 1].getParentTileID() == gameBoardPositionArray[colPos][rowPos - 1].getParentTileID());
    }

    boolean notCoveringEntireOddNotFlippedTile(int colPos, int rowPos) {
        return !(gameBoardPositionArray[colPos][rowPos].getParentTileID() == gameBoardPositionArray[colPos][rowPos - 1].getParentTileID()
                && gameBoardPositionArray[colPos][rowPos].getParentTileID() == gameBoardPositionArray[colPos + 1][rowPos - 1].getParentTileID()
                && gameBoardPositionArray[colPos][rowPos - 1].getParentTileID() == gameBoardPositionArray[colPos + 1][rowPos - 1].getParentTileID());
    }

    boolean noTigerPenBelowEvenFlippedTile(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getTigerCount() == 0 // not nuking over a tiger pen
                && gameBoardPositionArray[colPos][rowPos + 1].getTigerCount() == 0
                && gameBoardPositionArray[colPos - 1][rowPos + 1].getTigerCount() == 0;
    }

    boolean noTigerPenBelowOddFlippedTile(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getTigerCount() == 0
                && gameBoardPositionArray[colPos + 1][rowPos + 1].getTigerCount() == 0
                && gameBoardPositionArray[colPos][rowPos + 1].getTigerCount() == 0;
    }

    boolean noTigerPenBelowEvenNotFlippedTile(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getTigerCount() == 0
                && gameBoardPositionArray[colPos - 1][rowPos - 1].getTigerCount() == 0
                && gameBoardPositionArray[colPos][rowPos - 1].getTigerCount() == 0;
    }

    boolean noTigerPenBelowOddNotFlippedTile(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getTigerCount() == 0
                && gameBoardPositionArray[colPos][rowPos - 1].getTigerCount() == 0
                && gameBoardPositionArray[colPos + 1][rowPos - 1].getTigerCount() == 0;
    }

    boolean noTotoroBelowEvenFlippedTile(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getTotoroCount() == 0 // not nuking over a totoro
                && gameBoardPositionArray[colPos][rowPos + 1].getTotoroCount() == 0
                && gameBoardPositionArray[colPos - 1][rowPos + 1].getTotoroCount() == 0;
    }

    boolean noTotoroBelowOddFlippedTile(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getTotoroCount() == 0
                && gameBoardPositionArray[colPos + 1][rowPos + 1].getTotoroCount() == 0
                && gameBoardPositionArray[colPos][rowPos + 1].getTotoroCount() == 0;
    }

    boolean noTotoroBelowEvenNotFlippedTile(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getTotoroCount() == 0
                && gameBoardPositionArray[colPos - 1][rowPos - 1].getTotoroCount() == 0
                && gameBoardPositionArray[colPos][rowPos - 1].getTotoroCount() == 0;
    }

    boolean noTotoroBelowOddNotFlippedTile(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getTotoroCount() == 0
                && gameBoardPositionArray[colPos][rowPos - 1].getTotoroCount() == 0
                && gameBoardPositionArray[colPos + 1][rowPos - 1].getTotoroCount() == 0;
    }

    boolean hexAOfNewTileIsTheVolcano(Tile tileToBePlaced) {
        return tileToBePlaced.getHexA().getHexTerrainType() == terrainTypes.VOLCANO;
    }

    boolean hexBOfNewTileIsTheVolcano(Tile tileToBePlaced) {
        if(tileToBePlaced.isFlipped())
            return tileToBePlaced.getHexC().getHexTerrainType() == terrainTypes.VOLCANO;
        else {
            return tileToBePlaced.getHexB().getHexTerrainType() == terrainTypes.VOLCANO;
        }
    }

    boolean hexCOfNewTileIsTheVolcano(Tile tileToBePlaced) {
        if(tileToBePlaced.isFlipped())
            return tileToBePlaced.getHexB().getHexTerrainType() == terrainTypes.VOLCANO;
        else {
            return tileToBePlaced.getHexC().getHexTerrainType() == terrainTypes.VOLCANO;
        }
    }

    boolean hexBelowIsAVolcano(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getHexTerrainType() == terrainTypes.VOLCANO;
    }

    boolean checkIfNoInvalidSettlementNukingForEvenFlippedTileWithVolcanoAtA(int colPos, int rowPos) {
        if ((getGameBoardPositionSettlementID(colPos, rowPos + 1)
                == getGameBoardPositionSettlementID(colPos - 1, rowPos + 1)) &&
                (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(colPos, rowPos + 1)) == 2)) {  // checking settlement shit
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(colPos, rowPos + 1)) == 1) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(colPos - 1, rowPos + 1)) == 1) {
            return false;
        } else {
            return true;
        }
    }

    boolean checkIfNoInvalidSettlementNukingForEvenFlippedTileWithVolcanoAtB(int colPos, int rowPos) {
        if ((getGameBoardPositionSettlementID(colPos, rowPos)
                == getGameBoardPositionSettlementID(colPos - 1, rowPos + 1)) &&
                (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(colPos, rowPos)) == 2)) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(colPos, rowPos)) == 1) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(colPos - 1, rowPos + 1)) == 1) {
            return false;
        } else {
            return true;
        }
    }

    boolean checkIfNoInvalidSettlementNukingForEvenFlippedTileWithVolcanoAtC(int colPos, int rowPos) {
        if ((getGameBoardPositionSettlementID(colPos, rowPos + 1)
                == getGameBoardPositionSettlementID(colPos, rowPos)) &&
                (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(colPos, rowPos)) == 2)) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(colPos, rowPos)) == 1) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(colPos, rowPos + 1)) == 1) {
            return false;
        } else {
            return true;
        }
    }

    boolean checkIfNoInvalidSettlementNukingForOddFlippedTileWithVolcanoAtA(int colPos, int rowPos) {
        if ((getGameBoardPositionSettlementID(colPos + 1, rowPos + 1)
                == getGameBoardPositionSettlementID(colPos, rowPos + 1)) &&
                (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(colPos + 1, rowPos + 1)) == 2)) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(colPos, rowPos + 1)) == 1) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(colPos + 1, rowPos + 1)) == 1) {
            return false;
        } else {
            return true;
        }
    }

    boolean checkIfNoInvalidSettlementNukingForOddFlippedTileWithVolcanoAtB(int colPos, int rowPos) {
        if ((getGameBoardPositionSettlementID(colPos, rowPos)
                == getGameBoardPositionSettlementID(colPos, rowPos + 1)) &&
                (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(colPos, rowPos)) == 2)) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(colPos, rowPos)) == 1) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(colPos, rowPos + 1)) == 1) {
            return false;
        } else {
            return true;
        }
    }

    boolean checkIfNoInvalidSettlementNukingForOddFlippedTileWithVolcanoAtC(int colPos, int rowPos) {
        if ((getGameBoardPositionSettlementID(colPos, rowPos)
                == getGameBoardPositionSettlementID(colPos + 1, rowPos + 1)) &&
                (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(colPos, rowPos)) == 2)) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(colPos, rowPos)) == 1) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(colPos + 1, rowPos + 1)) == 1) {
            return false;
        } else {
            return true;
        }
    }

    boolean checkIfNoInvalidSettlementNukingForEvenNotFlippedTileWithVolcanoAtA(int colPos, int rowPos) {
        if ((getGameBoardPositionSettlementID(colPos, rowPos - 1)
                == getGameBoardPositionSettlementID(colPos - 1, rowPos - 1)) &&
                (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(colPos, rowPos - 1)) == 2)) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(colPos, rowPos - 1)) == 1) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(colPos - 1, rowPos - 1)) == 1) {
            return false;
        } else {
            return true;
        }
    }

    boolean checkIfNoInvalidSettlementNukingForEvenNotFlippedTileWithVolcanoAtB(int colPos, int rowPos) {
        if ((getGameBoardPositionSettlementID(colPos, rowPos - 1)
                == getGameBoardPositionSettlementID(colPos, rowPos)) &&
                (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(colPos, rowPos)) == 2)) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(colPos, rowPos)) == 1) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(colPos, rowPos - 1)) == 1) {
            return false;
        } else {
            return true;
        }
    }

    boolean checkIfNoInvalidSettlementNukingForEvenNotFlippedTileWithVolcanoAtC(int colPos, int rowPos) {
        if ((getGameBoardPositionSettlementID(colPos, rowPos)
                == getGameBoardPositionSettlementID(colPos - 1, rowPos - 1)) &&
                (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(colPos, rowPos)) == 2)) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(colPos, rowPos)) == 1) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(colPos - 1, rowPos - 1)) == 1) {
            return false;
        } else {
            return true;
        }
    }

    boolean checkIfNoInvalidSettlementNukingForOddNotFlippedTileWithVolcanoAtA(int colPos, int rowPos) {
        if ((getGameBoardPositionSettlementID(colPos, rowPos - 1)
                == getGameBoardPositionSettlementID(colPos + 1, rowPos - 1)) &&
                (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(colPos, rowPos - 1)) == 2)) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(colPos, rowPos - 1)) == 1) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(colPos + 1, rowPos - 1)) == 1) {
            return false;
        } else {
            return true;
        }
    }

    boolean checkIfNoInvalidSettlementNukingForOddNotFlippedTileWithVolcanoAtB(int colPos, int rowPos) {
        if ((getGameBoardPositionSettlementID(colPos, rowPos)
                == getGameBoardPositionSettlementID(colPos + 1, rowPos - 1)) &&
                (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(colPos, rowPos + 1)) == 2)) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(colPos, rowPos)) == 1) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(colPos + 1, rowPos - 1)) == 1) {
            return false;
        } else {
            return true;
        }
    }

    boolean checkIfNoInvalidSettlementNukingForOddNotFlippedTileWithVolcanoAtC(int colPos, int rowPos) {
        if ((getGameBoardPositionSettlementID(colPos, rowPos)
                == getGameBoardPositionSettlementID(colPos, rowPos - 1)) &&
                (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(colPos, rowPos)) == 2)) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(colPos, rowPos)) == 1) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(colPos, rowPos - 1)) == 1) {
            return false;
        } else {
            return true;
        }
    }

    // TODO: skip doing split settlements if tile nukes over hexes that don't have any settlements on them
    void increaseEvenFlippedTileLevelAndUpdateGameBoard(int colPos, int rowPos, Tile tileToBePlaced) {
        setHexALevelAndUpdateGameBoard(colPos, rowPos, tileToBePlaced);
        setHexBLevelAndUpdateGameBoard(colPos, rowPos + 1, tileToBePlaced);
        setHexCLevelAndUpdateGameBoard(colPos - 1, rowPos + 1, tileToBePlaced);

        markHexIfSettlementAtPositionNeedsToBeSplitDueToNuke(colPos, rowPos-1);
        markHexIfSettlementAtPositionNeedsToBeSplitDueToNuke(colPos+1, rowPos);
        markHexIfSettlementAtPositionNeedsToBeSplitDueToNuke(colPos+1, rowPos+1);
        markHexIfSettlementAtPositionNeedsToBeSplitDueToNuke(colPos+1, rowPos+2);
        markHexIfSettlementAtPositionNeedsToBeSplitDueToNuke(colPos, rowPos+2);
        markHexIfSettlementAtPositionNeedsToBeSplitDueToNuke(colPos-1, rowPos+2);
        markHexIfSettlementAtPositionNeedsToBeSplitDueToNuke(colPos-2, rowPos+1);
        markHexIfSettlementAtPositionNeedsToBeSplitDueToNuke(colPos-1, rowPos);
        markHexIfSettlementAtPositionNeedsToBeSplitDueToNuke(colPos-1, rowPos-1);
    }

    void increaseOddFlippedTileLevelAndUpdateGameBoard(int colPos, int rowPos, Tile tileToBePlaced) {
        setHexALevelAndUpdateGameBoard(colPos, rowPos, tileToBePlaced);
        setHexBLevelAndUpdateGameBoard(colPos + 1, rowPos + 1, tileToBePlaced);
        setHexCLevelAndUpdateGameBoard(colPos, rowPos + 1, tileToBePlaced);

        markHexIfSettlementAtPositionNeedsToBeSplitDueToNuke(colPos+1, rowPos-1);
        markHexIfSettlementAtPositionNeedsToBeSplitDueToNuke(colPos+1, rowPos);
        markHexIfSettlementAtPositionNeedsToBeSplitDueToNuke(colPos+2, rowPos+1);
        markHexIfSettlementAtPositionNeedsToBeSplitDueToNuke(colPos+1, rowPos+2);
        markHexIfSettlementAtPositionNeedsToBeSplitDueToNuke(colPos, rowPos+2);
        markHexIfSettlementAtPositionNeedsToBeSplitDueToNuke(colPos-1, rowPos+2);
        markHexIfSettlementAtPositionNeedsToBeSplitDueToNuke(colPos-1, rowPos+1);
        markHexIfSettlementAtPositionNeedsToBeSplitDueToNuke(colPos-1, rowPos);
        markHexIfSettlementAtPositionNeedsToBeSplitDueToNuke(colPos, rowPos-1);
    }

    void increaseEvenNotFlippedTileLevelAndUpdateGameBoard(int colPos, int rowPos, Tile tileToBePlaced) {
        setHexALevelAndUpdateGameBoard(colPos, rowPos, tileToBePlaced);
        setHexBLevelAndUpdateGameBoard(colPos - 1, rowPos - 1, tileToBePlaced);
        setHexCLevelAndUpdateGameBoard(colPos, rowPos - 1, tileToBePlaced);

        markHexIfSettlementAtPositionNeedsToBeSplitDueToNuke(colPos, rowPos+1);
        markHexIfSettlementAtPositionNeedsToBeSplitDueToNuke(colPos-1, rowPos+1);
        markHexIfSettlementAtPositionNeedsToBeSplitDueToNuke(colPos-1, rowPos);
        markHexIfSettlementAtPositionNeedsToBeSplitDueToNuke(colPos-2, rowPos-1);
        markHexIfSettlementAtPositionNeedsToBeSplitDueToNuke(colPos-1, rowPos-2);
        markHexIfSettlementAtPositionNeedsToBeSplitDueToNuke(colPos, rowPos-2);
        markHexIfSettlementAtPositionNeedsToBeSplitDueToNuke(colPos+1, rowPos-2);
        markHexIfSettlementAtPositionNeedsToBeSplitDueToNuke(colPos+1, rowPos-1);
        markHexIfSettlementAtPositionNeedsToBeSplitDueToNuke(colPos+1, rowPos);
    }

    void increaseOddNotFlippedTileLevelAndUpdateGameBoard(int colPos, int rowPos, Tile tileToBePlaced) {
        setHexALevelAndUpdateGameBoard(colPos, rowPos, tileToBePlaced);
        setHexBLevelAndUpdateGameBoard(colPos, rowPos - 1, tileToBePlaced);
        setHexCLevelAndUpdateGameBoard(colPos + 1, rowPos - 1, tileToBePlaced);

        markHexIfSettlementAtPositionNeedsToBeSplitDueToNuke(colPos+1, rowPos+1);
        markHexIfSettlementAtPositionNeedsToBeSplitDueToNuke(colPos, rowPos+1);
        markHexIfSettlementAtPositionNeedsToBeSplitDueToNuke(colPos-1, rowPos);
        markHexIfSettlementAtPositionNeedsToBeSplitDueToNuke(colPos-1, rowPos-1);
        markHexIfSettlementAtPositionNeedsToBeSplitDueToNuke(colPos-1, rowPos-2);
        markHexIfSettlementAtPositionNeedsToBeSplitDueToNuke(colPos, rowPos-2);
        markHexIfSettlementAtPositionNeedsToBeSplitDueToNuke(colPos+1, rowPos-2);
        markHexIfSettlementAtPositionNeedsToBeSplitDueToNuke(colPos+2, rowPos-1);
        markHexIfSettlementAtPositionNeedsToBeSplitDueToNuke(colPos+1, rowPos);
    }

    void setHexALevelAndUpdateGameBoard(int colPos, int rowPos, Tile tileToBePlaced) {
        if(hexToBeNukedHasSettlement(gameBoardPositionArray[colPos][rowPos])) {
            decrementGameBoardSettlementListSize(getGameBoardPositionSettlementID(colPos, rowPos));
        }
        tileToBePlaced.getHexA().setHexLevel(gameBoardPositionArray[colPos][rowPos].getHexLevel() + 1);
        gameBoardPositionArray[colPos][rowPos] = tileToBePlaced.getHexA();
    }

    void setHexBLevelAndUpdateGameBoard(int colPos, int rowPos, Tile tileToBePlaced) {
        if(hexToBeNukedHasSettlement(gameBoardPositionArray[colPos][rowPos])) {
            decrementGameBoardSettlementListSize(getGameBoardPositionSettlementID(colPos, rowPos));
        }
        tileToBePlaced.getHexB().setHexLevel(gameBoardPositionArray[colPos][rowPos].getHexLevel() + 1);
        gameBoardPositionArray[colPos][rowPos] = tileToBePlaced.getHexB();
    }

    void setHexCLevelAndUpdateGameBoard(int colPos, int rowPos, Tile tileToBePlaced) {
        if(hexToBeNukedHasSettlement(gameBoardPositionArray[colPos][rowPos])) {
            decrementGameBoardSettlementListSize(getGameBoardPositionSettlementID(colPos, rowPos));
        }
        tileToBePlaced.getHexC().setHexLevel(gameBoardPositionArray[colPos][rowPos].getHexLevel() + 1);
        gameBoardPositionArray[colPos][rowPos] = tileToBePlaced.getHexC();
    }

    boolean hexToBeNukedHasSettlement(Hex hex) {
        return hex.getSettlementID() != 0;
    }

    void decrementGameBoardSettlementListSize(int settlementID) {
        this.gameBoardSettlementList[settlementID][1] -= 1;
    }

    void markHexIfSettlementAtPositionNeedsToBeSplitDueToNuke(int colPos, int rowPos) {
        if(gameBoardPositionNotEmpty(colPos, rowPos) && existsASettlementAtGameBoardPosition(colPos, rowPos)){
            addHexWithSettlementAdjacentToNukeToHexesBuiltOnList(colPos , rowPos);
        }
    }

    boolean gameBoardPositionNotEmpty(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos] != null;
    }

    boolean existsASettlementAtGameBoardPosition(int colPos, int rowPos) {
        return getGameBoardPositionSettlementID(colPos, rowPos) != 0;
    }



    void splitSettlements() {
        if(hexesBuiltOnThisTurn.isEmpty()) {
            resetTraversalList();
            return;
        }

        Pair currentCoordinates = hexesBuiltOnThisTurn.lastElement();
        hexesBuiltOnThisTurn.remove(hexesBuiltOnThisTurn.size()-1);
        markGameBoardPositionAsTraversed(currentCoordinates);
        hexesToResetTraversalValue.add(currentCoordinates);

        int playerID = getGameBoardPositionPlayerID(currentCoordinates);

        int oldSettlementID = getGameBoardPositionSettlementID(currentCoordinates);

        int masterSettlementID = getNewestAssignableSettlementID();
        setPlayerOwnedSettlementsListIsOwned(masterSettlementID, playerID); // add player ownership for new settlement

        setGameBoardPositionSettlementID(currentCoordinates, masterSettlementID);
        setGameBoardSettlementListPlayerID(masterSettlementID, playerID);

        updateGameBoardSettlementListValuesWhenThereIsAPieceAt(currentCoordinates, playerID, oldSettlementID, masterSettlementID);

        recursivelyAddAdjacentHexesToNewlySplitSettlement(currentCoordinates, playerID, masterSettlementID);

        splitSettlements();

    }

    void resetTraversalList() { // TODO: come up with non-naive solution to this problem
        for(Pair pair : hexesToResetTraversalValue){
            unmarkGameBoardPositionAsTraversed(pair.getRowPosition(), pair.getColumnPosition());
        }
        hexesToResetTraversalValue.clear();
    }

    void markGameBoardPositionAsTraversed(Pair currentCoordinates) {
        markGameBoardPositionAsTraversed(currentCoordinates.getColumnPosition(), currentCoordinates.getRowPosition());
    }

    boolean markGameBoardPositionAsTraversed(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].setIfAlreadyTraversed(true);
    }

    int getNewestAssignableSettlementID() {
        for (int i = 1; i < 256; i++) { // Note: Settlement ID of 0 denotes a hex with no settlement on it - never use this ID
            if (usedSettlementIDs[i] == 0) {
                usedSettlementIDs[i] = 1;
                return i;
            }
        }
        throw new RuntimeException("error: no available settlements found");
    }

    void recursivelyAddAdjacentHexesToNewlySplitSettlement(Pair currentCoordinates, int playerID, int masterSettlementID) {
        if(checkIfEven(currentCoordinates.getRowPosition())) {
            recursivelyAddAdjacentHexesToNewlySplitSettlementForEvenCoordinates(currentCoordinates, playerID, masterSettlementID);
        } else {
            recursivelyAddAdjacentHexesToNewlySplitSettlementForOddCoordinates(masterSettlementID, playerID, currentCoordinates);
        }
    }

    void recursivelyAddAdjacentHexesToNewlySplitSettlementForEvenCoordinates(Pair currentCoordinates, int playerID, int masterSettlementID) {
        splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()-1, currentCoordinates.getRowPosition()-1);
        splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition(), currentCoordinates.getRowPosition()-1);
        splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()+1, currentCoordinates.getRowPosition());
        splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition(), currentCoordinates.getRowPosition()+1);
        splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()-1, currentCoordinates.getRowPosition()+1);
        splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()-1, currentCoordinates.getRowPosition());
    }

    void recursivelyAddAdjacentHexesToNewlySplitSettlementForOddCoordinates(int masterSettlementID, int playerID, Pair currentCoordinates) {
        splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition(), currentCoordinates.getRowPosition()-1);
        splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()+1, currentCoordinates.getRowPosition()-1);
        splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()+1, currentCoordinates.getRowPosition());
        splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()+1, currentCoordinates.getRowPosition()+1);
        splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition(), currentCoordinates.getRowPosition()+1);
        splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()-1, currentCoordinates.getRowPosition());
    }


    //TODO: James will refactor on Thursday
    void splitSettlementsDriver(int masterSettlementID, int playerID, int colPos, int rowPos) {
        try { // is not null
            if(gameBoardPositionHasNotBeenCheckedYet(colPos, rowPos)) {
                if (gameBoardPositionOwnedByPlayer(playerID, colPos, rowPos)) {
                    markGameBoardPositionAsTraversed(colPos, rowPos); // mark as traversed

                    Pair currentCoordinates = new Pair(colPos, rowPos);
                    hexesToResetTraversalValue.add(currentCoordinates);

                    try {
                        markCurrentHexForRemovalFromBuiltOnThisTurnList(currentCoordinates);
                        deleteFromHexesBuiltOnThisTurn();
                    }
                    catch (NullPointerException e) {}

                    int oldSettlementID = getGameBoardPositionSettlementID(colPos, rowPos);

                    gameBoardPositionArray[colPos][rowPos].setSettlementID(masterSettlementID); // set hex settlement ID to new ID

                    updateGameBoardSettlementListValuesInDriverWhenThereIsAPieceAt(colPos, rowPos, masterSettlementID, playerID, oldSettlementID);

                    recursivelyAddAdjacentHexesToNewlySplitSettlement(currentCoordinates, playerID, masterSettlementID);

                    unmarkGameBoardPositionAsTraversed(colPos, rowPos);
                }
            }
        }
        catch(NullPointerException e) {return;} // end search if we reach null hex

        return;
    }

    int getGameBoardPositionSettlementID(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getSettlementID();
    }

    boolean unmarkGameBoardPositionAsTraversed(int colPos, int rowPos) {
        return gameBoardPositionArray[rowPos][colPos].setIfAlreadyTraversed(false);
    }

    void markCurrentHexForRemovalFromBuiltOnThisTurnList(Pair currentCoordinates) {
        int i = 0;
        for(Pair pair : hexesBuiltOnThisTurn) { // try to remove any occurrences of currently seen item in hexesBuiltOnThisTurn to not waste time re-splitting/merging already split/merged settlements
            if(currentCoordinates.getRowPosition() == pair.getRowPosition() && currentCoordinates.getColumnPosition() == pair.getColumnPosition()) {
                hexesBuiltOnThisTurn.elementAt(i).setCoordinates(-1, -1); // mark pair for deletion
            }
            i++;
        }
    }

    boolean gameBoardPositionHasNotBeenCheckedYet(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getIfAlreadyTraversed() == false;
    }

    void updateGameBoardSettlementListValuesInDriverWhenThereIsAPieceAt(int colPos, int rowPos, int masterSettlementID, int playerID, int oldSettlementID) {
        if (thereIsASettlerAt(colPos, rowPos)) {
            incrementGameBoardSettlementListSize(masterSettlementID);

            if(getGameBoardSettlementListSettlementSize(oldSettlementID) == 1){
                deleteSettlementFromGame(oldSettlementID, playerID);
            }
            else {
                decrementGameBoardSettlementListSize(oldSettlementID);
            }
        }
        else if (thereIsATotoroAt(colPos, rowPos)) {
            incrementGameBoardSettlementListSize(masterSettlementID);
            incrementGameBoardSettlementListTotoroCount(masterSettlementID);

            if(getGameBoardSettlementListSettlementSize(oldSettlementID) == 1){
                deleteSettlementFromGame(oldSettlementID, playerID);
            }
            else {
                decrementGameBoardSettlementListSize(oldSettlementID);
                decrementGameBoardSettlementListTotoroCount(oldSettlementID);
            }
        }
        else if (thereIsATigerAt(colPos, rowPos)) {
            incrementGameBoardSettlementListSize(masterSettlementID);
            incrementGameBoardSettlementListTigerCount(masterSettlementID);

            if(getGameBoardSettlementListSettlementSize(oldSettlementID) == 1){
                deleteSettlementFromGame(oldSettlementID, playerID);
            }
            else {
                decrementGameBoardSettlementListSize(oldSettlementID);
                decrementGameBoardSettlementListTigerCount(oldSettlementID);
            }
        }
    }

    void deleteSettlementFromGame(int settlementID, int playerID) {
        this.gameBoardSettlementList[settlementID][0] = 0;
        this.gameBoardSettlementList[settlementID][1] = 0;
        this.gameBoardSettlementList[settlementID][2] = 0;
        this.gameBoardSettlementList[settlementID][3] = 0;
        usedSettlementIDs[settlementID] = 0;
        setPlayerOwnedSettlementsListIsNotOwned(settlementID, playerID); // remove player ownership of settlement
    }

    void setPlayerOwnedSettlementsListIsNotOwned(int settlementID, int playerID) {
        this.playerOwnedSettlementsList[settlementID][playerID-1] = false;
    }

    void decrementGameBoardSettlementListTotoroCount(int settlementID) {
        this.gameBoardSettlementList[settlementID][2] -= 1;
    }

    void updateGameBoardSettlementListValuesWhenThereIsAPieceAt(Pair currentCoordinates, int playerID, int oldSettlementID, int masterSettlementID) {
        if (thereIsASettlerAt(currentCoordinates)) {
            incrementGameBoardSettlementListSize(masterSettlementID);
            if(getGameBoardSettlementListSettlementSize(oldSettlementID) == 1){
                deleteSettlementFromGame(oldSettlementID, playerID);
            } else {
                decrementGameBoardSettlementListSize(oldSettlementID);
            }
        } else if (thereIsATotoroAt(currentCoordinates)) {
            incrementGameBoardSettlementListSize(masterSettlementID);
            incrementGameBoardSettlementListTotoroCount(masterSettlementID);
            if(getGameBoardSettlementListSettlementSize(oldSettlementID) == 1){
                deleteSettlementFromGame(oldSettlementID, playerID);
            } else {
                decrementGameBoardSettlementListSize(oldSettlementID);
            }
        } else if (thereIsATigerAt(currentCoordinates)) {
            incrementGameBoardSettlementListSize(masterSettlementID);
            incrementGameBoardSettlementListTigerCount(masterSettlementID);
            if(getGameBoardSettlementListSettlementSize(oldSettlementID) == 1){
                deleteSettlementFromGame(oldSettlementID, playerID);
            } else {
                decrementGameBoardSettlementListSize(oldSettlementID);
                decrementGameBoardSettlementListTigerCount(oldSettlementID);
            }
        }
    }

    boolean thereIsATigerAt(Pair currentCoordinates) {
        return thereIsATigerAt(currentCoordinates.getColumnPosition(), currentCoordinates.getRowPosition());
    }

    boolean thereIsATigerAt(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getTigerCount() == 1;
    }

    boolean thereIsATotoroAt(Pair currentCoordinates) {
        return thereIsATotoroAt(currentCoordinates.getColumnPosition(), currentCoordinates.getRowPosition());
    }

    boolean thereIsATotoroAt(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getTotoroCount() == 1;
    }

    boolean thereIsASettlerAt(Pair currentCoordinates) {
        return thereIsASettlerAt(currentCoordinates.getColumnPosition(), currentCoordinates.getRowPosition());
    }

    boolean thereIsASettlerAt(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getSettlerCount() != 0;
    }

    void decrementGameBoardSettlementListTigerCount(int settlementID) {
        this.gameBoardSettlementList[settlementID][3] -= 1;
    }

    void setGameBoardPositionSettlementID(Pair currentCoordinates, int masterSettlementID) {
        gameBoardPositionArray[currentCoordinates.getColumnPosition()][currentCoordinates.getRowPosition()].setSettlementID(masterSettlementID);
    }

    void setGameBoardSettlementListPlayerID(int settlementID, int playerID) {
        this.gameBoardSettlementList[settlementID][0] = playerID;
    }



    void setTileAtPosition(int colPos, int rowPos, Tile tileToBePlaced) {
        if (!checkIfTileCanBePlacedAtPosition(colPos, rowPos, tileToBePlaced))return;

        if (tileIsEvenAndFlipped(rowPos, tileToBePlaced) && thereIsNotAFlippedEvenTileThere(colPos, rowPos))
            setEvenFlippedCoordinatesAndUpdateGameBoard(colPos, rowPos, tileToBePlaced);
        if (tileIsOddAndFlipped(rowPos, tileToBePlaced) && thereIsNotAFlippedOddTileThere(colPos, rowPos))
            setOddFlippedCoordinatesAndUpdateGameBoard(colPos, rowPos, tileToBePlaced);
        if (tileIsEvenAndNotFlipped(rowPos, tileToBePlaced) && thereIsNotANonFlippedEvenTileThere(colPos, rowPos))
            setEvenNotFlippedCoordinatesAndUpdateGameBoard(colPos, rowPos, tileToBePlaced);
        if (tileIsOddAndNotFlipped(rowPos, tileToBePlaced) && thereIsNotANonFlippedOddTileThere(colPos, rowPos))
            setOddNotFlippedCoordinatesAndUpdateGameBoard(colPos, rowPos, tileToBePlaced);

        updateValidTilePlacementList(tileToBePlaced);
        incrementGameBoardTileID();
        int hexAmount = 3;
        incrementGameBoardHexID(hexAmount);
    }

    boolean checkIfTileCanBePlacedAtPosition(int colPos, int rowPos, Tile tileToBePlaced) {
        if (tileIsEvenAndFlipped(rowPos, tileToBePlaced)) {
            if (validPlacementArray[colPos][rowPos] == -1) return false;
            if (validPlacementArray[colPos - 1][rowPos + 1] == -1) return false;
            if (validPlacementArray[colPos][rowPos + 1] == -1) return false;
        } else if (tileIsOddAndFlipped(rowPos, tileToBePlaced)) {
            if (validPlacementArray[colPos][rowPos] == -1) return false;
            if (validPlacementArray[colPos - 1][rowPos + 1] == -1) return false;
            if (validPlacementArray[colPos + 1][rowPos + 1] == -1) return false;
        } else if (tileIsEvenAndNotFlipped(rowPos, tileToBePlaced)) {
            if (validPlacementArray[colPos][rowPos] == -1) return false;
            if (validPlacementArray[colPos - 1][rowPos - 1] == -1) return false;
            if (validPlacementArray[colPos][rowPos - 1] == -1) return false;
        } else if (tileIsOddAndNotFlipped(rowPos, tileToBePlaced)) {
            if (validPlacementArray[colPos][rowPos] == -1) return false;
            if (validPlacementArray[colPos][rowPos - 1] == -1) return false;
            if (validPlacementArray[colPos + 1][rowPos - 1] == -1) return false;
        }
        return true;
    }

    boolean tileIsEvenAndFlipped(int rowPos, Tile tileToBePlaced) {
        return (tileToBePlaced.isFlipped() && checkIfEven(rowPos));
    }

    boolean tileIsOddAndFlipped(int rowPos, Tile tileToBePlaced) {
        return (tileToBePlaced.isFlipped() && !checkIfEven(rowPos));
    }

    boolean tileIsEvenAndNotFlipped(int rowPos, Tile tileToBePlaced) {
        return (!tileToBePlaced.isFlipped() && checkIfEven(rowPos));
    }

    boolean tileIsOddAndNotFlipped(int rowPos, Tile tileToBePlaced) {
        return (!tileToBePlaced.isFlipped() && !checkIfEven(rowPos));
    }

    boolean checkIfEven(int rowPos) {
        if (rowPos % 2 == 0) {
            return true;
        } else {
            return false;
        }
    }

    boolean thereIsNotAFlippedEvenTileThere(int colPos, int rowPos) {
        return checkIfHexDoesNotOccupyPosition(colPos, rowPos) &&
                checkIfHexDoesNotOccupyPosition(colPos - 1, rowPos + 1) &&
                checkIfHexDoesNotOccupyPosition(colPos, rowPos + 1);
    }

    boolean thereIsNotAFlippedOddTileThere(int colPos, int rowPos) {
        return checkIfHexDoesNotOccupyPosition(colPos, rowPos) &&
                checkIfHexDoesNotOccupyPosition(colPos, rowPos + 1) &&
                checkIfHexDoesNotOccupyPosition(colPos + 1, rowPos + 1);
    }

    boolean thereIsNotANonFlippedEvenTileThere(int colPos, int rowPos) {
        return checkIfHexDoesNotOccupyPosition(colPos, rowPos) &&
                checkIfHexDoesNotOccupyPosition(colPos - 1, rowPos - 1) &&
                checkIfHexDoesNotOccupyPosition(colPos, rowPos - 1);
    }

    boolean thereIsNotANonFlippedOddTileThere(int colPos, int rowPos) {
        return checkIfHexDoesNotOccupyPosition(colPos, rowPos) &&
                checkIfHexDoesNotOccupyPosition(colPos, rowPos - 1) &&
                checkIfHexDoesNotOccupyPosition(colPos + 1, rowPos - 1);
    }

    boolean checkIfHexDoesNotOccupyPosition(int colPos, int rowPos) {
        if (gameBoardPositionArray[colPos][rowPos] == null) {
            return true;
        } else {
            return false;
        }
    }

    void setEvenFlippedCoordinatesAndUpdateGameBoard(int colPos, int rowPos, Tile tileToBePlaced) {
        setHexACoordinateAndUpdateGameBoard(colPos, rowPos, tileToBePlaced);
        setHexBCoordinateAndUpdateGameBoard(colPos - 1, rowPos + 1, tileToBePlaced);
        setHexCCoordinateAndUpdateGameBoard(colPos, rowPos + 1, tileToBePlaced);
    }

    void setOddFlippedCoordinatesAndUpdateGameBoard(int colPos, int rowPos, Tile tileToBePlaced) {
        setHexACoordinateAndUpdateGameBoard(colPos, rowPos, tileToBePlaced);
        setHexBCoordinateAndUpdateGameBoard(colPos, rowPos + 1, tileToBePlaced);
        setHexCCoordinateAndUpdateGameBoard(colPos + 1, rowPos + 1, tileToBePlaced);
    }

    void setEvenNotFlippedCoordinatesAndUpdateGameBoard(int colPos, int rowPos, Tile tileToBePlaced) {
        setHexACoordinateAndUpdateGameBoard(colPos, rowPos, tileToBePlaced);
        setHexBCoordinateAndUpdateGameBoard(colPos - 1, rowPos - 1, tileToBePlaced);
        setHexCCoordinateAndUpdateGameBoard(colPos, rowPos - 1, tileToBePlaced);
    }

    void setOddNotFlippedCoordinatesAndUpdateGameBoard(int colPos, int rowPos, Tile tileToBePlaced) {
        setHexACoordinateAndUpdateGameBoard(colPos, rowPos, tileToBePlaced);
        setHexBCoordinateAndUpdateGameBoard(colPos, rowPos - 1, tileToBePlaced);
        setHexCCoordinateAndUpdateGameBoard(colPos + 1, rowPos - 1, tileToBePlaced);
    }

    void setHexACoordinateAndUpdateGameBoard(int colPos, int rowPos, Tile tileToBePlaced) {
        tileToBePlaced.getHexA().getHexCoordinate().setCoordinates(colPos, rowPos);
        this.gameBoardPositionArray[colPos][rowPos] = tileToBePlaced.getHexA();
    }

    void setHexBCoordinateAndUpdateGameBoard(int colPos, int rowPos, Tile tileToBePlaced) {
        tileToBePlaced.getHexB().getHexCoordinate().setCoordinates(colPos, rowPos);
        this.gameBoardPositionArray[colPos][rowPos] = tileToBePlaced.getHexB();
    }

    void setHexCCoordinateAndUpdateGameBoard(int colPos, int rowPos, Tile tileToBePlaced) {
        tileToBePlaced.getHexC().getHexCoordinate().setCoordinates(colPos, rowPos);
        this.gameBoardPositionArray[colPos][rowPos] = tileToBePlaced.getHexC();
    }

    void updateValidTilePlacementList(Tile tileThatWasPlaced) {
        if (tileThatWasPlaced.isFlipped()) {
            setValidFlippedPositions(tileThatWasPlaced);
        } else {
            setValidNotFlippedPositions(tileThatWasPlaced);
        }
        setBoardPositionAsOccupied(tileThatWasPlaced);
    }

    void setValidFlippedPositions(Tile tileThatWasPlaced) {
        if (boardPositionRelativeToHomeHexIsEmpty(1, 2, tileThatWasPlaced))
            setBoardPositionRelativeToHomeHexAsValid(1, 2, tileThatWasPlaced);
        if (boardPositionRelativeToHomeHexIsEmpty(0, 2, tileThatWasPlaced)) ;
        setBoardPositionRelativeToHomeHexAsValid(0, 2, tileThatWasPlaced);
        if (boardPositionRelativeToHomeHexIsEmpty(-1, 2, tileThatWasPlaced))
            setBoardPositionRelativeToHomeHexAsValid(-1, 2, tileThatWasPlaced);
        if (boardPositionRelativeToHomeHexIsEmpty(-2, 1, tileThatWasPlaced))
            setBoardPositionRelativeToHomeHexAsValid(-2, 1, tileThatWasPlaced);
        if (boardPositionRelativeToHomeHexIsEmpty(-1, 0, tileThatWasPlaced))
            setBoardPositionRelativeToHomeHexAsValid(-1, 0, tileThatWasPlaced);
        if (boardPositionRelativeToHomeHexIsEmpty(-1, -1, tileThatWasPlaced))
            setBoardPositionRelativeToHomeHexAsValid(-1, -1, tileThatWasPlaced);
        if (boardPositionRelativeToHomeHexIsEmpty(0, -1, tileThatWasPlaced))
            setBoardPositionRelativeToHomeHexAsValid(0, -1, tileThatWasPlaced);
        if (boardPositionRelativeToHomeHexIsEmpty(1, 0, tileThatWasPlaced))
            setBoardPositionRelativeToHomeHexAsValid(1, 0, tileThatWasPlaced);
        if (boardPositionRelativeToHomeHexIsEmpty(1, 1, tileThatWasPlaced))
            setBoardPositionRelativeToHomeHexAsValid(1, 1, tileThatWasPlaced);
    }

    void setValidNotFlippedPositions(Tile tileThatWasPlaced) {
        if (boardPositionRelativeToHomeHexIsEmpty(0, 1, tileThatWasPlaced))
            setBoardPositionRelativeToHomeHexAsValid(0, 1, tileThatWasPlaced);
        if (boardPositionRelativeToHomeHexIsEmpty(-1, 1, tileThatWasPlaced))
            setBoardPositionRelativeToHomeHexAsValid(-1, 1, tileThatWasPlaced);
        if (boardPositionRelativeToHomeHexIsEmpty(-1, 0, tileThatWasPlaced))
            setBoardPositionRelativeToHomeHexAsValid(-1, 0, tileThatWasPlaced);
        if (boardPositionRelativeToHomeHexIsEmpty(-2, -1, tileThatWasPlaced))
            setBoardPositionRelativeToHomeHexAsValid(-2, -1, tileThatWasPlaced);
        if (boardPositionRelativeToHomeHexIsEmpty(-1, -2, tileThatWasPlaced))
            setBoardPositionRelativeToHomeHexAsValid(-1, -2, tileThatWasPlaced);
        if (boardPositionRelativeToHomeHexIsEmpty(0, -2, tileThatWasPlaced))
            setBoardPositionRelativeToHomeHexAsValid(0, -2, tileThatWasPlaced);
        if (boardPositionRelativeToHomeHexIsEmpty(1, -2, tileThatWasPlaced))
            setBoardPositionRelativeToHomeHexAsValid(1, -2, tileThatWasPlaced);
        if (boardPositionRelativeToHomeHexIsEmpty(1, -1, tileThatWasPlaced))
            setBoardPositionRelativeToHomeHexAsValid(1, -1, tileThatWasPlaced);
        if (boardPositionRelativeToHomeHexIsEmpty(1, 0, tileThatWasPlaced))
            setBoardPositionRelativeToHomeHexAsValid(1, 0, tileThatWasPlaced);
    }

    boolean boardPositionRelativeToHomeHexIsEmpty(int colOffset, int rowOffset, Tile tileThatWasPlaced) {
        return validPlacementArray[accessGameBoardXValue(tileThatWasPlaced.getHexA()) + colOffset][accessGameBoardYValue(tileThatWasPlaced.getHexA()) + rowOffset] == 0;
    }

    void setBoardPositionRelativeToHomeHexAsValid(int colOffset, int rowOffset, Tile tileThatWasPlaced) {
        validPlacementArray[accessGameBoardXValue(tileThatWasPlaced.getHexA()) + colOffset][accessGameBoardYValue(tileThatWasPlaced.getHexA()) + rowOffset] = 1;
    }

    void setBoardPositionAsOccupied(Tile tileThatWasPlaced) {
        validPlacementArray[accessGameBoardXValue(tileThatWasPlaced.getHexA())][accessGameBoardYValue(tileThatWasPlaced.getHexA())] = -1;
        validPlacementArray[accessGameBoardXValue(tileThatWasPlaced.getHexB())][accessGameBoardYValue(tileThatWasPlaced.getHexB())] = -1;
        validPlacementArray[accessGameBoardXValue(tileThatWasPlaced.getHexC())][accessGameBoardYValue(tileThatWasPlaced.getHexC())] = -1;
    }

    int accessGameBoardXValue(Hex hexToCheck) {
        return hexToCheck.getHexCoordinate().getColumnPosition();
    }

    int accessGameBoardYValue(Hex hexToCheck) {
        return hexToCheck.getHexCoordinate().getRowPosition();
    }

    void incrementGameBoardTileID() {
        this.gameBoardTileID += 1;
    }

    void incrementGameBoardHexID(int hexAmount) {
        this.gameBoardHexID += hexAmount;
    }



    boolean checkIfTileBeingPlacedWillBeAdjacent(int colPos, int rowPos, Tile tileBeingPlaced) {
        if (tileBeingPlaced.isFlipped()) {
            if (validPlacementArray[colPos + 1][rowPos + 2] == 1) {
                return true;
            } else if (validPlacementArray[colPos][rowPos + 2] == 1) {
                return true;
            } else if (validPlacementArray[colPos - 1][rowPos + 2] == 1) {
                return true;
            } else if (validPlacementArray[colPos - 2][rowPos + 1] == 1) {
                return true;
            } else if (validPlacementArray[colPos - 1][rowPos] == 1) {
                return true;
            } else if (validPlacementArray[colPos - 1][rowPos - 1] == 1) {
                return true;
            } else if (validPlacementArray[colPos][rowPos - 1] == 1) {
                return true;
            } else if (validPlacementArray[colPos + 1][rowPos] == 1) {
                return true;
            } else if (validPlacementArray[colPos + 1][rowPos + 1] == 1) {
                return true;
            }
        } else {
            if (validPlacementArray[colPos][rowPos + 1] == 1) {
                return true;
            } else if (validPlacementArray[colPos - 1][rowPos + 1] == 1) {
                return true;
            } else if (validPlacementArray[colPos - 1][rowPos] == 1) {
                return true;
            } else if (validPlacementArray[colPos - 2][rowPos - 1] == 1) {
                return true;
            } else if (validPlacementArray[colPos - 1][rowPos - 2] == 1) {
                return true;
            } else if (validPlacementArray[colPos][rowPos - 2] == 1) {
                return true;
            } else if (validPlacementArray[colPos + 1][rowPos - 2] == 1) {
                return true;
            } else if (validPlacementArray[colPos + 1][rowPos - 1] == 1) {
                return true;
            } else if (validPlacementArray[colPos + 1][rowPos] == 1) {
                return true;
            }
        }
        return false;
    }



    void buildSettlement(int colPos, int rowPos, Player playerBuilding) {
        if (isValidSettlementLocation(colPos, rowPos) && playerBuilding.getVillagerCount() >= 1) {
            gameBoardPositionArray[colPos][rowPos].setSettlerCount(1);

            int newSettlementID = getNewestAssignableSettlementID();
            gameBoardPositionArray[colPos][rowPos].setSettlementID(newSettlementID);

            assignPlayerNewSettlementInList(playerBuilding, newSettlementID, 1);
            addHexWithSettlementAdjacentToNukeToHexesBuiltOnList(colPos, rowPos);

            gameBoardPositionArray[colPos][rowPos].setPlayerID(playerBuilding.getPlayerID());
            playerBuilding.increaseSettlementCount();
            playerBuilding.decreaseVillagerCount(1);
            playerBuilding.increaseScore(1);
        }
    }

    boolean isValidSettlementLocation(int colPos, int rowPos) {
        try {
            if (isNotBuiltOn(colPos, rowPos)) {
                if (isOnLevelOne(colPos, rowPos)) {
                    if (isHabitable(colPos, rowPos)) {
                        return true;
                    }
                }
            }
        }catch(Exception e) {return false;}
        return false;
    }

    boolean isNotBuiltOn(int colPos, int rowPos) {
        if (gameBoardPositionArray[colPos][rowPos].isNotBuiltOn()) {
            return true;
        } else {
            return false;
        }
    }

    boolean isOnLevelOne(int colPos, int rowPos) {
        if (gameBoardPositionArray[colPos][rowPos].getHexLevel() == 1) {
            return true;
        } else {
            return false;
        }
    }

    boolean isHabitable(int colPos, int rowPos) {
        if (gameBoardPositionArray[colPos][rowPos].getHexTerrainType().getHabitablity() == 1) {
            return true;
        } else {
            return false;
        }
    }

    void assignPlayerNewSettlementInList(Player owningPlayer, int settlementID, int settlementSize) {
        this.gameBoardSettlementList[settlementID][0] = owningPlayer.getPlayerID();
        setPlayerOwnedSettlementsListIsOwned(settlementID, owningPlayer.getPlayerID());
        assignSizeToGameBoardSettlementList(settlementID, settlementSize);
    }

    void setPlayerOwnedSettlementsListIsOwned(int settlementID, int playerID) {
        this.playerOwnedSettlementsList[settlementID][playerID-1] = true;
    }

    void assignSizeToGameBoardSettlementList(int settlementID, int settlementSize) {
        this.gameBoardSettlementList[settlementID][1] = settlementSize;
    }

    void addHexWithSettlementAdjacentToNukeToHexesBuiltOnList(int colPos, int rowPos) {
        Pair lastHexSettledOn = new Pair(colPos, rowPos);
        hexesBuiltOnThisTurn.addElement(lastHexSettledOn);
    }




    void placeTotoroSanctuary(int colPos, int rowPos, int settlementID, Player playerBuilding) {
        if(checkIfValidTotoroPlacement(colPos, rowPos, settlementID, playerBuilding)) {
            incrementGameBoardSettlementListTotoroCount(settlementID);
            gameBoardPositionArray[colPos][rowPos].setTotoroCount(1);
            playerBuilding.increaseScore(200);
            incrementGameBoardSettlementListSize(settlementID);
            playerBuilding.decreaseTotoroCount();
        }
    }

    boolean checkIfValidTotoroPlacement(int colPos, int rowPos, int settlementID, Player playerBuilding) {
        try {
            if(gameBoardPositionArray[colPos][rowPos].getHexTerrainType() == terrainTypes.VOLCANO) {
                return false;
            }
            if(isNotBuiltOn(colPos, rowPos) == false) {
                return false;
            }
        } catch (NullPointerException e) {
            return false; // false for null hexes
        }
        if(getGameBoardSettlementListTotoroCount(settlementID) != 0) {
            return false;
        }
        if(getGameBoardSettlementListOwner(settlementID) != playerBuilding.getPlayerID()) {
            return false;
        }
        if(getGameBoardSettlementListSettlementSize(settlementID) < 5) {
            return false;
        }
        if(playerBuilding.getTotoroCount() == 0) {
            return false;
        }

        if(checkIfEven(rowPos)) {
            if(hexToPlaceSpecialPieceOnEvenRowIsNotAdjacentToSpecifiedSettlement(colPos, rowPos, settlementID)) {
                return false;
            }
        }
        else {
            if(hexToPlaceSpecialPieceOnOddRowIsNotAdjacentToSpecifiedSettlement(colPos, rowPos, settlementID)) {
                return false;
            }
        }
        return true;
    }

    int getGameBoardSettlementListTotoroCount(int settlementID) {
        return this.gameBoardSettlementList[settlementID][2];
    }

    boolean hexToPlaceSpecialPieceOnEvenRowIsNotAdjacentToSpecifiedSettlement(int colPos, int rowPos, int settlementID) {
        return !(adjacentHexToTotoroIsNotNullAndIsCorrectSettlementID(colPos-1, rowPos-1, settlementID)
                || adjacentHexToTotoroIsNotNullAndIsCorrectSettlementID(colPos, rowPos-1, settlementID)
                || adjacentHexToTotoroIsNotNullAndIsCorrectSettlementID(colPos+1, rowPos, settlementID)
                || adjacentHexToTotoroIsNotNullAndIsCorrectSettlementID(colPos, rowPos+1, settlementID)
                || adjacentHexToTotoroIsNotNullAndIsCorrectSettlementID(colPos-1, rowPos+1, settlementID)
                || adjacentHexToTotoroIsNotNullAndIsCorrectSettlementID(colPos-1, rowPos, settlementID));
    }

    boolean adjacentHexToTotoroIsNotNullAndIsCorrectSettlementID(int colPos, int rowPos, int settlementID) {
        try {
            if (getGameBoardPositionSettlementID(colPos, rowPos) != settlementID) {
                return false;
            }
        } catch (NullPointerException e) {
            return false; // false for null hexes
        }
        return true;
    }

    boolean hexToPlaceSpecialPieceOnOddRowIsNotAdjacentToSpecifiedSettlement(int colPos, int rowPos, int settlementID) {
        return !(adjacentHexToTotoroIsNotNullAndIsCorrectSettlementID(colPos, rowPos-1, settlementID)
                || adjacentHexToTotoroIsNotNullAndIsCorrectSettlementID(colPos+1, rowPos-1, settlementID)
                || adjacentHexToTotoroIsNotNullAndIsCorrectSettlementID(colPos+1, rowPos, settlementID)
                || adjacentHexToTotoroIsNotNullAndIsCorrectSettlementID(colPos+1, rowPos+1, settlementID)
                || adjacentHexToTotoroIsNotNullAndIsCorrectSettlementID(colPos, rowPos+1, settlementID)
                || adjacentHexToTotoroIsNotNullAndIsCorrectSettlementID(colPos-1, rowPos, settlementID));
    }

    void incrementGameBoardSettlementListTotoroCount(int settlementID) {
        this.gameBoardSettlementList[settlementID][2] += 1;
    }



    int findAdjacentSettlementWithoutTotoro(int rowPos, int colPos){
        if(checkIfEven(rowPos)){
            int sID1 = getGameBoardPositionSettlementID(rowPos-1, rowPos-1);
            int sID2 = getGameBoardPositionSettlementID(rowPos, rowPos-1);
            int sID3 = getGameBoardPositionSettlementID(rowPos-1, rowPos);
            int sID4 = getGameBoardPositionSettlementID(rowPos+1, rowPos);
            int sID5 = getGameBoardPositionSettlementID(rowPos-1, rowPos+1);
            int sID6 = getGameBoardPositionSettlementID(rowPos, rowPos+1);
            if(gameBoardSettlementList[sID1][2]==0){return sID1;}
            if(gameBoardSettlementList[sID2][2]==0){return sID2;}
            if(gameBoardSettlementList[sID3][2]==0){return sID3;}
            if(gameBoardSettlementList[sID4][2]==0){return sID4;}
            if(gameBoardSettlementList[sID5][2]==0){return sID5;}
            if(gameBoardSettlementList[sID6][2]==0){return sID6;}

        }else{
            int sID1 = getGameBoardPositionSettlementID(rowPos, rowPos-1);
            int sID2 = getGameBoardPositionSettlementID(rowPos+1, rowPos-1);
            int sID3 = getGameBoardPositionSettlementID(rowPos-1, rowPos);
            int sID4 = getGameBoardPositionSettlementID(rowPos+1, rowPos);
            int sID5 = getGameBoardPositionSettlementID(rowPos, rowPos+1);
            int sID6 = getGameBoardPositionSettlementID(rowPos+1, rowPos+1);
            if(gameBoardSettlementList[sID1][2]==0){return sID1;}
            if(gameBoardSettlementList[sID2][2]==0){return sID2;}
            if(gameBoardSettlementList[sID3][2]==0){return sID3;}
            if(gameBoardSettlementList[sID4][2]==0){return sID4;}
            if(gameBoardSettlementList[sID5][2]==0){return sID5;}
            if(gameBoardSettlementList[sID6][2]==0){return sID6;}
        }
        return -1;
    }

    int findAdjacentSettlementWithoutTiger(int rowPos, int colPos){
        if(checkIfEven(rowPos)){
            int sID1 = getGameBoardPositionSettlementID(rowPos-1, rowPos-1);
            int sID2 = getGameBoardPositionSettlementID(rowPos, rowPos-1);
            int sID3 = getGameBoardPositionSettlementID(rowPos-1, rowPos);
            int sID4 = getGameBoardPositionSettlementID(rowPos+1, rowPos);
            int sID5 = getGameBoardPositionSettlementID(rowPos-1, rowPos+1);
            int sID6 = getGameBoardPositionSettlementID(rowPos, rowPos+1);
            if(gameBoardSettlementList[sID1][3]==0){return sID1;}
            if(gameBoardSettlementList[sID2][3]==0){return sID2;}
            if(gameBoardSettlementList[sID3][3]==0){return sID3;}
            if(gameBoardSettlementList[sID4][3]==0){return sID4;}
            if(gameBoardSettlementList[sID5][3]==0){return sID5;}
            if(gameBoardSettlementList[sID6][3]==0){return sID6;}

        }else{
            int sID1 = getGameBoardPositionSettlementID(rowPos, rowPos-1);
            int sID2 = getGameBoardPositionSettlementID(rowPos+1, rowPos-1);
            int sID3 = getGameBoardPositionSettlementID(rowPos-1, rowPos);
            int sID4 = getGameBoardPositionSettlementID(rowPos+1, rowPos);
            int sID5 = getGameBoardPositionSettlementID(rowPos, rowPos+1);
            int sID6 = getGameBoardPositionSettlementID(rowPos+1, rowPos+1);
            if(gameBoardSettlementList[sID1][3]==0){return sID1;}
            if(gameBoardSettlementList[sID2][3]==0){return sID2;}
            if(gameBoardSettlementList[sID3][3]==0){return sID3;}
            if(gameBoardSettlementList[sID4][3]==0){return sID4;}
            if(gameBoardSettlementList[sID5][3]==0){return sID5;}
            if(gameBoardSettlementList[sID6][3]==0){return sID6;}
        }
        return -1;
    }



    void placeTigerPen(int colPos, int rowPos, int settlementID, Player playerBuilding) {
        if(checkIfValidTigerPlacement(colPos, rowPos, settlementID, playerBuilding)) {
            incrementGameBoardSettlementListTigerCount(settlementID);
            gameBoardPositionArray[colPos][rowPos].setTigerCount(1);
            playerBuilding.increaseScore(75);
            incrementGameBoardSettlementListSize(settlementID);
            playerBuilding.decreaseTigerCount();
        }
    }

    boolean checkIfValidTigerPlacement(int colPos, int rowPos, int settlementID, Player playerBuilding) {
        try {
            if(gameBoardPositionArray[colPos][rowPos].getHexTerrainType() == terrainTypes.VOLCANO) {
                return false;
            }
            if(isNotBuiltOn(colPos, rowPos) == false) {
                return false;
            }
            if(gameBoardPositionArray[colPos][rowPos].getHexLevel() < 3) {
                return false;
            }
        } catch (NullPointerException e) {
            return false; // false for null hexes
        }
        if(getGameBoardSettlementListTigerCount(settlementID) != 0) {
            return false;
        }
        if(getGameBoardSettlementListOwner(settlementID) != playerBuilding.getPlayerID()) {
            return false;
        }
        if(playerBuilding.getTigerCount() == 0) {
            return false;
        }

        if(checkIfEven(rowPos)) {
            if(hexToPlaceSpecialPieceOnEvenRowIsNotAdjacentToSpecifiedSettlement(colPos, rowPos, settlementID)) {
                return false;
            }
        }
        else {
            if(hexToPlaceSpecialPieceOnOddRowIsNotAdjacentToSpecifiedSettlement(colPos, rowPos, settlementID)) {
                return false;
            }
        }
        return true;
    }

    int getGameBoardSettlementListTigerCount(int settlementID) {
        return this.gameBoardSettlementList[settlementID][3];
    }

    int getGameBoardSettlementListOwner(int settlementID) {
        return this.gameBoardSettlementList[settlementID][0];
    }

    void incrementGameBoardSettlementListTigerCount(int settlementID) {
        this.gameBoardSettlementList[settlementID][3] += 1;
    }



    void expandSettlement(int colPos, int rowPos, terrainTypes expansionType, Player player) {
        int villagersNeededForExpansion = calculateVillagersForExpansion(colPos, rowPos, expansionType);


        if (villagersNeededForExpansion <= player.getVillagerCount()) {
            int score = calculateScoreForExpansion(colPos, rowPos, expansionType);
            expandSettlements(colPos, rowPos, expansionType, player, getGameBoardPositionSettlementID(colPos, rowPos));
            player.decreaseVillagerCount(villagersNeededForExpansion);
            player.increaseScore(score);
        }
    }

    int calculateVillagersForExpansion(int colPos, int rowPos, terrainTypes expansionType) {
        int returnValue;

        if (gameBoardPositionArray[colPos][rowPos].isNotBuiltOn() == false) {//is part of a settlement
            returnValue = recursiveExpansionCalculation(colPos, rowPos, expansionType);
            resetHexPlayerIDs(colPos, rowPos);
            return returnValue;
        }
        return -1;
    }

    int recursiveExpansionCalculation(int colPos, int rowPos, terrainTypes expansionType) {
        int returnVal = 0;
        if (checkIfEven(rowPos)) {
            returnVal += recursiveExpansionHelper(colPos, rowPos, expansionType, -1, -1);
            returnVal += recursiveExpansionHelper(colPos, rowPos, expansionType, 0, -1);
            returnVal += recursiveExpansionHelper(colPos, rowPos, expansionType, -1, 0);
            returnVal += recursiveExpansionHelper(colPos, rowPos, expansionType, +1, 0);
            returnVal += recursiveExpansionHelper(colPos, rowPos, expansionType, -1, +1);
            returnVal += recursiveExpansionHelper(colPos, rowPos, expansionType, 0, +1);
        } else {
            returnVal += recursiveExpansionHelper(colPos, rowPos, expansionType, 0, -1);
            returnVal += recursiveExpansionHelper(colPos, rowPos, expansionType, +1, -1);
            returnVal += recursiveExpansionHelper(colPos, rowPos, expansionType, -1, 0);
            returnVal += recursiveExpansionHelper(colPos, rowPos, expansionType, +1, 0);
            returnVal += recursiveExpansionHelper(colPos, rowPos, expansionType, 0, +1);
            returnVal += recursiveExpansionHelper(colPos, rowPos, expansionType, +1, +1);
        }
        return returnVal;
    }

    int recursiveExpansionHelper(int colPos, int rowPos, terrainTypes expansionType, int colOffset, int rowOffset) {
        try {
            if (gameBoardPositionArray[colPos + colOffset][rowPos + rowOffset].getHexTerrainType() == expansionType) {//matches
                if (gameBoardPositionArray[colPos + colOffset][rowPos + rowOffset].getPlayerID() == 0) {//has not been seen
                    gameBoardPositionArray[colPos + colOffset][rowPos + rowOffset].setPlayerID(-1); //mark as seen, add level to return val
                    return gameBoardPositionArray[colPos + colOffset][rowPos + rowOffset].getHexLevel() + recursiveExpansionCalculation(colPos + colOffset, rowPos + rowOffset, expansionType);
                }
            }
        } catch (Exception e) {
            return 0; //ignore null hexes
        }

        return 0;
    }

    void resetHexPlayerIDs(int colPos, int rowPos) {
        if (checkIfEven(rowPos)) {
            resetHexPlayerIDsHelper(colPos, rowPos, -1, -1);
            resetHexPlayerIDsHelper(colPos, rowPos, 0, -1);
            resetHexPlayerIDsHelper(colPos, rowPos, -1, 0);
            resetHexPlayerIDsHelper(colPos, rowPos, +1, 0);
            resetHexPlayerIDsHelper(colPos, rowPos, -1, +1);
            resetHexPlayerIDsHelper(colPos, rowPos, 0, +1);
        } else {
            resetHexPlayerIDsHelper(colPos, rowPos, 0, -1);
            resetHexPlayerIDsHelper(colPos, rowPos, +1, -1);
            resetHexPlayerIDsHelper(colPos, rowPos, -1, 0);
            resetHexPlayerIDsHelper(colPos, rowPos, +1, 0);
            resetHexPlayerIDsHelper(colPos, rowPos, 0, +1);
            resetHexPlayerIDsHelper(colPos, rowPos, +1, +1);
        }
    }

    void resetHexPlayerIDsHelper(int colPos, int rowPos, int colOffset, int rowOffset) {
        try {
            if (gameBoardPositionArray[colPos + colOffset][rowPos + rowOffset].getPlayerID() == -1) {//has been seen
                gameBoardPositionArray[colPos + colOffset][rowPos + rowOffset].setPlayerID(0); //marks as unseen
                resetHexPlayerIDs(colPos + colOffset, rowPos + rowOffset);
            }
        } catch (Exception e) {

        }
    }

    void expandSettlements(int colPos, int rowPos, terrainTypes expansionType, Player player, int homeHexSettlementID) {
        if (checkIfEven(rowPos)) {
            expandSettlementsHelper(colPos, rowPos, expansionType, player, -1, -1, homeHexSettlementID);
            expandSettlementsHelper(colPos, rowPos, expansionType, player, 0, -1, homeHexSettlementID);
            expandSettlementsHelper(colPos, rowPos, expansionType, player, -1, 0, homeHexSettlementID);
            expandSettlementsHelper(colPos, rowPos, expansionType, player, +1, 0, homeHexSettlementID);
            expandSettlementsHelper(colPos, rowPos, expansionType, player, -1, +1, homeHexSettlementID);
            expandSettlementsHelper(colPos, rowPos, expansionType, player, 0, +1, homeHexSettlementID);
        } else {
            expandSettlementsHelper(colPos, rowPos, expansionType, player, 0, -1, homeHexSettlementID);
            expandSettlementsHelper(colPos, rowPos, expansionType, player, +1, -1, homeHexSettlementID);
            expandSettlementsHelper(colPos, rowPos, expansionType, player, -1, 0, homeHexSettlementID);
            expandSettlementsHelper(colPos, rowPos, expansionType, player, +1, 0, homeHexSettlementID);
            expandSettlementsHelper(colPos, rowPos, expansionType, player, 0, +1, homeHexSettlementID);
            expandSettlementsHelper(colPos, rowPos, expansionType, player, +1, +1, homeHexSettlementID);
        }

    }

    void expandSettlementsHelper(int colPos, int rowPos, terrainTypes expansionType, Player player, int colOffset, int rowOffset, int homeHexSettlementID) {
        try {
            if (gameBoardPositionArray[colPos + colOffset][rowPos + rowOffset].getHexTerrainType() == expansionType) {//matches
                if (gameBoardPositionArray[colPos + colOffset][rowPos + rowOffset].getPlayerID() == 0) {//has not been seen
                    gameBoardPositionArray[colPos + colOffset][rowPos + rowOffset].setPlayerID(player.getPlayerID());
                    gameBoardPositionArray[colPos + colOffset][rowPos + rowOffset].setSettlerCount(gameBoardPositionArray[colPos + colOffset][rowPos + rowOffset].getHexLevel());
                    gameBoardPositionArray[colPos + colOffset][rowPos + rowOffset].setSettlementID(homeHexSettlementID);
                    incrementGameBoardSettlementListSize(homeHexSettlementID);

                    addHexWithSettlementAdjacentToNukeToHexesBuiltOnList(colPos + colOffset, rowPos + rowOffset);

                    expandSettlements(colPos + colOffset, rowPos + rowOffset, expansionType, player, homeHexSettlementID);
                }
            }
        } catch (Exception e) {

        }
    }

    void incrementGameBoardSettlementListSize(int settlementID) {
        this.gameBoardSettlementList[settlementID][1] += 1;
    }

    int calculateScoreForExpansion(int colPos, int rowPos, terrainTypes expansionType) {
        int returnValue;

        if (gameBoardPositionArray[colPos][rowPos].isNotBuiltOn() == false) {//is part of a settlement
            returnValue = expansionScore(colPos, rowPos, expansionType);
            resetHexPlayerIDs(colPos, rowPos);
            return returnValue;
        }
        return -1;
    }

    int expansionScore(int colPos, int rowPos, terrainTypes expansionType) {
        int returnVal = 0;
        if (checkIfEven(rowPos)) {
            returnVal += expansionScoreHelper(colPos, rowPos, expansionType, -1, -1);
            returnVal += expansionScoreHelper(colPos, rowPos, expansionType, 0, -1);
            returnVal += expansionScoreHelper(colPos, rowPos, expansionType, -1, 0);
            returnVal += expansionScoreHelper(colPos, rowPos, expansionType, +1, 0);
            returnVal += expansionScoreHelper(colPos, rowPos, expansionType, -1, +1);
            returnVal += expansionScoreHelper(colPos, rowPos, expansionType, 0, +1);
        } else {
            returnVal += expansionScoreHelper(colPos, rowPos, expansionType, 0, -1);
            returnVal += expansionScoreHelper(colPos, rowPos, expansionType, +1, -1);
            returnVal += expansionScoreHelper(colPos, rowPos, expansionType, -1, 0);
            returnVal += expansionScoreHelper(colPos, rowPos, expansionType, +1, 0);
            returnVal += expansionScoreHelper(colPos, rowPos, expansionType, 0, +1);
            returnVal += expansionScoreHelper(colPos, rowPos, expansionType, +1, +1);
        }
        return returnVal;
    }

    int expansionScoreHelper(int colPos, int rowPos, terrainTypes expansionType, int colOffset, int rowOffset) {
        try {
            if (gameBoardPositionArray[colPos + colOffset][rowPos + rowOffset].getHexTerrainType() == expansionType) {//matches
                if (gameBoardPositionArray[colPos + colOffset][rowPos + rowOffset].getPlayerID() == 0) {//has not been seen
                    gameBoardPositionArray[colPos + colOffset][rowPos + rowOffset].setPlayerID(-1); //mark as seen, add level to return val
                    int returnValue = gameBoardPositionArray[colPos + colOffset][rowPos + rowOffset].getHexLevel();
                    returnValue *= returnValue;
                    returnValue += expansionScore(colPos + colOffset, rowPos + rowOffset, expansionType);
                    return returnValue;
                }
            }
        } catch (NullPointerException e) {
            return 0; //ignore null hexes
        }

        return 0;
    }



    boolean isMySettlement(int colPos, int rowPos, Player player){
        int settlementID = getGameBoardPositionSettlementID(colPos, rowPos);

        if(gameBoardSettlementList[settlementID][0] == player.getPlayerID()) {
            return true;
        }
        return false;
    }



    void mergeSettlements() {
        if(hexesBuiltOnThisTurn.isEmpty()) {
            resetTraversalList();
            return;
        }

        Pair currentCoordinates = hexesBuiltOnThisTurn.lastElement();
        hexesBuiltOnThisTurn.remove(hexesBuiltOnThisTurn.size()-1);
        markGameBoardPositionAsTraversed(currentCoordinates);
        int playerID = getGameBoardPositionPlayerID(currentCoordinates);

        hexesToResetTraversalValue.add(currentCoordinates);

        int masterSettlementID = getGameBoardPositionSettlementID(currentCoordinates);

        recursivelyAddAdjacentHexesToNewlyMergeSettlement(currentCoordinates, playerID, masterSettlementID);

        mergeSettlements();
    }

    void recursivelyAddAdjacentHexesToNewlyMergeSettlement(Pair currentCoordinates, int playerID, int masterSettlementID) {
        if(checkIfEven(currentCoordinates.getRowPosition())) {
            recursivelyAddAdjacentHexesToNewlyMergeSettlementForEvenCoordinates(currentCoordinates, playerID, masterSettlementID);
        }
        else {
            recursivelyAddAdjacentHexesToNewlyMergeSettlementForOddCoordinates(masterSettlementID, playerID, currentCoordinates);
        }
    }

    void recursivelyAddAdjacentHexesToNewlyMergeSettlementForEvenCoordinates(Pair currentCoordinates, int playerID, int masterSettlementID) {
        mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()-1, currentCoordinates.getRowPosition()-1);
        mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition(), currentCoordinates.getRowPosition()-1);
        mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()+1, currentCoordinates.getRowPosition());
        mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition(), currentCoordinates.getRowPosition()+1);
        mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()-1, currentCoordinates.getRowPosition()+1);
        mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()-1, currentCoordinates.getRowPosition());
    }

    void recursivelyAddAdjacentHexesToNewlyMergeSettlementForOddCoordinates(int masterSettlementID, int playerID, Pair currentCoordinates) {
        mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition(), currentCoordinates.getRowPosition()-1);
        mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()+1, currentCoordinates.getRowPosition()-1);
        mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()+1, currentCoordinates.getRowPosition());
        mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()+1, currentCoordinates.getRowPosition()+1);
        mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition(), currentCoordinates.getRowPosition()+1);
        mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()-1, currentCoordinates.getRowPosition());
    }


    //TODO: James will refactor on Thursday
    void mergeSettlementsDriver(int masterSettlementID, int playerID, int colPos, int rowPos) {
        try { // is not null
            if(gameBoardPositionHasNotBeenCheckedYet(colPos, rowPos)) {
                if (gameBoardPositionOwnedByPlayer(playerID, colPos, rowPos)) {
                    markGameBoardPositionAsTraversed(colPos, rowPos);

                    Pair currentCoordinates = new Pair(colPos, rowPos);
                    hexesToResetTraversalValue.add(currentCoordinates);

                    try {
                        markCurrentHexForRemovalFromBuiltOnThisTurnList(currentCoordinates);
                        deleteFromHexesBuiltOnThisTurn();
                    }
                    catch (NullPointerException e) {}

                    addHexToNewlyMergedSettlement(masterSettlementID, playerID, colPos, rowPos);
                    recursivelyAddAdjacentHexesToNewlyMergeSettlement(currentCoordinates, playerID, masterSettlementID);
                }
            }
        }
        catch(NullPointerException e) {return;} // end search if we reach null hex

        return;
    }

    void addHexToNewlyMergedSettlement(int masterSettlementID, int playerID, int colPos, int rowPos) {
        if(getGameBoardPositionSettlementID(colPos, rowPos) != masterSettlementID) { // if we are not at a part of the master settlement, we need to decrement the values of the settlement currently
            // here, increment the necessary values for the master settlement and update the hex settlementID
            if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(colPos, rowPos)) == 1) { // if the settlement we are currently merging is of size 1,
                // delete that settlement from player list and game list
                deleteSettlementFromGame(getGameBoardPositionSettlementID(colPos, rowPos), playerID); // delete settlement from game
            } else { // else just decrement values in list of that settlement for size/totoro/tiger pen
                if (thereIsASettlerAt(colPos, rowPos)) {
                    decrementGameBoardSettlementListSize(getGameBoardPositionSettlementID(colPos, rowPos));
                } else if (thereIsATotoroAt(colPos, rowPos)) {
                    decrementGameBoardSettlementListSize(masterSettlementID);
                    decrementGameBoardSettlementListSize(masterSettlementID);
                } else if (thereIsATigerAt(colPos, rowPos)) {
                    decrementGameBoardSettlementListSize(masterSettlementID);
                    decrementGameBoardSettlementListSize(masterSettlementID);
                }
            }

            gameBoardPositionArray[colPos][rowPos].setSettlementID(masterSettlementID); // set hex settlement ID to new ID

            if (thereIsASettlerAt(colPos, rowPos)) { // update newly acquired hex with master settlement values and vice versa
                incrementGameBoardSettlementListSize(masterSettlementID);
            } else if (thereIsATotoroAt(colPos, rowPos)) {
                incrementGameBoardSettlementListSize(masterSettlementID);
                incrementGameBoardSettlementListTotoroCount(masterSettlementID);
            } else if (thereIsATigerAt(colPos, rowPos)) {
                incrementGameBoardSettlementListSize(masterSettlementID);
                incrementGameBoardSettlementListTigerCount(masterSettlementID);
            }
        }
    }

    void deleteFromHexesBuiltOnThisTurn() {
        Predicate<Pair> pairPredicate = pair -> pair.getColumnPosition() == -1 && pair.getRowPosition() == -1; // if pair was marked for deletion, delete it
        hexesBuiltOnThisTurn.removeIf(pairPredicate);
    }

    boolean gameBoardPositionOwnedByPlayer(int playerID, int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getPlayerID() == playerID;
    }

    int getGameBoardSettlementListSettlementSize(int settlementID) {
        return this.gameBoardSettlementList[settlementID][1];
    }

    int getGameBoardPositionSettlementID(Pair currentCoordinates) {
        return getGameBoardPositionSettlementID(currentCoordinates.getColumnPosition(), currentCoordinates.getRowPosition());
    }

    int getGameBoardPositionPlayerID(Pair currentCoordinates) {
        return gameBoardPositionArray[currentCoordinates.getColumnPosition()][currentCoordinates.getRowPosition()].getPlayerID();
    }



    int getGameBoardTileID() {
        return gameBoardTileID;
    }

    int getGameBoardHexID() {
        return gameBoardHexID;
    }

    Hex[][] getGameBoardPositionArray() {
        return gameBoardPositionArray;
    }

    int[][] getValidPlacementArray() {
        return validPlacementArray;
    }

    void setGameBoardSettlementListTotoroCount(int settlementID, int totoroCount) {
        this.gameBoardSettlementList[settlementID][2] = totoroCount;
    }

    void setGameBoardSettlementListTigerCount(int settlementID, int tigerCount) {
        this.gameBoardSettlementList[settlementID][3] = tigerCount;
    }

    boolean areFiveTotorosInALine(int colPos, int rowPos,int playerID){
        for(int i = 1; i<=5;i++){
            if(gameBoardPositionArray[colPos-1][rowPos].getPlayerID()!=playerID){ //owned by player
                if(gameBoardPositionArray[colPos-i][rowPos].getSettlerCount() < 1){ //has a settler
                    return false;
                }
            }
        }
        return true;
    } // when would 5 totoros be in a line?

    boolean playerOwnsSettlementWithID(int settlementID, int playerID) {
        return this.playerOwnedSettlementsList[settlementID][playerID-1];
    }
}
