package com.dmonsters.items;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.dmonsters.main.MainMod;

public class BabyEye extends Item
{
    public BabyEye()
    {
        setRegistryName("babyEye");
        setUnlocalizedName(MainMod.MODID + ".babyEye");
        this.setCreativeTab(MainMod.MOD_CREATIVETAB);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack stack = playerIn.getHeldItem(hand);
        if (!playerIn.canPlayerEdit(pos, facing, stack))
        {
            return EnumActionResult.FAIL;
        }
        else
        {
            if (!worldIn.isRemote)
            {
                Block blockIn = worldIn.getBlockState(pos).getBlock();
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
                Item blockItem = getItemFromBlock(blockIn);
                ItemStack blockItemStack = new ItemStack(blockItem, 1);
                EntityItem item = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), blockItemStack);
                worldIn.spawnEntity(item);
                //--stack.stackSize;
                stack.shrink(1);
            }
            return EnumActionResult.SUCCESS;
        }
    }
}