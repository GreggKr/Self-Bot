package me.sirgregg.selfbot.util.objects.urbandictionary;

import com.google.gson.annotations.SerializedName;

public class Definition {
	private String definition, author, word, example;

	@SerializedName("permalink")
	private String link;

	@SerializedName("thumbs_up")
	private int thumbsUp;

	@SerializedName("thumbs_down")
	private int thumbsDown;

	@SerializedName("defid")
	private int id;

	@SerializedName("current_vote")
	private String currentVote;

	public String getDefinition() {
		return definition;
	}

	public String getAuthor() {
		return author;
	}

	public String getWord() {
		return word;
	}

	public String getExample() {
		return example;
	}

	public String getLink() {
		return link;
	}

	public int getThumbsUp() {
		return thumbsUp;
	}

	public int getThumbsDown() {
		return thumbsDown;
	}

	public int getId() {
		return id;
	}

	public String getCurrentVote() {
		return currentVote;
	}
}