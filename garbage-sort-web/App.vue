<script>
import { mapMutations } from 'vuex';
import { version } from './package.json';
import checkUpdate from '@/uni_modules/uni-upgrade-center-app/utils/check-update';
import axios from './static/utils/request.js';


export default {
	onLaunch: function() {
		// #ifdef H5
		console.log(
			`%c hello uniapp %c v${version} `,
			'background:#35495e ; padding: 1px; border-radius: 3px 0 0 3px;  color: #fff',
			'background:#007aff ;padding: 1px; border-radius: 0 3px 3px 0;  color: #fff; font-weight: bold;'
		);
		// #endif
		// 线上示例使用
		// console.log('%c uni-app官方团队诚邀优秀前端工程师加盟，一起打造更卓越的uni-app & uniCloud，欢迎投递简历到 hr2013@dcloud.io', 'color: red');
		console.log('App Launch');
		// #ifdef APP-PLUS
		// App平台检测升级，服务端代码是通过uniCloud的云函数实现的，详情可参考：https://ext.dcloud.net.cn/plugin?id=4542
		if (plus.runtime.appid !== 'HBuilder') {
			// 真机运行不需要检查更新，真机运行时appid固定为'HBuilder'，这是调试基座的appid
			checkUpdate();
		}

		// 一键登录预登陆，可以显著提高登录速度
		uni.preLogin({
			provider: 'univerify',
			success: res => {
				// 成功
				this.setUniverifyErrorMsg();
				console.log('preLogin success: ', res);
			},
			fail: res => {
				this.setUniverifyLogin(false);
				this.setUniverifyErrorMsg(res.errMsg);
				// 失败
				console.log('preLogin fail res: ', res);
			}
		});
		// #endif


		// 查询用户是否已经注册
		this.login();

		// 尝试获取用户定位

	},
	onShow: function() {},
	onHide: function() {
		console.log('App Hide');
	},
	globalData: {
		test: ''
	},
	methods: {
		...mapMutations(['setUniverifyErrorMsg', 'setUniverifyLogin']),
		login() {
			wx.login({
				success: res => {
					console.log('login', res);
					axios
						.post('/user/login', {
							code: res.code
						})
						.then(res => {
							this.$store.commit('user/setOpenId', res.data.data.openid);
							if (res.data.data.isSaved === '1') {
								this.$store.commit('user/setIsSaved', true);
								this.$store.dispatch('user/setUser', {
									nickName: res.data.data.userName,
									avatarUrl: res.data.data.avatar
								});
							} else {
								uni.showModal({
									title: '请求授权信息',
									content: '是否授权',
									success: res => {
										console.log(res);
										if (res.confirm) {
											uni.getUserProfile({
												desc: '您的个人信息将用于快速登录',
												lang: 'zh_CN',
												success: res => {
													// 设置用户信息
													if (res.userInfo) {
														this.$store.dispatch('user/setUser', res.userInfo);
														axios.post('/user/saveUser', {
															code: this.$store.state.user.openId,
															userName: res.userInfo.nickName,
															avatar: res.userInfo.avatarUrl
														});
														this.$store.commit('user/setIsSaved', true);
													}
												},
												fail: err => {
													console.log(err);
												}
											});
										} else if (res.cancel) {
											uni.getUserInfo({
												desc: '您的个人信息将用于快速登录',
												lang: 'zh_CN',
												success: res => {
													// 设置用户信息
													if (res.userInfo) {
														this.$store.dispatch('user/setUser', res.userInfo);
													}
												},
												fail: err => {
													console.log(err);
												}
											});
										}
									}
								});
							}
						});
				},
				fail: err => {
					this.$store.commit('user/setOpenId', 'o0A7N5PUiwU0xnj48RGFdqG1u51I');
				},
				complete: () => {
					uni.$emit('loginSuccess');
				}
			});
		}
	}
};
</script>

<style>
/* #ifndef APP-PLUS-NVUE */
/* uni.css - 通用组件、模板样式库，可以当作一套ui库应用 */
@import './common/animate.css' @import './common/uni.css';
@import '@/static/customicons.css';
/* H5 兼容 pc 所需 */
/* #ifdef H5 */
@media screen and (min-width: 768px) {
	body {
		overflow-y: scroll;
	}
}

/* 顶栏通栏样式 */
/* .uni-top-window {
	    left: 0;
	    right: 0;
	} */

uni-page-body {
	background-color: #f5f5f5 !important;
	min-height: 100% !important;
	height: auto !important;
}

.uni-top-window uni-tabbar .uni-tabbar {
	background-color: #fff !important;
}

.uni-app--showleftwindow .hideOnPc {
	display: none !important;
}

/* #endif */

/* 以下样式用于 hello uni-app 演示所需 */
page {
	background-color: #efeff4;
	height: 100%;
	font-size: 28 rpx;
	/* line-height: 1.8; */
}

.fix-pc-padding {
	padding: 0 50px;
}

.uni-header-logo {
	padding: 30 rpx;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	margin-top: 10 rpx;
}

.uni-header-image {
	width: 100px;
	height: 100px;
}

.uni-hello-text {
	color: #7a7e83;
}

.uni-hello-addfile {
	text-align: center;
	line-height: 300 rpx;
	background: #fff;
	padding: 50 rpx;
	margin-top: 10px;
	font-size: 38 rpx;
	color: #808080;
}

/* #endif*/
</style>
