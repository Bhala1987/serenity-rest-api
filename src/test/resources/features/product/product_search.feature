Feature: Search for the product

### Please use endpoint GET https://waarkoop-server.herokuapp.com/api/v1/search/demo/{product} for getting the products.
### Available products: "orange", "apple", "pasta", "cola"
### Prepare Positive and negative scenarios

  #POSITIVE TEST CASES
  Scenario Outline: Search for the product results
    When we hit the endpoint with "<product>"
    And we see the results displayed for "<product>"
    Examples:
      | product |
      | apple   |
      | orange  |
      | pasta   |
      | cola    |

  #NEGATIVE TEST CASES
  Scenario Outline: Search for the invalid product results
    When we hit the endpoint with "<invalid_product>"
    And we should see the error
      | message   | server               |
      | Not found | https://waarkoop.com |
    Examples:
      | invalid_product |
      | 10000           |
      | car             |
      | %^*(            |
      | a1O0&_Zf        |

  Scenario: empty product
    When we hit the endpoint with ""
    And we should not be authenticated

