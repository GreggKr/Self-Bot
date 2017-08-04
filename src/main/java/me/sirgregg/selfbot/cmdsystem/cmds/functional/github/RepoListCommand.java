package me.sirgregg.selfbot.cmdsystem.cmds.functional.github;

import me.sirgregg.selfbot.SelfBot;
import me.sirgregg.selfbot.cmdsystem.Command;
import me.sirgregg.selfbot.util.EmbedUtil;
import me.sirgregg.selfbot.util.config.Configuration;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class RepoListCommand extends Command {
	private Configuration configuration = SelfBot.getConfiguration();

	public RepoListCommand() {
		super(new String[]{"repolist", "listrepos"}, "repolist <user> <page>", "Retrieves a list of a users's GitHub repositories.");
	}

	@Override
	public void execute(MessageReceivedEvent e, String[] args) {
		Color color = e.getGuild().getSelfMember().getColor();
		// 1 -> user
		// 2 -> page
		if (args.length < 2) {
			e.getMessage().editMessage(
					EmbedUtil.createEmbed(color, "**Invalid usage:**\n" + "Correct usage: " + SelfBot.getConfiguration().getLead() + getUsage()))
					.queue();
			return;
		}

		String user = args[0];
		int page = -1;
		try {
			page = Integer.parseInt(args[1]);
		} catch (NumberFormatException ex) {
			e.getMessage().editMessage(
					EmbedUtil.createEmbed(color, "**Caught Exception:\n**Please enter a valid number.")
			).queue();
		}

		GitHubClient client = new GitHubClient();
		client.setOAuth2Token(configuration.getGithubOAuthToken());
		RepositoryService service = new RepositoryService(client);

		EmbedBuilder embedBuilder = new EmbedBuilder();
		embedBuilder.setColor(color);
		try {
			List<Repository> repos = service.getRepositories(user);
			embedBuilder.setDescription("**Repositories from '" + user + "':**\n");
			if (repos.size() < 10) {
				for (Repository repo : repos) {
					embedBuilder.appendDescription(repo.getName() + "\n");
				}
				embedBuilder.appendDescription("\nProfile: <https://www.github.com/" + user + "/>");
				embedBuilder.setFooter("Page 1 of 1", null);
			} else {
				int from = (page * 10) - 10;
				int to = (page * 10);

				if (from > repos.size() || from > to) {
					from = repos.size() - 10;
					to = repos.size();
				}

				if (to > repos.size()) {
					to = repos.size();
				}

				List<Repository> cutRepos = repos.subList(from, to);
				for (Repository repo : cutRepos) {
					embedBuilder.appendDescription(repo.getName() + "\n");
				}
				embedBuilder.appendDescription("\nProfile: <https://www.github.com/" + user + "/>");
				embedBuilder.setFooter("Page " + page + " of " + Math.round(repos.size() / 10), null);

			}
			e.getMessage().editMessage(embedBuilder.build()).queue();

		} catch (IOException | IllegalArgumentException ex) {
			e.getMessage().editMessage(EmbedUtil.createEmbed(color, "**Exception Caught:**\nUser not found or has a null charset.\nPlease report this to SirGregg.")).queue();
		}

	}
}