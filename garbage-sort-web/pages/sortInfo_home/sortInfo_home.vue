<template>
    <view class="root">
        <view
            class="header"
        >
		<image class="header-bg-image" src="https://cdn.jsdelivr.net/gh/LisnXy/WxCDN/garbage-classification/images/background.svg" mode="aspectFit"></image>
            <view class="search">
                <view class="search-header" @click="popSelector">
                    <text class="city-selector">{{ selectedCity }}</text>
                    <image
                        src="../../static/image/下拉.svg"
                        mode="scaleToFill"
                        style="height: 28rpx; width: 28rpx; margin-left: 30rpx"
                    ></image>
                    <view class="border-fill"></view>
                </view>
                <view class="search-body">
                    <view class="search-input">
                        <input
                            v-model="inputValue"
                            maxlength="10"
                            type="text"
                            placeholder="输入要搜索的物品(例电池)"
                            style="width: 100%;font-size:0.9rem;letter-spacing: 1px;"
                            @focus="searchBarFocused"
                            @blur="searchBarBlured($event)"
                            @input="search($event)"
                        />
                    </view>
                </view>
            </view>
        </view>
        <view
            id="body"
            class="body"
            :style="{
                'background-color': bodyColor,
                'background-image': bodyImage
            }"
        >
            <view class="cards-swiper" v-if="!searchMode">
                <swiper
                    :current="currentItem"
                    :indicator-dots="false"
                    :autoplay="false"
                    next-margin="50rpx"
                    previous-margin="50rpx"
                    @change="onSwiperChange($event)"
                >
                    <swiper-item>
                        <view
                            class="swiper-item"
                            style="background-color: #395a98"
                            @click="gotoDetail"
                        >
                            <view class="swiper-icon">
                                <image
                                    src="../../static/icons/可回收垃圾.png"
                                    mode="aspectFit"
                                    style="
                                        width: 100%;
                                        height: 50%;
                                        transform: scale(0.8);
                                    "
                                ></image>
                            </view>
                            <view class="swiper-content">
                                <text class="title">{{ swiperTitles[0] }}</text>
                                <scroll-view
                                    class="swiper-text"
                                    scroll-y="true"
                                >
                                    <text>
                                        {{ swiperTips[0] }}
                                    </text>
                                </scroll-view>
                            </view>
                        </view>
                    </swiper-item>
                    <swiper-item>
                        <view
                            class="swiper-item"
                            style="background-color: #b85555"
                            @click="gotoDetail"
                        >
                            <view class="swiper-icon">
                                <image
                                    src="../../static/icons/有害垃圾.png"
                                    mode="aspectFit"
                                    style="width: 100%; height: 50%"
                                ></image>
                            </view>
                            <view class="swiper-content">
                                <text class="title">{{ swiperTitles[1] }}</text>
                                <scroll-view
                                    class="swiper-text"
                                    scroll-y="true"
                                >
                                    <text>
                                        {{ swiperTips[1] }}
                                    </text>
                                </scroll-view>
                            </view>
                        </view>
                    </swiper-item>
                    <swiper-item>
                        <view
                            class="swiper-item"
                            style="background-color: #65ba8a"
                            @click="gotoDetail"
                        >
                            <view class="swiper-icon">
                                <image
                                    src="../../static/icons/厨余垃圾.png"
                                    mode="aspectFit"
                                    style="width: 100%; height: 50%"
                                ></image>
                            </view>
                            <view class="swiper-content">
                                <text class="title">{{ swiperTitles[2] }}</text>
                                <scroll-view
                                    class="swiper-text"
                                    scroll-y="true"
                                >
                                    <text>
                                        {{ swiperTips[2] }}
                                    </text>
                                </scroll-view>
                            </view>
                        </view>
                    </swiper-item>
                    <swiper-item>
                        <view
                            class="swiper-item"
                            style="background-color: #879696"
                            @click="gotoDetail"
                        >
                            <view class="swiper-icon">
                                <image
                                    src="../../static/icons/其他垃圾.png"
                                    mode="aspectFit"
                                    style="width: 100%; height: 50%"
                                ></image>
                            </view>
                            <view class="swiper-content">
                                <text class="title">{{ swiperTitles[3] }}</text>
                                <scroll-view
                                    class="swiper-text"
                                    scroll-y="true"
                                >
                                    <text>
                                        {{ swiperTips[3] }}
                                    </text>
                                </scroll-view>
                            </view>
                        </view>
                    </swiper-item>
                    <swiper-item v-if="hasBigTrash">
                        <view
                            class="swiper-item"
                            style="background-color: #ebb852"
                            @click="gotoDetail"
                        >
                            <view class="swiper-icon">
                                <image
                                    src="../../static/icons/大件垃圾.png"
                                    mode="aspectFit"
                                    style="
                                        width: 120%;
                                        height: 50%;
                                        position: relative;
                                        right: 8px;
                                    "
                                ></image>
                            </view>
                            <view class="swiper-content">
                                <text class="title">{{ swiperTitles[4] }}</text>
                                <scroll-view
                                    class="swiper-text"
                                    scroll-y="true"
                                >
                                    <text>
                                        {{ swiperTips[4] }}
                                    </text>
                                </scroll-view>
                            </view>
                        </view>
                    </swiper-item>
                </swiper>
            </view>
            <scroll-view
                class="requirements"
                v-if="!searchMode"
                scroll-y="true"
            >
                <view
                    class="requirement"
                    v-for="(requirement, index) in requirements[currentItem]"
                >
                    <uni-icons type="checkmarkempty" color="#FFF"></uni-icons>
                    <text>{{ requirement }}</text>
                </view>
            </scroll-view>
            <scroll-view
                class="search-result"
                scroll-y="true"
                v-if="searchMode"
            >
                <uni-list v-if="showResult">
                    <uni-list-item
                        :title="item.garbageName"
                        v-for="(item, index) in searchResult"
                    >
                        <view
                            slot="footer"
                            class="list-item-icon"
                            :style="{ color: bodyColors[item.type - 1] }"
                        >
                            {{ swiperTitles[item.type - 1] }}
                        </view>
                    </uni-list-item>
                </uni-list>
            </scroll-view>
        </view>
        <uni-popup ref="popup" type="bottom" background-color="#fff">
            <view class="selector-container">
                <view class="selector-header">
                    <uni-title type="h1" title="选择城市"></uni-title>
                </view>
                <view class="divider" />
                <view class="picker-container">
                    <van-picker
                        ref="picker"
                        :columns="citiesList"
                        style="height: 100%; width: 100%"
                        @confirm="onConfirm"
                        show-toolbar="true"
                        @cancel="onCancel"
                        :default-index="defaultIndex"
                    ></van-picker>
                </view>
            </view>
        </uni-popup>
        <tab-bar :selected-index="0"></tab-bar>
    </view>
