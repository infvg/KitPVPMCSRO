package net.mcsro.utilities.dyncmd.command;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import net.mcsro.utilities.dyncmd.command.objects.AbstractCommand;
import net.mcsro.utilities.dyncmd.command.objects.ChildCommand;
import net.mcsro.utilities.dyncmd.command.objects.CommandInfo;
import net.mcsro.utilities.dyncmd.command.objects.DefaultChildCommand;
import net.mcsro.utilities.dyncmd.command.objects.ParentCommand;
import net.mcsro.utilities.dyncmd.command.objects.QueuedCommand;
import net.mcsro.utilities.dyncmd.command.objects.RegisteredCommand;
import net.mcsro.utilities.particles.ReflectionUtils;
import net.mcsro.utilities.reflection.ClassEnumerator;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.help.GenericCommandHelpTopic;
import org.bukkit.help.HelpTopic;
import org.bukkit.help.HelpTopicComparator;
import org.bukkit.help.IndexHelpTopic;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandManager {
	private final Plugin plugin;
	private final Map<Integer, List<QueuedCommand>> queuedCommands = new ConcurrentHashMap<Integer, List<QueuedCommand>>();
	private final Map<String, RegisteredCommand> registeredCommands = new ConcurrentHashMap<String, RegisteredCommand>();
	private CommandMap commandMap;

	public CommandManager(Plugin plugin) {
		this.plugin = plugin;
	}

	public void registerHelp() {
		Set<HelpTopic> help = new TreeSet<HelpTopic>(
				HelpTopicComparator.helpTopicComparatorInstance());
		for (String s : registeredCommands.keySet()) {
			Command cmd = commandMap.getCommand(s);
			if (cmd != null) {
				HelpTopic topic = new GenericCommandHelpTopic(cmd);
				help.add(topic);
			}
		}
		IndexHelpTopic topic = new IndexHelpTopic(plugin.getName(),
				"All commands for " + plugin.getName(), null, help,
				"Below is a list of all " + plugin.getName() + " commands:");
		Bukkit.getServer().getHelpMap().addTopic(topic);
	}

	public void registerCommands() {
		Class<?>[] classes = ClassEnumerator.getInstance()
				.getClassesFromThisJar(plugin);
		if (classes == null || classes.length == 0) {
			return;
		}
		for (Class<?> c : classes) {
			try {
				if (CommandListener.class.isAssignableFrom(c)
						&& !c.isInterface() && !c.isEnum() && !c.isAnnotation()) {
					if (JavaPlugin.class.isAssignableFrom(c)) {
						if (plugin.getClass().equals(c)) {
							registerCommands(plugin);
						}
					} else {
						registerCommands(c.newInstance());
					}
				}
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		processQueuedCommands();
	}

	private void processQueuedCommands() {
		synchronized (queuedCommands) {
			int MAX_ITERATION = 0;
			for (int i : queuedCommands.keySet()) {
				if (i > MAX_ITERATION) {
					MAX_ITERATION = i;
				}
			}
			for (int i = 1; i <= MAX_ITERATION; i++) {
				List<QueuedCommand> queuedCommandList = queuedCommands.get(i);
				if (queuedCommandList == null || queuedCommandList.isEmpty()) {
					continue;
				}
				for (QueuedCommand queue : queuedCommandList) {
					DynCmd commandHandler = queue.getMethod()
							.getAnnotation(DynCmd.class);
					String[] list = commandHandler.command().split("\\.");
					RegisteredCommand registered;
					synchronized (registeredCommands) {
						if (!registeredCommands.containsKey(list[0])) {
							RegisteredCommand registeredEmpty = new RegisteredCommand(
									null);
							registeredEmpty.setCommand(list[0]);
							synchronized (registeredCommands) {
								registeredCommands
										.put(list[0], registeredEmpty);
							}
							AbstractCommand abstractCmd = new AbstractCommand(
									list[0]);
							abstractCmd.setDescription("Use '/" + list[0]
									+ " help' to view the subcommands.");
							abstractCmd.setPermission("");
							abstractCmd
									.setPermissionMessage("You don't have permission to do that.");
							abstractCmd.setUsage("/" + list[0] + " <command>");
							abstractCmd.setExecutor(registeredEmpty);
							registerBaseCommand(abstractCmd);
							continue;
						}
						registered = registeredCommands.get(list[0]);
					}
					registerChild(queue, commandHandler, registered,
							list[list.length - 1]);
					for (String s : commandHandler.aliases()) {
						registerChild(queue, commandHandler, registered, s);
					}
				}
			}
			queuedCommands.clear();
		}
	}

	private void registerChild(QueuedCommand queue,
			DynCmd commandHandler, RegisteredCommand registered,
			String s) {
		ChildCommand child = new ChildCommand(commandHandler);
		ParentCommand parentCommand = recursivelyFindInnerMostParent(
				commandHandler.command(), registered, 1);
		String[] list = commandHandler.command().split("\\.");
		if (list.length == 2) {
			registered.addChild(s, child);
			return;
		}
		if (parentCommand.getClass().equals(registered.getClass())) {
			if (!registered.getCommandHandler().command()
					.equals(list[list.length - 2 <= 0 ? 0 : list.length - 2])) {
				List<String> indexer = Arrays.asList(list);
				int index = indexer.indexOf(registered.getCommandHandler()
						.command());
				String s1 = list[index + 1];
				DefaultChildCommand dummyChild = new DefaultChildCommand(s1);
				dummyChild.setPermission(registered.getPermission());
				registered.addChild(s1, dummyChild);
				registerChild(queue, commandHandler, registered, s);
				s1 = "";
				for (String s2 : indexer) {
					if (indexer.indexOf(s2) < index + 1) {
						s1 += s2 + ".";
					}
					
				}
				return;
			}
			registered.addChild(s, child);
		} else if (parentCommand.getClass().equals(DefaultChildCommand.class)) {
			DefaultChildCommand childParent = (DefaultChildCommand) parentCommand;
			if (!childParent.getCommand().equals(
					list[list.length - 2 <= 0 ? 0 : list.length - 2])) {
				List<String> indexer = Arrays.asList(list);
				int index = indexer.indexOf(registered.getCommandHandler()
						.command());
				String s1 = list[index + 1];
				DefaultChildCommand dummyChild = new DefaultChildCommand(s1);
				dummyChild.setPermission(childParent.getPermission());
				childParent.addChild(s1, dummyChild);
				registerChild(queue, commandHandler, registered, s);
				s1 = "";
				for (String s2 : indexer) {
					if (indexer.indexOf(s2) < index + 1) {
						s1 += s2 + ".";
					}
				}
				return;
			}
			childParent.addChild(s, child);
		} else {
			ChildCommand childParent = (ChildCommand) parentCommand;
			if (!childParent.getCommand().equals(
					list[list.length - 2 <= 0 ? 0 : list.length - 2])) {
				List<String> indexer = Arrays.asList(list);
				int index = indexer.indexOf(registered.getCommandHandler()
						.command());
				String s1 = list[index + 1];
				DefaultChildCommand dummyChild = new DefaultChildCommand(s1);
				dummyChild.setPermission(childParent.getPermission());
				childParent.addChild(s1, dummyChild);
				registerChild(queue, commandHandler, registered, s);
				s1 = "";
				for (String s2 : indexer) {
					if (indexer.indexOf(s2) < index + 1) {
						s1 += s2 + ".";
					}
				}
				return;
			}
			childParent.addChild(s, child);
		}
	}

	private ParentCommand recursivelyFindInnerMostParent(String command,
			ParentCommand parentCommand, int start) {
		String[] list = command.split("\\.");
		if (start > list.length - 1) {
			return parentCommand;
		}
		return parentCommand.hasChild(list[start]) ? recursivelyFindInnerMostParent(
				command, parentCommand.getChild(list[start]), ++start)
				: parentCommand;
	}

	public void registerCommands(Object classObject) {
		if (!CommandListener.class.isAssignableFrom(classObject.getClass())) {
			return;
		}
		for (Method method : classObject.getClass().getDeclaredMethods()) {
			DynCmd commandHandler = method
					.getAnnotation(DynCmd.class);
			if (commandHandler == null
					|| method.getParameterTypes()[0] != CommandInfo.class) {
				continue;
			}
			Object object = classObject;
			if (Modifier.isStatic(method.getModifiers())) {
				object = null;
			}
			if (commandHandler.command().contains(".")) {
				queueCommand(object, method, commandHandler);
			} else {
				registerBaseCommand(object, method, commandHandler);
			}
		}
		processQueuedCommands();
	}

	private void registerBaseCommand(Object classObject, Method method,
			DynCmd commandHandler) {
		QueuedCommand queue = new QueuedCommand(classObject, method);
		RegisteredCommand registered = new RegisteredCommand(queue);
		synchronized (registeredCommands) {
			registeredCommands.put(commandHandler.command(), registered);
		}
		AbstractCommand abstractCmd = new AbstractCommand(
				commandHandler.command());
		abstractCmd.setAliases(Arrays.asList(commandHandler.aliases()));
		abstractCmd.setDescription(commandHandler.description());
		abstractCmd.setPermission(commandHandler.permission());
		abstractCmd.setPermissionMessage(commandHandler.noPermission());
		abstractCmd.setUsage(commandHandler.usage());
		abstractCmd.setExecutor(registered);
		registerBaseCommand(abstractCmd);
	}

	private void registerBaseCommand(AbstractCommand command) {
		if (getCommandMap().getCommand(command.getName()) == null) {
			getCommandMap().register(plugin.getName(), command);
		}
	}

	private void queueCommand(Object classObject, Method method,
			DynCmd commandHandler) {
		synchronized (queuedCommands) {
			QueuedCommand queue = new QueuedCommand(classObject, method);
			int numberOfChildren = commandHandler.command().split("\\.").length - 1;
			List<QueuedCommand> queueList = queuedCommands
					.get(numberOfChildren);
			if (queueList == null) {
				queueList = new LinkedList<QueuedCommand>();
			}
			if (!queueList.contains(queue)) {
				queueList.add(queue);
			}
			queuedCommands.put(numberOfChildren, queueList);
		}
	}

	private CommandMap getCommandMap() {
		if (commandMap == null) {
			if (plugin.getServer().getPluginManager() instanceof SimplePluginManager) {
				try {
					Object field = ReflectionUtils.getField(plugin.getServer()
							.getPluginManager(), "commandMap");
					commandMap = (SimpleCommandMap) field;
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return commandMap;
	}
}