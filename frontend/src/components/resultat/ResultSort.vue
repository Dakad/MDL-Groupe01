<template>
  <div class="sort-options">
    <md-list>
      <md-subheader>
        <md-icon>ballot</md-icon>
        <span class="md-list-item-text">Sort By</span>
      </md-subheader>

      <md-list-item>
        <md-radio v-model="sortBy" value="name" @change="onChangeSortBy($event)"/>
        <span class="md-list-item-text">Name</span>
      </md-list-item>

      <md-list-item>
        <md-radio v-model="sortBy" value="title" @change="onChangeSortBy($event)"/>
        <span class="md-list-item-text">Title</span>
      </md-list-item>

      <md-list-item>
        <md-radio v-model="sortBy" value="date" @change="onChangeSortBy($event)"/>
        <span class="md-list-item-text">Date</span>
      </md-list-item>

      <md-divider></md-divider>

      <md-subheader>
        <md-icon>filter_list</md-icon>
        <span class="md-list-item-text">Order By</span>
      </md-subheader>

      <md-list-item>
        <md-radio v-model="orderBy" value="asc" @change="onChangeOrderBy($event)"/>
        <span class="md-list-item-text">Ascending</span>
      </md-list-item>

      <md-list-item>
        <md-radio v-model="orderBy" value="desc" @change="onChangeOrderBy($event)"/>
        <span class="md-list-item-text">Descending</span>
      </md-list-item>
    </md-list>

    <small>{{ sortBy }} + {{ orderBy }}</small>
  </div>
</template>

<script>
export default {
  props: {
    sort: String,
    order: String,
    activetTab: String
  },
  data() {
    return {
      sortBy: this.sort,
      orderBy: this.order
    };
  },
  computed: {},
  methods: {
    onChangeSortBy(by) {
      this.$emit("change:sort", this.sortBy);
    },
    onChangeOrderBy(by) {
      this.$emit("change:order", this.orderBy);
    }
  }
};
</script>

<style scoped>
.md-list {
  margin: 10px;
  display: inline-block;
}
</style>
