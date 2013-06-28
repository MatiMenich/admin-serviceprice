package com.movix.adminSP.model.comparators;

import java.util.Comparator;

import com.movix.adminSP.model.dto.ServicePriceDTO;


public class SPComparator implements Comparator<ServicePriceDTO> {

	@Override
	public int compare(ServicePriceDTO o1, ServicePriceDTO o2) {
		return o1.getServicio().compareTo(o2.getServicio());
	}

}
