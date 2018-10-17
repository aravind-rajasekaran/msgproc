package com.jpmc.msgproc.sender;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.jpmc.msgproc.exception.SystemException;
import com.jpmc.msgproc.receiver.MessageReceiver;
import com.jpmc.msgproc.receiver.MessageReceiverImpl;
import com.jpmc.msgproc.util.Constants;

/**
 * @author Aravind Rajasekaran
 * @version 1.0
 * @since 1.0
 */
public class MessageSenderImpl implements MessageSender {

	private MessageReceiver messageReceiver;
	static final Logger logger = Logger.getLogger(MessageSenderImpl.class);

	public MessageSenderImpl() {
		this.messageReceiver = new MessageReceiverImpl();
	}

	/**
	 * send message to be processed from the file.
	 * 
	 * @param no
	 *            parameter.
	 * 
	 * @return boolean true when there is no exception.
	 */
	public boolean canSendMessage() {
		boolean isMessageProcessed = false;
		try {
			InputStream stream = new FileInputStream(Constants.MESSAGES_LOCATION);
			Scanner scanner = new Scanner(stream);
			isMessageProcessed = hasMsgProcessed(isMessageProcessed, scanner);
			scanner.close();
			stream.close();
		} catch (NullPointerException | IOException ex) {
			throw new SystemException("No input resource were found to send message from");
		}
		return isMessageProcessed;
	}

	public boolean hasMsgProcessed(boolean isMessageProcessed, Scanner scanner) {
		boolean isMsgProcessed = isMessageProcessed;
		while (scanner.hasNextLine()) {
			String message = scanner.nextLine();
			isMsgProcessed = messageReceiver.hasReceivedMsg(message);
		}
		return isMsgProcessed;
	}
}