# Jupiter

Jupiter is a powerful, auto sync config library.

**IMPORTANT NOTICE: Jupiter V2 is not compatible with mods which based on V1!**

## Features

### For players

- Convenient in-game config edit screens.
- Auto sync with server if you are connect to dedicated servers & you have proper permissions.
- (Neo)Forge config system support, you can also edit them in `Jupiter`.

### For develops

- Simple creation of config instance.
- Config support `int`, `double`, `string`,`list` and so on.
- Customized codec based config entry: You can add config entry for any value type.
- Permission control for dedicate server config.

#### How to use (For developer)

1.Create config class and extend `FileConfigContainer`.

2.Add config in `init`
method. ([Example](https://github.com/IAFEnvoy/Jupiter/blob/main/src/main/java/com/iafenvoy/jupiter/test/TestConfig.java))

3.If your config is for server/common, register it with `ServerConfigManager.registerServerConfig`.

4.Create your config screen. There are 3 types of screen to select.

i.`ConfigSelectScreen`: Create a config select screen. User can select which config to edit. Include permission check.

ii.`ClientConfigScreen`: Create a client config edit screen.

iii.`ServerConfigScreen`: Create a server config edit screen. **Not include permission check.**

## Special thanks

- `masady`: Author of `Malilib`, config screen designs is partly based on it.
- `Fuzs`: Author of `Forge Config API Port`: `Jupiter` can load (Neo)Forge configs more convenient with its universal
  APIs.

## Discord

https://discord.gg/NDzz2upqAk