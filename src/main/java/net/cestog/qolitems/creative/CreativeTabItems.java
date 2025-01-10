package net.cestog.qolitems.creative;


import net.cestog.qolitems.Item.Items;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;


import java.util.function.Supplier;


import static net.cestog.qolitems.Qolitems.MODID;

public class CreativeTabItems {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // First CreativeModeTab "Shears"
    public static final Supplier<CreativeModeTab> SHEARS_MOD = CREATIVE_MODE_TABS.register("shears_mod",
            () -> CreativeModeTab.builder() .title(Component.translatable("QOL Items"))
                    .icon(() -> new ItemStack(Items.WOODEN_SHEAR.get()))
                    .displayItems((params, output) -> {
                        output.accept(Items.WOODEN_SHEAR.get());
                        output.accept(Items.STONE_SHEAR.get());
                        output.accept(Items.COPPER_SHEAR.get());
                        output.accept(Items.IRON_SHEAR.get());
                        output.accept(Items.GOLDEN_SHEAR.get());
                        output.accept(Items.DIAMOND_SHEAR.get());
                        output.accept(Items.EMERALD_SHEAR.get());
                        output.accept(Items.WOODEN_BUCKET.get());
                        output.accept(Items.WOODEN_WATER_BUCKET.get());
                        output.accept(Items.WOODEN_LAVA_BUCKET.get());
                    })
                    .build()
    );

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

}
