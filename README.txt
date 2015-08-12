This Proof of Concept showcases how Wiremock can be used to enrich an existing service for BDD testing purposes.

Wiremock acta as a proxy between the tested service and the integrated service and enables the integrated service to return more data where appropriate.

An example would be if an integrated service exists already and should be leveraged for testing with real data but cannot provide data for certain scenarios since either too complicated for the setup or currently being developed.

poc-wiremock-proxy:
This project is a wiremock webapp used to proxy the integrated service. See how wiremock can be packaged as a war here: https://github.com/tomakehurst/wiremock/tree/master/sample-war.
The only difference is that I use maven instead of gradle.
The integrated service is http://jsonplaceholder.typicode.com/posts/{id}

poc-rest-service:
This project is a rest service representing a service being implemented for example. It is the main target to test for various scenarios. Without wiremock this service would be limited to what the integrated service provides.
It is using spring boot deployed as a war on the same container as the wiremock webapp (in my case I used JBoss).
Instead of jsonplaceholder service, this service targets http://localhost/poc-wiremock-proxy/posts/{id}
With some environment configuration we can target the proxy in a TEST environment but the real service in PROD for example.

poc-jbehave-bdd:
This project is using JBehave to automate BDD tests. It leverages wiremock remote access in order to control the integrated service proxy to return the data it requires.
It uses http://localhost/poc-wiremock-proxy/__admin/mappings for controlling the behaviors
It uses http://localhost/poc-rest-service/postings/{id} as a target for testing.

In order for this setup to work.
1. Wiremock knows the integrated service address.
2. The Rest Service must connect to the proxy instead of the real integrated service.
3. JBehave must know about both the real service to be tested and the proxy to control the data being returned.

With that setup we have 3 scenarios possible.
1. Real request/responses a proxied through wiremock (/postings/1)
2. Mocked data can be preloaded in the proxy (/postings/1000)
3. JBehave uploads a specific response for the integrated service (/postings/2000 created within the JBehave test)
- And then it requests the target rest service (which will see the mocked response instead of the real service call (404))

One note is that it would be great if wiremock could support multiple services at once. Here we are limited since the proxy will always use the complete context path to call the real service. Therefore you might easily have collisions there and the only way around this is to create 1 poc-wiremock-proxy per service to proxy.

Hope it helps anyone wanting to test services with limited data returned from the integrated services.
