<template>
  <div id="content">
    <h1>Scientific paper</h1>
    <b-btn
      class="mt-4"
      variant="link"
      v-on:click="startProcess()"
    >Start new paper submission process</b-btn>
    <b-list-group>
      <b-list-group-item v-for="journal of journals" :key="journal.id">
        {{journal.name}}
        <b-list-group>
          <b-list-group-item v-for="publication of journal.publications" :key="publication.id">
            Publication {{publication.volume}}/{{publication.issue}} ({{publication.date.substring(0, 10)}})
            <b-list-group>
              <b-list-group-item
                v-for="paper of publication.articles"
                :key="paper.id"
              >{{paper.title}}</b-list-group-item>
            </b-list-group>
          </b-list-group-item>
        </b-list-group>
      </b-list-group-item>
    </b-list-group>
  </div>
</template>

<script>
import JournalService from "../api/journal-service.js";
import ProcessService from "../api/process-service.js";
export default {
  data() {
    return {
      journals: []
    };
  },
  methods: {
    startProcess() {
      ProcessService.startProcess(
        "Process_paper_submission",
        { author: this.$store.getters.getUsername },
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
  padding-left: 20%;
  padding-right: 20%;
}
</style>
