Feature: Test

  Scenario: UI - Create and Edit Publisher and Posts
    Given I navigate to "http://localhost:3000/admin" and log in with username "admin@example.com" and password "password"
    When I enter Publishers Page
    When I create a new Publisher with name="test user " and email="thisistest{replace_with_time}@yahoo.com"
    When I validate Publishers Table Data
    When I enter Posts Page
    When I create a new Post with title="test title " content="Testing our Post Creation " status="ACTIVE"
    When I validate Posts Table Data
    When I open Post Edit Page
    When I Edit Status to "REMOVED"
    When I Save Edited Changes
    When I validate Posts Table Data



  Scenario: API - Create and Edit Publisher and Posts
    Given I navigate to "http://localhost:3000/admin" and log in with username "admin@example.com" and password "password"
    When I create a new API Publisher with name="test user " and email="thisistest{replace_with_time}@yahoo.com"
    When I create a new API Post with title="test title " and content="test content " and status="ACTIVE"
    When I update "status" to "REMOVED"