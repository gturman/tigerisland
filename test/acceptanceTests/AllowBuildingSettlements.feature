Feature: Allowing the building of a settlement

Background:
Given I am player
And I am in the build phase of my turn
And I have chosen to settle
And I still have villager

Scenario: I place a villager on a hex
Given the hex is habitable
And the hex is currently empty
And the hex is level one
When I try to place a villager on a hex
Then I should see that my settlement was placed
And my total villager count has decreased by one
And my point total has increased by one
And if my villager is adjacent to one of my settlements, that villager merges into that settlement and its size increases by one, otherwise it becomes it’s own settlement