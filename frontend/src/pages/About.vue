<template>
  <b-container fluid class="About">
    <b-row class="title">
      <b-col>
        <h1>About Us</h1>
        <p>Froggosaur est une application web qui vous permettra de rechercher et de créer des états de l'art.</p>
      </b-col>
    </b-row>
    
    <b-row class="title">
      <b-col>
        <h2>Our Team</h2>
      </b-col>
    </b-row>

    <div class="card-expansion">
      <person image="../assets/team/christelle.jpg" name="Christelle Trinon" role="Product Owner" descr="Description"></person>
      <md-card>
        <md-card-media>
          <img src="../assets/team/guillaume.jpg" alt="People">
        </md-card-media>

        <md-card-header>
          <div class="md-title">Guillaume Maître</div>
          <div class="md-subhead">Scrum Master</div>
        </md-card-header>

        <md-card-expand>
          <md-card-actions md-alignment="space-between">
            <md-card-expand-trigger>
              <md-button>Learn more</md-button>
            </md-card-expand-trigger>
          </md-card-actions>

          <md-card-expand-content>
            <md-card-content>
              Description
            </md-card-content>
          </md-card-expand-content>
        </md-card-expand>
      </md-card>

      <md-card>
        <md-card-media>
          <img src="../assets/team/bastien.jpg" alt="People">
        </md-card-media>

        <md-card-header>
          <div class="md-title">Bastien Müllers</div>
          <div class="md-subhead">Infrastructure Manager</div>
        </md-card-header>

        <md-card-expand>
          <md-card-actions md-alignment="space-between">
            <md-card-expand-trigger>
              <md-button>Learn more</md-button>
            </md-card-expand-trigger>
          </md-card-actions>

          <md-card-expand-content>
            <md-card-content>
              Description
            </md-card-content>
          </md-card-expand-content>
        </md-card-expand>
      </md-card>

      <br>
      <md-card>
        <md-card-media>
          <img src="../assets/team/david.jpg" alt="People">
        </md-card-media>

        <md-card-header>
          <div class="md-title">David Adegnon</div>
          <div class="md-subhead">Team Member</div>
        </md-card-header>

        <md-card-expand>
          <md-card-actions md-alignment="space-between">
            <md-card-expand-trigger>
              <md-button>Learn more</md-button>
            </md-card-expand-trigger>
          </md-card-actions>

          <md-card-expand-content>
            <md-card-content>
              Description
            </md-card-content>
          </md-card-expand-content>
        </md-card-expand>
      </md-card>

      <md-card>
        <md-card-media>
          <img src="../assets/team/william.jpg" alt="People">
        </md-card-media>

        <md-card-header>
          <div class="md-title">William Turatsinze</div>
          <div class="md-subhead">Team Member</div>
        </md-card-header>

        <md-card-expand>
          <md-card-actions md-alignment="space-between">
            <md-card-expand-trigger>
              <md-button>Learn more</md-button>
            </md-card-expand-trigger>
          </md-card-actions>

          <md-card-expand-content>
            <md-card-content>
              Description
            </md-card-content>
          </md-card-expand-content>
        </md-card-expand>
      </md-card>

      <md-card>
        <md-card-media>
          <img src="../assets/team/ludovic.jpg" alt="People">
        </md-card-media>

        <md-card-header>
          <div class="md-title">Ludovic Wasterlain</div>
          <div class="md-subhead">Team Member</div>
        </md-card-header>

        <md-card-expand>
          <md-card-actions md-alignment="space-between">
            <md-card-expand-trigger>
              <md-button>Learn more</md-button>
            </md-card-expand-trigger>
          </md-card-actions>

          <md-card-expand-content>
            <md-card-content>
              Description
            </md-card-content>
          </md-card-expand-content>
        </md-card-expand>
      </md-card>
    </div>   
  </b-container>



</template>

<script>
import Person from "../components/about/Person.vue"
export default {
  name: "About",
  components: {
    Person
  }
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