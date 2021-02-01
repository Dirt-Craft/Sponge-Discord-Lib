package net.dirtcraft.spongediscordlib.users.roles;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Role;

public final class DiscordRole {

    private int staff;
    private char color;
    private String name;
    RoleSupplier roleSupplier;
    long id;
    RoleManager roleManager;
    int ordinal;
    JDA jda;

    public DiscordRole(RoleSupplier supplier, int staffLevel, char color, String name){
        this.roleSupplier = supplier;
        this.staff = staffLevel;
        this.color = color;
        this.name = name;
        reload();
    }

    public DiscordRole(RoleSupplier supplier, char color, String name){
        this(supplier, 0, color, name);
    }

    public DiscordRole(RoleSupplier supplier, int staffLevel, String name){
        this(supplier, staffLevel, '7', name);
    }

    public DiscordRole(RoleSupplier supplier, String name){
        this(supplier, 0, '7', name);
    }

    public boolean isStaff(){
        return staff > 0;
    }

    public int getStaffLevel(){
        return staff;
    }

    public long getRoleId(){
        return id;
    }

    public Role getRole(){
        return jda.getRoleById(id);
    }

    public String getName(){
        return name;
    }

    public int ordinal(){
        return ordinal;
    }

    public String getStyle(){
        return isStaff() ? "§" + color + "§l" : "§7";
    }

    public String getChevron(){
        return (color == '7' ? "§9" : "§" + color) + "§l»";
    }

    public void reload(){
        id = roleSupplier.getId();
    }

    public interface RoleSupplier{
        String getIdString();

        default long getId(){
            return getIdString() != null && getIdString().matches("\\d+")? Long.parseLong(getIdString()): -1;
        }
    }
}
