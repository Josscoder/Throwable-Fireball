package jossc.throwable.fireball.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemID;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import jossc.throwable.fireball.entity.EntityFireBall;

public class EventListener implements Listener {

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onInteract(PlayerInteractEvent event) {
    Player player = event.getPlayer();
    Item item = event.getItem();

    if (!item.equals(Item.get(ItemID.FIRE_CHARGE))) {
      return;
    }

    FullChunk chunk = player
      .getLevel()
      .getChunk((byte) player.getX() >> 4, (byte) player.z >> 4);
    CompoundTag compoundTag = new CompoundTag();

    EntityFireBall fireBall = new EntityFireBall(chunk, compoundTag, player);

    fireBall.setPosition(new Vector3(player.x, player.y + 2, player.z));
    fireBall.setMotion(
      new Vector3(
        -Math.sin(player.yaw / 180 * Math.PI) *
        Math.cos(player.pitch / 180 * Math.PI),
        -Math.sin(player.pitch / 180 * Math.PI),
        Math.cos(player.yaw / 180 * Math.PI) *
        Math.cos(player.pitch / 180 * Math.PI)
      )
    );
    fireBall.setRotation(player.yaw, player.pitch);
    fireBall.spawnToAll();
    fireBall.setMotion(fireBall.getMotion().multiply(2));

    event.setCancelled();
  }
}