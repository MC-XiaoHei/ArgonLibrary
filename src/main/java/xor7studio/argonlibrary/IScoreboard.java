package xor7studio.argonlibrary;

import net.minecraft.entity.player.PlayerEntity;

public interface IScoreboard {
  
  public void update();
  
  public void update(PlayerEntity player);
  
  public void setAutoUpdate(boolean is);
  
  public void addAutoSendPlayer(PlayerEntity player);

  public void removeAutoSendPlayer(PlayerEntity player);
  
  public void clearAutoSendPlayer();
  
  public void setLine(int line, String name);
  
  public void setMaxLine(int max);
  
  public String getLine(int line);
  
  public int getMaxLine();
  
  public String getTtile();
  
  public void setTitle(String title);
}