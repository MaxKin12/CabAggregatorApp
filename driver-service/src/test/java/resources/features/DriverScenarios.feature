Feature: Driver management in driver-service

  Scenario: Find driver by id
    Given driver with id 1
    When searching driver by id
    Then received driver response has status 200
    And driver body no carsId equals to
    """
      {
        "id": 1,
        "name": "ivan",
        "email": "driver@email1.com",
        "phone": "+375292110001",
        "gender": "male",
        "rate": null
      }
    """

  Scenario: Invalid attempt to find driver by id
    Given driver with id 2000000
    When searching driver by id
    Then received driver response has status 404

  Scenario: Find all drivers
    When getting all drivers
    Then received driver response has status 200

  Scenario: Sign up new driver
    Given driver's data for signing up
    """
      {
        "name": "Pepe the Frog",
        "email": "qwaqwa@gmail.com",
        "phone": "+375293333333",
        "gender": "male"
      }
    """
    When saving driver
    Then received driver response has status 201
    And body after driver create operation equals to
    """
      {
        "id": null,
        "name": "Pepe the Frog",
        "email": "qwaqwa@gmail.com",
        "phone": "+375293333333",
        "gender": "male",
        "rate": null,
        "carIds": []
      }
    """
    And check if driver created in db

  Scenario: Invalid attempt to sign up new driver (repeated data)
    Given driver's data for signing up
    """
      {
        "name": "Pepe the Frog",
        "email": "qwaqwa@gmail.com",
        "phone": "+375292110001",
        "gender": "male"
      }
    """
    When saving driver
    Then received driver response has status 400

  Scenario: Update driver
    Given driver's data for update with id 1
    """
      {
        "name": "Kermit the Frog",
        "email": "driver@email1.com",
        "phone": "+375292110001",
        "gender": "female"
      }
    """
    When updating driver
    Then received driver response has status 200
    And driver body no carsId equals to
    """
      {
        "id": 1,
        "name": "Kermit the Frog",
        "email": "driver@email1.com",
        "phone": "+375292110001",
        "gender": "female",
        "rate": null
      }
    """

  Scenario: Invalid attempt to update driver (invalid id)
    Given driver's data for update with id 2000000
    """
      {
        "name": "Pepe the Frog",
        "email": "driver@email1.com",
        "phone": "+375291110001",
        "gender": "male"
      }
    """
    When updating driver
    Then received driver response has status 404

  Scenario: Invalid attempt to update driver (repeated data)
    Given driver's data for update with id 1
    """
      {
        "name": "Pepe the Frog",
        "email": "driver@email1.com",
        "phone": "+375292110002",
        "gender": "male"
      }
    """
    When updating driver
    Then received driver response has status 400

  Scenario: Delete driver
    Given driver with id 11
    When deleting driver
    Then received driver response has status 204

  Scenario: Invalid attempt to delete driver
    Given driver with id 2000000
    When deleting driver
    Then received driver response has status 404