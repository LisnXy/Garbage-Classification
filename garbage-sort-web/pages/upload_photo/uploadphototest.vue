<template>
	<!-- 虚化背景 -->
	<page-meta :page-style="'overflow:'+(showPopup?'hidden':'visible')"></page-meta>
	<view class="root">
		<view class="header">
			<view class="header-caption" v-if="!ifChoosed">
				<text style="font-size: 2.5rem;font-weight: 600;letter-spacing: 8px;">智享分类</text>
				<text style="font-size: 1.1rem;margin-top: 0.5rem;letter-spacing: 2px;">智能垃圾识别 助你分类无忧</text>
			</view>
			<image src="https://cdn.jsdelivr.net/gh/LisnXy/WxCDN/garbage-classification/images/upload-bg.png"
				mode="aspectFit" v-if="!ifChoosed" style="width: 100%;height:60%;position:relative;bottom: 20px;">
			</image>
		</view>

		<!-- 照相机图标部分 -->
		<view class="main-container" :style="{height:containerHeight,position:containerPosition}">
			<view class="dropdown-btn-container" v-if="ifChoosed" @click="initPage">
				<image src="../../static/icons/dropdown.svg" mode="aspectFit"></image>
			</view>
			<view id="breath-btn" @click="ChooseImage()" v-if="!ifChoosed">
				<view id="inner" class="white-border">
					<image src="../../static/image/照相机.svg" mode="aspectFit">
					</image>
				</view>
			</view>
			<!-- 展示预选图部分 -->
			<view class="bg-img" v-for="(item,index) in imgList" :key="index" :data-url="imgList[index]">
				<image :src="imgList[index]" mode="aspectFit" style="width:100%;max-height: 100%;" @tap="ViewImage">
				</image>
				<view class="cu-tag bg-red" @tap.stop="DelImg" :data-index="index">
					<text class='cuIcon-close'></text>
				</view>
				<uni-transition :duration="500" :mode-class="['slide-bottom','fade']" :show="showBtnContainer" style="width: 100%;display: flex;justify-content: center;"
					@change="resAniHandler">
					<view class="reupload-btn-container">
						<view class="reupload-btn">
							<image src="../../static/icons/刷新.svg" mode="aspectFit" @click="ChooseImage"></image>
						</view>
					</view>
					<view class="button-container">
						<view class="single-btn" @click="UploadImage('single')"><text style="font-weight:500;">单目标<text style="color:gray;font-size:0.7rem;margin-left:5px;">快速识别</text></text></view>
						<view class="multi-btn" @click="UploadImage('multi')"><text style="font-weight:500;">多目标<text style="color:gray;font-size:0.7rem;margin-left:5px;">检测识别</text></text></view>
					</view>
				</uni-transition>
				<uni-transition :duration="500" mode-class="fade" :show="showResult" style="width:100%;">
					<scroll-view class="result-list-block" :scroll-y="true">
						<uni-list class="result-list">
							<uni-list-item v-for="(item, inde) in result" :key="inde" :title="item.label"
								:note="item.Similarity" :clickable="false" :showArrow="false">
								<view slot="footer" class="list-item-icon"
									:style="{ color: bodyColors[item.type - 1] }">
									{{ mapName(item.type)}}
								</view>
							</uni-list-item>
						</uni-list>
					</scroll-view>
				</uni-transition>
			</view>
			<view class="solids" @tap="ChooseImage" v-if="imgList.length<1">
				<text class='cuIcon-cameraadd'></text>
			</view>
		</view>

		<view v-if="ifChoosed" class="priview-title">
			<text>T</text>
		</view>


		<tab-bar :selectedIndex="1" style="position:absolute;bottom: 0;width:100%;z-index: 2;" @switch-tab="initPage">
		</tab-bar>
	</view>
</template>

