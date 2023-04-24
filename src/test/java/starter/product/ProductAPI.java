package starter.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.environment.SystemEnvironmentVariables;
import net.thucydides.core.util.EnvironmentVariables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductAPI {

    private static final Logger logger = Logger.getLogger("Product");
    static EnvironmentVariables environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();
    static String webserviceEndpoint = EnvironmentSpecificConfiguration.from(environmentVariables).getProperty("product.service.endpoint");
    private static final String RESULTS_BY_PRODUCT = webserviceEndpoint + "{product}";
    List<ProductResponse.Product> products;
    ProductResponse.Detail detail;
    String product;
    String message;
    String server;

    @Step("Fetch the results based on the product {0} search")
    public void fetchTheResultsByProduct(String product) {
        this.product = product;
        SerenityRest.given().pathParam("product", product).get(RESULTS_BY_PRODUCT);
    }

    public void productSearchResultsAssertions(String product) {
        products = List.of(SerenityRest.lastResponse().as(ProductResponse.Product[].class));
        List<String> translatedStrings = new ArrayList<>();
        if (product.contentEquals("orange")) {
            translatedStrings.addAll(Arrays.asList("sinas", "sinaas", "sinaasappel", "sinaasappelsap"));
        }
        if (product.contentEquals("pasta")) {
            translatedStrings.addAll(Arrays.asList("spaghetti", "ravioli", "penne", "tortellini", "gnocchi", "fusilli", "macaroni", "farfalle", "rigate", "rigatoni", "tagliatelle", "orzo", "cappelletti"));
        }
        if (product.contentEquals("cola")) {
            translatedStrings.add("pepsi");
        }
        assertThat(products.stream().allMatch(p -> p.getTitle().toLowerCase().contains(product) || translatedStrings.stream().anyMatch(t -> p.getTitle().toLowerCase().contains(t)))).withFailMessage("Expected that the title of each product list should contain the word : " + product + " but it is not as in : " + products).isTrue();
        logger.info("***All the product search results are having the word " + product + " in the title of each item of arraylist : " + products + ".***");
    }

    public void checkError(DataTable dataTable) {
        detail = SerenityRest.lastResponse().as(ProductResponse.class).getDetail();
        List<List<String>> data = dataTable.asLists();
        for (int i = 1; i < data.size(); i++) {
            message = Objects.isNull(data.get(i).get(0)) ? null : data.get(i).get(0);
            server = Objects.isNull(data.get(i).get(1)) ? null : data.get(i).get(1);
        }
        ProductResponse.Detail expectedDetail = new ProductResponse.Detail();
        expectedDetail.setError(true);
        expectedDetail.setMessage(message);
        expectedDetail.setRequested_item(product);
        expectedDetail.setServed_by(server);
        ObjectMapper mapper = new ObjectMapper();
        String jsonString;
        try {
            jsonString = mapper.writeValueAsString(expectedDetail);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        assertThat(detail.getError() && detail.getMessage().contentEquals(message) && detail.getRequested_item().contentEquals(product) && detail.getServed_by().contentEquals(server)).withFailMessage("Expected detail object is : " + jsonString + " but it is : " + detail).isTrue();
        logger.info("***Expected & Actual detail error object is same : " + expectedDetail + " .***");
    }
}
