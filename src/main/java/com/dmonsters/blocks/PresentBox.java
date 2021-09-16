package com.dmonsters.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.entity.passive.EntityZombieHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.dmonsters.main.MainMod;
import com.dmonsters.main.ModBlocks;
import com.dmonsters.main.ModItems;

public class PresentBox extends Block
{
    protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.13, 0.0D, 0.13, 0.87, 0.75, 0.87);

    public PresentBox()
    {
        super(Material.CACTUS);
        setUnlocalizedName(MainMod.MODID + ".present_box");
        setRegistryName("present_box");
        setCreativeTab(MainMod.MOD_CREATIVETAB);
        this.setHardness(1);
        this.setResistance(50);
    }

    public boolean isFullCube(IBlockState state)
    {
        return true;
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return AABB;
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public int quantityDropped(Random random)
    {
        return 1;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (!worldIn.isRemote)
        {
            Random rand = new Random();
            float rndNum = rand.nextFloat();
            if (rndNum < 0.7F)
            {
                worldIn.setBlockToAir(pos);
                worldIn.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 1, true);
            }
            else if (rndNum > 0.7F && rndNum < 0.8F)
            {
                Item spawnedItem = spawnRandomItem();
                ItemStack newItem = new ItemStack(spawnedItem, 1);
                EntityItem item = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), newItem);
                worldIn.spawnEntity(item);
                worldIn.setBlockToAir(pos);
            }
            else if (rndNum > 0.8F && rndNum < 0.95F)
            {
                EntityLiving entity = spawnMonster(worldIn);
                entity.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), playerIn.rotationYaw, 0.0F);
                worldIn.spawnEntity(entity);
                worldIn.setBlockToAir(pos);
            }
            else
            {
                worldIn.setBlockState(pos, ModBlocks.dump.getStateFromMeta(rand.nextInt(5)));
            }
        }
        return true;
    }

    @Override
    public PresentBox setCreativeTab(CreativeTabs tab)
    {
        super.setCreativeTab(tab);
        return this;
    }

    private Item spawnRandomItem()
    {
        List<Item> itemsList = new ArrayList<>();
        itemsList.add(Items.APPLE);
        itemsList.add(Items.GOLD_NUGGET);
        itemsList.add(Items.LEATHER_HELMET);
        itemsList.add(Items.FISH);
        itemsList.add(Items.REDSTONE);
        itemsList.add(ModItems.mob_spawner_item_present);
        itemsList.add(Items.GUNPOWDER);
        itemsList.add(Items.REDSTONE);
        itemsList.add(Items.IRON_INGOT);
        itemsList.add(Items.IRON_SWORD);
        Random rand = new Random();
        int rndNum = rand.nextInt(itemsList.size());
        return itemsList.get(rndNum);
    }

    private EntityLiving spawnMonster(World worldIn)
    {
        List<EntityLiving> monstersList = new ArrayList<>();
        monstersList.add(new EntityCreeper(worldIn));
        monstersList.add(new EntityZombie(worldIn));
        monstersList.add(new EntitySkeleton(worldIn));
        monstersList.add(new EntitySilverfish(worldIn));
        monstersList.add(new EntityBlaze(worldIn));
        monstersList.add(new EntityMagmaCube(worldIn));
        monstersList.add(new EntityZombieHorse(worldIn));
        monstersList.add(new EntitySkeletonHorse(worldIn));
        monstersList.add(new EntityPigZombie(worldIn));
        Random rand = new Random();
        int rndNum = rand.nextInt(monstersList.size());
        return monstersList.get(rndNum);
    }
}