package net.frozenblock.wilderwild.entity.ai.crab;

import com.google.common.collect.ImmutableMap;
import net.frozenblock.wilderwild.entity.Crab;
import net.frozenblock.wilderwild.registry.RegisterMemoryModuleTypes;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import org.jetbrains.annotations.NotNull;

public class CrabDig<E extends Crab> extends Behavior<E> {
	public CrabDig(int duration) {
		super(
			ImmutableMap.of(
				MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_ABSENT,
				MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT,
				MemoryModuleType.NEAREST_PLAYERS, MemoryStatus.REGISTERED,
				RegisterMemoryModuleTypes.IS_UNDERGROUND, MemoryStatus.REGISTERED
			),
			duration
		);
	}

	@Override
	protected boolean canStillUse(ServerLevel level, E entity, long gameTime) {
		return true;
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel level, @NotNull E crab) {
		boolean noPlayers = crab.getBrain().getMemory(MemoryModuleType.NEAREST_PLAYERS).get().isEmpty();
		if (!noPlayers) {
			crab.getBrain().setMemoryWithExpiry(MemoryModuleType.DIG_COOLDOWN, Unit.INSTANCE, 80L);
		}
		return noPlayers && crab.canHideOnGround();
	}

	@Override
	protected void start(ServerLevel level, @NotNull E crab, long gameTime) {
		crab.getNavigation().stop();
		crab.setPose(Pose.DIGGING);
		crab.playSound(RegisterSounds.ENTITY_CRAB_DIG, 0.5F, 1.0F);
		crab.resetDiggingTicks();
	}

	@Override
	protected void stop(ServerLevel level, @NotNull E crab, long gameTime) {
		crab.getBrain().setMemory(RegisterMemoryModuleTypes.IS_UNDERGROUND, true);
		crab.getBrain().setMemoryWithExpiry(MemoryModuleType.DIG_COOLDOWN, Unit.INSTANCE, CrabAi.getRandomEmergeCooldown(crab));
	}
}
