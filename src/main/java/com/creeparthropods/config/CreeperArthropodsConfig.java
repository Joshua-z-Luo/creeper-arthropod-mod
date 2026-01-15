package com.creeparthropods.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class CreeperArthropodsConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File FILE = FabricLoader.getInstance().getConfigDir()
            .resolve("creeper_arthropods.json")
            .toFile();

    public String _comment = "Set any value below to true to disable that feature.";
    public boolean disableCreeperArthropod = false;
    public boolean disableAnvil = false;
    public boolean disableEnchantingTable = false;

    public static CreeperArthropodsConfig INSTANCE;

    public static void load() {
        try {
            if (FILE.exists()) {
                INSTANCE = GSON.fromJson(new FileReader(FILE), CreeperArthropodsConfig.class);
                if (INSTANCE == null) INSTANCE = new CreeperArthropodsConfig();
                if (INSTANCE._comment == null) INSTANCE._comment = "Set any value below to true to disable that feature.";
                save();
            } else {
                INSTANCE = new CreeperArthropodsConfig();
                save();
            }
        } catch (Exception e) {
            INSTANCE = new CreeperArthropodsConfig();
            save();
        }
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(FILE)) {
            GSON.toJson(INSTANCE, writer);
        } catch (Exception ignored) {}
    }
}
