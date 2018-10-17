package com.jpmc.msgproc.generator;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jpmc.msgproc.dto.AdjustmentDO;
import com.jpmc.msgproc.dto.ProductDO;
import com.jpmc.msgproc.util.Constants;

/**
 * @author Aravind Rajasekaran
 * @version 1.0
 * @since 1.0
 */
public class ReportGeneratorImpl implements ReportGenerator {

	static final Logger logger = Logger.getLogger(ReportGeneratorImpl.class);

	/**
	 * Generate report as logs in console after every 10 msgs.
	 * 
	 * @param saleMap
	 *            map which was populated while processing the message
	 * @return void
	 */
	@Override
	public void generateReport(Map<String, ProductDO> saleMap) {
		logger.info("\n-------Begin Sales Report-------");
		for (Map.Entry<String, ProductDO> entry : saleMap.entrySet()) {
			String saleMsg = entry.getKey().concat(Constants.COLON)
					.concat(Constants.TOTAL_QUANTITY + entry.getValue().getQuantity()).concat(" ")
					.concat(Constants.TOTAL_VALUE + entry.getValue().getValue() + Constants.PENCE);
			logger.info(saleMsg);
		}
		logger.info("-------End of Sales Report-------");
	}

	/**
	 * Generate report as logs in console after 50 msgs
	 * 
	 * @param adjMap
	 *            map which was populated while processing the messagetype 3.
	 * @return void
	 */
	@Override
	public void generateAdjustmentReport(Map<String, ProductDO> saleMap, Map<String, List<AdjustmentDO>> adjMap) {
		generateReport(saleMap);
		logger.info("\n---Application has PAUSED. Adjustment Report generation in process---");
		logger.info("---Application will NOT accept any new messages---");
		for (Map.Entry<String, List<AdjustmentDO>> entry : adjMap.entrySet()) {
			logger.info(entry.getKey().toUpperCase().concat(Constants.COLON));
			for (AdjustmentDO msg : entry.getValue()) {
				logger.info(msg.getAdjusmentMsg());
			}
		}
	}
}