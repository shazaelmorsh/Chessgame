package model.pieces.sidekicks;

import model.game.Cell;
import model.game.Game;
import model.game.Player;
import model.pieces.Piece;
import model.pieces.heroes.Armored;
import model.pieces.heroes.Medic;
import model.pieces.heroes.Ranged;
import model.pieces.heroes.Speedster;
import model.pieces.heroes.Super;
import model.pieces.heroes.Tech;

public abstract class SideKick extends Piece {

	public SideKick(Player player, Game game, String name) {
		super(player, game, name);
	}

	public void attack(Piece target) {

		Cell c = getGame().getCellAt(getPosI(), getPosJ());

		if (!(target instanceof SideKick)) {

			if (target instanceof Armored && !((Armored) target).isArmorUp()) {
				c.setPiece(new Armored(getOwner(), getGame(),"Armord"));
			}

			if (target instanceof Medic) {
				c.setPiece(new Medic(getOwner(), getGame(),"Medic"));
			}

			if (target instanceof Ranged) {
				c.setPiece(new Ranged(getOwner(), getGame(),"Ranged"));
			}

			if (target instanceof Speedster) {
				c.setPiece(new Speedster(getOwner(), getGame(),"Speedster"));
			}

			if (target instanceof Super) {
				c.setPiece(new Super(getOwner(), getGame(),"Super"));
			}

			if (target instanceof Tech) {
				c.setPiece(new Tech(getOwner(), getGame(),"Tech"));
			}

		}

		super.attack(target);

	}

	@Override
	public String toString() {
		return "K";
	}
}
