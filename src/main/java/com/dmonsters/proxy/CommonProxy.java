package com.dmonsters.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.dmonsters.main.EventHandler;
import com.dmonsters.main.ModBlocks;
import com.dmonsters.main.ModEntities;
import com.dmonsters.network.PacketHandler;

public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent e)
    {
        PacketHandler.init("dmonsters");
        ModBlocks.init();
        ModEntities.init();
    }

    public void init(FMLInitializationEvent e)
    {
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }

    public void postInit(FMLPostInitializationEvent e)
    {

    }
}