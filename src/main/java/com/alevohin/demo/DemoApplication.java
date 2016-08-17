package com.alevohin.demo;

import com.alevohin.demo.calculator.CacheableCalculator;
import com.alevohin.demo.calculator.MathCalculators;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;
import java.net.URL;

public class DemoApplication extends Application<Configuration> {

    public static void main(String[] arguments) throws Exception {
        if (arguments.length == 0) {
            final URL settings = DemoApplication.class.getResource("/settings.yml");
            String[] args = new String[]{"server", settings.getFile()};
            new DemoApplication().run(args);
        } else {
            new DemoApplication().run(arguments);
        }
    }

    @Override
    public void run(final Configuration config, final Environment environment) throws Exception {
        environment.jersey().register(new CalculatorResource(
            new CacheableCalculator(MathCalculators.summator()),
            new CacheableCalculator(MathCalculators.multiplicator()),
            new CacheableCalculator(MathCalculators.divider())
        ));
        environment.jersey().register(new CalculatorExceptionMapper());
    }
}
