<template>
    <div class="social">
        <div class ="column">
            <h2>Social Informations</h2>
            <ul class="social-icons">
              <!--TODO : GET SOCIAL LINK FOR PROFILE-->
                <li><a href="http://www.facebook.com"><img src="@/assets/Facebook_Logo.png" /></a></li>
                <li><a href="http://www.twitter.com"><img src="@/assets/Twitter_Logo.png" /></a></li>
                <li><a href="http://www.youtube.com"><img src="@/assets/Linkedin_logo.png" /></a></li>
            </ul>
            <md-card>
                <md-card-header>
                    <div class="md-title">Biography</div>
                </md-card-header>

                <md-card-content>
                  {{jsonFollow.Biography}}
                </md-card-content>
            </md-card>

        </div>
        <div class ="column">
            <h2>Followed : {{nbrFollowed}}</h2>
            <!--TODO: Use authorList component-->
              <ul id="Followed">
                <li v-for="people in jsonFollow.Followed" :key="people">{{ people }}</li>
              </ul>
        </div>
        <div class ="column">
            <h2>Followers : {{nbrFollowers}}</h2>
              <ul id="Follower">
                <li v-for="people in jsonFollow.Follower" :key="people">{{ people }}</li>
              </ul>
        </div>
    </div>
</template>

<script>
import authorList from "@/components/resulat/AuthorList";
import jsonFollow from "@/assets/dummy-Follow.json"
export default {
  components:{authorList},
  data(){
    return{
      jsonFollow,
      loading:false,
    }
  },
  computed:{
      nbrFollowers:function(){
        return this.jsonFollow.Follower.length
      },
      nbrFollowed:function(){
        return this.jsonFollow.Followed.length

      }
    }
}
  
    
</script>
<style scoped>
.column {
  float: left;
  width: 33.33%;
  padding: 5px;
}

.social-icons li {
  display:inline-block;
  list-style-type:none;
  -webkit-user-select:none;
  -moz-user-select:none;
  }
.social-icons li a {
  border-bottom: none;
  }
.social-icons li img {
  width:70px;
  height:70px;
  margin-right: 20px;
}
</style>


