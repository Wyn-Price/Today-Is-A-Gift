package com.wynprice.todayisagift;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.IClientCommand;

public class TIAGCommand implements IClientCommand
{

	@Override
	public String getName() {
		return "holiday";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/holiday " + (TIAGConfig.commandFormat == 0 ? "<day>/<month>" : "<month>/<day>");
	}

	@Override
	public List<String> getAliases() {
		return Lists.newArrayList();
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(args.length < 1 || args[0].split("/").length < 2) {
			throw new WrongUsageException("commands.tiag.format" + (TIAGConfig.commandFormat == 1 ? 1 : 0) + ".usage");
		}
		
		int month = CommandBase.parseInt(args[0].split("/")[1]);
		int day = CommandBase.parseInt(args[0].split("/")[0]);
		
		if(TIAGConfig.commandFormat == 1) {
			int oldMonth = month;
			month = day;
			day = oldMonth;
		} 
		Minecraft.getMinecraft().player.sendStatusMessage(new TextComponentTranslation("tiag.happy", TodayIsAGift.getText(month, day, new Random().nextLong())), false);
		
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return true;
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
			BlockPos targetPos) {
		if(args.length > 0) {
			Calendar calendar = Calendar.getInstance();
			int month = calendar.get(Calendar.MONTH) + 1;
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			String output;
			if(TIAGConfig.commandFormat == 1) {
				output = month + "/" + day;
			} else {
				output = day + "/" + month;
			}
			return Lists.newArrayList(output);
		}
		return Lists.newArrayList();
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return false;
	}

	@Override
	public int compareTo(ICommand arg0) {
		return getName().compareTo(arg0.getName());
	}

	@Override
	public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
		return false;
	}

}
