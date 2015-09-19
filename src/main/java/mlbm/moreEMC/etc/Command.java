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

	//명령어이름
	@Override
	public String getCommandName() {
		return "memc";
	}
	//명령어 사용방법
	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		return "/memc <" + StatCollector.translateToLocal("cmd."+ MoreEMC.MODID + ".help") + ">";
	}

	//명령어 리스트
	@Override
	public List getCommandAliases() {
		return this.aliases;
	}
	
	//명령어 처리
	@Override
	public void processCommand(ICommandSender icommandsender, String[] argString) { 
		if(icommandsender instanceof EntityPlayer) {//플레이어가 명령어를 칠경우
			EntityPlayer player = (EntityPlayer) icommandsender;
			
			
			if( argString.length == 0) {
				player.addChatMessage(new ChatComponentTranslation("인자가 입력되지 않았습니다"));
			}else {
				//입력
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
