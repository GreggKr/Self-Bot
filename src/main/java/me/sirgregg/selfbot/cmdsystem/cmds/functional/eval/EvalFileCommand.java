package me.sirgregg.selfbot.cmdsystem.cmds.functional.eval;

import me.sirgregg.selfbot.SelfBot;
import me.sirgregg.selfbot.cmdsystem.Command;
import me.sirgregg.selfbot.util.EmbedUtil;
import me.sirgregg.selfbot.util.config.Configuration;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class EvalFileCommand extends Command {
	private Configuration configuration = SelfBot.getConfiguration();
	public EvalFileCommand() {
		super ("evalfile", "evalfile", "Evaluate a script from a file.");
	}

	@Override
	public void execute(MessageReceivedEvent e, String[] args) {
		Color color = e.getGuild().getSelfMember().getColor();

		ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("nashorn");
		try {
			FileReader reader = new FileReader(configuration.getScriptFilePath());

			scriptEngine.put("e", e);
			scriptEngine.put("message", e.getMessage());
			scriptEngine.put("channel", e.getChannel());
			scriptEngine.put("args", args);
			scriptEngine.put("jda", e.getJDA());
			scriptEngine.put("guild", e.getGuild());
			scriptEngine.put("author", e.getAuthor());

			Object output = scriptEngine.eval(reader);

			reader.close();
			e.getMessage().editMessage(EmbedUtil.createEmbed(color, "**Execution Successful**\n" + (output == null ? "Nothing returned." : output.toString()))).queue();
		} catch (ScriptException ex) {
			e.getMessage().editMessage(EmbedUtil.createEmbed(color, "**Error caught**\n" + ex)).queue();
		} catch (FileNotFoundException ex) {
			e.getMessage().editMessage(EmbedUtil.createEmbed(color, "**Error caught**\nFailed to find script file.")).queue();
		} catch (IOException ex) {
			e.getMessage().editMessage(EmbedUtil.createEmbed(color, "**Error caught**\nFailed to close reader.")).queue();
		}
	}
	/*

	SAMPLE FILE:
	(C:\SelfBot\script.js)

	- - - - -
	function add(a, b) {
		return a + b;
	}

	add(1, 2);
	- - - - -


	 */
}
