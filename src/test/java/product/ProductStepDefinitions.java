package product;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;

import static net.serenitybdd.rest.SerenityRest.restAssuredThat;

public class ProductStepDefinitions {

    @Steps
    ProductAPI productAPI;

    @When("we hit the endpoint with {string}")
    public void weHitTheEndpoint(String product) {
        productAPI.fetchTheResultsByProduct(product);
    }


    @And("we see the results displayed for {string}")
    public void weSeeTheResultsDisplayedFor(String product) {
        restAssuredThat(response -> response.statusCode(200));
        productAPI.productSearchResultsAssertions(product);
    }

    @And("we should see the error")
    public void weShouldSeeTheError(DataTable dataTable) {
        restAssuredThat(response -> response.statusCode(404));
        productAPI.checkError(dataTable);
    }

    @And("we should not be authenticated")
    public void weShouldNotBeAuthenticated() {
        restAssuredThat(response -> response.statusCode(401));
    }
}
