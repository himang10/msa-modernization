package com.skcc.mongodb.exam04.entity;

public class ReadingModes {
	private boolean text;
	private boolean image;

	// Getter Methods

	public boolean getText() {
		return text;
	}

	public boolean getImage() {
		return image;
	}

	// Setter Methods

	public void setText(boolean text) {
		this.text = text;
	}

	public void setImage(boolean image) {
		this.image = image;
	}
}