package jogo.main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import jogo.entities.Character;
import jogo.entities.Hero;
import jogo.entities.Villan;
import jogo.entities.Enum.CombatStatus;
import jogo.entities.Enum.HealthStatus;
import jogo.entities.item.Item;
import jogo.entities.item.Potion;
import jogo.entities.item.Weapon;
import jogo.exception.GameException;

public class Main {

	public static void main(String[] args) {
		List<Character> aliveCharacters = new ArrayList<>();
		List<Character> deadCharacters = new ArrayList<>();
		List<Item> collectedItems = new ArrayList<>();
		Scanner sc = new Scanner(System.in);
		
		try{
			Hero hero = createHero(sc);
			aliveCharacters.add(hero);
			while(hero.getHealthStatus().equals(HealthStatus.ALIVE)) {
				try {
					System.out.println();
					System.out.println("--------------------------------------------------------------------------------------------");
					System.out.println(hero.getName() + " está andando");
					historyFlow(randomWlak(), sc, hero, aliveCharacters, collectedItems);
					System.out.println();
					verifyAlive(aliveCharacters, deadCharacters);
					verifyVillans(aliveCharacters);
//					System.out.println(aliveCharacters);
//					System.out.println(deadCharacters);
				}
				catch (GameException e) {
					System.out.println(e.getMessage());
				} 
				catch (FileNotFoundException e) {
					System.out.println("Arquivo .txt não encontrado");
				} 
				catch (IOException e) {
					System.out.println("Erro ao ler o arquivo .txt");
				}
				catch (IndexOutOfBoundsException e) {
					System.out.println("Escolha impossível, perdeu a vez;");
				}
				finally {
					villansTurn(aliveCharacters);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			sc.close();
		}
	}
	
	private static void villansTurn(List<Character> aliveCharacters) {
		for(Character c : aliveCharacters) {
			if(c instanceof Villan) {
				((Villan) c).attackHero();
			}
		}
	}

	private static void verifyAlive(List<Character> aliveCharacters, List<Character> deadCharacters) {
	    aliveCharacters.removeIf(x -> {
	        if (x.getHealth() <= 0) {
	            x.setHealthStatus(HealthStatus.DEAD);
	            x.setCombatStatus(CombatStatus.NOT_IN_COMBAT);
	            deadCharacters.add(x);
	            return true;
	        }
	        return false;
	    });
	}
	private static void verifyVillans(List<Character> aliveCharacters) {
		for(Character x : aliveCharacters) {
			if(x instanceof Hero) {
				((Hero) x).getVillans().removeIf(v -> v.getHealthStatus().equals(HealthStatus.DEAD));
				}
		}
	}
	
	private static void printWeapons(Hero hero) {
		if(!hero.getWeapons().isEmpty())
			System.out.println(hero.listWeapons());
		else
			System.out.println("Não há armas");
	}
	
	private static void printItems(Hero hero) {
		if(!hero.getItems().isEmpty())
			System.out.println(hero.listItems());
		else
			System.out.println("Não há itens");
	}
	
	private static void printPotions(Hero hero) {
		if(!hero.getPotions().isEmpty())
			System.out.println(hero.listPotions());
		else
			System.out.println("Não há poções");
	}
	
	private static void printVillans(Hero hero) {
		if(!hero.getVillans().isEmpty())
			System.out.println(hero.listVillans());
		else
			System.out.println("Não há vilões");
	}

	private static Hero createHero(Scanner sc) {
		Random r = new Random();
		int power = r.nextInt(4) + 1;
		System.out.print("Escolha um nome para o herói: ");
		return new Hero(sc.nextLine(), 10, power);
	}
	
	private static Potion createPotion(Hero hero) {
		return new Potion("Poção de cura", null, hero);
	}
	
	private static Weapon createWeapon(Hero hero) {
		Random r = new Random();
		int weapon = r.nextInt(2)+1;
		if(weapon == 1)
			return new Weapon("Arco", 5, null, hero, 2);
		else
			return new Weapon("Espada", 10, null, hero, 4);
	}
	
	private static int randomWlak() {
		Random r = new Random();
		int lucky = r.nextInt(100) + 1;
		return lucky;
	}
	
	private static void historyFlow(int walk, Scanner sc, Hero hero, List<Character> aliveCharacters, 
	List<Item> collectedItems) throws FileNotFoundException, IOException {
		if(walk <= 33) {
			itemHistory(walk, hero, collectedItems);
		}
		else if(walk > 33 && walk <= 66) {
			villanHistory(hero, aliveCharacters);
		}
		else {
			System.out.println(hero.getName() + " nada achou");
		}
		System.out.println();
		System.out.println("Status do herói:");
		System.out.println(hero);
		System.out.print("Poções disponíveis: ");
		printPotions(hero);
		System.out.print("Armas disponíveis: ");
		printWeapons(hero);
		System.out.println();
		System.out.println("Escolha um caminho");
		System.out.println(printWays(hero));
		int choice = sc.nextInt();
		choices(choice, hero, sc);
		
	}

	private static void itemHistory(int walk, Hero hero, List<Item> collectedItems) {
		if(walk <= 16)
			foundPotion(hero, collectedItems);
		else
			foundWeapon(hero, collectedItems);

	}
	
	private static void villanHistory(Hero hero, List<Character> aliveCharacters) 
	throws FileNotFoundException, IOException {
		Villan villan = new Villan(villanName(), hero);
		aliveCharacters.add(villan);
		System.out.println("O herói encontrou o(a) vilã(o) " + villan.getName());
		System.out.println(villan);
	}
	
	private static void foundPotion(Hero hero, List<Item> collectedItems) {
		Potion potion = createPotion(hero);
		hero.collect(potion);
		collectedItems.add(potion);
		System.out.println(hero.getName() + " encontrou algo: " + potion);
	}
	
	private static void foundWeapon(Hero hero, List<Item> collectedItems) {
		Weapon weapon = createWeapon(hero);
		hero.collect(weapon);
		collectedItems.add(weapon);
		System.out.println(hero.getName() + " encontrou algo: " + weapon);
	}
	
	private static String villanName() throws FileNotFoundException, IOException {
		Random random = new Random();
        int totalLines = 0;
        String selectedName = null;
        String file= "\\atividade_Fabio\\jogo\\src\\nomes.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while (line != null) {
                totalLines++;
                if (random.nextInt(totalLines) == 0) {
                    selectedName = line;
                }
                line = reader.readLine();
            }
        }
        String firstLetter = selectedName.substring(0,1);
        String text = selectedName.substring(1).toLowerCase();
        return firstLetter + text;
	}
	
