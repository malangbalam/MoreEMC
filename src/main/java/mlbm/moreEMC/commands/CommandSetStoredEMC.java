package mlbm.moreEMC.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mlbm.moreEMC.eventhandler.BlockSelector;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MovingObjectPosition;

public class CommandSetStoredEMC extends CommandBase {
	public BlockSelector bs = new BlockSelector();
	
	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		return super.addTabCompletionOptions(sender, args);
	}

	@Override
	public String getCommandName() {
		return "setstoredemc";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "moreEMC.cmd.setstoredemc.usage";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if((args.length==1) && (args[0].equals("select"))){
			
		}else{
			showUsage(sender);
		}
	}
	
	private void showUsage(ICommandSender sender){
		sender.addChatMessage(new ChatComponentTranslation("commands.generic.syntax", new Object[]{}));
		sender.addChatMessage(new ChatComponentTranslation("moreEMC.cmd.setstoredemc.usage", new Object[]{}));
	}
}
