import java.awt.Color;
import java.util.Random;

interface MineSweeperGUI {
	public void setTextToTile(int x, int y, String text);
	public void setTileColor(int x, int y, Color c); // 追加したメソッド
	public void win();
	public void lose();
}

public class MineSweeper {

	private final int height;
	private final int width;
	private final int numberOfTiles;
	private final int numberOfBombs;
	private int[][] table;
	// 追加したフィールド変数
	private int[][] jotai;
	private boolean gameNow;
	private int count;
	private boolean firstClick;
	// コンストラクタ
	public MineSweeper(int height, int width, int numberOfBombs) throws Exception {
		this.height = height;
		this.width = width;
		this.numberOfTiles = height * width;
		this.numberOfBombs = numberOfBombs;
		this.table = new int[height][width];
		this.jotai = new int[height][width];
		this.count = this.numberOfTiles - this.numberOfBombs;
		this.gameNow = true;
		this.firstClick = true;
		// 無限ループ回避
		if(this.numberOfBombs > this.numberOfTiles - 9) {
			throw new Exception("爆弾が多すぎます");
		}
		
		//    this.initTable();
	}

	public int getHeight() { // 使っていない
		return height;
	}

	public int getWidth() { // 使っていない
		return width;
	}

	void initTable(int x, int y, MineSweeperGUI gui) {
		// 爆弾設置
		this.setBombs(x, y, gui);

		// implement
		// table のそれぞれの位置で周囲にいくつ爆弾があるか数える
		for(int i = 0; i < this.height; i++) {
			for(int j = 0; j < this.width; j++) {
				// 爆弾じゃないなら数える
				if(this.table[i][j] != -1) this.countBumb(j, i);
			}
		}

		this.openTile(x, y, gui);
	}

	// すべてリセット
	public void reset(MineSweeperGUI gui) {
		this.firstClick = true;
		this.gameNow = true;
		this.table = new int[this.height][this.width];
		this.jotai = new int[this.height][this.width];
		this.count = this.numberOfTiles - this.numberOfBombs;

		for(int i = 0; i < this.height; i++) {
			for(int j = 0; j < this.width; j++) {
				gui.setTextToTile(j, i, "");
				gui.setTileColor(j, i, Color.LIGHT_GRAY);
			}
		}
	}

	// table中にnumberOfBombsの値だけランダムに爆弾を設置する．
	void setBombs(int x, int y, MineSweeperGUI gui) {
		// implement
		// クリックしたパネルの周囲8方向に爆弾は存在しない
		for(int i = y-1; i <= y+1; i++) {
			for(int j = x-1; j <= x+1; j++) {
				try {
					this.jotai[i][j] = -1;
				} catch(ArrayIndexOutOfBoundsException e){
					continue;
				}
			}
		}  
		// ランダムで位置を決定して設置
		Random rand = new Random();
		int l = 0;
		while(l < this.numberOfBombs) {
			int tmp = (int)rand.nextInt(this.numberOfTiles);
			int height = tmp / this.height;
			int width = tmp % this.height;
			if(this.table[height][width] != -1 
					&& this.jotai[height][width] != -1) {
				this.table[height][width] = -1;
				l++;
			}
		}
		// jotai が -1 だと open0 で開けないので元に戻す
		for(int i = y-1; i <= y+1; i++) {
			for(int j = x-1; j <= x+1; j++) {
				try {
					this.jotai[i][j] = 0;
				} catch(ArrayIndexOutOfBoundsException e) {
					continue;
				}
			}
		}
	}

	// 左クリックしたときに呼び出される．
	public void openTile(int x, int y, MineSweeperGUI gui) {
		// implement
		if(this.firstClick){
			this.firstClick = false;
			this.initTable(x, y, gui);
		}

		if(gameNow && jotai[y][x] != -1) {
			if(this.jotai[y][x] != 1) {
				this.jotai[y][x] = -1;
				if(this.table[y][x] == -1) {
					this.openAllTiles(gui);
					this.gameNow = false; 
					gui.lose();
				}
				else {
					this.count--;
					gui.setTextToTile(x, y, new Integer(this.table[y][x]).toString());
					gui.setTileColor(x, y, Color.WHITE);
					if(this.table[y][x] == 0) open0(x, y, gui);
					if(this.count == 0) {
						this.gameNow = false;
						gui.win();
					}
				}
			}
		}
	}

	// 右クリックされたときに呼び出される．
	public void setFlag(int x, int y, MineSweeperGUI gui) {
		// implement
		if(! this.firstClick) {
			if(this.jotai[y][x] == 0) {
				// フラッグを付ける
				this.jotai[y][x] = 1;
				gui.setTextToTile(x, y, "F");
			} else if(jotai[y][x] == 1){
				// フラッグを消す
				this.jotai[y][x] = 0;
				gui.setTextToTile(x, y, "");
			}
		}
	}

	// 0だとまわり全て表示
	private void open0(int x, int y, MineSweeperGUI gui) {
		for(int i = y-1; i <= y+1; i++) {
			for(int j = x-1; j <= x+1; j++) {
				try {
					if(this.jotai[i][j] != 0) continue;
					this.openTile(j, i, gui);
				} catch(ArrayIndexOutOfBoundsException e) {
					continue;
				} catch(StackOverflowError e) {
					continue;
				}
			}
		}
	}
	// まわりの爆弾の数を求める
	private void countBumb(int x, int y) {
		int countBumb = 0;
		for(int i = y-1; i <= y+1; i++) {
			for(int j = x-1; j <= x+1; j++) {
				if(i == y && j == x) continue;
				try {
					if(this.table[i][j] == -1) countBumb++;
				} catch (IndexOutOfBoundsException e) {
					continue;
				}
			}
		}
		this.table[y][x] = countBumb;
	}
	// 全て表示
	private void openAllTiles(MineSweeperGUI gui) {
		// implement
		for(int i = 0; i < this.height; i++) {
			for(int j = 0; j < this.width; j++) {
				if(this.table[i][j] == -1) {
					gui.setTextToTile(j, i, "B");
					gui.setTileColor(j, i, Color.WHITE);
				}
				else {
					gui.setTextToTile(j, i, new Integer(this.table[i][j]).toString());
					gui.setTileColor(j, i, Color.WHITE);
				}
			}
		}
	}

}
