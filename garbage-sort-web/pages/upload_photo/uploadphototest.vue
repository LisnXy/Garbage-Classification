<template>
	<!-- 虚化背景 -->
	<page-meta :page-style="'overflow:'+(showPopup?'hidden':'visible')"></page-meta>
	<view class="root">
		<view class="header">
			<view class="header-caption" v-if="!ifChoosed">
				<text>123123123123213</text>
			</view>
			<image src="https://cdn.jsdelivr.net/gh/LisnXy/WxCDN/garbage-classification/images/upload-bg.png"
				mode="aspectFit" v-if="!ifChoosed" style="width: 100%;height:60%;"></image>
		</view>

		<!-- 照相机图标部分 -->
		<view class="main-container" :style="{height:containerHeight,position:containerPosition}">
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
				<uni-transition :duration="500" :mode-class="['slide-bottom','fade']" :show="showBtnContainer" @change="resAniHandler">
					<view class="button-container">
						<view class="single-btn" @click="UploadImage('single')"><text>单目标</text></view>
						<view class="multi-btn" @click="UploadImage('multi')"><text>多目标</text></view>
					</view>
				</uni-transition>
				<uni-transition :duration="500" mode-class="fade" :show="showResult">
					<scroll-view class="result-list-block" :scroll-y="true">
						<uni-list class="result-list">
							<uni-list-item v-for="(item, inde) in result" :key="inde" :title="item.label"
								:note="item.type" :rightText="item.Similarity" :clickable="false" :showArrow="false">
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
			<text>测试</text>
		</view>


		<!-- 普通弹窗 -->
		<view class="container">
			<!-- 返回结果部分 -->
			<uni-popup ref="popup" background-color="#fff" @change="change">
				<!-- 预计返回base64的图片 -->
				<view>
					<!-- <image class="resultImage" :src="'data:image/png;base64,{{baseImage}}'"></image> -->
					<!-- 用前端保存的图片测试 -->
					<image :src="imgList[0]" mode="aspectFit" class="resultImage"
						style="margin:auto;max-width: 100%;max-height: 40vh;">
					</image>
				</view>
				<!-- 预计返回预测结果列表 -->
				<scroll-view class="result-list-block" :scroll-y="true">
					<uni-list class="result-list">
						<uni-list-item v-for="(item, index) in result" :key="index" :title="item.label"
							:note="item.type" :rightText="item.Similarity" :clickable="false" :showArrow="false">
						</uni-list-item>
					</uni-list>
				</scroll-view>
			</uni-popup>
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
				showResult:false,
				uploaded:false,
				result: [{
						"label": "鼠标",
						"Similarity": "68.3%",
						"type": "可回收垃圾"
					},
					{
						"label": "鼠标垫",
						"Similarity": "49.5%",
						"type": "干垃圾"
					},
					{
						"label": "吸顶灯",
						"Similarity": "36.9%",
						"type": "有害垃圾"
					},
					{
						"label": "吸顶灯",
						"Similarity": "36.9%",
						"type": "有害垃圾"
					},
					{
						"label": "吸顶灯",
						"Similarity": "36.9%",
						"type": "有害垃圾"
					}
				]
			}
		},

		methods: {
			// 初始化
			initPage() {
				// clear imageList
				this.imgList = [];
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
				console.log("点击按键");
				uni.chooseImage({
					count: 1, //上传图片上限默认1
					sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
					sourceType: ['camera', 'album'], //从相机、相册选择
					success: (res) => {
						this.toggleContainerStyle();
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
						console.log(this.imgList);
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
			//图片上传
			UploadImage(type) {
				if(this.uploaded){
					return
				}else{
					this.uploaded = true;
				}
				let url = null;
				if (type === 'single') {
					url = 'http://localhost:3030/uploadImgAPP/getLabel'
				} else {
					// todo 多目标接口
					url = ''
				}
				//页面加载
				// uni.showLoading({
				// 	title: '加载中'
				// });
				//上传
				uni.uploadFile({
					url: "http://localhost:3030/uploadImgAPP/getLabel",
					fileType: "image", //ZFB必填,不然报错
					filePath: this.imgList[0],
					name: "imgFile", // 一定要与后台@RequestParam("imgFile") MultipartFile变量名一致
					success: (res) => {
						console.log(res);
						//结束加载，打开弹窗
						// //这里测试用2秒作为加载时长
						// setTimeout(function() {
						// 	uni.hideLoading();
						// 	this.showPopup = true;

						// 	this.$refs.popup.open('bottom');
						// },2000);
						// this.result = res.data.result;
						uni.hideLoading();
						// this.showPopup = true;
						// this.$refs.popup.open('bottom'); //可以自定义弹窗弹出方向
					},
					// test 
					complete: () => {
						this.hideButtonContainer();
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
			resAniHandler(e){
				if(e.detail===false){
					this.showResult = true;
					this.uploaded = false;
				}
			}
		},
	}
</script>

<style lang="less">
	@import url("../../common/style/upload_photo.less");
</style>
