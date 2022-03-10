package me.brotherhong.fishinglife2.fishing;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import me.brotherhong.fishinglife2.Messages;
import me.brotherhong.fishinglife2.utils.ColorUtil;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BlockVector;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class FishingRegion {

    private String name;
    private String worldName;
    private BlockVector pos1;
    private BlockVector pos2;
    private List<FishingDrop> drops;

    public FishingRegion() {
        drops = new ArrayList<>();
    }

    public FishingRegion(String name, Region region) {
        this.name = name;
        this.worldName = Objects.requireNonNull(region.getWorld()).getName();
        setRegionPosition(region);
        drops = new ArrayList<>();
    }

    public static FishingRegion fromSection(ConfigurationSection section) {
        FishingRegion result = new FishingRegion();
        result.setName(section.getName());
        result.setWorldName(section.getString("world"));
        BlockVector pos1 = new BlockVector(
                section.getInt("pos1.x"),
                section.getInt("pos1.y"),
                section.getInt("pos1.z")
        );
        result.setPos1(pos1);
        BlockVector pos2 = new BlockVector(
                section.getInt("pos2.x"),
                section.getInt("pos2.y"),
                section.getInt("pos2.z")
        );
        result.setPos2(pos2);
        List<FishingDrop> drops = new ArrayList<>();
        ConfigurationSection dropSection = section.getConfigurationSection("drops");
        assert dropSection != null;
        for (String i : dropSection.getKeys(false)) {
            drops.add(FishingDrop.fromSection(Objects.requireNonNull(dropSection.getConfigurationSection(i))));
        }
        result.setDrops(drops);
        return result;
    }

    public void setToSection(ConfigurationSection section) {
        section.set("world", worldName);
        section.set("pos1.x", pos1.getBlockX());
        section.set("pos1.y", pos1.getBlockY());
        section.set("pos1.z", pos1.getBlockZ());

        section.set("pos2.x", pos2.getBlockX());
        section.set("pos2.y", pos2.getBlockY());
        section.set("pos2.z", pos2.getBlockZ());

        section = section.createSection("drops");

        for (int i = 0;i < drops.size();i++) {
            FishingDrop drop = drops.get(i);
            ConfigurationSection dropSection = section.createSection(i + "");
            drop.setToSection(dropSection);
        }
    }

    public void deleteDrop(int index) {
        drops.remove(index);
    }

    public void addDrop(FishingDrop drop) {
        drops.add(drop);
        drops.sort(Comparator.comparingDouble(FishingDrop::getChance));
    }

    public FishingDrop getReward() {
        double totalChance = drops.stream().mapToDouble(FishingDrop::getChance).sum();
        double random = Math.random() * totalChance;
        for (FishingDrop drop : drops) {
            if (random <= drop.getChance()) {
                return drop;
            }
            random -= drop.getChance();
        }
        return new FishingDrop(new ItemStack(Material.COD, 1), 100.0, false);
    }

    public void setRegionPosition(Region region) {
        setWorldName(Objects.requireNonNull(region.getWorld()).getName());
        BlockVector3 point = region.getMinimumPoint();
        setPos1(new BlockVector(point.getBlockX(), point.getBlockY(), point.getBlockZ()));
        point = region.getMaximumPoint();
        setPos2(new BlockVector(point.getBlockX(), point.getBlockY(), point.getBlockZ()));
    }

    public boolean isConflict(Region region) {
        BlockVector3 rpos1 = region.getMinimumPoint();
        BlockVector3 rpos2 = region.getMaximumPoint();
        return (isIntersect(pos1.getBlockX(), pos2.getBlockX(), rpos1.getBlockX(), rpos2.getBlockX()) &&
                isIntersect(pos1.getBlockY(), pos2.getBlockY(), rpos1.getBlockY(), rpos2.getBlockY()) &&
                isIntersect(pos1.getBlockZ(), pos2.getBlockZ(), rpos1.getBlockZ(), rpos2.getBlockZ()));
    }

    public boolean contains(Location location) {
        return (pos1.getBlockX() <= location.getBlockX() && location.getBlockX() <= pos2.getBlockX() &&
                pos1.getBlockY() <= location.getBlockY() && location.getBlockY() <= pos2.getBlockY() &&
                pos1.getBlockZ() <= location.getBlockZ() && location.getBlockZ() <= pos2.getBlockZ());
    }

    public void displayBoundary() {
        World world = Bukkit.getWorld(worldName);
        Particle particle = Particle.REDSTONE;
        int[] colors = ColorUtil.toRGB256(Messages.BOUNDARY_COLOR.get());
        Particle.DustOptions dustColor = new Particle.DustOptions(Color.fromRGB(colors[0], colors[1], colors[2]), 1.0f);
        int amount = 3;
        double d = 0.25;
        double[] start = {pos1.getBlockX(), pos1.getBlockY(), pos1.getBlockZ()};
        double[] end = {pos2.getBlockX()+1, pos2.getBlockY()+1, pos2.getBlockZ()+1};
        assert world != null;
        // X
        for (double x = start[0];x <= end[0];x+=d) {
            world.spawnParticle(particle, x, start[1], start[2], amount, 0, 0, 0, dustColor);
            world.spawnParticle(particle, x, end[1], start[2], amount, 0, 0, 0, dustColor);
            world.spawnParticle(particle, x, start[1], end[2], amount, 0, 0, 0, dustColor);
            world.spawnParticle(particle, x, end[1], end[2], amount, 0, 0, 0, dustColor);
        }
        // Y
        for (double y = start[1];y <= end[1];y+=d) {
            world.spawnParticle(particle, start[0], y, start[2], amount, 0, 0, 0, dustColor);
            world.spawnParticle(particle, end[0], y, start[2], amount, 0, 0, 0, dustColor);
            world.spawnParticle(particle, start[0], y, end[2], amount, 0, 0, 0, dustColor);
            world.spawnParticle(particle, end[0], y, end[2], amount, 0, 0, 0, dustColor);
        }
        // Z
        for (double z = start[2];z <= end[2];z+=d) {
            world.spawnParticle(particle, start[0], start[1], z, amount, 0, 0, 0, dustColor);
            world.spawnParticle(particle, end[0], start[1], z, amount, 0, 0, 0, dustColor);
            world.spawnParticle(particle, start[0], end[1], z, amount, 0, 0, 0, dustColor);
            world.spawnParticle(particle, end[0], end[1], z, amount, 0, 0, 0, dustColor);
        }
    }

    public Location getTpLocation() {
        World world = Bukkit.getWorld(worldName);
        Location location = new Location(world,
                (pos1.getX() + pos2.getX()) / 2,
                (pos1.getY() + pos2.getY()) / 2,
                (pos1.getZ() + pos2.getZ()) / 2
                );
        assert world != null;
        return world.getHighestBlockAt(location).getLocation();
    }

    private boolean isIntersect(int p1, int p2, int q1, int q2) {
        if (p1 > q1)
            return (q2 >= p1);
        return (p2 >= q1);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FishingDrop> getDrops() {
        return drops;
    }

    public void setDrops(List<FishingDrop> drops) {
        this.drops = drops;
    }

    public String getWorldName() {
        return worldName;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public BlockVector getPos1() {
        return pos1;
    }

    public void setPos1(BlockVector pos1) {
        this.pos1 = pos1;
    }

    public BlockVector getPos2() {
        return pos2;
    }

    public void setPos2(BlockVector pos2) {
        this.pos2 = pos2;
    }
}

