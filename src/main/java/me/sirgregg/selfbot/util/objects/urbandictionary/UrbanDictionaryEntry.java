package me.sirgregg.selfbot.util.objects.urbandictionary;

import com.google.gson.annotations.SerializedName;

public class UrbanDictionaryEntry {
	private String[] tags, sounds;

	@SerializedName("result_type")
	private String resultType;

	@SerializedName("list")
	private Definition[] definitions;

	public String[] getTags() {
		return tags;
	}

	public String[] getSounds() {
		return sounds;
	}

	public String getResultType() {
		return resultType;
	}

	public Definition[] getDefinitions() {
		return definitions;
	}
}