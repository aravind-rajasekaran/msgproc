package com.jpmc.msgproc.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

import com.jpmc.msgproc.sender.MessageSenderImpl;
import com.jpmc.msgproc.util.Constants;

import junit.framework.TestCase;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MessageSenderImplTest extends TestCase {

	@Rule
	public final ExpectedSystemExit exit = ExpectedSystemExit.none();

	static MessageSenderImpl msgSender;

	@BeforeClass
	public static void setInstances() {
		msgSender = new MessageSenderImpl();
	}

	@Test
	public void sendMessageTest() { // input message file with 50 messages
		assertEquals(true, msgSender.canSendMessage());
	}

	@Test(expected = NullPointerException.class) // input message file has to be removed to achieve this
	public void sendMessageTestFail() {
		boolean isMsgProcessed = false;
		Scanner scanner = null;
		msgSender.hasMsgProcessed(isMsgProcessed, scanner);
	}

	@Test // input message file with 10 messages
	public void sendMessageTestForTenMessages() {
		boolean isMsgProcessed = false;
		Scanner scanner = null;
		try {
			scanner = new Scanner(new FileInputStream(Constants.TEN_MESSAGES_LOCATION));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		assertEquals(true, msgSender.hasMsgProcessed(isMsgProcessed, scanner));
	}

	@Test // input message file with 51 messages
	public void sendMessageTestFailForFiftyOnethMessage() {
		boolean isMsgProcessed = false;
		Scanner scanner = null;
		try {
			scanner = new Scanner(new FileInputStream(Constants.GT50_MESSAGES_LOCATION));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		exit.expectSystemExit();
		msgSender.hasMsgProcessed(isMsgProcessed, scanner);
	}
}