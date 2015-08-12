package com.teksystems.poc.wiremock.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class PostController {

	@Autowired
	private Environment environment;

	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping("/postings/{id}")
	public Post readPost(@PathVariable("id") String id) {
		Post post = restTemplate.getForObject(
				environment.getProperty("post.service.url") + "/" + id,
				Post.class);
		post.setBody("THROUGH SERVICE: " + post.getBody());
		return post;
	}
}
