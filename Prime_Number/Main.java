import java.util.Scanner;

public class Main {
	
	void run() {
		// 素数の配列
		// 1は素数、-１は素数でない
		int[] array = new int[100000000];
		int tmp = (int) Math.sqrt(array.length);
		for(int i = 2; i < array.length; i++) {
			if(array[i] != 0) continue;
			if(i > tmp) {
				for(int j = i; j < array.length; j++) {
					if(array[j] == 0) array[j] = 1;
				}
				break;
			}
			// 素数
			array[i] = 1;
			// 素数の倍数除去
			for(int j = 2*i; j < array.length; j += i) {
				array[j] = -1;
			}
		}
		System.out.println("input");
		// 入力
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		int count = 0;
		for(int i = 0; i < n; i++) {
			int input = scan.nextInt();
			if(array[input] == 1) count++;
		}
		// 出力
		System.out.println(count);
	}
	
	public static void main(String[] args) {
		new Main().run();
	}
}
