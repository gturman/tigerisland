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
        gameBoardTileID = 1;
        gameBoardHexID = 1;
        usedSettlementIDs[0] = 1; // Note: Settlement ID of 0 denotes a hex with no settlement on it - never use this ID
    }



    void placeFirstTileAndUpdateValidPlacementList() {
        updateGameBoardPositionArrayWithFirstTileHexes();

        int hexAmount = 5;
        incrementGameBoardHexID(hexAmount);
        incrementGameBoardTileID();

        updateValidPlacementArrayWithFirstTile();
    }

    private void updateGameBoardPositionArrayWithFirstTileHexes() {
        gameBoardPositionArray[102][102] = new Hex(1, 1, terrainTypes.VOLCANO);
        gameBoardPositionArray[101][101] = new Hex(2, 1, terrainTypes.JUNGLE);
        gameBoardPositionArray[102][101] = new Hex(3, 1, terrainTypes.LAKE);
        gameBoardPositionArray[101][103] = new Hex(4, 1, terrainTypes.ROCKY);
        gameBoardPositionArray[102][103] = new Hex(5, 1, terrainTypes.GRASSLANDS);
    }

    private void updateValidPlacementArrayWithFirstTile() {
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
        try {
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
        } catch(Exception e){};
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

    private boolean thereAreNoHexesUnderEvenAndFlippedTile(int colPos, int rowPos) {
        return hexDoesNotOccupyPosition(colPos, rowPos) &&
                hexDoesNotOccupyPosition(colPos - 1, rowPos + 1) &&
                hexDoesNotOccupyPosition(colPos, rowPos + 1);
    }

    private boolean thereAreNoHexesUnderOddAndFlippedTile(int colPos, int rowPos) {
        return hexDoesNotOccupyPosition(colPos, rowPos) &&
                hexDoesNotOccupyPosition(colPos, rowPos + 1) &&
                hexDoesNotOccupyPosition(colPos + 1, rowPos + 1);
    }

    private boolean thereAreNoHexesUnderEvenAndNotFlippedTile(int colPos, int rowPos) {
        return hexDoesNotOccupyPosition(colPos, rowPos) &&
                hexDoesNotOccupyPosition(colPos - 1, rowPos - 1) &&
                hexDoesNotOccupyPosition(colPos, rowPos - 1);
    }

    private boolean thereAreNoHexesUnderOddAndNotFlippedTile(int colPos, int rowPos) {
        return hexDoesNotOccupyPosition(colPos, rowPos) &&
                hexDoesNotOccupyPosition(colPos, rowPos - 1) &&
                hexDoesNotOccupyPosition(colPos + 1, rowPos - 1);
    }

    private boolean hexDoesNotOccupyPosition(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos] == null;
    }

    private void setEvenFlippedCoordinatesAndUpdateGameBoard(int colPos, int rowPos, Tile tileToBePlaced) {
        setHexACoordinateAndUpdateGameBoard(colPos, rowPos, tileToBePlaced);
        setHexBCoordinateAndUpdateGameBoard(colPos - 1, rowPos + 1, tileToBePlaced);
        setHexCCoordinateAndUpdateGameBoard(colPos, rowPos + 1, tileToBePlaced);
    }

    private void setOddFlippedCoordinatesAndUpdateGameBoard(int colPos, int rowPos, Tile tileToBePlaced) {
        setHexACoordinateAndUpdateGameBoard(colPos, rowPos, tileToBePlaced);
        setHexBCoordinateAndUpdateGameBoard(colPos, rowPos + 1, tileToBePlaced);
        setHexCCoordinateAndUpdateGameBoard(colPos + 1, rowPos + 1, tileToBePlaced);
    }

    private void setEvenNotFlippedCoordinatesAndUpdateGameBoard(int colPos, int rowPos, Tile tileToBePlaced) {
        setHexACoordinateAndUpdateGameBoard(colPos, rowPos, tileToBePlaced);
        setHexBCoordinateAndUpdateGameBoard(colPos - 1, rowPos - 1, tileToBePlaced);
        setHexCCoordinateAndUpdateGameBoard(colPos, rowPos - 1, tileToBePlaced);
    }

    private void setOddNotFlippedCoordinatesAndUpdateGameBoard(int colPos, int rowPos, Tile tileToBePlaced) {
        setHexACoordinateAndUpdateGameBoard(colPos, rowPos, tileToBePlaced);
        setHexBCoordinateAndUpdateGameBoard(colPos, rowPos - 1, tileToBePlaced);
        setHexCCoordinateAndUpdateGameBoard(colPos + 1, rowPos - 1, tileToBePlaced);
    }

    private void setHexACoordinateAndUpdateGameBoard(int colPos, int rowPos, Tile tileToBePlaced) {
        tileToBePlaced.getHexA().getCoordinatePair().setCoordinates(colPos, rowPos);
        this.gameBoardPositionArray[colPos][rowPos] = tileToBePlaced.getHexA();
    }

    private void setHexBCoordinateAndUpdateGameBoard(int colPos, int rowPos, Tile tileToBePlaced) {
        tileToBePlaced.getHexB().getCoordinatePair().setCoordinates(colPos, rowPos);
        this.gameBoardPositionArray[colPos][rowPos] = tileToBePlaced.getHexB();
    }

    private void setHexCCoordinateAndUpdateGameBoard(int colPos, int rowPos, Tile tileToBePlaced) {
        tileToBePlaced.getHexC().getCoordinatePair().setCoordinates(colPos, rowPos);
        this.gameBoardPositionArray[colPos][rowPos] = tileToBePlaced.getHexC();
    }

    private void updateValidTilePlacementList(Tile tileThatWasPlaced) {
        if (tileThatWasPlaced.isFlipped()) {
            setValidFlippedPositions(tileThatWasPlaced);
        } else {
            setValidNotFlippedPositions(tileThatWasPlaced);
        }
        setBoardPositionAsOccupied(tileThatWasPlaced);
    }

    private void setValidFlippedPositions(Tile tileThatWasPlaced) {
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

    private void setValidNotFlippedPositions(Tile tileThatWasPlaced) {
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

    private boolean boardPositionsRelativeToHomeHexAreEmpty(int colOffset, int rowOffset, Tile tileThatWasPlaced) {
        return validPlacementArray[accessGameBoardColValue(tileThatWasPlaced.getHexA()) + colOffset][accessGameBoardRowValue(tileThatWasPlaced.getHexA()) + rowOffset] == 0;
    }

    private void setBoardPositionsRelativeToHomeHexAsValid(int colOffset, int rowOffset, Tile tileThatWasPlaced) {
        validPlacementArray[accessGameBoardColValue(tileThatWasPlaced.getHexA()) + colOffset][accessGameBoardRowValue(tileThatWasPlaced.getHexA()) + rowOffset] = 1;
    }

    private void setBoardPositionAsOccupied(Tile tileThatWasPlaced) {
        validPlacementArray[accessGameBoardColValue(tileThatWasPlaced.getHexA())][accessGameBoardRowValue(tileThatWasPlaced.getHexA())] = -1;
        validPlacementArray[accessGameBoardColValue(tileThatWasPlaced.getHexB())][accessGameBoardRowValue(tileThatWasPlaced.getHexB())] = -1;
        validPlacementArray[accessGameBoardColValue(tileThatWasPlaced.getHexC())][accessGameBoardRowValue(tileThatWasPlaced.getHexC())] = -1;
    }

    private int accessGameBoardColValue(Hex hex) {
        return hex.getCoordinatePair().getColumnPosition();
    }

    private int accessGameBoardRowValue(Hex hex) {
        return hex.getCoordinatePair().getRowPosition();
    }

// todo: can nuke tigers

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

            int hexAmount = 3;
            incrementGameBoardHexID(hexAmount);
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

    private boolean canValidlyPlaceEvenFlippedTileInLocation(int colPos, int rowPos) {
        return noEmptySpacesBelowEvenFlippedTile(colPos, rowPos) &&
                allHexesBelowEvenFlippedTileAreAtSameLevel(colPos, rowPos) &&
                notCoveringEntireEvenFlippedTile(colPos, rowPos) &&
                /*noTigerPenBelowEvenFlippedTile(colPos, rowPos) &&*/
                noTotoroBelowEvenFlippedTile(colPos, rowPos);
    }

    private boolean canValidlyPlaceOddFlippedTileInLocation(int colPos, int rowPos) {
        return noEmptySpacesBelowOddFlippedTile(colPos, rowPos) &&
                allHexesBelowOddFlippedTileAreAtSameLevel(colPos, rowPos) &&
                notCoveringEntireOddFlippedTile(colPos, rowPos) &&
                /*noTigerPenBelowEvenFlippedTile(colPos, rowPos) &&*/
                noTotoroBelowOddFlippedTile(colPos, rowPos);
    }

    private boolean canValidlyPlaceEvenNotFlippedTileInLocation(int colPos, int rowPos) {
        return noEmptySpacesBelowEvenNotFlippedTile(colPos, rowPos) &&
                allHexesBelowEvenNotFlippedTileAreAtSameLevel(colPos, rowPos) &&
                notCoveringEntireEvenNotFlippedTile(colPos, rowPos) &&
                /*noTigerPenBelowEvenFlippedTile(colPos, rowPos) &&*/
                noTotoroBelowEvenNotFlippedTile(colPos, rowPos);
    }

    private boolean canValidlyPlaceOddNotFlippedTileInLocation(int colPos, int rowPos) {
        return noEmptySpacesBelowOddNotFlippedTile(colPos, rowPos) &&
                allHexesBelowOddNotFlippedTileAreAtSameLevel(colPos, rowPos) &&
                notCoveringEntireOddNotFlippedTile(colPos, rowPos) &&
                /*noTigerPenBelowEvenFlippedTile(colPos, rowPos) &&*/
                noTotoroBelowOddNotFlippedTile(colPos, rowPos);
    }

    private boolean noEmptySpacesBelowEvenFlippedTile(int colPos, int rowPos) {
        return gameBoardPositionNotEmpty(colPos, rowPos)
                && gameBoardPositionNotEmpty(colPos, rowPos + 1)
                && gameBoardPositionNotEmpty(colPos - 1, rowPos + 1);
    }

    private boolean noEmptySpacesBelowOddFlippedTile(int colPos, int rowPos) {
        return gameBoardPositionNotEmpty(colPos, rowPos)
                && gameBoardPositionNotEmpty(colPos + 1, rowPos + 1)
                && gameBoardPositionNotEmpty(colPos, rowPos + 1);
    }

    private boolean noEmptySpacesBelowEvenNotFlippedTile(int colPos, int rowPos) {
        return gameBoardPositionNotEmpty(colPos, rowPos)
                && gameBoardPositionNotEmpty(colPos - 1, rowPos - 1)
                && gameBoardPositionNotEmpty(colPos, rowPos - 1);
    }

    private boolean noEmptySpacesBelowOddNotFlippedTile(int colPos, int rowPos) {
        return gameBoardPositionNotEmpty(colPos, rowPos)
                && gameBoardPositionNotEmpty(colPos, rowPos - 1)
                && gameBoardPositionNotEmpty(colPos + 1, rowPos - 1);
    }

    private boolean allHexesBelowEvenFlippedTileAreAtSameLevel(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getLevel() == gameBoardPositionArray[colPos][rowPos + 1].getLevel()
                && gameBoardPositionArray[colPos][rowPos].getLevel() == gameBoardPositionArray[colPos - 1][rowPos + 1].getLevel()
                && gameBoardPositionArray[colPos][rowPos + 1].getLevel() == gameBoardPositionArray[colPos - 1][rowPos + 1].getLevel();
    }

    private boolean allHexesBelowOddFlippedTileAreAtSameLevel(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getLevel() == gameBoardPositionArray[colPos + 1][rowPos + 1].getLevel()
                && gameBoardPositionArray[colPos][rowPos].getLevel() == gameBoardPositionArray[colPos][rowPos + 1].getLevel()
                && gameBoardPositionArray[colPos + 1][rowPos + 1].getLevel() == gameBoardPositionArray[colPos][rowPos + 1].getLevel();
    }

    private boolean allHexesBelowEvenNotFlippedTileAreAtSameLevel(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getLevel() == gameBoardPositionArray[colPos - 1][rowPos - 1].getLevel()
                && gameBoardPositionArray[colPos][rowPos].getLevel() == gameBoardPositionArray[colPos][rowPos - 1].getLevel()
                && gameBoardPositionArray[colPos - 1][rowPos - 1].getLevel() == gameBoardPositionArray[colPos][rowPos - 1].getLevel();
    }

    private boolean allHexesBelowOddNotFlippedTileAreAtSameLevel(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getLevel() == gameBoardPositionArray[colPos][rowPos - 1].getLevel()
                && gameBoardPositionArray[colPos][rowPos].getLevel() == gameBoardPositionArray[colPos + 1][rowPos - 1].getLevel()
                && gameBoardPositionArray[colPos][rowPos - 1].getLevel() == gameBoardPositionArray[colPos + 1][rowPos - 1].getLevel();
    }

    private boolean notCoveringEntireEvenFlippedTile(int colPos, int rowPos) {
        return !(gameBoardPositionArray[colPos][rowPos].getParentTileID() == gameBoardPositionArray[colPos][rowPos + 1].getParentTileID()
                && gameBoardPositionArray[colPos][rowPos].getParentTileID() == gameBoardPositionArray[colPos - 1][rowPos + 1].getParentTileID()
                && gameBoardPositionArray[colPos][rowPos + 1].getParentTileID() == gameBoardPositionArray[colPos - 1][rowPos + 1].getParentTileID());
    }

    private boolean notCoveringEntireOddFlippedTile(int colPos, int rowPos) {
        return !(gameBoardPositionArray[colPos][rowPos].getParentTileID() == gameBoardPositionArray[colPos + 1][rowPos + 1].getParentTileID()
                && gameBoardPositionArray[colPos][rowPos].getParentTileID() == gameBoardPositionArray[colPos][rowPos + 1].getParentTileID()
                && gameBoardPositionArray[colPos + 1][rowPos + 1].getParentTileID() == gameBoardPositionArray[colPos][rowPos + 1].getParentTileID());
    }

    private boolean notCoveringEntireEvenNotFlippedTile(int colPos, int rowPos) {
        return !(gameBoardPositionArray[colPos][rowPos].getParentTileID() == gameBoardPositionArray[colPos - 1][rowPos - 1].getParentTileID()
                && gameBoardPositionArray[colPos][rowPos].getParentTileID() == gameBoardPositionArray[colPos][rowPos - 1].getParentTileID()
                && gameBoardPositionArray[colPos - 1][rowPos - 1].getParentTileID() == gameBoardPositionArray[colPos][rowPos - 1].getParentTileID());
    }

    private boolean notCoveringEntireOddNotFlippedTile(int colPos, int rowPos) {
        return !(gameBoardPositionArray[colPos][rowPos].getParentTileID() == gameBoardPositionArray[colPos][rowPos - 1].getParentTileID()
                && gameBoardPositionArray[colPos][rowPos].getParentTileID() == gameBoardPositionArray[colPos + 1][rowPos - 1].getParentTileID()
                && gameBoardPositionArray[colPos][rowPos - 1].getParentTileID() == gameBoardPositionArray[colPos + 1][rowPos - 1].getParentTileID());
    }
/*
    private boolean noTigerPenBelowEvenFlippedTile(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getTigerCount() == 0
                && gameBoardPositionArray[colPos][rowPos + 1].getTigerCount() == 0
                && gameBoardPositionArray[colPos - 1][rowPos + 1].getTigerCount() == 0;
    }

    private boolean noTigerPenBelowOddFlippedTile(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getTigerCount() == 0
                && gameBoardPositionArray[colPos + 1][rowPos + 1].getTigerCount() == 0
                && gameBoardPositionArray[colPos][rowPos + 1].getTigerCount() == 0;
    }

    private boolean noTigerPenBelowEvenNotFlippedTile(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getTigerCount() == 0
                && gameBoardPositionArray[colPos - 1][rowPos - 1].getTigerCount() == 0
                && gameBoardPositionArray[colPos][rowPos - 1].getTigerCount() == 0;
    }

    private boolean noTigerPenBelowOddNotFlippedTile(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getTigerCount() == 0
                && gameBoardPositionArray[colPos][rowPos - 1].getTigerCount() == 0
                && gameBoardPositionArray[colPos + 1][rowPos - 1].getTigerCount() == 0;
    }*/

    private boolean noTotoroBelowEvenFlippedTile(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getTotoroCount() == 0 // not nuking over a totoro
                && gameBoardPositionArray[colPos][rowPos + 1].getTotoroCount() == 0
                && gameBoardPositionArray[colPos - 1][rowPos + 1].getTotoroCount() == 0;
    }

    private boolean noTotoroBelowOddFlippedTile(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getTotoroCount() == 0
                && gameBoardPositionArray[colPos + 1][rowPos + 1].getTotoroCount() == 0
                && gameBoardPositionArray[colPos][rowPos + 1].getTotoroCount() == 0;
    }

    private boolean noTotoroBelowEvenNotFlippedTile(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getTotoroCount() == 0
                && gameBoardPositionArray[colPos - 1][rowPos - 1].getTotoroCount() == 0
                && gameBoardPositionArray[colPos][rowPos - 1].getTotoroCount() == 0;
    }

    private boolean noTotoroBelowOddNotFlippedTile(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getTotoroCount() == 0
                && gameBoardPositionArray[colPos][rowPos - 1].getTotoroCount() == 0
                && gameBoardPositionArray[colPos + 1][rowPos - 1].getTotoroCount() == 0;
    }

    private boolean hexAOfNewTileIsTheVolcano(Tile tileToBePlaced) {
        return tileToBePlaced.getHexA().getTerrainType() == terrainTypes.VOLCANO;
    }

    private boolean hexBOfNewTileIsTheVolcano(Tile tileToBePlaced) {
        if(tileToBePlaced.isFlipped())
            return tileToBePlaced.getHexC().getTerrainType() == terrainTypes.VOLCANO;
        else {
            return tileToBePlaced.getHexB().getTerrainType() == terrainTypes.VOLCANO;
        }
    }

    private boolean hexCOfNewTileIsTheVolcano(Tile tileToBePlaced) {
        if(tileToBePlaced.isFlipped())
            return tileToBePlaced.getHexB().getTerrainType() == terrainTypes.VOLCANO;
        else {
            return tileToBePlaced.getHexC().getTerrainType() == terrainTypes.VOLCANO;
        }
    }

    private boolean hexBelowIsAVolcano(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getTerrainType() == terrainTypes.VOLCANO;
    }

    private boolean noSettlementsGetCompletelyNukedForEvenFlippedTileWithVolcanoAtA(int colPos, int rowPos) {
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

    private boolean noSettlementsGetCompletelyNukedForEvenFlippedTileWithVolcanoAtB(int colPos, int rowPos) {
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

    private boolean noSettlementsGetCompletelyNukedForEvenFlippedTileWithVolcanoAtC(int colPos, int rowPos) {
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

    private boolean noSettlementGetsCompletelyNukedForOddAndFlippedTileWithVolcanoAtA(int colPos, int rowPos) {
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

    private boolean noSettlementGetsCompletelyNukedForOddAndFlippedTileWithVolcanoAtB(int colPos, int rowPos) {
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

    private boolean noSettlementGetsCompletelyNukedForOddAndFlippedTileWithVolcanoAtC(int colPos, int rowPos) {
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

    private boolean noSettlementGetsCompletelyNukedForEvenAndNotFlippedTileWithVolcanoAtA(int colPos, int rowPos) {
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

    private boolean noSettlementGetsCompletelyNukedForEvenAndNotFlippedTileWithVolcanoAtB(int colPos, int rowPos) {
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

    private boolean noSettlementGetsCompletelyNukedForEvenAndNotFlippedTileWithVolcanoAtC(int colPos, int rowPos) {
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

    private boolean noSettlementGetsCompletelyNukedForOddAndNotFlippedTileWithVolcanoAtA(int colPos, int rowPos) {
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

    private boolean noSettlementGetsCompletelyNukedForOddAndNotFlippedTileWithVolcanoAtB(int colPos, int rowPos) {
        if ((getGameBoardPositionSettlementID(new Pair(colPos, rowPos))
                == getGameBoardPositionSettlementID(new Pair(colPos + 1, rowPos - 1))) &&
                (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(new Pair(colPos, rowPos))) == 2)) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(new Pair(colPos, rowPos))) == 1) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(new Pair(colPos + 1, rowPos - 1))) == 1) {
            return false;
        } else {
            return true;
        }
    }

    private boolean noSettlementGetsCompletelyNukedForOddAndNotFlippedTileWithVolcanoAtC(int colPos, int rowPos) {
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

    private void increaseEvenFlippedTileLevelAndUpdateGameBoardPositionArray(int colPos, int rowPos, Tile tileToBePlaced) {
        setHexALevelAndUpdateGameBoard(colPos, rowPos, tileToBePlaced);
        setHexBLevelAndUpdateGameBoard(colPos - 1, rowPos + 1, tileToBePlaced);
        setHexCLevelAndUpdateGameBoard(colPos, rowPos + 1, tileToBePlaced);

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

    private void increaseOddFlippedTileLevelAndUpdateGameBoardPositionArray(int colPos, int rowPos, Tile tileToBePlaced) {
        setHexALevelAndUpdateGameBoard(colPos, rowPos, tileToBePlaced);
        setHexBLevelAndUpdateGameBoard(colPos, rowPos + 1, tileToBePlaced);
        setHexCLevelAndUpdateGameBoard(colPos + 1, rowPos + 1, tileToBePlaced);

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

    private void increaseEvenNotFlippedTileLevelAndUpdateGameBoardPositionArray(int colPos, int rowPos, Tile tileToBePlaced) {
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

    private void increaseOddNotFlippedTileLevelAndUpdateGameBoardPositionArray(int colPos, int rowPos, Tile tileToBePlaced) {
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

    private void setHexALevelAndUpdateGameBoard(int colPos, int rowPos, Tile tileToBePlaced) {
        if(hexToBeNukedHasSettlement(gameBoardPositionArray[colPos][rowPos])) {
            decrementGameBoardSettlementListSize(getGameBoardPositionSettlementID(new Pair(colPos, rowPos)));
        }
        tileToBePlaced.getHexA().setLevel(gameBoardPositionArray[colPos][rowPos].getLevel() + 1);
        gameBoardPositionArray[colPos][rowPos] = tileToBePlaced.getHexA();
    }

    private void setHexBLevelAndUpdateGameBoard(int colPos, int rowPos, Tile tileToBePlaced) {
        if(hexToBeNukedHasSettlement(gameBoardPositionArray[colPos][rowPos])) {
            decrementGameBoardSettlementListSize(getGameBoardPositionSettlementID(new Pair(colPos, rowPos)));
        }
        tileToBePlaced.getHexB().setLevel(gameBoardPositionArray[colPos][rowPos].getLevel() + 1);
        gameBoardPositionArray[colPos][rowPos] = tileToBePlaced.getHexB();
    }

    private void setHexCLevelAndUpdateGameBoard(int colPos, int rowPos, Tile tileToBePlaced) {
        if(hexToBeNukedHasSettlement(gameBoardPositionArray[colPos][rowPos])) {
            decrementGameBoardSettlementListSize(getGameBoardPositionSettlementID(new Pair(colPos, rowPos)));
        }
        tileToBePlaced.getHexC().setLevel(gameBoardPositionArray[colPos][rowPos].getLevel() + 1);
        gameBoardPositionArray[colPos][rowPos] = tileToBePlaced.getHexC();
    }

    private void markHexIfSettlementAtPositionNeedsToBeSplitDueToNuke(int colPos, int rowPos) {
        if(gameBoardPositionNotEmpty(colPos, rowPos) && hexToBeNukedHasSettlement(gameBoardPositionArray[colPos][rowPos])){
            addHexToHexesBuiltOnList(colPos , rowPos);
        }
    }

    private boolean gameBoardPositionNotEmpty(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos] != null;
    }

    private boolean hexToBeNukedHasSettlement(Hex hex) {
        return hex.getSettlementID() != 0;
    }



    void buildSettlement(int colPos, int rowPos, Player playerBuilding) {
        if (isValidSettlementLocation(colPos, rowPos) && playerBuilding.getSettlerCount() >= 1) {
            gameBoardPositionArray[colPos][rowPos].setSettlerCount(1);

            int newSettlementID = getNewestAssignableSettlementID();
            setGameBoardPositionSettlementID(new Pair(colPos, rowPos) ,newSettlementID);

            assignPlayerNewSettlement(playerBuilding, newSettlementID, 1);
            addHexToHexesBuiltOnList(colPos, rowPos);

            gameBoardPositionArray[colPos][rowPos].setPlayerID(playerBuilding.getPlayerID());

            playerBuilding.increaseSettlementCount();
            playerBuilding.decreaseSettlerCount(1);
            playerBuilding.increaseScore(1);

            mergeSettlements();
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

    private boolean isOnLevelOne(int colPos, int rowPos) {
        if (gameBoardPositionArray[colPos][rowPos].getLevel() == 1) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isHabitable(int colPos, int rowPos) {
        if (gameBoardPositionArray[colPos][rowPos].getTerrainType().getHabitablity() == 1) {
            return true;
        } else {
            return false;
        }
    }



    void placeTotoroSanctuary(int colPos, int rowPos, int settlementID, Player playerBuilding) {
        if(isValidTotoroPlacement(colPos, rowPos, settlementID, playerBuilding)) {
            gameBoardPositionArray[colPos][rowPos].setTotoroCount(1);
            gameBoardPositionArray[colPos][rowPos].setPlayerID(playerBuilding.getPlayerID());
            gameBoardPositionArray[colPos][rowPos].setSettlementID(settlementID);
            playerBuilding.increaseScore(200);
            playerBuilding.decreaseTotoroCount();
            incrementGameBoardSettlementListSize(settlementID);
            incrementGameBoardSettlementListTotoroCount(settlementID);

            addHexToHexesBuiltOnList(colPos, rowPos);
            mergeSettlements();
        }
    }

    boolean isValidTotoroPlacement(int colPos, int rowPos, int settlementID, Player playerBuilding) {
        try {
            if(gameBoardPositionArray[colPos][rowPos].getTerrainType() == terrainTypes.VOLCANO) {
                return false;
            }
            if(isBuiltOn(colPos, rowPos)) {
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

        if(isEven(rowPos)) {
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



    void placeTigerPen(int colPos, int rowPos, int settlementID, Player playerBuilding) {
        if(checkIfValidTigerPlacement(colPos, rowPos, settlementID, playerBuilding)) {
            gameBoardPositionArray[colPos][rowPos].setTigerCount(1);
            gameBoardPositionArray[colPos][rowPos].setPlayerID(playerBuilding.getPlayerID());
            gameBoardPositionArray[colPos][rowPos].setSettlementID(settlementID);
            playerBuilding.increaseScore(75);
            playerBuilding.decreaseTigerCount();
            incrementGameBoardSettlementListSize(settlementID);
            incrementGameBoardSettlementListTigerCount(settlementID);

            addHexToHexesBuiltOnList(colPos, rowPos);
            mergeSettlements();
        }
    }

    boolean checkIfValidTigerPlacement(int colPos, int rowPos, int settlementID, Player playerBuilding) {
        try {
            if(gameBoardPositionArray[colPos][rowPos].getTerrainType() == terrainTypes.VOLCANO) {
                return false;
            }
            if(isBuiltOn(colPos, rowPos)) {
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

        if(isEven(rowPos)) {
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



    private boolean hexToPlaceSpecialPieceOnEvenRowIsNotAdjacentToSpecifiedSettlement(int colPos, int rowPos, int settlementID) {
        return !(adjacentHexToSpecialPieceIsNotNullAndIsCorrectSettlementID(colPos-1, rowPos-1, settlementID)
                || adjacentHexToSpecialPieceIsNotNullAndIsCorrectSettlementID(colPos, rowPos-1, settlementID)
                || adjacentHexToSpecialPieceIsNotNullAndIsCorrectSettlementID(colPos+1, rowPos, settlementID)
                || adjacentHexToSpecialPieceIsNotNullAndIsCorrectSettlementID(colPos, rowPos+1, settlementID)
                || adjacentHexToSpecialPieceIsNotNullAndIsCorrectSettlementID(colPos-1, rowPos+1, settlementID)
                || adjacentHexToSpecialPieceIsNotNullAndIsCorrectSettlementID(colPos-1, rowPos, settlementID));
    }

    private boolean hexToPlaceSpecialPieceOnOddRowIsNotAdjacentToSpecifiedSettlement(int colPos, int rowPos, int settlementID) {
        return !(adjacentHexToSpecialPieceIsNotNullAndIsCorrectSettlementID(colPos, rowPos-1, settlementID)
                || adjacentHexToSpecialPieceIsNotNullAndIsCorrectSettlementID(colPos+1, rowPos-1, settlementID)
                || adjacentHexToSpecialPieceIsNotNullAndIsCorrectSettlementID(colPos+1, rowPos, settlementID)
                || adjacentHexToSpecialPieceIsNotNullAndIsCorrectSettlementID(colPos+1, rowPos+1, settlementID)
                || adjacentHexToSpecialPieceIsNotNullAndIsCorrectSettlementID(colPos, rowPos+1, settlementID)
                || adjacentHexToSpecialPieceIsNotNullAndIsCorrectSettlementID(colPos-1, rowPos, settlementID));
    }

    private boolean adjacentHexToSpecialPieceIsNotNullAndIsCorrectSettlementID(int colPos, int rowPos, int settlementID) {
        try {
            if (getGameBoardPositionSettlementID(new Pair(colPos, rowPos)) != settlementID) {
                return false;
            }
        } catch (NullPointerException e) {
            return false; // false for null hexes
        }
        return true;
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
                if(isBuiltOn(colPos, rowPos)) {
                    if(gameBoardHexIsOwnedByPlayer(player.getPlayerID(), colPos, rowPos)) {
                        int homeHexID = getGameBoardPositionSettlementID(new Pair(colPos, rowPos));

                        markGameBoardHexAsTraversed(new Pair(colPos, rowPos));

                        hexesToResetTraversalValue.add(new Pair(colPos, rowPos));

                        if (isEven(rowPos)) {
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
        mergeSettlements();
    }

    private void expandSettlementDriver(int colPos, int rowPos, terrainTypes expansionType, Player player, int homeHexID) {
        try{
            if(hexHasNotBeenTraversedYet(colPos, rowPos)) {
                if (isBuiltOn(colPos, rowPos)) {
                    if (gameBoardHexIsOwnedByPlayer(player.getPlayerID(), colPos, rowPos)) {
                        markGameBoardHexAsTraversed(new Pair(colPos, rowPos));

                        hexesToResetTraversalValue.add(new Pair(colPos, rowPos));

                        if (getGameBoardPositionSettlementID(new Pair(colPos, rowPos)) == homeHexID) {
                            if (isEven(rowPos)) {
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
                        }
                        return;
                    }
                } else if (isNotBuiltOn(colPos, rowPos)) {
                    if(gameBoardPositionArray[colPos][rowPos].getTerrainType() == expansionType) {
                        Pair currentCoordinates = new Pair(colPos, rowPos);
                        markGameBoardHexAsTraversed(currentCoordinates);

                        hexesToResetTraversalValue.add(currentCoordinates);
                        hexesBuiltOnThisTurn.add(currentCoordinates);

                        int hexLevel = gameBoardPositionArray[colPos][rowPos].getLevel();

                        gameBoardPositionArray[colPos][rowPos].setSettlerCount(hexLevel);
                        setGameBoardPositionSettlementID(currentCoordinates, homeHexID);
                        gameBoardPositionArray[colPos][rowPos].setPlayerID(player.getPlayerID());
                        incrementGameBoardSettlementListSize(homeHexID);

                        if (isEven(rowPos)) {
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
            if(isBuiltOn(colPos, rowPos)) {
                if(gameBoardHexIsOwnedByPlayer(player.getPlayerID(), colPos, rowPos)) {
                    int returnVal = 0;
                    int homeHexID = gameBoardPositionArray[colPos][rowPos].getSettlementID();

                    markGameBoardHexAsTraversed(new Pair(colPos, rowPos));
                    hexesToResetTraversalValue.add(new Pair(colPos, rowPos));

                    if (isEven(rowPos)) {
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

    private int calculateVillagersForExpansionDriver(int colPos, int rowPos, terrainTypes expansionType, Player player, int homeHexID) {
        try{
            int returnVal = 0;
            if(hexHasNotBeenTraversedYet(colPos, rowPos)) {
                if (isBuiltOn(colPos, rowPos)) { // if hex is built on
                    if (gameBoardHexIsOwnedByPlayer(player.getPlayerID(), colPos, rowPos)) {
                        markGameBoardHexAsTraversed(new Pair(colPos, rowPos));
                        hexesToResetTraversalValue.add(new Pair(colPos, rowPos));

                        if(getGameBoardPositionSettlementID(new Pair(colPos, rowPos)) == homeHexID) {
                            if (isEven(rowPos)) {
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
                        }
                        return returnVal;
                    }
                } else if (isNotBuiltOn(colPos, rowPos)) {
                    if(gameBoardPositionArray[colPos][rowPos].getTerrainType() == expansionType) {
                        markGameBoardHexAsTraversed(new Pair(colPos, rowPos));
                        hexesToResetTraversalValue.add(new Pair(colPos, rowPos));

                        int hexLevel = gameBoardPositionArray[colPos][rowPos].getLevel();
                        returnVal += hexLevel; // number of meeples needed to expand onto hex

                        if (isEven(rowPos)) {
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

    private int calculateScoreForExpansion(int colPos, int rowPos, terrainTypes expansionType, Player player) {
        try {
            if(isBuiltOn(colPos, rowPos)) {
                if(gameBoardHexIsOwnedByPlayer(player.getPlayerID(), colPos, rowPos)) {
                    int returnVal = 0;
                    int homeHexID = getGameBoardPositionSettlementID(new Pair(colPos, rowPos));

                    markGameBoardHexAsTraversed(new Pair(colPos, rowPos));
                    hexesToResetTraversalValue.add(new Pair(colPos, rowPos));

                    if (isEven(rowPos)) {
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

    private int calculateScoreForExpansionDriver(int colPos, int rowPos, terrainTypes expansionType, Player player, int homeHexID) {
        try{
            int returnVal = 0;
            if(hexHasNotBeenTraversedYet(colPos, rowPos)) {
                if (isBuiltOn(colPos, rowPos)) {
                    if (gameBoardHexIsOwnedByPlayer(player.getPlayerID(), colPos, rowPos)) {
                        markGameBoardHexAsTraversed(new Pair(colPos, rowPos));
                        hexesToResetTraversalValue.add(new Pair(colPos, rowPos));

                        if(getGameBoardPositionSettlementID(new Pair(colPos, rowPos)) == homeHexID) {
                            if (isEven(rowPos)) {
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
                        }
                        return returnVal;
                    }
                } else if (isNotBuiltOn(colPos, rowPos)) {
                    if(gameBoardPositionArray[colPos][rowPos].getTerrainType() == expansionType) {
                        gameBoardPositionArray[colPos][rowPos].setIfAlreadyTraversed(true);
                        markGameBoardHexAsTraversed(new Pair(colPos, rowPos));
                        hexesToResetTraversalValue.add(new Pair(colPos, rowPos));

                        int hexLevel = gameBoardPositionArray[colPos][rowPos].getLevel();
                        returnVal += (hexLevel * hexLevel); // number of meeples needed to expand onto hex

                        if (isEven(rowPos)) {
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



    private void splitSettlements() {
        if(hexesBuiltOnThisTurn.isEmpty()) {
            resetTraversalList();
            return;
        }

        Pair currentCoordinates = hexesBuiltOnThisTurn.lastElement();
        hexesBuiltOnThisTurn.remove(hexesBuiltOnThisTurn.size()-1);
        markGameBoardHexAsTraversed(currentCoordinates);
        hexesToResetTraversalValue.add(currentCoordinates);

        int playerID = getGameBoardPositionPlayerID(currentCoordinates);
        int oldSettlementID = getGameBoardPositionSettlementID(currentCoordinates);
        int masterSettlementID = getNewestAssignableSettlementID();

        setPlayerOwnedSettlementsListIsOwned(masterSettlementID, playerID); // add player ownership for new settlement
        setGameBoardPositionSettlementID(currentCoordinates, masterSettlementID);
        setGameBoardSettlementListPlayerID(masterSettlementID, playerID);

        updateGameBoardSettlementListValuesWhenThereIsAPieceAtPosition(currentCoordinates, playerID, oldSettlementID, masterSettlementID);

        recursivelyAddAdjacentHexesToNewlySplitSettlement(currentCoordinates, playerID, masterSettlementID);

        splitSettlements();
    }

    private void splitSettlementsDriver(int masterSettlementID, int playerID, int colPos, int rowPos) {
        try {
            if(hexHasNotBeenTraversedYet(colPos, rowPos)) {
                if (gameBoardHexIsOwnedByPlayer(playerID, colPos, rowPos)) {
                    Pair currentCoordinates = new Pair(colPos, rowPos);
                    markGameBoardHexAsTraversed(currentCoordinates);
                    hexesToResetTraversalValue.add(currentCoordinates);

                    try {
                        markCurrentHexForRemovalFromBuiltOnThisTurnList(currentCoordinates);
                        deleteFromHexesBuiltOnThisTurn();
                    }
                    catch (NullPointerException e) {}

                    int oldSettlementID = getGameBoardPositionSettlementID(currentCoordinates);
                    setGameBoardPositionSettlementID(currentCoordinates, masterSettlementID);

                    updateGameBoardSettlementListValuesInDriverWhenThereIsAPieceAtPosition(currentCoordinates.getColumnPosition(), currentCoordinates.getRowPosition(), masterSettlementID, playerID, oldSettlementID);
                    recursivelyAddAdjacentHexesToNewlySplitSettlement(currentCoordinates, playerID, masterSettlementID);

                    unmarkGameBoardPositionAsTraversed(currentCoordinates.getColumnPosition(), currentCoordinates.getRowPosition());
                }
            }
        }
        catch(NullPointerException e) {return;} // end search at null hex

        return;
    }

    private void recursivelyAddAdjacentHexesToNewlySplitSettlement(Pair currentCoordinates, int playerID, int masterSettlementID) {
        if(isEven(currentCoordinates.getRowPosition())) {
            recursivelyMoveToAdjacentHexesOfNewlySplitSettlementForEvenCoordinates(currentCoordinates, playerID, masterSettlementID);
        } else {
            recursivelyMoveTodjacentHexesOfNewlySplitSettlementForOddCoordinates(masterSettlementID, playerID, currentCoordinates);
        }
    }

    private void recursivelyMoveToAdjacentHexesOfNewlySplitSettlementForEvenCoordinates(Pair currentCoordinates, int playerID, int masterSettlementID) {
        splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()-1, currentCoordinates.getRowPosition()-1);
        splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition(), currentCoordinates.getRowPosition()-1);
        splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()+1, currentCoordinates.getRowPosition());
        splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition(), currentCoordinates.getRowPosition()+1);
        splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()-1, currentCoordinates.getRowPosition()+1);
        splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()-1, currentCoordinates.getRowPosition());
    }

    private void recursivelyMoveTodjacentHexesOfNewlySplitSettlementForOddCoordinates(int masterSettlementID, int playerID, Pair currentCoordinates) {
        splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition(), currentCoordinates.getRowPosition()-1);
        splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()+1, currentCoordinates.getRowPosition()-1);
        splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()+1, currentCoordinates.getRowPosition());
        splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()+1, currentCoordinates.getRowPosition()+1);
        splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition(), currentCoordinates.getRowPosition()+1);
        splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()-1, currentCoordinates.getRowPosition());
    }

    private void updateGameBoardSettlementListValuesWhenThereIsAPieceAtPosition(Pair currentCoordinates, int playerID, int oldSettlementID, int masterSettlementID) {
        if (hexHasSettlersOnIt(currentCoordinates)) {
            incrementGameBoardSettlementListSize(masterSettlementID);
            if(getGameBoardSettlementListSettlementSize(oldSettlementID) == 1){
                deleteSettlementFromGame(oldSettlementID, playerID);
            } else {
                decrementGameBoardSettlementListSize(oldSettlementID);
            }
        } else if (hexHasTotoroOnIt(currentCoordinates)) {
            incrementGameBoardSettlementListSize(masterSettlementID);
            incrementGameBoardSettlementListTotoroCount(masterSettlementID);
            if(getGameBoardSettlementListSettlementSize(oldSettlementID) == 1){
                deleteSettlementFromGame(oldSettlementID, playerID);
            } else {
                decrementGameBoardSettlementListSize(oldSettlementID);
                decrementGameBoardSettlementListTotoroCount(oldSettlementID);
            }
        } else if (hexHasTigerOnIt(currentCoordinates)) {
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

    private void updateGameBoardSettlementListValuesInDriverWhenThereIsAPieceAtPosition(int colPos, int rowPos, int masterSettlementID, int playerID, int oldSettlementID) {
        if (hexHasSettlersOnIt(new Pair(colPos, rowPos))) {
            incrementGameBoardSettlementListSize(masterSettlementID);

            if(getGameBoardSettlementListSettlementSize(oldSettlementID) == 1){
                deleteSettlementFromGame(oldSettlementID, playerID);
            }
            else {
                decrementGameBoardSettlementListSize(oldSettlementID);
            }
        }
        else if (hexHasTotoroOnIt(new Pair(colPos, rowPos))) {
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
        else if (hexHasTigerOnIt(new Pair(colPos, rowPos))) {
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



    void mergeSettlements() {
        if(hexesBuiltOnThisTurn.isEmpty()) {
            resetTraversalList();
            return;
        }

        Pair currentCoordinates = hexesBuiltOnThisTurn.lastElement();
        hexesBuiltOnThisTurn.remove(hexesBuiltOnThisTurn.size()-1);
        markGameBoardHexAsTraversed(currentCoordinates);
        int playerID = getGameBoardPositionPlayerID(currentCoordinates);

        hexesToResetTraversalValue.add(currentCoordinates);

        int masterSettlementID = getGameBoardPositionSettlementID(currentCoordinates);

        recursivelyAddAdjacentHexesToNewlyMergeSettlement(currentCoordinates, playerID, masterSettlementID);

        mergeSettlements();
    }

    private void mergeSettlementsDriver(int masterSettlementID, int playerID, int colPos, int rowPos) {
        try { // is not null
            if(hexHasNotBeenTraversedYet(colPos, rowPos)) {
                if (isBuiltOn(colPos, rowPos)) {
                    if (gameBoardHexIsOwnedByPlayer(playerID, colPos, rowPos)) {
                        markGameBoardHexAsTraversed(new Pair(colPos, rowPos));

                        Pair currentCoordinates = new Pair(colPos, rowPos);
                        hexesToResetTraversalValue.add(currentCoordinates);

                        try {
                            markCurrentHexForRemovalFromBuiltOnThisTurnList(currentCoordinates);
                            deleteFromHexesBuiltOnThisTurn();
                        } catch (NullPointerException e) {}

                        addHexToNewlyMergedSettlement(masterSettlementID, playerID, colPos, rowPos);
                        recursivelyAddAdjacentHexesToNewlyMergeSettlement(currentCoordinates, playerID, masterSettlementID);
                    }
                }
            }
        }
        catch(NullPointerException e) {return;} // end search if we reach null hex

        return;
    }

    private void recursivelyAddAdjacentHexesToNewlyMergeSettlement(Pair currentCoordinates, int playerID, int masterSettlementID) {
        if(isEven(currentCoordinates.getRowPosition())) {
            recursivelyAddAdjacentHexesToNewlyMergeSettlementForEvenCoordinates(currentCoordinates, playerID, masterSettlementID);
        }
        else {
            recursivelyAddAdjacentHexesToNewlyMergeSettlementForOddCoordinates(masterSettlementID, playerID, currentCoordinates);
        }
    }

    private void recursivelyAddAdjacentHexesToNewlyMergeSettlementForEvenCoordinates(Pair currentCoordinates, int playerID, int masterSettlementID) {
        mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()-1, currentCoordinates.getRowPosition()-1);
        mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition(), currentCoordinates.getRowPosition()-1);
        mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()+1, currentCoordinates.getRowPosition());
        mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition(), currentCoordinates.getRowPosition()+1);
        mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()-1, currentCoordinates.getRowPosition()+1);
        mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()-1, currentCoordinates.getRowPosition());
    }

    private void recursivelyAddAdjacentHexesToNewlyMergeSettlementForOddCoordinates(int masterSettlementID, int playerID, Pair currentCoordinates) {
        mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition(), currentCoordinates.getRowPosition()-1);
        mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()+1, currentCoordinates.getRowPosition()-1);
        mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()+1, currentCoordinates.getRowPosition());
        mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()+1, currentCoordinates.getRowPosition()+1);
        mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition(), currentCoordinates.getRowPosition()+1);
        mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()-1, currentCoordinates.getRowPosition());
    }

    private void addHexToNewlyMergedSettlement(int masterSettlementID, int playerID, int colPos, int rowPos) {
        if(getGameBoardPositionSettlementID(new Pair(colPos, rowPos)) != masterSettlementID) { // if we are not at a part of the master settlement, we need to decrement the values of the settlement currently
            // here, increment the necessary values for the master settlement and update the hex settlementID
            if (getGameBoardSettlementListSettlementSize(getGameBoardPositionSettlementID(new Pair(colPos, rowPos))) == 1) { // if the settlement we are currently merging is of size 1,
                // delete that settlement from player list and game list
                deleteSettlementFromGame(getGameBoardPositionSettlementID(new Pair(colPos, rowPos)), playerID); // delete settlement from game
            } else { // else just decrement values in list of that settlement for size/totoro/tiger pen
                if (hexHasSettlersOnIt(new Pair(colPos, rowPos))) {
                    decrementGameBoardSettlementListSize(getGameBoardPositionSettlementID(new Pair(colPos, rowPos)));
                } else if (hexHasTotoroOnIt(new Pair(colPos, rowPos))) {
                    decrementGameBoardSettlementListTotoroCount(getGameBoardPositionSettlementID(new Pair(colPos, rowPos)));
                    decrementGameBoardSettlementListSize(getGameBoardPositionSettlementID(new Pair(colPos, rowPos)));
                } else if (hexHasTigerOnIt(new Pair(colPos, rowPos))) {
                    decrementGameBoardSettlementListTigerCount(getGameBoardPositionSettlementID(new Pair(colPos, rowPos)));
                    decrementGameBoardSettlementListSize(getGameBoardPositionSettlementID(new Pair(colPos, rowPos)));
                }
            }

            setGameBoardPositionSettlementID(new Pair(colPos, rowPos), masterSettlementID);

            if (hexHasSettlersOnIt(new Pair(colPos, rowPos))) { // update newly acquired hex with master settlement values and vice versa
                incrementGameBoardSettlementListSize(masterSettlementID);
            } else if (hexHasTotoroOnIt(new Pair(colPos, rowPos))) {
                incrementGameBoardSettlementListTotoroCount(masterSettlementID);
                incrementGameBoardSettlementListSize(masterSettlementID);
            } else if (hexHasTigerOnIt(new Pair(colPos, rowPos))) {
                incrementGameBoardSettlementListTigerCount(masterSettlementID);
                incrementGameBoardSettlementListSize(masterSettlementID);
            }
        }
    }



    boolean areFiveSettlersInALine(int colPos, int rowPos, int playerID){
        for(int i = 1; i<=5;i++){
            if(gameBoardPositionArray[colPos-1][rowPos].getPlayerID()!=playerID){ //owned by player
                if(gameBoardPositionArray[colPos-i][rowPos].getSettlerCount() < 1){ //has a settler
                    return false;
                }
            }
        }
        return true;
    } // when would 5 totoros be in a line?



    // General purpose functions go here (smaller functions commonly found across multiple functions)

    private void incrementGameBoardTileID() {
        this.gameBoardTileID += 1;
    }

    private void incrementGameBoardHexID(int hexAmount) {
        this.gameBoardHexID += hexAmount;
    }

    private boolean tileIsEvenAndFlipped(int rowPos, Tile tileToBePlaced) {
        return (tileToBePlaced.isFlipped() && isEven(rowPos));
    }

    private boolean tileIsOddAndFlipped(int rowPos, Tile tileToBePlaced) {
        return (tileToBePlaced.isFlipped() && !isEven(rowPos));
    }

    private boolean tileIsEvenAndNotFlipped(int rowPos, Tile tileToBePlaced) {
        return (!tileToBePlaced.isFlipped() && isEven(rowPos));
    }

    private boolean tileIsOddAndNotFlipped(int rowPos, Tile tileToBePlaced) {
        return (!tileToBePlaced.isFlipped() && !isEven(rowPos));
    }

    Hex[][] getGameBoardPositionArray() {
        return gameBoardPositionArray;
    }

    int[][] getValidPlacementArray() {
        return validPlacementArray;
    }

    private int getGameBoardPositionSettlementID(Pair currentCoordinates) {
        return gameBoardPositionArray[currentCoordinates.getColumnPosition()][currentCoordinates.getRowPosition()].getSettlementID();
    }

    private int getGameBoardPositionPlayerID(Pair currentCoordinates) {
        return gameBoardPositionArray[currentCoordinates.getColumnPosition()][currentCoordinates.getRowPosition()].getPlayerID();
    }

    private void setGameBoardPositionSettlementID(Pair currentCoordinates, int masterSettlementID) {
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

    Vector<Pair> getHexesBuiltOnThisTurn() {
        return hexesBuiltOnThisTurn;
    }

    private boolean gameBoardHexIsOwnedByPlayer(int playerID, int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getPlayerID() == playerID;
    }

    private boolean isEven(int rowPos) {
        return rowPos % 2 == 0;
    }

    private int getNewestAssignableSettlementID() {
        for (int i = 1; i < 256; i++) { // Note: Settlement ID of 0 denotes a hex with no settlement on it - never use this ID
            if (usedSettlementIDs[i] == 0) {
                usedSettlementIDs[i] = 1;
                return i;
            }
        }
        throw new RuntimeException("error: no available settlements found");
    }

    private void assignPlayerNewSettlement(Player owningPlayer, int settlementID, int settlementSize) {
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

    private void addHexToHexesBuiltOnList(int colPos, int rowPos) {
        Pair lastHexSettledOn = new Pair(colPos, rowPos);
        hexesBuiltOnThisTurn.addElement(lastHexSettledOn);
    }

    private void markGameBoardHexAsTraversed(Pair currentCoordinates) {
        gameBoardPositionArray[currentCoordinates.getColumnPosition()][currentCoordinates.getRowPosition()].setIfAlreadyTraversed(true);
    }

    private boolean unmarkGameBoardPositionAsTraversed(int colPos, int rowPos) {
        return gameBoardPositionArray[rowPos][colPos].setIfAlreadyTraversed(false);
    }

    private boolean hexHasNotBeenTraversedYet(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getIfAlreadyTraversed() == false;
    }

    private void markCurrentHexForRemovalFromBuiltOnThisTurnList(Pair currentCoordinates) {
        int i = 0;
        for(Pair pair : hexesBuiltOnThisTurn) { // try to remove any occurrences of currently seen item in hexesBuiltOnThisTurn to not waste time re-splitting/merging already split/merged settlements
            if(currentCoordinates.getRowPosition() == pair.getRowPosition() && currentCoordinates.getColumnPosition() == pair.getColumnPosition()) {
                hexesBuiltOnThisTurn.elementAt(i).setCoordinates(-1, -1); // mark pair for deletion
            }
            i++;
        }
    }

    private void deleteFromHexesBuiltOnThisTurn() {
        Predicate<Pair> pairPredicate = pair -> pair.getColumnPosition() == -1 && pair.getRowPosition() == -1; // if pair was marked for deletion, delete it
        hexesBuiltOnThisTurn.removeIf(pairPredicate);
    }

    private void resetTraversalList() {
        for(Pair pair : hexesToResetTraversalValue){
            unmarkGameBoardPositionAsTraversed(pair.getRowPosition(), pair.getColumnPosition());
        }
        hexesToResetTraversalValue.clear();
    }

    private void deleteSettlementFromGame(int settlementID, int playerID) {
        this.gameBoardSettlementList[settlementID][0] = 0;
        this.gameBoardSettlementList[settlementID][1] = 0;
        this.gameBoardSettlementList[settlementID][2] = 0;
        this.gameBoardSettlementList[settlementID][3] = 0;
        usedSettlementIDs[settlementID] = 0;
        setPlayerOwnedSettlementsListIsNotOwned(settlementID, playerID); // remove player ownership of settlement
    }

    private void setPlayerOwnedSettlementsListIsNotOwned(int settlementID, int playerID) {
        this.playerOwnedSettlementsList[settlementID][playerID-1] = false;
    }

    int getGameBoardSettlementListOwner(int settlementID) {
        return this.gameBoardSettlementList[settlementID][0];
    }

    int getGameBoardSettlementListSettlementSize(int settlementID) {
        return this.gameBoardSettlementList[settlementID][1];
    }

    int getGameBoardSettlementListTotoroCount(int settlementID) {
        return this.gameBoardSettlementList[settlementID][2];
    }

    int getGameBoardSettlementListTigerCount(int settlementID) {
        return this.gameBoardSettlementList[settlementID][3];
    }

    private void incrementGameBoardSettlementListSize(int settlementID) {
        this.gameBoardSettlementList[settlementID][1] += 1;
    }

    private void decrementGameBoardSettlementListSize(int settlementID) {
        gameBoardSettlementList[settlementID][1] -= 1;
    }

    private void incrementGameBoardSettlementListTotoroCount(int settlementID) {
        this.gameBoardSettlementList[settlementID][2] += 1;
    }

    private void decrementGameBoardSettlementListTotoroCount(int settlementID) {
        this.gameBoardSettlementList[settlementID][2] -= 1;
    }

    private void incrementGameBoardSettlementListTigerCount(int settlementID) {
        this.gameBoardSettlementList[settlementID][3] += 1;
    }

    private void decrementGameBoardSettlementListTigerCount(int settlementID) {
        this.gameBoardSettlementList[settlementID][3] -= 1;
    }

    int findAdjacentSettlementWithoutTotoro(int colPos, int rowPos){
        if(isEven(rowPos)){
            int setID;
            try{
                setID = getGameBoardPositionSettlementID(new Pair(colPos-1, rowPos-1));
                if(gameBoardSettlementList[setID][2]==0 && setID != 0){return setID;}
            }catch(Exception e){}
            try{
                setID = getGameBoardPositionSettlementID(new Pair(colPos, rowPos-1));
                if(gameBoardSettlementList[setID][2]==0 && setID != 0){return setID;}
            }catch(Exception e){}
            try{
                setID = getGameBoardPositionSettlementID(new Pair(colPos-1, rowPos));
                if(gameBoardSettlementList[setID][2]==0 && setID != 0){return setID;}
            }catch(Exception e){}
            try{
                setID = getGameBoardPositionSettlementID(new Pair(colPos+1, rowPos));
                if(gameBoardSettlementList[setID][2]==0 && setID != 0){return setID;}
            }catch(Exception e){}
            try{
                setID = getGameBoardPositionSettlementID(new Pair(colPos-1, rowPos+1));
                if(gameBoardSettlementList[setID][2]==0 && setID != 0){return setID;}
            }catch(Exception e){}
            try{
                setID = getGameBoardPositionSettlementID(new Pair(colPos, rowPos+1));
                if(gameBoardSettlementList[setID][2]==0 && setID != 0){return setID;}
            }catch(Exception e){}
        }else{
            int setID;
            try{
                setID = getGameBoardPositionSettlementID(new Pair(colPos, rowPos-1));
                if(gameBoardSettlementList[setID][2]==0 && setID != 0){return setID;}
            }catch(Exception e){}
            try{
                setID = getGameBoardPositionSettlementID(new Pair(colPos+1, rowPos-1));
                if(gameBoardSettlementList[setID][2]==0 && setID != 0){return setID;}
            }catch(Exception e){}
            try{
                setID = getGameBoardPositionSettlementID(new Pair(colPos-1, rowPos));
                if(gameBoardSettlementList[setID][2]==0 && setID != 0){return setID;}
            }catch(Exception e){}
            try{
                setID = getGameBoardPositionSettlementID(new Pair(colPos+1, rowPos));
                if(gameBoardSettlementList[setID][2]==0 && setID != 0){return setID;}
            }catch(Exception e){}
            try{
                setID = getGameBoardPositionSettlementID(new Pair(colPos, rowPos+1));
                if(gameBoardSettlementList[setID][2]==0 && setID != 0){return setID;}
            }catch(Exception e){}
            try{
                setID = getGameBoardPositionSettlementID(new Pair(colPos+1, rowPos+1));
                if(gameBoardSettlementList[setID][2]==0 && setID != 0){return setID;}
            }catch(Exception e){}
        }
        return -1;
    }

    int findAdjacentSettlementWithoutTiger(int colPos, int rowPos){
        if(isEven(rowPos)){
            int setID;
            try{
                setID = getGameBoardPositionSettlementID(new Pair(colPos-1, rowPos-1));
                if(gameBoardSettlementList[setID][3]==0 && setID != 0){return setID;}
            }catch(Exception e){}
            try{
                setID = getGameBoardPositionSettlementID(new Pair(colPos, rowPos-1));
                if(gameBoardSettlementList[setID][3]==0 && setID != 0){return setID;}
            }catch(Exception e){}
            try{
                setID = getGameBoardPositionSettlementID(new Pair(colPos-1, rowPos));
                if(gameBoardSettlementList[setID][3]==0 && setID != 0){return setID;}
            }catch(Exception e){}
            try{
                setID = getGameBoardPositionSettlementID(new Pair(colPos-1, rowPos));
                if(gameBoardSettlementList[setID][3]==0 && setID != 0){return setID;}
            }catch(Exception e){}
            try{
                setID = getGameBoardPositionSettlementID(new Pair(colPos-1, rowPos+1));
                if(gameBoardSettlementList[setID][3]==0 && setID != 0){return setID;}
            }catch(Exception e){}
            try{
                setID = getGameBoardPositionSettlementID(new Pair(colPos, rowPos+1));
                if(gameBoardSettlementList[setID][3]==0 && setID != 0){return setID;}
            }catch(Exception e){}
        }else{
            int setID;
            try{
                setID = getGameBoardPositionSettlementID(new Pair(colPos, rowPos-1));
                if(gameBoardSettlementList[setID][3]==0 && setID != 0){return setID;}
            }catch(Exception e){}
            try{
                setID = getGameBoardPositionSettlementID(new Pair(colPos+1, rowPos-1));
                if(gameBoardSettlementList[setID][3]==0 && setID != 0){return setID;}
            }catch(Exception e){}
            try{
                setID = getGameBoardPositionSettlementID(new Pair(colPos-1, rowPos));
                if(gameBoardSettlementList[setID][3]==0 && setID != 0){return setID;}
            }catch(Exception e){}
            try{
                setID = getGameBoardPositionSettlementID(new Pair(colPos+1, rowPos));
                if(gameBoardSettlementList[setID][3]==0 && setID != 0){return setID;}
            }catch(Exception e){}
            try{
                setID = getGameBoardPositionSettlementID(new Pair(colPos, rowPos+1));
                if(gameBoardSettlementList[setID][3]==0 && setID != 0){return setID;}
            }catch(Exception e){}
            try{
                setID = getGameBoardPositionSettlementID(new Pair(colPos+1, rowPos+1));
                if(gameBoardSettlementList[setID][3]==0 && setID != 0){return setID;}
            }catch(Exception e){}
        }
        return -1;
    }


    private boolean hexHasSettlersOnIt(Pair currentCoordinates) {
        return gameBoardPositionArray[currentCoordinates.getColumnPosition()][currentCoordinates.getRowPosition()].getSettlerCount() != 0;
    }

    private boolean hexHasTotoroOnIt(Pair currentCoordinates) {
        return gameBoardPositionArray[currentCoordinates.getColumnPosition()][currentCoordinates.getRowPosition()].getTotoroCount() == 1;
    }

    private boolean hexHasTigerOnIt(Pair currentCoordinates) {
        return gameBoardPositionArray[currentCoordinates.getColumnPosition()][currentCoordinates.getRowPosition()].getTigerCount() == 1;
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

    private boolean isNotBuiltOn(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].isNotBuiltOn();
    }

    private boolean isBuiltOn(int colPos, int rowPos) {
        return !(isNotBuiltOn(colPos, rowPos));
    }
}