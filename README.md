# Yet Another Cobble Gen

<img src="https://i.imgur.com/c1DH9VL.png" alt="" width="230" />
<img src="https://i.imgur.com/iaETp3c.png" alt="" width="200" />
<img src="https://i.imgur.com/Ol1Tcf8.png" alt="" width="200" />

## Description
Flexible customizable item generators

![2023-05-01_20 28 14](https://user-images.githubusercontent.com/33298273/235516748-db3374f1-adaa-46ce-b9f0-ebf87b5bf690.png)

## Default Config

```json5
{
    "generators": {
        // Generator type
        "cobble": {
            // Items that it generate
            // "item_id": probability
            "minecraft:cobblestone": 100,
            "minecraft:cobbled_deepslate": 30,
            "minecraft:mossy_cobblestone": 10
        },
        "ore": {
            "minecraft:coal_ore": 100,
            "minecraft:copper_ore": 70,
            "minecraft:iron_ore": 50,
            "minecraft:gold_ore": 30,
            "minecraft:redstone_ore": 20,
            "minecraft:lapis_ore": 20,
            "minecraft:diamond_ore": 15,
            "minecraft:emerald_ore": 10,
            "minecraft:nether_quartz_ore": 5
        },
        "stone": {
            "minecraft:stone": 100,
            "minecraft:diorite": 50,
            "minecraft:granite": 50,
            "minecraft:andesite": 50,
            "minecraft:calcite": 20,
            "minecraft:dripstone_block": 20,
            "minecraft:deepslate": 5
        }
        // U can also add own generator, but it required some additional files like textures, models, loot_tables and etc as datapack
    }
}
```
