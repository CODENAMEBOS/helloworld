package co.yedam;

public class VariableExe {
	public static void main(String[] args) {
		//정수형.
	//	int num1 = 100;
		System.out.println(Integer.MAX_VALUE);
	//	long num2 = 200; // 8byte
		System.out.println(Long.MAX_VALUE);
		
		
		byte num3 = 30; // -128 ~ 127 (8bit)
		num3 = 126;
	//for(int i=1; 1<10; i++) {
			System.out.println("num3:" + num3++);
		short num4 = 32767; //
		System.out.println(Short.MAX_VALUE*2);
	
	char chr = 97; //(2byte) 65536개의 유니코드
	for (int i=1; i<=26; i++)
		
	System.out.println(chr++); //형변환
	
	
	
	
	
	}
}

