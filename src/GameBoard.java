/*
 * Created by William on 3/14/2017.
 */

public class GameBoard {
    private int boardHeight = 202;
    private int boardWidth = 202;
    private int GameboardTileID;
    private int GameboardHexID;

    // TODO: implement valid placement array but for settlement IDs for each instance of array
    public int[][] validPlacementArray = new int[boardHeight][boardWidth];
    public Hex[][] gameBoardPositionArray = new Hex[boardHeight][boardWidth];
    public int [][] gameboardSettlementList = new int[256][4]; // NEVER USE 0 FOR SETTLEMENT ID

    GameBoard() {
        this.GameboardTileID = 1;
        this.GameboardHexID = 1;
        this.gameboardSettlementList[0][1] = -1; // invalid settlement ID;
    }

    void placeFirstTileAndUpdateValidPlacementList()
    {
        gameBoardPositionArray[102][102] = new Hex(1, 1, terrainTypes.VOLCANO);
        gameBoardPositionArray[101][101] = new Hex(2, 1, terrainTypes.JUNGLE);;
        gameBoardPositionArray[102][101] = new Hex(3, 1, terrainTypes.LAKE);;
        gameBoardPositionArray[101][103] = new Hex(4, 1, terrainTypes.ROCKY);;
        gameBoardPositionArray[102][103] = new Hex(5, 1, terrainTypes.GRASSLANDS);

        int hexAmount = 5;

        incrementGameboardHexID(hexAmount);
        incrementGameboardTileID();

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
                increaseEvenFlippedTileLevelAndUpdateGameboard(colPos, rowPos, tileToBePlaced);
            if (tileIsOddAndFlipped(rowPos, tileToBePlaced))
                increaseOddFlippedTileLevelAndUpdateGameboard(colPos, rowPos, tileToBePlaced);
            if (tileIsEvenAndNotFlipped(rowPos, tileToBePlaced))
                increaseEvenNotFlippedTileLevelAndUpdateGameboard(colPos, rowPos, tileToBePlaced);
            if (tileIsOddAndNotFlipped(rowPos, tileToBePlaced))
                increaseOddNotFlippedTileLevelAndUpdateGameboard(colPos, rowPos, tileToBePlaced);
        }
    }

    void increaseOddNotFlippedTileLevelAndUpdateGameboard(int colPos, int rowPos, Tile tileToBePlaced) {
        setHexALevelAndUpdateGameboard(colPos, rowPos, tileToBePlaced);
        setHexBLevelAndUpdateGameboard(colPos, rowPos - 1, tileToBePlaced);
        setHexCLevelAndUpdateGameboard(colPos + 1, rowPos - 1, tileToBePlaced);
    }

    void increaseEvenNotFlippedTileLevelAndUpdateGameboard(int colPos, int rowPos, Tile tileToBePlaced) {
        setHexALevelAndUpdateGameboard(colPos, rowPos, tileToBePlaced);
        setHexBLevelAndUpdateGameboard(colPos - 1, rowPos - 1, tileToBePlaced);
        setHexCLevelAndUpdateGameboard(colPos, rowPos - 1, tileToBePlaced);
    }

    void increaseOddFlippedTileLevelAndUpdateGameboard(int colPos, int rowPos, Tile tileToBePlaced) {
        setHexALevelAndUpdateGameboard(colPos, rowPos, tileToBePlaced);
        setHexBLevelAndUpdateGameboard(colPos + 1, rowPos + 1, tileToBePlaced);
        setHexCLevelAndUpdateGameboard(colPos, rowPos + 1, tileToBePlaced);
    }

    void increaseEvenFlippedTileLevelAndUpdateGameboard(int colPos, int rowPos, Tile tileToBePlaced) {
        setHexALevelAndUpdateGameboard(colPos, rowPos, tileToBePlaced);
        setHexBLevelAndUpdateGameboard(colPos, rowPos + 1, tileToBePlaced);
        setHexCLevelAndUpdateGameboard(colPos - 1, rowPos + 1, tileToBePlaced);
    }
