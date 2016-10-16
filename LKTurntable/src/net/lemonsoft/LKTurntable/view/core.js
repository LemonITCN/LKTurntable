function LKTurntable() {
    /* 定义ID常量 */
    const LK_TURNTABLE_BACK_ID = "lk-turntable-back";
    const LK_TURNTABLE_PANEL_ID = "lk-turntable-panel";
    const LK_TURNTABLE_PLAY_ID = "lk-turntable-play";

    var rotateCallback = function () {
    };

    /**
     * 设置抽奖转盘的图片
     * @param imageURL 抽奖转盘的图片URL
     */
    this.setPanelImage = function (imageURL) {
        document.getElementById(LK_TURNTABLE_PANEL_ID).src = imageURL;
    };

    /**
     * 设置抽奖按钮的点击事件
     * @param func 点击事件函数
     */
    this.setPlayButtonOnClick = function (func) {
        document.getElementById(LK_TURNTABLE_PLAY_ID).onclick = func;
    };

    /**
     * 设置转盘抽奖旋转完毕的回调函数
     * @param func 抽奖旋转完毕的回调函数
     */
    this.setRorateCompleteCallback = function (func) {
        rotateCallback = func;
    };

    /**
     * 开始旋转抽奖转盘
     * @param aimAngle 要旋转的目标角度
     */
    this.rotateTurntable = function (aimAngle) {
        $("#" + LK_TURNTABLE_PANEL_ID).rotate({
            duration: 6000,
            angle: 0,
            animateTo: 360 * 3 + aimAngle,
            easing: $.easing.easeOutSine,
            callback: rotateCallback
        });
    }

}

// 单例对象
LKTurntable.sharedClient = null;
/**
 * 单例方法
 * @returns {LKTurntable|null}
 */
LKTurntable.sharedInstance = function () {
    if (this.sharedClient == null)
        this.sharedClient = new LKTurntable();
    return this.sharedClient;
};

$(function () {
    LKTurntable.sharedInstance().setPlayButtonOnClick(function () {
        var result = lkTurntable.prizeDraw().split("_");
        LKTurntable.sharedInstance().setRorateCompleteCallback(function () {

        });
        LKTurntable.sharedInstance().rotateTurntable(parseFloat(result[2]));
    });
});