package com.xpansive.layer;

/*
 * Provides the public interface for a chunk (16 * 256 * 16).
 */
public class Chunk {

	public static final int DEPTH = 16;
	public static final int HEIGHT = 256;
	public static final int WIDTH = 16;
	private final World world;
	private final int x, z, wx, wz;

	Chunk(int x, int z, World world) {
		this.x = x;
		this.z = z;
		wx = x << 4;
		wz = z << 4;
		this.world = world;
	}

	/**
	 * Gets the block data value at the specified coordinates within this chunk.
	 * <p/>
	 * @param x The x location of the data to get. 0 <= x <= 15.
	 * @param y The y location of the data to get. 0 <= y <= 255.
	 * @param z The z location of the data to get. 0 <= z <= 15.
	 * @return The data value. 0 <= data <= 15.
	 */
	public byte getBlockData(int x, int y, int z) {
		if (outOfBounds(x, z)) {
			return 0;
		}

		return world._getBlockData(wx + x, y, wz + z);
	}

	/**
	 * Gets the block type id at the specified coordinates within this chunk.
	 * <p/>
	 * @param x The x location of the type id to get. 0 <= x <= 15.
	 * @param y The y location of the type id to get. 0 <= y <= 255.
	 * @param z The z location of the type id to get. 0 <= z <= 15.
	 * @return The type id. 0 <= id <= 15.
	 */
	public int getBlockTypeId(int x, int y, int z) {
		if (outOfBounds(x, z)) {
			return 0;
		}

		return world._getBlockTypeId(wx + x, y, wz + z);
	}

	/**
	 * Returns the x coordinate of this chunk in chunk coordinates.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Returns the x coordinate of this chunk in chunk coordinates.
	 */
	public int getZ() {
		return z;
	}

	/**
	 * Sets the block data value at the specified coordinates within this chunk.
	 * <p/>
	 * @param x The x location of the data to set. 0 <= x <= 15.
	 * @param y The y location of the data to set. 0 <= y <= 255.
	 * @param z The z location of the data to set. 0 <= z <= 15.
	 * @param data The data value to set. 0 &lt;= data <= 15.
	 */
	public void setBlockData(int x, int y, int z, int data) {
		if (outOfBounds(x, z)) {
			return;
		}

		world._setBlockData(wx + x, y, wz + z, data);
	}

	/**
	 * Sets the block type id at the specified coordinates within this chunk.
	 * <p/>
	 * @param x The x location of the type id to set. 0 <= x <= 15.
	 * @param y The y location of the type id to set. 0 <= y <= 255.
	 * @param z The z location of the type id to set. 0 <= z <= 15.
	 * @param typeId The type id value to set. 0 <= typeId <= 15.
	 */
	public void setBlockTypeId(int x, int y, int z, int typeId) {
		if (outOfBounds(x, z)) {
			return;
		}

		world._setBlockTypeId(wx + x, y, wz + z, typeId);
	}

	private static boolean outOfBounds(int x, int z) {
		// The world handles y coords
		return x >= WIDTH || x < 0 || z >= DEPTH || z < 0;
	}

	public World getWorld() {
		return world;
	}
}