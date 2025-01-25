package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class StepDefinitions {

    @Given("I login to the app with manager")
    public void i_login_to_the_app_with_manager() {
        System.out.println("Logging in as manager");
    }

    @Given("I have a new product with details")
    public void i_have_a_new_product_with_details(io.cucumber.datatable.DataTable dataTable) {
        System.out.println("Creating a new product with the following details : " + dataTable);
    }

    @And("the product with the same name color and category does not already exist in the database")
    public void the_product_with_the_same_name_color_and_category_does_not_already_exist() {
        System.out.println("Checking whether a product with the same name, color and category exist.");
        System.out.println("If so - choose another product data");
    }

    @When("I create the product through API")
    public void i_create_the_product_through_api() {
        System.out.println("Creating the product via API.");
    }

    @And("I verify the response status from API is correct")
    public void i_verify_the_response_status_from_API_is_correct() {
        System.out.println("Validate the status is 200 (successful)");
        System.out.println("If not - fail the test and print the resp0nse error message");
    }

    @And("I retrieve new product ID and other parameters from API response")
    public void i_retrieve_new_product_ID_and_other_parameters_from_API_response() {
        System.out.println("Retrieve Newly created product ID and other parameters from the response JSON");
    }

    @And("I verify the product exists in database")
    public void i_verify_the_product_exists_in_database() {
        System.out.println("Verifying the newly created product exists in the database");
    }

    @And("I verify the product ID is identical to the ID from API response")
    public void i_verify_the_product_ID_is_identical_to_the_ID_from_API_response() {
        System.out.println("verifying the product ID is identical to the ID from API response");
    }

    @And("I verify the product details corresponds to the inserted data or data from API response")
    public void i_verify_the_product_details_corresponds_to_the_inserted_data_or_data_from_API_response() {
        System.out.println("verifying the product details corresponds to the inserted data or data from API response");
    }

    @And("I verify the product default details corresponds to the expected ones")
    public void i_verify_the_product_default_details_corresponds_to_the_expected_ones() {
        System.out.println("verifying the product default details corresponds to the expected ones");
    }

    @Then("the product should be created successfully")
    public void the_product_should_be_created_successfully() {
        System.out.println("Product creation was successful.");
    }

    @Given("I login to the app as a customer")
    public void i_login_to_the_app_as_a_customer() {
        System.out.println("Logging in as a customer and not as a manager");
    }

    @And("I enter products page")
    public void i_enter_products_page() {
        System.out.println("entering products page");
    }

    @When("I see that the created product exists within products list with correct name and data")
    public void i_see_that_the_created_product_exists_within_products_list_with_correct_name_and_data() {
        System.out.println("Validate I can find the created product");
    }

    @And("I try to purchase it")
    public void i_try_to_purchase_it() {
        System.out.println("Try to purchase it");
    }

    @And("I verify the message for purchase is successful")
    public void i_verify_the_message_for_purchase_is_successful() {
        System.out.println("validate I receive a successfully completed order message");
    }

    @And("I verify purchasedAmount field within database has been incremented")
    public void i_verify_purchasedAmount_field_within_database_has_been_incremented() {
        System.out.println("Verify in DB - 'purchasedAmount' has been incrementd");
    }

    @Then("the product performance works fine")
    public void the_product_performance_works_fine() {
        System.out.println("All product creation steps were completed successfully");
    }




    @Given("I have several products with details")
    public void i_have_several_products_with_details(io.cucumber.datatable.DataTable dataTable) {
        System.out.println("several new product with the following details : " + dataTable);
    }

    @And("each product within the list does not have the same name color and category in the database")
    public void each_product_within_the_list_does_not_have_the_same_name_color_and_category_in_the_database() {
        System.out.println("Checking for each product within the list whether a product with the same name, color and category exist.");
        System.out.println("If so - choose another product data");
    }

    @When("I create all products through API")
    public void i_create_all_products_through_api() {
        System.out.println("Creating all the products via API.");
    }

    @And("I retrieve ID and other parameters from API response for each newly created product")
    public void i_retrieve_ID_and_other_parameters_from_API_response_for_each_newly_created_product() {
        System.out.println("store each product ID and all the response JSON Data");
    }

    @And("I verify each product exists in database")
    public void i_verify_each_product_exists_in_database() {
        System.out.println("Validate each product exist in DB");
    }

    @And("I verify each product ID is identical to the ID from API response")
    public void i_verify_each_product_ID_is_identical_to_the_ID_from_API_response() {
        System.out.println("Each ID is identical to the one retrieved from API response");
    }

    @And("I verify each product details corresponds to the inserted data or data from API response")
    public void i_verify_each_product_details_corresponds_to_the_inserted_data_or_data_from_API_response() {
        System.out.println("Each product data is equal to the inserted one or data from the API response");
    }

    @And("I verify each product default details corresponds to the expected ones")
    public void i_verify_each_product_default_details_corresponds_to_the_expected_ones() {
        System.out.println("Each product default data is equal to the expected default for each");
    }


    @Then("all the products should be created successfully")
    public void all_the_products_should_be_created_successfully() {
        System.out.println("All the Products creation was successful.");
    }

    @When("I see that each created product exists within products list with correct name and data")
    public void i_see_that_each_created_product_exists_within_products_list_with_correct_name_and_data() {
        System.out.println("Validate I can find each one of the created product");
    }

    @And("I try to purchase each one of it")
    public void i_try_to_purchase_each_one_of_it() {
        System.out.println("Try to purchase each one of them through UI");
    }

    @And("I verify the message for each purchase is successful")
    public void i_verify_the_message_for_each_purchase_is_successful() {
        System.out.println("Verify for each purchase attempt I receive a successful message");
    }

    @When("I verify purchasedAmount field within database has been incremented for each product")
    public void i_verify_purchased_amount_field_within_database_has_been_incremented_for_each_product() {
        System.out.println("Verify for each product in DB - 'purchasedAmount' has been incremented");
    }



    @Given("I have a product in the system with details")
    public void i_have_a_product_in_the_system_with_details(io.cucumber.datatable.DataTable dataTable) {
        System.out.println("I have an existing product with the following details : " + dataTable);
    }

    @When("I update the product with details")
    public void i_update_the_product_with_details(io.cucumber.datatable.DataTable dataTable) {
        System.out.println("Updating existing product with the following details - " + dataTable);
    }

    @When("I update the product through API")
    public void i_update_the_product_through_API() {
        System.out.println("Updating the product via API. Validate the status is 200 (successful)");
        System.out.println("If not, fail the test and print the status and failure message");
        System.out.println("If yes - validate all the data has been successfully updated in database");;
    }

    @And("I verify all the product data has been correctly updated in database")
    public void i_verify_all_the_product_data_has_been_correctly_updated_in_database() {
        System.out.println("validate all the data has been successfully updated in database");;
    }

    @Then("the product should be updated successfully")
    public void the_product_should_be_updated_successfully() {
        System.out.println("Product update was successful.");
    }

    @When("I see that the product data has been updated successfully")
    public void i_see_that_the_product_data_has_been_updated_successfully() {
        System.out.println("Validating all the data within the product has been updated successfully as expected");
    }

    @Then("product updating process has been completed successfully")
    public void product_updating_process_has_been_completed_successfully() {
        System.out.println("Product updating process was completed successfully");
    }

    @Given("I have an active order")
    public void i_have_an_active_order() {
        System.out.println("I have an active order to be proceeded");
    }

    @When("I process the order through API")
    public void i_process_the_order_through_API() {
        System.out.println("I proceed the pending order through API");
    }

    @When("I verify I have no pending orders")
    public void i_verify_i_have_no_pending_orders() {
        System.out.println("Verify I have no further pending orders and log out from the system");
    }

    @And("I logout from the system")
    public void i_logout_from_the_system() {
        System.out.println("I logout from the system");
    }

    @Then("Order processing has been completed successfully")
    public void order_processing_has_been_completed_successfully() {
        System.out.println("Order processing has been completed successfully");
    }
}
