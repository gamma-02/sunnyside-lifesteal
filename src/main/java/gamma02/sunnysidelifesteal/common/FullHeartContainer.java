package gamma02.sunnysidelifesteal.common;

import gamma02.sunnysidelifesteal.SunnysideLifesteal;
import gamma02.sunnysidelifesteal.client.ClientUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FireworkRocketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

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
            user.heal(2);
            NbtCompound fireworks = new NbtCompound();
            NbtCompound ExplosionNBT = new NbtCompound();
            List<Integer> dyes = new ArrayList<>();
            for (int i = 0; i < DyeColor.values().length; i++) {dyes.add(i);}
            ExplosionNBT.putIntArray(FireworkRocketItem.COLORS_KEY, dyes);
            ExplosionNBT.putInt(FireworkRocketItem.TYPE_KEY, 1);
            ExplosionNBT.putBoolean(FireworkRocketItem.TRAIL_KEY, true);
            ExplosionNBT.putBoolean(FireworkRocketItem.FLICKER_KEY, true);
            fireworks.put(FireworkRocketItem.EXPLOSIONS_KEY, ExplosionNBT);
            fireworks.putByte(FireworkRocketItem.FLIGHT_KEY, (byte) 0);

            ItemStack stack = Items.FIREWORK_ROCKET.getDefaultStack();
            assert stack.getNbt() != null;
            NbtCompound nbt = stack.getNbt();

            nbt.put("Fireworks", fireworks);
            stack.setNbt(nbt);

            if(!world.isClient){
                SunnysideLifesteal.server.getCommandManager().execute(SunnysideLifesteal.server.getCommandSource().withSilent(), "summon firework_rocket " + user.getX() + " " + user.getY() + " " + user.getZ() +" {LifeTime:20,FireworksItem:{id:firework_rocket,Count:1,tag:{Fireworks:{Explosions:[{Type:1,Flicker:1,Trail:1,Colors:[I;1973019,11743532,3887386,5320730,2437522,8073150,2651799,11250603,4408131,14188952,4312372,14602026,6719955,12801229,15435844,15790320],FadeColors:[I;11743532,3887386,5320730,2437522,8073150,2651799,14188952,4312372,14602026,6719955,12801229,15435844]}],Flight:0}}}}");
            }

            if (world.isClient) {
                ClientUtils.doClientStuff(user, true);
            }
            return TypedActionResult.consume(user.getStackInHand(hand));
        }

        return super.use(world, user, hand);
    }
}
