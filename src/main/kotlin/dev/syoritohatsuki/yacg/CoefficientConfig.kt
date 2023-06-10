package dev.syoritohatsuki.yacg

import dev.syoritohatsuki.yacg.YetAnotherCobblestoneGenerator.MOD_ID
import dev.syoritohatsuki.yacg.YetAnotherCobblestoneGenerator.logger
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.nio.file.Paths
import kotlin.system.exitProcess

object CoefficientConfig {
    private val configDir: File = Paths.get("", "config", MOD_ID).toFile()
    private val configFile = File(configDir, "config.json")

    private val json = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    init {
        if (!configDir.exists()) configDir.mkdirs()
        if (!configFile.exists()) configFile.writeText(json.encodeToString(Generators()))
    }

    fun getBlocks(type: String): Set<Generators.GenerateItem>? = try {
        json.decodeFromString<Generators>(configFile.readText()).generators[type]
    } catch (e: Exception) {
        logger.error(e.localizedMessage)
        emptySet()
    }

    fun getTypes(): Set<String> = try {
        json.decodeFromString<Generators>(configFile.readText()).generators.keys
    } catch (e: Exception) {
        try {
            configFile.apply {
                copyTo(File(configDir, "backup_config.json"))
                writeText(json.encodeToString(Generators()))
            }
            json.decodeFromString<Generators>(configFile.readText()).generators.keys
        } catch (e: Exception) {
            logger.error(e.localizedMessage)
            exitProcess(0)
        }
    }

    @Serializable
    data class Generators(
        val generators: Map<String, Set<GenerateItem>> = mapOf(
            "cobble" to setOf(
                GenerateItem("minecraft:cobblestone", 100, 1),
                GenerateItem("minecraft:cobbled_deepslate", 30, 1),
                GenerateItem("minecraft:mossy_cobblestone", 10, 1),
            ),
            "ore" to setOf(
                GenerateItem("minecraft:coal_ore", 100, 1),
                GenerateItem("minecraft:copper_ore", 70, 1),
                GenerateItem("minecraft:iron_ore", 50, 1),
                GenerateItem("minecraft:gold_ore", 30, 1),
                GenerateItem("minecraft:redstone_ore", 20, 1),
                GenerateItem("minecraft:lapis_ore", 20, 1),
                GenerateItem("minecraft:diamond_ore", 15, 1),
                GenerateItem("minecraft:emerald_ore", 10, 1),
                GenerateItem("minecraft:nether_quartz_ore", 5, 1),
            ),
            "stone" to setOf(
                GenerateItem("minecraft:stone", 100, 1),
                GenerateItem("minecraft:diorite", 50, 1),
                GenerateItem("minecraft:granite", 50, 1),
                GenerateItem("minecraft:andesite", 50, 1),
                GenerateItem("minecraft:calcite", 20, 1),
                GenerateItem("minecraft:dripstone_block", 20, 1),
                GenerateItem("minecraft:deepslate", 5, 1),
            )
        )
    ) {
        @Serializable
        data class GenerateItem(
            val itemId: String,
            val coefficient: Int,
            val count: Int
        )
    }
}