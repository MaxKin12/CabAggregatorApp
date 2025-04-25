Feature: Ride management in rides-service

  Scenario: Full ride-booking lifecycle
    Given passenger with id "e5f3a033-51a5-4bc0-9443-2b63d5f59ca3" and driver with id "4ba41155-bcff-48d5-8675-bc0aac800e99" and car with id 1
    When the passenger books a ride
    Then the ride is create actually and stored in a queue
    When the driver is ready and accepts a ride
    Then the driver is attached to the ride and rides status is changed to "accepted"
    When we try to find the ride by id
    Then we successfully get the ride
    When the passenger search for the last ride
    Then he gets the current ride
    When a person changes the ride status to "cancelled"
    Then the ride status is actually changed to "cancelled"
    When a person deletes the ride
    Then the ride is actually deleted
