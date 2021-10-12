package com.venterok.zdravie.features

import com.venterok.zdravie.Zdravie
import com.venterok.zdravie.Zdravie.Companion.formatColor
import com.venterok.zdravie.utils.CompanionVariables.Companion.config
import com.venterok.zdravie.utils.CompanionVariables.Companion.mainPressureEffect
import com.venterok.zdravie.utils.CompanionVariables.Companion.timerUpdate
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable

class CavePressure {
    companion object {
        val caveList = ArrayList<Player>()
    }
    fun timer() {
        object : BukkitRunnable() {
            override fun run() {
                val onlinePlayers = Bukkit.getOnlinePlayers()

                for (player in onlinePlayers) {
                    if (player.location.blockY > config.getInt("cavePressure.Y-Level")) {
                        if(caveList.contains(player)) {caveList.remove(player)}

                        if(player.hasPotionEffect(mainPressureEffect)) {player.removePotionEffect(mainPressureEffect)}
                        continue
                    }

                    if(!caveList.contains(player) && config.getBoolean("cavePressure.message-enabled")) {player.sendMessage(formatColor(config.getString("cavePressure.message")!!))}
                    caveList.add(player)
                    playerPressureEffect(player)

                }
            }
        }.runTaskTimer(Zdravie.inst!!, 0, timerUpdate)
    }
    fun playerPressureEffect(pl: Player) {

        pl.addPotionEffect(PotionEffect(mainPressureEffect, 100000, 0, true, false))
        val effects = config.getConfigurationSection("cavePressure.effects")!!.getKeys(false)

        for (effect in effects) {
            val effectA = config.getInt("cavePressure.effects.$effect.amplifier")
            pl.addPotionEffect(PotionEffect(PotionEffectType.getByName(effect!!)!!, timerUpdate.toInt() + 2, effectA, true, false))
        }
    }
}