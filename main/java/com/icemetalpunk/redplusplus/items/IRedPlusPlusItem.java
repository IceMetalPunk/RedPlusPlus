package com.icemetalpunk.redplusplus.items;

public interface IRedPlusPlusItem {
	public void registerWithModel(boolean isClient);

	public abstract void registerRecipes();
}
