<template>
  <div id="content">
    <h1>Sign up</h1>
    <b-form @submit="submit" class="mt-4 mb-4">
      <div v-for="field of formFields" :key="field.id">
        <b-form-group :label="field.label">
          <b-form-input
            :type="field.label == 'Password' ? 'password' : field.label == 'Email' ? 'email' : 'text'"
            v-model="form[field.id]"
            required
            :placeholder="`Enter ${field.id}`"
          ></b-form-input>
        </b-form-group>
      </div>
      <b-btn type="submit" variant="primary" :block="true">Submit</b-btn>
    </b-form>
  </div>
</template>

<script>
import ProcessService from "../api/process-service.js";
export default {
  data() {
    return {
      formFields: [],
      form: {},
      processId: "",
      taskId: ""
    };
  },
  methods: {
    submit(e) {
      e.preventDefault();
      ProcessService.postForm(
        this.taskId,
        this.form,
        res => {
          this.$toastr.s("Registration successful!");
          this.$router.push("/signin");
        },
        res => {
          this.$toastr.e(res.data.error);
        }
      );
    }
  },
  created() {
    ProcessService.startProcess(
      "Process_user_registration",
      {},
      res => {
        this.processId = res.bodyText;
        ProcessService.getForm(
          this.processId,
          this.$route.name,
          res => {
            this.taskId = res.data.taskId;
            this.formFields = res.data.formFields;
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
