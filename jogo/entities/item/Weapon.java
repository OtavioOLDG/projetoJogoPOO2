package jogo.entities.item;

import jogo.entities.Hero;
import jogo.entities.Enum.ItemStatus;

public class Weapon extends Item{

	private Integer damage;
	
	public Weapon() {
		super();
	}

	public Weapon(String name, Integer uses, ItemStatus itemStatus, Hero hero, Integer damage) {
		super(name, uses, itemStatus, hero);
		this.damage = damage;
	}

	public Integer getDamege() {
		return damage;
	}

	@Override
	public void useItem() {
		this.getHero().getAim().setHealth(this.getHero().getAim().getHealth() - (damage + this.getHero().getPower()));
		if(this.getHero().getAim().getHealth() <= 0) {
			this.getHero().setAim(null);
		}
	}

	@Override
	public String toString() {
		return this.getName()+ " de dano " + damage + " com mais " + this.getUses() + " usos";
	}
	
	

}
