<template>
  <div class="info md-layout md-alignment-top-center">
    <div class="infotop md-layout-item md-size-80 md-layout md-alignment-center-left">
      <div class="base md-layout-item md-size-50">
        <InfoBase :profil="profil"></InfoBase>
        </div>

      <div class="stats md-layout-item md-size-40">
        <StatsLink/>
      </div>
    </div>

    <div class="tabs md-layout-item md-size-90">
      <md-tabs>
        <md-tab id="profile" md-label="My profile">
          <MyProfile :infoPro="infoPro"></MyProfile>
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
          ></md-empty-state>-->
        </md-tab>
        <md-tab id="Social" md-label="Social">
          <Social/>
        </md-tab>
        <md-tab id="Bookmarks" md-label="Bookmarks">
          <Bookmarks :bookmarked="bookmarkList"/>
        <md-empty-state
          v-if="!bookmarkList || bookmarkList == 0"
          md-icon="view_module"
          md-label="No bookmarks found"
          md-description="Search for article/state of the art to bookmark them."
          ></md-empty-state>
        </md-tab>
      </md-tabs>
    </div>
  </div>
</template>

<script>
import InfoBase from "@/components/profil/InfoBase";
import MyProfile from "../components/profil/MyProfile";
import Social from "../components/profil/Social";
import StatsLink from "../components/profil/StatsLink";
import Bookmarks from "../components/profil/Bookmarks"
import {
  getProfileBase,
  getBookmark,
  getProfileInfoPro,
  getProfileSota
} from "../services/api-user";
import { EventBus, EVENT_USER_LOGOUT, EVENT_BYE_REDIRECTION } from '@/services/event-bus.js';

export default {
  name: "profil",
  components: { MyProfile, InfoBase, Social, StatsLink, Bookmarks },
  data() {
    return {
      profil: {},
      bookmarkList: {},
      sota: {},
      reference:null,
      infoPro: {},
      username: {},
      loading: false
    };
  },
  created() {
    this.username = this.$route.params["username"];
    this.fetchProfile();
    this.fetchBookmark()
    //this.fetchSota();
    this.fetchDataPro();
        EventBus.$on(EVENT_USER_LOGOUT, _ => {
      this.$router.replace({ name : 'accueil' }, function onComplete() {
        EventBus.$emit(EVENT_BYE_REDIRECTION, true)       
      })
    })
  },
  methods: {
    fetchProfile() {
      getProfileBase(this.username).then(data => {
        this.profil = data;
      });
    },
    fetchBookmark() {
      getBookmark(this.username).then(data => {
        this.bookmarkList = data;
      });
    },
    fetchSota() {
      getSota(this.reference).then(data =>{ 
      this.sota = data});
    },
    fetchDataPro() {
      getProfileInfoPro(this.username).then(data => {
        this.infoPro = data;
      });
    }
  }
};
</script>

<style scoped>
/* .info {
  position: absolute;
  height: 100%;
  width: 100%;
} */

.infotop {
  margin-top: 2%;
}

/* .infotop {
  position: relative;
  height: 30%;
  width: 100%;
} */
/* .stats {
  position: relative;
  left: 40%;
  bottom: 28%;
  width: 40%;
  height: 15%;
  margin-top: 2%;
} */

.tabs {
  /* position: relative;
  height: 60%;
  width: 100%; */
  margin-top: 2%;
}

#profile {
  height: 370px;
}
</style>
