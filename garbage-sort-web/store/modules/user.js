/**
 * @description 用户模块
 * @author Lisn
 * @date 2022/4/2
 */

const state = {
    nickName: 'lisn',
    avatarUrl:
        'https://img2.baidu.com/it/u=3895119537,2684520677&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500'
};

const mutations = {
    setNickName(state, payload) {
        state.nickName = payload;
    },
    setAvatarUrl(state, payload) {
        state.avatarUrl = payload;
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
