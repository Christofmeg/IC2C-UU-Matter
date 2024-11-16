package com.christofmeg.ic2cuumatter.nei;

import codechicken.nei.api.API;
import ic2.core.block.machine.gui.GuiMatter;

public class NEIPlugin {

    public static void init() {
        API.registerRecipeHandler(new MassFabricatorCategory());
        API.registerUsageHandler(new MassFabricatorCategory());
        API.registerGuiOverlay(GuiMatter.class, "matter", 5, 11);
    }
}