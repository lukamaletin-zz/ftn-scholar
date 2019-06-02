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

const ProcessService = {
    signin: (data, success, error) => {
        return Vue.http.post(`/api/auth/signin`, data).then(success, error);
    },
    startProcess: (processKey, data, success, error) => {
        return Vue.http.post(`/api/process/start/${processKey}`, data).then(success, error);
    },
    getForm: (processId, taskKey, success, error) => {
        return Vue.http.get(`/api/form/${processId}/${taskKey}`).then(success, error);
    },
    postForm: (taskId, data, success, error) => {
        return Vue.http.post(`/api/form/${taskId}`, data).then(success, error);
    },
    setProcessVariables: (processId, data, success, error) => {
        return Vue.http.post(`/api/process/${processId}/variables`, data).then(success, error);

    },
    completeTask: (processId, taskKey, success, error) => {
        return Vue.http.post(`/api/task/${processId}/${taskKey}/complete`).then(success, error);
    },
    completeTaskWithVariables: (processId, taskKey, data, success, error) => {
        return Vue.http.post(`/api/task/${processId}/${taskKey}/complete-variables`, data).then(success, error);
    },
    getTasks: (success, error) => {
        return Vue.http.get(`/api/task`).then(success, error);
    }
}

export default ProcessService;
