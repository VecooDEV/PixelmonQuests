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
import com.pixelmonmod.pixelmon.api.storage.PCStorage;
import com.pixelmonmod.pixelmon.api.storage.PokemonStorage;
import com.pixelmonmod.pixelmon.api.storage.StoragePosition;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.BaseStats;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.enums.EnumType;
import com.pixelmonmod.pixelmon.items.ItemPixelmonSprite;
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
import java.util.ArrayList;
import java.util.List;

public class PokemonSubmitTask extends Task {
    public String pokemon = "Any";
    public int level = 0;
    public String types = "Any";
    public String nature = "Any";
    public boolean hiddenAbility = false;
    public String gender = "Any";
    public String growth = "Any";
    public int ivs = 0;
    public int generation = 0;
    public int weight = 0;
    public String form = "Any";
    public String customTexture = "Any";
    public boolean shiny = false;
    public boolean beast = false;
    public boolean legendary = false;

    public boolean consume = false;
    public long amount = 3L;

    public PokemonSubmitTask(Quest quest) {
        super(quest);
    }

    @Override
    public TaskType getType() {
        return PixelmonIntegration.POKEMON_SUBMIT;
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
        nbt.setString("types", this.types);
        nbt.setString("nature", this.nature);
        nbt.setBoolean("hiddenAbility", this.hiddenAbility);
        nbt.setString("gender", this.gender);
        nbt.setString("growth", this.growth);
        nbt.setInteger("ivs", this.ivs);
        nbt.setInteger("generation", this.generation);
        nbt.setInteger("weight", this.weight);
        nbt.setString("form", this.form);
        nbt.setString("customTexture", this.customTexture);
        nbt.setBoolean("shiny", this.shiny);
        nbt.setBoolean("beast", this.beast);
        nbt.setBoolean("legendary", this.legendary);
        nbt.setBoolean("consume", this.consume);
        nbt.setLong("amount", this.amount);
    }

    @Override
    public void readData(NBTTagCompound nbt) {
        super.readData(nbt);
        this.pokemon = nbt.getString("pokemon");
        this.level = nbt.getInteger("level");
        this.types = nbt.getString("types");
        this.nature = nbt.getString("nature");
        this.hiddenAbility = nbt.getBoolean("hiddenAbility");
        this.gender = nbt.getString("gender");
        this.growth = nbt.getString("growth");
        this.ivs = nbt.getInteger("ivs");
        this.generation = nbt.getInteger("generation");
        this.weight = nbt.getInteger("weight");
        this.form = nbt.getString("form");
        this.customTexture = nbt.getString("customTexture");
        this.shiny = nbt.getBoolean("shiny");
        this.beast = nbt.getBoolean("beast");
        this.legendary = nbt.getBoolean("legendary");
        this.consume = nbt.getBoolean("consume");
        this.amount = nbt.getLong("amount");
    }

    @Override
    public void writeNetData(DataOut data) {
        super.writeNetData(data);
        data.writeString(this.pokemon);
        data.writeInt(this.level);
        data.writeString(this.types);
        data.writeString(this.nature);
        data.writeBoolean(this.hiddenAbility);
        data.writeString(this.gender);
        data.writeString(this.growth);
        data.writeInt(this.ivs);
        data.writeInt(this.generation);
        data.writeInt(this.weight);
        data.writeString(this.form);
        data.writeString(this.customTexture);
        data.writeBoolean(this.shiny);
        data.writeBoolean(this.beast);
        data.writeBoolean(this.legendary);
        data.writeBoolean(this.consume);
        data.writeVarLong(this.amount);
    }

    @Override
    public void readNetData(DataIn data) {
        super.readNetData(data);
        this.pokemon = data.readString();
        this.level = data.readInt();
        this.types = data.readString();
        this.nature = data.readString();
        this.hiddenAbility = data.readBoolean();
        this.gender = data.readString();
        this.growth = data.readString();
        this.ivs = data.readInt();
        this.generation = data.readInt();
        this.weight = data.readInt();
        this.form = data.readString();
        this.customTexture = data.readString();
        this.shiny = data.readBoolean();
        this.beast = data.readBoolean();
        this.legendary = data.readBoolean();
        this.consume = data.readBoolean();
        this.amount = data.readVarLong();
    }

    @Override
    public int autoSubmitOnPlayerTick() {
        return this.consume ? 0 : 20;
    }

    @Override
    public String getAltTitle() {
        if (this.consume) {
            return I18n.format("pixelmonquests.pokemon_submit_consume.title", this.amount, this.pokemon);
        } else {
            return I18n.format("pixelmonquests.pokemon_submit.title", this.amount, this.pokemon);
        }
    }

