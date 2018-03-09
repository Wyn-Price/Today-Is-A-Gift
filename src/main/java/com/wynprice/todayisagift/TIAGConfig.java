package com.wynprice.todayisagift;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid=TodayIsAGift.MODID)
@EventBusSubscriber(modid=TodayIsAGift.MODID)
public class TIAGConfig {
	
	public static boolean enabled = true;
	
	@Config.Comment("Where the Middle of the text box is, distance from the center of the screen.\nSetting this to 0 means it will be in the center of the screen")
	public static int posX = 0;
	
	@Config.Comment("Where the Top of the text box is.")
	public static int posY = 8;
	
	@Config.Comment("The color of the text box on the Main Menu")
	public static int backgroundColor = -1072689136;
	
	@Config.Comment("The color of the text in the main menu")
	public static int forgroundColor = 16777215;
	
	@Config.Comment("The format of the command\nUse 0 for day/month (Everywhere apart from US)\nUse 1 for month/day (US)")
	public static int commandFormat = 0;
	
	@SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if(event.getConfigID().equals(TodayIsAGift.MODID)) {
            ConfigManager.sync(TodayIsAGift.MODID, Config.Type.INSTANCE);
        }
    }
}
