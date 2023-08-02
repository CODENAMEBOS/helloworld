package co.micol.board.service;

import lombok.Data;

@Data
public class BoardVO {
	private Integer boardNum;
	private String BoardTitle;
	private String BoardMain;
	private String BoardWriter;
	private String BoardWriteDate;

	public BoardVO() {

	}

	@Override
	public String toString() {
		System.out.print(boardNum + " ");
		System.out.print(BoardTitle + " ");
		System.out.print("   작성자 : " + BoardWriter + " ");
		System.out.println("작성시간 : " + BoardWriteDate + " ");
		System.out.println("-----------------------------------------");
		return null;
	}

	public BoardVO(int boardNum, String boardTitle, String boardMain, String boardWriter, String boardWriteDate) {
		this.boardNum = boardNum;
		this.BoardTitle = boardTitle;
		this.BoardMain = boardMain;
		this.BoardWriter = boardWriter;
		this.BoardWriteDate = boardWriteDate;
	}

}
