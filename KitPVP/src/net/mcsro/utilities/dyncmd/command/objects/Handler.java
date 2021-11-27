package net.mcsro.utilities.dyncmd.command.objects;


public interface Handler {
	void handleCommand(CommandInfo info) throws Exception;
}