package gamma02.sunnysidelifesteal.common;

import gamma02.sunnysidelifesteal.client.ClientUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import gamma02.sunnysidelifesteal.SunnysideLifesteal;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import org.lwjgl.system.CallbackI;

import static gamma02.sunnysidelifesteal.SunnysideLifesteal.FILLED_CONTAINER;
import static gamma02.sunnysidelifesteal.SunnysideLifesteal.HealthMap;

public class HeartContainerItem extends Item {
    public HeartContainerItem(Settings settings) {
        super(settings);
    }



    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(HealthMap.containsKey(user.getUuid())){
            if(HealthMap.get(user.getUuid()) > 10){
                HealthMap.put(user.getUuid(), HealthMap.get(user.getUuid())-1);
                user.getStackInHand(hand).decrement(1);
                user.giveItemStack(new ItemStack(FILLED_CONTAINER));
                user.sendAbilitiesUpdate();
                if (world.isClient) {
                    ClientUtils.doClientStuff(user, false);
                }
                return super.use(world, user, hand);
            }
        }else{
            HealthMap.put(user.getUuid(), 20d);

            HealthMap.put(user.getUuid(), HealthMap.get(user.getUuid())-2);
            user.getStackInHand(hand).decrement(1);
            user.giveItemStack(new ItemStack(FILLED_CONTAINER));
            user.sendAbilitiesUpdate();
            if (world.isClient) {
                ClientUtils.doClientStuff(user, false);
            }
            return super.use(world, user, hand);

        }



        return super.use(world, user, hand);
    }
}
