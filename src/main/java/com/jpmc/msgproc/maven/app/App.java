package com.jpmc.msgproc.maven.app;

import org.apache.log4j.Logger;

import com.jpmc.msgproc.sender.MessageSender;
import com.jpmc.msgproc.sender.MessageSenderImpl;

public class App {

	static final Logger logger = Logger.getLogger(App.class);

	public static void main(String[] args) {
		MessageSender msgSenderImpl = new MessageSenderImpl();
		msgSenderImpl.canSendMessage();
	}
}
