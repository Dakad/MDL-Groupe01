<template>
    <section class='Research'>
            <div class ='tabs'>
            <md-tabs>
                <md-tab id="sota" md-label="State Of The Art">
                  <!--Todo : Make a list of state art-->
                  <sota/>
                </md-tab>
                <md-tab id="article" md-label="Article">
                  <articles/>
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
      radio2: "asc"
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

