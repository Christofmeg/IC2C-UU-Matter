package com.christofmeg.ic2cuumatter;

import ic2.api.recipe.*;
import ic2.core.Ic2Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Map;

public class ModRecipes {

    public static IMachineRecipeManager matterAmplifier;

    public static void init() {
        for (Map.Entry<IRecipeInput, RecipeOutput> list : Recipes.matterAmplifier.getRecipes().entrySet()) {
            if (list != null) {
                for (ItemStack input : list.getKey().getInputs()) {
                    addAmplifier(new RecipeInputItemStack(input), list.getValue().metadata.getInteger("amplification"));
                }
            }
        }
    }

    public static void addAmplifier(IRecipeInput input, int amplification) {
        NBTTagCompound metadata = new NBTTagCompound();
        metadata.setInteger("amplification", amplification);
        ModRecipes.matterAmplifier.addRecipe(input, metadata, Ic2Items.uuMatterCell);
    }

}
