package com.vecoo.pixlemonquests.task;

import com.feed_the_beast.ftblib.lib.config.ConfigGroup;
import com.feed_the_beast.ftblib.lib.icon.Icon;
import com.feed_the_beast.ftblib.lib.icon.ItemIcon;
import com.feed_the_beast.ftblib.lib.io.DataIn;
import com.feed_the_beast.ftblib.lib.io.DataOut;
import com.feed_the_beast.ftbquests.quest.Quest;
import com.feed_the_beast.ftbquests.quest.QuestData;
import com.feed_the_beast.ftbquests.quest.task.Task;
import com.feed_the_beast.ftbquests.quest.task.TaskData;
import com.feed_the_beast.ftbquests.quest.task.TaskType;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.BaseStats;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.items.ItemPixelmonSprite;
import com.vecoo.pixlemonquests.integration.PixelmonIntegration;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PokemonDefeatTask extends Task {
    public String pokemon = "Any";
    public int level = 0;
    public String type1 = "Any";
    public String type2 = "Any";
    public String boss = "None";
    public String gender = "Any";
    public int generation = 0;
    public String form = "Any";
    public String customTexture = "Any";
    public boolean shiny = false;
    public boolean beast = false;
    public boolean legendary = false;
    public long amount = 10L;

    public PokemonDefeatTask(Quest quest) {
        super(quest);
    }

    @Override
    public TaskType getType() {
        return PixelmonIntegration.POKEMON_DEFEAT;
    }

    @Override
    public long getMaxProgress() {
        return this.amount;
    }

    @Override
    public void writeData(NBTTagCompound nbt) {
        super.writeData(nbt);
        nbt.setString("pokemon", this.pokemon);
        nbt.setInteger("level", this.level);
        nbt.setString("type1", this.type1);
        nbt.setString("type2", this.type2);
        nbt.setString("boss", this.boss);
        nbt.setString("gender", this.gender);
        nbt.setInteger("generation", this.generation);
        nbt.setString("form", this.form);
        nbt.setString("customTexture", this.customTexture);
        nbt.setBoolean("shiny", this.shiny);
        nbt.setBoolean("beast", this.beast);
        nbt.setBoolean("legendary", this.legendary);
        nbt.setLong("amount", this.amount);
    }

    @Override
    public void readData(NBTTagCompound nbt) {
        super.readData(nbt);
        this.pokemon = nbt.getString("pokemon");
        this.level = nbt.getInteger("level");
        this.type1 = nbt.getString("type1");
        this.type2 = nbt.getString("type2");
        this.boss = nbt.getString("boss");
        this.gender = nbt.getString("gender");
        this.generation = nbt.getInteger("generation");
        this.form = nbt.getString("form");
        this.customTexture = nbt.getString("customTexture");
        this.shiny = nbt.getBoolean("shiny");
        this.beast = nbt.getBoolean("beast");
        this.legendary = nbt.getBoolean("legendary");
        this.amount = nbt.getLong("amount");
    }

    @Override
    public void writeNetData(DataOut data) {
        super.writeNetData(data);
        data.writeString(this.pokemon);
        data.writeInt(this.level);
        data.writeString(this.type1);
        data.writeString(this.type2);
        data.writeString(this.boss);
        data.writeString(this.gender);
        data.writeInt(this.generation);
        data.writeString(this.form);
        data.writeString(this.customTexture);
        data.writeBoolean(this.shiny);
        data.writeBoolean(this.beast);
        data.writeBoolean(this.legendary);
        data.writeVarLong(this.amount);
    }

    @Override
    public void readNetData(DataIn data) {
        super.readNetData(data);
        this.pokemon = data.readString();
        this.level = data.readInt();
        this.type1 = data.readString();
        this.type2 = data.readString();
        this.boss = data.readString();
        this.gender = data.readString();
        this.generation = data.readInt();
        this.form = data.readString();
        this.customTexture = data.readString();
        this.shiny = data.readBoolean();
        this.beast = data.readBoolean();
        this.legendary = data.readBoolean();
        this.amount = data.readVarLong();
    }

    @Override
    public String getAltTitle() {
        return I18n.format("pixelmonquests.pokemon_defeat.title", this.amount, this.pokemon);
    }

    @Override
    public Icon getAltIcon() {
        if (EnumSpecies.getFromNameAnyCaseNoTranslate(this.pokemon) != null) {
            Pokemon pokemon = Pixelmon.pokemonFactory.create(PokemonSpec.from(this.pokemon));

            if (this.shiny) {
                pokemon.setShiny(true);
            }

            if (!this.form.equalsIgnoreCase("Any")) {
                switch (this.form) {
                    case "alolan": {
                        pokemon.setForm(1);
                        break;
                    }
                    case "galarian": {
                        pokemon.setForm(2);
                        break;
                    }
                    case "hisuian": {
                        pokemon.setForm(3);
                        break;
                    }
                }
            }

            if (!this.customTexture.equalsIgnoreCase("Any")) {
                pokemon.setCustomTexture(this.customTexture);
            }

            return ItemIcon.getItemIcon(ItemPixelmonSprite.getPhoto(pokemon));
        }
        return super.getAltIcon();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getConfig(ConfigGroup config) {
        super.getConfig(config);
        config.addString("pokemon", () -> this.pokemon, v -> this.pokemon = v, "Any").setDisplayName(new TextComponentTranslation("pixelmonquests.pokemon"));
        config.addInt("level", () -> this.level, v -> this.level = v, 0, 0, Integer.MAX_VALUE).setDisplayName(new TextComponentTranslation("pixelmonquests.level"));
        config.addString("type1", () -> this.type1, v -> this.type1 = v, "Any").setDisplayName(new TextComponentTranslation("pixelmonquests.type1"));
        config.addString("type2", () -> this.type2, v -> this.type2 = v, "Any").setDisplayName(new TextComponentTranslation("pixelmonquests.type2"));
        config.addString("boss", () -> this.boss, v -> this.boss = v, "None").setDisplayName(new TextComponentTranslation("pixelmonquests.boss"));
        config.addString("gender", () -> this.gender, v -> this.gender = v, "Any").setDisplayName(new TextComponentTranslation("pixelmonquests.gender"));
        config.addInt("generation", () -> this.generation, v -> this.generation = v, 0, 0, Integer.MAX_VALUE).setDisplayName(new TextComponentTranslation("pixelmonquests.generation"));
        config.addString("form", () -> this.form, v -> this.form = v, "All").setDisplayName(new TextComponentTranslation("pixelmonquests.form"));
        config.addString("customTexture", () -> this.customTexture, v -> this.customTexture = v, "Any").setDisplayName(new TextComponentTranslation("pixelmonquests.customTexture"));
        config.addBool("shiny", () -> this.shiny, v -> this.shiny = v, false).setDisplayName(new TextComponentTranslation("pixelmonquests.shiny"));
        config.addBool("beast", () -> this.beast, v -> this.beast = v, false).setDisplayName(new TextComponentTranslation("pixelmonquests.beast"));
        config.addBool("legendary", () -> this.legendary, v -> this.legendary = v, false).setDisplayName(new TextComponentTranslation("pixelmonquests.legendary"));
        config.addLong("amount", () -> this.amount, v -> this.amount = v, 10L, 1L, Long.MAX_VALUE).setDisplayName(new TextComponentTranslation("pixelmonquests.amount"));
    }

    @Override
    public TaskData createData(QuestData questData) {
        return new PokemonDefeatTask.Data(this, questData);
    }

    public static class Data extends TaskData<PokemonDefeatTask> {
        private Data(PokemonDefeatTask task, QuestData data) {
            super(task, data);
        }

        public void progress(EntityPixelmon entityPixelmon) {
            if (this.isComplete()) {
                return;
            }

            Pokemon pokemon = entityPixelmon.getPokemonData();
            EnumSpecies pokemonSpec = entityPixelmon.getSpecies();
            BaseStats pokemonStats = entityPixelmon.getBaseStats();

            if (!task.pokemon.equalsIgnoreCase(pokemonSpec.getPokemonName()) && !task.pokemon.equalsIgnoreCase("Any")) {
                return;
            }

            if (task.level > pokemon.getLevel()) {
                return;
            }

            if (!task.type1.equalsIgnoreCase(pokemonStats.getType1().getName()) && !task.type1.equalsIgnoreCase("Any")) {
                return;
            }

            if (pokemonStats.getType2() != null) {
                if (!task.type2.equalsIgnoreCase(pokemonStats.getType2().getName()) && !task.type2.equalsIgnoreCase("Any")) {
                    return;
                }
            }

            if (!task.boss.equalsIgnoreCase(entityPixelmon.getBossMode().name()) && !task.boss.equalsIgnoreCase("None")) {
                return;
            }

            if (!task.gender.equalsIgnoreCase(pokemon.getGender().getName()) && !task.gender.equalsIgnoreCase("Any")) {
                return;
            }

            if (task.generation == pokemonSpec.getGeneration() && pokemonSpec.getGeneration() != 0) {
                return;
            }

            if (!task.form.equalsIgnoreCase(pokemon.getFormEnum().getName()) && !task.form.equalsIgnoreCase("Any")) {
                return;
            }

            if (!task.customTexture.equalsIgnoreCase(pokemon.getCustomTexture()) && !task.customTexture.equalsIgnoreCase("Any")) {
                return;
            }

            if (task.shiny != pokemon.isShiny()) {
                return;
            }

            if (task.beast != pokemonSpec.isUltraBeast()) {
                return;
            }

            if (task.legendary != pokemonSpec.isLegendary()) {
                return;
            }

            this.addProgress(1L);
        }
    }
}
