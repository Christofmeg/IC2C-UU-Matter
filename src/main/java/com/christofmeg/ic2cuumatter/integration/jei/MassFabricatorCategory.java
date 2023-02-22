package com.christofmeg.ic2cuumatter.integration.jei;

import javax.annotation.Nullable;

import ic2.core.platform.lang.storage.Ic2BlockLang;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class MassFabricatorCategory implements IRecipeCategory<MassFabricatorCategory.MassFabricatorRecipe> {

    public static String UID = "ic2cuumatter.mass_fabricator";
    IDrawable background;
    IDrawable background_fix;
    MassFabricatorRecipe recipe;

    public MassFabricatorCategory(IGuiHelper helper, ItemStack itemStack, ResourceLocation texture) {
        background = helper.createDrawable(texture, 48, 13, 133, 69);
        this.background_fix = helper.drawableBuilder(texture, 4, 4, 3, 69).build();
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return Ic2BlockLang.massFabricator.getLocalized();
    }

    @Override
    public String getModName() {
        return "ic2cuumatter";
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, MassFabricatorRecipe recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        guiItemStacks.init(0, false, 65, 4);
        guiItemStacks.init(1, true, 65, 40);
        guiItemStacks.set(ingredients);
        this.recipe = recipeWrapper;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {

        background_fix.draw(minecraft, 125, 0);

        FontRenderer font = minecraft.fontRenderer;
        String tierHV = I18n.format("translation.ic2cuumatter.tier.hv");
        font.drawString(tierHV, 0, 0, 4210752);

        String energy = I18n.format("translation.ic2cuumatter.energy");
        font.drawString(energy, 0, 52, 4210752);

        font.drawString(new String("7,000,000 EU"), 0, 62, 4210752);
        font.drawString(new String("512 EU/t"), 90, 62, 4210752);

        if (recipe.getEnergy() != "0") {
            String amplifier = I18n.format("translation.ic2cuumatter.amplifier");
            font.drawString(amplifier, 90, 21, 4210752);
            if (recipe.getEnergy() == "100,000") {
                font.drawString(I18n.format("+ " + recipe.getEnergy()), 86, 31, 4210752);
            } else if (recipe.getEnergy() == "45,000") {
                font.drawString(I18n.format("+ " + recipe.getEnergy()), 92, 31, 4210752);
            } else {
                font.drawString(I18n.format("+ " + recipe.getEnergy()), 98, 31, 4210752);
            }
        }
    }

    public static final class MassFabricatorRecipe implements IRecipeWrapper {

        public String energy;
        public ItemStack inputItem;
        public ItemStack outputItem;

        public MassFabricatorRecipe(ItemStack itemInput, ItemStack itemOutput, @Nullable String energyRequired) {
            this.energy = energyRequired;
            this.inputItem = itemInput;
            this.outputItem = itemOutput;
        }

        public String getEnergy() {
            return energy;
        }

        @Override
        public void getIngredients(IIngredients ingredients) {
            ingredients.setOutput(VanillaTypes.ITEM, outputItem);
            ingredients.setInput(VanillaTypes.ITEM, inputItem);
        }
    }

}
