<template>
	<view class="tabBar-root">
		<view class="tabBar-item" @click="switchTab(0)">
			<image :src="iconPath(0)" mode="scaleToFill" class="aside-image"></image>
			<text>主页</text>
		</view>
		<view class="tabBar-item" @click="switchTab(1)">
			<image :src="iconPath(1)" mode="scaleToFill" class="mid-btn"></image>
			<text style="position: relative; top: 15px">
				搜索
			</text>
		</view>
		<view class="tabBar-item" @click="switchTab(2)">
			<image :src="iconPath(2)" mode="scaleToFill" class="aside-image"></image>
			<text>个人中心</text>
		</view>
	</view>
</template>

<script>
	export default {
		name: 'tabBar',
		data() {
			return {
				list: [{
						pagePath: '../../pages/sortInfo_home/sortInfo_home',
						iconPath: 'https://lisncloud-1311502437.cos.ap-shanghai.myqcloud.com/lanchao/icons/home_off.svg',
						selectedIconPath: 'https://lisncloud-1311502437.cos.ap-shanghai.myqcloud.com/lanchao/icons/home_on.svg',
						text: '主页'
					},
					{
						pagePath: '../../pages/upload_photo/uploadphototest',
						iconPath: 'https://lisncloud-1311502437.cos.ap-shanghai.myqcloud.com/lanchao/icons/Search_off.svg',
						selectedIconPath: 'https://lisncloud-1311502437.cos.ap-shanghai.myqcloud.com/lanchao/icons/Search_on.svg',
						text: '搜索'
					},
					{
						pagePath: '../../pages/userPage/userPage',
						iconPath: 'https://lisncloud-1311502437.cos.ap-shanghai.myqcloud.com/lanchao/icons/user_off.svg',
						selectedIconPath: 'https://lisncloud-1311502437.cos.ap-shanghai.myqcloud.com/lanchao/icons/account_on.svg',
						text: '个人中心'
					}
				]
			};
		},
		props: {
			selectedIndex: Number
		},
		computed: {},
		methods: {
			switchTab(itemIndex) {
				uni.switchTab({
					url: this.list[itemIndex].pagePath,
					success: () => {
						this.$emit('switch-tab', itemIndex);
					},
					fail(err) {
						console.log(err);
					}
				});
			},
			iconPath(itemIndex) {
				if (this.selectedIndex === itemIndex) {
					return this.list[itemIndex].selectedIconPath;
				} else {
					return this.list[itemIndex].iconPath;
				}
			}
		}
	};
</script>

<style lang="less">
	
	
	.tabBar-root {
		background-color: #fff;
		width: 100%;
		align-self: flex-end;
		display: flex;
		justify-content: space-around;
		height: 80px;
		border-top:1px solid #efefef;
	}

	.tabBar-item {
		position: relative;
		flex: 1;
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		image {
			width: 30px;
			height: 30px;
		}

		text {
			display: block;
			font-size:0.8rem;
		}
	}

	.mid-btn {
		border-radius: 50%;
		width: 50px !important;
		height: 50px !important;
		position: absolute;
		bottom: 40%;
		background-color: #fff;
		padding: 0.7rem;
		border-top:1px solid #efefef;
	}

	.aside-image {
		position: relative;
		bottom: 10px;
	}
</style>
