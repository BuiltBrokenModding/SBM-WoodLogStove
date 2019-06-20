package com.builtbroken.sbmwoodlogstove;

import com.builtbroken.sbmwoodlogstove.content.BlockStove;
import com.builtbroken.sbmwoodlogstove.content.TileEntityStove;
import com.builtbroken.sbmwoodlogstove.content.gui.GuiHandler;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = WoodLogStove.MODID, name = WoodLogStove.MOD_NAME, version = WoodLogStove.VERSION)
@Mod.EventBusSubscriber(modid = WoodLogStove.MODID)
public class WoodLogStove
{
	public static final String MOD_NAME = "[SBM] Wood Log Stove";
	public static final String MODID = "sbmwoodlogstove";

	public static final String STOVE_ID = MODID + ":stove";
	public static final String CHARRED_STOVE_ID = MODID + ":charred_stove";

	public static final String MAJOR_VERSION = "@MAJOR@";
	public static final String MINOR_VERSION = "@MINOR@";
	public static final String REVISION_VERSION = "@REVIS@";
	public static final String BUILD_VERSION = "@BUILD@";
	public static final String MC_VERSION = "@MC@";
	public static final String VERSION = MC_VERSION + "-" + MAJOR_VERSION + "." + MINOR_VERSION + "." + REVISION_VERSION + "." + BUILD_VERSION;

	@Mod.Instance(MODID)
	public static WoodLogStove INSTANCE;

	@GameRegistry.ObjectHolder(WoodLogStove.STOVE_ID)
	public static BlockStove stove;
	@GameRegistry.ObjectHolder(WoodLogStove.CHARRED_STOVE_ID)
	public static BlockStove charredStove;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new GuiHandler());
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().register(new BlockStove(false).setRegistryName(STOVE_ID).setTranslationKey(STOVE_ID));
		event.getRegistry().register(new BlockStove(true).setRegistryName(CHARRED_STOVE_ID).setTranslationKey(CHARRED_STOVE_ID));
		GameRegistry.registerTileEntity(TileEntityStove.class, new ResourceLocation(WoodLogStove.MODID, "stove"));
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().register(new ItemBlock(stove).setRegistryName(stove.getRegistryName()));
		event.getRegistry().register(new ItemBlock(charredStove).setRegistryName(charredStove.getRegistryName()));
	}
}
