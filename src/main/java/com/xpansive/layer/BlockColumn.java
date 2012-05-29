package com.xpansive.layer;

/**
 * Represents a vertical column of blocks, reaching from layer 0 to 256.
 */
public class BlockColumn {

	private int x, z;
	private World world;

	public BlockColumn(int x, int z, World world) {
		this.x = x;
		this.z = z;
		this.world = world;
	}

	/**
	 * Gets the block data value at the specified coordinates within this block column.
	 * <p/>
	 * @param y The y location of the data to get. 0 <= y <= 255.
	 * @return The data value. 0 <= data <= 15.
	 */
	public byte getBlockData(int y) {
		return world.getBlockData(x, y, z);
	}

	/**
	 * Gets the block type id at the specified coordinates within this block column.
	 * <p/>
	 * @param y The y location of the type id to get. 0 <= y <= 255.
	 * @return The type id. 0 <= id <= 15.
	 */
	public int getBlockTypeId(int y) {
		return world.getBlockTypeId(x, y, z);
	}

	/**
	 * Returns the x coordinate of this block column.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Returns the z coordinate of this block column.
	 */
	public int getZ() {
		return z;
	}

	/**
	 * Sets the block data value at the specified coordinates within this block column.
	 * <p/>
	 * @param y The y location of the data to set. 0 <= y <= 255.
	 * @param data The data value to set. 0 &lt;= data <= 15.
	 */
	public void setBlockData(int y, int data) {
		world.setBlockData(x, y, z, data);
	}

	/**
	 * Sets the block type id at the specified coordinates within this block column.
	 * <p/>
	 * @param y The y location of the type id to set. 0 <= y <= 255.
	 * @param typeId The type id value to set. 0 <= typeId <= 15.
	 */
	public void setBlockTypeId(int y, int typeId) {
		world.setBlockTypeId(x, y, z, typeId);
	}
}
