<template>
  <div id="content">
    <h1>Submit paper</h1>
    <b-form @submit="submit" class="mt-4 mb-4">
      <b-row>
        <b-col class="col-md-6">
          <b-form-group label="Title">
            <b-form-input
              type="text"
              v-model="metadata.title"
              required
              placeholder="Enter title"
              autofocus
            ></b-form-input>
          </b-form-group>
          <b-form-group label="Keywords">
            <b-form-input
              type="text"
              v-model="keywords"
              required
              placeholder="Enter keywords (comma separated)"
            ></b-form-input>
          </b-form-group>
          <b-form-group label="Abstract">
            <b-form-textarea
              v-model="metadata.paperAbstract"
              rows="5"
              max-rows="5"
              maxlength="999"
              required
              placeholder="Enter abstract"
            ></b-form-textarea>
          </b-form-group>
          <b-form-group label="Field">
            <b-form-select v-model="metadata.field" required>
              <option v-for="field of fields" :key="field.id" :value="field.value">{{field.name}}</option>
            </b-form-select>
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
          <b-btn class="mt-4" variant="success" :block="true" v-on:click="addAuthor()">Add author</b-btn>
          <b-btn class="mt-4" type="submit" variant="primary" :block="true">Submit</b-btn>
        </b-col>
        <b-col class="col-md-6">
          <div v-for="(coauthor, index) of metadata.coauthors" :key="coauthor.id">
            <b-form-group label="First name">
              <b-form-input
                type="text"
                v-model="metadata.coauthors[index].firstName"
                required
                placeholder="Enter first name"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="Last name">
              <b-form-input
                type="text"
                v-model="metadata.coauthors[index].lastName"
                required
                placeholder="Enter last name"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="Email">
              <b-form-input
                type="email"
                v-model="metadata.coauthors[index].email"
                required
                placeholder="Enter email"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="City">
              <b-form-input
                type="text"
                v-model="metadata.coauthors[index].city"
                required
                placeholder="Enter city"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="Country">
              <b-form-input
                type="text"
                v-model="metadata.coauthors[index].country"
                required
                placeholder="Enter country"
              ></b-form-input>
              <b-form-group>
                <b-btn
                  class="mt-4"
                  variant="danger"
                  v-on:click="removeAuthor(index)"
                  :block="true"
                >Remove author {{index+1}}</b-btn>
              </b-form-group>
            </b-form-group>
          </div>
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
      metadata: { coauthors: [], paperAbstract: "" },
      keywords: "",
      fields: Util.scientificFields,
      file: null
    };
  },
  methods: {
    submit(e) {
      e.preventDefault();

      this.metadata.keywords = this.keywords.split(", ");
      PaperService.saveMetadata(
        this.$route.params["processId"],
        this.metadata,
        res => {
          const input = new FormData();
          input.append("file", this.file);
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
    },
    addAuthor() {
      this.metadata.coauthors.push({});
    },
    removeAuthor(index) {
      this.metadata.coauthors.splice(index, 1);
    }
  }
};
</script>

<style scoped>
#content {
  padding-left: 20%;
  padding-right: 20%;
}
</style>
