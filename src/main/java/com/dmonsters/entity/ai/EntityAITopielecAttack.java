package com.dmonsters.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.dmonsters.entity.EntityTopielec;
import com.dmonsters.main.ModConfig;

public class EntityAITopielecAttack extends DeadlyMonsterAIBase
{
    private final EntityTopielec topielec;
    private final float speed;
    private final int searchDistance = ModConfig.CATEGORY_TOPIELEC.topielecSearchDistance;
    private int ticks;
    private EntityPlayer playerEntity;

    public EntityAITopielecAttack(EntityTopielec owner, float speed)
    {
        this.topielec = owner;
        this.speed = speed;
    }

    public boolean shouldExecute()
    {
        if (this.topielec.getAttackTarget() instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) this.topielec.getAttackTarget();
            if (player != null && !player.isCreative() && !player.isRiding())
            {
                double distance = this.topielec.getDistance(player.posX, player.posY, player.posZ);
                if (distance < 2d)
                {
                    playerEntity = player;
                    return true;
                }
            }
        }
        return false;
    }

    public void updateTask()
    {
        double myX = this.topielec.posX;
        double myY = this.topielec.posY;
        double myZ = this.topielec.posZ;
        playerEntity.setPositionAndUpdate(myX, myY, myZ);

        ticks++;
        if (ticks > 40)
            return;
        else
            ticks = 0;
        BlockPos targetPos = findBestPosition();
        //System.out.println(targetPos);
        float[] normVec = normalizeVector(targetPos.subtract(this.topielec.getPosition()));
        //System.out.println(normVec[0] + ", " + myY + ", " + normVec[2]);
        this.topielec.setMovementVector(normVec[0], normVec[1], normVec[2]);
    }

    private float[] normalizeVector(BlockPos v)
    {
        float length = (float) Math.sqrt((v.getX() * v.getX()) + (v.getY() * v.getY()) + (v.getZ() * v.getZ()));
        float[] newVec = new float[3];
        newVec[0] = (v.getX() / length) * speed;
        newVec[1] = (v.getY() / length) * speed;
        newVec[2] = (v.getZ() / length) * speed;
        return newVec;
    }

    private BlockPos findBestPosition()
    {
        BlockPos myPos = this.topielec.getPosition();
        BlockPos bestPos = myPos;
        int minBoundsX = -searchDistance + myPos.getX();
        int maxBoundsX = searchDistance + myPos.getX();
        int minBoundsZ = -searchDistance + myPos.getZ();
        int maxBoundsZ = searchDistance + myPos.getZ();
        World worldIn = this.topielec.getEntityWorld();
        int deepestY = myPos.getY();
        //System.out.println("START " + myPos);
        //System.out.println(deepestY);
        for (int x = minBoundsX; x < maxBoundsX; x++)
        {
            for (int z = minBoundsZ; z < maxBoundsZ; z++)
            {
                int tempDeepestY = myPos.getY();
                for (int y = myPos.getY(); y > 0; y--)
                {
                    BlockPos currPos = new BlockPos(x, y, z);
                    Block block = worldIn.getBlockState(currPos).getBlock();
                    //System.out.println(y + " " + block + ", " + currPos);
                    if (block == Blocks.WATER && y <= tempDeepestY)
                    {
                        tempDeepestY = y;
                    }
                    else
                    {
                        if (tempDeepestY <= deepestY)
                        {
                            deepestY = tempDeepestY;
                            bestPos = currPos;
                        }
                        break;
                    }
                }
            }
        }
        //System.out.println("END: " + bestPos);
        return new BlockPos(bestPos.getX(), myPos.getY(), bestPos.getZ());
    }
}