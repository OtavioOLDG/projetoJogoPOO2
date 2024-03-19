package jogo.Interface;

import jogo.entities.item.Item;

public interface Collectible {
	public void collect(Item item);
	public Item drop(Item item);
}
