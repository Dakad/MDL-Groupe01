<template>
  <div class="social">
    <div class="column">
      <h2>Biography</h2>
      <md-card>
        <md-card-header>
          <div class="md-title"></div>
        </md-card-header>

        <md-card-content>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Tristique senectus et netus et malesuada. In mollis nunc sed id. Posuere morbi leo urna molestie at elementum eu facilisis. Elementum pulvinar etiam non quam lacus suspendisse faucibus. Molestie nunc non blandit massa enim nec dui nunc. At augue eget arcu dictum varius. Ullamcorper a lacus vestibulum sed. Nisl tincidunt eget nullam non nisi est sit. Pretium viverra suspendisse potenti nullam ac tortor. Erat pellentesque adipiscing commodo elit at imperdiet dui accumsan sit. Velit aliquet sagittis id consectetur purus ut faucibus pulvinar elementum. Sit amet cursus sit amet. Eget sit amet tellus cras. Placerat in egestas erat imperdiet. Massa massa ultricies mi quis hendrerit dolor magna eget. Elementum nisi quis eleifend quam adipiscing vitae proin sagittis. Sit amet nisl purus in mollis nunc sed id semper. Commodo viverra maecenas accumsan lacus vel. Sit amet risus nullam eget felis eget nunc lobortis.</md-card-content>
      </md-card>
    </div>
    <div class="column">
      <h2>Followed : </h2>
      <!--TODO: Use authorList component-->
      <ul id="Followed">
         <!--<li v-for="people in jsonFollow.Followed" :key="people">{{ people }}</li>-->
      </ul>
    </div>
    <div class="column">
      <h2>Followers : </h2>
      <ul id="Follower">
        <!--<li v-for="people in jsonFollow.Follower" :key="people">{{ people }}</li>-->
      </ul>
    </div>
  </div>
</template>

<script>
import authorList from "@/components/resultat/AuthorList";

export default {
  components: {
    /*authorList*/
  },
  data() {
    return {
      loading: false
    };
  },
  computed: {
    nbrFollowers: function() {
      //return this.jsonFollow.Follower.length;
    },
    nbrFollowed: function() {
      //return this.jsonFollow.Followed.length;
    }
  }
};
</script>
<style scoped>
.column {
  float: left;
  width: 33.33%;
  padding: 5px;
}
</style>


