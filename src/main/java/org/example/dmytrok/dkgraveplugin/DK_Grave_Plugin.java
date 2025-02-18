package org.example.dmytrok.dkgraveplugin;

import org.bukkit.plugin.java.JavaPlugin;
import org.example.dmytrok.dkgraveplugin.events.GraveAccessEvent;
import org.example.dmytrok.dkgraveplugin.events.GraveKeyAfterRebornEvent;
import org.example.dmytrok.dkgraveplugin.events.GraveSpawnEvent;
import org.example.dmytrok.dkgraveplugin.commands.GraveBreakerCommand;

public final class DK_Grave_Plugin extends JavaPlugin {

    private static DK_Grave_Plugin instance;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("DK_Grave_Plugin enabled");
        getServer().getPluginManager().registerEvents(new GraveSpawnEvent(), this);
        getServer().getPluginManager().registerEvents(new GraveAccessEvent(), this);
        getServer().getPluginManager().registerEvents(new GraveKeyAfterRebornEvent(), this);

        if(getCommand("getGraveBreaker") != null) {
            getCommand("getGraveBreaker").setExecutor(new GraveBreakerCommand());
            getLogger().info("Grave Breaker is work");
        } else {
            getLogger().info("Something WRONG");
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("DK_Grave_Plugin disabled");
    }

    public static DK_Grave_Plugin getInstance() {
        return instance;
    }
}

