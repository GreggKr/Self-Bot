package me.sirgregg.selfbot.cmdsystem.cmds.functional;

import me.sirgregg.selfbot.cmdsystem.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class LineCommand extends Command {
	public LineCommand() {
		super("line", "line", "Posts a line in the chat to separate stuff.");
	}

	@Override
	public void execute(MessageReceivedEvent e, String[] args) {
		e.getMessage()
				.editMessage("~~                                                                                                                                                                 ~~")
				.queue();
	}
}