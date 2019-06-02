<template>
  <div id="content">
    <h1>Sign in</h1>
    <b-form @submit="submit" class="mt-4 mb-4">
      <b-form-group label="Username">
        <b-form-input
          type="text"
          v-model="form.username"
          required
          placeholder="Enter username"
          autofocus
        ></b-form-input>
      </b-form-group>
      <b-form-group label="Password">
        <b-form-input type="password" v-model="form.password" required placeholder="Enter password"></b-form-input>
      </b-form-group>
      <b-btn type="submit" variant="primary" :block="true">Submit</b-btn>
    </b-form>
  </div>
</template>

<script>
import ProcessService from "../api/process-service.js";
export default {
  data() {
    return {
      form: {
        username: "",
        password: ""
      }
    };
  },
  methods: {
    submit(e) {
      e.preventDefault();
      ProcessService.signin(
        this.form,
        res => {
          const authenticatedUser = res.data;
          localStorage.setItem(
            "authenticatedUser",
            JSON.stringify(authenticatedUser)
          );

          this.$store.commit("setUser", authenticatedUser.user);

          switch (authenticatedUser.user.role) {
            case "AUTHOR":
              this.$router.push("home");
              break;
            case "CHIEF_EDITOR":
              this.$router.push("settings");
              break;
            case "FIELD_EDITOR":
              this.$router.push("tasks");
              break;
            case "REVIEWER":
              this.$router.push("tasks");
              break;
            default:
              this.$router.push("/");
          }

          this.$toastr.s(`Welcome ${this.form.username}`);
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
  padding-left: 35%;
  padding-right: 35%;
}
</style>
