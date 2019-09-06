/**
 * Copyright 2017 弘远技术研发中心. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc;


import com.critc.network.modal.TestGrid;
import com.critc.network.modal.TestPointVector;
import com.critc.rail.service.TestRailNetworkElementService;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({TestGrid.class, TestPointVector.class, TestRailNetworkElementService.class})
public class TestAll {

}
