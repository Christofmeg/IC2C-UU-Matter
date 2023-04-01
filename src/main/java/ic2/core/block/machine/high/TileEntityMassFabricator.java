package ic2.core.block.machine.high;

import ic2.api.classic.network.adv.NetworkField;
import ic2.api.classic.network.adv.NetworkField.BitLevel;
import ic2.api.classic.tile.IInfoTile;
import ic2.api.classic.tile.machine.IFuelMachine;
import ic2.api.classic.tile.machine.IProgressMachine;
import ic2.core.RotationList;
import ic2.core.block.base.tile.TileEntityElecMachine;
import ic2.core.block.base.util.comparator.ComparatorManager;
import ic2.core.block.base.util.comparator.comparators.ComparatorFuelMachine;
import ic2.core.block.base.util.comparator.comparators.ComparatorProgress;
import ic2.core.block.base.util.info.FuelMachineInfo;
import ic2.core.block.base.util.info.ProgressInfo;
import ic2.core.block.base.util.info.misc.IPumpTile;
import ic2.core.block.machine.high.container.ContainerPlasmafier;
import ic2.core.inventory.base.IHasGui;
import ic2.core.inventory.container.ContainerIC2;
import ic2.core.inventory.filters.CommonFilters;
import ic2.core.inventory.gui.GuiComponentContainer;
import ic2.core.inventory.management.AccessRule;
import ic2.core.inventory.management.InventoryHandler;
import ic2.core.inventory.management.SlotType;
import ic2.core.platform.lang.components.base.LocaleComp;
import ic2.core.platform.lang.storage.Ic2BlockLang;
import ic2.core.platform.registry.Ic2Items;
import ic2.core.util.misc.StackUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

public class TileEntityPlasmafier extends TileEntityElecMachine implements ITickable, IPumpTile, IFuelMachine, IProgressMachine, IHasGui {
    @NetworkField(
            index = 7,
            compression = BitLevel.Bit8
    )
    public int uuMatter = 0;
    @NetworkField(
            index = 8,
            compression = BitLevel.Bit16
    )
    public int plasma;

    public TileEntityPlasmafier() {
        super(3, 2048);
        this.maxEnergy = 5120000;
        this.addGuiFields(new String[]{"uuMatter"});
        this.addNetworkFields(new String[]{"plasma"});
        this.addInfos(new IInfoTile.InfoComponent[]{new FuelMachineInfo(this), new ProgressInfo(this)});
    }

    protected void addSlots(InventoryHandler handler) {
        handler.registerDefaultSideAccess(AccessRule.Both, RotationList.ALL);
        handler.registerDefaultSlotAccess(AccessRule.Import, new int[]{0, 1});
        handler.registerDefaultSlotAccess(AccessRule.Export, new int[]{2});
        handler.registerDefaultSlotsForSide(RotationList.HORIZONTAL, new int[]{0});
        handler.registerDefaultSlotsForSide(RotationList.UP, new int[]{1});
        handler.registerDefaultSlotsForSide(RotationList.DOWN, new int[]{2});
        handler.registerInputFilter(CommonFilters.uuMatter, new int[]{0});
        handler.registerInputFilter(CommonFilters.EmptyCell, new int[]{1});
        handler.registerSlotType(SlotType.Fuel, new int[]{0});
        handler.registerSlotType(SlotType.Input, new int[]{1});
        handler.registerSlotType(SlotType.Output, new int[]{2});
    }

    protected void addComparators(ComparatorManager manager) {
        super.addComparators(manager);
        manager.addComparatorMode(new ComparatorFuelMachine(this));
        manager.addComparatorMode(new ComparatorProgress(this));
    }

    public float getFuel() {
        return (float)this.uuMatter;
    }

    public float getMaxFuel() {
        return 300.0F;
    }

    public float getProgress() {
        return (float)this.plasma;
    }

    public float getMaxProgress() {
        return 10000.0F;
    }

    public int getPumpCharge() {
        return this.plasma;
    }

    public int getMaxPumpCharge() {
        return 10000;
    }

    public LocaleComp getBlockName() {
        return Ic2BlockLang.plasmafire;
    }

    public ContainerIC2 getGuiContainer(EntityPlayer player) {
        return new ContainerPlasmafier(player.inventory, this);
    }

    public Class<? extends GuiScreen> getGuiClass(EntityPlayer player) {
        return GuiComponentContainer.class;
    }

    public void onGuiClosed(EntityPlayer player) {
    }

    public boolean canInteractWith(EntityPlayer player) {
        return !this.isInvalid();
    }

    public boolean hasGui(EntityPlayer player) {
        return true;
    }

    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.uuMatter = nbt.getInteger("Matter");
        this.plasma = nbt.getInteger("Plasma");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("Matter", this.uuMatter);
        nbt.setInteger("Plasma", this.plasma);
        return nbt;
    }

    public boolean supportsNotify() {
        return false;
    }

    public void update() {
        if (this.world.getTotalWorldTime() % 40L == 0L) {
            this.refillMatter();
        }

        if (this.hasEnergy(10240) && this.uuMatter > 0 && this.plasma < 10000) {
            this.useEnergy(10240);
            ++this.plasma;
            --this.uuMatter;
            this.getNetwork().updateTileEntityField(this, "plasma");
            this.getNetwork().updateTileGuiField(this, "uuMatter");
        }

        if (this.plasma >= 1000 && this.world.getTotalWorldTime() % 50L == 0L) {
            this.fillCell();
        }

    }

    public void fillCell() {
        boolean empty = ((ItemStack)this.inventory.get(2)).isEmpty();
        if (StackUtil.isStackEqual((ItemStack)this.inventory.get(1), Ic2Items.emptyCell) && (empty || StackUtil.isStackEqual((ItemStack)this.inventory.get(2), Ic2Items.plasmaCell) && ((ItemStack)this.inventory.get(2)).getCount() < ((ItemStack)this.inventory.get(2)).getMaxStackSize())) {
            ((ItemStack)this.inventory.get(1)).shrink(1);
            if (((ItemStack)this.inventory.get(2)).isEmpty()) {
                this.inventory.set(2, Ic2Items.plasmaCell.copy());
            } else {
                ((ItemStack)this.inventory.get(2)).grow(1);
            }

            this.plasma -= 1000;
            this.getNetwork().updateTileEntityField(this, "plasma");
        }

    }

    public void refillMatter() {
        if (this.uuMatter <= 50 && !((ItemStack)this.inventory.get(0)).isEmpty()) {
            if (StackUtil.isStackEqual((ItemStack)this.inventory.get(0), Ic2Items.uuMatter)) {
                this.uuMatter += 100;
                ((ItemStack)this.inventory.get(0)).shrink(1);
                this.getNetwork().updateTileGuiField(this, "uuMatter");
            }

        }
    }
}