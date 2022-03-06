package me.brotherhong.fishinglife2;

public enum Permissions {

    OP("op"),
    SHOW("show");

    private final String perm;

    Permissions(String perm) {
        this.perm = "fishinglife." + perm;
    }

    public String getPerm() {
        return perm;
    }
}
