package com.forbidden.island.enums;

/**
 * 表示地图上地块的状态。
 */
public enum TileStatus {
    /** 普通状态，地块正常 */
    Normal,
    /** 淹没状态，地块被部分水淹没 */
    Flooded,
    /** 沉没状态，地块完全沉没不可通行 */
    Sunk
}