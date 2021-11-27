package net.mcsro.utilities.dyncmd.command.objects;

import java.lang.reflect.Method;

public class QueuedCommand {
	private final Object object;
	private final Method method;

	public QueuedCommand(Object object, Method method) {
		this.object = object;
		this.method = method;
	}

	public Object getObject() {
		return object;
	}

	public Method getMethod() {
		return method;
	}
}
