package net.mcsro.utilities;

import org.bukkit.ChatColor;

public class Scroller {

	String text;
	
	public Scroller(String string){
		this.text = string;
	}
	
	public String next(){
		String next = text;
		if(ChatColor.stripColor(next) == next){
			return next;
		} else {
			
		}
		
		return next;
	}
	
}
