package com.jpmc.msgproc.processor;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jpmc.msgproc.dto.ProductDO;
import com.jpmc.msgproc.exception.SystemException;
import com.jpmc.msgproc.util.Constants;

/**
 * @author Aravind Rajasekaran
 * @version 1.0
 * @since 1.0
 */
public class MessageProcessorImpl implements MessageProcessor {

	static final Logger logger = Logger.getLogger(MessageProcessorImpl.class);

	/**
	 * Process the message type 1 and save the processed message in map.
	 * 
	 * @param messageArray
	 *            String array parsed from input message saleMap Map which holds all
	 *            the saletype and quantity, value.
	 * 
	 * @return saleMap returns map with processed message as key and value
	 */
	@Override
	public Map<String, ProductDO> processMessageOne(String[] messageArray, Map<String, ProductDO> saleMap) {

		ProductDO productDO = new ProductDO();
		final String saleType = messageArray[Constants.INTEGER_ZERO];

		if (!saleMap.containsKey(saleType)) {
			productDO.setQuantity(BigDecimal.valueOf(1L));
			productDO.setValue(new BigDecimal(messageArray[Constants.INTEGER_TWO].substring(Constants.INTEGER_ZERO,
					messageArray[Constants.INTEGER_TWO].length() - Constants.INTEGER_ONE)));
			saleMap.put(saleType, productDO);
		} else {
			saleMap.put(saleType, totalValue(messageArray, saleMap));
		}

		logger.info(String.join(" ", Arrays.asList(messageArray)));

		return saleMap;

	}

	/**
	 * Process the message type 2 and save the processed message in map.
	 * 
	 * @param messageArray
	 *            String array parsed from input message saleMap Map which holds all
	 *            the saletype and quantity, value.
	 * 
	 * @return saleMap returns map with processed message as key and value
	 */
	@Override
	public Map<String, ProductDO> processMessageTwo(String[] messageArray, Map<String, ProductDO> saleMap) {

		ProductDO productDO = new ProductDO();
		final String saleType = messageArray[Constants.INTEGER_THREE].substring(Constants.INTEGER_ZERO,
				messageArray[Constants.INTEGER_THREE].length() - Constants.INTEGER_ONE);
		final BigDecimal numOfSales = new BigDecimal(messageArray[Constants.INTEGER_ZERO]);

		if (!saleMap.containsKey(saleType)) {
			productDO.setQuantity(numOfSales);
			productDO.setValue(numOfSales
					.multiply(new BigDecimal(messageArray[Constants.INTEGER_FIVE].substring(Constants.INTEGER_ZERO,
							messageArray[Constants.INTEGER_FIVE].length() - Constants.INTEGER_ONE))));
		} else {
			productDO.setQuantity(saleMap.get(saleType).getQuantity().add(numOfSales));
			productDO.setValue((saleMap.get(saleType).getValue()).add((numOfSales)
					.multiply(new BigDecimal(messageArray[Constants.INTEGER_FIVE].substring(Constants.INTEGER_ZERO,
							messageArray[Constants.INTEGER_FIVE].length() - Constants.INTEGER_ONE)))));
		}
		saleMap.put(saleType, productDO);
		logger.info(String.join(" ", Arrays.asList(messageArray)));

		return saleMap;

	}

	/**
	 * Process the message type 3 and save the processed message in map.
	 * 
	 * @param messageArray
	 *            String array parsed from input message saleMap Map which holds all
	 *            the saletype and quantity & value.
	 * 
	 * @return saleMap returns map with processed message as key and value
	 */
	@Override
	public Map<String, ProductDO> processMessageThree(String[] messageArray, Map<String, ProductDO> saleMap) {

		final String saleType = messageArray[Constants.INTEGER_TWO].substring(Constants.INTEGER_ZERO,
				messageArray[Constants.INTEGER_TWO].length() - Constants.INTEGER_ONE);

		if (!saleMap.containsKey(saleType)) {
			throw new SystemException("No sale message for the sale type was received before to adjust now");
		}

		BigDecimal totalQuantity = computeTotal(
				messageArray[Constants.INTEGER_ONE].substring(Constants.INTEGER_ZERO,
						messageArray[Constants.INTEGER_ONE].length() - Constants.INTEGER_ONE),
				saleMap.get(saleType).getQuantity());

		switch (messageArray[Constants.INTEGER_ZERO].toLowerCase()) {
		case Constants.MATH_ADD:
			saleMap.get(saleType).setValue(saleMap.get(saleType).getValue().add(totalQuantity));
			break;
		case Constants.MATH_SUB:
			saleMap.get(saleType).setValue(saleMap.get(saleType).getValue().subtract(totalQuantity));
			break;
		case Constants.MATH_MUL:
			saleMap.get(saleType).setValue(saleMap.get(saleType).getValue().multiply(totalQuantity));
			break;
		default:
			break;
		}

		logger.info(String.join(" ", Arrays.asList(messageArray)));
		return saleMap;

	}

	/**
	 * Calculate the total value for the message type 1 .
	 * 
	 * @param messageArray
	 *            String array parsed from input message saleMap Map which holds all
	 *            the saletype and quantity, value.
	 * 
	 * @return productDO returns DO with total value and quantity in setter.
	 */
	private ProductDO totalValue(String[] messageArray, Map<String, ProductDO> saleMap) {
		ProductDO productDO = new ProductDO();
		productDO.setQuantity(
				saleMap.get(messageArray[Constants.INTEGER_ZERO]).getQuantity().add(BigDecimal.valueOf(1L)));
		productDO.setValue(saleMap.get(messageArray[Constants.INTEGER_ZERO]).getValue()
				.add(new BigDecimal(messageArray[Constants.INTEGER_TWO].substring(Constants.INTEGER_ZERO,
						messageArray[Constants.INTEGER_TWO].length() - Constants.INTEGER_ONE))));
		return productDO;
	}

	/**
	 * Calculate the total value for the message type 3.
	 * 
	 * @param value
	 *            value to be adjusted from message 3 saleQuantity from the presaved
	 *            salemap.
	 * 
	 * @return returns the multiplied figure of both the parameters
	 */
	private BigDecimal computeTotal(String value, BigDecimal saleQuantity) {

		return new BigDecimal(value).multiply(saleQuantity);

	}

}
