package me.sirgregg.selfbot;

import me.sirgregg.selfbot.cmdsystem.CommandHandler;
import me.sirgregg.selfbot.util.Logger;
import me.sirgregg.selfbot.util.config.Configuration;
import me.sirgregg.selfbot.util.config.ConfigurationHandler;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;

public class SelfBot {
	private static JDA jda;
	private static ConfigurationHandler handler;
	private static long lastStartupTime;

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		handler = new ConfigurationHandler();
		handler.setupJson(); // Creates configuration file
		handler.parseJson(); // Gets the values from the configuration file

		if (handler.getConfiguration().getDiscordToken().equals("replace-me-with-your-discord-token")
				|| handler.getConfiguration().getGithubOAuthToken().equals("replace-me-with-your-github-oauth2-token")
				|| handler.getConfiguration().getBitlyOAuthToken().equals("replace-me-with-your-bitly-oauth-token")) {
			Logger.fatal("Stopping initialization of JDA. Please change tokens in config.json!");
			System.exit(0);
		}

		try {
			jda = new JDABuilder(AccountType.CLIENT)
					.setToken(handler.getConfiguration().getDiscordToken())
					.setAutoReconnect(true)
					.buildBlocking();
			registerListeners();
			Logger.info("Successfully initialized JDA.");
		} catch (LoginException | RateLimitedException | IllegalArgumentException | InterruptedException e) {
			Logger.fatal("Caught an exception. Please check your discordToken in config.json. Exception: " + e);
		}
		lastStartupTime = System.currentTimeMillis() - startTime;
	}

	// Registers all command listeners using the established JDA connection.
	private static void registerListeners() {
		jda.addEventListener(new CommandHandler());
	}

	// Gets the an instance of Configuration that contains config.json values.
	public static Configuration getConfiguration() {
		return handler.getConfiguration();
	}

	public static String getLastStartupTime() {
		long minutes = (lastStartupTime / 1000) / 60;
		long seconds = (lastStartupTime / 1000) % 60;
		return minutes + "m " + seconds + "s";
	}

}