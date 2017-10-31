package me.refraction.alwayssharp.particles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.WorldSettings;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * Created by Refraction on 31/10/2017.
 */
public class Particles {

    private Minecraft mc;
    private boolean hasSharpness;
    private ItemStack weapon;
    private boolean breakLoop;

    public Particles() {
        this.mc = Minecraft.getMinecraft();
        MinecraftForge.EVENT_BUS.register((Object) this);
    }

    @SubscribeEvent
    public void onLeftClick(TickEvent.ClientTickEvent event) {
        if(!mc.gameSettings.keyBindAttack.isKeyDown()) {
            breakLoop = true;
        }
        else {
            if(!this.mc.inGameHasFocus || this.mc.objectMouseOver == null) {
                return;
            }
            if(this.shouldSpawnParticles()) {
                hasSharpness = false;
                ItemStack currentItem = this.mc.thePlayer.getCurrentEquippedItem();
                weapon = currentItem;
                if(currentItem != null) {
                    if(currentItem.getItem() == Items.diamond_sword || currentItem.getItem() == Items.diamond_axe || currentItem.getItem() == Items.iron_sword || currentItem.getItem() == Items.iron_axe ||  currentItem.getItem() == Items.golden_sword || currentItem.getItem() == Items.golden_axe || currentItem.getItem() == Items.stone_sword || currentItem.getItem() == Items.stone_axe || currentItem.getItem() == Items.wooden_sword || currentItem.getItem() == Items.wooden_axe) {
                        if(weapon.isItemEnchanted()) {
                            NBTTagList enchants = weapon.getEnchantmentTagList();
                            for(int i = 0; i < enchants.tagCount(); i++) {
                                NBTTagCompound enchant = ((NBTTagList) enchants).getCompoundTagAt(i);
                                if(enchant.getInteger("id") == 16) {
                                    hasSharpness = true;
                                    break;
                                }
                            }
                        }
                        if(!hasSharpness && breakLoop) {
                            this.attemptParticleSpawn();
                            breakLoop = false;
                        }
                    }
                }
            }
        }

    }

    private boolean shouldSpawnParticles() {
        final Entity entity = this.mc.objectMouseOver.entityHit;
        return (entity instanceof EntityLiving || (entity instanceof EntityOtherPlayerMP && ((EntityOtherPlayerMP)entity).isEntityAlive())) && (this.mc.playerController.getCurrentGameType() == WorldSettings.GameType.SURVIVAL || this.mc.playerController.getCurrentGameType() == WorldSettings.GameType.CREATIVE);
    }

    private void attemptParticleSpawn() {
        final BlockPos tmp = this.mc.objectMouseOver.entityHit.getPosition();
        try {
            for (int i = 0; i < 20; ++i) {
                final double x = tmp.getX() + Math.random();
                final double y = tmp.getY() + 0.3 + Math.random() * 1.3;
                final double z = tmp.getZ() + Math.random();
                final double xOffset = Math.random() * 2.0 - 1.3;
                final double yOffset = Math.random() * 0.8;
                final double zOffset = Math.random() * 2.0 - 1.3;
                if(this.mc.theWorld.isRemote) {
                    this.mc.theWorld.spawnParticle(EnumParticleTypes.CRIT_MAGIC, x, y, z, xOffset, yOffset, zOffset, new int[0]);
                }
            }
        }
        catch (Exception e) {

        }
    }

}
