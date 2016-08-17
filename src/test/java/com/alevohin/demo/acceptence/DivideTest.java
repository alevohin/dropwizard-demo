package com.alevohin.demo.acceptence;

import com.alevohin.demo.CalculatorResult;
import com.alevohin.demo.DemoApplication;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import io.dropwizard.testing.junit.DropwizardAppRule;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(DataProviderRunner.class)
public class DivideTest {

    @ClassRule
    public static final DropwizardAppRule RULE = new DropwizardAppRule(DemoApplication.class);

    @Test
    @UseDataProvider("dataProviderDivide")
    public void divide(String a, String b, String expected) {
        Client client = new JerseyClientBuilder().build();
        final String url = String.format(
            "http://localhost:%d/divide/%s/%s",
            RULE.getLocalPort(), a, b
        );
        final Response response = client.target(url).request().get();
        assertThat(response.getStatus()).isEqualTo(200);
        CalculatorResult calculatorResult = response.readEntity(CalculatorResult.class);
        assertThat(calculatorResult.getValue()).isEqualTo(expected);
    }

    @DataProvider
    public static Object[][] dataProviderDivide() {
        return new Object[][]{
            {"0", "1", "0"},
            {"0", "1.0", "0"},
            {"0.0", "1.0", "0"},
            {"-1.0", "1.0", "-1"},
            {"1.0", "-1.0", "-1"},
            {"3", "2", "1.5"},
            {"-3", "2", "-1.5"},
            {"-3", "-2", "1.5"},
            {"48", "8", "6"},
            {"48", "6", "8"},
            {"10", "-2.5", "-4"},
            {"10", "-4", "-2.5"},
            {"1", "1000000000000", "1E-12"},
            {"1E-1", "1E-1", "1"},
        };
    }

    @Test
    @UseDataProvider("dataProviderDivideError")
    public void divideError(String params, String expected, int code) {
        Client client = new JerseyClientBuilder().build();
        final String url = String.format(
            "http://localhost:%d/divide%s",
            RULE.getLocalPort(), params
        );
        final Response response = client.target(url).request().get();
        assertThat(response.getStatus()).isEqualTo(code);
        if (expected != null) {
            CalculatorResult calculatorResult = response.readEntity(CalculatorResult.class);
            assertThat(calculatorResult.getError()).isEqualTo(expected);
        }
    }

    @DataProvider
    public static Object[][] dataProviderDivideError() {
        return new Object[][]{
            {"/1/a", null, 400},
            {"/1/a", null, 400},
            {"/a/2", null, 400},
            {"/1/0", "Division by zero", 400}
        };
    }
}
