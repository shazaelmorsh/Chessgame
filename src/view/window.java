package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.ItemSelectable;
import java.awt.Label;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.ListCellRenderer;
import javax.swing.RootPaneContainer;

import exceptions.InvalidPowerTargetException;
import exceptions.InvalidPowerUseException;
import exceptions.OccupiedCellException;
import exceptions.PowerAlreadyUsedException;
import exceptions.UnallowedMovementException;
import exceptions.WrongTurnException;
import model.game.Direction;
import model.game.Game;
import model.game.Player;
import model.pieces.Piece;
import model.pieces.heroes.ActivatablePowerHero;
import model.pieces.heroes.Armored;
import model.pieces.heroes.Hero;
import model.pieces.heroes.Medic;
import model.pieces.heroes.Ranged;
import model.pieces.heroes.Super;
import model.pieces.heroes.Tech;
import model.pieces.sidekicks.SideKick;

public class window extends JFrame implements ActionListener {
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	JToolBar tool = new JToolBar();
	JPanel board;
	JPanel d = new JPanel(new GridLayout(3, 3));
	JButton[][] buttons;
	JPanel payload1 = new JPanel(new GridLayout(6, 1));
	JPanel payload2 = new JPanel(new GridLayout(6, 1));
	JPanel info1;
	JLabel infoL;
	Game g;
	JButton move;
	JButton upright;
	JButton upleft;
	JButton up;
	JButton down;
	JButton right;
	JButton left;
	JButton downleft;
	JButton downright;
	boolean wait1;
	boolean wait2;
	boolean isMedic;
	boolean isMedic2;
	boolean isMedic3;
	boolean waitsuper=false;
	boolean waitRanged=false;
	Piece p1DeadSelected;

	JLabel currentPlayer;
	// Piece a;
	JComboBox<Piece> p1dead = new JComboBox<Piece>();
	JComboBox<Piece> p2dead = new JComboBox<Piece>();
	Direction d1;
	int a, b;
	int x, y;
	Piece temp;
	boolean wait = false;

