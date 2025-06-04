Feature: Car management in driver-service

  Scenario: Find car by id
    Given car with id 1
    When searching car by id
    Then received car response has status 200
    And car body equals to
    """
      {
        "id": 1,
        "brand": "Toyota",
        "number": "1234AB-3",
        "color": "Red",
        "driverId": "1377f946-6c0a-45eb-88a6-e9131c3c27ff"
      }
    """

  Scenario: Invalid attempt to find car by id
    Given car with id 20
    When searching car by id
    Then received car response has status 404

  Scenario: Find all cars
    When getting all cars
    Then received car response has status 200

  Scenario: Sign up new car
    Given car's data for signing up
    """
      {
        "brand": "BMW",
        "number": "1012TP-7",
        "color": "Yellow",
        "driverId": "7757fc43-a2c4-4504-b464-bf61b293af72"
      }
    """
    When saving car
    Then received car response has status 201
    And body after create car operation equals to
    """
      {
        "id": null,
        "brand": "BMW",
        "number": "1012TP-7",
        "color": "Yellow",
        "driverId": "7757fc43-a2c4-4504-b464-bf61b293af72"
      }
    """
    And check if created car in db

  Scenario: Invalid attempt to sign up new car (repeated data)
    Given car's data for signing up
    """
      {
        "brand": "BMW",
        "number": "1234AB-3",
        "color": "Yellow",
        "driverId": "7757fc43-a2c4-4504-b464-bf61b293af72"
      }
    """
    When saving car
    Then received car response has status 400

  Scenario: Update car
    Given car's data for update with id 1
    """
      {
        "brand": "Toyota",
        "number": "1234AB-3",
        "color": "Pink",
        "driverId": "1377f946-6c0a-45eb-88a6-e9131c3c27ff"
      }
    """
    When updating car
    Then received car response has status 200
    And car body equals to
    """
      {
        "id": 1,
        "brand": "Toyota",
        "number": "1234AB-3",
        "color": "Pink",
        "driverId": "1377f946-6c0a-45eb-88a6-e9131c3c27ff"
      }
    """

  Scenario: Invalid attempt to update car (invalid id)
    Given car's data for update with id 20
    """
      {
        "brand": "Toyota",
        "number": "1234AB-3",
        "color": "Pink",
        "driverId": "1377f946-6c0a-45eb-88a6-e9131c3c27ff"
      }
    """
    When updating car
    Then received car response has status 404

  Scenario: Invalid attempt to update car (repeated data)
    Given car's data for update with id 1
    """
      {
        "brand": "Toyota",
        "number": "1234AB-5",
        "color": "Pink",
        "driverId": "1377f946-6c0a-45eb-88a6-e9131c3c27ff"
      }
    """
    When updating car
    Then received car response has status 400

  Scenario: Delete car
    Given car with id 1
    When deleting car
    Then received car response has status 204
    And check if car deleted in db

  Scenario: Invalid attempt to delete car
    Given car with id 20
    When deleting car
    Then received car response has status 404
