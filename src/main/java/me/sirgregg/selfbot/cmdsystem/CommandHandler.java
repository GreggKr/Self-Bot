package me.sirgregg.selfbot.cmdsystem;

import me.sirgregg.selfbot.SelfBot;
import me.sirgregg.selfbot.cmdsystem.cmds.fun.*;
import me.sirgregg.selfbot.cmdsystem.cmds.functional.HelpCommand;
import me.sirgregg.selfbot.cmdsystem.cmds.functional.LineCommand;
import me.sirgregg.selfbot.cmdsystem.cmds.functional.UrbanDictionaryCommand;
import me.sirgregg.selfbot.cmdsystem.cmds.functional.eval.EvalCommand;
import me.sirgregg.selfbot.cmdsystem.cmds.functional.eval.EvalFileCommand;
import me.sirgregg.selfbot.cmdsystem.cmds.functional.github.RepoCommand;
import me.sirgregg.selfbot.cmdsystem.cmds.functional.github.RepoListCommand;
import me.sirgregg.selfbot.cmdsystem.cmds.functional.info.BotInfoCommand;
import me.sirgregg.selfbot.cmdsystem.cmds.functional.info.GuildInfoCommand;
import me.sirgregg.selfbot.cmdsystem.cmds.functional.info.UserInfoCommand;
import me.sirgregg.selfbot.util.Logger;
import me.sirgregg.selfbot.util.config.Configuration;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandHandler extends ListenerAdapter {
	private static List<Command> commands = new ArrayList<>();
	private Configuration configuration = SelfBot.getConfiguration();

	public CommandHandler() {
		addCommand(new HelpCommand());
		addCommand(new PingCommand());
		addCommand(new CatCommand());
		addCommand(new SafetyPigCommand());
		addCommand(new FancyCommand());
		addCommand(new RepoListCommand());
		addCommand(new RepoCommand());
		addCommand(new LineCommand());
		addCommand(new LolCommand());
		addCommand(new QuoteCommand());
		addCommand(new EvalCommand());
		addCommand(new ShortenCommand());
		addCommand(new GuildInfoCommand());
		addCommand(new BotInfoCommand());
		addCommand(new UserInfoCommand());
		addCommand(new UrbanDictionaryCommand());
		addCommand(new EvalFileCommand());
	}

	// Returns a list of commands that you can't change.
	public static List<Command> getCommands() {
		return Collections.unmodifiableList(commands);
	}

	private void addCommand(Command command) {
		if (!commands.contains(command)) {
			commands.add(command);
			Logger.info("Registered " + command.getKeywords()[0] + " command!");
		}
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		if (!e.getJDA().getSelfUser().equals(e.getAuthor()))
			return; // Makes sure it doesn't listen for its own messages

		String[] rawArgs = e.getMessage().getContent().split(" ");
		String[] args = Arrays.copyOfRange(rawArgs, 1, rawArgs.length); // Gets an array without the trigger

		if (!rawArgs[0].toLowerCase().startsWith(configuration.getLead())) return;
		Logger.info("Caught a command from you.");

		String keyword = rawArgs[0].toLowerCase().replace(configuration.getLead(), ""); // Gets the first argument *without* the lead

		for (Command command : commands) {
			List<String> keywords = Arrays.asList(command.getKeywords());
			if (keywords.contains(keyword.toLowerCase())) {
				// NOTE: Now use e.getMessage().editMessage(). Helps reduce ratelimted effects
				//e.getMessage().delete().queue(); // Removes the trigger message.
				Logger.info("Executed " + command.getKeywords()[0] + " command.");
				command.execute(e, args);
			}
		}
	}
}