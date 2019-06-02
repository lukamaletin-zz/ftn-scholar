<template>
  <div id="content">
    <h1>Select reviewers</h1>
    <b-form @submit="submit" class="mt-4 mb-4">
      <b-form-group :label="`Select at least two reviewers for a paper in ${metadata.field}`">
        <b-form-select multiple :select-size="8" v-model="selected" :options="options"/>
      </b-form-group>
      <b-form-group label="Review deadline">
        <b-form-input type="datetime-local" v-model="date" required></b-form-input>
      </b-form-group>
      <b-btn type="submit" variant="primary" :block="true">Submit</b-btn>
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
      metadata: {},
      fields: Util.scientificFields,
      options: [],
      selected: [],
      date: Util.newDeadline()
    };
  },
  methods: {
    submit(e) {
      e.preventDefault();

      if (this.selected.length == 0) {
        return;
      }

      ProcessService.completeTaskWithVariables(
        this.$route.params["processId"],
        this.$route.name,
        { reviewers: this.selected, deadlineDate: this.date },
        res => {
          this.$router.push("/tasks");
        },
        res => {
          this.$toastr.e(res.data.error);
        }
      );
    }
  },
  created() {
    PaperService.getMetadata(
      this.$route.params["processId"],
      res => {
        this.metadata = res.data;
        this.metadata.field = Util.getFieldName(this.metadata.field);
        PaperService.getReviewers(
          this.$route.params["processId"],
          res => {
            this.options = res.data.map(r => {
              r.expertise = r.expertise.map(e => Util.getFieldName(e));
              return {
                value: r.username,
                text: `${r.firstName} ${r.lastName} (${r.expertise.join(", ")})`
              };
            });
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
};
</script>

<style scoped>
#content {
  padding-left: 35%;
  padding-right: 35%;
}
</style>
