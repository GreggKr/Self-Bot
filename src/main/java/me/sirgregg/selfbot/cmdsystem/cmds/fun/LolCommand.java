package me.sirgregg.selfbot.cmdsystem.cmds.fun;

import me.sirgregg.selfbot.cmdsystem.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class LolCommand extends Command {
	public LolCommand() {
		super("lol", "lol", "Displays a \"lol\" picture");
	}

	@Override
	public void execute(MessageReceivedEvent e, String[] args) {
		e.getMessage().editMessage("```" +
				"   ,--,                    ,--,         \n" +
				",---.'|       ,----..   ,---.'|      \n" +
				"|   | :      /   /   \\  |   | :     \n" +
				":   : |     /   .     : :   : |      \n" +
				"|   ' :    .   /   ;.  \\|   ' :     \n" +
				";   ; '   .   ;   /  ` ;;   ; '      \n" +
				"'   | |__ ;   |  ; \\ ; |'   | |__   \n" +
				"|   | :.'||   :  | ; | '|   | :.'|   \n" +
				"'   :    ;.   |  ' ' ' :'   :    ;   \n" +
				"|   |  ./ '   ;  \\; /  ||   |  ./   \n" +
				";   : ;    \\   \\  ',  / ;   : ;    \n" +
				"|   ,/      ;   :    /  |   ,/       \n" +
				"'---'        \\   \\ .'   '---'      \n" +
				"              `---`                  \n" +
				"```"
		).queue();
	}
}