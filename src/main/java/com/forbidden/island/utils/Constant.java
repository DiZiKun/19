package com.forbidden.island.utils;

import java.util.Objects;

/**
 * 游戏中使用的全局常量类。
 * 包含界面尺寸、图像尺寸、资源路径等配置信息。
 */
public abstract class Constant {

    // ====== 窗口相关尺寸 ======

    /** 主窗口的宽度（像素） */
    public static final int FRAME_WIDTH = 1080;

    /** 主窗口的高度（像素） */
    public static final int FRAME_HEIGHT = 1030;

    // ====== 冒险者图像尺寸 ======

    /** 冒险者图像的宽度（像素） */
    public static final int ADVENTURER_WIDTH = 73;

    /** 冒险者图像的高度（像素） */
    public static final int ADVENTURER_HEIGHT = 85;

    // ====== 洪水图像尺寸 ======

    /** 洪水图像的宽度（像素） */
    public static final int FLOOD_WIDTH = 120;

    /** 洪水图像的高度（像素） */
    public static final int FLOOD_HEIGHT = 85;

    // ====== 宝藏图像尺寸 ======

    /** 宝藏图像的宽度（像素） */
    public static final int TREASURE_WIDTH = 120;

    /** 宝藏图像的高度（像素） */
    public static final int TREASURE_HEIGHT = 85;

    // ====== 地砖图像尺寸 ======

    /** 地图单元格（地砖）的宽度（像素） */
    public static final int TILE_WIDTH = 110;

    /** 地图单元格（地砖）的高度（像素） */
    public static final int TILE_HEIGHT = 85;

    // ====== 游戏棋盘尺寸 ======

    /** 游戏棋盘区域的宽度（像素） */
    public static final int BOARD_WIDTH = 700;

    /** 游戏棋盘区域的高度（像素） */
    public static final int BOARD_HEIGHT = 700;

    // ====== 水位计图像尺寸 ======

    /** 水位计图像的宽度（像素） */
    public static final int WATER_METER_WIDTH = 120;

    /** 水位计图像的高度（像素） */
    public static final int WATER_METER_HEIGHT = 350;

    // ====== 资源路径 ======

    /** 图像资源的根目录路径（获取类路径下的 /image 文件夹） */
    public static final String RESOURCES_PATH = Objects.requireNonNull(
            Constant.class.getResource("/image")
    ).getPath();
}
