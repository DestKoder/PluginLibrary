package ru.dest.library.item;

import lombok.Getter;
import org.bukkit.Material;
@Getter
public class ItemProps {

    private final Material material;
    private int model = 0;
    private int maxStackSize;

    public ItemProps(Material material){
        this.material = material;
        maxStackSize = material.getMaxStackSize();
    }


    public ItemProps model(int model){
        this.model =  model;
        return this;
    }

    public ItemProps stacksTo(int maxStackSize){
        this.maxStackSize = maxStackSize;
        return this;
    }

}
