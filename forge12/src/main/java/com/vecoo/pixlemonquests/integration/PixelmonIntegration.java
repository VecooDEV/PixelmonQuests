package com.vecoo.pixlemonquests.integration;

import com.feed_the_beast.ftblib.lib.icon.Icon;
import com.feed_the_beast.ftbquests.quest.task.TaskType;
import com.vecoo.pixlemonquests.task.PokemonCatchTask;
import com.vecoo.pixlemonquests.task.PokemonDefeatTask;
import com.vecoo.pixlemonquests.task.PokemonEggHatchTask;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PixelmonIntegration {
    public static TaskType POKEMON_CATCH;
    public static TaskType POKEMON_DEFEAT;
    public static TaskType POKEMON_EGG_HATCH;

    @SubscribeEvent
    public void registerTasks(RegistryEvent.Register<TaskType> event) {
        event.getRegistry().register(POKEMON_CATCH = new TaskType(PokemonCatchTask::new).setRegistryName("pokemon_catch").setIcon(Icon.getIcon("pixelmon:items/pokeballs/pokeball")));
        event.getRegistry().register(POKEMON_DEFEAT = new TaskType(PokemonDefeatTask::new).setRegistryName("pokemon_defeat").setIcon(Icon.getIcon("pixelmon:items/pokeballs/greatball")));
        event.getRegistry().register(POKEMON_EGG_HATCH = new TaskType(PokemonEggHatchTask::new).setRegistryName("pokemon_egg_hatch").setIcon(Icon.getIcon("pixelmon:sprites/eggs/egg1")));
    }
}