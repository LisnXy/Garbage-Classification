<template>
    <view class="user-root">
        <view class="user-header">
            <view class="avatar-container">
                <image
                    class="avatar"
                    :src="avatarUrl"
                    mode="scaleToFill"
                ></image>
            </view>
            <view class="user-name-container">
                <text class="user-name">{{ userName }}</text>
            </view>
        </view>
        <view class="user-body">
            <view class="cards-container">
                <view class="card" @click="goTestPage">
                    <image
                        src="../../static/icons/知识问答.png"
                        mode="scaleToFill"
                    ></image>
                    <text>知识问答</text>
                </view>
                <view class="card"></view>
            </view>
        </view>
    </view>
</template>

<script>
import axios from '../../static/utils/request.js';
export default {
    data() {
        return {};
    },
    methods: {
        /**
         * 跳转到测试页面
         */
        goTestPage() {
            uni.navigateTo({
                url: '../sortTest/sortTest'
            });
        }
    },
    computed: {
        /**
         * @description 返回用户的头像URL
         * @returns {String} 用户头像url
         */
        avatarUrl() {
            return this.$store.state.user.avatarUrl;
        },
        /**
         * @description 返回用户昵称
         * @returns {String} 用户昵称
         */
        userName() {
            return this.$store.state.user.nickName;
        }
    },
    mounted() {
        wx.getUserInfo({
            lang: 'zh_CN',
            success: (res) => {
                // 设置用户信息
                if (res.userInfo) {
                    this.$store.dispatch('user/setUser', res.userInfo);
                }
            }
        });
    }
};
</script>

<style lang="less" scoped>
@import url('../../common/style/userPage');
</style>