// TODO: UPDATE LISTS AFTER NUKE; IMPLEMENT LOGIC FOR DELETING SETTLEMENT IF NUKE DELETES SIZE 1 SETTLEMENT
    void setHexALevelAndUpdateGameboard(int colPos, int rowPos, Tile tileToBePlaced) {

        tileToBePlaced.getHexA().setHexLevel(gameBoardPositionArray[colPos][rowPos].getHexLevel() + 1);
        gameBoardPositionArray[colPos][rowPos] = tileToBePlaced.getHexA();
    }

    void setHexBLevelAndUpdateGameboard(int colPos, int rowPos, Tile tileToBePlaced) {

        tileToBePlaced.getHexB().setHexLevel(gameBoardPositionArray[colPos][rowPos].getHexLevel() + 1);
        gameBoardPositionArray[colPos][rowPos] = tileToBePlaced.getHexB();
    }

    void setHexCLevelAndUpdateGameboard(int colPos, int rowPos, Tile tileToBePlaced) {

        tileToBePlaced.getHexC().setHexLevel(gameBoardPositionArray[colPos][rowPos].getHexLevel() + 1);
        gameBoardPositionArray[colPos][rowPos] = tileToBePlaced.getHexC();
    }

    // TODO: REFACTOR (James started will continue later)
    boolean checkIfValidNuke(int colPos, int rowPos, Tile tileToBePlaced) {
        if (tileIsEvenAndFlipped(rowPos, tileToBePlaced)) {
            if ((gameBoardPositionArray[colPos][rowPos] != null
                    && gameBoardPositionArray[colPos][rowPos + 1] != null
                    && gameBoardPositionArray[colPos - 1][rowPos + 1] != null)
                    &&
                    (gameBoardPositionArray[colPos][rowPos].getHexLevel() == gameBoardPositionArray[colPos][rowPos + 1].getHexLevel()
                            && gameBoardPositionArray[colPos][rowPos].getHexLevel() == gameBoardPositionArray[colPos - 1][rowPos + 1].getHexLevel()
                            && gameBoardPositionArray[colPos][rowPos + 1].getHexLevel() == gameBoardPositionArray[colPos - 1][rowPos + 1].getHexLevel())
                    &&
                    !(gameBoardPositionArray[colPos][rowPos].getParentTileID() == gameBoardPositionArray[colPos][rowPos + 1].getParentTileID()
                            && gameBoardPositionArray[colPos][rowPos].getParentTileID() == gameBoardPositionArray[colPos - 1][rowPos + 1].getParentTileID()
                            && gameBoardPositionArray[colPos][rowPos + 1].getParentTileID() == gameBoardPositionArray[colPos - 1][rowPos + 1].getParentTileID())
                    &&
                    (gameBoardPositionArray[colPos][rowPos].getTigerCount() == 0
                            && gameBoardPositionArray[colPos][rowPos + 1].getTigerCount() == 0
                            && gameBoardPositionArray[colPos - 1][rowPos + 1].getTigerCount() == 0)
                    &&
                    (gameBoardPositionArray[colPos][rowPos].getTotoroCount() == 0
                            && gameBoardPositionArray[colPos][rowPos + 1].getTotoroCount() == 0
                            && gameBoardPositionArray[colPos - 1][rowPos + 1].getTotoroCount() == 0)) {
                if (tileToBePlaced.getHexA().getHexTerrainType() == terrainTypes.VOLCANO) {
                    if (gameBoardPositionArray[colPos][rowPos].getHexTerrainType() == terrainTypes.VOLCANO) {
                        if ((gameBoardPositionArray[colPos][rowPos + 1].getSettlementID()
                                == gameBoardPositionArray[colPos - 1][rowPos + 1].getSettlementID()) &&
                                (getGameboardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos + 1].getSettlementID()) == 2)) {
                            return false;
                        } else if (getGameboardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos + 1].getSettlementID()) == 1) {
                            return false;
                        } else if (getGameboardSettlementListSettlementSize(gameBoardPositionArray[colPos - 1][rowPos + 1].getSettlementID()) == 1) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                } else if (tileToBePlaced.getHexB().getHexTerrainType() == terrainTypes.VOLCANO) {
                    if (gameBoardPositionArray[colPos][rowPos + 1].getHexTerrainType() == terrainTypes.VOLCANO) {
                        if ((gameBoardPositionArray[colPos][rowPos].getSettlementID()
                                == gameBoardPositionArray[colPos - 1][rowPos + 1].getSettlementID()) &&
                                (getGameboardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos].getSettlementID()) == 2)) {
                            return false;
                        } else if (getGameboardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos].getSettlementID()) == 1) {
                            return false;
                        } else if (getGameboardSettlementListSettlementSize(gameBoardPositionArray[colPos - 1][rowPos + 1].getSettlementID()) == 1) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                } else if (tileToBePlaced.getHexC().getHexTerrainType() == terrainTypes.VOLCANO) {
                    if (gameBoardPositionArray[colPos - 1][rowPos + 1].getHexTerrainType() == terrainTypes.VOLCANO) {
                        if ((gameBoardPositionArray[colPos][rowPos + 1].getSettlementID()
                                == gameBoardPositionArray[colPos][rowPos].getSettlementID()) &&
                                (getGameboardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos].getSettlementID()) == 2)) {
                            return false;
                        } else if (getGameboardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos].getSettlementID()) == 1) {
                            return false;
                        } else if (getGameboardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos + 1].getSettlementID()) == 1) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                }
            }
        }
        if (tileIsOddAndFlipped(rowPos, tileToBePlaced)) {
            if ((gameBoardPositionArray[colPos][rowPos] != null
                    && gameBoardPositionArray[colPos + 1][rowPos + 1] != null
                    && gameBoardPositionArray[colPos][rowPos + 1] != null)
                    &&
                    (gameBoardPositionArray[colPos][rowPos].getHexLevel() == gameBoardPositionArray[colPos + 1][rowPos + 1].getHexLevel()
                            && gameBoardPositionArray[colPos][rowPos].getHexLevel() == gameBoardPositionArray[colPos][rowPos + 1].getHexLevel()
                            && gameBoardPositionArray[colPos + 1][rowPos + 1].getHexLevel() == gameBoardPositionArray[colPos][rowPos + 1].getHexLevel())
                    &&
                    !(gameBoardPositionArray[colPos][rowPos].getParentTileID() == gameBoardPositionArray[colPos + 1][rowPos + 1].getParentTileID()
                            && gameBoardPositionArray[colPos][rowPos].getParentTileID() == gameBoardPositionArray[colPos][rowPos + 1].getParentTileID()
                            && gameBoardPositionArray[colPos + 1][rowPos + 1].getParentTileID() == gameBoardPositionArray[colPos][rowPos + 1].getParentTileID())
                    &&
                    (gameBoardPositionArray[colPos][rowPos].getTigerCount() == 0
                            && gameBoardPositionArray[colPos + 1][rowPos + 1].getTigerCount() == 0
                            && gameBoardPositionArray[colPos][rowPos + 1].getTigerCount() == 0)
                    &&
                    (gameBoardPositionArray[colPos][rowPos].getTotoroCount() == 0
                            && gameBoardPositionArray[colPos + 1][rowPos + 1].getTotoroCount() == 0
                            && gameBoardPositionArray[colPos][rowPos + 1].getTotoroCount() == 0)) {
                if (tileToBePlaced.getHexA().getHexTerrainType() == terrainTypes.VOLCANO) {
                    if (gameBoardPositionArray[colPos][rowPos].getHexTerrainType() == terrainTypes.VOLCANO) {
                        if ((gameBoardPositionArray[colPos + 1][rowPos + 1].getSettlementID()
                                == gameBoardPositionArray[colPos][rowPos + 1].getSettlementID()) &&
                                (getGameboardSettlementListSettlementSize(gameBoardPositionArray[colPos + 1][rowPos + 1].getSettlementID()) == 2)) {
                            return false;
                        } else if (getGameboardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos + 1].getSettlementID()) == 1) {
                            return false;
                        } else if (getGameboardSettlementListSettlementSize(gameBoardPositionArray[colPos + 1][rowPos + 1].getSettlementID()) == 1) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                } else if (tileToBePlaced.getHexB().getHexTerrainType() == terrainTypes.VOLCANO) {
                    if (gameBoardPositionArray[colPos + 1][rowPos + 1].getHexTerrainType() == terrainTypes.VOLCANO) {
                        if ((gameBoardPositionArray[colPos][rowPos].getSettlementID()
                                == gameBoardPositionArray[colPos][rowPos + 1].getSettlementID()) &&
                                (getGameboardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos].getSettlementID()) == 2)) {
                            return false;
                        } else if (getGameboardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos].getSettlementID()) == 1) {
                            return false;
                        } else if (getGameboardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos + 1].getSettlementID()) == 1) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                } else if (tileToBePlaced.getHexC().getHexTerrainType() == terrainTypes.VOLCANO) {
                    if (gameBoardPositionArray[colPos][rowPos + 1].getHexTerrainType() == terrainTypes.VOLCANO) {
                        if ((gameBoardPositionArray[colPos][rowPos].getSettlementID()
                                == gameBoardPositionArray[colPos + 1][rowPos + 1].getSettlementID()) &&
                                (getGameboardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos].getSettlementID()) == 2)) {
                            return false;
                        } else if (getGameboardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos].getSettlementID()) == 1) {
                            return false;
                        } else if (getGameboardSettlementListSettlementSize(gameBoardPositionArray[colPos + 1][rowPos + 1].getSettlementID()) == 1) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                }
            }
        }

        if (tileIsEvenAndNotFlipped(rowPos, tileToBePlaced)) {
            if ((gameBoardPositionArray[colPos][rowPos] != null
                    && gameBoardPositionArray[colPos - 1][rowPos - 1] != null
                    && gameBoardPositionArray[colPos][rowPos - 1] != null)
                    &&
                    (gameBoardPositionArray[colPos][rowPos].getHexLevel() == gameBoardPositionArray[colPos - 1][rowPos - 1].getHexLevel()
                            && gameBoardPositionArray[colPos][rowPos].getHexLevel() == gameBoardPositionArray[colPos][rowPos - 1].getHexLevel()
                            && gameBoardPositionArray[colPos - 1][rowPos - 1].getHexLevel() == gameBoardPositionArray[colPos][rowPos - 1].getHexLevel())
                    &&
                    !(gameBoardPositionArray[colPos][rowPos].getParentTileID() == gameBoardPositionArray[colPos - 1][rowPos - 1].getParentTileID()
                            && gameBoardPositionArray[colPos][rowPos].getParentTileID() == gameBoardPositionArray[colPos][rowPos - 1].getParentTileID()
                            && gameBoardPositionArray[colPos - 1][rowPos - 1].getParentTileID() == gameBoardPositionArray[colPos][rowPos - 1].getParentTileID())
                    &&
                    (gameBoardPositionArray[colPos][rowPos].getTigerCount() == 0
                            && gameBoardPositionArray[colPos - 1][rowPos - 1].getTigerCount() == 0
                            && gameBoardPositionArray[colPos][rowPos - 1].getTigerCount() == 0)
                    &&
                    (gameBoardPositionArray[colPos][rowPos].getTotoroCount() == 0
                            && gameBoardPositionArray[colPos - 1][rowPos - 1].getTotoroCount() == 0
                            && gameBoardPositionArray[colPos][rowPos - 1].getTotoroCount() == 0)) {
                if (tileToBePlaced.getHexA().getHexTerrainType() == terrainTypes.VOLCANO) {
                    if (gameBoardPositionArray[colPos][rowPos].getHexTerrainType() == terrainTypes.VOLCANO) {
                        if ((gameBoardPositionArray[colPos][rowPos - 1].getSettlementID()
                                == gameBoardPositionArray[colPos - 1][rowPos - 1].getSettlementID()) &&
                                (getGameboardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos - 1].getSettlementID()) == 2)) {
                            return false;
                        } else if (getGameboardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos - 1].getSettlementID()) == 1) {
                            return false;
                        } else if (getGameboardSettlementListSettlementSize(gameBoardPositionArray[colPos - 1][rowPos - 1].getSettlementID()) == 1) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                } else if (tileToBePlaced.getHexB().getHexTerrainType() == terrainTypes.VOLCANO) {
                    if (gameBoardPositionArray[colPos - 1][rowPos - 1].getHexTerrainType() == terrainTypes.VOLCANO) {
                        if ((gameBoardPositionArray[colPos][rowPos - 1].getSettlementID()
                                == gameBoardPositionArray[colPos][rowPos].getSettlementID()) &&
                                (getGameboardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos].getSettlementID()) == 2)) {
                            return false;
                        } else if (getGameboardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos].getSettlementID()) == 1) {
                            return false;
                        } else if (getGameboardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos - 1].getSettlementID()) == 1) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                } else if (tileToBePlaced.getHexC().getHexTerrainType() == terrainTypes.VOLCANO) {
                    if (gameBoardPositionArray[colPos][rowPos - 1].getHexTerrainType() == terrainTypes.VOLCANO) {
                        if ((gameBoardPositionArray[colPos][rowPos].getSettlementID()
                                == gameBoardPositionArray[colPos - 1][rowPos - 1].getSettlementID()) &&
                                (getGameboardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos].getSettlementID()) == 2)) {
                            return false;
                        } else if (getGameboardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos].getSettlementID()) == 1) {
                            return false;
                        } else if (getGameboardSettlementListSettlementSize(gameBoardPositionArray[colPos - 1][rowPos - 1].getSettlementID()) == 1) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                }
            }
        }
        if (tileIsOddAndNotFlipped(rowPos, tileToBePlaced)) {
            if ((gameBoardPositionArray[colPos][rowPos] != null
                    && gameBoardPositionArray[colPos][rowPos - 1] != null
                    && gameBoardPositionArray[colPos + 1][rowPos - 1] != null)
                    &&
                    (gameBoardPositionArray[colPos][rowPos].getHexLevel() == gameBoardPositionArray[colPos][rowPos - 1].getHexLevel()
                            && gameBoardPositionArray[colPos][rowPos].getHexLevel() == gameBoardPositionArray[colPos + 1][rowPos - 1].getHexLevel()
                            && gameBoardPositionArray[colPos][rowPos - 1].getHexLevel() == gameBoardPositionArray[colPos + 1][rowPos - 1].getHexLevel())
                    &&
                    !(gameBoardPositionArray[colPos][rowPos].getParentTileID() == gameBoardPositionArray[colPos][rowPos - 1].getParentTileID()
                            && gameBoardPositionArray[colPos][rowPos].getParentTileID() == gameBoardPositionArray[colPos + 1][rowPos - 1].getParentTileID()
                            && gameBoardPositionArray[colPos][rowPos - 1].getParentTileID() == gameBoardPositionArray[colPos + 1][rowPos - 1].getParentTileID())
                    &&
                    (gameBoardPositionArray[colPos][rowPos].getTigerCount() == 0
                            && gameBoardPositionArray[colPos][rowPos - 1].getTigerCount() == 0
                            && gameBoardPositionArray[colPos + 1][rowPos - 1].getTigerCount() == 0)
                    &&
                    (gameBoardPositionArray[colPos][rowPos].getTotoroCount() == 0
                            && gameBoardPositionArray[colPos][rowPos - 1].getTotoroCount() == 0
                            && gameBoardPositionArray[colPos + 1][rowPos - 1].getTotoroCount() == 0)) {
                if (tileToBePlaced.getHexA().getHexTerrainType() == terrainTypes.VOLCANO) {
                    if (gameBoardPositionArray[colPos][rowPos].getHexTerrainType() == terrainTypes.VOLCANO) {
                        if ((gameBoardPositionArray[colPos][rowPos - 1].getSettlementID()
                                == gameBoardPositionArray[colPos + 1][rowPos - 1].getSettlementID()) &&
                                (getGameboardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos - 1].getSettlementID()) == 2)) {
                            return false;
                        } else if (getGameboardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos - 1].getSettlementID()) == 1) {
                            return false;
                        } else if (getGameboardSettlementListSettlementSize(gameBoardPositionArray[colPos + 1][rowPos - 1].getSettlementID()) == 1) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                } else if (tileToBePlaced.getHexB().getHexTerrainType() == terrainTypes.VOLCANO) {
                    if (gameBoardPositionArray[colPos][rowPos - 1].getHexTerrainType() == terrainTypes.VOLCANO) {
                        if ((gameBoardPositionArray[colPos][rowPos].getSettlementID()
                                == gameBoardPositionArray[colPos + 1][rowPos - 1].getSettlementID()) &&
                                (getGameboardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos + 1].getSettlementID()) == 2)) {
                            return false;
                        } else if (getGameboardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos].getSettlementID()) == 1) {
                            return false;
                        } else if (getGameboardSettlementListSettlementSize(gameBoardPositionArray[colPos + 1][rowPos - 1].getSettlementID()) == 1) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                } else if (tileToBePlaced.getHexC().getHexTerrainType() == terrainTypes.VOLCANO) {
                    if (gameBoardPositionArray[colPos + 1][rowPos - 1].getHexTerrainType() == terrainTypes.VOLCANO) {
                        if ((gameBoardPositionArray[colPos][rowPos].getSettlementID()
                                == gameBoardPositionArray[colPos][rowPos - 1].getSettlementID()) &&
                                (getGameboardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos].getSettlementID()) == 2)) {
                            return false;
                        } else if (getGameboardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos].getSettlementID()) == 1) {
                            return false;
                        } else if (getGameboardSettlementListSettlementSize(gameBoardPositionArray[colPos][rowPos - 1].getSettlementID()) == 1) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    void setTileAtPosition(int colPos, int rowPos, Tile tileToBePlaced) {
        if (!checkIfTileCanBePlacedAtPosition(colPos, rowPos, tileToBePlaced)) return;

        if (tileIsEvenAndFlipped(rowPos, tileToBePlaced) && thereIsNotAFlippedEvenTileThere(colPos, rowPos))
            setEvenFlippedCoordinatesAndUpdateGameboard(colPos, rowPos, tileToBePlaced);
        if (tileIsOddAndFlipped(rowPos, tileToBePlaced) && thereIsNotAFlippedOddTileThere(colPos, rowPos))
            setOddFlippedCoordinatesAndUpdateGameboard(colPos, rowPos, tileToBePlaced);
        if (tileIsEvenAndNotFlipped(rowPos, tileToBePlaced) && thereIsNotANonFlippedEvenTileThere(colPos, rowPos))
            setEvenNotFlippedCoordinatesAndUpdateGameboard(colPos, rowPos, tileToBePlaced);
        if (tileIsOddAndNotFlipped(rowPos, tileToBePlaced) && thereIsNotANonFlippedOddTileThere(colPos, rowPos))
            setOddNotFlippedCoordinatesAndUpdateGameboard(colPos, rowPos, tileToBePlaced);

        updateValidTilePlacementList(tileToBePlaced);
        incrementGameboardTileID();
        int hexAmount = 3;
        incrementGameboardHexID(hexAmount);
    }

    void setOddNotFlippedCoordinatesAndUpdateGameboard(int colPos, int rowPos, Tile tileToBePlaced) {
        setHexACoordinateAndUpdateGameboard(colPos, rowPos, tileToBePlaced);
        setHexBCoordinateAndUpdateGameboard(colPos, rowPos - 1, tileToBePlaced);
        setHexCCoordinateAndUpdateGameboard(colPos + 1, rowPos - 1, tileToBePlaced);
    }

    void setEvenNotFlippedCoordinatesAndUpdateGameboard(int colPos, int rowPos, Tile tileToBePlaced) {
        setHexACoordinateAndUpdateGameboard(colPos, rowPos, tileToBePlaced);
        setHexBCoordinateAndUpdateGameboard(colPos - 1, rowPos - 1, tileToBePlaced);
        setHexCCoordinateAndUpdateGameboard(colPos, rowPos - 1, tileToBePlaced);
    }

    void setOddFlippedCoordinatesAndUpdateGameboard(int colPos, int rowPos, Tile tileToBePlaced) {
        setHexACoordinateAndUpdateGameboard(colPos, rowPos, tileToBePlaced);
        setHexBCoordinateAndUpdateGameboard(colPos, rowPos + 1, tileToBePlaced);
        setHexCCoordinateAndUpdateGameboard(colPos + 1, rowPos + 1, tileToBePlaced);
    }

    void setEvenFlippedCoordinatesAndUpdateGameboard(int colPos, int rowPos, Tile tileToBePlaced) {
        setHexACoordinateAndUpdateGameboard(colPos, rowPos, tileToBePlaced);
        setHexBCoordinateAndUpdateGameboard(colPos - 1, rowPos + 1, tileToBePlaced);
        setHexCCoordinateAndUpdateGameboard(colPos, rowPos + 1, tileToBePlaced);
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

    void setHexACoordinateAndUpdateGameboard(int colPos, int rowPos, Tile tileToBePlaced) {
        tileToBePlaced.getHexA().getHexCoordinate().setCoordinates(colPos, rowPos);
        this.gameBoardPositionArray[colPos][rowPos] = tileToBePlaced.getHexA();
    }

    void setHexBCoordinateAndUpdateGameboard(int colPos, int rowPos, Tile tileToBePlaced) {
        tileToBePlaced.getHexB().getHexCoordinate().setCoordinates(colPos, rowPos);
        this.gameBoardPositionArray[colPos][rowPos] = tileToBePlaced.getHexB();
    }

    void setHexCCoordinateAndUpdateGameboard(int colPos, int rowPos, Tile tileToBePlaced) {
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
        validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())][accessGameboardYValue(tileThatWasPlaced.getHexA())] = -1;
        validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexB())][accessGameboardYValue(tileThatWasPlaced.getHexB())] = -1;
        validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexC())][accessGameboardYValue(tileThatWasPlaced.getHexC())] = -1;
    }

    void setBoardPositionRelativeToHomeHexAsValid(int colOffset, int rowOffset, Tile tileThatWasPlaced) {
        validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA()) + colOffset][accessGameboardYValue(tileThatWasPlaced.getHexA()) + rowOffset] = 1;
    }

    boolean boardPositionRelativeToHomeHexIsEmpty(int colOffset, int rowOffset, Tile tileThatWasPlaced) {
        return validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA()) + colOffset][accessGameboardYValue(tileThatWasPlaced.getHexA()) + rowOffset] == 0;
    }

    int accessGameboardXValue(Hex hexToCheck) {
        return hexToCheck.getHexCoordinate().getColumnPosition();
    }

    int accessGameboardYValue(Hex hexToCheck) {
        return hexToCheck.getHexCoordinate().getRowPosition();
    }

    boolean isNotBuiltOn(int colPos, int rowPos){
        if (gameBoardPositionArray[colPos][rowPos].isNotBuiltOn()) {
            return true;
        }else{
            return false;
        }
    }

    boolean isOnLevelOne(int colPos, int rowPos){
        if(gameBoardPositionArray[colPos][rowPos].getHexLevel() == 1){
            return true;
        }else{
            return false;
        }
    }

    boolean isHabitable(int colPos, int rowPos){
        if(gameBoardPositionArray[colPos][rowPos].getHexTerrainType().getHabitablity() == 1){
            return true;
        }else{
            return false;
        }
    }

    boolean isValidSettlementLocation(int colPos, int rowPos){
        if (isNotBuiltOn(colPos,rowPos)) {
            if (isOnLevelOne(colPos,rowPos)) {
                if (isHabitable(colPos,rowPos)) {
                    return true;
                }
            }
        }
        return false;
    }

    void buildSettlement(int colPos, int rowPos, Player playerBuilding) {
        if (isValidSettlementLocation(colPos,rowPos) && playerBuilding.getVillagerCount()>=1) {
            gameBoardPositionArray[colPos][rowPos].setSettlerCount(1);

            int newSettlementID = getNewestAssignableSettlementID();
            gameBoardPositionArray[colPos][rowPos].setSettlementID(newSettlementID);

            assignPlayerNewSettlementInList(playerBuilding, newSettlementID, 1);

            gameBoardPositionArray[colPos][rowPos].setPlayerID(playerBuilding.getPlayerID());
            playerBuilding.decreaseVillagerCount(1);
            playerBuilding.increaseScore(1);
        }
    }

    int calculateVillagersForExpansion(int colPos, int rowPos, terrainTypes expansionType){
        int returnValue;

        if(gameBoardPositionArray[colPos][rowPos].isNotBuiltOn()==false){//is part of a settlement
            returnValue = recursiveExpansionCalculation(colPos,rowPos,expansionType);
            resetHexPlayerIDs(colPos,rowPos);
            return returnValue;
        }
        return -1;
    }

    void resetHexPlayerIDsHelper(int colPos, int rowPos, int colOffset, int rowOffset){
        try {
            if (gameBoardPositionArray[colPos + colOffset][rowPos + rowOffset].getPlayerID() == -1) {//has been seen
                gameBoardPositionArray[colPos + colOffset][rowPos + rowOffset].setPlayerID(0); //marks as unseen
                resetHexPlayerIDs(colPos+colOffset,rowPos+rowOffset);
            }
        }catch(Exception e){

        }
    }

    void resetHexPlayerIDs(int colPos, int rowPos){
        if(checkIfEven(rowPos)){
            resetHexPlayerIDsHelper(colPos,rowPos,-1,-1);
            resetHexPlayerIDsHelper(colPos,rowPos,0,-1);
            resetHexPlayerIDsHelper(colPos,rowPos,-1,0);
            resetHexPlayerIDsHelper(colPos,rowPos,+1,0);
            resetHexPlayerIDsHelper(colPos,rowPos,-1,+1);
            resetHexPlayerIDsHelper(colPos,rowPos,0,+1);
        }else{
            resetHexPlayerIDsHelper(colPos,rowPos,0,-1);
            resetHexPlayerIDsHelper(colPos,rowPos,+1,-1);
            resetHexPlayerIDsHelper(colPos,rowPos,-1,0);
            resetHexPlayerIDsHelper(colPos,rowPos,+1,0);
            resetHexPlayerIDsHelper(colPos,rowPos,0,+1);
            resetHexPlayerIDsHelper(colPos,rowPos,+1,+1);
        }
    }

    int recursiveExpansionHelper(int colPos, int rowPos, terrainTypes expansionType, int colOffset, int rowOffset){
        try {
            if (gameBoardPositionArray[colPos + colOffset][rowPos + rowOffset].getHexTerrainType() == expansionType) {//matches
                if (gameBoardPositionArray[colPos + colOffset][rowPos + rowOffset].getPlayerID() == 0) {//has not been seen
                    gameBoardPositionArray[colPos + colOffset][rowPos + rowOffset].setPlayerID(-1); //mark as seen, add level to return val
                    return gameBoardPositionArray[colPos + colOffset][rowPos + rowOffset].getHexLevel() + recursiveExpansionCalculation(colPos+colOffset,rowPos+rowOffset,expansionType);
                }
            }
        }catch(Exception e){
            return 0; //ignore null hexes
        }

        return 0;
    }

    int recursiveExpansionCalculation(int colPos, int rowPos, terrainTypes expansionType){
        int returnVal=0;
        if(checkIfEven(rowPos)){
            returnVal += recursiveExpansionHelper(colPos,rowPos,expansionType,-1,-1);
            returnVal += recursiveExpansionHelper(colPos,rowPos,expansionType,0,-1);
            returnVal += recursiveExpansionHelper(colPos,rowPos,expansionType,-1,0);
            returnVal += recursiveExpansionHelper(colPos,rowPos,expansionType,+1,0);
            returnVal += recursiveExpansionHelper(colPos,rowPos,expansionType,-1,+1);
            returnVal += recursiveExpansionHelper(colPos,rowPos,expansionType,0,+1);
        }else{
            returnVal += recursiveExpansionHelper(colPos,rowPos,expansionType,0,-1);
            returnVal += recursiveExpansionHelper(colPos,rowPos,expansionType,+1,-1);
            returnVal += recursiveExpansionHelper(colPos,rowPos,expansionType,-1,0);
            returnVal += recursiveExpansionHelper(colPos,rowPos,expansionType,+1,0);
            returnVal += recursiveExpansionHelper(colPos,rowPos,expansionType,0,+1);
            returnVal += recursiveExpansionHelper(colPos,rowPos,expansionType,+1,+1);
        }
        return returnVal;
    }

    void expandSettlementsHelper(int colPos, int rowPos, terrainTypes expansionType, Player player, int colOffset, int rowOffset, int homeHexSettlementID){
        try {
            if (gameBoardPositionArray[colPos + colOffset][rowPos + rowOffset].getHexTerrainType() == expansionType) {//matches
                if (gameBoardPositionArray[colPos + colOffset][rowPos + rowOffset].getPlayerID() == 0) {//has not been seen
                    gameBoardPositionArray[colPos + colOffset][rowPos + rowOffset].setPlayerID(player.getPlayerID());
                    gameBoardPositionArray[colPos + colOffset][rowPos + rowOffset].setSettlerCount(gameBoardPositionArray[colPos + colOffset][rowPos + rowOffset].getHexLevel());
                    gameBoardPositionArray[colPos + colOffset][rowPos + rowOffset].setSettlementID(homeHexSettlementID);
                    incrementGameboardSettlementListSize(homeHexSettlementID);
                    expandSettlements(colPos + colOffset, rowPos + rowOffset, expansionType, player, homeHexSettlementID);
                }
            }
        }catch(Exception e){
            
        }
    }
    
    void expandSettlements(int colPos, int rowPos, terrainTypes expansionType, Player player, int homeHexSettlementID){
        if(checkIfEven(rowPos)){
            expandSettlementsHelper(colPos,rowPos,expansionType,player,-1,-1, homeHexSettlementID);
            expandSettlementsHelper(colPos,rowPos,expansionType,player,0,-1, homeHexSettlementID);
            expandSettlementsHelper(colPos,rowPos,expansionType,player,-1,0, homeHexSettlementID);
            expandSettlementsHelper(colPos,rowPos,expansionType,player,+1,0, homeHexSettlementID);
            expandSettlementsHelper(colPos,rowPos,expansionType,player,-1,+1, homeHexSettlementID);
            expandSettlementsHelper(colPos,rowPos,expansionType,player,0,+1, homeHexSettlementID);
        }else{
            expandSettlementsHelper(colPos,rowPos,expansionType,player,0,-1, homeHexSettlementID);
            expandSettlementsHelper(colPos,rowPos,expansionType,player,+1,-1, homeHexSettlementID);
            expandSettlementsHelper(colPos,rowPos,expansionType,player,-1,0, homeHexSettlementID);
            expandSettlementsHelper(colPos,rowPos,expansionType,player,+1,0, homeHexSettlementID);
            expandSettlementsHelper(colPos,rowPos,expansionType,player,0,+1, homeHexSettlementID);
            expandSettlementsHelper(colPos,rowPos,expansionType,player,+1,+1, homeHexSettlementID);
        }

    }

    int calculateScoreForExpansion(int colPos, int rowPos, terrainTypes expansionType){
        int returnValue;

        if(gameBoardPositionArray[colPos][rowPos].isNotBuiltOn()==false){//is part of a settlement
            returnValue = expansionScore(colPos,rowPos,expansionType);
            resetHexPlayerIDs(colPos,rowPos);
            return returnValue;
        }
        return -1;
    }

    int expansionScoreHelper(int colPos, int rowPos, terrainTypes expansionType, int colOffset, int rowOffset){
        try {
            if (gameBoardPositionArray[colPos + colOffset][rowPos + rowOffset].getHexTerrainType() == expansionType) {//matches
                if (gameBoardPositionArray[colPos + colOffset][rowPos + rowOffset].getPlayerID() == 0) {//has not been seen
                    gameBoardPositionArray[colPos + colOffset][rowPos + rowOffset].setPlayerID(-1); //mark as seen, add level to return val
                    int returnValue = gameBoardPositionArray[colPos + colOffset][rowPos + rowOffset].getHexLevel();
                    returnValue *= returnValue;
                    returnValue += expansionScore(colPos+colOffset,rowPos+rowOffset,expansionType);
                    return returnValue;
                }
            }
        }catch(Exception e){
            return 0; //ignore null hexes
        }

        return 0;
    }

    int expansionScore(int colPos, int rowPos, terrainTypes expansionType){
        int returnVal=0;
        if(checkIfEven(rowPos)){
            returnVal += expansionScoreHelper(colPos,rowPos,expansionType,-1,-1);
            returnVal += expansionScoreHelper(colPos,rowPos,expansionType,0,-1);
            returnVal += expansionScoreHelper(colPos,rowPos,expansionType,-1,0);
            returnVal += expansionScoreHelper(colPos,rowPos,expansionType,+1,0);
            returnVal += expansionScoreHelper(colPos,rowPos,expansionType,-1,+1);
            returnVal += expansionScoreHelper(colPos,rowPos,expansionType,0,+1);
        }else{
            returnVal += expansionScoreHelper(colPos,rowPos,expansionType,0,-1);
            returnVal += expansionScoreHelper(colPos,rowPos,expansionType,+1,-1);
            returnVal += expansionScoreHelper(colPos,rowPos,expansionType,-1,0);
            returnVal += expansionScoreHelper(colPos,rowPos,expansionType,+1,0);
            returnVal += expansionScoreHelper(colPos,rowPos,expansionType,0,+1);
            returnVal += expansionScoreHelper(colPos,rowPos,expansionType,+1,+1);
        }
        return returnVal;
    }

    void expandSettlement(int colPos, int rowPos, terrainTypes expansionType, Player player){
        int villagersNeededForExpansion = calculateVillagersForExpansion(colPos,rowPos,expansionType);
        if(villagersNeededForExpansion <= player.getVillagerCount()){
            int score = calculateScoreForExpansion(colPos,rowPos,expansionType);
            expandSettlements(colPos,rowPos,expansionType,player, gameBoardPositionArray[colPos][rowPos].getSettlementID());
            player.decreaseVillagerCount(villagersNeededForExpansion);
            player.increaseScore(score);
        }
    }

    int getGameboardTileID() {
        return GameboardTileID;
    }

    int getGameBoardHexID() {
        return GameboardHexID;
    }

    void incrementGameboardTileID() {
        this.GameboardTileID += 1;
    }

    void incrementGameboardHexID(int hexAmount) {
        this.GameboardHexID += hexAmount;
    }

    Hex[][] getGameBoardPositionArray() {
        return gameBoardPositionArray;
    }

    int[][] getValidPlacementArray(){
        return validPlacementArray;
    }

    int getNewestAssignableSettlementID() {
        for(int i = 1; i < 256; i++) {
            if (this.gameboardSettlementList[i][0] == 0) {
                return i;
            }
        }
        throw new RuntimeException("error: available settlement not found");
    }

    void assignPlayerNewSettlementInList(Player owningPlayer, int settlementID, int settlementSize) {
        this.gameboardSettlementList[settlementID][0] = owningPlayer.getPlayerID();
        owningPlayer.setOwnedSettlementsListIsOwned(settlementID);
        assignSizeToGameboardSettlementList(settlementID, settlementSize);
    }

    void assignSizeToGameboardSettlementList(int settlementID, int settlementSize) {
        this.gameboardSettlementList[settlementID][1] = settlementSize;
    }

    void incrementGameboardSettlementListSize(int settlementID) {
        this.gameboardSettlementList[settlementID][1] += 1;
    }

    void incrementGameboardSettlementListTotoroCount(int settlementID) {
        this.gameboardSettlementList[settlementID][2] += 1;
    }

    void incrementGameboardSettlementListTigerCount(int settlementID) {
        this.gameboardSettlementList[settlementID][3] += 1;
    }

    int getGameboardSettlementListOwner(int settlementID) {
        return this.gameboardSettlementList[settlementID][0];
    }

    int getGameboardSettlementListSettlementSize(int settlementID) {
        return this.gameboardSettlementList[settlementID][1];
    }

    int getGameboardSettlementListTotoroCount(int settlementID) {
        return this.gameboardSettlementList[settlementID][2];
    }

    int getGameboardSettlementListTigerCount(int settlementID) {
        return this.gameboardSettlementList[settlementID][3];
    }

    void deleteGameBoardSettlementListValues(int settlementID) {
        this.gameboardSettlementList[settlementID][0] = 0;
        this.gameboardSettlementList[settlementID][1] = 0;
        this.gameboardSettlementList[settlementID][2] = 0;
        this.gameboardSettlementList[settlementID][3] = 0;
    }

}