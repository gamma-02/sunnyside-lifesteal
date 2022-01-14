package gamma02.sunnysidelifesteal.common;

import gamma02.sunnysidelifesteal.client.ClientUtils;
import net.minecraft.entity.damage.DamageSource;
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
        if(!HealthMap.containsKey(user.getUuid()))
            HealthMap.put(user.getUuid(), 20d);
        if(HealthMap.get(user.getUuid()) > 10 && !user.getItemCooldownManager().isCoolingDown(this)){
            HealthMap.put(user.getUuid(), HealthMap.get(user.getUuid())-1);
            user.getStackInHand(hand).decrement(1);
            user.giveItemStack(new ItemStack(FILLED_CONTAINER));
            user.setHealth(((float) HealthMap.get(user.getUuid()).doubleValue()));
            user.getItemCooldownManager().set(this, 100);
            if (world.isClient) {
                ClientUtils.doClientStuff(user, false);
            }

            return super.use(world, user, hand);
        }







        return super.use(world, user, hand);
    }
}
