package xor7studio.argonlibrary;

import net.minecraft.server.network.ServerPlayerEntity;

public interface IScoreboard {
  void update();
  void update(ServerPlayerEntity player);
  void setAutoUpdate(boolean is);
  void addAutoSendPlayer(ServerPlayerEntity player);
  void removeAutoSendPlayer(ServerPlayerEntity player);
  void clearAutoSendPlayer();
  void setLine(int line, String name);
  void setMaxLine(int max);
  String getLine(int line);
  int getMaxLine();
  String getTitle();
  void setTitle(String title);
  String getName();
}
