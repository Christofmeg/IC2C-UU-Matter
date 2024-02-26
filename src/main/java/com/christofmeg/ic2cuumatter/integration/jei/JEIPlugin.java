package com.christofmeg.ic2cuumatter.integration.jei;

import ic2.core.block.machines.containers.ev.PlasmafierContainer;
import ic2.core.block.machines.containers.hv.MassFabricatorContainer;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Items;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@SuppressWarnings("unused")
@JeiPlugin
public class JEIPlugin implements IModPlugin {

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return new ResourceLocation("ic2cuumatter", "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new MassFabricatorCategory(registration.getJeiHelpers().getGuiHelper(), IC2Blocks.MASS_FABRICATOR),
                new PlasmafierCategory(registration.getJeiHelpers().getGuiHelper(), PlasmafierContainer.TEXTURE,
                        IC2Blocks.PLASMAFIER));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(MassFabricatorCategory.TYPE, MassFabricatorCategory.MassFabricatorRecipe.getMassFabricatorRecipes());

        registration.addRecipes(PlasmafierCategory.TYPE,
                List.of(new PlasmafierCategory.PlasmafierRecipe(Ingredient.of(new ItemStack(IC2Items.UUMATTER, 10)),
                                Ingredient.of(IC2Items.CELL_EMPTY), new ItemStack(IC2Items.CELL_PLASMA))));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(IC2Blocks.MASS_FABRICATOR), MassFabricatorCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(IC2Blocks.PLASMAFIER), PlasmafierCategory.TYPE);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addGuiContainerHandler(IC2Screen.class, new GuiClickableArea());
    }

    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        int mrecipeSlotStart = 0;
        int mrecipeSlotCount = 1;
        int minventorySlotStart = 2;
        int minventorySlotCount = 41;
        registration.addRecipeTransferHandler(MassFabricatorContainer.class, null,
                MassFabricatorCategory.TYPE, mrecipeSlotStart, mrecipeSlotCount, minventorySlotStart,
                minventorySlotCount);

        int recipeSlotStart = 0;
        int recipeSlotCount = 2;
        int inventorySlotStart = 2;
        int inventorySlotCount = 42;
        registration.addRecipeTransferHandler(PlasmafierContainer.class, null,
                PlasmafierCategory.TYPE, recipeSlotStart, recipeSlotCount, inventorySlotStart, inventorySlotCount);
    }

}