package jogo.entities.item;

import jogo.entities.Hero;
import jogo.entities.Enum.ItemStatus;

public abstract class Item {
	private String name;
	private Integer uses;
	private ItemStatus itemStatus;
	
	private Hero hero;
	
	public Item() {
		super();
	}
	public Item(String name, Integer uses, ItemStatus itemStatus, Hero hero) {
		this.name = name;
		this.uses = uses;
		this.hero = hero;
		this.setItemStatus(itemStatus);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getUses() {
		return uses;
	}
	public void setUses(Integer uses) {
		this.uses = uses;
	}
	public Hero getHero() {
		return hero;
	}
	public ItemStatus getItemStatus() {
		return itemStatus;
	}
	public void setItemStatus(ItemStatus itemStatus) {
		this.itemStatus = itemStatus;
	}
	
	public abstract void useItem();
	
}
