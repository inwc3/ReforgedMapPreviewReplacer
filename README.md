Moves the `war3mapMap.blp` of a Warcraft III map to `war3mapMap_ingame.blp` and `war3mapPreview.blp` to `war3mapMap.blp` in order to allow WE-friendly custom map preview images in Reforged while still preserving the WE-generated minimap for ingame (saves it on another path).

# Build uberjar
`
./gradlew jar
`

# Use (after building)

* Import a preview image in your map to the path `war3mapPreview.blp`.

* Create a script running at map initialization and insert

`
call BlzChangeMinimapTerrainTex("war3mapMap_ingame.blp")
`

(`war3mapMap_ingame.blp` is where this tool will put the original `war3mapMap.blp` file.)

* Close the map in editor (make sure file is not locked) and invoke this tool like

`
java -jar .\build\libs\ReforgedMapPreviewReplacer-uberjar-0.1.jar minimap.w3m minimap_out.w3m
`

(Replace `minimap.w3m` by the path to your map file and `minimap_out.w3m` is the output path.)