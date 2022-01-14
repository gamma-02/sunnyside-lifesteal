package gamma02.sunnysidelifesteal.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import gamma02.sunnysidelifesteal.SunnysideLifesteal;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class GiveLifeCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher){
        dispatcher.register(CommandManager.literal("givelife")
//                    ServerPlayerEntity giver = context.getSource().getPlayer();
//                    HitResult result = giver.raycast(50, 0.0f, false);
//                    if(result.getType() == HitResult.Type.ENTITY) {
//                        if(((EntityHitResult) result).getEntity() instanceof PlayerEntity) {
//                            SunnysideLifesteal.HealthMap.put(giver.getUuid(), SunnysideLifesteal.HealthMap.get(giver.getUuid()) - 2);
//                            SunnysideLifesteal.HealthMap.put(((EntityHitResult) result).getEntity().getUuid(), SunnysideLifesteal.HealthMap.get(((EntityHitResult) result).getEntity().getUuid()) + 2);
//                            return Command.SINGLE_SUCCESS;
//                        }else {
//                            return 0;
//                        }
//                    }else{
//                        return 0;
//                    }

                .then(CommandManager.argument("target", EntityArgumentType.player()).executes( context -> {return name(context, context.getSource().getPlayer(), EntityArgumentType.getPlayer(context, "target"));})));
    }
    public static Vec3d getRotationVec3d(float pitch, float yaw) {
        float f = pitch * 0.017453292F;
        float g = -yaw * 0.017453292F;
        float h = MathHelper.cos(g);
        float i = MathHelper.sin(g);
        float j = MathHelper.cos(f);
        float k = MathHelper.sin(f);
        return new Vec3d((i * j), (-k), (h * j));
    }
    public static int name(CommandContext<ServerCommandSource> context, ServerPlayerEntity giver, ServerPlayerEntity target){
        SunnysideLifesteal.HealthMap.put(giver.getUuid(), SunnysideLifesteal.HealthMap.get(giver.getUuid()) - 2);
        SunnysideLifesteal.HealthMap.put(target.getUuid(), SunnysideLifesteal.HealthMap.get(target.getUuid()) + 2);
        return Command.SINGLE_SUCCESS;
    }

}

