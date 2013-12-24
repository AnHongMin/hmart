package com.hmart.common.util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExp {

	public static void main(String[] args) {

		/**
			1) ^ : 문자열의 시작
			2) $ : 문자열의 종료
			3) . : 임의의 한 문자 (문자의 종류와 관계없음)
			4) | : or의 의미임
			5) ? : 앞 문자가 없거나 하나 있을때
			6) + : 앞 문자가 하나 이상임 (최소 한개 이상)
			7) * : 앞 문자가 없을 수도 있고, 무한정 많을 수도 있음
			8) [] : 문자 클래스의 지정, 문자의 집합이나 범위를 나태내면 - 기호를 사용한다. [] 내에서 ^를 사용하면 not의 의미임
			9) {} : 횟수나 범위를 나타냄. 예를 들어 k{5}의 경우 k가 5번 반복되는 경우임, a{3,5}는 a가 3번 이상 5번 이하 반복되는 경우임
			10) \w : 알파벳이나 숫자
			11) \W : \w의 not. 즉 알파벳이나 숫자를 제외한 문자
			12) \d : [0-9]와 동일
			13) \D : 숫자를 제외한 모든 문자
			
			
			1) 숫자만 : ^[0-9]*$
			2) 영문자만 : ^[a-zA-Z]*$
			3) 한글만 : ^[가-힣]*$
			4) 영어 & 숫자만 : ^[a-zA-Z0-9]*$
			5) E-Mail : ^[a-zA-Z0-9]+@[a-zA-Z0-9]+$
			6) 휴대폰 : ^01(?:0|1|[6-9]) - (?:\d{3}|\d{4}) - \d{4}$
			7) 일반전화 : ^\d{2.3} - \d{3,4} - \d{4}$
			8) 주민등록번호 : \d{6} \- [1-4]\d{6}
			9) IP 주소 : ([0-9]{1,3}) \. ([0-9]{1,3}) \. ([0-9]{1,3}) \. ([0-9]{1,3})
		 */
		String strInput = "a";
		boolean flag = Pattern.matches("^[0-9]*$", strInput);
		System.out.println(flag);

		Pattern p = Pattern.compile("(a*)(b)");
		Matcher m = p.matcher("aaaaab");

		if (m.matches()) {
			for (int i = 0; i < m.groupCount() + 1; i++) {
				System.out.println(i + ":" + m.group(i));
			}
		} else {
			System.out.println("not match!");
		}

		boolean b = Pattern.matches("a*b", "aaaaab");

		System.out.println("b : " + b);

		/*
		 * result> 0:aaaaab 1:aaaaa 2:b 0번째는 매칭된 부분.
		 */

		String a = "I love H Her";
		System.out.println(a.replaceAll("([A-Z])", "\"$1\""));

		/*
		 * result> "I" love her 자바도 $1을 쓸 수 있다.
		 */

		p = Pattern.compile("cat");
		m = p.matcher("one cat two cats in the yard");
		StringBuffer sb = new StringBuffer();

		while (m.find()) {
			m.appendReplacement(sb, "dog");
			System.out.println("1 : " + sb.toString());
		}

		m.appendTail(sb);
		System.out.println("2 : " + sb.toString());

		/*
		 * result> one dog one dog two dog one dog two dogs in the yard
		 */
	}
}
