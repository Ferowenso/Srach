package com.example.frontend.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class ThreadPojo {

	@JsonProperty("lastPostTime")
	private LocalDateTime lastPostTime;

	@JsonProperty("createdAt")
	private LocalDateTime createdAt;

	@JsonProperty("name")
	private String name;

	@JsonProperty("threadNumber")
	private Long threadNumber;

	@JsonProperty("text")
	private String text;


	@JsonProperty("locked")
	private boolean locked;

	@JsonProperty("board")
	private BoardPojo board;

	@JsonProperty("username")
	private String username;

	public LocalDateTime getLastPostTime(){
		return lastPostTime;
	}

	public LocalDateTime getCreatedAt(){
		return createdAt;
	}

	public String getName(){
		return name;
	}

	public Long getThreadNumber(){
		return threadNumber;
	}

	public String getText(){
		return text;
	}

	public BoardPojo getBoard(){
		return board;
	}

	public String getUsername(){
		return username;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setBoard(BoardPojo board) {
		this.board = board;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}
}