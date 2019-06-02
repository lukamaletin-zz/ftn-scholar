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

const PaymentService = {
    signup: (data, success, error) => {
        return Vue.http.post(`/api/payment/signup`, data).then(success, error);
    },
    getRegisteredPaymentMethods: (success, error) => {
        return Vue.http.get(`/api/payment/registered-methods`).then(success, error);
    },
    getPaymentRegistrationUrl: (paymentMethod, success, error) => {
        return Vue.http.get(`/api/payment/url/${paymentMethod}`).then(success, error);
    },
    initPurchase: (data, success, error) => {
        return Vue.http.post(`/api/payment`, data).then(success, error);
    }
}

export default PaymentService;
