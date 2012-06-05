package com.xpansive.layer;

import com.xpansive.layer.util.LayerRandom;

public interface StructureGenerator {

	/**
	 * Generates a structure based at the specified coordinates in the given world. You may stray out of the given area, as the given values
	 * are only to direct you.
	 * <p/>
	 * @param world The world to generate the structure in.
	 * @param x The suggested x coordinate to generate this structure centered on.
	 * @param z The z coordinate.
	 * @param random A LayerRandom instance you may use for generating random numbers.
	 */
	public abstract void generate(World world, int x, int z, LayerRandom random);
}
