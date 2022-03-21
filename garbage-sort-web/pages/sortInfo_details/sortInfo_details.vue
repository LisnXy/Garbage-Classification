<template>
	<view class="root">
		<!-- 选择栏 -->
		<view class="details-selectBox" :style="{'background-color':selectColor}"> 
			<view v-for="(item,index) in swiperTitles" :key="index" class="details-selectItems">
				<view class="details-selectItemTitle" @click="selectGarbageType(index)">
					<text>{{item}}</text>
				</view>
			</view>
		</view>	
		<view class="details-body" :style="{'background-color':bodyColor}">
			<scroll-view class="details-list-block" 
						 :scroll-y="true" 
						 @scrolltolower="getGarbageList()">
				<uni-list class="details_list" style="height: 90%;">
					<uni-list-item v-for="(item,index) in garbageDetailsList" 
								   :key="index" 
								   :title="item"
								   clickable="true" 
								   showArrow="true" 
								   style="">
						<view v-if="isOver"><text>没有数据了</text></view>
					</uni-list-item>
				</uni-list>
			</scroll-view>
			<!-- <button class="addMoreButton">加载更多</button> -->
		</view>
	</view>
</template>

<script>
	import axios from '../../static/utils/request.js';
	export default {
		data(){
			return{
				bodyColors: ["#274883", "#2c5f42", "#843636", "#4e5452", "#9d782d"],
				bodyColor: "#274883",
				selectColors: ["#007AFF", "#4ba171", "#9f4342", "#6f7774", "#e0ab40"],
				selectColor: "#007AFF",
				swiperTitles: ["可回收物", "厨余垃圾", "有害垃圾", "其他垃圾"],
				garbageDetailsList:[],
				cityID: 0,
				garbageType: 1,
				pageIndex: 1,
				isOver: false,
				hasBigTrash: false,
			} 
		},
		methods:{
			//点击切换垃圾类型
			selectGarbageType(index){
				//修改主题色
				this.bodyColor = this.bodyColors[index];
				this.selectColor = this.selectColors[index];
				//初始化垃圾数据
				this.garbageDetailsList = [];
				this.pageIndex = 1;
				//更新垃圾类型并加载数据
				this.garbageType = index + 1;
				this.getGarbageTypeDetails();
				uni.setNavigationBarColor({
					backgroundColor: '#000000'
				})
			},
			//获取垃圾数据
			getGarbageTypeDetails(){
				//加载垃圾分类数据(默认每页20条)
				axios.post('/cityinfo/garbagetype?pageSize=20&pageNum='+this.pageIndex, {
					cityID: this.cityID,
					type: this.garbageType,	
				}).then(res=>{
					let data = res.data.data.records;
					let str = data.map(item => item.garbageName);
					console.log(str)
					//将新获取的20条数据加载到垃圾详情数组中
					this.garbageDetailsList = [...this.garbageDetailsList,...str];
					console.log(this.garbageDetailsList);
				})
			},
			//下拉获取新数据
			getGarbageList() {
				// console.log(this.pageIndex)
				//判断数据是否加载完成
				if(this.garbageDetailsList.length < this.pageIndex*20) {
					uni.showToast({
						title: '没有更多了'
					})
					this.isOver=true
					return 
				}
				console.log(this.isOver);
				//每加载一页令页数+1
				this.pageIndex++;
				this.getGarbageTypeDetails();
			},
		},
		//默认修改导航栏背景颜色
		// onNavigationBarButtonTap(e){
		// 	console.log(e.index);
		// 	if(e.index==0){
		// 		// #ifdef APP-PLUS  
		// 		const currentWebview = this.$mp.page.$getAppWebview(); 
		// 		currentWebview.setNavigationBarColor({
		// 			backgroundColor: '#4ba171'
		// 		})
		// 		// #endif	 
		// 	}
		// },
		mounted(){
			//初始化加载数据
			this.getGarbageTypeDetails();
		},
		onLoad() {
		}
	}
	
</script>

<style>
	@import url("../../common/style/sortInfo_details.less");
</style>
