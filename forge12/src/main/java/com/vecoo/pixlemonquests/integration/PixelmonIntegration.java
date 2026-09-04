package com.vecoo.pixlemonquests.integration;

import com.feed_the_beast.ftblib.lib.icon.Icon;
import com.feed_the_beast.ftbquests.quest.task.TaskType;
import com.vecoo.pixlemonquests.task.*;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PixelmonIntegration {
    public static TaskType POKEMON_CATCH;
    public static TaskType POKEMON_DEFEAT;
    public static TaskType POKEMON_EGG_HATCH;
    public static TaskType POKEDEX;
    public static TaskType POKEMON_SUBMIT;

    @SubscribeEvent
    public void registerTasks(RegistryEvent.Register<TaskType> event) {
        event.getRegistry().register(POKEMON_CATCH = new TaskType(PokemonCatchTask::new).setRegistryName("pokemon_catch").setIcon(Icon.getIcon("pixelmon:items/pokeballs/pokeball")));
        event.getRegistry().register(POKEMON_DEFEAT = new TaskType(PokemonDefeatTask::new).setRegistryName("pokemon_defeat").setIcon(Icon.getIcon("pixelmon:items/pokeballs/greatball")));
        event.getRegistry().register(POKEMON_EGG_HATCH = new TaskType(PokemonEggHatchTask::new).setRegistryName("pokemon_egg_hatch").setIcon(Icon.getIcon("pixelmon:sprites/eggs/egg1")));
        event.getRegistry().register(POKEDEX = new TaskType(PokedexTask::new).setRegistryName("pokedex").setIcon(Icon.getIcon("pixelmon:items/pokeballs/masterball")));
        event.getRegistry().register(POKEMON_SUBMIT = new TaskType(PokemonSubmitTask::new).setRegistryName("pokemon_submit").setIcon(Icon.getIcon("pixelmon:items/pokeballs/parkball")));
    }
}