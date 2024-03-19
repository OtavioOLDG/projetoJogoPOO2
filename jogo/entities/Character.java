package jogo.entities;

import jogo.entities.Enum.CombatStatus;
import jogo.entities.Enum.HealthStatus;

public abstract class Character {
	
	private String name;
	private Integer health;
	private Integer power;
	private HealthStatus healthStatus;
	private CombatStatus combatStatus;
	protected Character aim;
	
	public Character() {
	}
	
	public Character(String name, Integer health, Integer power) {
		this.name = name;
		this.health = health;
		this.power = power;
		this.healthStatus = HealthStatus.ALIVE;
		this.combatStatus = CombatStatus.NOT_IN_COMBAT;
		this.aim = null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getHealth() {
		return health;
	}

	public void setHealth(Integer health) {
		this.health = health;
	}

	public Integer getPower() {
		return power;
	}

	public void setPower(Integer power) {
		this.power = power;
	}

	public HealthStatus getHealthStatus() {
		return healthStatus;
	}

	public void setHealthStatus(HealthStatus status) {
		this.healthStatus = status;
	}

	public Character getAim() {
		return aim;
	}

	public CombatStatus getCombatStatus() {
		return combatStatus;
	}

	public void setCombatStatus(CombatStatus combatStatus) {
		this.combatStatus = combatStatus;
	}
	
	protected final String getStringHealthStatus() {
		if(this.getHealthStatus().equals(HealthStatus.ALIVE))
			return "vivo(a)";
		return "morto(a)";
	}
	
	protected final String getStringCombatStatus() {
		if(this.getCombatStatus().equals(CombatStatus.NOT_IN_COMBAT))
			return "fora de combate";
		else if(this.getCombatStatus().equals(CombatStatus.ATTACKING))
			return "em modo de ataque";
		return "em modo de defesa";
	}
	
	public abstract void setAim(Character aim);
}
