package me.sirgregg.selfbot.cmdsystem.cmds.fun;

import me.sirgregg.selfbot.cmdsystem.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class SafetyPigCommand extends Command {
	public SafetyPigCommand() {
		super(new String[] { "safteypig", "spig" }, "spig", "Displays an image of safety pig. Oink oink!");
	}
	@Override
	public void execute(MessageReceivedEvent e, String[] args) {
		e.getMessage().editMessage("```" +
				"Oink oink!                          \n" +
				" _._ _..._ .-',     _.._(`))        \n" +
				"'-. `     '  /-._.-'    ',/         \n" +
				"   )         \\            '.       \n" +
				"  / _    _   |              \\      \n" +
				" |  a    a   /               |      \n" +
				" \\  .-.                     ;      \n" +
				"  '-('' ).-'       ,'       ;       \n" +
				"     '-;           |      .'        \n" +
				"        \\           \\    /        \n" +
				"        | 7  .__  _.-\\   \\        \n" +
				"        | |  |  `` /  /`   /         \n" +
				"        /,_|  |   /,_/   /          \n" +
				"           /,_/      '`-'              " + "```").queue();
	}
}