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
import com.google.common.collect.ImmutableList;

public class Queen extends Piece{
	private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATE={-9,-8,-7,-1,1,7,8,9};//Queen can move with respect to this coordinate only
	public Queen( Alliance pieceAlliance ,int piecePosition) {
		super(PieceType.QUEEN,  piecePosition, pieceAlliance);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Collection<Move> calculateLegalMove(final Board board) {
		final List<Move> legalMoves=new ArrayList<>();
		for(final int candidateCoordinateOffset:CANDIDATE_MOVE_VECTOR_COORDINATE) //We are going to loop through each of the loop
		{
			int candidateDestinationCoordinate=this.piecePosition;
			while(BoardUtils.isValidTileCoorinate(candidateDestinationCoordinate)){
				if(isFirstColumnExclusion(this.piecePosition,candidateCoordinateOffset)
						   || isEigthColumnExclusion(this.piecePosition,candidateCoordinateOffset)){
							//break;
							continue;
						}
				
				
				
				
				
				candidateDestinationCoordinate+=candidateCoordinateOffset;
				if(BoardUtils.isValidTileCoorinate(candidateDestinationCoordinate)){
					final Tile candidateDestinationTile=board.getTile(candidateDestinationCoordinate);
					 
					if(!candidateDestinationTile.isTileOccupied()) //checking on tile not occupied by some other piece
					 {
						 legalMoves.add(new MajorMove(board,this,candidateDestinationCoordinate)); //Adding new non -attacking  Move
					 }
					else //tile is occupied by something else
					{
						final Piece pieceAtDestination =candidateDestinationTile.getPiece(); // need to get that piece at that location
						final Alliance pieceAlliance=pieceAtDestination.getPieceAlliance(); // need to get Alliance of that piece
			            if(this.pieceAlliance!=pieceAlliance) //it mean it is Enemy 
			            {
			            	legalMoves.add(new AttackMove(board,this,candidateDestinationCoordinate,pieceAtDestination)); //Adding new attacking Move
			            }
			            break; //for example if some knight exist in between it that's why break exist here 
					}
					
				}
			}
			
		}
		return ImmutableList.copyOf(legalMoves);
	}
	public Queen movePiece(Move move) {
		//create new Queen @current update location
		return new Queen(move.getMovedPiece().getPieceAlliance(),move.getDestinationCoordinate());//create new Bishop @ current location
	}
	@Override
	public String toString()
	{
		return PieceType.QUEEN.toString();
	}
	private static boolean isFirstColumnExclusion(final int currentPosition,final int candidateOffset){
		return BoardUtils.FIRST_COLUMN[currentPosition] 
				&& (candidateOffset==-9 ||candidateOffset==-1|| candidateOffset==7) ;
	}
	private static boolean isEigthColumnExclusion(final int currentPosition,final int candidateOffset){
		return BoardUtils.EIGTH_COLUMN[currentPosition] 
				&& ( candidateOffset==-7 ||candidateOffset==1|| candidateOffset==9 ) ;

 }
}
