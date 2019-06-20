package com.builtbroken.sbmwoodlogstove.client;

import org.lwjgl.opengl.GL11;

import com.builtbroken.sbmwoodlogstove.content.BlockStove;
import com.builtbroken.sbmwoodlogstove.content.TileEntityStove;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.ForgeHooksClient;

public class TileEntityStoveRenderer extends TileEntitySpecialRenderer<TileEntityStove>
{
	@Override
	public void render(TileEntityStove te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
	{
		EnumFacing facing = te.getWorld().getBlockState(te.getPos()).getValue(BlockStove.FACING);
		ItemStack fuel = te.inventory.getStackInSlot(1);
		ItemStack input = te.inventory.getStackInSlot(0);
		ItemStack output = te.inventory.getStackInSlot(2);
		IBakedModel modelFuel = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(fuel, te.getWorld(), null);
		IBakedModel modelInput = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(input, te.getWorld(), null);
		IBakedModel modelOutput = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(output, te.getWorld(), null);

		GlStateManager.enableRescaleNormal();
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
		GlStateManager.enableBlend();
		RenderHelper.enableStandardItemLighting();
		GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

		//render fuel
		GlStateManager.pushMatrix();
		GlStateManager.translate(x + 0.5D, y + 0.01F, z + 0.5D);
		GlStateManager.scale(0.5F, 0.5F, 0.5F);
		modelFuel = ForgeHooksClient.handleCameraTransforms(modelFuel, TransformType.GROUND, false);
		GlStateManager.rotate(-90, 1, 0, 0);
		handleItemRotation(facing);
		Minecraft.getMinecraft().getRenderItem().renderItem(fuel, modelFuel);
		GlStateManager.popMatrix();

		//render input
		GlStateManager.pushMatrix();
		handleItemPosition(facing, x, y, z, 0.4D, 0.6D);
		GlStateManager.scale(0.5F, 0.5F, 0.5F);
		modelInput = ForgeHooksClient.handleCameraTransforms(modelInput, TransformType.GROUND, false);
		GlStateManager.rotate(-90, 1, 0, 0);
		handleItemRotation(facing);
		Minecraft.getMinecraft().getRenderItem().renderItem(input, modelInput);
		GlStateManager.popMatrix();

		//render output
		GlStateManager.pushMatrix();
		handleItemPosition(facing, x, y, z, 0.6D, 0.4D);
		GlStateManager.scale(0.5F, 0.5F, 0.5F);
		modelOutput = ForgeHooksClient.handleCameraTransforms(modelOutput, TransformType.GROUND, false);
		GlStateManager.rotate(-90, 1, 0, 0);
		handleItemRotation(facing);
		Minecraft.getMinecraft().getRenderItem().renderItem(output, modelOutput);
		GlStateManager.popMatrix();

		GlStateManager.disableRescaleNormal();
		GlStateManager.disableBlend();
	}

	private void handleItemRotation(EnumFacing facing)
	{
		if(facing == EnumFacing.NORTH)
			GlStateManager.rotate(180, 0, 0, 1);
		else if(facing == EnumFacing.EAST)
			GlStateManager.rotate(90, 0, 0, 1);
		else if(facing == EnumFacing.WEST)
			GlStateManager.rotate(-90, 0, 0, 1);
	}

	private void handleItemPosition(EnumFacing facing, double x, double y, double z, double offset1, double offset2)
	{
		if(facing == EnumFacing.NORTH)
			GlStateManager.translate(x + offset2, y + 0.355D, z + offset2);
		else if(facing == EnumFacing.EAST)
			GlStateManager.translate(x + offset1, y + 0.355D, z + offset2);
		else if(facing == EnumFacing.SOUTH)
			GlStateManager.translate(x + offset1, y + 0.355D, z + offset1);
		else if(facing == EnumFacing.WEST)
			GlStateManager.translate(x + offset2, y + 0.355D, z + offset1);
	}
}
