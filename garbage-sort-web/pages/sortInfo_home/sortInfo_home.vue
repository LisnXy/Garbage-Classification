<template>
	<view class="root">
		<view class="header" style="background-image: url('../../static/image/垃圾分类.svg');">
			<view class="search">
				<view class="search-header" @click="popSelector">
					<text class="city-selector">{{selectedCity}}
					</text>
					<image src="../../static/image/下拉.svg" mode="scaleToFill"
						style="height: 28rpx;width: 28rpx;margin-left:30rpx"></image>
					<view class="border-fill"></view>
				</view>
				<view class="search-body">
					<view class="icon">
						<uni-icons type="search" size="20"></uni-icons>
					</view>
					<view class="search-input">
						<input type="text" placeholder="输入要搜索的物品(例电池)" style="width: 100%;" />
					</view>
				</view>
			</view>
		</view>
		<view id="body" class="body" :style="{'background-color':bodyColor}">
			<view class="cards-swiper">
				<swiper :indicator-dots="false" :autoplay="false" next-margin="50rpx" previous-margin="50rpx"
					ref='swiper' @change="onSwiperChange($event)">
					<swiper-item>
						<view class="swiper-item" style="background-color:#395a98 ;">
							<view class="swiper-icon">
								<image src="../../static/icons/可回收垃圾.png" mode="aspectFit"
									style="width: 100%;height: 50%;"></image>
							</view>
							<view class="swiper-content">
								<text class="title">可回收物</text>
								<view class="swiper-text">
									<text>
										可回收物指适宜回收利用和资源化利用的生活废弃物。
									</text>
								</view>
							</view>
						</view>
					</swiper-item>
					<swiper-item>
						<view class="swiper-item" style="background-color: #65ba8a;">
							<view class="swiper-icon">
								<image src="../../static/icons/厨余垃圾.png" mode="aspectFit"
									style="width: 100%;height: 50%;"></image>
							</view>
							<view class="swiper-content">
								<text class="title">厨余垃圾</text>
								<view class="swiper-text">
									<text>
										厨余垃圾是指居民日常生活及食品加工、饮食服务、单位供餐等活动中产生的垃圾。
									</text>
								</view>
							</view>
						</view>
					</swiper-item>
					<swiper-item>
						<view class="swiper-item" style="background-color:#b85555 ;">
							<view class="swiper-icon">
								<image src="../../static/icons/有害垃圾.png" mode="aspectFit"
									style="width: 100%;height: 50%;"></image>
							</view>
							<view class="swiper-content">
								<text class="title">有害垃圾</text>
								<view class="swiper-text">
									<text>
										厨余垃圾是指居民日常生活及食品加工、饮食服务、单位供餐等活动中产生的垃圾。
									</text>
								</view>
							</view>
						</view>
					</swiper-item>
					<swiper-item>
						<view class="swiper-item" style="background-color:#879696 ;">
							<view class="swiper-icon">
								<image src="../../static/icons/其他垃圾.png" mode="aspectFit"
									style="width: 100%;height: 50%;"></image>
							</view>
							<view class="swiper-content">
								<text class="title">其他垃圾</text>
								<view class="swiper-text">
									<text>
										其他垃圾包括砖瓦陶瓷、渣土、卫生间废纸、瓷器碎片、动物排泄物、一次性用品等难以回收的废弃物。
									</text>
								</view>
							</view>
						</view>
					</swiper-item>
					<swiper-item>
						<view class="swiper-item" style="background-color:#ebb852 ;">
							<view class="swiper-icon">
								<image src="../../static/icons/大件垃圾.png" mode="aspectFit"
									style="width: 120%;height: 50%;position: relative;right: 8px;"></image>
							</view>
							<view class="swiper-content">
								<text class="title">大件垃圾</text>
								<view class="swiper-text">
									<text>
										其他垃圾包括砖瓦陶瓷、渣土、卫生间废纸、瓷器碎片、动物排泄物、一次性用品等难以回收的废弃物。
									</text>
								</view>
							</view>
						</view>
					</swiper-item>
				</swiper>
			</view>
		</view>

		<uni-popup ref="popup" type="bottom" background-color="#fff">
			<view class="selector-container">
				<view class="selector-header">
					<uni-title type="h1" title="选择城市"></uni-title>
				</view>
				<view class="divider" />
				<view class="picker-container" ref="picker">
					<van-picker ref="picker" :columns="citiesList" style="height:100%;width: 100%;" @confirm="onConfirm"
						show-toolbar="true" @cancel="onCancel" :default-index="defaultIndex">
					</van-picker>
				</view>
			</view>
		</uni-popup>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				selectedCity: '北京市',
				citiesList: ['武汉市', '北京市', '上海市', '深圳市'],
				defaultIndex: 2,
				bodyColors: ["#274883", "#4ba171", "#9f4342", "#6f7774","#e0ab40"],
				bodyColor: "#274883",
			}
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
				this.$data.selectedCity = this.$refs.picker.getValues()[0];
				this.$refs.popup.close();
			},
			// 改变初始index为用户默认选中的城市
			getDefaultIndex() {
				const index = this.$data.citiesList.findIndex((e) =>
					e === this.$data.selectedCity
				);
				if (index > 0) {
					this.$data.defaultIndex = index;
				}
			},
			// swiper 被滑动
			onSwiperChange(event) {
				let index = event.detail.current;
				this.$data.bodyColor = this.$data.bodyColors[index];
			}
		},
		mounted() {
			this.getDefaultIndex();
		}
	}
</script>

<style>
	@import '../../common/style/sortInfo_home.less';
</style>
