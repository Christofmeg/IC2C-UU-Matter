package com.christofmeg.ic2cuumatter;

import com.christofmeg.ic2cuumatter.nei.NEIPlugin;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import ic2.core.BasicMachineRecipeManager;

@SuppressWarnings("unused")
@Mod(
        modid = IC2CUUMatter.MOD_ID,
        version = IC2CUUMatter.VERSION,
        dependencies = "after:IC2;after:NotEnoughItems;"
)
public class IC2CUUMatter {
    public static final String MOD_ID = "ic2cuumatter";
    public static final String VERSION = "1.0.0";

    @EventHandler
    public void load(FMLPreInitializationEvent event) {
        ModRecipes.matterAmplifier = new BasicMachineRecipeManager();
        if (event.getSide().isClient() && Loader.isModLoaded("NotEnoughItems")) {
            NEIPlugin.init();
        }
    }

    @EventHandler
    public void onInit(FMLInitializationEvent event) {
        ModRecipes.init();
    }

}
