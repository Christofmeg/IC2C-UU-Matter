package com.christofmeg.ic2cuumatter.integration.jei;

import ic2.core.platform.lang.storage.Ic2BlockLang;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated.StartDirection;
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
import scala.actors.threadpool.Arrays;

public class PlasmafierCategory implements IRecipeCategory<PlasmafierCategory.PlasmafierRecipe> {

    public static String UID = "ic2cuumatter.plasmafier";
    public static final ResourceLocation plasmafierTexture = new ResourceLocation("ic2cuumatter",
            "textures/gui/plasmafier.png");
    IDrawable background;
    IDrawable press1;
    IDrawable press2;
    IDrawable press3;
    IDrawable tank1;
    IDrawable tank2;
    IDrawable tank3;
    IDrawable plasma;
    IDrawable glass;

    public PlasmafierCategory(IGuiHelper helper, ItemStack itemStack, ResourceLocation texture) {
        background = helper.createDrawable(texture, 13, 14, 132, 64);
        press1 = helper.createDrawable(texture, 176, 41, 12, 1);
        press2 = helper.createDrawable(texture, 176, 42, 12, 1);
        press3 = helper.createDrawable(texture, 176, 45, 12, 1);
        tank1 = helper.drawableBuilder(plasmafierTexture, 201, 0, 12, 41).buildAnimated(250, StartDirection.TOP, true);
        tank2 = helper.drawableBuilder(plasmafierTexture, 213, 0, 12, 41).buildAnimated(250, StartDirection.TOP, true);
        tank3 = helper.drawableBuilder(plasmafierTexture, 225, 0, 12, 41).buildAnimated(250, StartDirection.TOP, true);
        plasma = helper.drawableBuilder(texture, 176, 0, 12, 41).buildAnimated(250, StartDirection.TOP, true);
        glass = helper.createDrawable(texture, 189, 0, 12, 46);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return Ic2BlockLang.plasmafire.getLocalized();
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
    public void setRecipe(IRecipeLayout recipeLayout, PlasmafierRecipe recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        guiItemStacks.init(0, true, 102, 8); // empty cell
        guiItemStacks.init(1, true, 30, 20); // uu matter
        guiItemStacks.init(2, false, 102, 30); // plasma cell
        guiItemStacks.set(ingredients);
    }

    @Override
    public void drawExtras(Minecraft minecraft) {

        press1.draw(minecraft, 69, 45);
        press2.draw(minecraft, 69, 46);
        press2.draw(minecraft, 69, 47);
        press2.draw(minecraft, 69, 48);
        press3.draw(minecraft, 69, 49);
        tank1.draw(minecraft, 69, 4);
        tank2.draw(minecraft, 69, 5);
        tank3.draw(minecraft, 69, 8);
        plasma.draw(minecraft, 69, 9);
        glass.draw(minecraft, 68, 4);

        FontRenderer font = minecraft.fontRenderer;
        String tierHV = I18n.format("translation.ic2cuumatter.tier.hv");
        font.drawString(tierHV, 0, 0, 4210752);

        String ticks = I18n.format("translation.ic2cuumatter.ticks");
        font.drawString(ticks, 0, 57, 4210752);
        font.drawString(new String("512 EU/t"), 89, 57, 4210752);

    }

    public static final class PlasmafierRecipe implements IRecipeWrapper {

        public ItemStack inputEmptyCell;
        public ItemStack inputUU;
        public ItemStack outputItem;

        public PlasmafierRecipe(ItemStack emptyCellInput, ItemStack uuInput, ItemStack itemOutput) {
            this.inputEmptyCell = emptyCellInput;
            this.inputUU = uuInput;
            this.outputItem = itemOutput;
        }

        @SuppressWarnings("unchecked")
        @Override
        public void getIngredients(IIngredients ingredients) {
            ingredients.setInputs(VanillaTypes.ITEM, Arrays.asList(new ItemStack[] { inputEmptyCell, inputUU }));
            ingredients.setOutput(VanillaTypes.ITEM, outputItem);
        }
    }

}
