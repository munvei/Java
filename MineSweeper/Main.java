import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Dimension;


public class Main extends Frame implements WindowListener,
MineSweeperGUI, ActionListener {

	private MineSweeper ms;
	private Button[][] tileTable;
	private static final Font f = new Font("serif", Font.BOLD,16);
	private final ResultDialog resultDialog;

	public Main() {
		super("MineSweeper");
		try{
			ms = new MineSweeper(9, 9, 10); // size: 9x9 , bomb: 10	
			init();
		} catch(Exception e) {
			System.out.println(e);
		} finally {
			// 追加
			this.resultDialog = new ResultDialog(this, "Result", ms, this);
		}
	}

	public static void main(String[] args) {
		new Main();
	}

	private void init() {
		this.tileTable = new Button[ms.getHeight()][ms.getWidth()];
		this.addWindowListener(this);
		this.setLayout(new GridLayout(ms.getHeight(), ms.getWidth()));
		for (int i = 0; i < ms.getHeight(); i++) {
			for (int j = 0; j < ms.getWidth(); j++) {
				Button tile = new Button();
				tile.setBackground(Color.LIGHT_GRAY);
				tile.setFont(f);
				tile.addMouseListener(new MouseEventHandler(ms, this, j, i));
				tileTable[i][j] = tile;
				this.add(tile);
			}
		}
		this.setSize(50 * ms.getWidth(), 50 * ms.getHeight());

		// 追加
		MenuBar menubar = new MenuBar();
		Menu menu = new Menu("Menu");
		MenuItem mi1 = new MenuItem("Retry");
		MenuItem mi2 = new MenuItem("Exit");
		mi1.addActionListener(this);
		mi2.addActionListener(this);
		menu.add(mi1);
		menu.add(mi2);
		menubar.add(menu);
		this.setMenuBar(menubar);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		// オブジェクトで分岐
		if(e.getActionCommand() == "Retry") {
			ms.reset(this);
		} else if(e.getActionCommand() == "Exit") {
			System.exit(0);
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {

	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}

	@Override
	public void windowClosed(WindowEvent e) {

	}

	@Override
	public void windowIconified(WindowEvent e) {

	}

	@Override
	public void windowDeiconified(WindowEvent e) {

	}

	@Override
	public void windowActivated(WindowEvent e) {

	}

	@Override
	public void windowDeactivated(WindowEvent e) {

	}

	@Override
	public void setTextToTile(int x, int y, String text) {
		this.tileTable[y][x].setLabel(text);
	}
	// 追加したメソッド
	@Override
	public void setTileColor(int x, int y, Color c) {
		this.tileTable[y][x].setBackground(c);
	}

	@Override
	public void win() {
		resultDialog.showDialog("Win !!!");
	}

	@Override
	public void lose() {
		resultDialog.showDialog("Lose ...");
	}
}




class MouseEventHandler implements MouseListener {

	MineSweeper ms;
	MineSweeperGUI msgui;
	int x, y;

	MouseEventHandler(MineSweeper ms, MineSweeperGUI msgui, int x, int y) {
		this.ms = ms;
		this.msgui = msgui;
		this.x = x;
		this.y = y;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch (e.getButton()) {
		case MouseEvent.BUTTON1: {
			// マウスの左ボタンが押された時
			ms.openTile(x, y, msgui);
		} break;
		case MouseEvent.BUTTON2: {
			// マウスの真ん中のボタンが押された時
		} break;
		case MouseEvent.BUTTON3: {
			// マウスの右ボタンが押された時
			ms.setFlag(x, y, msgui);
		} break;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

}


class ResultDialog extends Dialog {

	Label label;
	Button btn1;
	Button btn2;

	public ResultDialog(Frame owner, String title,
			MineSweeper ms, MineSweeperGUI msgui) {
		// モーダルダイアログ化
		super(owner, title, true);
		setLayout(new GridLayout(2, 2));
		Panel p1 = new Panel();
		label = new Label();
		p1.add(label);
		this.add(p1);
		this.setSize(200, 100);
		// 追加部分
		btn1 = new Button();
		btn1.setPreferredSize(new Dimension(70, 20));
		btn1.setLabel("Retry");
		btn1.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				ms.reset(msgui);
				setVisible(false);
			}
			@Override
			public void mousePressed(MouseEvent e) {

			}
			@Override
			public void mouseReleased(MouseEvent e) {

			}
			@Override
			public void mouseEntered(MouseEvent e) {

			}
			@Override
			public void mouseExited(MouseEvent e) {

			}
		});

		btn2 = new Button();
		btn2.setPreferredSize(new Dimension(70, 20));
		btn2.setLabel("Exit");
		btn2.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
			@Override
			public void mousePressed(MouseEvent e) {

			}
			@Override
			public void mouseReleased(MouseEvent e) {

			}
			@Override
			public void mouseEntered(MouseEvent e) {

			}
			@Override
			public void mouseExited(MouseEvent e) {

			}
		});
		Panel p2 = new Panel();
		p2.add(btn1);
		p2.add(btn2);
		this.add(p2);
	}

	public void showDialog(String message) {
		Panel p1 = new Panel();
		this.label.setText(message);
		this.setVisible(true);
	}
}
