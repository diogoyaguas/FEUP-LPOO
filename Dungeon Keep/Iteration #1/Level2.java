import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Level2 {
	public static void main(String[] args) {
		char[][] array = {
				{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
				{ 'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'k', 'X' },
				{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' } };
	
		
		int hy = 8, hx = 1;
		int oy = 1, ox = 4;
		int paux = 4, pauy = 2;
		char hero = 'H';
		char ogre = 'O';
		char pau = '*';
		
		Scanner scan = new Scanner(System.in);

		display(array, hero, ogre, pau, hx, hy, ox, oy, paux, pauy);
		
		while (!(((Math.abs(ox - hx) <= 1) && (Math.abs(oy - hy) <= 1)) && (hx == ox || oy == hy) || ((Math.abs(paux - hx) <= 1) && (Math.abs(pauy - hy) <= 1)) && (hx == paux || pauy == hy) || (hx == 0 && hy == 1))) {
			System.out.println("\nInsert a key (eg: W,A,S,D)");
			char key = scan.next().charAt(0);
			
			switch (key) {
			case 'w':
				if (array[hy - 1][hx] == 'k') {
					hero = 'K';
					array[1][8] = ' ';
				}
				if (array[hy - 1][hx] == ' ') {
					hy--;
				}
				break;
			case 's':
				if (array[hy + 1][hx] == ' ') {
					hy++;
				}
				break;
			case 'd':
				if (array[hy][hx + 1] == 'k') {
					hero = 'K';
					array[1][8] = ' ';
				}
				if (array[hy][hx + 1] == ' ') {
					hx++;
				}
				break;
			case 'a':
				if (array[hy][hx - 1] == 'I'){
					array[1][0] = 'S';
					break;
				}
				if (array[hy][hx - 1] == ' ' || array[hy][hx - 1] == 'S') {
					hx--;
					
				}
				break;
			default:
				break;
			}
			
			int rand = ThreadLocalRandom.current().nextInt(0,4);
			
			switch (rand) {
			case 0:
				if (array[oy - 1][ox] == ' ' || array[oy - 1][ox] == 'k') {
					oy--;
				}
				break;
			case 1:
				if (array[oy + 1][ox] == ' ' || array[oy + 1][ox] == 'k') {
					oy++;
				}
				break;
			case 2:
				if (array[oy][ox + 1] == ' ' || array[oy][ox + 1] == 'k') {
					ox++;
				}
				break;
			case 3:
				if (array[oy][ox - 1] == ' ' || array[oy][ox - 1] == 'k') {
					ox--;
					
				}
				break;
			default:
				break;
			}
			int paurnd = ThreadLocalRandom.current().nextInt(0,4);

			
			switch (paurnd) {
			case 0:
				if (array[oy - 1][ox] == ' ' || array[oy - 1][ox] == 'k') {
					pauy = oy - 1;
					paux = ox;
				}
				break;
			case 1:
				if (array[oy + 1][ox] == ' ' || array[oy + 1][ox] == 'k') {
					pauy = oy + 1;
					paux = ox;
				}
				break;
			case 2:
				if (array[oy][ox + 1] == ' ' || array[oy][ox + 1] == 'k') {
					paux = ox + 1;
					pauy = oy;
				}
				break;
			case 3:
				if (array[oy][ox - 1] == ' ' || array[oy][ox - 1] == 'k') {
					paux = ox - 1;
					pauy = oy;
				}
				break;
			default:
				break;
			}
			
			
			if(paux == 8 && pauy == 1){
				pau = '$';
			}else
				pau = '*';
			
			if(ox == 8 && oy == 1){
				ogre = '$';
			}else
				ogre = 'O';
			
			display(array, hero, ogre, pau, hx, hy, ox, oy, paux, pauy);
			
			
		}
		if (hx == 0 && hy == 1){
			System.out.println("\nYou Won");
		}else{
			System.out.println("\nYou Lost");
		}
		
		scan.close();
	}
	
	public static void display(char[][] arr, char hero, char ogre,  char pau, int hx, int hy, int ox, int oy, int paux, int pauy){
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 9; j++) {
				if(i == hy && j == hx){
					System.out.print(hero);
					continue;
				}
				if(i == oy && j == ox){
					System.out.print(ogre);
					continue;
				}
				if(i == pauy && j == paux){
					System.out.print(pau);
					continue;
				}
				System.out.print(arr[i][j]);
			}
			System.out.println(arr[i][9]);
		}
	}
}
