package net.croc.doomedextras;

import net.croc.doomedextras.items.*;
import net.mattlives.doomedmatu.body.DrugType;
import net.mattlives.doomedmatu.compat.jei.AcquisitionCatalog;
import net.mattlives.doomedmatu.core.DoomedMod;
import net.mattlives.doomedmatu.entity.TraderStock;
import net.mattlives.doomedmatu.item.ItemInfo;
import net.mattlives.doomedmatu.item.MedDrinkItem;
import net.mattlives.doomedmatu.item.SyringeItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import net.mattlives.doomedmatu.registry.DoomedItems;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RegistryItems {
    public static DeferredRegister<Item> ITEMS = DoomedExtrasMod.ITEMS;

    public static void registerItemInfo(String path, ItemInfo.Def def) {
        ItemInfo.register(new ResourceLocation(DoomedExtrasMod.MOD_ID, path), def);
    }

    private static void med(String path, int value) {
        registerItemInfo(path, new ItemInfo.Def(0.1F, value, 0.0F, false, false, Set.of("medical"), 0, Map.of(), 0.0F));
    }

    private static RegistryObject<Item> chem(String id) {
        return ITEMS.register(id, () -> new Item(new Item.Properties()));
    }

    static void registerItemInfo() {
        med("urgencykit72h", 40);
        med("ai2", 40);
        med("itk", 60);
        med("ifak", 70);
        med("coffee_tablet", 4);
        med("ibuprofen", 6);
        med("potassium_iodide", 8);
        med("lidocaine_tablet", 8);
        med("handsanitizer", 4);
        med("caffeine_syringe", 5);
        med("lidocaine_syringe", 10);
        med("kevlar", 10);
        med("geiger_counter", 20);
        med("solarcell", 3);
        med("solarpanel", 20);
        med("dopantmix", 8);
    }

    public static final RegistryObject<Item> COFFEE_TABLET = ITEMS.register("coffee_tablet", () -> new PillsTablet((new Item.Properties()).stacksTo(1).durability(4), "", (sp, d) -> {
        d.setEnergy(d.getEnergy() - 5.0F);
        d.setCaffeinated(d.getCaffeinated() + 600);
        d.setBpMedicineOffset(d.getBpMedicineOffset() + 50 * 0.4F);
    }));

    public static final RegistryObject<Item> IBUPROFEN = ITEMS.register("ibuprofen", () -> new PillsBottle((new Item.Properties()).stacksTo(1).durability(3), "", (sp, d) -> {
        d.setNsaidLevel(d.getNsaidLevel() + 32.0F);
    }));

    public static final RegistryObject<Item> POTASSIUM_IODIDE = ITEMS.register("potassium_iodide", () -> new PillsBottle((new Item.Properties()).stacksTo(1).durability(3), "Antirad - the radiation sickness loosens its hold.", (sp, d) -> {
        d.setRadiationSickness(Math.max(0.0F, d.getRadiationSickness() - 8.0F));
        d.addSicknessAmount(2.5F);
    }));

    public static final RegistryObject<Item> LIDOCAINE_TABLET = ITEMS.register("lidocaine_tablet", () -> new PillsTablet((new Item.Properties()).stacksTo(1).durability(4), "", (sp, d) -> {
        d.setFibrillationForced(false);
        d.setFibrillation(Math.max(0, d.getFibrillation() - 12.0F));
    }));

    public static final RegistryObject<Item> HAND_SANITIZER = ITEMS.register("handsanitizer", () -> new HandSanitizer((new Item.Properties().stacksTo(1)).durability(6)));

    public static final RegistryObject<Item> DOPANT_MIX = ITEMS.register("dopantmix", () -> new MedDrinkItem((new Item.Properties().stacksTo(4)), "You feel a rush of energy.", (sp, d) -> {
        float ml = 30;
        d.setEnergy(d.getEnergy() - ml * 0.12F);
        d.setConsciousness(Math.max(100, d.getConsciousness() + ml * 0.24F));
        d.setCaffeinated(d.getCaffeinated() + 300);
        d.setBpMedicineOffset(d.getBpMedicineOffset() + ml * 0.4F);
        d.setAdrenaline(d.getAdrenaline() + 0.7F * ml);
        d.addOpiateDepot(ml * 0.4F, 0.1F);
        d.setNsaidLevel(d.getNsaidLevel() + ml * 0.32F);
    }));

    public static final RegistryObject<Item> CAFFEINE_SYRINGE = ITEMS.register("caffeine_syringe", () -> new SyringeItem(DrugType.valueOf("CAFFEINE"), (new Item.Properties()).durability(100)));
    public static final RegistryObject<Item> LIDOCAINE_SYRINGE = ITEMS.register("lidocaine_syringe", () -> new SyringeItem(DrugType.valueOf("LIDOCAINE"), (new Item.Properties()).durability(100)));

    public static final RegistryObject<Item> LIDOCAINE_PHARMA = chem("lidocaine_pharma");
    public static final RegistryObject<Item> AMMONIA_CRYSTAL = chem("ammonia_crystal");
    public static final RegistryObject<Item> GROUND_COFFEE = chem("ground_coffee");
    public static final RegistryObject<Item> KEVLAR = chem("kevlar");

    public static final RegistryObject<Item> GEIGER_COUNTER = ITEMS.register("geiger_counter", () -> new GeigerCounter(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> SOLAR_CELL = ITEMS.register("solarcell", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> SOLAR_PANEL = ITEMS.register("solarpanel", () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> KIT_URGENCY_72H = ITEMS.register("urgencykit72h", () -> new ZippedMedkit((new Item.Properties()).stacksTo(1), "French 72h Urgency kit - has basic necessities to survive for the next 72 hours.", (player, data) -> {
        Inventory inv = player.getInventory();
        inv.add(new ItemStack(DoomedItems.BANDAGE.get(), 2));
        inv.add(new ItemStack(DoomedItems.ADHESIVE_BANDAGE.get(), 2));
        inv.add(new ItemStack(DoomedItems.ANTISEPTIC.get(), 1));

        ItemStack canteen = new ItemStack(DoomedItems.CANTEEN.get(), 1);
        CompoundTag tag = canteen.getOrCreateTag();
        tag.putInt("Water", 6);
        tag.putBoolean("Clean", true);
        canteen.setTag(tag);
        inv.add(canteen);

        Item small_battery = ForgeRegistries.ITEMS.getValue(new ResourceLocation(DoomedMod.MOD_ID, "smallbattery"));
        Item water_bottle = ForgeRegistries.ITEMS.getValue(new ResourceLocation(DoomedMod.MOD_ID, "waterbottle"));
        Item nutrient_bar = ForgeRegistries.ITEMS.getValue(new ResourceLocation(DoomedMod.MOD_ID, "nutrientbar"));
        Item candy_bar = ForgeRegistries.ITEMS.getValue(new ResourceLocation(DoomedMod.MOD_ID, "candybar"));
        if (small_battery != null) inv.add(new ItemStack(small_battery, 1));
        if (candy_bar != null) inv.add(new ItemStack(candy_bar, 3));
        if (water_bottle != null) inv.add(new ItemStack(water_bottle, 2));
        if (nutrient_bar != null) inv.add(new ItemStack(nutrient_bar, 2));
    }));

    public static final RegistryObject<Item> KIT_AI2 = ITEMS.register("ai2", () -> new ZippedMedkit((new Item.Properties()).stacksTo(1), "French 72h Urgency kit - has basic necessities to survive for the next 72 hours.", (player, data) -> {
        Inventory inv = player.getInventory();
        inv.add(new ItemStack(POTASSIUM_IODIDE.get(), 2));
        inv.add(new ItemStack(DoomedItems.ACTIVATED_CHARCOAL.get(), 2));
        inv.add(new ItemStack(DoomedItems.CEFTRIAXONE_SYRINGE.get(), 1));
        inv.add(new ItemStack(DoomedItems.RELIEFCREAM_SYRINGE.get(), 1));
    }));

    public static final RegistryObject<Item> KIT_IFAK = ITEMS.register("ifak", () -> new ZippedMedkit((new Item.Properties()).stacksTo(1), "French 72h Urgency kit - has basic necessities to survive for the next 72 hours.", (player, data) -> {
        Inventory inv = player.getInventory();
        inv.add(new ItemStack(DoomedItems.TWEEZERS.get(), 1));
        inv.add(new ItemStack(DoomedItems.MEDICAL_SUTURE.get(), 3));
        inv.add(new ItemStack(DoomedItems.ANALGESIC_GAUZE.get(), 2));
        inv.add(new ItemStack(DoomedItems.PLASTIC_BANDAGE.get(), 2));
        inv.add(new ItemStack(DoomedItems.CHEST_DRAIN.get(), 1));
        inv.add(new ItemStack(DoomedItems.ANTIBIOTICS.get(), 1));
        inv.add(new ItemStack(DoomedItems.PAINKILLERS.get(), 1));
    }));

    public static final RegistryObject<Item> KIT_ITK = ITEMS.register("itk", () -> new ZippedMedkit((new Item.Properties()).stacksTo(1), "French 72h Urgency kit - has basic necessities to survive for the next 72 hours.", (player, data) -> {
        Inventory inv = player.getInventory();
        inv.add(new ItemStack(DoomedItems.TWEEZERS.get(), 1));
        inv.add(new ItemStack(DoomedItems.TOURNIQUET.get(), 1));
        inv.add(new ItemStack(DoomedItems.TOURNIQUET.get(), 1));
        inv.add(new ItemStack(DoomedItems.PLASTIC_BANDAGE.get(), 2));
        inv.add(new ItemStack(DoomedItems.COMBAT_PEN.get(), 1));
        inv.add(new ItemStack(DoomedItems.RINGERSOLUTION_SYRINGE.get(), 1));
        inv.add(new ItemStack(DoomedItems.MORPHINE_SYRINGE.get(), 1));
    }));

    private static List<RegistryObject<Item>> TRADABLES;

    public static List<RegistryObject<Item>> loadTradables() {
        TRADABLES = new ArrayList<>();
        registerTradables();
        return TRADABLES;
    }

    private static void tradable(RegistryObject<Item> item) {
        TRADABLES.add(item);
    }

    static void registerTradables() {
        tradable(KIT_AI2);
        tradable(KIT_URGENCY_72H);
        tradable(KIT_IFAK);
        tradable(KIT_ITK);
        tradable(COFFEE_TABLET);
        tradable(CAFFEINE_SYRINGE);
        tradable(LIDOCAINE_TABLET);
        tradable(LIDOCAINE_SYRINGE);
        tradable(IBUPROFEN);
        tradable(POTASSIUM_IODIDE);
        tradable(DOPANT_MIX);
        tradable(KEVLAR);
        tradable(GEIGER_COUNTER);
    }

    public static void register() {
        registerItemInfo();
    }
}

