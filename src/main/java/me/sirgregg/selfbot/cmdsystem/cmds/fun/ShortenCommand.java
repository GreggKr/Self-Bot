package me.sirgregg.selfbot.cmdsystem.cmds.fun;

import com.google.gson.JsonParser;
import me.sirgregg.selfbot.SelfBot;
import me.sirgregg.selfbot.cmdsystem.Command;
import me.sirgregg.selfbot.util.EmbedUtil;
import me.sirgregg.selfbot.util.config.Configuration;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class ShortenCommand extends Command {
	private Configuration configuration = SelfBot.getConfiguration();

	public ShortenCommand() {
		super("shorten", "shorten <url>", "Generates a <bit.ly> shortened link.");
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
		String enteredUrl = args[0];
		if (!enteredUrl.toLowerCase().startsWith("https://") && !(enteredUrl.toLowerCase().startsWith("htpp://"))) {
			enteredUrl = "http://" + enteredUrl;
		}
		String key = configuration.getBitlyOAuthToken();

		try {
			String reqUrl = "https://api-ssl.bitly.com/v3/shorten?access_token=" + key + "&longUrl=" + URLEncoder.encode(enteredUrl, "UTF-8");

			URLConnection connection = new URL(reqUrl).openConnection();
			connection.connect();

			StringBuilder jsonBuilder = new StringBuilder();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				jsonBuilder.append(line);
			}

			String resJson = jsonBuilder.toString();
			String returnValue = new JsonParser().parse(resJson).getAsJsonObject().get("data").getAsJsonObject().get("url").getAsString();

			if (returnValue.equals("")) {
				e.getMessage().editMessage(EmbedUtil.createEmbed(color, "**Bad HTML status code.**\nStatus code: " + new JsonParser().parse(resJson).getAsJsonObject().get("status_code"))).queue();
				return;
			}

			e.getMessage().editMessage(EmbedUtil.createEmbed(color, "**Successfully shortened URL.**\nLong URL: <" + new JsonParser().parse(resJson).getAsJsonObject().get("data").getAsJsonObject().get("long_url").getAsString()
					+ ">\nShort URL: <" + returnValue + ">")).queue();
		} catch (Exception ex) {
			e.getMessage().editMessage(EmbedUtil.createEmbed(color, "**Caught Exception.**\nFailed to open connection with provided URL.")).queue();
		}

	}
}