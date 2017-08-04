package me.sirgregg.selfbot.cmdsystem.cmds.fun;

import me.sirgregg.selfbot.cmdsystem.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.time.temporal.ChronoUnit;

public class PingCommand extends Command {
	public PingCommand() {
		super("ping", "ping", "Shows the ping between you and the server you're in.");
	}

	@Override
	public void execute(MessageReceivedEvent e, String[] args) {
		e.getMessage().editMessage(
				"Pingging...").queue(
				(message) -> message.editMessage("Current ping: " + message.getCreationTime().until(message.getEditedTime(), ChronoUnit.MILLIS) + "ms").queue());
	}
}