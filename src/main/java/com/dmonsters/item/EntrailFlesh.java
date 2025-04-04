package com.dmonsters.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.dmonsters.DeadlyMonsters;
import com.dmonsters.entity.EntityEntrail;

public class EntrailFlesh extends Item
{
    public EntrailFlesh()
    {
        setRegistryName("entrail_flesh");
        setUnlocalizedName(DeadlyMonsters.MOD_ID + ".entrail_flesh");
        this.setCreativeTab(DeadlyMonsters.MOD_CREATIVE_TAB);
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        if (!attacker.world.isRemote && !(target instanceof EntityEntrail))
        {
            target.setDead();
            double x, y, z;
            x = target.posX;
            y = target.posY;
            z = target.posZ;
            Entity slime = new EntityEntrail(attacker.world);
            slime.setPosition(x, y, z);
            attacker.world.spawnEntity(slime);
            stack.shrink(1);
        }
        return true;
    }
}