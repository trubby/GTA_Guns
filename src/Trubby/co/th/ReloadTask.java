package Trubby.co.th;

import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class ReloadTask extends BukkitRunnable{

	Gun gun;
	
	public ReloadTask(Gun gun) {
		this.gun = gun;
	}
	
	@Override
	public void run() {
		//TODO consume ammo
		gun.ammo = gun.maxAmmo;
		GTAGuns.instance.ab.sendActionBar(gun.p, ChatColor.RED + "" + gun.ammo + " / " + gun.maxAmmo);
		
		gun.reloading = false;
	}

}
