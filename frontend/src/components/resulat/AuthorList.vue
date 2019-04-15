<template>
  <div class="author">
    <ul v-for="(author, index) in list" :key="index">
      <router-link :to="{name: 'authorDetails', params : {slug : author.slug}}">{{author.name}}</router-link>
      <!-- <router-link :to="{name: 'profil', params : {slug : user.username}}">{{author.username}}</router-link> -->
    </ul>
  </div>
</template>

<script>
export default {
  props: {
    list: Array
  },
  data() {
    return {};
  },
  computed: {
    authors() {
      return this.list || [];
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
