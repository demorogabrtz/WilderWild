package net.frozenblock.wilderwild.block.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.item.FireflyBottle;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FireflyLanternBlockEntity extends BlockEntity {
    ArrayList<FireflyInLantern> fireflies = new ArrayList<>();

    public FireflyLanternBlockEntity(BlockPos pos, BlockState state) {
        super(RegisterBlockEntities.FIREFLY_LANTERN, pos, state);
    }

    public void serverTick(World world, BlockPos pos, BlockState state) {
        if (!this.fireflies.isEmpty()) {
            for (FireflyInLantern firefly : this.fireflies) {
                firefly.tick();
            }
            for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, pos)) {
                player.networkHandler.sendPacket(this.toUpdatePacket());
            }
        }
    }

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }

    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if (nbt.contains("fireflies", 9)) {
            this.fireflies.clear();
            DataResult<?> var10000 = FireflyInLantern.CODEC.listOf().parse(new Dynamic<>(NbtOps.INSTANCE, nbt.getList("fireflies", 10)));
            Logger var10001 = WilderWild.LOGGER;
            Objects.requireNonNull(var10001);
            Optional<List> list = (Optional<List>) var10000.resultOrPartial(var10001::error);
            if (list.isPresent()) {
                List<?> fireflyList = list.get();
                for (Object o : fireflyList) {
                    this.fireflies.add((FireflyInLantern) o);
                }
            }
        }
    }

    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        DataResult<?> var10000 = FireflyInLantern.CODEC.listOf().encodeStart(NbtOps.INSTANCE, this.fireflies);
        Logger var10001 = WilderWild.LOGGER;
        Objects.requireNonNull(var10001);
        var10000.resultOrPartial(var10001::error).ifPresent((cursorsNbt) -> {
            nbt.put("fireflies", (NbtElement) cursorsNbt);
        });
    }

    public ArrayList<FireflyInLantern> getFireflies() {
        return this.fireflies;
    }

    public void addFirefly(FireflyBottle bottle, String name, World world) {
        Vec3d newVec = new Vec3d(0.5 + world.random.nextTriangular(0, 0.2), 0.155F + 0.3125, 0.5 + world.random.nextTriangular(0, 0.2));
        this.fireflies.add(new FireflyInLantern(Vec3d.ZERO, bottle.color, name, Math.random() > 0.7, (int) (Math.random() * 20)));
    }

    public void removeFirefly(FireflyInLantern firefly) {
        this.fireflies.remove(firefly);
    }

    public static class FireflyInLantern {
        public Vec3d pos;
        public String color;
        public String customName;
        public boolean flickers;
        public int age;

        public static final Codec<FireflyInLantern> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                Vec3d.CODEC.fieldOf("pos").forGetter(FireflyInLantern::getPos),
                Codec.STRING.fieldOf("color").forGetter(FireflyInLantern::getColor),
                Codec.STRING.fieldOf("customName").orElse("").forGetter(FireflyInLantern::getCustomName),
                Codec.BOOL.fieldOf("flickers").orElse(false).forGetter(FireflyInLantern::getFlickers),
                Codec.INT.fieldOf("age").forGetter(FireflyInLantern::getAge)
        ).apply(instance, FireflyInLantern::new));

        public FireflyInLantern(Vec3d pos, String color, String customName, boolean flickers, int age) {
            this.pos = pos;
            this.color = color;
            this.customName = customName;
            this.flickers = flickers;
            this.age = age;
        }

        public void tick() {
            ++this.age;
        }

        public Vec3d getPos() {
            return this.pos;
        }

        public String getColor() {
            return this.color;
        }

        public String getCustomName() {
            return this.customName;
        }

        public boolean getFlickers() {
            return this.flickers;
        }

        public int getAge() {
            return this.age;
        }

    }

}