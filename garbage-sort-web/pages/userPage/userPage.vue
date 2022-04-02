<template>
    <view class="user-root">
        <view class="user-header"></view>
        <view class="user-body"></view>
    </view>
</template>

<script>
export default {
    data() {
        return {};
    },
    methods: {},
    mounted() {
        wx.login({
            success(res) {
                console.log(res.code);
            },
        });
    },
};
</script>

<style lang="less" scoped></style>
