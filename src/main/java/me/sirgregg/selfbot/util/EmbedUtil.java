package me.sirgregg.selfbot.util;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;

import java.awt.*;

public class EmbedUtil {
	public static MessageEmbed createEmbed(Color color, String description) {
		EmbedBuilder embedBuilder = new EmbedBuilder();
		embedBuilder.setColor(color).setDescription(description);
		return embedBuilder.build();
	}

	public static MessageEmbed createEmbedWithAuthor(Color color, User author, String description) {
		EmbedBuilder embedBuilder = new EmbedBuilder();
		embedBuilder.setColor(color).setAuthor(author.getName(), null, author.getAvatarUrl()).setDescription(description);
		return embedBuilder.build();
	}

	public static MessageEmbed createFieldEmbed(Color color, String fieldName, String fieldValue) {
		EmbedBuilder embedBuilder = new EmbedBuilder();
		embedBuilder.setColor(color).addField(fieldName, fieldValue, true);
		return embedBuilder.build();
	}

	public static MessageEmbed createImageEmbed(Color color, String imageFile) {
		EmbedBuilder embedBuilder = new EmbedBuilder();
		embedBuilder.setColor(color).setImage(imageFile);
		return embedBuilder.build();
	}
}