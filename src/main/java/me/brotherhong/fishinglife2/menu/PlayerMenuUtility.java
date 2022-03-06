package me.brotherhong.fishinglife2.menu;

import org.bukkit.entity.Player;

public class PlayerMenuUtility {

    private Player owner;
    private String regionName;
    private int clickedSlot;
    private ConfirmType confirmType;

    public PlayerMenuUtility(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public int getClickedSlot() {
        return clickedSlot;
    }

    public void setClickedSlot(int clickedSlot) {
        this.clickedSlot = clickedSlot;
    }

    public ConfirmType getConfirmType() {
        return confirmType;
    }

    public void setConfirmType(ConfirmType confirmType) {
        this.confirmType = confirmType;
    }
}
