package net.mcsro.utilities;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class Infventory {
	private int size;
	private Player owner;
	private String name;
	private InventoryType type;
	private Inventory inv;
	public Infventory(Player owner, int size,String name){
		this.size = size;
		this.owner = owner;
		
		
		type = InventoryType.CHEST;
		this.name = name;
		this.inv = Bukkit.createInventory(owner, size,name);
	}
	public Infventory(Player owner, int size){
		this.size = size;
		this.owner = owner;
		
		type = InventoryType.CHEST;
		this.inv = Bukkit.createInventory(owner, size);
	}
	public Infventory(Player owner, InventoryType type, String name){
		this.owner = owner;
		this.type = type;
		this.inv = Bukkit.createInventory(owner, type,name);
	}
	public Infventory(Player owner, InventoryType type){
		this.owner = owner;
		this.type = type;
		this.inv = Bukkit.createInventory(owner, type);
	}
	public Infventory fill(ItemStack mat){
		for(int i = 0; i > size; i++){
			inv.setItem(i, mat);
		}
		return this;
	}
		public Infventory fill(ItemBuilder mat){
			for(int i = 0; i > size; i++){
				inv.setItem(i, mat.build());
			}
			return this;
	}
		public Infventory fill(Material mat){
			for(int i = 0; i > size; i++){
				inv.setItem(i, new ItemBuilder(mat,1).build());
			}
			return this;
		}
			public Infventory fill(Material mat, short s){
				for(int i = 0; i > size; i++){
					inv.setItem(i, new ItemBuilder(mat,1,(short) s).build());
				}
				return this;
	}
		public Infventory fill(Material mat, int quantity, short s){
				for(int i = 0; i > size; i++){
					inv.setItem(i, new ItemBuilder(mat,quantity,(short) s).build());
				}
				return this;
	}
		public Infventory fill(Material mat, int quantity){
			for(int i = 0; i > size; i++){
				inv.setItem(i, new ItemBuilder(mat,quantity).build());
			}
			return this;
}
		public Infventory setItem(int location,ItemStack stack){
			inv.setItem(location, stack);
			return this;
		}
		public Infventory setItem(int location,ItemBuilder stack){
			inv.setItem(location, stack.build());
			return this;
		}
			public Infventory setItem(int i,Material mat, short s){
					inv.setItem(i, new ItemBuilder(mat,1,(short) s).build());
					return this;
	}
		public Infventory setItem(int i,Material mat, int quantity, short s){
					inv.setItem(i, new ItemBuilder(mat,quantity,(short) s).build());
					return this;
	}
		public Infventory setItem(int i,Material mat, int quantity){
				inv.setItem(i, new ItemBuilder(mat,quantity).build());
				return this;
			}
		public Infventory setItem(int i,Material mat){
			inv.setItem(i, new ItemBuilder(mat,1).build());
			return this;
		}
		public Infventory addItem(ItemStack stack){
			inv.addItem(stack);
			return this;
		}
		public Infventory addItem(ItemBuilder stack){
			inv.addItem(stack.build());
			return this;
		}
			public Infventory addItem(Material mat, short s){
					inv.addItem(new ItemBuilder(mat,1,(short) s).build());
					return this;
				
	}
		public Infventory addItem(Material mat, int quantity, short s){
					inv.addItem(new ItemBuilder(mat,quantity,(short) s).build());
					return this;
				
	}
		public Infventory addItem(Material mat, int quantity){
				inv.addItem(new ItemBuilder(mat,quantity).build());
				return this;
			}
		public Infventory addItem(Material mat){
			inv.addItem( new ItemBuilder(mat,1).build());
			return this;
		}
		public Infventory clear() {
			inv.clear();
			return this;
		}
		public Infventory clear(int i) {
			inv.clear(i);
			return this;
		}
		@SuppressWarnings("deprecation")
		public boolean contains(int i) {
			return inv.contains(i);
		}
		public boolean contains(Material mat) throws IllegalArgumentException {
			return inv.contains(mat);
		}
		public boolean contains(ItemStack stack) {
			return inv.contains(stack);
		}
		@SuppressWarnings("deprecation")
		public boolean contains(int i, int i1) {
			return inv.contains(i,i1);
		}
		public boolean contains(Material arg0, int arg1)
				throws IllegalArgumentException {
			return inv.contains(arg0, arg1);
		}
		public boolean contains(ItemStack arg0, int arg1) {
			return inv.contains(arg0, arg1);
		}
		public boolean containsAtLeast(ItemStack stack, int i) {
			return inv.containsAtLeast(stack, i);
		}
		@SuppressWarnings("deprecation")
		public int first(int i) {
			return inv.first(i);
		}
		public int first(Material mat) throws IllegalArgumentException {
			return inv.first(mat);
		}
		public int first(ItemStack stack) {
			return inv.first(stack);
		}
		public int firstEmpty() {
			return inv.firstEmpty();
		}
		public ItemStack[] getContents() {
			return inv.getContents();
		}
		public InventoryHolder getHolder() {
			return inv.getHolder();
		}
		public ItemStack getItem(int i) {
			return inv.getItem(i);
		}
		public String getName() {
			return name;
		}
		public int getSize() {
			return size;
		}
		public String getTitle() {
			return name;
		}
		public InventoryType getType() {
			return type;
		}
		public Infventory remove(Material mat) throws IllegalArgumentException {
			inv.remove(mat);
			return this;
			
		}
		public Infventory remove(ItemStack arg0) {
			inv.remove(arg0);
			return this;
		}
		public Infventory setContents(ItemStack[] arg0)
				throws IllegalArgumentException {
			inv.setContents(arg0);
			return this;
		}
		public Player getOwner() {
			return owner;
		}
		public Inventory build(){
			return inv;
		}
}
