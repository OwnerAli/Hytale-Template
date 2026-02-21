# Mod Template

A simple template for creating [Hytale](https://hytale.com/) mods using Gradle.

## Getting Started

Follow these steps to use this template:

### 1. Getting the HytaleServer.jar

**1.1 Windows**
- Press the Windows + R keys at the same time
- Type %appdata% and press enter
- Open the Hytale > install > release > package > game > latest > Server
- Right click HytaleServer.jar and select "copy as path"
- Open `build.gradle.kts` in your IntelliJ project and update the following:
  - Replace the text 'your path here' with your path
    `(e.g. implementation(files("C:\Users\altur\AppData\Roaming\Hytale\install\release\package\game\latest\Server\HytaleServer.jar")))`

**1.2 Mac (Untested)**
- Open Finder
- Press Cmd + Shift + G to open "Go to Folder"
- Type ~/Library/Application Support and press enter
- Open the Hytale > install > release > package > game > latest > Server folder
- Right click HytaleServer.jar, hold Option, and select "Copy as Pathname"
- Open `build.gradle.kts` in your IntelliJ project and update the following:
  - Replace the text 'your path here' with your path
    `(e.g. implementation(files("/Users/altur/Library/Application Support/Hytale/install/release/package/game/latest/Server/HytaleServer.jar")))`

### 2. Update Package Name

Change the package name from `me.alii` to your own package name throughout the project files.

### 3. Update Manifest

Edit `resources/manifest.json` with your mod information:

- **Group**: Your group/organization ID
- **Name**: Your mod name
- **Version**: Your mod version
- **Description**: A brief description of your mod
- **Authors**: Your name and any contributors
- **Website**: Your website or repository URL (optional)
- **Main**: Your main class path (e.g., `packagename.MainClassName`)
- **ServerVersion**: Target server version (Keep as `*` for all versions) (no need to modify)
- **IncludesAssetPack**: Set to `true` if your mod includes assets, `false` otherwise

### 4. Build Your Mod

Run the following command to build your mod:

```bash
./gradlew clean build
```

Your mod JAR will be generated in build/libs

## That's It!

You're ready to start developing your mod. Happy coding!
