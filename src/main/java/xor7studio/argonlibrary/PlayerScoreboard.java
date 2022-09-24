package xor7studio.argonlibrary;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import xor7studio.argonlibrary.scoreboard.ScoreboardHandler;

import java.util.ArrayList;
import java.util.List;

public class PlayerScoreboard {
    private List<String> objectives;
    private Integer size;
    private String name;
    private ScoreboardHandler scoreboardHandler;
    private List<ServerPlayerEntity> players;
    public PlayerScoreboard(String name){init(0,name);}
    public PlayerScoreboard(int size, String name){init(size,name);}
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
        update();
    }
    public void setLine(int line,String data){
        if(line>size)
            resize(line);
        objectives.set(line,data);
        update();
    }
    public void update(){
        scoreboardHandler.update(players);
    }
}
