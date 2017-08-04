package me.sirgregg.selfbot.cmdsystem.cmds.functional.info;

import com.google.gson.JsonParser;
import me.sirgregg.selfbot.SelfBot;
import me.sirgregg.selfbot.cmdsystem.Command;
import me.sirgregg.selfbot.util.EmbedUtil;
import me.sirgregg.selfbot.util.config.Configuration;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.time.format.DateTimeFormatter;

public class GuildInfoCommand extends Command {
	private Configuration configuration = SelfBot.getConfiguration();
	public GuildInfoCommand() {
		super(new String[] { "guildinfo", "ginfo" }, "ginfo", "Displays information about the server.");
	}

	@Override
	public void execute(MessageReceivedEvent e, String[] args) {
		Color color = e.getGuild().getSelfMember().getColor();
		String key = configuration.getBitlyOAuthToken();

		try {
			String reqUrl = "https://api-ssl.bitly.com/v3/shorten?access_token=" + key + "&longUrl=" + URLEncoder.encode(e.getGuild().getIconUrl(), "UTF-8");
			URLConnection urlConnection = new URL(reqUrl).openConnection();
			urlConnection.connect();

			StringBuilder jsonBuilder = new StringBuilder();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
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
			e.getMessage().editMessage(EmbedUtil.createEmbed(
					color,
					"**Information about Guild: **\n**Name: **" + e.getGuild().getName() + "\n" +
							"**Owner: **" + e.getGuild().getOwner().getEffectiveName() + "#" + e.getGuild().getOwner().getUser().getDiscriminator() + "\n" +
							"**Created On: **" + e.getGuild().getCreationTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) + "\n" +
							"**# of Members: **" + e.getGuild().getMembers().size() + "\n" +
							"**Download the Icon: **<" + returnValue + ">\n"
			)).queue();
		} catch (IOException ex) {
			e.getMessage().editMessage(EmbedUtil.createEmbed(color, "**Caught Exception.**\nInvalid icon URL.")).queue();
			return;
		}

	}
}