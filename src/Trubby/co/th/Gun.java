package Trubby.co.th;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitTask;

public class Gun {
	
	public int ammo;
	public ItemStack is;
	public int maxAmmo = 10;
	public boolean reloading = false;
	public boolean fireRate = true;
	public boolean loaded = false;
	public Player p;
	public BukkitTask reloadTask;
	
	public Gun(ItemStack is, Player p) {
		this.is = is;
		this.p = p;
	}
	
	public void shoot(Player shooter){
		//reloading
		if(reloading == true || fireRate == false){
			return;
		}
		
		if(loaded == false){
			load();
			loaded = true;
		}
		
		//really shot
		if(ammo > 0){
			Arrow arrow = shooter.launchProjectile(Arrow.class, shooter.getLocation().getDirection().multiply(4));
			arrow.setKnockbackStrength(0);
			arrow.setCritical(true);
			
			
			ammo--;
			GTAGuns.instance.ab.sendActionBar(shooter, ChatColor.RED + "" + ammo + " / " + maxAmmo);
			shooter.playSound(shooter.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1f, 1f);
			
			//Multiple shot
			/*Bukkit.getScheduler().runTaskLater(GTAGuns.instance, new Runnable() {
				
				@Override
				public void run() {
					if(ammo > 0){
						Arrow arrow = shooter.launchProjectile(Arrow.class, shooter.getLocation().getDirection().multiply(2.5));
						arrow.setKnockbackStrength(0);
						arrow.setCritical(true);
						
						GTAGuns.instance.ab.sendActionBar(shooter, ChatColor.RED + "" + ammo + " / " + maxAmmo);
						ammo--;
						shooter.playSound(shooter.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1f, 1f);
					}
				}
			}, 5L);*/
			
			fireRate = false;
			
			Bukkit.getScheduler().runTaskLater(GTAGuns.instance, new Runnable() {
				
				@Override
				public void run() {
					fireRate = true;
				}
			}, 4L);
			
			//save
			/*ItemMeta im = is.getItemMeta();
			im.setDisplayName(ammo + "/" + maxAmmo);
			is.setItemMeta(im);*/
			
		}else{
			reload(shooter);
		}
	}
	
	public void reload(Player shooter){
		Bukkit.broadcastMessage("reload");
		reloading = true;
		GTAGuns.instance.ab.sendActionBar(shooter, ChatColor.RED + "" + ammo + " / " + maxAmmo);
		
		reloadTask = new ReloadTask(this).runTaskLater(GTAGuns.instance, 40l);
	}
	
	public void save(){
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ammo + "/" + maxAmmo);
		is.setItemMeta(im);
		
		if(reloadTask != null){
			reloadTask.cancel();
		}
	}
	
	public void load(){
		ItemMeta im = is.getItemMeta();
		
		if(!im.getDisplayName().contains("/")){
			im.setDisplayName("0/" + maxAmmo);
		}
		
		ammo = Integer.parseInt(im.getDisplayName().split("/")[0]);
		maxAmmo = Integer.parseInt(im.getDisplayName().split("/")[1]);
	}
	
}
