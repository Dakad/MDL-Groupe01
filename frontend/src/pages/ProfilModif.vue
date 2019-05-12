<template>
    <div class="general">
      <div class="left">
        <div class="md-layout">
          <md-field>
            <md-icon class="material-icons">
              insert_photo
            </md-icon>
            <label>Change your avatar</label>
            <md-input v-model="avatarimg"></md-input>
          </md-field>
          <md-autocomplete v-model="university" :md-options="universities">
            <md-icon class="material-icons">
              home
            </md-icon>
            <label>Change your university</label>
          </md-autocomplete>
          <md-autocomplete v-model="domain" :md-options="domains" >
            <md-icon class="material-icons">
              keyboard
            </md-icon>
            <label>Change your domain</label>
          </md-autocomplete>
          <md-field>
            <label>Change your bio</label>
            <md-textarea v-model="bio"></md-textarea>
          </md-field>
        </div>
      </div>
      <div class="right">
        <md-field>
          <label>Change your email address</label>
          <md-icon class="material-icons">
            email
          </md-icon>
          <md-input v-model="emailAddress" ></md-input>
        </md-field>
        <md-field>
          <label>Change your research groups</label>
          <md-icon class="material-icons">
            group
          </md-icon>
          <md-input v-model="rGroup" md-autogrow></md-input>
        </md-field>
        <md-field>
          <label>Change your interest</label>
          <md-icon class="material-icons">
            location_searching
          </md-icon>
          <md-input v-model="interest" md-autogrow></md-input>
        </md-field>
      </div>
    </div>
</template>

<script>
  import { postModificationProfile, getProfileBase, getProfileInfoPro} from '@/services/api-user'
  import { EventBus, EVENT_USER_LOGOUT, EVENT_BYE_REDIRECTION } from '@/services/event-bus.js';

    export default {
        name: "ProfilModif",
      data: () =>({
        university: null,
        domain: null,
        emailAddress: null,
        rGroup: 'Nadi, IRIDIA, BRUH',
        interest: 'Infovis, Bruh, IT',
        avatarimg: 'link to image',
        domains: ["ComputerScience", "Literature", "Chemistry", "Physics",
          "Biology", "Medicine", "Economics", "Economics", "Psychology",
          "Laws", "Mathematics", "Veterinary", "History", "Engineering",
          "Language"],
        universities: ["UNamur", "ULB", "Ulg", "UMons", "Kul", "Oxford", "MIT"],
        bio: "BLELBLE",
        username: null,
        profil: {},
      }),

      methods: {
        fetchProfileBase() {
          getProfileBase(this.username).then(data => {
            this.profil = data;
            console.log(this.profil);
          });
        },

        fetchDataPro() {
          getProfileInfoPro(this.username).then(function(data) {
            this.infoPro = data;
            console.log(this.infoPro);
          });
        },

        postData(){
          postModificationProfile()
        },
      },

      computed: {

      },

      created() {
          //todo get the information from BD
        this.username = this.$route.params["username"];

        this.fetchProfileBase()
        this.fetchDataPro()

        this.emailAddress = this.profil.email
        this.university = this.profil.university
        this.domain = this.profil.domain



        EventBus.$on(EVENT_USER_LOGOUT, _ => {
          this.$router.replace({ name : 'accueil' }, function onComplete() {
            EventBus.$emit(EVENT_BYE_REDIRECTION, true)
          })
        })

      },



    }
</script>

<style scoped>
.left{
  float: left;
  width: 41%;
  margin: 2%;
}
  .right{
    float: left;
    width: 41%;
    margin: 2%;
  }
</style>
