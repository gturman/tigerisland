Feature: Allowing player tile draw

  Scenario: I draw a tile
    Given I am a player
    When I try to draw a tile
    Then I receive a tile

