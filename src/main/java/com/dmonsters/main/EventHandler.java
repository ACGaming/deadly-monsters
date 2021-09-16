package com.dmonsters.main;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemSword;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.dmonsters.entity.EntityHauntedCow;
import com.dmonsters.entity.EntityTopielec;
import com.dmonsters.item.Harpoon;
import com.dmonsters.network.PacketClientFXUpdate;
import com.dmonsters.network.PacketHandler;
import slimeknights.tconstruct.library.tools.SwordCore;
import slimeknights.tconstruct.library.tools.ranged.BowCore;

public class EventHandler
{
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onPlayerAttack(AttackEntityEvent e)
    {
        Entity entity = e.getTarget();
        if (!e.getEntity().getEntityWorld().isRemote)
        {
            if (!ModConfig.CATEGORY_HAUNTED_COW.hauntedCowDisabled && entity instanceof EntityHauntedCow)
            {
                World world = entity.getEntityWorld();
                EntityPlayer player = e.getEntityPlayer();
                Item itemClass = player.getHeldItemMainhand().getItem();
                if (ModConfig.CATEGORY_HAUNTED_COW.hauntedCowDisableTimeChange || itemClass instanceof ItemSword || itemClass instanceof ItemBow || (Loader.isModLoaded("tconstruct") && itemClass instanceof SwordCore) || (Loader.isModLoaded("tconstruct") && itemClass instanceof BowCore))
                {
                    return;
                }
                if (player.world.isDaytime())
                {
                    Style red = new Style().setColor(TextFormatting.DARK_RED);
                    TextComponentTranslation msg = new TextComponentTranslation("msg.dmonsters.haunted_cow");
                    msg.setStyle(red);
                    PacketHandler.INSTANCE.sendToAll(new PacketClientFXUpdate(player.getPosition(), PacketClientFXUpdate.Type.TIME_CHANGE));
                    long worldTime = world.getWorldTime();
                    while (worldTime % 18000 != 0)
                    {
                        worldTime++;
                    }
                    world.setWorldTime(worldTime);
                    player.sendMessage(msg);
                }
            }
        }
        if (!ModConfig.CATEGORY_TOPIELEC.topielecDisabled && ModConfig.CATEGORY_TOPIELEC.topielecHarpoonOnly && entity instanceof EntityTopielec)
        {
            EntityPlayer player = e.getEntityPlayer();
            Item itemClass = player.getHeldItemMainhand().getItem();
            e.setCanceled(!(itemClass instanceof Harpoon));
        }
    }
}