package com.teksystems.poc.wiremock.jbehave;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import org.springframework.web.client.RestTemplate;

import com.teksystems.poc.wiremock.web.Post;

public class WiremockSteps {

	private static String POST_URL = "http://localhost:8080/poc-rest-service/postings/";
	private static String ADMIN_NEW_URL = "http://localhost:8080/poc-wiremock-proxy/__admin/mappings/new";
	private static String ADMIN_RESET_URL = "http://localhost:8080/poc-wiremock-proxy/__admin/mappings/reset";

	private String postId;
	private RestTemplate restTemplate;
	private Post post;
	private Boolean noData;
	private String postTemplate;

	@Given("a post")
	public void givenAPost() throws IOException {
		noData = false;
		post = null;
		postId = null;
		restTemplate = new RestTemplate();
		postTemplate = new String(Files.readAllBytes(Paths.get("src/test/resources/com/teksystems/poc/wiremock/jbehave/post_mapping.json")));
	}

	@When("id is $id")
	public void whenIdIs(String id) {
		postId = id;
	}

	@When("we have no post data available")
	public void whenNoDataAvailable() {
		noData = true;
	}

	@Then("the title must be $title")
	public void thenTitleMustBe(String title) {
		try {
			if (Boolean.TRUE.equals(noData)) {
				// send server the new mocked response which includes the title
				// expected
				restTemplate.postForLocation(ADMIN_NEW_URL, postTemplate.replaceAll("POST_ID", postId).replaceAll("POST_TITLE",title));
			}

			post = restTemplate.getForObject(POST_URL + postId, Post.class);
			Assert.assertEquals("Service returned the wrong title", title,
					post.getTitle());
		} finally {
			cleanUp();
		}
	}

	private void cleanUp() {
		// send wiremock a reset to default canned responses, removing any of
		// them we could have uploaded
		restTemplate.postForLocation(ADMIN_RESET_URL, "");
	}
}
