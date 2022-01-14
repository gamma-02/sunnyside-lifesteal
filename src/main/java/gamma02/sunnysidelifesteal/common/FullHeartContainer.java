package gamma02.sunnysidelifesteal.common;

import gamma02.sunnysidelifesteal.SunnysideLifesteal;
import gamma02.sunnysidelifesteal.client.ClientUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FireworkRocketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static gamma02.sunnysidelifesteal.SunnysideLifesteal.HealthMap;

public class FullHeartContainer extends Item {


    public FullHeartContainer(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!HealthMap.containsKey(user.getUuid()))
            HealthMap.put(user.getUuid(), 20d);
        if(HealthMap.get(user.getUuid()) < 30){
            HealthMap.put(user.getUuid(), HealthMap.get(user.getUuid()) + 1);
            user.getStackInHand(hand).decrement(1);
            if (world.isClient) {
                ClientUtils.doClientStuff(user, true);
            }
            return TypedActionResult.consume(user.getStackInHand(hand));
        }

        return super.use(world, user, hand);
    }
}
