package com.example.frontend.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BoardPojo {

	@JsonProperty("postCounter")
	private int postCounter;

	@JsonProperty("subject")
	private String subject;

	@JsonProperty("codeName")
	private String codeName;

	@JsonProperty("name")
	private String name;

	public int getPostCounter(){
		return postCounter;
	}

	public String getSubject(){
		return subject;
	}

	public String getCodeName(){
		return codeName;
	}

	public String getName(){
		return name;
	}
}