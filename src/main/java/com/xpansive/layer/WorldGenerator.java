package com.xpansive.layer;

import org.bukkit.World.Environment;
import org.bukkit.generator.ChunkGenerator;

/**
 * Represents a world generator.
 */
public abstract class WorldGenerator {
	/**
	 * Gets the Environment of the generated world.
	 *
	 * @return The Environment of this world.
	 */
	public Environment getEnvironment() {
		return Environment.NORMAL;
	}

	/**
	 * Gets the ID of this WedgeGenerator. If you only have one generator, do not override this at all or return null.
	 *
	 * @return return The ID of this WedgeGenerator.
	 */
	public String getId() {
		return null;
	}

	public abstract BiomeGenerator getBiomeGenerator(int x, int z);

	/**
	 * If the generated world will contain extended block IDs, override this to return true.
	 *
	 * @return A boolean indicating whether this generator will use extended block IDs.
	 */
	public boolean usesExtendedBlockIds() {
		return false;
	}

	ChunkGenerator getChunkGenerator() {
		return new LayerChunkGenerator(this);
	}
}
