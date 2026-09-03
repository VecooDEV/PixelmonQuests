package com.vecoo.pixlemonquests.task;

import com.feed_the_beast.ftblib.lib.config.ConfigGroup;
import com.feed_the_beast.ftblib.lib.io.DataIn;
import com.feed_the_beast.ftblib.lib.io.DataOut;
import com.feed_the_beast.ftbquests.quest.Quest;
import com.feed_the_beast.ftbquests.quest.QuestData;
import com.feed_the_beast.ftbquests.quest.task.Task;
import com.feed_the_beast.ftbquests.quest.task.TaskData;
import com.feed_the_beast.ftbquests.quest.task.TaskType;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.pokedex.Pokedex;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import com.vecoo.pixlemonquests.integration.PixelmonIntegration;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class PokedexTask extends Task {
    public int percent = 50;

    public PokedexTask(Quest quest) {
        super(quest);
    }

    @Override
    public TaskType getType() {
        return PixelmonIntegration.POKEDEX;
    }

    @Override
    public long getMaxProgress() {
        return 1L;
    }

    @Override
    public void writeData(NBTTagCompound nbt) {
        super.writeData(nbt);
        nbt.setInteger("percent", this.percent);
    }

    @Override
    public void readData(NBTTagCompound nbt) {
        super.readData(nbt);
        this.percent = nbt.getInteger("percent");
    }

    @Override
    public void writeNetData(DataOut data) {
        super.writeNetData(data);
        data.writeInt(this.percent);
    }

    @Override
    public void readNetData(DataIn data) {
        super.readNetData(data);
        this.percent = data.readInt();
    }

    @Override
    public int autoSubmitOnPlayerTick() {
        return 20;
    }

    @Override
    public String getAltTitle() {
        return I18n.format("pixelmonquests.pokedex.title", this.percent);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getConfig(ConfigGroup config) {
        super.getConfig(config);

        config.addInt("percent", () -> this.percent, v -> this.percent = v, 50, 1, 100).setDisplayName(new TextComponentTranslation("pixelmonquests.pokedex.percent"));
    }

    @Override
    public TaskData createData(QuestData questData) {
        return new Data(this, questData);
    }

    public static class Data extends TaskData<PokedexTask> {
        private Data(PokedexTask task, QuestData data) {
            super(task, data);
        }

        @Override
        public void submitTask(@Nonnull EntityPlayerMP player, @Nonnull ItemStack craftedItem) {
            if (isComplete()) {
                return;
            }

            PlayerPartyStorage storage = Pixelmon.storageManager.getParty(player);

            if (storage == null || storage.pokedex == null) {
                return;
            }

            int total = Pokedex.size();

            if (total <= 0) {
                return;
            }

            int caught = storage.pokedex.countCaught();

            if ((long) caught * 100L >= (long) total * task.percent) {
                this.addProgress(1L);
            }
        }
    }
}