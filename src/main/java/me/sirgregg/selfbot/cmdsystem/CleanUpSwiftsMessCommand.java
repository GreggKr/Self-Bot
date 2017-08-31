package me.sirgregg.selfbot.cmdsystem;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.GuildController;

/*
Why does this class exist?
https://gyazo.com/19aae5ac3e97f4f03a251bbcbade8c7c (That is only half of them)
https://gyazo.com/d6f52f596f2c8abfe8fcb9d3d65c76af
 */
public class CleanUpSwiftsMessCommand extends Command {
	public CleanUpSwiftsMessCommand() {
		super("cleanupswiftsmess", "cleanupswiftsmess", "I hate you SwiftBeatz.");
	}

	@Override
	public void execute(MessageReceivedEvent e, String[] args) {
		GuildController controller = e.getGuild().getController();
		e.getGuild().getTextChannels().forEach((textChannel -> {
			if (textChannel.getName().contains("sirgregg")) {
				textChannel.delete().queue();
			}
		}));
	}
}
