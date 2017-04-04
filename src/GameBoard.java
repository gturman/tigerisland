/*
 * Created by William on 3/14/2017.
 */

import java.util.*;

public class GameBoard {
    private int boardHeight = 202;
    private int boardWidth = 202;
    private int gameBoardTileID;
    private int gameBoardHexID;

    public int[][] validPlacementArray = new int[boardHeight][boardWidth];
    public Hex[][] gameBoardPositionArray = new Hex[boardHeight][boardWidth];
    public int[][] gameBoardSettlementList = new int[256][4];
    public int[] usedSettlementIDs = new int[256];

    public Vector<Pair> hexesBuiltOnThisTurn = new Vector();
    private int lastBuiltSettlementID;

    GameBoard() {
        this.gameBoardTileID = 1;
        this.gameBoardHexID = 1;
    }

    void placeFirstTileAndUpdateValidPlacementList() {
        updateGameBoardPositionArrayWithFirstTileHexes();

        int hexAmount = 5;

        incrementGameBoardHexID(hexAmount);
        incrementGameBoardTileID();

        updateValidPlacementArrayWithFirstTile();
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

    void updateGameBoardPositionArrayWithFirstTileHexes() {
        gameBoardPositionArray[102][102] = new Hex(1, 1, terrainTypes.VOLCANO);
        gameBoardPositionArray[101][101] = new Hex(2, 1, terrainTypes.JUNGLE);
        gameBoardPositionArray[102][101] = new Hex(3, 1, terrainTypes.LAKE);
        gameBoardPositionArray[101][103] = new Hex(4, 1, terrainTypes.ROCKY);
        gameBoardPositionArray[102][103] = new Hex(5, 1, terrainTypes.GRASSLANDS);
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
            createUsableSettlementID();
        }
    }

