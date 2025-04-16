Feature: Rate management in rates-service

  Scenario: Full lifecycle of basic operation for rate management
    Given "passenger" with id "e5f3a033-51a5-4bc0-9443-2b63d5f59ca3" wants to rate 4 the ride 1 with driver "4ba41155-bcff-48d5-8675-bc0aac800e99" and left the comment "it was cozy ride"
    When the passenger rates a ride
    Then the rate is created
    And we check the existence
    When we try to find the rate by id
    Then we successfully get the rate
    When passenger tries to find his average rate
    Then we successfully get the average rate
    When we try to check all driver's rates
    Then we get the page
    When passenger changes the rate value to 5
    Then the rate value is changed
    And we check value changes to 5
    When passenger deletes the rate
    Then the rate is actually deleted
