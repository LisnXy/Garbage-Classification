<template>
    <view>
        <page-head :title="title"></page-head>
        <view class="uni-padding-wrap uni-common-mt">
            <view class="uni-btn-v">
                <button type="primary" @tap="getOrient">
                    获取设备的方向信息
                </button>
                <button type="primary" @tap="watchOrient">
                    监听设备的方向变化
                </button>
                <button type="primary" @tap="watchStop">停止监听</button>
            </view>
            <view class="uni-textarea">
                <textarea :value="value" />
            </view>
        </view>
    </view>
</template>
<script>
var id = null;
export default {
    data() {
        return {
            title: 'orientation',
            value: '',
        };
    },
    onUnload() {
        this.watchStop();
    },
    methods: {
        getOrient: function () {
            var that = this;
            plus.orientation.getCurrentOrientation(
                function (o) {
                    that.value =
                        'alpha：' +
                        o.alpha +
                        '\nbeta：' +
                        o.beta +
                        '\ngamma：' +
                        o.gamma;
                },
                function (e) {
                    console.log('获取失败:' + e.message);
                },
            );
        },
        watchOrient: function () {
            var that = this;
            if (id) {
                return;
            }
            id = plus.orientation.watchOrientation(
                function (o) {
                    that.value =
                        '监听设备方向变化信息\n' +
                        'alpha：' +
                        o.alpha +
                        '\nbeta：' +
                        o.beta +
                        '\ngamma：' +
                        o.gamma;
                },
                function (e) {
                    plus.orientation.clearWatch(id);
                    id = null;
                    console.log('监听失败:' + e.message);
                },
            );
        },
        watchStop: function () {
            if (id) {
                plus.orientation.clearWatch(id);
                id = null;
            } else {
                console.log('没有监听设备方向变化');
            }
        },
    },
};
</script>

<style></style>
