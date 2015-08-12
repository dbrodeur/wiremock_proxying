package com.teksystems.poc.wiremock.web;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Simple object representing a post.
 * See http://jsonplaceholder.typicode.com/posts
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Post {
	private String id;
	private String userId;
	private String title;
	private String body;
	

	public void setId(String id) {
		this.id = id;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getId() {
		return id;
	}
	public String getUserId() {
		return userId;
	}
	public String getTitle() {
		return title;
	}
	public String getBody() {
		return body;
	}
}
