/**
 * Copyright 2019 靳磊. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.rail.vo;

import com.critc.rail.vo.NodeView;
import com.critc.util.json.JsonUtil;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * what:    (这里用一句话描述这个类的作用). <br/>
 * when:    (这里描述这个类的适用时机 – 可选).<br/>
 * how:     (这里描述这个类的使用方法 – 可选).<br/>
 * warning: (这里描述这个类的注意事项 – 可选).<br/>
 *
 * @author 靳磊 created on 2019/10/12
 */
public class TestLoadJsonFile {
    @Test
    public void testLoad() throws IOException {
        InputStream inputStream = this.getClass().getResourceAsStream("nodeViews.json");
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        String json = new String(bytes);
        NodeView[] nodeView = JsonUtil.toObject(json, NodeView[].class);
        Assert.assertNotNull(nodeView);
    }
}
