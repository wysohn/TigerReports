package fr.mrtigreroux.tigerreports.objects.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.mrtigreroux.tigerreports.data.ConfigSound;
import fr.mrtigreroux.tigerreports.data.MenuItem;
import fr.mrtigreroux.tigerreports.objects.CustomItem;
import fr.mrtigreroux.tigerreports.objects.Report;
import fr.mrtigreroux.tigerreports.objects.User;

/**
 * @author MrTigreroux
 */

public abstract class Menu {

	protected User u;
	protected Player p;
	protected int size;
	protected int page;
	protected Report r;
	protected String action;
	protected String target;
	
	public Menu(User u, int size, int page, Report r, String action, String target) {
		this.u = u;
		this.p = u.getPlayer();
		this.size = size;
		this.page = page;
		this.r = r;
		this.action = action;
		this.target = target;
	}
	
	public Inventory getInventory(String title, boolean borders) {
		if(title.length() > 32) title = title.substring(0, 29)+"..";
		Inventory inv = Bukkit.createInventory(null, size, title);
		if(borders) {
			ItemStack gui = new CustomItem().type(Material.STAINED_GLASS_PANE).damage((byte) 7).name("").create();
			int size = inv.getSize();
			for(int position = 9; position <= 17; position++) inv.setItem(position, gui);
			for(int position = size-9; position <= size-1; position++) inv.setItem(position, gui);
			inv.setItem(size-5, MenuItem.CLOSE.get());
		}
		return inv;
	}
	
	public void click(ItemStack item, int slot, ClickType click) {
		if(slot == -1 || item == null || item.getType() == Material.AIR || (item.getType() == Material.STAINED_GLASS_PANE && ((slot >= size-9 && slot <= size-1) || (slot >= 9 && slot <= 17)))) return;
		if(slot == size-5 && item.isSimilar(MenuItem.CLOSE.get())) {
			p.closeInventory();
			u.playSound(ConfigSound.MENU.get());
			return;
		}
		if(page != 0) {
			int newPage = slot == size-7 && item.isSimilar(MenuItem.PAGE_SWITCH_PREVIOUS.get()) ? page-1 : slot == size-3 && item.isSimilar(MenuItem.PAGE_SWITCH_NEXT.get()) ? page+1 : 0;
			if(newPage != 0) {
				this.page = newPage;
				open(true);
				return;
			}
		}
		onClick(item, slot, click);
	}
	
	public abstract void open(boolean sound);
	public abstract void onClick(ItemStack item, int slot, ClickType click);
	
}
