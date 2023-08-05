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
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class MassFabricatorCategory implements IRecipeCategory<MassFabricatorCategory.MassFabricatorRecipe> {

    public static final RecipeType<MassFabricatorRecipe> TYPE = new RecipeType<>(
            new ResourceLocation("ic2cuumatter", "mass_fabricator"), MassFabricatorRecipe.class);

    public static final ResourceLocation slotVanilla = new ResourceLocation("jei",
            "textures/gui/slot.png");
    public static final ResourceLocation guiVanilla = new ResourceLocation("jei",
            "textures/gui/gui_vanilla.png");

    IDrawable background;
    IDrawable icon;
    IDrawable slot;
    IDrawable bigSlot;

    public MassFabricatorCategory(IGuiHelper helper, ItemLike itemStack) {
        background = helper.createBlankDrawable(133, 69);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(itemStack));
        slot = helper.drawableBuilder(slotVanilla, 0, 0, 18, 18).setTextureSize(18, 18).build();
        bigSlot = helper.drawableBuilder(guiVanilla, 90, 74, 26, 26).build();
    }

    @Override
    public @NotNull RecipeType<MassFabricatorRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("block.ic2.mass_fabricator");
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return background;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder iRecipeLayoutBuilder, MassFabricatorRecipe recipe,
                          @NotNull IFocusGroup iFocusGroup) {
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT, 67, 42).addIngredients(recipe.item());
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.OUTPUT, 67, 5).addItemStack(recipe.output());
    }

    @Override
    public void draw(MassFabricatorRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull PoseStack stack, double mouseX,
                     double mouseY) {

        slot.draw(stack, 66, 41);
        bigSlot.draw(stack, 62, 0);

        Font font = Minecraft.getInstance().font;
        Component tier = Component.translatable("translation.ic2cuumatter.tier");
        Component HV = Component.translatable("translation.ic2cuumatter.tier.hv");
        Component tierHV = Component.translatable("translation.ic2cuumatter.format2", tier, HV);
        font.draw(stack, tierHV, 0.0F, 0.0F, 4210752);

        Component energy = Component.translatable("translation.ic2cuumatter.energy");
        Component energyFormat = Component.translatable("translation.ic2cuumatter.format1", energy);
        font.draw(stack, energyFormat, 0.0F, 52.0F, 4210752);

        font.draw(stack, Component.nullToEmpty("7,000,000 EU"), 0.0F, 62.0F, 4210752);
        font.draw(stack, Component.nullToEmpty("512 EU/p"), 88.0F, 62.0F, 4210752);

        if (!Objects.equals(recipe.getEnergy(), "0")) {
            Component amplifier = Component.translatable("translation.ic2cuumatter.amplifier");
            Component amplifierFormat = Component.translatable("translation.ic2cuumatter.format1", amplifier);
            font.draw(stack, amplifierFormat, 90.0F, 20.0F, 4210752);
            if (Objects.equals(recipe.getEnergy(), "100,000")) {
                font.draw(stack, Component.nullToEmpty("+ " + recipe.getEnergy()), 86.0F, 30.0F, 4210752);
            } else if (Objects.equals(recipe.getEnergy(), "45,000")) {
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