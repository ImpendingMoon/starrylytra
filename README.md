# Starrylytra
A Client-Side Minecraft mod which replaces the Elytra material with the End Portal material

Built for Minecraft 1.21.7. Probably works in other versions.

## Usage

To use this effect, an Elytra must have the `"starry"` tag in the `custom_data` component.

This can be obtained with the command `/give <player> minecraft:elytra[minecraft:custom_data{starry:1}]`

## Building

- Run `./gradlew build`
- The jar will be in `/build/libs` 

### Requirements

- Java 21 SDK
- Minecraft 1.21.7 (ish)
- Fabric Loader + API
