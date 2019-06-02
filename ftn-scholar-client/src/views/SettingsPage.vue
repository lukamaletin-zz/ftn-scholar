<template>
  <div id="content">
    <h1>{{journal.name}}</h1>
    <div class="mt-5">
      <div v-if="!journal.paymentsEnabled">
        <h4>Connect with coin10 payment services:</h4>
        <b-form @submit="submit">
          <b-form-group label="Client ID">
            <b-form-input
              type="text"
              v-model="form.clientId"
              required
              placeholder="Enter client ID"
              autofocus
            ></b-form-input>
          </b-form-group>
          <b-btn type="submit" variant="primary" :block="true">Submit</b-btn>
        </b-form>
        <b-btn
          variant="link"
          v-on:click="redirect(`${paymentServerUrl}/#/signup`)"
        >Not registered yet? Sign up now on coin10</b-btn>
      </div>
      <div v-else>
        <h4>Journal is connected with coin10 payment services</h4>
        <div v-if="journal.paymentOption == 'OpenAccess'">
          <div v-for="pm of registeredPaymentMethods" :key="pm.method">
            <b-btn
              v-if="pm.subscriptionBased"
              variant="link"
              v-on:click="redirectToRegistrationForm(pm.method)"
            >Register {{pm.method}} payments</b-btn>
          </div>
        </div>
        <div v-else>
          <div v-for="pm of registeredPaymentMethods" :key="pm.method">
            <b-btn
              v-if="!pm.subscriptionBased"
              variant="link"
              v-on:click="redirectToRegistrationForm(pm.method)"
            >Register {{pm.method}} payments</b-btn>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import JournalService from "../api/journal-service.js";
import PaymentService from "../api/payment-service.js";
export default {
  data() {
    return {
      paymentServerUrl: this.$store.getters.getPaymentServerUrl,
      form: {},
      journal: {},
      registeredPaymentMethods: {}
    };
  },
  methods: {
    submit(e) {
      e.preventDefault();
      PaymentService.signup(
        this.form,
        res => {
          this.$toastr.s("Success! Register for payment methods next.");
          this.journal.paymentsEnabled = true;
          this.getRegisteredPaymentMethods();
        },
        res => {
          this.$toastr.e(res.data.error);
        }
      );
    },
    redirect(url) {
      window.open(url);
    },
    redirectToRegistrationForm(paymentMethod) {
      PaymentService.getPaymentRegistrationUrl(
        paymentMethod,
        res => {
          this.redirect(res.bodyText);
        },
        res => {
          this.$toastr.e(res.data.error);
        }
      );
    },
    getRegisteredPaymentMethods() {
      PaymentService.getRegisteredPaymentMethods(
        res => {
          this.registeredPaymentMethods = res.data.filter(p => !p.linked);
        },
        res => {
          this.$toastr.e(res.data.error);
        }
      );
    }
  },
  created() {
    const authenticatedUser = localStorage.getItem("authenticatedUser");
    if (!authenticatedUser) {
      this.$router.push("/signin");
    }

    const id = JSON.parse(authenticatedUser).user.id;
    JournalService.findByChiefEditorId(
      id,
      res => {
        this.journal = res.data;
        if (this.journal.paymentsEnabled) {
          this.getRegisteredPaymentMethods();
        }
      },
      res => {
        this.$toastr.e(res.data.error);
      }
    );
  }
};
</script>

<style scoped>
#content {
  padding-left: 25%;
  padding-right: 25%;
}
</style>
