
public class Main {
	public static void main(String[] args) {
		int n = 10;
		int[] array = new int[n];
		// 反対から入れてるだけ
		for(int i = 0; i < array.length; i++) {
			array[i] = n-i;
		}
		QuickSort sort = new QuickSort(n, array);
		// 表示
		sort.printAll();
	}
}
