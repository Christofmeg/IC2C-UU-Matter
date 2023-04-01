package com.christofmeg.ic2cuumatter.integration.jei;

import java.util.Arrays;

import com.christofmeg.ic2cuumatter.integration.jei.category.MassFabricatorCategory;
import com.christofmeg.ic2cuumatter.integration.jei.category.PlasmafierCategory;

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
    public void register(IModRegistry registration) {

        registration.addRecipeCatalyst(new ItemStack(Ic2States.massfabricator.getBlock(), 1, 1),
                MassFabricatorCategory.UID);
        registration.addRecipeCatalyst(new ItemStack(Ic2States.plasmafier.getBlock(), 1, 5), PlasmafierCategory.UID);

        registration.addRecipes(Arrays.asList(
                new MassFabricatorCategory.MassFabricatorRecipe(new ItemStack(Items.AIR),
                        new ItemStack(Ic2Items.uuMatter.getItem(), 1, 202), "0"),
                new MassFabricatorCategory.MassFabricatorRecipe(new ItemStack(Ic2Items.scrap.getItem(), 1, 200),
                        new ItemStack(Ic2Items.uuMatter.getItem(), 1, 202), "5,000"),
                new MassFabricatorCategory.MassFabricatorRecipe(new ItemStack(Ic2Items.scrapBox.getItem()),
                        new ItemStack(Ic2Items.uuMatter.getItem(), 1, 202), "45,000"),
                new MassFabricatorCategory.MassFabricatorRecipe(new ItemStack(Ic2Items.scrapMetal.getItem(), 1, 201),
                        new ItemStack(Ic2Items.uuMatter.getItem(), 1, 202), "100,000")),
                MassFabricatorCategory.UID);

        registration.addRecipes(Arrays.asList(new PlasmafierCategory.PlasmafierRecipe(
                new ItemStack(Ic2Items.emptyCell.getItem()), new ItemStack(Ic2Items.uuMatter.getItem(), 10, 202),
                new ItemStack(Ic2Items.plasmaCell.getItem(), 1, 108))), PlasmafierCategory.UID);

        int mwidth = 16;
        int mheight = 15;
        int mposX = 114;
        int mposY = 39;
        registration.addRecipeClickArea(MassFabricatorClickableArea.class, mposX, mposY, mwidth, mheight,
                new String[] { MassFabricatorCategory.UID });

        int width = 20;
        int height = 54;
        int posX = 78;
        int posY = 14;

        registration.addRecipeClickArea(PlasmafierClickableArea.class, posX, posY, width, height,
                new String[] { PlasmafierCategory.UID });

    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper helper = registration.getJeiHelpers().getGuiHelper();

        registration.addRecipeCategories(
                new MassFabricatorCategory(helper, Ic2Items.massfabricator, Ic2Resources.massfabricator),
                new PlasmafierCategory(helper, Ic2Items.plasmafier, Ic2Resources.plasmafire));
    }

    /*
     * GuiScreen gui = guiScreen; Minecraft minecraft = gui.mc; if (minecraft !=
     * null) { if (gui instanceof GuiComponentContainer) { if
     * (guiScreen.getGuiName() == Ic2BlockLang.massFabricator.getLocalized()) { } }
     * }
     */

}