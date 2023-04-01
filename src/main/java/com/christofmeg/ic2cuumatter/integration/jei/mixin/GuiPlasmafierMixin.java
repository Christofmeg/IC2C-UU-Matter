package com.christofmeg.ic2cuumatter.integration.jei.mixin;

import org.spongepowered.asm.mixin.Mixin;

import com.christofmeg.ic2cuumatter.integration.jei.PlasmafierClickableArea;

import ic2.core.block.machine.high.TileEntityPlasmafier;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

@Mixin(TileEntityPlasmafier.class)
public abstract class GuiPlasmafierMixin {

//    @Inject(at = @At(value = "RETURN"), method = "getGuiClass", cancellable = true)
//TODO    @Overwrite
    public Class<? extends GuiScreen> getGuiClass(EntityPlayer player) {
        return PlasmafierClickableArea.class;
    }

}
