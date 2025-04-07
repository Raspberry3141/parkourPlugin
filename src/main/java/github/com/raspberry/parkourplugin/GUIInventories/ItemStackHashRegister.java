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
    public Yaml yaml = new Yaml();

    public enum ITMES {
        //TODO: change to an array
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

            HashMap<String,Object> courses = (HashMap<String, Object>) obj.get("CPURSES");
            lore = emptyLore;
            lore.add((String) courses.get("lore"));
            displayName = (String) courses.get("name");
            item = new ItemStack(Material.getMaterial((String) courses.get("material")),1);
            modifyMeta(ITMES.COURSES,item,displayName,lore);
            lore.clear();


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        lore = emptyLore;
        lore.add("Try out community 2p Parkour Courses!");
        item = new ItemStack(Material.SLIME_BALL  ,1);
        modifyMeta(ITMES.COMMUNITY,item,"§b§lCommunity", lore);
        lore.clear();

        lore = emptyLore;
        lore.add("Teleports to previous checkpoint");
        item = new ItemStack(Material.CARROT_STICK  ,1);
        modifyMeta(ITMES.PCP,item,"§e§lPCP Wand", lore);
        lore.clear();

        lore = emptyLore;
        lore.add("Puts you into practice mode");
        item = new ItemStack(Material.BEACON  ,1);
        modifyMeta(ITMES.PRAC,item,"§b§lToggle practice", lore);
        lore.clear();

        lore = emptyLore;
        lore.add("Puts you into fly mode");
        item = new ItemStack(Material.FEATHER  ,1);
        modifyMeta(ITMES.FLY,item,"§e§lToggle Fly", lore);
        lore.clear();

        lore = emptyLore;
        lore.add("Select Games to Play!");
        item = new ItemStack(Material.SUGAR_CANE  ,1);
        modifyMeta(ITMES.HUB,item,"§e§lGame Selector", lore);
        lore.clear();

        lore = emptyLore;
        item = new ItemStack(Material.STAINED_GLASS_PANE  ,1);
        modifyMeta(ITMES.OCCUPIED_SLOT,item," ", lore);
        lore.clear();

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
