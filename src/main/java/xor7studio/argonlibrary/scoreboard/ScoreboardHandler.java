package xor7studio.argonlibrary.scoreboard;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.ScoreboardDisplayS2CPacket;
import net.minecraft.network.packet.s2c.play.ScoreboardObjectiveUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ScoreboardPlayerUpdateS2CPacket;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class ScoreboardHandler {
    public static ScoreboardHandler INSTANCE = new ScoreboardHandler();
    public final List<String> currentContent = new ArrayList<>(1);
    public List<String> lastContent = new ArrayList<>(1);
    public String currentTitle = "";
    private String lastTitle = "";
    private String currentDisplayName = "HGScoreboard";
    private Unsafe unsafe;

    private ScoreboardHandler() {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        PacketSendEvent.INSTANCE.register((playerEntity, packet) -> {
            if (packet instanceof IScoreboardS2CPacket) {
                if (!((IScoreboardS2CPacket) packet).hg_isHgGamePacket()) {
                    return ActionResult.FAIL;
                }
            }
            return ActionResult.PASS;
        });
    }
    public void update(List<ServerPlayerEntity> entities) {
        // 保存老计分板内部名称
        String storeName = currentDisplayName;
        // 重新设置计分板内部名称
        currentDisplayName = UUID.randomUUID().toString().substring(0, 6);
        //更新计分板
        lastTitle = currentTitle;
        lastContent.clear();
        lastContent.addAll(currentContent);
        for(ServerPlayerEntity entity:entities) sendUpdate(entity,storeName);
    }
    private void sendUpdate(ServerPlayerEntity entity, String oldName) {
        try {
            // 创建计分板
            IScoreboardObjectiveUpdateS2CPacket packet =
                    (IScoreboardObjectiveUpdateS2CPacket) (unsafe.allocateInstance(ScoreboardObjectiveUpdateS2CPacket.class));
            packet.hg_setName(currentDisplayName);
            packet.hg_setDisplayName(new LiteralText(lastTitle));
            packet.hg_setType(ScoreboardCriterion.RenderType.INTEGER);
            packet.hg_setMode(0);
            packet.hg_setHgGamePacket(true);
            entity.networkHandler.sendPacket((Packet<?>) packet);
            // 推送计分板
            for (int i = lastContent.size() - 1; i >= 0; i--) {
                IScoreboardPlayerUpdateS2CPacket packet2 =
                        (IScoreboardPlayerUpdateS2CPacket) (unsafe.allocateInstance(ScoreboardPlayerUpdateS2CPacket.class));
                packet2.hg_setPlayerName(lastContent.get(i));
                packet2.hg_setScore(lastContent.size() - i);
                packet2.hg_setObjectiveName(currentDisplayName);
                packet2.hg_setMode(ServerScoreboard.UpdateMode.CHANGE);
                packet2.hg_setHgGamePacket(true);
                entity.networkHandler.sendPacket((Packet<?>) packet2);
            }
            //移除老的计分板
            if (oldName != null) {
                IScoreboardObjectiveUpdateS2CPacket packet4 =
                        (IScoreboardObjectiveUpdateS2CPacket) unsafe.allocateInstance(ScoreboardObjectiveUpdateS2CPacket.class);
                packet4.hg_setName(oldName);
                packet4.hg_setDisplayName(new LiteralText(lastTitle));
                packet4.hg_setType(ScoreboardCriterion.RenderType.INTEGER);
                packet4.hg_setMode(1);
                packet4.hg_setHgGamePacket(true);
                entity.networkHandler.sendPacket((Packet<?>) packet4);
            }
            // 设置显示位置
            IScoreboardDisplayS2CPacket packet1 =
                    (IScoreboardDisplayS2CPacket) unsafe.allocateInstance(ScoreboardDisplayS2CPacket.class);
            packet1.hg_setSlot(1);
            packet1.hg_setName(currentDisplayName);
            packet1.hg_setHgGamePacket(true);
            entity.networkHandler.sendPacket((Packet<?>) packet1);
        } catch (Exception exception) {
            new RuntimeException("尝试更新计分板时出现异常", exception).printStackTrace();
        }
    }
    public void init(PlayerEntity entity){
        sendUpdate((ServerPlayerEntity) entity, null);
    }
}