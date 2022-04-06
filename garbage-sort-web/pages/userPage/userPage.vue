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
            <view class="member-card-container">
                <view class="member-card">
                    <text>我的积分</text>
                    <!--TODO 绑定变量-->
                    <text style="font-weight: 700">1234</text>
                </view>
            </view>
            <view class="cards-container">
                <view class="card" @click="goTestPage">
                    <image
                        src="../../static/icons/问答.svg"
                        mode="scaleToFill"
                        style="background-color: #f7eff2"
                    ></image>
                    <text>知识问答</text>
                </view>
                <view class="card">
                    <image
                        src="../../static/icons/商城.svg"
                        mode="scaleToFill"
                        style="background-color: #eeeff7"
                    ></image>
                    <text>积分商城</text>
                </view>
                <view class="card chart-container">
                    <qiun-data-charts
                        type="pie"
                        :chartData="pieData"
                        style="height: 90%"
                    />
                </view>
            </view>
        </view>
        <tab-bar
            :selected-index="2"
            style="width: 100%; position: absolute; bottom: 0"
        ></tab-bar>
    </view>
</template>

<script>
import axios from '../../static/utils/request.js';

export default {
    data() {
        return {
            pieData: {
                series: [
                    {
                        name: '一班',
                        data: 50
                    },
                    {
                        name: '二班',
                        data: 30
                    },
                    {
                        name: '三班',
                        data: 20
                    },
                    {
                        name: '四班',
                        data: 18
                    }
                ]
            }
        };
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
    // TODO 处理后续的数据
    onShow() {
        axios
            .get('/user/userInfo', {
                params: {
                    openID: this.$store.state.user.openId
                }
            })
            .then((res) => {
                console.log(res.data);
            });
    }
};
</script>

<style lang="less" scoped>
@import url('../../common/style/userPage');
</style>
