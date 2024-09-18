package com.vecoo.pixlemonquests.listener;

import com.feed_the_beast.ftbquests.quest.QuestData;
import com.feed_the_beast.ftbquests.quest.ServerQuestFile;
import com.feed_the_beast.ftbquests.quest.task.TaskData;
import com.pixelmonmod.pixelmon.api.events.BeatWildPixelmonEvent;
import com.pixelmonmod.pixelmon.api.events.CaptureEvent;
import com.pixelmonmod.pixelmon.api.events.EggHatchEvent;
import com.pixelmonmod.pixelmon.api.events.battles.AttackEvent;
import com.pixelmonmod.pixelmon.api.events.battles.BattleEndEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.enums.battle.EnumBattleEndCause;
import com.vecoo.pixlemonquests.task.PokemonCatchTask;
import com.vecoo.pixlemonquests.task.PokemonDefeatTask;
import com.vecoo.pixlemonquests.task.PokemonEggHatchTask;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TaskListener {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onCatch(CaptureEvent.SuccessfulCapture event) {
        EntityPixelmon entityPixelmon = event.getPokemon();

        if (entityPixelmon == null) {
            return;
        }

        QuestData data = ServerQuestFile.INSTANCE.getData(event.player);

        if (data == null) {
            return;
        }

        for (PokemonCatchTask task : ServerQuestFile.INSTANCE.collect(PokemonCatchTask.class)) {
            TaskData taskData = data.getTaskData(task);

            if (taskData.progress < task.getMaxProgress() && task.quest.canStartTasks(data)) {
                ((PokemonCatchTask.Data) taskData).progress(entityPixelmon);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onCatchRaid(CaptureEvent.SuccessfulRaidCapture event) {
        EntityPixelmon entityPixelmon = event.getPokemon();

        if (entityPixelmon == null) {
            return;
        }

        QuestData data = ServerQuestFile.INSTANCE.getData(event.player);

        if (data == null) {
            return;
        }

        for (PokemonCatchTask task : ServerQuestFile.INSTANCE.collect(PokemonCatchTask.class)) {
            TaskData taskData = data.getTaskData(task);
            if (taskData.progress < task.getMaxProgress() && task.quest.canStartTasks(data)) {
                ((PokemonCatchTask.Data) taskData).progress(entityPixelmon);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onDefeat(BeatWildPixelmonEvent event) {
        EntityPixelmon entityPixelmon = event.wpp.getFaintedPokemon().entity;

        if (entityPixelmon == null || entityPixelmon.hasOwner()) {
            return;
        }

        QuestData data = ServerQuestFile.INSTANCE.getData(event.player);

        if (data == null) {
            return;
        }

        for (PokemonDefeatTask task : ServerQuestFile.INSTANCE.collect(PokemonDefeatTask.class)) {
            TaskData taskData = data.getTaskData(task);
            if (taskData.progress < task.getMaxProgress() && task.quest.canStartTasks(data)) {
                ((PokemonDefeatTask.Data) taskData).progress(entityPixelmon);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onHatch(EggHatchEvent.Post event) {
        Pokemon pokemon = event.getPokemon();

        if (pokemon == null) {
            return;
        }

        QuestData data = ServerQuestFile.INSTANCE.getData(event.getPlayer());

        if (data == null) {
            return;
        }

        for (PokemonEggHatchTask task : ServerQuestFile.INSTANCE.collect(PokemonEggHatchTask.class)) {
            TaskData taskData = data.getTaskData(task);
            if (taskData.progress < task.getMaxProgress() && task.quest.canStartTasks(data)) {
                ((PokemonEggHatchTask.Data) taskData).progress(pokemon);
            }
        }
    }
}