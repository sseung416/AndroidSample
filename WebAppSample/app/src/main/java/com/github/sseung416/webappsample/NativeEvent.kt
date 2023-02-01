package com.github.sseung416.webappsample

/**
 * 안드로이드 내부에서 실행하는 이벤트의 키 값
 * */
enum class NativeEvent {
    HANDLE_SHOW_LOADING,
    HANDLE_HIDE_LOADING,
    HANDLE_SHOW_DIALOG;

    companion object {
        /** ordinal 값으로 enum 값을 조회함 */
        fun get(ordinal: Int): NativeEvent = values()[ordinal]
    }
}