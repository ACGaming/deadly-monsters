package com.dmonsters.render;

import javax.annotation.Nonnull;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import com.dmonsters.entity.EntityStranger;
import com.dmonsters.main.MainMod;
import com.dmonsters.model.ModelStranger;

public class RenderStranger extends RenderLiving<EntityStranger>
{
    public static final Factory FACTORY = new Factory();
    private final ResourceLocation mobTexture = new ResourceLocation(MainMod.MOD_ID + ":textures/entity/stranger.png");

    public RenderStranger(RenderManager rendermanagerIn)
    {
        super(rendermanagerIn, new ModelStranger(), 0.5F);
    }

    protected void preRenderCallback(EntityStranger entity, float f)
    {
        GL11.glScalef(1.0F, 1.0F, 1.0F);
    }

    @Override
    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull EntityStranger entity)
    {
        return mobTexture;
    }

    public static class Factory implements IRenderFactory<EntityStranger>
    {
        @Override
        public Render<? super EntityStranger> createRenderFor(RenderManager manager)
        {
            return new RenderStranger(manager);
        }
    }
}