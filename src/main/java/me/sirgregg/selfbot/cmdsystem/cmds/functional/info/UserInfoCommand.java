package me.sirgregg.selfbot.cmdsystem.cmds.functional.info;

import com.google.gson.JsonParser;
import me.sirgregg.selfbot.SelfBot;
import me.sirgregg.selfbot.cmdsystem.Command;
import me.sirgregg.selfbot.util.EmbedUtil;
import me.sirgregg.selfbot.util.config.Configuration;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class UserInfoCommand extends Command {
	private Configuration configuration = SelfBot.getConfiguration();

	public UserInfoCommand() {
		super(new String[]{"userinfo", "uinfo"}, "uinfo <user>", "Displays information about the mentioned user");
	}

	@Override
	public void execute(MessageReceivedEvent e, String[] args) {
		Color color = e.getGuild().getSelfMember().getColor();
		String key = configuration.getBitlyOAuthToken();

		if (args.length < 1) {
			e.getMessage().editMessage(
					EmbedUtil.createEmbed(color, "**Invalid usage:**\n" + "Correct usage: " + SelfBot.getConfiguration().getLead() + getUsage()))
					.queue();
			return;
		}

		List<User> mentionedUsers = e.getMessage().getMentionedUsers();
		if (mentionedUsers.size() == 0) {
			e.getMessage().editMessage(
					EmbedUtil.createEmbed(color, "**Invalid usage:**\n" + "Correct usage: " + SelfBot.getConfiguration().getLead() + getUsage()))
					.queue();
			return;
		}
		User mentionedUser = mentionedUsers.get(0);

		try {
			String returnValue;
			if (mentionedUser.getEffectiveAvatarUrl()  == null) {
				returnValue = "None.";
			} else {
				String reqUrl = "https://api-ssl.bitly.com/v3/shorten?access_token=" + key + "&longUrl=" + URLEncoder.encode(mentionedUser.getEffectiveAvatarUrl(), "UTF-8");
				URLConnection urlConnection = new URL(reqUrl).openConnection();
				urlConnection.connect();

				StringBuilder jsonBuilder = new StringBuilder();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					jsonBuilder.append(line);
				}
				String resJson = jsonBuilder.toString();

				returnValue = new JsonParser().parse(resJson).getAsJsonObject().get("data").getAsJsonObject().get("url").getAsString();

				if (returnValue.equals("")) {
					e.getMessage().editMessage(EmbedUtil.createEmbed(color, "**Bad HTML status code.**\nStatus code: " + new JsonParser().parse(resJson).getAsJsonObject().get("status_code"))).queue();
					return;
				}
			}
			e.getMessage().editMessage(EmbedUtil.createEmbed(color,
					"**Name: **" + mentionedUser.getName() + "#" + mentionedUser.getDiscriminator() + "\n" +
							"**Creation Time: **" + mentionedUser.getCreationTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) + "\n" +
							"**ID: **" + mentionedUser.getId() + "\n" +
							"**Profile Picture: **<" + returnValue + ">")).queue();
		} catch (IOException ex) {
			e.getMessage().editMessage(EmbedUtil.createEmbed(color, "**Caught Exception.**\nInvalid icon URL.")).queue();
		}


	}
}