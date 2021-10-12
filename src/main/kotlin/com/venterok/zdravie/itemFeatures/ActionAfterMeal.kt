package com.venterok.zdravie.itemFeatures

import com.ruverq.mauris.items.ItemsLoader
import com.venterok.zdravie.Zdravie.Companion.formatColor
import com.venterok.zdravie.utils.CompanionVariables.Companion.chanceHelper
import com.venterok.zdravie.utils.CompanionVariables.Companion.config
import com.venterok.zdravie.utils.CompanionVariables.Companion.hasAvailableSlot
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class ActionAfterMeal : Listener {
    @EventHandler
    fun mealActions(e: PlayerItemConsumeEvent) {
        val pl = e.player
        val food = e.item

        val itemList = config.getConfigurationSection("mealActions.items")!!.getKeys(false)

        //Mauris item check
        var name = "STONE"
        name = if (Bukkit.getPluginManager().getPlugin("Mauris") != null) {
            val miItem = ItemsLoader.getMaurisItem(food)
            if (miItem != null) {
                miItem.name
            } else food.type.name
        } else food.type.name

        //Check item in list
        if (!itemList.contains(name)) return
        val itemActionList = config.getConfigurationSection("mealActions.items.$name")!!.getKeys(false) //feature list

        // Message
        if (itemActionList.contains("message")) {pl.sendMessage(formatColor("mealActions.items.$name.message"))}
        // Heart heal
        if (itemActionList.contains("hearts-heal")) {
            val howManyHeal = config.getInt("mealActions.items.$name.hearts-heal")

            if (pl.health + howManyHeal > 20) {
                val heal = pl.health + howManyHeal - 20
                pl.health = heal
            }
            else pl.health = pl.health + howManyHeal
        }
        // Receive items
        if (itemActionList.contains("receive-items")) {
            val receiveItems = config.getConfigurationSection("mealActions.items.$name.receive-items")!!.getKeys(false)

            for (item in receiveItems) {

                if (config.getStringList("mealActions.items.$name.receive-items.$item").contains("chance")) {
                    if (!chanceHelper(config.getInt("mealActions.items.$name.receive-items.$item.chance"))) return } // Chance

                // Get Mauris or Vanilla item
                val rItem: ItemStack = if (ItemsLoader.getMaurisItem(item).asItemStack == null) {
                    ItemStack(Material.valueOf(item))
                } else ItemsLoader.getMaurisItem(item).asItemStack

                val amount = config.getInt("mealActions.items.$name.receive-items.$item.amount")
                rItem.amount = amount

                if (!hasAvailableSlot(pl)) {
                    pl.inventory.addItem(rItem)
                    pl.updateInventory()
                }
                pl.location.world!!.dropItem(pl.location, rItem)
            }
        }
        //Effects
        if (itemActionList.contains("effects")) {
            val effectsList = config.getConfigurationSection("mealActions.items.$name.effects")!!.getKeys(false)

            for (effect in effectsList) {
                if (config.getStringList("mealActions.items.$name.effects.$effect").contains("chance")) {
                    if (!chanceHelper(config.getInt("mealActions.items.$name.effects.$effect.chance"))) return } // Chance

                val givenEffect : PotionEffectType = PotionEffectType.getByName(effect)!!
                pl.addPotionEffect(PotionEffect(givenEffect, config.getInt("mealActions.items.$name.effects.$effect.duration"), config.getInt("mealActions.items.$name.effects.$effect.amplifier"), true, false))
            }
        }
    }
}