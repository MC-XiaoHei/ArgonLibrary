package xor7studio.argonlibrary;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.Scoreboard;
import org.lwjgl.system.CallbackI;
import xor7studio.argonlibrary.scoreboard.ScoreboardHandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ScoreboardManager {
    private List<String> objectives;
    private Integer size;
    private String name;
    private ScoreboardHandler scoreboardHandler;
    public ScoreboardManager(String name){init(0,name);}
    public ScoreboardManager(int size, String name){init(size,name);}
    private void init(int size,String name){
        objectives = new ArrayList<>();
        for(int i=0;i<size;i++)objectives.add("");
        scoreboardHandler=ScoreboardHandler.INSTANCE;
        scoreboardHandler.lastContent=objectives;
    }
    public void openScoreboard(PlayerEntity player){
        scoreboardHandler.init(player);
    }
    public void resize(int resized){
        List<String> newList=new ArrayList<>();
        for(int i=0;i<size;i++)newList.add(objectives.get(i));
        objectives=newList;
    }
}
