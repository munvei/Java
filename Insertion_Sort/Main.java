import java.util.Scanner;

public class Main {
	void run() {
		// 入力
		Scanner scan = new Scanner(System.in);
		int[] array = new int[1000];
		int n = scan.nextInt();
		for(int i = 0; i < n; i++) {
			array[i] = scan.nextInt();
		}
		// ソート
		for(int i = 1; i < n; i++) {
			System.out.print((i-1) + ": ");
			this.printArray(array, n);
			if(array[i] < array[i-1]) {
				for(int j = i; j > 0; j--) {
					if(array[j] >= array[j-1]) break;
					int tmp = array[j];
					array[j] = array[j-1];
					array[j-1] = tmp;
				}
			}
		}
		System.out.print("result: ");
		this.printArray(array, n);
	}
	
	void printArray(int[] array, int n) {
		for(int i = 0; i < n; i++) {
			System.out.print(array[i]);
			if(i == n-1) System.out.println();
			else System.out.print(" ");
		}
	}
	public static void main(String[] args) {
		new Main().run();
	}
}
