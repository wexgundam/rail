/**
 * Copyright 2019 靳磊. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.rail.controller;

import com.critc.rail.service.StationService;
import com.critc.tile.modal.VectorTileSystem;
import com.critc.tile.service.FeaturesVoFactory;
import com.critc.tile.service.VectorTileSystemService;
import com.critc.tile.service.ViewService;
import com.critc.tile.vo.FeaturesVo;
import com.critc.util.json.JsonUtil;
import com.critc.util.web.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * what:    (这里用一句话描述这个类的作用). <br/>
 * when:    (这里描述这个类的适用时机 – 可选).<br/>
 * how:     (这里描述这个类的使用方法 – 可选).<br/>
 * warning: (这里描述这个类的注意事项 – 可选).<br/>
 *
 * @author 靳磊 created on 2019/10/11
 */
@RequestMapping("/rail")
@Controller
public class RailController {
    @Autowired
    VectorTileSystemService vectorTileSystemService;
    @Autowired
    ViewService viewService;
    @Autowired
    private StationService stationService;

    /**
     * what:    根据用户请求，返回对应的图形对象. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/10/23
     */
    @RequestMapping(value = "/features")
    public void getFeatures(@RequestParam(value = "zoom-level") Integer zoomLevel,
                            @RequestParam(value = "previous-zoom-level") Integer previousZoomLevel,
                            @RequestParam(value = "view-width") Double viewWidth,
                            @RequestParam(value = "view-height") Double viewHeight,
                            @RequestParam(value = "figure-center-view-coordinate-delta-x") Double figureCenterViewCoordinateDeltaX,
                            @RequestParam(value = "figure-center-view-coordinate-delta-y") Double figureCenterViewCoordinateDeltaY,
                            @RequestParam(value = "locked-view-coordinate-delta-x") Double lockedViewCoordinateDeltaX,
                            @RequestParam(value = "locked-view-coordinate-delta-y") Double lockedViewCoordinateDeltaY,
                            HttpServletResponse response) {

        ////////根据请求参数构建请求方的显示环境////////
        VectorTileSystem vectorTileSystem = vectorTileSystemService.createVectorTileSystem(
                viewService.getFigure(),
                viewWidth, viewHeight,
                zoomLevel, previousZoomLevel,
                figureCenterViewCoordinateDeltaX, figureCenterViewCoordinateDeltaY,
                lockedViewCoordinateDeltaX, lockedViewCoordinateDeltaY);

        FeaturesVoFactory featuresVoFactory = new FeaturesVoFactory();
        FeaturesVo featuresVo = featuresVoFactory.createFeaturesVo(vectorTileSystem, viewService);

        ModelMap modelMap = new ModelMap();
        modelMap.put("success", true);
        modelMap.put("data", featuresVo);
        String json = JsonUtil.toStr(modelMap);
        WebUtil.out(response, json);
    }
}
