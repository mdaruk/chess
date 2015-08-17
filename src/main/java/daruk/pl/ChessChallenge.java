package daruk.pl;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Class ChessChallenge iterates through all possible combinations
 * of placing figures on chess board.
 * It is counting and saving all the combinations
 * where figures are not threatened by others
 * @author Mariusz Daruk
 */
public final class ChessChallenge {
	
	final static String TRANSLATE_FIGURE[] = {"","K","Q","B","R","N"};
	
	// m size of chess board
	private final byte mMax;
	
	// n size of chess board
	private final byte nMax;

	private Thread threads[];
	
	final private byte figures[];
	 
	private final byte figuresCount;
	
	private static StringBuilder goodCombinations;
	
	public static int allCombinationCount;
	
	/*
	 * Add combinations. Workers use this method to pass results
	 */
	public static void addCombinations(final String combinations, final int combinationsCount) {
		goodCombinations.append(combinations);
		
		allCombinationCount += combinationsCount;
	}
	/*
	 * Setup class figure structure from simply constructor using
	 * figures quantity
	 */
	private byte[] getFigures(final byte king, final byte queen, final byte bishop, final byte rook,
			final byte knight) {
		
		// Figures kind quantity
		final int kindQuantity = 
				 (king>0?1:0)
                +(queen>0?1:0)
                +(bishop>0?1:0)
                +(rook>0?1:0)
                +(knight>0?1:0);
		
		// array figures represents available figures to place on chess board
		// two field of array represent one figure and its quantity
		// first field is kind, second is quantity, 
		// next two field represent another figure kind
		// [1,5,2,1] - represents five Kings and one Queen
		// figures kind: 1-King, 2-Queen, 3-Bishop, 4-Rook, 5-Knight
		final byte figures[] = new byte[2 *kindQuantity];
		
		byte i = 0;
		if(king > 0) { // if any Kings available fill two field with 1 as kind and quantity
			figures[i++] = 1;
			figures[i++] = king;
		}
		if(queen > 0) { // if any Queens available fill first free two field with 2 as kind and quantity
			figures[i++] = 2;
			figures[i++] = queen;
		}
		if(bishop > 0) {
			figures[i++] = 3;
			figures[i++] = bishop;
		}
		if(rook > 0) {
			figures[i++] = 4;
			figures[i++] = rook;
		}
		if(knight > 0) {
			figures[i++] = 5;
			figures[i++] = knight;
		}

		return figures;
	}
	/* Constructor using fields
	 * m x n chess board, king is quantity of Kings, queen is quantity of Queens ...
	 * */
	public ChessChallenge(final byte m, final byte n, final byte king, final byte queen, final byte bishop, final byte rook,
			final byte knight) {
		super();
		goodCombinations = new StringBuilder();
		allCombinationCount = 0;
		this.mMax = m;
		this.nMax = n;
		figures = getFigures(king, queen, bishop, rook, knight);
		
		figuresCount = (byte) (king+queen+bishop+rook+knight);
		threads = new Thread[m*n*figures.length]; // that is qty of starting combinations by placing first figure
		
		startThreads();
	}

	// Starting Workers Threads
	private void startThreads() {
		int threadIndex = 0;

			for(byte f = 0; f < figures.length/2;f++) { // choosing figures kind
				for(byte tm = 0; tm < mMax; tm++) {// choosing m row
					for(byte tn = 0; tn < nMax; tn++) {//choosing n column
						
							// board is structure of figures on board
							// one figure takes 2 field: [kind,m,n] 
							final byte board[] = new byte[3];
							board[0] = figures[f*2];
							board[1] = tm;
							board[2] = tn;
							final byte tmpFigures[] =  ChessChecker.removeFigure(figures, f);
	
							ChessWorker worker;
							
							if(figures[f*2] == (byte)2 || figures[f*2] == (byte)4) {
								worker = new ChessWorker(board, tmpFigures, mMax, nMax, figuresCount, this, (byte) (tm+1), (byte) 0);
							} else if(figures[f*2] == (byte)1){
								worker = new ChessWorker(board, tmpFigures, mMax, nMax, figuresCount, this, tm, (byte)(tn+2));
							} else {
								worker = new ChessWorker(board, tmpFigures, mMax, nMax, figuresCount, this, tm, (byte)(tn+1));
							}
							Thread thread = new Thread(worker);
							
							thread.start();
							threads[threadIndex++] = thread;
						
					}
				}
			}	
			
			// Wait for all threads to finish and pass good combinations
			for (final Thread thread : threads) {
				try {
					if(thread != null) {
						thread.join();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}	
			}
	  
	}
	/*
	 * returns all good combinations
	 */
	public static String getAllCombinations() {
		
		return goodCombinations.toString();
	}
	/*
	 * Returns Combinations count
	 */
	public static int getCombinationsCount() {
		  return allCombinationCount;
	}
	
	/*
	 * parameters:
	 * output.filename m n king queen bishop rook knight
	 */
	public static void main(String[] args) {
		
		if(args.length == 8) {
			final byte m = Byte.parseByte(args[1]);
			final byte n = Byte.parseByte(args[2]);
			final byte kings = Byte.parseByte(args[3]);
			final byte queens = Byte.parseByte(args[4]);
			final byte bishops = Byte.parseByte(args[5]);
			final byte rooks = Byte.parseByte(args[6]);
			final byte knights = Byte.parseByte(args[7]);
			final String filename = args[0];
			
			final long start = System.currentTimeMillis();
			
			final ChessChallenge chess = new ChessChallenge(m,n,kings,queens,bishops,rooks,knights);
			
			
			// Display combinations
			Writer writer = null;

			try {
			    writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream(filename), "utf-8"));
			    writer.write(getAllCombinations());
			    final long stop = System.currentTimeMillis();
			    writer.write("Total time: "+ (stop-start)+ " ms solutions count:"+ChessChallenge.getCombinationsCount());
			} catch (IOException ex) {
				System.err.println(ex.getMessage());
			} finally {
			   try {writer.close();} 
			   catch (Exception ex) {ex.printStackTrace();}
			}

			
		} else {
			System.out.println("ChessCHallenge takes as a parameters outputFileName\n"+
					"Chess Board dimentions MxN and quantities of figures:\n"+
					"ChessCHallenge outputFile M N Kings Queens Bishops Rooks Knights\n"+
					"example: ChessCHallenge output.txt 7 7 2 2 2 0 1");
		}
		
	}
}


