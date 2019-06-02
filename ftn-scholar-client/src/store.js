import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        user: null,
        journal: null
    },
    getters: {
        getUsername: (state) => {
            return state.user ? state.user.username : '';
        },
        isAuthenticated: (state) => {
            return state.user ? state.user.role != '' : false;
        },
        isAuthor: (state) => {
            return state.user ? state.user.role == 'AUTHOR' : false;
        },
        isChiefEditor: (state) => {
            return state.user ? state.user.role == 'CHIEF_EDITOR' : false;
        },
        isFieldEditor: (state) => {
            return state.user ? state.user.role == 'FIELD_EDITOR' : false;
        },
        isReviewer: (state) => {
            return state.user ? state.user.role == 'REVIEWER' : false;
        },
        getPaymentServerUrl: () => {
            return 'https://localhost:8080';
        },
        getJournal: (state) => {
            return state.journal;
        }
    },
    mutations: {
        setUser: (state, value) => {
            state.user = value;
        },
        setJournal: (state, value) => {
            state.journal = value;
        }
    },
    actions: {
    }
})
