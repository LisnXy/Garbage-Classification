<template>
	<view class="root">
		<uni-nav-bar :fixed="true" :height="height" :border="false" :backgroundColor="selectColor" @clickLeft="navBack">
			<view slot="left" class="nav-icon">
				<uni-icons type="back" color="#FFF" :size="iconSize" style="position: absolute;" :style="{top:iconPosition}">
				</uni-icons>
			</view>
			<view class="nav-title" slot="default">
				<text :style="{top:iconPosition}">{{cityName}} • 垃圾查询</text>
			</view>
		</uni-nav-bar>
		<!-- 选择栏 -->
		<view class="details-selectBox" :style="{'background-color':selectColor}">
			<view class="items-container">
				<view v-for="(item,index) in swiperTitles" :key="index" class="details-selectItems">
					<view class="details-selectItemTitle" @click="selectGarbageType(index)">
						<text class="nav-text">{{item}}</text>
					</view>
				</view>
				<!-- 选择指示框 -->
			</view>
			<view class="select-indicator" :style="{left:selectedIndicatorPosition,'background-color':selectedIndicatorColor,'margin-left':selectedIndicatorMargin,'width':selectedIndicatorLength}">
			</view>
		</view>
		<!-- 页面主体部分 -->
		<view class="details-body" :style="{'background-color':bodyColor}">
			<!-- 上方icon栏目部分 -->
			<view class="trash-icons" :style="{'background-color':selectColor}">
				<view class="trash-icon-container" v-for="(item,index) in iconSource" :key="index">
					<image :src="`../../static/trash-icons/${item}.svg`" mode=""></image>
					<text>{{item}}</text>
				</view>
			</view>
			<!-- 列表部分 -->
			<scroll-view class="details-list-block" :scroll-y="true" @scrolltolower="getGarbageList()">
				<uni-list class="details_list">
					<uni-list-item v-for="(item,index) in garbageDetailsList" :key="index" :title="item"
						:clickable="false" :showArrow="false">
					</uni-list-item>
				</uni-list>
			</scroll-view>
		</view>
	</view>
</template>

<script>
	import axios from '../../static/utils/request.js';
	export default {
		data() {
			return {
				bodyColors: ["#274883", "#9f4342", "#4ba171", "#6f7774", "#e0ab40"],
				bodyColor: "#274883",
				selectColors: ["#395a98", "#b85555", "#65ba8a;", "#879696", "#ebb852"],
				selectColor: "#395a98",
				indicatorColors:['#6b97e2','#d97978','#8dd1a9',"#dce6ec","#ffc859"],
				swiperTitles: ["可回收物", "厨余垃圾", "有害垃圾", "其他垃圾"],
				garbageDetailsList: [],
				cityID: 0,
				cityName:'',
				garbageType: 1,
				pageIndex: 1,
				hasBigTrash: false,
				currentItem:0,
				selectedIndicatorMargin:'1px',
				selectedIndicatorLength: '1px',
				trashIconSourceMap:{
					harmful:['过期化妆品','废弃温度计','过期药品','废旧灯泡','废旧小家电','蓄电池'],
					leftover:['剩菜剩饭','残枝落叶','果壳','果皮','菜梗菜叶','鱼骨'],
					recyclable:['玻璃类','废纸类','牛奶盒','织物类','金属类','塑料类'],
					other:['宠物粪便','一次性餐具','女性卫生用品','破旧陶瓷品','烟头','污染纸张'],
					bigItems:['椅子','柜子','桌子','床','沙发','门']
				}
			}
		},
		methods: {
			//点击切换垃圾类型
			selectGarbageType(index, switched) {
				//修改主题色,如果是页面间跳转，则取消动画效果
				this.bodyColor = this.bodyColors[index];
				this.selectColor = this.selectColors[index];
				if(this.currentItem!==index){
					this.currentItem = index;
					//初始化垃圾数据
					this.garbageDetailsList = [];
					this.pageIndex = 1;
					//更新垃圾类型并加载数据
					this.garbageType = index + 1;
					this.getGarbageTypeDetails();
				}
			},
			//获取垃圾数据
			getGarbageTypeDetails() {
				//加载垃圾分类数据(默认每页20条)
				axios.post('/cityinfo/garbagetype?pageSize=20&pageNum=' + this.pageIndex, {
					cityID: this.cityID,
					type: this.garbageType,
				}).then(res => {
					let data = res.data.data.records;
					let str = data.map(item => item.garbageName);
					//将新获取的20条数据加载到垃圾详情数组中
					this.garbageDetailsList = [...this.garbageDetailsList, ...str];
				})
			},
			//下拉获取新数据
			getGarbageList() {
				//判断数据是否加载完成
				if(this.isOver){
						uni.showToast({
							title: '没有更多了'
						})
				}else {
					//每加载一页令页数+1
					this.pageIndex++;
					this.getGarbageTypeDetails();
				}
			},
			// 每次跳转到该页面，都从 Vuex 中重新取数据
			initData() {
				this.cityID = this.$store.state.cityId;
				this.swiperTitles = this.$store.state.classes;
				this.cityName = this.$store.state.cityName;
			},
			// 返回页面
			navBack(){
				uni.navigateBack({});
			},
			// 动态设置margin 和 长度
			setMargin(){
				const query = wx.createSelectorQuery();
				query.select('.nav-text').boundingClientRect();
				query.select('.details-selectItems').boundingClientRect().exec((res)=>{
					const margin = (((res[1].width-res[0].width)/2).toFixed(1) + 'px');
					const width = res[0].width.toFixed(1) + 'px';
					this.selectedIndicatorLength = width;
					this.selectedIndicatorMargin = margin;
				});
			}
		},
		computed: {
			//计算自定义导航栏的高度
			height() {
				const data = wx.getMenuButtonBoundingClientRect();
				return data.bottom + 10 + 'px';
			},
			//动态计算返回图标的位置
			iconPosition() {
				const data = wx.getMenuButtonBoundingClientRect();
				return data.top + 'px';
			},
			//动态计算返回图标的大小
			iconSize() {
				const data = wx.getMenuButtonBoundingClientRect();
				return data.height;
			},
			//列表数据是否全部加载完成
			isOver(){
				return this.garbageDetailsList.length < this.pageIndex * 20;
			},
			//指示器的位置
			selectedIndicatorPosition(){
				let classesNum = this.swiperTitles.length;
				return this.currentItem*(100/classesNum)+'vw';
			},
			//指示器的颜色
			selectedIndicatorColor(){
				return this.indicatorColors[this.currentItem];
			},
			//Icon图标的资源加载
			iconSource(){
				switch(this.currentItem){
					case 0:return this.trashIconSourceMap.recyclable;
					case 1:return this.trashIconSourceMap.harmful;
					case 2:return this.trashIconSourceMap.leftover;
					case 3:return this.trashIconSourceMap.other;
					case 4:return this.trashIconSourceMap.bigItems;
					default:return null;
				}
			}
		},
		mounted() {
			//初始化加载数据
			this.initData();
			this.getGarbageTypeDetails();
			this.setMargin();
		},
		onLoad(data) {
			this.initData();
			wx.setNavigationBarColor({
				frontColor: '#ffffff',
				backgroundColor: this.selectColors[data.index],
			});
			this.currentItem = parseInt(data.index);
			this.selectGarbageType(parseInt(data.index));
		},
	}
</script>

<style>
	@import url("../../common/style/sortInfo_details.less");
</style>
