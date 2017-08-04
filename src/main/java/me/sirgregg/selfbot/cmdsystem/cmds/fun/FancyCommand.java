package me.sirgregg.selfbot.cmdsystem.cmds.fun;

import me.sirgregg.selfbot.SelfBot;
import me.sirgregg.selfbot.cmdsystem.Command;
import me.sirgregg.selfbot.util.EmbedUtil;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.Color;

public class FancyCommand extends Command {
	public FancyCommand() {
		super(new String[]{"fancy", "f"}, "fancy <message>", "Displays an embed with custom text.");
	}

	@Override
	public void execute(MessageReceivedEvent e, String[] args) {
		Color color = e.getGuild().getSelfMember().getColor();
		// All -> <message>
		if (args.length < 1) {
			e.getMessage().editMessage(
					EmbedUtil.createEmbed(color, "**Invalid usage:**\n" + "Correct usage: " + SelfBot.getConfiguration().getLead() + getUsage()))
					.queue();
			return;
		}
		StringBuilder sb = new StringBuilder();
		for (String string : args) {
			sb.append(string).append(" ");
		}
		String message = sb.toString().trim();

		e.getMessage().editMessage(
				EmbedUtil.createEmbedWithAuthor(color, e.getAuthor(), message))
				.queue();
	}
}