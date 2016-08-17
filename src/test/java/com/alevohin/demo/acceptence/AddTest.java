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
public class AddTest {

    @ClassRule
    public static final DropwizardAppRule RULE = new DropwizardAppRule(DemoApplication.class);

    @Test
    @UseDataProvider("dataProviderAdd2")
    public void add2(String a, String b, String expected) {
        Client client = new JerseyClientBuilder().build();
        final String url = String.format(
            "http://localhost:%d/add/%s/%s",
            RULE.getLocalPort(), a, b
        );
        final Response response = client.target(url).request().get();
        assertThat(response.getStatus()).isEqualTo(200);
        CalculatorResult calculatorResult = response.readEntity(CalculatorResult.class);
        assertThat(calculatorResult.getValue()).isEqualTo(expected);
    }

    @DataProvider
    public static Object[][] dataProviderAdd2() {
        return new Object[][]{
            {"0", "0", "0"},
            {"1", "2", "3"},
            {"1.3", "2.4", "3.7"},
            {"2", "1", "3"},
            {"1", "-1", "0"},
            {"1.5", "-1.5", "0"},
            {"-1.5", "1.5", "0"},
            {"-1.5", "-1.41", "-2.91"},
            {"1E-1", "-1E-1", "0"},
        };
    }

    @Test
    @UseDataProvider("dataProviderAdd3")
    public void add3(String a, String b, String c, String expected) {
        Client client = new JerseyClientBuilder().build();
        final String url = String.format(
            "http://localhost:%d/add/%s/%s/%s",
            RULE.getLocalPort(), a, b, c
        );
        final Response response = client.target(url).request().get();
        assertThat(response.getStatus()).isEqualTo(200);
        CalculatorResult calculatorResult = response.readEntity(CalculatorResult.class);
        assertThat(calculatorResult.getValue()).isEqualTo(expected);
    }

    @DataProvider
    public static Object[][] dataProviderAdd3() {
        return new Object[][]{
            {"0", "0", "0", "0"},
            {"1", "2", "3", "6"},
            {"1.3", "2.4", "3.7", "7.4"},
            {"3", "2", "1", "6"},
            {"1", "-1", "0", "0"},
            {"1.5", "-1.5", "0", "0"},
            {"-1.5", "1.5", "0", "0"},
            {"-1.5", "-1.41", "1.21", "-1.7"},
        };
    }

    @Test
    @UseDataProvider("dataProviderAddError")
    public void addError(String params, int code) {
        Client client = new JerseyClientBuilder().build();
        final String url = String.format(
            "http://localhost:%d/add%s",
            RULE.getLocalPort(), params
        );
        final Response response = client.target(url).request().get();
        assertThat(response.getStatus()).isEqualTo(code);
    }

    @DataProvider
    public static Object[][] dataProviderAddError() {
        return new Object[][]{
            {"/1/a", 400},
            {"/a/2", 400},
            {"/1/2/a", 400},
            {"/1.11.1/2", 400}
        };
    }
}
