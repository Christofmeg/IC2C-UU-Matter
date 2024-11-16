package com.christofmeg.ic2cuumatter.nei;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import com.christofmeg.ic2cuumatter.ModRecipes;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;
import ic2.core.Ic2Items;
import ic2.core.util.StackUtil;
import ic2.neiIntegration.core.MachineRecipeHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MassFabricatorCategory extends MachineRecipeHandler {

    public MassFabricatorCategory() {
        this.arecipes.add(createEmptyInputRecipe());
    }

    @Override
    public String getRecipeName() {
        return Ic2Items.massFabricator.getDisplayName();
    }

    public String getRecipeId() {
        return "ic2.matter";
    }

    @Override
    public String getOverlayIdentifier() {
        return "matter";
    }

    @Override
    public String getGuiTexture() {
        return "ic2:textures/guiSprites/GUIMatter.png";
    }

    public Map<IRecipeInput, RecipeOutput> getRecipeList() {
        return ModRecipes.matterAmplifier.getRecipes();
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(this.getRecipeId())) {
            for (Map.Entry<IRecipeInput, RecipeOutput> list : this.getRecipeList().entrySet()) {
                if (list != null) {
                    this.arecipes.add(new CachedIORecipe(list.getKey(), list.getValue().items.get(0), list.getValue().metadata.getInteger("amplification")));
                }
            }
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        for (Map.Entry<IRecipeInput, RecipeOutput> list : this.getRecipeList().entrySet()) {
            if (list != null) {
                for (ItemStack output : list.getValue().items) {
                    if (output != null) {
                        if (NEIServerUtils.areStacksSameTypeCrafting(output, result)) {
                            this.arecipes.add(new CachedIORecipe(list.getKey(), list.getValue().items.get(0), list.getValue().metadata.getInteger("amplification")));
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        for (Map.Entry<IRecipeInput, RecipeOutput> list : this.getRecipeList().entrySet()) {
            if (list != null && list.getKey() != null) {
                if (list.getKey().matches(ingredient)) {
                    this.arecipes.add(new CachedIORecipe(list.getKey(), list.getValue().items.get(0), list.getValue().metadata.getInteger("amplification")));
                }
            }
        }
    }

    public class CachedIORecipe extends CachedRecipe {
        public List<PositionedStack> ingredients = new ArrayList<>();
        public ItemStack output;
        public int amplifierValue;

        @Override
        public List<PositionedStack> getIngredients() {
            return this.getCycledIngredients(MassFabricatorCategory.this.cycleticks / 20, this.ingredients);
        }

        @Override
        public PositionedStack getResult() {
            return new PositionedStack(output, 109, 7);
        }

        public int getAmplifierValue() {
            return this.amplifierValue;
        }

        public CachedIORecipe(IRecipeInput input, ItemStack output, int amplifierValue) {
            List<ItemStack> items = new ArrayList<>();
            for (ItemStack item : input.getInputs()) {
                items.add(StackUtil.copyWithSize(item, input.getAmount()));
            }
            this.ingredients.add(new PositionedStack(items, 109, 43));
            this.output = output;
            this.amplifierValue = amplifierValue;
        }

        public CachedIORecipe(ItemStack output, int amplifierValue) {
            this.output = output;
            this.amplifierValue = amplifierValue;
        }
    }

    private CachedIORecipe createEmptyInputRecipe() {
        return new CachedIORecipe(Ic2Items.uuMatterCell, 0);
    }

    @Override
    public void drawExtras(int recipeIndex) {
        FontRenderer font = Minecraft.getMinecraft().fontRenderer;
        int color = 4210752;
        String euScrap = NumberFormat.getIntegerInstance().format(7000000 / 6).replace(' ', ',');
        String euNoScrap = NumberFormat.getIntegerInstance().format(7000000).replace(' ', ',');
        
        String tierHV = I18n.format("translation.ic2cuumatter.tier.hv");
        font.drawString(tierHV, 0, 5, color, false);

        font.drawString("512 EU/t", 0, 15, color, false);

        String energy = I18n.format("translation.ic2cuumatter.energy");
        font.drawString(energy, 0, 45, color, false);

        MassFabricatorCategory.CachedIORecipe recipe = (MassFabricatorCategory.CachedIORecipe)this.arecipes.get(recipeIndex);
        int amplifierValue = recipe.getAmplifierValue();

        if (amplifierValue != 0) {
            String amplifier = I18n.format("translation.ic2cuumatter.amplifier");
            font.drawString(amplifier, 0, 25, color, false);
            if (amplifierValue == 100000) {
                font.drawString(I18n.format("" + amplifierValue), 0, 35, color, false);
            }
            if (amplifierValue == 45000) {
                font.drawString(I18n.format("" + amplifierValue), 0, 35, color, false);
            }
            if (amplifierValue == 5000) {
                font.drawString(I18n.format("" + amplifierValue), 0, 35, color, false);
            }
            font.drawString(euScrap + " EU", 0, 55, color, false);
        } else {
            font.drawString(euNoScrap + " EU", 0, 55, color, false);
        }
    }

}