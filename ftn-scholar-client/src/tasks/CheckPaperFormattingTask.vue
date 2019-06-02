<template>
  <div id="content">
    <h1>Check paper formatting</h1>
    <b-row class="mt-4 mb-4">
      <b-col>
        <b-form-group label="Download paper to review its formatting">
          <b-btn variant="primary" :block="true" v-on:click="downloadPaper()">Download</b-btn>
        </b-form-group>
        <b-btn class="mt-4" variant="success" :block="true" v-on:click="submit(true)">Proceed</b-btn>
      </b-col>
      <b-col>
        <b-form-group label="Required corrections" class="mt-4">
          <b-form-textarea v-model="review.content" rows="5" max-rows="5" maxlength="999" required></b-form-textarea>
        </b-form-group>
        <b-form-group label="Corrections deadline">
          <b-form-input type="datetime-local" v-model="date" required></b-form-input>
        </b-form-group>
        <b-btn
          class="mt-4"
          variant="danger"
          :block="true"
          v-on:click="submit(false)"
        >Request corrections</b-btn>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import PaperService from "../api/paper-service.js";
import ProcessService from "../api/process-service.js";
import Util from "../util/util.js";
import saveAs from "file-saver";
export default {
  data() {
    return {
      file: null,
      date: Util.newDeadline(),
      review: {}
    };
  },
  methods: {
    submit(formatted) {
      const variables = { isPaperFormatted: formatted };
      if (!formatted) {
        variables.deadlineDate = this.date;

        PaperService.saveReview(
          this.$route.params["processId"],
          this.review,
          res => {
            ProcessService.completeTaskWithVariables(
              this.$route.params["processId"],
              this.$route.name,
              variables,
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
      } else {
        ProcessService.completeTaskWithVariables(
          this.$route.params["processId"],
          this.$route.name,
          variables,
          res => {
            this.$router.push("/tasks");
          },
          res => {
            this.$toastr.e(res.data.error);
          }
        );
      }
    },
    downloadPaper() {
      const blob = new Blob([this.file], { type: "application/pdf" });
      saveAs(blob, `${this.$route.params["processId"]}.pdf`);
    }
  },
  created() {
    PaperService.getFile(
      this.$route.params["processId"],
      res => {
        this.file = res.data;
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
