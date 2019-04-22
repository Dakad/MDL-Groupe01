<template>
  <div class="info">
    <div class="infotop">
    <InfoBase :profil="profil"></InfoBase>
    </div>

    <div class="tabs">
    <md-tabs>
      <md-tab id="profile" md-label="My profile">
        <MyProfile></MyProfile>
      </md-tab>
      <md-tab id="Sota" md-label="State Of The Art">
        <!-- TODO avoir la fonction envoyer les SOTA et adapter le code à la list reçues
        <h5>Owned SotA</h5>
        <sota-list v-show="!loading" :list="sotaList.owned"></sota-list>
        <md-empty-state
          v-if="!sotaList.owned || sotaList.owned == 0"
          md-icon="view_module"
          md-label="No owned state of the art found"
          md-description="Create State of the art to see them here."
        ></md-empty-state>
        <h5>Followed SotA</h5>
        <sota-list v-show="!loading" :list="sotaList.followed"></sota-list>
        <md-empty-state
          v-if="!sotaList.followed || sotaList.followed == 0"
          md-icon="view_module"
          md-label="No owned state of the art found"
          md-description="Create State of the art to see them here."
        ></md-empty-state>  -->
      </md-tab>
      <md-tab id="Social" md-label="Social">

      </md-tab>
      <md-tab id="Bookmarks" md-label="Bookmarks">
        <sota-list v-show="!loading" :list="bookmarkList"></sota-list>
        <md-empty-state
          v-if="!bookmarkList || bookmarkList == 0"
          md-icon="view_module"
          md-label="No bookmark found"
          md-description="Search for article to bookmark them."
        ></md-empty-state>
      </md-tab>
    </md-tabs>
    </div>
  </div>
</template>

<script>
  import InfoBase from "@/components/profil/Info-base";
  import {getProfileBase, getBookmark} from "../services/api-user";
  import MyProfile from "../components/profil/MyProfile";


  export default {
      name: "profil",
      components: {MyProfile, InfoBase},
      data() {
        return{
          profil: null,
          bookmarkList: null,
          sotaList: null
        }
      },
      created() {
        this.fetchProfile()
        this.fetchBookmark()
        this.fetchSota()
      },
      methods: {
        fetchProfile(){
          getProfileBase(username).then(function(data){
            this.profil = data;
            console.log(profil);
          })
        },
        fetchBookmark(){
          getBookmark(username).then(function(data){
            this.bookmarkList = data;
            console.log(bookmarkList);
          })
        },
        fetchSota(){
          getBookmark(username).then(function(data){
            this.sotaList = data;
            console.log(sotaList);
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
