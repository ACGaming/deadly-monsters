package com.dmonsters.render;

import javax.annotation.Nonnull;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import com.dmonsters.DeadlyMonsters;
import com.dmonsters.entity.EntityFreezer;
import com.dmonsters.model.ModelFreezer;

public class RenderFreezer extends RenderLiving<EntityFreezer>
{
    public static final Factory FACTORY = new Factory();
    private final ResourceLocation mobTexture = new ResourceLocation(DeadlyMonsters.MOD_ID + ":textures/entity/freezer_idle.png");
    private final ResourceLocation mobTextureAttaking = new ResourceLocation(DeadlyMonsters.MOD_ID + ":textures/entity/freezer_angry.png");

    public RenderFreezer(RenderManager rendermanagerIn)
    {
        super(rendermanagerIn, new ModelFreezer(), 0.5F);
    }

    protected void preRenderCallback(EntityFreezer entity, float f)
    {
        GL11.glScalef(1.0F, 1.0F, 1.0F);
    }

    @Override
    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull EntityFreezer entity)
    {
        if (entity.getAttacking())
            return mobTextureAttaking;
        return mobTexture;
    }

    public static class Factory implements IRenderFactory<EntityFreezer>
    {
        @Override
        public Render<? super EntityFreezer> createRenderFor(RenderManager manager)
        {
            return new RenderFreezer(manager);
        }
    }
}