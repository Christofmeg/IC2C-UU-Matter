package com.christofmeg.ic2cuumatter.integration.jei;

import java.util.Arrays;

import ic2.core.block.machines.containers.ev.PlasmafierContainer;
import ic2.core.block.machines.containers.hv.MassFabricatorContainer;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Items;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

@JeiPlugin
public class IC2JEIPlugin implements IModPlugin {

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

}