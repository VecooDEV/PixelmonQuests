package com.vecoo.pixlemonquests.task;

import com.feed_the_beast.ftblib.lib.config.ConfigGroup;
import com.feed_the_beast.ftblib.lib.icon.Icon;
import com.feed_the_beast.ftblib.lib.io.DataIn;
import com.feed_the_beast.ftblib.lib.io.DataOut;
import com.feed_the_beast.ftbquests.quest.Quest;
import com.feed_the_beast.ftbquests.quest.QuestData;
import com.feed_the_beast.ftbquests.quest.task.Task;
import com.feed_the_beast.ftbquests.quest.task.TaskData;
import com.feed_the_beast.ftbquests.quest.task.TaskType;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.vecoo.pixlemonquests.integration.PixelmonIntegration;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PokemonEggHatchTask extends Task {
    public String pokemon = "Any";
    public String nature = "Any";
    public String gender = "Any";
    public int ivs = 0;
    public int generation = 0;
    public boolean shiny = false;
    public boolean beast = false;
    public boolean legendary = false;
    public long amount = 10L;

    public PokemonEggHatchTask(Quest quest) {
        super(quest);
    }

    @Override
    public TaskType getType() {
        return PixelmonIntegration.POKEMON_EGG_HATCH;
    }

    @Override
    public long getMaxProgress() {
        return this.amount;
    }

    @Override
    public void writeData(NBTTagCompound nbt) {
        super.writeData(nbt);
        nbt.setString("pokemon", this.pokemon);
        nbt.setString("nature", this.nature);
        nbt.setString("gender", this.gender);
        nbt.setInteger("ivs", this.ivs);
        nbt.setInteger("generation", this.generation);
        nbt.setBoolean("shiny", this.shiny);
        nbt.setBoolean("beast", this.beast);
        nbt.setBoolean("legendary", this.legendary);
        nbt.setLong("amount", this.amount);
    }

    @Override
    public void readData(NBTTagCompound nbt) {
        super.readData(nbt);
        this.pokemon = nbt.getString("pokemon");
        this.nature = nbt.getString("nature");
        this.gender = nbt.getString("gender");
        this.ivs = nbt.getInteger("ivs");
        this.generation = nbt.getInteger("generation");
        this.shiny = nbt.getBoolean("shiny");
        this.beast = nbt.getBoolean("beast");
        this.legendary = nbt.getBoolean("legendary");
        this.amount = nbt.getLong("amount");
    }

    @Override
    public void writeNetData(DataOut data) {
        super.writeNetData(data);
        data.writeString(this.pokemon);
        data.writeString(this.nature);
        data.writeString(this.gender);
        data.writeInt(this.ivs);
        data.writeInt(this.generation);
        data.writeBoolean(this.shiny);
        data.writeBoolean(this.beast);
        data.writeBoolean(this.legendary);
        data.writeVarLong(this.amount);
    }

    @Override
    public void readNetData(DataIn data) {
        super.readNetData(data);
        this.pokemon = data.readString();
        this.nature = data.readString();
        this.gender = data.readString();
        this.ivs = data.readInt();
        this.generation = data.readInt();
        this.shiny = data.readBoolean();
        this.beast = data.readBoolean();
        this.legendary = data.readBoolean();
        this.amount = data.readVarLong();
    }

    @Override
    public String getAltTitle() {
        return I18n.format("pixelmonquests.pokemon_egg_hatch.title", this.amount, this.pokemon);
    }

    @Override
    public Icon getAltIcon() {
        return super.getAltIcon();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getConfig(ConfigGroup config) {
        super.getConfig(config);
        config.addString("pokemon", () -> this.pokemon, v -> this.pokemon = v, "Any").setDisplayName(new TextComponentTranslation("pixelmonquests.pokemon"));
        config.addString("nature", () -> this.nature, v -> this.nature = v, "Any").setDisplayName(new TextComponentTranslation("pixelmonquests.nature"));
        config.addString("gender", () -> this.gender, v -> this.gender = v, "Any").setDisplayName(new TextComponentTranslation("pixelmonquests.gender"));
        config.addInt("ivs", () -> this.ivs, v -> this.ivs = v, 0, 0, Integer.MAX_VALUE).setDisplayName(new TextComponentTranslation("pixelmonquests.ivs"));
        config.addInt("generation", () -> this.generation, v -> this.generation = v, 0, 0, Integer.MAX_VALUE).setDisplayName(new TextComponentTranslation("pixelmonquests.generation"));
        config.addBool("shiny", () -> this.shiny, v -> this.shiny = v, false).setDisplayName(new TextComponentTranslation("pixelmonquests.shiny"));
        config.addBool("beast", () -> this.beast, v -> this.beast = v, false).setDisplayName(new TextComponentTranslation("pixelmonquests.beast"));
        config.addBool("legendary", () -> this.legendary, v -> this.legendary = v, false).setDisplayName(new TextComponentTranslation("pixelmonquests.legendary"));
        config.addLong("amount", () -> this.amount, v -> this.amount = v, 10L, 1L, Long.MAX_VALUE).setDisplayName(new TextComponentTranslation("pixelmonquests.amount"));
    }

    @Override
    public TaskData createData(QuestData questData) {
        return new PokemonEggHatchTask.Data(this, questData);
    }

    public static class Data extends TaskData<PokemonEggHatchTask> {
        private Data(PokemonEggHatchTask task, QuestData data) {
            super(task, data);
        }

        public void progress(Pokemon pokemon) {
            if (this.isComplete()) {
                return;
            }

            EnumSpecies pokemonSpec = pokemon.getSpecies();

            if (!task.pokemon.equalsIgnoreCase(pokemonSpec.getPokemonName()) && !task.pokemon.equalsIgnoreCase("Any")) {
                return;
            }

            if (!task.nature.equalsIgnoreCase(pokemon.getNature().getName()) && !task.nature.equalsIgnoreCase("Any")) {
                return;
            }

            if (!task.gender.equalsIgnoreCase(pokemon.getGender().getName()) && !task.gender.equalsIgnoreCase("Any")) {
                return;
            }

            if (task.ivs > pokemon.getIVs().getTotal()) {
                return;
            }

            if (task.generation != pokemonSpec.getGeneration() && task.generation != 0) {
                return;
            }

            if (task.shiny && !pokemon.isShiny()) {
                return;
            }

            if (task.beast && !pokemonSpec.isUltraBeast()) {
                return;
            }

            if (task.legendary && !pokemonSpec.isLegendary()) {
                return;
            }

            this.addProgress(1L);
        }
    }
}
