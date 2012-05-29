package com.xpansive.layer;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ChunkSectionPoolTest {

	private World.ChunkSectionPool factory;

	@Before
	public void setup() {
		factory = new World(null, null, false).getChunkSectionPool();
	}

	@Test
	public void testNotGenerated() {
		assertNull(factory.getGeneratedOnly(0, 0, 0));
	}

	@Test
	public void testGenerateNew() {
		assertNotNull(factory.get(0, 0, 0));
		assertNotNull(factory.getGeneratedOnly(0, 0, 0));
	}

	@Test
	public void testBounds() {
		assertNull(factory.get(0, Integer.MAX_VALUE, 0));
		assertNull(factory.get(0, Integer.MIN_VALUE, 0));
		assertNotNull(factory.get(0, 0, 0));
		assertNull(factory.get(0, Chunk.HEIGHT >> 4, 0));
		assertNotNull(factory.get(Integer.MAX_VALUE, 0, Integer.MAX_VALUE));
		assertNotNull(factory.get(Integer.MIN_VALUE, 0, Integer.MIN_VALUE));
	}

	@Test
	public void testFree() {
		ChunkSection section = factory.get(0, 0, 0);
		assertNotNull(section);
		factory.free(section);
		assertNull(factory.getGeneratedOnly(0, 0, 0));
		assertNotNull(factory.get(0, 0, 0));
	}

	@Test
	public void testSame() {
		assertEquals(factory.get(0, 0, 0), factory.get(0, 0, 0));
	}

	@Test
	public void testMultipleSections() {
		ChunkSection section1 = factory.get(0, 0, 0);
		ChunkSection section2 = factory.get(0, 1, 0);
		ChunkSection section3 = factory.get(0, 2, 0);
		ChunkSection section4 = factory.get(0, 3, 0);

		assertEquals(section2, factory.get(0, 1, 0));
		assertEquals(section4, factory.get(0, 3, 0));
		assertEquals(section1, factory.get(0, 0, 0));
		assertEquals(section3, factory.get(0, 2, 0));

		assertNotSame(section1, section2);
		assertNotSame(section3, section4);
	}
}
