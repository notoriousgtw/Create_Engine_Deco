package net.notogt.create_engine_deco.block.custom;

import com.simibubi.create.AllItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class SmallBoilerBlock extends DirectionalBlock {

    public static final EnumProperty<SmallBoilerBlock.Connected> CONNECTED = EnumProperty.create("connected", SmallBoilerBlock.Connected.class);

    public SmallBoilerBlock(Properties pProperties) {
        super(pProperties);
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH).setValue(CONNECTED, Connected.FALSE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(FACING, CONNECTED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite());
    }

    public enum Connected implements StringRepresentable {
        FALSE("false", 0), BOTTOM("bottom", 1), LEFT("left", 2), TOP("top", 3), RIGHT("right", 4);

        public final String name;
        public final int id;

        Connected(String name, int id) {
            this.name = name;
            this.id = id;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

        public static Connected get(int id) {
            for (Connected c : Connected.values()) {
                if (c.id == id)
                    return c;
            }
            return null;
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pPlayer.getItemInHand(pHand).getItem() == AllItems.WRENCH.asItem()) {
            Direction hit_direction = pHit.getDirection();
            Direction facing = pState.getValue(FACING);
            if (hit_direction == facing) {
                int id = pState.getValue(CONNECTED).id;
                if (id != 4)
                    pLevel.setBlock(pPos, pState.setValue(CONNECTED, Connected.get(id + 1)), 0);
                else
                    pLevel.setBlock(pPos, pState.setValue(CONNECTED, Connected.get(0)), 0);
            } else if (hit_direction == facing.getOpposite()) {
                int id = pState.getValue(CONNECTED).id;
                if (id != 0)
                    pLevel.setBlock(pPos, pState.setValue(CONNECTED, Connected.get(id - 1)), 0);
                else
                    pLevel.setBlock(pPos, pState.setValue(CONNECTED, Connected.get(4)), 0);
            } else {
                pLevel.setBlock(pPos, pState.setValue(FACING, facing.getClockWise(hit_direction.getAxis())), 0);
            }
            return InteractionResult.SUCCESS;
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

}