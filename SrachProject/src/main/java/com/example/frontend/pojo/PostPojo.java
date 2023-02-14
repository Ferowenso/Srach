package com.example.frontend.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class PostPojo {

	@JsonProperty("dateTime")
	private LocalDateTime dateTime;

	@JsonProperty("op")
	private boolean op;

	@JsonProperty("sage")
	private boolean sage;

	@JsonProperty("text")
	private String text;

	@JsonProperty("thread")
	private ThreadPojo thread;

	@JsonProperty("postNumber")
	private int postNumber;

	@JsonProperty("username")
	private String username;

	public LocalDateTime getDateTime(){
		return dateTime;
	}

	public boolean isOp(){
		return op;
	}

	public boolean isSage(){
		return sage;
	}

	public String getText(){
		return text;
	}

	public ThreadPojo getThread(){
		return thread;
	}

	public int getPostNumber(){
		return postNumber;
	}

	public String getUsername(){
		return username;
	}

	public void setOp(boolean op) {
		this.op = op;
	}

	public void setSage(boolean sage) {
		this.sage = sage;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setThread(ThreadPojo thread) {
		this.thread = thread;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}