package ru.dest.library.event;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockInteractEvent extends PlayerInteractEvent {

    private static final HandlerList handlers = new HandlerList();

    public BlockInteractEvent(@NotNull Player who, @NotNull Action action, @Nullable ItemStack item, @NotNull Block clickedBlock, @NotNull BlockFace clickedFace, @Nullable EquipmentSlot hand) {
        super(who, action, item, clickedBlock, clickedFace, hand);
    }

    @Override
    public boolean hasBlock() {
        return true;
    }

    @NotNull
    @Override
    public Block getClickedBlock() {
        assert super.getClickedBlock() != null;
        return super.getClickedBlock();
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isCancelled() {
        return super.isCancelled();
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static @NotNull HandlerList getHandlerList(){
        return handlers;
    }
}
