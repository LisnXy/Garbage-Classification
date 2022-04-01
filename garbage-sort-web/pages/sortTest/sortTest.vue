<template>
	<view class="test-root">
		<view class="test-header">
			<image src="../../static/image/test-bg.svg" mode=""></image>
		</view>
		<view class="test-body">
			<view class="test-body-header">
				<text class="query-counter"><text>{{itemIndex}}</text>/10</text>
				<!-- @todo 绑定query-body-->
				<text class="query-body">
					<text class="query-item">{{currentItem.garbageName}}</text>
					应该放进哪个垃圾桶？
				</text>
			</view>
			<view class="test-body-answers">
				<view class="answer-card" style="background-color: #436BAD;" @click="answer(1)">
					<image src="../../static/icons/可回收垃圾.png" mode="aspectFit" style="transform: scale(0.8);"></image>
					<text>可回收垃圾</text>
				</view>
				<view class="answer-card" style="background-color: #BA5353;" @click="answer(2)">
					<image src="../../static/icons/有害垃圾.png" mode="aspectFit"></image>
					<text>有害垃圾</text>
				</view>
				<view class="answer-card" style="background-color: #64BD8C;" @click="answer(3)">
					<image src="../../static/icons/厨余垃圾.png" mode="aspectFit"></image>
					<text>厨余垃圾</text>
				</view>
				<view class="answer-card" style="background-color: #849494;" @click="answer(4)">
					<image src="../../static/icons/其他垃圾.png" mode="aspectFit"></image>
					<text>其他垃圾</text>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import axios from '../../static/utils/request.js';
	export default {
		data() {
			return {
				// 当前题目的索引
				itemIndex: 1,
				// 存储全部题目
				questionItems: [],
				// 存储错误的题目
				falseItems: [],
				// 存储正确的题目
				correctItems:[],
				// 当前题目,默认反馈为加载中
				currentItem: {
					garbageName: '加载中····'
				}
			}
		},
		methods: {
			// 获取题目
			getQuestions() {
				axios.get("/answer", {
					params: {
						cityID: this.$store.state.cityId
					}
				}).then((res) => {
					this.questionItems = res.data.data;
					this.currentItem = this.questionItems[0];
				}).catch(error => {
					console.log(error);
				})
			},
			// 判断回答是否正确
			answer(type) {
				// 反馈交互部分
				if (this.currentItem.type === type) {
					uni.showToast({
						title: '回答正确',
						icon: 'success',
						mask: true,
						position: 'top',
						duration:1000,
						success: () => {
							setTimeout(() => {
								this.correctItems.push(this.currentItem);
								this.nextItem();
							}, 1000)
						}
					});
				} else {
					uni.showModal({
						title: "回答错误",
						content: `正确答案为：${this.currentItemText}`,
						showCancel: false,
						success: () => {
							this.falseItems.push(this.currentItem);
							this.nextItem();
						}
					})
				}
			},
			//切换题目
			nextItem() {
				if (this.itemIndex < this.questionItems.length) {
					this.itemIndex++;
					this.currentItem = this.questionItems[this.itemIndex-1];
				}else{
					// 结束了则进行页面跳转
					uni.redirectTo({
						url:'../sortTestFinished/sortTestFinished',
						success: () => {
							console.log("emited");
							uni.$once("loaded",()=>{
								uni.$emit("questionItems",{
									falseItems:this.falseItems,
									correctItems:this.correctItems
								})
							})
						}
					})
				}
			}
		},
		computed: {
			// 将垃圾种类的id映射到类别
			currentItemText() {
				switch (Number(this.currentItem.type)) {
					case 1:
						return '可回收物';
					case 2:
						return '有害垃圾';
					case 3:
						return '厨余垃圾';
					default:
						return "其他垃圾";
				}
			}
		},
		mounted() {
			this.getQuestions();
		}
	}
</script>

<style lang="less" scoped>
	@import url("../../common/style/sortTest.less");
</style>
