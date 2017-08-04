package me.sirgregg.selfbot.cmdsystem;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public abstract class Command {
	private String[] keywords;
	private String usage, description;

	// Creates a Command with only one keyword
	public Command(String keyword, String usage, String description) {
		this(new String[]{keyword}, usage, description);
	}

	// Creates a Command with more than one keyword
	public Command(String[] keywords, String usage, String description) {
		this.keywords = keywords;
		this.usage = usage;
		this.description = description;
	}

	public abstract void execute(MessageReceivedEvent e, String[] args);

	public String[] getKeywords() {
		return keywords;
	}

	public String getUsage() {
		return usage;
	}

	public String getDescription() {
		return description;
	}
}