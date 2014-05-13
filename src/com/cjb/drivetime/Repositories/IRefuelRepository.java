package com.cjb.drivetime.Repositories;

import java.util.List;

import com.cjb.drivetime.Entity.Refuel;

public interface IRefuelRepository extends IRepository<Refuel, Integer> {
	List<Refuel> GetAll();
}
