package com.venterok.zdravie.utils

import com.venterok.zdravie.Zdravie
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.potion.PotionEffectType
import kotlin.random.Random


class CompanionVariables {
    companion object {
        val config = Zdravie.inst!!.config
        val timerUpdate = config.getInt("timer-update-time").toLong()
        val mainPressureEffect : PotionEffectType = PotionEffectType.getByName(config.getString("cavePressure.main-pressure-effect")!!)!!

        val path = Zdravie.inst!!.dataFolder

        fun chanceHelper (chance: Int): Boolean {
            val random = Random.nextInt(1, 100)
            var trueOrFalse = false
            trueOrFalse = chance <= random
            return trueOrFalse
        }
        fun hasAvailableSlot(player: Player): Boolean {
            val inv: Inventory = player.inventory
            for (item in inv.contents) {
                if (item == null) {
                    return true
                }
            }
            return false
        }
    }
}