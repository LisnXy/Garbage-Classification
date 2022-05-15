/**
 * @description 用户模块
 * @author Lisn
 * @date 2022/4/2
 */

const state = {
    nickName: '',
    avatarUrl: null,
    openId: null,
	isSaved: false
};

const mutations = {
    setNickName(state, payload) {
        state.nickName = payload;
    },
    setAvatarUrl(state, payload) {
        state.avatarUrl = payload;
    },
    setOpenId(state, payload) {
        state.openId = payload;
    },
	setIsSaved(state,payload) {
		state.isSaved = payload;
	}
};

const getters = {};

const actions = {
    setUser(context, userInfo) {
        if (userInfo) {
            context.commit('setNickName', userInfo.nickName);
            context.commit('setAvatarUrl', userInfo.avatarUrl);
        }
    }
};

export default {
    namespaced: true,
    state,
    mutations,
    getters,
    actions
};
