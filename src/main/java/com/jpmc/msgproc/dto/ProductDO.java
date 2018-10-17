package com.jpmc.msgproc.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductDO implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal quantity;

	private BigDecimal value;

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

}
