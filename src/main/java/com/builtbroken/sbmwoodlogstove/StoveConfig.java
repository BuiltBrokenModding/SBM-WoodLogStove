package com.builtbroken.sbmwoodlogstove;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Name;

@Config(modid=WoodLogStove.MODID)
public class StoveConfig
{
	@Name("food_cook_time")
	@Comment("How long it will take to cook a food item (200 is the vanilla furnace's cook time)")
	public static int foodCookTime = 200;

	@Name("other_cook_time")
	@Comment("How long it will take to cook anything that is not food")
	public static int otherCookTime = 800;

	@Name("durability")
	@Comment("The amount of ticks the stove can burn for until it turns into its charred version (20 ticks = 1 second)")
	public static int stoveDurability = 14400; //12 minutes

	@Name("food_items")
	@Comment("Add registry names (e.g. minecraft:porkchop) here for items you want the stove to view as food")
	public static String[] foodItems = new String[0];
}
