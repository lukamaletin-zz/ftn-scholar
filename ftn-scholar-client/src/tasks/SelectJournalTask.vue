<template>
  <div id="content">
    <h1>Select journal</h1>
    <b-form @submit="selectJournal" class="mt-4 mb-4">
      <b-form-group label="Journal">
        <b-form-select v-model="journal">
          <option v-for="option of journals" :key="option.id" :value="option">{{option.name}}</option>
        </b-form-select>
      </b-form-group>
      <b-btn type="submit" variant="primary" :block="true">Select</b-btn>
    </b-form>
  </div>
</template>

<script>
import JournalService from "../api/journal-service.js";
import ProcessService from "../api/process-service.js";
export default {
  data() {
    return {
      journals: [],
      journal: null
    };
  },
  methods: {
    selectJournal(e) {
      e.preventDefault();
      ProcessService.completeTaskWithVariables(
        this.$route.params["processId"],
        this.$route.name,
        { isOpenAccess: this.journal.id, journalId: this.journal.id },
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
    JournalService.findAll(
      res => {
        this.journals = res.data;
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
