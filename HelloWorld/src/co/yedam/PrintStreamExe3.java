package co.yedam;

import java.util.Scanner;

public class PrintStreamExe3 {
	public static void main(String[] args) {
		//사용자 3개 숫자(정수) 입력
		// max 계산, 평균계산 최대값은 90이고 평균은 80입니다.
		
	    Scanner scn = new Scanner(System.in);
        int maxValue = 0, sum=0;
        double average = 0;
        		
		System.out.print("값 입력>>");
		String num1 = scn.nextLine();
		
		System.out.print("값 입력>>");
		String num2 = scn.nextLine();
		
		System.out.print("값 입력>>");
		String num3 = scn.nextLine();
		
		average = (Integer.parseInt(num1) + Integer.parseInt(num2) + Integer.parseInt(num3)) /3;
		
		
		if(Integer.parseInt(num1) > maxValue) {
			maxValue = Integer.parseInt(num1);
		}
		if(Integer.parseInt(num2) > maxValue) {
			maxValue = Integer.parseInt(num2);
		}
		if(Integer.parseInt(num3) > maxValue) {
			maxValue = Integer.parseInt(num3);
		}

		System.out.println("최대값은" + maxValue+"이고 평균운"+average+"입니다.");
		
		
	}
}