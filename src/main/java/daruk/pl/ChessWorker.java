package daruk.pl;

public class ChessWorker implements Runnable{
	final private byte figuresCount;
	final private ChessChallenge parent;
	private byte mMax;
	private byte nMax;
	
	final private byte placedFiguresQty = 1;
	final private byte tm;
	final private byte tn;
	final private byte board[];
	final private byte figures[];

	final private StringBuilder combinations = new StringBuilder();
	
	private int combinationsCount = 0;
	
	public ChessWorker(byte[] board, byte[] figures, byte mMax,
			byte nMax, byte figuresCount,
			ChessChallenge parent, byte tm, byte tn) {
		this.board = board;
		this.figures = figures;
		this.mMax = mMax;
		this.nMax = nMax;
		this.figuresCount = figuresCount;
		this.parent = parent;
		this.tm = tm;
		this.tn = tn;
	}


	

	
	private void resolve(final byte board[], final byte figures[], final byte placedFiguresQty, final byte startM, final byte startN) {
		for(byte f = 0; f < figures.length/2;f++) { // choose figure kind	
			for(byte tm = startM; tm < mMax; tm++) {//choose m
				byte sn = 0;
				if(tm == startM) sn = startN;
				
				for(byte tn = sn; tn < nMax; tn++) {//choose n
					//check
					if(ChessChecker.isSafeForFigure(board, figures[f*2], tm, tn, placedFiguresQty)) {
						if(placedFiguresQty + 1 == figuresCount) {

							for(int b = 0; b < board.length;b++) {
								if(b % 3 == 0) {
									combinations.append(ChessChallenge.TRANSLATE_FIGURE[board[b]]);
								} else {
									combinations.append(board[b]);
								}
							}
							
							combinations.append(ChessChallenge.TRANSLATE_FIGURE[figures[f*2]]);
							combinations.append(tm);
							combinations.append(tn);
							combinations.append("\n");
							
							combinationsCount++;

						} else {
							//place figure and go on
							final byte tmpBoard[] = new byte[board.length+3];
							System.arraycopy(board,0,tmpBoard,0,board.length);	
							tmpBoard[3*placedFiguresQty]   = figures[f*2];
							tmpBoard[3*placedFiguresQty+1] = tm;
							tmpBoard[3*placedFiguresQty+2] = tn;
						
							final byte tmpFigures[] =  ChessChecker.removeFigure(figures, f);
							
							if(figures[f*2] == 2 || figures[f*2] == 4) {
								resolve(tmpBoard, tmpFigures, (byte)(placedFiguresQty+1), (byte)(tm+1), (byte)0);
								
							} else if(figures[f*2] == 1){
								resolve(tmpBoard, tmpFigures, (byte)(placedFiguresQty+1), (byte)tm, (byte)(tn+2));
								
							} else {
								resolve(tmpBoard, tmpFigures, (byte)(placedFiguresQty+1), (byte)tm, (byte)(tn+1));
								
							}
						}
					}//if test
				}
			}
		}	
	}

	public void run() {
		resolve(board, figures, placedFiguresQty, tm, tn);
		synchronized(parent) {
			ChessChallenge.addCombinations(combinations.toString(), combinationsCount);
		}
	}
}
