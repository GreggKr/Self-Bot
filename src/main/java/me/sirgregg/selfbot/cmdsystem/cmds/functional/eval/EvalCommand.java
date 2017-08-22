package me.sirgregg.selfbot.cmdsystem.cmds.functional.eval;

import me.sirgregg.selfbot.SelfBot;
import me.sirgregg.selfbot.cmdsystem.Command;
import me.sirgregg.selfbot.util.EmbedUtil;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.awt.*;

public class EvalCommand extends Command {

	public EvalCommand() {
		super("eval", "eval <evaluation>", "Executes code based on the Nashorn engine.");
	}

	@Override
	public void execute(MessageReceivedEvent e, String[] args) {
		Color color = e.getGuild().getSelfMember().getColor();
		if (args.length == 0) {
			e.getMessage().editMessage(
					EmbedUtil.createEmbed(color, "**Invalid usage:**\n" + "Correct usage: " + SelfBot.getConfiguration().getLead() + getUsage()))
					.queue();
			return;
		}

		StringBuilder sb = new StringBuilder();
		for (String string : args) {
			sb.append(string).append(" ");
		}

		String script = sb.toString().trim();
		ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("nashorn");
		try {
			scriptEngine.put("e", e);
			scriptEngine.put("message", e.getMessage());
			scriptEngine.put("channel", e.getChannel());
			scriptEngine.put("args", args);
			scriptEngine.put("jda", e.getJDA());
			scriptEngine.put("guild", e.getGuild());
			scriptEngine.put("author", e.getAuthor());

			Object output = scriptEngine.eval(script);
			e.getMessage().editMessage(EmbedUtil.createEmbed(color, "**Execution Successful**\n" + (output == null ? "Nothing returned." : output.toString()))).queue();
		} catch (ScriptException ex) {
			e.getMessage().editMessage(EmbedUtil.createEmbed(color, "**Error caught**\n" + ex)).queue();
		}

	}
}