<template>
    <view class="finished-root">
        <view class="finished-header">
            <image
                src="../../static/image/test-bg2.svg"
                mode="aspectFit"
            ></image>
            <view class="score">
                <text>{{ correctItems.length * 10 }} 分</text>
                <text
                    style="
                        background-color: rgba(117, 156, 209, 0.7);
                        font-size: 0.9rem;
                        color: #f5f5f5;
                        margin: auto;
                        border-radius: 6px;
                        padding: 5px 20px;
                    "
                >
                    下次你会做的更好
                </text>
            </view>
        </view>
        <view class="finished-body">
            <scroll-view class="list-container" scroll-y="true">
                <uni-list :border="false">
                    <uni-list-item v-for="(item, index) in falseItems">
                        <view
                            class="list-item-header"
                            slot="header"
                            style="background-color: #f8efee"
                        >
                            <uni-icons
                                type="closeempty"
                                color="#B65454"
                                size="25"
                            ></uni-icons>
                        </view>
                        <view class="list-item-body" slot="body">
                            {{ item.garbageName }}
                        </view>
                        <view
                            class="list-item-footer"
                            slot="footer"
                            :style="{
                                'border-color': bodyColors[item.type - 1],
                                color: bodyColors[item.type - 1]
                            }"
                        >
                            <text>{{ getText(item.type) }}</text>
                        </view>
                    </uni-list-item>
                    <uni-list-item v-for="(item, index) in correctItems">
                        <view
                            class="list-item-header"
                            slot="header"
                            style="background-color: #edf7f4"
                        >
                            <uni-icons
                                type="checkmarkempty"
                                color="#86CBA5"
                                size="25"
                            ></uni-icons>
                        </view>
                        <view class="list-item-body" slot="body">
                            {{ item.garbageName }}
                        </view>
                        <view
                            class="list-item-footer"
                            slot="footer"
                            :style="{
                                'border-color': bodyColors[item.type - 1],
                                color: bodyColors[item.type - 1]
                            }"
                        >
                            <text>{{ getText(item.type) }}</text>
                        </view>
                    </uni-list-item>
                </uni-list>
            </scroll-view>
            <view class="next-button-container">
                <button type="default" @click="nextBatch">
                    <text>再测一次</text>
                </button>
            </view>
        </view>
    </view>
</template>

<script>
export default {
    data() {
        return {
            correctItems: [
                {
                    garbageName: '垃圾1',
                    type: 1
                },
                {
                    garbageName: '垃圾2',
                    type: 2
                }
            ],
            falseItems: [
                {
                    garbageName: '垃圾1',
                    type: 1
                },
                {
                    garbageName: '垃圾2',
                    type: 2
                }
            ],
            bodyColors: ['#426AAD', '#B45454', '#64BC8C', '#849494']
        };
    },
    methods: {
        // 根据垃圾类别序号，返回相应的文本
        getText(type) {
            switch (Number(type)) {
                case 1:
                    return '可回收物';
                case 2:
                    return '有害垃圾';
                case 3:
                    return '厨余垃圾';
                default:
                    return '其他垃圾';
            }
        },
        // 下一批
        nextBatch() {
            uni.redirectTo({
                url: '../sortTest/sortTest'
            });
        }
    },
    mounted() {
        uni.$once('questionItems', (data) => {
            this.correctItems = data.correctItems;
            this.falseItems = data.falseItems;
        });
        uni.$emit('loaded');
    }
};
</script>

<style lang="less" scoped>
@import url('../../common/style/sortTestFinished.less');
</style>
