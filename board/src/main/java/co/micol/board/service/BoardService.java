package co.micol.board.service;

import java.util.List;

public interface BoardService {
    List<BoardVO> BoardSelectListAll();
    
	public int BoardInsert(BoardVO vo);
	
	public BoardVO BoardSelect(BoardVO vo);

	public int BoardDelete(BoardVO vo);
	
	public int BoardUpdate(BoardVO vo);
}
