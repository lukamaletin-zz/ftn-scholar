<template>
  <div id="content">
    <h1>Check corrections</h1>
    <b-form @submit="submit" class="mt-4 mb-4">
      <b-btn variant="primary" :block="true" v-on:click="downloadPaper()">Download paper</b-btn>
      <b-form-group label="Author's reply" class="mt-4">
        <b-form-input type="text" v-model="metadata.reviewReply" readonly></b-form-input>
      </b-form-group>
      <b-form-group label="Action">
        <b-form-select v-model="action" required>
          <option v-for="action of actions" :key="action.id" :value="action.value">{{action.name}}</option>
        </b-form-select>
      </b-form-group>
      <b-form-group label="Corrections deadline" v-if="action == 'CORRECTIONS'">
        <b-form-input type="datetime-local" v-model="date" required></b-form-input>
      </b-form-group>
      <b-form-group label="Review  deadline" v-if="action == 'ADDITIONAL_REVIEW'">
        <b-form-input type="datetime-local" v-model="date" required></b-form-input>
      </b-form-group>
      <b-btn class="mt-4" type="submit" variant="success" :block="true">Proceed</b-btn>
    </b-form>
  </div>
</template>

<script>
import PaperService from "../api/paper-service.js";
import ProcessService from "../api/process-service.js";
import Util from "../util/util.js";
export default {
  data() {
    return {
      reviews: [],
      file: null,
      metadata: {},
      actions: Util.reviewActions.splice(0, 3),
      action: "",
      date: Util.newDeadline()
    };
  },
  methods: {
    submit(e) {
      e.preventDefault();

      const reviewers = [
        ...new Set(
          this.reviews
            .filter(r => r.reviewer.role != 'CHIEF_EDITOR')
            .map(r => r.reviewer.username))
      ];
      const variables = {
        paperCorrected: this.action,
        reviewers: reviewers
      };
      if (this.action == this.actions[1].value) {
        variables.deadlineDate = this.date;
      }

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
    downloadPaper() {
      const blob = new Blob([this.file], { type: "application/pdf" });
      saveAs(blob, `${this.$route.params["processId"]}.pdf`);
    },
    getGrade(review) {
      return review.grade != null ? Util.getGradeName(review.grade) : "Initial";
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

    PaperService.getReviews(
      this.$route.params["processId"],
      res => {
        this.reviews = res.data;
      },
      res => {
        this.$toastr.e(res.data.error);
      }
    );

    PaperService.getMetadata(
      this.$route.params["processId"],
      res => {
        this.metadata = res.data;
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
  padding-left: 30%;
  padding-right: 30%;
}
</style>
