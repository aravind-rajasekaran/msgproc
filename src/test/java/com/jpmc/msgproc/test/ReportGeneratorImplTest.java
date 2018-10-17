package com.jpmc.msgproc.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.jpmc.msgproc.dto.AdjustmentDO;
import com.jpmc.msgproc.dto.ProductDO;
import com.jpmc.msgproc.generator.ReportGeneratorImpl;

import junit.framework.TestCase;

@RunWith(JUnit4.class)
public class ReportGeneratorImplTest extends TestCase {
	static ReportGeneratorImpl rptGenerator;

	@BeforeClass
	public static void getInstances() {
		rptGenerator = new ReportGeneratorImpl();
	}

	@Test
	public void generateReportTest() {
		Map<String, ProductDO> saleMap = new HashMap<>();
		ProductDO productDO = new ProductDO();
		productDO.setQuantity(new BigDecimal(10));
		productDO.setValue(new BigDecimal(200));
		saleMap.put("doll", productDO);
		rptGenerator.generateReport(saleMap);
	}

	@Test
	public void generateAdjustmentReportTest() {
		Map<String, ProductDO> saleMap = new HashMap<>();
		ProductDO productDO = new ProductDO();
		productDO.setQuantity(new BigDecimal(10));
		productDO.setValue(new BigDecimal(200));
		saleMap.put("doll", productDO);

		Map<String, List<AdjustmentDO>> adjMap = new HashMap<>();
		List<AdjustmentDO> adjustmentList = new ArrayList<>();
		AdjustmentDO adjDO1 = new AdjustmentDO();
		adjDO1.setAdjusmentMsg("Add 10p pens");
		adjustmentList.add(adjDO1);
		AdjustmentDO adjDO2 = new AdjustmentDO();
		adjDO2.setAdjusmentMsg("Subtract 2p pens");
		adjustmentList.add(adjDO2);
		adjMap.put("doll", adjustmentList);
		rptGenerator.generateAdjustmentReport(saleMap, adjMap);
	}

}