<script>
	import axios from '../../static/utils/request.js';
	export default {
		data() {
			return {
				showPopup: false,
				ifChoosed: false,
				uploadType: "单目标识别",
				containerHeight: '250px',
				containerPosition: 'relative',
				imgList: [],
				imgMaxNum: 1,
				baseImage: '',
				showBtnContainer: true,
				showResult: false,
				uploaded: false,
				result: [],
				bodyColors: ['#274883', '#9f4342', '#4ba171', '#6f7774', '#e0ab40'],
			}
		},

		methods: {
			// 初始化
			initPage() {
				// clear imageList
				this.imgList = [];
				// clear resultSet
				this.result = [];
				// close preview window
				this.ifChoosed = false;
				// init containerStyle
				this.containerHeight = '250px';
				// init buttonContainer 
				this.showBtnContainer = true;
				// init resultContainer
				this.showResult = false;
			},
			// 切换按钮容器的样式
			toggleContainerStyle() {
				if (this.containerHeight === '250px') {
					this.containerHeight = '70%;'
				} else {
					this.containerHeight = '250px;'
				}
			},
			//图片选择
			ChooseImage() {
				uni.chooseImage({
					count: 1, //上传图片上限默认1
					sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
					sourceType: ['camera', 'album'], //从相机、相册选择
					success: (res) => {
						console.log(this.ifChoosed);
						if (!this.ifChoosed) {
							this.toggleContainerStyle();
						}
						console.log(res);
						//返回图片url数组
						const tempFilePaths = res.tempFilePaths;
						//上传图片过多
						if (tempFilePaths.length > this.imgMaxNum) {
							uni.showToast({
								title: '超过上传图片的最大数量',
								icon: 'none'
							})
						} else {
							this.imgList = res.tempFilePaths;
							this.ifChoosed = true;
						}
					},
				});
			},
			//弹窗
			change(e) {
				this.showPopup = e.show;
			},
			// 隐藏图片预览界面的按钮
			hideButtonContainer() {
				this.showBtnContainer = false;
			},
			// 显示图片预览界面的按钮
			showButtonContainer() {
				this.showBtnContainer = true;
			},
			//图片上传
			UploadImage(type) {
				// 防止抖动
				if (this.uploaded) {
					return
				} else {
					this.uploaded = true;
				}
				// 判断类型
				let url = null;
				let isSingle = false;
				if (type === 'single') {
					url = `http://124.220.210.6:8000/predictConv`
					isSingle = true
				} else {
					url = `http://124.220.210.6:8000/predictYolo`
				}
				// 页面加载
				uni.showLoading({
					title: '加载中'
				});
				//上传
				uni.uploadFile({
					url: url,
					fileType: "image", //ZFB必填,不然报错
					filePath: this.imgList[0],
					name: "imgFile", // 一定要与后台@RequestParam("imgFile") MultipartFile变量名一致
					success: (res) => {
						console.log(res);
						const data = JSON.parse(res.data).data;
						if (isSingle) {
							// 处理单目标的返回数据
							this.result.push({
								"label": data.garbageName,
								"Similarity": `${(data.probability*100).toFixed(1)}%`,
								"type": data.garbageType
							})
						} else {
							// 处理多目标的返回数据
							this.result = data.label.map(item => {
								const props = item.split(/\s+/);
								return {
									"label": props[0],
									"Similarity": `${(props[1]*100).toFixed(0)}%`,
									"type": Number([props[2]])
								}
							})
							this.imgList[0] = `http://124.220.210.6:3030/images/${data.imgstr}`
						}
						uni.hideLoading();
						this.hideButtonContainer();
					},
					fail: () => {
						uni.showToast({
							title: "加载失败",
							icon: "error"
						});
					},
					complete: () => {
						this.uploaded = false;
					}
				});
			},
			//图片预览
			ViewImage(e) {
				uni.previewImage({
					urls: this.imgList,
					current: e.currentTarget.dataset.url
				});
			},
			//图片删除
			DelImg(e) {
				this.imgList.splice(e.currentTarget.dataset.index, 1)
			},
			// 处理动画效果
			resAniHandler(e) {
				if (e.detail === false) {
					this.showResult = true;
					this.uploaded = false;
				}
			},
			// 用于映射名称
			mapName(type) {
				switch (type) {
					case 1:
						return '可回收物';
					case 2:
						return '有害垃圾';
					case 3:
						return '厨余垃圾';
					case 4:
						return '其他垃圾';
					default:
						return null;
				}
			}
		},
	}
</script>

<style lang="less">
	@import url("../../common/style/upload_photo.less");
</style>
