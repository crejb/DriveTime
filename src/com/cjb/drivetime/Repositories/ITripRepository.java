package com.cjb.drivetime.Repositories;

import java.util.List;

import com.cjb.drivetime.Entity.Trip;

public interface ITripRepository extends IRepository<Trip, Integer> {
	List<Trip> GetAll();
}