        void createUsableSettlementID() {
            for(int i = 0; i < 256; i++) {
                if(usedSettlementIDs[i] == 1){
                     resetGameBoardSettlementListValues(i);
                     usedSettlementIDs[i] = 0;
                }
            }
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

            void markHexIfSettlementAtPositionNeedsToBeSplitDueToNuke(int colPos, int rowPos) {
            if(gameBoardPositionNotEmpty(colPos, rowPos) && existsASettlementAtGameBoardPosition(colPos, rowPos)){
                addHexWithSettlementAdjacentToNukeToHexesBuiltOnList(colPos , rowPos);
                setHexAsLastAddedToHexesBuiltOnList(colPos, rowPos);
            }
        }

                void setHexAsLastAddedToHexesBuiltOnList(int colPos, int rowPos) {
                    lastBuiltSettlementID = gameBoardPositionArray[colPos][rowPos].getSettlementID();
                }

                void addHexWithSettlementAdjacentToNukeToHexesBuiltOnList(int colPos, int rowPos) {
                    Pair lastHexSettledOn = new Pair(colPos, rowPos);
                    hexesBuiltOnThisTurn.addElement(lastHexSettledOn);
                }

                boolean existsASettlementAtGameBoardPosition(int colPos, int rowPos) {
                    return gameBoardPositionArray[colPos][rowPos].getSettlementID() != 0;
                }

                boolean gameBoardPositionNotEmpty(int colPos, int rowPos) {
                    return gameBoardPositionArray[colPos][rowPos] != null;
                }

            void setHexALevelAndUpdateGameBoard(int colPos, int rowPos, Tile tileToBePlaced) {
                if(hexToBeNukedHasSettlement(gameBoardPositionArray[colPos][rowPos])) {
                    decrementGameBoardSettlementListSize(gameBoardPositionArray[colPos][rowPos].getSettlementID());
                }
                tileToBePlaced.getHexA().setHexLevel(gameBoardPositionArray[colPos][rowPos].getHexLevel() + 1);
                gameBoardPositionArray[colPos][rowPos] = tileToBePlaced.getHexA();
            }

            void setHexBLevelAndUpdateGameBoard(int colPos, int rowPos, Tile tileToBePlaced) {
                if(hexToBeNukedHasSettlement(gameBoardPositionArray[colPos][rowPos])) {
                    decrementGameBoardSettlementListSize(gameBoardPositionArray[colPos][rowPos].getSettlementID());
                }
                tileToBePlaced.getHexB().setHexLevel(gameBoardPositionArray[colPos][rowPos].getHexLevel() + 1);
                gameBoardPositionArray[colPos][rowPos] = tileToBePlaced.getHexB();
            }

            void setHexCLevelAndUpdateGameBoard(int colPos, int rowPos, Tile tileToBePlaced) {
                if(hexToBeNukedHasSettlement(gameBoardPositionArray[colPos][rowPos])) {
                    decrementGameBoardSettlementListSize(gameBoardPositionArray[colPos][rowPos].getSettlementID());
                }
                tileToBePlaced.getHexC().setHexLevel(gameBoardPositionArray[colPos][rowPos].getHexLevel() + 1);
                gameBoardPositionArray[colPos][rowPos] = tileToBePlaced.getHexC();
            }

                boolean hexToBeNukedHasSettlement(Hex hex) {
                return hex.getSettlementID() != 0;
            }

    //TODO: James will refactor on Thursday
    void splitSettlements() {
        if(hexesBuiltOnThisTurn.isEmpty()) {
            return;
        }

        Pair currentCoordinates = hexesBuiltOnThisTurn.lastElement();
        hexesBuiltOnThisTurn.remove(hexesBuiltOnThisTurn.size()-1);
        gameBoardPositionArray[currentCoordinates.getColumnPosition()][currentCoordinates.getRowPosition()].setIfAlreadyTraversed(true);

        int playerID = gameBoardPositionArray[currentCoordinates.getColumnPosition()][currentCoordinates.getRowPosition()].getPlayerID();

        int oldSettlementID = gameBoardPositionArray[currentCoordinates.getColumnPosition()][currentCoordinates.getRowPosition()].getSettlementID();
        usedSettlementIDs[oldSettlementID] = 1;

        int masterSettlementID;

        do{
            masterSettlementID = getNewestAssignableSettlementID();
        }while(usedSettlementIDs[masterSettlementID] == 1);

        gameBoardPositionArray[currentCoordinates.getColumnPosition()][currentCoordinates.getRowPosition()].setSettlementID(masterSettlementID);

        if (gameBoardPositionArray[currentCoordinates.getColumnPosition()][currentCoordinates.getRowPosition()].getSettlerCount() != 0) { // update newly acquired hex with master settlement values and vice versa
            incrementGameBoardSettlementListSize(masterSettlementID);
        } else if (gameBoardPositionArray[currentCoordinates.getColumnPosition()][currentCoordinates.getRowPosition()].getTotoroCount() == 1) {
            incrementGameBoardSettlementListSize(masterSettlementID);
            incrementGameBoardSettlementListTotoroCount(masterSettlementID);
        } else if (gameBoardPositionArray[currentCoordinates.getColumnPosition()][currentCoordinates.getRowPosition()].getTigerCount() == 1) {
            incrementGameBoardSettlementListSize(masterSettlementID);
            incrementGameBoardSettlementListTigerCount(masterSettlementID);
        }

        if(checkIfEven(currentCoordinates.getRowPosition())) {
            splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()-1, currentCoordinates.getRowPosition()-1);
            splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition(), currentCoordinates.getRowPosition()-1);
            splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()+1, currentCoordinates.getRowPosition());
            splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition(), currentCoordinates.getRowPosition()+1);
            splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()-1, currentCoordinates.getRowPosition()+1);
            splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()-1, currentCoordinates.getRowPosition());
        }
        else {
            splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition(), currentCoordinates.getRowPosition()-1);
            splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()+1, currentCoordinates.getRowPosition()-1);
            splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()+1, currentCoordinates.getRowPosition());
            splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()+1, currentCoordinates.getRowPosition()+1);
            splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition(), currentCoordinates.getRowPosition()+1);
            splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()-1, currentCoordinates.getRowPosition());
        }

        try { // do rest of calculations if possible
            Pair nextCoordinates = hexesBuiltOnThisTurn.lastElement();
            setHexAsLastAddedToHexesBuiltOnList(nextCoordinates.getColumnPosition(), nextCoordinates.getRowPosition());
            splitSettlements();
        }
        catch (Exception e){
            return;
        }

        gameBoardPositionArray[currentCoordinates.getColumnPosition()][currentCoordinates.getRowPosition()].setIfAlreadyTraversed(false);
    }

    //TODO: James will refactor on Thursday
    void splitSettlementsDriver(int masterSettlementID, int playerID, int colPos, int rowPos) {
        try { // is not null
            if(gameBoardPositionArray[colPos][rowPos].getIfAlreadyTraversed() == false) { // hasn't been checked yet
                if (gameBoardPositionArray[colPos][rowPos].getPlayerID() == playerID) { // owned by player
                    gameBoardPositionArray[colPos][rowPos].setIfAlreadyTraversed(true); // mark as traversed

                    Pair currentCoordinates = new Pair(colPos, rowPos);
                    try { // TODO: FIX DELETION
                        Stack<Integer> indicesToRemove = new Stack<>();
                        int i = 0;
                        for(Pair pair : hexesBuiltOnThisTurn) { // try to remove any occurrences of currently seen item in hexesBuiltOnThisTurn
                            if(currentCoordinates.getRowPosition() == pair.getRowPosition() && currentCoordinates.getColumnPosition() == pair.getColumnPosition()) {
                                indicesToRemove.push(i);
                            }
                            i++;
                        }
                        while(!indicesToRemove.isEmpty()) {
                            hexesBuiltOnThisTurn.removeElementAt(indicesToRemove.lastElement());
                            indicesToRemove.pop();
                        }
                    }
                    catch (NullPointerException e) {}

                    gameBoardPositionArray[colPos][rowPos].setSettlementID(masterSettlementID); // set hex settlement ID to new ID

                    if (gameBoardPositionArray[colPos][rowPos].getSettlerCount() != 0) { // update newly acquired hex with master settlement values and vice versa
                        incrementGameBoardSettlementListSize(masterSettlementID);
                    } else if (gameBoardPositionArray[colPos][rowPos].getTotoroCount() == 1) {
                        incrementGameBoardSettlementListSize(masterSettlementID);
                        incrementGameBoardSettlementListTotoroCount(masterSettlementID);
                    } else if (gameBoardPositionArray[colPos][rowPos].getTigerCount() == 1) {
                        incrementGameBoardSettlementListSize(masterSettlementID);
                        incrementGameBoardSettlementListTigerCount(masterSettlementID);
                    }

                    if(checkIfEven(currentCoordinates.getRowPosition())) { // go to next hexes
                        splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()-1, currentCoordinates.getRowPosition()-1);
                        splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition(), currentCoordinates.getRowPosition()-1);
                        splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()+1, currentCoordinates.getRowPosition());
                        splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition(), currentCoordinates.getRowPosition()+1);
                        splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()-1, currentCoordinates.getRowPosition()+1);
                        splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()-1, currentCoordinates.getRowPosition());
                    }
                    else {
                        splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition(), currentCoordinates.getRowPosition()-1);
                        splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()+1, currentCoordinates.getRowPosition()-1);
                        splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()+1, currentCoordinates.getRowPosition());
                        splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()+1, currentCoordinates.getRowPosition()+1);
                        splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition(), currentCoordinates.getRowPosition()+1);
                        splitSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()-1, currentCoordinates.getRowPosition());
                    }

                    gameBoardPositionArray[rowPos][colPos].setIfAlreadyTraversed(false); // un-mark as traversed
                }
            }
        }
        catch(NullPointerException e) {
            return; // end search if we reach null hex
        }

        return;
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

    boolean canValidlyPlaceOddNotFlippedTileInLocation(int colPos, int rowPos) {
        return noEmptySpacesBelowOddNotFlippedTile(colPos, rowPos) &&
                sameHexLevelBelowAsNewOddNotFlippedTile(colPos, rowPos) &&
                notCoveringEntireOddNotFlippedTile(colPos, rowPos) &&
                noTigerPenBelowOddNotFlippedTile(colPos, rowPos) &&
                noTotoroBelowOddNotFlippedTile(colPos, rowPos);
    }

    boolean canValidlyPlaceEvenNotFlippedTileInLocation(int colPos, int rowPos) {
        return noEmptySpacesBelowEvenNotFlippedTile(colPos, rowPos) &&
                sameHexLevelBelowAsNewEvenNotFlippedTile(colPos, rowPos) &&
                notCoveringEntireEvenNotFlippedTile(colPos, rowPos) &&
                noTigerPenBelowEvenNotFlippedTile(colPos, rowPos) &&
                noTotoroBelowEvenNotFlippedTile(colPos, rowPos);
    }

    boolean canValidlyPlaceOddFlippedTileInLocation(int colPos, int rowPos) {
        return noEmptySpacesBelowOddFlippedTile(colPos, rowPos) &&
                sameHexLevelBelowAsNewOddFlippedTile(colPos, rowPos) &&
                notCoveringEntireOddFlippedTile(colPos, rowPos) &&
                noTigerPenBelowOddFlippedTile(colPos, rowPos) &&
                noTotoroBelowOddFlippedTile(colPos, rowPos);
    }

    boolean canValidlyPlaceEvenFlippedTileInLocation(int colPos, int rowPos) {
        return noEmptySpacesBelowEvenFlippedTile(colPos, rowPos) &&
                sameHexLevelBelowAsNewEvenFlippedTile(colPos, rowPos) &&
                notCoveringEntireEvenFlippedTile(colPos, rowPos) &&
                noTigerPenBelowEvenFlippedTile(colPos, rowPos) &&
                noTotoroBelowEvenFlippedTile(colPos, rowPos);
    }

    boolean checkIfNoInvalidSettlementNukingForOddNotFlippedTileWithVolcanoAtC(int colPos, int rowPos) {
        if ((gameBoardPositionArray[colPos][rowPos].getSettlementID()
                == gameBoardPositionArray[colPos][rowPos - 1].getSettlementID()) &&
                (getGameBoardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos].getSettlementID()) == 2)) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos].getSettlementID()) == 1) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos - 1].getSettlementID()) == 1) {
            return false;
        } else {
            return true;
        }
    }

    boolean checkIfNoInvalidSettlementNukingForOddNotFlippedTileWithVolcanoAtB(int colPos, int rowPos) {
        if ((gameBoardPositionArray[colPos][rowPos].getSettlementID()
                == gameBoardPositionArray[colPos + 1][rowPos - 1].getSettlementID()) &&
                (getGameBoardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos + 1].getSettlementID()) == 2)) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos].getSettlementID()) == 1) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(gameBoardPositionArray[colPos + 1][rowPos - 1].getSettlementID()) == 1) {
            return false;
        } else {
            return true;
        }
    }

    boolean checkIfNoInvalidSettlementNukingForOddNotFlippedTileWithVolcanoAtA(int colPos, int rowPos) {
        if ((gameBoardPositionArray[colPos][rowPos - 1].getSettlementID()
                == gameBoardPositionArray[colPos + 1][rowPos - 1].getSettlementID()) &&
                (getGameBoardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos - 1].getSettlementID()) == 2)) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos - 1].getSettlementID()) == 1) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(gameBoardPositionArray[colPos + 1][rowPos - 1].getSettlementID()) == 1) {
            return false;
        } else {
            return true;
        }
    }

    boolean checkIfNoInvalidSettlementNukingForEvenNotFlippedTileWithVolcanoAtC(int colPos, int rowPos) {
        if ((gameBoardPositionArray[colPos][rowPos].getSettlementID()
                == gameBoardPositionArray[colPos - 1][rowPos - 1].getSettlementID()) &&
                (getGameBoardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos].getSettlementID()) == 2)) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos].getSettlementID()) == 1) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(gameBoardPositionArray[colPos - 1][rowPos - 1].getSettlementID()) == 1) {
            return false;
        } else {
            return true;
        }
    }

    boolean checkIfNoInvalidSettlementNukingForEvenNotFlippedTileWithVolcanoAtB(int colPos, int rowPos) {
        if ((gameBoardPositionArray[colPos][rowPos - 1].getSettlementID()
                == gameBoardPositionArray[colPos][rowPos].getSettlementID()) &&
                (getGameBoardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos].getSettlementID()) == 2)) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos].getSettlementID()) == 1) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos - 1].getSettlementID()) == 1) {
            return false;
        } else {
            return true;
        }
    }

    boolean checkIfNoInvalidSettlementNukingForEvenNotFlippedTileWithVolcanoAtA(int colPos, int rowPos) {
        if ((gameBoardPositionArray[colPos][rowPos - 1].getSettlementID()
                == gameBoardPositionArray[colPos - 1][rowPos - 1].getSettlementID()) &&
                (getGameBoardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos - 1].getSettlementID()) == 2)) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos - 1].getSettlementID()) == 1) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(gameBoardPositionArray[colPos - 1][rowPos - 1].getSettlementID()) == 1) {
            return false;
        } else {
            return true;
        }
    }

    boolean checkIfNoInvalidSettlementNukingForOddFlippedTileWithVolcanoAtC(int colPos, int rowPos) {
        if ((gameBoardPositionArray[colPos][rowPos].getSettlementID()
                == gameBoardPositionArray[colPos + 1][rowPos + 1].getSettlementID()) &&
                (getGameBoardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos].getSettlementID()) == 2)) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos].getSettlementID()) == 1) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(gameBoardPositionArray[colPos + 1][rowPos + 1].getSettlementID()) == 1) {
            return false;
        } else {
            return true;
        }
    }

    boolean checkIfNoInvalidSettlementNukingForOddFlippedTileWithVolcanoAtB(int colPos, int rowPos) {
        if ((gameBoardPositionArray[colPos][rowPos].getSettlementID()
                == gameBoardPositionArray[colPos][rowPos + 1].getSettlementID()) &&
                (getGameBoardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos].getSettlementID()) == 2)) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos].getSettlementID()) == 1) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos + 1].getSettlementID()) == 1) {
            return false;
        } else {
            return true;
        }
    }

    boolean checkIfNoInvalidSettlementNukingForOddFlippedTileWithVolcanoAtA(int colPos, int rowPos) {
        if ((gameBoardPositionArray[colPos + 1][rowPos + 1].getSettlementID()
                == gameBoardPositionArray[colPos][rowPos + 1].getSettlementID()) &&
                (getGameBoardSettlementListSettlementSize(gameBoardPositionArray[colPos + 1][rowPos + 1].getSettlementID()) == 2)) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos + 1].getSettlementID()) == 1) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(gameBoardPositionArray[colPos + 1][rowPos + 1].getSettlementID()) == 1) {
            return false;
        } else {
            return true;
        }
    }

    boolean checkIfNoInvalidSettlementNukingForEvenFlippedTileWithVolcanoAtC(int colPos, int rowPos) {
        if ((gameBoardPositionArray[colPos][rowPos + 1].getSettlementID()
                == gameBoardPositionArray[colPos][rowPos].getSettlementID()) &&
                (getGameBoardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos].getSettlementID()) == 2)) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos].getSettlementID()) == 1) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos + 1].getSettlementID()) == 1) {
            return false;
        } else {
            return true;
        }
    }

    boolean checkIfNoInvalidSettlementNukingForEvenFlippedTileWithVolcanoAtB(int colPos, int rowPos) {
        if ((gameBoardPositionArray[colPos][rowPos].getSettlementID()
                == gameBoardPositionArray[colPos - 1][rowPos + 1].getSettlementID()) &&
                (getGameBoardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos].getSettlementID()) == 2)) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos].getSettlementID()) == 1) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(gameBoardPositionArray[colPos - 1][rowPos + 1].getSettlementID()) == 1) {
            return false;
        } else {
            return true;
        }
    }

    boolean checkIfNoInvalidSettlementNukingForEvenFlippedTileWithVolcanoAtA(int colPos, int rowPos) {
        if ((gameBoardPositionArray[colPos][rowPos + 1].getSettlementID()
                == gameBoardPositionArray[colPos - 1][rowPos + 1].getSettlementID()) &&
                (getGameBoardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos + 1].getSettlementID()) == 2)) {  // checking settlement shit
            return false;
        } else if (getGameBoardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos + 1].getSettlementID()) == 1) {
            return false;
        } else if (getGameBoardSettlementListSettlementSize(gameBoardPositionArray[colPos - 1][rowPos + 1].getSettlementID()) == 1) {
            return false;
        } else {
            return true;
        }
    }

    boolean hexBelowIsAVolcano(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getHexTerrainType() == terrainTypes.VOLCANO;
    }

    boolean hexCOfNewTileIsTheVolcano(Tile tileToBePlaced) {
        if(tileToBePlaced.isFlipped())
        return tileToBePlaced.getHexB().getHexTerrainType() == terrainTypes.VOLCANO;
        else {
            return tileToBePlaced.getHexC().getHexTerrainType() == terrainTypes.VOLCANO;
        }
    }

    boolean hexBOfNewTileIsTheVolcano(Tile tileToBePlaced) {
        if(tileToBePlaced.isFlipped())
        return tileToBePlaced.getHexC().getHexTerrainType() == terrainTypes.VOLCANO;
        else {
            return tileToBePlaced.getHexB().getHexTerrainType() == terrainTypes.VOLCANO;
        }
    }

    boolean hexAOfNewTileIsTheVolcano(Tile tileToBePlaced) {
        return tileToBePlaced.getHexA().getHexTerrainType() == terrainTypes.VOLCANO;
    }

    boolean noTotoroBelowOddNotFlippedTile(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getTotoroCount() == 0
                && gameBoardPositionArray[colPos][rowPos - 1].getTotoroCount() == 0
                && gameBoardPositionArray[colPos + 1][rowPos - 1].getTotoroCount() == 0;
    }

    boolean noTotoroBelowEvenNotFlippedTile(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getTotoroCount() == 0
                && gameBoardPositionArray[colPos - 1][rowPos - 1].getTotoroCount() == 0
                && gameBoardPositionArray[colPos][rowPos - 1].getTotoroCount() == 0;
    }

    boolean noTotoroBelowOddFlippedTile(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getTotoroCount() == 0
                && gameBoardPositionArray[colPos + 1][rowPos + 1].getTotoroCount() == 0
                && gameBoardPositionArray[colPos][rowPos + 1].getTotoroCount() == 0;
    }

    boolean noTotoroBelowEvenFlippedTile(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getTotoroCount() == 0 // not nuking over a totoro
                && gameBoardPositionArray[colPos][rowPos + 1].getTotoroCount() == 0
                && gameBoardPositionArray[colPos - 1][rowPos + 1].getTotoroCount() == 0;
    }

    boolean noTigerPenBelowOddNotFlippedTile(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getTigerCount() == 0
                && gameBoardPositionArray[colPos][rowPos - 1].getTigerCount() == 0
                && gameBoardPositionArray[colPos + 1][rowPos - 1].getTigerCount() == 0;
    }

    boolean noTigerPenBelowEvenNotFlippedTile(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getTigerCount() == 0
                && gameBoardPositionArray[colPos - 1][rowPos - 1].getTigerCount() == 0
                && gameBoardPositionArray[colPos][rowPos - 1].getTigerCount() == 0;
    }

    boolean noTigerPenBelowOddFlippedTile(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getTigerCount() == 0
                && gameBoardPositionArray[colPos + 1][rowPos + 1].getTigerCount() == 0
                && gameBoardPositionArray[colPos][rowPos + 1].getTigerCount() == 0;
    }

    boolean noTigerPenBelowEvenFlippedTile(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getTigerCount() == 0 // not nuking over a tiger pen
                && gameBoardPositionArray[colPos][rowPos + 1].getTigerCount() == 0
                && gameBoardPositionArray[colPos - 1][rowPos + 1].getTigerCount() == 0;
    }

    boolean notCoveringEntireOddNotFlippedTile(int colPos, int rowPos) {
        return !(gameBoardPositionArray[colPos][rowPos].getParentTileID() == gameBoardPositionArray[colPos][rowPos - 1].getParentTileID()
                && gameBoardPositionArray[colPos][rowPos].getParentTileID() == gameBoardPositionArray[colPos + 1][rowPos - 1].getParentTileID()
                && gameBoardPositionArray[colPos][rowPos - 1].getParentTileID() == gameBoardPositionArray[colPos + 1][rowPos - 1].getParentTileID());
    }

    boolean notCoveringEntireEvenNotFlippedTile(int colPos, int rowPos) {
        return !(gameBoardPositionArray[colPos][rowPos].getParentTileID() == gameBoardPositionArray[colPos - 1][rowPos - 1].getParentTileID()
                && gameBoardPositionArray[colPos][rowPos].getParentTileID() == gameBoardPositionArray[colPos][rowPos - 1].getParentTileID()
                && gameBoardPositionArray[colPos - 1][rowPos - 1].getParentTileID() == gameBoardPositionArray[colPos][rowPos - 1].getParentTileID());
    }

    boolean notCoveringEntireOddFlippedTile(int colPos, int rowPos) {
        return !(gameBoardPositionArray[colPos][rowPos].getParentTileID() == gameBoardPositionArray[colPos + 1][rowPos + 1].getParentTileID()
                && gameBoardPositionArray[colPos][rowPos].getParentTileID() == gameBoardPositionArray[colPos][rowPos + 1].getParentTileID()
                && gameBoardPositionArray[colPos + 1][rowPos + 1].getParentTileID() == gameBoardPositionArray[colPos][rowPos + 1].getParentTileID());
    }

    boolean notCoveringEntireEvenFlippedTile(int colPos, int rowPos) {
        return !(gameBoardPositionArray[colPos][rowPos].getParentTileID() == gameBoardPositionArray[colPos][rowPos + 1].getParentTileID() // making sure not nuking over same entire tile
                && gameBoardPositionArray[colPos][rowPos].getParentTileID() == gameBoardPositionArray[colPos - 1][rowPos + 1].getParentTileID()
                && gameBoardPositionArray[colPos][rowPos + 1].getParentTileID() == gameBoardPositionArray[colPos - 1][rowPos + 1].getParentTileID());
    }

    boolean sameHexLevelBelowAsNewOddNotFlippedTile(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getHexLevel() == gameBoardPositionArray[colPos][rowPos - 1].getHexLevel()
                && gameBoardPositionArray[colPos][rowPos].getHexLevel() == gameBoardPositionArray[colPos + 1][rowPos - 1].getHexLevel()
                && gameBoardPositionArray[colPos][rowPos - 1].getHexLevel() == gameBoardPositionArray[colPos + 1][rowPos - 1].getHexLevel();
    }

    boolean sameHexLevelBelowAsNewEvenNotFlippedTile(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getHexLevel() == gameBoardPositionArray[colPos - 1][rowPos - 1].getHexLevel()
                && gameBoardPositionArray[colPos][rowPos].getHexLevel() == gameBoardPositionArray[colPos][rowPos - 1].getHexLevel()
                && gameBoardPositionArray[colPos - 1][rowPos - 1].getHexLevel() == gameBoardPositionArray[colPos][rowPos - 1].getHexLevel();
    }

    boolean sameHexLevelBelowAsNewOddFlippedTile(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getHexLevel() == gameBoardPositionArray[colPos + 1][rowPos + 1].getHexLevel()
                && gameBoardPositionArray[colPos][rowPos].getHexLevel() == gameBoardPositionArray[colPos][rowPos + 1].getHexLevel()
                && gameBoardPositionArray[colPos + 1][rowPos + 1].getHexLevel() == gameBoardPositionArray[colPos][rowPos + 1].getHexLevel();
    }

    boolean sameHexLevelBelowAsNewEvenFlippedTile(int colPos, int rowPos) {
        return gameBoardPositionArray[colPos][rowPos].getHexLevel() == gameBoardPositionArray[colPos][rowPos + 1].getHexLevel() //making sure hex level of new tile placing is same as one below
                && gameBoardPositionArray[colPos][rowPos].getHexLevel() == gameBoardPositionArray[colPos - 1][rowPos + 1].getHexLevel()
                && gameBoardPositionArray[colPos][rowPos + 1].getHexLevel() == gameBoardPositionArray[colPos - 1][rowPos + 1].getHexLevel();
    }

    boolean noEmptySpacesBelowOddNotFlippedTile(int colPos, int rowPos) {
        return gameBoardPositionNotEmpty(colPos, rowPos)
                && gameBoardPositionNotEmpty(colPos, rowPos - 1)
                && gameBoardPositionNotEmpty(colPos + 1, rowPos - 1);
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

    boolean noEmptySpacesBelowEvenFlippedTile(int colPos, int rowPos) {
        return gameBoardPositionNotEmpty(colPos, rowPos) // not nuking over empty gameBoard
                && gameBoardPositionNotEmpty(colPos, rowPos + 1)
                && gameBoardPositionNotEmpty(colPos - 1, rowPos + 1);
    }

    void setTileAtPosition(int colPos, int rowPos, Tile tileToBePlaced) {
        if (!checkIfTileCanBePlacedAtPosition(colPos, rowPos, tileToBePlaced)) return;

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

    void setOddNotFlippedCoordinatesAndUpdateGameBoard(int colPos, int rowPos, Tile tileToBePlaced) {
        setHexACoordinateAndUpdateGameBoard(colPos, rowPos, tileToBePlaced);
        setHexBCoordinateAndUpdateGameBoard(colPos, rowPos - 1, tileToBePlaced);
        setHexCCoordinateAndUpdateGameBoard(colPos + 1, rowPos - 1, tileToBePlaced);
    }

    void setEvenNotFlippedCoordinatesAndUpdateGameBoard(int colPos, int rowPos, Tile tileToBePlaced) {
        setHexACoordinateAndUpdateGameBoard(colPos, rowPos, tileToBePlaced);
        setHexBCoordinateAndUpdateGameBoard(colPos - 1, rowPos - 1, tileToBePlaced);
        setHexCCoordinateAndUpdateGameBoard(colPos, rowPos - 1, tileToBePlaced);
    }

    void setOddFlippedCoordinatesAndUpdateGameBoard(int colPos, int rowPos, Tile tileToBePlaced) {
        setHexACoordinateAndUpdateGameBoard(colPos, rowPos, tileToBePlaced);
        setHexBCoordinateAndUpdateGameBoard(colPos, rowPos + 1, tileToBePlaced);
        setHexCCoordinateAndUpdateGameBoard(colPos + 1, rowPos + 1, tileToBePlaced);
    }

    void setEvenFlippedCoordinatesAndUpdateGameBoard(int colPos, int rowPos, Tile tileToBePlaced) {
        setHexACoordinateAndUpdateGameBoard(colPos, rowPos, tileToBePlaced);
        setHexBCoordinateAndUpdateGameBoard(colPos - 1, rowPos + 1, tileToBePlaced);
        setHexCCoordinateAndUpdateGameBoard(colPos, rowPos + 1, tileToBePlaced);
    }

    boolean thereIsNotANonFlippedOddTileThere(int colPos, int rowPos) {
        return checkIfHexDoesNotOccupyPosition(colPos, rowPos) &&
                checkIfHexDoesNotOccupyPosition(colPos, rowPos - 1) &&
                checkIfHexDoesNotOccupyPosition(colPos + 1, rowPos - 1);
    }

    boolean thereIsNotANonFlippedEvenTileThere(int colPos, int rowPos) {
        return checkIfHexDoesNotOccupyPosition(colPos, rowPos) &&
                checkIfHexDoesNotOccupyPosition(colPos - 1, rowPos - 1) &&
                checkIfHexDoesNotOccupyPosition(colPos, rowPos - 1);
    }

    boolean thereIsNotAFlippedOddTileThere(int colPos, int rowPos) {
        return checkIfHexDoesNotOccupyPosition(colPos, rowPos) &&
                checkIfHexDoesNotOccupyPosition(colPos, rowPos + 1) &&
                checkIfHexDoesNotOccupyPosition(colPos + 1, rowPos + 1);
    }

    boolean thereIsNotAFlippedEvenTileThere(int colPos, int rowPos) {
        return checkIfHexDoesNotOccupyPosition(colPos, rowPos) &&
                checkIfHexDoesNotOccupyPosition(colPos - 1, rowPos + 1) &&
                checkIfHexDoesNotOccupyPosition(colPos, rowPos + 1);
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

    boolean checkIfHexDoesNotOccupyPosition(int colPos, int rowPos) {
        if (gameBoardPositionArray[colPos][rowPos] == null) {
            return true;
        } else {
            return false;
        }
    }

    boolean checkIfEven(int rowPos) {
        if (rowPos % 2 == 0) {
            return true;
        } else {
            return false;
        }
    }

    void updateValidTilePlacementList(Tile tileThatWasPlaced) {
        if (tileThatWasPlaced.isFlipped()) {
            setValidFlippedPositions(tileThatWasPlaced);
        } else {
            setValidNotFlippedPositions(tileThatWasPlaced);
        }
        setBoardPositionAsOccupied(tileThatWasPlaced);
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

    void setBoardPositionAsOccupied(Tile tileThatWasPlaced) {
        validPlacementArray[accessGameBoardXValue(tileThatWasPlaced.getHexA())][accessGameBoardYValue(tileThatWasPlaced.getHexA())] = -1;
        validPlacementArray[accessGameBoardXValue(tileThatWasPlaced.getHexB())][accessGameBoardYValue(tileThatWasPlaced.getHexB())] = -1;
        validPlacementArray[accessGameBoardXValue(tileThatWasPlaced.getHexC())][accessGameBoardYValue(tileThatWasPlaced.getHexC())] = -1;
    }

    void setBoardPositionRelativeToHomeHexAsValid(int colOffset, int rowOffset, Tile tileThatWasPlaced) {
        validPlacementArray[accessGameBoardXValue(tileThatWasPlaced.getHexA()) + colOffset][accessGameBoardYValue(tileThatWasPlaced.getHexA()) + rowOffset] = 1;
    }

    boolean boardPositionRelativeToHomeHexIsEmpty(int colOffset, int rowOffset, Tile tileThatWasPlaced) {
        return validPlacementArray[accessGameBoardXValue(tileThatWasPlaced.getHexA()) + colOffset][accessGameBoardYValue(tileThatWasPlaced.getHexA()) + rowOffset] == 0;
    }

    int accessGameBoardXValue(Hex hexToCheck) {
        return hexToCheck.getHexCoordinate().getColumnPosition();
    }

    int accessGameBoardYValue(Hex hexToCheck) {
        return hexToCheck.getHexCoordinate().getRowPosition();
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

    boolean isValidSettlementLocation(int colPos, int rowPos) {
        if (isNotBuiltOn(colPos, rowPos)) {
            if (isOnLevelOne(colPos, rowPos)) {
                if (isHabitable(colPos, rowPos)) {
                    return true;
                }
            }
        }
        return false;
    }

    void buildSettlement(int colPos, int rowPos, Player playerBuilding) {
        if (isValidSettlementLocation(colPos, rowPos) && playerBuilding.getVillagerCount() >= 1) {
            gameBoardPositionArray[colPos][rowPos].setSettlerCount(1);

            int newSettlementID = getNewestAssignableSettlementID();
            lastBuiltSettlementID = newSettlementID;
            gameBoardPositionArray[colPos][rowPos].setSettlementID(newSettlementID);

            assignPlayerNewSettlementInList(playerBuilding, newSettlementID, 1);

            addHexWithSettlementAdjacentToNukeToHexesBuiltOnList(colPos, rowPos);

            gameBoardPositionArray[colPos][rowPos].setPlayerID(playerBuilding.getPlayerID());
            playerBuilding.decreaseVillagerCount(1);
            playerBuilding.increaseScore(1);
        }
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

    private boolean hexToPlaceSpecialPieceOnEvenRowIsNotAdjacentToSpecifiedSettlement(int colPos, int rowPos, int settlementID) {
        return !(adjacentHexToTotoroIsNotNullAndIsCorrectSettlementID(colPos-1, rowPos-1, settlementID)
                || adjacentHexToTotoroIsNotNullAndIsCorrectSettlementID(colPos, rowPos-1, settlementID)
                || adjacentHexToTotoroIsNotNullAndIsCorrectSettlementID(colPos+1, rowPos, settlementID)
                || adjacentHexToTotoroIsNotNullAndIsCorrectSettlementID(colPos, rowPos+1, settlementID)
                || adjacentHexToTotoroIsNotNullAndIsCorrectSettlementID(colPos-1, rowPos+1, settlementID)
                || adjacentHexToTotoroIsNotNullAndIsCorrectSettlementID(colPos-1, rowPos, settlementID));
    }

    private boolean hexToPlaceSpecialPieceOnOddRowIsNotAdjacentToSpecifiedSettlement(int colPos, int rowPos, int settlementID) {
        return !(adjacentHexToTotoroIsNotNullAndIsCorrectSettlementID(colPos, rowPos-1, settlementID)
                || adjacentHexToTotoroIsNotNullAndIsCorrectSettlementID(colPos+1, rowPos-1, settlementID)
                || adjacentHexToTotoroIsNotNullAndIsCorrectSettlementID(colPos+1, rowPos, settlementID)
                || adjacentHexToTotoroIsNotNullAndIsCorrectSettlementID(colPos+1, rowPos+1, settlementID)
                || adjacentHexToTotoroIsNotNullAndIsCorrectSettlementID(colPos, rowPos+1, settlementID)
                || adjacentHexToTotoroIsNotNullAndIsCorrectSettlementID(colPos-1, rowPos, settlementID));
    }

    boolean adjacentHexToTotoroIsNotNullAndIsCorrectSettlementID(int colPos, int rowPos, int settlementID) {
        try {
            if (gameBoardPositionArray[colPos][rowPos].getSettlementID() != settlementID) {
                return false;
            }
         } catch (NullPointerException e) {
            return false; // false for null hexes
        }
        return true;
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

    void resetHexPlayerIDsHelper(int colPos, int rowPos, int colOffset, int rowOffset) {
        try {
            if (gameBoardPositionArray[colPos + colOffset][rowPos + rowOffset].getPlayerID() == -1) {//has been seen
                gameBoardPositionArray[colPos + colOffset][rowPos + rowOffset].setPlayerID(0); //marks as unseen
                resetHexPlayerIDs(colPos + colOffset, rowPos + rowOffset);
            }
        } catch (Exception e) {

        }
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

    void expandSettlementsHelper(int colPos, int rowPos, terrainTypes expansionType, Player player, int colOffset, int rowOffset, int homeHexSettlementID) {
        try {
            if (gameBoardPositionArray[colPos + colOffset][rowPos + rowOffset].getHexTerrainType() == expansionType) {//matches
                if (gameBoardPositionArray[colPos + colOffset][rowPos + rowOffset].getPlayerID() == 0) {//has not been seen
                    gameBoardPositionArray[colPos + colOffset][rowPos + rowOffset].setPlayerID(player.getPlayerID());
                    gameBoardPositionArray[colPos + colOffset][rowPos + rowOffset].setSettlerCount(gameBoardPositionArray[colPos + colOffset][rowPos + rowOffset].getHexLevel());
                    gameBoardPositionArray[colPos + colOffset][rowPos + rowOffset].setSettlementID(homeHexSettlementID);
                    incrementGameBoardSettlementListSize(homeHexSettlementID);

                    addHexWithSettlementAdjacentToNukeToHexesBuiltOnList(colPos + colOffset, rowPos + rowOffset);

                    lastBuiltSettlementID = homeHexSettlementID;

                    expandSettlements(colPos + colOffset, rowPos + rowOffset, expansionType, player, homeHexSettlementID);
                }
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

    int calculateScoreForExpansion(int colPos, int rowPos, terrainTypes expansionType) {
        int returnValue;

        if (gameBoardPositionArray[colPos][rowPos].isNotBuiltOn() == false) {//is part of a settlement
            returnValue = expansionScore(colPos, rowPos, expansionType);
            resetHexPlayerIDs(colPos, rowPos);
            return returnValue;
        }
        return -1;
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

    void expandSettlement(int colPos, int rowPos, terrainTypes expansionType, Player player) {
        int villagersNeededForExpansion = calculateVillagersForExpansion(colPos, rowPos, expansionType);

        if ( isMySettlement(colPos, rowPos, player) ) {

            if (villagersNeededForExpansion <= player.getVillagerCount()) {
                int score = calculateScoreForExpansion(colPos, rowPos, expansionType);
                expandSettlements(colPos, rowPos, expansionType, player, gameBoardPositionArray[colPos][rowPos].getSettlementID());
                player.decreaseVillagerCount(villagersNeededForExpansion);
                player.increaseScore(score);
            }
         }
    }

    boolean isMySettlement(int colPos, int rowPos, Player player){

        int settlementID = gameBoardPositionArray[colPos][rowPos].getSettlementID();

        if(gameBoardSettlementList[settlementID][0] == player.getPlayerID()){
            return true;
        }
        return false;
    }

    void mergeSettlements() {
        if(hexesBuiltOnThisTurn.isEmpty()) {
            return;
        }

        Pair currentCoordinates = hexesBuiltOnThisTurn.lastElement();
        hexesBuiltOnThisTurn.remove(hexesBuiltOnThisTurn.size()-1);
        gameBoardPositionArray[currentCoordinates.getColumnPosition()][currentCoordinates.getRowPosition()].setIfAlreadyTraversed(true);
        int playerID = gameBoardPositionArray[currentCoordinates.getColumnPosition()][currentCoordinates.getRowPosition()].getPlayerID();

        int masterSettlementID = gameBoardPositionArray[currentCoordinates.getColumnPosition()][currentCoordinates.getRowPosition()].getSettlementID();

        if(checkIfEven(currentCoordinates.getRowPosition())) {
            mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()-1, currentCoordinates.getRowPosition()-1);
            mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition(), currentCoordinates.getRowPosition()-1);
            mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()+1, currentCoordinates.getRowPosition());
            mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition(), currentCoordinates.getRowPosition()+1);
            mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()-1, currentCoordinates.getRowPosition()+1);
            mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()-1, currentCoordinates.getRowPosition());
        }
        else {
            mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition(), currentCoordinates.getRowPosition()-1);
            mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()+1, currentCoordinates.getRowPosition()-1);
            mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()+1, currentCoordinates.getRowPosition());
            mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()+1, currentCoordinates.getRowPosition()+1);
            mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition(), currentCoordinates.getRowPosition()+1);
            mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()-1, currentCoordinates.getRowPosition());
        }

        try { // do rest of calculations if possible
            Pair nextCoordinates = hexesBuiltOnThisTurn.lastElement();
            setHexAsLastAddedToHexesBuiltOnList(nextCoordinates.getColumnPosition(), nextCoordinates.getRowPosition());
            mergeSettlements();
        }
        catch (Exception e){
            return;
        }

        gameBoardPositionArray[currentCoordinates.getColumnPosition()][currentCoordinates.getRowPosition()].setIfAlreadyTraversed(false);
    }

    void mergeSettlementsDriver(int masterSettlementID, int playerID, int colPos, int rowPos) {
        try { // is not null
            if(gameBoardPositionArray[colPos][rowPos].getIfAlreadyTraversed() == false) { // hasn't been checked yet
                if (gameBoardPositionArray[colPos][rowPos].getPlayerID() == playerID) { // owned by player
                    gameBoardPositionArray[colPos][rowPos].setIfAlreadyTraversed(true); // mark as traversed

                     Pair currentCoordinates = new Pair(colPos, rowPos);
                    try { // TODO: get this working to save time on traversal; it doesn't seem to delete values from hexesBuiltOnThisTurn and method gets called for already traversed values (but nothing happens with them)
                        Stack<Integer> indicesToRemove = new Stack<>();
                        int i = 0;
                        for(Pair pair : hexesBuiltOnThisTurn) { // try to remove any occurrences of currently seen item in hexesBuiltOnThisTurn
                            if(currentCoordinates.getRowPosition() == pair.getRowPosition() && currentCoordinates.getColumnPosition() == pair.getColumnPosition()) {
                                indicesToRemove.push(i);
                            }
                            i++;
                        }
                        while(!indicesToRemove.isEmpty()) {
                            hexesBuiltOnThisTurn.remove(indicesToRemove.lastElement()); // TODO: for some reason, using removeElementAt for this line breaks splitting settlements code even though in
                            indicesToRemove.pop();                                      // for splitting settlements code I use the same function in the same context
                        }
                    }
                    catch (NullPointerException e) {}

                    if(gameBoardPositionArray[colPos][rowPos].getSettlementID() != masterSettlementID) { // if we are not at a part of the master settlement, we need to decrement the values of the settlement currently
                                                                                                         // here, increment the necessary values for the master settlement and update the hex settlementID
                         if (getGameBoardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos].getSettlementID()) == 1) { // if the settlement we are currently merging is of size 1,
                                                                                                                                        // delete that settlement from player list and game list
                            resetGameBoardSettlementListValues(gameBoardPositionArray[colPos][rowPos].getSettlementID()); // delete settlement
                            //currentPlayer.setOwnedSettlementsListIsNotOwned(gameBoardPositionArray[colPos][rowPos].getSettlementID()); // remove ownership
                         }
                         else { // else just decrement values in list of that settlement for size/totoro/tiger pen
                            if (gameBoardPositionArray[colPos][rowPos].getSettlerCount() != 0) {
                                decrementGameBoardSettlementListSize(gameBoardPositionArray[colPos][rowPos].getSettlementID());
                            }
                            else if (gameBoardPositionArray[colPos][rowPos].getTotoroCount() == 1) {
                                 decrementGameBoardSettlementListSize(masterSettlementID);
                                 decrementGameBoardSettlementListSize(masterSettlementID);
                            }
                            else if (gameBoardPositionArray[colPos][rowPos].getTigerCount() == 1) {
                                decrementGameBoardSettlementListSize(masterSettlementID);
                                decrementGameBoardSettlementListSize(masterSettlementID);
                            }
                         }

                        gameBoardPositionArray[colPos][rowPos].setSettlementID(masterSettlementID); // set hex settlement ID to new ID

                        if (gameBoardPositionArray[colPos][rowPos].getSettlerCount() != 0) { // update newly acquired hex with master settlement values and vice versa
                            incrementGameBoardSettlementListSize(masterSettlementID);
                        } else if (gameBoardPositionArray[colPos][rowPos].getTotoroCount() == 1) {
                            incrementGameBoardSettlementListSize(masterSettlementID);
                            incrementGameBoardSettlementListTotoroCount(masterSettlementID);
                        } else if (gameBoardPositionArray[colPos][rowPos].getTigerCount() == 1) {
                            incrementGameBoardSettlementListSize(masterSettlementID);
                            incrementGameBoardSettlementListTigerCount(masterSettlementID);
                        }
                    }
                    if(checkIfEven(currentCoordinates.getRowPosition())) { // go to next hexes
                        mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()-1, currentCoordinates.getRowPosition()-1);
                        mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition(), currentCoordinates.getRowPosition()-1);
                        mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()+1, currentCoordinates.getRowPosition());
                        mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition(), currentCoordinates.getRowPosition()+1);
                        mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()-1, currentCoordinates.getRowPosition()+1);
                        mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()-1, currentCoordinates.getRowPosition());
                    }
                    else {
                        mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition(), currentCoordinates.getRowPosition()-1);
                        mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()+1, currentCoordinates.getRowPosition()-1);
                        mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()+1, currentCoordinates.getRowPosition());
                        mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()+1, currentCoordinates.getRowPosition()+1);
                        mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition(), currentCoordinates.getRowPosition()+1);
                        mergeSettlementsDriver(masterSettlementID, playerID, currentCoordinates.getColumnPosition()-1, currentCoordinates.getRowPosition());
                    }

                    gameBoardPositionArray[rowPos][colPos].setIfAlreadyTraversed(false); // un-mark as traversed
                }
            }
        }
        catch(NullPointerException e) {
            return; // end search if we reach null hex
        }

        return;
    }

    int getGameBoardTileID() {
        return gameBoardTileID;
    }

    int getGameBoardHexID() {
        return gameBoardHexID;
    }

    void incrementGameBoardTileID() {
        this.gameBoardTileID += 1;
    }

    void incrementGameBoardHexID(int hexAmount) {
        this.gameBoardHexID += hexAmount;
    }

    Hex[][] getGameBoardPositionArray() {
        return gameBoardPositionArray;
    }

    int[][] getValidPlacementArray() {
        return validPlacementArray;
    }

    int getNewestAssignableSettlementID() {
        for (int i = 1; i < 256; i++) { // TODO: FIX ME USE OTHER LIST
            if (this.gameBoardSettlementList[i][1] == 0) {
                return i;
            }
        }
        throw new RuntimeException("error: no available settlements found");
    }

    void assignPlayerNewSettlementInList(Player owningPlayer, int settlementID, int settlementSize) {

        this.gameBoardSettlementList[settlementID][0] = owningPlayer.getPlayerID();
        owningPlayer.setOwnedSettlementsListIsOwned(settlementID);
        assignSizeToGameBoardSettlementList(settlementID, settlementSize);
    }

    void assignSizeToGameBoardSettlementList(int settlementID, int settlementSize) {
        this.gameBoardSettlementList[settlementID][1] = settlementSize;
    }

    void setGameBoardSettlementListPlayerID(int settlementID, int playerID) {
        this.gameBoardSettlementList[settlementID][0] = playerID;
    }

    void incrementGameBoardSettlementListSize(int settlementID) {
        this.gameBoardSettlementList[settlementID][1] += 1;
    }

    void decrementGameBoardSettlementListSize(int settlementID) {
        this.gameBoardSettlementList[settlementID][1] -= 1;
    }

    void incrementGameBoardSettlementListTotoroCount(int settlementID) {
        this.gameBoardSettlementList[settlementID][2] += 1;
    }

    void decrementGameBoardSettlementListTotoroCount(int settlementID) {
        this.gameBoardSettlementList[settlementID][2] -= 1;
    }

    void incrementGameBoardSettlementListTigerCount(int settlementID) {
        this.gameBoardSettlementList[settlementID][3] += 1;
    }

    void decrementGameBoardSettlementListTigerCount(int settlementID) {
        this.gameBoardSettlementList[settlementID][3] -= 1;
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

    void setGameBoardSettlementListTotoroCount(int settlementID, int totoroCount) {
        this.gameBoardSettlementList[settlementID][2] = totoroCount;
    }

    int getGameBoardSettlementListTigerCount(int settlementID) {
        return this.gameBoardSettlementList[settlementID][3];
    }

    void setGameBoardSettlementListTigerCount(int settlementID, int tigerCount) {
        this.gameBoardSettlementList[settlementID][3] = tigerCount;
    }

    void resetGameBoardSettlementListValues(int settlementID) {
        this.gameBoardSettlementList[settlementID][0] = 0;
        this.gameBoardSettlementList[settlementID][1] = 0;
        this.gameBoardSettlementList[settlementID][2] = 0;
        this.gameBoardSettlementList[settlementID][3] = 0;
    }

    public int getLastBuiltSettlementID() {
        return this.lastBuiltSettlementID;
    }
}
