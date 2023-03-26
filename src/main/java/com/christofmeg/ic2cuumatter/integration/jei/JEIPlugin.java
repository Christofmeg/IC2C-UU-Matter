package com.christofmeg.ic2cuumatter.integration.jei;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import ic2.api.recipes.registries.IMachineRecipeList;
import ic2.core.block.base.IC2ContainerBlock;
import ic2.core.block.machines.components.ev.ColossalMachineComponent;
import ic2.core.block.machines.components.hv.MassFabricatorComponent;
import ic2.core.block.machines.containers.ev.ColossalMachineContainer;
import ic2.core.block.machines.containers.ev.PlasmafierContainer;
import ic2.core.block.machines.containers.hv.MassFabricatorContainer;
import ic2.core.block.machines.tiles.hv.MassFabricatorTileEntity;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.gui.ComponentContainerScreen;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.collection.CollectionUtils;
import ic2.jeiplugin.core.IC2GuiHandler;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.gui.handlers.IGuiClickableArea;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

@JeiPlugin
public class JEIPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation("ic2cuumatter", "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new MassFabricatorCategory(registration.getJeiHelpers().getGuiHelper(), MassFabricatorContainer.TEXTURE,
                        IC2Blocks.MASS_FABRICATOR),
                new PlasmafierCategory(registration.getJeiHelpers().getGuiHelper(), PlasmafierContainer.TEXTURE,
                        IC2Blocks.PLASMAFIER));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(MassFabricatorCategory.TYPE, Arrays.asList(

                new MassFabricatorCategory.MassFabricatorRecipe(Ingredient.of(new ItemStack(Items.AIR)),
                        new ItemStack(IC2Items.UUMATTER), "0"),

                new MassFabricatorCategory.MassFabricatorRecipe(Ingredient.of(new ItemStack(IC2Items.SCRAP)),
                        new ItemStack(IC2Items.UUMATTER), "1,000"),

                new MassFabricatorCategory.MassFabricatorRecipe(Ingredient.of(new ItemStack(IC2Items.SCRAPBOX)),
                        new ItemStack(IC2Items.UUMATTER), "45,000"),

                new MassFabricatorCategory.MassFabricatorRecipe(Ingredient.of(new ItemStack(IC2Items.SCRAP_METAL)),
                        new ItemStack(IC2Items.UUMATTER), "100,000")));

        registration.addRecipes(PlasmafierCategory.TYPE,
                Arrays.asList(
                        new PlasmafierCategory.PlasmafierRecipe(Ingredient.of(new ItemStack(IC2Items.UUMATTER, 10)),
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

}