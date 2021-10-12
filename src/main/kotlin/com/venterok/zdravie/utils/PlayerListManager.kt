package com.venterok.zdravie.utils

import com.venterok.zdravie.utils.CompanionVariables.Companion.path
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File

class PlayerListManager {
    companion object {
        val playersList = hashMapOf<Player, PlayerInfo>()

        fun setVivacity(player: String, vivacity: Int) {
            val file = File("$path/data.yml")
            val data = YamlConfiguration.loadConfiguration(file)

            data.set("$player.vivacity", vivacity)
            data.save(file)
        }
        fun setTimeWithoutSleep(player: String, withoutSleep: Int) {
            val file = File("$path/data.yml")
            val data = YamlConfiguration.loadConfiguration(file)

            data.set("$player.vivacity", withoutSleep)
            data.save(file)
        }
        fun setData(player: String, withoutSleep: Int, vivacity: Int) {
            val file = File("$path/data.yml")
            val data = YamlConfiguration.loadConfiguration(file)

            data.set("$player.vivacity", withoutSleep)
            data.set("$player.vivacity", vivacity)
            data.save(file)
        }
    }

}