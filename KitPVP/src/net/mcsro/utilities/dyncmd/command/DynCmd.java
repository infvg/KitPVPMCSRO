package net.mcsro.utilities.dyncmd.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DynCmd {
	String command();

	String[] aliases() default {};

	String permission() default "";

	String noPermission() default "You don't have permission to do that.";

	String usage() default "";

	String description() default "";

	int min() default 0;

	int max() default -1;

	boolean playerOnly() default false;
}