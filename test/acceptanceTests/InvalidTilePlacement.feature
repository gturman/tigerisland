Feature: Disallowing player tile placement

  Background:
    Given I am a player about to do an invalid placement
    And I am in the tile placement phase of my turn for an invalid placement
    And I have drawn a tile for an invalid placement
    And I am placing a tile on the board at a certain level

  Scenario: I try to place a tile that is not adjacent to any hex
    Given no edges of my tile touches another hexes’ edge
    When I try to place a tile
    Then my tile is prevented from being placed

  Scenario: I try to place a tile completely over another tile
    Given my tile entirely overlaps another tile one level below it
    When I try to place a tile
    Then my tile is prevented from being placed

  Scenario: I place a tile overlapping a size one settlement
    Given my tile overlaps a size one settlement
    When I try to place a tile
    Then my tile is prevented from being placed

  Scenario: I try to place a tile over hexes of different levels
    Given one or more hex is not of the same level
    When I try to place a tile
    Then my tile is prevented from being placed

  Scenario: I try to place a tile over a Totoro
    Given at least once hex below my tile has a Totoro
    When I try to place a tile
    Then my tile is prevented from being placed

  Scenario: I try to place a tile over a Tiger Playground
    Given at least one hex below my tile has a Tiger Playground
    When I try to place a tile
    Then my tile is prevented from being placed

  Scenario: I try to place my tile’s volcano over a non-volcano	hex
    Given my tile’s volcano was not placed over a volcano
    When I try to place a tile
    Then my tile is prevented from being placed
