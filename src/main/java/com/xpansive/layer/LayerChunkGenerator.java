package com.xpansive.layer;

import java.util.Random;
import org.bukkit.generator.ChunkGenerator;

class LayerChunkGenerator extends ChunkGenerator {

	private final WorldGenerator generator;
	private World world;

	LayerChunkGenerator(WorldGenerator generator) {
		this.generator = generator;
	}

	@Override
	public byte[][] generateBlockSections(org.bukkit.World bukkitWorld, Random random, int x, int z, BiomeGrid biomes) {
		if (generator.usesExtendedBlockIds()) {
			return null; // Shouldn't happen.
		}

		if (!world.isChunkGeneratedInMemory(x, z)) {
			world.generateChunkBase(x, z);
		}
		world.setBoundsCheck(true);
		generator.getBiomeGenerator(x << 4, z << 4).generateStructures(world, x << 4, z << 4);
		world.setBoundsCheck(false);

		/*for (int cx = 0; cx < Chunk.WIDTH; cx++) {
			for (int cz = 0; cz < Chunk.DEPTH; cz++) {
				int wx = (x << 4) + cx;
				int wz = (z << 4) + cz;
				generator.getBiomeGenerator(wx, wz).generateBaseTerrain(world.getBlockColumn(wx, wz));
			}
		}
		generator.getBiomeGenerator(x << 4, z << 4).generateStructures(world, x << 4, z << 4);
*/
		byte[][] data = new byte[Chunk.HEIGHT >> 4][];
		World.ChunkSectionPool chunkSections = world.getChunkSectionPool();
		for (int y = 0; y < data.length; y++) {
			ChunkSection chunkSection = chunkSections.getGeneratedOnly(x, y, z);
			if (chunkSection != null) {
				data[y] = chunkSection.getRawBlocks();
				chunkSections.free(chunkSection);
			}
		}

		return data;
	}

	@Override
	public short[][] generateExtBlockSections(org.bukkit.World bukkitWorld, Random random, int x, int z, BiomeGrid biomes) {
		if (world == null) {
			world = new World(generator, bukkitWorld, false);
		}

		if (!generator.usesExtendedBlockIds()) {
			return null; // Use generateBlockSections instead.
		}
		//TODO: implement

		return null;
	}
}
