package mlbm.moreEMC.etc;

import java.util.ArrayList;
import java.util.List;

import mlbm.moreEMC.MoreEMC;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;

public class Command implements ICommand {
	private List aliases;
	
	public void Commands() {
		this.aliases = new ArrayList();
		this.aliases.add("memc");
	}
	
	@Override
	public int compareTo(Object arg0) {
		return 0;
	}

	//��ɾ��̸�
	@Override
	public String getCommandName() {
		return "memc";
	}
	//��ɾ� �����
	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		return "/memc <" + StatCollector.translateToLocal("cmd."+ MoreEMC.MODID + ".help") + ">";
	}

	//��ɾ� ����Ʈ
	@Override
	public List getCommandAliases() {
		return this.aliases;
	}
	
	//��ɾ� ó��
	@Override
	public void processCommand(ICommandSender icommandsender, String[] argString) { 
		if(icommandsender instanceof EntityPlayer) {//�÷��̾ ��ɾ ĥ���
			EntityPlayer player = (EntityPlayer) icommandsender;
			
			
			if( argString.length == 0) {
				player.addChatMessage(new ChatComponentTranslation("���ڰ� �Էµ��� �ʾҽ��ϴ�"));
			}else {
				//�Է�
			}
		}
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_) {
		return true;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_) {
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {
		return false;
	}
	
}
