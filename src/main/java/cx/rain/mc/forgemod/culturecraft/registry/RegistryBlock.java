package cx.rain.mc.forgemod.culturecraft.registry;

import cx.rain.mc.forgemod.culturecraft.CultureCraft;
import cx.rain.mc.forgemod.culturecraft.api.annotation.ModBlock;
import cx.rain.mc.forgemod.culturecraft.api.annotation.ModItem;
import cx.rain.mc.forgemod.culturecraft.group.Groups;
import cx.rain.mc.forgemod.culturecraft.utility.AnnotationsHelper;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = CultureCraft.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryBlock {
    public static Map<String, Block> BLOCKS = new LinkedHashMap<>();

    static {
        String blocksPackage = "cx.rain.mc.forgemod.culturecraft.block.automatic";
        for (Class<?> clazz : AnnotationsHelper.getClassAnnotated(blocksPackage, ModBlock.class)) {
            try {
                ModBlock modBlock = clazz.getAnnotation(ModBlock.class);
                String registryName = modBlock.name();
                if (!registryName.isEmpty()) {
                    Block block = ((Block) clazz.getConstructor().newInstance())
                            .setRegistryName(CultureCraft.MODID, registryName);
                    BLOCKS.put(registryName, block);
                }
            } catch (NoSuchMethodException
                    | IllegalAccessException
                    | InstantiationException
                    | InvocationTargetException ex) {
                ex.printStackTrace();
            }
        }
    }

    @SubscribeEvent
    public static void onRegisterBlock(RegistryEvent.Register<Block> event) {
        CultureCraft.getInstance().getLog().info("Registering more blocks.");
        BLOCKS.forEach((name, block) -> {
            event.getRegistry().register(block);
        });
    }

    @SubscribeEvent
    public static void onRegisterBlockItem(RegistryEvent.Register<Item> event) {
        CultureCraft.getInstance().getLog().info("Registering block items.");
        BLOCKS.forEach((name, block) -> {
            event.getRegistry().register(new BlockItem(block, new Item.Properties().group(Groups.BLOCKS))
                    .setRegistryName(name));
        });
    }
}
