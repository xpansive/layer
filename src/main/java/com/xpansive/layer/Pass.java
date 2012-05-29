package com.xpansive.layer;

public interface Pass {

	/**
	 * Runs this pass on the specified chunk, processing only the block locations given.
	 * <p/>
	 * @param column The vertical block column to operate on.
	 */
	public void run(BlockColumn column);

}
