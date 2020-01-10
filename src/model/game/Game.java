package model.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import model.pieces.Piece;
import model.pieces.heroes.Armored;
import model.pieces.heroes.Medic;
import model.pieces.heroes.Ranged;
import model.pieces.heroes.Speedster;
import model.pieces.heroes.Super;
import model.pieces.heroes.Tech;
import model.pieces.sidekicks.SideKickP1;
import model.pieces.sidekicks.SideKickP2;

public class Game {

	private final int payloadPosTarget = 6;
	private final int boardWidth = 6;

	private final int boardHeight = 7;

	private final int p1InitRow = boardHeight - 3;

	private final int p2InitRow = 1;

	private Player player1;
	private Player player2;

	private Player currentPlayer;

	private Cell[][] board;

	public Cell getCellAt(int i, int j) {
		return board[i][j];
	}

	public void setPieceAt(int i, int j, Piece p) {
		board[i][j].setPiece(p);
	}

	public Game(Player player1, Player player2) {
		super();
		this.player1 = player1;
		this.player2 = player2;

		board = new Cell[boardHeight][boardWidth];
		assemblePieces();
		currentPlayer = player1;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void switchTurns() {
		currentPlayer = (currentPlayer == player1) ? player2 : player1;
	}

	public int getPayloadPosTarget() {
		return payloadPosTarget;
	}

	public void assemblePieces(){

		Object[]a={new Armored(player2,this,"Armord2"),new Medic(player2,this,"Medic2"),new Tech(player2,this,"Tech2"),new Speedster(player2,this,"Speedster2"),new Super(player2,this,"Super2"),new Ranged(player2,this,"Ranged2")};
		Collections.shuffle(Arrays.asList(a));
		Object[]b={new Armored(player1,this,"Armord1"),new Medic(player1,this,"Medic1"),new Tech(player1,this,"Tech1"),new Speedster(player1,this,"Speedster1"),new Super(player1,this,"Super1"),new Ranged(player1,this,"Ranged1")};
		Collections.shuffle(Arrays.asList(b));
		for(int i=0;i<6;i++){
			board[1][i]=new Cell((Piece) a[i]);
			((Piece) a[i]).setPosJ(i);
			((Piece) a[i]).setPosI(1);
			board[5][i]=new Cell((Piece)b[i]);
			((Piece) b[i]).setPosJ(i);
			((Piece) b[i]).setPosI(5);
			Piece x=new SideKickP2(this,"Sidekick2");
			board[2][i]=new Cell(x);
			((Piece)x).setPosJ(i);
			((Piece)x).setPosI(2);
			Piece y=new SideKickP1(this,"Sidekick1");
			board[4][i]=new Cell(y);
			((Piece)y).setPosJ(i);
			((Piece)y).setPosI(4);
			

			board[0][i]=new Cell();
			board[3][i]=new Cell();
			board[6][i]=new Cell();
		
		}
		
	}

	public boolean checkWinner() {
		if (currentPlayer.getPayloadPos() == payloadPosTarget) {
			return true;
		}

		return false;
	}

	@Override
	public String toString() {
		String s = "";
		System.out.println("      " + getPlayer2().getName());
		System.out.print("| ");
		for (int i = 0; i < board[0].length; i++)
			System.out.print("--");
		System.out.println("|");
		for (int i = 0; i < board.length; i++) {
			System.out.print("| ");
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] == null)
					System.out.print("n ");
				else
					System.out.print(board[i][j] + " ");
			}
			System.out.println("|");
		}
		System.out.print("| ");
		for (int i = 0; i < board[0].length; i++)
			System.out.print("--");
		System.out.println("|");
		System.out.println("    " + getPlayer1().getName());
		return s;
	}

	public int getBoardWidth() {
		return boardWidth;
	}

	public int getBoardHeight() {
		return boardHeight;
	}

}
