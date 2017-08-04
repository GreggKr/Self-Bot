package me.sirgregg.selfbot.util;

import me.sirgregg.selfbot.SelfBot;
import me.sirgregg.selfbot.util.config.Configuration;
import net.dv8tion.jda.core.utils.SimpleLog;

public class Logger {
	private static final SimpleLog SIMPLE_LOG = SimpleLog.getLog("SelfBot");
	private static Configuration configuration = SelfBot.getConfiguration();
	private static boolean loggingEnabled = configuration.isLoggingEnabled();
	public static void info(Object object) {
		if (loggingEnabled) {
			SIMPLE_LOG.info(object);
		}
	}

	public static void fatal(Object object) {
		if (loggingEnabled) {
			SIMPLE_LOG.fatal(object);
		}
	}

	private boolean isLoggingEnabled() {
		return loggingEnabled;
	}
}