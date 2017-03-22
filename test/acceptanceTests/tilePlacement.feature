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

