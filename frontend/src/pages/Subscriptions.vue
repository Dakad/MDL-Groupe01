<template>
    <section class="md-layout">
        <div><h2>Articles recently posted by people you follow :</h2></div>
        <div class="articles">
            <article-list v-show="!loading" :list = "articles"></article-list>
        </div>
    </section>
</template>

<script>
import ArticleList from "@/components/resultat/ArticleList"
import { getSubscriptions } from '../services/api-article';
export default {
    name: 'Subscriptions',

    data() {
        return {
            articles: {},
            loading : false,
        };
    },

    created(){
        this.fetchSubscriptions();
    },

    methods:{
        fetchSubscriptions(){
            return getSubscriptions()
                .then(data => {this.articles = data;console.log(data);!this.loading});
        }
    },
}
</script>

<style scoped>
h2{
    margin-top : 25px;
    margin-left : 40px;
}

article-list{
    margin-top : 25px;
    margin-left : 40px;
    margin-right: 40px;
}
</style>