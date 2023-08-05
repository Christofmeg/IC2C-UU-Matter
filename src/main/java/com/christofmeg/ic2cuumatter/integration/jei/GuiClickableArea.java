package com.christofmeg.ic2cuumatter.integration.jei;

import ic2.core.block.machines.components.hv.MassFabricatorComponent;
import ic2.core.block.machines.containers.ev.PlasmafierContainer;
import ic2.core.block.machines.containers.hv.MassFabricatorContainer;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.ComponentContainerScreen;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.utils.collection.CollectionUtils;
import mezz.jei.api.gui.handlers.IGuiClickableArea;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class GuiClickableArea implements IGuiContainerHandler<IC2Screen> {

    public GuiClickableArea() {
    }

    public @NotNull Collection<IGuiClickableArea> getGuiClickableAreas(@NotNull IC2Screen containerScreen, double mouseX, double mouseY) {
        List<IGuiClickableArea> areas = CollectionUtils.createList();
        if (containerScreen instanceof ComponentContainerScreen) {
            ContainerComponent<?> comp = containerScreen.getCastedContainer(ContainerComponent.class);
            if (comp != null) {
                if (comp.getHolder() != null) {
                    if (comp instanceof MassFabricatorContainer && comp.getComponent(MassFabricatorComponent.class) != null) {
                        int width = 16;
                        int height = 15;
                        int posX = 80;
                        int posY = 40;
                        areas.add(IGuiClickableArea.createBasic(posX, posY, width, height, MassFabricatorCategory.TYPE));
                    }
                    else if (comp instanceof PlasmafierContainer) {
                        int width = 20;
                        int height = 54;
                        int posX = 78;
                        int posY = 14;
                        areas.add(IGuiClickableArea.createBasic(posX, posY, width, height, PlasmafierCategory.TYPE));
                    }
                }
            }
        }

        return areas;
    }

}
