package com.christofmeg.ic2cuumatter.integration.jei;

import java.util.Arrays;
import java.util.Collections;

import javax.annotation.Nonnull;

import com.christofmeg.ic2cuumatter.integration.jei.category.MassFabricatorCategory;
import com.christofmeg.ic2cuumatter.integration.jei.category.PlasmafierCategory;

import ic2.core.IC2;
import ic2.core.block.machine.high.container.ContainerMassFabricator;
import ic2.core.block.machine.high.container.ContainerPlasmafier;
import ic2.core.inventory.gui.custom.MassFabricatorGui;
import ic2.core.inventory.gui.custom.PlasmafierGui;
import ic2.core.platform.registry.Ic2Items;
import ic2.core.platform.registry.Ic2Resources;
import ic2.core.platform.registry.Ic2States;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@JEIPlugin
@SideOnly(Side.CLIENT)
public class ModJEIPlugin implements IModPlugin {

    @Override
    public void register(@Nonnull IModRegistry registration) {

        registration.addRecipeCatalyst(new ItemStack(Ic2States.massfabricator.getBlock(), 1, 1),
                MassFabricatorCategory.UID);
        registration.addRecipeCatalyst(new ItemStack(Ic2States.plasmafier.getBlock(), 1, 5), PlasmafierCategory.UID);

        if (IC2.config.isLoaded() && IC2.config.getConfig().getCategory("balance").getValues().get("UUNeedsScrap")
                .getBoolean() == false) {
            registration
                    .addRecipes(
                            Arrays.asList(new MassFabricatorCategory.MassFabricatorRecipe(new ItemStack(Items.AIR),
                                    new ItemStack(Ic2Items.uuMatter.getItem(), 1, 202), null)),
                            MassFabricatorCategory.UID);
        }

        registration.addRecipes(Arrays.asList(
                new MassFabricatorCategory.MassFabricatorRecipe(new ItemStack(Ic2Items.scrap.getItem(), 1, 200),
                        new ItemStack(Ic2Items.uuMatter.getItem(), 1, 202), "5,000"),
                new MassFabricatorCategory.MassFabricatorRecipe(new ItemStack(Ic2Items.scrapBox.getItem()),
                        new ItemStack(Ic2Items.uuMatter.getItem(), 1, 202), "45,000"),
                new MassFabricatorCategory.MassFabricatorRecipe(new ItemStack(Ic2Items.scrapMetal.getItem(), 1, 201),
                        new ItemStack(Ic2Items.uuMatter.getItem(), 1, 202), "100,000")),
                MassFabricatorCategory.UID);

        registration.addRecipes(Collections.singletonList(new PlasmafierCategory.PlasmafierRecipe(
                new ItemStack(Ic2Items.emptyCell.getItem()), new ItemStack(Ic2Items.uuMatter.getItem(), 10, 202),
                new ItemStack(Ic2Items.plasmaCell.getItem(), 1, 108))), PlasmafierCategory.UID);

        registration.addRecipeClickArea(MassFabricatorGui.class, 114, 39, 16, 15, MassFabricatorCategory.UID);

        registration.addRecipeClickArea(PlasmafierGui.class, 78, 14, 20, 54, PlasmafierCategory.UID);

        int mrecipeSlotStart = 0;
        int mrecipeSlotCount = 1;
        int minventorySlotStart = 2;
        int minventorySlotCount = 36;
        registration.getRecipeTransferRegistry().addRecipeTransferHandler(ContainerMassFabricator.class,
                MassFabricatorCategory.UID, mrecipeSlotStart, mrecipeSlotCount, minventorySlotStart,
                minventorySlotCount);

        int recipeSlotStart = 1;
        int recipeSlotCount = 2;
        int inventorySlotStart = 0;
        int inventorySlotCount = 39;
        registration.getRecipeTransferRegistry().addRecipeTransferHandler(ContainerPlasmafier.class,
                PlasmafierCategory.UID, recipeSlotStart, recipeSlotCount, inventorySlotStart, inventorySlotCount);

    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper helper = registration.getJeiHelpers().getGuiHelper();

        registration.addRecipeCategories(new MassFabricatorCategory(helper, Ic2Resources.massfabricator),
                new PlasmafierCategory(helper, Ic2Items.plasmafier, Ic2Resources.plasmafire));
    }

}