package com.builtbroken.sbmwoodlogstove.client;

import com.builtbroken.sbmwoodlogstove.WoodLogStove;
import com.builtbroken.sbmwoodlogstove.content.TileEntityStove;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid=WoodLogStove.MODID)
public class ClientReg
{
	@SubscribeEvent
	public static void onModelRegistry(ModelRegistryEvent event)
	{
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(WoodLogStove.stove), 0, new ModelResourceLocation(WoodLogStove.stove.getRegistryName(), "normal"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(WoodLogStove.charredStove), 0, new ModelResourceLocation(WoodLogStove.charredStove.getRegistryName(), "normal"));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityStove.class, new TileEntityStoveRenderer());
	}
}
