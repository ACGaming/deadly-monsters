package com.dmonsters.render;

import javax.annotation.Nonnull;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import com.dmonsters.DeadlyMonsters;
import com.dmonsters.entity.EntityPresent;
import com.dmonsters.model.ModelPresent;

public class RenderPresent extends RenderLiving<EntityPresent>
{
    public static final Factory FACTORY = new Factory();
    private final ResourceLocation mobTexture = new ResourceLocation(DeadlyMonsters.MOD_ID + ":textures/entity/present.png");

    public RenderPresent(RenderManager rendermanagerIn)
    {
        super(rendermanagerIn, new ModelPresent(), 0.5F);
    }

    protected void preRenderCallback(EntityPresent entity, float f)
    {
        GL11.glScalef(1.0F, 1.0F, 1.0F);
    }

    @Override
    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull EntityPresent entity)
    {
        return mobTexture;
    }

    public static class Factory implements IRenderFactory<EntityPresent>
    {
        @Override
        public Render<? super EntityPresent> createRenderFor(RenderManager manager)
        {
            return new RenderPresent(manager);
        }
    }
}