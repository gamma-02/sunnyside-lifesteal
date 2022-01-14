package gamma02.sunnysidelifesteal.client;

import gamma02.sunnysidelifesteal.SunnysideLifesteal;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.particle.FireworksSparkParticle;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FireworkRocketItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.DyeColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static gamma02.sunnysidelifesteal.SunnysideLifesteal.FILLED_CONTAINER;

@Environment(EnvType.CLIENT)
public class ClientUtils {

    public static boolean isPlayerClient(PlayerEntity entity){
        return entity instanceof ClientPlayerEntity;
    }

    public static void doClientStuff(PlayerEntity entity, boolean full){
        if(isPlayerClient(entity)){
            ClientPlayerEntity player = (ClientPlayerEntity) entity;
            MinecraftClient client = player.client;
            if(!full){
                client.gameRenderer.showFloatingItem(SunnysideLifesteal.HEART_CONTAINER.getDefaultStack());

            }else{
                client.gameRenderer.showFloatingItem(FILLED_CONTAINER.getDefaultStack());



            }
        }
    }

}
