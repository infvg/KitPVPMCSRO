package net.mcsro.utilities.dyncmd.command.objects;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import net.mcsro.utilities.ChatUtils;
import net.mcsro.utilities.dyncmd.command.DynCmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RegisteredCommand extends ParentCommand implements
		CommandExecutor, Handler {
	private final QueuedCommand queuedCommand;
	private String command = "";
	private Handler handler = this;

	public RegisteredCommand(QueuedCommand queuedCommand) {
		this.queuedCommand = queuedCommand;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String s,
			String[] args) {
		try {
			DynCmd commandHandler = getMethod().getAnnotation(
					DynCmd.class);
			List<String> strings = Arrays.asList(args);
			getHandler().handleCommand(
					new CommandInfo(this, this, commandHandler, sender, s,
							sortQuotedArgs(strings), commandHandler.usage(),
							commandHandler.permission()));
		} catch (Exception e) {
			ChatUtils.send(sender, "<red>Failed to handle command properly.");
		}
		return true;
	}

	@Override
	public void handleCommand(CommandInfo info) throws Exception {
		try {
			this.getMethod().invoke(queuedCommand.getObject(), info);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public void displayDefaultUsage(CommandSender sender, String command,
			ParentCommand parentCommand) {
		String prefix;
		if (command.equals(getCommand())) {
			ChatUtils.send(sender, "Usage: /%s", command);
			prefix = command;
		} else {
			ChatUtils.send(sender, "Usage for '%s'", command);
			prefix = recursivelyAddToPrefix(getCommand(), command);
		}
		recursivelyDisplayChildUsage(sender, parentCommand, prefix);
	}

	public String recursivelyAddToPrefix(String prefix, String command) {
		for (Entry<String, ChildCommand> entry : getChildCommands().entrySet()) {
			if (entry.getKey().equals(command)) {
				prefix = prefix + " " + command;
			} else {
				prefix = prefix + " " + entry.getKey();
				recursivelyAddToPrefix(prefix, command);
			}
		}
		return prefix;
	}

	public void recursivelyDisplayChildUsage(CommandSender sender,
			ParentCommand parentCommand, String prefix) {
		for (Entry<String, ChildCommand> entry : parentCommand
				.getChildCommands().entrySet()) {
			String usage = entry.getValue().getUsage();
			String description = entry.getValue().getDescription();
			ChatUtils.send(sender, "/%s %s %s %s", prefix, entry.getKey(),
					usage, description);
			if (!entry.getValue().getChildCommands().isEmpty()) {
				prefix += " " + entry.getKey();
				recursivelyDisplayChildUsage(sender, entry.getValue(), prefix);
			}
		}
	}

	public List<String> sortQuotedArgs(List<String> args) {
		return sortEnclosedArgs(args, '"');
	}

	private List<String> sortEnclosedArgs(List<String> args, char c) {
		List<String> strings = new ArrayList<String>(args.size());
		for (int i = 0; i < args.size(); ++i) {
			String arg = args.get(i);
			if (arg.length() == 0) {
				continue;
			}
			if (arg.charAt(0) == c) {
				int j;
				final StringBuilder builder = new StringBuilder();
				for (j = i; j < args.size(); ++j) {
					String arg2 = args.get(j);
					if (arg2.charAt(arg2.length() - 1) == c
							&& arg2.length() >= 1) {
						builder.append(j != i ? " " : "").append(
								arg2.substring(j == i ? 1 : 0,
										arg2.length() - 1));
						break;
					} else {
						builder.append(j == i ? arg2.substring(1) : " " + arg2);
					}
				}
				if (j < args.size()) {
					arg = builder.toString();
					i = j;
				}
			}
			strings.add(arg);
		}
		return strings;
	}

	private Method getMethod() {
		return queuedCommand.getMethod();
	}

	public DynCmd getCommandHandler() {
		return getMethod().getAnnotation(DynCmd.class);
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public String getPermission() {
		if (queuedCommand == null) {
			return "";
		} else {
			return getCommandHandler().permission();
		}
	}

	public String getCommand() {
		if (queuedCommand == null) {
			return command;
		} else {
			return getCommandHandler().command();
		}
	}

	public void setCommand(String command) {
		this.command = command;
	}
}