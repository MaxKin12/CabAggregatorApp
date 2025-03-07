Feature: Car management in driver-service

  Scenario: Find car by id
    Given car with id 1
    When searching car by id
    Then received car response has status 200
    And car body equals to
    """
      {
        "id": 1,
        "brand": "volkswagen",
        "number": "1001TP-7",
        "color": "grey",
        "driverId": 1
      }
    """

  Scenario: Invalid attempt to find car by id
    Given car with id 2000000
    When searching car by id
    Then received car response has status 404

  Scenario: Find all cars
    When getting all cars
    Then received car response has status 200

  Scenario: Sign up new car
    Given car's data for signing up
    """
      {
        "brand": "bmw",
        "number": "1012TP-7",
        "color": "yellow",
        "driverId": 2
      }
    """
    When saving car
    Then received car response has status 201
    And body after create car operation equals to
    """
      {
        "id": null,
        "brand": "bmw",
        "number": "1012TP-7",
        "color": "yellow",
        "driverId": 2
      }
    """
    And check if created car in db

  Scenario: Invalid attempt to sign up new car (repeated data)
    Given car's data for signing up
    """
      {
        "brand": "volkswagen",
        "number": "1002TA-7",
        "color": "pink",
        "driverId": 1
      }
    """
    When saving car
    Then received car response has status 400

  Scenario: Update car
    Given car's data for update with id 1
    """
      {
        "brand": "volkswagen",
        "number": "1001TP-7",
        "color": "pink",
        "driverId": 1
      }
    """
    When updating car
    Then received car response has status 200
    And car body equals to
    """
      {
        "id": 1,
        "brand": "volkswagen",
        "number": "1001TP-7",
        "color": "pink",
        "driverId": 1
      }
    """

  Scenario: Invalid attempt to update car (invalid id)
    Given car's data for update with id 2000000
    """
      {
        "brand": "volkswagen",
        "number": "1001TP-7",
        "color": "pink",
        "driverId": 1
      }
    """
    When updating car
    Then received car response has status 404

  Scenario: Invalid attempt to update car (repeated data)
    Given car's data for update with id 1
    """
      {
        "brand": "volkswagen",
        "number": "1002TA-7",
        "color": "pink",
        "driverId": 1
      }
    """
    When updating car
    Then received car response has status 400

  Scenario: Delete car
    Given car with id 10
    When deleting car
    Then received car response has status 204

  Scenario: Invalid attempt to delete car
    Given car with id 2000000
    When deleting car
    Then received car response has status 404
