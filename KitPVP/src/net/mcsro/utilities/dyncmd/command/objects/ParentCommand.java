package net.mcsro.utilities.dyncmd.command.objects;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ParentCommand {
	protected final Map<String, ChildCommand> childCommands = new ConcurrentHashMap<String, ChildCommand>();

	public void addChild(String s, ChildCommand child) {
		synchronized (childCommands) {
			childCommands.put(s.toLowerCase(), child);
		}
	}

	public boolean hasChild(String s) {
		synchronized (childCommands) {
			return childCommands.containsKey(s.toLowerCase());
		}
	}

	public ChildCommand getChild(String s) {
		synchronized (childCommands) {
			return childCommands.get(s.toLowerCase());
		}
	}

	public Map<String, ChildCommand> getChildCommands() {
		return childCommands;
	}
}