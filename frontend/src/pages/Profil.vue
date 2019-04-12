<template>
  <div class="info">
    <InfoBase></InfoBase>
  </div>
</template>

<script>
  import InfoBase from "@/components/profil/Info-base";
    export default {
        name: "profil",
      components: {InfoBase}
    }
</script>

<style scoped>
.info {
  float: left;
  width: 20%;
}
</style>
