Feature: Allowing player tile placement

  Background:
    Given I am the player
    And I am in the tile placement phase of my turn
    And I have drawn a tile
    And I am placing a tile on the board at a certain level

  Scenario: I place a tile on an empty board
    Given the board is empty
    When I try to place a tile
    Then my tile is placed on the center of the board

  Scenario: I place a tile on the same level
    Given one or more edge of my tiles touches one or more of another tile's edge
    When I try to place a tile
    Then my tile is placed correctly

  Scenario: I try to place a tile completely over another tile
    Given my tile entirely overlaps another tile one level below it
    When I try to place my tile
    Then my tile is prevented from being placed

  Scenario: I try to place a tile over hexes of different levels
    Given one or more hex is not of the same level
    When I try to place a tile
    Then my tile is prevented from being placed

  Scenario: I try to place my tile’s volcano over a non-volcano	hex
    Given my tile’s volcano was not placed over a volcano
    When I try to place a tile
    Then my tile is prevented from being placed

  Scenario: I try to place a tile that is not adjacent to any hex
    Given no edges of my tile touches another hexes’ edge
    When I try to place a tile
    Then my tile is prevented from being placed
