<template>
    <section class="md-layout">
        <div><h2>Title be like :</h2></div>
        <div class="articles">
            <article-list></article-list>
            <md-empty-state>
                <text>Bruh</text>
            </md-empty-state>
        </div>
    </section>
</template>

<script>
import ArticleList from "@/components/resultat/ArticleList"
import { getSubscriptions } from '../services/api-article';
//import Search from "@/components/navbar/Search"
export default {
    name: 'Subscriptions',

    data() {
        return {
            articles: {},
        };
    },

    created(){
        this.fetchSubscriptions();
    },

    methods:{
        fetchSubscriptions(){
            return getSubscriptions()
                .then(data => {this.articles = data;});
        }
    },
}
</script>

<style scoped>
h2{
    margin-top : 25px;
    margin-left : 40px;
}
</style>