package com.jpmc.msgproc.processor;

import java.util.Map;

import com.jpmc.msgproc.dto.ProductDO;

public interface MessageProcessor {

	public abstract Map<String, ProductDO> processMessageOne(String[] messageArray, Map<String, ProductDO> saleMap);

	public abstract Map<String, ProductDO> processMessageTwo(String[] messageArray, Map<String, ProductDO> saleMap);

	public abstract Map<String, ProductDO> processMessageThree(String[] messageArray, Map<String, ProductDO> saleMap);

}
