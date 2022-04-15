<template>
	<view class="user-root">
		<view class="user-header">
			<view class="user-name-container">
				<text class="user-name"><text>Hi，</text>{{ userName }}</text>
			</view>
			<view class="avatar-container">
				<image class="avatar" :src="avatarUrl" mode="scaleToFill"></image>
			</view>
		</view>
		<view class="user-body">
			<view class="member-card-container">
				<view class="member-card">
					<view class="main-line">
						<text>我的积分</text>
						<text style="font-weight: 700">{{ score }}</text>
					</view>
					<view class="sub-line">
						<text>
							您对垃圾分类知识的了解超过了{{ percentage }}%的用户
						</text>
					</view>
				</view>
			</view>
			<view class="cards-container">
				<view class="card" @click="goTestPage">
					<image src="../../static/icons/问答.svg" mode="scaleToFill" style="background-color: #f7eff2"></image>
					<text>知识问答</text>
				</view>
				<view class="card">
					<image src="../../static/icons/商城.svg" mode="scaleToFill" style="background-color: #eeeff7"></image>
					<text>积分商城</text>
				</view>
				<view class="card chart-container">
					<view class="empty" v-if="!renderChart">
						<image src="../../static/image/empty.svg"></image>
						<text>暂无统计数据</text>
					</view>
					<view class="chart" v-if="renderChart">
						<qiun-data-charts type="pie" :chartData="pieData" style="height: 100%; width: 100%" />
					</view>
					<view class="chart-caption" v-if="renderChart">
						<text>
							你对
							<text :style="{ color: weakColor }" style="margin: 0 0.5rem">
								{{ weakItem }}
							</text>
							的了解较弱
						</text>
					</view>
				</view>
			</view>
		</view>
		<tab-bar :selected-index="2" style="width: 100%; position: absolute; bottom: 0"></tab-bar>
	</view>
</template>

<script>
	import axios from '../../static/utils/request.js';

	// 控制重新连接的次数

	export default {
		components: {},

		data() {
			return {
				pieData: {
					series: []
				},
				score: '',
				percentage: '',
				weakItem: '',
				renderChart: true
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
			},
			/**
			 * 接收参数，修改数据
			 */
			setData(data) {
				this.score = data.score;
				this.percentage = (parseFloat(`${data.surpassPercent}`)*100).toFixed(1);
				// 判断是否有数据
				if (data.recordInfo) {
					this.pieData.series = Object.keys(data.recordInfo).map(
						(item) => {
							return {
								name: this.mapName(item),
								data: data.recordInfo[item]
							};
						}
					);
					this.renderChart = true;
					this.weakItem = this.findWeak();
				} else {
					this.renderChart = false;
				}
			},
			/**
			 * 获得数据
			 */
			getData() {
				axios
					.get('/user/userInfo', {
						params: {
							openID: this.$store.state.user.openId
						}
					})
					.then((res) => {
						this.setData(res.data.data);
					})
					.catch((err) => {
						setTimeout(() => {
							axios
								.get('/user/userInfo', {
									params: {
										openID: this.$store.state.user.openId
									}
								})
								.then((res) => {
									this.setData(res.data.data);
								})
						}, 500);
					});
			},
			/**
			 * 名称映射，用于 setData
			 */
			mapName(name) {
				switch (name) {
					case 'otherPercent':
						return '其他垃圾';
					case 'harmfulPercent':
						return '有害垃圾';
					case 'recyclePercent':
						return '可回收垃圾';
					case 'kitchenPercent':
						return '厨余垃圾';
					default:
						return null;
				}
			},
			/**
			 * 找到数值最大的垃圾类别
			 * @returns {String} 垃圾类别名称
			 */
			findWeak() {
				if (this.pieData.series.length) {
					let weakItem = this.pieData.series[0];
					for (const item of this.pieData.series) {
						if (item.data >= weakItem.data) {
							weakItem = item;
						}
					}
					return weakItem.name;
				}
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
			},

			weakColor() {
				switch (this.weakItem) {
					case '有害垃圾':
						return '#e97a7a';
					case '可回收垃圾':
						return '#6b9ffe';
					case '厨余垃圾':
						return '#82e0ac';
					default:
						return '#adc8c8';
				}
			}
		},
		onShow() {
			this.getData();
		}
	};
</script>

<style lang="less" scoped>
	@import url('../../common/style/userPage');
</style>
