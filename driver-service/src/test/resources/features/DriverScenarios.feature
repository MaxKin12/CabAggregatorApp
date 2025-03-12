Feature: Driver management in driver-service

  Scenario: Find driver by id
    Given driver with id 1
    When searching driver by id
    Then received driver response has status 200
    And driver body equals to
    """
      {
        "id": 1,
        "name": "John Cena",
        "email": "john@gmail.com",
        "phone": "+375291111111",
        "gender": "male",
        "rate": 4.52,
        "carIds": [1]
      }
    """

  Scenario: Invalid attempt to find driver by id
    Given driver with id 20
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
    And check if driver is created in db

  Scenario: Invalid attempt to sign up new driver (repeated data)
    Given driver's data for signing up
    """
      {
        "name": "Pepe the Frog",
        "email": "john@gmail.com",
        "phone": "+375293333333",
        "gender": "male"
      }
    """
    When saving driver
    Then received driver response has status 400

  Scenario: Update driver
    Given driver's data for update with id 1
    """
      {
        "name": "Pepe the Frog",
        "email": "john@gmail.com",
        "phone": "+375291111111",
        "gender": "female"
      }
    """
    When updating driver
    Then received driver response has status 200
    And driver body equals to
    """
      {
        "id": 1,
        "name": "Pepe the Frog",
        "email": "john@gmail.com",
        "phone": "+375291111111",
        "gender": "female",
        "rate": 4.52,
        "carIds": [1]
      }
    """

  Scenario: Invalid attempt to update driver (invalid id)
    Given driver's data for update with id 20
    """
      {
        "name": "Pepe the Frog",
        "email": "john@gmail.com",
        "phone": "+375291111111",
        "gender": "female"
      }
    """
    When updating driver
    Then received driver response has status 404

  Scenario: Invalid attempt to update driver (repeated data)
    Given driver's data for update with id 1
    """
      {
        "name": "Pepe the Frog",
        "email": "leeroy228@gmail.com",
        "phone": "+375291111111",
        "gender": "female"
      }
    """
    When updating driver
    Then received driver response has status 400

  Scenario: Delete driver
    Given driver with id 1
    When deleting driver
    Then received driver response has status 204
    And check if driver is deleted in db

  Scenario: Invalid attempt to delete driver
    Given driver with id 20
    When deleting driver
    Then received driver response has status 404
