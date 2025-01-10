package net.cestog.qolitems.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;


import static net.cestog.qolitems.Qolitems.MODID;

public class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);

    public static final Supplier<Block> BLOKJE = BLOCKS.register("blokje",
            () -> new Block(BlockBehaviour.Properties.of()
                    .sound(SoundType.WOOD)
                    .destroyTime(5)));


    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
