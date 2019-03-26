<template>
    <div class="card-expansion">
      <md-card>
        <md-card-media>
          <img :src="image" alt="People">
        </md-card-media>

        <md-card-header>
          <div class="md-title">{{ name }}</div>
          <div class="md-subhead">{{ role }}</div>
        </md-card-header>

        <md-card-expand>
          <md-card-actions md-alignment="space-between">
            <md-card-expand-trigger>
              <md-button>Learn more</md-button>
            </md-card-expand-trigger>
          </md-card-actions>

          <md-card-expand-content>
            <md-card-content>
              {{ descr }}
            </md-card-content>
          </md-card-expand-content>
        </md-card-expand>
      </md-card>
    </div>   
</template>

<script>
export default {
  name: "Person",
  props: ['image','name','role','descr'],
};
</script>


<style scoped>
  .title {
    text-align: center;
  }
  .card-expansion {
    height: 480px;
    text-align: center;
  }
  .md-card {
    width: 320px;
    margin: 4px;
    display: inline-block;
    vertical-align: top;
  }
</style>