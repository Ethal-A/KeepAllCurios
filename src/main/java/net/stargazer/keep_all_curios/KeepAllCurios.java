package net.stargazer.keep_all_curios;

import com.mojang.logging.LogUtils;
import java.util.function.Predicate;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;
import top.theillusivec4.curios.api.event.DropRulesEvent;
import top.theillusivec4.curios.api.type.capability.ICurio;

@Mod(KeepAllCurios.MOD_ID)
public final class KeepAllCurios {

    public static final String MOD_ID = "keep_all_curios";
    public static final Logger LOGGER = LogUtils.getLogger();
    private static final Predicate<ItemStack> KEEP_ALL_CURIOS = stack -> true;

    public KeepAllCurios(IEventBus modEventBus) {
        NeoForge.EVENT_BUS.addListener(KeepAllCurios::onDropRules);
    }

    private static void onDropRules(final DropRulesEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        try {
            event.addOverride(KEEP_ALL_CURIOS, ICurio.DropRule.ALWAYS_KEEP);
        } catch (Exception exception) {
            LOGGER.error("Failed to mark Curios as kept on death for {}", event.getEntity().getScoreboardName(), exception);
        }
    }
}
