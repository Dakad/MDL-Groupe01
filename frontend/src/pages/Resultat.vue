<template>
    <section class='Research'>
            <div class ='tabs'>
            <md-tabs>
                <md-tab id="sota" md-label="State Of The Art">
                  <!--Todo : Make a list of state art-->
                  <sota title="Action Movies" date="28-03-2019" v-bind:authors="['Jean-claude Van Damme', 'Bruce Willis']"/>
                  <sota title="Computer Sciences" date="20-06-1945" v-bind:authors="['Von neuman', 'Alan Turing', 'Ada Lovelace']"/>
                  <sota title="Complot" date="11-09-2001" v-bind:authors="['Georges Bush', 'Al Gore']"/>
                </md-tab>
                <md-tab id="article" md-label="Article">
                  <!--Todo : make a list of article-->
                  <articles title="La visualisation des ovnis" date="01-01-2019" v-bind:authors="['Bruno Dumas']"/>
                  <articles title="Le réchauffement climatique en musique" date="27-09-2005" v-bind:authors="['Joe Duplantier',' Mario Duplantier', ' Christian Andreu', 'Jean-Michel Labadie', 'Alexandre Cornillon']"/>
                  <articles title="Machine Learning : visualisation quality" date="08-12-2018" v-bind:authors="['Benoit Frenay','Adrien Bibal']"/>
                  <articles title="Global warming: Creation of china " date="01-04-2019" v-bind:authors="['Donald Trump']"/>
                </md-tab>
                <md-tab id="author" md-label="Author">
                  <author/>
                </md-tab>
                <md-tab id="graphics" md-label="Graphics">
                  <graphics/>
                </md-tab>
            </md-tabs>
        </div>



          <div class="first">
            <h2>Sort by :</h2>
            <md-radio v-model="radio1" value="domain">Domain of Research</md-radio>
            <md-radio v-model="radio1" value="date">Date</md-radio>
            <md-radio v-model="radio1" value="views">Views</md-radio>
            <md-radio v-model="radio1" value="ref">References</md-radio>
          </div>
          <div class="second">
            <md-radio v-model="radio2" value="asc">Ascending</md-radio>
            <md-radio v-model="radio2" value="des">Descending</md-radio>
            <small>{{ radio1 }} + {{ radio2 }} </small>
          </div>

    </section>
</template>

<script>
import sota from '@/components/result/Sota'
import author from '@/components/result/Author'
import graphics from '@/components/result/Graphics'
import articles from '@/components/result/Article'
export default {
    components :{
       sota, author, graphics, articles

    },
    data: () => ({
      radio1: "domain",
      radio2: "asc",
    })
}
</script>

<style scoped>
.tabs{
    position: absolute;
    top: 15%;
    left:25%;
}

.first{
    position: relative;
    top: 15%;
    left: 5%;
}
  
.second{
    position: relative;
    top: 60%;
    left: 5%;
}
.md-radio {
    display: flex;
  }

</style>

