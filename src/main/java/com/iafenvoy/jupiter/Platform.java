package com.iafenvoy.jupiter;

//? neoforge {
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.neoforgespi.language.IModInfo;
//?}
//? forge {
/*import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.IModInfo;
*///?}
//? fabric {
/*import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
*///?}

public final class Platform {
    public static String resolveModName(String id) {
        //? neoforge {
        return ModList.get().getModContainerById(id).map(ModContainer::getModInfo).map(IModInfo::getDisplayName).orElse("%ERROR%");
        //?}
        //? forge {
        /*return ModList.get().getModContainerById(id).map(ModContainer::getModInfo).map(IModInfo::getDisplayName).orElse("%ERROR%");
         *///?}
        //? fabric {
        /*return FabricLoader.getInstance().getModContainer(id).map(ModContainer::getMetadata).map(ModMetadata::getName).orElse("%ERROR%");
         *///?}
    }
}
