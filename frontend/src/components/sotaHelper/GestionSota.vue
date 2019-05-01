<template>
    <ul id="articlesShow">
        <li v-for="article in articles" :key="article">
            <md-card md-with-hover>
                <md-card-header>
                    <div class="md-title">{{article.title}}</div>
                </md-card-header>

                <md-card-content>
                    Lorem ipsum dolor sit amet, consectetur adipisicing elit. Optio itaque ea, nostrum odio. Dolores, sed accusantium quasi non.
                </md-card-content>

            </md-card>
            
        </li>
    </ul>
</template>

<script>
import json from "@/assets/dummy-Results.json"
export default {
    name:"SotAGestion",
    data(){
        return{
            json,
            articles:json.results.articles
        }
    }
}
</script>

<style scoped>

</style>
