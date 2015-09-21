package mlbm.moreEMC.commands;

import java.util.List;

import mlbm.moreEMC.utils.blockselector.BlockSelector;
import mlbm.moreEMC.utils.blockselector.BlockSelector.BlockSelectedListener;
import mlbm.moreEMC.utils.blockselector.WorldPosition;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentTranslation;

public class CommandSetStoredEMC extends CommandBase implements BlockSelectedListener {
	public BlockSelector bs = new BlockSelector(this);

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
		if (sender instanceof EntityPlayerMP) {
			if ((args.length == 1) && (args[0].equals("select"))) {
				sender.addChatMessage(new ChatComponentTranslation("moreEMC.cmd.select.help", new Object[] {}));
				bs.request((EntityPlayerMP) sender);
			} else if ((args.length == 2) && (args[0].equals("set"))) {
				int value = parseInt(sender, args[1]);
				WorldPosition selected = bs.getData((EntityPlayerMP) sender);
				if (selected != null) {
					// perform stuffs
				} else {
					sender.addChatMessage(
							new ChatComponentTranslation("moreEMC.cmd.selectionRequired", new Object[] {}));
				}
			} else {
				showUsage(sender);
			}
		}
	}

	private void showUsage(ICommandSender sender) {
		sender.addChatMessage(new ChatComponentTranslation("commands.generic.syntax", new Object[] {}));
		sender.addChatMessage(new ChatComponentTranslation("moreEMC.cmd.setstoredemc.usage", new Object[] {}));
	}

	@Override
	public void onBlockSelected(EntityPlayerMP player, WorldPosition position) {
		String dimid = position.world != null ? Integer.toString(position.world.provider.dimensionId) : "null";
		Object[] data = { Integer.toString(position.x), Integer.toString(position.y), Integer.toString(position.z),
				dimid };
		player.addChatMessage(new ChatComponentTranslation("moreEMC.cmd.selected", data));
	}
}
