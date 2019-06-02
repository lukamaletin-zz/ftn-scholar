<template>
  <div>
    <div>
      <b-navbar toggleable="md" type="dark" variant="dark" fixed="top">
        <b-navbar-toggle target="nav_collapse"></b-navbar-toggle>
        <b-navbar-brand to="/">FTN scholar</b-navbar-brand>
        <b-collapse is-nav id="nav_collapse">
          <b-navbar-nav>
            <b-nav-item v-if="isChiefEditor()" to="/settings">Settings</b-nav-item>
            <b-nav-item v-if="isAuthor()" to="/home">Home</b-nav-item>
            <b-nav-item v-if="isAuthenticated()" to="/tasks">Tasks</b-nav-item>
          </b-navbar-nav>
          <b-navbar-nav class="ml-auto">
            <b-nav-item v-if="isAuthenticated()" v-on:click="signout()">Sign Out</b-nav-item>
            <b-nav-item v-if="!isAuthenticated()" to="/signin">Sign In</b-nav-item>
            <b-nav-item v-if="!isAuthenticated()" to="/signup">Sign Up</b-nav-item>
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>
    </div>
    <div id="content">
      <b-container fluid>
        <router-view/>
      </b-container>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    isAuthenticated() {
      return this.$store.getters.isAuthenticated;
    },
    isAuthor() {
      return this.$store.getters.isAuthor;
    },
    isChiefEditor() {
      return this.$store.getters.isChiefEditor;
    },
    isFieldEditor() {
      return this.$store.getters.isFieldEditor;
    },
    isReviewer() {
      return this.$store.getters.isReviewer;
    },
    signout() {
      this.$store.commit("setUser", null);
      localStorage.clear();
      this.$router.push("/signin");
    }
  },
  created() {
    const authenticatedUser = localStorage.getItem("authenticatedUser");
    if (authenticatedUser) {
      this.$store.commit("setUser", JSON.parse(authenticatedUser).user);
    } else {
      this.$router.push("/signin");
    }
  }
};
</script>

<style>
#content {
  margin-top: 70px;
}
</style>
