package com.venterok.zdravie.itemFeatures

import com.ruverq.mauris.items.ItemsLoader
import com.venterok.zdravie.utils.CompanionVariables.Companion.config
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import kotlin.random.Random

class RawFood : Listener {
    @EventHandler
    fun rawFoodCheck(e: PlayerItemConsumeEvent) {

        val pl = e.player
        val food = e.item

        val itemList = config.getStringList("rawFood.items")

        //Mauris item check
        var name = "STONE"
        name = if (Bukkit.getPluginManager().getPlugin("Mauris") != null) {
            val miItem = ItemsLoader.getMaurisItem(food)
            if (miItem != null) {
                miItem.name
            } else food.type.name
        } else food.type.name

        if (!itemList.contains(name)) return

        val randomDuration = Random.nextInt(200, 260)
        val randomAmplifier = Random.nextInt(1, 3)

        if (config.getBoolean("rawfood.message-enabled")) {config.getString("rawfood.message")}
        pl.addPotionEffect(PotionEffect(PotionEffectType.POISON, 1 + randomDuration, 1 + randomAmplifier, true, false))

    }
}