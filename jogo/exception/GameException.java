package jogo.exception;

public class GameException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public GameException(String msg) {
		super(msg);
	}
}
