package com.movix.adminSP.model.comparators;

import java.util.Comparator;

import com.movix.adminSP.model.dto.CanalDTO;

public class CanalComparator implements Comparator<CanalDTO>{

	@Override
	public int compare(CanalDTO o1, CanalDTO o2) {
		return o1.getNombre().compareTo(o2.getNombre());
	}

}
