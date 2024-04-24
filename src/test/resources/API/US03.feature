Feature: US03 As a user, I should be able to access the financial information of the customer via API connection.

  @03-01
  Scenario Outline: TC01 When the get request is sent,
  it should be verified that the status code is 200 and the message and other information in the response body.

    Given The api user constructs the base url with the "customer" token.
    When The api user sets "api/profile/get-customer-data" path parameters
    Then The API user sends a GET request and records the response without body from the api endpoint.
    Then The api user verifies that the status code is 200
    Then The api user verifies that the message information in the response body is "success"
    Then The api user verifies the content of the data <wallet_running_balance>, <wallet_pending_balance>, <total_coupon>, <total_wishlist>, <total_cancel_order> in the response body.

    Examples:
      | wallet_running_balance | wallet_pending_balance | total_coupon | total_wishlist | total_cancel_order |
      | 0                      | 0                      | 1            | 0             | 0                  |

  Scenario: TC02 When a GET request is sent with invalid authorization information,it should be verified
  that the status code returned is 401 and the message information in the response body is "Unauthenticated."

    Given The api user constructs the base url with the "invalid" token.
    When The api user sets "api/profile/get-customer-data" path parameters
    Then The API user records the response without body from the api endpoint, confirming that the status code is '401' and the reason phrase is Unauthorized.