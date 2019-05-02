<template>
  <div class="container">
    <div class="title">
      <h2>State of the Art Helper</h2>
    </div>

    <div class="tabs">
      <md-tabs md-alignment="fixed" md-active-tab="visu">
        <md-tab id="gestion" md-label="Gestion" md-icon="view_module"></md-tab>
        <md-tab id="visu" md-label="Visualisation" md-icon="share">
          <sota-graphic/>
        </md-tab>
        <md-tab id="recommanded" md-label="Recommanded" md-icon="thumb_up"></md-tab>
        <md-tab id="uploadOne" md-label="Upload new SotA" md-icon="plus_one">
          <create-sota></create-sota>
        </md-tab>
      </md-tabs>
    </div>
  </div>
</template>

<script>
import SotaCreate from "../components/sota-helper/SotaCreate";
import SotaGraphic from "@/components/sota-helper/SotaGraphic";

export default {
  name: "SotaHelper",
  components: { createSota: SotaCreate, SotaGraphic }
};
</script>

<style scoped>
.container {
  /* position: absolute;
  width: 100%;
  height: 86%;
  max-width: 1900px;
  margin-top: 1%; */
}

.title {
  position: relative;
  width: 100%;
  height: 10%;
}
h2 {
  text-align: center;
}

.tabs {
  width: 99%;
  height: 85%;
  margin-top: 3%;
}

#uploadOne {
  width: 100%;
  height: 550px;
}
</style>
