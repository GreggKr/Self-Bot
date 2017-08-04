package me.sirgregg.selfbot.cmdsystem.cmds.functional.github;

import me.sirgregg.selfbot.SelfBot;
import me.sirgregg.selfbot.cmdsystem.Command;
import me.sirgregg.selfbot.util.EmbedUtil;
import me.sirgregg.selfbot.util.config.Configuration;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.awt.*;
import java.io.IOException;

public class RepoCommand extends Command {
	private Configuration configuration = SelfBot.getConfiguration();
	public RepoCommand() {
		super("repo", "repo <user> <name>", "Retrieves information about a users's GitHub repository.");
	}

	@Override
	public void execute(MessageReceivedEvent e, String[] args) {
		Color color = e.getGuild().getSelfMember().getColor();

		// 1 -> user
		// 2 -> repo name
		if (args.length < 2) {
			e.getMessage().editMessage(
					EmbedUtil.createEmbed(color, "**Invalid usage:**\n" + "Correct usage: " + SelfBot.getConfiguration().getLead() + getUsage()))
					.queue();
			return;
		}

		String user = args[0];
		String repoName = args[1];

		GitHubClient client = new GitHubClient();
		client.setOAuth2Token(configuration.getGithubOAuthToken());
		RepositoryService service = new RepositoryService(client);

		try {
			Repository repo = service.getRepository(user, repoName);

			e.getMessage().editMessage(EmbedUtil.createEmbed(color, "**GitHub Repository Information:**\n" +
					"Creator: " + repo.getOwner().getLogin() + "\n" +
					"Name: " + repo.getName() + "\n" +
					"Description: " + ((repo.getDescription() == null) ? "Not defined." : repo.getDescription()) + "\n" +
					"# of watchers: " + repo.getWatchers() + "\n" +
					"URL: <https://www.github.com/" + repo.getOwner().getLogin() + "/" + repo.getName() + "/>\n" +
					"Times forked: " + repo.getForks() + "\n" +
					"Is fork? " + repo.isFork() + "\n" +
					"Language: " + ((repo.getLanguage() == null) ? "Not defined." : repo.getLanguage()) + "\n" +
					"Pushed at: " + repo.getPushedAt().toString())).queue();
		} catch(IOException ex) {
			e.getMessage().editMessage(EmbedUtil.createEmbed(color, "**Error:**\nEither the user or their repository was not found.")).queue();
		}

	}
}