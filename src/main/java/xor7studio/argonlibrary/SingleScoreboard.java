package xor7studio.argonlibrary;

import net.minecraft.network.packet.s2c.play.ScoreboardDisplayS2CPacket;
import net.minecraft.network.packet.s2c.play.ScoreboardObjectiveUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ScoreboardPlayerUpdateS2CPacket;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import xor7studio.util.Xor7IO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

// Develop by violetc

public class SingleScoreboard implements IScoreboard {

    private final ServerPlayerEntity player;
    private final String[] lines;
    private final String name;

    private String title;
    private int maxLine;
    private boolean isCreate;
    private boolean autoUpdate;

    private final List<String> needRemoveLine;
    private boolean needUpdateLine;
    private boolean needUpdateTitle;

    public SingleScoreboard(ServerPlayerEntity player) {
        this(player, "");
    }

    public SingleScoreboard(ServerPlayerEntity player, String title) {
        this(player, title, 12);
    }

    public SingleScoreboard(ServerPlayerEntity player, String title, int maxLine) {
        this.player = player;
        this.lines = new String[100];
        this.name = UUID.randomUUID().toString().substring(0, 5);
        this.autoUpdate = true;
        this.title = title;
        this.maxLine = maxLine;
        this.isCreate = false;
        this.needUpdateLine = false;
        this.needRemoveLine = new ArrayList<>();
        this.needUpdateTitle = false;

        update();
    }

    @Override
    public void update() {
        update(player);
    }

    @Override
    public void update(ServerPlayerEntity player) {
        if (!isCreate) {
            player.networkHandler.sendPacket(new ScoreboardObjectiveUpdateS2CPacket(
                    new ScoreboardObjective(null, name, null, Text.of(title), ScoreboardCriterion.RenderType.INTEGER),
                    ScoreboardObjectiveUpdateS2CPacket.ADD_MODE
            ));
            for (int i = 0; i < maxLine; i++) {
                player.networkHandler.sendPacket(new ScoreboardPlayerUpdateS2CPacket(
                        ServerScoreboard.UpdateMode.CHANGE,
                        name,
                        getRealLine(i),
                        i
                ));
            }
            isCreate = true;
        } else {
            if (needUpdateTitle) {
                player.networkHandler.sendPacket(new ScoreboardObjectiveUpdateS2CPacket(
                        new ScoreboardObjective(null, name, null, Text.of(title), ScoreboardCriterion.RenderType.INTEGER),
                        ScoreboardObjectiveUpdateS2CPacket.UPDATE_MODE
                ));
                needUpdateTitle = false;
            }
            if (needUpdateLine) {
                while (!needRemoveLine.isEmpty()) {
                    player.networkHandler.sendPacket(new ScoreboardPlayerUpdateS2CPacket(
                            ServerScoreboard.UpdateMode.REMOVE,
                            name,
                            needRemoveLine.remove(0),
                            0
                    ));
                }
                for (int i = 0; i < maxLine; i++) {
                    player.networkHandler.sendPacket(new ScoreboardPlayerUpdateS2CPacket(
                            ServerScoreboard.UpdateMode.CHANGE,
                            name,
                            getRealLine(i),
                            i
                    ));
                }
                needUpdateLine = false;
            }
        }
                    player.networkHandler.sendPacket(new ScoreboardDisplayS2CPacket(
                    1,
                    new ScoreboardObjective(null, name, null, Text.of(title), null)
            ));
    }

    private void updateLine(String old, int line) {
            player.networkHandler.sendPacket(new ScoreboardPlayerUpdateS2CPacket(
                    ServerScoreboard.UpdateMode.REMOVE,
                    name,
                    old,
                    line
            ));
        player.networkHandler.sendPacket(new ScoreboardPlayerUpdateS2CPacket(
                ServerScoreboard.UpdateMode.CHANGE,
                name,
                getRealLine(line),
                line
        ));
    }
    private void updateTitle() {
        player.networkHandler.sendPacket(new ScoreboardObjectiveUpdateS2CPacket(
                new ScoreboardObjective(null, name, null, Text.of(title), ScoreboardCriterion.RenderType.INTEGER),
                ScoreboardObjectiveUpdateS2CPacket.UPDATE_MODE
        ));
    }
    
    private void getRealLine(int line) {
        return "§a§r".repeat(line) + Objects.requireNonNullElse(lines[line, ""]);
    }

    @Override
    public void setAutoUpdate(boolean is) {
        this.autoUpdate = is;
    }

    @Override
    public void setLine(int line, String newLine) {
        String old = getRealLine(line);
        lines[line] = newLine;
        if (autoUpdate) {
            updateLine(old, line);
        } else {
            needUpdateLine = true;
            needRemoveLine.add(old);
        }
    }

    @Override
    public void setMaxLine(int max) {
        this.maxLine = max;
        update();
    }

    @Override
    public String getLine(int line) {
        return lines[line];
    }

    @Override
    public int getMaxLine() {
        return maxLine;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
        if (autoUpdate) {
            updateTitle();
        } else {
            needUpdateTitle = true;
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void addAutoSendPlayer(ServerPlayerEntity player) {
        throw new IllegalStateException("Not support");
    }

    @Override
    public void removeAutoSendPlayer(ServerPlayerEntity player) {
        throw new IllegalStateException("Not support");
    }

    @Override
    public void clearAutoSendPlayer() {
        throw new IllegalStateException("Not support");
    }
}
