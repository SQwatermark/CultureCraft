package cx.rain.mc.forgemod.culturecraft.group;

import cx.rain.mc.forgemod.culturecraft.CultureCraft;
import cx.rain.mc.forgemod.culturecraft.registry.RegistryItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class GroupFoods extends ItemGroup {
    private static final String label = "foods";

    public GroupFoods() {
        super(CultureCraft.MODID + "." + label);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(RegistryItem.ITEMS.get("tofu"));
    }
}
