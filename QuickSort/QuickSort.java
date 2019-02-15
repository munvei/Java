// 与えられた配列の先頭をpivotとしたQuickSortを行うクラス
public class QuickSort {
	private int len;
	private int[] array;
	private int left, right; // ポインター
	
	private QuickSort() {}
	public QuickSort(int len, int[] array) {
		this.len = len;
		this.array = array;
		
		this.sort(0, len-1);
	}
	
	private void sort(int left, int right) {
		// 範囲が1,配列の範囲外なら終了
		if(right-left <= 0 || right < 0 || left > this.len-1) return; // アセンブラ動かない理由ここじゃね
		
		int start = left; // pivot
		int end = right;
		// 指定されたpivotでソート
		while(true) {
			// pivot以下のものを右から探す
			for( ; array[right] > array[start]; right--);
			// pivotより大きいものを左から探す
			for( ; left <= end && array[left] <= array[start]; left++); // あとここも
			if(left < right) {
				// 入れ替える
				this.swap(left, right);
			} else {
				// pivotと入れ替え
				this.swap(right, start);
				break;
			}
		}
		// 再起
		sort(start, right-1);
		sort(right+1, end);
	}
	
	private void swap(int a, int b) {
		int tmp = array[a];
		array[a] = array[b];
		array[b] = tmp;
	}
	
	public void printAll() {
		for(int i = 0; i < this.array.length; i++) System.out.println("array[" + i + "] = " + array[i]);
	}
}
