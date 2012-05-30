package com.xpansive.layer;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class ChunkTest {
	private Chunk chunk;
	private World world = new World(null, null);
	private int chunkCount;

	@Before
	public void setUp() {
		chunk = new Chunk(chunkCount, chunkCount++, world);
	}

	@Test
	public void testInitalBlockData() {
		assertEquals(0, chunk.getBlockData(0, 0, 0));
	}

	@Test
	public void testBlockData() {
		chunk.setBlockData(0, 0, 0, 10);
		assertEquals(10, chunk.getBlockData(0, 0, 0));
	}

	@Test
	public void testBlockDataCap() {
		chunk.setBlockData(0, 0, 0, 17);
		assertEquals(1, chunk.getBlockData(0, 0, 0));
	}

	@Test
	public void testBlockDataBounds() {
		chunk.setBlockData(Chunk.WIDTH, 0, 0, 5);
		chunk.setBlockData(-1, 0, 0, 5);
		chunk.setBlockData(0, Chunk.HEIGHT, 0, 5);
		chunk.setBlockData(0, -1, 0, 5);
		chunk.setBlockData(0, 0, Chunk.DEPTH, 5);
		chunk.setBlockData(0, 0, -1, 5);
		assertEquals(0, chunk.getBlockData(Chunk.WIDTH, 0, 0));
		assertEquals(0, chunk.getBlockData(-1, 0, 0));
		assertEquals(0, chunk.getBlockData(0, Chunk.HEIGHT, 0));
		assertEquals(0, chunk.getBlockData(0, -1, 0));
		assertEquals(0, chunk.getBlockData(0, 0, Chunk.DEPTH));
		assertEquals(0, chunk.getBlockData(0, 0, -1));
	}
}
