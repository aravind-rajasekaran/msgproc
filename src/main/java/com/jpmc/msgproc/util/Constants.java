package com.jpmc.msgproc.util;

public class Constants {

	private Constants() {
		throw new IllegalStateException("Constant class");
	}

	public static final int INTEGER_ZERO = 0;

	public static final int INTEGER_ONE = 1;

	public static final int INTEGER_TWO = 2;

	public static final int INTEGER_THREE = 3;

	public static final int INTEGER_FIVE = 5;

	public static final int INTEGER_TEN = 10;

	public static final int INTEGER_FIFTY = 50;

	public static final String COLON = ":";

	public static final String PENCE = "p";

	public static final String SPACE_STRING = " ";

	public static final String SALE_MSG_SINGLE = "at";

	public static final String MATH_ADD = "add";

	public static final String MATH_SUB = "subtract";

	public static final String MATH_MUL = "multiply";

	public static final String SALE_MSG_MANY = "sales";

	public static final String TOTAL_VALUE = "total value: ";

	public static final String TOTAL_QUANTITY = "total quantity: ";

	public static final String TEN_MESSAGES_LOCATION = "src/main/resources/report-10.txt";

	public static final String MESSAGES_LOCATION = "src/main/resources/report.txt";

	public static final String GT50_MESSAGES_LOCATION = "src/main/resources/report-gt50.txt";
}
