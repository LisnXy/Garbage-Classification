/**
 * @description 用户模块
 * @author Lisn
 * @date 2022/4/2
 */

const state = {
    nickName: null,
    avatarUrl: null
};

const mutation = {
    setNickName(state, payload) {
        state.nickName = payload;
    },
    setAvatarUrl(state, payload) {
        state.avatarUrl = payload;
    }
};

const getters = {};

const actions = {};
