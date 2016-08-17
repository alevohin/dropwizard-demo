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
public class SubtractTest {

    @ClassRule
    public static final DropwizardAppRule RULE = new DropwizardAppRule(DemoApplication.class);

    @Test
    @UseDataProvider("dataProviderSubtract2")
    public void subtract2(String a, String b, String expected) {
        Client client = new JerseyClientBuilder().build();
        final String url = String.format(
            "http://localhost:%d/subtract/%s/%s",
            RULE.getLocalPort(), a, b
        );
        final Response response = client.target(url).request().get();
        assertThat(response.getStatus()).isEqualTo(200);
        CalculatorResult calculatorResult = response.readEntity(CalculatorResult.class);
        assertThat(calculatorResult.getValue()).isEqualTo(expected);
    }

    @DataProvider
    public static Object[][] dataProviderSubtract2() {
        return new Object[][]{
            {"0", "0", "0"},
            {"1", "2", "-1"},
            {"1.3", "2.4", "-1.1"},
            {"2", "1", "1"},
            {"1", "-1", "2"},
            {"1.5", "1.5", "0"},
            {"-1.5", "-1.41", "-0.09"},
            {"1E-1", "1E-1", "0"},
        };
    }

    @Test
    @UseDataProvider("dataProviderSubtract3")
    public void subtract3(String a, String b, String c, String expected) {
        Client client = new JerseyClientBuilder().build();
        final String url = String.format(
            "http://localhost:%d/subtract/%s/%s/%s",
            RULE.getLocalPort(), a, b, c
        );
        final Response response = client.target(url).request().get();
        assertThat(response.getStatus()).isEqualTo(200);
        CalculatorResult calculatorResult = response.readEntity(CalculatorResult.class);
        assertThat(calculatorResult.getValue()).isEqualTo(expected);
    }

    @DataProvider
    public static Object[][] dataProviderSubtract3() {
        return new Object[][]{
            {"0", "0", "0", "0"},
            {"1", "2", "3", "-4"},
            {"-1.3", "2.4", "3.7", "-7.4"},
            {"3", "2", "1", "0"},
            {"1", "1", "0", "0"},
            {"1.5", "1.5", "0", "0"},
            {"1.5", "0", "1.5", "0"},
            {"1.5", "1.41", "+1.21", "-1.12"},
        };
    }

    @Test
    @UseDataProvider("dataProviderSubtractError")
    public void subtractError(String params, int code) {
        Client client = new JerseyClientBuilder().build();
        final String url = String.format(
            "http://localhost:%d/subtract%s",
            RULE.getLocalPort(), params
        );
        final Response response = client.target(url).request().get();
        assertThat(response.getStatus()).isEqualTo(code);
    }

    @DataProvider
    public static Object[][] dataProviderSubtractError() {
        return new Object[][]{
            {"/1/a", 400},
            {"/a/2", 400},
            {"/1/2/a", 400},
            {"/1.11.1/2", 400}
        };
    }
}
