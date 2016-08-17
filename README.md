# Task

Write a class that implements a simple calculator web service that caches the results of its computations, with endpoints similar to the following:

```
/add/{a}/{b}/{c}
/subtract/{a}/{b}/{c}
/multiply/{a}/{b}/{c}
/divide/{a}/{b}
```

You may use Jersey / JAX-RS to avoid having to parse JSON manually. Each endpoint should support the GET method, and it should return the result in JSON format. If there is more than one call for the same operation on the same numbers, then the result should be returned from the cache rather than being recomputed.

The calculator calls should support the addition, subtraction, or multiplication of up to three numbers in the same call.

# Solution

* Java 8
* Maven 3
* Dropwizard 0.9.2 (Jersey / JAX-RS)
* JUnit 4.12
