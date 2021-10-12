package com.venterok.zdravie.features

import com.venterok.zdravie.Zdravie.Companion.formatColor
import com.venterok.zdravie.utils.CompanionVariables.Companion.config
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class FractureFeature : Listener {
    @EventHandler
    fun onFracture (e: EntityDamageEvent) {
        if (e.entity.type != EntityType.PLAYER || e.cause != EntityDamageEvent.DamageCause.FALL) return

        val pl = e.entity as Player
        val dropHeight = pl.fallDistance

        if (dropHeight < config.getInt("fracture.min-block")) return

        pl.isGliding = true
        if (config.getBoolean("fracture.message-block")) {pl.sendMessage(formatColor(config.getString("fracture.message")!!))}

        val effectTime = config.getInt("fracture.effect-time") + (dropHeight * 10).toInt()
        val effect = PotionEffectType.getByName(config.getString("fracture.effect")!!)

        pl.addPotionEffect(PotionEffect(effect!!, effectTime, 1, true, false))
    }
}