package com.dmonsters.entity.ai;

import net.minecraft.util.math.BlockPos;

import com.dmonsters.entity.EntityTopielec;

public class EntityAITopielecFollow extends DeadlyMonsterAIBase
{
    private final EntityTopielec topielec;
    private final float speed;

    public EntityAITopielecFollow(EntityTopielec owner, float speed)
    {
        this.topielec = owner;
        this.speed = speed;
    }

    public boolean shouldExecute()
    {
        return this.topielec.getAttackTarget() != null;
    }

    public void updateTask()
    {
        BlockPos resultPos = this.topielec.getAttackTarget().getPosition().subtract(this.topielec.getPosition());
        float[] normVec = normalizeVector(resultPos);
        this.topielec.setMovementVector(normVec[0], normVec[1], normVec[2]);
        float yawRad = (float) Math.atan2(normVec[2], normVec[0]);
        float yawDeg = (float) ((yawRad > 0 ? yawRad : (2 * Math.PI + yawRad)) * 360 / (2 * Math.PI));
        if (yawDeg > 0)
        {
            this.topielec.setPositionAndRotation(this.topielec.posX, this.topielec.posY, this.topielec.posZ, yawDeg, yawDeg);
        }
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
}