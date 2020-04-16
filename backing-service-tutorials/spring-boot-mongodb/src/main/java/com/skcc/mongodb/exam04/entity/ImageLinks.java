package com.skcc.mongodb.exam04.entity;

public class ImageLinks {
	private String smallThumbnail;
	private String thumbnail;

	// Getter Methods

	public String getSmallThumbnail() {
		return smallThumbnail;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	// Setter Methods

	public void setSmallThumbnail(String smallThumbnail) {
		this.smallThumbnail = smallThumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
}