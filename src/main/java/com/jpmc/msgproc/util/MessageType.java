package com.jpmc.msgproc.util;

public enum MessageType {
	MESSAGE_TYPE_1(1), MESSAGE_TYPE_2(2), MESSAGE_TYPE_3(3);

	private int value;

	MessageType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
