<template>
	<!-- 虚化背景 -->
	<page-meta :page-style="'overflow:'+(showPopup?'hidden':'visible')"></page-meta>
	<view class="root">		
		<!-- 类别 -->
		<!-- <view>{{this.uploadType}}</view> -->
		
		<!-- 照相机图标部分 -->
		<view id="breath-btn" @click="ChooseImage()" v-if="!ifChoosed">
			<view id="inner" class="white-border">
				<image src="../../static/image/照相机.png"
					   mode="aspectFit">
				</image>
			</view>
		</view>
		
		<view v-if="ifChoosed" class="priview-title">
			<text>测试</text>
		</view>
		<!-- 展示预选图部分 -->
		<uni-card class="priview-img-block" :cover="cover" v-if="ifChoosed">
			<view class="cu-form-group">
				<view class="grid col-4 grid-square flex-sub">
					<view class="bg-img" v-for="(item,index) in imgList" :key="index" @tap="ViewImage" :data-url="imgList[index]">
						<image :src="imgList[index]" 
							   mode="aspectFit"
							   style="margin:auto;max-width: 100%;max-height: 40vh;"
							   >
						</image>
						<view class="cu-tag bg-red" @tap.stop="DelImg" :data-index="index">
							<text class='cuIcon-close'></text>
						</view>
					</view>
					<view class="solids" @tap="ChooseImage" v-if="imgList.length<1">
						<text class='cuIcon-cameraadd'></text>
					</view>
				</view>
			</view>
			
			<!-- 分割 -->
			<view class="dividing-line"></view>
			
			<!-- 按钮部分 -->
			<view slot="actions" class="card-actions">
				<!-- 重选按钮 -->
				<view class="card-actions-item" @click="ChooseImage()">
					<button plain="true">
						<uni-icons type="refreshempty"  color="#999"></uni-icons>
						<text class="card-actions-item-text">重选</text>
					</button>					
				</view>
				<!-- 上传按钮 -->
				<view class="card-actions-item" @click="UploadImage()">
					<button plain="true">
						<uni-icons type="search"  color="#999"></uni-icons>
						<text class="card-actions-item-text">上传</text>
					</button>
				</view>
			</view>
		</uni-card>

		<!-- 普通弹窗 -->
		<view class="container">
			<!-- 返回结果部分 -->
			<uni-popup ref="popup" background-color="#fff" @change="change">
				<!-- 预计返回base64的图片 -->
				<view>
					<!-- <image class="resultImage" :src="'data:image/png;base64,{{baseImage}}'"></image> -->
					<!-- 用前端保存的图片测试 -->
					<image :src="imgList[0]"
						   mode="aspectFit"
						   class="resultImage"
						   style="margin:auto;max-width: 100%;max-height: 40vh;"
						   >
					</image>
				</view>
				<!-- 预计返回预测结果列表 -->
				<scroll-view
				    class="result-list-block"
				    :scroll-y="true"
				>
				    <uni-list class="result-list">
				        <uni-list-item
				            v-for="(item, index) in result"
				            :key="index"
				            :title="item.label"
							:note="item.type"
							:rightText="item.Similarity"
				            :clickable="false"
				            :showArrow="false"
				        >
						</uni-list-item>
				    </uni-list>
				</scroll-view>
			</uni-popup>
		</view>
		<tab-bar :selectedIndex="1" style="position:absolute;bottom: 0;width:100%;" @switch-tab="initPage"></tab-bar>
	</view>
</template>

<script>
	import axios from '../../static/utils/request.js';
	// import breathBtn from '../../components/breath-btn/breath-btn.vue';
	export default {
		data(){
			return {
				showPopup: false,
				ifChoosed: false,
				uploadType: "单目标识别",
				imgList: [],
				imgMaxNum: 1,
				baseImage: '',
				result: [
					{
						"label" : "鼠标",
						"Similarity" : "68.3%",
						"type" : "可回收垃圾"
					},
					{
						"label" : "鼠标垫",
						"Similarity" : "49.5%",
						"type" : "干垃圾"
					},
					{
						"label" : "吸顶灯",
						"Similarity" : "36.9%",
						"type" : "有害垃圾"
					},
					{
						"label" : "吸顶灯",
						"Similarity" : "36.9%",
						"type" : "有害垃圾"
					},
					{
						"label" : "吸顶灯",
						"Similarity" : "36.9%",
						"type" : "有害垃圾"
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
			},
			//图片选择
			ChooseImage() {
				console.log("点击按键");
				uni.chooseImage({
					count: 1, //上传图片上限默认1
					sizeType: ['original','compressed'], //可以指定是原图还是压缩图，默认二者都有
					sourceType: ['camera','album'], //从相机、相册选择
					success: (res) => {
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
					// new
					complete:()=>{
						this.ifChoosed = true;
					}
				});
			},
			//弹窗
			change(e) {
				this.showPopup = e.show
			},
			//图片上传
			UploadImage() {
				//页面加载
				uni.showLoading({
					title: '加载中'
				});
				//上传
				uni.uploadFile({
					url: "http://localhost:3030/uploadImgAPP/getLabel",
					fileType:"image",//ZFB必填,不然报错
					filePath: this.imgList[0], 
					name: "imgFile", // 一定要与后台@RequestParam("imgFile") MultipartFile变量名一致
					success:(res) => {
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
						this.showPopup = true;
						this.$refs.popup.open('bottom'); //可以自定义弹窗弹出方向
						
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
		}
	}
</script>

<style>
	@import url("../../common/style/upload_photo.less");
</style>
