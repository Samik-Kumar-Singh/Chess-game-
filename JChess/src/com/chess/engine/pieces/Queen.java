package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.board.Move.AttackMove;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.pieces.Piece.PieceType;
import com.google.common.collect.ImmutableList;

public class Queen extends Piece {
	
	private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {-9,-8,-7,-1,1,7,8,9 };

	public Queen(final Alliance pieceAlliance,final int piecePosition) {
		super(PieceType.QUEEN,piecePosition, pieceAlliance,true);
		// TODO Auto-generated constructor stub
	}
	public Queen(final Alliance pieceAlliance,final int piecePosition,final boolean isFirstMove) {
		super(PieceType.QUEEN,piecePosition, pieceAlliance,isFirstMove);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
		// TODO Auto-generated method stub
		final List<Move> legalMoves=new ArrayList<>();
		for(final int candidateCoordinateOffset:CANDIDATE_MOVE_VECTOR_COORDINATES){
			int candidateDestinationCoordinate=this.piecePosition;
			while(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				if(isFirstColumnExclusion(candidateDestinationCoordinate,candidateCoordinateOffset) || 
						isEightColumnExclusion(candidateDestinationCoordinate,candidateCoordinateOffset)) {
					break;
				}
				candidateDestinationCoordinate += candidateCoordinateOffset;
				if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
					final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
					if(!candidateDestinationTile.isTileOccupied()) {
						legalMoves.add(new MajorMove(board,this,candidateDestinationCoordinate));}
					else {
						final Piece pieceAtDestination = candidateDestinationTile.getPiece();
						final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
						if(this.pieceAlliance!=pieceAlliance) {
							legalMoves.add(new AttackMove(board,this,candidateDestinationCoordinate,pieceAtDestination));
						}
						break;
					}
				
				}
			}	
		}
		return ImmutableList.copyOf(legalMoves);
	}
	
	@Override
	public String toString() {
		return PieceType.QUEEN.toString();
	}
	@Override
	public Queen movePiece(final Move move) {
		// TODO Auto-generated method stub
		return new Queen(move.getMovedPiece().getPieceAlliance(),move.getDestinationCoordinate());
	}
	
	private static boolean isFirstColumnExclusion(final int currentPosition,final int candidateOffset) {
		return BoardUtils.FIRST_COLOUMN[currentPosition] && (candidateOffset == -1 || candidateOffset == -9 || candidateOffset == 7);
	}
	private static boolean isEightColumnExclusion(final int currentPosition,final int candidateOffset) {
		return BoardUtils.EIGHTH_COLOUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == 7 || candidateOffset == 1);
	}

}
