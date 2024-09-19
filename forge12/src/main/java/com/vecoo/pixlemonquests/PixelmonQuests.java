package com.vecoo.pixlemonquests;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.vecoo.pixlemonquests.integration.PixelmonIntegration;
import com.vecoo.pixlemonquests.listener.TaskListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = PixelmonQuests.MOD_ID, acceptableRemoteVersions = "*", useMetadata = true)
public class    PixelmonQuests {
    public static final String MOD_ID = "pixelmonquests";
    private static final Logger LOGGER = LogManager.getLogger("PixelmonQuests");

    private static PixelmonQuests instance;

    @Mod.EventHandler
    public void onPreInitialization(FMLPreInitializationEvent event) {
        instance = this;
    }

    @Mod.EventHandler
    public void onInitialization(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new PixelmonIntegration());
        Pixelmon.EVENT_BUS.register(new TaskListener());
    }

    public static PixelmonQuests getInstance() {
        return instance;
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}