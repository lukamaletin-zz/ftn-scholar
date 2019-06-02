<template>
  <div id="content">
    <h1>Review paper</h1>
    <b-form @submit="submit" class="mt-4 mb-4">
      <b-form-group label="Download paper to review it">
        <b-btn variant="primary" :block="true" v-on:click="downloadPaper()">Download</b-btn>
      </b-form-group>
      <b-form-group label="Review">
        <b-form-textarea v-model="review.content" rows="5" max-rows="5" maxlength="999" required></b-form-textarea>
      </b-form-group>
      <b-form-group label="Comment for editor">
        <b-form-textarea v-model="review.comment" rows="5" max-rows="5" maxlength="999" required></b-form-textarea>
      </b-form-group>
      <b-form-group label="Action">
        <b-form-select v-model="review.grade" required>
          <option v-for="grade of grades" :key="grade.id" :value="grade.value">{{grade.name}}</option>
        </b-form-select>
      </b-form-group>
      <b-btn type="submit" variant="success" :block="true">Proceed</b-btn>
    </b-form>
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
      review: {},
      grades: Util.reviewGrades
    };
  },
  methods: {
    submit(e) {
      e.preventDefault();

      PaperService.saveReview(
        this.$route.params["processId"],
        this.review,
        res => {
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
  padding-left: 30%;
  padding-right: 30%;
}
</style>
