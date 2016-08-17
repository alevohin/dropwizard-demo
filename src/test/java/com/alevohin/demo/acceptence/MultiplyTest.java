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
public class MultiplyTest {

    @ClassRule
    public static final DropwizardAppRule RULE = new DropwizardAppRule(DemoApplication.class);

    @Test
    @UseDataProvider("dataProviderMultiply2")
    public void multiply2(String a, String b, String expected) {
        Client client = new JerseyClientBuilder().build();
        final String url = String.format(
            "http://localhost:%d/multiply/%s/%s",
            RULE.getLocalPort(), a, b
        );
        final Response response = client.target(url).request().get();
        assertThat(response.getStatus()).isEqualTo(200);
        CalculatorResult calculatorResult = response.readEntity(CalculatorResult.class);
        assertThat(calculatorResult.getValue()).isEqualTo(expected);
    }

    @DataProvider
    public static Object[][] dataProviderMultiply2() {
        return new Object[][]{
            {"0", "0", "0"},
            {"1", "0", "0"},
            {"-1", "0", "0"},
            {"1.0", "0", "0"},
            {"-1.0", "0", "0"},
            {"1", "0.0", "0"},
            {"-1", "0.0", "0"},
            {"2", "3", "6"},
            {"3", "2", "6"},
            {"-2", "3", "-6"},
            {"-2", "-3", "6"},
            {"2", "2.5", "5"},
            {"2.5", "2", "5"},
            {"-2", "2.5", "-5"},
            {"2.5", "-2", "-5"},
            {"1", "1000000000000", "1E+12"},
            {"0.02", "25", "0.5"},
            {"1E-1", "1E+1", "1"},
        };
    }

    @Test
    @UseDataProvider("dataProviderMultiply3")
    public void multiply3(String a, String b, String c, String expected) {
        Client client = new JerseyClientBuilder().build();
        final String url = String.format(
            "http://localhost:%d/multiply/%s/%s/%s",
            RULE.getLocalPort(), a, b, c
        );
        final Response response = client.target(url).request().get();
        assertThat(response.getStatus()).isEqualTo(200);
        CalculatorResult calculatorResult = response.readEntity(CalculatorResult.class);
        assertThat(calculatorResult.getValue()).isEqualTo(expected);
    }

    @DataProvider
    public static Object[][] dataProviderMultiply3() {
        return new Object[][]{
            {"0", "0", "0", "0"},
            {"1", "0", "0", "0"},
            {"2", "3", "4", "24"},
            {"3", "2", "4", "24"},
            {"4", "3", "2", "24"},
            {"4", "3", "-2", "-24"},
            {"2", "1.5", "2.5", "7.5"},
            {"1", "1000000000000", "1E-5", "1E+7"},
            {"0.02", "25", "-1.1", "-0.55"},
        };
    }

    @Test
    @UseDataProvider("dataProviderMultiplyError")
    public void multiplyError(String params, int code) {
        Client client = new JerseyClientBuilder().build();
        final String url = String.format(
            "http://localhost:%d/multiply%s",
            RULE.getLocalPort(), params
        );
        final Response response = client.target(url).request().get();
        assertThat(response.getStatus()).isEqualTo(code);
    }

    @DataProvider
    public static Object[][] dataProviderMultiplyError() {
        return new Object[][]{
            {"/1/a", 400},
            {"/a/2", 400},
            {"/1/2/a", 400},
            {"/1.11.1/2", 400}
        };
    }
}
