<template>
    <view class="page">
        <view class="img-view" v-if="!showSwiper">
            <image :src="imgUrls[0]"></image>
        </view>
        <swiper v-if="showSwiper" indicator-dots="true">
            <swiper-item v-for="(img, key) in imgUrls" :key="key">
                <image :src="img" />
            </swiper-item>
        </swiper>
        <view class="uni-padding-wrap uni-common-mt">
            <view class="uni-title">
                <view>
                    在App端默认为标题栏透明，当用户向下滚动时，标题栏逐渐由透明转变为不透明；当用户再次向上滚动时，标题栏又从不透明变为透明状态。
                </view>
                <view>在微信小程序端，导航栏始终为不透明样式。</view>
                <!-- <view>在支付宝小程序里请使用真机调试查看效果。</view> -->
            </view>
            <view class="uni-title uni-common-mt">图片类型</view>
        </view>
        <view class="uni-list">
            <radio-group @change="radioChange">
                <label
                    class="uni-list-cell uni-list-cell-pd"
                    v-for="(item, index) in items"
                    :key="index"
                >
                    <view>{{ item.name }}</view>
                    <view>
                        <radio :value="item.value" :checked="item.checked" />
                    </view>
                </label>
            </radio-group>
        </view>
        <view style="height: 1000rpx"></view>
    </view>
</template>

<script>
export default {
    data() {
        return {
            showSwiper: false,
            imgUrls: [
                '../../../static/shuijiao.jpg',
                'https://vkceyugu.cdn.bspapp.com/VKCEYUGU-dc-site/b4b60b10-5168-11eb-bd01-97bc1429a9ff.jpg',
                'https://vkceyugu.cdn.bspapp.com/VKCEYUGU-dc-site/b1dcfa70-5168-11eb-bd01-97bc1429a9ff.jpg',
            ],
            items: [
                {
                    value: 'img',
                    name: '静态图',
                    checked: true,
                },
                {
                    value: 'swiper',
                    name: '轮播图',
                    checked: false,
                },
            ],
        };
    },
    methods: {
        radioChange(e) {
            this.showSwiper = e.detail.value === 'swiper';
        },
    },
};
</script>

<style>
image,
swiper,
.img-view {
    width: 750rpx;
    width: 100%;
    height: 500rpx;
}
.page-section-title {
    margin-top: 50rpx;
}
</style>
