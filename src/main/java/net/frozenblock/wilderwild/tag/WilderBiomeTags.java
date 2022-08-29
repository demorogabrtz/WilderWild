package net.frozenblock.wilderwild.tag;

import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public final class WilderBiomeTags {
    public static final TagKey<Biome> FIREFLY_SPAWNABLE_DURING_DAY = of("firefly_spawnable_during_day");
    public static final TagKey<Biome> FIREFLY_SPAWNABLE_CAVE = of("firefly_spawnable_cave");
    public static final TagKey<Biome> FIREFLY_SPAWNABLE = of("firefly_spawnable");
    public static final TagKey<Biome> ABANDONED_CABIN_HAS_STRUCTURE = of("has_structure/abandoned_cabin");
    public static final TagKey<Biome> PINK_JELLYFISH = of("pink_jellyfish");

    private WilderBiomeTags() {
    }

    private static TagKey<Biome> of(String path) {
        return TagKey.create(Registry.BIOME_REGISTRY, WilderWild.id(path));
    }
}
