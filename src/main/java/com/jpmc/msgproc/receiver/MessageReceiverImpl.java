package com.jpmc.msgproc.receiver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import com.jpmc.msgproc.dto.AdjustmentDO;
import com.jpmc.msgproc.dto.ProductDO;
import com.jpmc.msgproc.exception.SystemException;
import com.jpmc.msgproc.generator.ReportGenerator;
import com.jpmc.msgproc.generator.ReportGeneratorImpl;
import com.jpmc.msgproc.processor.MessageProcessor;
import com.jpmc.msgproc.processor.MessageProcessorImpl;
import com.jpmc.msgproc.util.Constants;
import com.jpmc.msgproc.util.MessageType;

/**
 * @author Aravind Rajasekaran
 * @version 1.0
 * @since 1.0
 */
public class MessageReceiverImpl implements MessageReceiver {
	private ReportGenerator rptGenImpl;
	private MessageProcessor msgProcImpl;
	private Map<String, ProductDO> saleMap = new HashMap<>();
	private Map<String, List<AdjustmentDO>> adjustmentMap = new HashMap<>();
	private Queue<String> messageQueue = new LinkedBlockingQueue<>(50);
	static final Logger logger = Logger.getLogger(MessageReceiverImpl.class);

	public MessageReceiverImpl() {
		msgProcImpl = new MessageProcessorImpl();
		rptGenImpl = new ReportGeneratorImpl();
	}

	/**
	 * receives message from the sender.
	 * 
	 * @param message
	 *            message from the sender.
	 * 
	 * @return void.
	 */
	@Override
	public boolean hasReceivedMsg(String message) {
		if (message.isEmpty()) {
			throw new SystemException("Invalid message input");
		}
		try {
			messageQueue.add(message);
		} catch (IllegalStateException ex) {
			logger.info("Application could not accept the new message " + "" + message + " as it was PAUSED");
			System.exit(0);
		}
		return canProcessMsg(message, messageQueue.size());
	}

	/**
	 * process every message by redirecting the message to correct processor based
	 * on the message type.
	 * 
	 * @param message
	 *            message from the receiveMessage. msgCount count of the message
	 *            from the receiveMessage
	 * 
	 * @return void.
	 */
	@Override
	public boolean canProcessMsg(String message, int msgCount) {
		String[] messageArray = null;
		try {
			messageArray = message.split(Constants.SPACE_STRING);
		} catch (ArrayIndexOutOfBoundsException ex) {
			logger.error("Blank message received");
		}
		switch (findMessageType(message)) {
		case 1:
			msgProcImpl.processMessageOne(messageArray, saleMap);
			break;
		case 2:
			msgProcImpl.processMessageTwo(messageArray, saleMap);
			break;
		case 3:
			adjustmentMap = saveAdjustmentMessage(message, messageArray, adjustmentMap);
			msgProcImpl.processMessageThree(messageArray, saleMap);
			break;
		default:
			break;
		}
		if (msgCount % Constants.INTEGER_TEN == Constants.INTEGER_ZERO
				&& msgCount % Constants.INTEGER_FIFTY != Constants.INTEGER_ZERO) {
			rptGenImpl.generateReport(saleMap);
		}
		if (msgCount % Constants.INTEGER_FIFTY == Constants.INTEGER_ZERO) {
			rptGenImpl.generateAdjustmentReport(saleMap, adjustmentMap);
		}
		return true;
	}

	/**
	 * save the adjustment messages to the corresponding sale type.
	 * 
	 * @param message
	 *            message from the processMessage. messageArray string array derived
	 *            from message. adjustmentMap map holds the adjustment message and
	 *            the sale type.
	 * 
	 * @return returns the populated adjusment map.
	 */
	@Override
	public Map<String, List<AdjustmentDO>> saveAdjustmentMessage(String message, String[] messageArray,
			Map<String, List<AdjustmentDO>> adjustmentMap) {
		AdjustmentDO adjustmentDO = new AdjustmentDO();
		final String saleType = messageArray[Constants.INTEGER_TWO].substring(Constants.INTEGER_ZERO,
				messageArray[Constants.INTEGER_TWO].length() - Constants.INTEGER_ONE);
		if (!adjustmentMap.containsKey(saleType)) {
			List<AdjustmentDO> adjustmentList = new ArrayList<>();
			adjustmentDO.setAdjusmentMsg(message);
			adjustmentList.add(adjustmentDO);
			adjustmentMap.put(saleType, adjustmentList);
		} else {
			adjustmentDO.setAdjusmentMsg(message);
			adjustmentMap.get(saleType).add(adjustmentDO);
		}
		return adjustmentMap;
	}

	/**
	 * find the type of the input message.
	 * 
	 * @param message
	 *            message from the processMessage.
	 * 
	 * @return returns the enum of the corresponding msg.
	 */
	public int findMessageType(String message) {
		if (message.contains(Constants.SALE_MSG_MANY)) {
			return MessageType.MESSAGE_TYPE_2.getValue();
		} else if (message.contains(Constants.SALE_MSG_SINGLE)) {
			return MessageType.MESSAGE_TYPE_1.getValue();
		} else {
			return MessageType.MESSAGE_TYPE_3.getValue();
		}
	}
}