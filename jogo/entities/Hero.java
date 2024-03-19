package jogo.entities;

import java.util.ArrayList;
import java.util.List;

import jogo.Interface.Collectible;
import jogo.entities.Enum.CombatStatus;
import jogo.entities.Enum.ItemStatus;
import jogo.entities.item.Item;
import jogo.entities.item.Potion;
import jogo.entities.item.Weapon;

public class Hero extends Character implements Collectible{
	
	protected List<Villan> villans = new ArrayList<>();
	private List<Item> items = new ArrayList<>();
	private List<Weapon> weapons = new ArrayList<>();
	private List<Potion> potions = new ArrayList<>();

	public Hero() {
		super();
	}

	public Hero(String name, Integer health, Integer power) {
		super(name, health, power);
	}
	
	public void addVillan(Villan villan) {
		villans.add(villan);
	}
	
	public void removeVillan(Villan villan) {
		villans.remove(villan);
	}

	public List<Villan> getVillans() {
		return villans;
	}
	
	public List<Item> getItems() {
		return items;
	}

	public List<Weapon> getWeapons() {
		return weapons;
	}

	public List<Potion> getPotions() {
		return potions;
	}

	@Override
	public String toString() {
		String villan = "ninguém, ";
		StringBuilder x = new StringBuilder();
		if(!villans.isEmpty()) {
			int count = 0;
			for (Villan v : villans) {
				if(count == 0)
					x.append(v.getName() + ", ");
			}
			villan = x.toString();
		}
		return this.getName() + " está "+ getStringHealthStatus() +  ", possuindo " + this.getHealth() + " de pontos de vida e poder de força de " + this.getPower()
		+ " pontos, tem como inimigo " + villan + "atualmente se encontra " + getStringCombatStatus() + ";";
	}

	@Override
	public void setAim(Character villan) {
		if(villans.contains(villan)) {
			this.aim = villan;
		}
		else
			this.aim = null;
	}
	
	public void attack(int choice) {
		this.setCombatStatus(CombatStatus.ATTACKING);
		getWeapons().get(choice - 1).useItem();
	}

	public void defend(int choice) {
		this.setCombatStatus(CombatStatus.DEFENDING);
		getPotions().get(choice - 1).useItem();
	}

	@Override
	public void collect(Item item) {
		items.add(item);
		item.setItemStatus(ItemStatus.COLLECTED);
		if(item instanceof Weapon)
			this.weapons.add((Weapon) item);
		else if(item instanceof Potion)
			this.potions.add((Potion) item);
	}

	@Override
	public Item drop(Item item) {
		item.setItemStatus(ItemStatus.DROPPED);
		items.remove(item);
		if(item instanceof Weapon)
			weapons.remove(item);
		else
			potions.remove(item);
		return item;
	}
	
	public String listWeapons() {
		StringBuilder str = new StringBuilder();
		int count = 1;
		for(Weapon i : weapons) {
			str.append(count + "- " + i.getName() + ";\n");
			count++;
		}
		return str.toString();
	}
	
	public String listPotions() {
		StringBuilder str = new StringBuilder();
		int count = 1;
		for(Potion i : potions) {
			str.append(count + "- " + i.getName() + ";\n");
			count++;
		}
		return str.toString();
	}
	
	public String listVillans() {
		StringBuilder str = new StringBuilder();
		int count = 1;
		for(Villan v : villans) {
			str.append(count + "- " + v.getName() + ";\n");
			count++;
		}
		return str.toString();
	}
	
	public String listItems() {
		StringBuilder str = new StringBuilder();
		int count = 1;
		for(Item i : items) {
			str.append(count + "- " + i.getName() + ";\n");
			count++;
		}
		return str.toString();
	}
	
	
}
