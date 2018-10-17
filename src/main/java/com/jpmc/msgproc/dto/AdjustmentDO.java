package com.jpmc.msgproc.dto;

import java.io.Serializable;

public class AdjustmentDO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String adjusmentMsg;

	public String getAdjusmentMsg() {
		return adjusmentMsg;
	}

	public void setAdjusmentMsg(String adjusmentMsg) {
		this.adjusmentMsg = adjusmentMsg;
	}

}
