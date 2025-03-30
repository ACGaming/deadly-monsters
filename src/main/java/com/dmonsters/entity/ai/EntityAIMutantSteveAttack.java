package com.dmonsters.entity.ai;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.dmonsters.entity.EntityMutantSteve;

public class EntityAIMutantSteveAttack extends DeadlyMonsterAIMelee
{
    private final EntityMutantSteve mutantSteve;
    private int raiseArmTicks;
    private int ticks;

    public EntityAIMutantSteveAttack(EntityMutantSteve mutantSteve, double speed, boolean longMemory)
    {
        super(mutantSteve, speed, longMemory);
        this.mutantSteve = mutantSteve;
        this.setMutexBits(7);
    }

    @Override
    public void startExecuting()
    {
        super.startExecuting();
        this.raiseArmTicks = 0;
    }

    @Override
    public void resetTask()
    {
        super.resetTask();
        this.mutantSteve.setArmsRaised(false);
    }

    @Override
    public void updateTask()
    {
        super.updateTask();
        ++this.raiseArmTicks;

        this.mutantSteve.setArmsRaised(this.raiseArmTicks >= 5 && this.raiseArmTicks < 10);

        ticks++;
        if (ticks == 20 && !this.mutantSteve.isInWater())
        {
            ticks = 0;
            if (this.attacker.world.getGameRules().getBoolean("mobGriefing"))
            {
                destroyAround(0, 0.25F);
                destroyAround(1, 0.5F);
                destroyAround(2, 0.75F);
            }
        }
    }

    private void destroyAround(int yOffset, float destroyChance)
    {
        IBlockState blockToDestroy;
        BlockPos.MutableBlockPos blockToDestroyPos = new BlockPos.MutableBlockPos();
        World world = this.attacker.world;
        double x = this.attacker.posX;
        double y = this.attacker.posY;
        double z = this.attacker.posZ;
        float hardness;
        float hardnessThreshold = 5;
        boolean destroyedBlock = false;

        for (int dx = -1; dx <= 1; ++dx)
        {
            for (int dz = -1; dz <= 1; ++dz)
            {
                if (dx == 0 && dz == 0) continue;
                if (world.rand.nextFloat() < destroyChance)
                {
                    blockToDestroyPos.setPos(x + dx, y + yOffset, z + dz);
                    blockToDestroy = world.getBlockState(blockToDestroyPos);
                    if (blockToDestroy.getMaterial() != Material.AIR)
                    {
                        hardness = blockToDestroy.getBlockHardness(world, blockToDestroyPos);
                        if (hardness > -1 && hardness < hardnessThreshold)
                        {
                            world.destroyBlock(blockToDestroyPos, true);
                            destroyedBlock = true;
                        }
                    }
                }
            }
        }

        if (destroyedBlock)
        {
            this.attacker.swingArm(EnumHand.MAIN_HAND);
        }
    }
}