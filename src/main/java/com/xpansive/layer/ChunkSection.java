package com.xpansive.layer;

import java.util.Arrays;

/**
 * This represents a 16*16*16 chunk, not the conventional 16*16*256
 */
public class ChunkSection {

	public static final int DEPTH = 16;
	public static final int HEIGHT = 16;
	public static final int WIDTH = 16;
	private final byte blocks[] = new byte[WIDTH * HEIGHT * DEPTH];
	private final byte extData[] = new byte[WIDTH * HEIGHT * DEPTH];    // Holds both the block id extension and data
	private int x, y, z;

	private static int offset(int x, int y, int z) {

		// Use the same format as Bukkit for easy transfers
		return y << 8 | z << 4 | x;
	}

	void clearBlocks() {
		Arrays.fill(blocks, (byte) 0);
		Arrays.fill(extData, (byte) 0);
	}

	byte getBlockData(int x, int y, int z) {
		return (byte) (extData[offset(x, y, z)] & 0xF);
	}

	byte getBlockId(int x, int y, int z) {
		return blocks[offset(x, y, z)];
	}

	byte getBlockIdExt(int x, int y, int z) {
		return (byte) (extData[offset(x, y, z)] >>> 4 & 0xF);
	}

	short getFullBlockId(int x, int y, int z) {
		return (short) (getBlockId(x, y, z) & 0xFF | getBlockIdExt(x, y, z) << 8 & 0xF00);
	}

	int getX() {
		return x;
	}

	int getY() {
		return y;
	}

	int getZ() {
		return z;
	}

	void setBlockData(int x, int y, int z, byte data) {
		extData[offset(x, y, z)] = (byte) (extData[offset(x, y, z)] & 0xF0 | data & 0xF);
	}

	void setBlockId(int x, int y, int z, byte id) {
		blocks[offset(x, y, z)] = id;
	}

	void setBlockIdExt(int x, int y, int z, byte ext) {
		extData[offset(x, y, z)] = (byte) (extData[offset(x, y, z)] & 0xF | ext << 4 & 0xF0);
	}

	void setFullBlockId(int x, int y, int z, short id) {
		setBlockId(x, y, z, (byte) (id & 0xFF));
		setBlockIdExt(x, y, z, (byte) (id >>> 8 & 0xF));
	}

	void setX(int x) {
		this.x = x;
	}

	void setY(int y) {
		this.y = y;
	}

	void setZ(int z) {
		this.z = z;
	}

	byte[] getRawBlocks() {
		return blocks.clone();
	}
}