package com.xpansive.layer;

import com.xpansive.layer.util.LayerRandom;
import java.util.Random;

public abstract class BiomeGenerator {

	private final WorldGenerator generator;
	private final Pass[] passes;
	private final StructureGenerator[] structures;
	private long seed;

	public BiomeGenerator(WorldGenerator generator) {
		this.generator = generator;
		passes = getPasses();
		structures = getSturctures();
	}

	protected abstract Pass[] getPasses();

	protected abstract StructureGenerator[] getSturctures();

	protected WorldGenerator getGenerator() {
		return generator;
	}

	void generateBaseTerrain(BlockColumn column) {
		for (Pass pass : passes) {
			pass.run(column, column.getWorld().getRandom());
		}
	}

	void generateStructures(World world, int x, int z) {
		seed = world.getSeed();

		for (StructureGenerator structure : structures) {
			structure.generate(world, x, z, new LayerRandom(new Random(seed * (x * 12345) * (z * 67890))));
		}
	}
}
