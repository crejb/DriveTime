package com.cjb.drivetime.ViewModel;

import com.cjb.drivetime.Entity.TimeInterval;


public interface ITripTimerListener {
	void tripTimeChanged(TimeInterval interval);
}
