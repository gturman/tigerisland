Feature: Allowing player tile placement

  Background:
    Given I am a player
    And I am in the tile placement phase of my turn
    And I have drawn a tile
    And I am placing a tile on the board at a certain level

  Scenario: I place a tile on an empty board
    Given the board is empty
    When I place the tile
    Then my tile is placed on the center of the board

  Scenario: I place a tile on the same level
    Given one or more edges of my tile touches one or more of another tile’s edges
    When I place the tile
    Then I should see that my tile was placed

  Scenario: I place a tile over other tiles
    Given my tile’s volcano is aligned with the bottom tile’s volcano
    And my tile does not completely overlap a single tile
    And my tile does not completely overlap a settlement or Totoro or Tiger
    And all of the tiles I am trying to cover are of the same level
    When I place the tile
    Then I should see that my tile was placed