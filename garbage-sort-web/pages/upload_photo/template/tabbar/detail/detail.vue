<template>
    <view class="root">
        <view class="uni-padding-wrap">
            <view class="uni-title">
                这是选项卡页面跳转详情页面的演示，下面是页面跳转时传递过来的标题：
            </view>
            <view class="uni-title">{{ title }}</view>
        </view>
    </view>
</template>
<script>
export default {
    data() {
        return {
            title: '',
        };
    },
    onLoad(e) {
        this.title = e.title || '';
    },
};
</script>
<style></style>
