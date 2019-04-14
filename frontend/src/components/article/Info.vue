<template>
  <div>
    <p>Date of publication : {{ datePublication }}</p>
    <p>Date of creation : {{ dateCreation }}</p>
    <p>
      Link :
      <a :href="link">{{ link }}</a>
    </p>
    <p>Number of citation : {{ nbCitation }}</p>
    <p>Number of page : {{nbPage}}</p>
    <p>Number of views : {{ nbVue }}</p>
  </div>
</template>

<script>
import json from "@/assets/dummy-Article.json";
export default {
  name: "Info",
  props: ["info"],
  data() {
    return {
      json,
      datePublication: json.published_at,
      dateCreation: json.created_at,
      link: json.link,
      nbCitation: json.nb_citations,
      nbPage: json.nb_pages,
      nbVue: json.nb_views
    };
  }
};
</script>

<style>
</style>
