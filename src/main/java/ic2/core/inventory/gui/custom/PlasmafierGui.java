package com.christofmeg.ic2cuumatter.integration.jei;

import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.GuiComponentContainer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class PlasmafierGui extends GuiComponentContainer {

    public PlasmafierGui(ContainerComponent inventorySlotsIn) {
        super(inventorySlotsIn);
    }

}