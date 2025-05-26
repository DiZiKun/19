package com.forbidden.island.view.handler;

import com.forbidden.island.utils.Constant;
import com.forbidden.island.utils.ImageUtil;
import com.forbidden.island.view.ElementEngine;
import com.forbidden.island.view.TreasurePanel;

import javax.swing.*;

/**
 * WaterMeterRendering 类实现了 IRendering 接口，
 * 负责游戏中水位计（水表）的界面渲染与更新。
 */
public class WaterMeterRendering implements IRendering {

    /**
     * 构造方法
     * 初始化时设置水位计的图标，显示初始状态。
     */
    public WaterMeterRendering() {
        // 通过 ElementEngine 获取当前水位计对应的图片路径
        // 设置 TreasurePanel 中 waterMeter 组件的图标，并指定图片宽高
        TreasurePanel.waterMeter.setIcon(new ImageIcon(
                ImageUtil.getImage(ElementEngine.getWaterMeterImg(),
                        Constant.WATER_METER_WIDTH,
                        Constant.WATER_METER_HEIGHT)));
    }

    /**
     * update 方法
     * 当游戏状态改变水位计时，刷新水位计的图标，反映最新的水位状态。
     */
    @Override
    public void update() {
        // 重新获取当前水位计图片，更新图标显示
        TreasurePanel.waterMeter.setIcon(new ImageIcon(
                ImageUtil.getImage(ElementEngine.getWaterMeterImg(),
                        Constant.WATER_METER_WIDTH,
                        Constant.WATER_METER_HEIGHT)));
    }

    /**
     * finish 方法
     * 游戏结束或不再需要显示水位计时，隐藏水位计组件。
     */
    @Override
    public void finish() {
        // 设置水位计组件不可见，隐藏于界面上
        TreasurePanel.waterMeter.setVisible(false);
    }
}
