package com.venterok.zdravie

import com.venterok.zdravie.features.CavePressure
import com.venterok.zdravie.features.FractureFeature
import com.venterok.zdravie.itemFeatures.ActionAfterMeal
import com.venterok.zdravie.itemFeatures.RawFood
import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.regex.Matcher
import java.util.regex.Pattern

class Zdravie : JavaPlugin() {
    override fun onEnable() {
        inst = this
        configFile = setUpConfig()

        Bukkit.getPluginManager().registerEvents(FractureFeature(), this)
        Bukkit.getPluginManager().registerEvents(ActionAfterMeal(), this)
        Bukkit.getPluginManager().registerEvents(RawFood(), this)
    }

    override fun onDisable() {

    }
    companion object {
        var configFile : File? = null
        private val pattern: Pattern = Pattern.compile("#[a-fA-F0-9]{6}")
        fun formatColor(msg: String): String {
            var msg = msg
            var matcher: Matcher = pattern.matcher(msg)
            while (matcher.find()) {
                val color = msg.substring(matcher.start(), matcher.end())
                msg = msg.replace(color, ChatColor.of(color).toString() + "")
                matcher = pattern.matcher(msg)
            }
            return ChatColor.translateAlternateColorCodes('&', msg)
        }
        fun unformatColor(msg: String): String {
            return ChatColor.stripColor(msg);
        }
        var inst: Zdravie? = null
    }
    private fun setUpConfig(): File {
        val config = File(dataFolder.toString() + File.separator + "config.yml")
        if (!config.exists()) {
            logger.info("Creating config file...")
            getConfig().options().copyDefaults(true)
            saveDefaultConfig()
        }
        return config
    }
}
