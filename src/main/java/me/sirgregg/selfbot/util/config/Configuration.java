package me.sirgregg.selfbot.util.config;

public class Configuration {
	private String lead, discordToken, githubOAuthToken, bitlyOAuthToken, version;
	private boolean loggingEnabled;

	public String getLead() {
		return lead;
	}

	public String getDiscordToken() {
		return discordToken;
	}

	public String getGithubOAuthToken() {
		return githubOAuthToken;
	}

	public String getBitlyOAuthToken() {
		return bitlyOAuthToken;
	}

	public String getVersion() {
		return version;
	}

	public boolean isLoggingEnabled() {
		return loggingEnabled;
	}
}