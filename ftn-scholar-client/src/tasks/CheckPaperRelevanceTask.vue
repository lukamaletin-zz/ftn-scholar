<template>
  <div id="content">
    <h1>Check paper relevance</h1>
    <b-form class="mt-4 mb-4">
      <b-form-group label="Title">
        <b-form-input type="text" v-model="metadata.title" readonly></b-form-input>
      </b-form-group>
      <b-form-group label="Keywords">
        <b-form-input type="text" v-model="keywords" readonly></b-form-input>
      </b-form-group>
      <b-form-group label="Abstract">
        <b-form-textarea v-model="metadata.paperAbstract" rows="5" max-rows="5" readonly></b-form-textarea>
      </b-form-group>
      <b-form-group label="Scientific field">
        <b-form-input type="text" v-model="field" readonly></b-form-input>
      </b-form-group>
      <b-row>
        <b-col>
          <b-btn class="mt-4" variant="success" :block="true" v-on:click="submit(true)">Proceed</b-btn>
        </b-col>
        <b-col>
          <b-btn class="mt-4" variant="danger" :block="true" v-on:click="submit(false)">Reject</b-btn>
        </b-col>
      </b-row>
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
      keywords: "",
      field: ""
    };
  },
  methods: {
    submit(relevant) {
      ProcessService.completeTaskWithVariables(
        this.$route.params["processId"],
        this.$route.name,
        { isPaperRelevant: relevant },
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
        this.keywords = this.metadata.keywords.join(", ");
        this.field = Util.getFieldName(this.metadata.field);
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
