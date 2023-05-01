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

    fun getBlocks(type: String): Map<String, Int>? = try {
        json.decodeFromString<Generators>(configFile.readText()).generators[type]
    } catch (e: Exception) {
        logger.error(e.localizedMessage)
        emptyMap()
    }

    fun getTypes(): Set<String> = try {
        json.decodeFromString<Generators>(configFile.readText()).generators.keys
    } catch (e: Exception) {
        logger.error(e.localizedMessage)
        exitProcess(0)
    }

    @Serializable
    data class Generators(
        val generators: Map<String, Map<String, Int>> = mapOf(
            "cobble" to mapOf(
                "minecraft:cobblestone" to 100,
                "minecraft:cobbled_deepslate" to 30,
                "minecraft:mossy_cobblestone" to 10,
            ),
            "ore" to mapOf(
                "minecraft:coal_ore" to 100,
                "minecraft:copper_ore" to 70,
                "minecraft:iron_ore" to 50,
                "minecraft:gold_ore" to 30,
                "minecraft:redstone_ore" to 20,
                "minecraft:lapis_ore" to 20,
                "minecraft:diamond_ore" to 15,
                "minecraft:emerald_ore" to 10,
                "minecraft:nether_quartz_ore" to 5,
            ),
            "stone" to mapOf(
                "minecraft:stone" to 100,
                "minecraft:diorite" to 50,
                "minecraft:granite" to 50,
                "minecraft:andesite" to 50,
                "minecraft:calcite" to 20,
                "minecraft:dripstone_block" to 20,
                "minecraft:deepslate" to 5,
            )
        )
    )
}