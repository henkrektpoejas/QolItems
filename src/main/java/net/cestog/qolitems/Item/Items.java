package net.cestog.qolitems.Item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.fluids.FluidType;
import net.minecraft.world.level.material.Fluids;

import java.util.function.Supplier;

import static net.cestog.qolitems.Qolitems.MODID;

public class Items {

    // DeferredRegister for items
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    // Shears registrations
    public static final DeferredItem<Item> WOODEN_SHEAR = ITEMS.register("wooden_shear",
            () -> new ShearsItem(new Item.Properties()
                    .durability(140)));
    public static final DeferredItem<Item> STONE_SHEAR = ITEMS.register("stone_shear",
            () -> new ShearsItem(new Item.Properties()
                    .durability(180)));
    public static final DeferredItem<Item> COPPER_SHEAR = ITEMS.register("copper_shear",
            () -> new ShearsItem(new Item.Properties()
                    .durability(210)));
    public static final DeferredItem<Item> IRON_SHEAR = ITEMS.register("iron_shear",
            () -> new ShearsItem(new Item.Properties()
                    .durability(240)));
    public static final DeferredItem<Item> GOLDEN_SHEAR = ITEMS.register("golden_shear",
            () -> new ShearsItem(new Item.Properties()
                    .durability(280)));
    public static final DeferredItem<Item> DIAMOND_SHEAR = ITEMS.register("diamond_shear",
            () -> new ShearsItem(new Item.Properties()
                    .durability(320)));
    public static final DeferredItem<Item> EMERALD_SHEAR = ITEMS.register("emerald_shear",
            () -> new ShearsItem(new Item.Properties()
                    .durability(360)));

    public static final DeferredItem<Item> WOODEN_BUCKET = ITEMS.register("wooden_bucket",
            () -> new WoodenBucketItem(new Item.Properties().stacksTo(1).durability(10), Fluids.EMPTY));

    public static final DeferredItem<Item> WOODEN_WATER_BUCKET = ITEMS.register("wooden_water_bucket",
            () -> new WoodenBucketItem(new Item.Properties().stacksTo(1).durability(10), Fluids.WATER));

    public static final DeferredItem<Item> WOODEN_LAVA_BUCKET = ITEMS.register("wooden_lava_bucket",
            () -> new WoodenBucketItem(new Item.Properties().stacksTo(1).durability(10), Fluids.LAVA));



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}