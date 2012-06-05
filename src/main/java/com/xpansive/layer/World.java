package com.xpansive.layer;

import com.xpansive.layer.util.Pool;
import java.util.ArrayList;
import java.util.List;

public class World {

	public static final int HEIGHT = Chunk.HEIGHT;
	private ChunkSectionPool sectionPool;
	private org.bukkit.World bukkitWorld;
	private WorldGenerator generator;

	World(WorldGenerator generator, org.bukkit.World bukkitWorld) {
		sectionPool = new ChunkSectionPool();
		this.bukkitWorld = bukkitWorld;
		this.generator = generator;
	}

	public long getSeed() {
		return bukkitWorld.getSeed();
	}

	public byte getBlockData(int x, int y, int z) {
		if (isChunkGenerated(x >> 4, z >> 4)) {
			return bukkitWorld.getBlockAt(x, y, z).getData();
		} else if (!isChunkGeneratedInMemory(x >> 4, z >> 4)) {
			generateChunkBase(x >> 4, z >> 4);
		}
		return _getBlockData(x, y, z);
	}

	// The methods starting with an underscore are internal and will only work properly on chunks which are in the correct state.
	byte _getBlockData(int x, int y, int z) {
		if (outOfBounds(y)) {
			return 0;
		}

		ChunkSection chunkSection = sectionPool.get(x >> 4, y >> 4, z >> 4);
		return chunkSection.getBlockData(x & 15, y & 15, z & 15);
	}

	public int getBlockTypeId(int x, int y, int z) {
		if (isChunkGenerated(x >> 4, z >> 4)) {
			return bukkitWorld.getBlockTypeIdAt(x, y, z);
		} else if (!isChunkGeneratedInMemory(x >> 4, z >> 4)) {
			generateChunkBase(x >> 4, z >> 4);
		}
		return _getBlockTypeId(x, y, z);
	}

	int _getBlockTypeId(int x, int y, int z) {
		if (outOfBounds(y)) {
			return 0;
		}

		ChunkSection chunkSection = sectionPool.get(x >> 4, y >> 4, z >> 4);
		return chunkSection.getFullBlockId(x & 15, y & 15, z & 15);
	}

	public void setBlockData(int x, int y, int z, int data) {
		if (isChunkGenerated(x >> 4, z >> 4)) {
			bukkitWorld.getBlockAt(x, y, z).setData((byte) data);
			return;
		} else if (!isChunkGeneratedInMemory(x >> 4, z >> 4)) {
			generateChunkBase(x >> 4, z >> 4);
		}
		_setBlockData(x, y, z, data);
	}

	void _setBlockData(int x, int y, int z, int data) {
		if (outOfBounds(y)) {
			return;
		}

		ChunkSection chunkSection = sectionPool.get(x >> 4, y >> 4, z >> 4);
		chunkSection.setBlockData(x & 15, y & 15, z & 15, (byte) data);
	}

	public void setBlockTypeId(int x, int y, int z, int typeId) {
		if (isChunkGenerated(x >> 4, z >> 4)) {
			bukkitWorld.getBlockAt(x, y, z).setTypeId(typeId);
			return;
		} else if (!isChunkGeneratedInMemory(x >> 4, z >> 4)) {
			generateChunkBase(x >> 4, z >> 4);
		}
		_setBlockTypeId(x, y, z, typeId);
	}

	void _setBlockTypeId(int x, int y, int z, int typeId) {
		if (outOfBounds(y)) {
			return;
		}

		ChunkSection chunkSection = sectionPool.get(x >> 4, y >> 4, z >> 4);
		chunkSection.setFullBlockId(x & 15, y & 15, z & 15, (short) typeId);
	}

	private boolean outOfBounds(int y) {
		return y < 0 || y >= HEIGHT;
	}

	boolean isChunkGeneratedInMemory(int x, int z) {
		boolean generated = false;
		for (int i = 0; i < HEIGHT >> 4; i++) {
			generated |= (sectionPool.getGeneratedOnly(x, i, z) != null);
		}
		return generated;
	}

	boolean isChunkGenerated(int x, int z) {
		return bukkitWorld.loadChunk(x, z, false);
	}

	Chunk getChunk(int x, int z) {
		return new Chunk(x, z, this);
	}

	BlockColumn getBlockColumn(int x, int z) {
		return new BlockColumn(x, z, this);
	}

	void generateChunkBase(int x, int z) {
		for (int cx = 0; cx < Chunk.WIDTH; cx++) {
			for (int cz = 0; cz < Chunk.DEPTH; cz++) {
				int wx = (x << 4) + cx;
				int wz = (z << 4) + cz;
				generator.getBiomeGenerator(wx, wz).generateBaseTerrain(getBlockColumn(wx, wz));
			}
		}
	}

	ChunkSectionPool getChunkSectionPool() {
		return sectionPool;
	}

	class ChunkSectionPool {

		private final Pool.PoolObjectFactory<ChunkSection> chunkObjectFactory = new Pool.PoolObjectFactory<ChunkSection>() {

			@Override
			public ChunkSection createObject() {
				return new ChunkSection();
			}
		};
		private final Pool<ChunkSection> chunkPool = new Pool<ChunkSection>(chunkObjectFactory);
		private final List<ChunkSection> chunkSections = new ArrayList<ChunkSection>();

		private ChunkSectionPool() {
		}

		private ChunkSection newSection(int x, int y, int z) {
			if (y >= Chunk.HEIGHT >> 4 || y < 0) {
				return null;
			}

			ChunkSection section = chunkPool.newObject();
			chunkSections.add(0, section);
			section.setX(x);
			section.setY(y);
			section.setZ(z);
			section.clearBlocks();
			return section;
		}

		public void free(ChunkSection chunkSection) {
			chunkPool.free(chunkSection);
			chunkSections.remove(chunkSection);
		}

		ChunkSection get(int x, int y, int z) {
			ChunkSection section = getGeneratedOnly(x, y, z);
			if (section != null) {
				return section;
			}
			return newSection(x, y, z);
		}

		public ChunkSection getGeneratedOnly(int x, int y, int z) {
			if (y >= Chunk.HEIGHT >> 4 || y < 0) {
				return null;
			}

			for (int i = 0; i < chunkSections.size(); i++) {
				ChunkSection section = chunkSections.get(i);
				if (section.getX() == x && section.getY() == y && section.getZ() == z) {
					return section;
				}
			}

			return null;
		}
	}
}
