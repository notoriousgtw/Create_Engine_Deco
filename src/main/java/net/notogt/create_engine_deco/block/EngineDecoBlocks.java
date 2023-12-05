package net.notogt.create_engine_deco.block;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.decoration.copycat.CopycatBlockEntity;
import com.simibubi.create.content.decoration.copycat.CopycatModel;
import com.simibubi.create.content.decoration.copycat.CopycatStepModel;
import com.simibubi.create.foundation.data.BuilderTransformers;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.notogt.create_engine_deco.CreateEngineDeco;
import net.notogt.create_engine_deco.block.decoration.CopycatBoilerBlock;
import net.notogt.create_engine_deco.block.decoration.CopycatBoilerModel;
import net.notogt.create_engine_deco.block.decoration.SmallBoilerBlock;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static net.notogt.create_engine_deco.CreateEngineDeco.REGISTRATE;


public class EngineDecoBlocks {

    static {
        REGISTRATE.defaultCreativeTab(CreativeModeTabs.BUILDING_BLOCKS);
    }
    public static final BlockEntry<SmallBoilerBlock> SMALL_BOILER =
            REGISTRATE.block("small_boiler", SmallBoilerBlock::new)
                    .simpleItem()
                    .lang("Small Boiler")
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .register();

    public static final BlockEntry<CopycatBoilerBlock> COPYCAT_BOILER =
            REGISTRATE.block("copycat_boiler", CopycatBoilerBlock::new)
                    .transform(BuilderTransformers.copycat())
                    .onRegister(CreateRegistrate.blockModel(() -> CopycatBoilerModel::new))
                    .item()
                    .transform(customItemModel("copycat_base", "step"))
                    .register();


    public static final BlockEntityEntry<CopycatBlockEntity> COPYCAT =
            REGISTRATE.blockEntity("copycat", CopycatBlockEntity::new)
                    .validBlocks(EngineDecoBlocks.COPYCAT_BOILER)
                    .register();
    public static void register() {}

}
