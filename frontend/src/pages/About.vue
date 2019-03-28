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
      <person image="https://i.imgur.com/zy9W4LX.jpg" name="Christelle Trinon" role="Product Owner" descr="Description"></person>
      <person image="https://i.imgur.com/iiXpIYv.jpg" name="Guillaume Maître" role="Scum Master" descr="Description"></person>
      <person image="https://i.imgur.com/5uuVNrI.jpg" name="Bastien Müllers" role="Infrastructure Manager" descr="Description"></person>
      <br>
      <person image="https://i.imgur.com/0gt2KG4.jpg" name="David Adegnon" role="Team Member" descr="Description"></person>
      <person image="https://i.imgur.com/rRJjme8.jpg" name="William Turatsinze" role="Team Member" descr="Description"></person>
      <person image="https://i.imgur.com/LIKBZf0.jpg" name="Ludovic Wasterlain" role="Team Member" descr="Description"></person>
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