    @Override
    public Icon getAltIcon() {
        if (EnumSpecies.getFromNameAnyCaseNoTranslate(this.pokemon) != null) {
            Pokemon pokemon = Pixelmon.pokemonFactory.create(PokemonSpec.from(this.pokemon));

            if (this.shiny) {
                pokemon.setShiny(true);
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
        config.addString("types", () -> this.types, v -> this.types = v, "Any").setDisplayName(new TextComponentTranslation("pixelmonquests.types"));
        config.addString("nature", () -> this.nature, v -> this.nature = v, "Any").setDisplayName(new TextComponentTranslation("pixelmonquests.nature"));
        config.addBool("hiddenAbility", () -> this.hiddenAbility, v -> this.hiddenAbility = v, false).setDisplayName(new TextComponentTranslation("pixelmonquests.hiddenAbility"));
        config.addString("gender", () -> this.gender, v -> this.gender = v, "Any").setDisplayName(new TextComponentTranslation("pixelmonquests.gender"));
        config.addString("growth", () -> this.growth, v -> this.growth = v, "Any").setDisplayName(new TextComponentTranslation("pixelmonquests.growth"));
        config.addInt("ivs", () -> this.ivs, v -> this.ivs = v, 0, 0, Integer.MAX_VALUE).setDisplayName(new TextComponentTranslation("pixelmonquests.ivs"));
        config.addInt("generation", () -> this.generation, v -> this.generation = v, 0, 0, Integer.MAX_VALUE).setDisplayName(new TextComponentTranslation("pixelmonquests.generation"));
        config.addInt("weight", () -> this.weight, v -> this.weight = v, 0, 0, Integer.MAX_VALUE).setDisplayName(new TextComponentTranslation("pixelmonquests.weight"));
        config.addString("form", () -> this.form, v -> this.form = v, "Any").setDisplayName(new TextComponentTranslation("pixelmonquests.form"));
        config.addString("customTexture", () -> this.customTexture, v -> this.customTexture = v, "Any").setDisplayName(new TextComponentTranslation("pixelmonquests.customTexture"));
        config.addBool("shiny", () -> this.shiny, v -> this.shiny = v, false).setDisplayName(new TextComponentTranslation("pixelmonquests.shiny"));
        config.addBool("beast", () -> this.beast, v -> this.beast = v, false).setDisplayName(new TextComponentTranslation("pixelmonquests.beast"));
        config.addBool("legendary", () -> this.legendary, v -> this.legendary = v, false).setDisplayName(new TextComponentTranslation("pixelmonquests.legendary"));
        config.addBool("consume", () -> this.consume, v -> this.consume = v, false).setDisplayName(new TextComponentTranslation("pixelmonquests.consume"));
        config.addLong("amount", () -> this.amount, v -> this.amount = v, 10L, 1L, Long.MAX_VALUE).setDisplayName(new TextComponentTranslation("pixelmonquests.amount"));
    }

    @Override
    public TaskData createData(QuestData questData) {
        return new Data(this, questData);
    }

    public static class Data extends TaskData<PokemonSubmitTask> {
        private Data(PokemonSubmitTask task, QuestData data) {
            super(task, data);
        }

        @Override
        public void submitTask(@Nonnull EntityPlayerMP player, @Nonnull ItemStack craftedItem) {
            if (isComplete()) {
                return;
            }

            PlayerPartyStorage party = Pixelmon.storageManager.getParty(player);

            if (party == null) {
                return;
            }

            PCStorage pc = Pixelmon.storageManager.getPCForPlayer(player.getUniqueID());
            List<StorageEntry> matches = new ArrayList<>();

            collectPartyMatches(party, matches, task.getMaxProgress());

            if (matches.size() < task.getMaxProgress() && pc != null) {
                collectMatches(pc, matches, task.getMaxProgress());
            }

            if (matches.isEmpty()) {
                return;
            }

            if (task.consume) {
                submitWithConsume(player, matches);
            } else {
                submitWithoutConsume(matches);
            }
        }

        private void submitWithoutConsume(List<StorageEntry> matches) {
            long targetProgress = Math.min(matches.size(), task.getMaxProgress());

            long difference = targetProgress - this.progress;

            if (difference > 0L) {
                this.addProgress(difference);
            }
        }

        private void collectPartyMatches(PlayerPartyStorage party, List<StorageEntry> matches, long limit) {
            for (int slot = 0; slot < 6; slot++) {
                Pokemon pokemon = party.get(slot);

                if (pokemon == null || pokemon.isEgg()) {
                    continue;
                }

                if (!matches(pokemon)) {
                    continue;
                }

                StoragePosition position = party.getPosition(pokemon);

                if (position == null) {
                    continue;
                }

                matches.add(new StorageEntry(party, position, pokemon));

                if (matches.size() >= limit) {
                    return;
                }
            }
        }

        private void submitWithConsume(EntityPlayerMP player, List<StorageEntry> matches) {
            long remaining = task.getMaxProgress() - this.progress;

            if (remaining <= 0L) {
                return;
            }

            long consumed = 0L;

            for (StorageEntry entry : matches) {
                if (consumed >= remaining) {
                    break;
                }

                Pokemon pokemon = entry.storage.get(entry.position);

                if (pokemon == null || !pokemon.getUUID().equals(entry.pokemon.getUUID())) {
                    continue;
                }

                pokemon.retrieve();

                returnHeldItem(player, pokemon);

                entry.storage.set(entry.position, null);

                consumed++;
            }

            if (consumed > 0L) {
                this.addProgress(consumed);
            }
        }

        private void collectMatches(PokemonStorage storage, List<StorageEntry> matches, long limit) {
            if (matches.size() >= limit) {
                return;
            }

            for (Pokemon pokemon : storage.getAll()) {
                if (pokemon == null || pokemon.isEgg()) {
                    continue;
                }

                if (!matches(pokemon)) {
                    continue;
                }

                StoragePosition position = storage.getPosition(pokemon);

                if (position == null) {
                    continue;
                }

                matches.add(new StorageEntry(storage, position, pokemon));

                if (matches.size() >= limit) {
                    return;
                }
            }
        }

        private boolean matches(Pokemon pokemon) {
            EnumSpecies species = pokemon.getSpecies();
            BaseStats stats = pokemon.getBaseStats();

            if (!task.pokemon.equalsIgnoreCase("Any") && !task.pokemon.equalsIgnoreCase(species.getPokemonName())) {
                return false;
            }

            if (task.level > pokemon.getLevel()) {
                return false;
            }

            if (!task.types.equalsIgnoreCase("Any")) {
                EnumType type = EnumType.parseType(task.types);

                if (type == null || !stats.getTypeList().contains(type)) {
                    return false;
                }
            }

            if (!task.nature.equalsIgnoreCase("Any") && !task.nature.equalsIgnoreCase(pokemon.getNature().getName())) {
                return false;
            }

            if (task.hiddenAbility != (pokemon.getAbilitySlot() == 2)) {
                return false;
            }

            if (!task.gender.equalsIgnoreCase("Any") && !task.gender.equalsIgnoreCase(pokemon.getGender().getName())) {
                return false;
            }

            if (!task.growth.equalsIgnoreCase("Any") && !task.growth.equalsIgnoreCase(pokemon.getGrowth().getName())) {
                return false;
            }

            if (task.ivs > pokemon.getIVs().getTotal()) {
                return false;
            }

            if (task.generation != 0 && task.generation != species.getGeneration()) {
                return false;
            }

            if (task.weight > stats.getWeight()) {
                return false;
            }

            if (!task.form.equalsIgnoreCase("Any") && !task.form.equalsIgnoreCase(pokemon.getFormEnum().getName())) {
                return false;
            }

            if (!task.customTexture.equalsIgnoreCase("Any") && !task.customTexture.equalsIgnoreCase(pokemon.getCustomTexture())) {
                return false;
            }

            if (task.shiny && !pokemon.isShiny()) {
                return false;
            }

            if (task.beast != species.isUltraBeast()) {
                return false;
            }

            return task.legendary == species.isLegendary();
        }

        private void returnHeldItem(EntityPlayerMP player, Pokemon pokemon) {
            ItemStack heldItem = pokemon.getHeldItem();

            if (heldItem.isEmpty()) {
                return;
            }

            ItemStack itemStack = heldItem.copy();

            if (!player.inventory.addItemStackToInventory(itemStack) && !itemStack.isEmpty()) {
                player.dropItem(itemStack, false);
            }
        }

        private static class StorageEntry {
            private final PokemonStorage storage;
            private final StoragePosition position;
            private final Pokemon pokemon;

            private StorageEntry(PokemonStorage storage, StoragePosition position, Pokemon pokemon) {
                this.storage = storage;
                this.position = position;
                this.pokemon = pokemon;
            }
        }
    }
}