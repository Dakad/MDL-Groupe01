<template>
  <div class="info">
    <div class="infotop">
    <InfoBase :profil="profil"></InfoBase>
    </div>

    <div class="tabs">
    <md-tabs>
      <md-tab id="profile" md-label="My profile">

      </md-tab>
      <md-tab id="Sota" md-label="State Of The Art">
        <Sota/>
      </md-tab>
      <md-tab id="Social" md-label="Social">

      </md-tab>
      <md-tab id="Bookmarks" md-label="Bookmarks">

      </md-tab>
    </md-tabs>
    </div>
  </div>
</template>

<script>
  import InfoBase from "@/components/profil/Info-base";
  import Sota from "../components/profil/Sota";
  import {getProfileBase} from "../services/api-user";

  export default {
      name: "profil",
      components: {Sota, InfoBase},
      data() {
        return{
          profil: null,
        }
      },
      created() {
        this.fetchProfile()
      },
      methods: {
        fetchProfile(){
          getProfileBase(username).then(function(data){
            this.profil = data;
            console.log(profil);
          })
        }
      }
    }

</script>

<style scoped>
  .info{
    position: absolute;
    height: 100%;
    width: 100%;
  }

  .infotop {
    position: relative;
    height: 30%;
    width: 100%;
  }

  .tabs{
    position: relative;
    height: 60%;
    width: 100%;
  }
</style>
