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

    private int[][] validPlacementArray = new int[boardHeight][boardWidth];
    private Hex[][] gameBoardPositionArray = new Hex[boardHeight][boardWidth];

    private int[][] gameBoardSettlementList = new int[256][4];
    private int[] usedSettlementIDs = new int[256]; // Note: Settlement ID of 0 denotes a hex with no settlement on it - never use this ID
    private boolean[][] playerOwnedSettlementsList = new boolean[256][2]; // Note: Settlement ID of 0 denotes a hex with no settlement on it - never use this ID

    private Vector<Pair> hexesBuiltOnThisTurn = new Vector();
    private Vector<Pair> hexesToResetTraversalValue = new Vector();

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



    void setTileAtPosition(int colPos, int rowPos, Tile tileToBePlaced) {
        if (hexesToPlaceTileOnAreAlreadyOccupied(colPos, rowPos, tileToBePlaced))
            return;

        if (tileIsEvenAndFlipped(rowPos, tileToBePlaced) && thereAreNoHexesUnderEvenAndFlippedTile(colPos, rowPos))
            setEvenFlippedCoordinatesAndUpdateGameBoard(colPos, rowPos, tileToBePlaced);
        if (tileIsOddAndFlipped(rowPos, tileToBePlaced) && thereAreNoHexesUnderOddAndFlippedTile(colPos, rowPos))
            setOddFlippedCoordinatesAndUpdateGameBoard(colPos, rowPos, tileToBePlaced);
        if (tileIsEvenAndNotFlipped(rowPos, tileToBePlaced) && thereAreNoHexesUnderEvenAndNotFlippedTile(colPos, rowPos))
            setEvenNotFlippedCoordinatesAndUpdateGameBoard(colPos, rowPos, tileToBePlaced);
        if (tileIsOddAndNotFlipped(rowPos, tileToBePlaced) && thereAreNoHexesUnderOddAndNotFlippedTile(colPos, rowPos))
            setOddNotFlippedCoordinatesAndUpdateGameBoard(colPos, rowPos, tileToBePlaced);

        updateValidTilePlacementList(tileToBePlaced);
        incrementGameBoardTileID();
        int hexAmount = 3;
        incrementGameBoardHexID(hexAmount);
    }

    boolean hexesToPlaceTileOnAreAlreadyOccupied(int colPos, int rowPos, Tile tileToBePlaced) {
        if (tileIsEvenAndFlipped(rowPos, tileToBePlaced)) {
            if (validPlacementArray[colPos][rowPos] == -1) return true;
            if (validPlacementArray[colPos - 1][rowPos + 1] == -1) return true;
            if (validPlacementArray[colPos][rowPos + 1] == -1) return true;
        } else if (tileIsOddAndFlipped(rowPos, tileToBePlaced)) {
            if (validPlacementArray[colPos][rowPos] == -1) return true;
            if (validPlacementArray[colPos - 1][rowPos + 1] == -1) return true;
            if (validPlacementArray[colPos + 1][rowPos + 1] == -1) return true;
        } else if (tileIsEvenAndNotFlipped(rowPos, tileToBePlaced)) {
            if (validPlacementArray[colPos][rowPos] == -1) return true;
            if (validPlacementArray[colPos - 1][rowPos - 1] == -1) return true;
            if (validPlacementArray[colPos][rowPos - 1] == -1) return true;
        } else if (tileIsOddAndNotFlipped(rowPos, tileToBePlaced)) {
            if (validPlacementArray[colPos][rowPos] == -1) return true;
            if (validPlacementArray[colPos][rowPos - 1] == -1) return true;
            if (validPlacementArray[colPos + 1][rowPos - 1] == -1) return true;
        }
        return false;
    }

    boolean thereAreNoHexesUnderEvenAndFlippedTile(int colPos, int rowPos) {
        return hexDoesNotOccupyPosition(colPos, rowPos) &&
                hexDoesNotOccupyPosition(colPos - 1, rowPos + 1) &&
                hexDoesNotOccupyPosition(colPos, rowPos + 1);
    }

    boolean thereAreNoHexesUnderOddAndFlippedTile(int colPos, int rowPos) {
        return hexDoesNotOccupyPosition(colPos, rowPos) &&
                hexDoesNotOccupyPosition(colPos, rowPos + 1) &&
                hexDoesNotOccupyPosition(colPos + 1, rowPos + 1);
    }

    boolean thereAreNoHexesUnderEvenAndNotFlippedTile(int colPos, int rowPos) {
        return hexDoesNotOccupyPosition(colPos, rowPos) &&
                hexDoesNotOccupyPosition(colPos - 1, rowPos - 1) &&
                hexDoesNotOccupyPosition(colPos, rowPos - 1);
    }

    boolean thereAreNoHexesUnderOddAndNotFlippedTile(int colPos, int rowPos) {
        return hexDoesNotOccupyPosition(colPos, rowPos) &&
                hexDoesNotOccupyPosition(colPos, rowPos - 1) &&
                hexDoesNotOccupyPosition(colPos + 1, rowPos - 1);
    }

    boolean hexDoesNotOccupyPosition(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos] == null;
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
        tileToBePlaced.getHexA().getCoordinatePair().setCoordinates(colPos, rowPos);
        this.gameBoardPositionArray[colPos][rowPos] = tileToBePlaced.getHexA();
    }

    void setHexBCoordinateAndUpdateGameBoard(int colPos, int rowPos, Tile tileToBePlaced) {
        tileToBePlaced.getHexB().getCoordinatePair().setCoordinates(colPos, rowPos);
        this.gameBoardPositionArray[colPos][rowPos] = tileToBePlaced.getHexB();
    }

    void setHexCCoordinateAndUpdateGameBoard(int colPos, int rowPos, Tile tileToBePlaced) {
        tileToBePlaced.getHexC().getCoordinatePair().setCoordinates(colPos, rowPos);
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
        if (boardPositionsRelativeToHomeHexAreEmpty(1, 2, tileThatWasPlaced))
            setBoardPositionsRelativeToHomeHexAsValid(1, 2, tileThatWasPlaced);
        if (boardPositionsRelativeToHomeHexAreEmpty(0, 2, tileThatWasPlaced)) ;
        setBoardPositionsRelativeToHomeHexAsValid(0, 2, tileThatWasPlaced);
        if (boardPositionsRelativeToHomeHexAreEmpty(-1, 2, tileThatWasPlaced))
            setBoardPositionsRelativeToHomeHexAsValid(-1, 2, tileThatWasPlaced);
        if (boardPositionsRelativeToHomeHexAreEmpty(-2, 1, tileThatWasPlaced))
            setBoardPositionsRelativeToHomeHexAsValid(-2, 1, tileThatWasPlaced);
        if (boardPositionsRelativeToHomeHexAreEmpty(-1, 0, tileThatWasPlaced))
            setBoardPositionsRelativeToHomeHexAsValid(-1, 0, tileThatWasPlaced);
        if (boardPositionsRelativeToHomeHexAreEmpty(-1, -1, tileThatWasPlaced))
            setBoardPositionsRelativeToHomeHexAsValid(-1, -1, tileThatWasPlaced);
        if (boardPositionsRelativeToHomeHexAreEmpty(0, -1, tileThatWasPlaced))
            setBoardPositionsRelativeToHomeHexAsValid(0, -1, tileThatWasPlaced);
        if (boardPositionsRelativeToHomeHexAreEmpty(1, 0, tileThatWasPlaced))
            setBoardPositionsRelativeToHomeHexAsValid(1, 0, tileThatWasPlaced);
        if (boardPositionsRelativeToHomeHexAreEmpty(1, 1, tileThatWasPlaced))
            setBoardPositionsRelativeToHomeHexAsValid(1, 1, tileThatWasPlaced);
    }

    void setValidNotFlippedPositions(Tile tileThatWasPlaced) {
        if (boardPositionsRelativeToHomeHexAreEmpty(0, 1, tileThatWasPlaced))
            setBoardPositionsRelativeToHomeHexAsValid(0, 1, tileThatWasPlaced);
        if (boardPositionsRelativeToHomeHexAreEmpty(-1, 1, tileThatWasPlaced))
            setBoardPositionsRelativeToHomeHexAsValid(-1, 1, tileThatWasPlaced);
        if (boardPositionsRelativeToHomeHexAreEmpty(-1, 0, tileThatWasPlaced))
            setBoardPositionsRelativeToHomeHexAsValid(-1, 0, tileThatWasPlaced);
        if (boardPositionsRelativeToHomeHexAreEmpty(-2, -1, tileThatWasPlaced))
            setBoardPositionsRelativeToHomeHexAsValid(-2, -1, tileThatWasPlaced);
        if (boardPositionsRelativeToHomeHexAreEmpty(-1, -2, tileThatWasPlaced))
            setBoardPositionsRelativeToHomeHexAsValid(-1, -2, tileThatWasPlaced);
        if (boardPositionsRelativeToHomeHexAreEmpty(0, -2, tileThatWasPlaced))
            setBoardPositionsRelativeToHomeHexAsValid(0, -2, tileThatWasPlaced);
        if (boardPositionsRelativeToHomeHexAreEmpty(1, -2, tileThatWasPlaced))
            setBoardPositionsRelativeToHomeHexAsValid(1, -2, tileThatWasPlaced);
        if (boardPositionsRelativeToHomeHexAreEmpty(1, -1, tileThatWasPlaced))
            setBoardPositionsRelativeToHomeHexAsValid(1, -1, tileThatWasPlaced);
        if (boardPositionsRelativeToHomeHexAreEmpty(1, 0, tileThatWasPlaced))
            setBoardPositionsRelativeToHomeHexAsValid(1, 0, tileThatWasPlaced);
    }

    boolean boardPositionsRelativeToHomeHexAreEmpty(int colOffset, int rowOffset, Tile tileThatWasPlaced) {
        return validPlacementArray[accessGameBoardXValue(tileThatWasPlaced.getHexA()) + colOffset][accessGameBoardYValue(tileThatWasPlaced.getHexA()) + rowOffset] == 0;
    }

    void setBoardPositionsRelativeToHomeHexAsValid(int colOffset, int rowOffset, Tile tileThatWasPlaced) {
        validPlacementArray[accessGameBoardXValue(tileThatWasPlaced.getHexA()) + colOffset][accessGameBoardYValue(tileThatWasPlaced.getHexA()) + rowOffset] = 1;
    }

    void setBoardPositionAsOccupied(Tile tileThatWasPlaced) {
        validPlacementArray[accessGameBoardXValue(tileThatWasPlaced.getHexA())][accessGameBoardYValue(tileThatWasPlaced.getHexA())] = -1;
        validPlacementArray[accessGameBoardXValue(tileThatWasPlaced.getHexB())][accessGameBoardYValue(tileThatWasPlaced.getHexB())] = -1;
        validPlacementArray[accessGameBoardXValue(tileThatWasPlaced.getHexC())][accessGameBoardYValue(tileThatWasPlaced.getHexC())] = -1;
    }



    void nukeTiles(int colPos, int rowPos, Tile tileToBePlaced) {
        if (nukeAtPositionIsValid(colPos, rowPos, tileToBePlaced)) {
            if (tileIsEvenAndFlipped(rowPos, tileToBePlaced))
                increaseEvenFlippedTileLevelAndUpdateGameBoardPositionArray(colPos, rowPos, tileToBePlaced);
            if (tileIsOddAndFlipped(rowPos, tileToBePlaced))
                increaseOddFlippedTileLevelAndUpdateGameBoardPositionArray(colPos, rowPos, tileToBePlaced);
            if (tileIsEvenAndNotFlipped(rowPos, tileToBePlaced))
                increaseEvenNotFlippedTileLevelAndUpdateGameBoardPositionArray(colPos, rowPos, tileToBePlaced);
            if (tileIsOddAndNotFlipped(rowPos, tileToBePlaced))
                increaseOddNotFlippedTileLevelAndUpdateGameBoardPositionArray(colPos, rowPos, tileToBePlaced);
            splitSettlements();

            int hexAmount;
            incrementGameBoardHexID(3);
            incrementGameBoardTileID();
        }
    }

    boolean nukeAtPositionIsValid(int colPos, int rowPos, Tile tileToBePlaced) {
        if (tileIsEvenAndFlipped(rowPos, tileToBePlaced) && canValidlyPlaceEvenFlippedTileInLocation(colPos, rowPos)) {
            if (hexAOfNewTileIsTheVolcano(tileToBePlaced) && hexBelowIsAVolcano(colPos, rowPos))
                return noSettlementsGetCompletelyNukedForEvenFlippedTileWithVolcanoAtA(colPos, rowPos);
            if (hexBOfNewTileIsTheVolcano(tileToBePlaced) && hexBelowIsAVolcano(colPos, rowPos + 1))
                return noSettlementsGetCompletelyNukedForEvenFlippedTileWithVolcanoAtB(colPos, rowPos);
            if (hexCOfNewTileIsTheVolcano(tileToBePlaced) && hexBelowIsAVolcano(colPos - 1, rowPos + 1))
                return noSettlementsGetCompletelyNukedForEvenFlippedTileWithVolcanoAtC(colPos, rowPos);
        }

        if (tileIsOddAndFlipped(rowPos, tileToBePlaced) && canValidlyPlaceOddFlippedTileInLocation(colPos, rowPos)) {
            if (hexAOfNewTileIsTheVolcano(tileToBePlaced) && hexBelowIsAVolcano(colPos, rowPos))
                return noSettlementGetsCompletelyNukedForOddAndFlippedTileWithVolcanoAtA(colPos, rowPos);
            if (hexBOfNewTileIsTheVolcano(tileToBePlaced) && hexBelowIsAVolcano(colPos + 1, rowPos + 1))
                return noSettlementGetsCompletelyNukedForOddAndFlippedTileWithVolcanoAtB(colPos, rowPos);
            if (hexCOfNewTileIsTheVolcano(tileToBePlaced) && hexBelowIsAVolcano(colPos, rowPos + 1))
                return noSettlementGetsCompletelyNukedForOddAndFlippedTileWithVolcanoAtC(colPos, rowPos);
        }

        if (tileIsEvenAndNotFlipped(rowPos, tileToBePlaced) && canValidlyPlaceEvenNotFlippedTileInLocation(colPos, rowPos)) {
            if (hexAOfNewTileIsTheVolcano(tileToBePlaced) && hexBelowIsAVolcano(colPos, rowPos))
                return noSettlementGetsCompletelyNukedForEvenAndNotFlippedTileWithVolcanoAtA(colPos, rowPos);
            if (hexBOfNewTileIsTheVolcano(tileToBePlaced) && hexBelowIsAVolcano(colPos - 1, rowPos - 1))
                return noSettlementGetsCompletelyNukedForEvenAndNotFlippedTileWithVolcanoAtB(colPos, rowPos);
            if (hexCOfNewTileIsTheVolcano(tileToBePlaced) && hexBelowIsAVolcano(colPos, rowPos - 1))
                return noSettlementGetsCompletelyNukedForEvenAndNotFlippedTileWithVolcanoAtC(colPos, rowPos);
        }

        if (tileIsOddAndNotFlipped(rowPos, tileToBePlaced) && canValidlyPlaceOddNotFlippedTileInLocation(colPos, rowPos)) {
            if (hexAOfNewTileIsTheVolcano(tileToBePlaced) && hexBelowIsAVolcano(colPos, rowPos))
                return noSettlementGetsCompletelyNukedForOddAndNotFlippedTileWithVolcanoAtA(colPos, rowPos);
            if (hexBOfNewTileIsTheVolcano(tileToBePlaced) && hexBelowIsAVolcano(colPos, rowPos - 1))
                return noSettlementGetsCompletelyNukedForOddAndNotFlippedTileWithVolcanoAtB(colPos, rowPos);
            if (hexCOfNewTileIsTheVolcano(tileToBePlaced) && hexBelowIsAVolcano(colPos + 1, rowPos - 1))
                return noSettlementGetsCompletelyNukedForOddAndNotFlippedTileWithVolcanoAtC(colPos, rowPos);
        }
        return false;
    }

    boolean canValidlyPlaceEvenFlippedTileInLocation(int colPos, int rowPos) {
        return noEmptySpacesBelowEvenFlippedTile(colPos, rowPos) &&
                allHexesBelowEvenFlippedTileAreAtSameLevel(colPos, rowPos) &&
                notCoveringEntireEvenFlippedTile(colPos, rowPos) &&
                noTigerPenBelowEvenFlippedTile(colPos, rowPos) &&
                noTotoroBelowEvenFlippedTile(colPos, rowPos);
    }

    boolean canValidlyPlaceOddFlippedTileInLocation(int colPos, int rowPos) {
        return noEmptySpacesBelowOddFlippedTile(colPos, rowPos) &&
                allHexesBelowOddFlippedTileAreAtSameLevel(colPos, rowPos) &&
                notCoveringEntireOddFlippedTile(colPos, rowPos) &&
                noTigerPenBelowOddFlippedTile(colPos, rowPos) &&
                noTotoroBelowOddFlippedTile(colPos, rowPos);
    }

    boolean canValidlyPlaceEvenNotFlippedTileInLocation(int colPos, int rowPos) {
        return noEmptySpacesBelowEvenNotFlippedTile(colPos, rowPos) &&
                allHexesBelowEvenNotFlippedTileAreAtSameLevel(colPos, rowPos) &&
                notCoveringEntireEvenNotFlippedTile(colPos, rowPos) &&
                noTigerPenBelowEvenNotFlippedTile(colPos, rowPos) &&
                noTotoroBelowEvenNotFlippedTile(colPos, rowPos);
    }

    boolean canValidlyPlaceOddNotFlippedTileInLocation(int colPos, int rowPos) {
        return noEmptySpacesBelowOddNotFlippedTile(colPos, rowPos) &&
                allHexesBelowOddNotFlippedTileAreAtSameLevel(colPos, rowPos) &&
                notCoveringEntireOddNotFlippedTile(colPos, rowPos) &&
                noTigerPenBelowOddNotFlippedTile(colPos, rowPos) &&
                noTotoroBelowOddNotFlippedTile(colPos, rowPos);
    }

    boolean noEmptySpacesBelowEvenFlippedTile(int colPos, int rowPos) {
        return gameBoardPositionNotEmpty(colPos, rowPos)
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

    boolean allHexesBelowEvenFlippedTileAreAtSameLevel(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getLevel() == gameBoardPositionArray[colPos][rowPos + 1].getLevel()
                && gameBoardPositionArray[colPos][rowPos].getLevel() == gameBoardPositionArray[colPos - 1][rowPos + 1].getLevel()
                && gameBoardPositionArray[colPos][rowPos + 1].getLevel() == gameBoardPositionArray[colPos - 1][rowPos + 1].getLevel();
    }

    boolean allHexesBelowOddFlippedTileAreAtSameLevel(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getLevel() == gameBoardPositionArray[colPos + 1][rowPos + 1].getLevel()
                && gameBoardPositionArray[colPos][rowPos].getLevel() == gameBoardPositionArray[colPos][rowPos + 1].getLevel()
                && gameBoardPositionArray[colPos + 1][rowPos + 1].getLevel() == gameBoardPositionArray[colPos][rowPos + 1].getLevel();
    }

    boolean allHexesBelowEvenNotFlippedTileAreAtSameLevel(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getLevel() == gameBoardPositionArray[colPos - 1][rowPos - 1].getLevel()
                && gameBoardPositionArray[colPos][rowPos].getLevel() == gameBoardPositionArray[colPos][rowPos - 1].getLevel()
                && gameBoardPositionArray[colPos - 1][rowPos - 1].getLevel() == gameBoardPositionArray[colPos][rowPos - 1].getLevel();
    }

    boolean allHexesBelowOddNotFlippedTileAreAtSameLevel(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getLevel() == gameBoardPositionArray[colPos][rowPos - 1].getLevel()
                && gameBoardPositionArray[colPos][rowPos].getLevel() == gameBoardPositionArray[colPos + 1][rowPos - 1].getLevel()
                && gameBoardPositionArray[colPos][rowPos - 1].getLevel() == gameBoardPositionArray[colPos + 1][rowPos - 1].getLevel();
    }

    boolean notCoveringEntireEvenFlippedTile(int colPos, int rowPos) {
        return !(gameBoardPositionArray[colPos][rowPos].getParentTileID() == gameBoardPositionArray[colPos][rowPos + 1].getParentTileID()
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
        return gameBoardPositionArray[colPos][rowPos].getTigerCount() == 0
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
        return tileToBePlaced.getHexA().getTerrainType() == terrainTypes.VOLCANO;
    }

    boolean hexBOfNewTileIsTheVolcano(Tile tileToBePlaced) {
        if(tileToBePlaced.isFlipped())
            return tileToBePlaced.getHexC().getTerrainType() == terrainTypes.VOLCANO;
        else {
            return tileToBePlaced.getHexB().getTerrainType() == terrainTypes.VOLCANO;
        }
    }

    boolean hexCOfNewTileIsTheVolcano(Tile tileToBePlaced) {
        if(tileToBePlaced.isFlipped())
            return tileToBePlaced.getHexB().getTerrainType() == terrainTypes.VOLCANO;
        else {
            return tileToBePlaced.getHexC().getTerrainType() == terrainTypes.VOLCANO;
        }
    }

    boolean hexBelowIsAVolcano(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getTerrainType() == terrainTypes.VOLCANO;
    }

    boolean noSettlementsGetCompletelyNukedForEvenFlippedTileWithVolcanoAtA(int colPos, int rowPos) {
        if ((getGameBoardPositionSettlementID(new Pair(colPos, rowPos + 1))
                == getGameBoardPositionSettlementID(new Pair(colPos - 1, rowPos + 1))) &&
                (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(new Pair(colPos, rowPos + 1))) == 2)) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(new Pair(colPos, rowPos + 1))) == 1) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(new Pair (colPos - 1, rowPos + 1))) == 1) {
            return false;
        } else {
            return true;
        }
    }

    boolean noSettlementsGetCompletelyNukedForEvenFlippedTileWithVolcanoAtB(int colPos, int rowPos) {
        if ((getGameBoardPositionSettlementID(new Pair(colPos, rowPos))
                == getGameBoardPositionSettlementID(new Pair(colPos - 1, rowPos + 1))) &&
                (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(new Pair(colPos, rowPos))) == 2)) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(new Pair(colPos, rowPos))) == 1) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(new Pair(colPos - 1, rowPos + 1))) == 1) {
            return false;
        } else {
            return true;
        }
    }

    boolean noSettlementsGetCompletelyNukedForEvenFlippedTileWithVolcanoAtC(int colPos, int rowPos) {
        if ((getGameBoardPositionSettlementID(new Pair(colPos, rowPos + 1))
                == getGameBoardPositionSettlementID(new Pair(colPos, rowPos))) &&
                (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(new Pair(colPos, rowPos))) == 2)) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(new Pair(colPos, rowPos))) == 1) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(new Pair(colPos, rowPos + 1))) == 1) {
            return false;
        } else {
            return true;
        }
    }

    boolean noSettlementGetsCompletelyNukedForOddAndFlippedTileWithVolcanoAtA(int colPos, int rowPos) {
        if ((getGameBoardPositionSettlementID(new Pair(colPos + 1, rowPos + 1))
                == getGameBoardPositionSettlementID(new Pair(colPos, rowPos + 1))) &&
                (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(new Pair(colPos + 1, rowPos + 1))) == 2)) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(new Pair(colPos, rowPos + 1))) == 1) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(new Pair(colPos + 1, rowPos + 1))) == 1) {
            return false;
        } else {
            return true;
        }
    }

    boolean noSettlementGetsCompletelyNukedForOddAndFlippedTileWithVolcanoAtB(int colPos, int rowPos) {
        if ((getGameBoardPositionSettlementID(new Pair(colPos, rowPos))
                == getGameBoardPositionSettlementID(new Pair(colPos, rowPos + 1))) &&
                (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(new Pair(colPos, rowPos))) == 2)) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(new Pair (colPos, rowPos))) == 1) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(new Pair(colPos, rowPos + 1))) == 1) {
            return false;
        } else {
            return true;
        }
    }

    boolean noSettlementGetsCompletelyNukedForOddAndFlippedTileWithVolcanoAtC(int colPos, int rowPos) {
        if ((getGameBoardPositionSettlementID(new Pair(colPos, rowPos))
                == getGameBoardPositionSettlementID(new Pair(colPos + 1, rowPos + 1))) &&
                (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(new Pair(colPos, rowPos))) == 2)) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(new Pair(colPos, rowPos))) == 1) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(new Pair(colPos + 1, rowPos + 1))) == 1) {
            return false;
        } else {
            return true;
        }
    }

    boolean noSettlementGetsCompletelyNukedForEvenAndNotFlippedTileWithVolcanoAtA(int colPos, int rowPos) {
        if ((getGameBoardPositionSettlementID(new Pair(colPos, rowPos - 1))
                == getGameBoardPositionSettlementID(new Pair(colPos - 1, rowPos - 1))) &&
                (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(new Pair(colPos, rowPos - 1))) == 2)) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(new Pair(colPos, rowPos - 1))) == 1) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(new Pair(colPos - 1, rowPos - 1))) == 1) {
            return false;
        } else {
            return true;
        }
    }

    boolean noSettlementGetsCompletelyNukedForEvenAndNotFlippedTileWithVolcanoAtB(int colPos, int rowPos) {
        if ((getGameBoardPositionSettlementID(new Pair(colPos, rowPos - 1))
                == getGameBoardPositionSettlementID(new Pair(colPos, rowPos))) &&
                (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(new Pair(colPos, rowPos))) == 2)) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(new Pair(colPos, rowPos))) == 1) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(new Pair(colPos, rowPos - 1))) == 1) {
            return false;
        } else {
            return true;
        }
    }

    boolean noSettlementGetsCompletelyNukedForEvenAndNotFlippedTileWithVolcanoAtC(int colPos, int rowPos) {
        if ((getGameBoardPositionSettlementID(new Pair(colPos, rowPos))
                == getGameBoardPositionSettlementID(new Pair(colPos - 1, rowPos - 1))) &&
                (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(new Pair(colPos, rowPos))) == 2)) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(new Pair(colPos, rowPos))) == 1) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(new Pair(colPos - 1, rowPos - 1))) == 1) {
            return false;
        } else {
            return true;
        }
    }

    boolean noSettlementGetsCompletelyNukedForOddAndNotFlippedTileWithVolcanoAtA(int colPos, int rowPos) {
        if ((getGameBoardPositionSettlementID(new Pair(colPos, rowPos - 1))
                == getGameBoardPositionSettlementID(new Pair(colPos + 1, rowPos - 1))) &&
                (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(new Pair(colPos, rowPos - 1))) == 2)) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(new Pair(colPos, rowPos - 1))) == 1) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(new Pair(colPos + 1, rowPos - 1))) == 1) {
            return false;
        } else {
            return true;
        }
    }

    boolean noSettlementGetsCompletelyNukedForOddAndNotFlippedTileWithVolcanoAtB(int colPos, int rowPos) {
        if ((getGameBoardPositionSettlementID(new Pair(colPos, rowPos))
                == getGameBoardPositionSettlementID(new Pair(colPos + 1, rowPos - 1))) &&
                (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(new Pair(colPos, rowPos + 1))) == 2)) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(new Pair(colPos, rowPos))) == 1) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(new Pair(colPos + 1, rowPos - 1))) == 1) {
            return false;
        } else {
            return true;
        }
    }

    boolean noSettlementGetsCompletelyNukedForOddAndNotFlippedTileWithVolcanoAtC(int colPos, int rowPos) {
        if ((getGameBoardPositionSettlementID(new Pair(colPos, rowPos))
                == getGameBoardPositionSettlementID(new Pair(colPos, rowPos - 1))) &&
                (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(new Pair(colPos, rowPos))) == 2)) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(new Pair(colPos, rowPos))) == 1) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(new Pair(colPos, rowPos - 1))) == 1) {
            return false;
        } else {
            return true;
        }
    }

    void increaseEvenFlippedTileLevelAndUpdateGameBoardPositionArray(int colPos, int rowPos, Tile tileToBePlaced) {
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

    void increaseOddFlippedTileLevelAndUpdateGameBoardPositionArray(int colPos, int rowPos, Tile tileToBePlaced) {
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

    void increaseEvenNotFlippedTileLevelAndUpdateGameBoardPositionArray(int colPos, int rowPos, Tile tileToBePlaced) {
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

    void increaseOddNotFlippedTileLevelAndUpdateGameBoardPositionArray(int colPos, int rowPos, Tile tileToBePlaced) {
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
            decrementGameBoardSettlementListSize(getGameBoardPositionSettlementID(new Pair(colPos, rowPos)));
        }
        tileToBePlaced.getHexA().setLevel(gameBoardPositionArray[colPos][rowPos].getLevel() + 1);
        gameBoardPositionArray[colPos][rowPos] = tileToBePlaced.getHexA();
    }

    void setHexBLevelAndUpdateGameBoard(int colPos, int rowPos, Tile tileToBePlaced) {
        if(hexToBeNukedHasSettlement(gameBoardPositionArray[colPos][rowPos])) {
            decrementGameBoardSettlementListSize(getGameBoardPositionSettlementID(new Pair(colPos, rowPos)));
        }
        tileToBePlaced.getHexB().setLevel(gameBoardPositionArray[colPos][rowPos].getLevel() + 1);
        gameBoardPositionArray[colPos][rowPos] = tileToBePlaced.getHexB();
    }

    void setHexCLevelAndUpdateGameBoard(int colPos, int rowPos, Tile tileToBePlaced) {
        if(hexToBeNukedHasSettlement(gameBoardPositionArray[colPos][rowPos])) {
            decrementGameBoardSettlementListSize(getGameBoardPositionSettlementID(new Pair(colPos, rowPos)));
        }
        tileToBePlaced.getHexC().setLevel(gameBoardPositionArray[colPos][rowPos].getLevel() + 1);
        gameBoardPositionArray[colPos][rowPos] = tileToBePlaced.getHexC();
    }

    void markHexIfSettlementAtPositionNeedsToBeSplitDueToNuke(int colPos, int rowPos) {
        if(gameBoardPositionNotEmpty(colPos, rowPos) && hexToBeNukedHasSettlement(gameBoardPositionArray[colPos][rowPos])){
            addHexWithSettlementAdjacentToNukeToHexesBuiltOnList(colPos , rowPos);
        }
    }

    boolean gameBoardPositionNotEmpty(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos] != null;
    }

    boolean hexToBeNukedHasSettlement(Hex hex) {
        return hex.getSettlementID() != 0;
    }

    // TODO: Kevin stopped refactoring here

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

                    int oldSettlementID = getGameBoardPositionSettlementID(currentCoordinates);

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

    void resetTraversalList() {
        for(Pair pair : hexesToResetTraversalValue){
            unmarkGameBoardPositionAsTraversed(pair.getRowPosition(), pair.getColumnPosition());
        }
        hexesToResetTraversalValue.clear();
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



    boolean checkIfEven(int rowPos) {
        if (rowPos % 2 == 0) {
            return true;
        } else {
            return false;
        }
    }





    int accessGameBoardXValue(Hex hexToCheck) {
        return hexToCheck.getCoordinatePair().getColumnPosition();
    }

    int accessGameBoardYValue(Hex hexToCheck) {
        return hexToCheck.getCoordinatePair().getRowPosition();
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
        if (isValidSettlementLocation(colPos, rowPos) && playerBuilding.getSettlerCount() >= 1) {
            gameBoardPositionArray[colPos][rowPos].setSettlerCount(1);

            int newSettlementID = getNewestAssignableSettlementID();
            gameBoardPositionArray[colPos][rowPos].setSettlementID(newSettlementID);

            assignPlayerNewSettlementInList(playerBuilding, newSettlementID, 1);
            addHexWithSettlementAdjacentToNukeToHexesBuiltOnList(colPos, rowPos);

            gameBoardPositionArray[colPos][rowPos].setPlayerID(playerBuilding.getPlayerID());
            playerBuilding.increaseSettlementCount();
            playerBuilding.decreaseSettlerCount(1);
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
        if (gameBoardPositionArray[colPos][rowPos].getLevel() == 1) {
            return true;
        } else {
            return false;
        }
    }

    boolean isHabitable(int colPos, int rowPos) {
        if (gameBoardPositionArray[colPos][rowPos].getTerrainType().getHabitablity() == 1) {
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
            gameBoardPositionArray[colPos][rowPos].setPlayerID(playerBuilding.getPlayerID());
            playerBuilding.increaseScore(200);
            incrementGameBoardSettlementListSize(settlementID);
            playerBuilding.decreaseTotoroCount();
        }
    }

    boolean checkIfValidTotoroPlacement(int colPos, int rowPos, int settlementID, Player playerBuilding) {
        try {
            if(gameBoardPositionArray[colPos][rowPos].getTerrainType() == terrainTypes.VOLCANO) {
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
            if (getGameBoardPositionSettlementID(new Pair(colPos, rowPos)) != settlementID) {
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
            int setID;
            try{
                setID = getGameBoardPositionSettlementID(new Pair(rowPos-1, rowPos-1));
                if(gameBoardSettlementList[setID][2]==0){return setID;}
            }catch(Exception e){}
            try{
                setID = getGameBoardPositionSettlementID(new Pair(rowPos, rowPos-1));
                if(gameBoardSettlementList[setID][2]==0){return setID;}
            }catch(Exception e){}
            try{
                setID = getGameBoardPositionSettlementID(new Pair(rowPos-1, rowPos));
                if(gameBoardSettlementList[setID][2]==0){return setID;}
            }catch(Exception e){}
            try{
                setID = getGameBoardPositionSettlementID(new Pair(rowPos-1, rowPos));
                if(gameBoardSettlementList[setID][2]==0){return setID;}
            }catch(Exception e){}
            try{
                setID = getGameBoardPositionSettlementID(new Pair(rowPos-1, rowPos+1));
                if(gameBoardSettlementList[setID][2]==0){return setID;}
            }catch(Exception e){}
            try{
                setID = getGameBoardPositionSettlementID(new Pair(rowPos, rowPos+1));
                if(gameBoardSettlementList[setID][2]==0){return setID;}
            }catch(Exception e){}
        }else{
            int setID;
            try{
                setID = getGameBoardPositionSettlementID(new Pair(rowPos, rowPos-1));
                if(gameBoardSettlementList[setID][2]==0){return setID;}
            }catch(Exception e){}
            try{
                setID = getGameBoardPositionSettlementID(new Pair(rowPos+1, rowPos-1));
                if(gameBoardSettlementList[setID][2]==0){return setID;}
            }catch(Exception e){}
            try{
                setID = getGameBoardPositionSettlementID(new Pair(rowPos-1, rowPos));
                if(gameBoardSettlementList[setID][2]==0){return setID;}
            }catch(Exception e){}
            try{
                setID = getGameBoardPositionSettlementID(new Pair(rowPos+1, rowPos));
                if(gameBoardSettlementList[setID][2]==0){return setID;}
            }catch(Exception e){}
            try{
                setID = getGameBoardPositionSettlementID(new Pair(rowPos, rowPos+1));
                if(gameBoardSettlementList[setID][2]==0){return setID;}
            }catch(Exception e){}
            try{
                setID = getGameBoardPositionSettlementID(new Pair(rowPos+1, rowPos+1));
                if(gameBoardSettlementList[setID][2]==0){return setID;}
            }catch(Exception e){}
        }
        return -1;
    }

    int findAdjacentSettlementWithoutTiger(int rowPos, int colPos){
        if(checkIfEven(rowPos)){
            int setID;
            try{
                setID = getGameBoardPositionSettlementID(new Pair(rowPos-1, rowPos-1));
                if(gameBoardSettlementList[setID][3]==0){return setID;}
            }catch(Exception e){}
            try{
                setID = getGameBoardPositionSettlementID(new Pair(rowPos, rowPos-1));
                if(gameBoardSettlementList[setID][3]==0){return setID;}
            }catch(Exception e){}
            try{
                setID = getGameBoardPositionSettlementID(new Pair(rowPos-1, rowPos));
                if(gameBoardSettlementList[setID][3]==0){return setID;}
            }catch(Exception e){}
            try{
                setID = getGameBoardPositionSettlementID(new Pair(rowPos-1, rowPos));
                if(gameBoardSettlementList[setID][3]==0){return setID;}
            }catch(Exception e){}
            try{
                setID = getGameBoardPositionSettlementID(new Pair(rowPos-1, rowPos+1));
                if(gameBoardSettlementList[setID][3]==0){return setID;}
            }catch(Exception e){}
            try{
                setID = getGameBoardPositionSettlementID(new Pair(rowPos, rowPos+1));
                if(gameBoardSettlementList[setID][3]==0){return setID;}
            }catch(Exception e){}
        }else{
            int setID;
            try{
                setID = getGameBoardPositionSettlementID(new Pair(rowPos, rowPos-1));
                if(gameBoardSettlementList[setID][3]==0){return setID;}
            }catch(Exception e){}
            try{
                setID = getGameBoardPositionSettlementID(new Pair(rowPos+1, rowPos-1));
                if(gameBoardSettlementList[setID][3]==0){return setID;}
            }catch(Exception e){}
            try{
                setID = getGameBoardPositionSettlementID(new Pair(rowPos-1, rowPos));
                if(gameBoardSettlementList[setID][3]==0){return setID;}
            }catch(Exception e){}
            try{
                setID = getGameBoardPositionSettlementID(new Pair(rowPos+1, rowPos));
                if(gameBoardSettlementList[setID][3]==0){return setID;}
            }catch(Exception e){}
            try{
                setID = getGameBoardPositionSettlementID(new Pair(rowPos, rowPos+1));
                if(gameBoardSettlementList[setID][3]==0){return setID;}
            }catch(Exception e){}
            try{
                setID = getGameBoardPositionSettlementID(new Pair(rowPos+1, rowPos+1));
                if(gameBoardSettlementList[setID][3]==0){return setID;}
            }catch(Exception e){}
        }
        return -1;
    }



    void placeTigerPen(int colPos, int rowPos, int settlementID, Player playerBuilding) {
        if(checkIfValidTigerPlacement(colPos, rowPos, settlementID, playerBuilding)) {
            incrementGameBoardSettlementListTigerCount(settlementID);
            gameBoardPositionArray[colPos][rowPos].setTigerCount(1);
            gameBoardPositionArray[colPos][rowPos].setPlayerID(playerBuilding.getPlayerID());
            playerBuilding.increaseScore(75);
            incrementGameBoardSettlementListSize(settlementID);
            playerBuilding.decreaseTigerCount();
        }
    }

    boolean checkIfValidTigerPlacement(int colPos, int rowPos, int settlementID, Player playerBuilding) {
        try {
            if(gameBoardPositionArray[colPos][rowPos].getTerrainType() == terrainTypes.VOLCANO) {
                return false;
            }
            if(isNotBuiltOn(colPos, rowPos) == false) {
                return false;
            }
            if(gameBoardPositionArray[colPos][rowPos].getLevel() < 3) {
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
        int villagersNeededForExpansion = calculateVillagersForExpansion(colPos, rowPos, expansionType, player);
        resetTraversalList();

        if(villagersNeededForExpansion == 0) {
            return;
        }
        else if(villagersNeededForExpansion <= player.getSettlerCount()) {
            player.increaseScore(calculateScoreForExpansion(colPos, rowPos, expansionType, player));
            player.decreaseSettlerCount(villagersNeededForExpansion);
            resetTraversalList();

            try {
                if(!(isNotBuiltOn(colPos, rowPos))) { // is built on
                    if(isMySettlement(colPos, rowPos, player)) { // owned by player
                        int homeHexID = getGameBoardPositionSettlementID(new Pair(colPos, rowPos));

                        gameBoardPositionArray[colPos][rowPos].setIfAlreadyTraversed(true); // mark hex as traversed

                        hexesToResetTraversalValue.add(new Pair(colPos, rowPos));

                        if (checkIfEven(rowPos)) {
                            expandSettlementDriver(colPos-1, rowPos-1, expansionType, player, homeHexID);
                            expandSettlementDriver(colPos, rowPos-1, expansionType, player, homeHexID);
                            expandSettlementDriver(colPos-1, rowPos, expansionType, player, homeHexID);
                            expandSettlementDriver(colPos+1, rowPos, expansionType, player, homeHexID);
                            expandSettlementDriver(colPos-1, rowPos+1, expansionType, player, homeHexID);
                            expandSettlementDriver(colPos, rowPos+1, expansionType, player, homeHexID);
                        } else {
                            expandSettlementDriver(colPos, rowPos-1, expansionType, player, homeHexID);
                            expandSettlementDriver(colPos+1, rowPos-1, expansionType, player, homeHexID);
                            expandSettlementDriver(colPos-1, rowPos, expansionType, player, homeHexID);
                            expandSettlementDriver(colPos+1, rowPos, expansionType, player, homeHexID);
                            expandSettlementDriver(colPos, rowPos+1, expansionType, player, homeHexID);
                            expandSettlementDriver(colPos+1, rowPos+1, expansionType, player, homeHexID);
                        }
                    }
                }
            }catch(Exception e){return;} // don't do anything for null hexes
        }
        resetTraversalList();
    }

    void expandSettlementDriver(int colPos, int rowPos, terrainTypes expansionType, Player player, int homeHexID) {
        try{
            if(gameBoardPositionArray[colPos][rowPos].getIfAlreadyTraversed() == false) { // not already traversed
                if (isNotBuiltOn(colPos, rowPos) == false) { // if hex is built on
                    if (isMySettlement(colPos, rowPos, player)) { // if settlement is mine
                        gameBoardPositionArray[colPos][rowPos].setIfAlreadyTraversed(true); // mark hex as traversed
                        hexesToResetTraversalValue.add(new Pair(colPos, rowPos));

                        if (checkIfEven(rowPos)) {
                            expandSettlementDriver(colPos - 1, rowPos - 1, expansionType, player, homeHexID);
                            expandSettlementDriver(colPos, rowPos - 1, expansionType, player, homeHexID);
                            expandSettlementDriver(colPos - 1, rowPos, expansionType, player, homeHexID);
                            expandSettlementDriver(colPos + 1, rowPos, expansionType, player, homeHexID);
                            expandSettlementDriver(colPos - 1, rowPos + 1, expansionType, player, homeHexID);
                            expandSettlementDriver(colPos, rowPos + 1, expansionType, player, homeHexID);
                        } else {
                            expandSettlementDriver(colPos, rowPos - 1, expansionType, player, homeHexID);
                            expandSettlementDriver(colPos + 1, rowPos - 1, expansionType, player, homeHexID);
                            expandSettlementDriver(colPos - 1, rowPos, expansionType, player, homeHexID);
                            expandSettlementDriver(colPos + 1, rowPos, expansionType, player, homeHexID);
                            expandSettlementDriver(colPos, rowPos + 1, expansionType, player, homeHexID);
                            expandSettlementDriver(colPos + 1, rowPos + 1, expansionType, player, homeHexID);
                        }

                        return;
                    }
                } else if (isNotBuiltOn(colPos, rowPos)) { // if hex is not built on and free for expansion
                    if(gameBoardPositionArray[colPos][rowPos].getTerrainType() == expansionType) { // is desired terrain type
                        gameBoardPositionArray[colPos][rowPos].setIfAlreadyTraversed(true); // mark hex as traversed

                        Pair currentCoordinates = new Pair(colPos, rowPos);
                        hexesToResetTraversalValue.add(currentCoordinates);
                        hexesBuiltOnThisTurn.add(currentCoordinates);

                        int hexLevel = gameBoardPositionArray[colPos][rowPos].getLevel();

                        gameBoardPositionArray[colPos][rowPos].setSettlerCount(hexLevel); // set villagers on hex level
                        setGameBoardPositionSettlementID(new Pair(colPos, rowPos), homeHexID); // set settlement ownership ID
                        gameBoardPositionArray[colPos][rowPos].setPlayerID(player.getPlayerID()); // set player ownership



                        incrementGameBoardSettlementListSize(homeHexID); // increase size of home settlement

                        if (checkIfEven(rowPos)) {
                            expandSettlementDriver(colPos - 1, rowPos - 1, expansionType, player, homeHexID);
                            expandSettlementDriver(colPos, rowPos - 1, expansionType, player, homeHexID);
                            expandSettlementDriver(colPos - 1, rowPos, expansionType, player, homeHexID);
                            expandSettlementDriver(colPos + 1, rowPos, expansionType, player, homeHexID);
                            expandSettlementDriver(colPos - 1, rowPos + 1, expansionType, player, homeHexID);
                            expandSettlementDriver(colPos, rowPos + 1, expansionType, player, homeHexID);
                        } else {
                            expandSettlementDriver(colPos, rowPos - 1, expansionType, player, homeHexID);
                            expandSettlementDriver(colPos + 1, rowPos - 1, expansionType, player, homeHexID);
                            expandSettlementDriver(colPos - 1, rowPos, expansionType, player, homeHexID);
                            expandSettlementDriver(colPos + 1, rowPos, expansionType, player, homeHexID);
                            expandSettlementDriver(colPos, rowPos + 1, expansionType, player, homeHexID);
                            expandSettlementDriver(colPos + 1, rowPos + 1, expansionType, player, homeHexID);
                        }
                        return;
                    }
                }
            }
        }catch(Exception e){return;} // return for null hexes

        return; // return for any other case by default
    }

    int calculateVillagersForExpansion(int colPos, int rowPos, terrainTypes expansionType, Player player) {
        try {
            if(!(isNotBuiltOn(colPos, rowPos))) { // is built on
                if(isMySettlement(colPos, rowPos, player)) { // owned by player
                    int returnVal = 0;
                    int homeHexID = gameBoardPositionArray[colPos][rowPos].getSettlementID();

                    gameBoardPositionArray[colPos][rowPos].setIfAlreadyTraversed(true); // mark hex as traversed
                    hexesToResetTraversalValue.add(new Pair(colPos, rowPos));

                    if (checkIfEven(rowPos)) {
                        returnVal += calculateVillagersForExpansionDriver(colPos-1, rowPos-1, expansionType, player, homeHexID);
                        returnVal += calculateVillagersForExpansionDriver(colPos, rowPos-1, expansionType, player, homeHexID);
                        returnVal += calculateVillagersForExpansionDriver(colPos-1, rowPos, expansionType, player, homeHexID);
                        returnVal += calculateVillagersForExpansionDriver(colPos+1, rowPos, expansionType, player, homeHexID);
                        returnVal += calculateVillagersForExpansionDriver(colPos-1, rowPos+1, expansionType, player, homeHexID);
                        returnVal += calculateVillagersForExpansionDriver(colPos, rowPos+1, expansionType, player, homeHexID);
                    } else {
                        returnVal += calculateVillagersForExpansionDriver(colPos, rowPos-1, expansionType, player, homeHexID);
                        returnVal += calculateVillagersForExpansionDriver(colPos+1, rowPos-1, expansionType, player, homeHexID);
                        returnVal += calculateVillagersForExpansionDriver(colPos-1, rowPos, expansionType, player, homeHexID);
                        returnVal += calculateVillagersForExpansionDriver(colPos+1, rowPos, expansionType, player, homeHexID);
                        returnVal += calculateVillagersForExpansionDriver(colPos, rowPos+1, expansionType, player, homeHexID);
                        returnVal += calculateVillagersForExpansionDriver(colPos+1, rowPos+1, expansionType, player, homeHexID);
                    }

                    return returnVal;
                }
            }
        }catch(Exception e){return 0;} // don't do anything for null hexes
        return 0;
    }

    int calculateVillagersForExpansionDriver(int colPos, int rowPos, terrainTypes expansionType, Player player, int homeHexID) {
        try{
            int returnVal = 0;
            if(gameBoardPositionArray[colPos][rowPos].getIfAlreadyTraversed() == false) { // not already traversed
                if (isNotBuiltOn(colPos, rowPos) == false) { // if hex is built on
                    if (isMySettlement(colPos, rowPos, player)) { // if settlement is mine
                        gameBoardPositionArray[colPos][rowPos].setIfAlreadyTraversed(true); // mark hex as traversed
                        hexesToResetTraversalValue.add(new Pair(colPos, rowPos));

                        if (checkIfEven(rowPos)) {
                            returnVal += calculateVillagersForExpansionDriver(colPos - 1, rowPos - 1, expansionType, player, homeHexID);
                            returnVal += calculateVillagersForExpansionDriver(colPos, rowPos - 1, expansionType, player, homeHexID);
                            returnVal += calculateVillagersForExpansionDriver(colPos - 1, rowPos, expansionType, player, homeHexID);
                            returnVal += calculateVillagersForExpansionDriver(colPos + 1, rowPos, expansionType, player, homeHexID);
                            returnVal += calculateVillagersForExpansionDriver(colPos - 1, rowPos + 1, expansionType, player, homeHexID);
                            returnVal += calculateVillagersForExpansionDriver(colPos, rowPos + 1, expansionType, player, homeHexID);
                        } else {
                            returnVal += calculateVillagersForExpansionDriver(colPos, rowPos - 1, expansionType, player, homeHexID);
                            returnVal += calculateVillagersForExpansionDriver(colPos + 1, rowPos - 1, expansionType, player, homeHexID);
                            returnVal += calculateVillagersForExpansionDriver(colPos - 1, rowPos, expansionType, player, homeHexID);
                            returnVal += calculateVillagersForExpansionDriver(colPos + 1, rowPos, expansionType, player, homeHexID);
                            returnVal += calculateVillagersForExpansionDriver(colPos, rowPos + 1, expansionType, player, homeHexID);
                            returnVal += calculateVillagersForExpansionDriver(colPos + 1, rowPos + 1, expansionType, player, homeHexID);
                        }

                        return returnVal;
                    }
                } else if (isNotBuiltOn(colPos, rowPos)) { // if hex is not built on and is free for expansion
                    if(gameBoardPositionArray[colPos][rowPos].getTerrainType() == expansionType) { // is desired terrain type
                        gameBoardPositionArray[colPos][rowPos].setIfAlreadyTraversed(true); // mark hex as traversed
                        hexesToResetTraversalValue.add(new Pair(colPos, rowPos));

                        int hexLevel = gameBoardPositionArray[colPos][rowPos].getLevel();
                        returnVal += hexLevel; // number of meeples needed to expand onto hex

                        if (checkIfEven(rowPos)) {
                            returnVal += calculateVillagersForExpansionDriver(colPos - 1, rowPos - 1, expansionType, player, homeHexID);
                            returnVal += calculateVillagersForExpansionDriver(colPos, rowPos - 1, expansionType, player, homeHexID);
                            returnVal += calculateVillagersForExpansionDriver(colPos - 1, rowPos, expansionType, player, homeHexID);
                            returnVal += calculateVillagersForExpansionDriver(colPos + 1, rowPos, expansionType, player, homeHexID);
                            returnVal += calculateVillagersForExpansionDriver(colPos - 1, rowPos + 1, expansionType, player, homeHexID);
                            returnVal += calculateVillagersForExpansionDriver(colPos, rowPos + 1, expansionType, player, homeHexID);
                        } else {
                            returnVal += calculateVillagersForExpansionDriver(colPos, rowPos - 1, expansionType, player, homeHexID);
                            returnVal += calculateVillagersForExpansionDriver(colPos + 1, rowPos - 1, expansionType, player, homeHexID);
                            returnVal += calculateVillagersForExpansionDriver(colPos - 1, rowPos, expansionType, player, homeHexID);
                            returnVal += calculateVillagersForExpansionDriver(colPos + 1, rowPos, expansionType, player, homeHexID);
                            returnVal += calculateVillagersForExpansionDriver(colPos, rowPos + 1, expansionType, player, homeHexID);
                            returnVal += calculateVillagersForExpansionDriver(colPos + 1, rowPos + 1, expansionType, player, homeHexID);
                        }

                        return returnVal;
                    }
                }
            }
        }catch(Exception e){return 0;} // return 0 for null hexes

        return 0; // return 0 for any other case by default
    }

    int calculateScoreForExpansion(int colPos, int rowPos, terrainTypes expansionType, Player player) {
        try {
            if(!(isNotBuiltOn(colPos, rowPos))) { // is built on
                if(isMySettlement(colPos, rowPos, player)) { // owned by player
                    int returnVal = 0;
                    int homeHexID = gameBoardPositionArray[colPos][rowPos].getSettlementID();

                    gameBoardPositionArray[colPos][rowPos].setIfAlreadyTraversed(true); // mark hex as traversed
                    hexesToResetTraversalValue.add(new Pair(colPos, rowPos));

                    if (checkIfEven(rowPos)) {
                        returnVal += calculateScoreForExpansionDriver(colPos-1, rowPos-1, expansionType, player, homeHexID);
                        returnVal += calculateScoreForExpansionDriver(colPos, rowPos-1, expansionType, player, homeHexID);
                        returnVal += calculateScoreForExpansionDriver(colPos-1, rowPos, expansionType, player, homeHexID);
                        returnVal += calculateScoreForExpansionDriver(colPos+1, rowPos, expansionType, player, homeHexID);
                        returnVal += calculateScoreForExpansionDriver(colPos-1, rowPos+1, expansionType, player, homeHexID);
                        returnVal += calculateScoreForExpansionDriver(colPos, rowPos+1, expansionType, player, homeHexID);
                    } else {
                        returnVal += calculateScoreForExpansionDriver(colPos, rowPos-1, expansionType, player, homeHexID);
                        returnVal += calculateScoreForExpansionDriver(colPos+1, rowPos-1, expansionType, player, homeHexID);
                        returnVal += calculateScoreForExpansionDriver(colPos-1, rowPos, expansionType, player, homeHexID);
                        returnVal += calculateScoreForExpansionDriver(colPos+1, rowPos, expansionType, player, homeHexID);
                        returnVal += calculateScoreForExpansionDriver(colPos, rowPos+1, expansionType, player, homeHexID);
                        returnVal += calculateScoreForExpansionDriver(colPos+1, rowPos+1, expansionType, player, homeHexID);
                    }

                    return returnVal;
                }
            }
        }catch(Exception e){return 0;} // don't do anything for null hexes
        return 0;
    }

    int calculateScoreForExpansionDriver(int colPos, int rowPos, terrainTypes expansionType, Player player, int homeHexID) {
        try{
            int returnVal = 0;
            if(gameBoardPositionArray[colPos][rowPos].getIfAlreadyTraversed() == false) { // not already traversed
                if (isNotBuiltOn(colPos, rowPos) == false) { // if hex is built on
                    if (isMySettlement(colPos, rowPos, player)) { // if settlement is mine
                        gameBoardPositionArray[colPos][rowPos].setIfAlreadyTraversed(true); // mark hex as traversed
                        hexesToResetTraversalValue.add(new Pair(colPos, rowPos));

                        if (checkIfEven(rowPos)) {
                            returnVal += calculateScoreForExpansionDriver(colPos - 1, rowPos - 1, expansionType, player, homeHexID);
                            returnVal += calculateScoreForExpansionDriver(colPos, rowPos - 1, expansionType, player, homeHexID);
                            returnVal += calculateScoreForExpansionDriver(colPos - 1, rowPos, expansionType, player, homeHexID);
                            returnVal += calculateScoreForExpansionDriver(colPos + 1, rowPos, expansionType, player, homeHexID);
                            returnVal += calculateScoreForExpansionDriver(colPos - 1, rowPos + 1, expansionType, player, homeHexID);
                            returnVal += calculateScoreForExpansionDriver(colPos, rowPos + 1, expansionType, player, homeHexID);
                        } else {
                            returnVal += calculateScoreForExpansionDriver(colPos, rowPos - 1, expansionType, player, homeHexID);
                            returnVal += calculateScoreForExpansionDriver(colPos + 1, rowPos - 1, expansionType, player, homeHexID);
                            returnVal += calculateScoreForExpansionDriver(colPos - 1, rowPos, expansionType, player, homeHexID);
                            returnVal += calculateScoreForExpansionDriver(colPos + 1, rowPos, expansionType, player, homeHexID);
                            returnVal += calculateScoreForExpansionDriver(colPos, rowPos + 1, expansionType, player, homeHexID);
                            returnVal += calculateScoreForExpansionDriver(colPos + 1, rowPos + 1, expansionType, player, homeHexID);
                        }

                        return returnVal;
                    }
                } else if (isNotBuiltOn(colPos, rowPos)) { // not built on
                    if(gameBoardPositionArray[colPos][rowPos].getTerrainType() == expansionType) { // is desired terrain type
                        gameBoardPositionArray[colPos][rowPos].setIfAlreadyTraversed(true); // mark hex as traversed
                        hexesToResetTraversalValue.add(new Pair(colPos, rowPos));

                        int hexLevel = gameBoardPositionArray[colPos][rowPos].getLevel();

                        returnVal += (hexLevel * hexLevel); // number of meeples needed to expand onto hex

                        if (checkIfEven(rowPos)) {
                            returnVal += calculateScoreForExpansionDriver(colPos - 1, rowPos - 1, expansionType, player, homeHexID);
                            returnVal += calculateScoreForExpansionDriver(colPos, rowPos - 1, expansionType, player, homeHexID);
                            returnVal += calculateScoreForExpansionDriver(colPos - 1, rowPos, expansionType, player, homeHexID);
                            returnVal += calculateScoreForExpansionDriver(colPos + 1, rowPos, expansionType, player, homeHexID);
                            returnVal += calculateScoreForExpansionDriver(colPos - 1, rowPos + 1, expansionType, player, homeHexID);
                            returnVal += calculateScoreForExpansionDriver(colPos, rowPos + 1, expansionType, player, homeHexID);
                        } else {
                            returnVal += calculateScoreForExpansionDriver(colPos, rowPos - 1, expansionType, player, homeHexID);
                            returnVal += calculateScoreForExpansionDriver(colPos + 1, rowPos - 1, expansionType, player, homeHexID);
                            returnVal += calculateScoreForExpansionDriver(colPos - 1, rowPos, expansionType, player, homeHexID);
                            returnVal += calculateScoreForExpansionDriver(colPos + 1, rowPos, expansionType, player, homeHexID);
                            returnVal += calculateScoreForExpansionDriver(colPos, rowPos + 1, expansionType, player, homeHexID);
                            returnVal += calculateScoreForExpansionDriver(colPos + 1, rowPos + 1, expansionType, player, homeHexID);
                        }

                        return returnVal;
                    }
                }
            }
        }catch(Exception e){return 0;} // return 0 for null hexes

        return 0; // return 0 for any other case by default
    }


    boolean isMySettlement(int colPos, int rowPos, Player player){
        int settlementID = getGameBoardPositionSettlementID(new Pair(colPos, rowPos));

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
        if(getGameBoardPositionSettlementID(new Pair(colPos, rowPos)) != masterSettlementID) { // if we are not at a part of the master settlement, we need to decrement the values of the settlement currently
            // here, increment the necessary values for the master settlement and update the hex settlementID
            if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(new Pair(colPos, rowPos))) == 1) { // if the settlement we are currently merging is of size 1,
                // delete that settlement from player list and game list
                deleteSettlementFromGame(getGameBoardPositionSettlementID(new Pair(colPos, rowPos)), playerID); // delete settlement from game
            } else { // else just decrement values in list of that settlement for size/totoro/tiger pen
                if (thereIsASettlerAt(colPos, rowPos)) {
                    decrementGameBoardSettlementListSize(getGameBoardPositionSettlementID(new Pair(colPos, rowPos)));
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





    // TODO: GENERAL PURPOSE FUNCTIONS GO HERE (SMALLER FUNCTIONS USED ACROSS MULTIPLE LARGER FUNCIONS)

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

    void incrementGameBoardSettlementListSize(int settlementID) {
        this.gameBoardSettlementList[settlementID][1] += 1;
    }

    void decrementGameBoardSettlementListSize(int settlementID) {
        gameBoardSettlementList[settlementID][1] -= 1;
    }

    Hex[][] getGameBoardPositionArray() {
        return gameBoardPositionArray;
    }

    int[][] getValidPlacementArray() {
        return validPlacementArray;
    }

    int getGameBoardPositionSettlementID(Pair currentCoordinates) {
        return gameBoardPositionArray[currentCoordinates.getColumnPosition()][currentCoordinates.getRowPosition()].getSettlementID();
    }

    int getGameBoardPositionPlayerID(Pair currentCoordinates) {
        return gameBoardPositionArray[currentCoordinates.getColumnPosition()][currentCoordinates.getRowPosition()].getPlayerID();
    }

    void setGameBoardPositionSettlementID(Pair currentCoordinates, int masterSettlementID) {
        gameBoardPositionArray[currentCoordinates.getColumnPosition()][currentCoordinates.getRowPosition()].setSettlementID(masterSettlementID);
    }

    void setGameBoardSettlementListPlayerID(int settlementID, int playerID) {
        this.gameBoardSettlementList[settlementID][0] = playerID;
    }

    int getGameBoardTileID() {
        return gameBoardTileID;
    }

    int getGameBoardHexID() {
        return gameBoardHexID;
    }

    boolean playerOwnsSettlementWithID(int settlementID, int playerID) {
        return this.playerOwnedSettlementsList[settlementID][playerID-1];
    }

    public Vector<Pair> getHexesBuiltOnThisTurn() {
        return hexesBuiltOnThisTurn;
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
}