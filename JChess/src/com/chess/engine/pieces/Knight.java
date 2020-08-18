package com.chess.engine.pieces;
import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;
import com.chess.engine.board.Move.*;
import com.chess.engine.pieces.Piece.PieceType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Knight extends Piece {
	
	private static int[] CANDIDATE_MOVE_COORDINATES= {-17,-15,-10,-6,6,10,15,17};
	
	public Knight(final Alliance pieceAlliance,final int piecePosition) {
		super(PieceType.KNIGHT,piecePosition, pieceAlliance,true);
		// TODO Auto-generated constructor stub
	}
	public Knight(final Alliance pieceAlliance,final int piecePosition,final boolean isFirstMove) {
		super(PieceType.KNIGHT,piecePosition, pieceAlliance,isFirstMove);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board){
		
	
		final List<Move> legalMoves=new ArrayList<>();
		for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {
			final int candidateDestinationCoordinate=this.piecePosition+currentCandidateOffset;
			if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
				if(isFirstColumnExclusion(this.piecePosition,currentCandidateOffset) || 
				   isSecondColoumnExclusion(this.piecePosition,currentCandidateOffset) ||
				   isSeventhColoumnExclusion(this.piecePosition,currentCandidateOffset) ||
				   isEigthColoumnExclusion(this.piecePosition,currentCandidateOffset)) {
					continue;
				}
				
			final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
			if(!candidateDestinationTile.isTileOccupied()) {
				legalMoves.add(new MajorMove(board,this,candidateDestinationCoordinate));}
			else {
				final Piece pieceAtDestination = candidateDestinationTile.getPiece();
				final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
				if(this.pieceAlliance!=pieceAlliance) {
					legalMoves.add(new Move.MajorAttackMove(board,this,candidateDestinationCoordinate,pieceAtDestination));
				}
			}
		}
		
		}
		return ImmutableList.copyOf(legalMoves);
	}

	@Override
	public String toString() {
		return PieceType.KNIGHT.toString();
	}
	@Override
	public Knight movePiece(final Move move) {
		// TODO Auto-generated method stub
		return new Knight(move.getMovedPiece().getPieceAlliance(),move.getDestinationCoordinate());
	}
	
	
	
	private static boolean isFirstColumnExclusion(final int currentPosition,final int candidateOffset) {
		
		return BoardUtils.FIRST_COLOUMN[currentPosition] && ((candidateOffset == -17) || candidateOffset == -10 
				|| candidateOffset == 6 || candidateOffset == 15);		
	}
	
	private static boolean isSecondColoumnExclusion(final int currentPosition, final int candidateOffset ) {
		return BoardUtils.SECOND_COLOUMN[currentPosition] && ((candidateOffset == -10) || candidateOffset == 6);
	}
	private static boolean isSeventhColoumnExclusion(final int currentPosition, final int candidateOffset ) {
		return BoardUtils.SEVENTH_COLOUMN[currentPosition] && (candidateOffset == -6 || candidateOffset == 10);
	}
	private static boolean isEigthColoumnExclusion(final int currentPosition, final int candidateOffset ) {
		return BoardUtils.EIGHTH_COLOUMN[currentPosition] && (candidateOffset == -15 || candidateOffset == -6
				|| candidateOffset == 10 || candidateOffset == 17);
	}
	
}
