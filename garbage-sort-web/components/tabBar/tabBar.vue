<template>
    <view class="tabBar-root">
        <view class="tabBar-item" @click="switchTab(0)">
            <image :src="iconPath(0)" mode="scaleToFill"></image>
            <text>主页</text>
        </view>
        <view class="tabBar-item" @click="switchTab(1)">
            <image
                :src="iconPath(1)"
                mode="scaleToFill"
                class="mid-btn"
            ></image>
            <text style="z-index: 2000">搜索</text>
        </view>
        <view class="tabBar-item" @click="switchTab(2)">
            <image :src="iconPath(2)" mode="scaleToFill"></image>
            <text>个人中心</text>
        </view>
    </view>
</template>

<script>
export default {
    name: 'tabBar',
    data() {
        return {
            list: [
                {
                    pagePath: '../../pages/sortInfo_home/sortInfo_home',
                    iconPath: '../../static/icons/home_off.png',
                    selectedIconPath: '../../static/icons/home_on.png',
                    text: '主页'
                },
                {
                    pagePath: '../../pages/sortInfo_home/sortInfo_home',
                    iconPath: '../../static/icons/Search_off.png',
                    selectedIconPath: '../../static/icons/Search_on.png',
                    text: '搜索'
                },
                {
                    pagePath: '../../pages/userPage/userPage',
                    iconPath: '../../static/icons/user_off.png',
                    selectedIconPath: '../../static/icons/account_on.png',
                    text: '个人中心'
                }
            ]
        };
    },
    props: {
        selectedIndex: Number
    },
    computed: {},
    methods: {
        switchTab(itemIndex) {
            uni.switchTab({
                url: this.list[itemIndex].pagePath,
                fail(err) {
                    console.log(err);
                }
            });
        },
        iconPath(itemIndex) {
            if (this.selectedIndex === itemIndex) {
                return this.list[itemIndex].selectedIconPath;
            } else {
                return this.list[itemIndex].iconPath;
            }
        }
    }
};
</script>

<style lang="less">
.tabBar-root {
    background-color: #fff;
    width: 100%;
    align-self: flex-end;
    display: flex;
    justify-content: space-around;
    height: 100px;
}

.tabBar-item {
    position: relative;
    flex: 1;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    image {
        width: 36px;
        height: 36px;
    }
    text {
        display: block;
    }
}

.mid-btn {
    border-radius: 50%;
    width: 60px !important;
    height: 60px !important;
    position: absolute;
    bottom: 40%;
    background-color: #fff;
    padding: 1rem;
}
</style>
