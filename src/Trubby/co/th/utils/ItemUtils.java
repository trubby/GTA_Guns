package Trubby.co.th.utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtils {

	public static ItemMeta getMeta(ItemStack is){
		if(is.hasItemMeta()){
			return is.getItemMeta();
		}else{
			return null;
		}
	}
	
}