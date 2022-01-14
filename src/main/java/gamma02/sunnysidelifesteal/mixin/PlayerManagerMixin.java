package gamma02.sunnysidelifesteal.mixin;

import net.minecraft.network.ClientConnection;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.server.PlayerManager;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {


    @Inject(method = "onPlayerConnect", at = @At("HEAD"))
    public void onConnect(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci){
    }

}
