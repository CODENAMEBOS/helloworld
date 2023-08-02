package co.micol.board.ServiceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.micol.board.db.DataSource;
import co.micol.board.service.BoardService;
import co.micol.board.service.BoardVO;

public class ServiceImpl implements BoardService {
	private DataSource dao = DataSource.getInstance();
	private Connection conn;
	private PreparedStatement psmt;
	private ResultSet rs;

	private void close() {
		try {
			if (rs != null)
				rs.close();
			if (psmt != null)
				psmt.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<BoardVO> BoardSelectListAll() {
		String sql = "SELECT * FROM BOARD";
		List<BoardVO> boards = new ArrayList<BoardVO>();
		BoardVO vo;
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();

			while (rs.next()) {
				vo = new BoardVO();
				vo.setBoardNum(rs.getInt("board_num"));
				vo.setBoardTitle(rs.getString("board_title"));
				vo.setBoardMain(rs.getString("board_main"));
				vo.setBoardWriter(rs.getString("board_writer"));
				vo.setBoardWriteDate(rs.getString("board_writedate"));
				boards.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return boards;
	}

	@Override
	public int BoardInsert(BoardVO vo) {
	    int n = 0;
	    String sql = "INSERT INTO BOARD VALUES (?, ?, ?, ?, ?)";
	    try {
	        conn = dao.getConnection();

	        // 글 번호 생성 로직
	        String maxBoardNumSql = "SELECT MAX(BOARD_NUM) FROM BOARD";
	        psmt = conn.prepareStatement(maxBoardNumSql);
	        ResultSet rs = psmt.executeQuery();
	        int maxBoardNum = 0;
	        if (rs.next()) {
	            maxBoardNum = rs.getInt(1);
	        }
	        int newBoardNum = maxBoardNum + 1;

	        psmt = conn.prepareStatement(sql);
	        psmt.setInt(1, newBoardNum);
	        psmt.setString(2, vo.getBoardTitle());
	        psmt.setString(3, vo.getBoardMain());
	        psmt.setString(4, vo.getBoardWriter());
	        psmt.setString(5, vo.getBoardWriteDate());
	        n = psmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close();
	    }
	    return n;
	}

	@Override
	public BoardVO BoardSelect(BoardVO vo) {
		String sql = "SELECT * FROM board WHERE BOARD_NUM = ?";
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, vo.getBoardNum());
			rs = psmt.executeQuery();
			if (rs.next()) {
				vo = new BoardVO();
				vo.setBoardNum(rs.getInt("board_num"));
				vo.setBoardTitle(rs.getString("board_title"));
				vo.setBoardMain(rs.getString("board_main"));
				vo.setBoardWriter(rs.getString("board_writer"));
				vo.setBoardWriteDate(rs.getString("board_writedate"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return vo;
	}

	@Override
	public int BoardDelete(BoardVO vo) {
		int n = 0;
		String sql = "DELETE FROM BOARD WHERE BOARD_NUM = ?";
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, vo.getBoardNum());
			n = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return n;
	}

	@Override
	public int BoardUpdate(BoardVO vo) {
		String sql = "UPDATE BOARD SET BOARD_TITLE = ?, BOARD_MAIN = ? WHERE BOARD_NUM = ?";
		int n = 0;
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, vo.getBoardTitle());
			psmt.setString(2, vo.getBoardMain());
			psmt.setInt(3, vo.getBoardNum());
			n = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return n;
	}

}
