package Trubby.co.th;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent.Status;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import Trubby.co.th.utils.ActionBars;

public class GTAGuns extends JavaPlugin implements Listener{

	public HashMap<UUID, Gun> guns = new HashMap<>();
	public static GTAGuns instance;
	ActionBars ab;
	
	@Override
	public void onEnable() {
		instance = this;
		Bukkit.getPluginManager().registerEvents(this, this);
		ab = new ActionBars();
	}
	
	
	/********************************************************************/
	@EventHandler
	public void onClick(PlayerInteractEvent e){
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
			//ab.sendActionBar(e.getPlayer(), ChatColor.RED + "TEST", 1);
			//e.getPlayer().setItemInHand(new ItemStack(Material.IRON_SPADE, 10));
			Player p = e.getPlayer();
			if(isGun(p.getItemInHand())){
				
				ItemStack is = p.getItemInHand();
				ItemMeta im;
				
				//Already Register Gun
				if(guns.containsKey(p.getUniqueId())){
					if(/*guns.get(p.getUniqueId()).is == is*/ true){
						//TODO SHOOT...
						
						Gun gun = guns.get(p.getUniqueId());
						gun.shoot(p);
						
					}else{
						//register gun
					}
				//Register gun
				}else{
					if(!is.hasItemMeta()){
						im = is.getItemMeta();
						im.setDisplayName("10/10");
						is.setItemMeta(im);
						
					}else{ //Manipulate gun
						Gun gun = new Gun(is, p);
						guns.put(p.getUniqueId(), gun);
						gun.shoot(p);
					}
				}
			}
		}
	}
	/***************************************************************/
	
	@EventHandler
	public void onSlotChange(PlayerItemHeldEvent e){
		Player p = e.getPlayer();
		if(guns.containsKey(p.getUniqueId())){
			guns.get(p.getUniqueId()).save();
			guns.remove(p.getUniqueId());
		}
	}
	
	/*@EventHandler
	public void onOpenInventory(InventoryClickEvent e){
		Player p = (Player) e.getWhoClicked();
		if(guns.containsKey(p.getUniqueId())){
			Bukkit.broadcastMessage("0");
			
			if(e.getSlot() == e.getWhoClicked().getInventory().getHeldItemSlot()){
				Bukkit.broadcastMessage("test1");
				e.setCancelled(true);
				guns.get(p.getUniqueId()).save();
				e.getInventory().setItem(e.getSlot(), guns.get(p.getUniqueId()).is);
				guns.remove(p.getUniqueId());
			}else{
				Bukkit.broadcastMessage("test2");
				guns.get(p.getUniqueId()).save();
				guns.remove(p.getUniqueId());
			}
		}
		
	}*/
	
	/*@EventHandler
	public void onOpenInventory(InventoryDragEvent e){
		Player p = (Player) e.getWhoClicked();
		if(guns.containsKey(p.getUniqueId())){
			Bukkit.broadcastMessage("test");
			e.setCancelled(true);
			
			guns.get(p.getUniqueId()).save();
			guns.remove(p.getUniqueId());
		}
	}*/
	
	
	
	/***************************************************************/
	
	@EventHandler
	public void onSnowballHit(EntityDamageByEntityEvent e){
		if(e.getDamager() instanceof Arrow){
			if(e.getEntity() instanceof LivingEntity){
				//Bukkit.broadcastMessage("test");
				Bukkit.getScheduler().runTaskLater(this, new Runnable() {
					
					@Override
					public void run() {
						LivingEntity le = (LivingEntity) e.getEntity();
						le.setNoDamageTicks(0);
						Bukkit.broadcastMessage("" + le.getNoDamageTicks() + " " + e.getEntityType());
					}
				}, 0L);
			}
		}
	}
	
	@EventHandler
	public void onArrowHit(ProjectileHitEvent e){
		if(e.getEntity() instanceof Arrow){
			e.getEntity().remove();
		}
	}
	
	public boolean isGun(ItemStack is){
		if(is.getType() == Material.IRON_PICKAXE){
			return true;
		}else{
			return false;
		}
	}
	
	@EventHandler
	public void onResourcePack(PlayerResourcePackStatusEvent e){
		if(e.getStatus() == Status.DECLINED){
			Bukkit.getScheduler().runTaskLater(this, new Runnable() {
				
				@Override
				public void run() {
					e.getPlayer().kickPlayer("โหลด Resource Pack ด้วยครับ");
					Bukkit.broadcastMessage("test");
				}
			}, 50L);
		}
	}
}
