/**
 * Copyright 2017 弘远技术研发中心. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc;


import com.critc.network.modal.TestGrid;
import com.critc.network.modal.TestPointVector;
import com.critc.network.service.TestGridService;
import com.critc.network.service.TestJts;
import com.critc.rail.service.TestLinkService;
import com.critc.rail.service.TestRailNetworkElementService;
import com.critc.rail.service.TestStationService;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestGrid.class,
        TestPointVector.class,
        TestRailNetworkElementService.class,
        TestGridService.class,
        TestJts.class,
        TestLinkService.class,
        TestStationService.class
})
public class TestAll {

}
