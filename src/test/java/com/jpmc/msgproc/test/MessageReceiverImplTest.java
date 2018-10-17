package com.jpmc.msgproc.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.jpmc.msgproc.dto.AdjustmentDO;
import com.jpmc.msgproc.exception.SystemException;
import com.jpmc.msgproc.receiver.MessageReceiverImpl;

import junit.framework.TestCase;

@RunWith(JUnit4.class)
public class MessageReceiverImplTest extends TestCase {

	static MessageReceiverImpl msgReceiver;

	@BeforeClass
	public static void setInstances() {
		msgReceiver = new MessageReceiverImpl();
	}

	@Test
	public void receiveMessageTest() {
		String message = "orange at 20p";
		assertEquals(true, msgReceiver.hasReceivedMsg(message));
	}

	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void receiveMessageTestFail() {
		String message = " ";
		int msgCount = 1;
		msgReceiver.canProcessMsg(message, msgCount);

	}

	@Test
	public void processMessageOneTest() {
		String message = "doll at 50p";
		int msgCount = 1;
		assertEquals(true, msgReceiver.canProcessMsg(message, msgCount));
	}

	@Test
	public void processMessageTwoTest() {
		String message = "20 sales of pens at 200p each";
		int msgCount = 1;
		assertEquals(true, msgReceiver.canProcessMsg(message, msgCount));
	}

	@Test
	public void processMessageThreeTest() {
		String[] messageArray = { "apple at 10p", "Add 20p apples" };
		int msgCount = 1;
		for (int i = 0; i < messageArray.length; i++) {
			assertEquals(true, msgReceiver.canProcessMsg(messageArray[i], msgCount));
		}
	}

	@Test(expected = SystemException.class)
	public void processMessageThreeTestFail() {
		String message = "Add 20p apples";
		int msgCount = 1;
		msgReceiver.canProcessMsg(message, msgCount);
	}

	@Test
	public void saveAdjustmentMessageTest() {
		String message = "Add 2000p plates";
		String[] messageArray = message.split(" ");
		Map<String, List<AdjustmentDO>> adjustmentMap = new HashMap<>();
		msgReceiver.saveAdjustmentMessage(message, messageArray, adjustmentMap);
		assertEquals(adjustmentMap.get("plate").get(0).getAdjusmentMsg(), "Add 2000p plates");
		assertEquals(adjustmentMap.containsKey("plate"), true);
	}

	@Test
	public void findMessageTypeOneTest() {
		String message = "mobile at 30000p";
		assertEquals(1, msgReceiver.findMessageType(message));
	}

	@Test
	public void findMessageTypeTwoTest() {
		String message = "sales of mobiles at 3000p each";
		assertEquals(2, msgReceiver.findMessageType(message));
	}

	@Test
	public void findMessageTypeThreeTest() {
		String message = "subtract 2p apples";
		assertEquals(3, msgReceiver.findMessageType(message));
	}

}
