package xor7studio.argonlibrary;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import xor7studio.argonlibrary.scoreboard.ScoreboardHandler;

import java.util.ArrayList;
import java.util.List;

public class Scoreboard {
    private List<String> objectives;
    private Integer size;
    public String name;
    private ScoreboardHandler scoreboardHandler;
    private List<ServerPlayerEntity> players;
    public Scoreboard(String name){init(0,name);}
    public Scoreboard(int size, String name){init(size,name);}
    private void init(int size,String name){
        this.size=size;
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
        scoreboardHandler.currentTitle=name;
        scoreboardHandler.update(players);
    }
}
