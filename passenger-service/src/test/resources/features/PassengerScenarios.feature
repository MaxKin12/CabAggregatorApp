Feature: Passenger management in passenger-service

  Scenario: Find passenger by id
    Given passenger with id "04f13490-2048-4b99-8514-17a4e90dc3ba"
    When searching passenger by id
    Then received response has status 200
    And body equals to
    """
      {
        "id": "04f13490-2048-4b99-8514-17a4e90dc3ba",
        "name": "John Cena",
        "email": "john@gmail.com",
        "phone": "+375291111111",
        "rate": 4.52
      }
    """

  Scenario: Invalid attempt to find passenger by id
    Given passenger with id "0220f106-09a1-429c-a309-e7f99cf4499a"
    When searching passenger by id
    Then received response has status 404

  Scenario: Find all passengers
    When getting all passengers
    Then received response has status 200

  Scenario: Sign up new passenger
    Given passenger's data for signing up
    """
      {
        "name": "Pepe the Frog",
        "email": "qwaqwa@gmail.com",
        "phone": "+375293333333"
      }
    """
    When saving passenger
    Then received response has status 201
    And body after create operation equals to
    """
      {
        "id": null,
        "name": "Pepe the Frog",
        "email": "qwaqwa@gmail.com",
        "phone": "+375293333333",
        "rate": null
      }
    """
    And check if created in db

  Scenario: Invalid attempt to sign up new passenger (repeated data)
    Given passenger's data for signing up
    """
      {
        "name": "Pepe the Frog",
        "email": "some@email1.com",
        "phone": "+375291111111"
      }
    """
    When saving passenger
    Then received response has status 400

  Scenario: Update passenger
    Given passenger's data for update with id "04f13490-2048-4b99-8514-17a4e90dc3ba"
    """
      {
        "name": "Kermit the Frog",
        "email": "some@email1.com",
        "phone": "+375291110001"
      }
    """
    When updating passenger
    Then received response has status 200
    And body equals to
    """
      {
        "id": "04f13490-2048-4b99-8514-17a4e90dc3ba",
        "name": "Kermit the Frog",
        "email": "some@email1.com",
        "phone": "+375291110001",
        "rate": 4.52
      }
    """

  Scenario: Invalid attempt to update passenger (invalid id)
    Given passenger's data for update with id "0220f106-09a1-429c-a309-e7f99cf4499a"
    """
      {
        "name": "Kermit the Frog",
        "email": "john@gmail.com",
        "phone": "+375291111111"
      }
    """
    When updating passenger
    Then received response has status 404

  Scenario: Invalid attempt to update passenger (repeated data)
    Given passenger's data for update with id "04f13490-2048-4b99-8514-17a4e90dc3ba"
    """
      {
        "name": "Kermit the Frog",
        "email": "john@gmail.com",
        "phone": "+375292222222"
      }
    """
    When updating passenger
    Then received response has status 400

  Scenario: Delete passenger
    Given passenger with id "04f13490-2048-4b99-8514-17a4e90dc3ba"
    When deleting passenger
    Then received response has status 204
    And check if deleted in db

  Scenario: Invalid attempt to delete passenger
    Given passenger with id "0220f106-09a1-429c-a309-e7f99cf4499a"
    When deleting passenger
    Then received response has status 404
