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

const PaperService = {
    saveMetadata: (processId, data, success, error) => {
        return Vue.http.post(`/api/paper/${processId}`, data).then(success, error);
    },
    getMetadata: (processId, success, error) => {
        return Vue.http.get(`/api/paper/${processId}`).then(success, error);
    },
    saveFile: (processId, data, success, error) => {
        return Vue.http.post(`/api/paper/${processId}/file`, data).then(success, error);
    },
    getFile: (processId, success, error) => {
        return Vue.http.get(`/api/paper/${processId}/file`, {
            responseType: "arraybuffer"
        }).then(success, error);
    },
    saveReview: (processId, data, success, error) => {
        return Vue.http.post(`/api/paper/${processId}/review`, data).then(success, error);
    },
    getReviews: (processId, success, error) => {
        return Vue.http.get(`/api/paper/${processId}/review`).then(success, error);
    },
    getReviewers: (processId, success, error) => {
        return Vue.http.get(`/api/paper/${processId}/reviewers`).then(success, error);
    },
    saveReviewReply: (processId, data, success, error) => {
        return Vue.http.post(`/api/paper/${processId}/reviewReply`, data).then(success, error);
    }
}

export default PaperService;
