package com.jpmc.msgproc.test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.jpmc.msgproc.dto.ProductDO;
import com.jpmc.msgproc.exception.SystemException;
import com.jpmc.msgproc.processor.MessageProcessorImpl;

import junit.framework.TestCase;

@RunWith(JUnit4.class)
public class MessageProcessorImplTest extends TestCase {

	static MessageProcessorImpl msgProccessor;

	@BeforeClass
	public static void getInstances() {
		msgProccessor = new MessageProcessorImpl();
	}

	@Test
	public void processMessageOneTestForIfCondition() {
		String message = "cup at 30p";
		String[] messageArray = message.split(" ");
		Map<String, ProductDO> saleMap = new HashMap<>();
		msgProccessor.processMessageOne(messageArray, saleMap);
		assertEquals(true, saleMap.containsKey("cup"));
		assertEquals(new BigDecimal(1), saleMap.get("cup").getQuantity());
		assertEquals(new BigDecimal(30), saleMap.get("cup").getValue());
	}

	@Test
	public void processMessageOneTestForElseCondition() {
		String message = "mat at 30p";
		String[] messageArray = message.split(" ");
		Map<String, ProductDO> saleMap = new HashMap<>();
		ProductDO productDO = new ProductDO();
		productDO.setQuantity(new BigDecimal(1));
		productDO.setValue(new BigDecimal(10));
		saleMap.put("mat", productDO);
		msgProccessor.processMessageOne(messageArray, saleMap);
		assertEquals(true, saleMap.containsKey("mat"));
		assertEquals(new BigDecimal(2), saleMap.get("mat").getQuantity());
		assertEquals(new BigDecimal(40), saleMap.get("mat").getValue());
	}

	@Test
	public void processMessageTwoTestForIfCondition() {
		String message = "20 sales of apples at 10p each";
		String[] messageArray = message.split(" ");
		String saleType = "apple";
		Map<String, ProductDO> saleMap = new HashMap<>();
		msgProccessor.processMessageTwo(messageArray, saleMap);
		assertEquals(true, saleMap.containsKey(saleType));
		assertEquals(new BigDecimal(20), saleMap.get(saleType).getQuantity());
		assertEquals(new BigDecimal(200), saleMap.get(saleType).getValue());
	}

	@Test
	public void processMessageTwoTestForElseCondition() {
		String message = "20 sales of bottles at 10p each";
		String saleType = "bottle";
		String[] messageArray = message.split(" ");
		Map<String, ProductDO> saleMap = new HashMap<>();
		ProductDO productDO = new ProductDO();
		productDO.setQuantity(new BigDecimal(1));
		productDO.setValue(new BigDecimal(10));
		saleMap.put(saleType, productDO);
		msgProccessor.processMessageTwo(messageArray, saleMap);
		assertEquals(true, saleMap.containsKey(saleType));
		assertEquals(new BigDecimal(21), saleMap.get(saleType).getQuantity());
		assertEquals(new BigDecimal(210), saleMap.get(saleType).getValue());
	}

	@Test
	public void processMessageThreeTestForCaseAdd() {
		String message = "Add 30p bags";
		String[] messageArray = message.split(" ");
		Map<String, ProductDO> saleMap = new HashMap<>();
		ProductDO productDO = new ProductDO();
		productDO.setQuantity(new BigDecimal(10));
		productDO.setValue(new BigDecimal(1000));
		saleMap.put("bag", productDO);
		msgProccessor.processMessageThree(messageArray, saleMap);
		assertEquals(new BigDecimal(1300), saleMap.get("bag").getValue());
		assertEquals(new BigDecimal(10), saleMap.get("bag").getQuantity());
	}

	@Test
	public void processMessageThreeTestForCaseSubtract() {
		String message = "Subtract 3p belts";
		String[] messageArray = message.split(" ");
		Map<String, ProductDO> saleMap = new HashMap<>();
		ProductDO productDO = new ProductDO();
		productDO.setQuantity(new BigDecimal(10));
		productDO.setValue(new BigDecimal(1000));
		saleMap.put("belt", productDO);
		msgProccessor.processMessageThree(messageArray, saleMap);
		assertEquals(new BigDecimal(970), saleMap.get("belt").getValue());
		assertEquals(new BigDecimal(10), saleMap.get("belt").getQuantity());
	}

	@Test
	public void processMessageThreeTestForCaseMultiply() {
		String message = "Multiply 2p cords";
		String[] messageArray = message.split(" ");
		Map<String, ProductDO> saleMap = new HashMap<>();
		ProductDO productDO = new ProductDO();
		productDO.setQuantity(new BigDecimal(10));
		productDO.setValue(new BigDecimal(1000));
		saleMap.put("cord", productDO);
		msgProccessor.processMessageThree(messageArray, saleMap);
		assertEquals(new BigDecimal(20000), saleMap.get("cord").getValue());
		assertEquals(new BigDecimal(10), saleMap.get("cord").getQuantity());
	}

	@Test(expected = SystemException.class)
	public void processMessageThreeTestForFailure() {
		String message = "Add 2p cups";
		String[] messageArray = message.split(" ");
		Map<String, ProductDO> saleMap = new HashMap<>();
		msgProccessor.processMessageThree(messageArray, saleMap);
	}
}