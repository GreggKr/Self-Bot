package me.sirgregg.selfbot.util.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import me.sirgregg.selfbot.util.Logger;

import java.io.*;

public class ConfigurationHandler {
	private Configuration configuration;

	public void setupJson() {
		String path = "config.json"; // Name of the config file
		File file = new File(path); // Creates a temp file
		if (file.exists() && file.isFile()) return; // Checks if the file exists and is a file not a dir

		try {
			Writer writer = new FileWriter(path); // TODO: Change Writer to FileWriter. Using Writer for testing due to a bug.
			JsonObject jsonObject = new JsonObject();

			jsonObject.addProperty("lead", "me.");
			jsonObject.addProperty("version", "1.0");
			jsonObject.addProperty("discordToken", "replace-me-with-your-discord-token");
			jsonObject.addProperty("githubOAuthToken", "replace-me-with-your-github-oauth2-token");
			jsonObject.addProperty("bitlyOAuthToken", "replace-me-with-your-bitly-oauth-token");
			jsonObject.addProperty("loggingEnabled", true);
			jsonObject.addProperty("scriptFilePath", "C:\\SelfBot\\script.js");

			Gson gson = new GsonBuilder().setPrettyPrinting().create(); // Without pretty printing, it's all on one line
			gson.toJson(jsonObject, writer);
			writer.close();

			Logger.info("Successfully set up all default configuration values.");
		} catch (IOException e) {
			Logger.fatal("Caught IOException while trying to write to " + path + ". Exception: " + e);
		}
	}

	public void parseJson() {
		String path = "config.json";
		try {
			Reader reader = new FileReader(path); // TODO: Change Reader to FileReader. Using Reader for testing due to a bug.

			Gson gson = new GsonBuilder().create();
			configuration = gson.fromJson(reader, Configuration.class);

			Logger.info("Successfully loaded all configuration values.");
		} catch (IOException e) {
			Logger.fatal("Caught IOException while trying to read from " + path + ". Exception: " + e);
		}
	}

	public Configuration getConfiguration() {
		return configuration;
	}
}