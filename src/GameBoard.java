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
    public int[] settlementSizeList = new int[51];

    GameBoard(){
        this.GameboardTileID = 1;
        this.GameboardHexID = 1;
    }


    void nukeTiles(int colPos, int rowPos, Tile tileToBePlaced) {
        if(checkIfValidNuke(colPos, rowPos, tileToBePlaced)) {
            if(tileIsEvenAndFlipped(rowPos, tileToBePlaced))
                increaseEvenFlippedTileLevelAndUpdateGameboard(colPos, rowPos, tileToBePlaced);
            if(tileIsOddAndFlipped(rowPos, tileToBePlaced))
                increaseOddFlippedTileLevelAndUpdateGameboard(colPos, rowPos, tileToBePlaced);
            if(tileIsEvenAndNotFlipped(rowPos,tileToBePlaced))
                increaseEvenNotFlippedTileLevelAndUpdateGameboard(colPos, rowPos, tileToBePlaced);
            if(tileIsOddAndNotFlipped(rowPos, tileToBePlaced))
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
                                        (settlementSizeList[gameBoardPositionArray[colPos][rowPos + 1].getSettlementID()] == 2)) {
                                    return false;
                                } else if (settlementSizeList[gameBoardPositionArray[colPos][rowPos + 1].getSettlementID()] == 1) {
                                    return false;
                                } else if (settlementSizeList[gameBoardPositionArray[colPos - 1][rowPos + 1].getSettlementID()] == 1) {
                                    return false;
                                } else {
                                    return true;
                                }
                            }
                        } else if (tileToBePlaced.getHexB().getHexTerrainType() == terrainTypes.VOLCANO) {
                            if (gameBoardPositionArray[colPos][rowPos + 1].getHexTerrainType() == terrainTypes.VOLCANO) {
                                if ((gameBoardPositionArray[colPos][rowPos].getSettlementID()
                                        == gameBoardPositionArray[colPos - 1][rowPos + 1].getSettlementID()) &&
                                        (settlementSizeList[gameBoardPositionArray[colPos][rowPos].getSettlementID()] == 2)) {
                                    return false;
                                } else if (settlementSizeList[gameBoardPositionArray[colPos][rowPos].getSettlementID()] == 1) {
                                    return false;
                                } else if (settlementSizeList[gameBoardPositionArray[colPos - 1][rowPos + 1].getSettlementID()] == 1) {
                                    return false;
                                } else {
                                    return true;
                                }
                            }
                        } else if (tileToBePlaced.getHexC().getHexTerrainType() == terrainTypes.VOLCANO) {
                            if (gameBoardPositionArray[colPos - 1][rowPos + 1].getHexTerrainType() == terrainTypes.VOLCANO) {
                                if ((gameBoardPositionArray[colPos][rowPos + 1].getSettlementID()
                                        == gameBoardPositionArray[colPos][rowPos].getSettlementID()) &&
                                        (settlementSizeList[gameBoardPositionArray[colPos][rowPos].getSettlementID()] == 2)) {
                                    return false;
                                } else if (settlementSizeList[gameBoardPositionArray[colPos][rowPos].getSettlementID()] == 1) {
                                    return false;
                                } else if (settlementSizeList[gameBoardPositionArray[colPos][rowPos + 1].getSettlementID()] == 1) {
                                    return false;
                                } else {
                                    return true;
                                }
                            }
                        }
                    }
                }
                if (tileIsOddAndFlipped(rowPos, tileToBePlaced)){
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
                                        (settlementSizeList[gameBoardPositionArray[colPos + 1][rowPos + 1].getSettlementID()] == 2)) {
                                    return false;
                                } else if (settlementSizeList[gameBoardPositionArray[colPos][rowPos + 1].getSettlementID()] == 1) {
                                    return false;
                                } else if (settlementSizeList[gameBoardPositionArray[colPos + 1][rowPos + 1].getSettlementID()] == 1) {
                                    return false;
                                } else {
                                    return true;
                                }
                            }
                        } else if (tileToBePlaced.getHexB().getHexTerrainType() == terrainTypes.VOLCANO) {
                            if (gameBoardPositionArray[colPos + 1][rowPos + 1].getHexTerrainType() == terrainTypes.VOLCANO) {
                                if ((gameBoardPositionArray[colPos][rowPos].getSettlementID()
                                        == gameBoardPositionArray[colPos][rowPos + 1].getSettlementID()) &&
                                        (settlementSizeList[gameBoardPositionArray[colPos][rowPos].getSettlementID()] == 2)) {
                                    return false;
                                } else if (settlementSizeList[gameBoardPositionArray[colPos][rowPos].getSettlementID()] == 1) {
                                    return false;
                                } else if (settlementSizeList[gameBoardPositionArray[colPos][rowPos + 1].getSettlementID()] == 1) {
                                    return false;
                                } else {
                                    return true;
                                }
                            }
                        } else if (tileToBePlaced.getHexC().getHexTerrainType() == terrainTypes.VOLCANO) {
                            if (gameBoardPositionArray[colPos][rowPos + 1].getHexTerrainType() == terrainTypes.VOLCANO) {
                                if ((gameBoardPositionArray[colPos][rowPos].getSettlementID()
                                        == gameBoardPositionArray[colPos + 1][rowPos + 1].getSettlementID()) &&
                                        (settlementSizeList[gameBoardPositionArray[colPos][rowPos].getSettlementID()] == 2)) {
                                    return false;
                                } else if (settlementSizeList[gameBoardPositionArray[colPos][rowPos].getSettlementID()] == 1) {
                                    return false;
                                } else if (settlementSizeList[gameBoardPositionArray[colPos + 1][rowPos + 1].getSettlementID()] == 1) {
                                    return false;
                                } else {
                                    return true;
                                }
                            }
                        }
                    }
            }

            if(tileIsEvenAndNotFlipped(rowPos, tileToBePlaced)){
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
                                    (settlementSizeList[gameBoardPositionArray[colPos][rowPos - 1].getSettlementID()] == 2)) {
                                return false;
                            } else if (settlementSizeList[gameBoardPositionArray[colPos][rowPos - 1].getSettlementID()] == 1) {
                                return false;
                            } else if (settlementSizeList[gameBoardPositionArray[colPos - 1][rowPos - 1].getSettlementID()] == 1) {
                                return false;
                            } else {
                                return true;
                            }
                        }
                    } else if (tileToBePlaced.getHexB().getHexTerrainType() == terrainTypes.VOLCANO) {
                        if (gameBoardPositionArray[colPos - 1][rowPos - 1].getHexTerrainType() == terrainTypes.VOLCANO) {
                            if ((gameBoardPositionArray[colPos][rowPos - 1].getSettlementID()
                                    == gameBoardPositionArray[colPos][rowPos].getSettlementID()) &&
                                    (settlementSizeList[gameBoardPositionArray[colPos][rowPos].getSettlementID()] == 2)) {
                                return false;
                            } else if (settlementSizeList[gameBoardPositionArray[colPos][rowPos].getSettlementID()] == 1) {
                                return false;
                            } else if (settlementSizeList[gameBoardPositionArray[colPos][rowPos - 1].getSettlementID()] == 1) {
                                return false;
                            } else {
                                return true;
                            }
                        }
                    } else if (tileToBePlaced.getHexC().getHexTerrainType() == terrainTypes.VOLCANO) {
                        if (gameBoardPositionArray[colPos][rowPos - 1].getHexTerrainType() == terrainTypes.VOLCANO) {
                            if ((gameBoardPositionArray[colPos][rowPos].getSettlementID()
                                    == gameBoardPositionArray[colPos - 1][rowPos - 1].getSettlementID()) &&
                                    (settlementSizeList[gameBoardPositionArray[colPos][rowPos].getSettlementID()] == 2)) {
                                return false;
                            } else if (settlementSizeList[gameBoardPositionArray[colPos][rowPos].getSettlementID()] == 1) {
                                return false;
                            } else if (settlementSizeList[gameBoardPositionArray[colPos - 1][rowPos - 1].getSettlementID()] == 1) {
                                return false;
                            } else {
                                return true;
                            }
                        }
                    }
                }
            }
        if(tileIsOddAndNotFlipped(rowPos, tileToBePlaced)){
                if((gameBoardPositionArray[colPos][rowPos] != null
                   && gameBoardPositionArray[colPos][rowPos-1] != null
                   && gameBoardPositionArray[colPos+1][rowPos-1] != null)
                   &&
                   (gameBoardPositionArray[colPos][rowPos].getHexLevel() == gameBoardPositionArray[colPos][rowPos-1].getHexLevel()
                   && gameBoardPositionArray[colPos][rowPos].getHexLevel() == gameBoardPositionArray[colPos+1][rowPos-1].getHexLevel()
                   && gameBoardPositionArray[colPos][rowPos-1].getHexLevel() == gameBoardPositionArray[colPos+1][rowPos-1].getHexLevel())
                   &&
                   !(gameBoardPositionArray[colPos][rowPos].getParentTileID() == gameBoardPositionArray[colPos][rowPos-1].getParentTileID()
                   && gameBoardPositionArray[colPos][rowPos].getParentTileID() == gameBoardPositionArray[colPos+1][rowPos-1].getParentTileID()
                   && gameBoardPositionArray[colPos][rowPos-1].getParentTileID() == gameBoardPositionArray[colPos+1][rowPos-1].getParentTileID())
                   &&
                   (gameBoardPositionArray[colPos][rowPos].getTigerCount() == 0
                   && gameBoardPositionArray[colPos][rowPos-1].getTigerCount() == 0
                   && gameBoardPositionArray[colPos+1][rowPos-1].getTigerCount() == 0)
                   &&
                   (gameBoardPositionArray[colPos][rowPos].getTotoroCount() == 0
                   && gameBoardPositionArray[colPos][rowPos-1].getTotoroCount() == 0
                   && gameBoardPositionArray[colPos+1][rowPos-1].getTotoroCount() == 0)) {
                    if(tileToBePlaced.getHexA().getHexTerrainType() == terrainTypes.VOLCANO) {
                        if(gameBoardPositionArray[colPos][rowPos].getHexTerrainType() == terrainTypes.VOLCANO) {
                            if((gameBoardPositionArray[colPos][rowPos-1].getSettlementID()
                                    == gameBoardPositionArray[colPos+1][rowPos-1].getSettlementID()) &&
                                    (settlementSizeList[gameBoardPositionArray[colPos][rowPos-1].getSettlementID()] == 2)) {
                                return false;
                            }
                            else if(settlementSizeList[gameBoardPositionArray[colPos][rowPos-1].getSettlementID()] == 1) {
                                return false;
                            }
                            else if(settlementSizeList[gameBoardPositionArray[colPos+1][rowPos-1].getSettlementID()] == 1) {
                                return false;
                            }
                            else {
                                return true;
                            }
                        }
                    }
                    else if(tileToBePlaced.getHexB().getHexTerrainType() == terrainTypes.VOLCANO) {
                        if(gameBoardPositionArray[colPos][rowPos-1].getHexTerrainType() == terrainTypes.VOLCANO) {
                            if((gameBoardPositionArray[colPos][rowPos].getSettlementID()
                                    == gameBoardPositionArray[colPos+1][rowPos-1].getSettlementID()) &&
                                    (settlementSizeList[gameBoardPositionArray[colPos][rowPos+1].getSettlementID()] == 2)) {
                                return false;
                            }
                            else if(settlementSizeList[gameBoardPositionArray[colPos][rowPos].getSettlementID()] == 1) {
                                return false;
                            }
                            else if(settlementSizeList[gameBoardPositionArray[colPos+1][rowPos-1].getSettlementID()] == 1) {
                                return false;
                            }
                            else {
                                return true;
                            }
                        }
                    }
                    else if(tileToBePlaced.getHexC().getHexTerrainType() == terrainTypes.VOLCANO) {
                        if(gameBoardPositionArray[colPos+1][rowPos-1].getHexTerrainType() == terrainTypes.VOLCANO) {
                            if((gameBoardPositionArray[colPos][rowPos].getSettlementID()
                                    == gameBoardPositionArray[colPos][rowPos-1].getSettlementID()) &&
                                    (settlementSizeList[gameBoardPositionArray[colPos][rowPos].getSettlementID()] == 2)) {
                                return false;
                            }
                            else if(settlementSizeList[gameBoardPositionArray[colPos][rowPos].getSettlementID()] == 1) {
                                return false;
                            }
                            else if(settlementSizeList[gameBoardPositionArray[colPos][rowPos-1].getSettlementID()] == 1) {
                                return false;
                            }
                            else {
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
        if(tileIsOddAndFlipped(rowPos, tileToBePlaced) && thereIsNotAFlippedOddTileThere(colPos, rowPos))
            setOddFlippedCoordinatesAndUpdateGameboard(colPos, rowPos, tileToBePlaced);
        if(tileIsEvenAndNotFlipped(rowPos, tileToBePlaced) && thereIsNotANonFlippedEvenTileThere(colPos, rowPos))
            setEvenNotFlippedCoordinatesAndUpdateGameboard(colPos, rowPos, tileToBePlaced);
        if(tileIsOddAndNotFlipped(rowPos, tileToBePlaced) && thereIsNotANonFlippedOddTileThere(colPos, rowPos))
            setOddNotFlippedCoordinatesAndUpdateGameboard(colPos, rowPos, tileToBePlaced);

        updateValidTilePlacementList(tileToBePlaced);
        incrementGameboardTileID();
        incrementGameboardHexID();
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
                checkIfHexDoesNotOccupyPosition(colPos, rowPos +1) &&
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
            if (validPlacementArray[colPos][rowPos] == -1)            return false;
            if (validPlacementArray[colPos - 1][rowPos + 1] == -1)    return false;
            if (validPlacementArray[colPos][rowPos + 1] == -1)        return false;
        } else if (tileIsOddAndFlipped(rowPos, tileToBePlaced)) {
            if (validPlacementArray[colPos][rowPos] == -1)            return false;
            if (validPlacementArray[colPos - 1][rowPos + 1] == -1)    return false;
            if (validPlacementArray[colPos + 1][rowPos + 1] == -1)    return false;
        } else if(tileIsEvenAndNotFlipped(rowPos, tileToBePlaced)){
            if (validPlacementArray[colPos][rowPos] == -1)            return false;
            if (validPlacementArray[colPos - 1][rowPos - 1] == -1)    return false;
            if (validPlacementArray[colPos][rowPos - 1] == -1)        return false;
        } else if(tileIsOddAndNotFlipped(rowPos, tileToBePlaced)){
            if (validPlacementArray[colPos][rowPos] == -1)            return false;
            if (validPlacementArray[colPos][rowPos - 1] == -1)        return false;
            if (validPlacementArray[colPos + 1][rowPos - 1] == -1)    return false;
        }
        return true;
    }

    boolean tileIsEvenAndFlipped(int rowPos, Tile tileToBePlaced){
        return (tileToBePlaced.isFlipped() && checkIfEven(rowPos));
    }

    boolean tileIsOddAndFlipped(int rowPos, Tile tileToBePlaced){
        return (tileToBePlaced.isFlipped() && !checkIfEven(rowPos));
    }

    boolean tileIsEvenAndNotFlipped(int rowPos, Tile tileToBePlaced){
        return (!tileToBePlaced.isFlipped() && checkIfEven(rowPos));
    }

    boolean tileIsOddAndNotFlipped(int rowPos, Tile tileToBePlaced){
        return (!tileToBePlaced.isFlipped() && !checkIfEven(rowPos));
    }

    boolean checkIfTileBeingPlacedWillBeAdjacent(int colPos, int rowPos, Tile tileBeingPlaced){
        if(tileBeingPlaced.isFlipped()){
            if(validPlacementArray[colPos+1][rowPos+2] == 1){
                return true;
            }else if( validPlacementArray[colPos][rowPos+2] == 1 ){
                return true;
            }else if( validPlacementArray[colPos-1][rowPos+2] == 1){
                return true;
            }else if( validPlacementArray[colPos-2][rowPos+1] == 1) {
                return true;
            }else if( validPlacementArray[colPos-1][rowPos] == 1){
                return true;
            }else if(validPlacementArray[colPos-1][rowPos-1] == 1){
                return true;
            }else if(validPlacementArray[colPos][rowPos-1] == 1){
                return true;
            }else if(validPlacementArray[colPos+1][rowPos] == 1){
                return true;
            }else if(validPlacementArray[colPos+1][rowPos+1] == 1) {
                return true;
            }
        }else{
            if(validPlacementArray[colPos][rowPos+1] == 1){
                return true;
            }else if( validPlacementArray[colPos-1][rowPos+1] == 1 ){
                return true;
            }else if( validPlacementArray[colPos-1][rowPos] == 1){
                return true;
            }else if( validPlacementArray[colPos-2][rowPos-1] == 1) {
                return true;
            }else if( validPlacementArray[colPos-1][rowPos-2] == 1){
                return true;
            }else if(validPlacementArray[colPos][rowPos-2] == 1){
                return true;
            }else if(validPlacementArray[colPos+1][rowPos-2] == 1){
                return true;
            }else if(validPlacementArray[colPos+1][rowPos-1] == 1){
                return true;
            }else if(validPlacementArray[colPos+1][rowPos] == 1){
                return true;
            }
        }
        return false;
    }

    boolean checkIfHexDoesNotOccupyPosition(int colPos, int rowPos) {
        if(gameBoardPositionArray[colPos][rowPos] == null) {
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
        if(tileThatWasPlaced.isFlipped()) {
            setValidFlippedPositions(tileThatWasPlaced);
        } else {
            setValidNotFlippedPositions(tileThatWasPlaced);
        }
        setBoardPositionAsOccupied(tileThatWasPlaced);
    }

    void setValidNotFlippedPositions(Tile tileThatWasPlaced) {
        if(boardPositionRelativeToHomeHexIsEmpty(0,1, tileThatWasPlaced))
            setBoardPositionRelativeToHomeHexAsValid(0, 1, tileThatWasPlaced);
        if(boardPositionRelativeToHomeHexIsEmpty(-1,1, tileThatWasPlaced))
            setBoardPositionRelativeToHomeHexAsValid(-1, 1, tileThatWasPlaced);
        if(boardPositionRelativeToHomeHexIsEmpty(-1,0, tileThatWasPlaced))
            setBoardPositionRelativeToHomeHexAsValid(-1, 0, tileThatWasPlaced);
        if(boardPositionRelativeToHomeHexIsEmpty(-2,-1, tileThatWasPlaced))
            setBoardPositionRelativeToHomeHexAsValid(-2, -1, tileThatWasPlaced);
        if(boardPositionRelativeToHomeHexIsEmpty(-1,-2, tileThatWasPlaced))
            setBoardPositionRelativeToHomeHexAsValid(-1, -2, tileThatWasPlaced);
        if(boardPositionRelativeToHomeHexIsEmpty(0,-2, tileThatWasPlaced))
            setBoardPositionRelativeToHomeHexAsValid(0, -2, tileThatWasPlaced);
        if(boardPositionRelativeToHomeHexIsEmpty(1,-2, tileThatWasPlaced))
            setBoardPositionRelativeToHomeHexAsValid(1, -2, tileThatWasPlaced);
        if(boardPositionRelativeToHomeHexIsEmpty(1,-1, tileThatWasPlaced))
            setBoardPositionRelativeToHomeHexAsValid(1, -1, tileThatWasPlaced);
        if(boardPositionRelativeToHomeHexIsEmpty(1,0, tileThatWasPlaced))
            setBoardPositionRelativeToHomeHexAsValid(1, 0, tileThatWasPlaced);
    }

    void setValidFlippedPositions(Tile tileThatWasPlaced) {
        if(boardPositionRelativeToHomeHexIsEmpty(1,2, tileThatWasPlaced))
            setBoardPositionRelativeToHomeHexAsValid(1, 2, tileThatWasPlaced);
        if(boardPositionRelativeToHomeHexIsEmpty(0,2, tileThatWasPlaced));
            setBoardPositionRelativeToHomeHexAsValid(0, 2, tileThatWasPlaced);
        if(boardPositionRelativeToHomeHexIsEmpty(-1,2, tileThatWasPlaced))
            setBoardPositionRelativeToHomeHexAsValid(-1, 2, tileThatWasPlaced);
        if(boardPositionRelativeToHomeHexIsEmpty(-2,1, tileThatWasPlaced))
            setBoardPositionRelativeToHomeHexAsValid(-2, 1, tileThatWasPlaced);
        if(boardPositionRelativeToHomeHexIsEmpty(-1,0, tileThatWasPlaced))
            setBoardPositionRelativeToHomeHexAsValid(-1, 0, tileThatWasPlaced);
        if(boardPositionRelativeToHomeHexIsEmpty(-1,-1, tileThatWasPlaced))
            setBoardPositionRelativeToHomeHexAsValid(-1, -1, tileThatWasPlaced);
        if(boardPositionRelativeToHomeHexIsEmpty(0,-1, tileThatWasPlaced))
            setBoardPositionRelativeToHomeHexAsValid(0, -1, tileThatWasPlaced);
        if(boardPositionRelativeToHomeHexIsEmpty(1,0, tileThatWasPlaced))
            setBoardPositionRelativeToHomeHexAsValid(1, 0, tileThatWasPlaced);
        if(boardPositionRelativeToHomeHexIsEmpty(1,1, tileThatWasPlaced))
            setBoardPositionRelativeToHomeHexAsValid(1, 1, tileThatWasPlaced);
    }

    void setBoardPositionAsOccupied(Tile tileThatWasPlaced){
        validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())][accessGameboardYValue(tileThatWasPlaced.getHexA())] = -1;
        validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexB())][accessGameboardYValue(tileThatWasPlaced.getHexB())] = -1;
        validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexC())][accessGameboardYValue(tileThatWasPlaced.getHexC())] = -1;
    }

    void setBoardPositionRelativeToHomeHexAsValid(int colOffset, int rowOffset, Tile tileThatWasPlaced){
        validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())+colOffset][accessGameboardYValue(tileThatWasPlaced.getHexA())+rowOffset] = 1;
    }

    boolean boardPositionRelativeToHomeHexIsEmpty(int colOffset, int rowOffset, Tile tileThatWasPlaced){
        return validPlacementArray[accessGameboardXValue(tileThatWasPlaced.getHexA())+colOffset][accessGameboardYValue(tileThatWasPlaced.getHexA())+rowOffset] == 0;
    }

    int accessGameboardXValue(Hex hexToCheck) {
        return hexToCheck.getHexCoordinate().getColumnPosition();
    }

    int accessGameboardYValue(Hex hexToCheck) {
        return hexToCheck.getHexCoordinate().getRowPosition();
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

    void incrementGameboardHexID() {
        this.GameboardHexID += 3;
    }

    Hex[][] getGameBoardPositionArray() {
        return gameBoardPositionArray;
    }

    int[][] getValidPlacementArray(){
        return validPlacementArray;
    }

    void setSettlementSizeList(int id, int size) {
        this.settlementSizeList[id] = size;
    }

    int getSettlementSizeBasedOnID(int id) {
        return this.settlementSizeList[id];
    }
}