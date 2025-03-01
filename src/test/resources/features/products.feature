Feature: E-Commerce Product Management

# Add new book to the catalog and validating its functionality
Scenario: Add a book to catalog
  Given I login to the app with manager
  Given I have a new product with details
     | name       | price | category | color |
     | TestItem   | 29.99 | Books    | NA    |

  # Products within the catalog must be unique in name/ color and category - if one already exist - try different data
  And the product with the same name color and category does not already exist in the database
  When I create the product through API

  # If the status is not OK - fail the test, print the error message and don't go further
  And I verify the response status from API is correct
  And I retrieve new product ID and other parameters from API response
  And I verify the product exists in database
  And I verify the product ID is identical to the ID from API response
  And I verify the product details corresponds to the inserted data or data from API response

  # Those default values might include values like: purchasedAmount = 0 or updatedDate and createdDate = Today
  And I verify the product default details corresponds to the expected ones
  Then the product should be created successfully

  # Validate, not a manager can see the newly created product and successfully purchase it
  Given I login to the app as a customer
  And I enter products page
  When I see that the created product exists within products list with correct name and data
  And I try to purchase it
  And I verify the message for purchase is successful
  And I verify purchasedAmount field within database has been incremented
  Then the product performance works fine

# Deliberately setting the same name but not the same category - to validate it is still possible
Scenario: Add another product to catalog
  Given I login to the app with manager
  Given I have a new product with details
     | name       | price | category |  color |
     | TestItem   | 29.99 | T-Shirt  |  black |
  And the product with the same name color and category does not already exist in the database
  When I create the product through API
  And I verify the response status from API is correct
  And I retrieve new product ID and other parameters from API response
  And I verify the product exists in database
  And I verify the product ID is identical to the ID from API response
  And I verify the product details corresponds to the inserted data or data from API response
  And I verify the product default details corresponds to the expected ones
  Then the product should be created successfully
  Given I login to the app as a customer
  And I enter products page
  When I see that the created product exists within products list with correct name and data
  And I try to purchase it
  And I verify the message for purchase is successful
  And I verify purchasedAmount field within database has been incremented
  Then the product performance works fine

# Same names and, from time to time - same category or color but never all 3 the same
Scenario: Create multiple products
  Given I login to the app with manager
  Given I have several products with details
       | name               | price | category   | color  |
       | Lord Of The Ring   | 29.99 | T-Shirt    | Blue   |
       | Lord Of The Ring   | 29.99 | T-Shirt    | Red    |
       | Lord Of The Ring   | 29.99 | Book       | NA     |
       | Lord Of The Ring   | 29.99 | Video game | NA     |
       | Lord Of The Ring   | 29.99 | Poster     | White  |
  And each product within the list does not have the same name color and category in the database
  When I create all products through API
  And I verify the response status from API is correct
  And I retrieve ID and other parameters from API response for each newly created product
  And I verify each product exists in database
  And I verify each product ID is identical to the ID from API response
  And I verify each product details corresponds to the inserted data or data from API response
  And I verify each product default details corresponds to the expected ones
  Then all the products should be created successfully
  Given I login to the app as a customer
  And I enter products page
  When I see that each created product exists within products list with correct name and data
  And I try to purchase each one of it
  And I verify the message for each purchase is successful
  And I verify purchasedAmount field within database has been incremented for each product
  Then the product performance works fine

Scenario: Update product
  Given I login to the app with manager
  Given I have a product in the system with details
       | name       | ID  |
       | TestItem   | 123 |
  Given I update the product with details
         | description       | price  |  category    |  units  | discount | weight | color | manufacturer |
         | new description   | 49.99  |  Electronics |  100    | 20       | 2.5    | black | Intel        |

  When I update the product through API
  And I verify the response status from API is correct
  And I verify all the product data has been correctly updated in database
  Then the product should be updated successfully
  Given I login to the app as a customer
  And I enter products page
  When I see that the product data has been updated successfully
  Then product updating process has been completed successfully

Scenario: Process order and logout
  Given I login to the app with manager
  Given I have an active order
  When I process the order through API
  And I verify the response status from API is correct

  # After completing the order - no further orders should be found on my page
  When I verify I have no pending orders
  And I logout from the system
  Then Order processing has been completed successfully