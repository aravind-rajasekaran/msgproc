package com.jpmc.msgproc.generator;

import java.util.Map;

import com.jpmc.msgproc.dto.AdjustmentDO;
import com.jpmc.msgproc.dto.ProductDO;

import java.util.List;

public interface ReportGenerator {

	public abstract void generateReport(Map<String, ProductDO> saleMap);

	public abstract void generateAdjustmentReport(Map<String, ProductDO> saleMap,
			Map<String, List<AdjustmentDO>> adjMap);

}
