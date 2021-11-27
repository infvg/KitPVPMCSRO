package net.mcsro.kitpvp.utils;

import net.mcsro.kitpvp.KitPVP;
import net.mcsro.kitpvp.kits.Kit;
import net.mcsro.kitpvp.kits.Slapper;
import net.mcsro.utilities.Infventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BuyingUtils implements Listener {
	
	private static ItemStack no = new ItemBuilder(Material.WOOL, 1,(short) 14)
			.setTitle("§c§lCANCEL").build();
	private static ItemStack yes = new ItemBuilder(Material.WOOL,1, (short) 5)
			.setTitle("§c§lCONFIRM").build();
	private static Inventory inv;
	
	private static void buildInv(){
		no = new ItemBuilder(Material.WOOL, 1,(short) 14)
		.setTitle("§c§lCANCEL").build();
		yes = new ItemBuilder(Material.WOOL, 1, (short) 5)
		.setTitle("§c§lCONFIRM").build();
		inv = new Infventory(null, 45, "§aConfirmation").build();

		inv.setItem(18, yes);inv.setItem(19, yes);inv.setItem(20, yes);
		inv.setItem(36, yes);inv.setItem(37, yes);inv.setItem(38, yes);
		inv.setItem(27, yes);inv.setItem(28, yes);inv.setItem(29, yes);
		inv.setItem(24, no);inv.setItem(25, no);inv.setItem(26, no);
		inv.setItem(33, no);inv.setItem(34, no);inv.setItem(35, no);
		inv.setItem(42, no);inv.setItem(43, no);inv.setItem(44, no);
	}

	public static void confirm(Player p, Class<? extends Kit> kit) {
		buildInv();
		inv.clear();
		buildInv();
		inv.clear();
		if (kit.equals(Slapper.class)) {
			inv.setItem(4, new ItemBuilder(Material.RAW_FISH, 1, (short) 0)
					.setTitle("&9&lSLAPPER &7▪&6 50 Coins").build());
			p.openInventory(inv);
		}
	}

	@EventHandler
	public void onInteract(InventoryClickEvent e) {
		if (e.getInventory().equals(inv)) {
			if (e.getCurrentItem() != null) {
				if (e.getCurrentItem().equals(yes)) {
					if (e.getInventory().getItem(4).getType() == Material.RAW_FISH) {
						KitPVP.buy((Player) e.getWhoClicked(), new Slapper());
					}

				} else if (e.getCurrentItem().equals(no)) {
					e.getWhoClicked().closeInventory();
				}
			}
			e.setCancelled(true);
		}
	}

}
