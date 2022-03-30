<template>
	<view class="test-root">
		<view class="test-header">
			<image src="../../static/image/test-bg.svg" mode=""></image>
		</view>
		<view class="test-body">
			<view class="test-body-header">
				<!-- @todo 绑定query-counter-->
				<text class="query-counter"><text>10</text>/10</text>
				<!-- @todo 绑定query-body-->
				<text class="query-body">
					<text class="query-item">铜线</text>
					应该放进哪个垃圾桶？
				</text>
			</view>
			<view class="test-body-answers">
				<view class="answer-card" style="background-color: #436BAD;">
					<image src="../../static/icons/可回收垃圾.png" mode="aspectFit"
					style="transform: scale(0.8);"></image>
					<text>可回收垃圾</text>
				</view>
				<view class="answer-card" style="background-color: #BA5353;">
					<image src="../../static/icons/有害垃圾.png" mode="aspectFit"></image>
					<text>有害垃圾</text>
				</view>
				<view class="answer-card" style="background-color: #64BD8C;">
					<image src="../../static/icons/厨余垃圾.png" mode="aspectFit"></image>
					<text>厨余垃圾</text>
				</view>
				<view class="answer-card" style="background-color: #849494;">
					<image src="../../static/icons/其他垃圾.png" mode="aspectFit"></image>
					<text>其他垃圾</text>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {

			}
		},
		methods: {

		}
	}
</script>

<style lang="less" scoped>
	@import url("../../common/style/sortTest.less");
</style>
