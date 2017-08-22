package me.sirgregg.selfbot.cmdsystem.cmds.functional.info;

import me.sirgregg.selfbot.SelfBot;
import me.sirgregg.selfbot.cmdsystem.Command;
import me.sirgregg.selfbot.util.EmbedUtil;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class BotInfoCommand extends Command {
	public BotInfoCommand() {
		super(new String[] { "info", "botinfo", "binfo"}, "botinfo", "Displays information about the bot.");
	}
	@Override
	public void execute(MessageReceivedEvent e, String[] args) {
		e.getMessage().editMessage(EmbedUtil.createEmbed(e.getGuild().getSelfMember().getColor(),
				"**Information about the Bot:**\n" +
						"**Last startup time: **" + SelfBot.getLastStartupTime() + "\n" +
						"**Uptime: **" + SelfBot.getUpTime() + "\n" +
						"**Creator: **Gregg" + "\n" +
						"**GitHub link: **<https://github.com/SirGregg/Self-Bot>")).queue();
	}
}