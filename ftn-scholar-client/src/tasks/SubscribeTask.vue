<template>
  <div id="content">
    <h1>Subscribe to {{journal != null ? journal.name : 'journal'}}?</h1>
    <b-btn variant="link" v-on:click="subscribe()">SUBSCRIBE (${{journal.price}})</b-btn>
    <p>You will get your next task once the subscription is fully completed.</p>
  </div>
</template>

<script>
import PaymentService from "../api/payment-service.js";
import ProcessService from "../api/process-service.js";
export default {
  data() {
    return {
      journal: this.$store.getters.getJournal
    };
  },
  methods: {
    subscribe() {
      PaymentService.initPurchase(
        { journal: this.journal, subscriptionBased: true },
        res => {
          window.open(res.bodyText);
          ProcessService.completeTask(
            this.$route.params["processId"],
            this.$route.name,
            res => {
              this.$router.push("/tasks");
            },
            res => {
              this.$toastr.e(res.data.error);
            }
          );
        },
        res => {
          this.$toastr.e(res.data.error);
        }
      );
    }
  }
};
</script>

<style scoped>
#content {
  padding-left: 30%;
  padding-right: 30%;
}
</style>
