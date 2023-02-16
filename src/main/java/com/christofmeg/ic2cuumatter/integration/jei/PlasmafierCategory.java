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

public class PlasmafierCategory implements IRecipeCategory<PlasmafierCategory.PlasmafierRecipe> {

	public static final RecipeType<PlasmafierRecipe> TYPE = new RecipeType<>(
			new ResourceLocation("ic2cuumatter", "plasmafier"), PlasmafierRecipe.class);
	public static final ResourceLocation plasmafierTexture = new ResourceLocation("ic2cuumatter",
			"textures/gui/plasmafier.png");

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

	public PlasmafierCategory(IGuiHelper helper, ResourceLocation texture, ItemLike itemStack) {
		background = helper.createDrawable(texture, 13, 14, 132, 64);
		icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(itemStack));
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
	public RecipeType<PlasmafierRecipe> getRecipeType() {
		return TYPE;
	}

	@Override
	public Component getTitle() {
		return Component.translatable("block.ic2.plasmafier");
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
	public void setRecipe(IRecipeLayoutBuilder iRecipeLayoutBuilder, PlasmafierRecipe recipe, IFocusGroup iFocusGroup) {
		iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT, 31, 21).addIngredients(recipe.tool());
		iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT, 103, 9).addIngredients(recipe.block());
		iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.OUTPUT, 103, 31).addItemStack(recipe.output());
	}

	@SuppressWarnings("resource")
	@Override
	public void draw(PlasmafierRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX,
			double mouseY) {
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
		Component tierEV = Component.translatable("translation.ic2cuumatter.tier.ev");
		font.draw(stack, tierEV, 0.0F, 0.0F, 4210752);

		Component ticks = Component.translatable("translation.ic2cuumatter.ticks");
		font.draw(stack, ticks, 0.0F, 57.0F, 4210752);
		font.draw(stack, Component.nullToEmpty("2,048 EU/p"), 79.0F, 57.0F, 4210752);
	}

	record PlasmafierRecipe(Ingredient tool, Ingredient block, ItemStack output) {
	}

}