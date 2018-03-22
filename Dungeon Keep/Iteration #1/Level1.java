import java.util.Scanner;

public class BasicGame {

	public static void main(String[] args) {
		char[][] array = {
				{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
				{ 'X', 'H', ' ', ' ', 'I', ' ', 'X', ' ', 'G', 'X' },
				{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X' },
				{ 'X', ' ', 'I', ' ', 'I', ' ', 'X', ' ', ' ', 'X' },
				{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X' },
				{ 'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', 'X', ' ', 'X' },
				{ 'X', ' ', 'I', ' ', 'I', ' ', 'X', 'k', ' ', 'X' },
				{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' } };
		
		char[] ordens = { 'a', 's', 's', 's', 's', 'a', 'a', 'a', 'a', 'a', 'a', 's', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'w', 'w', 'w', 'w', 'w'};
		int ordem = 0;
		int posx = 1, posy = 1;
		int posgx = 8, posgy =1;
		display(array);

		Scanner scan = new Scanner(System.in);

		while (!((Math.abs(posgx - posx) <= 1) && (Math.abs(posgy - posy) <= 1) && (posx == posgx || posy == posgy) || (posx == 0 && (posy == 5 || posy == 6)))) {
			System.out.println("\nInsert a key (eg: W,A,S,D)");
			char key = scan.next().charAt(0);

			switch (key) {
			case 'w':
				if (array[posy - 1][posx] == ' ') {
					array[posy][posx] = ' ';
					posy--;
					array[posy][posx] = 'H';
				}
				break;
			case 's':
				if (array[posy + 1][posx] == ' ') {
					array[posy][posx] = ' ';
					posy++;
					array[posy][posx] = 'H';
				}
				break;
			case 'd':
				if (array[posy][posx + 1] == ' ') {
					array[posy][posx] = ' ';
					posx++;
					array[posy][posx] = 'H';
				}
				break;
			case 'a':
				if  (posx == 8 && posy == 8){
					array[5][0] = 'S';
					array[6][0] = 'S';
					break;
				}
				if (array[posy][posx - 1] == ' ' || array[posy][posx - 1] == 'S') {
					array[posy][posx] = ' ';
					posx--;
					array[posy][posx] = 'H';
				}
				break;
			default:
				break;
			}
			switch (ordens[ordem]) {
			case 'w':
					array[posgy][posgx] = ' ';
					posgy--;
					array[posgy][posgx] = 'G';
				break;
			case 's':
					array[posgy][posgx] = ' ';
					posgy++;
					array[posgy][posgx] = 'G';
				break;
			case 'd':
					array[posgy][posgx] = ' ';
					posgx++;
					array[posgy][posgx] = 'G';
				break;
			case 'a':
					array[posgy][posgx] = ' ';
					posgx--;
					array[posgy][posgx] = 'G';
				break;
			default:
				break;
			}
			if(ordem == ordens.length-1){
				ordem = 0;
			}else{
				ordem++;
			}
			System.out.println("\n\n\n");
			display(array);
		}
		
		if (posx == 0 && (posy == 5 || posy == 6)){
			System.out.println("\nYou Won");
		}else{
			System.out.println("\nYou Lost");
		}
		
		scan.close();
	}
	public static void display(char[][] arr){
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(arr[i][j]);
			}
			System.out.println(arr[i][9]);
		}
	}

}
