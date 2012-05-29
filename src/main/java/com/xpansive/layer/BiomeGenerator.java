package com.xpansive.layer;

import java.util.HashMap;

public abstract class BiomeGenerator {
	private final WorldGenerator generator;
	private final Pass[] passes;
	private final StructureGenerator[] structures;

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
			pass.run(column);
		}
	}

	void generateStructures(World world, int x, int z) {
		for (StructureGenerator structure : structures) {
			structure.generate(world, x, z);
		}
	}
}
