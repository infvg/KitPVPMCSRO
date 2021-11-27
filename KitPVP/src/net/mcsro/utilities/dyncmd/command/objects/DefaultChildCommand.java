package net.mcsro.utilities.dyncmd.command.objects;


public class DefaultChildCommand extends ChildCommand {
	public DefaultChildCommand(String command) {
		super(null);
		this.command = command;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}
}