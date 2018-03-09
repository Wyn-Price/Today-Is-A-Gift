package com.wynprice.todayisagift;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.Language;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid=TodayIsAGift.MODID, name=TodayIsAGift.MODNAME, version = TodayIsAGift.VERSION, clientSideOnly=true)
@EventBusSubscriber
public class TodayIsAGift {
	public static final String MODID = "todayisagift";
	public static final String MODNAME = "Today Is A Gift";
	public static final String VERSION = "1.0.0";
	
	@EventHandler
	public void postinit(FMLServerStartingEvent event) {
		event.registerServerCommand(new TIAGCommand());
	}
	
	private static final HashMap<Language, YearFile> CACHE = new HashMap<>();
	
	private static long currentLong = 0L;
	
	@SubscribeEvent
	public static void onGuiOpenEvent(GuiOpenEvent event) {
		if(event.getGui() instanceof GuiMainMenu && TIAGConfig.enabled) {
			currentLong = new Random().nextLong();
		}
	}
	
	@SubscribeEvent
	public static void onGuiScreenEvent(GuiScreenEvent event) {
		if(event.getGui() instanceof GuiMainMenu && TIAGConfig.enabled) {
			Calendar calendar = Calendar.getInstance(); 
			String text = I18n.format("tiag.happy", getText(calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH), currentLong));
			FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
			event.getGui().drawRect(event.getGui().width / 2 - fr.getStringWidth(text) / 2 - 5 + TIAGConfig.posX, TIAGConfig.posY, event.getGui().width / 2 + fr.getStringWidth(text) / 2 + 5 + TIAGConfig.posX, TIAGConfig.posY + 14, TIAGConfig.backgroundColor);
			event.getGui().drawCenteredString(Minecraft.getMinecraft().fontRenderer, text, event.getGui().width / 2 + TIAGConfig.posX, TIAGConfig.posY + 3, TIAGConfig.forgroundColor);
		}
	}
	
	public static String getText(int month, int day, long rand) {
		try {
			Language currentLang = Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage();
			if(!CACHE.containsKey(currentLang)) {
				InputStream stream = TodayIsAGift.class.getResourceAsStream("/assets/todayisagift/textfiles/" + currentLang.getLanguageCode() + ".txt");
				if(stream == null) {
					stream = TodayIsAGift.class.getResourceAsStream("/assets/todayisagift/textfiles/en_us.txt");
				}
				CACHE.put(currentLang, new YearFile(CharStreams.toString(new InputStreamReader(stream, Charsets.UTF_8))));
			}
			return CACHE.get(currentLang).getToday(month, day, rand);
		} catch (Exception e) {
			return "Error: " + e.getMessage();
		}
	}
}
