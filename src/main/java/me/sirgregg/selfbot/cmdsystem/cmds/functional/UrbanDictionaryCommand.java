package me.sirgregg.selfbot.cmdsystem.cmds.functional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import me.sirgregg.selfbot.SelfBot;
import me.sirgregg.selfbot.cmdsystem.Command;
import me.sirgregg.selfbot.util.EmbedUtil;
import me.sirgregg.selfbot.util.URLUtil;
import me.sirgregg.selfbot.util.objects.urbandictionary.Definition;
import me.sirgregg.selfbot.util.objects.urbandictionary.UrbanDictionaryEntry;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class UrbanDictionaryCommand extends Command {
	public UrbanDictionaryCommand() {
		super(new String[] { "urbandictionary", "urbandic", "dic", "define" }, "define <term>", "Uses Urban Dictionary's API to find a definition to a word.");
	}

	@Override
	public void execute(MessageReceivedEvent e, String[] args) {
		Color color = e.getGuild().getSelfMember().getColor();
		if (args.length == 0) {
			e.getMessage().editMessage(
					EmbedUtil.createEmbed(color, "**Invalid usage:**\n" + "Correct usage: " + SelfBot.getConfiguration().getLead() + getUsage()))
					.queue();
			return;
		}

		StringBuilder sb = new StringBuilder();
		for (String string : args) {
			sb.append(string).append(" ");
		}
		String term = sb.toString().trim();

		try {
			String url = "http://api.urbandictionary.com/v0/define?term=" + URLEncoder.encode(term, "UTF-8");

			URLConnection connection = new URL(url).openConnection();
			connection.connect();

			StringBuilder jsonBuilder = new StringBuilder();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				jsonBuilder.append(line);
			}

			String json = jsonBuilder.toString();
			boolean exists = !(new JsonParser().parse(json).getAsJsonObject().get("result_type").getAsString().equals("no_results"));

			if (!exists) {
				e.getMessage().editMessage(EmbedUtil.createEmbed(color, "**Exception Caught**\nNo definitions found.")).queue();
			} else {
				Gson gson = new GsonBuilder().create();
				UrbanDictionaryEntry entry = gson.fromJson(URLUtil.readURL(url), UrbanDictionaryEntry.class);

				Definition definition = entry.getDefinitions()[0];
				e.getMessage().editMessage(EmbedUtil.createEmbed(color,
						"**Urban Dictionary Definition**\n" +
								"**Word: **" + term + "\n" +
								"**Definition: **" + definition.getDefinition() + "\n" +
								"**Author: **" + definition.getAuthor() + "\n" +
								"**Up Votes / Down Votes: **" + definition.getThumbsUp() + " / " + definition.getThumbsDown() + "\n" +
								"**Example: **" + definition.getExample())).queue();
			}
		} catch (IOException ex) {
			e.getMessage().editMessage(EmbedUtil.createEmbed(color, "**Exception Caught**\nError in URL. Please contact Gregg.")).queue();
		}
	}
}