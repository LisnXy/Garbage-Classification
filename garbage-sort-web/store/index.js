// #ifndef VUE3
import Vue from 'vue'
import Vuex from 'vuex'
import user from './modules/user'
Vue.use(Vuex)
const store = new Vuex.Store({
// #endif

// #ifdef VUE3
import { createStore } from 'vuex'
const store = createStore({
// #endif
	state: {
		// 城市的id
		cityId:0,
		//城市的名称
		cityName:'',
		// 垃圾的种类
		classes:null,
	},
	mutations: {
		// 设置垃圾的种类
		setClasses(state,newClasses){
			state.classes = newClasses;
		},
		// 设置城市的Id
		setCityId(state,cityId){
			state.cityId = cityId;
		},
		// 设置城市的名称
		setCityName(state,cityName){
			state.cityName = cityName;
		}
	},
	getters: {

	},
	actions: {
			
	},
	modules: {
		user:user
	}
})

export default store
