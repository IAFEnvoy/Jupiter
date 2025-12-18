@file:Suppress("UnstableApiUsage")

plugins {
    id("fabric-loom")
    id("dev.kikugie.postprocess.jsonlang")
    id("me.modmuss50.mod-publish-plugin")
}

tasks.named<ProcessResources>("processResources") {
    val props = HashMap<String, String>().apply {
        this["version"] = project.property("mod.version") as String
        this["minecraft"] = project.property("deps.minecraft") as String
    }

    filesMatching(listOf("fabric.mod.json", "META-INF/neoforge.mods.toml", "META-INF/mods.toml")) {
        expand(props)
    }
}

version = "${property("mod.version")}-${property("deps.minecraft")}-fabric"
base.archivesName = property("mod.id") as String

jsonlang {
    languageDirectories = listOf("assets/${property("mod.id")}/lang")
    prettyPrint = true
}

repositories {
    mavenLocal()
    maven("https://maven.parchmentmc.org") { name = "ParchmentMC" }
    maven("https://maven.terraformersmc.com/") { name = "ModMenu" }
    maven("https://maven.nucleoid.xyz/") { name = "Placeholder API" }
    maven("https://api.modrinth.com/maven") { name = "Forge Config Api Port" }
}

dependencies {
    minecraft("com.mojang:minecraft:${property("deps.minecraft")}")
    mappings(loom.layered {
        officialMojangMappings()
        if (hasProperty("deps.parchment"))
            parchment("org.parchmentmc.data:parchment-${property("deps.parchment")}@zip")
    })
    modImplementation("net.fabricmc:fabric-loader:${property("deps.fabric-loader")}")

    modImplementation("net.fabricmc.fabric-api:fabric-api:${property("deps.fabric-api")}")
    var fabricApiVersion = property("deps.fabric-api") as String
    include(fabricApi.module("fabric-api-base", fabricApiVersion))
    include(fabricApi.module("fabric-networking-api-v1", fabricApiVersion))
    if (stonecutter.eval(stonecutter.current.version, "<=1.21.8"))
        include(fabricApi.module("fabric-resource-loader-v0", fabricApiVersion))
    else
        include(fabricApi.module("fabric-resource-loader-v1", fabricApiVersion))

    modImplementation("com.terraformersmc:modmenu:${property("deps.mod_menu")}")

    // @formatter:off
    // Night Config
    modCompileOnly("com.electronwill.night-config:core:3.8.2")
    modCompileOnly("com.electronwill.night-config:toml:3.8.2")
    modCompileOnly("maven.modrinth:forge-config-api-port:${property("deps.forge_config_api_port")}")
    // @formatter:on
}

fabricApi {
    configureDataGeneration() {
        outputDirectory = file("$rootDir/src/main/generated")
        client = true
    }
}

tasks {
    processResources {
        exclude("**/neoforge.mods.toml", "**/mods.toml", "**/pack.mcmeta")
        dependsOn("stonecutterGenerate")
    }

    register<Copy>("buildAndCollect") {
        group = "build"
        from(remapJar.map { it.archiveFile })
        into(rootProject.layout.buildDirectory.file("libs/${project.property("mod.version")}"))
        dependsOn("build")
    }
}

java {
    withSourcesJar()
    val javaCompat = if (stonecutter.eval(stonecutter.current.version, ">=1.20.5")) JavaVersion.VERSION_21
    else if (stonecutter.eval(stonecutter.current.version, ">=1.18")) JavaVersion.VERSION_17
    else if (stonecutter.eval(stonecutter.current.version, ">=1.17")) JavaVersion.VERSION_16
    else JavaVersion.VERSION_1_8
    sourceCompatibility = javaCompat
    targetCompatibility = javaCompat
}

val additionalVersionsStr = findProperty("publish.additionalVersions") as String?
val additionalVersions: List<String> = additionalVersionsStr
    ?.split(",")
    ?.map { it.trim() }
    ?.filter { it.isNotEmpty() }
    ?: emptyList()

publishMods {
    file = tasks.remapJar.map { it.archiveFile.get() }
    additionalFiles.from(tasks.remapSourcesJar.map { it.archiveFile.get() })

    val modVersion = property("mod.version") as String
    type = if (modVersion.contains("alpha")) ALPHA
    else if (modVersion.contains("beta")) BETA
    else STABLE

    displayName = "${property("mod.name")} $modVersion for ${stonecutter.current.version} Fabric"
    version = "${modVersion}-${property("deps.minecraft")}-fabric"
    changelog = provider { rootProject.file("CHANGELOG.md").readText() }
    modLoaders.add("fabric")

    modrinth {
        projectId = property("publish.modrinth") as String
        accessToken = env.MODRINTH_API_KEY.orNull()
        minecraftVersions.add(stonecutter.current.version)
        minecraftVersions.addAll(additionalVersions)
    }

    curseforge {
        projectId = property("publish.curseforge") as String
        accessToken = env.CURSEFORGE_API_KEY.orNull()
        minecraftVersions.add(stonecutter.current.version)
        minecraftVersions.addAll(additionalVersions)
    }
}
