package github.com.raspberry.parkourplugin.tests;

import github.com.raspberry.parkourplugin.GUIInventories.InventoryList;
import github.com.raspberry.parkourplugin.GUIInventories.ItemStackHashRegister;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.yaml.snakeyaml.Yaml;

//This is not an actual test, rather a trigger for features for testing purpose
public class runTest implements CommandExecutor {
    ItemStackHashRegister factory;
    InventoryList invlst;
    Yaml yaml = new Yaml();
    public runTest(ItemStackHashRegister itemfactory, InventoryList invls) {
        factory = itemfactory;
        invlst =invls;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        factory.createList();
        invlst.initializeInventories();
        Player player = (Player) commandSender;
        player.getInventory().addItem(factory.SpecialItems.get(ItemStackHashRegister.ITMES.HUB));
        player.getInventory().addItem(factory.SpecialItems.get(ItemStackHashRegister.ITMES.PRAC));
        player.getInventory().addItem(factory.SpecialItems.get(ItemStackHashRegister.ITMES.PCP));
        player.getInventory().addItem(factory.SpecialItems.get(ItemStackHashRegister.ITMES.FLY));

        //FileInputStream inputStream = null;
        //try {
        //    inputStream = new FileInputStream("C:\\Users\\masan\\IdeaProjects\\parkourPlugin\\src\\main\\java\\github\\com\\raspberry\\parkourplugin\\playerItemsManager\\hi.yaml");
        //    Map<String, Object> obj = yaml.load(inputStream);
        //    HashMap<String, Object> age = (HashMap<String, Object>) obj.get("age");
        //    ArrayList<String> greets = (ArrayList<String>) obj.get("greetings");
        //    System.out.println(age.get("1st"));
        //    System.out.printf(greets.get(0));
        //
        //} catch (FileNotFoundException e) {
        //    throw new RuntimeException(e);
        //}
        //
        //DumperOptions options = new DumperOptions();
        //options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        //Yaml newYaml = new Yaml(options);
        //Map<String, Object> dataToWrite = new HashMap<>();
        //dataToWrite.put("said","hi");
        //Map<String, Object> nested = new HashMap<>();
        //nested.put("lowestnest", 1);
        //dataToWrite.put("nested map",nested);
        //
        //try (FileWriter fileWriter = new FileWriter("C:\\Users\\masan\\IdeaProjects\\parkourPlugin\\src\\main\\java\\github\\com\\raspberry\\parkourplugin\\playerItemsManager\\hi.yaml")) {
        //    yaml.dump(dataToWrite,fileWriter);
        //} catch (IOException io) {
        //    io.printStackTrace();
        //}


        return true;
    }
}
