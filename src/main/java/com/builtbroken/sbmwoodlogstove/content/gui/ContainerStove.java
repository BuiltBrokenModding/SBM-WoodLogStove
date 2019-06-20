package com.builtbroken.sbmwoodlogstove.content.gui;

import com.builtbroken.sbmwoodlogstove.content.TileEntityStove;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.SlotItemHandler;

/**
 * Created by Dark(DarkGuardsman, Robert) on 6/9/2019.
 */
public class ContainerStove extends Container
{
    private final TileEntityStove tileFurnace;
    private int cookTime;
    private int totalCookTime;
    private int furnaceBurnTime;
    private int currentItemBurnTime;

    public ContainerStove(InventoryPlayer playerInventory, TileEntityStove furnaceInventory)
    {
        this.tileFurnace = furnaceInventory;
        this.addSlotToContainer(new SlotItemHandler(furnaceInventory.inventory, 0, 56, 17));
        this.addSlotToContainer(new SlotFurnaceFuel(furnaceInventory.inventory, 1, 56, 53));
        this.addSlotToContainer(new SlotFurnaceOutput(playerInventory.player, furnaceInventory.inventory, 2, 116, 35));

        //Player inventory
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        //Player hotbar
        for (int k = 0; k < 9; ++k)
        {
            this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }

    @Override
    public void addListener(IContainerListener listener)
    {
        super.addListener(listener);
        listener.sendWindowProperty(this, 0, this.tileFurnace.furnaceBurnTime);
        listener.sendWindowProperty(this, 1, this.tileFurnace.currentItemBurnTime);
        listener.sendWindowProperty(this, 2, this.tileFurnace.cookTime);
        listener.sendWindowProperty(this, 3, this.tileFurnace.totalCookTime);
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.listeners.size(); ++i)
        {
            IContainerListener icontainerlistener = this.listeners.get(i);

            if (this.cookTime != this.tileFurnace.cookTime)
            {
                icontainerlistener.sendWindowProperty(this, 2, this.tileFurnace.cookTime);
            }

            if (this.furnaceBurnTime != this.tileFurnace.furnaceBurnTime)
            {
                icontainerlistener.sendWindowProperty(this, 0, this.tileFurnace.furnaceBurnTime);
            }

            if (this.currentItemBurnTime != this.tileFurnace.currentItemBurnTime)
            {
                icontainerlistener.sendWindowProperty(this, 1, this.tileFurnace.currentItemBurnTime);
            }

            if (this.totalCookTime != this.tileFurnace.totalCookTime)
            {
                icontainerlistener.sendWindowProperty(this, 3, this.tileFurnace.totalCookTime);
            }
        }

        this.cookTime = this.tileFurnace.cookTime;
        this.furnaceBurnTime = this.tileFurnace.furnaceBurnTime;
        this.currentItemBurnTime = this.tileFurnace.currentItemBurnTime;
        this.totalCookTime = this.tileFurnace.totalCookTime;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        //this.tileFurnace.setField(id, data);
        if(id == 0)
        {
            tileFurnace.furnaceBurnTime = data;
        }
        else if(id == 1)
        {
            tileFurnace.currentItemBurnTime = data;
        }
        else if(id == 2)
        {
            tileFurnace.cookTime = data;
        }
        else if(id == 3)
        {
            tileFurnace.totalCookTime = data;
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index == 2)
            {
                if (!this.mergeItemStack(itemstack1, 3, 39, true))
                {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (index != 1 && index != 0)
            {
                if (!FurnaceRecipes.instance().getSmeltingResult(itemstack1).isEmpty())
                {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (TileEntityFurnace.isItemFuel(itemstack1))
                {
                    if (!this.mergeItemStack(itemstack1, 1, 2, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (index >= 3 && index < 30)
                {
                    if (!this.mergeItemStack(itemstack1, 30, 39, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (index >= 30 && index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 3, 39, false))
            {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount())
            {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }
}
