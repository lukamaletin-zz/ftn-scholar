<template>
  <div id="content">
    <b-row>
      <b-col md="6">
        <h2>Tasks</h2>
      </b-col>
      <b-col>
        <b-form-group horizontal>
          <b-input-group>
            <b-form-input v-model="filter" placeholder="Type to search..." autofocus/>
            <b-input-group-append>
              <b-btn :disabled="!filter" @click="filter = ''">Clear</b-btn>
            </b-input-group-append>
          </b-input-group>
        </b-form-group>
      </b-col>
    </b-row>
    <b-table striped hover :items="tasks" :fields="fields" :filter="filter" @row-clicked="details"></b-table>
  </div>
</template>

<script>
import ProcessService from "../api/process-service.js";
export default {
  data() {
    return {
      tasks: [],
      fields: [
        {
          label: "Task",
          key: "data.name"
        },
        {
          label: "Journal",
          key: "journal.name"
        },
        {
          label: "Created",
          key: "data.created",
          formatter: value => {
            return value != null ? new Date(value).toLocaleString() : "";
          }
        },
        {
          label: "Due",
          key: "deadlineDate",
          formatter: value => {
            return value != null ? new Date(value).toLocaleString() : "/";
          }
        }
      ],
      filter: null
    };
  },
  methods: {
    details(item) {
      this.$store.commit("setJournal", item.journal);
      this.$router.push({
        name: item.data.taskDefinitionKey,
        params: {
          processId: item.data.processInstanceId
        }
      });
    }
  },
  created() {
    ProcessService.getTasks(
      res => {
        this.tasks = res.data;
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
  padding-left: 10%;
  padding-right: 10%;
}
</style>
