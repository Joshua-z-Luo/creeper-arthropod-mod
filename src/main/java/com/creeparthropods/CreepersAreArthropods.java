package com.creeparthropods;

import net.fabricmc.api.ModInitializer;
import com.creeparthropods.config.CreeperArthropodsConfig;

public class CreepersAreArthropods implements ModInitializer {

    @Override
    public void onInitialize() {
        CreeperArthropodsConfig.load();
        System.out.println("Creepers Are Arthropods mod initialized!");
    }
}
