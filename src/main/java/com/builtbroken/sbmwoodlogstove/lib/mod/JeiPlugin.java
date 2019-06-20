package com.builtbroken.sbmwoodlogstove.lib.mod;

import com.builtbroken.sbmwoodlogstove.WoodLogStove;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class JeiPlugin implements IModPlugin
{
	@Override
	public void register(IModRegistry registry)
	{
		registry.addRecipeCatalyst(new ItemStack(WoodLogStove.stove), VanillaRecipeCategoryUid.SMELTING);
	}
}