</template>

<script>
import axios from '../../static/utils/request.js';
export default {
    data() {
        return {
            bodyImage: null,
            selectedCity: '上海市',
            citiesList: [],
            swiperTitles: [
                '可回收物',
                '厨余垃圾',
                '有害垃圾',
                '其他垃圾',
                '大件垃圾'
            ],
            swiperTips: [
                '可回收物指适宜回收利用和资源化利用的生活废弃物。',
                '厨余垃圾是指居民日常生活及食品加工、饮食服务、单位供餐等活动中产生的垃圾。',
                '有害垃圾指对人体健康或者自然环境造成直接或者潜在危害的生活废弃物。',
                '其他垃圾包括砖瓦陶瓷、渣土、卫生间废纸、瓷器碎片、动物排泄物、一次性用品等难以回收的废弃物。',
                '大件垃圾是指体积较大、整体性强，需要拆分再处理的废弃物品。'
            ],
            requirements: ['', '', '', '', ''],
            currentItem: 0,
            defaultIndex: 1,
            hasBigTrash: false,
            bodyColors: ['#274883', '#9f4342', '#4ba171', '#6f7774', '#e0ab40'],
            bodyColor: '#274883',
            searchMode: false,
            cityId: 0,
            searchResult: [],
            typeNames: [],
            showResult: false,
            inputValue: ''
        };
    },
    methods: {
        // 弹出选择栏
        popSelector() {
            this.$refs.popup.open();
        },
        // 用户点击取消，关闭选择栏
        onCancel() {
            this.$refs.popup.close();
        },
		
        // 用户点击确认，关闭选择栏并绑定数值
        onConfirm() {
            // 更改信息
            this.$data.selectedCity = this.$refs.picker.getValues()[0];
            this.$data.cityId = this.$refs.picker.getIndexes()[0];
            // 发送请求
            this.loadCityData();
            // 关闭弹出的选择栏
            this.$refs.popup.close();
            // 当current为4时自动跳转第一项
            if (this.currentItem === 4) {
                this.currentItem = 0;
            }
            // 如果搜索栏不为空,则变更城市id,再次发送请求
            if (this.inputValue.trim().length !== 0) {
                this.search({ detail: { value: this.inputValue } });
            }
        },
        // 改变初始index为用户默认选中的城市
        getDefaultIndex() {
            const index = this.$data.citiesList.findIndex(
                (e) => e === this.$data.selectedCity
            );
            if (index > 0) {
                this.$data.defaultIndex = index;
            }
            // 初始化选中的城市ID
            this.cityId = this.defaultIndex;
        },
        // swiper 被滑动
        onSwiperChange(event) {
            let index = event.detail.current;
            this.currentItem = index;
            this.$data.bodyColor = this.$data.bodyColors[index];
        },
        // 异步请求加载数据
        loadData() {
            this.loadCitiesData();
            this.loadCityData();
        },
        // 加载 Cities 数据项
        loadCitiesData() {
            axios.get('/cityinfo/cities').then((res) => {
                let data = res.data.data;
                this.citiesList = data.map((item) => item.cityName);
                this.getDefaultIndex();
            });
        },
        // 加载目标城市的分类信息
        loadCityData() {
            axios
                .post('/cityinfo/desc', {
                    cityName: this.selectedCity
                })
                .then((res) => {
                    // 判断该城市是否拥有大件垃圾
                    if (res.data.data.length === 5) {
                        this.hasBigTrash = true;
                    } else {
                        this.hasBigTrash = false;
                    }
                    // 根据 type 排序
                    res.data.data.sort((a, b) => {
                        return a.type - b.type;
                    });
                    let data = res.data.data;
                    // 载入数据
                    this.swiperTips = data.map((item) => item.description);
                    this.swiperTitles = data.map((item) => item.typeName);
                    this.requirements = data.map((item) => item.requirement);
                    // 分割字符串
                    this.requirements = this.requirements.map((item) =>
                        item.split('\n')
                    );
                });
        },
        // 搜索栏获得焦点
        searchBarFocused() {
            // 隐藏其他部件
            this.searchMode = true;
            this.bodyColor = null;
            this.bodyImage = `linear-gradient(rgba(107, 151, 226, 0.7), rgba(142, 209, 252, 0.7));`;
        },
        // 搜索栏失去焦点
        searchBarBlured(event) {
            // 当搜索栏不为空的时候显示部件
            if (event.target.value.length === 0) {
                this.searchMode = false;
                this.bodyColor = this.bodyColors[this.currentItem];
                this.bodyImage = null;
            }
        },
        // 搜索
        search(event) {
            let detail = event.detail;
            // 如果内容为空，则清空搜索结果框
            if (detail.value.trim().length == 0) {
                this.searchResult = [];
                this.showResult = false;
            } else {
                // 内容不为空，向后端发送请求
                axios
                    .post('/cityinfo/garbagesearch', {
                        cityID: this.cityId,
                        search: detail.value
                    })
                    .then((res) => {
                        this.searchResult = res.data.data;
                        if (res.data.data.length != 0) {
                            this.showResult = true;
                        } else {
                            this.showResult = false;
                        }
                    });
            }
        },
        // 跳转至 Detail 页面
        gotoDetail() {
            uni.navigateTo({
                url: `../sortInfo_details/sortInfo_details?index=${this.currentItem}`
            });
        },
        // 初始化vuex
        initStates() {
            this.$store.commit('setCityId', this.cityId);
            this.$store.commit('setClasses', this.swiperTitles);
            this.$store.commit('setCityName', this.selectedCity);
        },
    },
    // 实时更新Vuex状态
    watch: {
        cityId(newId, oldId) {
            this.$store.commit('setCityId', newId);
        },
        swiperTitles(newTitles, oldTitles) {
            this.$store.commit('setClasses', newTitles);
        },
        selectedCity(newCity, oldCity) {
            this.$store.commit('setCityName', newCity);
        }
    },
    mounted() {
        this.loadData();
        this.initStates();
    },
	onLoad() {
		uni.$once('cityLoaded',(city)=>{
			this.selectedCity = city;
			this.getDefaultIndex();
			this.loadCityData();
		})
	}
};
</script>

<style lang="less">
@import '../../common/style/sortInfo_home.less';
.header-bg-image {
		position:absolute;
		width:100vw;
		height:33vh;
	}
</style>
