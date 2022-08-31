package net.frozenblock.wilderwild.entity;

import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.NotNull;

public class JellyCloud extends Entity {
    private static final EntityDataAccessor<Float> DATA_RADIUS = SynchedEntityData.defineId(JellyCloud.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> AGE = SynchedEntityData.defineId(JellyCloud.class, EntityDataSerializers.INT);

    public static final int STOPS_GROWING_TIME = 35;
    public static final int TEXTURE_INCREASE_PERCENT = 10;

    public JellyCloud(EntityType<? extends JellyCloud> entityType, Level level) {
        super(entityType, level);
        this.noPhysics = true;
        this.setRadius(3.0f);
    }

    public JellyCloud(Level level, double d, double e, double f) {
        this(RegisterEntities.JELLY_CLOUD, level);
        this.setPos(d, e, f);
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(AGE, 0);
        this.getEntityData().define(DATA_RADIUS, 1.25f);
    }

    @Override
    public void refreshDimensions() {
        double d = this.getX();
        double e = this.getY();
        double f = this.getZ();
        super.refreshDimensions();
        this.setPos(d, e, f);
    }

    public InteractionResult interact(@NotNull Player player, @NotNull InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (itemStack.getItem() == Items.GLASS_BOTTLE) {
            Item item = RegisterItems.JELLY_BOTTLE;
            //TODO: JELLY BOTTLE FILL SOUND
            player.playSound(SoundEvents.HONEY_DRINK, 1.0F, this.random.nextFloat() * 0.2f + 0.8f);
            if (!player.isCreative()) {
                player.getItemInHand(interactionHand).shrink(1);
            }
            ItemStack bottleStack = new ItemStack(item);
            player.getInventory().placeItemBackInInventory(bottleStack);
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }
        return InteractionResult.PASS;
    }

    @Override
    public void tick() {
        this.setRadius(this.getRadius() - 0.0025F);
        if (this.getRadius() <= 0) {
            this.discard();
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag nbt) {
        this.setAge(nbt.getInt("age"));
        this.setRadius(nbt.getFloat("Radius"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag nbt) {
        nbt.putInt("age", this.getAge());
        nbt.putFloat("Radius", this.getRadius());
    }

    public float getRadius() {
        return this.getEntityData().get(DATA_RADIUS);
    }

    public void setRadius(float f) {
        if (!this.level.isClientSide) {
            this.getEntityData().set(DATA_RADIUS, Mth.clamp(f, 0.0f, 32.0f));
        }
    }

    public int getAge() {
        return this.getEntityData().get(AGE);
    }

    public void setAge(int i) {
        this.getEntityData().set(AGE, i);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
        if (DATA_RADIUS.equals(entityDataAccessor)) {
            this.refreshDimensions();
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    @Override
    public PushReaction getPistonPushReaction() {
        return PushReaction.IGNORE;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    @Override
    public EntityDimensions getDimensions(@NotNull Pose pose) {
        return EntityDimensions.scalable(this.getRadius() * 2.0f, 0.5f);
    }
}

