import { createStore } from "vuex";

export default createStore({
    state: {
        authKey: null
    },
    mutations: {
        setAuthKey(state, authKey) {
            state.authKey = authKey
        }
    }
})


