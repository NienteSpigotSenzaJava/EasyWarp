# 🚀 EasyWarp

Easily create, delete and teleport to a warp.

![Demonstration](./images/demo.gif)

## Building

If you want to build the project by yourself just run

``` bash
git clone https://github.com/NienteSpigotSenzaJava/EasyWarp
cd EasyWarp
mvn clean package
```

you will find ```EasyWarp-1.0.0.jar``` in the ```/target``` directory.

## Usage

Here below you can find a list of commands.

| Command               | Description                               | Permission             |
|-----------------------|-------------------------------------------|------------------------|
| ```/warp <warp>```    | Teleports the player to the selected warp | ```easywarp.warp```    |
| ```/setwarp <warp>``` | Creates a new warp with the selected name | ```easywarp.setwarp``` |
| ```/delwarp <warp>``` | Deletes the selected warp                 | ```easywarp.delwarp``` |
| ```/warps ```         | Gives a list of all the existing warps    | ```easywarp.warps```   |

## Configuration

You can configure the plugin in the ```config.yml``` file, you will also find a ```warps.yml``` file, but you don't have to modify it, it only stores the warps.

Here below you can find a list of options for the plugin.

| Option                      | Default     | Description                                                                                                   |
|-----------------------------|-------------|---------------------------------------------------------------------------------------------------------------|
| ```cooldown```              | ```5```     | Amount of seconds to wait before the teleport after the ```/warp``` command, set it to ```0``` to disable it. |
| ```cancelTpOnMove```        | ```true```  | Cancels the teleport if the player moves after the ```/warp``` command, set it to ```false``` to disable it.  |
| ```requireWarpPermission``` | ```false``` | Requires a permission for every warp (```easywarp.warp.warpname```), set it to ```true``` to enable it.       |

## Compatibility

The plugin should work from version ```1.13.2``` to version ```1.19.4``` (latest atm), for any problem just open an issue.

## Contributing

Pull requests and issues are welcome. I obviously accept any constructive criticism.

## License

[MIT](https://choosealicense.com/licenses/mit/)
