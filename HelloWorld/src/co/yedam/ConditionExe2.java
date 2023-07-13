package co.yedam;

import java.util.Scanner;

public class ConditionExe2 {
	public static void main(String[] args) {
	
		Scanner scn = new Scanner(System.in);
		while(true) {
			System.out.println("메뉴를 선택하세요(숫자를 입력>>");
			String menu = scn.nextLine();
			
			//switch, if eldeif else
		}
		
	//	if(result )
		
		System.out.println("end of prog");
		
	} //end of main
	
	public static void login() {
		System.out.println("로그인 메뉴입니다.");
	}
	public static void logout() {
		System.out.println("로그아웃 메뉴입니다.");
	}
	public static void signup() {
		System.out.println("회원가입 메뉴입니다.");
	}
	
}
