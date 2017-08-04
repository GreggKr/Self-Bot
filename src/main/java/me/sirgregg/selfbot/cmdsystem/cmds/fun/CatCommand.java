package me.sirgregg.selfbot.cmdsystem.cmds.fun;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.sirgregg.selfbot.cmdsystem.Command;
import me.sirgregg.selfbot.util.EmbedUtil;
import me.sirgregg.selfbot.util.URLUtil;
import me.sirgregg.selfbot.util.objects.CatImage;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.IOException;

public class CatCommand extends Command {
	public CatCommand() {
		super("cat", "cat", "Displays a random image of a cat.");
	}

	@Override
	public void execute(MessageReceivedEvent e, String[] args) {
		try {
			Gson gson = new GsonBuilder().create();
			CatImage catImage = gson.fromJson(URLUtil.readURL("http://random.cat/meow"), CatImage.class); // Sets catImage to the json gotten from the cat

			e.getMessage().editMessage(EmbedUtil.createImageEmbed(e.getGuild().getSelfMember().getColor(), catImage.getFile())).queue();
		} catch (IOException ex) {
			e.getMessage().editMessage(EmbedUtil.createEmbed(e.getGuild().getSelfMember().getColor(), "**Caught Exception**\nCouldn't open URL.")).queue();
		}
	}
}