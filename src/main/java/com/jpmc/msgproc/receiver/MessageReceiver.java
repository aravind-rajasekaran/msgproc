package com.jpmc.msgproc.receiver;

import java.util.Map;

import com.jpmc.msgproc.dto.AdjustmentDO;

import java.util.List;

public interface MessageReceiver {

	public abstract boolean hasReceivedMsg(String message);

	public abstract boolean canProcessMsg(String message, int msgCount);

	public abstract Map<String, List<AdjustmentDO>> saveAdjustmentMessage(String message, String[] messageArray,
			Map<String, List<AdjustmentDO>> adjustmentMap);

}