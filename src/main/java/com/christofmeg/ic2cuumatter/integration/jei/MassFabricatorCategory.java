package com.christofmeg.ic2cuumatter.integration.jei;

import com.mojang.blaze3d.vertex.PoseStack;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class MassFabricatorCategory implements IRecipeCategory<MassFabricatorCategory.MassFabricatorRecipe> {

    public static final RecipeType<MassFabricatorRecipe> TYPE = new RecipeType<>(
            new ResourceLocation("ic2cuumatter", "mass_fabricator"), MassFabricatorRecipe.class);

    IDrawable background;
    IDrawable icon;

    public MassFabricatorCategory(IGuiHelper helper, ResourceLocation texture, ItemLike itemStack) {
        background = helper.createDrawable(texture, 13, 14, 133, 69);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(itemStack));
    }

    @Override
    public RecipeType<MassFabricatorRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.ic2.mass_fabricator");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder iRecipeLayoutBuilder, MassFabricatorRecipe recipe,
            IFocusGroup iFocusGroup) {
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT, 67, 42).addIngredients(recipe.item());
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.OUTPUT, 67, 5).addItemStack(recipe.output());
    }

    @SuppressWarnings("resource")
    @Override
    public void draw(MassFabricatorRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX,
            double mouseY) {

        Font font = Minecraft.getInstance().font;
        Component tierHV = Component.translatable("translation.ic2cuumatter.tier.hv");
        font.draw(stack, tierHV, 0.0F, 0.0F, 4210752);

        Component energy = Component.translatable("translation.ic2cuumatter.energy");
        font.draw(stack, energy, 0.0F, 52.0F, 4210752);

        font.draw(stack, Component.nullToEmpty("7,000,000 EU"), 0.0F, 62.0F, 4210752);
        font.draw(stack, Component.nullToEmpty("512 EU/p"), 88.0F, 62.0F, 4210752);

        if (recipe.getEnergy() != "0") {
            Component amplifier = Component.translatable("translation.ic2cuumatter.amplifier");
            font.draw(stack, amplifier, 90.0F, 20.0F, 4210752);
            if (recipe.getEnergy() == "100,000") {
                font.draw(stack, Component.nullToEmpty("+ " + recipe.getEnergy()), 86.0F, 30.0F, 4210752);
            } else if (recipe.getEnergy() == "45,000") {
                font.draw(stack, Component.nullToEmpty("+ " + recipe.getEnergy()), 92.0F, 30.0F, 4210752);
            } else {
                font.draw(stack, Component.nullToEmpty("+ " + recipe.getEnergy()), 98.0F, 30.0F, 4210752);
            }
        }
    }

    record MassFabricatorRecipe(Ingredient item, ItemStack output, String energy) {
        public String getEnergy() {
            return energy;
        }
    }

}