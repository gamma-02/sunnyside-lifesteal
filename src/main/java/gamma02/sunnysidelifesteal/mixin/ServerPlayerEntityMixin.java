package gamma02.sunnysidelifesteal.mixin;



import com.mojang.authlib.GameProfile;
import gamma02.sunnysidelifesteal.SunnysideLifesteal;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.UUID;

import static gamma02.sunnysidelifesteal.SunnysideLifesteal.HealthMap;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {

    @Shadow public abstract void sendAbilitiesUpdate();

    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
        super(world, pos, yaw, profile);
    }

    @Inject(method = "onDeath", at = @At("HEAD"))
    public void onDeathMixin(DamageSource source, CallbackInfo ci){
        if(source.getAttacker() instanceof PlayerEntity && HealthMap.containsKey(source.getAttacker().getUuid()) && HealthMap.containsKey(this.getUuid())){
            if(HealthMap.get(this.getUuid()) >= 10) {
                HealthMap.put(this.getUuid(), this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).getBaseValue() - 1);
                HealthMap.put(source.getAttacker().getUuid(), HealthMap.get(source.getAttacker().getUuid()) + 1);
            }
        }
    }

    @Inject(method="readCustomDataFromNbt", at = @At("HEAD"))
    public void readHealth(NbtCompound nbt, CallbackInfo ci){
        if(nbt.contains("CustomHealth")) {
            this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(nbt.getDouble("CustomHealth"));
            this.sendAbilitiesUpdate();
            HealthMap.put(this.getUuid(), nbt.getDouble("CustomHealth"));
        }else{
            HealthMap.put(this.getUuid(), 20d);
        }
    }

    @Inject(method = "writeCustomDataToNbt", at=@At("HEAD"))
    public void writeHealth(NbtCompound nbt, CallbackInfo ci){

        nbt.putDouble("CustomHealth", this.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH));
    }

//    @Inject(method = "onDisconnect", at = @At("HEAD"))
//    public void yeetFromMap(CallbackInfo ci){
//        HealthMap.remove(this.getUuid());
//    }

    @Inject(method="tick", at=@At("HEAD"))
    public void e(CallbackInfo ci){
        if(HealthMap.containsKey(this.getUuid())){
            this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(HealthMap.get(this.getUuid()));
            this.sendAbilitiesUpdate();
        }

    }






}
