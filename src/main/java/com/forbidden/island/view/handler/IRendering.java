package com.forbidden.island.view.handler;

/**
 * IRendering 接口定义了与图形界面 (GUI) 渲染相关的基本行为。
 * 实现该接口的类负责更新游戏画面和处理游戏结束时的界面状态。
 */
public interface IRendering {
    /**
     * 更新图形界面。
     * 一般在游戏状态改变（如角色移动、卡牌变化等）后调用，
     * 以刷新界面并反映最新游戏状态。
     */
    void update();

    /**
     * 当游戏结束时禁用所有界面组件。
     * 实现类应通过该方法禁用按钮、输入等交互控件，防止继续操作。
     */
    void finish();
}
