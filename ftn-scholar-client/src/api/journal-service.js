import Vue from 'vue';
import VueResource from 'vue-resource';

Vue.use(VueResource);

Vue.http.interceptors.push((request, next) => {
    const authenticatedUser = localStorage.getItem("authenticatedUser");
    if (authenticatedUser) {
        request.headers.set('X-Auth-Token', JSON.parse(authenticatedUser).token)
    }
    next()
})

const JournalService = {
    findAll: (success, error) => {
        return Vue.http.get(`/api/journal`).then(success, error);
    },
    findById: (id, success, error) => {
        return Vue.http.get(`/api/journal/${id}`).then(success, error);
    },
    findByChiefEditorId: (id, success, error) => {
        return Vue.http.get(`/api/journal/chief/${id}`).then(success, error);
    }
}

export default JournalService;
