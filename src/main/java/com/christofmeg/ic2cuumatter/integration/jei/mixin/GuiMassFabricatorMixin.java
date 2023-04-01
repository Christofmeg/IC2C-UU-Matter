package com.christofmeg.ic2cuumatter.integration.jei.mixin;

import org.spongepowered.asm.mixin.Mixin;

import com.christofmeg.ic2cuumatter.integration.jei.MassFabricatorClickableArea;

import ic2.core.block.machine.high.TileEntityMassFabricator;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

@Mixin(TileEntityMassFabricator.class)
public abstract class GuiMassFabricatorMixin {

//TODO    @Overwrite
    public Class<? extends GuiScreen> getGuiClass(EntityPlayer player) {
        return MassFabricatorClickableArea.class;
    }

}
