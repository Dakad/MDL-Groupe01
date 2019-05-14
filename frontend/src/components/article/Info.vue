<template>
  <div>
    <p>Date of publication : {{ publicationDate }}</p>
    <p>Date of creation : {{ info.created_at }}</p>
    <p>
      Link :
      <a :href="info.link" target="_blank">{{ info.link }}</a>
    </p>
    <p>Number of citation : {{ info.nb_citations }}</p>
    <p>Number of page : {{info.pages}}</p>
    <p>Number of views : {{ info.nb_views }}</p>
  </div>
</template>

<script>
  export default {
    name: "Info",
    props: {
      info: {
        type: Object,
        required: true,
        default: () => {}
      }
    },
    data() {
      return {};
    },
    computed: {
      publicationDate() {
        let str = this.info.year;
        if (this.info.month) {
          str += "-" + this.info.month;
        }
        return str;
      }
    }
  };
</script>

<style>
</style>
