package jogo.entities.item;

import jogo.entities.Hero;
import jogo.entities.Enum.ItemStatus;

public class Potion extends Item{
	
	public Potion() {
		super();
	}

	public Potion(String name, ItemStatus itemStatus, Hero hero) {
		super(name, 1, itemStatus, hero);
	}
	
    @Override
    public void useItem() {
        if (getHero().getHealth() + 3 >= 10) 
            this.getHero().setHealth(10);
        else 
            this.getHero().setHealth(this.getHero().getHealth() + 3);
        this.setUses(this.getUses()-1);
        if(getUses() == 0)
        	getHero().drop(this);
    }

	@Override
	public String toString() {
		return this.getName() + " de " + this.getUses() + " usos";
	}
    
    
}