	private static String printWays(Hero hero) {
		StringBuilder str = new StringBuilder();
		str.append("Digite:\n");
		if(!hero.getVillans().isEmpty()) {
			str.append("1 - para mirar em alguém\n");
		}
		if(!hero.getVillans().isEmpty() && hero.getAim() != null && !hero.getWeapons().isEmpty()) {
			str.append("2 - para atacar e usar uma arma;\n");
		}
		if(!hero.getPotions().isEmpty()) {
			str.append("3 - para defender e usar uma poção\n");
		}
		if(!hero.getItems().isEmpty()) {
			str.append("4 - para dropar um item\n");
		}
		str.append("5 - para passar o tempo\n");
		return str.toString();
		
	}
	
	public static void choices(int c, Hero hero, Scanner sc) {
		if(c == 1)
			aimHero(hero, sc);
		else if(c == 2)
			heroAttack(hero, sc);
		else if(c == 3)
			heroDeffend(hero, sc);
		else if(c == 4)
			heroDrop(hero, sc);
		else if(c == 5)
			System.out.println(hero.getName() + " descansou");
		else
			throw new GameException("Escolha de caminho errada");
	}

	public static void aimHero(Hero hero, Scanner sc) {
		System.out.println("Escolha um vilão");
		printVillans(hero);
		int choice = sc.nextInt();
		hero.setAim(hero.getVillans().get(choice - 1));
		System.out.println("O herói agora está mirando em " + ((Villan) hero.getAim()).getName());
	}
	
	
	public static void heroAttack(Hero hero, Scanner sc) {
		System.out.println("Escolha uma arma");
		printWeapons(hero);
		int choice = sc.nextInt();
		System.out.println(hero.getName() + " atacou " + ((Villan) hero.getAim()).getName());
		hero.attack(choice);
	}
	

	public static void heroDeffend(Hero hero, Scanner sc) {
		System.out.println("Escolha uma poção");
		printPotions(hero);;
		int choice = sc.nextInt();
		System.out.println(hero.getName() + " usou a poção " + hero.getPotions().get(choice - 1));
		hero.defend(choice);
	}
	
	private static void heroDrop(Hero hero, Scanner sc) {
		System.out.println("Escolha um item");
		printItems(hero);
		int choice = sc.nextInt();
		System.out.println(hero.getName() + " largou " + hero.drop(hero.getItems().get(choice - 1)));
		
	}
	
}
