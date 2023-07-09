package dev.syoritohatsuki.yacg.config

import dev.syoritohatsuki.yacg.YetAnotherCobblestoneGenerator.MOD_ID
import dev.syoritohatsuki.yacg.YetAnotherCobblestoneGenerator.logger
import dev.syoritohatsuki.yacg.common.item.UpgradeItem
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.nio.file.Paths
import kotlin.system.exitProcess

typealias Upgrades = Map<UpgradeItem.UpgradesTypes, Byte>

object UpgradesConfig {
    private val configDir: File = Paths.get("", "config", MOD_ID).toFile()
    private val configFile = File(configDir, "upgrades.json")

    private val json = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    private val default: Upgrades = mapOf(
        UpgradeItem.UpgradesTypes.COUNT to 2,
        UpgradeItem.UpgradesTypes.COEFFICIENT to 2,
        UpgradeItem.UpgradesTypes.SPEED to 2,
    )

    init {
        if (!configDir.exists()) configDir.mkdirs()
        if (!configFile.exists()) configFile.writeText(json.encodeToString(default))
    }

    fun getUpgradeModify(type: UpgradeItem.UpgradesTypes): Byte? = try {
        json.decodeFromString<Upgrades>(configFile.readText())[type]
    } catch (e: Exception) {
        try {
            configFile.apply {
                copyTo(File(configDir, "backup_upgrades.json"))
                writeText(json.encodeToString(default))
            }
            json.decodeFromString<Upgrades>(configFile.readText())[type]
        } catch (e: Exception) {
            logger.error(e.localizedMessage)
            exitProcess(0)
        }
    }
}