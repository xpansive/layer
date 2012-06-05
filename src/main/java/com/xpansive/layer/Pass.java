package com.xpansive.layer;

import com.xpansive.layer.util.LayerRandom;

public interface Pass {

	/**
	 * Runs this pass on the specified chunk, processing only the block locations given.
	 * <p/>
	 * @param column The vertical block column to operate on.
	 * @param random A LayerRandom instance you may use for generating random numbers.
	 */
	public void run(BlockColumn column, LayerRandom random);
}
