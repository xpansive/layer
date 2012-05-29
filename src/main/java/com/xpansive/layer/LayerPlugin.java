package com.xpansive.layer;

import java.util.logging.Level;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class LayerPlugin extends JavaPlugin {

	private WorldGenerator[] generators;

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		if (generators == null || generators.length == 0) {
			getLogger().warning(String.format("%s: No world generators!", getPluginName()));
		} else {
			for (WorldGenerator wedgeGenerator : generators) {
				if (wedgeGenerator.getId().equals(id)) {
					getLogger().info(String.format("%s: Using generator: %s", getPluginName(), id));
					return wedgeGenerator.getChunkGenerator();
				}
			}
		}
		getLogger().warning(String.format("%s: Invalid world generator id: %s", getPluginName(), id));
		return null;
	}

	@Override
	public void onEnable() {
		generators = getGenerators();
	}

	/**
	 * Gets the WedgeGenerators used in this WedgePlugin. Take note that if you are using a null generator ID only one generator may be
	 * used.
	 * <p/>
	 * @return A list of the wedgeGenerators to be used.
	 */
	public abstract WorldGenerator[] getGenerators();

	/**
	 * Gets the name of this plugin. This will be used to identify your world generator.
	 * <p/>
	 * @return The name of this plugin.
	 */
	public abstract String getPluginName();
}
