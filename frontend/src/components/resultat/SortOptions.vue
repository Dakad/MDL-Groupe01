<template>
  <div class="sort-options">
    <md-list>
      <md-subheader>
        <md-icon>ballot</md-icon>
        <span class="md-list-item-text">Sort By</span>
      </md-subheader>

      <md-list-item v-if="active == 'authors' ">
        >
        <md-radio v-model="sortBy" value="name" @change="onChangeSortBy($event)"/>
        <span class="md-list-item-text">Name</span>
      </md-list-item>

      <md-list-item v-if="active == 'users' ">
        >
        <md-radio v-model="sortBy" value="username" @change="onChangeSortBy($event)"/>
        <span class="md-list-item-text">Username</span>
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

      <md-divider v-if="tags && tags.length > 0"></md-divider>

      <md-subheader v-if="tags && tags.length > 0">
        <md-icon>format_list_bulleted</md-icon>
        <span class="md-list-item-text">Tags</span>
      </md-subheader>

      <md-list-item>
        <div>
          <md-chip
            class="md-accent tag"
            v-for="(tag,index) in tags"
            :key="index"
            md-deletable
            @md-delete="$emit('tag:remove', tag)"
          >{{tag}}</md-chip>
        </div>
      </md-list-item>
    </md-list>
    <slot></slot>
  </div>
</template>

<script>
export default {
  name: "SortOptions",
  props: {
    sort: String,
    order: String,
    active: String,
    tags: Array
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
  }
};
</script>

<style scoped>
.md-list {
  margin: 10px;
  display: inline-block;
}
.tag {
  display: block;
  margin: 5px;
}
</style>
