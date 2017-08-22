package me.sirgregg.selfbot.cmdsystem.cmds.fun;

import me.sirgregg.selfbot.SelfBot;
import me.sirgregg.selfbot.cmdsystem.Command;
import me.sirgregg.selfbot.util.EmbedUtil;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;

public class QuoteCommand extends Command {
	public QuoteCommand() {
		super(new String[] { "q", "quote" }, "quote [channel ID] <message ID>", "Displays a quote of the message.");
	}

	@Override
	public void execute(MessageReceivedEvent e, String[] args) {
		Color color = e.getGuild().getSelfMember().getColor();
		if (args.length < 1) {
			e.getMessage().editMessage(
					EmbedUtil.createEmbed(color, "**Invalid usage:**\n" + "Correct usage: " + SelfBot.getConfiguration().getLead() + getUsage()))
					.queue();
			return;
		}

		String id1 = args[0].replaceAll("<#(\\d+)>", "$1");
		if (!isId(id1)) {
			e.getMessage().editMessage(EmbedUtil.createEmbed(color, "**Caught Exception:**\n'" + id1 + "' is not a valid channel/message ID.")).queue();
			return;
		}
		MessageChannel channel = e.getJDA().getTextChannelById(id1);
		String messageId;
		if (channel != null) {
			if (args.length == 1) {
				e.getMessage().editMessage(EmbedUtil.createEmbed(color, "**Caught Exception:**\n No message ID provided.")).queue();
				return;
			}
			if (!isId(args[1])) {
				e.getMessage().editMessage(EmbedUtil.createEmbed(color, "**Caught Exception:**\n'" + args[1] + "' is not a valid message ID.")).queue();
				return;
			}
			messageId = args[1];
		} else {
			messageId = id1;
			if (args.length > 1) {
				String[] parts2 = args[1].split("\\s+", 2);
				String id2 = parts2[0].replaceAll("<#(\\d+)>", "$1");
				channel = e.getJDA().getTextChannelById(id2);
			}
		}
		if (channel == null) {
			channel = e.getChannel();
		}
		try {
			String foot = channel.equals(e.getChannel()) ? "" : "in #" + channel.getName();
			channel.getHistoryAround(messageId, 2).queue(history -> {
				if (history.getRetrievedHistory().isEmpty()) {
					e.getMessage().editMessage("**Caught Exception:**\nCould not find history around message " + messageId + ".").queue();
					return;
				}
				Message message = history.getRetrievedHistory().size() == 1 || history.getRetrievedHistory().size() == 2 ? history.getRetrievedHistory().get(0) : history.getRetrievedHistory().get(1);

				EmbedBuilder embedBuilder = new EmbedBuilder();
				embedBuilder.setAuthor(message.getAuthor().getName() + "#" + message.getAuthor().getDiscriminator(), null, message.getAuthor().getEffectiveAvatarUrl());
				Member member = message.getGuild().getMemberById(message.getAuthor().getId());
				if (member != null) {
					embedBuilder.setColor(member.getColor());
				}

				if (!message.getAttachments().isEmpty() && message.getAttachments().get(0).isImage()) {
					embedBuilder.setImage(message.getAttachments().get(0).getUrl());
				}

				if (message.isEdited()) {
					embedBuilder.setFooter("Edited: " + foot, null).setTimestamp(message.getEditedTime());
				} else {
					embedBuilder.setFooter("Sent: " + foot, null).setTimestamp(message.getCreationTime());
				}
				embedBuilder.setDescription(message.getRawContent());
				e.getMessage().editMessage(embedBuilder.build()).queue();
			});
		} catch (Exception ex) {
			e.getMessage().editMessage(EmbedUtil.createEmbed(color, "**Caught Exception:**\nUnknown cause.")).queue();
		}
	}

	private boolean isId(String id) {
		return id.matches("\\d{17,21}");
	}

}