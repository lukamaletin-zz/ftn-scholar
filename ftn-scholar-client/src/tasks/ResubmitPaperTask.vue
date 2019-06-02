<template>
  <div id="content">
    <h1>Submit paper</h1>
    <b-form @submit="submit" class="mt-4 mb-4">
      <div v-for="(review, index) of reviews" :key="review.id">
        <b-form-group :label="`Review ${index + 1}`">
          <b-form-textarea v-model="review.content" rows="5" max-rows="5" readonly></b-form-textarea>
        </b-form-group>
      </div>
      <b-form-group label="Reply" v-if="offerReply">
        <b-form-input type="text" v-model="reviewReply" required placeholder="Enter reply"></b-form-input>
      </b-form-group>
      <b-form-group label="Attachment">
        <b-form-file
          v-model="file"
          accept=".pdf"
          required
          :state="Boolean(file)"
          placeholder="Upload PDF..."
          drop-placeholder="Upload PDF..."
        />
      </b-form-group>
      <b-btn type="submit" variant="primary" :block="true">Submit</b-btn>
    </b-form>
  </div>
</template>

<script>
import PaperService from "../api/paper-service.js";
import ProcessService from "../api/process-service.js";
export default {
  data() {
    return {
      reviews: [],
      file: null,
      offerReply: false,
      reviewReply: "/"
    };
  },
  methods: {
    submit(e) {
      e.preventDefault();

      const input = new FormData();
      input.append("file", this.file);
      PaperService.saveReviewReply(
        this.$route.params["processId"],
        this.reviewReply,
        res => {
          PaperService.saveFile(
            this.$route.params["processId"],
            input,
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
        res => {
          this.$toastr.e(res.data.error);
        }
      );
    }
  },
  created() {
    this.offerReply = this.$route.name == "Task_make_paper_corrections";

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
