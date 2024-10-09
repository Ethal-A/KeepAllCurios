package net.ethala.keepallcurios;

import com.mojang.logging.LogUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.event.DropRulesEvent;
import top.theillusivec4.curios.api.type.capability.ICurio;
import org.slf4j.Logger;

@Mod(KeepAllCurios.MOD_ID)
@Mod.EventBusSubscriber(modid = KeepAllCurios.MOD_ID)
public class KeepAllCurios {
    public static final String MOD_ID = "keep_all_curios";
    private static final Logger LOGGER = LogUtils.getLogger(); // Directly reference a slf4j logger

    @SubscribeEvent
    public static void dropRuleOverride(DropRulesEvent event) {
        try {
            // Check if the drop is happening from a player
            if (event.getCurioHandler().getWearer() instanceof Player) {
                // Override drop rule
                // While you can create a predicate to check that that the item is an instance of a curio, testing revealed that if you do this, some curios from some mods will drop from the player upon death - this mod will not be compatible with all mods that use curios.
                event.addOverride((itemStack) -> true,
                        ICurio.DropRule.ALWAYS_KEEP);
            }
        } catch (Exception e) {
            // Avoid crashing if something goes wrong
            System.err.println("Error when overriding drop rules: " + e);
            LOGGER.error("Error when overriding drop rules: " + e);
        }
    }

    public KeepAllCurios() {
        // Register ourselves for server and other game events
        MinecraftForge.EVENT_BUS.register(this);
    }
}
