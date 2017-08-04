package me.sirgregg.selfbot.cmdsystem.cmds.functional;

import me.sirgregg.selfbot.cmdsystem.Command;
import me.sirgregg.selfbot.cmdsystem.CommandHandler;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class HelpCommand extends Command {
	public HelpCommand() {
		super(new String[]{
				"help", "?"
		}, "help", "Prints the help screen.");
	}

	@Override
	public void execute(MessageReceivedEvent e, String[] args) {
		EmbedBuilder embedBuilder = new EmbedBuilder().setColor(e.getGuild().getSelfMember().getColor());

		for (Command command : CommandHandler.getCommands()) {
			embedBuilder.addField(command.getUsage(), "*" + command.getDescription() + "*\n", false);
		}
		e.getMessage().editMessage(embedBuilder.build()).queue();
	}
}