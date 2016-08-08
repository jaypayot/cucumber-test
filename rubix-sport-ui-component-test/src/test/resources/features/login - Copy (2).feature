Feature: Sample scenario testing login page using Sports UI component connected to wiremock container

#  Background:
#    Given that I have the correct xml file
#    Then I start to run the mock file

  @SampleTest
  Scenario: Login successfully to Rubix Sports and check if background is White

     Given I launch Rubix Sports homepage
      When I enter valid "defaultUsername" in username field
      And enter valid "defaultPassword" in the password field
      And click the login button
      Then the homepage background color is "White"
      And I logout from Rubix Sports
#
#  @SampleTest
#  Scenario: Login successfully to Rubix Sports and check if background is pink.
#    Given I launch Rubix Sports homepage
#    When I enter valid "defaultUsername" in username field
#    And enter valid "defaultPassword" in the password field
#    And click the login button
#    Then the homepage background color is "Pink"