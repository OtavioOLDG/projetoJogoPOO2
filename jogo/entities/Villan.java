package jogo.entities;

import java.util.Random;

public class Villan extends Character{
	private Hero hero;
	
	public Villan() {
		super();
	}

	public Villan(String name, Hero hero) {
		super(name, 4, null);
		Random rd = new Random();
		this.setPower(rd.nextInt(4)+1);
		this.hero = hero;
		hero.addVillan(this);
		setAim(hero);
	}

	@Override
	public void setAim(Character aim) {
		this.aim = hero;
	}

	public void attackHero() {
		getHero().setHealth(getHero().getHealth() - this.getPower());
		System.out.println(this.getHero().getName() + " foi atacado por " + this.getName() + " e sofreu " + this.getPower() + " de dano");
	}

	@Override
	public String toString() {
		return this.getName() + " está "+ getStringHealthStatus() +  ", possuindo " + this.getHealth() + " de pontos de vida e poder de força de " + this.getPower()
		+ " pontos, tem como inimigo o herói " + hero.getName() + ", atualmente se encontra " + getStringCombatStatus() + ";";
	}

	public Hero getHero() {
		return hero;
	}
	

}