package daruk.pl;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestChessChecker {



	@Test
	public void testKing() {
		byte[][] chessBoard = 	{{0,0,0,0,0,0,0},
							  	 {0,1,1,1,0,0,0},
							  	 {0,1,1,1,0,0,0},
							  	 {0,1,1,1,0,0,0},
							  	 {0,0,0,0,0,0,0}};
		byte m = 2;
		byte n = 2;
		byte f = 1; //King
		for(byte i = 0 ; i < chessBoard.length; i++) {
			for(byte j = 0; j < chessBoard[0].length; j++) {
				assertTrue("Wrong ("+i+","+j+")", (chessBoard[i][j] == 1) == ChessChecker.testFigure(f, m, n, i, j));
			}
		}		
	}
	
	@Test
	public void testQueen() {
		byte[][] chessBoard = 	{{1,0,1,0,1,0,0},
							  	 {0,1,1,1,0,0,0},
							  	 {1,1,1,1,1,1,1},
							  	 {0,1,1,1,0,0,0},
							  	 {1,0,1,0,1,0,0}};
		byte m = 2;
		byte n = 2;
		byte f = 2; //Queen
		for(byte i = 0 ; i < chessBoard.length; i++) {
			for(byte j = 0; j < chessBoard[0].length; j++) {
				assertTrue("Wrong ("+i+","+j+")", (chessBoard[i][j] == 1) == ChessChecker.testFigure(f, m, n, i, j));
			}
		}	
	}
	@Test
	public void testBishop() {
		byte[][] chessBoard = 	{{1,0,0,0,1,0,0},
							  	 {0,1,0,1,0,0,0},
							  	 {0,0,1,0,0,0,0},
							  	 {0,1,0,1,0,0,0},
							  	 {1,0,0,0,1,0,0}};
		byte m = 2;
		byte n = 2;
		byte f = 3; //Bishop
		for(byte i = 0 ; i < chessBoard.length; i++) {
			for(byte j = 0; j < chessBoard[0].length; j++) {
				assertTrue("Wrong ("+i+","+j+")", (chessBoard[i][j] == 1) == ChessChecker.testFigure(f, m, n, i, j));
			}
		}	
	}
	@Test
	public void testRook() {
		byte[][] chessBoard = 	{{0,0,1,0,0,0,0},
							  	 {0,0,1,0,0,0,0},
							  	 {1,1,1,1,1,1,1},
							  	 {0,0,1,0,0,0,0},
							  	 {0,0,1,0,0,0,0}};
		byte m = 2;
		byte n = 2;
		byte f = 4; //Rook
		for(byte i = 0 ; i < chessBoard.length; i++) {
			for(byte j = 0; j < chessBoard[0].length; j++) {
				assertTrue("Wrong ("+i+","+j+")", (chessBoard[i][j] == 1) == ChessChecker.testFigure(f, m, n, i, j));
			}
		}	
	}
	@Test
	public void testKnight() {
		byte[][] chessBoard = 	{{0,0,0,0,0,0,0},
							  	 {0,0,1,0,1,0,0},
							  	 {0,1,0,0,0,1,0},
							  	 {0,0,0,0,0,0,0}, // not checking Knight position, loop iterates the way two figures cannot be in the same position
							  	 {0,1,0,0,0,1,0}, // it is not issue
							  	 {0,0,1,0,1,0,0},
							  	 {0,0,0,0,0,0,0}};
		byte m = 3;
		byte n = 3;
		byte f = 5; //Knight
		for(byte i = 0 ; i < chessBoard.length; i++) {
			for(byte j = 0; j < chessBoard[0].length; j++) {
				assertTrue("Wrong ("+i+","+j+")", (chessBoard[i][j] == 1) == ChessChecker.testFigure(f, m, n, i, j));
			}
		}	
	}	

	
	@Test
	public void testBoard() {
		byte[] board = {1,0,0}; // King on (0,0)
		/*
		byte[][] chessBoard = 	
			    {{K,1,0,0,0,0,0},
			  	 {1,1,0,0,0,0,0},
			  	 {0,0,X,0,0,0,0}, // only Rook & Queen cannot be placed on X
			  	 {0,0,0,0,0,0,0},
			  	 {0,0,0,0,0,0,0}};
		*/
		byte m = 3;
		byte n = 3;
		
		//checking position 3,3 for all kind of figures
		byte f = 5; //Knigh
		assertTrue("I can place figure here", ChessChecker.isSafeForFigure(board, f, m, n, (byte)1));
		f = 1; // King
		assertTrue("I can place King here", ChessChecker.isSafeForFigure(board, f, m, n, (byte)1));
		f = 2; // Queen
		assertFalse("I can not place Queen here", ChessChecker.isSafeForFigure(board, f, m, n, (byte)1));
		f = 3; //Bishop
		assertFalse("I can not place Bishop here", ChessChecker.isSafeForFigure(board, f, m, n, (byte)1));
		f = 4; //Rook
		assertTrue("I can place Rook here", ChessChecker.isSafeForFigure(board, f, m, n, (byte)1));
		
	}	
	@Test
	public void testBoard2() {
		
		byte figuresPlaced = 2;
		
		byte[] board = {1,0,0,2,3,2}; // King on (0,0), Queen(3,2)
		
		/*
		byte[][] chessBoard = 	
			    {{K,1,1,0,0,1,0},
			  	 {1,1,1,0,1,0,0},
			  	 {0,1,1,1,0,0,0}, 
			  	 {1,1,Q,1,1,1,1},
			  	 {0,1,1,1,0,0,0},
			  	 {1,0,1,0,1,X,0}  //cannot place Queen & Bishop on X because of king
			  	 };
		*/
		
		
		byte m = 5;
		byte n = 5;
		
		//checking position 3,3 for all kind of figures
		byte f = 5; //Knigh
		assertTrue("I can place figure here", ChessChecker.isSafeForFigure(board, f, m, n, figuresPlaced));
		f = 1; // King
		assertTrue("I can place King here", ChessChecker.isSafeForFigure(board, f, m, n, figuresPlaced));
		f = 2; // Queen
		assertFalse("I can not place Queen here", ChessChecker.isSafeForFigure(board, f, m, n, figuresPlaced));
		f = 3; //Bishop
		assertFalse("I can not place Bishop here", ChessChecker.isSafeForFigure(board, f, m, n, figuresPlaced));
		f = 4; //Rook
		assertTrue("I can place Rook here", ChessChecker.isSafeForFigure(board, f, m, n, figuresPlaced));
		/*
		byte[][] chessBoard = 	
			    {{K,1,1,0,0,1,0},
			  	 {1,1,1,0,1,0,0},
			  	 {0,1,1,1,0,0,0}, 
			  	 {1,1,Q,1,1,1,1},
			  	 {0,1,1,1,X,0,0},
			  	 {1,0,1,0,1,0,0}  //cannot place Knight on X because of Queen
			  	 };              //cannot place Queen & Bishop on X because of king
		*/
		m = 4;
		n = 4;
		
		//checking position 3,3 for all kind of figures
		f = 5; //Knigh
		assertFalse("I can not place figure here", ChessChecker.isSafeForFigure(board, f, m, n, figuresPlaced));
		f = 1; // King
		assertTrue("I can place King here", ChessChecker.isSafeForFigure(board, f, m, n, figuresPlaced));
		f = 2; // Queen
		assertFalse("I can not place Queen here", ChessChecker.isSafeForFigure(board, f, m, n, figuresPlaced));
		f = 3; //Bishop
		assertFalse("I can not place Bishop here", ChessChecker.isSafeForFigure(board, f, m, n, figuresPlaced));
		f = 4; //Rook
		assertTrue("I can place Rook here", ChessChecker.isSafeForFigure(board, f, m, n, figuresPlaced));
	}	

	

	
	

}