	public window() {
		String player1 = JOptionPane.showInputDialog(null, "insert name 1");
		String player2 = JOptionPane.showInputDialog(null, "insert name 2");
		Player p1 = new Player(player1);
		Player p2 = new Player(player2);
		g = new Game(p1, p2);
		Dimension DimMax = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setMaximumSize(DimMax);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.getContentPane().setBackground(new Color(81,54, 0));
		frame.setVisible(true);
		frame.setLayout(null);
		frame.setResizable(false);

		
		// WindowDestroyer myListener = new WindowDestroyer();
		// frame.addWindowListener(myListener);
		

		JPanel board = new JPanel();
		board.setLayout(new GridLayout(7, 6));
		board.setBounds(500,50, 800,800);

		buttons = new JButton[7][6];
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 6; j++) {
				if (g.getCellAt(i, j).getPiece() != null) {
					buttons[i][j] = new JButton(new ImageIcon(this.getClass().getResource(photo(g.getCellAt(i,j).getPiece().getName()))));
				} else {
					buttons[i][j] = new JButton();
				}
				buttons[i][j].addActionListener(this);
				buttons[i][j].setBackground(new Color(142,216,110));
				board.add(buttons[i][j]);
			}
		}

		upleft = new JButton("↖");
		d.add(upleft);
		upleft.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				d1 = Direction.UPLEFT;
				if(waitsuper==true) {
	                try {
						((Super) g.getCellAt(x,y).getPiece()).usePower(d1,null,null);
						waitsuper=false;
					} catch (InvalidPowerUseException e1) {
						JOptionPane.showMessageDialog(frame, "Power cannot be used,Try again :(");
						e1.printStackTrace();
					} catch (WrongTurnException e1) {
						JOptionPane.showMessageDialog(frame, "Not your turn !");
						e1.printStackTrace();
					}
					updateboard();
	}
		if(waitRanged==true) {
			try {
				((Ranged) g.getCellAt(x,y).getPiece()).usePower(d1,null,null);
			} catch (InvalidPowerUseException e1) {
				JOptionPane.showMessageDialog(frame, "Power cannot be used,Try again :(");
				e1.printStackTrace();
			} catch (WrongTurnException e1) {
				JOptionPane.showMessageDialog(frame, "Not your turn !");
				e1.printStackTrace();
			}waitRanged=false;
			updateboard();
			}
				if(isMedic){
					isMedic2 = true;
					JOptionPane.showMessageDialog(frame, "choose a dead friend to revive");
				}
				}
		});
		up = new JButton("^");
		up.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				d1 = Direction.UP;
				if(waitsuper==true) {
	                try {
						((Super) g.getCellAt(x,y).getPiece()).usePower(d1,null,null);
						waitsuper=false;
					} catch (InvalidPowerUseException e1) {
						JOptionPane.showMessageDialog(frame, "Power cannot be used,Try again :(");
						e1.printStackTrace();
					} catch (WrongTurnException e1) {
						JOptionPane.showMessageDialog(frame, "Not your turn !");
						e1.printStackTrace();
					}
					updateboard();
	}
		if(waitRanged==true) {
			try {
				((Ranged) g.getCellAt(x,y).getPiece()).usePower(d1,null,null);
			} catch (InvalidPowerUseException e1) {
				JOptionPane.showMessageDialog(frame, "Power cannot be used,Try again :(");
				e1.printStackTrace();
			} catch (WrongTurnException e1) {
				JOptionPane.showMessageDialog(frame, "Not your turn !");
				e1.printStackTrace();
			}waitRanged=false;
			updateboard();
			}
				if(isMedic){
					isMedic2 = true;
					JOptionPane.showMessageDialog(frame, "choose a dead friend to revive");
				}
			}
		});
		d.add(up);

		upright = new JButton("↗");
		upright.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				d1 = Direction.UPRIGHT;
				if(waitsuper==true) {
	                try {
						((Super) g.getCellAt(x,y).getPiece()).usePower(d1,null,null);
						waitsuper=false;
					} catch (InvalidPowerUseException e1) {
						JOptionPane.showMessageDialog(frame, "Power cannot be used,Try again :(");
						e1.printStackTrace();
					} catch (WrongTurnException e1) {
						JOptionPane.showMessageDialog(frame, "Not your turn !");
						e1.printStackTrace();
					}
					updateboard();
	}
		if(waitRanged==true) {
			try {
				((Ranged) g.getCellAt(x,y).getPiece()).usePower(d1,null,null);
			} catch (InvalidPowerUseException e1) {
				JOptionPane.showMessageDialog(frame, "Power cannot be used,Try again :(");
				e1.printStackTrace();
			} catch (WrongTurnException e1) {
				JOptionPane.showMessageDialog(frame, "Not your turn !");
				e1.printStackTrace();
			}waitRanged=false;
			updateboard();
			}
				if(isMedic){
					isMedic2 = true;
					JOptionPane.showMessageDialog(frame, "choose a dead friend to revive");
				}
			}
		});
		d.add(upright);

		left = new JButton("<");
		left.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				d1 = Direction.LEFT;
				if(waitsuper==true) {
	                try {
						((Super) g.getCellAt(x,y).getPiece()).usePower(d1,null,null);
						waitsuper=false;
					} catch (InvalidPowerUseException e1) {
						JOptionPane.showMessageDialog(frame, "Power cannot be used,Try again :(");
						e1.printStackTrace();
					} catch (WrongTurnException e1) {
						JOptionPane.showMessageDialog(frame, "Not your turn !");
						e1.printStackTrace();
					}
					updateboard();
	}
		if(waitRanged==true) {
			try {
				((Ranged) g.getCellAt(x,y).getPiece()).usePower(d1,null,null);
			} catch (InvalidPowerUseException e1) {
				JOptionPane.showMessageDialog(frame, "Power cannot be used,Try again :(");
				e1.printStackTrace();
			} catch (WrongTurnException e1) {
				JOptionPane.showMessageDialog(frame, "Not your turn !");
				e1.printStackTrace();
			}waitRanged=false;
			updateboard();
			}
				if(isMedic){
					isMedic2 = true;
					JOptionPane.showMessageDialog(frame, "choose a dead friend to revive");
				}
			}
		});
		d.add(left);
		JLabel p=new JLabel();
		p.setBackground(new Color(81,54, 0));
		d.add(p);
		right = new JButton(">");
		right.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				d1 = Direction.RIGHT;
				if(waitsuper==true) {
	                try {
						((Super) g.getCellAt(x,y).getPiece()).usePower(d1,null,null);
						waitsuper=false;
					} catch (InvalidPowerUseException e1) {
						JOptionPane.showMessageDialog(frame, "Power cannot be used,Try again :(");
						e1.printStackTrace();
					} catch (WrongTurnException e1) {
						JOptionPane.showMessageDialog(frame, "Not your turn !");
						e1.printStackTrace();
					}
					updateboard();
	}
		if(waitRanged==true) {
			try {
				((Ranged) g.getCellAt(x,y).getPiece()).usePower(d1,null,null);
			} catch (InvalidPowerUseException e1) {
				JOptionPane.showMessageDialog(frame, "Power cannot be used,Try again :(");
				e1.printStackTrace();
			} catch (WrongTurnException e1) {
				JOptionPane.showMessageDialog(frame, "Not your turn !");
				e1.printStackTrace();
			}waitRanged=false;
			updateboard();
			}
				if(isMedic){
					isMedic2 = true;
					JOptionPane.showMessageDialog(frame, "choose a dead friend to revive");
				}
			}
		});
		d.add(right);

		downleft = new JButton("↙");
		downleft.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				d1 = Direction.DOWNLEFT;
				if(waitsuper==true) {
	                try {
						((Super) g.getCellAt(x,y).getPiece()).usePower(d1,null,null);
						waitsuper=false;
					} catch (InvalidPowerUseException e1) {
						JOptionPane.showMessageDialog(frame, "Power cannot be used,Try again :(");
						e1.printStackTrace();
					} catch (WrongTurnException e1) {
						JOptionPane.showMessageDialog(frame, "Not your turn !");
						e1.printStackTrace();
					}
					updateboard();
	}
		if(waitRanged==true) {
			try {
				((Ranged) g.getCellAt(x,y).getPiece()).usePower(d1,null,null);
			} catch (InvalidPowerUseException e1) {
				JOptionPane.showMessageDialog(frame, "Power cannot be used,Try again :(");
				e1.printStackTrace();
			} catch (WrongTurnException e1) {
				JOptionPane.showMessageDialog(frame, "Not your turn !");
				e1.printStackTrace();
			}waitRanged=false;
			updateboard();
			}
				if(isMedic){
					isMedic2 = true;
					JOptionPane.showMessageDialog(frame, "choose a dead friend to revive");
				}

			}
		});

		d.add(downleft);

		down = new JButton("↓");
		down.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				d1 = Direction.DOWN;
				if(waitsuper==true) {
	                try {
						((Super) g.getCellAt(x,y).getPiece()).usePower(d1,null,null);
						waitsuper=false;
					} catch (InvalidPowerUseException e1) {
						JOptionPane.showMessageDialog(frame, "Power cannot be used,Try again :(");
						e1.printStackTrace();
					} catch (WrongTurnException e1) {
						JOptionPane.showMessageDialog(frame, "Not your turn !");
						e1.printStackTrace();
					}
					updateboard();
	}
		if(waitRanged==true) {
			try {
				((Ranged) g.getCellAt(x,y).getPiece()).usePower(d1,null,null);
			} catch (InvalidPowerUseException e1) {
				JOptionPane.showMessageDialog(frame, "Power cannot be used,Try again :(");
				e1.printStackTrace();
			} catch (WrongTurnException e1) {
				JOptionPane.showMessageDialog(frame, "Not your turn !");
				e1.printStackTrace();
			}waitRanged=false;
			updateboard();
			}
				if(isMedic){
					isMedic2 = true;
					JOptionPane.showMessageDialog(frame, "choose a dead friend to revive");
	
				}
			}
		});

		d.add(down);

		downright = new JButton("↘");
		downright.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				d1 = Direction.DOWNRIGHT;
				if(waitsuper==true) {
	                try {
						((Super) g.getCellAt(x,y).getPiece()).usePower(d1,null,null);
						waitsuper=false;
					} catch (InvalidPowerUseException e1) {
						JOptionPane.showMessageDialog(frame, "Power cannot be used,Try again :(");
						e1.printStackTrace();
					} catch (WrongTurnException e1) {
						JOptionPane.showMessageDialog(frame, "Not your turn !");
						e1.printStackTrace();
					}
					updateboard();
	}
		if(waitRanged==true) {
			try {
				((Ranged) g.getCellAt(x,y).getPiece()).usePower(d1,null,null);
			} catch (InvalidPowerUseException e1) {
				JOptionPane.showMessageDialog(frame, "Power cannot be used,Try again :(");
				e1.printStackTrace();
			} catch (WrongTurnException e1) {
				JOptionPane.showMessageDialog(frame, "Not your turn !");
				e1.printStackTrace();
			}waitRanged=false;
			updateboard();
			}
				if(isMedic){
					isMedic2 = true;
					JOptionPane.showMessageDialog(frame, "choose a dead friend to revive");

				}
			}
		});
		d.add(downright);

		tool.add(p1dead);
		tool.addSeparator();
		tool.add(new JSeparator(1));
		panel.add(tool);
		tool.addSeparator();
		JButton usepower = new JButton("use power");
		tool.add(usepower);
		tool.addSeparator();
		currentPlayer = new JLabel(g.getCurrentPlayer().getName());
		currentPlayer.setFont(currentPlayer.getFont().deriveFont(64.0f));
		currentPlayer.setForeground(new Color(185,188,181));
		tool.add(currentPlayer);
		tool.addSeparator();
		tool.setBackground(new Color(81,54, 0));
		JButton move = new JButton("move");

		usepower.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				waitsuper=wait=wait1=wait2=waitRanged=isMedic=isMedic2=isMedic3=false;
				// TODO Auto-generated method stub
				g.getCellAt(x, y).getPiece().setPosI(x);
				g.getCellAt(x, y).getPiece().setPosJ(y);
				if(g.getCellAt(x,y).getPiece() instanceof Super) {
					waitsuper=true;
					JOptionPane.showMessageDialog(frame,"choose a direction");
				}
				if(g.getCellAt(x,y).getPiece() instanceof Ranged) {
					waitRanged=true;
					
					JOptionPane.showMessageDialog(frame,"choose a direction");
				}
				if (g.getCellAt(x, y).getPiece() instanceof Tech) {
					a = x;
					b = y;
					Object[] options = { "Hack an enemy", "Restore an ability",
							"move a friendly piece" };
					int selected = JOptionPane.showOptionDialog(frame,
							"what power you want to use", "POWER",
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.DEFAULT_OPTION, null, options,
							options[2]);
					if (selected == 0) {
						wait = true;
						JOptionPane.showMessageDialog(frame, "Select the enemy");
					} else if (selected == 1) {
						wait = true;
						JOptionPane.showMessageDialog(frame, "choose a hero");
					} else if (selected == 2) {
						wait1 = true;
						JOptionPane.showMessageDialog(frame, "choose a friend");

					}
				}
				
				if (g.getCellAt(x, y).getPiece() instanceof Medic){
					JOptionPane.showMessageDialog(frame, "choose a direction");
					isMedic = true;
				}
				
				currentPlayer.setText(g.getCurrentPlayer().getName());
			}
		});
		
		ItemListener itemListener = new ItemListener() {
		      public void itemStateChanged(ItemEvent itemEvent) {
		        int state = itemEvent.getStateChange();
		        p1DeadSelected =(Piece) itemEvent.getItem();
		        isMedic3 = true;
					
				System.out.println(x+ " " +y);
		        System.out.println((state == ItemEvent.SELECTED) ? "Selected" : "Deselected");
		        System.out.println("Item: " + itemEvent.getItem());
		        ItemSelectable is = itemEvent.getItemSelectable();
		        System.out.println(", Selected: " + selectedString(is));
		        
		        if(isMedic2 && isMedic && isMedic3){
		        	g.getCellAt(x, y).getPiece().setPosI(x);
		        	g.getCellAt(x, y).getPiece().setPosJ(y);
					try {
						try {
							((Medic) g.getCellAt(x, y).getPiece()).usePower(d1,(Piece) p1DeadSelected, null);
						} catch (WrongTurnException e) {
							JOptionPane.showMessageDialog(frame, "Not your turn !");
							e.printStackTrace();
						}
						Point destination = g.getCellAt(x, y).getPiece().getDirectionPos(new Point(x, y),
								d1);
						buttons[destination.x][destination.y].setIcon(new ImageIcon(this.getClass().getResource(photo((g
								.getCellAt(temp.getPosI(), temp.getPosJ()).getPiece().getName())))));
						
					} catch (InvalidPowerUseException  e) {
						JOptionPane.showMessageDialog(frame, "cannot revive this piece !");
						e.printStackTrace();
					}
					isMedic = false; isMedic2 = false;isMedic3 = false;
					currentPlayer.setText(g.getCurrentPlayer().getName());
					updateComboBox2();
					updateComboBox1();
		        }
		      }
		    };
		    p1dead.addItemListener(itemListener);
		    p2dead.addItemListener(itemListener);

		tool.add(move);
		tool.addSeparator();
		tool.add(d);
		tool.add(new JSeparator(1));
		tool.add(p2dead);
		tool.setBounds(305,850, 1200,150);
		tool.setFloatable(false);
		// tool.setBackground(new Color(76,5,18));
		move.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (d1 != null && !g.getCellAt(x, y).equals(null)) {
					try {
						a = x;
						b = y;
						temp = null;
						g.getCellAt(x,y).getPiece().setPosI(x);
						g.getCellAt(x,y).getPiece().setPosJ(y);
						temp = g.getCellAt(x,y).getPiece();
						temp.move(d1);
						updateboard();
						currentPlayer.setText(g.getCurrentPlayer().getName());
						updatepayload1();
						updatepayload2();
						updateComboBox2();
						updateComboBox1();
						frame.validate();
						frame.repaint();
						checkwinner1();

					} catch (UnallowedMovementException e) {
						JOptionPane.showMessageDialog(frame, "Unallowed Movement,choose different direction");
						e.printStackTrace();
					} catch (OccupiedCellException e) {
						JOptionPane.showMessageDialog(frame, "You cannot move to an occupied cell");
						e.printStackTrace();
					} catch (WrongTurnException e) {
						JOptionPane.showMessageDialog(frame, "Not your turn !");
						e.printStackTrace();
					}
					
				}
				
			}
		});

		payload1.setBounds(150, 300,100,500);
		payload2.setBounds(1560, 300,100,500);

		info1 = new JPanel();
		infoL = new JLabel();
		info1.add(infoL);
		infoL.setBounds(10, 10, 130, 180);
		info1.setBackground(Color.WHITE);
		info1.setBounds(50, 50, 150, 200);
		info1.setLayout(null);
		frame.getContentPane().add(info1);
		frame.getContentPane().add(payload2);
		frame.getContentPane().add(payload1);
		frame.getContentPane().add(board);
		frame.getContentPane().add(tool);
		frame.validate();
		frame.repaint();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	private void updatepayload1() {
		payload1.removeAll();
		if (g != null)
			for (int i = 0; i < g.getPlayer1().getPayloadPos(); i++) {
				JPanel p = new JPanel();
				p.setBackground(Color.GREEN);
				if(i==g.getPlayer1().getPayloadPos()-1) {
					JButton n=new JButton (new ImageIcon(this.getClass().getResource("Payload.png")));	
					p.add(n);}
				payload1.add(p);
			
	}}

	private void updatepayload2() {
		payload2.removeAll();
		if (g != null)
			for (int i = 0; i < g.getPlayer2().getPayloadPos(); i++) {
				JPanel p = new JPanel();
				p.setBackground(Color.GREEN);
				if(i==g.getPlayer2().getPayloadPos()-1) {
					JButton n=new JButton (new ImageIcon(this.getClass().getResource("Payload.png")));	
					p.add(n);}
				payload2.add(p);
			
	}}
	public void updateboard() {
		for(int i=0;i<7;i++){
	    	for(int j=0;j<6;j++){
	    		if(g.getCellAt(i,j).getPiece()!=null) {
	    			buttons[i][j].setIcon(new ImageIcon(this.getClass().getResource(photo((g.getCellAt(i,j).getPiece().getName())))));}
	    		else {
	    		buttons[i][j].setIcon(new ImageIcon(this.getClass().getResource("")));
	    	}
	    }
	}}

	public void updateComboBox1() {
		p1dead.removeAllItems();
		p1dead.addItem(null);
		if (g != null)
			for (int i = 0; i < g.getPlayer1().getDeadCharacters().size(); i++) {
				p1dead.addItem(g.getPlayer1().getDeadCharacters().get(i));
			}
	}
	public void updateComboBox2() {
		p2dead.removeAllItems();
		p2dead.addItem(null);
		if (g != null)
			for (Piece dead : g.getPlayer2().getDeadCharacters()) {
				p2dead.addItem(dead);
			}
	}
	private String photo(String a) {
		if(a.contains("Sidekick1")){
			return ("Sidekick.png");}
		else if(a.contains("Sidekick2")){
			return ("Sidekick1.png");}
			else if (a.contains("Armord")) {
				return ("Armored.png");}
			else if (a.contains("Medic")) {
				return ("Medic.png");}
			else if (a.contains("Tech")) {
				return ("Tech.png");}
			else if (a.contains("Speedster")) {
				return ("Speedster.png");}
			else if (a.contains("Ranged")) {
				return ("Ranged.png");}
			else {
				return ("Super.png");}
			
			}
		
		
	
	
	public void checkwinner1() {

		if(g.getPlayer1().getPayloadPos()==6) {
			JOptionPane.showMessageDialog(frame,"The winner is"+g.getPlayer1().getName());
			 System.exit(0); 
		}
		else if(g.getPlayer2().getPayloadPos()==6) {
			JOptionPane.showMessageDialog(frame,"The winner is"+g.getPlayer2().getName());
			 System.exit(0); 
		}
		}


	public static void main(String[] args) {
		new window();
	}
	
	private void printInfo(int i, int j) {
		String arm = "No";
		if (g.getCellAt(i, j).getPiece() == null)
			infoL.setText("");
		else {
			if (g.getCellAt(i, j).getPiece() instanceof Armored && ((Armored)g.getCellAt(i, j).getPiece()).isArmorUp())
				arm = "Yes";
			if (g.getCellAt(i, j).getPiece() instanceof ActivatablePowerHero) {
				infoL.setText("<html>Piece Name: "
						+ g.getCellAt(i, j).getPiece().getName()
						+ "<br/>Owner: "
						+ g.getCellAt(i, j).getPiece().getOwner()
								.getName()
						+ "<br/>Power up: "
						+ ((ActivatablePowerHero) g.getCellAt(i, j)
								.getPiece()).isPowerUsed()
						+ "<br/>Has Armor: " + arm + "</html>");
			} else {
				infoL.setText("<html>Piece Name: "
						+ g.getCellAt(i, j).getPiece().getName()
						+ "<br/>Owner: "
						+ g.getCellAt(i, j).getPiece().getOwner()
								.getName() + "<br/>Power up: "
						+ "non Activable Power Hero"
						+ "<br/>Has Armor: " + arm + "</html>");
			}
		}
	}
	
	static private Piece selectedString(ItemSelectable is) {
	    Object selected[] = is.getSelectedObjects();
	    return ((selected.length == 0) ? null : (Piece) selected[0]);
	  }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		buttons[x][y].setBackground(new Color(103, 193, 98));
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 6; j++) {
				if (arg0.getSource() == buttons[i][j]) {
					
					x = i;
					y = j;
					buttons[i][j].setBackground(new Color(103, 250, 98));
					if (wait == true) { //Hack power
						try {
							((Tech) g.getCellAt(a, b).getPiece()).usePower(
									null, g.getCellAt(x, y).getPiece(), null);
							wait = false;
						} catch (InvalidPowerUseException e) {
							JOptionPane.showMessageDialog(frame, "Power cannot be used,Try again :(");
							e.printStackTrace();
						} catch (WrongTurnException e) {
							JOptionPane.showMessageDialog(frame, "Not your turn !");
							e.printStackTrace();
						}
					}
					if (wait2 == true) { // Teleport
						try {
							Point p = new Point(x, y);
							((Tech) g.getCellAt(a, b).getPiece()).usePower(
									null, temp, p);
							buttons[temp.getPosI()][temp.getPosJ()].setText(g
									.getCellAt(temp.getPosI(), temp.getPosJ())
									.getPiece().getName());
							wait1 = false;
							wait2 = false;
							updateboard();

						} catch (InvalidPowerUseException e) {
							JOptionPane.showMessageDialog(frame, "Power cannot be used,Try again :(");
							e.printStackTrace();
						} catch (WrongTurnException e) {
							JOptionPane.showMessageDialog(frame, "Not your turn !");
							e.printStackTrace();
						}
					}
					if (wait1 == true) {
						temp = g.getCellAt(x, y).getPiece();
						buttons[x][y].setText("");
						JOptionPane.showMessageDialog(frame,
								"choose empty place");
						wait2 = true;
					}
					
					
					
					printInfo(i,j);
				}
			}
		}
		
	}
}

