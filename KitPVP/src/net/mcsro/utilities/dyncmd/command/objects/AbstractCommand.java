package net.mcsro.utilities.dyncmd.command.objects;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AbstractCommand extends Command {
	private CommandExecutor executor = null;

	public AbstractCommand(String name) {
		super(name);
	}

	@Override
	public boolean execute(CommandSender commandSender, String s,
			String[] strings) {
		return executor != null
				&& executor.onCommand(commandSender, this, s, strings);
	}

	public CommandExecutor getExecutor() {
		return executor;
	}

	public void setExecutor(CommandExecutor executor) {
		this.executor = executor;
	}
}