<template>
  <div id="content">
    <h1>Reviews</h1>
    <b-form @submit="submit" class="mt-4 mb-4">
      <b-btn variant="primary" :block="true" v-on:click="downloadPaper()">Download paper</b-btn>
      <div class="mt-4" v-for="(review, index) of reviews" :key="review.id">
        <h4>Review {{index + 1}} ({{getGrade(review)}})</h4>
        <b-form-group
          :label="`Review from ${review.reviewer.firstName} ${review.reviewer.lastName}`"
        >
          <b-form-textarea v-model="review.content" rows="3" max-rows="3" readonly></b-form-textarea>
        </b-form-group>
        <b-form-group :label="`Comment for editor`">
          <b-form-textarea v-model="review.comment" rows="1" max-rows="1" readonly></b-form-textarea>
        </b-form-group>
        <hr>
      </div>
      <b-form-group label="Action">
        <b-form-select v-model="action" required>
          <option v-for="action of actions" :key="action.id" :value="action.value">{{action.name}}</option>
        </b-form-select>
      </b-form-group>
      <b-form-group label="Corrections deadline" v-if="action == 'CORRECTIONS'">
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
      actions: Util.reviewActions,
      action: "",
      date: Util.newDeadline()
    };
  },
  methods: {
    submit(e) {
      e.preventDefault();

      const variables = {};
      variables.isAdditionalReviewRequired =
        this.action == this.actions[1].value;
      if (!variables.isAdditionalReviewRequired) {
        variables.acceptPaper = this.action;
      }
      if (this.action == this.actions[2].value) {
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
  }
};
</script>

<style scoped>
#content {
  padding-left: 30%;
  padding-right: 30%;
}
</style>
