package co.micol.board.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import co.micol.board.ServiceImpl.ServiceImpl;
import co.micol.board.db.DataSource;
import co.micol.board.service.BoardVO;

public class MainMenu {
	private Scanner sc = new Scanner(System.in);
	private ServiceImpl bs = new ServiceImpl();
	private String loggedInUser = null;
	private String loggedInPw = null;
	private Connection conn;
	private PreparedStatement psmt;
	private ResultSet rs;
	private DataSource dao = DataSource.getInstance();

	private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String DB_USER = "c##micol";
	private static final String DB_PASSWORD = "1234";

	private boolean LoginMenu(String username, String password) {
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			String sql = "SELECT * FROM users WHERE user_id = ?";
			try (PreparedStatement psmt = conn.prepareStatement(sql)) {
				psmt.setString(1, username);
				try (ResultSet rs = psmt.executeQuery()) {
					if (rs.next()) {
						String dbUsername = rs.getString("user_id");
						String dbPassword = rs.getString("user_pw");
						return password.equals(dbPassword);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void run() {
	    boolean loginSuccess = false;

	    do {
	        System.out.println("<로그인을 실행해주세요.>");
	        System.out.println("아이디를 입력하세요.");
	        String username = sc.nextLine();
	        System.out.println("비밀번호를 입력하세요.");
	        String password = sc.nextLine();

	        if (LoginMenu(username, password)) {
	            System.out.println("<로그인에 성공했습니다.>");
	            loggedInUser = username;
	            loggedInPw = password;
	            loginSuccess = true;
	        } else {
	            System.out.println("<로그인에 실패했습니다. 아이디와 비밀번호를 확인하세요.>");
	            System.out.println("<다시 로그인하시겠습니까? (Y/N)>");
	            String choice = sc.nextLine();
	            if (!choice.equalsIgnoreCase("Y")) {
	                break;
	            }
	        }
	    } while (!loginSuccess);

	    if (loginSuccess) {
	        boolean b = false;
	        do {
	            displayMainMenu();
	            int key = sc.nextInt();
	            switch (key) {
	                case 1:
	                    BoardInsert();
	                    break;
	                case 2:
	                    BoardUpdate();
	                    break;
	                case 3:
	                    BoardDelete();
	                    break;
	                case 4:
	                    BoardSelect();
	                    break;
	                case 5:
	                    BoardSelectListAll();
	                    break;
	                case 6:
	                    System.out.println("<작업을 종료합니다.>");
	                    b = true;
	                    break;
	            }
	        } while (!b);
	    }

	    sc.close();
	}

	private void displayMainMenu() {
		System.out.println("===   자유 게시판   ===");
		System.out.println("===   1. 글 쓰기   ===");
		System.out.println("===   2. 글 수정   ===");
		System.out.println("===   3. 글 삭제   ===");
		System.out.println("=== 4. 글 단건조회  ===");
		System.out.println("=== 5. 전체글 조회  ===");
		System.out.println("===  6. 작업 종료  ===");
		System.out.println("====================");
	}

	private void BoardSelectListAll() { // 게시물 전체 조회
		System.out.println("<게시판의 전체 글을 조회합니다.>");
		List<BoardVO> boards = new ArrayList<BoardVO>();
		boards = bs.BoardSelectListAll();
		if (!boards.isEmpty()) {
			for (BoardVO board : boards) {
				board.toString();
			}
		} else {
			System.out.println("<등록된 글이 없습니다.>");
		}
	}

	private void BoardSelect() { // 게시물 단건 조회
		System.out.println("<조회하려는 게시물의 번호를 입력하세요.>");
		int code = sc.nextInt();

		BoardVO boardvo = new BoardVO();
		boardvo.setBoardNum(code);
		boardvo = bs.BoardSelect(boardvo);

		if (boardvo.getBoardNum() != null) {
			System.out.print(boardvo.getBoardNum());
			System.out.println("제목 : " + boardvo.getBoardTitle());
			System.out.println(boardvo.getBoardMain());
			System.out.print("작성자: " + boardvo.getBoardWriter());
			System.out.println("  작성일자: " + boardvo.getBoardWriteDate());
			System.out.println("===============================");
		} else {
			System.out.println("<존재하지 않는 게시물입니다.>");
		}
	}

	private void BoardDelete() {
		System.out.println("<삭제할 게시물의 번호를 입력하세요.>");
		int code = sc.nextInt();
		sc.nextLine();

		BoardVO boardvo = new BoardVO();
		boardvo.setBoardNum(code);

		boardvo = bs.BoardSelect(boardvo);

		// 작성자와 로그인한 사용자의 아이디 비교
		if (boardvo.getBoardWriter().equals(loggedInUser)) {
			int deleteCount = bs.BoardDelete(boardvo);
			if (deleteCount > 0) {
				System.out.println("<글이 삭제되었습니다.>");
			} else {
				System.out.println("<글 삭제가 실패하였습니다. 게시물 번호를 확인해주세요.>");
			}
		} else {
			System.out.println("<작성자만 해당 글을 삭제할 수 있습니다.>");
		}
	}

	private boolean checkPassword() {
		System.out.println("<비밀번호를 입력하세요.>");
		String password = sc.nextLine();
		return password.equals(loggedInPw); // 로그인한 사용자의 비밀번호와 입력한 비밀번호를 비교
	}

	private void BoardInsert() {
	    int num = 0;
	    System.out.println("글 제목을 입력하세요.");
	    sc.nextLine();
	    String title = sc.nextLine();

	    System.out.println("글 내용을 입력하세요.");
	    String main = sc.nextLine();

	    String writer = loggedInUser;

	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String writedate = sdf.format(new Date());

	    BoardVO boardvo = new BoardVO(num, title, main, writer, writedate);
	    int boards = bs.BoardInsert(boardvo);

	    if (boards != 0) {
	        System.out.println("글 등록이 완료되었습니다.");
	    } else {
	        System.out.println("글 등록이 실패하였습니다.");
	    }
	}

	private int getNextBoardNum() {
		String sql = "SELECT board_seq.NEXTVAL FROM dual";
		int nextNum = 0;
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			if (rs.next()) {
				nextNum = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return nextNum;
	}

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

	private void BoardUpdate() {
	    BoardVO boardvo = new BoardVO();
	    System.out.println("<수정이 필요한 글의 번호를 입력하세요.>");
	    int code = sc.nextInt();
	    boardvo.setBoardNum(code);

	    // sc.nextLine() 추가
	    sc.nextLine();

	    // 글 번호에 해당하는 게시물 정보 가져오기
	    BoardVO originalBoard = bs.BoardSelect(boardvo);
	    if (originalBoard == null) {
	        System.out.println("<해당 글 번호의 게시물이 존재하지 않습니다.>");
	        return;
	    }

	    // 수정 권한 확인
	    if (loggedInUser.equals(originalBoard.getBoardWriter())) {
	        String choice;
	        System.out.println("<제목을 수정하시겠습니까? (Y/N)>");
	        while (true) {
	            choice = sc.nextLine();
	            if (choice.equalsIgnoreCase("Y") || choice.equalsIgnoreCase("N")) {
	                break;
	            } else {
	                System.out.println("<Y 또는 N을 입력해주세요. (Y/N)>");
	            }
	        }
	        if (choice.equalsIgnoreCase("Y")) {
	            System.out.println("<제목을 입력하세요.>");
	            String title = sc.nextLine();
	            boardvo.setBoardTitle(title);
	        }

	        System.out.println("<내용을 수정하시겠습니까? (Y/N)>");
	        while (true) {
	            choice = sc.nextLine();
	            if (choice.equalsIgnoreCase("Y") || choice.equalsIgnoreCase("N")) {
	                break;
	            } else {
	                System.out.println("<Y 또는 N을 입력해주세요. (Y/N)>");
	            }
	        }
	        if (choice.equalsIgnoreCase("Y")) {
	            System.out.println("<내용을 입력하세요.>");
	            String main = sc.nextLine();
	            boardvo.setBoardMain(main);
	        }

	        int boards = bs.BoardUpdate(boardvo);
	        if (boards != 0) {
	            System.out.println("<수정 완료>");
	        } else {
	            System.out.println("<수정 실패>");
	        }
	    } else {
	        System.out.println("<권한이 없습니다. 글 수정을 취소합니다.>");
	    }
	}



}
