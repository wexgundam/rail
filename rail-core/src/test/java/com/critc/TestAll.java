/**
 * Copyright 2017 弘远技术研发中心. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        com.critc.network.modal.TestGrid.class,
        com.critc.network.modal.TestPointVector.class,
        com.critc.network.service.TestExecutorService.class,
        com.critc.network.service.TestGridService.class,
        com.critc.network.service.TestJts.class,
        com.critc.rail.dao.TestStationDao.class,
        com.critc.rail.dao.TestTrainlineDeportDao.class,
        com.critc.rail.service.TestLinkService.class,
        com.critc.rail.service.TestRailNetworkElementService.class,
        com.critc.rail.service.TestStationService.class,
        com.critc.rail.service.TestTrainlineDeportService.class,
})
public class TestAll {

}
