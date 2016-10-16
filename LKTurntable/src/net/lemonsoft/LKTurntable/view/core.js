function LKTurntable() {
    /* 定义ID常量 */
    const LK_TURNTABLE_BACK_ID = "lk-turntable-back";
    const LK_TURNTABLE_PANEL_ID = "lk-turntable-panel";
    const LK_TURNTABLE_PLAY_ID = "lk-turntable-play";

    /**
     * 设置抽奖转盘的图片
     * @param imageURL 抽奖转盘的图片URL
     */
    this.setPanelImage = function (imageURL) {
        document.getElementById(LK_TURNTABLE_PANEL_ID).src = imageURL;
    };

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