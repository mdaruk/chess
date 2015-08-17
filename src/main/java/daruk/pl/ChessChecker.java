package daruk.pl;

public class ChessChecker {

	public ChessChecker() {
	}
	
	/*
	 * returns true if figure standing on (xm,xn) is threatened position (tm,tn)
	 */
	public static boolean testFigure(final byte figure, final byte xm, final byte xn, final byte tm, final byte tn) {
		if(figure == (byte)2) { // Queen
			return xm == tm || xn == tn || (xm - tm)*(xm - tm) == (xn - tn)*(xn - tn);
		}else if(figure == (byte)4) { // Rook
			return xm == tm || xn == tn;
		}else if(figure == (byte)3) { // Bishop
			return (xm - tm)*(xm - tm) == (xn - tn)*(xn - tn);
		}else if(figure == (byte)5) { // Knight
			return (xm == tm-2 || xm == tm+2)&& (xn == tn-1 || xn == tn+1)
					||(xm == tm-1 || xm == tm+1)&& (xn == tn-2 || xn == tn+2);
		} else if(figure == (byte)1) { // King
			return (xm == tm || xm == tm+1 || xm == tm-1) && (xn == tn || xn == tn+1 || xn == tn-1);
		}else return false;
	}
	
	/*
	 * returns true if figure f can be placed on board on position (m,n)
	 * board contains figures described in board array, each in 3 bytes, first is kind
	 * 2nd m, 3rd n
	 */
	public static boolean isSafeForFigure(final byte[] board, final byte f, final byte m, final byte n, final byte placedFiguresQty) {
		boolean isPlaceSave = true;
		for(byte b = 0; b < placedFiguresQty;b++) {
			//checking if new figure is threatened by one on board
			//checking if one on the board is threatened new figure
			if(testFigure(board[b*3], board[b*3+1], board[b*3+2], m, n) 
			 ||testFigure(f, m, n, board[b*3+1], board[b*3+2])
					) { 
				isPlaceSave = false;
				break;
			}
		}
		return isPlaceSave;
	}
	
	/*
	 * removes one figure from figures from position index
	 */
	public static byte[] removeFigure(final byte[] figures, final byte index) {
		final boolean removeColumn = figures[index*2+1] == 1; 
	
		final byte[] dsc = new byte[(removeColumn? figures.length-2 : figures.length )];
	
		if(removeColumn) {
			System.arraycopy(figures,0,dsc,0,index*2);
			System.arraycopy(figures,index*2+2,dsc,index*2,figures.length-2 - index*2);
		} else {
			System.arraycopy(figures,0,dsc,0,figures.length);
			dsc[index*2+1] = (byte) (figures[index*2+1] - 1);
		}
		return dsc;
	}
}
