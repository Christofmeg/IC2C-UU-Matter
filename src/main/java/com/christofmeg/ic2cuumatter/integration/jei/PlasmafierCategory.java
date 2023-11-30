package com.christofmeg.ic2cuumatter.integration.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated.StartDirection;
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

public class PlasmafierCategory implements IRecipeCategory<PlasmafierCategory.PlasmafierRecipe> {

    public static final RecipeType<PlasmafierRecipe> TYPE = new RecipeType<>(
            new ResourceLocation("ic2cuumatter", "plasmafier"), PlasmafierRecipe.class);
    public static final ResourceLocation plasmafierTexture = new ResourceLocation("ic2cuumatter",
            "textures/gui/plasmafier.png");

    public static final ResourceLocation slotVanilla = new ResourceLocation("jei",
            "textures/gui/slot.png");

    IDrawable background;
    IDrawable icon;
    IDrawable press1;
    IDrawable press2;
    IDrawable press3;
    IDrawable tank1;
    IDrawable tank2;
    IDrawable tank3;
    IDrawable plasma;
    IDrawable glass;
    IDrawable slot;
    IDrawable frame;
    IDrawable frameBottom;
    IDrawable frameTop;

    int input1X = 31;
    int input1Y = 21;
    int input2X = 103;
    int input2Y = 9;
    int outputX = 103;
    int outputY = 31;

    public PlasmafierCategory(IGuiHelper helper, ResourceLocation texture, ItemLike itemStack) {
        background = helper.createBlankDrawable(132, 64);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(itemStack));
        slot = helper.drawableBuilder(slotVanilla, 0, 0, 18, 18).setTextureSize(18, 18).build();

        frame = helper.drawableBuilder(texture, 78, 15, 20, 52).build();
        frameBottom = helper.drawableBuilder(texture, 79, 67, 18, 1).build();
        frameTop = helper.drawableBuilder(texture, 79, 14, 18, 1).build();

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
    public @NotNull RecipeType<PlasmafierRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("block.ic2.plasmafier");
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
    public void setRecipe(IRecipeLayoutBuilder iRecipeLayoutBuilder, PlasmafierRecipe recipe, @NotNull IFocusGroup iFocusGroup) {
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT, input1X, input1Y).addIngredients(recipe.tool());
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT, input2X, input2Y).addIngredients(recipe.block());
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.OUTPUT, outputX, outputY).addItemStack(recipe.output());
    }

    @Override
    public void draw(@NotNull PlasmafierRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull PoseStack stack, double mouseX,
                     double mouseY) {
        slot.draw(stack, input1X - 1, input1Y - 1);
        slot.draw(stack, input2X - 1, input2Y - 1);
        slot.draw(stack, outputX - 1, outputY - 1);

        frame.draw(stack, 65, 1);
        frameBottom.draw(stack, 66, 53);
        frameTop.draw(stack, 66, 0);

        press1.draw(stack, 69, 45);
        press2.draw(stack, 69, 46);
        press2.draw(stack, 69, 47);
        press2.draw(stack, 69, 48);
        press3.draw(stack, 69, 49);
        tank1.draw(stack, 69, 4);
        tank2.draw(stack, 69, 5);
        tank3.draw(stack, 69, 8);
        plasma.draw(stack, 69, 9);
        glass.draw(stack, 68, 4);

        Font font = Minecraft.getInstance().font;
        Component tier = Component.translatable("translation.ic2cuumatter.tier");
        Component EV = Component.translatable("translation.ic2cuumatter.tier.ev");
        Component tierEV = Component.translatable("translation.ic2cuumatter.format2", tier, EV);
        font.draw(stack, tierEV, 0.0F, 0.0F, 4210752);

        Component ticks = Component.translatable("translation.ic2cuumatter.ticks");
        Component ticksFormat = Component.translatable("translation.ic2cuumatter.format2", ticks, "2,500");
        font.draw(stack, ticksFormat, 0.0F, 57.0F, 4210752);
        font.draw(stack, Component.nullToEmpty("2,048 EU/p"), 79.0F, 57.0F, 4210752);
    }

    record PlasmafierRecipe(Ingredient tool, Ingredient block, ItemStack output) {
    }

}