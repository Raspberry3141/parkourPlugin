package github.com.raspberry.parkourplugin.GUIInventories;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemStackHashRegister {
    public HashMap<ITMES,ItemStack> SpecialItems = new HashMap<ITMES,ItemStack>();
    Yaml yaml = new Yaml();

    public enum ITMES {
        RANKUP,
        COURSES,
        COMMUNITY,
        PCP,
        PRAC,
        FLY,
        HUB,
        OCCUPIED_SLOT
    }
    private final List<String> emptyLore = new ArrayList<String>();
    ItemStack item;
    List<String> lore;
    String displayName;

    public ItemStackHashRegister() {
    }

    public void createList() {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream("C:\\Database\\Items.yml");
            Map<String,Object> obj = yaml.load(inputStream);

            HashMap<String,Object> rankup = (HashMap<String, Object>) obj.get("RANKUP");
            lore = emptyLore;
            lore.add((String) rankup.get("lore"));
            displayName = (String) rankup.get("name");
            item = new ItemStack(Material.getMaterial((String) rankup.get("material")),1);
            modifyMeta(ITMES.RANKUP,item,displayName,lore);
            lore.clear();

            HashMap<String,Object> courses = (HashMap<String, Object>) obj.get("COURSES");
            lore = emptyLore;
            lore.add((String) courses.get("lore"));
            displayName = (String) courses.get("name");
            item = new ItemStack(Material.getMaterial((String) courses.get("material")),1);
            modifyMeta(ITMES.COURSES,item,displayName,lore);
            lore.clear();

            HashMap<String,Object> community = (HashMap<String, Object>) obj.get("COMMUNITY");
            lore = emptyLore;
            lore.add((String) community.get("lore"));
            displayName = (String) community.get("name");
            item = new ItemStack(Material.getMaterial((String) community.get("material")),1);
            modifyMeta(ITMES.COMMUNITY,item,displayName,lore);
            lore.clear();

            HashMap<String,Object> pcp = (HashMap<String, Object>) obj.get("PCP");
            lore = emptyLore;
            lore.add((String) pcp.get("lore"));
            displayName = (String) pcp.get("name");
            item = new ItemStack(Material.getMaterial((String) pcp.get("material")),1);
            modifyMeta(ITMES.PCP,item,displayName,lore);
            lore.clear();

            HashMap<String,Object> prac = (HashMap<String, Object>) obj.get("PRAC");
            lore = emptyLore;
            lore.add((String) prac.get("lore"));
            displayName = (String) prac.get("name");
            item = new ItemStack(Material.getMaterial((String) prac.get("material")),1);
            modifyMeta(ITMES.PRAC,item,displayName,lore);
            lore.clear();

            HashMap<String,Object> fly = (HashMap<String, Object>) obj.get("FLY");
            lore = emptyLore;
            lore.add((String) fly.get("lore"));
            displayName = (String) fly.get("name");
            item = new ItemStack(Material.getMaterial((String) fly.get("material")),1);
            modifyMeta(ITMES.FLY,item,displayName,lore);
            lore.clear();

            HashMap<String,Object> selector = (HashMap<String, Object>) obj.get("SUGAR_CANE");
            lore = emptyLore;
            lore.add((String) selector.get("lore"));
            displayName = (String) selector.get("name");
            item = new ItemStack(Material.getMaterial((String) selector.get("material")),1);
            modifyMeta(ITMES.HUB,item,displayName,lore);
            lore.clear();

            HashMap<String,Object> none = (HashMap<String, Object>) obj.get("OCCUPIED_SLOT");
            lore = emptyLore;
            lore.add((String) none.get("lore"));
            displayName = (String) none.get("name");
            item = new ItemStack(Material.getMaterial((String) none.get("material")),1);
            modifyMeta(ITMES.OCCUPIED_SLOT,item,displayName,lore);
            lore.clear();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    private void modifyMeta(ITMES place, ItemStack item, String itemDisplayName, List<String> lore) {
        ItemMeta itemmeta = item.getItemMeta();
        itemmeta.setDisplayName(itemDisplayName);
        itemmeta.setLore(lore);
        itemmeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,1,true);
        itemmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(itemmeta);
        addItemToList(item,place);
    }

    private void addItemToList(ItemStack item, ITMES place) {
        SpecialItems.put(place,item);
    }

}
