package com.dmonsters.render;

import javax.annotation.Nonnull;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import com.dmonsters.DeadlyMonsters;
import com.dmonsters.entity.EntityZombieChicken;
import com.dmonsters.model.ModelZombieChicken;

public class RenderZombieChicken extends RenderLiving<EntityZombieChicken>
{
    public static final Factory FACTORY = new Factory();
    private final ResourceLocation mobTexture = new ResourceLocation(DeadlyMonsters.MOD_ID + ":textures/entity/zombie_chicken.png");

    public RenderZombieChicken(RenderManager rendermanagerIn)
    {
        super(rendermanagerIn, new ModelZombieChicken(), 0.3F);
    }

    protected void preRenderCallback(EntityZombieChicken entity, float f)
    {
        GL11.glScalef(1.0F, 1.0F, 1.0F);
    }

    @Override
    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull EntityZombieChicken entity)
    {
        return mobTexture;
    }

    public static class Factory implements IRenderFactory<EntityZombieChicken>
    {
        @Override
        public Render<? super EntityZombieChicken> createRenderFor(RenderManager manager)
        {
            return new RenderZombieChicken(manager);
        }
    }
}